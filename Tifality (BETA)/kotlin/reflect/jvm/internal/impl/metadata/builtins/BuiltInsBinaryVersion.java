/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.builtins;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import org.jetbrains.annotations.NotNull;

public final class BuiltInsBinaryVersion
extends BinaryVersion {
    @JvmField
    @NotNull
    public static final BuiltInsBinaryVersion INSTANCE;
    @JvmField
    @NotNull
    public static final BuiltInsBinaryVersion INVALID_VERSION;
    public static final Companion Companion;

    public boolean isCompatible() {
        return this.isCompatibleTo(INSTANCE);
    }

    public BuiltInsBinaryVersion(int ... numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        super(Arrays.copyOf(numbers, numbers.length));
    }

    static {
        Companion = new Companion(null);
        INSTANCE = new BuiltInsBinaryVersion(1, 0, 7);
        INVALID_VERSION = new BuiltInsBinaryVersion(new int[0]);
    }

    public static final class Companion {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public final BuiltInsBinaryVersion readFrom(@NotNull InputStream stream) {
            Collection<Integer> collection;
            void $this$mapTo$iv$iv;
            Intrinsics.checkNotNullParameter(stream, "stream");
            DataInputStream dataInput = new DataInputStream(stream);
            int n = 1;
            Iterable $this$map$iv = new IntRange(n, dataInput.readInt());
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            Iterator iterator2 = $this$mapTo$iv$iv.iterator();
            while (iterator2.hasNext()) {
                int item$iv$iv;
                int n2 = item$iv$iv = ((IntIterator)iterator2).nextInt();
                collection = destination$iv$iv;
                boolean bl = false;
                Integer n3 = dataInput.readInt();
                collection.add(n3);
            }
            collection = (List)destination$iv$iv;
            int[] nArray = CollectionsKt.toIntArray(collection);
            int[] nArray2 = Arrays.copyOf(nArray, nArray.length);
            return new BuiltInsBinaryVersion(nArray2);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

