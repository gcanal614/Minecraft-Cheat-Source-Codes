/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class TimedEvent {
    private static final Map<String, Long> mapEventTimes = new HashMap<String, Long>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isActive(String name, long timeIntervalMs) {
        Map<String, Long> map = mapEventTimes;
        synchronized (map) {
            long i = System.currentTimeMillis();
            long j = mapEventTimes.computeIfAbsent(name, k -> i);
            if (i < j + timeIntervalMs) {
                return false;
            }
            mapEventTimes.put(name, i);
            return true;
        }
    }
}

