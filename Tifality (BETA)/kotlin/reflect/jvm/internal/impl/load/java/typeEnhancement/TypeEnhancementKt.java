/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.EnhancedTypeAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.EnhancementResult;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NotNullTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.Result;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SimpleResult;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeComponentPosition;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt$WhenMappings;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeEnhancementKt {
    private static final EnhancedTypeAnnotations ENHANCED_NULLABILITY_ANNOTATIONS;
    private static final EnhancedTypeAnnotations ENHANCED_MUTABILITY_ANNOTATIONS;

    @Nullable
    public static final KotlinType enhance(@NotNull KotlinType $this$enhance, @NotNull Function1<? super Integer, JavaTypeQualifiers> qualifiers) {
        Intrinsics.checkNotNullParameter($this$enhance, "$this$enhance");
        Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
        return TypeEnhancementKt.enhancePossiblyFlexible($this$enhance.unwrap(), qualifiers, 0).getTypeIfChanged();
    }

    public static final boolean hasEnhancedNullability(@NotNull KotlinType $this$hasEnhancedNullability) {
        Intrinsics.checkNotNullParameter($this$hasEnhancedNullability, "$this$hasEnhancedNullability");
        return TypeEnhancementKt.hasEnhancedNullability(SimpleClassicTypeSystemContext.INSTANCE, $this$hasEnhancedNullability);
    }

    public static final boolean hasEnhancedNullability(@NotNull TypeSystemCommonBackendContext $this$hasEnhancedNullability, @NotNull KotlinTypeMarker type2) {
        Intrinsics.checkNotNullParameter($this$hasEnhancedNullability, "$this$hasEnhancedNullability");
        Intrinsics.checkNotNullParameter(type2, "type");
        FqName fqName2 = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(fqName2, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        return $this$hasEnhancedNullability.hasAnnotation(type2, fqName2);
    }

    private static final Result enhancePossiblyFlexible(UnwrappedType $this$enhancePossiblyFlexible, Function1<? super Integer, JavaTypeQualifiers> qualifiers, int index) {
        Result result2;
        if (KotlinTypeKt.isError($this$enhancePossiblyFlexible)) {
            return new Result($this$enhancePossiblyFlexible, 1, false);
        }
        UnwrappedType unwrappedType = $this$enhancePossiblyFlexible;
        if (unwrappedType instanceof FlexibleType) {
            KotlinType enhancement2;
            SimpleResult lowerResult = TypeEnhancementKt.enhanceInflexible(((FlexibleType)$this$enhancePossiblyFlexible).getLowerBound(), qualifiers, index, TypeComponentPosition.FLEXIBLE_LOWER);
            SimpleResult upperResult = TypeEnhancementKt.enhanceInflexible(((FlexibleType)$this$enhancePossiblyFlexible).getUpperBound(), qualifiers, index, TypeComponentPosition.FLEXIBLE_UPPER);
            boolean bl = lowerResult.getSubtreeSize() == upperResult.getSubtreeSize();
            boolean bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean $i$a$-assert-TypeEnhancementKt$enhancePossiblyFlexible$22 = false;
                String $i$a$-assert-TypeEnhancementKt$enhancePossiblyFlexible$22 = "Different tree sizes of bounds: " + "lower = (" + ((FlexibleType)$this$enhancePossiblyFlexible).getLowerBound() + ", " + lowerResult.getSubtreeSize() + "), " + "upper = (" + ((FlexibleType)$this$enhancePossiblyFlexible).getUpperBound() + ", " + upperResult.getSubtreeSize() + ')';
                throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-TypeEnhancementKt$enhancePossiblyFlexible$22));
            }
            boolean wereChanges = lowerResult.getWereChanges() || upperResult.getWereChanges();
            KotlinType kotlinType = TypeWithEnhancementKt.getEnhancement(lowerResult.getType());
            if (kotlinType == null) {
                kotlinType = enhancement2 = TypeWithEnhancementKt.getEnhancement(upperResult.getType());
            }
            UnwrappedType type2 = !wereChanges ? $this$enhancePossiblyFlexible : TypeWithEnhancementKt.wrapEnhancement($this$enhancePossiblyFlexible instanceof RawTypeImpl ? (UnwrappedType)new RawTypeImpl(lowerResult.getType(), upperResult.getType()) : KotlinTypeFactory.flexibleType(lowerResult.getType(), upperResult.getType()), enhancement2);
            result2 = new Result(type2, lowerResult.getSubtreeSize(), wereChanges);
        } else if (unwrappedType instanceof SimpleType) {
            result2 = TypeEnhancementKt.enhanceInflexible((SimpleType)$this$enhancePossiblyFlexible, qualifiers, index, TypeComponentPosition.INFLEXIBLE);
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return result2;
    }

    /*
     * WARNING - void declaration
     */
    private static final SimpleResult enhanceInflexible(SimpleType $this$enhanceInflexible, Function1<? super Integer, JavaTypeQualifiers> qualifiers, int index, TypeComponentPosition position) {
        UnwrappedType result2;
        void enhancedNullability;
        EnhancementResult<Boolean> $this$mapIndexedTo$iv$iv;
        void enhancedClassifier;
        boolean shouldEnhance = TypeEnhancementKt.shouldEnhance(position);
        if (!shouldEnhance && $this$enhanceInflexible.getArguments().isEmpty()) {
            return new SimpleResult($this$enhanceInflexible, 1, false);
        }
        ClassifierDescriptor classifierDescriptor = $this$enhanceInflexible.getConstructor().getDeclarationDescriptor();
        if (classifierDescriptor == null) {
            return new SimpleResult($this$enhanceInflexible, 1, false);
        }
        Intrinsics.checkNotNullExpressionValue(classifierDescriptor, "constructor.declarationD\u2026pleResult(this, 1, false)");
        ClassifierDescriptor originalClass = classifierDescriptor;
        JavaTypeQualifiers effectiveQualifiers = qualifiers.invoke((Integer)index);
        EnhancementResult<ClassifierDescriptor> enhancementResult2 = TypeEnhancementKt.enhanceMutability(originalClass, effectiveQualifiers, position);
        ClassifierDescriptor classifierDescriptor2 = enhancementResult2.component1();
        Annotations enhancedMutabilityAnnotations = enhancementResult2.component2();
        TypeConstructor typeConstructor2 = enhancedClassifier.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "enhancedClassifier.typeConstructor");
        TypeConstructor typeConstructor3 = typeConstructor2;
        int globalArgIndex = index + 1;
        boolean wereChanges = enhancedMutabilityAnnotations != null;
        Iterable $this$mapIndexed$iv22 = $this$enhanceInflexible.getArguments();
        boolean $i$f$mapIndexed = false;
        Iterable iterable = $this$mapIndexed$iv22;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv22, 10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        Iterator iterator2 = $this$mapIndexedTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            TypeProjection typeProjection;
            void localArgIndex;
            void arg;
            Object item$iv$iv = iterator2.next();
            int n = index$iv$iv++;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection2 = (TypeProjection)item$iv$iv;
            int n2 = n;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            if (arg.isStarProjection()) {
                int n3 = globalArgIndex;
                globalArgIndex = n3 + 1;
                TypeConstructor typeConstructor4 = enhancedClassifier.getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor4, "enhancedClassifier.typeConstructor");
                typeProjection = TypeUtils.makeStarProjection(typeConstructor4.getParameters().get((int)localArgIndex));
            } else {
                Result enhanced = TypeEnhancementKt.enhancePossiblyFlexible(arg.getType().unwrap(), qualifiers, globalArgIndex);
                wereChanges = wereChanges || enhanced.getWereChanges();
                globalArgIndex += enhanced.getSubtreeSize();
                KotlinType kotlinType = enhanced.getType();
                Variance variance = arg.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue((Object)variance, "arg.projectionKind");
                typeProjection = TypeUtilsKt.createProjection(kotlinType, variance, typeConstructor3.getParameters().get((int)localArgIndex));
            }
            TypeProjection typeProjection3 = typeProjection;
            collection.add(typeProjection3);
        }
        List enhancedArguments = (List)destination$iv$iv;
        $this$mapIndexedTo$iv$iv = TypeEnhancementKt.getEnhancedNullability($this$enhanceInflexible, effectiveQualifiers, position);
        boolean $this$mapIndexed$iv22 = $this$mapIndexedTo$iv$iv.component1();
        Annotations enhancedNullabilityAnnotations = $this$mapIndexedTo$iv$iv.component2();
        wereChanges = wereChanges || enhancedNullabilityAnnotations != null;
        int subtreeSize = globalArgIndex - index;
        if (!wereChanges) {
            return new SimpleResult($this$enhanceInflexible, subtreeSize, false);
        }
        Annotations newAnnotations = TypeEnhancementKt.compositeAnnotationsOrSingle(CollectionsKt.listOfNotNull($this$enhanceInflexible.getAnnotations(), enhancedMutabilityAnnotations, enhancedNullabilityAnnotations));
        SimpleType enhancedType = KotlinTypeFactory.simpleType$default(newAnnotations, typeConstructor3, enhancedArguments, (boolean)enhancedNullability, null, 16, null);
        SimpleType enhancement2 = effectiveQualifiers.isNotNullTypeParameter() ? (SimpleType)new NotNullTypeParameter(enhancedType) : enhancedType;
        boolean nullabilityForWarning = enhancedNullabilityAnnotations != null && effectiveQualifiers.isNullabilityQualifierForWarning();
        UnwrappedType unwrappedType = result2 = nullabilityForWarning ? TypeWithEnhancementKt.wrapEnhancement($this$enhanceInflexible, enhancement2) : (UnwrappedType)enhancement2;
        if (unwrappedType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new SimpleResult((SimpleType)unwrappedType, subtreeSize, true);
    }

    private static final Annotations compositeAnnotationsOrSingle(List<? extends Annotations> $this$compositeAnnotationsOrSingle) {
        Annotations annotations2;
        switch ($this$compositeAnnotationsOrSingle.size()) {
            case 0: {
                String string = "At least one Annotations object expected";
                boolean bl = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            case 1: {
                annotations2 = CollectionsKt.single($this$compositeAnnotationsOrSingle);
                break;
            }
            default: {
                annotations2 = new CompositeAnnotations(CollectionsKt.toList((Iterable)$this$compositeAnnotationsOrSingle));
            }
        }
        return annotations2;
    }

    public static final boolean shouldEnhance(@NotNull TypeComponentPosition $this$shouldEnhance) {
        Intrinsics.checkNotNullParameter((Object)$this$shouldEnhance, "$this$shouldEnhance");
        return $this$shouldEnhance != TypeComponentPosition.INFLEXIBLE;
    }

    private static final <T> EnhancementResult<T> noChange(T $this$noChange) {
        return new EnhancementResult<T>($this$noChange, null);
    }

    private static final <T> EnhancementResult<T> enhancedNullability(T $this$enhancedNullability) {
        return new EnhancementResult<T>($this$enhancedNullability, ENHANCED_NULLABILITY_ANNOTATIONS);
    }

    private static final <T> EnhancementResult<T> enhancedMutability(T $this$enhancedMutability) {
        return new EnhancementResult<T>($this$enhancedMutability, ENHANCED_MUTABILITY_ANNOTATIONS);
    }

    private static final EnhancementResult<ClassifierDescriptor> enhanceMutability(ClassifierDescriptor $this$enhanceMutability, JavaTypeQualifiers qualifiers, TypeComponentPosition position) {
        if (!TypeEnhancementKt.shouldEnhance(position)) {
            return TypeEnhancementKt.noChange($this$enhanceMutability);
        }
        if (!($this$enhanceMutability instanceof ClassDescriptor)) {
            return TypeEnhancementKt.noChange($this$enhanceMutability);
        }
        JavaToKotlinClassMap mapping = JavaToKotlinClassMap.INSTANCE;
        MutabilityQualifier mutabilityQualifier = qualifiers.getMutability();
        if (mutabilityQualifier != null) {
            switch (TypeEnhancementKt$WhenMappings.$EnumSwitchMapping$0[mutabilityQualifier.ordinal()]) {
                case 1: {
                    if (position != TypeComponentPosition.FLEXIBLE_LOWER || !mapping.isMutable((ClassDescriptor)$this$enhanceMutability)) break;
                    return TypeEnhancementKt.enhancedMutability(mapping.convertMutableToReadOnly((ClassDescriptor)$this$enhanceMutability));
                }
                case 2: {
                    if (position != TypeComponentPosition.FLEXIBLE_UPPER || !mapping.isReadOnly((ClassDescriptor)$this$enhanceMutability)) break;
                    return TypeEnhancementKt.enhancedMutability(mapping.convertReadOnlyToMutable((ClassDescriptor)$this$enhanceMutability));
                }
            }
        }
        return TypeEnhancementKt.noChange($this$enhanceMutability);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final EnhancementResult<Boolean> getEnhancedNullability(KotlinType $this$getEnhancedNullability, JavaTypeQualifiers qualifiers, TypeComponentPosition position) {
        EnhancementResult<Boolean> enhancementResult2;
        if (!TypeEnhancementKt.shouldEnhance(position)) {
            return TypeEnhancementKt.noChange($this$getEnhancedNullability.isMarkedNullable());
        }
        NullabilityQualifier nullabilityQualifier = qualifiers.getNullability();
        if (nullabilityQualifier != null) {
            switch (TypeEnhancementKt$WhenMappings.$EnumSwitchMapping$1[nullabilityQualifier.ordinal()]) {
                case 1: {
                    enhancementResult2 = TypeEnhancementKt.enhancedNullability(true);
                    return enhancementResult2;
                }
                case 2: {
                    enhancementResult2 = TypeEnhancementKt.enhancedNullability(false);
                    return enhancementResult2;
                }
            }
        }
        enhancementResult2 = TypeEnhancementKt.noChange($this$getEnhancedNullability.isMarkedNullable());
        return enhancementResult2;
    }

    static {
        FqName fqName2 = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(fqName2, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        ENHANCED_NULLABILITY_ANNOTATIONS = new EnhancedTypeAnnotations(fqName2);
        FqName fqName3 = JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(fqName3, "JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION");
        ENHANCED_MUTABILITY_ANNOTATIONS = new EnhancedTypeAnnotations(fqName3);
    }
}

