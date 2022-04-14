package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLivingBase extends Entity {
   private final Map activePotionsMap = Maps.newHashMap();
   private final CombatTracker _combatTracker = new CombatTracker(this);
   public float field_110154_aX;
   public float moveStrafing;
   public float limbSwing;
   private static final String __OBFID = "CL_00001549";
   private int lastAttackerTime;
   private final ItemStack[] previousEquipment = new ItemStack[5];
   private int jumpTicks;
   private static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier sprintingSpeedBoostModifier;
   public float field_70768_au;
   private float field_110151_bq;
   protected int scoreValue;
   protected double newPosY;
   public float cameraPitch;
   public float prevRotationYawHead;
   public float jumpMovementFactor = 0.02F;
   protected double newRotationYaw;
   private float landMovementFactor;
   public int hurtTime;
   protected boolean dead;
   protected int entityAge;
   protected double newRotationPitch;
   public float rotationYawHead;
   public int maxHurtResistantTime = 20;
   public float prevSwingProgress;
   protected double newPosX;
   public boolean isSwingInProgress;
   public int swingProgressInt;
   private EntityLivingBase entityLivingToAttack;
   public float field_70770_ap;
   private BaseAttributeMap attributeMap;
   protected int recentlyHit;
   public float prevRenderYawOffset;
   public float prevLimbSwingAmount;
   public int arrowHitTimer;
   public float prevCameraPitch;
   private int revengeTimer;
   public float field_70763_ax;
   public float swingProgress;
   protected double newPosZ;
   public float renderYawOffset;
   public int maxHurtTime;
   private EntityLivingBase lastAttacker;
   protected float lastDamage;
   public int deathTime;
   public float field_70741_aB;
   public float moveForward;
   public float limbSwingAmount;
   protected boolean isJumping;
   public float field_70769_ao;
   public float attackedAtYaw;
   protected float field_70764_aw;
   protected int newPosRotationIncrements;
   protected float randomYawVelocity;
   private boolean potionsNeedUpdate = true;
   protected EntityPlayer attackingPlayer;

   protected String getDeathSound() {
      return "game.neutral.die";
   }

   static {
      sprintingSpeedBoostModifier = (new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
   }

   protected void updateAITick() {
      this.motionY += 0.03999999910593033D;
   }

   protected void func_175133_bi() {
      this.dataWatcher.updateObject(8, (byte)0);
      this.dataWatcher.updateObject(7, 0);
   }

   public void setAIMoveSpeed(float var1) {
      this.landMovementFactor = var1;
   }

   public EntityLivingBase getAITarget() {
      return this.entityLivingToAttack;
   }

   protected void func_180433_a(double var1, boolean var3, Block var4, BlockPos var5) {
      if (!this.isInWater()) {
         this.handleWaterMovement();
      }

      if (!this.worldObj.isRemote && this.fallDistance > 3.0F && var3) {
         IBlockState var6 = this.worldObj.getBlockState(var5);
         Block var7 = var6.getBlock();
         float var8 = (float)MathHelper.ceiling_float_int(this.fallDistance - 3.0F);
         if (var7.getMaterial() != Material.air) {
            double var9 = (double)Math.min(0.2F + var8 / 15.0F, 10.0F);
            if (var9 > 2.5D) {
               var9 = 2.5D;
            }

            int var11 = (int)(150.0D * var9);
            ((WorldServer)this.worldObj).func_175739_a(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, var11, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, Block.getStateId(var6));
         }
      }

      super.func_180433_a(var1, var3, var4, var5);
   }

   protected void updateArmSwingProgress() {
      int var1 = this.getArmSwingAnimationEnd();
      if (this.isSwingInProgress) {
         ++this.swingProgressInt;
         if (this.swingProgressInt >= var1) {
            this.swingProgressInt = 0;
            this.isSwingInProgress = false;
         }
      } else {
         this.swingProgressInt = 0;
      }

      this.swingProgress = (float)this.swingProgressInt / (float)var1;
   }

   public void func_152112_bu() {
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public abstract ItemStack[] getInventory();

   public boolean isOnSameTeam(EntityLivingBase var1) {
      return this.isOnTeam(var1.getTeam());
   }

   public void setAbsorptionAmount(float var1) {
      if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.field_110151_bq = var1;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.worldObj.isRemote) {
         int var1 = this.getArrowCountInEntity();
         if (var1 > 0) {
            if (this.arrowHitTimer <= 0) {
               this.arrowHitTimer = 20 * (30 - var1);
            }

            --this.arrowHitTimer;
            if (this.arrowHitTimer <= 0) {
               this.setArrowCountInEntity(var1 - 1);
            }
         }

         for(int var2 = 0; var2 < 5; ++var2) {
            ItemStack var3 = this.previousEquipment[var2];
            ItemStack var4 = this.getEquipmentInSlot(var2);
            if (!ItemStack.areItemStacksEqual(var4, var3)) {
               ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S04PacketEntityEquipment(this.getEntityId(), var2, var4));
               if (var3 != null) {
                  this.attributeMap.removeAttributeModifiers(var3.getAttributeModifiers());
               }

               if (var4 != null) {
                  this.attributeMap.applyAttributeModifiers(var4.getAttributeModifiers());
               }

               this.previousEquipment[var2] = var4 == null ? null : var4.copy();
            }
         }

         if (this.ticksExisted % 20 == 0) {
            this.getCombatTracker().func_94549_h();
         }
      }

      this.onLivingUpdate();
      double var9 = this.posX - this.prevPosX;
      double var10 = this.posZ - this.prevPosZ;
      float var5 = (float)(var9 * var9 + var10 * var10);
      float var6 = this.renderYawOffset;
      float var7 = 0.0F;
      this.field_70768_au = this.field_110154_aX;
      float var8 = 0.0F;
      if (var5 > 0.0025000002F) {
         var8 = 1.0F;
         var7 = (float)Math.sqrt((double)var5) * 3.0F;
         var6 = (float)Math.atan2(var10, var9) * 180.0F / 3.1415927F - 90.0F;
      }

      if (this.swingProgress > 0.0F) {
         var6 = this.rotationYaw;
      }

      if (!this.onGround) {
         var8 = 0.0F;
      }

      this.field_110154_aX += (var8 - this.field_110154_aX) * 0.3F;
      this.worldObj.theProfiler.startSection("headTurn");
      var7 = this.func_110146_f(var6, var7);
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("rangeChecks");

      while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      while(this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
         this.prevRenderYawOffset -= 360.0F;
      }

      while(this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
         this.prevRenderYawOffset += 360.0F;
      }

      while(this.rotationPitch - this.prevRotationPitch < -180.0F) {
         this.prevRotationPitch -= 360.0F;
      }

      while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while(this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
         this.prevRotationYawHead -= 360.0F;
      }

      while(this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
         this.prevRotationYawHead += 360.0F;
      }

      this.worldObj.theProfiler.endSection();
      this.field_70764_aw += var7;
   }

   public void removePotionEffectClient(int var1) {
      this.activePotionsMap.remove(var1);
   }

   public void moveEntityWithHeading(float var1, float var2) {
      double var3;
      float var5;
      if (this.isServerWorld()) {
         float var6;
         float var7;
         if (this.isInWater() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
            var3 = this.posY;
            var6 = 0.8F;
            var7 = 0.02F;
            var5 = (float)EnchantmentHelper.func_180318_b(this);
            if (var5 > 3.0F) {
               var5 = 3.0F;
            }

            if (!this.onGround) {
               var5 *= 0.5F;
            }

            if (var5 > 0.0F) {
               var6 += (0.54600006F - var6) * var5 / 3.0F;
               var7 += (this.getAIMoveSpeed() * 1.0F - var7) * var5 / 3.0F;
            }

            this.moveFlying(var1, var2, var7);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)var6;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= (double)var6;
            this.motionY -= 0.02D;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var3, this.motionZ)) {
               this.motionY = 0.30000001192092896D;
            }
         } else if (this.func_180799_ab() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
            var3 = this.posY;
            this.moveFlying(var1, var2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
            this.motionY -= 0.02D;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var3, this.motionZ)) {
               this.motionY = 0.30000001192092896D;
            }
         } else {
            float var8 = 0.91F;
            if (this.onGround) {
               var8 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
            }

            float var9 = 0.16277136F / (var8 * var8 * var8);
            if (this.onGround) {
               var6 = this.getAIMoveSpeed() * var9;
            } else {
               var6 = this.jumpMovementFactor;
            }

            this.moveFlying(var1, var2, var6);
            var8 = 0.91F;
            if (this.onGround) {
               var8 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
            }

            if (this.isOnLadder()) {
               var7 = 0.15F;
               this.motionX = MathHelper.clamp_double(this.motionX, (double)(-var7), (double)var7);
               this.motionZ = MathHelper.clamp_double(this.motionZ, (double)(-var7), (double)var7);
               this.fallDistance = 0.0F;
               if (this.motionY < -0.15D) {
                  this.motionY = -0.15D;
               }

               boolean var10 = this.isSneaking() && this instanceof EntityPlayer;
               if (var10 && this.motionY < 0.0D) {
                  this.motionY = 0.0D;
               }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && this.isOnLadder()) {
               this.motionY = 0.2D;
            }

            if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
               if (this.posY > 0.0D) {
                  this.motionY = -0.1D;
               } else {
                  this.motionY = 0.0D;
               }
            } else {
               this.motionY -= 0.08D;
            }

            this.motionY *= 0.9800000190734863D;
            this.motionX *= (double)var8;
            this.motionZ *= (double)var8;
         }
      }

      this.prevLimbSwingAmount = this.limbSwingAmount;
      var3 = this.posX - this.prevPosX;
      double var11 = this.posZ - this.prevPosZ;
      var5 = MathHelper.sqrt_double(var3 * var3 + var11 * var11) * 4.0F;
      if (var5 > 1.0F) {
         var5 = 1.0F;
      }

      this.limbSwingAmount += (var5 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
   }

   public void mountEntity(Entity var1) {
      if (this.ridingEntity != null && var1 == null) {
         if (!this.worldObj.isRemote) {
            this.dismountEntity(this.ridingEntity);
         }

         if (this.ridingEntity != null) {
            this.ridingEntity.riddenByEntity = null;
         }

         this.ridingEntity = null;
      } else {
         super.mountEntity(var1);
      }

   }

   public boolean isPlayerSleeping() {
      return false;
   }

   public void onLivingUpdate() {
      if (this.jumpTicks > 0) {
         --this.jumpTicks;
      }

      if (this.newPosRotationIncrements > 0) {
         double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
         double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
         double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
         double var7 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double)this.rotationYaw);
         this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.newPosRotationIncrements);
         this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
         --this.newPosRotationIncrements;
         this.setPosition(var1, var3, var5);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      } else if (!this.isServerWorld()) {
         this.motionX *= 0.98D;
         this.motionY *= 0.98D;
         this.motionZ *= 0.98D;
      }

      if (Math.abs(this.motionX) < 0.005D) {
         this.motionX = 0.0D;
      }

      if (Math.abs(this.motionY) < 0.005D) {
         this.motionY = 0.0D;
      }

      if (Math.abs(this.motionZ) < 0.005D) {
         this.motionZ = 0.0D;
      }

      this.worldObj.theProfiler.startSection("ai");
      if (this.isMovementBlocked()) {
         this.isJumping = false;
         this.moveStrafing = 0.0F;
         this.moveForward = 0.0F;
         this.randomYawVelocity = 0.0F;
      } else if (this.isServerWorld()) {
         this.worldObj.theProfiler.startSection("newAi");
         this.updateEntityActionState();
         this.worldObj.theProfiler.endSection();
      }

      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("jump");
      if (this.isJumping) {
         if (this.isInWater()) {
            this.updateAITick();
         } else if (this.func_180799_ab()) {
            this.func_180466_bG();
         } else if (this.onGround && this.jumpTicks == 0) {
            this.jump();
            this.jumpTicks = 10;
         }
      } else {
         this.jumpTicks = 0;
      }

      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("travel");
      this.moveStrafing *= 0.98F;
      this.moveForward *= 0.98F;
      this.randomYawVelocity *= 0.9F;
      this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("push");
      if (!this.worldObj.isRemote) {
         this.collideWithNearbyEntities();
      }

      this.worldObj.theProfiler.endSection();
   }

   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F;
   }

   protected void setBeenAttacked() {
      this.velocityChanged = this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();
   }

   protected int decreaseAirSupply(int var1) {
      int var2 = EnchantmentHelper.func_180319_a(this);
      return var2 > 0 && this.rand.nextInt(var2 + 1) > 0 ? var1 : var1 - 1;
   }

   public boolean isPotionActive(int var1) {
      return this.activePotionsMap.containsKey(var1);
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (this.func_180431_b(var1)) {
         return false;
      } else if (this.worldObj.isRemote) {
         return false;
      } else {
         this.entityAge = 0;
         if (this.getHealth() <= 0.0F) {
            return false;
         } else if (var1.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return false;
         } else {
            if ((var1 == DamageSource.anvil || var1 == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null) {
               this.getEquipmentInSlot(4).damageItem((int)(var2 * 4.0F + this.rand.nextFloat() * var2 * 2.0F), this);
               var2 *= 0.75F;
            }

            this.limbSwingAmount = 1.5F;
            boolean var3 = true;
            if ((float)this.hurtResistantTime > (float)this.maxHurtResistantTime / 2.0F) {
               if (var2 <= this.lastDamage) {
                  return false;
               }

               this.damageEntity(var1, var2 - this.lastDamage);
               this.lastDamage = var2;
               var3 = false;
            } else {
               this.lastDamage = var2;
               this.hurtResistantTime = this.maxHurtResistantTime;
               this.damageEntity(var1, var2);
               this.hurtTime = this.maxHurtTime = 10;
            }

            this.attackedAtYaw = 0.0F;
            Entity var4 = var1.getEntity();
            if (var4 != null) {
               if (var4 instanceof EntityLivingBase) {
                  this.setRevengeTarget((EntityLivingBase)var4);
               }

               if (var4 instanceof EntityPlayer) {
                  this.recentlyHit = 100;
                  this.attackingPlayer = (EntityPlayer)var4;
               } else if (var4 instanceof EntityWolf) {
                  EntityWolf var5 = (EntityWolf)var4;
                  if (var5.isTamed()) {
                     this.recentlyHit = 100;
                     this.attackingPlayer = null;
                  }
               }
            }

            if (var3) {
               this.worldObj.setEntityState(this, (byte)2);
               if (var1 != DamageSource.drown) {
                  this.setBeenAttacked();
               }

               if (var4 != null) {
                  double var9 = var4.posX - this.posX;

                  double var7;
                  for(var7 = var4.posZ - this.posZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {
                     var9 = (Math.random() - Math.random()) * 0.01D;
                  }

                  this.attackedAtYaw = (float)(Math.atan2(var7, var9) * 180.0D / 3.141592653589793D - (double)this.rotationYaw);
                  this.knockBack(var4, var2, var9, var7);
               } else {
                  this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
               }
            }

            String var10;
            if (this.getHealth() <= 0.0F) {
               var10 = this.getDeathSound();
               if (var3 && var10 != null) {
                  this.playSound(var10, this.getSoundVolume(), this.getSoundPitch());
               }

               this.onDeath(var1);
            } else {
               var10 = this.getHurtSound();
               if (var3 && var10 != null) {
                  this.playSound(var10, this.getSoundVolume(), this.getSoundPitch());
               }
            }

            return true;
         }
      }
   }

   public CombatTracker getCombatTracker() {
      return this._combatTracker;
   }

   protected float getSoundVolume() {
      return 1.0F;
   }

   public BaseAttributeMap getAttributeMap() {
      if (this.attributeMap == null) {
         this.attributeMap = new ServersideAttributeMap();
      }

      return this.attributeMap;
   }

   public void func_174812_G() {
      this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
   }

   protected void damageEntity(DamageSource var1, float var2) {
      if (!this.func_180431_b(var1)) {
         var2 = this.applyArmorCalculations(var1, var2);
         var2 = this.applyPotionDamageCalculations(var1, var2);
         float var3 = var2;
         var2 = Math.max(var2 - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - var2));
         if (var2 != 0.0F) {
            float var4 = this.getHealth();
            this.setHealth(var4 - var2);
            this.getCombatTracker().func_94547_a(var1, var4, var2);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - var2);
         }
      }

   }

   public boolean canBePushed() {
      return !this.isDead;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public boolean getAlwaysRenderNameTagForRender() {
      return false;
   }

   public void onItemPickup(Entity var1, int var2) {
      if (!var1.isDead && !this.worldObj.isRemote) {
         EntityTracker var3 = ((WorldServer)this.worldObj).getEntityTracker();
         if (var1 instanceof EntityItem) {
            var3.sendToAllTrackingEntity(var1, new S0DPacketCollectItem(var1.getEntityId(), this.getEntityId()));
         }

         if (var1 instanceof EntityArrow) {
            var3.sendToAllTrackingEntity(var1, new S0DPacketCollectItem(var1.getEntityId(), this.getEntityId()));
         }

         if (var1 instanceof EntityXPOrb) {
            var3.sendToAllTrackingEntity(var1, new S0DPacketCollectItem(var1.getEntityId(), this.getEntityId()));
         }
      }

   }

   protected void updatePotionEffects() {
      Iterator var1 = this.activePotionsMap.keySet().iterator();

      while(var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
         if (!var3.onUpdate(this)) {
            if (!this.worldObj.isRemote) {
               var1.remove();
               this.onFinishedPotionEffect(var3);
            }
         } else if (var3.getDuration() % 600 == 0) {
            this.onChangedPotionEffect(var3, false);
         }
      }

      if (this.potionsNeedUpdate) {
         if (!this.worldObj.isRemote) {
            this.func_175135_B();
         }

         this.potionsNeedUpdate = false;
      }

      int var11 = this.dataWatcher.getWatchableObjectInt(7);
      boolean var12 = this.dataWatcher.getWatchableObjectByte(8) > 0;
      if (var11 > 0) {
         boolean var4 = false;
         if (!this.isInvisible()) {
            var4 = this.rand.nextBoolean();
         } else {
            var4 = this.rand.nextInt(15) == 0;
         }

         if (var12) {
            var4 &= this.rand.nextInt(5) == 0;
         }

         if (var4 && var11 > 0) {
            double var5 = (double)(var11 >> 16 & 255) / 255.0D;
            double var7 = (double)(var11 >> 8 & 255) / 255.0D;
            double var9 = (double)(var11 >> 0 & 255) / 255.0D;
            this.worldObj.spawnParticle(var12 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, var5, var7, var9);
         }
      }

   }

   protected void func_175135_B() {
      if (this.activePotionsMap.isEmpty()) {
         this.func_175133_bi();
         this.setInvisible(false);
      } else {
         int var1 = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
         this.dataWatcher.updateObject(8, (byte)(PotionHelper.func_82817_b(this.activePotionsMap.values()) ? 1 : 0));
         this.dataWatcher.updateObject(7, var1);
         this.setInvisible(this.isPotionActive(Potion.invisibility.id));
      }

   }

   public boolean isEntityAlive() {
      return !this.isDead && this.getHealth() > 0.0F;
   }

   protected void jump() {
      this.motionY = (double)this.func_175134_bD();
      if (this.isPotionActive(Potion.jump)) {
         this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
      }

      if (this.isSprinting()) {
         float var1 = this.rotationYaw * 0.017453292F;
         this.motionX -= (double)(MathHelper.sin(var1) * 0.2F);
         this.motionZ += (double)(MathHelper.cos(var1) * 0.2F);
      }

      this.isAirBorne = true;
   }

   public boolean attackEntityAsMob(Entity var1) {
      this.setLastAttacker(var1);
      return false;
   }

   protected boolean func_146066_aG() {
      return !this.isChild();
   }

   protected void onChangedPotionEffect(PotionEffect var1, boolean var2) {
      this.potionsNeedUpdate = true;
      if (var2 && !this.worldObj.isRemote) {
         Potion.potionTypes[var1.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), var1.getAmplifier());
         Potion.potionTypes[var1.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), var1.getAmplifier());
      }

   }

   public boolean isOnLadder() {
      int var1 = MathHelper.floor_double(this.posX);
      int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
      int var3 = MathHelper.floor_double(this.posZ);
      Block var4 = this.worldObj.getBlockState(new BlockPos(var1, var2, var3)).getBlock();
      return (var4 == Blocks.ladder || var4 == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).func_175149_v());
   }

   public abstract ItemStack getEquipmentInSlot(int var1);

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEFINED;
   }

   public PotionEffect getActivePotionEffect(Potion var1) {
      return (PotionEffect)this.activePotionsMap.get(var1.id);
   }

   public void func_152111_bt() {
   }

   public void addPotionEffect(PotionEffect var1) {
      if (this.isPotionApplicable(var1)) {
         if (this.activePotionsMap.containsKey(var1.getPotionID())) {
            ((PotionEffect)this.activePotionsMap.get(var1.getPotionID())).combine(var1);
            this.onChangedPotionEffect((PotionEffect)this.activePotionsMap.get(var1.getPotionID()), true);
         } else {
            this.activePotionsMap.put(var1.getPotionID(), var1);
            this.onNewPotionEffect(var1);
         }
      }

   }

   public void setRotationYawHead(float var1) {
      this.rotationYawHead = var1;
   }

   protected String getHurtSound() {
      return "game.neutral.hurt";
   }

   public Vec3 getLook(float var1) {
      if (var1 == 1.0F) {
         return this.func_174806_f(this.rotationPitch, this.rotationYawHead);
      } else {
         float var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * var1;
         float var3 = this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * var1;
         return this.func_174806_f(var2, var3);
      }
   }

   public void dismountEntity(Entity var1) {
      double var2 = var1.posX;
      double var4 = var1.getEntityBoundingBox().minY + (double)var1.height;
      double var6 = var1.posZ;
      byte var8 = 1;

      for(int var9 = -var8; var9 <= var8; ++var9) {
         for(int var10 = -var8; var10 < var8; ++var10) {
            if (var9 != 0 || var10 != 0) {
               int var11 = (int)(this.posX + (double)var9);
               int var12 = (int)(this.posZ + (double)var10);
               AxisAlignedBB var13 = this.getEntityBoundingBox().offset((double)var9, 1.0D, (double)var10);
               if (this.worldObj.func_147461_a(var13).isEmpty()) {
                  if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var11, (int)this.posY, var12))) {
                     this.setPositionAndUpdate(this.posX + (double)var9, this.posY + 1.0D, this.posZ + (double)var10);
                     return;
                  }

                  if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var11, (int)this.posY - 1, var12)) || this.worldObj.getBlockState(new BlockPos(var11, (int)this.posY - 1, var12)).getBlock().getMaterial() == Material.water) {
                     var2 = this.posX + (double)var9;
                     var4 = this.posY + 1.0D;
                     var6 = this.posZ + (double)var10;
                  }
               }
            }
         }
      }

      this.setPositionAndUpdate(var2, var4, var6);
   }

   protected float applyPotionDamageCalculations(DamageSource var1, float var2) {
      if (var1.isDamageAbsolute()) {
         return var2;
      } else {
         int var3;
         int var4;
         float var5;
         if (this.isPotionActive(Potion.resistance) && var1 != DamageSource.outOfWorld) {
            var3 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            var4 = 25 - var3;
            var5 = var2 * (float)var4;
            var2 = var5 / 25.0F;
         }

         if (var2 <= 0.0F) {
            return 0.0F;
         } else {
            var3 = EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), var1);
            if (var3 > 20) {
               var3 = 20;
            }

            if (var3 > 0 && var3 <= 20) {
               var4 = 25 - var3;
               var5 = var2 * (float)var4;
               var2 = var5 / 25.0F;
            }

            return var2;
         }
      }
   }

   public EntityLivingBase getLastAttacker() {
      return this.lastAttacker;
   }

   public void setJumping(boolean var1) {
      this.isJumping = var1;
   }

   protected void dropFewItems(boolean var1, int var2) {
   }

   public void handleHealthUpdate(byte var1) {
      String var2;
      if (var1 == 2) {
         this.limbSwingAmount = 1.5F;
         this.hurtResistantTime = this.maxHurtResistantTime;
         this.hurtTime = this.maxHurtTime = 10;
         this.attackedAtYaw = 0.0F;
         var2 = this.getHurtSound();
         if (var2 != null) {
            this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         }

         this.attackEntityFrom(DamageSource.generic, 0.0F);
      } else if (var1 == 3) {
         var2 = this.getDeathSound();
         if (var2 != null) {
            this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         }

         this.setHealth(0.0F);
         this.onDeath(DamageSource.generic);
      } else {
         super.handleHealthUpdate(var1);
      }

   }

   public EntityLivingBase func_94060_bK() {
      return (EntityLivingBase)(this._combatTracker.func_94550_c() != null ? this._combatTracker.func_94550_c() : (this.attackingPlayer != null ? this.attackingPlayer : (this.entityLivingToAttack != null ? this.entityLivingToAttack : null)));
   }

   protected float func_110146_f(float var1, float var2) {
      float var3 = MathHelper.wrapAngleTo180_float(var1 - this.renderYawOffset);
      this.renderYawOffset += var3 * 0.3F;
      float var4 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
      boolean var5 = var4 < -90.0F || var4 >= 90.0F;
      if (var4 < -75.0F) {
         var4 = -75.0F;
      }

      if (var4 >= 75.0F) {
         var4 = 75.0F;
      }

      this.renderYawOffset = this.rotationYaw - var4;
      if (var4 * var4 > 2500.0F) {
         this.renderYawOffset += var4 * 0.2F;
      }

      if (var5) {
         var2 *= -1.0F;
      }

      return var2;
   }

   public void func_180426_a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.newPosX = var1;
      this.newPosY = var3;
      this.newPosZ = var5;
      this.newRotationYaw = (double)var7;
      this.newRotationPitch = (double)var8;
      this.newPosRotationIncrements = var9;
   }

   public EntityLivingBase(World var1) {
      super(var1);
      this.applyEntityAttributes();
      this.setHealth(this.getMaxHealth());
      this.preventEntitySpawning = true;
      this.field_70770_ap = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.field_70769_ao = (float)Math.random() * 12398.0F;
      this.rotationYaw = (float)(Math.random() * 3.141592653589793D * 2.0D);
      this.rotationYawHead = this.rotationYaw;
      this.stepHeight = 0.6F;
   }

   public abstract ItemStack getCurrentArmor(int var1);

   public int getTotalArmorValue() {
      int var1 = 0;
      ItemStack[] var2 = this.getInventory();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ItemStack var5 = var2[var4];
         if (var5 != null && var5.getItem() instanceof ItemArmor) {
            int var6 = ((ItemArmor)var5.getItem()).damageReduceAmount;
            var1 += var6;
         }
      }

      return var1;
   }

   protected void kill() {
      this.attackEntityFrom(DamageSource.outOfWorld, 4.0F);
   }

   public void updateRidden() {
      super.updateRidden();
      this.field_70768_au = this.field_110154_aX;
      this.field_110154_aX = 0.0F;
      this.fallDistance = 0.0F;
   }

   protected void updateEntityActionState() {
   }

   protected void onNewPotionEffect(PotionEffect var1) {
      this.potionsNeedUpdate = true;
      if (!this.worldObj.isRemote) {
         Potion.potionTypes[var1.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), var1.getAmplifier());
      }

   }

   public void setRevengeTarget(EntityLivingBase var1) {
      this.entityLivingToAttack = var1;
      this.revengeTimer = this.ticksExisted;
   }

   protected float applyArmorCalculations(DamageSource var1, float var2) {
      if (!var1.isUnblockable()) {
         int var3 = 25 - this.getTotalArmorValue();
         float var4 = var2 * (float)var3;
         this.damageArmor(var2);
         var2 = var4 / 25.0F;
      }

      return var2;
   }

   protected void addRandomArmor() {
   }

   public void renderBrokenItemStack(ItemStack var1) {
      this.playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);

      for(int var2 = 0; var2 < 5; ++var2) {
         Vec3 var3 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         var3 = var3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
         var3 = var3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
         double var4 = (double)(-this.rand.nextFloat()) * 0.6D - 0.3D;
         Vec3 var6 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.3D, var4, 0.6D);
         var6 = var6.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
         var6 = var6.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
         var6 = var6.addVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
         this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var6.xCoord, var6.yCoord, var6.zCoord, var3.xCoord, var3.yCoord + 0.05D, var3.zCoord, Item.getIdFromItem(var1.getItem()));
      }

   }

   public IAttributeInstance getEntityAttribute(IAttribute var1) {
      return this.getAttributeMap().getAttributeInstance(var1);
   }

   public void setSprinting(boolean var1) {
      super.setSprinting(var1);
      IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      if (var2.getModifier(sprintingSpeedBoostModifierUUID) != null) {
         var2.removeModifier(sprintingSpeedBoostModifier);
      }

      if (var1) {
         var2.applyModifier(sprintingSpeedBoostModifier);
      }

   }

   public void setHealth(float var1) {
      this.dataWatcher.updateObject(6, MathHelper.clamp_float(var1, 0.0F, this.getMaxHealth()));
   }

   protected float func_175134_bD() {
      return 0.42F;
   }

   public boolean canEntityBeSeen(Entity var1) {
      return this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null;
   }

   public final float getMaxHealth() {
      return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
   }

   protected void onFinishedPotionEffect(PotionEffect var1) {
      this.potionsNeedUpdate = true;
      if (!this.worldObj.isRemote) {
         Potion.potionTypes[var1.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), var1.getAmplifier());
      }

   }

   protected void damageArmor(float var1) {
   }

   public float getAIMoveSpeed() {
      return this.landMovementFactor;
   }

   protected void onDeathUpdate() {
      ++this.deathTime;
      if (this.deathTime == 20) {
         int var1;
         if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
            var1 = this.getExperiencePoints(this.attackingPlayer);

            while(var1 > 0) {
               int var2 = EntityXPOrb.getXPSplit(var1);
               var1 -= var2;
               this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
            }
         }

         this.setDead();

         for(var1 = 0; var1 < 20; ++var1) {
            double var8 = this.rand.nextGaussian() * 0.02D;
            double var4 = this.rand.nextGaussian() * 0.02D;
            double var6 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var8, var4, var6);
         }
      }

   }

   protected void entityInit() {
      this.dataWatcher.addObject(7, 0);
      this.dataWatcher.addObject(8, (byte)0);
      this.dataWatcher.addObject(9, (byte)0);
      this.dataWatcher.addObject(6, 1.0F);
   }

   public float getSwingProgress(float var1) {
      float var2 = this.swingProgress - this.prevSwingProgress;
      if (var2 < 0.0F) {
         ++var2;
      }

      return this.prevSwingProgress + var2 * var1;
   }

   public void fall(float var1, float var2) {
      super.fall(var1, var2);
      PotionEffect var3 = this.getActivePotionEffect(Potion.jump);
      float var4 = var3 != null ? (float)(var3.getAmplifier() + 1) : 0.0F;
      int var5 = MathHelper.ceiling_float_int((var1 - 3.0F - var4) * var2);
      if (var5 > 0) {
         this.playSound(this.func_146067_o(var5), 1.0F, 1.0F);
         this.attackEntityFrom(DamageSource.fall, (float)var5);
         int var6 = MathHelper.floor_double(this.posX);
         int var7 = MathHelper.floor_double(this.posY - 0.20000000298023224D);
         int var8 = MathHelper.floor_double(this.posZ);
         Block var9 = this.worldObj.getBlockState(new BlockPos(var6, var7, var8)).getBlock();
         if (var9.getMaterial() != Material.air) {
            Block.SoundType var10 = var9.stepSound;
            this.playSound(var10.getStepSound(), var10.getVolume() * 0.5F, var10.getFrequency() * 0.75F);
         }
      }

   }

   public abstract ItemStack getHeldItem();

   public float getRotationYawHead() {
      return this.rotationYawHead;
   }

   public boolean isOnTeam(Team var1) {
      return this.getTeam() != null ? this.getTeam().isSameTeam(var1) : false;
   }

   public int getAge() {
      return this.entityAge;
   }

   public boolean isServerWorld() {
      return !this.worldObj.isRemote;
   }

   public final int getArrowCountInEntity() {
      return this.dataWatcher.getWatchableObjectByte(9);
   }

   public void heal(float var1) {
      float var2 = this.getHealth();
      if (var2 > 0.0F) {
         this.setHealth(var2 + var1);
      }

   }

   public void clearActivePotions() {
      Iterator var1 = this.activePotionsMap.keySet().iterator();

      while(var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
         if (!this.worldObj.isRemote) {
            var1.remove();
            this.onFinishedPotionEffect(var3);
         }
      }

   }

   protected void func_180466_bG() {
      this.motionY += 0.03999999910593033D;
   }

   private int getArmSwingAnimationEnd() {
      return this.isPotionActive(Potion.digSpeed) ? 6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1 : (this.isPotionActive(Potion.digSlowdown) ? 6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6);
   }

   public abstract void setCurrentItemOrArmor(int var1, ItemStack var2);

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setFloat("HealF", this.getHealth());
      var1.setShort("Health", (short)((int)Math.ceil((double)this.getHealth())));
      var1.setShort("HurtTime", (short)this.hurtTime);
      var1.setInteger("HurtByTimestamp", this.revengeTimer);
      var1.setShort("DeathTime", (short)this.deathTime);
      var1.setFloat("AbsorptionAmount", this.getAbsorptionAmount());
      ItemStack[] var2 = this.getInventory();
      int var3 = var2.length;

      int var4;
      ItemStack var5;
      for(var4 = 0; var4 < var3; ++var4) {
         var5 = var2[var4];
         if (var5 != null) {
            this.attributeMap.removeAttributeModifiers(var5.getAttributeModifiers());
         }
      }

      var1.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));
      var2 = this.getInventory();
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         var5 = var2[var4];
         if (var5 != null) {
            this.attributeMap.applyAttributeModifiers(var5.getAttributeModifiers());
         }
      }

      if (!this.activePotionsMap.isEmpty()) {
         NBTTagList var6 = new NBTTagList();
         Iterator var7 = this.activePotionsMap.values().iterator();

         while(var7.hasNext()) {
            PotionEffect var8 = (PotionEffect)var7.next();
            var6.appendTag(var8.writeCustomPotionEffectToNBT(new NBTTagCompound()));
         }

         var1.setTag("ActiveEffects", var6);
      }

   }

   protected void func_175136_bO() {
      this.potionsNeedUpdate = true;
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      this.setAbsorptionAmount(var1.getFloat("AbsorptionAmount"));
      if (var1.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
         SharedMonsterAttributes.func_151475_a(this.getAttributeMap(), var1.getTagList("Attributes", 10));
      }

      if (var1.hasKey("ActiveEffects", 9)) {
         NBTTagList var2 = var1.getTagList("ActiveEffects", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
            if (var5 != null) {
               this.activePotionsMap.put(var5.getPotionID(), var5);
            }
         }
      }

      if (var1.hasKey("HealF", 99)) {
         this.setHealth(var1.getFloat("HealF"));
      } else {
         NBTBase var6 = var1.getTag("Health");
         if (var6 == null) {
            this.setHealth(this.getMaxHealth());
         } else if (var6.getId() == 5) {
            this.setHealth(((NBTTagFloat)var6).getFloat());
         } else if (var6.getId() == 2) {
            this.setHealth((float)((NBTTagShort)var6).getShort());
         }
      }

      this.hurtTime = var1.getShort("HurtTime");
      this.deathTime = var1.getShort("DeathTime");
      this.revengeTimer = var1.getInteger("HurtByTimestamp");
   }

   public final void setArrowCountInEntity(int var1) {
      this.dataWatcher.updateObject(9, (byte)var1);
   }

   protected String func_146067_o(int var1) {
      return var1 > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
   }

   public void removePotionEffect(int var1) {
      PotionEffect var2 = (PotionEffect)this.activePotionsMap.remove(var1);
      if (var2 != null) {
         this.onFinishedPotionEffect(var2);
      }

   }

   protected int getExperiencePoints(EntityPlayer var1) {
      return 0;
   }

   protected boolean isPlayer() {
      return false;
   }

   public void knockBack(Entity var1, float var2, double var3, double var5) {
      if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
         this.isAirBorne = true;
         float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
         float var8 = 0.4F;
         this.motionX /= 2.0D;
         this.motionY /= 2.0D;
         this.motionZ /= 2.0D;
         this.motionX -= var3 / (double)var7 * (double)var8;
         this.motionY += (double)var8;
         this.motionZ -= var5 / (double)var7 * (double)var8;
         if (this.motionY > 0.4000000059604645D) {
            this.motionY = 0.4000000059604645D;
         }
      }

   }

   public void swingItem() {
      if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
         this.swingProgressInt = -1;
         this.isSwingInProgress = true;
         if (this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, 0));
         }
      }

   }

   public boolean isChild() {
      return false;
   }

   public void onEntityUpdate() {
      this.prevSwingProgress = this.swingProgress;
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("livingEntityBaseTick");
      boolean var1 = this instanceof EntityPlayer;
      if (this.isEntityAlive()) {
         if (this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(DamageSource.inWall, 1.0F);
         } else if (var1 && !this.worldObj.getWorldBorder().contains(this.getEntityBoundingBox())) {
            double var2 = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
            if (var2 < 0.0D) {
               this.attackEntityFrom(DamageSource.inWall, (float)Math.max(1, MathHelper.floor_double(-var2 * this.worldObj.getWorldBorder().func_177727_n())));
            }
         }
      }

      if (this.isImmuneToFire() || this.worldObj.isRemote) {
         this.extinguish();
      }

      boolean var7 = var1 && ((EntityPlayer)this).capabilities.disableDamage;
      if (this.isEntityAlive() && this.isInsideOfMaterial(Material.water)) {
         if (!this.canBreatheUnderwater() && !this.isPotionActive(Potion.waterBreathing.id) && !var7) {
            this.setAir(this.decreaseAirSupply(this.getAir()));
            if (this.getAir() == -20) {
               this.setAir(0);

               for(int var3 = 0; var3 < 8; ++var3) {
                  float var4 = this.rand.nextFloat() - this.rand.nextFloat();
                  float var5 = this.rand.nextFloat() - this.rand.nextFloat();
                  float var6 = this.rand.nextFloat() - this.rand.nextFloat();
                  this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (double)var4, this.posY + (double)var5, this.posZ + (double)var6, this.motionX, this.motionY, this.motionZ);
               }

               this.attackEntityFrom(DamageSource.drown, 2.0F);
            }
         }

         if (!this.worldObj.isRemote && this.isRiding() && this.ridingEntity instanceof EntityLivingBase) {
            this.mountEntity((Entity)null);
         }
      } else {
         this.setAir(300);
      }

      if (this.isEntityAlive() && this.isWet()) {
         this.extinguish();
      }

      this.prevCameraPitch = this.cameraPitch;
      if (this.hurtTime > 0) {
         --this.hurtTime;
      }

      if (this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP)) {
         --this.hurtResistantTime;
      }

      if (this.getHealth() <= 0.0F) {
         this.onDeathUpdate();
      }

      if (this.recentlyHit > 0) {
         --this.recentlyHit;
      } else {
         this.attackingPlayer = null;
      }

      if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
         this.lastAttacker = null;
      }

      if (this.entityLivingToAttack != null) {
         if (!this.entityLivingToAttack.isEntityAlive()) {
            this.setRevengeTarget((EntityLivingBase)null);
         } else if (this.ticksExisted - this.revengeTimer > 100) {
            this.setRevengeTarget((EntityLivingBase)null);
         }
      }

      this.updatePotionEffects();
      this.field_70763_ax = this.field_70764_aw;
      this.prevRenderYawOffset = this.renderYawOffset;
      this.prevRotationYawHead = this.rotationYawHead;
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      this.worldObj.theProfiler.endSection();
   }

   public void setLastAttacker(Entity var1) {
      if (var1 instanceof EntityLivingBase) {
         this.lastAttacker = (EntityLivingBase)var1;
      } else {
         this.lastAttacker = null;
      }

      this.lastAttackerTime = this.ticksExisted;
   }

   public boolean isPotionActive(Potion var1) {
      return this.activePotionsMap.containsKey(var1.id);
   }

   protected void collideWithNearbyEntities() {
      List var1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
      if (var1 != null && !var1.isEmpty()) {
         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Entity var3 = (Entity)var1.get(var2);
            if (var3.canBePushed()) {
               this.collideWithEntity(var3);
            }
         }
      }

   }

   public Vec3 getLookVec() {
      return this.getLook(1.0F);
   }

   protected void collideWithEntity(Entity var1) {
      var1.applyEntityCollision(this);
   }

   public void onDeath(DamageSource var1) {
      Entity var2 = var1.getEntity();
      EntityLivingBase var3 = this.func_94060_bK();
      if (this.scoreValue >= 0 && var3 != null) {
         var3.addToPlayerScore(this, this.scoreValue);
      }

      if (var2 != null) {
         var2.onKillEntity(this);
      }

      this.dead = true;
      this.getCombatTracker().func_94549_h();
      if (!this.worldObj.isRemote) {
         int var4 = 0;
         if (var2 instanceof EntityPlayer) {
            var4 = EnchantmentHelper.getLootingModifier((EntityLivingBase)var2);
         }

         if (this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
            this.dropFewItems(this.recentlyHit > 0, var4);
            this.dropEquipment(this.recentlyHit > 0, var4);
            if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + (float)var4 * 0.01F) {
               this.addRandomArmor();
            }
         }
      }

      this.worldObj.setEntityState(this, (byte)3);
   }

   public final float getHealth() {
      return this.dataWatcher.getWatchableObjectFloat(6);
   }

   protected void dropEquipment(boolean var1, int var2) {
   }

   public Team getTeam() {
      return this.worldObj.getScoreboard().getPlayersTeam(this.getUniqueID().toString());
   }

   public float getAbsorptionAmount() {
      return this.field_110151_bq;
   }

   public Random getRNG() {
      return this.rand;
   }

   public void performHurtAnimation() {
      this.hurtTime = this.maxHurtTime = 10;
      this.attackedAtYaw = 0.0F;
   }

   protected float getSoundPitch() {
      return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
   }

   public boolean isEntityUndead() {
      return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
   }

   public int getRevengeTimer() {
      return this.revengeTimer;
   }

   protected void applyEntityAttributes() {
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
   }

   public Collection getActivePotionEffects() {
      return this.activePotionsMap.values();
   }

   public boolean isPotionApplicable(PotionEffect var1) {
      if (this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
         int var2 = var1.getPotionID();
         if (var2 == Potion.regeneration.id || var2 == Potion.poison.id) {
            return false;
         }
      }

      return true;
   }

   public int getLastAttackerTime() {
      return this.lastAttackerTime;
   }
}
