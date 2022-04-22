/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.Position
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.item.Item
 *  com.viaversion.viaversion.api.minecraft.metadata.MetaType
 *  com.viaversion.viaversion.api.minecraft.metadata.Metadata
 *  com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8
 *  com.viaversion.viaversion.api.protocol.Protocol
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.api.type.types.version.Types1_8
 *  com.viaversion.viaversion.libs.gson.JsonElement
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.UUID;

public class PlayerPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BOSSBAR, null, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                    int action = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    BossBarStorage bossBarStorage = (BossBarStorage)packetWrapper.user().get(BossBarStorage.class);
                    if (action == 0) {
                        bossBarStorage.add(uuid, ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)), ((Float)packetWrapper.read((Type)Type.FLOAT)).floatValue());
                        packetWrapper.read((Type)Type.VAR_INT);
                        packetWrapper.read((Type)Type.VAR_INT);
                        packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                    } else if (action == 1) {
                        bossBarStorage.remove(uuid);
                    } else if (action == 2) {
                        bossBarStorage.updateHealth(uuid, ((Float)packetWrapper.read((Type)Type.FLOAT)).floatValue());
                    } else if (action == 3) {
                        String title = ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT));
                        bossBarStorage.updateTitle(uuid, title);
                    }
                });
            }
        });
        protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_9.COOLDOWN);
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.PLUGIN_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String channel = (String)packetWrapper.get(Type.STRING, 0);
                    if (channel.equalsIgnoreCase("MC|TrList")) {
                        packetWrapper.passthrough((Type)Type.INT);
                        int size = packetWrapper.isReadable((Type)Type.BYTE, 0) ? ((Byte)packetWrapper.passthrough((Type)Type.BYTE)).byteValue() : ((Short)packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
                        for (int i = 0; i < size; ++i) {
                            packetWrapper.write(Type.ITEM, (Object)ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                            packetWrapper.write(Type.ITEM, (Object)ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                            boolean has3Items = (Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN);
                            if (has3Items) {
                                packetWrapper.write(Type.ITEM, (Object)ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
                            }
                            packetWrapper.passthrough((Type)Type.BOOLEAN);
                            packetWrapper.passthrough((Type)Type.INT);
                            packetWrapper.passthrough((Type)Type.INT);
                        }
                    } else if (channel.equalsIgnoreCase("MC|BOpen")) {
                        packetWrapper.read((Type)Type.VAR_INT);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.GAME_EVENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    short reason = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    if (reason == 3) {
                        ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Float)packetWrapper.get((Type)Type.FLOAT, 0)).intValue());
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.JOIN_GAME, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.setPlayerId((Integer)packetWrapper.get((Type)Type.INT, 0));
                    tracker.setPlayerGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue());
                    tracker.getClientEntityTypes().put(tracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                });
                this.handler(packetWrapper -> {
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    world.setEnvironment((int)((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue());
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.PLAYER_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> {
                    PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    int teleportId = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    pos.setConfirmId(teleportId);
                    byte flags = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    double x = (Double)packetWrapper.get((Type)Type.DOUBLE, 0);
                    double y = (Double)packetWrapper.get((Type)Type.DOUBLE, 1);
                    double z = (Double)packetWrapper.get((Type)Type.DOUBLE, 2);
                    float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
                    float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
                    packetWrapper.set((Type)Type.BYTE, 0, (Object)0);
                    if (flags != 0) {
                        if ((flags & 1) != 0) {
                            packetWrapper.set((Type)Type.DOUBLE, 0, (Object)(x += pos.getPosX()));
                        }
                        if ((flags & 2) != 0) {
                            packetWrapper.set((Type)Type.DOUBLE, 1, (Object)(y += pos.getPosY()));
                        }
                        if ((flags & 4) != 0) {
                            packetWrapper.set((Type)Type.DOUBLE, 2, (Object)(z += pos.getPosZ()));
                        }
                        if ((flags & 8) != 0) {
                            packetWrapper.set((Type)Type.FLOAT, 0, (Object)Float.valueOf(yaw += pos.getYaw()));
                        }
                        if ((flags & 0x10) != 0) {
                            packetWrapper.set((Type)Type.FLOAT, 1, (Object)Float.valueOf(pitch += pos.getPitch()));
                        }
                    }
                    pos.setPos(x, y, z);
                    pos.setYaw(yaw);
                    pos.setPitch(pitch);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.RESPAWN, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(packetWrapper -> ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue()));
                this.handler(packetWrapper -> {
                    ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
                    ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).changeWorld();
                });
                this.handler(packetWrapper -> {
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    world.setEnvironment(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CHAT_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String msg = (String)packetWrapper.get(Type.STRING, 0);
                    if (msg.toLowerCase().startsWith("/offhand")) {
                        packetWrapper.cancel();
                        PacketWrapper swapItems = PacketWrapper.create((int)19, null, (UserConnection)packetWrapper.user());
                        swapItems.write((Type)Type.VAR_INT, (Object)6);
                        swapItems.write(Type.POSITION, (Object)new Position(0, 0, 0));
                        swapItems.write((Type)Type.BYTE, (Object)-1);
                        PacketUtil.sendToServer(swapItems, Protocol1_8TO1_9.class, true, true);
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.INTERACT_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int type = (Integer)packetWrapper.get((Type)Type.VAR_INT, 1);
                    if (type == 2) {
                        packetWrapper.passthrough((Type)Type.FLOAT);
                        packetWrapper.passthrough((Type)Type.FLOAT);
                        packetWrapper.passthrough((Type)Type.FLOAT);
                    }
                    if (type == 2 || type == 0) {
                        packetWrapper.write((Type)Type.VAR_INT, (Object)0);
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_MOVEMENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    int playerId;
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    if (tracker.isInsideVehicle(playerId = tracker.getPlayerId())) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    if (pos.getConfirmId() != -1) {
                        return;
                    }
                    pos.setPos((Double)packetWrapper.get((Type)Type.DOUBLE, 0), (Double)packetWrapper.get((Type)Type.DOUBLE, 1), (Double)packetWrapper.get((Type)Type.DOUBLE, 2));
                    pos.setOnGround((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0));
                });
                this.handler(packetWrapper -> ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation());
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    if (pos.getConfirmId() != -1) {
                        return;
                    }
                    pos.setYaw(((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue());
                    pos.setPitch(((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue());
                    pos.setOnGround((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0));
                });
                this.handler(packetWrapper -> ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation());
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_POSITION_AND_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    double x = (Double)packetWrapper.get((Type)Type.DOUBLE, 0);
                    double y = (Double)packetWrapper.get((Type)Type.DOUBLE, 1);
                    double z = (Double)packetWrapper.get((Type)Type.DOUBLE, 2);
                    float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
                    float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
                    boolean onGround = (Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0);
                    PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    if (pos.getConfirmId() != -1) {
                        if (pos.getPosX() == x && pos.getPosY() == y && pos.getPosZ() == z && pos.getYaw() == yaw && pos.getPitch() == pitch) {
                            PacketWrapper confirmTeleport = packetWrapper.create(0);
                            confirmTeleport.write((Type)Type.VAR_INT, (Object)pos.getConfirmId());
                            PacketUtil.sendToServer(confirmTeleport, Protocol1_8TO1_9.class, true, true);
                            pos.setConfirmId(-1);
                        }
                    } else {
                        pos.setPos(x, y, z);
                        pos.setYaw(yaw);
                        pos.setPitch(pitch);
                        pos.setOnGround(onGround);
                        ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_DIGGING, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.POSITION);
                this.handler(packetWrapper -> {
                    int state = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    if (state == 0) {
                        ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).setMining(true);
                    } else if (state == 2) {
                        BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                        tracker.setMining(false);
                        tracker.setLastMining(System.currentTimeMillis() + 100L);
                        ((Cooldown)packetWrapper.user().get(Cooldown.class)).setLastHit(0L);
                    } else if (state == 1) {
                        BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class);
                        tracker.setMining(false);
                        tracker.setLastMining(0L);
                        ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.POSITION);
                this.map((Type)Type.BYTE, (Type)Type.VAR_INT);
                this.map(Type.ITEM, (Type)Type.NOTHING);
                this.create((Type)Type.VAR_INT, 0);
                this.map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
                this.handler(packetWrapper -> {
                    if ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0) == -1) {
                        packetWrapper.cancel();
                        PacketWrapper useItem = PacketWrapper.create((int)29, null, (UserConnection)packetWrapper.user());
                        useItem.write((Type)Type.VAR_INT, (Object)0);
                        PacketUtil.sendToServer(useItem, Protocol1_8TO1_9.class, true, true);
                    }
                });
                this.handler(packetWrapper -> {
                    if ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0) != -1) {
                        ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).place();
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.HELD_ITEM_CHANGE, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit());
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.ANIMATION, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    final PacketWrapper delayedPacket = PacketWrapper.create((int)26, null, (UserConnection)packetWrapper.user());
                    delayedPacket.write((Type)Type.VAR_INT, (Object)0);
                    Protocol1_8TO1_9.TIMER.schedule(new TimerTask(){

                        @Override
                        public void run() {
                            PacketUtil.sendToServer(delayedPacket, Protocol1_8TO1_9.class);
                        }
                    }, 5L);
                });
                this.handler(packetWrapper -> {
                    ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).updateMining();
                    ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.ENTITY_ACTION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    PlayerPosition pos;
                    int action = (Integer)packetWrapper.get((Type)Type.VAR_INT, 1);
                    if (action == 6) {
                        packetWrapper.set((Type)Type.VAR_INT, 1, (Object)7);
                    } else if (action == 0 && !(pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class)).isOnGround()) {
                        PacketWrapper elytra = PacketWrapper.create((int)20, null, (UserConnection)packetWrapper.user());
                        elytra.write((Type)Type.VAR_INT, (Object)((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)));
                        elytra.write((Type)Type.VAR_INT, (Object)8);
                        elytra.write((Type)Type.VAR_INT, (Object)0);
                        PacketUtil.sendToServer(elytra, Protocol1_8TO1_9.class, true, false);
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.STEER_VEHICLE, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.handler(packetWrapper -> {
                    int playerId;
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    int vehicle = tracker.getVehicle(playerId = tracker.getPlayerId());
                    if (vehicle != -1 && tracker.getClientEntityTypes().get(vehicle) == Entity1_10Types.EntityType.BOAT) {
                        PacketWrapper steerBoat = PacketWrapper.create((int)17, null, (UserConnection)packetWrapper.user());
                        float left = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
                        float forward = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
                        steerBoat.write((Type)Type.BOOLEAN, (Object)(forward != 0.0f || left < 0.0f ? 1 : 0));
                        steerBoat.write((Type)Type.BOOLEAN, (Object)(forward != 0.0f || left > 0.0f ? 1 : 0));
                        PacketUtil.sendToServer(steerBoat, Protocol1_8TO1_9.class);
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.UPDATE_SIGN, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.POSITION);
                this.handler(packetWrapper -> {
                    for (int i = 0; i < 4; ++i) {
                        packetWrapper.write(Type.STRING, (Object)ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)));
                    }
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.TAB_COMPLETE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> packetWrapper.write((Type)Type.BOOLEAN, (Object)false));
                this.map(Type.OPTIONAL_POSITION);
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CLIENT_SETTINGS, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE, (Type)Type.VAR_INT);
                this.map((Type)Type.BOOLEAN);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.create((Type)Type.VAR_INT, 1);
                this.handler(packetWrapper -> {
                    short flags = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    PacketWrapper updateSkin = PacketWrapper.create((int)28, null, (UserConnection)packetWrapper.user());
                    updateSkin.write((Type)Type.VAR_INT, (Object)((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId());
                    ArrayList<Metadata> metadata = new ArrayList<Metadata>();
                    metadata.add(new Metadata(10, (MetaType)MetaType1_8.Byte, (Object)((byte)flags)));
                    updateSkin.write(Types1_8.METADATA_LIST, metadata);
                    PacketUtil.sendPacket(updateSkin, Protocol1_8TO1_9.class);
                });
            }
        });
        protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String channel = (String)packetWrapper.get(Type.STRING, 0);
                    if (channel.equalsIgnoreCase("MC|BEdit") || channel.equalsIgnoreCase("MC|BSign")) {
                        Item book = (Item)packetWrapper.passthrough(Type.ITEM);
                        book.setIdentifier(386);
                        CompoundTag tag = book.tag();
                        if (tag.contains("pages")) {
                            ListTag pages = (ListTag)tag.get("pages");
                            if (pages.size() > ViaRewind.getConfig().getMaxBookPages()) {
                                packetWrapper.user().disconnect("Too many book pages");
                                return;
                            }
                            for (int i = 0; i < pages.size(); ++i) {
                                StringTag page = (StringTag)pages.get(i);
                                String value = page.getValue();
                                if (value.length() > ViaRewind.getConfig().getMaxBookPageSize()) {
                                    packetWrapper.user().disconnect("Book page too large");
                                    return;
                                }
                                value = ChatUtil.jsonToLegacy(value);
                                page.setValue(value);
                            }
                        }
                    } else if (channel.equalsIgnoreCase("MC|AdvCdm")) {
                        channel = "MC|AdvCmd";
                        packetWrapper.set(Type.STRING, 0, (Object)"MC|AdvCmd");
                    }
                });
            }
        });
    }
}

