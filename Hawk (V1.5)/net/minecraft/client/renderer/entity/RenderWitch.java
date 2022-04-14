package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.ResourceLocation;

public class RenderWitch extends RenderLiving {
   private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");
   private static final String __OBFID = "CL_00001033";

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180590_a((EntityWitch)var1, var2, var4, var6, var8, var9);
   }

   public void func_180590_a(EntityWitch var1, double var2, double var4, double var6, float var8, float var9) {
      ((ModelWitch)this.mainModel).field_82900_g = var1.getHeldItem() != null;
      super.doRender((EntityLiving)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180589_a((EntityWitch)var1);
   }

   public void doRender(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180590_a((EntityWitch)var1, var2, var4, var6, var8, var9);
   }

   protected void preRenderCallback(EntityWitch var1, float var2) {
      float var3 = 0.9375F;
      GlStateManager.scale(var3, var3, var3);
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180590_a((EntityWitch)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation func_180589_a(EntityWitch var1) {
      return witchTextures;
   }

   public void func_82422_c() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(EntityLivingBase var1, float var2) {
      this.preRenderCallback((EntityWitch)var1, var2);
   }

   public RenderWitch(RenderManager var1) {
      super(var1, new ModelWitch(0.0F), 0.5F);
      this.addLayer(new LayerHeldItemWitch(this));
   }
}
