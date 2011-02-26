/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devi.os.util;

import com.sun.lwuit.Command;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.Dialog;

import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.ActionEvent;

public class Dialogs implements ActionListener {
    private static Dialog current = null;
    public void showDialog(String titulo, String text, Command[] cmds) {
        TextArea message = new TextArea(text, 2, 50);
                 message.setEditable(false);
        
        if(cmds == null) cmds = new Command[] { new Command(Language.DialogDefault) };

        Dialog dg = new Dialog();
               dg.setTitle(titulo);
               dg.addComponent(message);
               dg.addCommandListener(this);

        for(int i = 0; i < cmds.length; i++) {
            dg.addCommand(cmds[i]);
        }
        dg.show();
        //dg.showModeless();
        current = dg;
    }

     public void actionPerformed(ActionEvent event) {
        String evento = event.getCommand().getCommandName();
        if(evento.length() == 0) return;

        Settings.d.debug("[DIALOG] Evento: " + evento);
        if(evento.equals(Language.DialogDefault)) {
            current.dispose();
        }
    }
}
