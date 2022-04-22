/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.BlockChangeRecord
 *  com.viaversion.viaversion.api.minecraft.Environment
 *  com.viaversion.viaversion.api.minecraft.Position
 *  com.viaversion.viaversion.api.minecraft.chunks.BaseChunk
 *  com.viaversion.viaversion.api.minecraft.chunks.Chunk
 *  com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
 *  com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl
 *  com.viaversion.viaversion.api.protocol.Protocol
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag
 *  com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld
 *  com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.ArrayList;

public class WorldPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BLOCK_ENTITY_DATA, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.POSITION);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(packetWrapper -> {
                    CompoundTag tag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
                    if (tag != null && tag.contains("SpawnData")) {
                        String entity = (String)((CompoundTag)tag.get("SpawnData")).get("id").getValue();
                        tag.remove("SpawnData");
                        tag.put("entityId", (Tag)new StringTag(entity));
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BLOCK_ACTION, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.POSITION);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int block = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    if (block >= 219 && block <= 234) {
                        block = 130;
                        packetWrapper.set((Type)Type.VAR_INT, 0, (Object)130);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BLOCK_CHANGE, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.POSITION);
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int combined = (Integer)packetWrapper.get((Type)Type.VAR_INT, 0);
                    int replacedCombined = ReplacementRegistry1_8to1_9.replace(combined);
                    packetWrapper.set((Type)Type.VAR_INT, 0, (Object)replacedCombined);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(packetWrapper -> {
                    for (BlockChangeRecord record : (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                        int replacedCombined = ReplacementRegistry1_8to1_9.replace(record.getBlockId());
                        record.setBlockId(replacedCombined);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.NAMED_SOUND, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String name = (String)packetWrapper.get(Type.STRING, 0);
                    if ((name = SoundRemapper.getOldName(name)) == null) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.set(Type.STRING, 0, (Object)name);
                    }
                });
                this.map((Type)Type.VAR_INT, (Type)Type.NOTHING);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.UNSIGNED_BYTE);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.EXPLOSION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.handler(packetWrapper -> {
                    int count = (Integer)packetWrapper.read((Type)Type.INT);
                    packetWrapper.write((Type)Type.INT, (Object)count);
                    for (int i = 0; i < count; ++i) {
                        packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
                    }
                });
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.UNLOAD_CHUNK, (ClientboundPacketType)ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int chunkX = (Integer)packetWrapper.read((Type)Type.INT);
                    int chunkZ = (Integer)packetWrapper.read((Type)Type.INT);
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    packetWrapper.write((Type)new Chunk1_8Type(world), (Object)new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList()));
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    Chunk chunk = (Chunk)packetWrapper.read((Type)new Chunk1_9_1_2Type(world));
                    for (ChunkSection section : chunk.getSections()) {
                        if (section == null) continue;
                        for (int i = 0; i < section.getPaletteSize(); ++i) {
                            int block = section.getPaletteEntry(i);
                            int replacedBlock = ReplacementRegistry1_8to1_9.replace(block);
                            section.setPaletteEntry(i, replacedBlock);
                        }
                    }
                    if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                        boolean skylight = world.getEnvironment() == Environment.NORMAL;
                        ChunkSection[] sections = new ChunkSection[16];
                        ChunkSectionImpl section = new ChunkSectionImpl(true);
                        sections[0] = section;
                        section.addPaletteEntry(0);
                        if (skylight) {
                            section.getLight().setSkyLight(new byte[2048]);
                        }
                        chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
                    }
                    packetWrapper.write((Type)new Chunk1_8Type(world), (Object)chunk);
                    UserConnection user = packetWrapper.user();
                    chunk.getBlockEntities().forEach(nbt -> {
                        short action;
                        String id;
                        if (!(nbt.contains("x") && nbt.contains("y") && nbt.contains("z") && nbt.contains("id"))) {
                            return;
                        }
                        Position position = new Position(((Integer)nbt.get("x").getValue()).intValue(), (short)((Integer)nbt.get("y").getValue()).intValue(), ((Integer)nbt.get("z").getValue()).intValue());
                        switch (id = (String)nbt.get("id").getValue()) {
                            case "minecraft:mob_spawner": {
                                action = 1;
                                break;
                            }
                            case "minecraft:command_block": {
                                action = 2;
                                break;
                            }
                            case "minecraft:beacon": {
                                action = 3;
                                break;
                            }
                            case "minecraft:skull": {
                                action = 4;
                                break;
                            }
                            case "minecraft:flower_pot": {
                                action = 5;
                                break;
                            }
                            case "minecraft:banner": {
                                action = 6;
                                break;
                            }
                            default: {
                                return;
                            }
                        }
                        PacketWrapper updateTileEntity = PacketWrapper.create((int)9, null, (UserConnection)user);
                        updateTileEntity.write(Type.POSITION, (Object)position);
                        updateTileEntity.write((Type)Type.UNSIGNED_BYTE, (Object)action);
                        updateTileEntity.write(Type.NBT, nbt);
                        PacketUtil.sendPacket(updateTileEntity, Protocol1_8TO1_9.class, false, false);
                    });
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.EFFECT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map(Type.POSITION);
                this.map((Type)Type.INT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    int id = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    if ((id = Effect.getOldId(id)) == -1) {
                        packetWrapper.cancel();
                        return;
                    }
                    packetWrapper.set((Type)Type.INT, 0, (Object)id);
                    if (id == 2001) {
                        int replacedBlock = ReplacementRegistry1_8to1_9.replace((Integer)packetWrapper.get((Type)Type.INT, 1));
                        packetWrapper.set((Type)Type.INT, 1, (Object)replacedBlock);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_PARTICLE, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    int type = (Integer)packetWrapper.get((Type)Type.INT, 0);
                    if (type > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (type == 42) {
                        packetWrapper.set((Type)Type.INT, 0, (Object)24);
                    } else if (type == 43) {
                        packetWrapper.set((Type)Type.INT, 0, (Object)3);
                    } else if (type == 44) {
                        packetWrapper.set((Type)Type.INT, 0, (Object)34);
                    } else if (type == 45) {
                        packetWrapper.set((Type)Type.INT, 0, (Object)1);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.MAP_DATA, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SOUND, (ClientboundPacketType)ClientboundPackets1_8.NAMED_SOUND, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int soundId = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    String sound = SoundRemapper.oldNameFromId(soundId);
                    if (sound == null) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.write(Type.STRING, (Object)sound);
                    }
                });
                this.handler(packetWrapper -> packetWrapper.read((Type)Type.VAR_INT));
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.UNSIGNED_BYTE);
            }
        });
    }
}

