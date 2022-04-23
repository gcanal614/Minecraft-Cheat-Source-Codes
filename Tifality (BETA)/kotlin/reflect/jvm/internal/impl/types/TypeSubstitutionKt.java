/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

public final class TypeSubstitutionKt {
    @JvmOverloads
    @NotNull
    public static final KotlinType replace(@NotNull KotlinType $this$replace, @NotNull List<? extends TypeProjection> newArguments, @NotNull Annotations newAnnotations) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter($this$replace, "$this$replace");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        if ((newArguments.isEmpty() || newArguments == $this$replace.getArguments()) && newAnnotations == $this$replace.getAnnotations()) {
            return $this$replace;
        }
        UnwrappedType unwrapped = $this$replace.unwrap();
        UnwrappedType unwrappedType2 = unwrapped;
        if (unwrappedType2 instanceof FlexibleType) {
            unwrappedType = KotlinTypeFactory.flexibleType(TypeSubstitutionKt.replace(((FlexibleType)unwrapped).getLowerBound(), newArguments, newAnnotations), TypeSubstitutionKt.replace(((FlexibleType)unwrapped).getUpperBound(), newArguments, newAnnotations));
        } else if (unwrappedType2 instanceof SimpleType) {
            unwrappedType = TypeSubstitutionKt.replace((SimpleType)unwrapped, newArguments, newAnnotations);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return unwrappedType;
    }

    public static /* synthetic */ KotlinType replace$default(KotlinType kotlinType, List list, Annotations annotations2, int n, Object object) {
        if ((n & 1) != 0) {
            list = kotlinType.getArguments();
        }
        if ((n & 2) != 0) {
            annotations2 = kotlinType.getAnnotations();
        }
        return TypeSubstitutionKt.replace(kotlinType, list, annotations2);
    }

    @JvmOverloads
    @NotNull
    public static final SimpleType replace(@NotNull SimpleType $this$replace, @NotNull List<? extends TypeProjection> newArguments, @NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter($this$replace, "$this$replace");
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        if (newArguments.isEmpty() && newAnnotations == $this$replace.getAnnotations()) {
            return $this$replace;
        }
        if (newArguments.isEmpty()) {
            return $this$replace.replaceAnnotations(newAnnotations);
        }
        return KotlinTypeFactory.simpleType$default(newAnnotations, $this$replace.getConstructor(), newArguments, $this$replace.isMarkedNullable(), null, 16, null);
    }

    public static /* synthetic */ SimpleType replace$default(SimpleType simpleType2, List list, Annotations annotations2, int n, Object object) {
        if ((n & 1) != 0) {
            list = simpleType2.getArguments();
        }
        if ((n & 2) != 0) {
            annotations2 = simpleType2.getAnnotations();
        }
        return TypeSubstitutionKt.replace(simpleType2, list, annotations2);
    }

    @NotNull
    public static final SimpleType asSimpleType(@NotNull KotlinType $this$asSimpleType) {
        Intrinsics.checkNotNullParameter($this$asSimpleType, "$this$asSimpleType");
        UnwrappedType unwrappedType = $this$asSimpleType.unwrap();
        if (!(unwrappedType instanceof SimpleType)) {
            unwrappedType = null;
        }
        SimpleType simpleType2 = (SimpleType)unwrappedType;
        if (simpleType2 == null) {
            String string = "This is should be simple type: " + $this$asSimpleType;
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return simpleType2;
    }
}

