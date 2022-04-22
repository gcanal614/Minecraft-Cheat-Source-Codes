/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GlowingTextHandler {
    public static void drawGlow(double x, double y, double x1, double y1, int color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(771, 770, 1, 0);
        GlStateManager.shadeModel(7425);
        GlowingTextHandler.drawGradientRect((int)x, (int)y, (int)x1, (int)(y + (y1 - y) / 2.0), 0, color);
        GlowingTextHandler.drawGradientRect((int)x, (int)(y + (y1 - y) / 2.0), (int)x1, (int)y1, color, 0);
        int radius = (int)((y1 - y) / 2.0);
        GlowingTextHandler.drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 0, color, 0);
        GlowingTextHandler.drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 1, color, 0);
        GlowingTextHandler.drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 2, color, 0);
        GlowingTextHandler.drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 3, color, 0);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPolygonPart(double x, double y, int radius, int part, int color, int endcolor) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha1 = (float)(endcolor >> 24 & 0xFF) / 255.0f;
        float red1 = (float)(endcolor >> 16 & 0xFF) / 255.0f;
        float green1 = (float)(endcolor >> 8 & 0xFF) / 255.0f;
        float blue1 = (float)(endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(771, 770, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        double TWICE_PI = Math.PI * 2;
        int i = part * 90;
        while (i <= part * 90 + 90) {
            double angle = Math.PI * 2 * (double)i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).color(red1, green1, blue1, alpha1).endVertex();
            ++i;
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(771, 770, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        GuiScreen cfr_ignored_0 = Minecraft.getMinecraft().currentScreen;
        bufferbuilder.pos(right, top, GuiScreen.zLevel).color(f1, f2, f3, f).endVertex();
        GuiScreen cfr_ignored_1 = Minecraft.getMinecraft().currentScreen;
        bufferbuilder.pos(left, top, GuiScreen.zLevel).color(f1, f2, f3, f).endVertex();
        GuiScreen cfr_ignored_2 = Minecraft.getMinecraft().currentScreen;
        bufferbuilder.pos(left, bottom, GuiScreen.zLevel).color(f5, f6, f7, f4).endVertex();
        GuiScreen cfr_ignored_3 = Minecraft.getMinecraft().currentScreen;
        bufferbuilder.pos(right, bottom, GuiScreen.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}

