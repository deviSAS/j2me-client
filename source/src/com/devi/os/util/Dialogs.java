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
