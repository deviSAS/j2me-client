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
import com.sun.lwuit.TabbedPane;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

public class FriendsUI {
    public static void show(Vector online, Vector offline) {
        Settings.loading.loadingDispose();

        Settings.temp.add("FriendsOnline", online);
        Settings.temp.add("FriendsOffline", offline);

        Settings.UIData.set("screen", "Friends");

        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Form friends = new Form();
                friends.addCommandListener(Settings.ui);

                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "1");

                friends.setTitle(Language.Nombres[1]);
                friends.setLayout(new BorderLayout());
                friends.setScrollable(false);

                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Vector on  = (Vector)Settings.temp.get("FriendsOnline");
                Settings.temp.del("FriendsOnline");

                Vector off = (Vector)Settings.temp.get("FriendsOffline");
                Settings.temp.del("FriendsOffline");

                TabbedPane tp = new TabbedPane();
                tp.addTab(Language.Tab[0], getFriends(on, true));
                tp.addTab(Language.Tab[1], getFriends(off, false));

                friends.addComponent(BorderLayout.CENTER, tp);

                friends.addCommand(new Command(""));
                friends.addCommand(new Command(Language.Buttons[3]));

                friends.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                friends.show();

                Settings.CurrentForm = friends;
            }
        });
    }

    private static Container getFriends(Vector list, boolean menu) {
        Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        ActionListener BotonAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String evento = evt.getCommand().getCommandName();
                Settings.ui.Avatar = evento;
                Settings.ui.subMenu(Language.AvatarNearMenu);
                //Settings.ui.subMenu(Language.OfflineFriendMenu);
            }
        };

        int max = list.size();
        if(max > 0) {
            for(int i = 0; i < max; i++) {
                Button btn = new Button(new Command(list.elementAt(i).toString()));
                btn.addActionListener(BotonAL);
                co.addComponent(btn);
            }
        } else {
            co.addComponent(new Label(Language.NoFriends + ((menu) ? Language.OnlineF : Language.OfflineF)));
        } return co;
    }
}
