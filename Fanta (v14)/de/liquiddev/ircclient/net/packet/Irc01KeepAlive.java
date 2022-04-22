/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc01KeepAlive
implements IrcPacket {
    @Override
    public void read(_ircIllIllIIlI connection) {
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this);
    }
}

