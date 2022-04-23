// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.irc.ircclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;

public class IRCClient
{
    private String username;
    private IRCReceiver receiver;
    private boolean connected;
    
    public IRCClient(final String username, final String host, final int port) {
        this.username = username;
        try {
            final Socket socket = new Socket(host, port);
            final Scanner in = new Scanner(socket.getInputStream());
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            final Scanner inp = new Scanner(System.in);
            (this.receiver = new IRCReceiver(socket, in, out, inp, username)).start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public IRCReceiver getReceiver() {
        return this.receiver;
    }
    
    public boolean isConnected() {
        return this.connected;
    }
    
    public void setConnected(final boolean connected) {
        this.connected = connected;
    }
}
