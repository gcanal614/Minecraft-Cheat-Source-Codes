/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CollectionsKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <K> Map<K, Integer> mapToIndex(@NotNull Iterable<? extends K> $this$mapToIndex) {
        Intrinsics.checkNotNullParameter($this$mapToIndex, "$this$mapToIndex");
        LinkedHashMap map2 = new LinkedHashMap();
        boolean bl = false;
        for (K k : $this$mapToIndex) {
            void index;
            ((Map)map2).put(k, (int)index);
            ++index;
        }
        return map2;
    }

    public static final <T> void addIfNotNull(@NotNull Collection<T> $this$addIfNotNull, @Nullable T t) {
        Intrinsics.checkNotNullParameter($this$addIfNotNull, "$this$addIfNotNull");
        if (t != null) {
            $this$addIfNotNull.add(t);
        }
    }

    @NotNull
    public static final <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap(CollectionsKt.capacity(expectedSize));
    }

    @NotNull
    public static final <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
        return new HashSet(CollectionsKt.capacity(expectedSize));
    }

    @NotNull
    public static final <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int expectedSize) {
        return new LinkedHashSet(CollectionsKt.capacity(expectedSize));
    }

    private static final int capacity(int expectedSize) {
        return expectedSize < 3 ? 3 : expectedSize + expectedSize / 3 + 1;
    }

    @NotNull
    public static final <T> List<T> compact(@NotNull ArrayList<T> $this$compact) {
        List list;
        Intrinsics.checkNotNullParameter($this$compact, "$this$compact");
        switch ($this$compact.size()) {
            case 0: {
                list = kotlin.collections.CollectionsKt.emptyList();
                break;
            }
            case 1: {
                list = kotlin.collections.CollectionsKt.listOf(kotlin.collections.CollectionsKt.first((List)$this$compact));
                break;
            }
            default: {
                ArrayList<T> arrayList = $this$compact;
                boolean bl = false;
                boolean bl2 = false;
                ArrayList<T> $this$apply = arrayList;
                boolean bl3 = false;
                $this$apply.trimToSize();
                list = arrayList;
            }
        }
        return list;
    }
}

