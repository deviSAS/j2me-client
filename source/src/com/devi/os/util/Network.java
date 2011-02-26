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
