/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

import de.liquiddev.ircclient.client._ircIIIlIllllI;
import de.liquiddev.ircclient.client._ircIIlIllIlIl;
import de.liquiddev.ircclient.client._ircIIllllIlIl;
import de.liquiddev.ircclient.client._ircIIlllllllI;
import de.liquiddev.ircclient.client._ircIlIIllIIll;
import de.liquiddev.ircclient.client._ircIllIIllIII;
import de.liquiddev.ircclient.client._irclIIIIIIlII;
import de.liquiddev.ircclient.client._ircllIlIlIlIl;
import de.liquiddev.ircclient.client._ircllllIllIIl;
import de.liquiddev.ircclient.client._irclllllIIlIl;
import de.liquiddev.ircclient.client._irclllllllllI;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public enum IrcRank {
    USER(0, 0, new _ircllIlIlIlIl().toString()),
    VIP(1, 10, String.valueOf(new _ircIIlIllIlIl().toString()) + "\u00a7" + new _irclllllllllI().toString()),
    MOD(2, 20, String.valueOf(new _ircIIIlIllllI().toString()) + "\u00a7" + new _ircIllIIllIII().toString()),
    ADMIN(3, 30, String.valueOf(new _irclIIIIIIlII().toString()) + "\u00a7" + new _ircIIllllIlIl().toString()),
    QUERY(4, 40, String.valueOf(new _irclllllIIlIl().toString()) + "\u00a7" + new _ircIlIIllIIll().toString()),
    DEV(5, 20, String.valueOf(new _ircllllIllIIl().toString()) + "\u00a7" + new _ircIIlllllllI().toString());

    private int _ircIllIllIIlI;
    private int _irclllllIIlII;
    private String _ircIllIllIIlI;

    public static IrcRank getById(int id) {
        IrcRank[] ircRankArray = IrcRank.values();
        int n = ircRankArray.length;
        int n2 = 0;
        while (n2 < n) {
            IrcRank ircRank = ircRankArray[n2];
            if (ircRank.getId() == id) {
                return ircRank;
            }
            ++n2;
        }
        return USER;
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private IrcRank(String id) {
        void permissionsLevel;
        void chatColor;
        void var3_1;
        this._ircIllIllIIlI = var3_1;
        this._ircIllIllIIlI = chatColor;
        this._irclllllIIlII = permissionsLevel;
    }

    public final int getId() {
        return this._ircIllIllIIlI;
    }

    public final int getPermissionLevel() {
        return this._irclllllIIlII;
    }

    public final String getChatColor() {
        return this._ircIllIllIIlI;
    }
}

