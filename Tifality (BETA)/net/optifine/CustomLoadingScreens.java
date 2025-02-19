/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.src.Config;
import net.optifine.CustomLoadingScreen;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomLoadingScreens {
    private static CustomLoadingScreen[] screens = null;
    private static int screensMinDimensionId = 0;

    public static CustomLoadingScreen getCustomLoadingScreen() {
        if (screens == null) {
            return null;
        }
        int i = PacketThreadUtil.lastDimensionId;
        int j = i - screensMinDimensionId;
        CustomLoadingScreen customloadingscreen = null;
        if (j >= 0 && j < screens.length) {
            customloadingscreen = screens[j];
        }
        return customloadingscreen;
    }

    public static void update() {
        screens = null;
        screensMinDimensionId = 0;
        Pair<CustomLoadingScreen[], Integer> pair = CustomLoadingScreens.parseScreens();
        screens = pair.getLeft();
        screensMinDimensionId = pair.getRight();
    }

    private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
        String s = "optifine/gui/loading/background";
        String s1 = ".png";
        String[] astring = ResUtils.collectFiles(s, s1);
        HashMap<Integer, String> map2 = new HashMap<Integer, String>();
        for (int i = 0; i < astring.length; ++i) {
            String s2 = astring[i];
            String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
            int j = Config.parseInt(s3, Integer.MIN_VALUE);
            if (j == Integer.MIN_VALUE) {
                CustomLoadingScreens.warn("Invalid dimension ID: " + s3 + ", path: " + s2);
                continue;
            }
            map2.put(j, s2);
        }
        Set set = map2.keySet();
        Integer[] ainteger = set.toArray(new Integer[set.size()]);
        Arrays.sort((Object[])ainteger);
        if (ainteger.length <= 0) {
            return new ImmutablePair<Object, Integer>(null, 0);
        }
        String s5 = "optifine/gui/loading/loading.properties";
        Properties properties2 = ResUtils.readProperties(s5, "CustomLoadingScreens");
        int k = ainteger[0];
        int l = ainteger[ainteger.length - 1];
        int i1 = l - k + 1;
        CustomLoadingScreen[] acustomloadingscreen = new CustomLoadingScreen[i1];
        for (int j1 = 0; j1 < ainteger.length; ++j1) {
            Integer integer = ainteger[j1];
            String s4 = (String)map2.get(integer);
            acustomloadingscreen[integer.intValue() - k] = CustomLoadingScreen.parseScreen(s4, integer, properties2);
        }
        return new ImmutablePair<CustomLoadingScreen[], Integer>(acustomloadingscreen, k);
    }

    public static void warn(String str) {
        Config.warn("CustomLoadingScreen: " + str);
    }

    public static void dbg(String str) {
        Config.dbg("CustomLoadingScreen: " + str);
    }
}

