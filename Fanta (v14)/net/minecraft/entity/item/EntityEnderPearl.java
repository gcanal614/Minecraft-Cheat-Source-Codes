/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl
extends EntityThrowable {
    private EntityLivingBase field_181555_c;

    public EntityEnderPearl(World p_i46455_1_) {
        super(p_i46455_1_);
    }

    public EntityEnderPearl(World worldIn, EntityLivingBase p_i1783_2_) {
        super(worldIn, p_i1783_2_);
        this.field_181555_c = p_i1783_2_;
    }

    public EntityEnderPearl(World worldIn, double p_i1784_2_, double p_i1784_4_, double p_i1784_6_) {
        super(worldIn, p_i1784_2_, p_i1784_4_, p_i1784_6_);
    }

    @Override
    protected void onImpact(MovingObjectPosition p_70184_1_) {
        EntityLivingBase entitylivingbase = this.getThrower();
        if (p_70184_1_.entityHit != null) {
            if (p_70184_1_.entityHit == this.field_181555_c) {
                return;
            }
            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase), 0.0f);
        }
        int i = 0;
        while (i < 32) {
            worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + rand.nextDouble() * 2.0, this.posZ, rand.nextGaussian(), 0.0, rand.nextGaussian(), new int[0]);
            ++i;
        }
        if (!EntityEnderPearl.worldObj.isRemote) {
            if (entitylivingbase instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;
                if (entityplayermp.playerNetServerHandler.getNetworkManager().isChannelOpen() && EntityPlayerMP.worldObj == worldObj && !entityplayermp.isPlayerSleeping()) {
                    if (rand.nextFloat() < 0.05f && worldObj.getGameRules().getBoolean("doMobSpawning")) {
                        EntityEndermite entityendermite = new EntityEndermite(worldObj);
                        entityendermite.setSpawnedByPlayer(true);
                        entityendermite.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
                        worldObj.spawnEntityInWorld(entityendermite);
                    }
                    if (entitylivingbase.isRiding()) {
                        entitylivingbase.mountEntity(null);
                    }
                    entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    entitylivingbase.fallDistance = 0.0f;
                    entitylivingbase.attackEntityFrom(DamageSource.fall, 5.0f);
                }
            } else if (entitylivingbase != null) {
                entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                entitylivingbase.fallDistance = 0.0f;
            }
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        EntityLivingBase entitylivingbase = this.getThrower();
        if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isEntityAlive()) {
            this.setDead();
        } else {
            super.onUpdate();
        }
    }
}

