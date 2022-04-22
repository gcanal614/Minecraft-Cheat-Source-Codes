/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class ColorUtils {
    public static Color getClickGuiColor() {
        return new Color(218, 20, 23);
    }

    public static Color getClickGuiColor(float alpha) {
        return new Color(218.0f, 20.0f, 23.0f, alpha);
    }

    public static Color getRandomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }

    public static Color rainBowEffectWithOffset(int offset, float custom) {
        Minecraft mc = Minecraft.getMinecraft();
        int tmp = offset;
        long l = System.currentTimeMillis() - (long)(tmp * 10 - 0);
        int i = Color.HSBtoRGB((float)(l % (long)((int)custom)) / custom, 1.0f, 1.0f);
        return new Color(i);
    }

    public static Color rainBowEffectWithOffset(int offset) {
        Minecraft mc = Minecraft.getMinecraft();
        int tmp = offset;
        long l = System.currentTimeMillis() - (long)(tmp * 10 - 0);
        int i = Color.HSBtoRGB((float)(l % 2000L) / 2000.0f, 1.0f, 1.0f);
        return new Color(i);
    }

    public static Color colorWithOffset(Color c, int offset) {
        Minecraft mc = Minecraft.getMinecraft();
        int tmp = offset;
        long l = System.currentTimeMillis() - (long)(tmp * 10 - 0);
        int i = Color.HSBtoRGB(c.getRGB(), (float)(l % 4000L) / 4000.0f, 1.0f);
        return new Color(i);
    }

    public static Color rainBow() {
        long l = System.currentTimeMillis() - 0L;
        int i = Color.HSBtoRGB((float)(l % 2000L) / 2000.0f, 1.0f, 1.0f);
        return new Color(i);
    }

    public static Color getColorAlpha(Color color, int alpha) {
        return ColorUtils.getColorAlpha(color.getRGB(), alpha);
    }

    public static Color getColorAlpha(int color, int alpha) {
        Color color2 = new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), alpha);
        return color2;
    }

    public static int getMulitpliedColor(Color color, double ammount) {
        return new Color((int)((double)color.getRed() * ammount), (int)((double)color.getGreen() * ammount), (int)((double)color.getBlue() * ammount), color.getAlpha()).getRGB();
    }

    public static int getMulitpliedColor(int color, double ammount) {
        return ColorUtils.getMulitpliedColor(new Color(color), ammount);
    }
}

