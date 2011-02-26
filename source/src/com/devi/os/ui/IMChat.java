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
import com.devi.os.IMSessions;
import com.devi.os.util.Language;

import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.animations.CommonTransitions;

import java.util.Vector;

public class IMChat {
    public static void show(String id) {
        if(Settings.temp.has("IMsid")) Settings.temp.set("IMsid", id);
        else Settings.temp.add("IMsid", id);

        Settings.UIData.set("screen", "IMChat");

        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Form im = new Form();
                im.addCommandListener(Settings.ui);

                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "1");

                im.setTitle("IM: " + Settings.temp.get("IMname"));
                im.setLayout(new BorderLayout());

                // Procesar los mensajes
                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container chatBar = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Settings.d.debug("[UI-IMChat] " + Settings.temp.get("IMsid").toString());
                String IMSid = Settings.temp.get("IMsid").toString();
                if(IMSid.equals("new")) {
                    chatBar.addComponent(new Label(Language.Labels[2])); 
                } else {
                    Vector IMmsg = IMSessions.getMessages(IMSid);
                    int total = IMmsg.size();
                    if(total > 0) {
                        for(int i = (total-1); i >= 0; i--) {
                            chatBar.addComponent(new Label(IMmsg.elementAt(i).toString()));
                        }
                    } else {
                         chatBar.addComponent(new Label(Language.Labels[2]));
                    }
                }

                chatBar.setScrollable(true);

                Settings.fields[0] = new TextField();
                co.addComponent(Settings.fields[0]);
                co.addComponent(new Button(new Command(Language.Buttons[7])));

                //temp.addComponent(BorderLayout.NORTH, posLabel);
                im.addComponent(BorderLayout.CENTER, chatBar);
                im.addComponent(BorderLayout.SOUTH, co);

                im.addCommand(new Command(Language.Buttons[6]));
                im.addCommand(new Command(Language.Buttons[3]));

                im.setScrollable(false);
                im.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                im.show();

                Settings.CurrentForm = im;
            }
        });
    }
}
