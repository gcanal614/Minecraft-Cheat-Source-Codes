package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class EntityAIOcelotSit extends EntityAIMoveToBlock
{
    private final EntityOcelot field_151493_a;
    
    public EntityAIOcelotSit(final EntityOcelot p_i45315_1_, final double p_i45315_2_) {
        super(p_i45315_1_, p_i45315_2_, 8);
        this.field_151493_a = p_i45315_1_;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_151493_a.isTamed() && !this.field_151493_a.isSitting() && super.shouldExecute();
    }
    
    @Override
    public boolean continueExecuting() {
        return super.continueExecuting();
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.field_151493_a.getAISit().setSitting(false);
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.field_151493_a.setSitting(false);
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.field_151493_a.getAISit().setSitting(false);
        if (!this.func_179487_f()) {
            this.field_151493_a.setSitting(false);
        }
        else if (!this.field_151493_a.isSitting()) {
            this.field_151493_a.setSitting(true);
        }
    }
    
    @Override
    protected boolean func_179488_a(final World worldIn, final BlockPos p_179488_2_) {
        if (!worldIn.isAirBlock(p_179488_2_.offsetUp())) {
            return false;
        }
        final IBlockState var3 = worldIn.getBlockState(p_179488_2_);
        final Block var4 = var3.getBlock();
        if (var4 == Blocks.chest) {
            final TileEntity var5 = worldIn.getTileEntity(p_179488_2_);
            if (var5 instanceof TileEntityChest && ((TileEntityChest)var5).numPlayersUsing < 1) {
                return true;
            }
        }
        else {
            if (var4 == Blocks.lit_furnace) {
                return true;
            }
            if (var4 == Blocks.bed && var3.getValue(BlockBed.PART_PROP) != BlockBed.EnumPartType.HEAD) {
                return true;
            }
        }
        return false;
    }
}
