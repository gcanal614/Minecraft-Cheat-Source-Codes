/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  adp$a
 *  ave
 *  avm
 *  axu
 */
package net.minecraft.realms;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.net.Proxy;
import net.minecraft.realms.RealmsScreen;

public class Realms {
    public static boolean isTouchScreen() {
        return ave.A().t.A;
    }

    public static Proxy getProxy() {
        return ave.A().O();
    }

    public static String sessionId() {
        avm avm2 = ave.A().L();
        if (avm2 == null) {
            return null;
        }
        return avm2.a();
    }

    public static String userName() {
        avm avm2 = ave.A().L();
        if (avm2 == null) {
            return null;
        }
        return avm2.c();
    }

    public static long currentTimeMillis() {
        return ave.J();
    }

    public static String getSessionId() {
        return ave.A().L().a();
    }

    public static String getUUID() {
        return ave.A().L().b();
    }

    public static String getName() {
        return ave.A().L().c();
    }

    public static String uuidToName(String string) {
        return ave.A().aa().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(string), null), false).getName();
    }

    public static void setScreen(RealmsScreen realmsScreen) {
        ave.A().a((axu)realmsScreen.getProxy());
    }

    public static String getGameDirectoryPath() {
        return ave.A().v.getAbsolutePath();
    }

    public static int survivalId() {
        return adp.a.b.a();
    }

    public static int creativeId() {
        return adp.a.c.a();
    }

    public static int adventureId() {
        return adp.a.d.a();
    }

    public static int spectatorId() {
        return adp.a.e.a();
    }

    public static void setConnectedToRealms(boolean bl) {
        ave.A().a(bl);
    }

    public static ListenableFuture<Object> downloadResourcePack(String string, String string2) {
        ListenableFuture listenableFuture = ave.A().R().a(string, string2);
        return listenableFuture;
    }

    public static void clearResourcePack() {
        ave.A().R().f();
    }
}

