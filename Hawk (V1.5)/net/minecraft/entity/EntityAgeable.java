package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {
   private float field_98056_d = -1.0F;
   private static final String __OBFID = "CL_00001530";
   protected int field_175503_c;
   private float field_98057_e;
   protected int field_175502_b;
   protected int field_175504_a;

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setGrowingAge(var1.getInteger("Age"));
      this.field_175502_b = var1.getInteger("ForcedAge");
   }

   public int getGrowingAge() {
      return this.worldObj.isRemote ? this.dataWatcher.getWatchableObjectByte(12) : this.field_175504_a;
   }

   public void func_175501_a(int var1, boolean var2) {
      int var3 = this.getGrowingAge();
      int var4 = var3;
      var3 += var1 * 20;
      if (var3 > 0) {
         var3 = 0;
         if (var4 < 0) {
            this.func_175500_n();
         }
      }

      int var5 = var3 - var4;
      this.setGrowingAge(var3);
      if (var2) {
         this.field_175502_b += var5;
         if (this.field_175503_c == 0) {
            this.field_175503_c = 40;
         }
      }

      if (this.getGrowingAge() == 0) {
         this.setGrowingAge(this.field_175502_b);
      }

   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.worldObj.isRemote) {
         if (this.field_175503_c > 0) {
            if (this.field_175503_c % 4 == 0) {
               this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D);
            }

            --this.field_175503_c;
         }

         this.setScaleForAge(this.isChild());
      } else {
         int var1 = this.getGrowingAge();
         if (var1 < 0) {
            ++var1;
            this.setGrowingAge(var1);
            if (var1 == 0) {
               this.func_175500_n();
            }
         } else if (var1 > 0) {
            --var1;
            this.setGrowingAge(var1);
         }
      }

   }

   public void addGrowth(int var1) {
      this.func_175501_a(var1, false);
   }

   public boolean isChild() {
      return this.getGrowingAge() < 0;
   }

   public boolean interact(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      if (var2 != null && var2.getItem() == Items.spawn_egg) {
         if (!this.worldObj.isRemote) {
            Class var3 = EntityList.getClassFromID(var2.getMetadata());
            if (var3 != null && this.getClass() == var3) {
               EntityAgeable var4 = this.createChild(this);
               if (var4 != null) {
                  var4.setGrowingAge(-24000);
                  var4.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                  this.worldObj.spawnEntityInWorld(var4);
                  if (var2.hasDisplayName()) {
                     var4.setCustomNameTag(var2.getDisplayName());
                  }

                  if (!var1.capabilities.isCreativeMode) {
                     --var2.stackSize;
                     if (var2.stackSize <= 0) {
                        var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected final void setSize(float var1, float var2) {
      boolean var3 = this.field_98056_d > 0.0F;
      this.field_98056_d = var1;
      this.field_98057_e = var2;
      if (!var3) {
         this.setScale(1.0F);
      }

   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(12, (byte)0);
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("Age", this.getGrowingAge());
      var1.setInteger("ForcedAge", this.field_175502_b);
   }

   public EntityAgeable(World var1) {
      super(var1);
   }

   public void setScaleForAge(boolean var1) {
      this.setScale(var1 ? 0.5F : 1.0F);
   }

   protected void func_175500_n() {
   }

   public void setGrowingAge(int var1) {
      this.dataWatcher.updateObject(12, (byte)MathHelper.clamp_int(var1, -1, 1));
      this.field_175504_a = var1;
      this.setScaleForAge(this.isChild());
   }

   protected final void setScale(float var1) {
      super.setSize(this.field_98056_d * var1, this.field_98057_e * var1);
   }

   public abstract EntityAgeable createChild(EntityAgeable var1);
}
