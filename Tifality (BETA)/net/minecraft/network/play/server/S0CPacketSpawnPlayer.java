/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0CPacketSpawnPlayer
implements Packet<INetHandlerPlayClient> {
    private int entityId;
    private UUID playerId;
    private int x;
    private int y;
    private int z;
    private byte yaw;
    private byte pitch;
    private int currentItem;
    private DataWatcher watcher;
    private List<DataWatcher.WatchableObject> field_148958_j;

    public S0CPacketSpawnPlayer() {
    }

    public S0CPacketSpawnPlayer(EntityPlayer p_i45171_1_) {
        this.entityId = p_i45171_1_.getEntityId();
        this.playerId = p_i45171_1_.getGameProfile().getId();
        this.x = MathHelper.floor_double(p_i45171_1_.posX * 32.0);
        this.y = MathHelper.floor_double(p_i45171_1_.posY * 32.0);
        this.z = MathHelper.floor_double(p_i45171_1_.posZ * 32.0);
        this.yaw = (byte)(p_i45171_1_.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(p_i45171_1_.rotationPitch * 256.0f / 360.0f);
        ItemStack lvt_2_1_ = p_i45171_1_.inventory.getCurrentItem();
        this.currentItem = lvt_2_1_ == null ? 0 : Item.getIdFromItem(lvt_2_1_.getItem());
        this.watcher = p_i45171_1_.getDataWatcher();
    }

    @Override
    public void readPacketData(PacketBuffer p_readPacketData_1_) throws IOException {
        this.entityId = p_readPacketData_1_.readVarIntFromBuffer();
        this.playerId = p_readPacketData_1_.readUuid();
        this.x = p_readPacketData_1_.readInt();
        this.y = p_readPacketData_1_.readInt();
        this.z = p_readPacketData_1_.readInt();
        this.yaw = p_readPacketData_1_.readByte();
        this.pitch = p_readPacketData_1_.readByte();
        this.currentItem = p_readPacketData_1_.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(p_readPacketData_1_);
    }

    @Override
    public void writePacketData(PacketBuffer p_writePacketData_1_) throws IOException {
        p_writePacketData_1_.writeVarIntToBuffer(this.entityId);
        p_writePacketData_1_.writeUuid(this.playerId);
        p_writePacketData_1_.writeInt(this.x);
        p_writePacketData_1_.writeInt(this.y);
        p_writePacketData_1_.writeInt(this.z);
        p_writePacketData_1_.writeByte(this.yaw);
        p_writePacketData_1_.writeByte(this.pitch);
        p_writePacketData_1_.writeShort(this.currentItem);
        this.watcher.writeTo(p_writePacketData_1_);
    }

    @Override
    public void processPacket(INetHandlerPlayClient p_processPacket_1_) {
        p_processPacket_1_.handleSpawnPlayer(this);
    }

    public List<DataWatcher.WatchableObject> func_148944_c() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.watcher.getAllWatched();
        }
        return this.field_148958_j;
    }

    public int getEntityID() {
        return this.entityId;
    }

    public UUID getPlayer() {
        return this.playerId;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public int getCurrentItemID() {
        return this.currentItem;
    }
}

