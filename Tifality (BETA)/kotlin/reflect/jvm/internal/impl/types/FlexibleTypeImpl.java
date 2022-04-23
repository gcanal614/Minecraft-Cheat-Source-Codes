/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.CustomTypeVariable;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class FlexibleTypeImpl
extends FlexibleType
implements CustomTypeVariable {
    private boolean assertionsDone;
    @JvmField
    public static boolean RUN_SLOW_ASSERTIONS;
    public static final Companion Companion;

    private final void runAssertions() {
        if (!RUN_SLOW_ASSERTIONS || this.assertionsDone) {
            return;
        }
        this.assertionsDone = true;
        boolean bl = !FlexibleTypesKt.isFlexible(this.getLowerBound());
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-FlexibleTypeImpl$runAssertions$52 = false;
            String $i$a$-assert-FlexibleTypeImpl$runAssertions$52 = "Lower bound of a flexible type can not be flexible: " + this.getLowerBound();
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-FlexibleTypeImpl$runAssertions$52));
        }
        bl = !FlexibleTypesKt.isFlexible(this.getUpperBound());
        bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-FlexibleTypeImpl$runAssertions$62 = false;
            String $i$a$-assert-FlexibleTypeImpl$runAssertions$62 = "Upper bound of a flexible type can not be flexible: " + this.getUpperBound();
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-FlexibleTypeImpl$runAssertions$62));
        }
        bl = Intrinsics.areEqual(this.getLowerBound(), this.getUpperBound()) ^ true;
        bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-FlexibleTypeImpl$runAssertions$72 = false;
            String $i$a$-assert-FlexibleTypeImpl$runAssertions$72 = "Lower and upper bounds are equal: " + this.getLowerBound() + " == " + this.getUpperBound();
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-FlexibleTypeImpl$runAssertions$72));
        }
        bl = KotlinTypeChecker.DEFAULT.isSubtypeOf(this.getLowerBound(), this.getUpperBound());
        bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Lower bound " + this.getLowerBound() + " of a flexible type must be a subtype of the upper bound " + this.getUpperBound();
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
    }

    @Override
    @NotNull
    public SimpleType getDelegate() {
        this.runAssertions();
        return this.getLowerBound();
    }

    @Override
    public boolean isTypeVariable() {
        return this.getLowerBound().getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor && Intrinsics.areEqual(this.getLowerBound().getConstructor(), this.getUpperBound().getConstructor());
    }

    @Override
    @NotNull
    public KotlinType substitutionResult(@NotNull KotlinType replacement) {
        UnwrappedType unwrappedType;
        UnwrappedType unwrapped;
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        UnwrappedType unwrappedType2 = unwrapped = replacement.unwrap();
        if (unwrappedType2 instanceof FlexibleType) {
            unwrappedType = unwrapped;
        } else if (unwrappedType2 instanceof SimpleType) {
            unwrappedType = KotlinTypeFactory.flexibleType((SimpleType)unwrapped, ((SimpleType)unwrapped).makeNullableAsSpecified(true));
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(unwrappedType, unwrapped);
    }

    @Override
    @NotNull
    public UnwrappedType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return KotlinTypeFactory.flexibleType(this.getLowerBound().replaceAnnotations(newAnnotations), this.getUpperBound().replaceAnnotations(newAnnotations));
    }

    @Override
    @NotNull
    public String render(@NotNull DescriptorRenderer renderer, @NotNull DescriptorRendererOptions options) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        if (options.getDebugMode()) {
            return '(' + renderer.renderType(this.getLowerBound()) + ".." + renderer.renderType(this.getUpperBound()) + ')';
        }
        return renderer.renderFlexibleType(renderer.renderType(this.getLowerBound()), renderer.renderType(this.getUpperBound()), TypeUtilsKt.getBuiltIns(this));
    }

    @Override
    @NotNull
    public UnwrappedType makeNullableAsSpecified(boolean newNullability) {
        return KotlinTypeFactory.flexibleType(this.getLowerBound().makeNullableAsSpecified(newNullability), this.getUpperBound().makeNullableAsSpecified(newNullability));
    }

    @Override
    @NotNull
    public FlexibleType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinType = kotlinTypeRefiner.refineType(this.getLowerBound());
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        KotlinType kotlinType2 = kotlinTypeRefiner.refineType(this.getUpperBound());
        if (kotlinType2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new FlexibleTypeImpl((SimpleType)kotlinType, (SimpleType)kotlinType2);
    }

    public FlexibleTypeImpl(@NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        super(lowerBound, upperBound);
    }

    static {
        Companion = new Companion(null);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

