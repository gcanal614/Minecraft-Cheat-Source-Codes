package net.minecraft.entity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   private static final String __OBFID = "CL_00001548";

   public static EntityLeashKnot func_174862_a(World var0, BlockPos var1) {
      EntityLeashKnot var2 = new EntityLeashKnot(var0, var1);
      var2.forceSpawn = true;
      var0.spawnEntityInWorld(var2);
      return var2;
   }

   public boolean onValidSurface() {
      return this.worldObj.getBlockState(this.field_174861_a).getBlock() instanceof BlockFence;
   }

   public EntityLeashKnot(World var1) {
      super(var1);
   }

   public boolean writeToNBTOptional(NBTTagCompound var1) {
      return false;
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
   }

   public EntityLeashKnot(World var1, BlockPos var2) {
      super(var1, var2);
      this.setPosition((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D);
      float var3 = 0.125F;
      float var4 = 0.1875F;
      float var5 = 0.25F;
      this.func_174826_a(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D, this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
   }

   public boolean isInRangeToRenderDist(double var1) {
      return var1 < 1024.0D;
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void onBroken(Entity var1) {
   }

   public float getEyeHeight() {
      return -0.0625F;
   }

   public int getHeightPixels() {
      return 9;
   }

   public static EntityLeashKnot func_174863_b(World var0, BlockPos var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getZ();
      List var5 = var0.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB((double)var2 - 1.0D, (double)var3 - 1.0D, (double)var4 - 1.0D, (double)var2 + 1.0D, (double)var3 + 1.0D, (double)var4 + 1.0D));
      Iterator var6 = var5.iterator();

      while(var6.hasNext()) {
         EntityLeashKnot var7 = (EntityLeashKnot)var6.next();
         if (var7.func_174857_n().equals(var1)) {
            return var7;
         }
      }

      return null;
   }

   public boolean interactFirst(EntityPlayer var1) {
      ItemStack var2 = var1.getHeldItem();
      boolean var3 = false;
      double var4;
      List var6;
      Iterator var7;
      EntityLiving var8;
      if (var2 != null && var2.getItem() == Items.lead && !this.worldObj.isRemote) {
         var4 = 7.0D;
         var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
         var7 = var6.iterator();

         while(var7.hasNext()) {
            var8 = (EntityLiving)var7.next();
            if (var8.getLeashed() && var8.getLeashedToEntity() == var1) {
               var8.setLeashedToEntity(this, true);
               var3 = true;
            }
         }
      }

      if (!this.worldObj.isRemote && !var3) {
         this.setDead();
         if (var1.capabilities.isCreativeMode) {
            var4 = 7.0D;
            var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
            var7 = var6.iterator();

            while(var7.hasNext()) {
               var8 = (EntityLiving)var7.next();
               if (var8.getLeashed() && var8.getLeashedToEntity() == this) {
                  var8.clearLeashed(true, false);
               }
            }
         }
      }

      return true;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
   }

   public int getWidthPixels() {
      return 9;
   }

   public void func_174859_a(EnumFacing var1) {
   }
}
