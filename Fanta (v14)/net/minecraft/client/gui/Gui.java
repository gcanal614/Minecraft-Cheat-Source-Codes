/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.module.impl.visual.Themes;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
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
    public static float zLevel;

    public static void drawRect2(double x1, double y1, double x2, double y2, int argbColor) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (x1 < x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            double temp1 = y1;
            y1 = y2;
            y2 = temp1;
        }
        float a = (float)(argbColor >> 24 & 0xFF) / 255.0f;
        float r = (float)(argbColor >> 16 & 0xFF) / 255.0f;
        float g = (float)(argbColor >> 8 & 0xFF) / 255.0f;
        float b = (float)(argbColor & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x1, y2, 0.0).endVertex();
        worldrenderer.pos(x2, y2, 0.0).endVertex();
        worldrenderer.pos(x2, y1, 0.0).endVertex();
        worldrenderer.pos(x1, y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRGBLineHorizontal(double x, double y, double width, float linewidth, int colors, boolean reverse) {
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth((float)linewidth);
        GL11.glBegin((int)3);
        colors = (int)((double)colors * width);
        double steps = width / (double)colors;
        double cX = x;
        double cX2 = x + steps;
        if (reverse) {
            int i = colors;
            while (i > 0) {
                int argbColor = Themes.rainbow(i * 10);
                float a = (float)(argbColor >> 24 & 0xFF) / 255.0f;
                float r = (float)(argbColor >> 16 & 0xFF) / 255.0f;
                float g = (float)(argbColor >> 8 & 0xFF) / 255.0f;
                float b = (float)(argbColor & 0xFF) / 255.0f;
                GlStateManager.color(r, g, b, a);
                GL11.glVertex2d((double)cX, (double)y);
                GL11.glVertex2d((double)cX2, (double)y);
                cX = cX2;
                cX2 += steps;
                --i;
            }
        } else {
            int i = 0;
            while (i < colors) {
                int argbColor = Themes.rainbow(i * 10);
                float a = (float)(argbColor >> 24 & 0xFF) / 255.0f;
                float r = (float)(argbColor >> 16 & 0xFF) / 255.0f;
                float g = (float)(argbColor >> 8 & 0xFF) / 255.0f;
                float b = (float)(argbColor & 0xFF) / 255.0f;
                GlStateManager.color(r, g, b, a);
                GL11.glVertex2d((double)cX, (double)y);
                GL11.glVertex2d((double)cX2, (double)y);
                cX = cX2;
                cX2 += steps;
                ++i;
            }
        }
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRGBLineVertical(double x, double y, double height, float linewidth, int colors, boolean reverse) {
        GlStateManager.shadeModel(7425);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth((float)linewidth);
        GL11.glBegin((int)3);
        colors = (int)((double)colors * height);
        double steps = height / (double)colors;
        double cY = y;
        double cY2 = y + steps;
        if (reverse) {
            int i = colors;
            while (i > 0) {
                int argbColor = Themes.rainbow(i * 10);
                float a = (float)(argbColor >> 24 & 0xFF) / 255.0f;
                float r = (float)(argbColor >> 16 & 0xFF) / 255.0f;
                float g = (float)(argbColor >> 8 & 0xFF) / 255.0f;
                float b = (float)(argbColor & 0xFF) / 255.0f;
                GlStateManager.color(r, g, b, a);
                GL11.glVertex2d((double)x, (double)cY);
                GL11.glVertex2d((double)x, (double)cY2);
                cY = cY2;
                cY2 += steps;
                --i;
            }
        } else {
            int i = 0;
            while (i < colors) {
                int argbColor = Themes.rainbow(i * 10);
                float a = (float)(argbColor >> 24 & 0xFF) / 255.0f;
                float r = (float)(argbColor >> 16 & 0xFF) / 255.0f;
                float g = (float)(argbColor >> 8 & 0xFF) / 255.0f;
                float b = (float)(argbColor & 0xFF) / 255.0f;
                GlStateManager.color(r, g, b, a);
                GL11.glVertex2d((double)x, (double)cY);
                GL11.glVertex2d((double)x, (double)cY2);
                cY = cY2;
                cY2 += steps;
                ++i;
            }
        }
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(float h, float l, float k, float g, int color) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (h < k) {
            int i = (int)h;
            h = k;
            k = i;
        }
        if (l < g) {
            int j = (int)l;
            l = (int)g;
            g = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(h, g, 0.0).endVertex();
        worldrenderer.pos(k, g, 0.0).endVertex();
        worldrenderer.pos(k, l, 0.0).endVertex();
        worldrenderer.pos(h, l, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glEnable((int)3042);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + width) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
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

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
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
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void drawCenteredString(BasicFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    public void drawString(BasicFontRenderer fontRendererIn, String text, float x, float y, int color) {
        fontRendererIn.drawStringWithShadow(text, x, y, color);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x + 0, y + height, zLevel).tex((float)(textureX + 0) * f, (float)(textureY + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, zLevel).tex((float)(textureX + width) * f, (float)(textureY + height) * f1).endVertex();
        worldrenderer.pos(x + width, y + 0, zLevel).tex((float)(textureX + width) * f, (float)(textureY + 0) * f1).endVertex();
        worldrenderer.pos(x + 0, y + 0, zLevel).tex((float)(textureX + 0) * f, (float)(textureY + 0) * f1).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0.0f, yCoord + (float)maxV, zLevel).tex((float)(minU + 0) * f, (float)(minV + maxV) * f1).endVertex();
        worldrenderer.pos(xCoord + (float)maxU, yCoord + (float)maxV, zLevel).tex((float)(minU + maxU) * f, (float)(minV + maxV) * f1).endVertex();
        worldrenderer.pos(xCoord + (float)maxU, yCoord + 0.0f, zLevel).tex((float)(minU + maxU) * f, (float)(minV + 0) * f1).endVertex();
        worldrenderer.pos(xCoord + 0.0f, yCoord + 0.0f, zLevel).tex((float)(minU + 0) * f, (float)(minV + 0) * f1).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0, yCoord + heightIn, zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + heightIn, zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + 0, zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
        worldrenderer.pos(xCoord + 0, yCoord + 0, zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public void drawHollowRect(float x, float y, float width, float height, float lineWidth, int color) {
        Gui.drawRect2(x, y, x + lineWidth, y + height, color);
        Gui.drawRect2(x + width - lineWidth, y, x + width, y + height, color);
        Gui.drawRect2(x, y, x + width, y + lineWidth, color);
        Gui.drawRect2(x, y + height - lineWidth, x + width, y + height, color);
    }

    public void drawHollowRect(double x, double y, double width, double height, double lineWidth, int color) {
        Gui.drawRect2(x, y, x + lineWidth, y + height, color);
        Gui.drawRect2(x + width - lineWidth, y, x + width, y + height, color);
        Gui.drawRect2(x, y, x + width, y + lineWidth, color);
        Gui.drawRect2(x, y + height - lineWidth, x + width, y + height, color);
    }

    public static void draw2dLine(double x, double z, double x1, double z1, Color color) {
        Gui.enableRender3D(true);
        RenderUtil.glColor(color.getRGB());
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)z);
        GL11.glVertex2d((double)x1, (double)z1);
        GL11.glEnd();
        Gui.disableRender3D(true);
    }

    public static void draw2dLine(double x, double z, double x1, double z1, double lineWidth, Color color) {
        Gui.enableRender3D(true);
        RenderUtil.glColor(color.getRGB());
        GL11.glLineWidth((float)((float)lineWidth));
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)z);
        GL11.glVertex2d((double)x1, (double)z1);
        GL11.glEnd();
        Gui.disableRender3D(true);
    }

    private static void enableRender3D(boolean disableDepth) {
        if (disableDepth) {
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
        }
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)1.0f);
    }

    private static void disableRender3D(boolean enableDepth) {
        if (enableDepth) {
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
        }
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

