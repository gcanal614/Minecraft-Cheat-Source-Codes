/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeCheckerImpl;
import kotlin.reflect.jvm.internal.impl.types.checker.NullabilityChecker;
import org.jetbrains.annotations.NotNull;

public final class TypeIntersector {
    public static final TypeIntersector INSTANCE;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final SimpleType intersectTypes$descriptors(@NotNull List<? extends SimpleType> types) {
        void $this$mapTo$iv;
        SimpleType p2;
        void $this$fold$iv;
        Collection<SimpleType> collection;
        Collection collection2;
        Intrinsics.checkNotNullParameter(types, "types");
        boolean bl = types.size() > 1;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-TypeIntersector$intersectTypes$232 = false;
            String $i$a$-assert-TypeIntersector$intersectTypes$232 = "Size should be at least 2, but it is " + types.size();
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-TypeIntersector$intersectTypes$232));
        }
        ArrayList<SimpleType> inputTypes = new ArrayList<SimpleType>();
        for (SimpleType simpleType2 : types) {
            if (simpleType2.getConstructor() instanceof IntersectionTypeConstructor) {
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Collection<KotlinType> collection3 = simpleType2.getConstructor().getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collection3, "type.constructor.supertypes");
                Iterable iterable = collection3;
                collection2 = inputTypes;
                boolean $i$f$map = false;
                void var7_13 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    KotlinType kotlinType = (KotlinType)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl3 = false;
                    void v1 = it;
                    Intrinsics.checkNotNullExpressionValue(v1, "it");
                    SimpleType simpleType22 = FlexibleTypesKt.upperIfFlexible((KotlinType)v1);
                    boolean bl4 = false;
                    boolean bl5 = false;
                    SimpleType it2 = simpleType22;
                    boolean bl6 = false;
                    SimpleType simpleType3 = simpleType2.isMarkedNullable() ? it2.makeNullableAsSpecified(true) : it2;
                    collection.add(simpleType3);
                }
                collection = (List)destination$iv$iv;
                ((ArrayList)collection2).addAll((Collection)collection);
                continue;
            }
            inputTypes.add(simpleType2);
        }
        Iterable $i$a$-assert-TypeIntersector$intersectTypes$232 = inputTypes;
        Object initial$iv = ResultNullability.START;
        boolean $i$f$fold = false;
        ResultNullability accumulator$iv = initial$iv;
        for (Object element$iv : $this$fold$iv) {
            UnwrappedType unwrappedType = (UnwrappedType)element$iv;
            ResultNullability p1 = accumulator$iv;
            boolean bl7 = false;
            accumulator$iv = p1.combine(p2);
        }
        ResultNullability resultNullability = accumulator$iv;
        initial$iv = inputTypes;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$mapTo = false;
        for (Object item$iv : $this$mapTo$iv) {
            void it;
            p2 = (SimpleType)item$iv;
            collection2 = destination$iv;
            boolean bl8 = false;
            collection = resultNullability == ResultNullability.NOT_NULL ? SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull((SimpleType)(it instanceof NewCapturedType ? (SimpleType)SpecialTypesKt.withNotNullProjection((NewCapturedType)it) : it)) : it;
            collection2.add(collection);
        }
        LinkedHashSet correctNullability = (LinkedHashSet)destination$iv;
        return this.intersectTypesWithoutIntersectionType(correctNullability);
    }

    private final SimpleType intersectTypesWithoutIntersectionType(Set<? extends SimpleType> inputTypes) {
        Collection<SimpleType> filteredSuperAndEqualTypes2;
        Collection<SimpleType> filteredEqualTypes2;
        if (inputTypes.size() == 1) {
            return (SimpleType)CollectionsKt.single((Iterable)inputTypes);
        }
        Function0 errorMessage2 = new Function0<String>(inputTypes){
            final /* synthetic */ Set $inputTypes;

            @NotNull
            public final String invoke() {
                return "This collections cannot be empty! input types: " + CollectionsKt.joinToString$default(this.$inputTypes, null, null, null, 0, null, null, 63, null);
            }
            {
                this.$inputTypes = set;
                super(0);
            }
        };
        Collection<SimpleType> collection = filteredEqualTypes2 = this.filterTypes((Collection<? extends SimpleType>)inputTypes, (Function2<? super SimpleType, ? super SimpleType, Boolean>)new Function2<KotlinType, KotlinType, Boolean>(this){

            public final boolean invoke(@NotNull KotlinType p1, @NotNull KotlinType p2) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                Intrinsics.checkNotNullParameter(p2, "p2");
                return TypeIntersector.access$isStrictSupertype((TypeIntersector)this.receiver, p1, p2);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(TypeIntersector.class);
            }

            public final String getName() {
                return "isStrictSupertype";
            }

            public final String getSignature() {
                return "isStrictSupertype(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)Z";
            }
        });
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        bl = false;
        if (_Assertions.ENABLED && !bl2) {
            Object r = errorMessage2.invoke();
            throw (Throwable)((Object)new AssertionError(r));
        }
        SimpleType simpleType2 = IntegerLiteralTypeConstructor.Companion.findIntersectionType(filteredEqualTypes2);
        if (simpleType2 != null) {
            SimpleType simpleType3 = simpleType2;
            bl = false;
            boolean bl3 = false;
            SimpleType it = simpleType3;
            boolean bl4 = false;
            return it;
        }
        Collection<SimpleType> collection2 = filteredSuperAndEqualTypes2 = this.filterTypes(filteredEqualTypes2, (Function2<? super SimpleType, ? super SimpleType, Boolean>)new Function2<KotlinType, KotlinType, Boolean>(NewKotlinTypeChecker.Companion.getDefault()){

            public final boolean invoke(@NotNull KotlinType p1, @NotNull KotlinType p2) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                Intrinsics.checkNotNullParameter(p2, "p2");
                return ((NewKotlinTypeCheckerImpl)this.receiver).equalTypes(p1, p2);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(NewKotlinTypeCheckerImpl.class);
            }

            public final String getName() {
                return "equalTypes";
            }

            public final String getSignature() {
                return "equalTypes(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)Z";
            }
        });
        boolean bl5 = false;
        boolean bl6 = !collection2.isEmpty();
        bl5 = false;
        if (_Assertions.ENABLED && !bl6) {
            Object r = errorMessage2.invoke();
            throw (Throwable)((Object)new AssertionError(r));
        }
        if (filteredSuperAndEqualTypes2.size() < 2) {
            return (SimpleType)CollectionsKt.single((Iterable)filteredSuperAndEqualTypes2);
        }
        return new IntersectionTypeConstructor((Collection<? extends KotlinType>)inputTypes).createType();
    }

    /*
     * Unable to fully structure code
     */
    private final Collection<SimpleType> filterTypes(Collection<? extends SimpleType> inputTypes, Function2<? super SimpleType, ? super SimpleType, Boolean> predicate) {
        filteredTypes = new ArrayList<SimpleType>(inputTypes);
        v0 = filteredTypes.iterator();
        Intrinsics.checkNotNullExpressionValue(v0, "filteredTypes.iterator()");
        iterator = v0;
        while (iterator.hasNext()) {
            block4: {
                block5: {
                    upper = iterator.next();
                    $this$any$iv = filteredTypes;
                    $i$f$any = false;
                    if (!($this$any$iv instanceof Collection) || !((Collection)$this$any$iv).isEmpty()) break block5;
                    v1 = false;
                    break block4;
                }
                for (T element$iv : $this$any$iv) {
                    lower = (SimpleType)element$iv;
                    $i$a$-any-TypeIntersector$filterTypes$shouldFilter$1 = false;
                    if (lower == upper) ** GOTO lbl-1000
                    v2 = lower;
                    Intrinsics.checkNotNullExpressionValue(v2, "lower");
                    v3 = upper;
                    Intrinsics.checkNotNullExpressionValue(v3, "upper");
                    if (predicate.invoke(v2, v3).booleanValue()) {
                        v4 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v4 = false;
                    }
                    if (!v4) continue;
                    v1 = true;
                    break block4;
                }
                v1 = false;
            }
            if (!(shouldFilter = v1)) continue;
            iterator.remove();
        }
        return filteredTypes;
    }

    private final boolean isStrictSupertype(KotlinType subtype, KotlinType supertype) {
        NewKotlinTypeCheckerImpl newKotlinTypeCheckerImpl = NewKotlinTypeChecker.Companion.getDefault();
        boolean bl = false;
        boolean bl2 = false;
        NewKotlinTypeCheckerImpl $this$with = newKotlinTypeCheckerImpl;
        boolean bl3 = false;
        return $this$with.isSubtypeOf(subtype, supertype) && !$this$with.isSubtypeOf(supertype, subtype);
    }

    private TypeIntersector() {
    }

    static {
        TypeIntersector typeIntersector;
        INSTANCE = typeIntersector = new TypeIntersector();
    }

    public static final /* synthetic */ boolean access$isStrictSupertype(TypeIntersector $this, KotlinType subtype, KotlinType supertype) {
        return $this.isStrictSupertype(subtype, supertype);
    }

    private static final abstract class ResultNullability
    extends Enum<ResultNullability> {
        public static final /* enum */ ResultNullability START;
        public static final /* enum */ ResultNullability ACCEPT_NULL;
        public static final /* enum */ ResultNullability UNKNOWN;
        public static final /* enum */ ResultNullability NOT_NULL;
        private static final /* synthetic */ ResultNullability[] $VALUES;

        static {
            ResultNullability[] resultNullabilityArray = new ResultNullability[4];
            ResultNullability[] resultNullabilityArray2 = resultNullabilityArray;
            resultNullabilityArray[0] = START = new START("START", 0);
            resultNullabilityArray[1] = ACCEPT_NULL = new ACCEPT_NULL("ACCEPT_NULL", 1);
            resultNullabilityArray[2] = UNKNOWN = new UNKNOWN("UNKNOWN", 2);
            resultNullabilityArray[3] = NOT_NULL = new NOT_NULL("NOT_NULL", 3);
            $VALUES = resultNullabilityArray;
        }

        @NotNull
        public abstract ResultNullability combine(@NotNull UnwrappedType var1);

        @NotNull
        protected final ResultNullability getResultNullability(@NotNull UnwrappedType $this$resultNullability) {
            Intrinsics.checkNotNullParameter($this$resultNullability, "$this$resultNullability");
            return $this$resultNullability.isMarkedNullable() ? ACCEPT_NULL : (NullabilityChecker.INSTANCE.isSubtypeOfAny($this$resultNullability) ? NOT_NULL : UNKNOWN);
        }

        private ResultNullability() {
        }

        public /* synthetic */ ResultNullability(String $enum$name, int $enum$ordinal, DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static ResultNullability[] values() {
            return (ResultNullability[])$VALUES.clone();
        }

        public static ResultNullability valueOf(String string) {
            return Enum.valueOf(ResultNullability.class, string);
        }

        static final class START
        extends ResultNullability {
            @Override
            @NotNull
            public ResultNullability combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                return this.getResultNullability(nextType);
            }

            /*
             * WARNING - void declaration
             */
            START() {
                void var1_1;
            }
        }

        static final class ACCEPT_NULL
        extends ResultNullability {
            @Override
            @NotNull
            public ResultNullability combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                return this.getResultNullability(nextType);
            }

            /*
             * WARNING - void declaration
             */
            ACCEPT_NULL() {
                void var1_1;
            }
        }

        static final class UNKNOWN
        extends ResultNullability {
            @Override
            @NotNull
            public ResultNullability combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                ResultNullability resultNullability = this.getResultNullability(nextType);
                boolean bl = false;
                boolean bl2 = false;
                ResultNullability it = resultNullability;
                boolean bl3 = false;
                return it == ACCEPT_NULL ? (ResultNullability)this : it;
            }

            /*
             * WARNING - void declaration
             */
            UNKNOWN() {
                void var1_1;
            }
        }

        static final class NOT_NULL
        extends ResultNullability {
            @Override
            @NotNull
            public NOT_NULL combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                return this;
            }

            /*
             * WARNING - void declaration
             */
            NOT_NULL() {
                void var1_1;
            }
        }
    }
}

