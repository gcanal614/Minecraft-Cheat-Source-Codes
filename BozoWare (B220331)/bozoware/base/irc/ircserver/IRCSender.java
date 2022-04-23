// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.irc.ircserver;

import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;

public class IRCSender extends Thread
{
    Socket socket;
    Scanner in;
    PrintWriter out;
    Scanner inp;
    
    public IRCSender(final Socket socket, final Scanner in, final PrintWriter out, final Scanner inp) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.inp = inp;
    }
    
    @Override
    public void run() {
        while (true) {}
    }
    
    public void addMessage(final String message) {
        this.out.println(message);
    }
}
