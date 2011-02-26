
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
