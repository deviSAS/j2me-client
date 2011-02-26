package com.devi.os.ui;

import com.devi.os.util.Settings;
import com.devi.os.util.Language;

import com.sun.lwuit.Button;
import com.sun.lwuit.Label;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Command;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GridLayout;

public class UserArea {
    private static String[] Imagenes = new String[] { "/ico/w/chat.png", "/ico/w/amigos.png", "/ico/w/grupos.png", "/ico/w/inventario.png", "/ico/w/mapa.png" };

    public static void show() {
        Settings.UIData.set("screen", "UserArea");
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                Settings.UIData.set("lastscr", Settings.UIData.get("pantalla"));
                Settings.UIData.set("pantalla", Settings.UIData.get("screen"));
                Settings.UIData.set("Reverse", "0");

                Form temp = new Form();
                temp.setLayout(new BorderLayout());
                temp.addCommandListener(Settings.ui);
                
                Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Container menu = new Container(new GridLayout(2, 2));

                for(int i = 0; i < Language.Nombres.length; i++) {
                    Button btn = new Button(new Command(Language.Nombres[i]));
                    Image img = null;
                    try {
                        img = Image.createImage(Imagenes[i]);
                    } catch(Exception e) { }

                    btn.setIcon(img);
                    btn.setUIID("MenuButton");
                    btn.setAlignment(Label.CENTER);
                    btn.setTextPosition(Label.BOTTOM);
                    menu.addComponent(btn);
                }

                menu.setScrollable(true);
                co.addComponent(menu);

                temp.addComponent(BorderLayout.CENTER, co);
                temp.addCommand(new Command(""));
                temp.addCommand(new Command(Language.Buttons[2]));
                temp.setScrollable(false);

                temp.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, Settings.UIData.get("Reverse").equals("1"), 500));
                temp.show();

                Settings.CurrentForm = temp;
            }
        });
    }
}