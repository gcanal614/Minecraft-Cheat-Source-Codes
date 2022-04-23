/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.RandomUtils
 */
package me.uncodable.srt.impl.utils;

import org.apache.commons.lang3.RandomUtils;

public class RngUtils {
    public static boolean isChance(int chance, int firstBound, int secondBound) {
        int roll = RandomUtils.nextInt((int)firstBound, (int)secondBound);
        return roll <= chance;
    }
}

