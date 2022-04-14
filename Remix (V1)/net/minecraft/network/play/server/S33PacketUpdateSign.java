package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S33PacketUpdateSign implements Packet
{
    private World field_179706_a;
    private BlockPos field_179705_b;
    private IChatComponent[] field_149349_d;
    
    public S33PacketUpdateSign() {
    }
    
    public S33PacketUpdateSign(final World worldIn, final BlockPos p_i45951_2_, final IChatComponent[] p_i45951_3_) {
        this.field_179706_a = worldIn;
        this.field_179705_b = p_i45951_2_;
        this.field_149349_d = new IChatComponent[] { p_i45951_3_[0], p_i45951_3_[1], p_i45951_3_[2], p_i45951_3_[3] };
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_179705_b = data.readBlockPos();
        this.field_149349_d = new IChatComponent[4];
        for (int var2 = 0; var2 < 4; ++var2) {
            this.field_149349_d[var2] = data.readChatComponent();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeBlockPos(this.field_179705_b);
        for (int var2 = 0; var2 < 4; ++var2) {
            data.writeChatComponent(this.field_149349_d[var2]);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleUpdateSign(this);
    }
    
    public BlockPos func_179704_a() {
        return this.field_179705_b;
    }
    
    public IChatComponent[] func_180753_b() {
        return this.field_149349_d;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}
