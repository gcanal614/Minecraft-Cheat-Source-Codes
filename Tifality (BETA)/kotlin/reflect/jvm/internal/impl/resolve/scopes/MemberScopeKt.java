/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MemberScopeKt {
    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final Set<Name> flatMapClassifierNamesOrNull(@NotNull Iterable<? extends MemberScope> $this$flatMapClassifierNamesOrNull) {
        Object v0;
        block2: {
            void var2_3;
            void $this$flatMapToNullable$iv;
            Intrinsics.checkNotNullParameter($this$flatMapClassifierNamesOrNull, "$this$flatMapClassifierNamesOrNull");
            Iterable<? extends MemberScope> iterable = $this$flatMapClassifierNamesOrNull;
            boolean bl = false;
            Collection destination$iv = new HashSet();
            boolean $i$f$flatMapToNullable = false;
            for (Object element$iv : $this$flatMapToNullable$iv) {
                Iterable list$iv;
                MemberScope p1 = (MemberScope)element$iv;
                boolean bl2 = false;
                if ((Iterable)p1.getClassifierNames() == null) {
                    v0 = null;
                    break block2;
                }
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            v0 = var2_3;
        }
        return v0;
    }
}

