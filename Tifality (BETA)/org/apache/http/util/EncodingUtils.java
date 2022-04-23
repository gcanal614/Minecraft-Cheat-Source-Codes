/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.util;

import java.io.UnsupportedEncodingException;
import org.apache.http.Consts;
import org.apache.http.util.Args;

public final class EncodingUtils {
    public static String getString(byte[] data2, int offset, int length, String charset) {
        Args.notNull(data2, "Input");
        Args.notEmpty(charset, "Charset");
        try {
            return new String(data2, offset, length, charset);
        }
        catch (UnsupportedEncodingException e) {
            return new String(data2, offset, length);
        }
    }

    public static String getString(byte[] data2, String charset) {
        Args.notNull(data2, "Input");
        return EncodingUtils.getString(data2, 0, data2.length, charset);
    }

    public static byte[] getBytes(String data2, String charset) {
        Args.notNull(data2, "Input");
        Args.notEmpty(charset, "Charset");
        try {
            return data2.getBytes(charset);
        }
        catch (UnsupportedEncodingException e) {
            return data2.getBytes();
        }
    }

    public static byte[] getAsciiBytes(String data2) {
        Args.notNull(data2, "Input");
        try {
            return data2.getBytes(Consts.ASCII.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new Error("ASCII not supported");
        }
    }

    public static String getAsciiString(byte[] data2, int offset, int length) {
        Args.notNull(data2, "Input");
        try {
            return new String(data2, offset, length, Consts.ASCII.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new Error("ASCII not supported");
        }
    }

    public static String getAsciiString(byte[] data2) {
        Args.notNull(data2, "Input");
        return EncodingUtils.getAsciiString(data2, 0, data2.length);
    }

    private EncodingUtils() {
    }
}

