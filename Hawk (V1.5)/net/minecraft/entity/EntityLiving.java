package net.minecraft.entity;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.BlockPosM;
import optifine.Config;
import optifine.Reflector;

public abstract class EntityLiving extends EntityLivingBase {
   public BiomeGenBase spawnBiome = null;
   public int livingSoundTime;
   private boolean canPickUpLoot;
   protected EntityMoveHelper moveHelper;
   private EntityLookHelper lookHelper;
   public int randomMobsId = 0;
   protected float[] equipmentDropChances = new float[5];
   private ItemStack[] equipment = new ItemStack[5];
   private EntityBodyHelper bodyHelper;
   private static final String __OBFID = "CL_00001550";
   private boolean isLeashed;
   private boolean persistenceRequired;
   protected int experienceValue;
   protected EntityJumpHelper jumpHelper;
   protected PathNavigate navigator;
   protected final EntityAITasks targetTasks;
   private NBTTagCompound leashNBTTag;
   private Entity leashedToEntity;
   protected final EntityAITasks tasks;
   public BlockPos spawnPosition = null;
   private EntitySenses senses;
   private EntityLivingBase attackTarget;

   public void setMoveForward(float var1) {
      this.moveForward = var1;
   }

   public void setAttackTarget(EntityLivingBase var1) {
      this.attackTarget = var1;
      Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, var1);
   }

   public boolean isNoDespawnRequired() {
      return this.persistenceRequired;
   }

   public void spawnExplosionParticle() {
      if (this.worldObj.isRemote) {
         for(int var1 = 0; var1 < 20; ++var1) {
            double var2 = this.rand.nextGaussian() * 0.02D;
            double var4 = this.rand.nextGaussian() * 0.02D;
            double var6 = this.rand.nextGaussian() * 0.02D;
            double var8 = 10.0D;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var2 * var8, this.posY + (double)(this.rand.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var6 * var8, var2, var4, var6);
         }
      } else {
         this.worldObj.setEntityState(this, (byte)20);
      }

   }

   protected void setNoAI(boolean var1) {
      this.dataWatcher.updateObject(15, (byte)(var1 ? 1 : 0));
   }

   public EntityMoveHelper getMoveHelper() {
      return this.moveHelper;
   }

   public boolean allowLeashing() {
      return !this.getLeashed() && !(this instanceof IMob);
   }

   public void eatGrassBonus() {
   }

   public void playLivingSound() {
      String var1 = this.getLivingSound();
      if (var1 != null) {
         this.playSound(var1, this.getSoundVolume(), this.getSoundPitch());
      }

   }

   public float getRenderSizeModifier() {
      return 1.0F;
   }

   protected void updateLeashedState() {
      if (this.leashNBTTag != null) {
         this.recreateLeash();
      }

      if (this.isLeashed) {
         if (!this.isEntityAlive()) {
            this.clearLeashed(true, true);
         }

         if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
            this.clearLeashed(true, true);
         }
      }

   }

   public ItemStack getEquipmentInSlot(int var1) {
      return this.equipment[var1];
   }

   public void setAIMoveSpeed(float var1) {
      super.setAIMoveSpeed(var1);
      this.setMoveForward(var1);
   }

   public boolean handleLavaMovement() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
   }

   public void onUpdate() {
      if (Config.isSmoothWorld() && this.canSkipUpdate()) {
         this.onUpdateMinimal();
      } else {
         super.onUpdate();
         if (!this.worldObj.isRemote) {
            this.updateLeashedState();
         }
      }

   }

   public void enablePersistence() {
      this.persistenceRequired = true;
   }

   public static Item getArmorItemForSlot(int var0, int var1) {
      switch(var0) {
      case 4:
         if (var1 == 0) {
            return Items.leather_helmet;
         } else if (var1 == 1) {
            return Items.golden_helmet;
         } else if (var1 == 2) {
            return Items.chainmail_helmet;
         } else if (var1 == 3) {
            return Items.iron_helmet;
         } else if (var1 == 4) {
            return Items.diamond_helmet;
         }
      case 3:
         if (var1 == 0) {
            return Items.leather_chestplate;
         } else if (var1 == 1) {
            return Items.golden_chestplate;
         } else if (var1 == 2) {
            return Items.chainmail_chestplate;
         } else if (var1 == 3) {
            return Items.iron_chestplate;
         } else if (var1 == 4) {
            return Items.diamond_chestplate;
         }
      case 2:
         if (var1 == 0) {
            return Items.leather_leggings;
         } else if (var1 == 1) {
            return Items.golden_leggings;
         } else if (var1 == 2) {
            return Items.chainmail_leggings;
         } else if (var1 == 3) {
            return Items.iron_leggings;
         } else if (var1 == 4) {
            return Items.diamond_leggings;
         }
      case 1:
         if (var1 == 0) {
            return Items.leather_boots;
         } else if (var1 == 1) {
            return Items.golden_boots;
         } else if (var1 == 2) {
            return Items.chainmail_boots;
         } else if (var1 == 3) {
            return Items.iron_boots;
         } else if (var1 == 4) {
            return Items.diamond_boots;
         }
      default:
         return null;
      }
   }

   public void handleHealthUpdate(byte var1) {
      if (var1 == 20) {
         this.spawnExplosionParticle();
      } else {
         super.handleHealthUpdate(var1);
      }

   }

   public boolean canBeSteered() {
      return false;
   }

   public void faceEntity(Entity var1, float var2, float var3) {
      double var4 = var1.posX - this.posX;
      double var6 = var1.posZ - this.posZ;
      double var8;
      if (var1 instanceof EntityLivingBase) {
         EntityLivingBase var10 = (EntityLivingBase)var1;
         var8 = var10.posY + (double)var10.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
      } else {
         var8 = (var1.getEntityBoundingBox().minY + var1.getEntityBoundingBox().maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
      }

      double var14 = (double)MathHelper.sqrt_double(var4 * var4 + var6 * var6);
      float var12 = (float)(Math.atan2(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      float var13 = (float)(-(Math.atan2(var8, var14) * 180.0D / 3.141592653589793D));
      this.rotationPitch = this.updateRotation(this.rotationPitch, var13, var3);
      this.rotationYaw = this.updateRotation(this.rotationYaw, var12, var2);
   }

   public EntityLiving(World var1) {
      super(var1);
      this.tasks = new EntityAITasks(var1 != null && var1.theProfiler != null ? var1.theProfiler : null);
      this.targetTasks = new EntityAITasks(var1 != null && var1.theProfiler != null ? var1.theProfiler : null);
      this.lookHelper = new EntityLookHelper(this);
      this.moveHelper = new EntityMoveHelper(this);
      this.jumpHelper = new EntityJumpHelper(this);
      this.bodyHelper = new EntityBodyHelper(this);
      this.navigator = this.func_175447_b(var1);
      this.senses = new EntitySenses(this);

      for(int var2 = 0; var2 < this.equipmentDropChances.length; ++var2) {
         this.equipmentDropChances[var2] = 0.085F;
      }

      UUID var5 = this.getUniqueID();
      long var3 = var5.getLeastSignificantBits();
      this.randomMobsId = (int)(var3 & 2147483647L);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.worldObj.theProfiler.startSection("looting");
      if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
         List var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D));
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            EntityItem var3 = (EntityItem)var2.next();
            if (!var3.isDead && var3.getEntityItem() != null && !var3.func_174874_s()) {
               this.func_175445_a(var3);
            }
         }
      }

      this.worldObj.theProfiler.endSection();
   }

   protected void despawnEntity() {
      Object var1 = null;
      Object var2 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
      Object var3 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
      if (this.persistenceRequired) {
         this.entityAge = 0;
      } else if ((this.entityAge & 31) == 31 && (var1 = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, this)) != var2) {
         if (var1 == var3) {
            this.entityAge = 0;
         } else {
            this.setDead();
         }
      } else {
         EntityPlayer var4 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
         if (var4 != null) {
            double var5 = var4.posX - this.posX;
            double var7 = var4.posY - this.posY;
            double var9 = var4.posZ - this.posZ;
            double var11 = var5 * var5 + var7 * var7 + var9 * var9;
            if (this.canDespawn() && var11 > 16384.0D) {
               this.setDead();
            }

            if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && var11 > 1024.0D && this.canDespawn()) {
               this.setDead();
            } else if (var11 < 1024.0D) {
               this.entityAge = 0;
            }
         }
      }

   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setBoolean("CanPickUpLoot", this.canPickUpLoot());
      var1.setBoolean("PersistenceRequired", this.persistenceRequired);
      NBTTagList var2 = new NBTTagList();

      NBTTagCompound var3;
      for(int var4 = 0; var4 < this.equipment.length; ++var4) {
         var3 = new NBTTagCompound();
         if (this.equipment[var4] != null) {
            this.equipment[var4].writeToNBT(var3);
         }

         var2.appendTag(var3);
      }

      var1.setTag("Equipment", var2);
      NBTTagList var6 = new NBTTagList();

      for(int var5 = 0; var5 < this.equipmentDropChances.length; ++var5) {
         var6.appendTag(new NBTTagFloat(this.equipmentDropChances[var5]));
      }

      var1.setTag("DropChances", var6);
      var1.setBoolean("Leashed", this.isLeashed);
      if (this.leashedToEntity != null) {
         var3 = new NBTTagCompound();
         if (this.leashedToEntity instanceof EntityLivingBase) {
            var3.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
            var3.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
         } else if (this.leashedToEntity instanceof EntityHanging) {
            BlockPos var7 = ((EntityHanging)this.leashedToEntity).func_174857_n();
            var3.setInteger("X", var7.getX());
            var3.setInteger("Y", var7.getY());
            var3.setInteger("Z", var7.getZ());
         }

         var1.setTag("Leash", var3);
      }

      if (this.isAIDisabled()) {
         var1.setBoolean("NoAI", this.isAIDisabled());
      }

   }

   protected int getExperiencePoints(EntityPlayer var1) {
      if (this.experienceValue > 0) {
         int var2 = this.experienceValue;
         ItemStack[] var3 = this.getInventory();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if (var3[var4] != null && this.equipmentDropChances[var4] <= 1.0F) {
               var2 += 1 + this.rand.nextInt(3);
            }
         }

         return var2;
      } else {
         return this.experienceValue;
      }
   }

   public void setEquipmentDropChance(int var1, float var2) {
      this.equipmentDropChances[var1] = var2;
   }

   protected void func_180481_a(DifficultyInstance var1) {
      if (this.rand.nextFloat() < 0.15F * var1.func_180170_c()) {
         int var2 = this.rand.nextInt(2);
         float var3 = this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;
         if (this.rand.nextFloat() < 0.095F) {
            ++var2;
         }

         if (this.rand.nextFloat() < 0.095F) {
            ++var2;
         }

         if (this.rand.nextFloat() < 0.095F) {
            ++var2;
         }

         for(int var4 = 3; var4 >= 0; --var4) {
            ItemStack var5 = this.getCurrentArmor(var4);
            if (var4 < 3 && this.rand.nextFloat() < var3) {
               break;
            }

            if (var5 == null) {
               Item var6 = getArmorItemForSlot(var4 + 1, var2);
               if (var6 != null) {
                  this.setCurrentItemOrArmor(var4 + 1, new ItemStack(var6));
               }
            }
         }
      }

   }

   public EntityJumpHelper getJumpHelper() {
      return this.jumpHelper;
   }

   public boolean func_174820_d(int var1, ItemStack var2) {
      int var3;
      if (var1 == 99) {
         var3 = 0;
      } else {
         var3 = var1 - 100 + 1;
         if (var3 < 0 || var3 >= this.equipment.length) {
            return false;
         }
      }

      if (var2 != null && getArmorPosition(var2) != var3 && (var3 != 4 || !(var2.getItem() instanceof ItemBlock))) {
         return false;
      } else {
         this.setCurrentItemOrArmor(var3, var2);
         return true;
      }
   }

   private void onUpdateMinimal() {
      ++this.entityAge;
      if (this instanceof EntityMob) {
         float var1 = this.getBrightness(1.0F);
         if (var1 > 0.5F) {
            this.entityAge += 2;
         }
      }

      this.despawnEntity();
   }

   protected void updateAITasks() {
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   protected void dropEquipment(boolean var1, int var2) {
      for(int var3 = 0; var3 < this.getInventory().length; ++var3) {
         ItemStack var4 = this.getEquipmentInSlot(var3);
         boolean var5 = this.equipmentDropChances[var3] > 1.0F;
         if (var4 != null && (var1 || var5) && this.rand.nextFloat() - (float)var2 * 0.01F < this.equipmentDropChances[var3]) {
            if (!var5 && var4.isItemStackDamageable()) {
               int var6 = Math.max(var4.getMaxDamage() - 25, 1);
               int var7 = var4.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var6) + 1);
               if (var7 > var6) {
                  var7 = var6;
               }

               if (var7 < 1) {
                  var7 = 1;
               }

               var4.setItemDamage(var7);
            }

            this.entityDropItem(var4, 0.0F);
         }
      }

   }

   public void setCurrentItemOrArmor(int var1, ItemStack var2) {
      this.equipment[var1] = var2;
   }

   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateGround(this, var1);
   }

   public void clearLeashed(boolean var1, boolean var2) {
      if (this.isLeashed) {
         this.isLeashed = false;
         this.leashedToEntity = null;
         if (!this.worldObj.isRemote && var2) {
            this.dropItem(Items.lead, 1);
         }

         if (!this.worldObj.isRemote && var1 && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, (Entity)null));
         }
      }

   }

   public void setLeashedToEntity(Entity var1, boolean var2) {
      this.isLeashed = true;
      this.leashedToEntity = var1;
      if (!this.worldObj.isRemote && var2 && this.worldObj instanceof WorldServer) {
         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
      }

   }

   protected String getLivingSound() {
      return null;
   }

   public final boolean interactFirst(EntityPlayer var1) {
      if (this.getLeashed() && this.getLeashedToEntity() == var1) {
         this.clearLeashed(true, !var1.capabilities.isCreativeMode);
         return true;
      } else {
         ItemStack var2 = var1.inventory.getCurrentItem();
         if (var2 != null && var2.getItem() == Items.lead && this.allowLeashing()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
               this.setLeashedToEntity(var1, true);
               --var2.stackSize;
               return true;
            }

            if (((EntityTameable)this).func_152114_e(var1)) {
               this.setLeashedToEntity(var1, true);
               --var2.stackSize;
               return true;
            }
         }

         return this.interact(var1) ? true : super.interactFirst(var1);
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
   }

   public Entity getLeashedToEntity() {
      return this.leashedToEntity;
   }

   public int getMaxSpawnedInChunk() {
      return 4;
   }

   public ItemStack getHeldItem() {
      return this.equipment[0];
   }

   private float updateRotation(float var1, float var2, float var3) {
      float var4 = MathHelper.wrapAngleTo180_float(var2 - var1);
      if (var4 > var3) {
         var4 = var3;
      }

      if (var4 < -var3) {
         var4 = -var3;
      }

      return var1 + var4;
   }

   public boolean isServerWorld() {
      return super.isServerWorld() && !this.isAIDisabled();
   }

   protected Item getDropItem() {
      return null;
   }

   public boolean isEntityInsideOpaqueBlock() {
      if (this.noClip) {
         return false;
      } else {
         BlockPosM var1 = new BlockPosM(0, 0, 0);

         for(int var2 = 0; var2 < 8; ++var2) {
            double var3 = this.posX + (double)(((float)((var2 >> 0) % 2) - 0.5F) * this.width * 0.8F);
            double var5 = this.posY + (double)(((float)((var2 >> 1) % 2) - 0.5F) * 0.1F);
            double var7 = this.posZ + (double)(((float)((var2 >> 2) % 2) - 0.5F) * this.width * 0.8F);
            var1.setXyz(var3, var5 + (double)this.getEyeHeight(), var7);
            if (this.worldObj.getBlockState(var1).getBlock().isVisuallyOpaque()) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean canSkipUpdate() {
      if (this.isChild()) {
         return false;
      } else if (this.hurtTime > 0) {
         return false;
      } else if (this.ticksExisted < 20) {
         return false;
      } else {
         World var1 = this.getEntityWorld();
         if (var1 == null) {
            return false;
         } else if (var1.playerEntities.size() != 1) {
            return false;
         } else {
            Entity var2 = (Entity)var1.playerEntities.get(0);
            double var3 = Math.max(Math.abs(this.posX - var2.posX) - 16.0D, 0.0D);
            double var5 = Math.max(Math.abs(this.posZ - var2.posZ) - 16.0D, 0.0D);
            double var7 = var3 * var3 + var5 * var5;
            return !this.isInRangeToRenderDist(var7);
         }
      }
   }

   public EntityLookHelper getLookHelper() {
      return this.lookHelper;
   }

   public int getVerticalFaceSpeed() {
      return 40;
   }

   public PathNavigate getNavigator() {
      return this.navigator;
   }

   public IEntityLivingData func_180482_a(DifficultyInstance var1, IEntityLivingData var2) {
      this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
      return var2;
   }

   public boolean getLeashed() {
      return this.isLeashed;
   }

   protected float func_110146_f(float var1, float var2) {
      this.bodyHelper.updateRenderAngles();
      return var2;
   }

   public int getMaxFallHeight() {
      if (this.getAttackTarget() == null) {
         return 3;
      } else {
         int var1 = (int)(this.getHealth() - this.getMaxHealth() * 0.33F);
         var1 -= (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
         if (var1 < 0) {
            var1 = 0;
         }

         return var1 + 3;
      }
   }

   protected void dropFewItems(boolean var1, int var2) {
      Item var3 = this.getDropItem();
      if (var3 != null) {
         int var4 = this.rand.nextInt(3);
         if (var2 > 0) {
            var4 += this.rand.nextInt(var2 + 1);
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            this.dropItem(var3, 1);
         }
      }

   }

   public EntitySenses getEntitySenses() {
      return this.senses;
   }

   protected void func_175445_a(EntityItem var1) {
      ItemStack var2 = var1.getEntityItem();
      int var3 = getArmorPosition(var2);
      if (var3 > -1) {
         boolean var4 = true;
         ItemStack var5 = this.getEquipmentInSlot(var3);
         if (var5 != null) {
            if (var3 == 0) {
               if (var2.getItem() instanceof ItemSword && !(var5.getItem() instanceof ItemSword)) {
                  var4 = true;
               } else if (var2.getItem() instanceof ItemSword && var5.getItem() instanceof ItemSword) {
                  ItemSword var8 = (ItemSword)var2.getItem();
                  ItemSword var10 = (ItemSword)var5.getItem();
                  if (var8.func_150931_i() != var10.func_150931_i()) {
                     var4 = var8.func_150931_i() > var10.func_150931_i();
                  } else {
                     var4 = var2.getMetadata() > var5.getMetadata() || var2.hasTagCompound() && !var5.hasTagCompound();
                  }
               } else if (var2.getItem() instanceof ItemBow && var5.getItem() instanceof ItemBow) {
                  var4 = var2.hasTagCompound() && !var5.hasTagCompound();
               } else {
                  var4 = false;
               }
            } else if (var2.getItem() instanceof ItemArmor && !(var5.getItem() instanceof ItemArmor)) {
               var4 = true;
            } else if (var2.getItem() instanceof ItemArmor && var5.getItem() instanceof ItemArmor) {
               ItemArmor var6 = (ItemArmor)var2.getItem();
               ItemArmor var7 = (ItemArmor)var5.getItem();
               if (var6.damageReduceAmount != var7.damageReduceAmount) {
                  var4 = var6.damageReduceAmount > var7.damageReduceAmount;
               } else {
                  var4 = var2.getMetadata() > var5.getMetadata() || var2.hasTagCompound() && !var5.hasTagCompound();
               }
            } else {
               var4 = false;
            }
         }

         if (var4 && this.func_175448_a(var2)) {
            if (var5 != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[var3]) {
               this.entityDropItem(var5, 0.0F);
            }

            if (var2.getItem() == Items.diamond && var1.getThrower() != null) {
               EntityPlayer var9 = this.worldObj.getPlayerEntityByName(var1.getThrower());
               if (var9 != null) {
                  var9.triggerAchievement(AchievementList.diamondsToYou);
               }
            }

            this.setCurrentItemOrArmor(var3, var2);
            this.equipmentDropChances[var3] = 2.0F;
            this.persistenceRequired = true;
            this.onItemPickup(var1, 1);
            var1.setDead();
         }
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if (var1.hasKey("CanPickUpLoot", 1)) {
         this.setCanPickUpLoot(var1.getBoolean("CanPickUpLoot"));
      }

      this.persistenceRequired = var1.getBoolean("PersistenceRequired");
      NBTTagList var2;
      int var3;
      if (var1.hasKey("Equipment", 9)) {
         var2 = var1.getTagList("Equipment", 10);

         for(var3 = 0; var3 < this.equipment.length; ++var3) {
            this.equipment[var3] = ItemStack.loadItemStackFromNBT(var2.getCompoundTagAt(var3));
         }
      }

      if (var1.hasKey("DropChances", 9)) {
         var2 = var1.getTagList("DropChances", 5);

         for(var3 = 0; var3 < var2.tagCount(); ++var3) {
            this.equipmentDropChances[var3] = var2.getFloat(var3);
         }
      }

      this.isLeashed = var1.getBoolean("Leashed");
      if (this.isLeashed && var1.hasKey("Leash", 10)) {
         this.leashNBTTag = var1.getCompoundTag("Leash");
      }

      this.setNoAI(var1.getBoolean("NoAI"));
   }

   protected boolean canDespawn() {
      return true;
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(15, (byte)0);
   }

   public boolean canPickUpLoot() {
      return this.canPickUpLoot;
   }

   public void setCanPickUpLoot(boolean var1) {
      this.canPickUpLoot = var1;
   }

   public EntityLivingBase getAttackTarget() {
      return this.attackTarget;
   }

   public boolean canAttackClass(Class var1) {
      return var1 != EntityGhast.class;
   }

   protected boolean func_175448_a(ItemStack var1) {
      return true;
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("mobBaseTick");
      if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
         this.livingSoundTime = -this.getTalkInterval();
         this.playLivingSound();
      }

      this.worldObj.theProfiler.endSection();
   }

   public int getTalkInterval() {
      return 80;
   }

   protected void func_180483_b(DifficultyInstance var1) {
      float var2 = var1.func_180170_c();
      if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25F * var2) {
         EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0F + var2 * (float)this.rand.nextInt(18)));
      }

      for(int var3 = 0; var3 < 4; ++var3) {
         ItemStack var4 = this.getCurrentArmor(var3);
         if (var4 != null && this.rand.nextFloat() < 0.5F * var2) {
            EnchantmentHelper.addRandomEnchantment(this.rand, var4, (int)(5.0F + var2 * (float)this.rand.nextInt(18)));
         }
      }

   }

   public ItemStack getCurrentArmor(int var1) {
      return this.equipment[var1 + 1];
   }

   protected final void updateEntityActionState() {
      ++this.entityAge;
      this.worldObj.theProfiler.startSection("checkDespawn");
      this.despawnEntity();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("sensing");
      this.senses.clearSensingCache();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("targetSelector");
      this.targetTasks.onUpdateTasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("goalSelector");
      this.tasks.onUpdateTasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("navigation");
      this.navigator.onUpdateNavigation();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("mob tick");
      this.updateAITasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("controls");
      this.worldObj.theProfiler.startSection("move");
      this.moveHelper.onUpdateMoveHelper();
      this.worldObj.theProfiler.endStartSection("look");
      this.lookHelper.onUpdateLook();
      this.worldObj.theProfiler.endStartSection("jump");
      this.jumpHelper.doJump();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.endSection();
   }

   protected boolean interact(EntityPlayer var1) {
      return false;
   }

   private boolean isAIDisabled() {
      return this.dataWatcher.getWatchableObjectByte(15) != 0;
   }

   public static int getArmorPosition(ItemStack var0) {
      if (var0.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && var0.getItem() != Items.skull) {
         if (var0.getItem() instanceof ItemArmor) {
            switch(((ItemArmor)var0.getItem()).armorType) {
            case 0:
               return 4;
            case 1:
               return 3;
            case 2:
               return 2;
            case 3:
               return 1;
            }
         }

         return 0;
      } else {
         return 4;
      }
   }

   public ItemStack[] getInventory() {
      return this.equipment;
   }

   private void recreateLeash() {
      if (this.isLeashed && this.leashNBTTag != null) {
         if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
            UUID var5 = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));
            List var6 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D));
            Iterator var3 = var6.iterator();

            while(var3.hasNext()) {
               EntityLivingBase var4 = (EntityLivingBase)var3.next();
               if (var4.getUniqueID().equals(var5)) {
                  this.leashedToEntity = var4;
                  break;
               }
            }
         } else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
            BlockPos var1 = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
            EntityLeashKnot var2 = EntityLeashKnot.func_174863_b(this.worldObj, var1);
            if (var2 == null) {
               var2 = EntityLeashKnot.func_174862_a(this.worldObj, var1);
            }

            this.leashedToEntity = var2;
         } else {
            this.clearLeashed(false, true);
         }
      }

      this.leashNBTTag = null;
   }

   public static enum SpawnPlacementType {
      private static final EntityLiving.SpawnPlacementType[] ENUM$VALUES = new EntityLiving.SpawnPlacementType[]{ON_GROUND, IN_AIR, IN_WATER};
      private static final String __OBFID = "CL_00002255";
      private static final EntityLiving.SpawnPlacementType[] $VALUES = new EntityLiving.SpawnPlacementType[]{ON_GROUND, IN_AIR, IN_WATER};
      ON_GROUND("ON_GROUND", 0, "ON_GROUND", 0),
      IN_WATER("IN_WATER", 2, "IN_WATER", 2),
      IN_AIR("IN_AIR", 1, "IN_AIR", 1);

      private SpawnPlacementType(String var3, int var4, String var5, int var6) {
      }
   }
}
