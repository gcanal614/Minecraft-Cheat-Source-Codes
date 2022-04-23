/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BinaryVersion {
    private final int major;
    private final int minor;
    private final int patch;
    @NotNull
    private final List<Integer> rest;
    private final int[] numbers;
    public static final Companion Companion = new Companion(null);

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    @NotNull
    public final int[] toArray() {
        return this.numbers;
    }

    protected final boolean isCompatibleTo(@NotNull BinaryVersion ourVersion) {
        Intrinsics.checkNotNullParameter(ourVersion, "ourVersion");
        return this.major == 0 ? ourVersion.major == 0 && this.minor == ourVersion.minor : this.major == ourVersion.major && this.minor <= ourVersion.minor;
    }

    public final boolean isAtLeast(@NotNull BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return this.isAtLeast(version.major, version.minor, version.patch);
    }

    public final boolean isAtLeast(int major, int minor, int patch) {
        if (this.major > major) {
            return true;
        }
        if (this.major < major) {
            return false;
        }
        if (this.minor > minor) {
            return true;
        }
        if (this.minor < minor) {
            return false;
        }
        return this.patch >= patch;
    }

    @NotNull
    public String toString() {
        List versions;
        int[] $this$takeWhile$iv = this.toArray();
        boolean $i$f$takeWhile = false;
        ArrayList<Integer> list$iv = new ArrayList<Integer>();
        int[] nArray = $this$takeWhile$iv;
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            int item$iv;
            int it = item$iv = nArray[i];
            boolean bl = false;
            if (!(it != -1)) break;
            list$iv.add(item$iv);
        }
        return (versions = (List)list$iv).isEmpty() ? "unknown" : CollectionsKt.joinToString$default(versions, ".", null, null, 0, null, null, 62, null);
    }

    public boolean equals(@Nullable Object other) {
        return other != null && Intrinsics.areEqual(this.getClass(), other.getClass()) && this.major == ((BinaryVersion)other).major && this.minor == ((BinaryVersion)other).minor && this.patch == ((BinaryVersion)other).patch && Intrinsics.areEqual(this.rest, ((BinaryVersion)other).rest);
    }

    public int hashCode() {
        int result2 = this.major;
        result2 += 31 * result2 + this.minor;
        result2 += 31 * result2 + this.patch;
        result2 += 31 * result2 + ((Object)this.rest).hashCode();
        return result2;
    }

    public BinaryVersion(int ... numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        this.numbers = numbers;
        Integer n = ArraysKt.getOrNull(this.numbers, 0);
        this.major = n != null ? n : -1;
        Integer n2 = ArraysKt.getOrNull(this.numbers, 1);
        this.minor = n2 != null ? n2 : -1;
        Integer n3 = ArraysKt.getOrNull(this.numbers, 2);
        this.patch = n3 != null ? n3 : -1;
        this.rest = this.numbers.length > 3 ? CollectionsKt.toList((Iterable)ArraysKt.asList(this.numbers).subList(3, this.numbers.length)) : CollectionsKt.emptyList();
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

