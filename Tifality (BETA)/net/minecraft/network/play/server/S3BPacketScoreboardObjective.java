/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;

public class S3BPacketScoreboardObjective
implements Packet<INetHandlerPlayClient> {
    private String objectiveName;
    private String objectiveValue;
    private IScoreObjectiveCriteria.EnumRenderType type;
    private int field_149342_c;

    public S3BPacketScoreboardObjective() {
    }

    public S3BPacketScoreboardObjective(ScoreObjective p_i45224_1_, int p_i45224_2_) {
        this.objectiveName = p_i45224_1_.getName();
        this.objectiveValue = p_i45224_1_.getDisplayName();
        this.type = p_i45224_1_.getCriteria().getRenderType();
        this.field_149342_c = p_i45224_2_;
    }

    @Override
    public void readPacketData(PacketBuffer p_readPacketData_1_) throws IOException {
        this.objectiveName = p_readPacketData_1_.readStringFromBuffer(16);
        this.field_149342_c = p_readPacketData_1_.readByte();
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            this.objectiveValue = p_readPacketData_1_.readStringFromBuffer(32);
            this.type = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(p_readPacketData_1_.readStringFromBuffer(16));
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_writePacketData_1_) throws IOException {
        p_writePacketData_1_.writeString(this.objectiveName);
        p_writePacketData_1_.writeByte(this.field_149342_c);
        if (this.field_149342_c == 0 || this.field_149342_c == 2) {
            p_writePacketData_1_.writeString(this.objectiveValue);
            p_writePacketData_1_.writeString(this.type.func_178796_a());
        }
    }

    @Override
    public void processPacket(INetHandlerPlayClient p_processPacket_1_) {
        p_processPacket_1_.handleScoreboardObjective(this);
    }

    public String func_149339_c() {
        return this.objectiveName;
    }

    public String func_149337_d() {
        return this.objectiveValue;
    }

    public int func_149338_e() {
        return this.field_149342_c;
    }

    public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
        return this.type;
    }
}

