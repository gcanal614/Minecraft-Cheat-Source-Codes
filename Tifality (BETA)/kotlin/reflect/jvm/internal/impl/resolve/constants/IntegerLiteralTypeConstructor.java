/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor$Companion$WhenMappings;
import kotlin.reflect.jvm.internal.impl.resolve.constants.PrimitiveTypeUtilKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IntegerLiteralTypeConstructor
implements TypeConstructor {
    private final long value;
    private final ModuleDescriptor module;
    @NotNull
    private final Set<KotlinType> possibleTypes;
    private final SimpleType type = KotlinTypeFactory.integerLiteralType(Annotations.Companion.getEMPTY(), this, false);
    private final Lazy supertypes$delegate = LazyKt.lazy((Function0)new Function0<List<SimpleType>>(this){
        final /* synthetic */ IntegerLiteralTypeConstructor this$0;

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final List<SimpleType> invoke() {
            void var1_1;
            SimpleType[] simpleTypeArray = new SimpleType[1];
            ClassDescriptor classDescriptor = this.this$0.getBuiltIns().getComparable();
            Intrinsics.checkNotNullExpressionValue(classDescriptor, "builtIns.comparable");
            SimpleType simpleType2 = classDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(simpleType2, "builtIns.comparable.defaultType");
            simpleTypeArray[0] = TypeSubstitutionKt.replace$default(simpleType2, CollectionsKt.listOf(new TypeProjectionImpl(Variance.IN_VARIANCE, IntegerLiteralTypeConstructor.access$getType$p(this.this$0))), null, 2, null);
            List<SimpleType> result2 = CollectionsKt.mutableListOf(simpleTypeArray);
            if (!IntegerLiteralTypeConstructor.access$isContainsOnlyUnsignedTypes(this.this$0)) {
                Collection collection = result2;
                SimpleType simpleType3 = this.this$0.getBuiltIns().getNumberType();
                boolean bl = false;
                collection.add(simpleType3);
            }
            return var1_1;
        }
        {
            this.this$0 = integerLiteralTypeConstructor;
            super(0);
        }
    });
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final Set<KotlinType> getPossibleTypes() {
        return this.possibleTypes;
    }

    private final boolean isContainsOnlyUnsignedTypes() {
        boolean bl;
        block3: {
            Iterable $this$all$iv = PrimitiveTypeUtilKt.getAllSignedLiteralTypes(this.module);
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    KotlinType it = (KotlinType)element$iv;
                    boolean bl2 = false;
                    if (!this.possibleTypes.contains(it)) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
        }
        return bl;
    }

    private final List<KotlinType> getSupertypes() {
        Lazy lazy = this.supertypes$delegate;
        IntegerLiteralTypeConstructor integerLiteralTypeConstructor = this;
        Object var3_3 = null;
        boolean bl = false;
        return (List)lazy.getValue();
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Collection<KotlinType> getSupertypes() {
        return this.getSupertypes();
    }

    @Override
    public boolean isDenotable() {
        return false;
    }

    @Override
    @Nullable
    public ClassifierDescriptor getDeclarationDescriptor() {
        return null;
    }

    @Override
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        return this.module.getBuiltIns();
    }

    @Override
    @NotNull
    public TypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @NotNull
    public String toString() {
        return "IntegerLiteralType" + this.valueToString();
    }

    public final boolean checkConstructor(@NotNull TypeConstructor constructor) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Iterable $this$any$iv = this.possibleTypes;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    KotlinType it = (KotlinType)element$iv;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual(it.getConstructor(), constructor)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    private final String valueToString() {
        return '[' + CollectionsKt.joinToString$default(this.possibleTypes, ",", null, null, 0, null, valueToString.1.INSTANCE, 30, null) + ']';
    }

    private IntegerLiteralTypeConstructor(long value, ModuleDescriptor module, Set<? extends KotlinType> possibleTypes) {
        this.value = value;
        this.module = module;
        this.possibleTypes = possibleTypes;
    }

    public static final /* synthetic */ SimpleType access$getType$p(IntegerLiteralTypeConstructor $this) {
        return $this.type;
    }

    public static final /* synthetic */ boolean access$isContainsOnlyUnsignedTypes(IntegerLiteralTypeConstructor $this) {
        return $this.isContainsOnlyUnsignedTypes();
    }

    public /* synthetic */ IntegerLiteralTypeConstructor(long value, ModuleDescriptor module, Set possibleTypes, DefaultConstructorMarker $constructor_marker) {
        this(value, module, possibleTypes);
    }

    public static final class Companion {
        @Nullable
        public final SimpleType findIntersectionType(@NotNull Collection<? extends SimpleType> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return this.findCommonSuperTypeOrIntersectionType(types, Mode.INTERSECTION_TYPE);
        }

        /*
         * WARNING - void declaration
         */
        private final SimpleType findCommonSuperTypeOrIntersectionType(Collection<? extends SimpleType> types, Mode mode) {
            if (types.isEmpty()) {
                return null;
            }
            Iterable $this$reduce$iv = types;
            boolean $i$f$reduce = false;
            Iterator iterator$iv = $this$reduce$iv.iterator();
            if (!iterator$iv.hasNext()) {
                throw (Throwable)new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object accumulator$iv = iterator$iv.next();
            while (iterator$iv.hasNext()) {
                void right;
                SimpleType simpleType2 = (SimpleType)iterator$iv.next();
                SimpleType left = (SimpleType)accumulator$iv;
                boolean bl = false;
                accumulator$iv = Companion.fold(left, (SimpleType)right, mode);
            }
            return (SimpleType)accumulator$iv;
        }

        private final SimpleType fold(SimpleType left, SimpleType right, Mode mode) {
            if (left == null || right == null) {
                return null;
            }
            TypeConstructor leftConstructor = left.getConstructor();
            TypeConstructor rightConstructor = right.getConstructor();
            return leftConstructor instanceof IntegerLiteralTypeConstructor && rightConstructor instanceof IntegerLiteralTypeConstructor ? this.fold((IntegerLiteralTypeConstructor)leftConstructor, (IntegerLiteralTypeConstructor)rightConstructor, mode) : (leftConstructor instanceof IntegerLiteralTypeConstructor ? this.fold((IntegerLiteralTypeConstructor)leftConstructor, right) : (rightConstructor instanceof IntegerLiteralTypeConstructor ? this.fold((IntegerLiteralTypeConstructor)rightConstructor, left) : null));
        }

        private final SimpleType fold(IntegerLiteralTypeConstructor left, IntegerLiteralTypeConstructor right, Mode mode) {
            Set set;
            switch (IntegerLiteralTypeConstructor$Companion$WhenMappings.$EnumSwitchMapping$0[mode.ordinal()]) {
                case 1: {
                    set = CollectionsKt.intersect((Iterable)left.getPossibleTypes(), (Iterable)right.getPossibleTypes());
                    break;
                }
                case 2: {
                    set = CollectionsKt.union((Iterable)left.getPossibleTypes(), (Iterable)right.getPossibleTypes());
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            Set possibleTypes = set;
            IntegerLiteralTypeConstructor constructor = new IntegerLiteralTypeConstructor(left.value, left.module, possibleTypes, null);
            return KotlinTypeFactory.integerLiteralType(Annotations.Companion.getEMPTY(), constructor, false);
        }

        private final SimpleType fold(IntegerLiteralTypeConstructor left, SimpleType right) {
            return left.getPossibleTypes().contains(right) ? right : null;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private static final class Mode
        extends Enum<Mode> {
            public static final /* enum */ Mode COMMON_SUPER_TYPE;
            public static final /* enum */ Mode INTERSECTION_TYPE;
            private static final /* synthetic */ Mode[] $VALUES;

            static {
                Mode[] modeArray = new Mode[2];
                Mode[] modeArray2 = modeArray;
                modeArray[0] = COMMON_SUPER_TYPE = new Mode();
                modeArray[1] = INTERSECTION_TYPE = new Mode();
                $VALUES = modeArray;
            }

            public static Mode[] values() {
                return (Mode[])$VALUES.clone();
            }

            public static Mode valueOf(String string) {
                return Enum.valueOf(Mode.class, string);
            }
        }
    }
}

