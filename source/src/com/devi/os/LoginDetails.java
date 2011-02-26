/*
 * Guarda toda la información inicial del usuario:
 * Isla, nombre, uuid, posición..
 */

package com.devi.os;

import com.devi.os.util.Session;
import com.devi.os.util.Settings;

import com.devi.os.json.JSONObject;
import com.devi.os.json.JSONException;

public class LoginDetails {
    private String api = "api/details";
    public void getLoginDetails() {
        /* Usar un nuevo hilo */
        new Thread() {
            public void run() {
                getDetails();
            }
        }.start();
    }

    public void getDetails() {
        Settings.d.debug("[LOGIN-DETAILS] Getting details..");
        try {
            String response = Settings.net.sendHttp(api, "session=" + Session.SessionID);
            Settings.d.debug("[LOGIN-DETAILS] Got: " + response);
            if(!response.startsWith("ERROR:")) {
                try {
                    JSONObject json = new JSONObject(response);

                    Settings.loginDetails.add("UserName", json.getString("name"));
                    Settings.loginDetails.add("AgentID", json.getString("id"));
                    Settings.loginDetails.add("MOTD", json.getString("motd"));
                    Settings.loginDetails.add("Position", json.getString("pos"));
                    Settings.loginDetails.add("Region", json.getString("reg"));
                    //LoginDetails.add("InventoryRoot", json.getString("InventoryRoot"));

                    //Dialogs dialog = new Dialogs();
                    //dialog.showDialog(Language.Titles[3], json.getString("MOTD"), null);
                    Settings.d.debug("[LOGIN-DETAILS] Message Of The Day: " + Settings.loginDetails.get("MOTD"));

                } catch (JSONException ex) {
                    Settings.d.debug("[LOGIN-DETAILS] JSONException: " + ex.getMessage());
                    Settings.loginDetails.add("Error", response);
                }
            } else {
                Settings.loginDetails.add("Error", response);
            }
        } catch (Exception e) {}
    }
}
