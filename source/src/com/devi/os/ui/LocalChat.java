package com.devi.os.ui;

import com.devi.os.util.Settings;
import com.devi.os.util.Language;
import com.devi.os.ChatLocal;

import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.Display;
import com.sun.lwuit.TextField;
import com.sun.lwuit.Command;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;

public class LocalChat {
    public static void show() {
        Settings.UIData.set("screen", "LocalChat");
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "1");

                Form temp = new Form();
                temp.addCommandListener(Settings.ui);
                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                temp.setTitle(Language.Titles[5]);
                temp.setLayout(new BorderLayout());

                // Procesar los mensajes
                Container chatBar = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                int total = ChatLocal.ChatMessages.size();
                if(total > 0) {
                    // Mostrarlos en reverso
                    for(int i = (total-1); i >= 0; i--) {
                        Label text = new Label(ChatLocal.ChatMessages.elementAt(i).toString());
                        if(ChatLocal.ChatType.elementAt(i).equals("2")) text.getStyle().setFgColor(10864810);
                        chatBar.addComponent(text);
                    }
                } else {
                    chatBar.addComponent(new Label(Language.Labels[2]));
                } chatBar.setScrollable(true);

                Settings.fields[0] = new TextField();
                co.addComponent(Settings.fields[0]);
                co.addComponent(new Button(new Command(Language.Buttons[7])));

                Label posLabel = new Label(Language.LocationAt + Settings.loginDetails.get("Region").toString() + ", " + Settings.loginDetails.get("Position").toString());

                temp.addComponent(BorderLayout.NORTH, posLabel);
                temp.addComponent(BorderLayout.CENTER, chatBar);
                temp.addComponent(BorderLayout.SOUTH, co);

                temp.addCommand(new Command(Language.Buttons[6]));
                temp.addCommand(new Command(Language.Buttons[3]));

                temp.setScrollable(false);

                temp.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                temp.show();

                Settings.CurrentForm = temp;
            }
        });
    }
}


