package com.zerosense.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {
    public static boolean SetCustomYaw = false;
    public static float CustomYaw = 0;

    public static void setCustomYaw(float customYaw) {
        CustomYaw = customYaw;
        SetCustomYaw = true;
        mc.thePlayer.rotationYawHead = customYaw;
    }

    public static void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
        Gui.drawRect(x, y, x + width, y + lineSize, borderColor);
        Gui.drawRect(x, y, x + lineSize, y + height, borderColor);
        Gui.drawRect(x + width, y, x + width - lineSize, y + height, borderColor);
        Gui.drawRect( x, y + height, x + width, y + height - lineSize, borderColor);
    }

    public static void drawRect(double x, double y, double width, double height, int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
        Gui.drawRect( x, y, x + width, y + height, color);
    }

    public static void resetPlayerYaw() {
        SetCustomYaw = false;
    }

    public static float getCustomYaw() {

        return CustomYaw;

    }

    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
    }

    public static boolean SetCustomPitch = false;
    public static float CustomPitch = 0;

    public static void setCustomPitch(float customPitch) {
        CustomPitch = customPitch;
        SetCustomPitch = true;
    }

    public static void resetPlayerPitch() {
        SetCustomPitch = false;
    }

    public static float getCustomPitch() {

        return CustomPitch;

    }

    // Made by lavaflowglow 11/19/2020 3:39 AM

    public static Minecraft mc = Minecraft.getMinecraft();
    public static WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
    public static Tessellator tessellator = Tessellator.getInstance();

    // Someone gave me this code
    public static void drawPlayerBox(Double posX, Double posY, Double posZ){
        double x =
                posX - 0.5
                        - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y =
                posY
                        - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z =
                posZ - 0.5
                        - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glColor4d(0, 1, 0, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        //drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glColor4d(1, 1, 1, 0.5F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void drawLine(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        minX -= Minecraft.getMinecraft().getRenderManager().renderPosX;
        minY -= Minecraft.getMinecraft().getRenderManager().renderPosY;
        minZ -= Minecraft.getMinecraft().getRenderManager().renderPosZ;

        maxX -= Minecraft.getMinecraft().getRenderManager().renderPosX;
        maxY -= Minecraft.getMinecraft().getRenderManager().renderPosY;
        maxZ -= Minecraft.getMinecraft().getRenderManager().renderPosZ;

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(3.0F);
        GL11.glColor4d(0, 1, 0, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        //drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glColor4d(1, 1, 1, 0.5F);

        tessellator.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);

    }

    public static void setColorForIcon(Color color) {
        GlStateManager.enableBlend();
        GlStateManager.color(((float) color.getRed()) / 255, ((float) color.getGreen()) / 255,
                ((float) color.getBlue()) / 255);
    }

    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }
    public static void drawBoundingBox(AxisAlignedBB paramAxisAlignedBB, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        byte b = 3;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4d(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(3.0F);
        GL11.glEnable(2848);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(b);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(b);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(b);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(b);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        worldRenderer.addVertex(paramAxisAlignedBB.minX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(b);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.minY, paramAxisAlignedBB.maxZ);
        worldRenderer.addVertex(paramAxisAlignedBB.maxX, paramAxisAlignedBB.maxY, paramAxisAlignedBB.maxZ);
        tessellator.draw();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
