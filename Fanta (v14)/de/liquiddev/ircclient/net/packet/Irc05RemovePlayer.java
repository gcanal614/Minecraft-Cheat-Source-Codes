/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc05RemovePlayer
implements IrcPacket {
    public byte[] hash;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.hash = var1_1._ircIllIllIIlI();
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.hash);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        IrcPlayer ircPlayer = IrcPlayer.getByHash(this.hash);
        if (ircPlayer != null) {
            ircPlayer.unregister();
        }
    }
}

