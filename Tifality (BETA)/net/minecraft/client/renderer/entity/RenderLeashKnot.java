/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLeashKnot
extends Render<EntityLeashKnot> {
    private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
    private ModelLeashKnot leashKnotModel = new ModelLeashKnot();

    public RenderLeashKnot(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(EntityLeashKnot entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GL11.glPushMatrix();
        GlStateManager.disableCull();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        float f = 0.0625f;
        GlStateManager.enableRescaleNormal();
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);
        this.leashKnotModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f);
        GL11.glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLeashKnot entity) {
        return leashKnotTextures;
    }
}

