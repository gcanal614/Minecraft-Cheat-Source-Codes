/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

final class _irclIIIIlIIII {
    private int _ircIllIllIIlI;

    _irclIIIIlIIII() {
    }

    public final String toString() {
        byte[] byArray = new byte[4];
        this._ircIllIllIIlI = 1407024028;
        byArray[0] = this._ircIllIllIIlI >> 24;
        this._ircIllIllIIlI = -154659151;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 90831294;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 748137923;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 21);
        return new String(byArray);
    }
}

