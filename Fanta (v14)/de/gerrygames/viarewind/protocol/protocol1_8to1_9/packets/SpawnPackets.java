/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.entities.EntityType
 *  com.viaversion.viaversion.api.protocol.Protocol
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.api.type.types.version.Types1_8
 *  com.viaversion.viaversion.api.type.types.version.Types1_9
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerBulletReplacement;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerReplacement;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.List;

public class SpawnPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.UUID, (Type)Type.NOTHING);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    int blockData;
                    int blockId;
                    Replacement replace;
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    byte typeId = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type = Entity1_10Types.getTypeFromId((int)typeId, (boolean)true);
                    if (typeId == 3 || typeId == 91 || typeId == 92 || typeId == 93) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (type == null) {
                        ViaRewind.getPlatform().getLogger().warning("[ViaRewind] Unhandled Spawn Object Type: " + typeId);
                        packetWrapper.cancel();
                        return;
                    }
                    int x = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                    int z = (Integer)packetWrapper.get((Type)Type.INT, 2);
                    if (type.is((EntityType)Entity1_10Types.EntityType.BOAT)) {
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        yaw = (byte)(yaw - 64);
                        packetWrapper.set((Type)Type.BYTE, 1, (Object)yaw);
                        packetWrapper.set((Type)Type.INT, 1, (Object)(y += 10));
                    } else if (type.is((EntityType)Entity1_10Types.EntityType.SHULKER_BULLET)) {
                        packetWrapper.cancel();
                        ShulkerBulletReplacement shulkerBulletReplacement = new ShulkerBulletReplacement(entityId, packetWrapper.user());
                        shulkerBulletReplacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        tracker.addEntityReplacement(shulkerBulletReplacement);
                        return;
                    }
                    int data = (Integer)packetWrapper.get((Type)Type.INT, 3);
                    if (type.isOrHasParent((EntityType)Entity1_10Types.EntityType.ARROW) && data != 0) {
                        packetWrapper.set((Type)Type.INT, 3, (Object)(--data));
                    }
                    if (type.is((EntityType)Entity1_10Types.EntityType.FALLING_BLOCK) && (replace = ReplacementRegistry1_8to1_9.getReplacement(blockId = data & 0xFFF, blockData = data >> 12 & 0xF)) != null) {
                        packetWrapper.set((Type)Type.INT, 3, (Object)(replace.getId() | replace.replaceData(data) << 12));
                    }
                    if (data > 0) {
                        packetWrapper.passthrough((Type)Type.SHORT);
                        packetWrapper.passthrough((Type)Type.SHORT);
                        packetWrapper.passthrough((Type)Type.SHORT);
                    } else {
                        short vX = (Short)packetWrapper.read((Type)Type.SHORT);
                        short vY = (Short)packetWrapper.read((Type)Type.SHORT);
                        short vZ = (Short)packetWrapper.read((Type)Type.SHORT);
                        PacketWrapper velocityPacket = PacketWrapper.create((int)18, null, (UserConnection)packetWrapper.user());
                        velocityPacket.write((Type)Type.VAR_INT, (Object)entityId);
                        velocityPacket.write((Type)Type.SHORT, (Object)vX);
                        velocityPacket.write((Type)Type.SHORT, (Object)vY);
                        velocityPacket.write((Type)Type.SHORT, (Object)vZ);
                        PacketUtil.sendPacket(velocityPacket, Protocol1_8TO1_9.class);
                    }
                    tracker.getClientEntityTypes().put(entityId, type);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_EXPERIENCE_ORB, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.SHORT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_GLOBAL_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.LIGHTNING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_MOB, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.UUID, (Type)Type.NOTHING);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    short typeId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    int x = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                    int z = (Integer)packetWrapper.get((Type)Type.INT, 2);
                    byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                    byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    byte headYaw = (Byte)packetWrapper.get((Type)Type.BYTE, 2);
                    if (typeId == 69) {
                        packetWrapper.cancel();
                        EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                        ShulkerReplacement shulkerReplacement = new ShulkerReplacement(entityId, packetWrapper.user());
                        shulkerReplacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        shulkerReplacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        shulkerReplacement.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(shulkerReplacement);
                    } else if (typeId == -1 || typeId == 255) {
                        packetWrapper.cancel();
                    }
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    short typeId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.getTypeFromId((int)typeId, (boolean)false));
                    tracker.sendMetadataBuffer(entityId);
                });
                this.handler(wrapper -> {
                    List metadataList = (List)wrapper.get(Types1_8.METADATA_LIST, 0);
                    int entityId = (Integer)wrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        replacement.updateMetadata(metadataList);
                    } else if (tracker.getClientEntityTypes().containsKey(entityId)) {
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_PAINTING, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.UUID, (Type)Type.NOTHING);
                this.map(Type.STRING);
                this.map(Type.POSITION);
                this.map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.PAINTING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_PLAYER, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.UUID);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> packetWrapper.write((Type)Type.SHORT, (Object)0));
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(wrapper -> {
                    List metadataList = (List)wrapper.get(Types1_8.METADATA_LIST, 0);
                    MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadataList);
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.PLAYER);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
    }
}

