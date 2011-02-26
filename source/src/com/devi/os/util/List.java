// Guarda los datos de la app:

// List.add("a", "b"); -> boolean
// List.get("a"); -> string
// List.set("a", "c"); -> boolean
// List.del("a"); -> boolean

package com.devi.os.util;
import java.util.Vector;

public class List {
    private Vector key = new Vector();
    private Vector val = new Vector();
    public boolean allowMultiple = false;

    public boolean add(String _key, Object _val) {
        if(pos(_key) == -1 || allowMultiple) {
            key.addElement(_key);
            val.addElement(_val);
            return true;
        } return false;
    }

    public Object get(String _key) {
        int kPos = pos(_key);
        if(kPos == -1) return null;
        return val.elementAt(kPos);
    }

    public boolean set(String _key, Object _new) {
        int kPos = pos(_key);
        if(kPos >= 0) {
            val.setElementAt(_new, kPos);
            return true;
        } return false;
    }

    public boolean del(String _key) {
        int kPos = pos(_key);
        if(kPos == -1) { return false; }
            key.removeElementAt(kPos);
            val.removeElementAt(kPos);
        return true;
    }

    private int pos(String _key) {
        if(key.isEmpty()) return -1;
        int size = key.size();
        for(int i = 0; i < size; i++) {
            String thisElement = key.elementAt(i).toString();
            if(thisElement.equals(_key)) return i;
        } return -1;
    }

    public int count() {
        return key.size();
    }

    public boolean has(String _key) {
        if(pos(_key) == -1) return false;
        return true;
    }

    public Object valAt(int p) {
        return val.elementAt(p);
    }
    
    public Object keyAt(int p) {
        return key.elementAt(p);
    }

    public void clean() {
        key.removeAllElements();
        val.removeAllElements();
    }
}
