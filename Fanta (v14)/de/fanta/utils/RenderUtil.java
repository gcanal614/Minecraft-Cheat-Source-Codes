/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawGradientRect(double x, double y, double width, double height, Color startColor, Color endColor) {
        Gui.drawGradientRect(x, y, x + width, y + height, startColor.getRGB(), endColor.getRGB());
    }

    public static void drawGradientRect(double x, double y, double width, double height, int startColor, int endColor) {
        Gui.drawGradientRect(x, y, x + width, y + height, startColor, endColor);
    }

    public static void prepareScissorBox(double x, double y, double width, double height) {
        int factor = RenderUtil.getScaledResolution().getScaleFactor();
        GL11.glScissor((int)((int)(x * (double)factor)), (int)((int)(((double)RenderUtil.getScaledResolution().getScaledHeight() - (y + height)) * (double)factor)), (int)((int)((x + width - x) * (double)factor)), (int)((int)((y + height - y) * (double)factor)));
    }

    public static void prepareScissorBox(float x, float y, float width, float height) {
        RenderUtil.prepareScissorBox((double)x, (double)y, (double)width, (double)height);
    }

    public static void drawRect(double x, double y, double width, double height, Color color) {
        Gui.drawRect2(x, y, x + width, y + height, color.getRGB());
    }

    public static void drawRect(double x, double y, double width, double height, int color) {
        Gui.drawRect2(x, y, x + width, y + height, color);
    }

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtil.glColor(color);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void rectangle2(double left, double top, double right, double bottom, Color color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void renderTag(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
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
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void scissorBox(float x, float y, float width, float length) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = scale.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)scaleFactor)), (int)((int)(((float)scale.getScaledHeight() - length) * (float)scaleFactor)), (int)((int)((width - x) * (float)scaleFactor)), (int)((int)((length - y) * (float)scaleFactor)));
    }

    public static void drawCircle(double x, double y, double radius, int color) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBegin((int)9);
        RenderUtil.glColor(color);
        int i = 0;
        while (i <= 360) {
            double x2 = Math.sin((double)i * Math.PI / 180.0) * radius;
            double y2 = Math.cos((double)i * Math.PI / 180.0) * radius;
            GL11.glVertex2d((double)(x + x2), (double)(y + y2));
            ++i;
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawTriangleFilled(float x, float y, float width, float height, int color) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        RenderUtil.glColor(color);
        GL11.glBegin((int)9);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glVertex2d((double)(x + width * 2.0f), (double)y);
        GL11.glVertex2d((double)(x + width * 2.0f), (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawTriangle(float x, float y, float width, float height, int color) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        RenderUtil.glColor(color);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glEnd();
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glVertex2d((double)(x + width * 2.0f), (double)y);
        GL11.glEnd();
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)(x + width * 2.0f), (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawRoundedRect(float x, float y, float width, float height, int roundFactor, int color) {
        GlStateManager.pushMatrix();
        RenderUtil.drawCircle(x + height / (float)roundFactor, y + height / (float)roundFactor, height / (float)roundFactor, color);
        RenderUtil.drawCircle(x + height / (float)roundFactor, y + height - height / (float)roundFactor, height / (float)roundFactor, color);
        RenderUtil.drawCircle(x + width - height / (float)roundFactor, y + height / (float)roundFactor, height / (float)roundFactor, color);
        RenderUtil.drawCircle(x + width - height / (float)roundFactor, y + height - height / (float)roundFactor, height / (float)roundFactor, color);
        Gui.drawRect(x, y + height / (float)roundFactor, x + width, y + height - height / (float)roundFactor, color);
        Gui.drawRect(x + height / (float)roundFactor, y, x + width - height / (float)roundFactor, y + height, color);
        GlStateManager.popMatrix();
    }

    public static void color(float red, float green, float blue, float alpha) {
        GL11.glColor4f((float)(red / 255.0f), (float)(green / 255.0f), (float)(blue / 255.0f), (float)(alpha / 255.0f));
    }

    public static void color(Color color) {
        RenderUtil.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static void color(int argb) {
        float alpha = (float)(argb >> 24 & 0xFF) / 255.0f;
        float red = (float)(argb >> 16 & 0xFF) / 255.0f;
        float green = (float)(argb >> 8 & 0xFF) / 255.0f;
        float blue = (float)(argb & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawRoundedRect2(double x, double y, double width, double height, double cornerRadius, Color color) {
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        RenderUtil.color(color);
        GL11.glBegin((int)9);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        int i = 0;
        while (i <= 90) {
            GL11.glVertex2d((double)(cornerX + Math.sin((double)i * Math.PI / 180.0) * cornerRadius), (double)(cornerY + Math.cos((double)i * Math.PI / 180.0) * cornerRadius));
            i += 30;
        }
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        i = 90;
        while (i <= 180) {
            GL11.glVertex2d((double)(cornerX + Math.sin((double)i * Math.PI / 180.0) * cornerRadius), (double)(cornerY + Math.cos((double)i * Math.PI / 180.0) * cornerRadius));
            i += 30;
        }
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        i = 180;
        while (i <= 270) {
            GL11.glVertex2d((double)(cornerX + Math.sin((double)i * Math.PI / 180.0) * cornerRadius), (double)(cornerY + Math.cos((double)i * Math.PI / 180.0) * cornerRadius));
            i += 30;
        }
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        i = 270;
        while (i <= 360) {
            GL11.glVertex2d((double)(cornerX + Math.sin((double)i * Math.PI / 180.0) * cornerRadius), (double)(cornerY + Math.cos((double)i * Math.PI / 180.0) * cornerRadius));
            i += 30;
        }
        GL11.glEnd();
        RenderUtil.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    public static void draw3dLine(double x, double y, double z, double x1, double y1, double z1, Color color) {
        GlStateManager.pushMatrix();
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)1.0f);
        RenderUtil.glColor(color.getRGB());
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glVertex3d((double)x1, (double)y1, (double)z1);
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }

    public static void draw2dLine(double x, double z, double x1, double z1, Color color) {
        GlStateManager.pushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)1.0f);
        RenderUtil.glColor(color.getRGB());
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)z);
        GL11.glVertex2d((double)x1, (double)z1);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }

    public static void startDrawing() {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
    }

    public static void stopDrawing() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void renderOne() {
        RenderUtil.checkSetupFBO();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)3.0f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glClearStencil((int)15);
        GL11.glStencilFunc((int)512, (int)1, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderTwo() {
        GL11.glEnable((int)2848);
        GL11.glStencilFunc((int)512, (int)0, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6914);
    }

    public static void renderThree() {
        GL11.glEnable((int)2848);
        GL11.glStencilFunc((int)514, (int)1, (int)15);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderFour() {
        GL11.glEnable((int)2848);
        RenderUtil.glColor(new Color(0, 172, 255).getRGB());
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)10754);
        GL11.glPolygonOffset((float)1.0f, (float)-2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }

    public static void renderFive() {
        GL11.glEnable((int)2848);
        GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
        GL11.glDisable((int)10754);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2960);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            RenderUtil.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Minecraft.getMinecraft().displayWidth, (int)Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)stencil_depth_buffer_ID);
    }

    public static Color injectAlpha(int color, int alpha) {
        Color temp = new Color(color);
        return new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), MathHelper.clamp_int(alpha, 0, 255));
    }
}

