/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptorKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.AnnotatedSimpleType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SimpleTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpander;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpansion;
import kotlin.reflect.jvm.internal.impl.types.TypeAliasExpansionReportStrategy;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinTypeFactory {
    @NotNull
    private static final Function1<KotlinTypeRefiner, SimpleType> EMPTY_REFINED_TYPE_FACTORY;
    public static final KotlinTypeFactory INSTANCE;

    private final MemberScope computeMemberScope(TypeConstructor constructor, List<? extends TypeProjection> arguments2, KotlinTypeRefiner kotlinTypeRefiner) {
        MemberScope memberScope2;
        ClassifierDescriptor descriptor2 = constructor.getDeclarationDescriptor();
        ClassifierDescriptor classifierDescriptor = descriptor2;
        if (classifierDescriptor instanceof TypeParameterDescriptor) {
            memberScope2 = descriptor2.getDefaultType().getMemberScope();
        } else if (classifierDescriptor instanceof ClassDescriptor) {
            KotlinTypeRefiner refinerToUse;
            KotlinTypeRefiner kotlinTypeRefiner2 = kotlinTypeRefiner;
            if (kotlinTypeRefiner2 == null) {
                kotlinTypeRefiner2 = refinerToUse = DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtilsKt.getModule(descriptor2));
            }
            memberScope2 = arguments2.isEmpty() ? ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible((ClassDescriptor)descriptor2, refinerToUse) : ModuleAwareClassDescriptorKt.getRefinedMemberScopeIfPossible((ClassDescriptor)descriptor2, TypeConstructorSubstitution.Companion.create(constructor, arguments2), refinerToUse);
        } else if (classifierDescriptor instanceof TypeAliasDescriptor) {
            MemberScope memberScope3 = ErrorUtils.createErrorScope("Scope for abbreviation: " + ((TypeAliasDescriptor)descriptor2).getName(), true);
            memberScope2 = memberScope3;
            Intrinsics.checkNotNullExpressionValue(memberScope3, "ErrorUtils.createErrorSc\u2026{descriptor.name}\", true)");
        } else {
            if (constructor instanceof IntersectionTypeConstructor) {
                return ((IntersectionTypeConstructor)constructor).createScopeForKotlinType();
            }
            throw (Throwable)new IllegalStateException("Unsupported classifier: " + descriptor2 + " for constructor: " + constructor);
        }
        return memberScope2;
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final SimpleType simpleType(@NotNull Annotations annotations2, @NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments2, boolean nullable, @Nullable KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        if (annotations2.isEmpty() && arguments2.isEmpty() && !nullable && constructor.getDeclarationDescriptor() != null) {
            ClassifierDescriptor classifierDescriptor = constructor.getDeclarationDescriptor();
            Intrinsics.checkNotNull(classifierDescriptor);
            Intrinsics.checkNotNullExpressionValue(classifierDescriptor, "constructor.declarationDescriptor!!");
            SimpleType simpleType2 = classifierDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(simpleType2, "constructor.declarationDescriptor!!.defaultType");
            return simpleType2;
        }
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(annotations2, constructor, arguments2, nullable, INSTANCE.computeMemberScope(constructor, arguments2, kotlinTypeRefiner), (Function1<? super KotlinTypeRefiner, ? extends SimpleType>)new Function1<KotlinTypeRefiner, SimpleType>(constructor, arguments2, annotations2, nullable){
            final /* synthetic */ TypeConstructor $constructor;
            final /* synthetic */ List $arguments;
            final /* synthetic */ Annotations $annotations;
            final /* synthetic */ boolean $nullable;

            @Nullable
            public final SimpleType invoke(@NotNull KotlinTypeRefiner refiner) {
                Intrinsics.checkNotNullParameter(refiner, "refiner");
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructor = KotlinTypeFactory.access$refineConstructor(KotlinTypeFactory.INSTANCE, this.$constructor, refiner, this.$arguments);
                if (expandedTypeOrRefinedConstructor == null) {
                    return null;
                }
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructor2 = expandedTypeOrRefinedConstructor;
                SimpleType simpleType2 = expandedTypeOrRefinedConstructor2.getExpandedType();
                if (simpleType2 != null) {
                    SimpleType simpleType3 = simpleType2;
                    boolean bl = false;
                    boolean bl2 = false;
                    SimpleType it = simpleType3;
                    boolean bl3 = false;
                    return it;
                }
                TypeConstructor typeConstructor2 = expandedTypeOrRefinedConstructor2.getRefinedConstructor();
                Intrinsics.checkNotNull(typeConstructor2);
                return KotlinTypeFactory.simpleType(this.$annotations, typeConstructor2, this.$arguments, this.$nullable, refiner);
            }
            {
                this.$constructor = typeConstructor2;
                this.$arguments = list;
                this.$annotations = annotations2;
                this.$nullable = bl;
                super(1);
            }
        });
    }

    public static /* synthetic */ SimpleType simpleType$default(Annotations annotations2, TypeConstructor typeConstructor2, List list, boolean bl, KotlinTypeRefiner kotlinTypeRefiner, int n, Object object) {
        if ((n & 0x10) != 0) {
            kotlinTypeRefiner = null;
        }
        return KotlinTypeFactory.simpleType(annotations2, typeConstructor2, list, bl, kotlinTypeRefiner);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType computeExpandedType(@NotNull TypeAliasDescriptor $this$computeExpandedType, @NotNull List<? extends TypeProjection> arguments2) {
        Intrinsics.checkNotNullParameter($this$computeExpandedType, "$this$computeExpandedType");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        return new TypeAliasExpander(TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false).expand(TypeAliasExpansion.Companion.create(null, $this$computeExpandedType, arguments2), Annotations.Companion.getEMPTY());
    }

    private final ExpandedTypeOrRefinedConstructor refineConstructor(TypeConstructor constructor, KotlinTypeRefiner kotlinTypeRefiner, List<? extends TypeProjection> arguments2) {
        ClassifierDescriptor classifierDescriptor;
        block5: {
            block4: {
                ClassifierDescriptor basicDescriptor = constructor.getDeclarationDescriptor();
                classifierDescriptor = basicDescriptor;
                if (classifierDescriptor == null) break block4;
                ClassifierDescriptor classifierDescriptor2 = classifierDescriptor;
                boolean bl = false;
                boolean bl2 = false;
                ClassifierDescriptor it = classifierDescriptor2;
                boolean bl3 = false;
                classifierDescriptor = kotlinTypeRefiner.refineDescriptor(it);
                if (classifierDescriptor != null) break block5;
            }
            return null;
        }
        ClassifierDescriptor descriptor2 = classifierDescriptor;
        if (descriptor2 instanceof TypeAliasDescriptor) {
            return new ExpandedTypeOrRefinedConstructor(KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor)descriptor2, arguments2), null);
        }
        TypeConstructor typeConstructor2 = descriptor2.getTypeConstructor().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstruct\u2026refine(kotlinTypeRefiner)");
        TypeConstructor refinedConstructor = typeConstructor2;
        return new ExpandedTypeOrRefinedConstructor(null, refinedConstructor);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(@NotNull Annotations annotations2, @NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments2, boolean nullable, @NotNull MemberScope memberScope2) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments2, nullable, memberScope2, (Function1<? super KotlinTypeRefiner, ? extends SimpleType>)new Function1<KotlinTypeRefiner, SimpleType>(constructor, arguments2, annotations2, nullable, memberScope2){
            final /* synthetic */ TypeConstructor $constructor;
            final /* synthetic */ List $arguments;
            final /* synthetic */ Annotations $annotations;
            final /* synthetic */ boolean $nullable;
            final /* synthetic */ MemberScope $memberScope;

            @Nullable
            public final SimpleType invoke(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructor = KotlinTypeFactory.access$refineConstructor(KotlinTypeFactory.INSTANCE, this.$constructor, kotlinTypeRefiner, this.$arguments);
                if (expandedTypeOrRefinedConstructor == null) {
                    return null;
                }
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructor2 = expandedTypeOrRefinedConstructor;
                SimpleType simpleType2 = expandedTypeOrRefinedConstructor2.getExpandedType();
                if (simpleType2 != null) {
                    SimpleType simpleType3 = simpleType2;
                    boolean bl = false;
                    boolean bl2 = false;
                    SimpleType it = simpleType3;
                    boolean bl3 = false;
                    return it;
                }
                TypeConstructor typeConstructor2 = expandedTypeOrRefinedConstructor2.getRefinedConstructor();
                Intrinsics.checkNotNull(typeConstructor2);
                return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(this.$annotations, typeConstructor2, this.$arguments, this.$nullable, this.$memberScope);
            }
            {
                this.$constructor = typeConstructor2;
                this.$arguments = list;
                this.$annotations = annotations2;
                this.$nullable = bl;
                this.$memberScope = memberScope2;
                super(1);
            }
        });
        boolean bl = false;
        boolean bl2 = false;
        SimpleTypeImpl it = simpleTypeImpl;
        boolean bl3 = false;
        return annotations2.isEmpty() ? (SimpleType)it : (SimpleType)new AnnotatedSimpleType(it, annotations2);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(@NotNull Annotations annotations2, @NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments2, boolean nullable, @NotNull MemberScope memberScope2, @NotNull Function1<? super KotlinTypeRefiner, ? extends SimpleType> refinedTypeFactory) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        Intrinsics.checkNotNullParameter(refinedTypeFactory, "refinedTypeFactory");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments2, nullable, memberScope2, refinedTypeFactory);
        boolean bl = false;
        boolean bl2 = false;
        SimpleTypeImpl it = simpleTypeImpl;
        boolean bl3 = false;
        return annotations2.isEmpty() ? (SimpleType)it : (SimpleType)new AnnotatedSimpleType(it, annotations2);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType simpleNotNullType(@NotNull Annotations annotations2, @NotNull ClassDescriptor descriptor2, @NotNull List<? extends TypeProjection> arguments2) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        TypeConstructor typeConstructor2 = descriptor2.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstructor");
        return KotlinTypeFactory.simpleType$default(annotations2, typeConstructor2, arguments2, false, null, 16, null);
    }

    @JvmStatic
    @NotNull
    public static final UnwrappedType flexibleType(@NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (Intrinsics.areEqual(lowerBound, upperBound)) {
            return lowerBound;
        }
        return new FlexibleTypeImpl(lowerBound, upperBound);
    }

    @JvmStatic
    @NotNull
    public static final SimpleType integerLiteralType(@NotNull Annotations annotations2, @NotNull IntegerLiteralTypeConstructor constructor, boolean nullable) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        TypeConstructor typeConstructor2 = constructor;
        List list = CollectionsKt.emptyList();
        MemberScope memberScope2 = ErrorUtils.createErrorScope("Scope for integer literal type", true);
        Intrinsics.checkNotNullExpressionValue(memberScope2, "ErrorUtils.createErrorSc\u2026eger literal type\", true)");
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(annotations2, typeConstructor2, list, nullable, memberScope2);
    }

    private KotlinTypeFactory() {
    }

    static {
        KotlinTypeFactory kotlinTypeFactory;
        INSTANCE = kotlinTypeFactory = new KotlinTypeFactory();
        EMPTY_REFINED_TYPE_FACTORY = EMPTY_REFINED_TYPE_FACTORY.1.INSTANCE;
    }

    public static final /* synthetic */ ExpandedTypeOrRefinedConstructor access$refineConstructor(KotlinTypeFactory $this, TypeConstructor constructor, KotlinTypeRefiner kotlinTypeRefiner, List arguments2) {
        return $this.refineConstructor(constructor, kotlinTypeRefiner, arguments2);
    }

    private static final class ExpandedTypeOrRefinedConstructor {
        @Nullable
        private final SimpleType expandedType;
        @Nullable
        private final TypeConstructor refinedConstructor;

        @Nullable
        public final SimpleType getExpandedType() {
            return this.expandedType;
        }

        @Nullable
        public final TypeConstructor getRefinedConstructor() {
            return this.refinedConstructor;
        }

        public ExpandedTypeOrRefinedConstructor(@Nullable SimpleType expandedType, @Nullable TypeConstructor refinedConstructor) {
            this.expandedType = expandedType;
            this.refinedConstructor = refinedConstructor;
        }
    }
}

