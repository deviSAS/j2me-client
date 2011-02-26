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

import com.devi.os.util.Name2Key;
import com.devi.os.util.Settings;
import com.devi.os.util.Session;
import com.devi.os.ui.AvatarsArea;

import com.devi.os.json.JSONArray;
import com.devi.os.json.JSONObject;
import com.devi.os.json.JSONException;

import java.util.Vector;

public class AvatarsNear {
    private String api = "api/send";
    
    public void refresh() {
        new Thread() {
            public void run() {
                try {
                    Vector names = new Vector();
                    String AvatarsList = Settings.net.sendHttp(api, "MT=avatarlist&sid=" + Session.SessionID);
                    try {
                        JSONArray lista = new JSONArray(AvatarsList);
                        int total = lista.length();
                        for(int i = 0; i < total; i++) {
                            String thisav = lista.getString(i);
                            try {
                                Settings.d.debug("[AVATARS] Got: " + thisav);
                                JSONObject av = new JSONObject(thisav);
                                names.addElement(av.get("name"));
                                Name2Key.addUser(av.getString("name"), av.getString("id"));
                            } catch (JSONException ex) { Settings.d.debug("[AVATARS] JSON Error: " + ex.getMessage() ); }
                        } AvatarsArea.show(names);
                    } catch(JSONException ex) {
                       Settings.d.debug("[AVATARS] JSON Error: " + ex.getMessage() );
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    //anunciar fallo.
                }
            }
        }.start();
    }
}
