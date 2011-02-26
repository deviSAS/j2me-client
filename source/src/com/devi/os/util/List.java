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
