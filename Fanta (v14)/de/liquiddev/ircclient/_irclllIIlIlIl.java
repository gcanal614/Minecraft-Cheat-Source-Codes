/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient;

final class _irclllIIlIlIl {
    private int _ircIllIllIIlI;

    _irclllIIlIlIl() {
    }

    public final String toString() {
        byte[] byArray = new byte[4];
        this._ircIllIllIIlI = 464665194;
        byArray[0] = (byte)(this._ircIllIllIIlI >>> 7);
        this._ircIllIllIIlI = -1671797444;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 8);
        this._ircIllIllIIlI = 454791629;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 2);
        this._ircIllIllIIlI = 487221729;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 22);
        return new String(byArray);
    }
}

