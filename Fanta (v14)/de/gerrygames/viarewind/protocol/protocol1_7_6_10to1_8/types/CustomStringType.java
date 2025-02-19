/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.api.type.PartialType
 *  com.viaversion.viaversion.api.type.Type
 *  io.netty.buffer.ByteBuf
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class CustomStringType
extends PartialType<String[], Integer> {
    public CustomStringType(Integer param) {
        super((Object)param, String[].class);
    }

    public String[] read(ByteBuf buffer, Integer size) throws Exception {
        if (buffer.readableBytes() < size / 4) {
            throw new RuntimeException("Readable bytes does not match expected!");
        }
        String[] array = new String[size.intValue()];
        for (int i = 0; i < size; ++i) {
            array[i] = (String)Type.STRING.read(buffer);
        }
        return array;
    }

    public void write(ByteBuf buffer, Integer size, String[] strings) throws Exception {
        for (String s : strings) {
            Type.STRING.write(buffer, (Object)s);
        }
    }
}

