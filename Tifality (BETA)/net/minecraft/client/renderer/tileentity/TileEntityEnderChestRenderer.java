/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityEnderChestRenderer
extends TileEntitySpecialRenderer<TileEntityEnderChest> {
    private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
    private ModelChest field_147521_c = new ModelChest();

    @Override
    public void renderTileEntityAt(TileEntityEnderChest te, double x, double y, double z, float partialTicks, int destroyStage) {
        int i = 0;
        if (te.hasWorldObj()) {
            i = te.getBlockMetadata();
        }
        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glScalef(4.0f, 4.0f, 1.0f);
            GL11.glTranslatef(0.0625f, 0.0625f, 0.0625f);
            GL11.glMatrixMode(5888);
        } else {
            this.bindTexture(ENDER_CHEST_TEXTURE);
        }
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float)x, (float)y + 1.0f, (float)z + 1.0f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        int j = 0;
        if (i == 2) {
            j = 180;
        }
        if (i == 3) {
            j = 0;
        }
        if (i == 4) {
            j = 90;
        }
        if (i == 5) {
            j = -90;
        }
        GL11.glRotatef(j, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
        f = 1.0f - f;
        f = 1.0f - f * f * f;
        this.field_147521_c.chestLid.rotateAngleX = -(f * (float)Math.PI / 2.0f);
        this.field_147521_c.renderAll();
        GlStateManager.disableRescaleNormal();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (destroyStage >= 0) {
            GL11.glMatrixMode(5890);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
    }
}

