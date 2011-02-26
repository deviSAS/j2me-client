package com.devi.os.util;

import java.io.IOException;
import com.devi.os.json.JSONObject;
import com.devi.os.json.JSONException;

public class Session {
    public static String SessionID = "GET";
    public static String Challenge = null;
    private static String api = "api/new";

    public static void newSession() {
        String response = null;
        try {
            Settings.d.debug("[SESSION] Getting SID");
            response = Settings.net.sendHttp("api/new", "login=mobile");
             try {
                JSONObject json = new JSONObject(response);
                SessionID = json.getString("id");
                Challenge = json.getString("ch");
                Settings.d.debug("[SESSION] Got SessionID: " + SessionID);
            } catch (JSONException ex) { Settings.d.debug("[SESSION] JSON: " + ex.getMessage()); }

        } catch(IOException e) { Settings.d.debug("[SESSION] Exception: " + e.getMessage()); }

    }
}
