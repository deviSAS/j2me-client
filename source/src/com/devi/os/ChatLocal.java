/* Guarda los mensajes del chat local, hasta 15 mensajes */

package com.devi.os;

import java.util.Vector;

public class ChatLocal {
    public static Vector ChatMessages = new Vector();
    public static Vector ChatType = new Vector();
    
    public static void addMessage(String from, String msg, String type) {
        clearOld();
        ChatType.addElement(type);
        ChatMessages.addElement(from + ": " + msg);
    }

    private static void clearOld() {
        if(ChatMessages.size() < 10) return;
        else {
            ChatType.removeElementAt(0);
            ChatMessages.removeElementAt(0);
        }
    }
}
