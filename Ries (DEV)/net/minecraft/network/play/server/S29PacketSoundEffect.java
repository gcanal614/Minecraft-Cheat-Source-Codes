/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import org.apache.commons.lang3.Validate;

public class S29PacketSoundEffect
implements Packet<INetHandlerPlayClient> {
    private String soundName;
    private int posX;
    private int posY = Integer.MAX_VALUE;
    private int posZ;
    private float soundVolume;
    private int soundPitch;

    public S29PacketSoundEffect() {
    }

    public S29PacketSoundEffect(String soundNameIn, double soundX, double soundY, double soundZ, float volume, float pitch) {
        Validate.notNull((Object)soundNameIn, (String)"name", (Object[])new Object[0]);
        this.soundName = soundNameIn;
        this.posX = (int)(soundX * 8.0);
        this.posY = (int)(soundY * 8.0);
        this.posZ = (int)(soundZ * 8.0);
        this.soundVolume = volume;
        this.soundPitch = (int)(pitch * 63.0f);
    }

    @Override
    public void readPacketData(PacketBuffer buf) {
        this.soundName = buf.readStringFromBuffer(256);
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.soundVolume = buf.readFloat();
        this.soundPitch = buf.readUnsignedByte();
    }

    @Override
    public void writePacketData(PacketBuffer buf) {
        buf.writeString(this.soundName);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
        buf.writeFloat(this.soundVolume);
        buf.writeByte(this.soundPitch);
    }

    public String getSoundName() {
        return this.soundName;
    }

    public double getX() {
        return (float)this.posX / 8.0f;
    }

    public double getY() {
        return (float)this.posY / 8.0f;
    }

    public double getZ() {
        return (float)this.posZ / 8.0f;
    }

    public float getVolume() {
        return this.soundVolume;
    }

    public float getPitch() {
        return (float)this.soundPitch / 63.0f;
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleSoundEffect(this);
    }
}

