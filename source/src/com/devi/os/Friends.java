/*
    Copyright (c) 2011, DEVI S.A.S and its contributors.

    See http://github.com/deviSAS/j2me-client/wiki/Contributors
    for a full list of copyright holders.

    Redistribution and use in source and binary forms,
    with or without modification, are permitted provided
    that the following conditions are met:

    - Redistributions of source code must retain the above
      copyright notice, this list of conditions and the following
      disclaimer.

    - Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials
      provided with the distribution.

    - Neither the name of the <DEVI S.A.S> nor the names of its
      contributors may be used to endorse or promote products
      derived from this software without specific prior written
      permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.

 */

package com.devi.os;

import com.devi.os.util.List;

import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

import com.devi.os.util.Name2Key;
import com.devi.os.util.Settings;
import com.devi.os.util.Session;
import com.devi.os.ui.FriendsUI;

import com.devi.os.json.JSONArray;
import com.devi.os.json.JSONObject;
import com.devi.os.json.JSONException;

public class Friends {
    private static List FriendsList = new List();
    private String api = "api/send";
    private static List nameReq = new List();

    public void init() {
        new Thread() {
            public void run() {
                try {
                    int TotalOnline = 0;
                    String _total = Settings.net.sendHttp(api, "MT=friends_count&sid=" + Session.SessionID);

                    try {
                        JSONObject tString = new JSONObject(_total);
                        TotalOnline = Integer.parseInt(tString.getString("t"));
                    } catch(JSONException ex) {}
                    
                    if(TotalOnline <= 0) {
                        Settings.d.debug("[FRIENDS] Zero friends connected or found.");
                        return;
                    }

                    Settings.d.debug("[FRIENDS] Count: " + TotalOnline);
                    
                    for(int i = 0; i < TotalOnline; i += 5) {
                        String Avatars = Settings.net.sendHttp(api, "MT=friends&sid=" + Session.SessionID + "&s=" + i);
                        if(Avatars == null) {
                            Avatars = Settings.net.sendHttp(api, "MT=friends&sid=" + Session.SessionID + "&s=" + i);
                            if(Avatars == null) {
                                Settings.d.debug("[FRIENDS] Got null on second friends call for i: " + i);
                                return;
                            }
                        }

                        try {
                            JSONArray lista = new JSONArray(Avatars);
                            int total = lista.length();
                            for(int a = 0; a < total; a++) {
                                String thisav = lista.getString(a);
                                try {
                                    Settings.d.debug("[FRIENDS] Got: " + thisav);
                                    JSONObject av = new JSONObject(thisav);

                                    boolean status = av.getBoolean("on");
                                    String name   = av.getString("name");
                                    String uuid   = av.getString("id");
                                    if(name.equals("null")) {
                                        if(Name2Key.getKey(uuid) == null) {
                                            Settings.net.sendHttp(api, "MT=getname&id=" + uuid);
                                            nameReq.add(uuid, ((status) ? "1" : "0"));
                                        } else {
                                            name = Name2Key.getKey(uuid);
                                            if(FriendsList.has(name)) FriendsList.set(name, ((status) ? "1" : "0"));
                                            else FriendsList.add(name, ((status) ? "1" : "0"));
                                        }
                                    } else {
                                        if(FriendsList.has(name)) FriendsList.set(name, ((status) ? "1" : "0"));
                                        else FriendsList.add(name, ((status) ? "1" : "0"));
                                        Name2Key.addUser(name, uuid);
                                    }
                                } catch (JSONException ex) { Settings.d.debug("[FRIENDS] JSON Error: " + ex.getMessage() ); }
                            }
                        } catch(JSONException ex) {
                           Settings.d.debug("[FRIENDS] JSON Error: " + ex.getMessage() );
                        }
                    } 
                    if(FriendsList.count() > 0 && nameReq.count() == 0) Settings.HaveFriends = true;
                    else if(FriendsList.count() == 0 && nameReq.count() == 0) {
                        new Friends(){}.init();
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void setName(String names) {
        try {
            JSONObject json = new JSONObject(names);
            int max = nameReq.count();
            for(int i = 0; i<max; i++) {
                String id = FriendsList.keyAt(i).toString();
                String stat = FriendsList.valAt(i).toString();
                String name = null;
                try {
                    name = json.getString(id);
                    if(!FriendsList.has(name)) {
                        Settings.d.debug("[FRIENDS] Added friend name from server: " + name);
                        FriendsList.add(name, stat);
                        nameReq.del(id);
                    }
                } catch(JSONException ie) { }
            }
        } catch (JSONException e) {
            Settings.d.debug("[FRIENDS] JSONException at setName: " + e.getMessage());
        }

        if(FriendsList.count() > 0 && nameReq.count() == 0) Settings.HaveFriends = true;

    }
    public static void get() {
        Vector Online = new Vector();
        Vector Offline = new Vector();

        int max = FriendsList.count();
        for(int i = 0; i < max; i++) {
            String name = FriendsList.keyAt(i).toString();
            String stat = FriendsList.valAt(i).toString();

            if(stat.equals("1")) Online.addElement(name);
            else Offline.addElement(name);
        }

        FriendsUI.show(Online, Offline);
    }

    public static void scheduleGet() {
        TimerTask timerTask = new TimerTask()  {
            public void run() {
                // Agregar un contador de intentos
                if(Settings.HaveFriends) Friends.get();
                else Friends.scheduleGet();
            }
        };
        new Timer().schedule(timerTask, 3000);
    }

    public static void update(String name, boolean status) {
        /* Ya debería tener el amigo porq es cargado desde el init */
        String id = Name2Key.getKey(name);
        if(FriendsList.has(id)) FriendsList.set(id, ((status) ? "1" : "0"));
        else FriendsList.add(id, ((status) ? "1" : "0"));
    }
}
