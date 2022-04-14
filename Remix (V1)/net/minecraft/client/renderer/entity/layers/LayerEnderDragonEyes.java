package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.entity.*;

public class LayerEnderDragonEyes implements LayerRenderer
{
    private static final ResourceLocation TEXTURE;
    private final RenderDragon dragonRenderer;
    
    public LayerEnderDragonEyes(final RenderDragon p_i46118_1_) {
        this.dragonRenderer = p_i46118_1_;
    }
    
    public void func_177210_a(final EntityDragon p_177210_1_, final float p_177210_2_, final float p_177210_3_, final float p_177210_4_, final float p_177210_5_, final float p_177210_6_, final float p_177210_7_, final float p_177210_8_) {
        this.dragonRenderer.bindTexture(LayerEnderDragonEyes.TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.disableLighting();
        GlStateManager.depthFunc(514);
        final char var9 = '\uf0f0';
        final int var10 = var9 % 65536;
        final int var11 = var9 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0f, var11 / 1.0f);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
        }
        this.dragonRenderer.getMainModel().render(p_177210_1_, p_177210_2_, p_177210_3_, p_177210_5_, p_177210_6_, p_177210_7_, p_177210_8_);
        this.dragonRenderer.func_177105_a(p_177210_1_, p_177210_4_);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.depthFunc(515);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.func_177210_a((EntityDragon)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    }
}
