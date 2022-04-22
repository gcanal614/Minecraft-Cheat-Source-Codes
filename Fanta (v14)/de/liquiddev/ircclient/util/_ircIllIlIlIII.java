/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.util;

final class _ircIllIlIlIII {
    private int _ircIllIllIIlI;

    _ircIllIlIlIII() {
    }

    public final String toString() {
        byte[] byArray = new byte[5];
        this._ircIllIllIIlI = 475373055;
        byArray[0] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 1672500489;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 6);
        this._ircIllIllIIlI = 1158781574;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 14);
        this._ircIllIllIIlI = 695194491;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 19);
        this._ircIllIllIIlI = 219267483;
        byArray[4] = (byte)(this._ircIllIllIIlI >>> 11);
        return new String(byArray);
    }
}

