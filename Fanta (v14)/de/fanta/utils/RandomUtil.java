/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.internal.ThreadLocalRandom
 */
package de.fanta.utils;

import io.netty.util.internal.ThreadLocalRandom;

public class RandomUtil {
    public static int nextInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static long nextLong(long origin, long bound) {
        return ThreadLocalRandom.current().nextLong(origin, bound);
    }

    public static float nextFloat(double origin, double bound) {
        return (float)ThreadLocalRandom.current().nextDouble((double)((float)origin), (double)((float)bound));
    }

    public static float nextFloat(float origin, float bound) {
        return (float)ThreadLocalRandom.current().nextDouble((double)origin, (double)bound);
    }

    public static double nextDouble(double origin, double bound) {
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }

    public static double randomSin() {
        return Math.sin(RandomUtil.nextDouble(0.0, Math.PI * 2));
    }
}

