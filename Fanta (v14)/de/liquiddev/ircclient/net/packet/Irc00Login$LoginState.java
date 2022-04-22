/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.net.packet._irclIIlIlllll;
import de.liquiddev.ircclient.net.packet._irclllIIlIlIl;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public enum Irc00Login$LoginState {
    LOGIN(0),
    SUCCESS(1),
    FAIL(2);

    private int _ircIllIllIIlI;

    static Irc00Login$LoginState _ircIllIllIIlI(int n) {
        Irc00Login$LoginState[] irc00Login$LoginStateArray = Irc00Login$LoginState.values();
        int n2 = irc00Login$LoginStateArray.length;
        int n3 = 0;
        while (n3 < n2) {
            Irc00Login$LoginState irc00Login$LoginState = irc00Login$LoginStateArray[n3];
            if (irc00Login$LoginState._ircIllIllIIlI == n) {
                return irc00Login$LoginState;
            }
            ++n3;
        }
        throw new NoSuchElementException(String.valueOf(new _irclIIlIlllll().toString()) + n + new _irclllIIlIlIl().toString());
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private Irc00Login$LoginState() {
        void var3_1;
        this._ircIllIllIIlI = var3_1;
    }

    public final int getId() {
        return this._ircIllIllIIlI;
    }
}

