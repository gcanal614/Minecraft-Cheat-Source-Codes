/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

@GwtCompatible
public final class Strings {
    private Strings() {
    }

    public static String nullToEmpty(@Nullable String string) {
        return string == null ? "" : string;
    }

    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return Strings.isNullOrEmpty(string) ? null : string;
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }

    public static String padStart(String string, int minLength, char padChar) {
        Preconditions.checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; ++i) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }

    public static String padEnd(String string, int minLength, char padChar) {
        Preconditions.checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);
        for (int i = string.length(); i < minLength; ++i) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    public static String repeat(String string, int count) {
        int n;
        Preconditions.checkNotNull(string);
        if (count <= 1) {
            Preconditions.checkArgument(count >= 0, "invalid count: %s", count);
            return count == 0 ? "" : string;
        }
        int len = string.length();
        long longSize = (long)len * (long)count;
        int size = (int)longSize;
        if ((long)size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + longSize);
        }
        char[] array = new char[size];
        string.getChars(0, len, array, 0);
        for (n = len; n < size - n; n <<= 1) {
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }

    public static String commonPrefix(CharSequence a2, CharSequence b2) {
        int p;
        Preconditions.checkNotNull(a2);
        Preconditions.checkNotNull(b2);
        int maxPrefixLength = Math.min(a2.length(), b2.length());
        for (p = 0; p < maxPrefixLength && a2.charAt(p) == b2.charAt(p); ++p) {
        }
        if (Strings.validSurrogatePairAt(a2, p - 1) || Strings.validSurrogatePairAt(b2, p - 1)) {
            --p;
        }
        return a2.subSequence(0, p).toString();
    }

    public static String commonSuffix(CharSequence a2, CharSequence b2) {
        int s;
        Preconditions.checkNotNull(a2);
        Preconditions.checkNotNull(b2);
        int maxSuffixLength = Math.min(a2.length(), b2.length());
        for (s = 0; s < maxSuffixLength && a2.charAt(a2.length() - s - 1) == b2.charAt(b2.length() - s - 1); ++s) {
        }
        if (Strings.validSurrogatePairAt(a2, a2.length() - s - 1) || Strings.validSurrogatePairAt(b2, b2.length() - s - 1)) {
            --s;
        }
        return a2.subSequence(a2.length() - s, a2.length()).toString();
    }

    @VisibleForTesting
    static boolean validSurrogatePairAt(CharSequence string, int index) {
        return index >= 0 && index <= string.length() - 2 && Character.isHighSurrogate(string.charAt(index)) && Character.isLowSurrogate(string.charAt(index + 1));
    }
}

