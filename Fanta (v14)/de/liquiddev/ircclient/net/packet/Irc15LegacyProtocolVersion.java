/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;

public class Irc15LegacyProtocolVersion
implements IrcPacket {
    public int protocolVersion;
    public String clientVersion;
    public String ircVersion;
    public boolean supportsExtensions;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this.protocolVersion = connection._ircIllIllIIlI();
        this.ircVersion = connection._ircIllIllIIlI();
        this.supportsExtensions = connection._irclllllIIlII();
        this.clientVersion = var1_1._ircIllIllIIlI();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void write(_ircIllIllIIlI connection) {
        void var1_1;
        connection._ircIllIllIIlI(this.protocolVersion);
        connection._irclllllIIlII(this.ircVersion);
        connection._ircIllIllIIlI(this.supportsExtensions);
        var1_1._irclllllIIlII(this.clientVersion);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
    }
}

