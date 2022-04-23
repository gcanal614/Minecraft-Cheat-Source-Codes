// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.util.Wrapper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.Minecraft;

public class RenderUtil
{
    public static Minecraft mc;
    public static WorldRenderer worldRenderer;
    public static Tessellator tessellator;
    
    public static void drawSmoothRoundedRect(float x, float y, float x1, float y1, final float radius, final int color) {
        GL11.glPushAttrib(0);
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        setColor(color);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * radius * -1.0, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius * -1.0);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * radius * -1.0, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius * -1.0);
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glBegin(2);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * radius * -1.0, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius * -1.0);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * radius * -1.0, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius * -1.0);
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static double animate(final double target, double current, double speed) {
        final boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        }
        else if (speed > 1.0) {
            speed = 1.0;
        }
        final double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += factor;
        }
        else {
            current -= factor;
        }
        return current;
    }
    
    public static void drawRoundedRect(float x, float y, float x1, float y1, final float radius, final int color) {
        GL11.glPushAttrib(0);
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        setColor(color);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * radius * -1.0, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius * -1.0);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * radius * -1.0, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius * -1.0);
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void drawSmoothRoundedRectWithWidth(final double x, final double y, final double width, final double height, final float radius, final int color) {
        drawSmoothRoundedRect((float)x, (float)y, (float)(x + width), (float)(y + height), radius, color);
    }
    
    public static void startScissor() {
        GL11.glClear(256);
        GL11.glEnable(3089);
    }
    
    public static void endScissor() {
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
        GL11.glDisable(3089);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableBlend();
    }
    
    public static void drawBoxOutline(final double x1, final double y1, final double x2, final double y2, final int colour, double thickness) {
        thickness /= 2.0;
        Gui.drawRect(x1 - thickness, y1 - thickness, x1 + thickness, y2 + thickness, colour);
        Gui.drawRect(x2 - thickness + 0.5, y1 - thickness - 0.5, x2 + thickness + 0.5, y2 + thickness, colour);
        Gui.drawRect(x1 - thickness, y1 - thickness - 0.5, x2 + thickness, y1 + thickness - 0.5, colour);
        Gui.drawRect(x1 - thickness, y2 - thickness, x2 + thickness, y2 + thickness, colour);
    }
    
    public static void drawRoundedOutline(final float x1, final float y1, final float x2, final float y2, final int colour, float thickness, final float radius) {
        thickness /= 2.0f;
        drawSmoothRoundedRect(x1 - thickness, y1 - thickness, x1 + thickness, y2 + thickness, radius, colour);
        drawSmoothRoundedRect(x2 - thickness + 0.5f, y1 - thickness - 0.5f, x2 + thickness + 0.5f, y2 + thickness, radius, colour);
        drawSmoothRoundedRect(x1 - thickness, y1 - thickness - 0.5f, x2 + thickness, y1 + thickness - 0.5f, radius, colour);
        drawSmoothRoundedRect(x1 - thickness, y2 - thickness, x2 + thickness, y2 + thickness, radius, colour);
    }
    
    public static void drawBox(final Double posX, final Double posY, final Double posZ, final float lineWidth, final int color) {
        final double x = posX - 0.5 - RenderManager.renderPosX;
        final double y = posY - RenderManager.renderPosY;
        final double z = posZ - 0.5 - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(lineWidth);
        setColorWithAlpha(color, 255);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        setColorWithAlpha(color, 255);
        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 2.0, z + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }
    
    public static void glHorizontalGradientQuad(final double x, final double y, final double width, final double height, final int startColor, final int endColor) {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        setColor(startColor);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        setColor(endColor);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
    }
    
    public static void drawImage(final ResourceLocation image, final float x, final float y, final int width, final int height) {
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, (float)width, (float)height, (float)width, (float)height);
    }
    
    public static void scissor(final double x, final double y, final double width, final double height) {
        int scaleFactor;
        for (scaleFactor = new ScaledResolution(Wrapper.getMinecraft(), Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight).getScaleFactor(); scaleFactor < 2 && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240; ++scaleFactor) {}
        GL11.glScissor((int)(x * scaleFactor), (int)(Minecraft.getMinecraft().displayHeight - (y + height) * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
    }
    
    public static void setColorWithAlpha(final int color, final int alpha) {
        final float a = (alpha & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    
    public static void setColor(final int color) {
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    
    static {
        RenderUtil.mc = Minecraft.getMinecraft();
        RenderUtil.worldRenderer = Tessellator.getInstance().getWorldRenderer();
        RenderUtil.tessellator = Tessellator.getInstance();
    }
}
