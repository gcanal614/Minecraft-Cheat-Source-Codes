// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Shader;
import java.io.IOException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;

public class SkiddedBlurUtil
{
    private static final Minecraft mc;
    private final ResourceLocation resourceLocation;
    private ShaderGroup shaderGroup;
    private Framebuffer framebuffer;
    private int lastFactor;
    private int lastWidth;
    private int lastHeight;
    
    public SkiddedBlurUtil() {
        this.resourceLocation = new ResourceLocation("BozoWare/Shaders/blur.json");
    }
    
    public SkiddedBlurUtil(final ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
    
    public void init() {
        try {
            (this.shaderGroup = new ShaderGroup(SkiddedBlurUtil.mc.getTextureManager(), SkiddedBlurUtil.mc.getResourceManager(), SkiddedBlurUtil.mc.getFramebuffer(), this.resourceLocation)).createBindFramebuffers(SkiddedBlurUtil.mc.displayWidth, SkiddedBlurUtil.mc.displayHeight);
            this.framebuffer = this.shaderGroup.mainFramebuffer;
        }
        catch (JsonSyntaxException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    private void setValues(final int strength) {
        this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set((float)strength);
        this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set((float)strength);
        this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set((float)strength);
        this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set((float)strength);
    }
    
    public final void blur(final int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(SkiddedBlurUtil.mc, SkiddedBlurUtil.mc.displayWidth, SkiddedBlurUtil.mc.displayHeight);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();
        if (this.sizeHasChanged(scaleFactor, width, height) || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        this.setValues(blurStrength);
        this.framebuffer.bindFramebuffer(true);
        this.shaderGroup.loadShaderGroup(SkiddedBlurUtil.mc.timer.renderPartialTicks);
        SkiddedBlurUtil.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.enableAlpha();
    }
    
    public final void blur(final double x, final double y, final double areaWidth, final double areaHeight, final int blurStrength) {
        final ScaledResolution scaledResolution = new ScaledResolution(SkiddedBlurUtil.mc, SkiddedBlurUtil.mc.displayWidth, SkiddedBlurUtil.mc.displayHeight);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();
        if (this.sizeHasChanged(scaleFactor, width, height) || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        GL11.glEnable(3089);
        RenderUtil.scissor(x, y, areaWidth, areaHeight);
        this.framebuffer.bindFramebuffer(true);
        this.shaderGroup.loadShaderGroup(SkiddedBlurUtil.mc.timer.renderPartialTicks);
        this.setValues(blurStrength);
        SkiddedBlurUtil.mc.getFramebuffer().bindFramebuffer(false);
        GL11.glDisable(3089);
    }
    
    private boolean sizeHasChanged(final int scaleFactor, final int width, final int height) {
        return this.lastFactor != scaleFactor || this.lastWidth != width || this.lastHeight != height;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
