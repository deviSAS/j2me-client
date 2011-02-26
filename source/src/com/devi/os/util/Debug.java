package com.devi.os.util;
public class Debug {
    private boolean _debug = true;
    public void debug(String text) {
        if(_debug) System.out.println(text);
    }
}
