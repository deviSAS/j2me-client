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
