package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomColors;

public class RenderXPOrb extends Render {
   private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
   private static final String __OBFID = "CL_00000993";

   public void doRender(EntityXPOrb var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
      this.bindEntityTexture(var1);
      int var10 = var1.getTextureByXP();
      float var11 = (float)(var10 % 4 * 16) / 64.0F;
      float var12 = (float)(var10 % 4 * 16 + 16) / 64.0F;
      float var13 = (float)(var10 / 4 * 16) / 64.0F;
      float var14 = (float)(var10 / 4 * 16 + 16) / 64.0F;
      float var15 = 1.0F;
      float var16 = 0.5F;
      float var17 = 0.25F;
      int var18 = var1.getBrightnessForRender(var9);
      int var19 = var18 % 65536;
      int var20 = var18 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var19 / 1.0F, (float)var20 / 1.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float var21 = 255.0F;
      float var22 = ((float)var1.xpColor + var9) / 2.0F;
      var20 = (int)((MathHelper.sin(var22 + 0.0F) + 1.0F) * 0.5F * var21);
      int var23 = (int)var21;
      int var24 = (int)((MathHelper.sin(var22 + 4.1887903F) + 1.0F) * 0.1F * var21);
      int var25 = var20 << 16 | var23 << 8 | var24;
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      float var26 = 0.3F;
      GlStateManager.scale(var26, var26, var26);
      Tessellator var27 = Tessellator.getInstance();
      WorldRenderer var28 = var27.getWorldRenderer();
      var28.startDrawingQuads();
      if (Config.isCustomColors()) {
         int var29 = CustomColors.getXpOrbColor(var21);
         if (var29 >= 0) {
            var25 = var29;
         }
      }

      var28.func_178974_a(var25, 128);
      var28.func_178980_d(0.0F, 1.0F, 0.0F);
      var28.addVertexWithUV((double)(0.0F - var16), (double)(0.0F - var17), 0.0D, (double)var11, (double)var14);
      var28.addVertexWithUV((double)(var15 - var16), (double)(0.0F - var17), 0.0D, (double)var12, (double)var14);
      var28.addVertexWithUV((double)(var15 - var16), (double)(1.0F - var17), 0.0D, (double)var12, (double)var13);
      var28.addVertexWithUV((double)(0.0F - var16), (double)(1.0F - var17), 0.0D, (double)var11, (double)var13);
      var27.draw();
      GlStateManager.disableBlend();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getTexture((EntityXPOrb)var1);
   }

   protected ResourceLocation getTexture(EntityXPOrb var1) {
      return experienceOrbTextures;
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityXPOrb)var1, var2, var4, var6, var8, var9);
   }

   public RenderXPOrb(RenderManager var1) {
      super(var1);
      this.shadowSize = 0.15F;
      this.shadowOpaque = 0.75F;
   }
}
