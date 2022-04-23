/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTypeCheckerContext
implements TypeSystemContext {
    private int argumentsDepth;
    private boolean supertypesLocked;
    @Nullable
    private ArrayDeque<SimpleTypeMarker> supertypesDeque;
    @Nullable
    private Set<SimpleTypeMarker> supertypesSet;

    @NotNull
    public abstract SupertypesPolicy substitutionSupertypePolicy(@NotNull SimpleTypeMarker var1);

    public abstract boolean areEqualTypeConstructors(@NotNull TypeConstructorMarker var1, @NotNull TypeConstructorMarker var2);

    @NotNull
    public KotlinTypeMarker prepareType(@NotNull KotlinTypeMarker type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        return type2;
    }

    @NotNull
    public KotlinTypeMarker refineType(@NotNull KotlinTypeMarker type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        return type2;
    }

    public abstract boolean isErrorTypeEqualsToAnything();

    public abstract boolean isStubTypeEqualsToAnything();

    @NotNull
    public LowerCapturedTypePolicy getLowerCapturedTypePolicy(@NotNull SimpleTypeMarker subType, @NotNull CapturedTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
    }

    @Nullable
    public Boolean addSubtypeConstraint(@NotNull KotlinTypeMarker subType, @NotNull KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return null;
    }

    public static /* synthetic */ Boolean addSubtypeConstraint$default(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean bl, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addSubtypeConstraint");
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        return abstractTypeCheckerContext.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, bl);
    }

    @Nullable
    public final ArrayDeque<SimpleTypeMarker> getSupertypesDeque() {
        return this.supertypesDeque;
    }

    @Nullable
    public final Set<SimpleTypeMarker> getSupertypesSet() {
        return this.supertypesSet;
    }

    public final void initialize() {
        boolean bl = !this.supertypesLocked;
        boolean bl2 = false;
        boolean bl3 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl4 = false;
            String string = "Assertion failed";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.supertypesLocked = true;
        if (this.supertypesDeque == null) {
            this.supertypesDeque = new ArrayDeque(4);
        }
        if (this.supertypesSet == null) {
            this.supertypesSet = SmartSet.Companion.create();
        }
    }

    public final void clear() {
        ArrayDeque<SimpleTypeMarker> arrayDeque = this.supertypesDeque;
        Intrinsics.checkNotNull(arrayDeque);
        arrayDeque.clear();
        Set<SimpleTypeMarker> set = this.supertypesSet;
        Intrinsics.checkNotNull(set);
        set.clear();
        this.supertypesLocked = false;
    }

    public abstract boolean isAllowedTypeVariable(@NotNull KotlinTypeMarker var1);

    @Override
    @NotNull
    public TypeConstructorMarker typeConstructor(@NotNull KotlinTypeMarker $this$typeConstructor) {
        Intrinsics.checkNotNullParameter($this$typeConstructor, "$this$typeConstructor");
        return TypeSystemContext.DefaultImpls.typeConstructor(this, $this$typeConstructor);
    }

    @Nullable
    public TypeArgumentMarker getArgumentOrNull(@NotNull SimpleTypeMarker $this$getArgumentOrNull, int index) {
        Intrinsics.checkNotNullParameter($this$getArgumentOrNull, "$this$getArgumentOrNull");
        return TypeSystemContext.DefaultImpls.getArgumentOrNull(this, $this$getArgumentOrNull, index);
    }

    @Override
    @NotNull
    public SimpleTypeMarker lowerBoundIfFlexible(@NotNull KotlinTypeMarker $this$lowerBoundIfFlexible) {
        Intrinsics.checkNotNullParameter($this$lowerBoundIfFlexible, "$this$lowerBoundIfFlexible");
        return TypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, $this$lowerBoundIfFlexible);
    }

    @Override
    @NotNull
    public SimpleTypeMarker upperBoundIfFlexible(@NotNull KotlinTypeMarker $this$upperBoundIfFlexible) {
        Intrinsics.checkNotNullParameter($this$upperBoundIfFlexible, "$this$upperBoundIfFlexible");
        return TypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, $this$upperBoundIfFlexible);
    }

    public boolean isDynamic(@NotNull KotlinTypeMarker $this$isDynamic) {
        Intrinsics.checkNotNullParameter($this$isDynamic, "$this$isDynamic");
        return TypeSystemContext.DefaultImpls.isDynamic(this, $this$isDynamic);
    }

    public boolean isDefinitelyNotNullType(@NotNull KotlinTypeMarker $this$isDefinitelyNotNullType) {
        Intrinsics.checkNotNullParameter($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return TypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, $this$isDefinitelyNotNullType);
    }

    public boolean hasFlexibleNullability(@NotNull KotlinTypeMarker $this$hasFlexibleNullability) {
        Intrinsics.checkNotNullParameter($this$hasFlexibleNullability, "$this$hasFlexibleNullability");
        return TypeSystemContext.DefaultImpls.hasFlexibleNullability(this, $this$hasFlexibleNullability);
    }

    public boolean isNothing(@NotNull KotlinTypeMarker $this$isNothing) {
        Intrinsics.checkNotNullParameter($this$isNothing, "$this$isNothing");
        return TypeSystemContext.DefaultImpls.isNothing(this, $this$isNothing);
    }

    public boolean isClassType(@NotNull SimpleTypeMarker $this$isClassType) {
        Intrinsics.checkNotNullParameter($this$isClassType, "$this$isClassType");
        return TypeSystemContext.DefaultImpls.isClassType(this, $this$isClassType);
    }

    @Nullable
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(@NotNull SimpleTypeMarker $this$fastCorrespondingSupertypes, @NotNull TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter($this$fastCorrespondingSupertypes, "$this$fastCorrespondingSupertypes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return TypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, $this$fastCorrespondingSupertypes, constructor);
    }

    public boolean isIntegerLiteralType(@NotNull SimpleTypeMarker $this$isIntegerLiteralType) {
        Intrinsics.checkNotNullParameter($this$isIntegerLiteralType, "$this$isIntegerLiteralType");
        return TypeSystemContext.DefaultImpls.isIntegerLiteralType(this, $this$isIntegerLiteralType);
    }

    @Override
    @NotNull
    public TypeArgumentMarker get(@NotNull TypeArgumentListMarker $this$get, int index) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        return TypeSystemContext.DefaultImpls.get(this, $this$get, index);
    }

    @Override
    public int size(@NotNull TypeArgumentListMarker $this$size) {
        Intrinsics.checkNotNullParameter($this$size, "$this$size");
        return TypeSystemContext.DefaultImpls.size(this, $this$size);
    }

    @Override
    public boolean identicalArguments(@NotNull SimpleTypeMarker a2, @NotNull SimpleTypeMarker b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return TypeSystemContext.DefaultImpls.identicalArguments(this, a2, b2);
    }

    public static final /* synthetic */ int access$getArgumentsDepth$p(AbstractTypeCheckerContext $this) {
        return $this.argumentsDepth;
    }

    public static final /* synthetic */ void access$setArgumentsDepth$p(AbstractTypeCheckerContext $this, int n) {
        $this.argumentsDepth = n;
    }

    public static final class LowerCapturedTypePolicy
    extends Enum<LowerCapturedTypePolicy> {
        public static final /* enum */ LowerCapturedTypePolicy CHECK_ONLY_LOWER;
        public static final /* enum */ LowerCapturedTypePolicy CHECK_SUBTYPE_AND_LOWER;
        public static final /* enum */ LowerCapturedTypePolicy SKIP_LOWER;
        private static final /* synthetic */ LowerCapturedTypePolicy[] $VALUES;

        static {
            LowerCapturedTypePolicy[] lowerCapturedTypePolicyArray = new LowerCapturedTypePolicy[3];
            LowerCapturedTypePolicy[] lowerCapturedTypePolicyArray2 = lowerCapturedTypePolicyArray;
            lowerCapturedTypePolicyArray[0] = CHECK_ONLY_LOWER = new LowerCapturedTypePolicy();
            lowerCapturedTypePolicyArray[1] = CHECK_SUBTYPE_AND_LOWER = new LowerCapturedTypePolicy();
            lowerCapturedTypePolicyArray[2] = SKIP_LOWER = new LowerCapturedTypePolicy();
            $VALUES = lowerCapturedTypePolicyArray;
        }

        public static LowerCapturedTypePolicy[] values() {
            return (LowerCapturedTypePolicy[])$VALUES.clone();
        }

        public static LowerCapturedTypePolicy valueOf(String string) {
            return Enum.valueOf(LowerCapturedTypePolicy.class, string);
        }
    }

    public static abstract class SupertypesPolicy {
        @NotNull
        public abstract SimpleTypeMarker transformType(@NotNull AbstractTypeCheckerContext var1, @NotNull KotlinTypeMarker var2);

        private SupertypesPolicy() {
        }

        public /* synthetic */ SupertypesPolicy(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static final class None
        extends SupertypesPolicy {
            public static final None INSTANCE;

            @NotNull
            public Void transformType(@NotNull AbstractTypeCheckerContext context, @NotNull KotlinTypeMarker type2) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(type2, "type");
                throw (Throwable)new UnsupportedOperationException("Should not be called");
            }

            private None() {
                super(null);
            }

            static {
                None none;
                INSTANCE = none = new None();
            }
        }

        public static final class UpperIfFlexible
        extends SupertypesPolicy {
            public static final UpperIfFlexible INSTANCE;

            @Override
            @NotNull
            public SimpleTypeMarker transformType(@NotNull AbstractTypeCheckerContext context, @NotNull KotlinTypeMarker type2) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(type2, "type");
                boolean bl = false;
                boolean bl2 = false;
                AbstractTypeCheckerContext $this$with = context;
                boolean bl3 = false;
                return $this$with.upperBoundIfFlexible(type2);
            }

            private UpperIfFlexible() {
                super(null);
            }

            static {
                UpperIfFlexible upperIfFlexible;
                INSTANCE = upperIfFlexible = new UpperIfFlexible();
            }
        }

        public static final class LowerIfFlexible
        extends SupertypesPolicy {
            public static final LowerIfFlexible INSTANCE;

            @Override
            @NotNull
            public SimpleTypeMarker transformType(@NotNull AbstractTypeCheckerContext context, @NotNull KotlinTypeMarker type2) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(type2, "type");
                boolean bl = false;
                boolean bl2 = false;
                AbstractTypeCheckerContext $this$with = context;
                boolean bl3 = false;
                return $this$with.lowerBoundIfFlexible(type2);
            }

            private LowerIfFlexible() {
                super(null);
            }

            static {
                LowerIfFlexible lowerIfFlexible;
                INSTANCE = lowerIfFlexible = new LowerIfFlexible();
            }
        }

        public static abstract class DoCustomTransform
        extends SupertypesPolicy {
            public DoCustomTransform() {
                super(null);
            }
        }
    }
}

