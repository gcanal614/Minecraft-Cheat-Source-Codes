/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEnderCrystal
extends Render<EntityEnderCrystal> {
    private static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    private ModelBase modelEnderCrystal = new ModelEnderCrystal(0.0f, true);

    public RenderEnderCrystal(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5f;
    }

    @Override
    public void doRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks) {
        float f = (float)entity.innerRotation + partialTicks;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        this.bindTexture(enderCrystalTextures);
        float f1 = MathHelper.sin(f * 0.2f) / 2.0f + 0.5f;
        f1 = f1 * f1 + f1;
        this.modelEnderCrystal.render(entity, 0.0f, f * 3.0f, f1 * 0.2f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityEnderCrystal entity) {
        return enderCrystalTextures;
    }
}

