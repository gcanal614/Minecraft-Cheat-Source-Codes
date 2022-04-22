/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

final class _ircIlIIlIllIl {
    private int _ircIllIllIIlI;

    _ircIlIIlIllIl() {
    }

    public final String toString() {
        byte[] byArray = new byte[4];
        this._ircIllIllIIlI = 1188920220;
        byArray[0] = this._ircIllIllIIlI >> 24;
        this._ircIllIllIIlI = -154659135;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 90569150;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 790080963;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 21);
        return new String(byArray);
    }
}

