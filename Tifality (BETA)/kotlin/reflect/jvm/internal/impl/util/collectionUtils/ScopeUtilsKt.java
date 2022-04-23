/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.util.collectionUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ScopeUtilsKt {
    @Nullable
    public static final <T> Collection<T> concat(@Nullable Collection<? extends T> $this$concat, @NotNull Collection<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        if (collection.isEmpty()) {
            return $this$concat;
        }
        if ($this$concat == null) {
            return collection;
        }
        if ($this$concat instanceof LinkedHashSet) {
            ((LinkedHashSet)$this$concat).addAll(collection);
            return $this$concat;
        }
        LinkedHashSet<T> result2 = new LinkedHashSet<T>($this$concat);
        result2.addAll(collection);
        return result2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final SmartList<MemberScope> listOfNonEmptyScopes(@NotNull Iterable<? extends MemberScope> scopes) {
        void var2_2;
        void $this$filterTo$iv;
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Iterable<? extends MemberScope> iterable = scopes;
        Collection destination$iv = new SmartList();
        boolean $i$f$filterTo = false;
        for (Object element$iv : $this$filterTo$iv) {
            MemberScope it = (MemberScope)element$iv;
            boolean bl = false;
            if (!(it != null && it != MemberScope.Empty.INSTANCE)) continue;
            destination$iv.add(element$iv);
        }
        return (SmartList)var2_2;
    }
}

