/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.effect;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityLightningBolt
extends EntityWeatherEffect {
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;

    public EntityLightningBolt(World worldIn, double posX, double posY, double posZ) {
        super(worldIn);
        this.setLocationAndAngles(posX, posY, posZ, 0.0f, 0.0f);
        this.lightningState = 2;
        this.boltVertex = rand.nextLong();
        this.boltLivingTime = rand.nextInt(3) + 1;
        BlockPos blockpos = new BlockPos(this);
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doFireTick") && (worldIn.getDifficulty() == EnumDifficulty.NORMAL || worldIn.getDifficulty() == EnumDifficulty.HARD) && worldIn.isAreaLoaded(blockpos, 10)) {
            if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos)) {
                worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState());
            }
            int i = 0;
            while (i < 4) {
                BlockPos blockpos1 = blockpos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);
                if (worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldIn, blockpos1)) {
                    worldIn.setBlockState(blockpos1, Blocks.fire.getDefaultState());
                }
                ++i;
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.lightningState == 2) {
            worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0f, 0.8f + rand.nextFloat() * 0.2f);
            worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0f, 0.5f + rand.nextFloat() * 0.2f);
        }
        --this.lightningState;
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
            } else if (this.lightningState < -rand.nextInt(10)) {
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = rand.nextLong();
                BlockPos blockpos = new BlockPos(this);
                if (!EntityLightningBolt.worldObj.isRemote && worldObj.getGameRules().getBoolean("doFireTick") && worldObj.isAreaLoaded(blockpos, 10) && worldObj.getBlockState(blockpos).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(worldObj, blockpos)) {
                    worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
                }
            }
        }
        if (this.lightningState >= 0) {
            if (EntityLightningBolt.worldObj.isRemote) {
                worldObj.setLastLightningBolt(2);
            } else {
                double d0 = 3.0;
                List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0 + d0, this.posZ + d0));
                int i = 0;
                while (i < list.size()) {
                    Entity entity = list.get(i);
                    entity.onStruckByLightning(this);
                    ++i;
                }
            }
        }
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
    }
}

