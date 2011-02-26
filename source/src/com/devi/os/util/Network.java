package com.devi.os.util;

import javax.microedition.io.*;
import java.io.*;

public class Network {
    private boolean netStatus = false;
    private String url = "http://SERVERURI:7000/";

    public String sendHttp( String path, String params ) throws IOException {

        HttpConnection        http = null;
        InputStream           is = null;
        OutputStream          os = null;
        String                message = "";
        
        params = "start&" + params + "&end"; //Server workaround.
        
        try {
            http = ( HttpConnection ) Connector.open( this.url + path );
            http.setRequestMethod(HttpConnection.POST);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-length", "" + params.getBytes().length);

            os = http.openOutputStream();
            os.write(params.getBytes());
            //os.flush();
            
            int respCode;

            try { respCode = http.getResponseCode(); }
            catch(Exception e) { return null; }

            if (respCode == http.HTTP_OK) {
                int len = (int)http.getLength();
                is = http.openDataInputStream();

                byte[] data = null;

                if (len != -1) {
                    data = new byte[len];
                    int bytesRead = is.read(data);
                } else {
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    int ch;
                    int count = 0;

                    while ((ch = is.read()) != -1) {
                        bo.write(ch);
                        count++;
                    }
                    data = bo.toByteArray();
                    bo.close();
                } message = new String(data);
            } else {
                Settings.d.debug("[NETWORK][ERROR] HTTP Response code: " + respCode);
            }
        } finally {
            if( http != null ) http.close();
            if( os != null ) os.close();
            if( is != null ) is.close();
        }
        //d.debug("[NETWORK] HTTP Response: " + message.toString());
        return message.toString();
    }
}
