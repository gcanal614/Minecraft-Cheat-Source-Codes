/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.connection.UserConnection
 *  com.viaversion.viaversion.api.protocol.AbstractProtocol
 *  com.viaversion.viaversion.api.protocol.packet.PacketWrapper
 *  com.viaversion.viaversion.api.protocol.packet.State
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.protocol.remapper.ValueTransformer
 *  com.viaversion.viaversion.api.type.Type
 */
package de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol1_7_0_5to1_7_6_10
extends AbstractProtocol<ClientboundPackets1_7, ClientboundPackets1_7, ServerboundPackets1_7, ServerboundPackets1_7> {
    public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Type.STRING){

        public String transform(PacketWrapper packetWrapper, String s) {
            return s.replace("-", "");
        }
    };

    public Protocol1_7_0_5to1_7_6_10() {
        super(ClientboundPackets1_7.class, ClientboundPackets1_7.class, ServerboundPackets1_7.class, ServerboundPackets1_7.class);
    }

    protected void registerPackets() {
        this.registerClientbound(State.LOGIN, 2, 2, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING, REMOVE_DASHES);
                this.map(Type.STRING);
            }
        });
        this.registerClientbound(ClientboundPackets1_7.SPAWN_PLAYER, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.VAR_INT);
                this.map(Type.STRING, REMOVE_DASHES);
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    int size = (Integer)packetWrapper.read((Type)Type.VAR_INT);
                    for (int i = 0; i < size * 3; ++i) {
                        packetWrapper.read(Type.STRING);
                    }
                });
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.INT);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.BYTE);
                this.map((Type)Type.SHORT);
                this.map(Types1_7_6_10.METADATA_LIST);
            }
        });
        this.registerClientbound(ClientboundPackets1_7.TEAMS, new PacketRemapper(){

            public void registerMap() {
                this.map(Type.STRING);
                this.map((Type)Type.BYTE);
                this.handler(packetWrapper -> {
                    byte mode = (Byte)packetWrapper.get((Type)Type.BYTE, 0);
                    if (mode == 0 || mode == 2) {
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough(Type.STRING);
                        packetWrapper.passthrough((Type)Type.BYTE);
                    }
                    if (mode == 0 || mode == 3 || mode == 4) {
                        List<Object> entryList = new ArrayList<String>();
                        int size = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
                        for (int i = 0; i < size; ++i) {
                            entryList.add((String)packetWrapper.read(Type.STRING));
                        }
                        entryList = entryList.stream().map(it -> it.length() > 16 ? it.substring(0, 16) : it).distinct().collect(Collectors.toList());
                        packetWrapper.write((Type)Type.SHORT, (Object)((short)entryList.size()));
                        for (String string : entryList) {
                            packetWrapper.write(Type.STRING, (Object)string);
                        }
                    }
                });
            }
        });
    }

    public void init(UserConnection userConnection) {
    }
}

