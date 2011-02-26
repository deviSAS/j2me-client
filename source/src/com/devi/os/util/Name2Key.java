/*
 * Guarda el nombre de los usuarios y su key
 *  - IMSessions
 *  - Friends
 */

package com.devi.os.util;

public class Name2Key {
    private static List users = new List();
    private static String nullKey = "00000000-0000-0000-0000-000000000000";

    public static void addUser(String name, String key) {
        users.add(name, key);
    }

    public static String getKey(String name) {
        String _key = users.get(name).toString();
        if(_key == null) return nullKey;
        return _key;
    }
}
