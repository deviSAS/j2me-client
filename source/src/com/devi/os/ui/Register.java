package com.devi.os.ui;

import com.devi.os.util.Settings;
import com.devi.os.util.Language;

import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.Display;
import com.sun.lwuit.TextField;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.Command;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;

public class Register {
    public static void show() {
        Settings.UIData.set("screen", "Register");
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "1");

                Form temp = new Form();
                temp.addCommandListener(Settings.ui);
                temp.setLayout(new BorderLayout());
                
                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                temp.setTitle(Language.Titles[1]);

                TextArea Texto = new TextArea(Language.Labels[0]);
                Texto.setEditable(false);

                co.addComponent(Texto);
                for(int i = 0; i < 6; i++) {
                    if(i != 2)  {
                        Settings.fields[i] =  new TextField();
                        Label _label = new Label(Language.Fields[i]);
                        co.addComponent(_label);
                        co.addComponent(Settings.fields[i]);
                    }
                }

                Label label = new Label(Language.Fields[2]);

                co.addComponent(label);
                co.addComponent(new Button(new Command(Language.Buttons[4])));

                temp.addComponent(BorderLayout.CENTER, co);

                temp.addCommand(new Command(Language.Buttons[3]));
                temp.addCommand(new Command(Language.Buttons[2]));

                temp.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                temp.show();

                Settings.CurrentForm = temp;
            }
        });
    }
}
