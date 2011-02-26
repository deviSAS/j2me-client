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
