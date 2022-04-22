/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEgg
extends EntityThrowable {
    public EntityEgg(World worldIn) {
        super(worldIn);
    }

    public EntityEgg(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityEgg(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.entityHit != null) {
            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        if (!EntityEgg.worldObj.isRemote && rand.nextInt(8) == 0) {
            int i = 1;
            if (rand.nextInt(32) == 0) {
                i = 4;
            }
            int j = 0;
            while (j < i) {
                EntityChicken entitychicken = new EntityChicken(worldObj);
                entitychicken.setGrowingAge(-24000);
                entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                worldObj.spawnEntityInWorld(entitychicken);
                ++j;
            }
        }
        double d0 = 0.08;
        int k = 0;
        while (k < 8) {
            worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double)rand.nextFloat() - 0.5) * 0.08, ((double)rand.nextFloat() - 0.5) * 0.08, ((double)rand.nextFloat() - 0.5) * 0.08, Item.getIdFromItem(Items.egg));
            ++k;
        }
        if (!EntityEgg.worldObj.isRemote) {
            this.setDead();
        }
    }
}

