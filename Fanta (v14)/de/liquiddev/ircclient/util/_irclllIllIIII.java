/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.util;

final class _irclllIllIIII {
    private int _ircIllIllIIlI;

    _irclllIllIIII() {
    }

    public final String toString() {
        byte[] byArray = new byte[3];
        this._ircIllIllIIlI = 734168789;
        byArray[0] = (byte)(this._ircIllIllIIlI >>> 23);
        this._ircIllIllIIlI = -721268881;
        byArray[1] = (byte)(this._ircIllIllIIlI >>> 11);
        this._ircIllIllIIlI = -746009228;
        byArray[2] = (byte)(this._ircIllIllIIlI >>> 22);
        return new String(byArray);
    }
}

