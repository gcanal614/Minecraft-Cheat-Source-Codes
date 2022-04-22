/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

import de.liquiddev.ircclient.client._ircIIIIIIIlll;
import de.liquiddev.ircclient.client._ircIIIIIlIIll;
import de.liquiddev.ircclient.client._ircIIIIlIlIlI;
import de.liquiddev.ircclient.client._ircIIIIllIlll;
import de.liquiddev.ircclient.client._ircIIIlIIIIll;
import de.liquiddev.ircclient.client._ircIIIlIlllII;
import de.liquiddev.ircclient.client._ircIIlIIIlIlI;
import de.liquiddev.ircclient.client._ircIlIIIIlIlI;
import de.liquiddev.ircclient.client._ircIlIIlIllIl;
import de.liquiddev.ircclient.client._ircIlIlllIllI;
import de.liquiddev.ircclient.client._ircIllIIIIllI;
import de.liquiddev.ircclient.client._ircIllIIIlIlI;
import de.liquiddev.ircclient.client._ircIllIIlIlll;
import de.liquiddev.ircclient.client._ircIllIlIIIII;
import de.liquiddev.ircclient.client._ircIllIlIlIII;
import de.liquiddev.ircclient.client._ircIllIllIIlI;
import de.liquiddev.ircclient.client._ircIllIllIlII;
import de.liquiddev.ircclient.client._irclIIIIlIIII;
import de.liquiddev.ircclient.client._irclIIIlIlllI;
import de.liquiddev.ircclient.client._irclIIlIIIIIl;
import de.liquiddev.ircclient.client._irclIIlIllIII;
import de.liquiddev.ircclient.client._irclIIlIlllll;
import de.liquiddev.ircclient.client._irclllIIlIIlI;
import de.liquiddev.ircclient.client._irclllIIlIlIl;
import de.liquiddev.ircclient.client._irclllIllIIII;
import de.liquiddev.ircclient.client._irclllllIIlII;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public enum ClientType {
    QUERY(new _ircIllIllIIlI().toString(), -1),
    UNKNOWN(new _ircIIIIlIlIlI().toString(), 0),
    SKID(new _irclIIIIlIIII().toString(), 1),
    SLOWLY(new _irclIIlIIIIIl().toString(), 2),
    FLEX(new _ircIlIIlIllIl().toString(), 3),
    COKEZ(new _irclllIIlIIlI().toString(), 4),
    VIOLENCE(new _ircIlIlllIllI().toString(), 5),
    SOMNIA(new _ircIllIllIlII().toString(), 6),
    FANTA(new _ircIIIIIIIlll().toString(), 7),
    JUSTY(new _irclllllIIlII().toString(), 8),
    FATALITY(new _ircIllIlIlIII().toString(), 9),
    ANDROID(new _irclIIlIlllll().toString(), 10),
    FABRIC(new _irclllIIlIlIl().toString(), 11),
    CANDY(new _ircIIlIIIlIlI().toString(), 12),
    TIERRA(new _ircIIIlIlllII().toString(), 13),
    KOKS(new _ircIllIIIIllI().toString(), 14),
    XANX(new _ircIIIIIlIIll().toString(), 15),
    SKIDSENSE(new _ircIIIIllIlll().toString(), 16),
    BASHCLIENT(new _ircIllIIIlIlI().toString(), 17),
    CECTUS(new _ircIllIlIIIII().toString(), 18),
    MIDNIGHT(new _ircIlIIIIlIlI().toString(), 19),
    CENTRUM(new _ircIllIIlIlll().toString(), 20);

    private String _ircIllIllIIlI;
    private int _ircIllIllIIlI;

    public static ClientType getById(int id) {
        ClientType[] clientTypeArray = ClientType.values();
        int n = clientTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            ClientType clientType = clientTypeArray[n2];
            if (id == clientType.getId()) {
                return clientType;
            }
            ++n2;
        }
        return UNKNOWN;
    }

    public static ClientType getByName(String name) {
        name = name.replace(new _irclIIIlIlllI().toString(), new _ircIIIlIIIIll().toString());
        ClientType[] clientTypeArray = ClientType.values();
        int n = clientTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            ClientType clientType = clientTypeArray[n2];
            String string = clientType.getName().replace(new _irclIIlIllIII().toString(), new _irclllIllIIII().toString());
            if (string.equalsIgnoreCase(name)) {
                return clientType;
            }
            ++n2;
        }
        return UNKNOWN;
    }

    /*
     * WARNING - void declaration
     */
    private ClientType() {
        void var3_1;
        void id;
        this._ircIllIllIIlI = id;
        this._ircIllIllIIlI = var3_1;
    }

    public final String getName() {
        return this._ircIllIllIIlI;
    }

    public final int getId() {
        return this._ircIllIllIIlI;
    }
}

