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
