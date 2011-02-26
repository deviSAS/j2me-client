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

import java.util.Vector;

import com.devi.os.util.Language;
import com.devi.os.util.Settings;

import com.sun.lwuit.Form;
import com.sun.lwuit.Display;
import com.sun.lwuit.Container;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

public class AvatarsArea {
    public static void show(Vector names) {
        Settings.loading.loadingDispose();
        Settings.temp.add("VectorNames", names);

        Settings.UIData.set("screen", "AvatarArea");

        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Form avs = new Form();
                avs.addCommandListener(Settings.ui);

                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "1");

                avs.setTitle(Language.Titles[7]);
                avs.setLayout(new BorderLayout());

                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Vector nm = (Vector)Settings.temp.get("VectorNames");
                Settings.temp.del("VectorNames");

                int max = nm.size();


                ActionListener BotonAL = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                         String evento = evt.getCommand().getCommandName();
                         Settings.ui.Avatar = evento;
                         Settings.ui.subMenu(Language.AvatarNearMenu);
                    }
                };

                if(max > 0) {
                    int added = 0;
                    for(int i = 0; i < max; i++) {
                        if(!(nm.elementAt(i).toString().equals(Settings.loginDetails.get("UserName").toString()))) {
                            Button btn = new Button(new Command(nm.elementAt(i).toString()));
                            btn.addActionListener(BotonAL);
                            co.addComponent(btn); added++;
                        }
                    } if(added == 0) {
                        co.addComponent(new Label(Language.NoAvArea));
                    }
                } else {
                    co.addComponent(new Label(Language.NoAvArea));
                }

                avs.addComponent(BorderLayout.CENTER, co);
                avs.addCommand(new Command(""));
                avs.addCommand(new Command(Language.Buttons[3]));

                avs.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                avs.show();

                Settings.CurrentForm = avs;
            }
        });
    }
}
