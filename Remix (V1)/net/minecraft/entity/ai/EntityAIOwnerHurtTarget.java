package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    EntityLivingBase theTarget;
    private int field_142050_e;
    
    public EntityAIOwnerHurtTarget(final EntityTameable p_i1668_1_) {
        super(p_i1668_1_, false);
        this.theEntityTameable = p_i1668_1_;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntityTameable.isTamed()) {
            return false;
        }
        final EntityLivingBase var1 = this.theEntityTameable.func_180492_cm();
        if (var1 == null) {
            return false;
        }
        this.theTarget = var1.getLastAttacker();
        final int var2 = var1.getLastAttackerTime();
        return var2 != this.field_142050_e && this.isSuitableTarget(this.theTarget, false) && this.theEntityTameable.func_142018_a(this.theTarget, var1);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theTarget);
        final EntityLivingBase var1 = this.theEntityTameable.func_180492_cm();
        if (var1 != null) {
            this.field_142050_e = var1.getLastAttackerTime();
        }
        super.startExecuting();
    }
}
