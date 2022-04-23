/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard
implements Packet<INetHandlerPlayClient> {
    private int position;
    private String scoreName;

    public S3DPacketDisplayScoreboard() {
    }

    public S3DPacketDisplayScoreboard(int p_i45216_1_, ScoreObjective p_i45216_2_) {
        this.position = p_i45216_1_;
        this.scoreName = p_i45216_2_ == null ? "" : p_i45216_2_.getName();
    }

    @Override
    public void readPacketData(PacketBuffer p_readPacketData_1_) throws IOException {
        this.position = p_readPacketData_1_.readByte();
        this.scoreName = p_readPacketData_1_.readStringFromBuffer(16);
    }

    @Override
    public void writePacketData(PacketBuffer p_writePacketData_1_) throws IOException {
        p_writePacketData_1_.writeByte(this.position);
        p_writePacketData_1_.writeString(this.scoreName);
    }

    @Override
    public void processPacket(INetHandlerPlayClient p_processPacket_1_) {
        p_processPacket_1_.handleDisplayScoreboard(this);
    }

    public int func_149371_c() {
        return this.position;
    }

    public String func_149370_d() {
        return this.scoreName;
    }
}

