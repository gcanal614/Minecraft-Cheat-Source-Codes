/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import shadersmod.client.Shaders;

public class LayerSpiderEyes
implements LayerRenderer {
    private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
    private final RenderSpider spiderRenderer;

    public LayerSpiderEyes(RenderSpider spiderRendererIn) {
        this.spiderRenderer = spiderRendererIn;
    }

    public void doRenderLayer(EntitySpider entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        this.spiderRenderer.bindTexture(SPIDER_EYES);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        if (entitylivingbaseIn.isInvisible()) {
            GlStateManager.depthMask(false);
        } else {
            GlStateManager.depthMask(true);
        }
        int c0 = 61680;
        int i2 = c0 % 65536;
        int j2 = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i2 / 1.0f, (float)j2 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
        }
        this.spiderRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        int k2 = entitylivingbaseIn.getBrightnessForRender(partialTicks);
        i2 = k2 % 65536;
        j2 = k2 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i2 / 1.0f, (float)j2 / 1.0f);
        this.spiderRenderer.func_177105_a(entitylivingbaseIn, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        this.doRenderLayer((EntitySpider)entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale);
    }
}

