/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterables
 *  org.apache.commons.io.Charsets
 *  org.apache.commons.io.IOUtils
 */
package net.optifine;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Lang {
    private static final Splitter splitter = Splitter.on((char)'=').limit(2);
    private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");

    public static void resourcesReloaded() {
        IResourcePack[] airesourcepack;
        Map map = I18n.getLocaleProperties();
        ArrayList<String> list = new ArrayList<String>();
        String s = "optifine/lang/";
        String s1 = "en_US";
        String s2 = ".lang";
        list.add(s + s1 + s2);
        if (!Config.getGameSettings().language.equals(s1)) {
            list.add(s + Config.getGameSettings().language + s2);
        }
        String[] astring = list.toArray(new String[0]);
        Lang.loadResources(Config.getDefaultResourcePack(), astring, map);
        for (IResourcePack iresourcepack : airesourcepack = Config.getResourcePacks()) {
            Lang.loadResources(iresourcepack, astring, map);
        }
    }

    private static void loadResources(IResourcePack rp, String[] files, Map localeProperties) {
        try {
            for (String s : files) {
                InputStream inputstream;
                ResourceLocation resourcelocation = new ResourceLocation(s);
                if (!rp.resourceExists(resourcelocation) || (inputstream = rp.getInputStream(resourcelocation)) == null) continue;
                Lang.loadLocaleData(inputstream, localeProperties);
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public static void loadLocaleData(InputStream is, Map localeProperties) throws IOException {
        for (String s : IOUtils.readLines((InputStream)is, (Charset)Charsets.UTF_8)) {
            String[] astring;
            if (s.isEmpty() || s.charAt(0) == '#' || (astring = (String[])Iterables.toArray((Iterable)splitter.split((CharSequence)s), String.class)) == null || astring.length != 2) continue;
            String s1 = astring[0];
            String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
            localeProperties.put(s1, s2);
        }
    }

    public static String get(String key) {
        return I18n.format(key, new Object[0]);
    }

    public static String get(String key, String def) {
        String s = I18n.format(key, new Object[0]);
        return s != null && !s.equals(key) ? s : def;
    }

    public static String getOn() {
        return I18n.format("options.on", new Object[0]);
    }

    public static String getOff() {
        return I18n.format("options.off", new Object[0]);
    }

    public static String getFast() {
        return I18n.format("options.graphics.fast", new Object[0]);
    }

    public static String getFancy() {
        return I18n.format("options.graphics.fancy", new Object[0]);
    }

    public static String getDefault() {
        return I18n.format("generator.default", new Object[0]);
    }
}

