package com.devi.os;

import com.devi.os.util.Session;
import com.devi.os.util.Settings;
import com.devi.os.util.Name2Key;

import com.devi.os.json.JSONArray;
import com.devi.os.json.JSONObject;
import com.devi.os.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

public class Events {
    private Timer check = new Timer();
    private String api = "api/events";
    private boolean single = false;

    public void Start() {
        if(single) return;
        else single = true;
        TimerTask timerTask = new TimerTask()  {
            public void run() {
                new Thread() {
                    public void run() {
                        try {
                            String queue = null;
                            queue = Settings.net.sendHttp(api, "sid=" + Session.SessionID);
                            if(queue != null) {
                                Settings.d.debug("[EVENTS] Got events string: " + queue);
                                try {
                                    JSONArray json = new JSONArray(queue);
                                    int queueLength = json.length();
                                    for(int i = 0; i < queueLength; i++) {
                                        try {
                                            JSONObject event = new JSONObject(json.getString(i));
                                            String MT = event.getString("MT");
                                            if(MT.equals("none")) return;
                                            if(MT.equals("chat")) {
                                                ChatLocal.addMessage(event.getString("from"), event.getString("msg"), event.getString("tipo"));
                                                return;
                                            }
                                            if(MT.equals("IM")) {
                                                String from = event.getString("from");
                                                Name2Key.addUser(from, event.getString("uuid"));
                                                IMSessions.IM(from, event.getString("sid"), event.getString("group"), from, event.getString("msg"));
                                                return;
                                            }
                                            if(MT.equals("avname")) {
                                                Friends.setName(event.getString("names"));
                                                return;
                                            }
                                            if(MT.equals("fstatus")) {
                                                if(event.getString("name").equals("null")) return;
                                                Friends.update(event.getString("name"), event.getBoolean("on"));
                                                return;
                                            }
                                            if(MT.equals("disconnected")) {
                                                //Cerrar datos de sesión
                                                //Salir y mostrar el error.
                                            }
                                        } catch(JSONException ie) {
                                        }
                                    }
                                    //String type = json.getString("MessageType");
                                    //if(validType(type)) sendData(type, event);
                                } catch (JSONException ex) { }
                            }
                        } catch(Exception e) {}
                    }
                }.start();
            }
        };
        check.schedule(timerTask, 0, Settings.EventTime);
    }
}
