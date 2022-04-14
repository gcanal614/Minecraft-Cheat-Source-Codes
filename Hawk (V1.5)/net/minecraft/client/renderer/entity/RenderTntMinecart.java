package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class RenderTntMinecart extends RenderMinecart {
   private static final String __OBFID = "CL_00001029";

   protected void func_180560_a(EntityMinecart var1, float var2, IBlockState var3) {
      this.func_180561_a((EntityMinecartTNT)var1, var2, var3);
   }

   protected void func_180561_a(EntityMinecartTNT var1, float var2, IBlockState var3) {
      int var4 = var1.func_94104_d();
      if (var4 > -1 && (float)var4 - var2 + 1.0F < 10.0F) {
         float var5 = 1.0F - ((float)var4 - var2 + 1.0F) / 10.0F;
         var5 = MathHelper.clamp_float(var5, 0.0F, 1.0F);
         var5 *= var5;
         var5 *= var5;
         float var6 = 1.0F + var5 * 0.3F;
         GlStateManager.scale(var6, var6, var6);
      }

      super.func_180560_a(var1, var2, var3);
      if (var4 > -1 && var4 / 5 % 2 == 0) {
         BlockRendererDispatcher var7 = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.func_179090_x();
         GlStateManager.disableLighting();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 772);
         GlStateManager.color(1.0F, 1.0F, 1.0F, (1.0F - ((float)var4 - var2 + 1.0F) / 100.0F) * 0.8F);
         GlStateManager.pushMatrix();
         var7.func_175016_a(Blocks.tnt.getDefaultState(), 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
         GlStateManager.enableLighting();
         GlStateManager.func_179098_w();
      }

   }

   public RenderTntMinecart(RenderManager var1) {
      super(var1);
   }
}
