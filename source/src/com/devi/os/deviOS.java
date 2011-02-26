package com.devi.os;

import javax.microedition.midlet.*;

//import com.devi.os.util.Dialogs;
//import com.devi.os.util.Language;
import com.devi.os.util.Settings;
import com.devi.os.util.MemoryTest;

import com.sun.lwuit.Display;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;


public class deviOS extends MIDlet {
    private Resources resources;
    public static int midletWidth = 0;
    public static int midletHeight = 0;

    public void startApp() {
        Display.init(this);
        try {
            this.resources = Resources.open("/deviThemes.res");
            UIManager.getInstance().setThemeProps(resources.getTheme("Green"));
        } catch (Exception ex) { ex.printStackTrace(); }
        
        MemoryTest run = new MemoryTest();

        Settings.ui = new MainUI(this);
        Settings.ui.showScreen("Init");
        
        //Dialogs dialog = new Dialogs();
        //dialog.showDialog(Language.Titles[4], Language.Labels[1], null);
        //Settings.loading.showLoading("Probando");

    }

    protected Resources getResources(){
        return this.resources;
    }

    public void destroyApp(boolean unconditional) {
        this.notifyDestroyed();
    }

    public void pauseApp() {}
}
