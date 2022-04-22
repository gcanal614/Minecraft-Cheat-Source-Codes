/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.api.IrcApiManager;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc17CustomData
implements IrcPacket {
    public String target;
    public String tag;
    public byte[] data;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.target = connection._ircIllIllIIlI();
        this.tag = connection._ircIllIllIIlI();
        this.data = var1_1._ircIllIllIIlI();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void write(_ircIllIllIIlI connection) {
        void var1_1;
        connection._irclllllIIlII(this.target);
        connection._irclllllIIlII(this.tag);
        var1_1._ircIllIllIIlI(this.data);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        IrcApiManager ircApiManager = connection._ircIllIllIIlI().getApiManager();
        ircApiManager.onCustomData(this.target, this.tag, this.data);
    }
}

