/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomGuiProperties;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomGuis {
    private static final Minecraft mc = Config.getMinecraft();
    private static CustomGuiProperties[][] guiProperties = null;
    public static final boolean isChristmas = CustomGuis.isChristmas();

    public static ResourceLocation getTextureLocation(ResourceLocation loc) {
        if (guiProperties == null) {
            return loc;
        }
        GuiScreen guiscreen = CustomGuis.mc.currentScreen;
        if (!(guiscreen instanceof GuiContainer)) {
            return loc;
        }
        if (loc.getResourceDomain().equals("minecraft") && loc.getResourcePath().startsWith("textures/gui/")) {
            return loc;
        }
        return loc;
    }

    public static void update() {
        guiProperties = null;
        if (Config.isCustomGuis()) {
            ArrayList<List<CustomGuiProperties>> list = new ArrayList<List<CustomGuiProperties>>();
            IResourcePack[] airesourcepack = Config.getResourcePacks();
            for (int i = airesourcepack.length - 1; i >= 0; --i) {
                IResourcePack iresourcepack = airesourcepack[i];
                CustomGuis.update(iresourcepack, list);
            }
            guiProperties = CustomGuis.propertyListToArray(list);
        }
    }

    private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> listProps) {
        if (listProps.isEmpty()) {
            return null;
        }
        CustomGuiProperties[][] acustomguiproperties = new CustomGuiProperties[CustomGuiProperties.EnumContainer.VALUES.length][];
        for (int i = 0; i < acustomguiproperties.length; ++i) {
            List<CustomGuiProperties> list;
            if (listProps.size() <= i || (list = listProps.get(i)) == null) continue;
            CustomGuiProperties[] acustomguiproperties1 = list.toArray(new CustomGuiProperties[0]);
            acustomguiproperties[i] = acustomguiproperties1;
        }
        return acustomguiproperties;
    }

    private static void update(IResourcePack rp, List<List<CustomGuiProperties>> listProps) {
        Object[] astring = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", null);
        Arrays.sort(astring);
        for (Object s : astring) {
            Config.dbg("CustomGuis: " + (String)s);
            try {
                ResourceLocation resourcelocation = new ResourceLocation((String)s);
                InputStream inputstream = rp.getInputStream(resourcelocation);
                if (inputstream == null) {
                    Config.warn("CustomGuis file not found: " + (String)s);
                    continue;
                }
                PropertiesOrdered properties = new PropertiesOrdered();
                properties.load(inputstream);
                inputstream.close();
                CustomGuiProperties customguiproperties = new CustomGuiProperties(properties, (String)s);
                if (!customguiproperties.isValid((String)s)) continue;
                CustomGuis.addToList(customguiproperties, listProps);
            }
            catch (FileNotFoundException var9) {
                Config.warn("CustomGuis file not found: " + (String)s);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static void addToList(CustomGuiProperties cgp, List<List<CustomGuiProperties>> listProps) {
        if (cgp.getContainer() == null) {
            CustomGuis.warn("Invalid container: " + (Object)((Object)cgp.getContainer()));
        } else {
            int i = cgp.getContainer().ordinal();
            while (listProps.size() <= i) {
                listProps.add(null);
            }
            List<CustomGuiProperties> list = listProps.get(i);
            if (list == null) {
                list = new ArrayList<CustomGuiProperties>();
                listProps.set(i, list);
            }
            list.add(cgp);
        }
    }

    private static boolean isChristmas() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26;
    }

    private static void warn(String str) {
        Config.warn("[CustomGuis] " + str);
    }
}

