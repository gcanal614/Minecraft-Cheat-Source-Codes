/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.NotNullTypeVariable;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class NotNullTypeParameter
extends DelegatingSimpleType
implements NotNullTypeVariable {
    @NotNull
    private final SimpleType delegate;

    @Override
    public boolean isTypeVariable() {
        return true;
    }

    @Override
    @NotNull
    public KotlinType substitutionResult(@NotNull KotlinType replacement) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        UnwrappedType unwrappedType2 = replacement.unwrap();
        if (!TypeUtils.isNullableType(unwrappedType2) && !TypeUtilsKt.isTypeParameter(unwrappedType2)) {
            return unwrappedType2;
        }
        UnwrappedType unwrappedType3 = unwrappedType2;
        if (unwrappedType3 instanceof SimpleType) {
            unwrappedType = this.prepareReplacement((SimpleType)unwrappedType2);
        } else if (unwrappedType3 instanceof FlexibleType) {
            unwrappedType = TypeWithEnhancementKt.wrapEnhancement(KotlinTypeFactory.flexibleType(this.prepareReplacement(((FlexibleType)unwrappedType2).getLowerBound()), this.prepareReplacement(((FlexibleType)unwrappedType2).getUpperBound())), TypeWithEnhancementKt.getEnhancement(unwrappedType2));
        } else {
            String string = "Incorrect type: " + unwrappedType2;
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return unwrappedType;
    }

    @Override
    public boolean isMarkedNullable() {
        return false;
    }

    private final SimpleType prepareReplacement(SimpleType $this$prepareReplacement) {
        SimpleType result2 = $this$prepareReplacement.makeNullableAsSpecified(false);
        if (!TypeUtilsKt.isTypeParameter($this$prepareReplacement)) {
            return result2;
        }
        return new NotNullTypeParameter(result2);
    }

    @Override
    @NotNull
    public NotNullTypeParameter replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new NotNullTypeParameter(this.getDelegate().replaceAnnotations(newAnnotations));
    }

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability ? this.getDelegate().makeNullableAsSpecified(true) : (SimpleType)this;
    }

    @Override
    @NotNull
    public NotNullTypeParameter replaceDelegate(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new NotNullTypeParameter(delegate);
    }

    @Override
    @NotNull
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    public NotNullTypeParameter(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }
}

