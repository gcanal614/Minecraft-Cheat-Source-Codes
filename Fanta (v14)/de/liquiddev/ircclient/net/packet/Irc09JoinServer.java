/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc09JoinServer
implements IrcPacket {
    public String server;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.server = var1_1._ircIllIllIIlI();
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.server);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
    }
}

