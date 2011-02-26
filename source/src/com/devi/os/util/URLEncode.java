package com.devi.os.util;
import java.io.IOException;

public class URLEncode {
    public String string(String s) throws IOException {
        StringBuffer n = new StringBuffer( s.length() );
        for (int i=0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            switch( c )
            {
                case ' ':
                    n.append("%20");
                break;
                case '+':
                    n.append("%2B");
                break;
                case '-':
                    n.append("%2D");
                break;
                case '/':
                    n.append("%2F");
                break;
                case '=':
                    n.append("%3D");
                break;
                case '?':
                    n.append("%3F");
                break;

                default:
                n.append( c );
                break;
            }
        }
        return n.toString();
    }
}
