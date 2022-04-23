/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderMinecart<T extends EntityMinecart>
extends Render<T> {
    private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
    protected ModelBase modelMinecart = new ModelMinecart();

    public RenderMinecart(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5f;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GL11.glPushMatrix();
        this.bindEntityTexture(entity);
        long i = (long)((Entity)entity).getEntityId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f = (((float)(i >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float f1 = (((float)(i >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float f2 = (((float)(i >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        GL11.glTranslatef(f, f1, f2);
        double d0 = ((EntityMinecart)entity).lastTickPosX + (((EntityMinecart)entity).posX - ((EntityMinecart)entity).lastTickPosX) * (double)partialTicks;
        double d1 = ((EntityMinecart)entity).lastTickPosY + (((EntityMinecart)entity).posY - ((EntityMinecart)entity).lastTickPosY) * (double)partialTicks;
        double d2 = ((EntityMinecart)entity).lastTickPosZ + (((EntityMinecart)entity).posZ - ((EntityMinecart)entity).lastTickPosZ) * (double)partialTicks;
        double d3 = 0.3f;
        Vec3 vec3 = ((EntityMinecart)entity).func_70489_a(d0, d1, d2);
        float f3 = ((EntityMinecart)entity).prevRotationPitch + (((EntityMinecart)entity).rotationPitch - ((EntityMinecart)entity).prevRotationPitch) * partialTicks;
        if (vec3 != null) {
            Vec3 vec31 = ((EntityMinecart)entity).func_70495_a(d0, d1, d2, d3);
            Vec3 vec32 = ((EntityMinecart)entity).func_70495_a(d0, d1, d2, -d3);
            if (vec31 == null) {
                vec31 = vec3;
            }
            if (vec32 == null) {
                vec32 = vec3;
            }
            x += vec3.xCoord - d0;
            y += (vec31.yCoord + vec32.yCoord) / 2.0 - d1;
            z += vec3.zCoord - d2;
            Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);
            if (vec33.lengthVector() != 0.0) {
                vec33 = vec33.normalize();
                entityYaw = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0 / Math.PI);
                f3 = (float)(Math.atan(vec33.yCoord) * 73.0);
            }
        }
        GL11.glTranslatef((float)x, (float)y + 0.375f, (float)z);
        GL11.glRotatef(180.0f - entityYaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-f3, 0.0f, 0.0f, 1.0f);
        float f5 = (float)((EntityMinecart)entity).getRollingAmplitude() - partialTicks;
        float f6 = ((EntityMinecart)entity).getDamage() - partialTicks;
        if (f6 < 0.0f) {
            f6 = 0.0f;
        }
        if (f5 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f5) * f5 * f6 / 10.0f * (float)((EntityMinecart)entity).getRollingDirection(), 1.0f, 0.0f, 0.0f);
        }
        int j = ((EntityMinecart)entity).getDisplayTileOffset();
        IBlockState iblockstate = ((EntityMinecart)entity).getDisplayTile();
        if (iblockstate.getBlock().getRenderType() != -1) {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f4 = 0.75f;
            GL11.glScalef(f4, f4, f4);
            GL11.glTranslatef(-0.5f, (float)(j - 8) / 16.0f, 0.5f);
            this.func_180560_a(entity, partialTicks, iblockstate);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindEntityTexture(entity);
        }
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelMinecart.render((Entity)entity, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return minecartTextures;
    }

    protected void func_180560_a(T minecart, float partialTicks, IBlockState state) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(state, ((Entity)minecart).getBrightness(partialTicks));
        GL11.glPopMatrix();
    }
}

