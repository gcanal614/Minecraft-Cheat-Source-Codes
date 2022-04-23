/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.server;

import club.tifality.utils.Wrapper;
import java.util.HashMap;
import java.util.Map;

public final class ServerUtils {
    private static final Map<String, Long> serverIpPingCache = new HashMap<String, Long>();
    private static final String HYPIXEL = "hypixel.net";

    private ServerUtils() {
    }

    public static void update(String ip, long ping) {
        serverIpPingCache.put(ip, ping);
    }

    public static long getPingToServer(String ip) {
        return serverIpPingCache.get(ip);
    }

    public static boolean isOnServer(String ip) {
        if (Wrapper.getMinecraft().isSingleplayer()) {
            return false;
        }
        return ServerUtils.getCurrentServerIP().endsWith(ip);
    }

    public static String getCurrentServerIP() {
        if (Wrapper.getMinecraft().isSingleplayer()) {
            return "Singleplayer";
        }
        return Wrapper.getMinecraft().getCurrentServerData().serverIP;
    }

    public static boolean isOnHypixel() {
        return ServerUtils.isOnServer(HYPIXEL);
    }

    public static long getPingToCurrentServer() {
        if (Wrapper.getMinecraft().isSingleplayer()) {
            return 0L;
        }
        return ServerUtils.getPingToServer(ServerUtils.getCurrentServerIP());
    }
}

