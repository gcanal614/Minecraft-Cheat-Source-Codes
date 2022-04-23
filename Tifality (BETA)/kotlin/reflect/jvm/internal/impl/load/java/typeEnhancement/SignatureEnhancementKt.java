/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SignatureEnhancementKt {
    @NotNull
    public static final JavaTypeQualifiers createJavaTypeQualifiers(@Nullable NullabilityQualifier nullability, @Nullable MutabilityQualifier mutability, boolean forWarning, boolean isAnyNonNullTypeParameter) {
        if (!isAnyNonNullTypeParameter || nullability != NullabilityQualifier.NOT_NULL) {
            return new JavaTypeQualifiers(nullability, mutability, false, forWarning);
        }
        return new JavaTypeQualifiers(nullability, mutability, true, forWarning);
    }

    @Nullable
    public static final <T> T select(@NotNull Set<? extends T> $this$select, @NotNull T low, @NotNull T high, @Nullable T own, boolean isCovariant) {
        Object object;
        block8: {
            block7: {
                Intrinsics.checkNotNullParameter($this$select, "$this$select");
                Intrinsics.checkNotNullParameter(low, "low");
                Intrinsics.checkNotNullParameter(high, "high");
                if (isCovariant) {
                    T t;
                    T supertypeQualifier;
                    Object object2 = $this$select.contains(low) ? low : (supertypeQualifier = $this$select.contains(high) ? high : null);
                    if (Intrinsics.areEqual(supertypeQualifier, low) && Intrinsics.areEqual(own, high)) {
                        t = null;
                    } else {
                        t = own;
                        if (t == null) {
                            t = supertypeQualifier;
                        }
                    }
                    return t;
                }
                object = own;
                if (object == null) break block7;
                T t = object;
                boolean bl = false;
                boolean bl2 = false;
                T it = t;
                boolean bl3 = false;
                object = CollectionsKt.toSet((Iterable)SetsKt.plus($this$select, own));
                if (object != null) break block8;
            }
            object = $this$select;
        }
        Object effectiveSet = object;
        return CollectionsKt.singleOrNull((Iterable)effectiveSet);
    }

    @Nullable
    public static final NullabilityQualifier select(@NotNull Set<? extends NullabilityQualifier> $this$select, @Nullable NullabilityQualifier own, boolean isCovariant) {
        Intrinsics.checkNotNullParameter($this$select, "$this$select");
        return own == NullabilityQualifier.FORCE_FLEXIBILITY ? NullabilityQualifier.FORCE_FLEXIBILITY : SignatureEnhancementKt.select($this$select, NullabilityQualifier.NOT_NULL, NullabilityQualifier.NULLABLE, own, isCovariant);
    }
}

