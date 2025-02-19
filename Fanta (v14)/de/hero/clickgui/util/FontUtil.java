/*
 * Decompiled with CFR 0.152.
 */
package de.hero.clickgui.util;

import de.fanta.Client;
import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.utils.UnicodeFontRenderer;
import de.fanta.utils.UnicodeFontRenderer5;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

public class FontUtil {
    private static BasicFontRenderer fontRenderer;
    private static UnicodeFontRenderer fontRenderer2;
    private static UnicodeFontRenderer5 fontRenderer3;

    public static void setupFontUtils() {
        fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        fontRenderer2 = Client.INSTANCE.unicodeBasicFontRenderer;
        fontRenderer3 = Client.INSTANCE.unicodeBasicFontRenderer5;
    }

    public static int getStringWidth(String text) {
        return fontRenderer3.getStringWidth(StringUtils.stripControlCodes(text));
    }

    public static int getFontHeight() {
        return FontUtil.fontRenderer3.FONT_HEIGHT;
    }

    public static void drawString(String text, double x, double y, int color) {
        fontRenderer3.drawString(text, (float)x, (float)y, color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        fontRenderer3.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    public static void drawCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double)(fontRenderer3.getStringWidth(text) / 2), y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double)(fontRenderer3.getStringWidth(text) / 2), y, color);
    }

    public static void drawTotalCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double)(fontRenderer3.getStringWidth(text) / 2), y - (double)(FontUtil.fontRenderer2.FONT_HEIGHT / 2), color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        double d = x - (double)(fontRenderer3.getStringWidth(text) / 2);
        fontRenderer2.getClass();
        FontUtil.drawStringWithShadow(text, d, y - (double)(9.0f / 2.0f), color);
    }
}

