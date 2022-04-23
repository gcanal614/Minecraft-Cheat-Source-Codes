/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;

public final class WireFormat {
    static final int MESSAGE_SET_ITEM_TAG = WireFormat.makeTag(1, 3);
    static final int MESSAGE_SET_ITEM_END_TAG = WireFormat.makeTag(1, 4);
    static final int MESSAGE_SET_TYPE_ID_TAG = WireFormat.makeTag(2, 0);
    static final int MESSAGE_SET_MESSAGE_TAG = WireFormat.makeTag(3, 2);

    static int getTagWireType(int tag) {
        return tag & 7;
    }

    public static int getTagFieldNumber(int tag) {
        return tag >>> 3;
    }

    static int makeTag(int fieldNumber, int wireType) {
        return fieldNumber << 3 | wireType;
    }

    public static enum FieldType {
        DOUBLE(JavaType.DOUBLE, 1),
        FLOAT(JavaType.FLOAT, 5),
        INT64(JavaType.LONG, 0),
        UINT64(JavaType.LONG, 0),
        INT32(JavaType.INT, 0),
        FIXED64(JavaType.LONG, 1),
        FIXED32(JavaType.INT, 5),
        BOOL(JavaType.BOOLEAN, 0),
        STRING(JavaType.STRING, 2){

            @Override
            public boolean isPackable() {
                return false;
            }
        }
        ,
        GROUP(JavaType.MESSAGE, 3){

            @Override
            public boolean isPackable() {
                return false;
            }
        }
        ,
        MESSAGE(JavaType.MESSAGE, 2){

            @Override
            public boolean isPackable() {
                return false;
            }
        }
        ,
        BYTES(JavaType.BYTE_STRING, 2){

            @Override
            public boolean isPackable() {
                return false;
            }
        }
        ,
        UINT32(JavaType.INT, 0),
        ENUM(JavaType.ENUM, 0),
        SFIXED32(JavaType.INT, 5),
        SFIXED64(JavaType.LONG, 1),
        SINT32(JavaType.INT, 0),
        SINT64(JavaType.LONG, 0);

        private final JavaType javaType;
        private final int wireType;

        private FieldType(JavaType javaType, int wireType) {
            this.javaType = javaType;
            this.wireType = wireType;
        }

        public JavaType getJavaType() {
            return this.javaType;
        }

        public int getWireType() {
            return this.wireType;
        }

        public boolean isPackable() {
            return true;
        }
    }

    public static enum JavaType {
        INT(0),
        LONG(0L),
        FLOAT(Float.valueOf(0.0f)),
        DOUBLE(0.0),
        BOOLEAN(false),
        STRING(""),
        BYTE_STRING(ByteString.EMPTY),
        ENUM(null),
        MESSAGE(null);

        private final Object defaultDefault;

        private JavaType(Object defaultDefault) {
            this.defaultDefault = defaultDefault;
        }
    }
}

