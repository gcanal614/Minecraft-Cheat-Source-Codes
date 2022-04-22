/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc08Whisper
implements IrcPacket {
    public String player;
    public String message;
    public boolean isFromMe;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.player = connection._ircIllIllIIlI();
        this.message = connection._ircIllIllIIlI();
        this.isFromMe = var1_1._irclllllIIlII();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void write(_ircIllIllIIlI connection) {
        void var1_1;
        connection._irclllllIIlII(this.player);
        var1_1._irclllllIIlII(this.message);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        connection._ircIllIllIIlI().getApiManager().onWhisperMessage(this.player, this.message, this.isFromMe);
    }
}

