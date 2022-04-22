/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.Position
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.item.Item
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.api.type.types.version.Types1_8
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.List;
import java.util.UUID;

public class EntityPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_EQUIPMENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.SHORT);
                this.map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                this.handler(packetWrapper -> {
                    Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    ItemRewriter.toClient(item);
                    packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, (Object)item);
                });
                this.handler(packetWrapper -> {
                    if ((Short)packetWrapper.get((Type)Type.SHORT, 0) > 4) {
                        packetWrapper.cancel();
                    }
                });
                this.handler(packetWrapper -> {
                    if (packetWrapper.isCancelled()) {
                        return;
                    }
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    UUID uuid = tracker.getPlayerUUID((Integer)packetWrapper.get((Type)Type.INT, 0));
                    if (uuid == null) {
                        return;
                    }
                    Item[] equipment = tracker.getPlayerEquipment(uuid);
                    if (equipment == null) {
                        equipment = new Item[5];
                        tracker.setPlayerEquipment(uuid, equipment);
                    }
                    equipment[((Short)packetWrapper.get((Type)Type.SHORT, (int)0)).shortValue()] = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    GameProfileStorage storage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile profile = storage.get(uuid);
                    if (profile != null && profile.gamemode == 3) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.USE_BED, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)((short)position.getY()));
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.COLLECT_ITEM, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_VELOCITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.DESTROY_ENTITIES, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int[] entityIds = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    for (int entityId : entityIds) {
                        tracker.removeEntity(entityId);
                    }
                    while (entityIds.length > 127) {
                        int[] entityIds2 = new int[127];
                        System.arraycopy(entityIds, 0, entityIds2, 0, 127);
                        int[] temp = new int[entityIds.length - 127];
                        System.arraycopy(entityIds, 127, temp, 0, temp.length);
                        entityIds = temp;
                        PacketWrapper destroy = PacketWrapper.create((int)19, null, (UserConnection)packetWrapper.user());
                        destroy.write(Types1_7_6_10.INT_ARRAY, (Object)entityIds2);
                        PacketUtil.sendPacket(destroy, Protocol1_7_6_10TO1_8.class);
                    }
                    packetWrapper.write(Types1_7_6_10.INT_ARRAY, (Object)entityIds);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_MOVEMENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte x = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        byte y = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        byte z = (Byte)packetWrapper.get((Type)Type.BYTE, 2);
                        replacement.relMove((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte x = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        byte y = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        byte z = (Byte)packetWrapper.get((Type)Type.BYTE, 2);
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 3);
                        byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 4);
                        replacement.relMove((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type = tracker.getClientEntityTypes().get(entityId);
                    if (type == Entity1_10Types.EntityType.MINECART_ABSTRACT) {
                        int y = (Integer)packetWrapper.get((Type)Type.INT, 2);
                        packetWrapper.set((Type)Type.INT, 2, (Object)(y += 12));
                    }
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int x = (Integer)packetWrapper.get((Type)Type.INT, 1);
                        int y = (Integer)packetWrapper.get((Type)Type.INT, 2);
                        int z = (Integer)packetWrapper.get((Type)Type.INT, 3);
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        replacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_HEAD_LOOK, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        replacement.setHeadYaw((float)yaw * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ATTACH_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    boolean leash = (Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0);
                    if (leash) {
                        return;
                    }
                    int passenger = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    int vehicle = (Integer)packetWrapper.get((Type)Type.INT, 1);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.setPassenger(vehicle, passenger);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_METADATA, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(wrapper -> {
                    List metadataList = (List)wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    int entityId = (Integer)wrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                    if (tracker.getClientEntityTypes().containsKey(entityId)) {
                        EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                        if (replacement != null) {
                            wrapper.cancel();
                            replacement.updateMetadata(metadataList);
                        } else {
                            MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                            if (metadataList.isEmpty()) {
                                wrapper.cancel();
                            }
                        }
                    } else {
                        tracker.addMetadataToBuffer(entityId, metadataList);
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_EFFECT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.VAR_INT, (Type)Type.SHORT);
                this.map((Type)Type.BYTE, (Type)Type.NOTHING);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.REMOVE_ENTITY_EFFECT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.map((Type)Type.BYTE);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_PROPERTIES, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT, (Type)Type.INT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    if (tracker.getEntityReplacement(entityId) != null) {
                        packetWrapper.cancel();
                        return;
                    }
                    int amount = (Integer)packetWrapper.passthrough((Type)Type.INT);
                    for (int i = 0; i < amount; ++i) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough((Type)Type.DOUBLE);
                        int modifierlength = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                        packetWrapper.write((Type)Type.SHORT, (Object)((short)modifierlength));
                        for (int j = 0; j < modifierlength; ++j) {
                            packetWrapper.passthrough(Type.UUID);
                            packetWrapper.passthrough((Type)Type.DOUBLE);
                            packetWrapper.passthrough((Type)Type.BYTE);
                        }
                    }
                });
            }
        });
        protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_ENTITY_NBT);
    }
}

