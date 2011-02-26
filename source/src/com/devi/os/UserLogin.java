/*
 * Maneja el Login del usuario
 */

package com.devi.os;

import com.devi.os.util.Session;
import com.devi.os.util.Settings;

import com.devi.os.json.JSONObject;
import com.devi.os.json.JSONException;
import java.io.IOException;

import com.sun.lwuit.ComboBox;

public class UserLogin {
    private String  api = "api/login";
    private String error = "";
    private String[] loginData;

    public void login(String first, String last,  String pwd) {
        if(first.length() <= 0 ||
                last.length() <= 0 ||
                    pwd.length() <= 0) {
            Settings.ui.LoginStatus(false, "EmptyField");
            return;
        }
        
        Settings.temp.add("loginFirst", first);
        Settings.temp.add("loginLast", last);
        Settings.temp.add("loginPwd", pwd);
        
        Session.newSession();
        
        new Thread() {
            public void run() {
                Settings.d.debug("[LOGIN] Login started..");

                loginData = new String[] { Session.SessionID, Session.Challenge,
                                           Settings.temp.get("loginFirst").toString(),
                                           Settings.temp.get("loginLast").toString(),
                                           Settings.temp.get("loginPwd").toString()};

                Settings.temp.del("loginFirst");
                Settings.temp.del("loginLast");
                Settings.temp.del("loginPwd");

                String loginGrid = Settings.GridCombo.getSelectedItem().toString();
                Settings.d.debug("[LOGIN] Test: " + loginData[3] + ", " + loginData[4] + ", " + loginGrid);
                
                boolean result = false;
                try {
                    String response = Settings.net.sendHttp(api,
                            "logindata="+ Settings.encode.string(loginData[1]) + "\\" + Settings.encode.string(loginData[2]) + "\\" + Settings.encode.string(loginData[3]) + "\\" + Settings.encode.string(loginData[4]) + "\\26" +
                            "&session=" + loginData[0] +
                            "&location=last" +
                            "&grid=" + loginGrid + "&sim=abc");
                    try {
                        JSONObject json = new JSONObject(response);
                        if(json.getString("success").equals("1")) { 
                            result = true;
                        } else {
                            error = json.getString("message");
                        }
                        Settings.d.debug("[LOGIN] Result: " + error + " | " + response);
                    } catch (JSONException ex) {
                        error = response;
                    } Settings.ui.LoginStatus(result, error);
                } catch(IOException e) {
                    e.printStackTrace();
                    Settings.ui.LoginStatus(false, e.getMessage());
                }
            }
        }.start();
    }

    public String getError() {
        return this.error;
    }
}
