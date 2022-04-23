// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.irc.ircserver;

import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;

public class IRCServer extends Thread
{
    Socket socket;
    Scanner in;
    PrintWriter out;
    Scanner inp;
    boolean loggedIN;
    String username;
    IRCSender sender;
    
    public IRCServer(final Socket socket, final Scanner in, final PrintWriter out, final Scanner inp, final String username) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.inp = inp;
        this.loggedIN = false;
        this.username = username;
    }
    
    public IRCSender getSender() {
        return this.sender;
    }
}
