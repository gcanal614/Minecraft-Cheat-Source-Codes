/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StarProjectionImplKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final KotlinType starProjectionType(@NotNull TypeParameterDescriptor $this$starProjectionType) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$starProjectionType, "$this$starProjectionType");
        DeclarationDescriptor declarationDescriptor = $this$starProjectionType.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters");
        }
        ClassifierDescriptorWithTypeParameters classDescriptor = (ClassifierDescriptorWithTypeParameters)declarationDescriptor;
        TypeConstructor typeConstructor2 = classDescriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "classDescriptor.typeConstructor");
        List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
        Intrinsics.checkNotNullExpressionValue(list, "classDescriptor.typeConstructor.parameters");
        Iterable $this$map$iv = list;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void v3 = it;
            Intrinsics.checkNotNullExpressionValue(v3, "it");
            TypeConstructor typeConstructor3 = v3.getTypeConstructor();
            collection.add(typeConstructor3);
        }
        List typeParameters2 = (List)destination$iv$iv;
        TypeSubstitutor typeSubstitutor2 = TypeSubstitutor.create(new TypeConstructorSubstitution(typeParameters2){
            final /* synthetic */ List $typeParameters;

            @Nullable
            public TypeProjection get(@NotNull TypeConstructor key) {
                TypeProjection typeProjection;
                Intrinsics.checkNotNullParameter(key, "key");
                if (this.$typeParameters.contains(key)) {
                    ClassifierDescriptor classifierDescriptor = key.getDeclarationDescriptor();
                    if (classifierDescriptor == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
                    }
                    typeProjection = TypeUtils.makeStarProjection((TypeParameterDescriptor)classifierDescriptor);
                } else {
                    typeProjection = null;
                }
                return typeProjection;
            }
            {
                this.$typeParameters = $captured_local_variable$0;
            }
        });
        List<KotlinType> list2 = $this$starProjectionType.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(list2, "this.upperBounds");
        KotlinType kotlinType = typeSubstitutor2.substitute(CollectionsKt.first(list2), Variance.OUT_VARIANCE);
        if (kotlinType == null) {
            SimpleType simpleType2 = DescriptorUtilsKt.getBuiltIns($this$starProjectionType).getDefaultBound();
            Intrinsics.checkNotNullExpressionValue(simpleType2, "builtIns.defaultBound");
            kotlinType = simpleType2;
        }
        return kotlinType;
    }
}

