/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devi.os;

import com.devi.os.util.Session;
import com.devi.os.util.Settings;
import com.devi.os.util.Name2Key;

import java.util.Timer;
import java.util.TimerTask;

public class DataSend {
    private String api = "api/send";
    private String temp;
    private String id;
    private String to;
    private String ton;

    public void sendChat(String msg) {
        temp = msg;
        new Thread() {
                public void run() {
                    try {
                        String res = Settings.net.sendHttp(api, "MT=chat&type=1&chan=0&msg=" + Settings.encode.string(temp) + "&sid=" + Session.SessionID);
                        Settings.d.debug("[DATA-SEND] Response: " + res);
                    } catch(Exception e) {}
                    ReloadTimer();
                }
        }.start();
    }

    public void sendIM(String toUser, String _id, String msg) {
        to   = Name2Key.getKey(toUser);
        id   = _id; //(IMSessions.sidType(_id)) ? toUser : _id; // Si es grupo no se envía al sid
        temp = msg;
        new Thread() {
            public void run() {
                try {
                    String IM = "MT=im&" + ((id.equals("new")) ? "" : "id=" + id) + "&to=" + to + "&msg=" + Settings.encode.string(temp) + "&sid=" + Session.SessionID;
                    Settings.net.sendHttp(api, IM);
                } catch(Exception e) {}
                if(!id.equals("new")) IMSessions.IM(Settings.loginDetails.get("UserName").toString(), id, "false", "0", temp);
                else Settings.temp.add("IMnew", temp);
                ReloadTimer();
           }
        }.start();
    }

    private void ReloadTimer() {
        TimerTask timerTask = new TimerTask()  {
            public void run() {
                Settings.ui.reloadScreen();
            }
        };
        new Timer().schedule(timerTask, 3000);
    }
}
