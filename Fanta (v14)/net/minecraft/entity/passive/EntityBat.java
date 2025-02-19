/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.passive;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBat
extends EntityAmbientCreature {
    private BlockPos spawnPosition;

    public EntityBat(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.9f);
        this.setIsBatHanging(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }

    @Override
    protected String getLivingSound() {
        return this.getIsBatHanging() && rand.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    @Override
    protected String getHurtSound() {
        return "mob.bat.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.bat.death";
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity p_82167_1_) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0);
    }

    public boolean getIsBatHanging() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setIsBatHanging(boolean isHanging) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        if (isHanging) {
            this.dataWatcher.updateObject(16, (byte)(b0 | 1));
        } else {
            this.dataWatcher.updateObject(16, (byte)(b0 & 0xFFFFFFFE));
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getIsBatHanging()) {
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.posY = (double)MathHelper.floor_double(this.posY) + 1.0 - (double)this.height;
        } else {
            this.motionY *= (double)0.6f;
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();
        if (this.getIsBatHanging()) {
            if (!worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
                this.setIsBatHanging(false);
                worldObj.playAuxSFXAtEntity(null, 1015, blockpos, 0);
            } else {
                if (rand.nextInt(200) == 0) {
                    this.rotationYawHead = rand.nextInt(360);
                }
                if (worldObj.getClosestPlayerToEntity(this, 4.0) != null) {
                    this.setIsBatHanging(false);
                    worldObj.playAuxSFXAtEntity(null, 1015, blockpos, 0);
                }
            }
        } else {
            if (!(this.spawnPosition == null || worldObj.isAirBlock(this.spawnPosition) && this.spawnPosition.getY() >= 1)) {
                this.spawnPosition = null;
            }
            if (this.spawnPosition == null || rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0) {
                this.spawnPosition = new BlockPos((int)this.posX + rand.nextInt(7) - rand.nextInt(7), (int)this.posY + rand.nextInt(6) - 2, (int)this.posZ + rand.nextInt(7) - rand.nextInt(7));
            }
            double d0 = (double)this.spawnPosition.getX() + 0.5 - this.posX;
            double d1 = (double)this.spawnPosition.getY() + 0.1 - this.posY;
            double d2 = (double)this.spawnPosition.getZ() + 0.5 - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5 - this.motionX) * (double)0.1f;
            this.motionY += (Math.signum(d1) * (double)0.7f - this.motionY) * (double)0.1f;
            this.motionZ += (Math.signum(d2) * 0.5 - this.motionZ) * (double)0.1f;
            float f = (float)(MathHelper.func_181159_b(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
            float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += f1;
            if (rand.nextInt(100) == 0 && worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
                this.setIsBatHanging(true);
            }
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        if (!EntityBat.worldObj.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.dataWatcher.updateObject(16, tagCompund.getByte("BatFlags"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
    }

    @Override
    public boolean getCanSpawnHere() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (blockpos.getY() >= worldObj.func_181545_F()) {
            return false;
        }
        int i = worldObj.getLightFromNeighbors(blockpos);
        int j = 4;
        if (this.isDateAroundHalloween(worldObj.getCurrentDate())) {
            j = 7;
        } else if (rand.nextBoolean()) {
            return false;
        }
        return i > rand.nextInt(j) ? false : super.getCanSpawnHere();
    }

    private boolean isDateAroundHalloween(Calendar p_175569_1_) {
        return p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20 || p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3;
    }

    @Override
    public float getEyeHeight() {
        return this.height / 2.0f;
    }
}

