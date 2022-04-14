package net.minecraft.entity.ai;

import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityAIOcelotAttack extends EntityAIBase
{
    World theWorld;
    EntityLiving theEntity;
    EntityLivingBase theVictim;
    int attackCountdown;
    
    public EntityAIOcelotAttack(final EntityLiving p_i1641_1_) {
        this.theEntity = p_i1641_1_;
        this.theWorld = p_i1641_1_.worldObj;
        this.setMutexBits(3);
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase var1 = this.theEntity.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        this.theVictim = var1;
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        return this.theVictim.isEntityAlive() && this.theEntity.getDistanceSqToEntity(this.theVictim) <= 225.0 && (!this.theEntity.getNavigator().noPath() || this.shouldExecute());
    }
    
    @Override
    public void resetTask() {
        this.theVictim = null;
        this.theEntity.getNavigator().clearPathEntity();
    }
    
    @Override
    public void updateTask() {
        this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0f, 30.0f);
        final double var1 = this.theEntity.width * 2.0f * this.theEntity.width * 2.0f;
        final double var2 = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.getEntityBoundingBox().minY, this.theVictim.posZ);
        double var3 = 0.8;
        if (var2 > var1 && var2 < 16.0) {
            var3 = 1.33;
        }
        else if (var2 < 225.0) {
            var3 = 0.6;
        }
        this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, var3);
        this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
        if (var2 <= var1 && this.attackCountdown <= 0) {
            this.attackCountdown = 20;
            this.theEntity.attackEntityAsMob(this.theVictim);
        }
    }
}
