/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.utils;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlurHelper2 {
    private ResourceLocation resourceLocation = new ResourceLocation("shaders/post/blur2.json");
    private ShaderGroup shaderGroup;
    private Framebuffer framebuffer;
    private int lastFactor;
    private int lastWidth;
    private int lastHeight;
    private Minecraft mc;

    public void init() {
        try {
            this.mc = Minecraft.getMinecraft();
            this.shaderGroup = new ShaderGroup(Minecraft.getMinecraft().getTextureManager(), Minecraft.getMinecraft().getResourceManager(), this.mc.getFramebuffer(), this.resourceLocation);
            this.shaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.framebuffer = this.shaderGroup.mainFramebuffer;
            return;
        }
        catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private void setValues(float strength) {
        this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set(strength);
    }

    public final void blur(float blurStrength) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int scaleFactor = scaledResolution.getScaleFactor();
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        if (this.lastFactor != scaleFactor || this.lastWidth != width || this.lastHeight != height || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        this.setValues(blurStrength);
        this.framebuffer.bindFramebuffer(true);
        this.shaderGroup.loadShaderGroup(this.mc.timer.renderPartialTicks);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.enableAlpha();
    }

    public void blurhotbar(float x, float y, float areaWidth, float areaHeight, float blurStrength) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int scaleFactor = scaledResolution.getScaleFactor();
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        if (this.lastFactor != scaleFactor || this.lastWidth != width || this.lastHeight != height || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        GL11.glEnable((int)3089);
        this.prepareScissorBox(x, y + 1.0f, areaWidth, areaHeight - 1.0f);
        this.prepareScissorBox(x, y + 51.0f, areaWidth, areaHeight - 1.0f);
        this.framebuffer.bindFramebuffer(true);
        this.shaderGroup.loadShaderGroup(this.mc.timer.renderPartialTicks);
        this.setValues(blurStrength);
        this.mc.getFramebuffer().bindFramebuffer(false);
        GL11.glDisable((int)3089);
        GlStateManager.enableAlpha();
    }

    public final void blur2(float x, float y, float areaWidth, float areaHeight, float blurStrength) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        int scaleFactor = scaledResolution.getScaleFactor();
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        if (this.lastFactor != scaleFactor || this.lastWidth != width || this.lastHeight != height || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);
        this.prepareScissorBox(x, y + 1.0f, areaWidth, areaHeight - 1.0f);
        this.framebuffer.bindFramebuffer(true);
        this.shaderGroup.loadShaderGroup(this.mc.timer.renderPartialTicks);
        this.setValues(blurStrength);
        this.mc.getFramebuffer().bindFramebuffer(false);
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
        GlStateManager.enableAlpha();
    }

    public void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)factor)), (int)((int)(((float)scale.getScaledHeight() - y2) * (float)factor)), (int)((int)((x2 - x) * (float)factor)), (int)((int)((y2 - y) * (float)factor)));
    }
}

