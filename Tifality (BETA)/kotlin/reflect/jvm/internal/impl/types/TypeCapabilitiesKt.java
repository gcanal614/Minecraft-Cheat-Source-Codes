/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.CustomTypeVariable;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SubtypingRepresentatives;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeCapabilitiesKt {
    public static final boolean isCustomTypeVariable(@NotNull KotlinType $this$isCustomTypeVariable) {
        Intrinsics.checkNotNullParameter($this$isCustomTypeVariable, "$this$isCustomTypeVariable");
        UnwrappedType unwrappedType = $this$isCustomTypeVariable.unwrap();
        if (!(unwrappedType instanceof CustomTypeVariable)) {
            unwrappedType = null;
        }
        CustomTypeVariable customTypeVariable = (CustomTypeVariable)((Object)unwrappedType);
        return customTypeVariable != null ? customTypeVariable.isTypeVariable() : false;
    }

    @Nullable
    public static final CustomTypeVariable getCustomTypeVariable(@NotNull KotlinType $this$getCustomTypeVariable) {
        CustomTypeVariable customTypeVariable;
        Intrinsics.checkNotNullParameter($this$getCustomTypeVariable, "$this$getCustomTypeVariable");
        UnwrappedType unwrappedType = $this$getCustomTypeVariable.unwrap();
        if (!(unwrappedType instanceof CustomTypeVariable)) {
            unwrappedType = null;
        }
        CustomTypeVariable customTypeVariable2 = (CustomTypeVariable)((Object)unwrappedType);
        if (customTypeVariable2 != null) {
            CustomTypeVariable customTypeVariable3 = customTypeVariable2;
            boolean bl = false;
            boolean bl2 = false;
            CustomTypeVariable it = customTypeVariable3;
            boolean bl3 = false;
            customTypeVariable = it.isTypeVariable() ? it : null;
        } else {
            customTypeVariable = null;
        }
        return customTypeVariable;
    }

    @NotNull
    public static final KotlinType getSubtypeRepresentative(@NotNull KotlinType $this$getSubtypeRepresentative) {
        Object object;
        Intrinsics.checkNotNullParameter($this$getSubtypeRepresentative, "$this$getSubtypeRepresentative");
        UnwrappedType unwrappedType = $this$getSubtypeRepresentative.unwrap();
        if (!(unwrappedType instanceof SubtypingRepresentatives)) {
            unwrappedType = null;
        }
        if ((object = (SubtypingRepresentatives)((Object)unwrappedType)) == null || (object = object.getSubTypeRepresentative()) == null) {
            object = $this$getSubtypeRepresentative;
        }
        return object;
    }

    @NotNull
    public static final KotlinType getSupertypeRepresentative(@NotNull KotlinType $this$getSupertypeRepresentative) {
        Object object;
        Intrinsics.checkNotNullParameter($this$getSupertypeRepresentative, "$this$getSupertypeRepresentative");
        UnwrappedType unwrappedType = $this$getSupertypeRepresentative.unwrap();
        if (!(unwrappedType instanceof SubtypingRepresentatives)) {
            unwrappedType = null;
        }
        if ((object = (SubtypingRepresentatives)((Object)unwrappedType)) == null || (object = object.getSuperTypeRepresentative()) == null) {
            object = $this$getSupertypeRepresentative;
        }
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean sameTypeConstructors(@NotNull KotlinType first, @NotNull KotlinType second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        UnwrappedType unwrappedType = first.unwrap();
        if (!(unwrappedType instanceof SubtypingRepresentatives)) {
            unwrappedType = null;
        }
        SubtypingRepresentatives subtypingRepresentatives = (SubtypingRepresentatives)((Object)unwrappedType);
        if (subtypingRepresentatives != null ? subtypingRepresentatives.sameTypeConstructor(second) : false) return true;
        UnwrappedType unwrappedType2 = second.unwrap();
        if (!(unwrappedType2 instanceof SubtypingRepresentatives)) {
            unwrappedType2 = null;
        }
        SubtypingRepresentatives subtypingRepresentatives2 = (SubtypingRepresentatives)((Object)unwrappedType2);
        if (subtypingRepresentatives2 == null) return false;
        boolean bl = subtypingRepresentatives2.sameTypeConstructor(first);
        if (!bl) return false;
        return true;
    }
}

