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

package com.devi.os;

import com.devi.os.util.Language;
import com.devi.os.util.Settings;
import com.devi.os.util.LogOut;
import com.sun.lwuit.Display;

import com.devi.os.ui.*;

import com.sun.lwuit.Dialog;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.util.Resources;

/* Ventana principal */
public class MainUI implements ActionListener {

    private deviOS midlet;
    private Resources resources;
    private DataSend send = new DataSend();
    public  String Avatar = ""; /* Pasar a TEMP */
    private LoginDetails loginDetails = new LoginDetails();

    public MainUI(deviOS midlet){
        super();
        this.midlet = midlet;
        Settings.UIData.add("screen", "");
        Settings.UIData.add("lastscr", "");
        Settings.UIData.add("Reverse", "");
        Settings.UIData.add("pantalla", "");
    }

    public void showScreen(String s) {
        if(s.equals("Init")) {
            Init.show();
            return;
        }
        if(s.equals("Register")) {
            Register.show();
            return;
        }
        if(s.equals("UserArea")) {
            UserArea.show();
            return;
        }
        if(s.equals("Chat")) {
            Chat.show();
            return;
        }
        if(s.equals("LocalChat")) {
            LocalChat.show();
            return;
        }
    }

    public void actionPerformed(ActionEvent event) {
        String evento = event.getCommand().getCommandName();
        if(evento.length() == 0) return;
        //this.showDialog("actionPerformed", evento, null);
        Settings.d.debug("[MAIN] Evento: " + evento);

        if(evento.equals(Language.Buttons[0])) { /* Entrar */
            if(Settings.loading.showLoading(Language.Loading[0])) {
                UserLogin uLogin = new UserLogin();
                uLogin.login(Settings.fields[0].getText(), Settings.fields[1].getText(), Settings.fields[2].getText());
            } return;
        }
        if(evento.equals(Language.Buttons[1])) { /* Registrar */
            /* Init: Registrar usuario */
            if(Settings.UIData.get("pantalla").equals("Init")) {
                this.showScreen("Register");
            } return;
        }
        if(evento.equals(Language.Buttons[4])) { /* Crear Cuenta */
            String t = Settings.fields[0].getTextAt(0);
            Settings.dialog.showDialog("Crear", t, null);
            return;
        }
        if(evento.equals(Language.Buttons[7])) { /* Enviar */
            if(Settings.fields[0].getText().length() == 0) return;
            if(Settings.UIData.get("pantalla").equals("LocalChat")) {
                Settings.loading.showLoading(Language.Loading[3]);
                send.sendChat(Settings.fields[0].getText());
            }
            if(Settings.UIData.get("pantalla").equals("IMChat")) {
                Settings.loading.showLoading(Language.Loading[3]);
                send.sendIM(Settings.temp.get("IMname").toString(), Settings.temp.get("IMsid").toString(), Settings.fields[0].getText());
            } return;
        }

        if(evento.equals(Language.Nombres[0])) { /* Chat */
            this.showScreen("Chat");
            return;
        }
        if(evento.equals(Language.Nombres[1])) { /* Amigos */
            if(Settings.HaveFriends) Friends.get();
            else {
                Settings.loading.showLoading(Language.Loading[4]);
                Friends.scheduleGet();
            } return;
        }
            if(Settings.UIData.get("pantalla").equals("Chat")) {
                if(evento.equals(Language.Titles[5])) { /* Chat local */
                    this.showScreen("LocalChat");
                    return;
                }
                if(IMSessions.sesiones.has(evento)) { /* Abrir IM */
                    if(Settings.temp.has("IMname")) Settings.temp.set("IMname", evento);
                    else Settings.temp.add("IMname", evento);
                    IMChat.show(IMSessions.sesiones.get(evento).toString());
                    return;
                }
                if(evento.equals(Language.Titles[7])) { /* Avatares cercanos */
                    if(Settings.loading.showLoading(Language.Loading[2])) {
                        AvatarsNear av = new AvatarsNear();
                        av.refresh();
                    }
                }
            }

        /*******************************************
         *************      SUBMENU       **********
         *******************************************/

        if(evento.equals(Language.ItemsMenu[0])) { /* Refrescar */
           reloadScreen();
           return;
        }
        
        if(evento.equals(Language.AvatarNearMenu[0])) { /* Abrir IM */
            if(Settings.temp.has("IMname")) Settings.temp.set("IMname", Avatar);
            else Settings.temp.add("IMname", Avatar);
            IMChat.show("new");
        }
        //if(evento.equals(Language.ItemsMenu[2])) { /* Cerrar */
        //    if(this.pantalla.equals("LocalChat")) this.showScreen("Chat");
        //    else this.showScreen("UserArea");
        //    return;
        //}
        //if(evento.equals(Language.ItemsMenu[3])) { /* Menú Principal */
        //    this.showScreen("UserArea");
        //    return;
        //}

        /*****  ***** ***** ******        ***** ***** ****   *****/

        if(evento.equals(Language.Buttons[3])) { /* Regresar */
            if(Settings.UIData.get("pantalla").equals("Chat") ||
               Settings.UIData.get("pantalla").equals("Friends")) {
                Settings.CurrentForm.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 500));
                this.showScreen("UserArea");
            }
            else if(Settings.UIData.get("pantalla").equals("IMChat") ||
                    Settings.UIData.get("pantalla").equals("LocalChat") ||
                    Settings.UIData.get("pantalla").equals("AvatarArea")) this.showScreen("Chat");
            else this.showScreen(Settings.UIData.get("lastsrc").toString());
            return;
        }
        if(evento.equals(Language.Buttons[2])) { /* Salir */
            if(Settings.LoggedIn) {
                if(Settings.loading.showLoading(Language.Loading[1])) {
                    LogOut logOut = new LogOut();
                    logOut.exit();
                }
            } else {
                this.midlet.destroyApp(false);
            } return;
        } if(evento.equals(Language.Buttons[6])) {
            if(Settings.UIData.get("pantalla").equals("LocalChat") ||
               Settings.UIData.get("pantalla").equals("IMChat")) {
                subMenu(Language.ItemsMenu);
            }// else {}
            return;
        }
    }

    public void reloadScreen() {
        if(Settings.loading.visible()) Settings.loading.loadingDispose();

        Settings.CurrentForm.setTransitionOutAnimator(null);
        Settings.CurrentForm.setTransitionInAnimator(null);
        //this.currentForm.removeAll();

        if(Settings.UIData.get("pantalla").equals("IMChat")) {
            IMChat.show(Settings.temp.get("IMsid").toString());
            return;
        }

        this.showScreen(Settings.UIData.get("pantalla").toString());
        return;
    }

    public void subMenu(String[] buttons) {

        Dialog submenu = new Dialog();/* {
            protected void sizeChanged(int height, int width)  {
                Settings.d.debug("[SIZE] " + width + ", " + height);
            }
        };*/

        submenu.setLayout(new GridLayout(2, 3));

        for(int i = 0; i < buttons.length; i++) {
            Button btn = new Button(new Command(buttons[i]));
            btn.addActionListener(this);
            btn.setAlignment(Button.CENTER);
            submenu.addComponent(btn);
        }

        submenu.setTransitionInAnimator(CommonTransitions.createFade(500));
        submenu.addCommand(new Command(""));
        submenu.addCommand(new Command(Language.Buttons[5])); /* Atras */

        int width = Display.getInstance().getDisplayWidth();
        submenu.show((width - ((buttons.length / 3) * 38)) - 10, 0, 0, 0, false, false);
        //Settings.CurrentForm = submenu;
    }

    /* Respuesta desde el UserLogin.java */
    public void LoginStatus(boolean status, String Errors) {
        if(Settings.loading.visible()) Settings.loading.loadingDispose();
        if(status) {
            Settings.LoggedIn = true;

            /* Iniciar clases necesarias */
            Friends friends = new Friends();
            friends.init(); // Pedir lista de amigos
            loginDetails.getLoginDetails();
            Settings.events.Start();

            /* Mostrar pantalla de inicio */
            this.showScreen("UserArea");
            return;
        } else {
            Settings.dialog.showDialog(Language.Titles[2], Errors, null);
        }
    }

    public void LoggedOut(boolean status) {
        Settings.loading.loadingDispose();
        if(status) this.midlet.destroyApp(false);
        else {
            Settings.dialog.showDialog(Language.Titles[2], Language.Labels[3], null);
        }
    }

}
