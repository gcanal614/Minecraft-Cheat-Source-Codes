/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.packet.Irc04AddPlayer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class _ircIIlIIIlIlI {
    private int _ircIllIllIIlI;
    private /* synthetic */ Irc04AddPlayer _ircIllIllIIlI;

    _ircIIlIIIlIlI(Irc04AddPlayer irc04AddPlayer) {
    }

    public final String toString() {
        byte[] byArray = new byte[4];
        this._ircIllIllIIlI = 477009254;
        byArray[0] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 138075999;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 15);
        this._ircIllIllIIlI = -1254734140;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = -441202163;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 18);
        return new String(byArray);
    }
}

