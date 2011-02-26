package com.devi.os;

import com.devi.os.util.List;
import com.devi.os.util.Language;
import com.devi.os.util.Settings;

//import com.sun.lwuit.Display;
import java.util.Vector;

public class IMSessions {
    public  static List sesiones = new List();
    private static List mensajes = new List();
    private static List nuevo    = new List();
    private static String sID;

    public static void IM(String from, String sid, String group, String offline, String message) {
        if(!mensajes.allowMultiple) mensajes.allowMultiple = true;
        boolean update = false;
        String mensaje = (offline.equals("1") ? Language.OfflineMessage : "" ) + from + ": " + message;
        if(!sesiones.has(from)) {
            sesiones.add(from, sid); //((group.equals("true")) ? "GroupChat" : from));
            // Alertar nuevo IM
            //Display.getInstance().vibrate(200);
            //Display.getInstance().flashBacklight(400);
        }
        if(Settings.temp.get("IMname").toString().toLowerCase().equals(from) && Settings.temp.get("IMsid").toString().equals("new")) {
            Settings.temp.set("IMsid", sid);
            mensajes.add(sid, Settings.temp.get("IMnew"));
            Settings.temp.del("IMnew");
        }
        mensajes.add(sid, mensaje);
        nuevo.add(sid, "im");
        Settings.d.debug("[IM] New im: " + mensaje);
        if(update) Settings.ui.reloadScreen();

        cleanUp(sid);
    }

    public static Vector getMessages(String sid) {
        Settings.d.debug("[IM] Getting messages for " + sid + ". Total IM's: " + mensajes.count());
        int max = mensajes.count();
        Vector msg = new Vector();
        for(int i = 0; i < max; i++) {
            if(mensajes.keyAt(i).toString().equals(sid)) {
                msg.addElement(mensajes.valAt(i).toString());
            }
        } return msg;
    }

    /* Eliminar en un nuevo trhead y evitar que se detenga la app */
    private static void cleanUp(String sid) {
        sID = sid;
        new Thread() {
            public void run() {
                int max = mensajes.count();
                Vector msg = new Vector();
                for(int i = 0; i < max; i++) {
                    if(mensajes.keyAt(i).toString().equals(sID)) {
                        msg.addElement(mensajes.keyAt(i).toString());
                    }
                } if(msg.size() > 10) mensajes.del(msg.elementAt(0).toString());
            }
        }.start();
    }

    public static int sidCount() {
        return sesiones.count();
    }

    public static String sidName(int pos) {
        return sesiones.keyAt(pos).toString();
    }

    public static boolean hasNew(int pos) {
        String sid = sesiones.keyAt(pos).toString();
        boolean newMsg = nuevo.has(sid);
        if(newMsg) nuevo.del(sid);
        return newMsg;
    }

    public static boolean sidType(String sid) {
        if(!sesiones.has(sid)) return false;
        if(sesiones.get(sid).equals("GroupChat")) return true;
        return false;
    }
}
