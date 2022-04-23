/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaTypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.NullabilityQualifierWithApplicability;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContextKt {
    @NotNull
    public static final LazyJavaResolverContext child(@NotNull LazyJavaResolverContext $this$child, @NotNull TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkNotNullParameter($this$child, "$this$child");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        return new LazyJavaResolverContext($this$child.getComponents(), typeParameterResolver, $this$child.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final JavaTypeQualifiersByElementType computeNewDefaultTypeQualifiers(@NotNull LazyJavaResolverContext $this$computeNewDefaultTypeQualifiers, @NotNull Annotations additionalAnnotations) {
        EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> enumMap;
        void $this$mapNotNullTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$computeNewDefaultTypeQualifiers, "$this$computeNewDefaultTypeQualifiers");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        if ($this$computeNewDefaultTypeQualifiers.getComponents().getAnnotationTypeQualifierResolver().getDisabled()) {
            return $this$computeNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
        }
        Iterable $this$mapNotNull$iv = additionalAnnotations;
        boolean $i$f$mapNotNull2 = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator<Object> iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            AnnotationDescriptor p1 = (AnnotationDescriptor)element$iv$iv;
            boolean bl2 = false;
            if (ContextKt.extractDefaultNullabilityQualifier($this$computeNewDefaultTypeQualifiers, p1) == null) continue;
            boolean bl3 = false;
            boolean bl4 = false;
            NullabilityQualifierWithApplicability it$iv$iv = nullabilityQualifierWithApplicability;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        List nullabilityQualifiersWithApplicability = (List)destination$iv$iv;
        if (nullabilityQualifiersWithApplicability.isEmpty()) {
            return $this$computeNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
        }
        Object object = $this$computeNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
        if (object != null && (object = ((JavaTypeQualifiersByElementType)object).getNullabilityQualifiers()) != null) {
            Object $i$f$mapNotNull2 = object;
            boolean bl = false;
            boolean bl6 = false;
            Object p1 = $i$f$mapNotNull2;
            boolean bl7 = false;
            enumMap = new EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus>((EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus>)p1);
        } else {
            enumMap = new EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus>(AnnotationTypeQualifierResolver.QualifierApplicabilityType.class);
        }
        EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> nullabilityQualifiersByType = enumMap;
        boolean wasUpdate = false;
        for (NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability : nullabilityQualifiersWithApplicability) {
            NullabilityQualifierWithMigrationStatus p1 = nullabilityQualifierWithApplicability.component1();
            Collection<AnnotationTypeQualifierResolver.QualifierApplicabilityType> applicableTo = nullabilityQualifierWithApplicability.component2();
            for (AnnotationTypeQualifierResolver.QualifierApplicabilityType applicabilityType : applicableTo) {
                void nullability;
                ((Map)nullabilityQualifiersByType).put(applicabilityType, nullability);
                wasUpdate = true;
            }
        }
        return !wasUpdate ? $this$computeNewDefaultTypeQualifiers.getDefaultTypeQualifiers() : new JavaTypeQualifiersByElementType(nullabilityQualifiersByType);
    }

    /*
     * WARNING - void declaration
     */
    private static final NullabilityQualifierWithApplicability extractDefaultNullabilityQualifier(LazyJavaResolverContext $this$extractDefaultNullabilityQualifier, AnnotationDescriptor annotationDescriptor) {
        void typeQualifier;
        ReportLevel jsr305State;
        AnnotationTypeQualifierResolver typeQualifierResolver = $this$extractDefaultNullabilityQualifier.getComponents().getAnnotationTypeQualifierResolver();
        NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability = typeQualifierResolver.resolveQualifierBuiltInDefaultAnnotation(annotationDescriptor);
        if (nullabilityQualifierWithApplicability != null) {
            NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability2 = nullabilityQualifierWithApplicability;
            boolean bl = false;
            boolean bl2 = false;
            NullabilityQualifierWithApplicability it = nullabilityQualifierWithApplicability2;
            boolean bl3 = false;
            return it;
        }
        AnnotationTypeQualifierResolver.TypeQualifierWithApplicability typeQualifierWithApplicability = typeQualifierResolver.resolveTypeQualifierDefaultAnnotation(annotationDescriptor);
        if (typeQualifierWithApplicability == null) {
            return null;
        }
        AnnotationTypeQualifierResolver.TypeQualifierWithApplicability typeQualifierWithApplicability2 = typeQualifierWithApplicability;
        AnnotationDescriptor annotationDescriptor2 = typeQualifierWithApplicability2.component1();
        List<AnnotationTypeQualifierResolver.QualifierApplicabilityType> applicability = typeQualifierWithApplicability2.component2();
        ReportLevel reportLevel = typeQualifierResolver.resolveJsr305CustomState(annotationDescriptor);
        if (reportLevel == null) {
            reportLevel = jsr305State = typeQualifierResolver.resolveJsr305AnnotationState((AnnotationDescriptor)typeQualifier);
        }
        if (jsr305State.isIgnore()) {
            return null;
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = $this$extractDefaultNullabilityQualifier.getComponents().getSignatureEnhancement().extractNullability((AnnotationDescriptor)typeQualifier);
        if (nullabilityQualifierWithMigrationStatus == null || (nullabilityQualifierWithMigrationStatus = NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatus, null, jsr305State.isWarning(), 1, null)) == null) {
            return null;
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifier = nullabilityQualifierWithMigrationStatus;
        return new NullabilityQualifierWithApplicability(nullabilityQualifier, (Collection<? extends AnnotationTypeQualifierResolver.QualifierApplicabilityType>)applicability);
    }

    @NotNull
    public static final LazyJavaResolverContext replaceComponents(@NotNull LazyJavaResolverContext $this$replaceComponents, @NotNull JavaResolverComponents components) {
        Intrinsics.checkNotNullParameter($this$replaceComponents, "$this$replaceComponents");
        Intrinsics.checkNotNullParameter(components, "components");
        return new LazyJavaResolverContext(components, $this$replaceComponents.getTypeParameterResolver(), $this$replaceComponents.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    /*
     * WARNING - void declaration
     */
    private static final LazyJavaResolverContext child(LazyJavaResolverContext $this$child, DeclarationDescriptor containingDeclaration, JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset, Lazy<JavaTypeQualifiersByElementType> delegateForTypeQualifiers) {
        TypeParameterResolver typeParameterResolver;
        JavaResolverComponents javaResolverComponents = $this$child.getComponents();
        JavaTypeParameterListOwner javaTypeParameterListOwner = typeParameterOwner;
        if (javaTypeParameterListOwner != null) {
            void it;
            JavaTypeParameterListOwner javaTypeParameterListOwner2 = javaTypeParameterListOwner;
            boolean bl = false;
            boolean bl2 = false;
            JavaTypeParameterListOwner javaTypeParameterListOwner3 = javaTypeParameterListOwner2;
            JavaResolverComponents javaResolverComponents2 = javaResolverComponents;
            boolean bl3 = false;
            LazyJavaTypeParameterResolver lazyJavaTypeParameterResolver = new LazyJavaTypeParameterResolver($this$child, containingDeclaration, (JavaTypeParameterListOwner)it, typeParametersIndexOffset);
            javaResolverComponents = javaResolverComponents2;
            typeParameterResolver = lazyJavaTypeParameterResolver;
        } else {
            typeParameterResolver = $this$child.getTypeParameterResolver();
        }
        Lazy<JavaTypeQualifiersByElementType> lazy = delegateForTypeQualifiers;
        TypeParameterResolver typeParameterResolver2 = typeParameterResolver;
        JavaResolverComponents javaResolverComponents3 = javaResolverComponents;
        return new LazyJavaResolverContext(javaResolverComponents3, typeParameterResolver2, lazy);
    }

    @NotNull
    public static final LazyJavaResolverContext childForMethod(@NotNull LazyJavaResolverContext $this$childForMethod, @NotNull DeclarationDescriptor containingDeclaration, @NotNull JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset) {
        Intrinsics.checkNotNullParameter($this$childForMethod, "$this$childForMethod");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        return ContextKt.child($this$childForMethod, containingDeclaration, typeParameterOwner, typeParametersIndexOffset, $this$childForMethod.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
    }

    public static /* synthetic */ LazyJavaResolverContext childForMethod$default(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return ContextKt.childForMethod(lazyJavaResolverContext, declarationDescriptor, javaTypeParameterListOwner, n);
    }

    @NotNull
    public static final LazyJavaResolverContext childForClassOrPackage(@NotNull LazyJavaResolverContext $this$childForClassOrPackage, @NotNull ClassOrPackageFragmentDescriptor containingDeclaration, @Nullable JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset) {
        Intrinsics.checkNotNullParameter($this$childForClassOrPackage, "$this$childForClassOrPackage");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        return ContextKt.child($this$childForClassOrPackage, containingDeclaration, typeParameterOwner, typeParametersIndexOffset, LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Function0<JavaTypeQualifiersByElementType>($this$childForClassOrPackage, containingDeclaration){
            final /* synthetic */ LazyJavaResolverContext $this_childForClassOrPackage;
            final /* synthetic */ ClassOrPackageFragmentDescriptor $containingDeclaration;

            @Nullable
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(this.$this_childForClassOrPackage, this.$containingDeclaration.getAnnotations());
            }
            {
                this.$this_childForClassOrPackage = lazyJavaResolverContext;
                this.$containingDeclaration = classOrPackageFragmentDescriptor;
                super(0);
            }
        }));
    }

    public static /* synthetic */ LazyJavaResolverContext childForClassOrPackage$default(LazyJavaResolverContext lazyJavaResolverContext, ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor, JavaTypeParameterListOwner javaTypeParameterListOwner, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            javaTypeParameterListOwner = null;
        }
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return ContextKt.childForClassOrPackage(lazyJavaResolverContext, classOrPackageFragmentDescriptor, javaTypeParameterListOwner, n);
    }

    @NotNull
    public static final LazyJavaResolverContext copyWithNewDefaultTypeQualifiers(@NotNull LazyJavaResolverContext $this$copyWithNewDefaultTypeQualifiers, @NotNull Annotations additionalAnnotations) {
        Intrinsics.checkNotNullParameter($this$copyWithNewDefaultTypeQualifiers, "$this$copyWithNewDefaultTypeQualifiers");
        Intrinsics.checkNotNullParameter(additionalAnnotations, "additionalAnnotations");
        return additionalAnnotations.isEmpty() ? $this$copyWithNewDefaultTypeQualifiers : new LazyJavaResolverContext($this$copyWithNewDefaultTypeQualifiers.getComponents(), $this$copyWithNewDefaultTypeQualifiers.getTypeParameterResolver(), LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Function0<JavaTypeQualifiersByElementType>($this$copyWithNewDefaultTypeQualifiers, additionalAnnotations){
            final /* synthetic */ LazyJavaResolverContext $this_copyWithNewDefaultTypeQualifiers;
            final /* synthetic */ Annotations $additionalAnnotations;

            @Nullable
            public final JavaTypeQualifiersByElementType invoke() {
                return ContextKt.computeNewDefaultTypeQualifiers(this.$this_copyWithNewDefaultTypeQualifiers, this.$additionalAnnotations);
            }
            {
                this.$this_copyWithNewDefaultTypeQualifiers = lazyJavaResolverContext;
                this.$additionalAnnotations = annotations2;
                super(0);
            }
        }));
    }
}

