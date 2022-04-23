/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBoat
extends Render<EntityBoat> {
    private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
    protected ModelBase modelBoat = new ModelBoat();

    public RenderBoat(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5f;
    }

    @Override
    public void doRender(EntityBoat entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 0.25f, (float)z);
        GL11.glRotatef(180.0f - entityYaw, 0.0f, 1.0f, 0.0f);
        float f = (float)entity.getTimeSinceHit() - partialTicks;
        float f1 = entity.getDamageTaken() - partialTicks;
        if (f1 < 0.0f) {
            f1 = 0.0f;
        }
        if (f > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f) * f * f1 / 10.0f * (float)entity.getForwardDirection(), 1.0f, 0.0f, 0.0f);
        }
        float f2 = 0.75f;
        GL11.glScalef(f2, f2, f2);
        GL11.glScalef(1.0f / f2, 1.0f / f2, 1.0f / f2);
        this.bindEntityTexture(entity);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelBoat.render(entity, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBoat entity) {
        return boatTextures;
    }
}

