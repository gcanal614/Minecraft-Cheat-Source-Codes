/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.util;

final class _irclllllIIlII {
    private int _ircIllIllIIlI;

    _irclllllIIlII() {
    }

    public final String toString() {
        byte[] byArray = new byte[7];
        this._ircIllIllIIlI = 475241983;
        byArray[0] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 1672499721;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 6);
        this._ircIllIllIIlI = 1158699654;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 14);
        this._ircIllIllIIlI = 695194491;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 219255195;
        byArray[4] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = 901805322;
        byArray[5] = this._ircIllIllIIlI >> 24;
        this._ircIllIllIIlI = -1263197771;
        byArray[6] = (byte)(this._ircIllIllIIlI >>> 3);
        return new String(byArray);
    }
}

