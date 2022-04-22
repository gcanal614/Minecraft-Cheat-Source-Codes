/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.client._irclIIIlllIlI;
import de.liquiddev.ircclient.client._irclIIlIlIIII;
import de.liquiddev.ircclient.client._irclIIlllIIII;
import de.liquiddev.ircclient.client._irclIIlllIIlI;
import de.liquiddev.ircclient.client._irclIlIlIlIII;
import de.liquiddev.ircclient.client._ircllIIlIIlll;
import de.liquiddev.ircclient.util._ircIllIllIIlI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IrcPlayer {
    private static HashMap _ircIllIllIIlI = new HashMap();
    private String _ircIllIllIIlI;
    private byte[] _ircIllIllIIlI;
    private String _irclllllIIlII;
    private IrcRank _ircIllIllIIlI;
    private String _ircIllIlIlIII;
    private long _ircIllIllIIlI;
    private long _irclllllIIlII;
    private String _irclIIlIlllll;
    private String _irclllIIlIlIl;
    private String _ircIIlIIIlIlI;

    public static IrcPlayer getByIngameName(String name) {
        return IrcPlayer.getByHash(de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI(name));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IrcPlayer getByHash(byte[] hash) {
        Object object = ByteBuffer.wrap(hash);
        HashMap hashMap = _ircIllIllIIlI;
        synchronized (hashMap) {
            return (IrcPlayer)_ircIllIllIIlI.get(object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IrcPlayer getByIrcNickname(String name) {
        Object object = name.toUpperCase();
        object = arg_0 -> IrcPlayer._ircIllIllIIlI((String)object, arg_0);
        HashMap hashMap = _ircIllIllIIlI;
        synchronized (hashMap) {
            return _ircIllIllIIlI.values().stream().filter(object).findAny().orElse(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isClientUser(String ingameName) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI(ingameName));
        HashMap hashMap = _ircIllIllIIlI;
        synchronized (hashMap) {
            return _ircIllIllIIlI.containsKey(byteBuffer);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Collection listPlayers() {
        HashMap hashMap = _ircIllIllIIlI;
        synchronized (hashMap) {
            return new ArrayList(_ircIllIllIIlI.values());
        }
    }

    /*
     * WARNING - void declaration
     */
    public IrcPlayer(String ircNick, byte[] hash, String serverIp, IrcRank rank, String clientName, String extra, long playtime, String clientVersion, String capeLocation) {
        void var3_3;
        void var1_1;
        void var2_2;
        this._ircIllIllIIlI = var2_2;
        this._ircIllIllIIlI = var1_1;
        this._irclllllIIlII = var3_3;
        this._ircIllIllIIlI = rank;
        this._ircIllIlIlIII = clientName;
        this._ircIllIllIIlI = playtime;
        this._irclllllIIlII = System.currentTimeMillis();
        this._irclIIlIlllll = extra;
        this._irclllIIlIlIl = clientVersion;
        this._ircIIlIIIlIlI = capeLocation;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void register() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this._ircIllIllIIlI);
        HashMap hashMap = _ircIllIllIIlI;
        synchronized (hashMap) {
            _ircIllIllIIlI.put(byteBuffer, this);
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unregister() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this._ircIllIllIIlI);
        HashMap hashMap = _ircIllIllIIlI;
        synchronized (hashMap) {
            _ircIllIllIIlI.remove(byteBuffer);
            return;
        }
    }

    public String getIrcNick() {
        return this._ircIllIllIIlI;
    }

    public String getServerIp() {
        return this._irclllllIIlII;
    }

    public IrcRank getRank() {
        return this._ircIllIllIIlI;
    }

    public String getClientName() {
        return this._ircIllIlIlIII;
    }

    public long getPlaytime() {
        long l = System.currentTimeMillis() - this._irclllllIIlII;
        return this._ircIllIllIIlI + l;
    }

    public long getUpdateTimestamp() {
        return this._irclllllIIlII;
    }

    public String getExtra() {
        return this._irclIIlIlllll;
    }

    public String getClientVersion() {
        return this._irclllIIlIlIl;
    }

    public String getCapeLocation() {
        return this._ircIIlIIIlIlI;
    }

    public boolean hasCape() {
        return this._ircIIlIIIlIlI != null;
    }

    public String toString() {
        return String.valueOf(new _irclIIlllIIII(this).toString()) + this._ircIllIllIIlI + new _irclIIIlllIlI(this).toString() + this._irclllllIIlII + new _irclIIlIlIIII(this).toString() + (Object)((Object)this._ircIllIllIIlI) + new _irclIIlllIIlI(this).toString() + this._ircIllIlIlIII + new _irclIlIlIlIII(this).toString() + this._irclllIIlIlIl + new _ircllIIlIIlll(this).toString();
    }

    private static /* synthetic */ boolean _ircIllIllIIlI(String string, IrcPlayer ircPlayer) {
        return string.equals(ircPlayer.getIrcNick().toUpperCase());
    }
}

