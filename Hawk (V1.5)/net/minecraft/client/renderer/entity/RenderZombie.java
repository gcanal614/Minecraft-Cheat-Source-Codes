package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderZombie extends RenderBiped {
   private static final String __OBFID = "CL_00001037";
   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
   private final List field_177122_o;
   private final ModelZombieVillager zombieVillagerModel;
   private final List field_177121_n;
   private final ModelBiped field_82434_o;
   private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");

   private void func_82427_a(EntityZombie var1) {
      if (var1.isVillager()) {
         this.mainModel = this.zombieVillagerModel;
         this.field_177097_h = this.field_177121_n;
      } else {
         this.mainModel = this.field_82434_o;
         this.field_177097_h = this.field_177122_o;
      }

      this.modelBipedMain = (ModelBiped)this.mainModel;
   }

   public void doRender(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180579_a((EntityZombie)var1, var2, var4, var6, var8, var9);
   }

   protected void rotateCorpse(EntityLivingBase var1, float var2, float var3, float var4) {
      this.rotateCorpse((EntityZombie)var1, var2, var3, var4);
   }

   protected ResourceLocation getEntityTexture(EntityLiving var1) {
      return this.func_180578_a((EntityZombie)var1);
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180579_a((EntityZombie)var1, var2, var4, var6, var8, var9);
   }

   protected void rotateCorpse(EntityZombie var1, float var2, float var3, float var4) {
      if (var1.isConverting()) {
         var3 += (float)(Math.cos((double)var1.ticksExisted * 3.25D) * 3.141592653589793D * 0.25D);
      }

      super.rotateCorpse(var1, var2, var3, var4);
   }

   public RenderZombie(RenderManager var1) {
      super(var1, new ModelZombie(), 0.5F, 1.0F);
      LayerRenderer var2 = (LayerRenderer)this.field_177097_h.get(0);
      this.field_82434_o = this.modelBipedMain;
      this.zombieVillagerModel = new ModelZombieVillager();
      this.addLayer(new LayerHeldItem(this));
      LayerBipedArmor var3 = new LayerBipedArmor(this, this) {
         private static final String __OBFID = "CL_00002429";
         final RenderZombie this$0;

         protected void func_177177_a() {
            this.field_177189_c = new ModelZombie(0.5F, true);
            this.field_177186_d = new ModelZombie(1.0F, true);
         }

         {
            this.this$0 = var1;
         }
      };
      this.addLayer(var3);
      this.field_177122_o = Lists.newArrayList(this.field_177097_h);
      if (var2 instanceof LayerCustomHead) {
         this.func_177089_b(var2);
         this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
      }

      this.func_177089_b(var3);
      this.addLayer(new LayerVillagerArmor(this));
      this.field_177121_n = Lists.newArrayList(this.field_177097_h);
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180579_a((EntityZombie)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180578_a((EntityZombie)var1);
   }

   protected ResourceLocation func_180578_a(EntityZombie var1) {
      return var1.isVillager() ? zombieVillagerTextures : zombieTextures;
   }

   public void func_180579_a(EntityZombie var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_82427_a(var1);
      super.doRender(var1, var2, var4, var6, var8, var9);
   }
}
