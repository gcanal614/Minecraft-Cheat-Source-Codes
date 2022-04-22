/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc03Chat
implements IrcPacket {
    public String clientType;
    public String player;
    public String message;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.clientType = connection._ircIllIllIIlI();
        this.player = connection._ircIllIllIIlI();
        this.message = var1_1._ircIllIllIIlI();
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.message);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        connection._ircIllIllIIlI().getApiManager().onPlayerChatMessage(this.player, this.clientType, this.message);
    }
}

