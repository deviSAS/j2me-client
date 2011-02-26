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

import com.devi.os.Events;
import com.devi.os.MainUI;

import com.sun.lwuit.Form;
import com.sun.lwuit.TextField;
import com.sun.lwuit.ComboBox;

public class Settings {
    public static int midletWidth = 0;
    public static int midletHeight = 0;
    public static boolean LoggedIn = false;
    public static Events events = new Events();
    public static MainUI ui;
    public static String loadingText;
    public static Network net = new Network();
    public static int EventTime = 10000;
    public static List loginDetails = new List();
    public static Debug d = new Debug();
    public static List temp = new List();
    public static List UIData = new List();
    public static Loading loading = new Loading();
    public static TextField[] fields = new TextField[10];
    public static Form CurrentForm = new Form();
    public static Dialogs dialog = new Dialogs();
    public static URLEncode encode = new URLEncode();
    public static boolean HaveFriends = false;
    public static String[] Grids = {"deviOS", "OSGrid", "ReactionGrid"};
    public static ComboBox GridCombo = null; // new ComboBox(Grids);
}
