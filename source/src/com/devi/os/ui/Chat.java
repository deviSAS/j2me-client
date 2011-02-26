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

package com.devi.os.ui;

import com.devi.os.util.Settings;
import com.devi.os.util.Language;
import com.devi.os.IMSessions;

import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Command;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;

public class Chat {
    public static void show() {
        Settings.UIData.set("screen", "Chat");
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "0");

                Form temp = new Form();
                temp.addCommandListener(Settings.ui);
                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                temp.setTitle(Language.Titles[6]);
                temp.setLayout(new BorderLayout());

                co.addComponent(new Button(new Command(Language.Titles[5])));
                co.addComponent(new Button(new Command(Language.Titles[7])));

                int n = IMSessions.sidCount();
                if(n > 0) {
                    for(int i = 0; i < n; i++) {
                        Button imSession = new Button(new Command(IMSessions.sidName(i)));
                        if(IMSessions.hasNew(i)) {
                            Image img = null;
                            try {
                                img = Image.createImage("/ico/new.png");
                            } catch(Exception e) { e.printStackTrace(); }
                            imSession.setIcon(img);
                            imSession.setAlignment(Button.CENTER);
                            imSession.setTextPosition(Button.LEFT);
                        }  co.addComponent(imSession);
                    }
                } else {
                    co.addComponent(new Label(Language.NoIM));
                }

                temp.addComponent(BorderLayout.CENTER, co);
                temp.addCommand(new Command(""));
                temp.addCommand(new Command(Language.Buttons[3]));

                temp.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                temp.show();
                
                Settings.CurrentForm = temp;
            }
        });
    }
}