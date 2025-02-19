/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.metadata.Metadata
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.type.Type
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.ArrayList;
import java.util.List;

public class ShulkerBulletReplacement
implements EntityReplacement {
    private int entityId;
    private List<Metadata> datawatcher = new ArrayList<Metadata>();
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headYaw;
    private UserConnection user;

    public ShulkerBulletReplacement(int entityId, UserConnection user) {
        this.entityId = entityId;
        this.user = user;
        this.spawn();
    }

    @Override
    public void setLocation(double x, double y, double z) {
        if (x != this.locX || y != this.locY || z != this.locZ) {
            this.locX = x;
            this.locY = y;
            this.locZ = z;
            this.updateLocation();
        }
    }

    @Override
    public void relMove(double x, double y, double z) {
        if (x == 0.0 && y == 0.0 && z == 0.0) {
            return;
        }
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        this.updateLocation();
    }

    @Override
    public void setYawPitch(float yaw, float pitch) {
        if (this.yaw != yaw && this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.updateLocation();
        }
    }

    @Override
    public void setHeadYaw(float yaw) {
        this.headYaw = yaw;
    }

    @Override
    public void updateMetadata(List<Metadata> metadataList) {
    }

    public void updateLocation() {
        PacketWrapper teleport = PacketWrapper.create((int)24, null, (UserConnection)this.user);
        teleport.write((Type)Type.VAR_INT, (Object)this.entityId);
        teleport.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
        teleport.write((Type)Type.INT, (Object)((int)(this.locY * 32.0)));
        teleport.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
        teleport.write((Type)Type.BYTE, (Object)((byte)(this.yaw / 360.0f * 256.0f)));
        teleport.write((Type)Type.BYTE, (Object)((byte)(this.pitch / 360.0f * 256.0f)));
        teleport.write((Type)Type.BOOLEAN, (Object)true);
        PacketWrapper head = PacketWrapper.create((int)25, null, (UserConnection)this.user);
        head.write((Type)Type.VAR_INT, (Object)this.entityId);
        head.write((Type)Type.BYTE, (Object)((byte)(this.headYaw / 360.0f * 256.0f)));
        PacketUtil.sendPacket(teleport, Protocol1_8TO1_9.class, true, true);
        PacketUtil.sendPacket(head, Protocol1_8TO1_9.class, true, true);
    }

    @Override
    public void spawn() {
        PacketWrapper spawn = PacketWrapper.create((int)14, null, (UserConnection)this.user);
        spawn.write((Type)Type.VAR_INT, (Object)this.entityId);
        spawn.write((Type)Type.BYTE, (Object)66);
        spawn.write((Type)Type.INT, (Object)0);
        spawn.write((Type)Type.INT, (Object)0);
        spawn.write((Type)Type.INT, (Object)0);
        spawn.write((Type)Type.BYTE, (Object)0);
        spawn.write((Type)Type.BYTE, (Object)0);
        spawn.write((Type)Type.INT, (Object)0);
        PacketUtil.sendPacket(spawn, Protocol1_8TO1_9.class, true, true);
    }

    @Override
    public void despawn() {
        PacketWrapper despawn = PacketWrapper.create((int)19, null, (UserConnection)this.user);
        despawn.write(Type.VAR_INT_ARRAY_PRIMITIVE, (Object)new int[]{this.entityId});
        PacketUtil.sendPacket(despawn, Protocol1_8TO1_9.class, true, true);
    }

    @Override
    public int getEntityId() {
        return this.entityId;
    }
}

