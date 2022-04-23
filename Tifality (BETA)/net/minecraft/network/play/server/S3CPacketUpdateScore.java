/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class S3CPacketUpdateScore
implements Packet<INetHandlerPlayClient> {
    private String name = "";
    private String objective = "";
    private int value;
    private Action action;

    public S3CPacketUpdateScore() {
    }

    public S3CPacketUpdateScore(Score p_i45958_1_) {
        this.name = p_i45958_1_.getPlayerName();
        this.objective = p_i45958_1_.getObjective().getName();
        this.value = p_i45958_1_.getScorePoints();
        this.action = Action.CHANGE;
    }

    public S3CPacketUpdateScore(String p_i45228_1_) {
        this.name = p_i45228_1_;
        this.objective = "";
        this.value = 0;
        this.action = Action.REMOVE;
    }

    public S3CPacketUpdateScore(String p_i45959_1_, ScoreObjective p_i45959_2_) {
        this.name = p_i45959_1_;
        this.objective = p_i45959_2_.getName();
        this.value = 0;
        this.action = Action.REMOVE;
    }

    @Override
    public void readPacketData(PacketBuffer p_readPacketData_1_) throws IOException {
        this.name = p_readPacketData_1_.readStringFromBuffer(40);
        this.action = p_readPacketData_1_.readEnumValue(Action.class);
        this.objective = p_readPacketData_1_.readStringFromBuffer(16);
        if (this.action != Action.REMOVE) {
            this.value = p_readPacketData_1_.readVarIntFromBuffer();
        }
    }

    @Override
    public void writePacketData(PacketBuffer p_writePacketData_1_) throws IOException {
        p_writePacketData_1_.writeString(this.name);
        p_writePacketData_1_.writeEnumValue(this.action);
        p_writePacketData_1_.writeString(this.objective);
        if (this.action != Action.REMOVE) {
            p_writePacketData_1_.writeVarIntToBuffer(this.value);
        }
    }

    @Override
    public void processPacket(INetHandlerPlayClient p_processPacket_1_) {
        p_processPacket_1_.handleUpdateScore(this);
    }

    public String getPlayerName() {
        return this.name;
    }

    public String getObjectiveName() {
        return this.objective;
    }

    public int getScoreValue() {
        return this.value;
    }

    public Action getScoreAction() {
        return this.action;
    }

    public static enum Action {
        CHANGE,
        REMOVE;

    }
}

