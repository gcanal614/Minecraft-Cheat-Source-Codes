/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.Via
 *  com.viaversion.viaversion.api.connection.StorableObject
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.Position
 *  com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types$EntityType
 *  com.viaversion.viaversion.api.minecraft.item.Item
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.libs.gson.JsonElement
 *  com.viaversion.viaversion.libs.gson.JsonParser
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.buffer.Unpooled
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.EntityReplacement;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Utils;
import de.gerrygames.viarewind.utils.math.AABB;
import de.gerrygames.viarewind.utils.math.Ray3d;
import de.gerrygames.viarewind.utils.math.RayTracing;
import de.gerrygames.viarewind.utils.math.Vector3d;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class PlayerPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.JOIN_GAME, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    if (!ViaRewind.getConfig().isReplaceAdventureMode()) {
                        return;
                    }
                    if ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0) == 2) {
                        packetWrapper.set((Type)Type.UNSIGNED_BYTE, 0, (Object)0);
                    }
                });
                this.handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.setGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue());
                    tracker.setPlayerId((Integer)packetWrapper.get((Type)Type.INT, 0));
                    tracker.getClientEntityTypes().put(tracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                    tracker.setDimension(((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue());
                });
                this.handler(packetWrapper -> {
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    world.setEnvironment((int)((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue());
                });
                this.handler(wrapper -> wrapper.user().put((StorableObject)new Scoreboard(wrapper.user())));
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CHAT_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.COMPONENT);
                this.handler(packetWrapper -> {
                    byte position = (Byte)packetWrapper.read((Type)Type.BYTE);
                    if (position == 2) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.INT, (Object)position.getY());
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_HEALTH, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.VAR_INT, (Type)Type.SHORT);
                this.map((Type)Type.FLOAT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.RESPAWN, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    if (!ViaRewind.getConfig().isReplaceAdventureMode()) {
                        return;
                    }
                    if ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 1) == 2) {
                        packetWrapper.set((Type)Type.UNSIGNED_BYTE, 1, (Object)0);
                    }
                });
                this.handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    tracker.setGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue());
                    if (tracker.getDimension() != ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue()) {
                        tracker.setDimension((Integer)packetWrapper.get((Type)Type.INT, 0));
                        tracker.clearEntities();
                        tracker.getClientEntityTypes().put(tracker.getPlayerId(), Entity1_10Types.EntityType.ENTITY_HUMAN);
                    }
                });
                this.handler(packetWrapper -> {
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    world.setEnvironment(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    playerPosition.setPositionPacketReceived(true);
                    byte flags = (Byte)packetWrapper.read((Type)Type.BYTE);
                    if ((flags & 1) == 1) {
                        double x = (Double)packetWrapper.get((Type)Type.DOUBLE, 0);
                        packetWrapper.set((Type)Type.DOUBLE, 0, (Object)(x += playerPosition.getPosX()));
                    }
                    double y = (Double)packetWrapper.get((Type)Type.DOUBLE, 1);
                    if ((flags & 2) == 2) {
                        y += playerPosition.getPosY();
                    }
                    playerPosition.setReceivedPosY(y);
                    packetWrapper.set((Type)Type.DOUBLE, 1, (Object)(y += (double)1.62f));
                    if ((flags & 4) == 4) {
                        double z = (Double)packetWrapper.get((Type)Type.DOUBLE, 2);
                        packetWrapper.set((Type)Type.DOUBLE, 2, (Object)(z += playerPosition.getPosZ()));
                    }
                    if ((flags & 8) == 8) {
                        float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
                        packetWrapper.set((Type)Type.FLOAT, 0, (Object)Float.valueOf(yaw += playerPosition.getYaw()));
                    }
                    if ((flags & 0x10) == 16) {
                        float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
                        packetWrapper.set((Type)Type.FLOAT, 1, (Object)Float.valueOf(pitch += playerPosition.getPitch()));
                    }
                });
                this.handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    packetWrapper.write((Type)Type.BOOLEAN, (Object)playerPosition.isOnGround());
                });
                this.handler(packetWrapper -> {
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    if (tracker.getSpectating() != tracker.getPlayerId()) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SET_EXPERIENCE, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.VAR_INT, (Type)Type.SHORT);
                this.map((Type)Type.VAR_INT, (Type)Type.SHORT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.GAME_EVENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    short mode = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    if (mode != 3) {
                        return;
                    }
                    int gamemode = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).intValue();
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    if (gamemode == 3 || tracker.getGamemode() == 3) {
                        Item[] equipment;
                        UUID uuid = packetWrapper.user().getProtocolInfo().getUuid();
                        if (gamemode == 3) {
                            GameProfileStorage.GameProfile profile = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(uuid);
                            equipment = new Item[5];
                            equipment[4] = profile.getSkull();
                        } else {
                            equipment = tracker.getPlayerEquipment(uuid);
                            if (equipment == null) {
                                equipment = new Item[5];
                            }
                        }
                        for (int i = 1; i < 5; ++i) {
                            PacketWrapper setSlot = PacketWrapper.create((int)47, null, (UserConnection)packetWrapper.user());
                            setSlot.write((Type)Type.BYTE, (Object)0);
                            setSlot.write((Type)Type.SHORT, (Object)((short)(9 - i)));
                            setSlot.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (Object)equipment[i]);
                            PacketUtil.sendPacket(setSlot, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
                this.handler(packetWrapper -> {
                    short mode = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    if (mode == 3) {
                        int gamemode = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).intValue();
                        if (gamemode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                            gamemode = 0;
                            packetWrapper.set((Type)Type.FLOAT, 0, (Object)Float.valueOf(0.0f));
                        }
                        ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setGamemode(gamemode);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.OPEN_SIGN_EDITOR, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.INT, (Object)position.getY());
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_INFO, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    int action = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    int count = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    GameProfileStorage gameProfileStorage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
                    for (int i = 0; i < count; ++i) {
                        GameProfileStorage.GameProfile gameProfile;
                        GameProfileStorage.GameProfile gameProfile2;
                        UUID uuid = (UUID)packetWrapper.read(Type.UUID);
                        if (action == 0) {
                            int ping;
                            String name = (String)packetWrapper.read(Type.STRING);
                            gameProfile2 = gameProfileStorage.get(uuid);
                            if (gameProfile2 == null) {
                                gameProfile2 = gameProfileStorage.put(uuid, name);
                            }
                            int propertyCount = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                            while (propertyCount-- > 0) {
                                gameProfile2.properties.add(new GameProfileStorage.Property((String)packetWrapper.read(Type.STRING), (String)packetWrapper.read(Type.STRING), (Boolean)packetWrapper.read((Type)Type.BOOLEAN) != false ? (String)packetWrapper.read(Type.STRING) : null));
                            }
                            int gamemode = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                            gameProfile2.ping = ping = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
                            gameProfile2.gamemode = gamemode;
                            if (((Boolean)packetWrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
                                gameProfile2.setDisplayName(ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)));
                            }
                            PacketWrapper packet = PacketWrapper.create((int)56, null, (UserConnection)packetWrapper.user());
                            packet.write(Type.STRING, (Object)gameProfile2.name);
                            packet.write((Type)Type.BOOLEAN, (Object)true);
                            packet.write((Type)Type.SHORT, (Object)((short)ping));
                            PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                            continue;
                        }
                        if (action == 1) {
                            EntityTracker tracker;
                            int entityId;
                            int gamemode = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                            gameProfile2 = gameProfileStorage.get(uuid);
                            if (gameProfile2 == null || gameProfile2.gamemode == gamemode) continue;
                            if ((gamemode == 3 || gameProfile2.gamemode == 3) && (entityId = (tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerEntityId(uuid)) != -1) {
                                Item[] equipment;
                                if (gamemode == 3) {
                                    equipment = new Item[5];
                                    equipment[4] = gameProfile2.getSkull();
                                } else {
                                    equipment = tracker.getPlayerEquipment(uuid);
                                    if (equipment == null) {
                                        equipment = new Item[5];
                                    }
                                }
                                for (short slot = 0; slot < 5; slot = (short)(slot + 1)) {
                                    PacketWrapper equipmentPacket = PacketWrapper.create((int)4, null, (UserConnection)packetWrapper.user());
                                    equipmentPacket.write((Type)Type.INT, (Object)entityId);
                                    equipmentPacket.write((Type)Type.SHORT, (Object)slot);
                                    equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (Object)equipment[slot]);
                                    PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
                                }
                            }
                            gameProfile2.gamemode = gamemode;
                            continue;
                        }
                        if (action == 2) {
                            int ping = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                            gameProfile2 = gameProfileStorage.get(uuid);
                            if (gameProfile2 == null) continue;
                            gameProfile2.ping = ping;
                            PacketWrapper packet = PacketWrapper.create((int)56, null, (UserConnection)packetWrapper.user());
                            packet.write(Type.STRING, (Object)gameProfile2.name);
                            packet.write((Type)Type.BOOLEAN, (Object)true);
                            packet.write((Type)Type.SHORT, (Object)((short)ping));
                            PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                            continue;
                        }
                        if (action == 3) {
                            String displayName = (Boolean)packetWrapper.read((Type)Type.BOOLEAN) != false ? ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)) : null;
                            gameProfile2 = gameProfileStorage.get(uuid);
                            if (gameProfile2 == null || gameProfile2.displayName == null && displayName == null || !(gameProfile2.displayName == null && displayName != null || gameProfile2.displayName != null && displayName == null) && gameProfile2.displayName.equals(displayName)) continue;
                            gameProfile2.setDisplayName(displayName);
                            continue;
                        }
                        if (action != 4 || (gameProfile = gameProfileStorage.remove(uuid)) == null) continue;
                        PacketWrapper packet = PacketWrapper.create((int)56, null, (UserConnection)packetWrapper.user());
                        packet.write(Type.STRING, (Object)gameProfile.name);
                        packet.write((Type)Type.BOOLEAN, (Object)false);
                        packet.write((Type)Type.SHORT, (Object)((short)gameProfile.ping));
                        PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_ABILITIES, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.BYTE);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    byte flags = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    float flySpeed = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
                    float walkSpeed = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
                    PlayerAbilities abilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
                    abilities.setInvincible((flags & 8) == 8);
                    abilities.setAllowFly((flags & 4) == 4);
                    abilities.setFlying((flags & 2) == 2);
                    abilities.setCreative((flags & 1) == 1);
                    abilities.setFlySpeed(flySpeed);
                    abilities.setWalkSpeed(walkSpeed);
                    if (abilities.isSprinting() && abilities.isFlying()) {
                        packetWrapper.set((Type)Type.FLOAT, 0, (Object)Float.valueOf(abilities.getFlySpeed() * 2.0f));
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String channel = (String)packetWrapper.get(Type.STRING, 0);
                    if (channel.equalsIgnoreCase("MC|TrList")) {
                        packetWrapper.passthrough((Type)Type.INT);
                        int size = packetWrapper.isReadable((Type)Type.BYTE, 0) ? ((Byte)packetWrapper.passthrough((Type)Type.BYTE)).byteValue() : ((Short)packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
                        for (int i = 0; i < size; ++i) {
                            Item item = ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM));
                            packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (Object)item);
                            item = ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM));
                            packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (Object)item);
                            boolean has3Items = (Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN);
                            if (has3Items) {
                                item = ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM));
                                packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (Object)item);
                            }
                            packetWrapper.passthrough((Type)Type.BOOLEAN);
                            packetWrapper.read((Type)Type.INT);
                            packetWrapper.read((Type)Type.INT);
                        }
                    } else if (channel.equalsIgnoreCase("MC|Brand")) {
                        packetWrapper.write(Type.REMAINING_BYTES, (Object)((String)packetWrapper.read(Type.STRING)).getBytes(StandardCharsets.UTF_8));
                    }
                    packetWrapper.cancel();
                    packetWrapper.setId(-1);
                    ByteBuf newPacketBuf = Unpooled.buffer();
                    packetWrapper.writeToBuffer(newPacketBuf);
                    PacketWrapper newWrapper = PacketWrapper.create((int)63, (ByteBuf)newPacketBuf, (UserConnection)packetWrapper.user());
                    newWrapper.passthrough(Type.STRING);
                    if (newPacketBuf.readableBytes() <= Short.MAX_VALUE) {
                        newWrapper.write((Type)Type.SHORT, (Object)((short)newPacketBuf.readableBytes()));
                        newWrapper.send(Protocol1_7_6_10TO1_8.class);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CAMERA, null, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    int entityId = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    int spectating = tracker.getSpectating();
                    if (spectating != entityId) {
                        tracker.setSpectating(entityId);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.TITLE, null, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    TitleRenderProvider titleRenderProvider = (TitleRenderProvider)Via.getManager().getProviders().get(TitleRenderProvider.class);
                    if (titleRenderProvider == null) {
                        return;
                    }
                    int action = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    UUID uuid = Utils.getUUID(packetWrapper.user());
                    switch (action) {
                        case 0: {
                            titleRenderProvider.setTitle(uuid, (String)packetWrapper.read(Type.STRING));
                            break;
                        }
                        case 1: {
                            titleRenderProvider.setSubTitle(uuid, (String)packetWrapper.read(Type.STRING));
                            break;
                        }
                        case 2: {
                            titleRenderProvider.setTimings(uuid, (Integer)packetWrapper.read((Type)Type.INT), (Integer)packetWrapper.read((Type)Type.INT), (Integer)packetWrapper.read((Type)Type.INT));
                            break;
                        }
                        case 3: {
                            titleRenderProvider.clear(uuid);
                            break;
                        }
                        case 4: {
                            titleRenderProvider.reset(uuid);
                        }
                    }
                });
            }
        });
        protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.TAB_LIST);
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.RESOURCE_PACK, ClientboundPackets1_7.PLUGIN_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.create(Type.STRING, "MC|RPack");
                this.handler(packetWrapper -> {
                    ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                    try {
                        Type.STRING.write(buf, (Object)((String)packetWrapper.read(Type.STRING)));
                        packetWrapper.write(Type.SHORT_BYTE_ARRAY, (Object)((byte[])Type.REMAINING_BYTES.read(buf)));
                    }
                    finally {
                        buf.release();
                    }
                });
                this.map(Type.STRING, (Type)Type.NOTHING);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.CHAT_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String msg = (String)packetWrapper.get(Type.STRING, 0);
                    int gamemode = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getGamemode();
                    if (gamemode == 3 && msg.toLowerCase().startsWith("/stp ")) {
                        String username = msg.split(" ")[1];
                        GameProfileStorage storage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
                        GameProfileStorage.GameProfile profile = storage.get(username, true);
                        if (profile != null && profile.uuid != null) {
                            packetWrapper.cancel();
                            PacketWrapper teleportPacket = PacketWrapper.create((int)24, null, (UserConnection)packetWrapper.user());
                            teleportPacket.write(Type.UUID, (Object)profile.uuid);
                            PacketUtil.sendToServer(teleportPacket, Protocol1_7_6_10TO1_8.class, true, true);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.INTERACT_ENTITY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT, (Type)Type.VAR_INT);
                this.map((Type)Type.BYTE, (Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int mode = (Integer)packetWrapper.get((Type)Type.VAR_INT, 1);
                    if (mode != 0) {
                        return;
                    }
                    int entityId = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    EntityReplacement replacement = tracker.getEntityReplacement(entityId);
                    if (!(replacement instanceof ArmorStandReplacement)) {
                        return;
                    }
                    ArmorStandReplacement armorStand = (ArmorStandReplacement)replacement;
                    AABB boundingBox = armorStand.getBoundingBox();
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    Vector3d pos = new Vector3d(playerPosition.getPosX(), playerPosition.getPosY() + 1.8, playerPosition.getPosZ());
                    double yaw = Math.toRadians(playerPosition.getYaw());
                    double pitch = Math.toRadians(playerPosition.getPitch());
                    Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                    Ray3d ray = new Ray3d(pos, dir);
                    Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0);
                    if (intersection == null) {
                        return;
                    }
                    intersection.substract(boundingBox.getMin());
                    mode = 2;
                    packetWrapper.set((Type)Type.VAR_INT, 1, (Object)mode);
                    packetWrapper.write((Type)Type.FLOAT, (Object)Float.valueOf((float)intersection.getX()));
                    packetWrapper.write((Type)Type.FLOAT, (Object)Float.valueOf((float)intersection.getY()));
                    packetWrapper.write((Type)Type.FLOAT, (Object)Float.valueOf((float)intersection.getZ()));
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_MOVEMENT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    playerPosition.setOnGround((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0));
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_POSITION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE, (Type)Type.NOTHING);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    double x = (Double)packetWrapper.get((Type)Type.DOUBLE, 0);
                    double feetY = (Double)packetWrapper.get((Type)Type.DOUBLE, 1);
                    double z = (Double)packetWrapper.get((Type)Type.DOUBLE, 2);
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    if (playerPosition.isPositionPacketReceived()) {
                        playerPosition.setPositionPacketReceived(false);
                        packetWrapper.set((Type)Type.DOUBLE, 1, (Object)(feetY -= 0.01));
                    }
                    playerPosition.setOnGround((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0));
                    playerPosition.setPos(x, feetY, z);
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    playerPosition.setYaw(((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue());
                    playerPosition.setPitch(((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue());
                    playerPosition.setOnGround((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0));
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_POSITION_AND_ROTATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.DOUBLE, (Type)Type.NOTHING);
                this.map((Type)Type.DOUBLE);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    double x = (Double)packetWrapper.get((Type)Type.DOUBLE, 0);
                    double feetY = (Double)packetWrapper.get((Type)Type.DOUBLE, 1);
                    double z = (Double)packetWrapper.get((Type)Type.DOUBLE, 2);
                    float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
                    float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
                    PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
                    if (playerPosition.isPositionPacketReceived()) {
                        playerPosition.setPositionPacketReceived(false);
                        feetY = playerPosition.getReceivedPosY();
                        packetWrapper.set((Type)Type.DOUBLE, 1, (Object)feetY);
                    }
                    playerPosition.setOnGround((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0));
                    playerPosition.setPos(x, feetY, z);
                    playerPosition.setYaw(yaw);
                    playerPosition.setPitch(pitch);
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_DIGGING, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int x = (Integer)packetWrapper.read((Type)Type.INT);
                    short y = (Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                    int z = (Integer)packetWrapper.read((Type)Type.INT);
                    packetWrapper.write(Type.POSITION, (Object)new Position(x, y, z));
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_BLOCK_PLACEMENT, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int x = (Integer)packetWrapper.read((Type)Type.INT);
                    short y = (Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                    int z = (Integer)packetWrapper.read((Type)Type.INT);
                    packetWrapper.write(Type.POSITION, (Object)new Position(x, y, z));
                    packetWrapper.passthrough((Type)Type.BYTE);
                    Item item = (Item)packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                    item = ItemRewriter.toServer(item);
                    packetWrapper.write(Type.ITEM, (Object)item);
                    for (int i = 0; i < 3; ++i) {
                        packetWrapper.passthrough((Type)Type.BYTE);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.ANIMATION, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int entityId = (Integer)packetWrapper.read((Type)Type.INT);
                    int animation = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
                    if (animation == 1) {
                        return;
                    }
                    packetWrapper.cancel();
                    switch (animation) {
                        case 104: {
                            animation = 0;
                            break;
                        }
                        case 105: {
                            animation = 1;
                            break;
                        }
                        case 3: {
                            animation = 2;
                            break;
                        }
                        default: {
                            return;
                        }
                    }
                    PacketWrapper entityAction = PacketWrapper.create((int)11, null, (UserConnection)packetWrapper.user());
                    entityAction.write((Type)Type.VAR_INT, (Object)entityId);
                    entityAction.write((Type)Type.VAR_INT, (Object)animation);
                    entityAction.write((Type)Type.VAR_INT, (Object)0);
                    PacketUtil.sendPacket(entityAction, Protocol1_7_6_10TO1_8.class, true, true);
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.ENTITY_ACTION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT, (Type)Type.VAR_INT);
                this.handler(packetWrapper -> packetWrapper.write((Type)Type.VAR_INT, (Object)((Byte)packetWrapper.read((Type)Type.BYTE) - 1)));
                this.map((Type)Type.INT, (Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int action = (Integer)packetWrapper.get((Type)Type.VAR_INT, 1);
                    if (action == 3 || action == 4) {
                        PlayerAbilities abilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
                        abilities.setSprinting(action == 3);
                        PacketWrapper abilitiesPacket = PacketWrapper.create((int)57, null, (UserConnection)packetWrapper.user());
                        abilitiesPacket.write((Type)Type.BYTE, (Object)abilities.getFlags());
                        abilitiesPacket.write((Type)Type.FLOAT, (Object)Float.valueOf(abilities.isSprinting() ? abilities.getFlySpeed() * 2.0f : abilities.getFlySpeed()));
                        abilitiesPacket.write((Type)Type.FLOAT, (Object)Float.valueOf(abilities.getWalkSpeed()));
                        PacketUtil.sendPacket(abilitiesPacket, Protocol1_7_6_10TO1_8.class);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.STEER_VEHICLE, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    EntityTracker tracker;
                    boolean jump = (Boolean)packetWrapper.read((Type)Type.BOOLEAN);
                    boolean unmount = (Boolean)packetWrapper.read((Type)Type.BOOLEAN);
                    short flags = 0;
                    if (jump) {
                        flags = (short)(flags + 1);
                    }
                    if (unmount) {
                        flags = (short)(flags + 2);
                    }
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)flags);
                    if (unmount && (tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class)).getSpectating() != tracker.getPlayerId()) {
                        PacketWrapper sneakPacket = PacketWrapper.create((int)11, null, (UserConnection)packetWrapper.user());
                        sneakPacket.write((Type)Type.VAR_INT, (Object)tracker.getPlayerId());
                        sneakPacket.write((Type)Type.VAR_INT, (Object)0);
                        sneakPacket.write((Type)Type.VAR_INT, (Object)0);
                        PacketWrapper unsneakPacket = PacketWrapper.create((int)11, null, (UserConnection)packetWrapper.user());
                        unsneakPacket.write((Type)Type.VAR_INT, (Object)tracker.getPlayerId());
                        unsneakPacket.write((Type)Type.VAR_INT, (Object)1);
                        unsneakPacket.write((Type)Type.VAR_INT, (Object)0);
                        PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.UPDATE_SIGN, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int x = (Integer)packetWrapper.read((Type)Type.INT);
                    short y = (Short)packetWrapper.read((Type)Type.SHORT);
                    int z = (Integer)packetWrapper.read((Type)Type.INT);
                    packetWrapper.write(Type.POSITION, (Object)new Position(x, y, z));
                    for (int i = 0; i < 4; ++i) {
                        String line = (String)packetWrapper.read(Type.STRING);
                        line = ChatUtil.legacyToJson(line);
                        packetWrapper.write(Type.COMPONENT, (Object)JsonParser.parseString((String)line));
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLAYER_ABILITIES, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.BYTE);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    PlayerAbilities abilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
                    if (abilities.isAllowFly()) {
                        byte flags = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                        abilities.setFlying((flags & 2) == 2);
                    }
                    packetWrapper.set((Type)Type.FLOAT, 0, (Object)Float.valueOf(abilities.getFlySpeed()));
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.TAB_COMPLETE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.create(Type.OPTIONAL_POSITION, null);
                this.handler(packetWrapper -> {
                    String msg = (String)packetWrapper.get(Type.STRING, 0);
                    if (msg.toLowerCase().startsWith("/stp ")) {
                        packetWrapper.cancel();
                        String[] args = msg.split(" ");
                        if (args.length <= 2) {
                            String prefix = args.length == 1 ? "" : args[1];
                            GameProfileStorage storage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
                            List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
                            PacketWrapper tabComplete = PacketWrapper.create((int)58, null, (UserConnection)packetWrapper.user());
                            tabComplete.write((Type)Type.VAR_INT, (Object)profiles.size());
                            for (GameProfileStorage.GameProfile profile : profiles) {
                                tabComplete.write(Type.STRING, (Object)profile.name);
                            }
                            PacketUtil.sendPacket(tabComplete, Protocol1_7_6_10TO1_8.class);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.CLIENT_SETTINGS, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN);
                this.map((Type)Type.BYTE, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    boolean cape = (Boolean)packetWrapper.read((Type)Type.BOOLEAN);
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)((short)(cape ? 127 : 126)));
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.PLUGIN_MESSAGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.map((Type)Type.SHORT, (Type)Type.NOTHING);
                this.handler(packetWrapper -> {
                    String channel;
                    switch (channel = (String)packetWrapper.get(Type.STRING, 0)) {
                        case "MC|TrSel": {
                            packetWrapper.passthrough((Type)Type.INT);
                            packetWrapper.read(Type.REMAINING_BYTES);
                            break;
                        }
                        case "MC|ItemName": {
                            byte[] data = (byte[])packetWrapper.read(Type.REMAINING_BYTES);
                            String name = new String(data, StandardCharsets.UTF_8);
                            packetWrapper.write(Type.STRING, (Object)name);
                            Windows windows = (Windows)packetWrapper.user().get(Windows.class);
                            PacketWrapper updateCost = PacketWrapper.create((int)49, null, (UserConnection)packetWrapper.user());
                            updateCost.write((Type)Type.UNSIGNED_BYTE, (Object)windows.anvilId);
                            updateCost.write((Type)Type.SHORT, (Object)0);
                            updateCost.write((Type)Type.SHORT, (Object)windows.levelCost);
                            PacketUtil.sendPacket(updateCost, Protocol1_7_6_10TO1_8.class, true, true);
                            break;
                        }
                        case "MC|BEdit": 
                        case "MC|BSign": {
                            Item book = (Item)packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                            CompoundTag tag = book.tag();
                            if (tag != null && tag.contains("pages")) {
                                ListTag pages = (ListTag)tag.get("pages");
                                for (int i = 0; i < pages.size(); ++i) {
                                    StringTag page = (StringTag)pages.get(i);
                                    String value = page.getValue();
                                    value = ChatUtil.legacyToJson(value);
                                    page.setValue(value);
                                }
                            }
                            packetWrapper.write(Type.ITEM, (Object)book);
                            break;
                        }
                        case "MC|Brand": {
                            packetWrapper.write(Type.STRING, (Object)new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
                        }
                    }
                });
            }
        });
    }
}

