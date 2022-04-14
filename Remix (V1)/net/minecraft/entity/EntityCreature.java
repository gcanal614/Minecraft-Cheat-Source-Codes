package net.minecraft.entity;

import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.pathfinding.*;

public abstract class EntityCreature extends EntityLiving
{
    public static final UUID field_110179_h;
    public static final AttributeModifier field_110181_i;
    private BlockPos homePosition;
    private float maximumHomeDistance;
    private EntityAIBase aiBase;
    private boolean field_110180_bt;
    
    public EntityCreature(final World worldIn) {
        super(worldIn);
        this.homePosition = BlockPos.ORIGIN;
        this.maximumHomeDistance = -1.0f;
        this.aiBase = new EntityAIMoveTowardsRestriction(this, 1.0);
    }
    
    public float func_180484_a(final BlockPos p_180484_1_) {
        return 0.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.func_180484_a(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0f;
    }
    
    public boolean hasPath() {
        return !this.navigator.noPath();
    }
    
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.func_180485_d(new BlockPos(this));
    }
    
    public boolean func_180485_d(final BlockPos p_180485_1_) {
        return this.maximumHomeDistance == -1.0f || this.homePosition.distanceSq(p_180485_1_) < this.maximumHomeDistance * this.maximumHomeDistance;
    }
    
    public void func_175449_a(final BlockPos p_175449_1_, final int p_175449_2_) {
        this.homePosition = p_175449_1_;
        this.maximumHomeDistance = (float)p_175449_2_;
    }
    
    public BlockPos func_180486_cf() {
        return this.homePosition;
    }
    
    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }
    
    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }
    
    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0f;
    }
    
    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        if (this.getLeashed() && this.getLeashedToEntity() != null && this.getLeashedToEntity().worldObj == this.worldObj) {
            final Entity var1 = this.getLeashedToEntity();
            this.func_175449_a(new BlockPos((int)var1.posX, (int)var1.posY, (int)var1.posZ), 5);
            final float var2 = this.getDistanceToEntity(var1);
            if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
                if (var2 > 10.0f) {
                    this.clearLeashed(true, true);
                }
                return;
            }
            if (!this.field_110180_bt) {
                this.tasks.addTask(2, this.aiBase);
                if (this.getNavigator() instanceof PathNavigateGround) {
                    ((PathNavigateGround)this.getNavigator()).func_179690_a(false);
                }
                this.field_110180_bt = true;
            }
            this.func_142017_o(var2);
            if (var2 > 4.0f) {
                this.getNavigator().tryMoveToEntityLiving(var1, 1.0);
            }
            if (var2 > 6.0f) {
                final double var3 = (var1.posX - this.posX) / var2;
                final double var4 = (var1.posY - this.posY) / var2;
                final double var5 = (var1.posZ - this.posZ) / var2;
                this.motionX += var3 * Math.abs(var3) * 0.4;
                this.motionY += var4 * Math.abs(var4) * 0.4;
                this.motionZ += var5 * Math.abs(var5) * 0.4;
            }
            if (var2 > 10.0f) {
                this.clearLeashed(true, true);
            }
        }
        else if (!this.getLeashed() && this.field_110180_bt) {
            this.field_110180_bt = false;
            this.tasks.removeTask(this.aiBase);
            if (this.getNavigator() instanceof PathNavigateGround) {
                ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
            }
            this.detachHome();
        }
    }
    
    protected void func_142017_o(final float p_142017_1_) {
    }
    
    static {
        field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
        field_110181_i = new AttributeModifier(EntityCreature.field_110179_h, "Fleeing speed bonus", 2.0, 2).setSaved(false);
    }
}
