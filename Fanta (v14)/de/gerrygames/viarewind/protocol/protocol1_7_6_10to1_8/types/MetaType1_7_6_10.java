/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.minecraft.metadata.MetaType
 *  com.viaversion.viaversion.api.type.Type
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;

public enum MetaType1_7_6_10 implements MetaType
{
    Byte(0, (Type)Type.BYTE),
    Short(1, (Type)Type.SHORT),
    Int(2, (Type)Type.INT),
    Float(3, (Type)Type.FLOAT),
    String(4, Type.STRING),
    Slot(5, Types1_7_6_10.COMPRESSED_NBT_ITEM),
    Position(6, Type.VECTOR),
    NonExistent(-1, (Type)Type.NOTHING);

    private final int typeID;
    private final Type type;

    public static MetaType1_7_6_10 byId(int id) {
        return MetaType1_7_6_10.values()[id];
    }

    private MetaType1_7_6_10(int typeID, Type type) {
        this.typeID = typeID;
        this.type = type;
    }

    public int typeId() {
        return this.typeID;
    }

    public Type type() {
        return this.type;
    }
}

