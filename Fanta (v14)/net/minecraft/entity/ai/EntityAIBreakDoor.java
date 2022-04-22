/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakDoor
extends EntityAIDoorInteract {
    private int breakingTime;
    private int previousBreakProgress = -1;

    public EntityAIBreakDoor(EntityLiving entityIn) {
        super(entityIn);
    }

    @Override
    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return false;
        }
        if (!EntityLiving.worldObj.getGameRules().getBoolean("mobGriefing")) {
            return false;
        }
        BlockDoor blockdoor = this.doorBlock;
        return !BlockDoor.isOpen(EntityLiving.worldObj, this.doorPosition);
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }

    @Override
    public boolean continueExecuting() {
        double d0 = this.theEntity.getDistanceSq(this.doorPosition);
        if (this.breakingTime <= 240) {
            BlockDoor blockdoor = this.doorBlock;
            if (!BlockDoor.isOpen(EntityLiving.worldObj, this.doorPosition) && d0 < 4.0) {
                boolean flag = true;
                return flag;
            }
        }
        boolean flag = false;
        return flag;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        EntityLiving.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, -1);
    }

    @Override
    public void updateTask() {
        super.updateTask();
        if (this.theEntity.getRNG().nextInt(20) == 0) {
            EntityLiving.worldObj.playAuxSFX(1010, this.doorPosition, 0);
        }
        ++this.breakingTime;
        int i = (int)((float)this.breakingTime / 240.0f * 10.0f);
        if (i != this.previousBreakProgress) {
            EntityLiving.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, i);
            this.previousBreakProgress = i;
        }
        if (this.breakingTime == 240 && EntityLiving.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            EntityLiving.worldObj.setBlockToAir(this.doorPosition);
            EntityLiving.worldObj.playAuxSFX(1012, this.doorPosition, 0);
            EntityLiving.worldObj.playAuxSFX(2001, this.doorPosition, Block.getIdFromBlock(this.doorBlock));
        }
    }
}

