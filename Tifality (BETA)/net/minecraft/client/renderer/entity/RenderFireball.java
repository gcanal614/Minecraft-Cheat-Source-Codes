/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderFireball
extends Render<EntityFireball> {
    private float scale;

    public RenderFireball(RenderManager renderManagerIn, float scaleIn) {
        super(renderManagerIn);
        this.scale = scaleIn;
    }

    @Override
    public void doRender(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GL11.glPushMatrix();
        this.bindEntityTexture(entity);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GL11.glScalef(this.scale, this.scale, this.scale);
        TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMaxU();
        float f2 = textureatlassprite.getMinV();
        float f3 = textureatlassprite.getMaxV();
        float f4 = 1.0f;
        float f5 = 0.5f;
        float f6 = 0.25f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        worldrenderer.pos(-0.5, -0.25, 0.0).tex(f, f3).func_181663_c(0.0f, 1.0f, 0.0f).endVertex();
        worldrenderer.pos(0.5, -0.25, 0.0).tex(f1, f3).func_181663_c(0.0f, 1.0f, 0.0f).endVertex();
        worldrenderer.pos(0.5, 0.75, 0.0).tex(f1, f2).func_181663_c(0.0f, 1.0f, 0.0f).endVertex();
        worldrenderer.pos(-0.5, 0.75, 0.0).tex(f, f2).func_181663_c(0.0f, 1.0f, 0.0f).endVertex();
        tessellator.draw();
        GlStateManager.disableRescaleNormal();
        GL11.glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFireball entity) {
        return TextureMap.locationBlocksTexture;
    }
}

