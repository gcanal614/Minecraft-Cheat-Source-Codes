/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.comparisons.ComparisonsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=5, xi=1, d1={"\u0000F\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0002\u0010\u0006\n\u0002\u0010\u0013\n\u0002\u0010\u0007\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\n\n\u0002\u0010\u0017\n\u0002\b\u0002\u001a-\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u0001H\u0007\u00a2\u0006\u0002\u0010\u0005\u001a5\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0006\u001a\u0002H\u0001H\u0007\u00a2\u0006\u0002\u0010\u0007\u001a9\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\t\"\u0002H\u0001H\u0007\u00a2\u0006\u0002\u0010\n\u001a\u0019\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0087\b\u001a!\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u000bH\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\n\u0010\b\u001a\u00020\f\"\u00020\u000bH\u0007\u001a\u0019\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\rH\u0087\b\u001a!\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\n\u0010\b\u001a\u00020\u000e\"\u00020\rH\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0000\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\n\u0010\b\u001a\u00020\u0010\"\u00020\u000fH\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0000\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\n\u0010\b\u001a\u00020\u0012\"\u00020\u0011H\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0013H\u0087\b\u001a!\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\n\u0010\b\u001a\u00020\u0014\"\u00020\u0013H\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0015H\u0087\b\u001a!\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\b\u001a\u001c\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\n\u0010\b\u001a\u00020\u0016\"\u00020\u0015H\u0007\u001a-\u0010\u0017\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u0001H\u0007\u00a2\u0006\u0002\u0010\u0005\u001a5\u0010\u0017\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u0006\u0010\u0006\u001a\u0002H\u0001H\u0007\u00a2\u0006\u0002\u0010\u0007\u001a9\u0010\u0017\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\t\"\u0002H\u0001H\u0007\u00a2\u0006\u0002\u0010\n\u001a\u0019\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000bH\u0087\b\u001a!\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u000bH\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b2\n\u0010\b\u001a\u00020\f\"\u00020\u000bH\u0007\u001a\u0019\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\rH\u0087\b\u001a!\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\n\u0010\b\u001a\u00020\u000e\"\u00020\rH\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000f2\n\u0010\b\u001a\u00020\u0010\"\u00020\u000fH\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00112\n\u0010\b\u001a\u00020\u0012\"\u00020\u0011H\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0013H\u0087\b\u001a!\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\n\u0010\b\u001a\u00020\u0014\"\u00020\u0013H\u0007\u001a\u0019\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0015H\u0087\b\u001a!\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\b\u001a\u001c\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\n\u0010\b\u001a\u00020\u0016\"\u00020\u0015H\u0007\u00a8\u0006\u0018"}, d2={"maxOf", "T", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "c", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "other", "", "(Ljava/lang/Comparable;[Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "", "", "", "", "", "", "minOf", "kotlin-stdlib"}, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt___ComparisonsJvmKt
extends ComparisonsKt__ComparisonsKt {
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T extends Comparable<? super T>> T maxOf(@NotNull T a2, @NotNull T b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return a2.compareTo(b2) >= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final byte maxOf(byte a2, byte b2) {
        int $i$f$maxOf = 0;
        return (byte)Math.max(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final short maxOf(short a2, short b2) {
        int $i$f$maxOf = 0;
        return (short)Math.max(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int maxOf(int a2, int b2) {
        int $i$f$maxOf = 0;
        return Math.max(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final long maxOf(long a2, long b2) {
        int $i$f$maxOf = 0;
        return Math.max(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final float maxOf(float a2, float b2) {
        int $i$f$maxOf = 0;
        return Math.max(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final double maxOf(double a2, double b2) {
        int $i$f$maxOf = 0;
        return Math.max(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T extends Comparable<? super T>> T maxOf(@NotNull T a2, @NotNull T b2, @NotNull T c) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        return ComparisonsKt.maxOf(a2, ComparisonsKt.maxOf(b2, c));
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final byte maxOf(byte a2, byte b2, byte c) {
        int $i$f$maxOf = 0;
        return (byte)Math.max(a2, Math.max(b2, c));
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final short maxOf(short a2, short b2, short c) {
        int $i$f$maxOf = 0;
        return (short)Math.max(a2, Math.max(b2, c));
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int maxOf(int a2, int b2, int c) {
        int $i$f$maxOf = 0;
        int n = 0;
        n = Math.max(b2, c);
        boolean bl = false;
        return Math.max(a2, n);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final long maxOf(long a2, long b2, long c) {
        int $i$f$maxOf = 0;
        boolean bl = false;
        long l = Math.max(b2, c);
        boolean bl2 = false;
        return Math.max(a2, l);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final float maxOf(float a2, float b2, float c) {
        int $i$f$maxOf = 0;
        boolean bl = false;
        float f2 = Math.max(b2, c);
        boolean bl2 = false;
        return Math.max(a2, f2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final double maxOf(double a2, double b2, double c) {
        int $i$f$maxOf = 0;
        boolean bl = false;
        double d = Math.max(b2, c);
        boolean bl2 = false;
        return Math.max(a2, d);
    }

    @SinceKotlin(version="1.4")
    @NotNull
    public static final <T extends Comparable<? super T>> T maxOf(@NotNull T a2, T ... other) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(other, "other");
        T max = a2;
        for (T e : other) {
            max = ComparisonsKt.maxOf(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    public static final byte maxOf(byte a2, byte ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        byte max = a2;
        for (byte e : other) {
            boolean bl = false;
            max = (byte)Math.max(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    public static final short maxOf(short a2, short ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        short max = a2;
        for (short e : other) {
            boolean bl = false;
            max = (short)Math.max(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    public static final int maxOf(int a2, int ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int max = a2;
        for (int e : other) {
            boolean bl = false;
            max = Math.max(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    public static final long maxOf(long a2, long ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        long max = a2;
        for (long e : other) {
            boolean bl = false;
            max = Math.max(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    public static final float maxOf(float a2, float ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        float max = a2;
        for (float e : other) {
            boolean bl = false;
            max = Math.max(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.4")
    public static final double maxOf(double a2, double ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        double max = a2;
        for (double e : other) {
            boolean bl = false;
            max = Math.max(max, e);
        }
        return max;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T extends Comparable<? super T>> T minOf(@NotNull T a2, @NotNull T b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return a2.compareTo(b2) <= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final byte minOf(byte a2, byte b2) {
        int $i$f$minOf = 0;
        return (byte)Math.min(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final short minOf(short a2, short b2) {
        int $i$f$minOf = 0;
        return (short)Math.min(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int minOf(int a2, int b2) {
        int $i$f$minOf = 0;
        return Math.min(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final long minOf(long a2, long b2) {
        int $i$f$minOf = 0;
        return Math.min(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final float minOf(float a2, float b2) {
        int $i$f$minOf = 0;
        return Math.min(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final double minOf(double a2, double b2) {
        int $i$f$minOf = 0;
        return Math.min(a2, b2);
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T extends Comparable<? super T>> T minOf(@NotNull T a2, @NotNull T b2, @NotNull T c) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        return ComparisonsKt.minOf(a2, ComparisonsKt.minOf(b2, c));
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final byte minOf(byte a2, byte b2, byte c) {
        int $i$f$minOf = 0;
        return (byte)Math.min(a2, Math.min(b2, c));
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final short minOf(short a2, short b2, short c) {
        int $i$f$minOf = 0;
        return (short)Math.min(a2, Math.min(b2, c));
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int minOf(int a2, int b2, int c) {
        int $i$f$minOf = 0;
        int n = 0;
        n = Math.min(b2, c);
        boolean bl = false;
        return Math.min(a2, n);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final long minOf(long a2, long b2, long c) {
        int $i$f$minOf = 0;
        boolean bl = false;
        long l = Math.min(b2, c);
        boolean bl2 = false;
        return Math.min(a2, l);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final float minOf(float a2, float b2, float c) {
        int $i$f$minOf = 0;
        boolean bl = false;
        float f2 = Math.min(b2, c);
        boolean bl2 = false;
        return Math.min(a2, f2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final double minOf(double a2, double b2, double c) {
        int $i$f$minOf = 0;
        boolean bl = false;
        double d = Math.min(b2, c);
        boolean bl2 = false;
        return Math.min(a2, d);
    }

    @SinceKotlin(version="1.4")
    @NotNull
    public static final <T extends Comparable<? super T>> T minOf(@NotNull T a2, T ... other) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(other, "other");
        T min = a2;
        for (T e : other) {
            min = ComparisonsKt.minOf(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    public static final byte minOf(byte a2, byte ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        byte min = a2;
        for (byte e : other) {
            boolean bl = false;
            min = (byte)Math.min(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    public static final short minOf(short a2, short ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        short min = a2;
        for (short e : other) {
            boolean bl = false;
            min = (short)Math.min(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    public static final int minOf(int a2, int ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int min = a2;
        for (int e : other) {
            boolean bl = false;
            min = Math.min(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    public static final long minOf(long a2, long ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        long min = a2;
        for (long e : other) {
            boolean bl = false;
            min = Math.min(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    public static final float minOf(float a2, float ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        float min = a2;
        for (float e : other) {
            boolean bl = false;
            min = Math.min(min, e);
        }
        return min;
    }

    @SinceKotlin(version="1.4")
    public static final double minOf(double a2, double ... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        double min = a2;
        for (double e : other) {
            boolean bl = false;
            min = Math.min(min, e);
        }
        return min;
    }
}

