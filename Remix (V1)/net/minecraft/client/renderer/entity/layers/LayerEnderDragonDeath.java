package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.boss.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerEnderDragonDeath implements LayerRenderer
{
    public void func_177213_a(final EntityDragon p_177213_1_, final float p_177213_2_, final float p_177213_3_, final float p_177213_4_, final float p_177213_5_, final float p_177213_6_, final float p_177213_7_, final float p_177213_8_) {
        if (p_177213_1_.deathTicks > 0) {
            final Tessellator var9 = Tessellator.getInstance();
            final WorldRenderer var10 = var9.getWorldRenderer();
            RenderHelper.disableStandardItemLighting();
            final float var11 = (p_177213_1_.deathTicks + p_177213_4_) / 200.0f;
            float var12 = 0.0f;
            if (var11 > 0.8f) {
                var12 = (var11 - 0.8f) / 0.2f;
            }
            final Random var13 = new Random(432L);
            GlStateManager.func_179090_x();
            GlStateManager.shadeModel(7425);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 1);
            GlStateManager.disableAlpha();
            GlStateManager.enableCull();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, -1.0f, -2.0f);
            for (int var14 = 0; var14 < (var11 + var11 * var11) / 2.0f * 60.0f; ++var14) {
                GlStateManager.rotate(var13.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(var13.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(var13.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(var13.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(var13.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(var13.nextFloat() * 360.0f + var11 * 90.0f, 0.0f, 0.0f, 1.0f);
                var10.startDrawing(6);
                final float var15 = var13.nextFloat() * 20.0f + 5.0f + var12 * 10.0f;
                final float var16 = var13.nextFloat() * 2.0f + 1.0f + var12 * 2.0f;
                var10.func_178974_a(16777215, (int)(255.0f * (1.0f - var12)));
                var10.addVertex(0.0, 0.0, 0.0);
                var10.func_178974_a(16711935, 0);
                var10.addVertex(-0.866 * var16, var15, -0.5f * var16);
                var10.addVertex(0.866 * var16, var15, -0.5f * var16);
                var10.addVertex(0.0, var15, 1.0f * var16);
                var10.addVertex(-0.866 * var16, var15, -0.5f * var16);
                var9.draw();
            }
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.shadeModel(7424);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179098_w();
            GlStateManager.enableAlpha();
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.func_177213_a((EntityDragon)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}
