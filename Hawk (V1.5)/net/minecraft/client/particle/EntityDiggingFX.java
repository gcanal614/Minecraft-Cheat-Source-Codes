package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFX extends EntityFX {
   private static final String __OBFID = "CL_00000932";
   private IBlockState field_174847_a;

   public EntityDiggingFX func_174845_l() {
      Block var1 = this.field_174847_a.getBlock();
      if (var1 == Blocks.grass) {
         return this;
      } else {
         int var2 = var1.getRenderColor(this.field_174847_a);
         this.particleRed *= (float)(var2 >> 16 & 255) / 255.0F;
         this.particleGreen *= (float)(var2 >> 8 & 255) / 255.0F;
         this.particleBlue *= (float)(var2 & 255) / 255.0F;
         return this;
      }
   }

   public int getFXLayer() {
      return 1;
   }

   public EntityDiggingFX func_174846_a(BlockPos var1) {
      if (this.field_174847_a.getBlock() == Blocks.grass) {
         return this;
      } else {
         int var2 = this.field_174847_a.getBlock().colorMultiplier(this.worldObj, var1);
         this.particleRed *= (float)(var2 >> 16 & 255) / 255.0F;
         this.particleGreen *= (float)(var2 >> 8 & 255) / 255.0F;
         this.particleBlue *= (float)(var2 & 255) / 255.0F;
         return this;
      }
   }

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = ((float)this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
      float var10 = var9 + 0.015609375F;
      float var11 = ((float)this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
      float var12 = var11 + 0.015609375F;
      float var13 = 0.1F * this.particleScale;
      if (this.particleIcon != null) {
         var9 = this.particleIcon.getInterpolatedU((double)(this.particleTextureJitterX / 4.0F * 16.0F));
         var10 = this.particleIcon.getInterpolatedU((double)((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
         var11 = this.particleIcon.getInterpolatedV((double)(this.particleTextureJitterY / 4.0F * 16.0F));
         var12 = this.particleIcon.getInterpolatedV((double)((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
      }

      float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var3 - interpPosX);
      float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var3 - interpPosY);
      float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var3 - interpPosZ);
      var1.func_178986_b(this.particleRed, this.particleGreen, this.particleBlue);
      var1.addVertexWithUV((double)(var14 - var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 - var6 * var13 - var8 * var13), (double)var9, (double)var12);
      var1.addVertexWithUV((double)(var14 - var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 - var6 * var13 + var8 * var13), (double)var9, (double)var11);
      var1.addVertexWithUV((double)(var14 + var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 + var6 * var13 + var8 * var13), (double)var10, (double)var11);
      var1.addVertexWithUV((double)(var14 + var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 + var6 * var13 - var8 * var13), (double)var10, (double)var12);
   }

   protected EntityDiggingFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, IBlockState var14) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.field_174847_a = var14;
      this.func_180435_a(Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178122_a(var14));
      this.particleGravity = var14.getBlock().blockParticleGravity;
      this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
      this.particleScale /= 2.0F;
   }

   public static class Factory implements IParticleFactory {
      private static final String __OBFID = "CL_00002575";

      public EntityFX func_178902_a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return (new EntityDiggingFX(var2, var3, var5, var7, var9, var11, var13, Block.getStateById(var15[0]))).func_174845_l();
      }
   }
}
