/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityEnchantmentTableRenderer
extends TileEntitySpecialRenderer<TileEntityEnchantmentTable> {
    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private ModelBook field_147541_c = new ModelBook();

    @Override
    public void renderTileEntityAt(TileEntityEnchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage) {
        float f1;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5f, (float)y + 0.75f, (float)z + 0.5f);
        float f = (float)te.tickCount + partialTicks;
        GL11.glTranslatef(0.0f, 0.1f + MathHelper.sin(f * 0.1f) * 0.01f, 0.0f);
        for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float)Math.PI; f1 -= (float)Math.PI * 2) {
        }
        while (f1 < (float)(-Math.PI)) {
            f1 += (float)Math.PI * 2;
        }
        float f2 = te.bookRotationPrev + f1 * partialTicks;
        GL11.glRotatef(-f2 * 180.0f / (float)Math.PI, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(80.0f, 0.0f, 0.0f, 1.0f);
        this.bindTexture(TEXTURE_BOOK);
        float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25f;
        float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75f;
        f3 = (f3 - (float)MathHelper.truncateDoubleToInt(f3)) * 1.6f - 0.3f;
        f4 = (f4 - (float)MathHelper.truncateDoubleToInt(f4)) * 1.6f - 0.3f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
        GlStateManager.enableCull();
        this.field_147541_c.render(null, f, f3, f4, f5, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
}

