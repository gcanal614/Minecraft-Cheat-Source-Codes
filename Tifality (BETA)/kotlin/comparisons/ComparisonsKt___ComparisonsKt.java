/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.comparisons.ComparisonsKt;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=5, xi=1, d1={"\u0000\u0018\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\u001aG\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\b\u001a?\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\t\u001aK\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\u000b\"\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\f\u001aG\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\b\u001a?\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\t\u001aK\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\u000b\"\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007\u00a2\u0006\u0002\u0010\f\u00a8\u0006\u000e"}, d2={"maxOf", "T", "a", "b", "c", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "other", "", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "minOf", "kotlin-stdlib"}, xs="kotlin/comparisons/ComparisonsKt")
class ComparisonsKt___ComparisonsKt
extends ComparisonsKt___ComparisonsJvmKt {
    @SinceKotlin(version="1.1")
    public static final <T> T maxOf(T a2, T b2, T c, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return ComparisonsKt.maxOf(a2, ComparisonsKt.maxOf(b2, c, comparator), comparator);
    }

    @SinceKotlin(version="1.1")
    public static final <T> T maxOf(T a2, T b2, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return comparator.compare(a2, b2) >= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.4")
    public static final <T> T maxOf(T a2, @NotNull T[] other, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        T max = a2;
        for (T e : other) {
            if (comparator.compare(max, e) >= 0) continue;
            max = e;
        }
        return max;
    }

    @SinceKotlin(version="1.1")
    public static final <T> T minOf(T a2, T b2, T c, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return ComparisonsKt.minOf(a2, ComparisonsKt.minOf(b2, c, comparator), comparator);
    }

    @SinceKotlin(version="1.1")
    public static final <T> T minOf(T a2, T b2, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return comparator.compare(a2, b2) <= 0 ? a2 : b2;
    }

    @SinceKotlin(version="1.4")
    public static final <T> T minOf(T a2, @NotNull T[] other, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        T min = a2;
        for (T e : other) {
            if (comparator.compare(min, e) <= 0) continue;
            min = e;
        }
        return min;
    }
}

