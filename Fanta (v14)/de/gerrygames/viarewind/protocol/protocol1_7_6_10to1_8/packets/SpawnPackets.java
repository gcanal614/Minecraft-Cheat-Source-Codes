/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.Position
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.entities.EntityType
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
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EndermiteReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.GuardianReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.List;
import java.util.UUID;

public class SpawnPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PLAYER, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                    packetWrapper.write(Type.STRING, (Object)uuid.toString());
                    GameProfileStorage gameProfileStorage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
                    if (gameProfile == null) {
                        packetWrapper.write(Type.STRING, (Object)"");
                        packetWrapper.write((Type)Type.VAR_INT, (Object)0);
                    } else {
                        packetWrapper.write(Type.STRING, (Object)(gameProfile.name.length() > 16 ? gameProfile.name.substring(0, 16) : gameProfile.name));
                        packetWrapper.write((Type)Type.VAR_INT, (Object)gameProfile.properties.size());
                        for (GameProfileStorage.Property property : gameProfile.properties) {
                            packetWrapper.write(Type.STRING, (Object)property.name);
                            packetWrapper.write(Type.STRING, (Object)property.value);
                            packetWrapper.write(Type.STRING, (Object)(property.signature == null ? "" : property.signature));
                        }
                    }
                    if (gameProfile != null && gameProfile.gamemode == 3) {
                        int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                        PacketWrapper equipmentPacket = PacketWrapper.create((int)4, null, (UserConnection)packetWrapper.user());
                        equipmentPacket.write((Type)Type.INT, (Object)entityId);
                        equipmentPacket.write((Type)Type.SHORT, (Object)4);
                        equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (Object)gameProfile.getSkull());
                        PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
                        for (short i = 0; i < 4; i = (short)(i + 1)) {
                            equipmentPacket = PacketWrapper.create((int)4, null, (UserConnection)packetWrapper.user());
                            equipmentPacket.write((Type)Type.INT, (Object)entityId);
                            equipmentPacket.write((Type)Type.SHORT, (Object)i);
                            equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, null);
                            PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.addPlayer((Integer)packetWrapper.get((Type)Type.VAR_INT, 0), uuid);
                });
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(packetWrapper -> {
                    List metadata = (List)packetWrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadata);
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.PLAYER);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    EntityTracker tracker;
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    byte typeId = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    int x = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                    int z = (Integer)packetWrapper.get((Type)Type.INT, 2);
                    byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                    byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 2);
                    if (typeId == 71) {
                        switch (yaw) {
                            case -128: {
                                z += 32;
                                yaw = 0;
                                break;
                            }
                            case -64: {
                                x -= 32;
                                yaw = -64;
                                break;
                            }
                            case 0: {
                                z -= 32;
                                yaw = -128;
                                break;
                            }
                            case 64: {
                                x += 32;
                                yaw = 64;
                            }
                        }
                    } else if (typeId == 78) {
                        packetWrapper.cancel();
                        tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                        ArmorStandReplacement armorStand = new ArmorStandReplacement(entityId, packetWrapper.user());
                        armorStand.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        armorStand.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        armorStand.setHeadYaw((float)yaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(armorStand);
                    } else if (typeId == 10) {
                        y += 12;
                    }
                    packetWrapper.set((Type)Type.BYTE, 0, (Object)typeId);
                    packetWrapper.set((Type)Type.INT, 0, (Object)x);
                    packetWrapper.set((Type)Type.INT, 1, (Object)y);
                    packetWrapper.set((Type)Type.INT, 2, (Object)z);
                    packetWrapper.set((Type)Type.BYTE, 1, (Object)pitch);
                    packetWrapper.set((Type)Type.BYTE, 2, (Object)yaw);
                    tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    Entity1_10Types.EntityType type = Entity1_10Types.getTypeFromId((int)typeId, (boolean)true);
                    tracker.getClientEntityTypes().put(entityId, type);
                    tracker.sendMetadataBuffer(entityId);
                    int data = (Integer)packetWrapper.get((Type)Type.INT, 3);
                    if (type != null && type.isOrHasParent((EntityType)Entity1_10Types.EntityType.FALLING_BLOCK)) {
                        int blockId = data & 0xFFF;
                        int blockData = data >> 12 & 0xF;
                        Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(blockId, blockData);
                        if (replace != null) {
                            blockId = replace.getId();
                            blockData = replace.replaceData(blockData);
                        }
                        data = blockId | blockData << 16;
                        packetWrapper.set((Type)Type.INT, 3, (Object)data);
                    }
                    if (data > 0) {
                        packetWrapper.passthrough((Type)Type.SHORT);
                        packetWrapper.passthrough((Type)Type.SHORT);
                        packetWrapper.passthrough((Type)Type.SHORT);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_MOB, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
                this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    short typeId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    int x = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                    int z = (Integer)packetWrapper.get((Type)Type.INT, 2);
                    byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                    byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    byte headYaw = (Byte)packetWrapper.get((Type)Type.BYTE, 2);
                    if (typeId == 30) {
                        packetWrapper.cancel();
                        EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                        ArmorStandReplacement armorStand = new ArmorStandReplacement(entityId, packetWrapper.user());
                        armorStand.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        armorStand.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        armorStand.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(armorStand);
                    } else if (typeId == 68) {
                        packetWrapper.cancel();
                        EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                        GuardianReplacement guardian = new GuardianReplacement(entityId, packetWrapper.user());
                        guardian.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        guardian.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        guardian.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(guardian);
                    } else if (typeId == 67) {
                        packetWrapper.cancel();
                        EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                        EndermiteReplacement endermite = new EndermiteReplacement(entityId, packetWrapper.user());
                        endermite.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        endermite.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        endermite.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                        tracker.addEntityReplacement(endermite);
                    } else if (typeId == 101 || typeId == 255 || typeId == -1) {
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
                    List metadataList = (List)wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
                    int entityId = (Integer)wrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                    if (tracker.getEntityReplacement(entityId) != null) {
                        tracker.getEntityReplacement(entityId).updateMetadata(metadataList);
                    } else if (tracker.getClientEntityTypes().containsKey(entityId)) {
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                    } else {
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PAINTING, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.INT, (Object)position.getY());
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.map((Type)Type.UNSIGNED_BYTE, (Type)Type.INT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.PAINTING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.SHORT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.EXPERIENCE_ORB);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.getClientEntityTypes().put(entityId, Entity1_10Types.EntityType.LIGHTNING);
                    tracker.sendMetadataBuffer(entityId);
                });
            }
        });
    }
}

