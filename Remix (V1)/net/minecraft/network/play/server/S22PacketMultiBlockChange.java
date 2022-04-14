package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class S22PacketMultiBlockChange implements Packet
{
    private ChunkCoordIntPair field_148925_b;
    private BlockUpdateData[] field_179845_b;
    
    public S22PacketMultiBlockChange() {
    }
    
    public S22PacketMultiBlockChange(final int p_i45181_1_, final short[] p_i45181_2_, final Chunk p_i45181_3_) {
        this.field_148925_b = new ChunkCoordIntPair(p_i45181_3_.xPosition, p_i45181_3_.zPosition);
        this.field_179845_b = new BlockUpdateData[p_i45181_1_];
        for (int var4 = 0; var4 < this.field_179845_b.length; ++var4) {
            this.field_179845_b[var4] = new BlockUpdateData(p_i45181_2_[var4], p_i45181_3_);
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_148925_b = new ChunkCoordIntPair(data.readInt(), data.readInt());
        this.field_179845_b = new BlockUpdateData[data.readVarIntFromBuffer()];
        for (int var2 = 0; var2 < this.field_179845_b.length; ++var2) {
            this.field_179845_b[var2] = new BlockUpdateData(data.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(data.readVarIntFromBuffer()));
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeInt(this.field_148925_b.chunkXPos);
        data.writeInt(this.field_148925_b.chunkZPos);
        data.writeVarIntToBuffer(this.field_179845_b.length);
        for (final BlockUpdateData var5 : this.field_179845_b) {
            data.writeShort(var5.func_180089_b());
            data.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(var5.func_180088_c()));
        }
    }
    
    public void func_180729_a(final INetHandlerPlayClient p_180729_1_) {
        p_180729_1_.handleMultiBlockChange(this);
    }
    
    public BlockUpdateData[] func_179844_a() {
        return this.field_179845_b;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180729_a((INetHandlerPlayClient)handler);
    }
    
    public class BlockUpdateData
    {
        private final short field_180091_b;
        private final IBlockState field_180092_c;
        
        public BlockUpdateData(final short p_i45984_2_, final IBlockState p_i45984_3_) {
            this.field_180091_b = p_i45984_2_;
            this.field_180092_c = p_i45984_3_;
        }
        
        public BlockUpdateData(final short p_i45985_2_, final Chunk p_i45985_3_) {
            this.field_180091_b = p_i45985_2_;
            this.field_180092_c = p_i45985_3_.getBlockState(this.func_180090_a());
        }
        
        public BlockPos func_180090_a() {
            return new BlockPos(S22PacketMultiBlockChange.this.field_148925_b.getBlock(this.field_180091_b >> 12 & 0xF, this.field_180091_b & 0xFF, this.field_180091_b >> 8 & 0xF));
        }
        
        public short func_180089_b() {
            return this.field_180091_b;
        }
        
        public IBlockState func_180088_c() {
            return this.field_180092_c;
        }
    }
}
