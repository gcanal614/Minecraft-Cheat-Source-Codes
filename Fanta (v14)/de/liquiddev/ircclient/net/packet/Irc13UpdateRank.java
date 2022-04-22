/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc13UpdateRank
implements IrcPacket {
    public IrcRank rank;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.rank = IrcRank.getById(var1_1._ircIllIllIIlI());
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.rank.getId());
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        ((SkidIrc)connection._ircIllIllIIlI()).updateRank(this.rank);
    }
}

