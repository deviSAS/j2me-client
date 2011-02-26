package com.devi.os.util;

import com.devi.os.Events;
import com.devi.os.MainUI;

import com.sun.lwuit.Form;
import com.sun.lwuit.TextField;
import com.sun.lwuit.ComboBox;

public class Settings {
    public static int midletWidth = 0;
    public static int midletHeight = 0;
    public static boolean LoggedIn = false;
    public static Events events = new Events();
    public static MainUI ui;
    public static String loadingText;
    public static Network net = new Network();
    public static int EventTime = 10000;
    public static List loginDetails = new List();
    public static Debug d = new Debug();
    public static List temp = new List();
    public static List UIData = new List();
    public static Loading loading = new Loading();
    public static TextField[] fields = new TextField[10];
    public static Form CurrentForm = new Form();
    public static Dialogs dialog = new Dialogs();
    public static URLEncode encode = new URLEncode();
    public static boolean HaveFriends = false;
    public static String[] Grids = {"deviOS", "OSGrid", "ReactionGrid"};
    public static ComboBox GridCombo = null; // new ComboBox(Grids);
}
