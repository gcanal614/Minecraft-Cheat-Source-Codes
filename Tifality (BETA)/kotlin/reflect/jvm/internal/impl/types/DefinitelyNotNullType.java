/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin._Assertions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.CustomTypeVariable;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.NullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DefinitelyNotNullType
extends DelegatingSimpleType
implements CustomTypeVariable,
DefinitelyNotNullTypeMarker {
    @NotNull
    private final SimpleType original;
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    protected SimpleType getDelegate() {
        return this.original;
    }

    @Override
    public boolean isMarkedNullable() {
        return false;
    }

    @Override
    public boolean isTypeVariable() {
        return this.getDelegate().getConstructor() instanceof NewTypeVariableConstructor || this.getDelegate().getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor;
    }

    @Override
    @NotNull
    public KotlinType substitutionResult(@NotNull KotlinType replacement) {
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return SpecialTypesKt.makeDefinitelyNotNullOrNotNull(replacement.unwrap());
    }

    @Override
    @NotNull
    public DefinitelyNotNullType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new DefinitelyNotNullType(this.getDelegate().replaceAnnotations(newAnnotations));
    }

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability ? this.getDelegate().makeNullableAsSpecified(newNullability) : (SimpleType)this;
    }

    @Override
    @NotNull
    public String toString() {
        return this.getDelegate() + "!!";
    }

    @Override
    @NotNull
    public DefinitelyNotNullType replaceDelegate(@NotNull SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new DefinitelyNotNullType(delegate);
    }

    @NotNull
    public final SimpleType getOriginal() {
        return this.original;
    }

    private DefinitelyNotNullType(SimpleType original) {
        this.original = original;
    }

    public /* synthetic */ DefinitelyNotNullType(SimpleType original, DefaultConstructorMarker $constructor_marker) {
        this(original);
    }

    public static final class Companion {
        @Nullable
        public final DefinitelyNotNullType makeDefinitelyNotNull$descriptors(@NotNull UnwrappedType type2) {
            DefinitelyNotNullType definitelyNotNullType;
            Intrinsics.checkNotNullParameter(type2, "type");
            if (type2 instanceof DefinitelyNotNullType) {
                definitelyNotNullType = (DefinitelyNotNullType)type2;
            } else if (this.makesSenseToBeDefinitelyNotNull(type2)) {
                if (type2 instanceof FlexibleType) {
                    boolean bl = Intrinsics.areEqual(((FlexibleType)type2).getLowerBound().getConstructor(), ((FlexibleType)type2).getUpperBound().getConstructor());
                    boolean bl2 = false;
                    if (_Assertions.ENABLED && !bl) {
                        boolean bl3 = false;
                        String string = "DefinitelyNotNullType for flexible type (" + type2 + ") can be created only from type variable with the same constructor for bounds";
                        throw (Throwable)((Object)new AssertionError((Object)string));
                    }
                }
                definitelyNotNullType = new DefinitelyNotNullType(FlexibleTypesKt.lowerIfFlexible(type2), null);
            } else {
                definitelyNotNullType = null;
            }
            return definitelyNotNullType;
        }

        private final boolean makesSenseToBeDefinitelyNotNull(UnwrappedType type2) {
            return TypeUtilsKt.canHaveUndefinedNullability(type2) && !NullabilityChecker.INSTANCE.isSubtypeOfAny(type2);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

