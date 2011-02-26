/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
