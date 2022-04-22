/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.minecraft.item.Item
 *  com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType
 *  com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
 *  com.viaversion.viaversion.api.type.Type
 *  com.viaversion.viaversion.libs.gson.JsonElement
 *  com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.utils.ChatUtil;
import java.util.UUID;

public class InventoryPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.OPEN_WINDOW, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    short windowId = (Short)packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
                    String windowType = (String)packetWrapper.read(Type.STRING);
                    short windowtypeId = (short)Windows.getInventoryType(windowType);
                    ((Windows)packetWrapper.user().get(Windows.class)).types.put(windowId, windowtypeId);
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)windowtypeId);
                    JsonElement titleComponent = (JsonElement)packetWrapper.read(Type.COMPONENT);
                    String title = ChatUtil.jsonToLegacy(titleComponent);
                    title = ChatUtil.removeUnusedColor(title, '8');
                    if (title.length() > 32) {
                        title = title.substring(0, 32);
                    }
                    packetWrapper.write(Type.STRING, (Object)title);
                    packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
                    packetWrapper.write((Type)Type.BOOLEAN, (Object)true);
                    if (windowtypeId == 11) {
                        packetWrapper.passthrough((Type)Type.INT);
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CLOSE_WINDOW, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.UNSIGNED_BYTE);
                this.handler(packetWrapper -> {
                    short windowsId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    ((Windows)packetWrapper.user().get(Windows.class)).remove(windowsId);
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SET_SLOT, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    short windowId = (Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                    short windowType = ((Windows)packetWrapper.user().get(Windows.class)).get(windowId);
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)windowId);
                    short slot = (Short)packetWrapper.read((Type)Type.SHORT);
                    if (windowType == 4) {
                        if (slot == 1) {
                            packetWrapper.cancel();
                            return;
                        }
                        if (slot >= 2) {
                            slot = (short)(slot - 1);
                        }
                    }
                    packetWrapper.write((Type)Type.SHORT, (Object)slot);
                });
                this.map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                this.handler(packetWrapper -> {
                    Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    ItemRewriter.toClient(item);
                    packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, (Object)item);
                });
                this.handler(packetWrapper -> {
                    UUID uuid;
                    short windowId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    if (windowId != 0) {
                        return;
                    }
                    short slot = (Short)packetWrapper.get((Type)Type.SHORT, 0);
                    if (slot < 5 || slot > 8) {
                        return;
                    }
                    Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    Item[] equipment = tracker.getPlayerEquipment(uuid = packetWrapper.user().getProtocolInfo().getUuid());
                    if (equipment == null) {
                        equipment = new Item[5];
                        tracker.setPlayerEquipment(uuid, equipment);
                    }
                    equipment[9 - slot] = item;
                    if (tracker.getGamemode() == 3) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.WINDOW_ITEMS, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    short windowId = (Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE);
                    short windowType = ((Windows)packetWrapper.user().get(Windows.class)).get(windowId);
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)windowId);
                    Item[] items = (Item[])packetWrapper.read(Type.ITEM_ARRAY);
                    if (windowType == 4) {
                        Item[] old = items;
                        items = new Item[old.length - 1];
                        items[0] = old[0];
                        System.arraycopy(old, 2, items, 1, old.length - 3);
                    }
                    for (int i = 0; i < items.length; ++i) {
                        items[i] = ItemRewriter.toClient(items[i]);
                    }
                    packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, (Object)items);
                });
                this.handler(packetWrapper -> {
                    GameProfileStorage.GameProfile profile;
                    UUID uuid;
                    short windowId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    if (windowId != 0) {
                        return;
                    }
                    Item[] items = (Item[])packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, 0);
                    EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
                    Item[] equipment = tracker.getPlayerEquipment(uuid = packetWrapper.user().getProtocolInfo().getUuid());
                    if (equipment == null) {
                        equipment = new Item[5];
                        tracker.setPlayerEquipment(uuid, equipment);
                    }
                    for (int i = 5; i < 9; ++i) {
                        equipment[9 - i] = items[i];
                        if (tracker.getGamemode() != 3) continue;
                        items[i] = null;
                    }
                    if (tracker.getGamemode() == 3 && (profile = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(uuid)) != null) {
                        items[5] = profile.getSkull();
                    }
                });
            }
        });
        protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.WINDOW_PROPERTY, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.UNSIGNED_BYTE);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.SHORT);
                this.handler(packetWrapper -> {
                    short windowId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    Windows windows = (Windows)packetWrapper.user().get(Windows.class);
                    short windowType = windows.get(windowId);
                    short property = (Short)packetWrapper.get((Type)Type.SHORT, 0);
                    short value = (Short)packetWrapper.get((Type)Type.SHORT, 1);
                    if (windowType == -1) {
                        return;
                    }
                    if (windowType == 2) {
                        Windows.Furnace furnace = windows.furnace.computeIfAbsent(windowId, x -> new Windows.Furnace());
                        if (property == 0 || property == 1) {
                            if (property == 0) {
                                furnace.setFuelLeft(value);
                            } else {
                                furnace.setMaxFuel(value);
                            }
                            if (furnace.getMaxFuel() == 0) {
                                packetWrapper.cancel();
                                return;
                            }
                            value = (short)(200 * furnace.getFuelLeft() / furnace.getMaxFuel());
                            packetWrapper.set((Type)Type.SHORT, 0, (Object)1);
                            packetWrapper.set((Type)Type.SHORT, 1, (Object)value);
                        } else if (property == 2 || property == 3) {
                            if (property == 2) {
                                furnace.setProgress(value);
                            } else {
                                furnace.setMaxProgress(value);
                            }
                            if (furnace.getMaxProgress() == 0) {
                                packetWrapper.cancel();
                                return;
                            }
                            value = (short)(200 * furnace.getProgress() / furnace.getMaxProgress());
                            packetWrapper.set((Type)Type.SHORT, 0, (Object)0);
                            packetWrapper.set((Type)Type.SHORT, 1, (Object)value);
                        }
                    } else if (windowType == 4) {
                        if (property > 2) {
                            packetWrapper.cancel();
                            return;
                        }
                    } else if (windowType == 8) {
                        windows.levelCost = value;
                        windows.anvilId = windowId;
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.CLOSE_WINDOW, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.UNSIGNED_BYTE);
                this.handler(packetWrapper -> {
                    short windowsId = (Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0);
                    ((Windows)packetWrapper.user().get(Windows.class)).remove(windowsId);
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.CLICK_WINDOW, new PacketRemapper(){

            public void registerMap() {
                this.handler(packetWrapper -> {
                    short windowId = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
                    packetWrapper.write((Type)Type.UNSIGNED_BYTE, (Object)windowId);
                    short windowType = ((Windows)packetWrapper.user().get(Windows.class)).get(windowId);
                    short slot = (Short)packetWrapper.read((Type)Type.SHORT);
                    if (windowType == 4 && slot > 0) {
                        slot = (short)(slot + 1);
                    }
                    packetWrapper.write((Type)Type.SHORT, (Object)slot);
                });
                this.map((Type)Type.BYTE);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.BYTE);
                this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                this.handler(packetWrapper -> {
                    Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                    ItemRewriter.toServer(item);
                    packetWrapper.set(Type.ITEM, 0, (Object)item);
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.WINDOW_CONFIRMATION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.BYTE);
                this.map((Type)Type.SHORT);
                this.map((Type)Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    short action = (Short)packetWrapper.get((Type)Type.SHORT, 0);
                    if (action == -89) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_7.CREATIVE_INVENTORY_ACTION, new PacketRemapper(){

            public void registerMap() {
                this.map((Type)Type.SHORT);
                this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                this.handler(packetWrapper -> {
                    Item item = (Item)packetWrapper.get(Type.ITEM, 0);
                    ItemRewriter.toServer(item);
                    packetWrapper.set(Type.ITEM, 0, (Object)item);
                });
            }
        });
    }
}

