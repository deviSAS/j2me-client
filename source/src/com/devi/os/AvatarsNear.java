
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
