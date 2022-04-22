/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.Vector
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.item.Item
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
 *  com.viaversion.viaversion.util.Pair
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
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
import com.viaversion.viaversion.util.Pair;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.util.RelativeMoveUtil;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_STATUS, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    byte status = (Byte)packetWrapper.read((Type)Type.BYTE);
                    if (status > 23) {
                        packetWrapper.cancel();
                        return;
                    }
                    packetWrapper.write((Type)Type.BYTE, (Object)status);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    short relX = (Short)packetWrapper.read((Type)Type.SHORT);
                    short relY = (Short)packetWrapper.read((Type)Type.SHORT);
                    short relZ = (Short)packetWrapper.read((Type)Type.SHORT);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        replacement.relMove((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
                        return;
                    }
                    Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)moves[0].getBlockX()));
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)moves[0].getBlockY()));
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)moves[0].getBlockZ()));
                    boolean onGround = (Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN);
                    if (moves.length > 1) {
                        PacketWrapper secondPacket = PacketWrapper.create((int)21, null, (UserConnection)packetWrapper.user());
                        secondPacket.write((Type)Type.VAR_INT, (Object)((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)));
                        secondPacket.write((Type)Type.BYTE, (Object)((byte)moves[1].getBlockX()));
                        secondPacket.write((Type)Type.BYTE, (Object)((byte)moves[1].getBlockY()));
                        secondPacket.write((Type)Type.BYTE, (Object)((byte)moves[1].getBlockZ()));
                        secondPacket.write((Type)Type.BOOLEAN, (Object)onGround);
                        PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_POSITION_AND_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    short relX = (Short)packetWrapper.read((Type)Type.SHORT);
                    short relY = (Short)packetWrapper.read((Type)Type.SHORT);
                    short relZ = (Short)packetWrapper.read((Type)Type.SHORT);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        replacement.relMove((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
                        replacement.setYawPitch((float)((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue() * 360.0f / 256.0f, (float)((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue() * 360.0f / 256.0f);
                        return;
                    }
                    Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)moves[0].getBlockX()));
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)moves[0].getBlockY()));
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)moves[0].getBlockZ()));
                    byte yaw = (Byte)packetWrapper.passthrough((Type)Type.BYTE);
                    byte pitch = (Byte)packetWrapper.passthrough((Type)Type.BYTE);
                    boolean onGround = (Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN);
                    Entity1_10Types.EntityType type = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(entityId);
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        yaw = (byte)(yaw - 64);
                        packetWrapper.set((Type)Type.BYTE, 3, (Object)yaw);
                    }
                    if (moves.length > 1) {
                        PacketWrapper secondPacket = PacketWrapper.create((int)23, null, (UserConnection)packetWrapper.user());
                        secondPacket.write((Type)Type.VAR_INT, (Object)((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)));
                        secondPacket.write((Type)Type.BYTE, (Object)((byte)moves[1].getBlockX()));
                        secondPacket.write((Type)Type.BYTE, (Object)((byte)moves[1].getBlockY()));
                        secondPacket.write((Type)Type.BYTE, (Object)((byte)moves[1].getBlockZ()));
                        secondPacket.write((Type)Type.BYTE, (Object)yaw);
                        secondPacket.write((Type)Type.BYTE, (Object)pitch);
                        secondPacket.write((Type)Type.BOOLEAN, (Object)onGround);
                        PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    Entity1_10Types.EntityType type = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(entityId);
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        yaw = (byte)(yaw - 64);
                        packetWrapper.set((Type)Type.BYTE, 0, (Object)yaw);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.VEHICLE_MOVE, (ClientboundPacketType)ClientboundPackets1_8.ENTITY_TELEPORT, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    int vehicle = tracker.getVehicle(tracker.getPlayerId());
                    if (vehicle == -1) {
                        packetWrapper.cancel();
                    }
                    packetWrapper.write((Type)Type.VAR_INT, (Object)vehicle);
                });
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                this.map((Type)Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
                this.handler(packetWrapper -> {
                    if (packetWrapper.isCancelled()) {
                        return;
                    }
                    PlayerPosition position = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    double x = (double)((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue() / 32.0;
                    double y = (double)((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue() / 32.0;
                    double z = (double)((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue() / 32.0;
                    position.setPos(x, y, z);
                });
                this.create((Type)Type.BOOLEAN, true);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    Entity1_10Types.EntityType type = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(entityId);
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        yaw = (byte)(yaw - 64);
                        packetWrapper.set((Type)Type.BYTE, 0, (Object)yaw);
                        int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                        packetWrapper.set((Type)Type.INT, 1, (Object)(y += 10));
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.DESTROY_ENTITIES, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    for (int entityId : (int[])packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                        tracker.removeEntity(entityId);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.REMOVE_ENTITY_EFFECT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> {
                    byte id = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    if (id > 23) {
                        packetWrapper.cancel();
                    }
                    if (id == 25) {
                        if (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() != ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId()) {
                            return;
                        }
                        Levitation levitation = (Levitation)packetWrapper.user().get(Levitation.class);
                        levitation.setActive(false);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_HEAD_LOOK, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
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
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_METADATA, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
                this.handler(wrapper -> {
                    List metadataList = (List)wrapper.get(Types1_8.METADATA_LIST, 0);
                    int entityId = (Integer)wrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                    if (tracker.getClientEntityTypes().containsKey(entityId)) {
                        MetadataRewriter.transform(tracker.getClientEntityTypes().get(entityId), metadataList);
                        if (metadataList.isEmpty()) {
                            wrapper.cancel();
                        }
                    } else {
                        tracker.addMetadataToBuffer(entityId, metadataList);
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ATTACH_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.create((Type)Type.BOOLEAN, true);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_EQUIPMENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int slot = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    if (slot == 1) {
                        packetWrapper.cancel();
                    } else if (slot > 1) {
                        --slot;
                    }
                    packetWrapper.write((Type)Type.SHORT, (Object)((short)slot));
                });
                this.map(Type.ITEM);
                this.handler(packetWrapper -> packetWrapper.set(Type.ITEM, 0, (Object)ItemRewriter.toClient((Item)packetWrapper.get(Type.ITEM, 0))));
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SET_PASSENGERS, null, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    int vehicle = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    int count = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    ArrayList<Integer> passengers = new ArrayList<Integer>();
                    for (int i = 0; i < count; ++i) {
                        passengers.add((Integer)packetWrapper.read((Type)Type.VAR_INT));
                    }
                    List<Integer> oldPassengers = entityTracker.getPassengers(vehicle);
                    entityTracker.setPassengers(vehicle, passengers);
                    if (!oldPassengers.isEmpty()) {
                        for (Integer passenger : oldPassengers) {
                            PacketWrapper detach = PacketWrapper.create((int)27, null, (UserConnection)packetWrapper.user());
                            detach.write((Type)Type.INT, (Object)passenger);
                            detach.write((Type)Type.INT, (Object)-1);
                            detach.write((Type)Type.BOOLEAN, (Object)false);
                            PacketUtil.sendPacket(detach, Protocol1_8TO1_9.class);
                        }
                    }
                    for (int i = 0; i < count; ++i) {
                        int v = i == 0 ? vehicle : passengers.get(i - 1);
                        int p = passengers.get(i);
                        PacketWrapper attach = PacketWrapper.create((int)27, null, (UserConnection)packetWrapper.user());
                        attach.write((Type)Type.INT, (Object)p);
                        attach.write((Type)Type.INT, (Object)v);
                        attach.write((Type)Type.BOOLEAN, (Object)false);
                        PacketUtil.sendPacket(attach, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_TELEPORT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    Entity1_10Types.EntityType type = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(entityId);
                    if (type == Entity1_10Types.EntityType.BOAT) {
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        yaw = (byte)(yaw - 64);
                        packetWrapper.set((Type)Type.BYTE, 0, (Object)yaw);
                        int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                        packetWrapper.set((Type)Type.INT, 1, (Object)(y += 10));
                    }
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).resetEntityOffset(entityId);
                });
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (replacement != null) {
                        packetWrapper.cancel();
                        int x = (Integer)packetWrapper.get((Type)Type.INT, 0);
                        int y = (Integer)packetWrapper.get((Type)Type.INT, 1);
                        int z = (Integer)packetWrapper.get((Type)Type.INT, 2);
                        byte yaw = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        byte pitch = (Byte)packetWrapper.get((Type)Type.BYTE, 1);
                        replacement.setLocation((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        replacement.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_PROPERTIES, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    boolean player = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() == ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId();
                    int size = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    int removed = 0;
                    for (int i = 0; i < size; ++i) {
                        String key = (String)packetWrapper.read(Type.STRING);
                        boolean skip = !Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(key);
                        double value = (Double)packetWrapper.read((Type)Type.DOUBLE);
                        int modifiersize = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                        if (!skip) {
                            packetWrapper.write(Type.STRING, (Object)key);
                            packetWrapper.write((Type)Type.DOUBLE, (Object)value);
                            packetWrapper.write((Type)Type.VAR_INT, (Object)modifiersize);
                        } else {
                            ++removed;
                        }
                        ArrayList<Pair<Byte, Double>> modifiers = new ArrayList<Pair<Byte, Double>>();
                        for (int j = 0; j < modifiersize; ++j) {
                            UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                            double amount = (Double)packetWrapper.read((Type)Type.DOUBLE);
                            byte operation = (Byte)packetWrapper.read((Type)Type.BYTE);
                            modifiers.add((Pair<Byte, Double>)new Pair((Object)operation, (Object)amount));
                            if (skip) continue;
                            packetWrapper.write(Type.UUID, (Object)uuid);
                            packetWrapper.write((Type)Type.DOUBLE, (Object)amount);
                            packetWrapper.write((Type)Type.BYTE, (Object)operation);
                        }
                        if (!player || !key.equals("generic.attackSpeed")) continue;
                        ((Cooldown)packetWrapper.user().get(Cooldown.class)).setAttackSpeed(value, modifiers);
                    }
                    packetWrapper.set((Type)Type.INT, 0, (Object)(size - removed));
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_EFFECT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> {
                    byte id = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    if (id > 23) {
                        packetWrapper.cancel();
                    }
                    if (id == 25) {
                        if (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() != ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId()) {
                            return;
                        }
                        Levitation levitation = (Levitation)packetWrapper.user().get(Levitation.class);
                        levitation.setActive(true);
                        levitation.setAmplifier(((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue());
                    }
                });
            }
        });
    }
}

