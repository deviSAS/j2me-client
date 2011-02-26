/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.devi.os.util;

import java.util.Timer;
import java.util.TimerTask;

public class MemoryTest {
    private Timer check = new Timer();
    private TimerTask timerTask;
    private long last = 0;
    private long total;
    
    public MemoryTest() {
        total = Runtime.getRuntime().totalMemory() / 97656;
        timerTask = new TimerTask()  {
            public void run() {
                long curMem = Runtime.getRuntime().freeMemory();
                long inUse  = ( Runtime.getRuntime().totalMemory() - curMem ) / 97656;
                Settings.d.debug("[MEMORY] In use: " + Math.floor(inUse) + "/" + Math.floor(total) + " " + ((inUse > last) ? "^" : "v"));
                last = inUse;
            }
        };
        check.schedule(timerTask,0,  20000);
    }
}
