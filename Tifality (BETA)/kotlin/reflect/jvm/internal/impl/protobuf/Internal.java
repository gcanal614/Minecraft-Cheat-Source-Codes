/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import kotlin.reflect.jvm.internal.impl.protobuf.Utf8;

public class Internal {
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.wrap(EMPTY_BYTE_ARRAY);

    public static boolean isValidUtf8(byte[] byteArray) {
        return Utf8.isValidUtf8(byteArray);
    }

    public static String toStringUtf8(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public static interface EnumLiteMap<T extends EnumLite> {
        public T findValueByNumber(int var1);
    }

    public static interface EnumLite {
        public int getNumber();
    }
}

