package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;

public class ModelDragon extends ModelBase {
   private static final String __OBFID = "CL_00000870";
   private ModelRenderer head;
   private ModelRenderer rearFoot;
   private ModelRenderer spine;
   private ModelRenderer rearLeg;
   private ModelRenderer jaw;
   private float partialTicks;
   private ModelRenderer wing;
   private ModelRenderer rearLegTip;
   private ModelRenderer frontLegTip;
   private ModelRenderer body;
   private ModelRenderer wingTip;
   private ModelRenderer frontFoot;
   private ModelRenderer frontLeg;

   private float updateRotations(double var1) {
      while(var1 >= 180.0D) {
         var1 -= 360.0D;
      }

      while(var1 < -180.0D) {
         var1 += 360.0D;
      }

      return (float)var1;
   }

   public ModelDragon(float var1) {
      this.textureWidth = 256;
      this.textureHeight = 256;
      this.setTextureOffset("body.body", 0, 0);
      this.setTextureOffset("wing.skin", -56, 88);
      this.setTextureOffset("wingtip.skin", -56, 144);
      this.setTextureOffset("rearleg.main", 0, 0);
      this.setTextureOffset("rearfoot.main", 112, 0);
      this.setTextureOffset("rearlegtip.main", 196, 0);
      this.setTextureOffset("head.upperhead", 112, 30);
      this.setTextureOffset("wing.bone", 112, 88);
      this.setTextureOffset("head.upperlip", 176, 44);
      this.setTextureOffset("jaw.jaw", 176, 65);
      this.setTextureOffset("frontleg.main", 112, 104);
      this.setTextureOffset("wingtip.bone", 112, 136);
      this.setTextureOffset("frontfoot.main", 144, 104);
      this.setTextureOffset("neck.box", 192, 104);
      this.setTextureOffset("frontlegtip.main", 226, 138);
      this.setTextureOffset("body.scale", 220, 53);
      this.setTextureOffset("head.scale", 0, 0);
      this.setTextureOffset("neck.scale", 48, 0);
      this.setTextureOffset("head.nostril", 112, 0);
      float var2 = -16.0F;
      this.head = new ModelRenderer(this, "head");
      this.head.addBox("upperlip", -6.0F, -1.0F, -8.0F + var2, 12, 5, 16);
      this.head.addBox("upperhead", -8.0F, -8.0F, 6.0F + var2, 16, 16, 16);
      this.head.mirror = true;
      this.head.addBox("scale", -5.0F, -12.0F, 12.0F + var2, 2, 4, 6);
      this.head.addBox("nostril", -5.0F, -3.0F, -6.0F + var2, 2, 2, 4);
      this.head.mirror = false;
      this.head.addBox("scale", 3.0F, -12.0F, 12.0F + var2, 2, 4, 6);
      this.head.addBox("nostril", 3.0F, -3.0F, -6.0F + var2, 2, 2, 4);
      this.jaw = new ModelRenderer(this, "jaw");
      this.jaw.setRotationPoint(0.0F, 4.0F, 8.0F + var2);
      this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.head.addChild(this.jaw);
      this.spine = new ModelRenderer(this, "neck");
      this.spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
      this.body = new ModelRenderer(this, "body");
      this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
      this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
      this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
      this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
      this.wing = new ModelRenderer(this, "wing");
      this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
      this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wingTip = new ModelRenderer(this, "wingtip");
      this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
      this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.wing.addChild(this.wingTip);
      this.frontLeg = new ModelRenderer(this, "frontleg");
      this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
      this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
      this.frontLegTip = new ModelRenderer(this, "frontlegtip");
      this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
      this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
      this.frontLeg.addChild(this.frontLegTip);
      this.frontFoot = new ModelRenderer(this, "frontfoot");
      this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
      this.frontLegTip.addChild(this.frontFoot);
      this.rearLeg = new ModelRenderer(this, "rearleg");
      this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
      this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
      this.rearLegTip = new ModelRenderer(this, "rearlegtip");
      this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
      this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
      this.rearLeg.addChild(this.rearLegTip);
      this.rearFoot = new ModelRenderer(this, "rearfoot");
      this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
      this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
      this.rearLegTip.addChild(this.rearFoot);
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.pushMatrix();
      EntityDragon var8 = (EntityDragon)var1;
      float var9 = var8.prevAnimTime + (var8.animTime - var8.prevAnimTime) * this.partialTicks;
      this.jaw.rotateAngleX = (float)(Math.sin((double)(var9 * 3.1415927F * 2.0F)) + 1.0D) * 0.2F;
      float var10 = (float)(Math.sin((double)(var9 * 3.1415927F * 2.0F - 1.0F)) + 1.0D);
      var10 = (var10 * var10 * 1.0F + var10 * 2.0F) * 0.05F;
      GlStateManager.translate(0.0F, var10 - 2.0F, -3.0F);
      GlStateManager.rotate(var10 * 2.0F, 1.0F, 0.0F, 0.0F);
      float var11 = -30.0F;
      float var12 = 0.0F;
      float var13 = 1.5F;
      double[] var14 = var8.getMovementOffsets(6, this.partialTicks);
      float var15 = this.updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] - var8.getMovementOffsets(10, this.partialTicks)[0]);
      float var16 = this.updateRotations(var8.getMovementOffsets(5, this.partialTicks)[0] + (double)(var15 / 2.0F));
      var11 += 2.0F;
      float var17 = var9 * 3.1415927F * 2.0F;
      var11 = 20.0F;
      float var18 = -12.0F;

      float var19;
      for(int var20 = 0; var20 < 5; ++var20) {
         double[] var21 = var8.getMovementOffsets(5 - var20, this.partialTicks);
         var19 = (float)Math.cos((double)((float)var20 * 0.45F + var17)) * 0.15F;
         this.spine.rotateAngleY = this.updateRotations(var21[0] - var14[0]) * 3.1415927F / 180.0F * var13;
         this.spine.rotateAngleX = var19 + (float)(var21[1] - var14[1]) * 3.1415927F / 180.0F * var13 * 5.0F;
         this.spine.rotateAngleZ = -this.updateRotations(var21[0] - (double)var16) * 3.1415927F / 180.0F * var13;
         this.spine.rotationPointY = var11;
         this.spine.rotationPointZ = var18;
         this.spine.rotationPointX = var12;
         var11 = (float)((double)var11 + Math.sin((double)this.spine.rotateAngleX) * 10.0D);
         var18 = (float)((double)var18 - Math.cos((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
         var12 = (float)((double)var12 - Math.sin((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
         this.spine.render(var7);
      }

      this.head.rotationPointY = var11;
      this.head.rotationPointZ = var18;
      this.head.rotationPointX = var12;
      double[] var23 = var8.getMovementOffsets(0, this.partialTicks);
      this.head.rotateAngleY = this.updateRotations(var23[0] - var14[0]) * 3.1415927F / 180.0F * 1.0F;
      this.head.rotateAngleZ = -this.updateRotations(var23[0] - (double)var16) * 3.1415927F / 180.0F * 1.0F;
      this.head.render(var7);
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-var15 * var13 * 1.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      this.body.rotateAngleZ = 0.0F;
      this.body.render(var7);

      for(int var24 = 0; var24 < 2; ++var24) {
         GlStateManager.enableCull();
         var19 = var9 * 3.1415927F * 2.0F;
         this.wing.rotateAngleX = 0.125F - (float)Math.cos((double)var19) * 0.2F;
         this.wing.rotateAngleY = 0.25F;
         this.wing.rotateAngleZ = (float)(Math.sin((double)var19) + 0.125D) * 0.8F;
         this.wingTip.rotateAngleZ = -((float)(Math.sin((double)(var19 + 2.0F)) + 0.5D)) * 0.75F;
         this.rearLeg.rotateAngleX = 1.0F + var10 * 0.1F;
         this.rearLegTip.rotateAngleX = 0.5F + var10 * 0.1F;
         this.rearFoot.rotateAngleX = 0.75F + var10 * 0.1F;
         this.frontLeg.rotateAngleX = 1.3F + var10 * 0.1F;
         this.frontLegTip.rotateAngleX = -0.5F - var10 * 0.1F;
         this.frontFoot.rotateAngleX = 0.75F + var10 * 0.1F;
         this.wing.render(var7);
         this.frontLeg.render(var7);
         this.rearLeg.render(var7);
         GlStateManager.scale(-1.0F, 1.0F, 1.0F);
         if (var24 == 0) {
            GlStateManager.cullFace(1028);
         }
      }

      GlStateManager.popMatrix();
      GlStateManager.cullFace(1029);
      GlStateManager.disableCull();
      float var25 = -((float)Math.sin((double)(var9 * 3.1415927F * 2.0F))) * 0.0F;
      var17 = var9 * 3.1415927F * 2.0F;
      var11 = 10.0F;
      var18 = 60.0F;
      var12 = 0.0F;
      var14 = var8.getMovementOffsets(11, this.partialTicks);

      for(int var22 = 0; var22 < 12; ++var22) {
         var23 = var8.getMovementOffsets(12 + var22, this.partialTicks);
         var25 = (float)((double)var25 + Math.sin((double)((float)var22 * 0.45F + var17)) * 0.05000000074505806D);
         this.spine.rotateAngleY = (this.updateRotations(var23[0] - var14[0]) * var13 + 180.0F) * 3.1415927F / 180.0F;
         this.spine.rotateAngleX = var25 + (float)(var23[1] - var14[1]) * 3.1415927F / 180.0F * var13 * 5.0F;
         this.spine.rotateAngleZ = this.updateRotations(var23[0] - (double)var16) * 3.1415927F / 180.0F * var13;
         this.spine.rotationPointY = var11;
         this.spine.rotationPointZ = var18;
         this.spine.rotationPointX = var12;
         var11 = (float)((double)var11 + Math.sin((double)this.spine.rotateAngleX) * 10.0D);
         var18 = (float)((double)var18 - Math.cos((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
         var12 = (float)((double)var12 - Math.sin((double)this.spine.rotateAngleY) * Math.cos((double)this.spine.rotateAngleX) * 10.0D);
         this.spine.render(var7);
      }

      GlStateManager.popMatrix();
   }

   public void setLivingAnimations(EntityLivingBase var1, float var2, float var3, float var4) {
      this.partialTicks = var4;
   }
}
