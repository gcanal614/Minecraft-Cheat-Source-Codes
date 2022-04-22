/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class _ircIllIIIIllI
extends Enum {
    private static /* enum */ _ircIllIIIIllI _ircIllIllIIlI = new _ircIllIIIIllI("LOAD", 0);
    private static /* enum */ _ircIllIIIIllI _irclllllIIlII = new _ircIllIIIIllI("ENABLE", 1);
    private static /* enum */ _ircIllIIIIllI _ircIllIlIlIII = new _ircIllIIIIllI("DISABLE", 2);
    private static final /* synthetic */ _ircIllIIIIllI[] _ircIllIllIIlI;

    static {
        _ircIllIllIIlI = new _ircIllIIIIllI[]{_ircIllIllIIlI, _irclllllIIlII, _ircIllIlIlIII};
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private _ircIllIIIIllI() {
        void var2_-1;
        void var1_-1;
    }

    public static _ircIllIIIIllI[] values() {
        _ircIllIIIIllI[] _ircIllIIIIllIArray = new _ircIllIIIIllI[3];
        System.arraycopy(_ircIllIllIIlI, 0, _ircIllIIIIllIArray, 0, 3);
        return _ircIllIIIIllIArray;
    }

    public static _ircIllIIIIllI valueOf(String string) {
        return Enum.valueOf(_ircIllIIIIllI.class, string);
    }
}

