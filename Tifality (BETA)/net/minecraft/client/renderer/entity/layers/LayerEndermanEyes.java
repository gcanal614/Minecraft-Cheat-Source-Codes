/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class LayerEndermanEyes
implements LayerRenderer<EntityEnderman> {
    private static final ResourceLocation field_177203_a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
    private final RenderEnderman endermanRenderer;

    public LayerEndermanEyes(RenderEnderman endermanRendererIn) {
        this.endermanRenderer = endermanRendererIn;
    }

    @Override
    public void doRenderLayer(EntityEnderman entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        this.endermanRenderer.bindTexture(field_177203_a);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0f, (float)k / 1.0f);
        GlStateManager.enableLighting();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
        }
        Config.getRenderGlobal().renderOverlayEyes = true;
        this.endermanRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        Config.getRenderGlobal().renderOverlayEyes = false;
        if (Config.isShaders()) {
            Shaders.endSpiderEyes();
        }
        this.endermanRenderer.func_177105_a(entitylivingbaseIn, partialTicks);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

