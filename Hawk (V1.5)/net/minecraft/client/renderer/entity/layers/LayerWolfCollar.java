package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomColors;

public class LayerWolfCollar implements LayerRenderer {
   private static final String __OBFID = "CL_00002405";
   private final RenderWolf field_177146_b;
   private static final ResourceLocation field_177147_a = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.func_177145_a((EntityWolf)var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public boolean shouldCombineTextures() {
      return true;
   }

   public void func_177145_a(EntityWolf var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.isTamed() && !var1.isInvisible()) {
         this.field_177146_b.bindTexture(field_177147_a);
         EnumDyeColor var9 = EnumDyeColor.func_176764_b(var1.func_175546_cu().func_176765_a());
         float[] var10 = EntitySheep.func_175513_a(var9);
         if (Config.isCustomColors()) {
            var10 = CustomColors.getWolfCollarColors(var9, var10);
         }

         GlStateManager.color(var10[0], var10[1], var10[2]);
         this.field_177146_b.getMainModel().render(var1, var2, var3, var5, var6, var7, var8);
      }

   }

   public LayerWolfCollar(RenderWolf var1) {
      this.field_177146_b = var1;
   }
}
