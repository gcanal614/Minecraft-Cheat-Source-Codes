/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.metadata.MetaType
 *  com.viaversion.viaversion.api.minecraft.metadata.Metadata
 *  com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.type.Type
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.math.AABB;
import de.gerrygames.viarewind.utils.math.Vector3d;
import java.util.ArrayList;
import java.util.List;

public class ArmorStandReplacement
implements EntityReplacement {
    private int entityId;
    private List<Metadata> datawatcher = new ArrayList<Metadata>();
    private int[] entityIds = null;
    private double locX;
    private double locY;
    private double locZ;
    private State currentState = null;
    private boolean invisible = false;
    private boolean nameTagVisible = false;
    private String name = null;
    private UserConnection user;
    private float yaw;
    private float pitch;
    private float headYaw;
    private boolean small = false;
    private boolean marker = false;
    private static int ENTITY_ID = 2147467647;

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    public ArmorStandReplacement(int entityId, UserConnection user) {
        this.entityId = entityId;
        this.user = user;
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
        if (this.yaw != yaw && this.pitch != pitch || this.headYaw != yaw) {
            this.yaw = yaw;
            this.headYaw = yaw;
            this.pitch = pitch;
            this.updateLocation();
        }
    }

    @Override
    public void setHeadYaw(float yaw) {
        if (this.headYaw != yaw) {
            this.headYaw = yaw;
            this.updateLocation();
        }
    }

    @Override
    public void updateMetadata(List<Metadata> metadataList) {
        for (Metadata metadata : metadataList) {
            this.datawatcher.removeIf(m -> m.id() == metadata.id());
            this.datawatcher.add(metadata);
        }
        this.updateState();
    }

    public void updateState() {
        int flags = 0;
        int armorStandFlags = 0;
        for (Metadata metadata : this.datawatcher) {
            if (metadata.id() == 0 && metadata.metaType() == MetaType1_8.Byte) {
                flags = ((Byte)metadata.getValue()).byteValue();
                continue;
            }
            if (metadata.id() == 2 && metadata.metaType() == MetaType1_8.String) {
                this.name = (String)metadata.getValue();
                if (this.name == null || !this.name.equals("")) continue;
                this.name = null;
                continue;
            }
            if (metadata.id() == 10 && metadata.metaType() == MetaType1_8.Byte) {
                armorStandFlags = ((Byte)metadata.getValue()).byteValue();
                continue;
            }
            if (metadata.id() != 3 || metadata.metaType() != MetaType1_8.Byte) continue;
            this.nameTagVisible = (byte)metadata.id() != 0;
        }
        this.invisible = (flags & 0x20) != 0;
        this.small = armorStandFlags & true;
        this.marker = (armorStandFlags & 0x10) != 0;
        State prevState = this.currentState;
        this.currentState = this.invisible && this.name != null ? State.HOLOGRAM : State.ZOMBIE;
        if (this.currentState != prevState) {
            this.despawn();
            this.spawn();
        } else {
            this.updateMetadata();
            this.updateLocation();
        }
    }

    public void updateLocation() {
        if (this.entityIds == null) {
            return;
        }
        if (this.currentState == State.ZOMBIE) {
            PacketWrapper teleport = PacketWrapper.create((int)24, null, (UserConnection)this.user);
            teleport.write((Type)Type.INT, (Object)this.entityId);
            teleport.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
            teleport.write((Type)Type.INT, (Object)((int)(this.locY * 32.0)));
            teleport.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
            teleport.write((Type)Type.BYTE, (Object)((byte)(this.yaw / 360.0f * 256.0f)));
            teleport.write((Type)Type.BYTE, (Object)((byte)(this.pitch / 360.0f * 256.0f)));
            PacketWrapper head = PacketWrapper.create((int)25, null, (UserConnection)this.user);
            head.write((Type)Type.INT, (Object)this.entityId);
            head.write((Type)Type.BYTE, (Object)((byte)(this.headYaw / 360.0f * 256.0f)));
            PacketUtil.sendPacket(teleport, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(head, Protocol1_7_6_10TO1_8.class, true, true);
        } else if (this.currentState == State.HOLOGRAM) {
            PacketWrapper detach = PacketWrapper.create((int)27, null, (UserConnection)this.user);
            detach.write((Type)Type.INT, (Object)this.entityIds[1]);
            detach.write((Type)Type.INT, (Object)-1);
            detach.write((Type)Type.BOOLEAN, (Object)false);
            PacketWrapper teleportSkull = PacketWrapper.create((int)24, null, (UserConnection)this.user);
            teleportSkull.write((Type)Type.INT, (Object)this.entityIds[0]);
            teleportSkull.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
            teleportSkull.write((Type)Type.INT, (Object)((int)((this.locY + (this.marker ? 54.85 : (this.small ? 56.0 : 57.0))) * 32.0)));
            teleportSkull.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
            teleportSkull.write((Type)Type.BYTE, (Object)0);
            teleportSkull.write((Type)Type.BYTE, (Object)0);
            PacketWrapper teleportHorse = PacketWrapper.create((int)24, null, (UserConnection)this.user);
            teleportHorse.write((Type)Type.INT, (Object)this.entityIds[1]);
            teleportHorse.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
            teleportHorse.write((Type)Type.INT, (Object)((int)((this.locY + 56.75) * 32.0)));
            teleportHorse.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
            teleportHorse.write((Type)Type.BYTE, (Object)0);
            teleportHorse.write((Type)Type.BYTE, (Object)0);
            PacketWrapper attach = PacketWrapper.create((int)27, null, (UserConnection)this.user);
            attach.write((Type)Type.INT, (Object)this.entityIds[1]);
            attach.write((Type)Type.INT, (Object)this.entityIds[0]);
            attach.write((Type)Type.BOOLEAN, (Object)false);
            PacketUtil.sendPacket(detach, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(teleportSkull, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(teleportHorse, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(attach, Protocol1_7_6_10TO1_8.class, true, true);
        }
    }

    public void updateMetadata() {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper metadataPacket = PacketWrapper.create((int)28, null, (UserConnection)this.user);
        if (this.currentState == State.ZOMBIE) {
            metadataPacket.write((Type)Type.INT, (Object)this.entityIds[0]);
            ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
            for (Metadata metadata : this.datawatcher) {
                if (metadata.id() < 0 || metadata.id() > 9) continue;
                metadataList.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
            }
            if (this.small) {
                metadataList.add(new Metadata(12, (MetaType)MetaType1_8.Byte, (Object)1));
            }
            MetadataRewriter.transform(Entity1_10Types.EntityType.ZOMBIE, metadataList);
            metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
        } else if (this.currentState == State.HOLOGRAM) {
            metadataPacket.write((Type)Type.INT, (Object)this.entityIds[1]);
            ArrayList<Metadata> metadataList = new ArrayList<Metadata>();
            metadataList.add(new Metadata(12, (MetaType)MetaType1_7_6_10.Int, (Object)-1700000));
            metadataList.add(new Metadata(10, (MetaType)MetaType1_7_6_10.String, (Object)this.name));
            metadataList.add(new Metadata(11, (MetaType)MetaType1_7_6_10.Byte, (Object)1));
            metadataPacket.write(Types1_7_6_10.METADATA_LIST, metadataList);
        } else {
            return;
        }
        PacketUtil.sendPacket(metadataPacket, Protocol1_7_6_10TO1_8.class);
    }

    @Override
    public void spawn() {
        if (this.entityIds != null) {
            this.despawn();
        }
        if (this.currentState == State.ZOMBIE) {
            PacketWrapper spawn = PacketWrapper.create((int)15, null, (UserConnection)this.user);
            spawn.write((Type)Type.VAR_INT, (Object)this.entityId);
            spawn.write((Type)Type.UNSIGNED_BYTE, (Object)54);
            spawn.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
            spawn.write((Type)Type.INT, (Object)((int)(this.locY * 32.0)));
            spawn.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
            spawn.write((Type)Type.BYTE, (Object)0);
            spawn.write((Type)Type.BYTE, (Object)0);
            spawn.write((Type)Type.BYTE, (Object)0);
            spawn.write((Type)Type.SHORT, (Object)0);
            spawn.write((Type)Type.SHORT, (Object)0);
            spawn.write((Type)Type.SHORT, (Object)0);
            spawn.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
            PacketUtil.sendPacket(spawn, Protocol1_7_6_10TO1_8.class, true, true);
            this.entityIds = new int[]{this.entityId};
        } else if (this.currentState == State.HOLOGRAM) {
            int[] entityIds = new int[]{this.entityId, ENTITY_ID--};
            PacketWrapper spawnSkull = PacketWrapper.create((int)14, null, (UserConnection)this.user);
            spawnSkull.write((Type)Type.VAR_INT, (Object)entityIds[0]);
            spawnSkull.write((Type)Type.BYTE, (Object)66);
            spawnSkull.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
            spawnSkull.write((Type)Type.INT, (Object)((int)(this.locY * 32.0)));
            spawnSkull.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
            spawnSkull.write((Type)Type.BYTE, (Object)0);
            spawnSkull.write((Type)Type.BYTE, (Object)0);
            spawnSkull.write((Type)Type.INT, (Object)0);
            PacketWrapper spawnHorse = PacketWrapper.create((int)15, null, (UserConnection)this.user);
            spawnHorse.write((Type)Type.VAR_INT, (Object)entityIds[1]);
            spawnHorse.write((Type)Type.UNSIGNED_BYTE, (Object)100);
            spawnHorse.write((Type)Type.INT, (Object)((int)(this.locX * 32.0)));
            spawnHorse.write((Type)Type.INT, (Object)((int)(this.locY * 32.0)));
            spawnHorse.write((Type)Type.INT, (Object)((int)(this.locZ * 32.0)));
            spawnHorse.write((Type)Type.BYTE, (Object)0);
            spawnHorse.write((Type)Type.BYTE, (Object)0);
            spawnHorse.write((Type)Type.BYTE, (Object)0);
            spawnHorse.write((Type)Type.SHORT, (Object)0);
            spawnHorse.write((Type)Type.SHORT, (Object)0);
            spawnHorse.write((Type)Type.SHORT, (Object)0);
            spawnHorse.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
            PacketUtil.sendPacket(spawnSkull, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(spawnHorse, Protocol1_7_6_10TO1_8.class, true, true);
            this.entityIds = entityIds;
        }
        this.updateMetadata();
        this.updateLocation();
    }

    public AABB getBoundingBox() {
        double w = this.small ? 0.25 : 0.5;
        double h = this.small ? 0.9875 : 1.975;
        Vector3d min = new Vector3d(this.locX - w / 2.0, this.locY, this.locZ - w / 2.0);
        Vector3d max = new Vector3d(this.locX + w / 2.0, this.locY + h, this.locZ + w / 2.0);
        return new AABB(min, max);
    }

    @Override
    public void despawn() {
        if (this.entityIds == null) {
            return;
        }
        PacketWrapper despawn = PacketWrapper.create((int)19, null, (UserConnection)this.user);
        despawn.write((Type)Type.BYTE, (Object)((byte)this.entityIds.length));
        for (int id : this.entityIds) {
            despawn.write((Type)Type.INT, (Object)id);
        }
        this.entityIds = null;
        PacketUtil.sendPacket(despawn, Protocol1_7_6_10TO1_8.class, true, true);
    }

    private static enum State {
        HOLOGRAM,
        ZOMBIE;

    }
}

