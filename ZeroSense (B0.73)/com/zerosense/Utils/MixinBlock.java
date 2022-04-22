package com.zerosense.Utils;


import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class MixinBlock {

    public void addCollisionBoxesToList(World world, BlockPos blockPos, IBlockState state, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> axisAlignedBBS, Entity entity){
        AxisAlignedBB axisAlignedbb = getCollisonBoundingBox(world,blockPos,state);
        if(axisAlignedbb != null && axisAlignedBB.intersectsWith(axisAlignedbb)){
            axisAlignedBBS.add(axisAlignedbb);
        }
    }

    public abstract void setBlockBounds(float p_setBlockBounds_1_,float p_setBlockBounds_2_,float p_setBlockBounds_3_,float p_setBlockBounds_4_,float p_setBlockBounds_5_, float p_setBlockBounds_6_);

    public abstract AxisAlignedBB getCollisonBoundingBox(World p_getCollisonBoundingBox_1_, BlockPos p_getCollisonBoundingBox_2_, IBlockState p_getCollisonBoundingBox_3_);
}
