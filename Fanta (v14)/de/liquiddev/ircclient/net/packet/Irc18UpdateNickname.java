/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc18UpdateNickname
implements IrcPacket {
    public String nickname;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.nickname = var1_1._ircIllIllIIlI();
    }

    @Override
    public void write(_ircIllIllIIlI connection) {
        connection._irclllllIIlII(this.nickname);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
        SkidIrc skidIrc = (SkidIrc)connection._ircIllIllIIlI();
        skidIrc.updateNickname(this.nickname);
    }
}

