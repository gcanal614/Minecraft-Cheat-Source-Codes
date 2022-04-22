/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net.packet._ircIllIIIIllI;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Irc14Extension
implements IrcPacket {
    public static final int MIN_PROTOCOL_VERSION = 2;
    private _ircIllIIIIllI _ircIllIllIIlI;
    private String _ircIllIllIIlI;
    private byte[] _ircIllIllIIlI;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(_ircIllIllIIlI connection) {
        void var1_1;
        this._ircIllIllIIlI = _ircIllIIIIllI.values()[connection._ircIllIllIIlI()];
        this._ircIllIllIIlI = connection._ircIllIllIIlI();
        this._ircIllIllIIlI = var1_1._ircIllIllIIlI();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void write(_ircIllIllIIlI connection) {
        void var1_1;
        connection._ircIllIllIIlI(this._ircIllIllIIlI.ordinal());
        connection._irclllllIIlII(this._ircIllIllIIlI);
        var1_1._ircIllIllIIlI(this._ircIllIllIIlI);
    }

    @Override
    public void handle(_ircIllIllIIlI connection) {
    }
}

