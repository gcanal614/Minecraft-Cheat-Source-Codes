package net.minecraft.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySquid extends EntityWaterMob {
   public float squidYaw;
   public float squidRotation;
   private float randomMotionVecZ;
   private float randomMotionVecY;
   public float prevSquidPitch;
   public float prevSquidRotation;
   public float tentacleAngle;
   private float rotationVelocity;
   public float lastTentacleAngle;
   private static final String __OBFID = "CL_00001651";
   private float randomMotionSpeed;
   public float prevSquidYaw;
   private float field_70871_bB;
   private float randomMotionVecX;
   public float squidPitch;

   protected Item getDropItem() {
      return null;
   }

   protected String getLivingSound() {
      return null;
   }

   protected String getHurtSound() {
      return null;
   }

   public float getEyeHeight() {
      return this.height * 0.5F;
   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(3 + var2) + 1;

      for(int var4 = 0; var4 < var3; ++var4) {
         this.entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), 0.0F);
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
   }

   public void func_175568_b(float var1, float var2, float var3) {
      this.randomMotionVecX = var1;
      this.randomMotionVecY = var2;
      this.randomMotionVecZ = var3;
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   public boolean isInWater() {
      return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
   }

   public void handleHealthUpdate(byte var1) {
      if (var1 == 19) {
         this.squidRotation = 0.0F;
      } else {
         super.handleHealthUpdate(var1);
      }

   }

   static boolean access$0(EntitySquid var0) {
      return var0.inWater;
   }

   public boolean getCanSpawnHere() {
      return this.posY > 45.0D && this.posY < 63.0D && super.getCanSpawnHere();
   }

   protected String getDeathSound() {
      return null;
   }

   public EntitySquid(World var1) {
      super(var1);
      this.setSize(0.95F, 0.95F);
      this.rand.setSeed((long)(1 + this.getEntityId()));
      this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
      this.tasks.addTask(0, new EntitySquid.AIMoveRandom(this));
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.prevSquidPitch = this.squidPitch;
      this.prevSquidYaw = this.squidYaw;
      this.prevSquidRotation = this.squidRotation;
      this.lastTentacleAngle = this.tentacleAngle;
      this.squidRotation += this.rotationVelocity;
      if ((double)this.squidRotation > 6.283185307179586D) {
         if (this.worldObj.isRemote) {
            this.squidRotation = 6.2831855F;
         } else {
            this.squidRotation = (float)((double)this.squidRotation - 6.283185307179586D);
            if (this.rand.nextInt(10) == 0) {
               this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
            }

            this.worldObj.setEntityState(this, (byte)19);
         }
      }

      if (this.inWater) {
         float var1;
         if (this.squidRotation < 3.1415927F) {
            var1 = this.squidRotation / 3.1415927F;
            this.tentacleAngle = MathHelper.sin(var1 * var1 * 3.1415927F) * 3.1415927F * 0.25F;
            if ((double)var1 > 0.75D) {
               this.randomMotionSpeed = 1.0F;
               this.field_70871_bB = 1.0F;
            } else {
               this.field_70871_bB *= 0.8F;
            }
         } else {
            this.tentacleAngle = 0.0F;
            this.randomMotionSpeed *= 0.9F;
            this.field_70871_bB *= 0.99F;
         }

         if (!this.worldObj.isRemote) {
            this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
            this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
            this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
         }

         var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / 3.1415927F - this.renderYawOffset) * 0.1F;
         this.rotationYaw = this.renderYawOffset;
         this.squidYaw = (float)((double)this.squidYaw + 3.141592653589793D * (double)this.field_70871_bB * 1.5D);
         this.squidPitch += (-((float)Math.atan2((double)var1, this.motionY)) * 180.0F / 3.1415927F - this.squidPitch) * 0.1F;
      } else {
         this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927F * 0.25F;
         if (!this.worldObj.isRemote) {
            this.motionX = 0.0D;
            this.motionY -= 0.08D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ = 0.0D;
         }

         this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
      }

   }

   public void moveEntityWithHeading(float var1, float var2) {
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
   }

   public boolean func_175567_n() {
      return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
   }

   class AIMoveRandom extends EntityAIBase {
      private static final String __OBFID = "CL_00002232";
      final EntitySquid this$0;
      private EntitySquid field_179476_a;

      public boolean shouldExecute() {
         return true;
      }

      AIMoveRandom(EntitySquid var1) {
         this.this$0 = var1;
         this.field_179476_a = var1;
      }

      public void updateTask() {
         int var1 = this.field_179476_a.getAge();
         if (var1 > 100) {
            this.field_179476_a.func_175568_b(0.0F, 0.0F, 0.0F);
         } else if (this.field_179476_a.getRNG().nextInt(50) == 0 || !EntitySquid.access$0(this.field_179476_a) || !this.field_179476_a.func_175567_n()) {
            float var2 = this.field_179476_a.getRNG().nextFloat() * 3.1415927F * 2.0F;
            float var3 = MathHelper.cos(var2) * 0.2F;
            float var4 = -0.1F + this.field_179476_a.getRNG().nextFloat() * 0.2F;
            float var5 = MathHelper.sin(var2) * 0.2F;
            this.field_179476_a.func_175568_b(var3, var4, var5);
         }

      }
   }
}
