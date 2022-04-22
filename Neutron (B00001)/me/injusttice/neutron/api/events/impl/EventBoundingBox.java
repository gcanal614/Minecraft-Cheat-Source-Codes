package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBoundingBox extends Event {

    private Block block;
    private BlockPos blockPos;
    private AxisAlignedBB boundingBox;
    
    public EventBoundingBox(final Block block, final BlockPos pos, final AxisAlignedBB boundingBox) {
        this.block = block;
        this.blockPos = pos;
        this.boundingBox = boundingBox;
    }
    
    public Block getBlock() {
        return block;
    }
    
    public BlockPos getBlockPos() {
        return blockPos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}
