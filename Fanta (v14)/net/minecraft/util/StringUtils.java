/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    public static String ticksToElapsedTime(int ticks) {
        int i = ticks / 20;
        int j = i / 60;
        return (i %= 60) < 10 ? String.valueOf(j) + ":0" + i : String.valueOf(j) + ":" + i;
    }

    public static String stripControlCodes(String p_76338_0_) {
        return patternControlCode.matcher(p_76338_0_).replaceAll("");
    }

    public static boolean isNullOrEmpty(String string) {
        return org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)string);
    }
}

