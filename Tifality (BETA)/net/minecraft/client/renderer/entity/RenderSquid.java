/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSquid
extends RenderLiving<EntitySquid> {
    private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");

    public RenderSquid(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySquid entity) {
        return squidTextures;
    }

    @Override
    protected void rotateCorpse(EntitySquid bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
        float f = bat.prevSquidPitch + (bat.squidPitch - bat.prevSquidPitch) * partialTicks;
        float f1 = bat.prevSquidYaw + (bat.squidYaw - bat.prevSquidYaw) * partialTicks;
        GL11.glTranslatef(0.0f, 0.5f, 0.0f);
        GL11.glRotatef(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(f1, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, -1.2f, 0.0f);
    }

    @Override
    protected float handleRotationFloat(EntitySquid livingBase, float partialTicks) {
        return livingBase.lastTentacleAngle + (livingBase.tentacleAngle - livingBase.lastTentacleAngle) * partialTicks;
    }
}

