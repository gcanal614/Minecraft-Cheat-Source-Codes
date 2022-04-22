/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.api.IrcApiManager;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc02Info
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
        IrcApiManager ircApiManager = connection._ircIllIllIIlI().getApiManager();
        ircApiManager.onChatMessage(this.message);
    }
}

