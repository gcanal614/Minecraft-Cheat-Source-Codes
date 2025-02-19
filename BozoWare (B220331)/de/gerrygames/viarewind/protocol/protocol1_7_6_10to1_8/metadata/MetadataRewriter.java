// 
// Decompiled by Procyon v0.5.36
// 

package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata;

import java.util.Iterator;
import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import com.viaversion.viaversion.api.minecraft.item.Item;
import de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata.MetaIndex1_8to1_7_6_10;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
import java.util.Collection;
import java.util.ArrayList;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;

public class MetadataRewriter
{
    public static void transform(final Entity1_10Types.EntityType type, final List<Metadata> list) {
        for (final Metadata entry : new ArrayList<Metadata>(list)) {
            final MetaIndex1_8to1_7_6_10 metaIndex = MetaIndex1_7_6_10to1_8.searchIndex(type, entry.id());
            try {
                if (metaIndex == null) {
                    throw new Exception("Could not find valid metadata");
                }
                if (metaIndex.getOldType() == MetaType1_7_6_10.NonExistent) {
                    list.remove(entry);
                }
                else {
                    Object value = entry.getValue();
                    if (!value.getClass().isAssignableFrom(metaIndex.getNewType().type().getOutputClass())) {
                        list.remove(entry);
                    }
                    else {
                        entry.setMetaTypeUnsafe(metaIndex.getOldType());
                        entry.setId(metaIndex.getIndex());
                        switch (metaIndex.getOldType()) {
                            case Int: {
                                if (metaIndex.getNewType() == MetaType1_8.Byte) {
                                    entry.setValue((int)value);
                                    if (metaIndex == MetaIndex1_8to1_7_6_10.ENTITY_AGEABLE_AGE && (int)entry.getValue() < 0) {
                                        entry.setValue(-25000);
                                    }
                                }
                                if (metaIndex.getNewType() == MetaType1_8.Short) {
                                    entry.setValue((int)value);
                                }
                                if (metaIndex.getNewType() == MetaType1_8.Int) {
                                    entry.setValue(value);
                                    continue;
                                }
                                continue;
                            }
                            case Byte: {
                                if (metaIndex.getNewType() == MetaType1_8.Int) {
                                    entry.setValue(((Integer)value).byteValue());
                                }
                                if (metaIndex.getNewType() == MetaType1_8.Byte) {
                                    if (metaIndex == MetaIndex1_8to1_7_6_10.ITEM_FRAME_ROTATION) {
                                        value = ((byte)value / 2).byteValue();
                                    }
                                    entry.setValue(value);
                                }
                                if (metaIndex == MetaIndex1_8to1_7_6_10.HUMAN_SKIN_FLAGS) {
                                    byte flags = (byte)value;
                                    final boolean cape = (flags & 0x1) != 0x0;
                                    flags = (byte)(cape ? 0 : 2);
                                    entry.setValue(flags);
                                    continue;
                                }
                                continue;
                            }
                            case Slot: {
                                entry.setValue(ItemRewriter.toClient((Item)value));
                                continue;
                            }
                            case Float: {
                                entry.setValue(value);
                                continue;
                            }
                            case Short: {
                                entry.setValue(value);
                                continue;
                            }
                            case String: {
                                entry.setValue(value);
                                continue;
                            }
                            case Position: {
                                entry.setValue(value);
                                continue;
                            }
                            default: {
                                ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
                                list.remove(entry);
                                continue;
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                list.remove(entry);
            }
        }
    }
}
