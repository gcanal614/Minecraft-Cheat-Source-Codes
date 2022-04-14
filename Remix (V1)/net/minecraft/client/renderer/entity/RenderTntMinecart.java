package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class RenderTntMinecart extends RenderMinecart
{
    public RenderTntMinecart(final RenderManager p_i46135_1_) {
        super(p_i46135_1_);
    }
    
    protected void func_180561_a(final EntityMinecartTNT p_180561_1_, final float p_180561_2_, final IBlockState p_180561_3_) {
        final int var4 = p_180561_1_.func_94104_d();
        if (var4 > -1 && var4 - p_180561_2_ + 1.0f < 10.0f) {
            float var5 = 1.0f - (var4 - p_180561_2_ + 1.0f) / 10.0f;
            var5 = MathHelper.clamp_float(var5, 0.0f, 1.0f);
            var5 *= var5;
            var5 *= var5;
            final float var6 = 1.0f + var5 * 0.3f;
            GlStateManager.scale(var6, var6, var6);
        }
        super.func_180560_a(p_180561_1_, p_180561_2_, p_180561_3_);
        if (var4 > -1 && var4 / 5 % 2 == 0) {
            final BlockRendererDispatcher var7 = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.func_179090_x();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 772);
            GlStateManager.color(1.0f, 1.0f, 1.0f, (1.0f - (var4 - p_180561_2_ + 1.0f) / 100.0f) * 0.8f);
            GlStateManager.pushMatrix();
            var7.func_175016_a(Blocks.tnt.getDefaultState(), 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.func_179098_w();
        }
    }
    
    @Override
    protected void func_180560_a(final EntityMinecart p_180560_1_, final float p_180560_2_, final IBlockState p_180560_3_) {
        this.func_180561_a((EntityMinecartTNT)p_180560_1_, p_180560_2_, p_180560_3_);
    }
}
