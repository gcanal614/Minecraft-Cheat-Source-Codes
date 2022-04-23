/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.full;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0014\u0010\u0004\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0014\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001H\u0007\u00a8\u0006\u0007"}, d2={"isSubtypeOf", "", "Lkotlin/reflect/KType;", "other", "isSupertypeOf", "withNullability", "nullable", "kotlin-reflection"})
@JvmName(name="KTypes")
public final class KTypes {
    @SinceKotlin(version="1.1")
    @NotNull
    public static final KType withNullability(@NotNull KType $this$withNullability, boolean nullable) {
        Intrinsics.checkNotNullParameter($this$withNullability, "$this$withNullability");
        return ((KTypeImpl)$this$withNullability).makeNullableAsSpecified$kotlin_reflection(nullable);
    }

    @SinceKotlin(version="1.1")
    public static final boolean isSubtypeOf(@NotNull KType $this$isSubtypeOf, @NotNull KType other) {
        Intrinsics.checkNotNullParameter($this$isSubtypeOf, "$this$isSubtypeOf");
        Intrinsics.checkNotNullParameter(other, "other");
        return TypeUtilsKt.isSubtypeOf(((KTypeImpl)$this$isSubtypeOf).getType(), ((KTypeImpl)other).getType());
    }

    @SinceKotlin(version="1.1")
    public static final boolean isSupertypeOf(@NotNull KType $this$isSupertypeOf, @NotNull KType other) {
        Intrinsics.checkNotNullParameter($this$isSupertypeOf, "$this$isSupertypeOf");
        Intrinsics.checkNotNullParameter(other, "other");
        return KTypes.isSubtypeOf(other, $this$isSupertypeOf);
    }
}

