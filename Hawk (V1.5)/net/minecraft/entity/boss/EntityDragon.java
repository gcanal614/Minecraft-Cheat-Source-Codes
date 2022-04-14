package net.minecraft.entity.boss;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDragon extends EntityLiving implements IMob, IEntityMultiPart, IBossDisplayData {
   public EntityEnderCrystal healingEnderCrystal;
   public EntityDragonPart dragonPartWing1;
   public EntityDragonPart dragonPartTail1;
   public EntityDragonPart[] dragonPartArray;
   public int ringBufferIndex = -1;
   private Entity target;
   public int deathTicks;
   public EntityDragonPart dragonPartTail2;
   public float prevAnimTime;
   public float animTime;
   public double targetY;
   public double[][] ringBuffer = new double[64][3];
   public EntityDragonPart dragonPartHead;
   public boolean slowed;
   public EntityDragonPart dragonPartBody;
   public double targetX;
   public EntityDragonPart dragonPartTail3;
   public double targetZ;
   public EntityDragonPart dragonPartWing2;
   private static final String __OBFID = "CL_00001659";
   public boolean forceNewTarget;

   private void collideWithEntities(List var1) {
      double var2 = (this.dragonPartBody.getEntityBoundingBox().minX + this.dragonPartBody.getEntityBoundingBox().maxX) / 2.0D;
      double var4 = (this.dragonPartBody.getEntityBoundingBox().minZ + this.dragonPartBody.getEntityBoundingBox().maxZ) / 2.0D;
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         Entity var7 = (Entity)var6.next();
         if (var7 instanceof EntityLivingBase) {
            double var8 = var7.posX - var2;
            double var10 = var7.posZ - var4;
            double var12 = var8 * var8 + var10 * var10;
            var7.addVelocity(var8 / var12 * 4.0D, 0.20000000298023224D, var10 / var12 * 4.0D);
         }
      }

   }

   protected String getLivingSound() {
      return "mob.enderdragon.growl";
   }

   public EntityDragon(World var1) {
      super(var1);
      this.dragonPartArray = new EntityDragonPart[]{this.dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F)};
      this.setHealth(this.getMaxHealth());
      this.setSize(16.0F, 8.0F);
      this.noClip = true;
      this.isImmuneToFire = true;
      this.targetY = 100.0D;
      this.ignoreFrustumCheck = true;
   }

   protected float getSoundVolume() {
      return 5.0F;
   }

   public double[] getMovementOffsets(int var1, float var2) {
      if (this.getHealth() <= 0.0F) {
         var2 = 0.0F;
      }

      var2 = 1.0F - var2;
      int var3 = this.ringBufferIndex - var1 * 1 & 63;
      int var4 = this.ringBufferIndex - var1 * 1 - 1 & 63;
      double[] var5 = new double[3];
      double var6 = this.ringBuffer[var3][0];
      double var8 = MathHelper.wrapAngleTo180_double(this.ringBuffer[var4][0] - var6);
      var5[0] = var6 + var8 * (double)var2;
      var6 = this.ringBuffer[var3][1];
      var8 = this.ringBuffer[var4][1] - var6;
      var5[1] = var6 + var8 * (double)var2;
      var5[2] = this.ringBuffer[var3][2] + (this.ringBuffer[var4][2] - this.ringBuffer[var3][2]) * (double)var2;
      return var5;
   }

   protected void onDeathUpdate() {
      ++this.deathTicks;
      if (this.deathTicks >= 180 && this.deathTicks <= 200) {
         float var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
         float var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
         float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)var1, this.posY + 2.0D + (double)var2, this.posZ + (double)var3, 0.0D, 0.0D, 0.0D);
      }

      int var4;
      int var5;
      if (!this.worldObj.isRemote) {
         if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
            var4 = 1000;

            while(var4 > 0) {
               var5 = EntityXPOrb.getXPSplit(var4);
               var4 -= var5;
               this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
            }
         }

         if (this.deathTicks == 1) {
            this.worldObj.func_175669_a(1018, new BlockPos(this), 0);
         }
      }

      this.moveEntity(0.0D, 0.10000000149011612D, 0.0D);
      this.renderYawOffset = this.rotationYaw += 20.0F;
      if (this.deathTicks == 200 && !this.worldObj.isRemote) {
         var4 = 2000;

         while(var4 > 0) {
            var5 = EntityXPOrb.getXPSplit(var4);
            var4 -= var5;
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
         }

         this.func_175499_a(new BlockPos(this.posX, 64.0D, this.posZ));
         this.setDead();
      }

   }

   private void attackEntitiesInList(List var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         Entity var3 = (Entity)var1.get(var2);
         if (var3 instanceof EntityLivingBase) {
            var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
            this.func_174815_a(this, var3);
         }
      }

   }

   private void setNewTarget() {
      this.forceNewTarget = false;
      ArrayList var1 = Lists.newArrayList(this.worldObj.playerEntities);
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         if (((EntityPlayer)var2.next()).func_175149_v()) {
            var2.remove();
         }
      }

      if (this.rand.nextInt(2) == 0 && !var1.isEmpty()) {
         this.target = (Entity)var1.get(this.rand.nextInt(var1.size()));
      } else {
         boolean var3;
         do {
            this.targetX = 0.0D;
            this.targetY = (double)(70.0F + this.rand.nextFloat() * 50.0F);
            this.targetZ = 0.0D;
            this.targetX += (double)(this.rand.nextFloat() * 120.0F - 60.0F);
            this.targetZ += (double)(this.rand.nextFloat() * 120.0F - 60.0F);
            double var4 = this.posX - this.targetX;
            double var6 = this.posY - this.targetY;
            double var8 = this.posZ - this.targetZ;
            var3 = var4 * var4 + var6 * var6 + var8 * var8 > 100.0D;
         } while(!var3);

         this.target = null;
      }

   }

   private void updateDragonEnderCrystal() {
      if (this.healingEnderCrystal != null) {
         if (this.healingEnderCrystal.isDead) {
            if (!this.worldObj.isRemote) {
               this.attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource((Explosion)null), 10.0F);
            }

            this.healingEnderCrystal = null;
         } else if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.setHealth(this.getHealth() + 1.0F);
         }
      }

      if (this.rand.nextInt(10) == 0) {
         float var1 = 32.0F;
         List var2 = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.getEntityBoundingBox().expand((double)var1, (double)var1, (double)var1));
         EntityEnderCrystal var3 = null;
         double var4 = Double.MAX_VALUE;
         Iterator var6 = var2.iterator();

         while(var6.hasNext()) {
            EntityEnderCrystal var7 = (EntityEnderCrystal)var6.next();
            double var8 = var7.getDistanceSqToEntity(this);
            if (var8 < var4) {
               var4 = var8;
               var3 = var7;
            }
         }

         this.healingEnderCrystal = var3;
      }

   }

   public void onLivingUpdate() {
      float var1;
      float var2;
      if (this.worldObj.isRemote) {
         var1 = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
         var2 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
         if (var2 <= -0.3F && var1 >= -0.3F && !this.isSlient()) {
            this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
         }
      }

      this.prevAnimTime = this.animTime;
      float var3;
      if (this.getHealth() <= 0.0F) {
         var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
         var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
         var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (double)var1, this.posY + 2.0D + (double)var2, this.posZ + (double)var3, 0.0D, 0.0D, 0.0D);
      } else {
         this.updateDragonEnderCrystal();
         var1 = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
         var1 *= (float)Math.pow(2.0D, this.motionY);
         if (this.slowed) {
            this.animTime += var1 * 0.5F;
         } else {
            this.animTime += var1;
         }

         this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
         if (this.ringBufferIndex < 0) {
            for(int var4 = 0; var4 < this.ringBuffer.length; ++var4) {
               this.ringBuffer[var4][0] = (double)this.rotationYaw;
               this.ringBuffer[var4][1] = this.posY;
            }
         }

         if (++this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
         }

         this.ringBuffer[this.ringBufferIndex][0] = (double)this.rotationYaw;
         this.ringBuffer[this.ringBufferIndex][1] = this.posY;
         double var6;
         double var8;
         double var10;
         float var12;
         float var25;
         float var26;
         double var29;
         if (this.worldObj.isRemote) {
            if (this.newPosRotationIncrements > 0) {
               var10 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
               var29 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
               var6 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
               var8 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
               this.rotationYaw = (float)((double)this.rotationYaw + var8 / (double)this.newPosRotationIncrements);
               this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
               --this.newPosRotationIncrements;
               this.setPosition(var10, var29, var6);
               this.setRotation(this.rotationYaw, this.rotationPitch);
            }
         } else {
            var10 = this.targetX - this.posX;
            var29 = this.targetY - this.posY;
            var6 = this.targetZ - this.posZ;
            var8 = var10 * var10 + var29 * var29 + var6 * var6;
            double var13;
            double var15;
            double var17;
            if (this.target != null) {
               this.targetX = this.target.posX;
               this.targetZ = this.target.posZ;
               var15 = this.targetX - this.posX;
               var17 = this.targetZ - this.posZ;
               double var19 = Math.sqrt(var15 * var15 + var17 * var17);
               var13 = 0.4000000059604645D + var19 / 80.0D - 1.0D;
               if (var13 > 10.0D) {
                  var13 = 10.0D;
               }

               this.targetY = this.target.getEntityBoundingBox().minY + var13;
            } else {
               this.targetX += this.rand.nextGaussian() * 2.0D;
               this.targetZ += this.rand.nextGaussian() * 2.0D;
            }

            if (this.forceNewTarget || var8 < 100.0D || var8 > 22500.0D || this.isCollidedHorizontally || this.isCollidedVertically) {
               this.setNewTarget();
            }

            var29 /= (double)MathHelper.sqrt_double(var10 * var10 + var6 * var6);
            var12 = 0.6F;
            var29 = MathHelper.clamp_double(var29, (double)(-var12), (double)var12);
            this.motionY += var29 * 0.10000000149011612D;
            this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
            var15 = 180.0D - Math.atan2(var10, var6) * 180.0D / 3.141592653589793D;
            var17 = MathHelper.wrapAngleTo180_double(var15 - (double)this.rotationYaw);
            if (var17 > 50.0D) {
               var17 = 50.0D;
            }

            if (var17 < -50.0D) {
               var17 = -50.0D;
            }

            Vec3 var33 = (new Vec3(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ)).normalize();
            var13 = (double)(-MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F));
            Vec3 var20 = (new Vec3((double)MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, var13)).normalize();
            float var21 = ((float)var20.dotProduct(var33) + 0.5F) / 1.5F;
            if (var21 < 0.0F) {
               var21 = 0.0F;
            }

            this.randomYawVelocity *= 0.8F;
            float var22 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
            double var23 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
            if (var23 > 40.0D) {
               var23 = 40.0D;
            }

            this.randomYawVelocity = (float)((double)this.randomYawVelocity + var17 * (0.699999988079071D / var23 / (double)var22));
            this.rotationYaw += this.randomYawVelocity * 0.1F;
            var25 = (float)(2.0D / (var23 + 1.0D));
            var26 = 0.06F;
            this.moveFlying(0.0F, -1.0F, var26 * (var21 * var25 + (1.0F - var25)));
            if (this.slowed) {
               this.moveEntity(this.motionX * 0.800000011920929D, this.motionY * 0.800000011920929D, this.motionZ * 0.800000011920929D);
            } else {
               this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }

            Vec3 var27 = (new Vec3(this.motionX, this.motionY, this.motionZ)).normalize();
            float var28 = ((float)var27.dotProduct(var20) + 1.0F) / 2.0F;
            var28 = 0.8F + 0.15F * var28;
            this.motionX *= (double)var28;
            this.motionZ *= (double)var28;
            this.motionY *= 0.9100000262260437D;
         }

         this.renderYawOffset = this.rotationYaw;
         this.dragonPartHead.width = this.dragonPartHead.height = 3.0F;
         this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
         this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
         this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
         this.dragonPartBody.height = 3.0F;
         this.dragonPartBody.width = 5.0F;
         this.dragonPartWing1.height = 2.0F;
         this.dragonPartWing1.width = 4.0F;
         this.dragonPartWing2.height = 3.0F;
         this.dragonPartWing2.width = 4.0F;
         var2 = (float)(this.getMovementOffsets(5, 1.0F)[1] - this.getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
         var3 = MathHelper.cos(var2);
         float var30 = -MathHelper.sin(var2);
         float var14 = this.rotationYaw * 3.1415927F / 180.0F;
         float var31 = MathHelper.sin(var14);
         float var16 = MathHelper.cos(var14);
         this.dragonPartBody.onUpdate();
         this.dragonPartBody.setLocationAndAngles(this.posX + (double)(var31 * 0.5F), this.posY, this.posZ - (double)(var16 * 0.5F), 0.0F, 0.0F);
         this.dragonPartWing1.onUpdate();
         this.dragonPartWing1.setLocationAndAngles(this.posX + (double)(var16 * 4.5F), this.posY + 2.0D, this.posZ + (double)(var31 * 4.5F), 0.0F, 0.0F);
         this.dragonPartWing2.onUpdate();
         this.dragonPartWing2.setLocationAndAngles(this.posX - (double)(var16 * 4.5F), this.posY + 2.0D, this.posZ - (double)(var31 * 4.5F), 0.0F, 0.0F);
         if (!this.worldObj.isRemote && this.hurtTime == 0) {
            this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
            this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
            this.attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D)));
         }

         double[] var32 = this.getMovementOffsets(5, 1.0F);
         double[] var18 = this.getMovementOffsets(0, 1.0F);
         var12 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
         float var34 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
         this.dragonPartHead.onUpdate();
         this.dragonPartHead.setLocationAndAngles(this.posX + (double)(var12 * 5.5F * var3), this.posY + (var18[1] - var32[1]) * 1.0D + (double)(var30 * 5.5F), this.posZ - (double)(var34 * 5.5F * var3), 0.0F, 0.0F);

         for(int var35 = 0; var35 < 3; ++var35) {
            EntityDragonPart var36 = null;
            if (var35 == 0) {
               var36 = this.dragonPartTail1;
            }

            if (var35 == 1) {
               var36 = this.dragonPartTail2;
            }

            if (var35 == 2) {
               var36 = this.dragonPartTail3;
            }

            double[] var37 = this.getMovementOffsets(12 + var35 * 2, 1.0F);
            float var38 = this.rotationYaw * 3.1415927F / 180.0F + this.simplifyAngle(var37[0] - var32[0]) * 3.1415927F / 180.0F * 1.0F;
            float var24 = MathHelper.sin(var38);
            var25 = MathHelper.cos(var38);
            var26 = 1.5F;
            float var39 = (float)(var35 + 1) * 2.0F;
            var36.onUpdate();
            var36.setLocationAndAngles(this.posX - (double)((var31 * var26 + var24 * var39) * var3), this.posY + (var37[1] - var32[1]) * 1.0D - (double)((var39 + var26) * var30) + 1.5D, this.posZ + (double)((var16 * var26 + var25 * var39) * var3), 0.0F, 0.0F);
         }

         if (!this.worldObj.isRemote) {
            this.slowed = this.destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox()) | this.destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
         }
      }

   }

   protected void entityInit() {
      super.entityInit();
   }

   protected boolean func_82195_e(DamageSource var1, float var2) {
      return super.attackEntityFrom(var1, var2);
   }

   protected void despawnEntity() {
   }

   protected String getHurtSound() {
      return "mob.enderdragon.hit";
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
   }

   private boolean destroyBlocksInAABB(AxisAlignedBB var1) {
      int var2 = MathHelper.floor_double(var1.minX);
      int var3 = MathHelper.floor_double(var1.minY);
      int var4 = MathHelper.floor_double(var1.minZ);
      int var5 = MathHelper.floor_double(var1.maxX);
      int var6 = MathHelper.floor_double(var1.maxY);
      int var7 = MathHelper.floor_double(var1.maxZ);
      boolean var8 = false;
      boolean var9 = false;

      for(int var10 = var2; var10 <= var5; ++var10) {
         for(int var11 = var3; var11 <= var6; ++var11) {
            for(int var12 = var4; var12 <= var7; ++var12) {
               Block var13 = this.worldObj.getBlockState(new BlockPos(var10, var11, var12)).getBlock();
               if (var13.getMaterial() != Material.air) {
                  if (var13 != Blocks.barrier && var13 != Blocks.obsidian && var13 != Blocks.end_stone && var13 != Blocks.bedrock && var13 != Blocks.command_block && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                     var9 = this.worldObj.setBlockToAir(new BlockPos(var10, var11, var12)) || var9;
                  } else {
                     var8 = true;
                  }
               }
            }
         }
      }

      if (var9) {
         double var16 = var1.minX + (var1.maxX - var1.minX) * (double)this.rand.nextFloat();
         double var17 = var1.minY + (var1.maxY - var1.minY) * (double)this.rand.nextFloat();
         double var14 = var1.minZ + (var1.maxZ - var1.minZ) * (double)this.rand.nextFloat();
         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var16, var17, var14, 0.0D, 0.0D, 0.0D);
      }

      return var8;
   }

   private void func_175499_a(BlockPos var1) {
      boolean var2 = true;
      double var3 = 12.25D;
      double var5 = 6.25D;

      for(int var7 = -1; var7 <= 32; ++var7) {
         for(int var8 = -4; var8 <= 4; ++var8) {
            for(int var9 = -4; var9 <= 4; ++var9) {
               double var10 = (double)(var8 * var8 + var9 * var9);
               if (var10 <= 12.25D) {
                  BlockPos var12 = var1.add(var8, var7, var9);
                  if (var7 < 0) {
                     if (var10 <= 6.25D) {
                        this.worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
                     }
                  } else if (var7 > 0) {
                     this.worldObj.setBlockState(var12, Blocks.air.getDefaultState());
                  } else if (var10 > 6.25D) {
                     this.worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
                  } else {
                     this.worldObj.setBlockState(var12, Blocks.end_portal.getDefaultState());
                  }
               }
            }
         }
      }

      this.worldObj.setBlockState(var1, Blocks.bedrock.getDefaultState());
      this.worldObj.setBlockState(var1.offsetUp(), Blocks.bedrock.getDefaultState());
      BlockPos var13 = var1.offsetUp(2);
      this.worldObj.setBlockState(var13, Blocks.bedrock.getDefaultState());
      this.worldObj.setBlockState(var13.offsetWest(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.EAST));
      this.worldObj.setBlockState(var13.offsetEast(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.WEST));
      this.worldObj.setBlockState(var13.offsetNorth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.SOUTH));
      this.worldObj.setBlockState(var13.offsetSouth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.NORTH));
      this.worldObj.setBlockState(var1.offsetUp(3), Blocks.bedrock.getDefaultState());
      this.worldObj.setBlockState(var1.offsetUp(4), Blocks.dragon_egg.getDefaultState());
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (var1 instanceof EntityDamageSource && ((EntityDamageSource)var1).func_180139_w()) {
         this.func_82195_e(var1, var2);
      }

      return false;
   }

   public Entity[] getParts() {
      return this.dragonPartArray;
   }

   public World func_82194_d() {
      return this.worldObj;
   }

   private float simplifyAngle(double var1) {
      return (float)MathHelper.wrapAngleTo180_double(var1);
   }

   public boolean attackEntityFromPart(EntityDragonPart var1, DamageSource var2, float var3) {
      if (var1 != this.dragonPartHead) {
         var3 = var3 / 4.0F + 1.0F;
      }

      float var4 = this.rotationYaw * 3.1415927F / 180.0F;
      float var5 = MathHelper.sin(var4);
      float var6 = MathHelper.cos(var4);
      this.targetX = this.posX + (double)(var5 * 5.0F) + (double)((this.rand.nextFloat() - 0.5F) * 2.0F);
      this.targetY = this.posY + (double)(this.rand.nextFloat() * 3.0F) + 1.0D;
      this.targetZ = this.posZ - (double)(var6 * 5.0F) + (double)((this.rand.nextFloat() - 0.5F) * 2.0F);
      this.target = null;
      if (var2.getEntity() instanceof EntityPlayer || var2.isExplosion()) {
         this.func_82195_e(var2, var3);
      }

      return true;
   }

   public void func_174812_G() {
      this.setDead();
   }
}
