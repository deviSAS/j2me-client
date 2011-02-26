/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devi.os.util;

public class LogOut {
    private String api = "api/logout";
    public void exit() {
        new Thread() {
            public void run() {
                try {
                    String s = Settings.net.sendHttp(api, "sid=" + Session.SessionID);
                    if(s.startsWith("true")) Settings.ui.LoggedOut(true);
                } catch (Exception e) {}
                Settings.ui.LoggedOut(false);
            }
        }.start();
    }
}
