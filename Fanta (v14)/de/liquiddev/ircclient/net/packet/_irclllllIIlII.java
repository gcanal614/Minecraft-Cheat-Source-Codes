/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.packet.Irc00Login;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class _irclllllIIlII {
    private int _ircIllIllIIlI;
    private /* synthetic */ Irc00Login _ircIllIllIIlI;

    _irclllllIIlII(Irc00Login irc00Login) {
    }

    public final String toString() {
        byte[] byArray = new byte[1];
        this._ircIllIllIIlI = 474243980;
        byArray[0] = (byte)(this._ircIllIllIIlI >>> 8);
        return new String(byArray);
    }
}

