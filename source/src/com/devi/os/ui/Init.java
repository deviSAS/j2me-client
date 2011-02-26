package com.devi.os.ui;

import com.devi.os.util.Settings;
import com.devi.os.util.Language;

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
import com.sun.lwuit.ComboBox;

public class Init {
    public static void show() {
        Settings.UIData.set("screen", "Init");
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "0");

                Form temp = new Form();
                temp.setLayout(new BorderLayout());
                
                temp.addCommandListener(Settings.ui);
                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                temp.setTitle(Language.Titles[0]);

                for(int i = 0; i < 3; i++) {
                    Settings.fields[i] =  new TextField();
                    Label label = new Label(Language.Fields[i]);
                    co.addComponent(label);
                    if(i == 2) Settings.fields[i].setConstraint(TextField.PASSWORD);
                    co.addComponent(Settings.fields[i]);
                }

                Settings.GridCombo = new ComboBox(Settings.Grids);
                co.addComponent(Settings.GridCombo);

                co.addComponent(new Button(new Command(Language.Buttons[0])));
                co.addComponent(new Button(new Command(Language.Buttons[1])));

                temp.addComponent(BorderLayout.CENTER, co);
                temp.addCommand(new Command(""));
                temp.addCommand(new Command(Language.Buttons[2]));

                temp.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                temp.show();

                Settings.CurrentForm = temp;
            }
        });
    }
}
