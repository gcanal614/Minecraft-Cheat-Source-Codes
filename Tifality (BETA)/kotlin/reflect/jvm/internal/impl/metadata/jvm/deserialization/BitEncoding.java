/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.UtfEncodingKt;
import org.jetbrains.annotations.NotNull;

public class BitEncoding {
    private static final boolean FORCE_8TO7_ENCODING;

    private BitEncoding() {
    }

    private static void addModuloByte(@NotNull byte[] data2, int increment) {
        if (data2 == null) {
            BitEncoding.$$$reportNull$$$0(4);
        }
        int n = data2.length;
        for (int i = 0; i < n; ++i) {
            data2[i] = (byte)(data2[i] + increment & 0x7F);
        }
    }

    @NotNull
    public static byte[] decodeBytes(@NotNull String[] data2) {
        if (data2 == null) {
            BitEncoding.$$$reportNull$$$0(7);
        }
        if (data2.length > 0 && !data2[0].isEmpty()) {
            char possibleMarker = data2[0].charAt(0);
            if (possibleMarker == '\u0000') {
                byte[] byArray = UtfEncodingKt.stringsToBytes(BitEncoding.dropMarker(data2));
                if (byArray == null) {
                    BitEncoding.$$$reportNull$$$0(8);
                }
                return byArray;
            }
            if (possibleMarker == '\uffff') {
                data2 = BitEncoding.dropMarker(data2);
            }
        }
        byte[] bytes = BitEncoding.combineStringArrayIntoBytes(data2);
        BitEncoding.addModuloByte(bytes, 127);
        return BitEncoding.decode7to8(bytes);
    }

    @NotNull
    private static String[] dropMarker(@NotNull String[] data2) {
        if (data2 == null) {
            BitEncoding.$$$reportNull$$$0(9);
        }
        String[] result2 = (String[])data2.clone();
        result2[0] = result2[0].substring(1);
        if (result2 == null) {
            BitEncoding.$$$reportNull$$$0(10);
        }
        return result2;
    }

    @NotNull
    private static byte[] combineStringArrayIntoBytes(@NotNull String[] data2) {
        if (data2 == null) {
            BitEncoding.$$$reportNull$$$0(11);
        }
        int resultLength = 0;
        for (String s : data2) {
            assert (s.length() <= 65535) : "String is too long: " + s.length();
            resultLength += s.length();
        }
        byte[] result2 = new byte[resultLength];
        int p = 0;
        for (String s : data2) {
            int n = s.length();
            for (int i = 0; i < n; ++i) {
                result2[p++] = (byte)s.charAt(i);
            }
        }
        if (result2 == null) {
            BitEncoding.$$$reportNull$$$0(12);
        }
        return result2;
    }

    @NotNull
    private static byte[] decode7to8(@NotNull byte[] data2) {
        if (data2 == null) {
            BitEncoding.$$$reportNull$$$0(13);
        }
        int resultLength = 7 * data2.length / 8;
        byte[] result2 = new byte[resultLength];
        int byteIndex = 0;
        int bit = 0;
        for (int i = 0; i < resultLength; ++i) {
            int firstPart = (data2[byteIndex] & 0xFF) >>> bit;
            int secondPart = (data2[++byteIndex] & (1 << bit + 1) - 1) << 7 - bit;
            result2[i] = (byte)(firstPart + secondPart);
            if (bit == 6) {
                ++byteIndex;
                bit = 0;
                continue;
            }
            ++bit;
        }
        if (result2 == null) {
            BitEncoding.$$$reportNull$$$0(14);
        }
        return result2;
    }

    static {
        String use8to7;
        try {
            use8to7 = System.getProperty("kotlin.jvm.serialization.use8to7");
        }
        catch (SecurityException e) {
            use8to7 = null;
        }
        FORCE_8TO7_ENCODING = "true".equals(use8to7);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 1: 
            case 3: 
            case 6: 
            case 8: 
            case 10: 
            case 12: 
            case 14: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 1: 
            case 3: 
            case 6: 
            case 8: 
            case 10: 
            case 12: 
            case 14: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "data";
                break;
            }
            case 1: 
            case 3: 
            case 6: 
            case 8: 
            case 10: 
            case 12: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/BitEncoding";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/BitEncoding";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "encodeBytes";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "encode8to7";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "splitBytesToStringArray";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "decodeBytes";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "dropMarker";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "combineStringArrayIntoBytes";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "decode7to8";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "encodeBytes";
                break;
            }
            case 1: 
            case 3: 
            case 6: 
            case 8: 
            case 10: 
            case 12: 
            case 14: {
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "encode8to7";
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "addModuloByte";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "splitBytesToStringArray";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "decodeBytes";
                break;
            }
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "dropMarker";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "combineStringArrayIntoBytes";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "decode7to8";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 1: 
            case 3: 
            case 6: 
            case 8: 
            case 10: 
            case 12: 
            case 14: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

