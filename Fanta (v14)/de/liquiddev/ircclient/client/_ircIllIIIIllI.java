/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

final class _ircIllIIIIllI {
    private int _ircIllIllIIlI;

    _ircIllIIIIllI() {
    }

    public final String toString() {
        byte[] byArray = new byte[4];
        this._ircIllIllIIlI = 1272806300;
        byArray[0] = this._ircIllIllIIlI >> 24;
        this._ircIllIllIIlI = -154659087;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 4);
        this._ircIllIllIIlI = 90962366;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 16);
        this._ircIllIllIIlI = 779595203;
        byArray[3] = (byte)(this._ircIllIllIIlI >>> 21);
        return new String(byArray);
    }
}

