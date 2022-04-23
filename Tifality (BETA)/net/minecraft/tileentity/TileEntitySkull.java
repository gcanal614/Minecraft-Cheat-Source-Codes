/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;

public class TileEntitySkull
extends TileEntity {
    private int skullType;
    private int skullRotation;
    private GameProfile playerProfile = null;

    @Override
    public void writeToNBT(NBTTagCompound p_writeToNBT_1_) {
        super.writeToNBT(p_writeToNBT_1_);
        p_writeToNBT_1_.setByte("SkullType", (byte)(this.skullType & 0xFF));
        p_writeToNBT_1_.setByte("Rot", (byte)(this.skullRotation & 0xFF));
        if (this.playerProfile != null) {
            NBTTagCompound lvt_2_1_ = new NBTTagCompound();
            NBTUtil.writeGameProfile(lvt_2_1_, this.playerProfile);
            p_writeToNBT_1_.setTag("Owner", lvt_2_1_);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound p_readFromNBT_1_) {
        super.readFromNBT(p_readFromNBT_1_);
        this.skullType = p_readFromNBT_1_.getByte("SkullType");
        this.skullRotation = p_readFromNBT_1_.getByte("Rot");
        if (this.skullType == 3) {
            String lvt_2_1_;
            if (p_readFromNBT_1_.hasKey("Owner", 10)) {
                this.playerProfile = NBTUtil.readGameProfileFromNBT(p_readFromNBT_1_.getCompoundTag("Owner"));
            } else if (p_readFromNBT_1_.hasKey("ExtraType", 8) && !StringUtils.isNullOrEmpty(lvt_2_1_ = p_readFromNBT_1_.getString("ExtraType"))) {
                this.playerProfile = new GameProfile(null, lvt_2_1_);
                this.updatePlayerProfile();
            }
        }
    }

    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound lvt_1_1_ = new NBTTagCompound();
        this.writeToNBT(lvt_1_1_);
        return new S35PacketUpdateTileEntity(this.pos, 4, lvt_1_1_);
    }

    public void setType(int p_setType_1_) {
        this.skullType = p_setType_1_;
        this.playerProfile = null;
    }

    public void setPlayerProfile(GameProfile p_setPlayerProfile_1_) {
        this.skullType = 3;
        this.playerProfile = p_setPlayerProfile_1_;
        this.updatePlayerProfile();
    }

    private void updatePlayerProfile() {
        this.playerProfile = TileEntitySkull.updateGameprofile(this.playerProfile);
        this.markDirty();
    }

    public static GameProfile updateGameprofile(GameProfile p_updateGameprofile_0_) {
        if (p_updateGameprofile_0_ != null && !StringUtils.isNullOrEmpty(p_updateGameprofile_0_.getName())) {
            if (p_updateGameprofile_0_.isComplete() && p_updateGameprofile_0_.getProperties().containsKey("textures")) {
                return p_updateGameprofile_0_;
            }
            if (MinecraftServer.getServer() == null) {
                return p_updateGameprofile_0_;
            }
            GameProfile lvt_1_1_ = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(p_updateGameprofile_0_.getName());
            if (lvt_1_1_ == null) {
                return p_updateGameprofile_0_;
            }
            Property lvt_2_1_ = Iterables.getFirst(lvt_1_1_.getProperties().get("textures"), null);
            if (lvt_2_1_ == null) {
                lvt_1_1_ = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(lvt_1_1_, true);
            }
            return lvt_1_1_;
        }
        return p_updateGameprofile_0_;
    }

    public int getSkullType() {
        return this.skullType;
    }

    public int getSkullRotation() {
        return this.skullRotation;
    }

    public void setSkullRotation(int p_setSkullRotation_1_) {
        this.skullRotation = p_setSkullRotation_1_;
    }
}

