/*
 * Decompiled with CFR 0.152.
 */
package kotlin.comparisons;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UnsignedKt;
import kotlin.comparisons.UComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=5, xi=1, d1={"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0007\u0010\b\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\t\u001a\u00020\n\"\u00020\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000b\u0010\f\u001a\"\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a+\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a&\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\n\u0010\t\u001a\u00020\u0012\"\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a\"\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0015H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a+\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001a&\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\n\u0010\t\u001a\u00020\u001a\"\u00020\u0015H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a\"\u0010\u0000\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001dH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a+\u0010\u0000\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u001dH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010!\u001a&\u0010\u0000\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\n\u0010\t\u001a\u00020\"\"\u00020\u001dH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010$\u001a\"\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b&\u0010\u0005\u001a+\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b'\u0010\b\u001a&\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\t\u001a\u00020\n\"\u00020\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b(\u0010\f\u001a\"\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010\u000f\u001a+\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b*\u0010\u0011\u001a&\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\n\u0010\t\u001a\u00020\u0012\"\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010\u0014\u001a\"\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0015H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b,\u0010\u0017\u001a+\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b-\u0010\u0019\u001a&\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\n\u0010\t\u001a\u00020\u001a\"\u00020\u0015H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b.\u0010\u001c\u001a\"\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001dH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u0010\u001f\u001a+\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u001dH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u0010!\u001a&\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\n\u0010\t\u001a\u00020\"\"\u00020\u001dH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u0010$\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u00062"}, d2={"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "other", "Lkotlin/UByteArray;", "maxOf-Wr6uiD8", "(B[B)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/UIntArray;", "maxOf-Md2H83M", "(I[I)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/ULongArray;", "maxOf-R03FKyM", "(J[J)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "Lkotlin/UShortArray;", "maxOf-t1qELG4", "(S[S)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-Wr6uiD8", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-Md2H83M", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-R03FKyM", "minOf-5PvTz6A", "minOf-VKSA0NQ", "minOf-t1qELG4", "kotlin-stdlib"}, xs="kotlin/comparisons/UComparisonsKt")
class UComparisonsKt___UComparisonsKt {
    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final int maxOf-J1ME1BU(int a2, int b2) {
        int n = a2;
        boolean bl = false;
        return UnsignedKt.uintCompare(n, b2) >= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final long maxOf-eb3DHEI(long a2, long b2) {
        long l = a2;
        boolean bl = false;
        return UnsignedKt.ulongCompare(l, b2) >= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final byte maxOf-Kr8caGY(byte a2, byte b2) {
        byte by = a2;
        boolean bl = false;
        byte by2 = by;
        boolean bl2 = false;
        int n = by2 & 0xFF;
        by2 = b2;
        bl2 = false;
        return Intrinsics.compare(n, by2 & 0xFF) >= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final short maxOf-5PvTz6A(short a2, short b2) {
        short s = a2;
        boolean bl = false;
        short s2 = s;
        boolean bl2 = false;
        int n = s2 & 0xFFFF;
        s2 = b2;
        bl2 = false;
        return Intrinsics.compare(n, s2 & 0xFFFF) >= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int maxOf-WZ9TVnA(int a2, int b2, int c) {
        int n = 0;
        return UComparisonsKt.maxOf-J1ME1BU(a2, UComparisonsKt.maxOf-J1ME1BU(b2, c));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final long maxOf-sambcqE(long a2, long b2, long c) {
        int n = 0;
        return UComparisonsKt.maxOf-eb3DHEI(a2, UComparisonsKt.maxOf-eb3DHEI(b2, c));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final byte maxOf-b33U2AM(byte a2, byte b2, byte c) {
        int n = 0;
        return UComparisonsKt.maxOf-Kr8caGY(a2, UComparisonsKt.maxOf-Kr8caGY(b2, c));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final short maxOf-VKSA0NQ(short a2, short b2, short c) {
        int n = 0;
        return UComparisonsKt.maxOf-5PvTz6A(a2, UComparisonsKt.maxOf-5PvTz6A(b2, c));
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final int maxOf-Md2H83M(int a2, int ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int max = a2;
        for (int e : other) {
            max = UComparisonsKt.maxOf-J1ME1BU(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final long maxOf-R03FKyM(long a2, long ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        long max = a2;
        for (long e : other) {
            max = UComparisonsKt.maxOf-eb3DHEI(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final byte maxOf-Wr6uiD8(byte a2, byte ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        byte max = a2;
        for (byte e : other) {
            max = UComparisonsKt.maxOf-Kr8caGY(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final short maxOf-t1qELG4(short a2, short ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        short max = a2;
        for (short e : other) {
            max = UComparisonsKt.maxOf-5PvTz6A(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final int minOf-J1ME1BU(int a2, int b2) {
        int n = a2;
        boolean bl = false;
        return UnsignedKt.uintCompare(n, b2) <= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final long minOf-eb3DHEI(long a2, long b2) {
        long l = a2;
        boolean bl = false;
        return UnsignedKt.ulongCompare(l, b2) <= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final byte minOf-Kr8caGY(byte a2, byte b2) {
        byte by = a2;
        boolean bl = false;
        byte by2 = by;
        boolean bl2 = false;
        int n = by2 & 0xFF;
        by2 = b2;
        bl2 = false;
        return Intrinsics.compare(n, by2 & 0xFF) <= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    public static final short minOf-5PvTz6A(short a2, short b2) {
        short s = a2;
        boolean bl = false;
        short s2 = s;
        boolean bl2 = false;
        int n = s2 & 0xFFFF;
        s2 = b2;
        bl2 = false;
        return Intrinsics.compare(n, s2 & 0xFFFF) <= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int minOf-WZ9TVnA(int a2, int b2, int c) {
        int n = 0;
        return UComparisonsKt.minOf-J1ME1BU(a2, UComparisonsKt.minOf-J1ME1BU(b2, c));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final long minOf-sambcqE(long a2, long b2, long c) {
        int n = 0;
        return UComparisonsKt.minOf-eb3DHEI(a2, UComparisonsKt.minOf-eb3DHEI(b2, c));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final byte minOf-b33U2AM(byte a2, byte b2, byte c) {
        int n = 0;
        return UComparisonsKt.minOf-Kr8caGY(a2, UComparisonsKt.minOf-Kr8caGY(b2, c));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final short minOf-VKSA0NQ(short a2, short b2, short c) {
        int n = 0;
        return UComparisonsKt.minOf-5PvTz6A(a2, UComparisonsKt.minOf-5PvTz6A(b2, c));
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final int minOf-Md2H83M(int a2, int ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int min = a2;
        for (int e : other) {
            min = UComparisonsKt.minOf-J1ME1BU(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final long minOf-R03FKyM(long a2, long ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        long min = a2;
        for (long e : other) {
            min = UComparisonsKt.minOf-eb3DHEI(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final byte minOf-Wr6uiD8(byte a2, byte ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        byte min = a2;
        for (byte e : other) {
            min = UComparisonsKt.minOf-Kr8caGY(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    @ExperimentalUnsignedTypes
    public static final short minOf-t1qELG4(short a2, short ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        short min = a2;
        for (short e : other) {
            min = UComparisonsKt.minOf-5PvTz6A(min, e);
        }
        return min;
    }
}

