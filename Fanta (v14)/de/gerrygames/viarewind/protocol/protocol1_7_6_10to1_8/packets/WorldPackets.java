/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.minecraft.BlockChangeRecord
 *  com.viaversion.viaversion.api.minecraft.Position
 *  com.viaversion.viaversion.api.minecraft.chunks.Chunk
 *  com.viaversion.viaversion.api.minecraft.chunks.ChunkSection
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.api.type.types.CustomByteType
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 *  com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld
 *  com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type
 *  com.viaversion.viaversion.util.ChatColorUtil
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.CustomByteType;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import com.viaversion.viaversion.util.ChatColorUtil;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks.ChunkPacketTransformer;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorder;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Chunk1_7_10Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.types.VarLongType;
import de.gerrygames.viarewind.utils.ChatUtil;
import de.gerrygames.viarewind.utils.PacketUtil;

public class WorldPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CHUNK_DATA, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
                    Chunk chunk = (Chunk)packetWrapper.read((Type)new Chunk1_8Type(world));
                    packetWrapper.write((Type)new Chunk1_7_10Type(world), (Object)chunk);
                    for (ChunkSection section : chunk.getSections()) {
                        if (section == null) continue;
                        for (int i = 0; i < section.getPaletteSize(); ++i) {
                            int block = section.getPaletteEntry(i);
                            int replacedBlock = ReplacementRegistry1_7_6_10to1_8.replace(block);
                            section.setPaletteEntry(i, replacedBlock);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    BlockChangeRecord[] records = (BlockChangeRecord[])packetWrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
                    packetWrapper.write((Type)Type.SHORT, (Object)((short)records.length));
                    packetWrapper.write((Type)Type.INT, (Object)(records.length * 4));
                    for (BlockChangeRecord record : records) {
                        short data = (short)(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY());
                        packetWrapper.write((Type)Type.SHORT, (Object)data);
                        int replacedBlock = ReplacementRegistry1_7_6_10to1_8.replace(record.getBlockId());
                        packetWrapper.write((Type)Type.SHORT, (Object)((short)replacedBlock));
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_CHANGE, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)((short)position.getY()));
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.handler(packetWrapper -> {
                    int meta;
                    int data = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    int blockId = data >> 4;
                    Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(blockId, meta = data & 0xF);
                    if (replace != null) {
                        blockId = replace.getId();
                        meta = replace.replaceData(meta);
                    }
                    packetWrapper.write((Type)Type.VAR_INT, (Object)blockId);
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)((short)meta));
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_ACTION, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.SHORT, (Object)((short)position.getY()));
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.VAR_INT);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_BREAK_ANIMATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.INT, (Object)position.getY());
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.map((Type)Type.BYTE);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MAP_BULK_CHUNK, new PacketRemapper(){

            public void registerMap() {
                this.handler(ChunkPacketTransformer::transformChunkBulk);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.EFFECT, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.BYTE, (Object)((byte)position.getY()));
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.map((Type)Type.INT);
                this.map((Type)Type.BOOLEAN);
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PARTICLE, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int particleId = (Integer)packetWrapper.read((Type)Type.INT);
                    Particle particle = Particle.find(particleId);
                    if (particle == null) {
                        particle = Particle.CRIT;
                    }
                    packetWrapper.write(Type.STRING, (Object)particle.name);
                    packetWrapper.read((Type)Type.BOOLEAN);
                });
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.FLOAT);
                this.map((Type)Type.INT);
                this.handler(packetWrapper -> {
                    String name = (String)packetWrapper.get(Type.STRING, 0);
                    Particle particle = Particle.find(name);
                    if (particle == Particle.ICON_CRACK || particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST) {
                        int data;
                        int id = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                        int n = data = particle == Particle.ICON_CRACK ? (Integer)packetWrapper.read((Type)Type.VAR_INT) : 0;
                        if (id >= 256 && id <= 422 || id >= 2256 && id <= 2267) {
                            particle = Particle.ICON_CRACK;
                        } else if (id >= 0 && id <= 164 || id >= 170 && id <= 175) {
                            if (particle == Particle.ICON_CRACK) {
                                particle = Particle.BLOCK_CRACK;
                            }
                        } else {
                            packetWrapper.cancel();
                            return;
                        }
                        name = particle.name + "_" + id + "_" + data;
                    }
                    packetWrapper.set(Type.STRING, 0, (Object)name);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_SIGN, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.SHORT, (Object)((short)position.getY()));
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.handler(packetWrapper -> {
                    for (int i = 0; i < 4; ++i) {
                        String line = (String)packetWrapper.read(Type.STRING);
                        line = ChatUtil.jsonToLegacy(line);
                        if ((line = ChatUtil.removeUnusedColor(line, '0')).length() > 15 && (line = ChatColorUtil.stripColor((String)line)).length() > 15) {
                            line = line.substring(0, 15);
                        }
                        packetWrapper.write(Type.STRING, (Object)line);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MAP_DATA, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    packetWrapper.cancel();
                    int id = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    byte scale = (Byte)packetWrapper.read((Type)Type.BYTE);
                    int count = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    byte[] icons = new byte[count * 4];
                    for (int i = 0; i < count; ++i) {
                        byte j = (Byte)packetWrapper.read((Type)Type.BYTE);
                        icons[i * 4] = (byte)(j >> 4 & 0xF);
                        icons[i * 4 + 1] = (Byte)packetWrapper.read((Type)Type.BYTE);
                        icons[i * 4 + 2] = (Byte)packetWrapper.read((Type)Type.BYTE);
                        icons[i * 4 + 3] = (byte)(j & 0xF);
                    }
                    int columns = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
                    if (columns > 0) {
                        int rows = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
                        short x = (Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                        short z = (Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                        byte[] data = (byte[])packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        for (int column = 0; column < columns; ++column) {
                            byte[] columnData = new byte[rows + 3];
                            columnData[0] = 0;
                            columnData[1] = (byte)(x + column);
                            columnData[2] = (byte)z;
                            for (int i = 0; i < rows; ++i) {
                                columnData[i + 3] = data[column + i * columns];
                            }
                            PacketWrapper columnUpdate = PacketWrapper.create((int)52, null, (UserConnection)packetWrapper.user());
                            columnUpdate.write((Type)Type.VAR_INT, (Object)id);
                            columnUpdate.write((Type)Type.SHORT, (Object)((short)columnData.length));
                            columnUpdate.write((Type)new CustomByteType(Integer.valueOf(columnData.length)), (Object)columnData);
                            PacketUtil.sendPacket(columnUpdate, Protocol1_7_6_10TO1_8.class, true, true);
                        }
                    }
                    if (count > 0) {
                        byte[] iconData = new byte[count * 3 + 1];
                        iconData[0] = 1;
                        for (int i = 0; i < count; ++i) {
                            iconData[i * 3 + 1] = (byte)(icons[i * 4] << 4 | icons[i * 4 + 3] & 0xF);
                            iconData[i * 3 + 2] = icons[i * 4 + 1];
                            iconData[i * 3 + 3] = icons[i * 4 + 2];
                        }
                        PacketWrapper iconUpdate = PacketWrapper.create((int)52, null, (UserConnection)packetWrapper.user());
                        iconUpdate.write((Type)Type.VAR_INT, (Object)id);
                        iconUpdate.write((Type)Type.SHORT, (Object)((short)iconData.length));
                        CustomByteType customByteType = new CustomByteType(Integer.valueOf(iconData.length));
                        iconUpdate.write((Type)customByteType, (Object)iconData);
                        PacketUtil.sendPacket(iconUpdate, Protocol1_7_6_10TO1_8.class, true, true);
                    }
                    PacketWrapper scaleUpdate = PacketWrapper.create((int)52, null, (UserConnection)packetWrapper.user());
                    scaleUpdate.write((Type)Type.VAR_INT, (Object)id);
                    scaleUpdate.write((Type)Type.SHORT, (Object)2);
                    scaleUpdate.write((Type)new CustomByteType(Integer.valueOf(2)), (Object)new byte[]{2, scale});
                    PacketUtil.sendPacket(scaleUpdate, Protocol1_7_6_10TO1_8.class, true, true);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    Position position = (Position)packetWrapper.read(Type.POSITION);
                    packetWrapper.write((Type)Type.INT, (Object)position.getX());
                    packetWrapper.write((Type)Type.SHORT, (Object)((short)position.getY()));
                    packetWrapper.write((Type)Type.INT, (Object)position.getZ());
                });
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map(Type.NBT, Types1_7_6_10.COMPRESSED_NBT);
            }
        });
        protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.SERVER_DIFFICULTY);
        protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.COMBAT_EVENT);
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.WORLD_BORDER, null, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    int action = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    WorldBorder worldBorder = (WorldBorder)packetWrapper.user().get(WorldBorder.class);
                    if (action == 0) {
                        worldBorder.setSize((Double)packetWrapper.read((Type)Type.DOUBLE));
                    } else if (action == 1) {
                        worldBorder.lerpSize((Double)packetWrapper.read((Type)Type.DOUBLE), (Double)packetWrapper.read((Type)Type.DOUBLE), (Long)packetWrapper.read((Type)VarLongType.VAR_LONG));
                    } else if (action == 2) {
                        worldBorder.setCenter((Double)packetWrapper.read((Type)Type.DOUBLE), (Double)packetWrapper.read((Type)Type.DOUBLE));
                    } else if (action == 3) {
                        worldBorder.init((Double)packetWrapper.read((Type)Type.DOUBLE), (Double)packetWrapper.read((Type)Type.DOUBLE), (Double)packetWrapper.read((Type)Type.DOUBLE), (Double)packetWrapper.read((Type)Type.DOUBLE), (Long)packetWrapper.read((Type)VarLongType.VAR_LONG), (Integer)packetWrapper.read((Type)Type.VAR_INT), (Integer)packetWrapper.read((Type)Type.VAR_INT), (Integer)packetWrapper.read((Type)Type.VAR_INT));
                    } else if (action == 4) {
                        worldBorder.setWarningTime((Integer)packetWrapper.read((Type)Type.VAR_INT));
                    } else if (action == 5) {
                        worldBorder.setWarningBlocks((Integer)packetWrapper.read((Type)Type.VAR_INT));
                    }
                    packetWrapper.cancel();
                });
            }
        });
    }
}

