/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeFlexibility;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution$WhenMappings;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RawSubstitution
extends TypeSubstitution {
    private static final JavaTypeAttributes lowerTypeAttr;
    private static final JavaTypeAttributes upperTypeAttr;
    public static final RawSubstitution INSTANCE;

    @Override
    @NotNull
    public TypeProjectionImpl get(@NotNull KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return new TypeProjectionImpl(this.eraseType(key));
    }

    /*
     * WARNING - void declaration
     */
    private final KotlinType eraseType(KotlinType type2) {
        KotlinType kotlinType;
        ClassifierDescriptor declaration = type2.getConstructor().getDeclarationDescriptor();
        ClassifierDescriptor classifierDescriptor = declaration;
        if (classifierDescriptor instanceof TypeParameterDescriptor) {
            kotlinType = this.eraseType(JavaTypeResolverKt.getErasedUpperBound$default((TypeParameterDescriptor)declaration, null, null, 3, null));
        } else if (classifierDescriptor instanceof ClassDescriptor) {
            void upper;
            void lower;
            ClassifierDescriptor declarationForUpper = FlexibleTypesKt.upperIfFlexible(type2).getConstructor().getDeclarationDescriptor();
            boolean bl = declarationForUpper instanceof ClassDescriptor;
            boolean bl2 = false;
            boolean bl3 = false;
            if (!bl) {
                boolean bl4 = false;
                String string = "For some reason declaration for upper bound is not a class " + "but \"" + declarationForUpper + "\" while for lower it's \"" + declaration + '\"';
                throw (Throwable)new IllegalStateException(string.toString());
            }
            Object object = this.eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.lowerIfFlexible(type2), (ClassDescriptor)declaration, lowerTypeAttr);
            SimpleType simpleType2 = ((Pair)object).component1();
            boolean isRawL = ((Pair)object).component2();
            Pair<SimpleType, Boolean> pair = this.eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.upperIfFlexible(type2), (ClassDescriptor)declarationForUpper, upperTypeAttr);
            object = pair.component1();
            boolean isRawU = pair.component2();
            kotlinType = isRawL || isRawU ? (UnwrappedType)new RawTypeImpl((SimpleType)lower, (SimpleType)upper) : KotlinTypeFactory.flexibleType((SimpleType)lower, (SimpleType)upper);
        } else {
            String string = "Unexpected declaration kind: " + declaration;
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return kotlinType;
    }

    /*
     * WARNING - void declaration
     */
    private final Pair<SimpleType, Boolean> eraseInflexibleBasedOnClassDescriptor(SimpleType type2, ClassDescriptor declaration, JavaTypeAttributes attr) {
        Collection<TypeProjection> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        if (type2.getConstructor().getParameters().isEmpty()) {
            return TuplesKt.to(type2, false);
        }
        if (KotlinBuiltIns.isArray(type2)) {
            TypeProjection componentTypeProjection = type2.getArguments().get(0);
            Variance variance = componentTypeProjection.getProjectionKind();
            KotlinType kotlinType = componentTypeProjection.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "componentTypeProjection.type");
            List<TypeProjectionImpl> arguments2 = CollectionsKt.listOf(new TypeProjectionImpl(variance, this.eraseType(kotlinType)));
            return TuplesKt.to(KotlinTypeFactory.simpleType$default(type2.getAnnotations(), type2.getConstructor(), arguments2, type2.isMarkedNullable(), null, 16, null), false);
        }
        if (KotlinTypeKt.isError(type2)) {
            SimpleType simpleType2 = ErrorUtils.createErrorType("Raw error type: " + type2.getConstructor());
            Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026pe: ${type.constructor}\")");
            return TuplesKt.to(simpleType2, false);
        }
        MemberScope memberScope2 = declaration.getMemberScope(INSTANCE);
        Intrinsics.checkNotNullExpressionValue(memberScope2, "declaration.getMemberScope(RawSubstitution)");
        MemberScope memberScope3 = memberScope2;
        Annotations annotations2 = type2.getAnnotations();
        TypeConstructor typeConstructor2 = declaration.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "declaration.typeConstructor");
        TypeConstructor typeConstructor3 = declaration.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor3, "declaration.typeConstructor");
        List<TypeParameterDescriptor> list = typeConstructor3.getParameters();
        Intrinsics.checkNotNullExpressionValue(list, "declaration.typeConstructor.parameters");
        Iterable arguments2 = list;
        TypeConstructor typeConstructor4 = typeConstructor2;
        Annotations annotations3 = annotations2;
        boolean $i$f$map = false;
        void var7_11 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void parameter;
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            void v8 = parameter;
            Intrinsics.checkNotNullExpressionValue(v8, "parameter");
            TypeProjection typeProjection = RawSubstitution.computeProjection$default(INSTANCE, (TypeParameterDescriptor)v8, attr, null, 4, null);
            collection.add(typeProjection);
        }
        collection = (List)destination$iv$iv;
        return TuplesKt.to(KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(annotations3, typeConstructor4, (List<? extends TypeProjection>)collection, type2.isMarkedNullable(), memberScope3, (Function1<? super KotlinTypeRefiner, ? extends SimpleType>)new Function1<KotlinTypeRefiner, SimpleType>(declaration, type2, attr){
            final /* synthetic */ ClassDescriptor $declaration;
            final /* synthetic */ SimpleType $type;
            final /* synthetic */ JavaTypeAttributes $attr;

            @Nullable
            public final SimpleType invoke(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                Object object;
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                Object object2 = this.$declaration;
                if (!(object2 instanceof ClassDescriptor)) {
                    object2 = object = null;
                }
                if (object2 == null || (object = DescriptorUtilsKt.getClassId((ClassifierDescriptor)object)) == null) {
                    return null;
                }
                Object classId = object;
                ClassDescriptor classDescriptor = kotlinTypeRefiner.findClassAcrossModuleDependencies((ClassId)classId);
                if (classDescriptor == null) {
                    return null;
                }
                ClassDescriptor refinedClassDescriptor = classDescriptor;
                if (Intrinsics.areEqual(refinedClassDescriptor, this.$declaration)) {
                    return null;
                }
                return (SimpleType)RawSubstitution.access$eraseInflexibleBasedOnClassDescriptor(RawSubstitution.INSTANCE, this.$type, refinedClassDescriptor, this.$attr).getFirst();
            }
            {
                this.$declaration = classDescriptor;
                this.$type = simpleType2;
                this.$attr = javaTypeAttributes;
                super(1);
            }
        }), true);
    }

    @NotNull
    public final TypeProjection computeProjection(@NotNull TypeParameterDescriptor parameter, @NotNull JavaTypeAttributes attr, @NotNull KotlinType erasedUpperBound) {
        TypeProjection typeProjection;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(attr, "attr");
        Intrinsics.checkNotNullParameter(erasedUpperBound, "erasedUpperBound");
        switch (RawSubstitution$WhenMappings.$EnumSwitchMapping$0[attr.getFlexibility().ordinal()]) {
            case 1: {
                typeProjection = new TypeProjectionImpl(Variance.INVARIANT, erasedUpperBound);
                break;
            }
            case 2: 
            case 3: {
                if (!parameter.getVariance().getAllowsOutPosition()) {
                    typeProjection = new TypeProjectionImpl(Variance.INVARIANT, DescriptorUtilsKt.getBuiltIns(parameter).getNothingType());
                    break;
                }
                List<TypeParameterDescriptor> list = erasedUpperBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(list, "erasedUpperBound.constructor.parameters");
                Collection collection = list;
                boolean bl = false;
                if (!collection.isEmpty()) {
                    typeProjection = new TypeProjectionImpl(Variance.OUT_VARIANCE, erasedUpperBound);
                    break;
                }
                typeProjection = JavaTypeResolverKt.makeStarProjection(parameter, attr);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return typeProjection;
    }

    public static /* synthetic */ TypeProjection computeProjection$default(RawSubstitution rawSubstitution, TypeParameterDescriptor typeParameterDescriptor, JavaTypeAttributes javaTypeAttributes, KotlinType kotlinType, int n, Object object) {
        if ((n & 4) != 0) {
            kotlinType = JavaTypeResolverKt.getErasedUpperBound$default(typeParameterDescriptor, null, null, 3, null);
        }
        return rawSubstitution.computeProjection(typeParameterDescriptor, javaTypeAttributes, kotlinType);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private RawSubstitution() {
    }

    static {
        RawSubstitution rawSubstitution;
        INSTANCE = rawSubstitution = new RawSubstitution();
        lowerTypeAttr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND);
        upperTypeAttr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND);
    }

    public static final /* synthetic */ Pair access$eraseInflexibleBasedOnClassDescriptor(RawSubstitution $this, SimpleType type2, ClassDescriptor declaration, JavaTypeAttributes attr) {
        return $this.eraseInflexibleBasedOnClassDescriptor(type2, declaration, attr);
    }
}

