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

import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BorderLayout;


public class Loading{
    private Dialog loading;
    private Label loadLabel = new Label();

    public boolean showLoading(String message) {
        loading = new Dialog() {
            protected void sizeChanged(int w, int h) {
                int middleW = (w/2) - 90;
                int middleH = (h/2) - 30;
                loading.show(middleH, middleH, middleW, middleW, false, false);
            }
        };
        
        Label newLabel = new Label(message);
        newLabel.setAlignment(Label.CENTER);
        
        if(loading.isVisible()) {
            loading.replace(loadLabel, newLabel, null);
            loading.repaint();
        } else {
            loading.setLayout(new BorderLayout());
            loading.addComponent(BorderLayout.CENTER, newLabel);

            int width = Display.getInstance().getDisplayWidth();
            int height = Display.getInstance().getDisplayHeight();
            int middleW = (width/2) - 90;
            int middleH = (height/2) - 30;

            loading.show(middleH, middleH, middleW, middleW, false, false);
        }
        loadLabel = newLabel;
        return true;
    }

    public boolean visible() {
        return loading.isVisible();
    }

    public void loadingDispose() {
        loading.dispose();
        loading.removeAll();
    }
}
