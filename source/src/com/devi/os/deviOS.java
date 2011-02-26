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
