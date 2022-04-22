/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc10LeaveServer
implements IrcPacket {
    public String reason;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.reason = var1_1._ircIllIllIIlI();
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.reason);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
    }
}

