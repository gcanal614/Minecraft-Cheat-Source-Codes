// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.event.block;

import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import bozoware.base.event.CancellableEvent;

public class EventAABB extends CancellableEvent
{
    private AxisAlignedBB boundingBox;
    private Block block;
    private BlockPos pos;
    
    public EventAABB(final Block block, final BlockPos pos, final AxisAlignedBB aabb) {
        this.boundingBox = aabb;
        this.pos = pos;
        this.block = block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
}
