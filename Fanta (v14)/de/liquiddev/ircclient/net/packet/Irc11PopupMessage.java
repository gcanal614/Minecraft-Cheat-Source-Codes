/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.packet._ircIIIlIlllII;
import java.awt.TrayIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Irc11PopupMessage
implements IrcPacket {
    public String message;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.message = var1_1._ircIllIllIIlI();
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.message);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        JFrame jFrame = new JFrame();
        jFrame.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jFrame, this.message, new _ircIIIlIlllII(this).toString(), TrayIcon.MessageType.ERROR.ordinal());
    }
}

