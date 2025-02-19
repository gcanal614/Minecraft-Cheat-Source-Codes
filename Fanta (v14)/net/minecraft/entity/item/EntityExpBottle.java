/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle
extends EntityThrowable {
    public EntityExpBottle(World worldIn) {
        super(worldIn);
    }

    public EntityExpBottle(World worldIn, EntityLivingBase p_i1786_2_) {
        super(worldIn, p_i1786_2_);
    }

    public EntityExpBottle(World worldIn, double p_i1787_2_, double p_i1787_4_, double p_i1787_6_) {
        super(worldIn, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.07f;
    }

    @Override
    protected float getVelocity() {
        return 0.7f;
    }

    @Override
    protected float getInaccuracy() {
        return -20.0f;
    }

    @Override
    protected void onImpact(MovingObjectPosition p_70184_1_) {
        if (!EntityExpBottle.worldObj.isRemote) {
            worldObj.playAuxSFX(2002, new BlockPos(this), 0);
            int i = 3 + EntityExpBottle.worldObj.rand.nextInt(5) + EntityExpBottle.worldObj.rand.nextInt(5);
            while (i > 0) {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, this.posX, this.posY, this.posZ, j));
            }
            this.setDead();
        }
    }
}

