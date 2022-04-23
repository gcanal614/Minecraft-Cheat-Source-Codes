/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import net.minecraft.client.gui.MinecraftFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Gui {
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    public static Tessellator tessellator = Tessellator.getInstance();
    public static WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    protected float zLevel;
    private static final float TEXTURE_FACTOR = 0.00390625f;

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        int alpha = color >> 24 & 0xFF;
        boolean needBlend = alpha < 255;
        GL11.glDisable(3553);
        if (needBlend) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GlStateManager.enableBlend();
            GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)alpha);
        } else {
            GL11.glColor3ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF));
        }
        GL11.glBegin(7);
        GL11.glVertex2f(left, top);
        GL11.glVertex2f(left, bottom);
        GL11.glVertex2f(right, bottom);
        GL11.glVertex2f(right, top);
        GL11.glEnd();
        if (needBlend) {
            GL11.glDisable(3042);
            GlStateManager.disableBlend();
        }
        GL11.glEnable(3553);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        int alpha = color >> 24 & 0xFF;
        boolean needBlend = alpha < 255;
        GL11.glDisable(3553);
        if (needBlend) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)alpha);
        } else {
            GL11.glColor3ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF));
        }
        GL11.glBegin(7);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        if (needBlend) {
            GL11.glDisable(3042);
        }
        GL11.glEnable(3553);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + (float)height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + (float)width) * f, (v + (float)height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + (float)width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + (float)vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + (float)uWidth) * f, (v + (float)vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + (float)uWidth) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    protected void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        Gui.drawRect(startX, y, endX + 1, y + 1, color);
    }

    protected void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }
        Gui.drawRect(x, startY + 1, x + 1, endY, color);
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
        GL11.glDisable(3553);
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.shadeModel(7425);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0).color4f(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, 0.0).color4f(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, 0.0).color4f(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, 0.0).color4f(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glEnable(3553);
    }

    public void drawCenteredString(MinecraftFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    public void drawString(MinecraftFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x, y, color);
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float tx = (float)textureX * 0.00390625f;
        float txw = (float)(textureX + width) * 0.00390625f;
        float ty = (float)textureY * 0.00390625f;
        float tyh = (float)(textureY + height) * 0.00390625f;
        GL11.glBegin(7);
        GL11.glTexCoord2f(tx, tyh);
        GL11.glVertex2i(x, y + height);
        GL11.glTexCoord2f(txw, tyh);
        GL11.glVertex2i(x + width, y + height);
        GL11.glTexCoord2f(txw, ty);
        GL11.glVertex2i(x + width, y);
        GL11.glTexCoord2f(tx, ty);
        GL11.glVertex2i(x, y);
        GL11.glEnd();
    }

    public static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
        float tx = (float)minU * 0.00390625f;
        float txw = (float)(minU + maxU) * 0.00390625f;
        float ty = (float)minV * 0.00390625f;
        float tyh = (float)(minV + maxV) * 0.00390625f;
        GL11.glBegin(7);
        GL11.glTexCoord2f(tx, tyh);
        GL11.glVertex2f(xCoord, yCoord + (float)maxV);
        GL11.glTexCoord2f(txw, tyh);
        GL11.glVertex2f(xCoord + (float)maxU, yCoord + (float)maxV);
        GL11.glTexCoord2f(txw, ty);
        GL11.glVertex2f(xCoord + (float)maxU, yCoord);
        GL11.glTexCoord2f(tx, ty);
        GL11.glVertex2f(xCoord, yCoord);
        GL11.glEnd();
    }

    public static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
        float minU = textureSprite.getMinU();
        float maxU = textureSprite.getMaxU();
        float minV = textureSprite.getMinV();
        float maxV = textureSprite.getMaxV();
        GL11.glBegin(7);
        GL11.glTexCoord2f(minU, maxV);
        GL11.glVertex2i(xCoord, yCoord + heightIn);
        GL11.glTexCoord2f(maxU, maxV);
        GL11.glVertex2i(xCoord + widthIn, yCoord + heightIn);
        GL11.glTexCoord2f(maxU, minV);
        GL11.glVertex2i(xCoord + widthIn, yCoord);
        GL11.glTexCoord2f(minU, minV);
        GL11.glVertex2i(xCoord, yCoord);
        GL11.glEnd();
    }
}

