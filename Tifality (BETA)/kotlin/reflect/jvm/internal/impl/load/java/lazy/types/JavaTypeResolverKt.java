/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImplKt;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionBase;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaTypeResolverKt {
    private static final FqName JAVA_LANG_CLASS_FQ_NAME = new FqName("java.lang.Class");

    @NotNull
    public static final TypeProjection makeStarProjection(@NotNull TypeParameterDescriptor typeParameter, @NotNull JavaTypeAttributes attr) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(attr, "attr");
        return attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE ? (TypeProjectionBase)new TypeProjectionImpl(StarProjectionImplKt.starProjectionType(typeParameter)) : (TypeProjectionBase)new StarProjectionImpl(typeParameter);
    }

    @NotNull
    public static final JavaTypeAttributes toAttributes(@NotNull TypeUsage $this$toAttributes, boolean isForAnnotationParameter, @Nullable TypeParameterDescriptor upperBoundForTypeParameter) {
        Intrinsics.checkNotNullParameter((Object)$this$toAttributes, "$this$toAttributes");
        return new JavaTypeAttributes($this$toAttributes, null, isForAnnotationParameter, upperBoundForTypeParameter, 2, null);
    }

    public static /* synthetic */ JavaTypeAttributes toAttributes$default(TypeUsage typeUsage, boolean bl, TypeParameterDescriptor typeParameterDescriptor, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            typeParameterDescriptor = null;
        }
        return JavaTypeResolverKt.toAttributes(typeUsage, bl, typeParameterDescriptor);
    }

    @NotNull
    public static final KotlinType getErasedUpperBound(@NotNull TypeParameterDescriptor $this$getErasedUpperBound, @Nullable TypeParameterDescriptor potentiallyRecursiveTypeParameter, @NotNull Function0<? extends KotlinType> defaultValue) {
        Intrinsics.checkNotNullParameter($this$getErasedUpperBound, "$this$getErasedUpperBound");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        if ($this$getErasedUpperBound == potentiallyRecursiveTypeParameter) {
            return defaultValue.invoke();
        }
        List<KotlinType> list = $this$getErasedUpperBound.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(list, "upperBounds");
        KotlinType firstUpperBound = CollectionsKt.first(list);
        if (firstUpperBound.getConstructor().getDeclarationDescriptor() instanceof ClassDescriptor) {
            KotlinType kotlinType = firstUpperBound;
            Intrinsics.checkNotNullExpressionValue(kotlinType, "firstUpperBound");
            return TypeUtilsKt.replaceArgumentsWithStarProjections(kotlinType);
        }
        TypeParameterDescriptor typeParameterDescriptor = potentiallyRecursiveTypeParameter;
        if (typeParameterDescriptor == null) {
            typeParameterDescriptor = $this$getErasedUpperBound;
        }
        TypeParameterDescriptor stopAt = typeParameterDescriptor;
        ClassifierDescriptor classifierDescriptor = firstUpperBound.getConstructor().getDeclarationDescriptor();
        if (classifierDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
        }
        TypeParameterDescriptor current = (TypeParameterDescriptor)classifierDescriptor;
        while (Intrinsics.areEqual(current, stopAt) ^ true) {
            List<KotlinType> list2 = current.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(list2, "current.upperBounds");
            KotlinType nextUpperBound = CollectionsKt.first(list2);
            if (nextUpperBound.getConstructor().getDeclarationDescriptor() instanceof ClassDescriptor) {
                KotlinType kotlinType = nextUpperBound;
                Intrinsics.checkNotNullExpressionValue(kotlinType, "nextUpperBound");
                return TypeUtilsKt.replaceArgumentsWithStarProjections(kotlinType);
            }
            ClassifierDescriptor classifierDescriptor2 = nextUpperBound.getConstructor().getDeclarationDescriptor();
            if (classifierDescriptor2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
            }
            current = (TypeParameterDescriptor)classifierDescriptor2;
        }
        return defaultValue.invoke();
    }

    public static /* synthetic */ KotlinType getErasedUpperBound$default(TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            typeParameterDescriptor2 = null;
        }
        if ((n & 2) != 0) {
            function0 = new Function0<SimpleType>(typeParameterDescriptor){
                final /* synthetic */ TypeParameterDescriptor $this_getErasedUpperBound;

                @NotNull
                public final SimpleType invoke() {
                    SimpleType simpleType2 = ErrorUtils.createErrorType("Can't compute erased upper bound of type parameter `" + this.$this_getErasedUpperBound + '`');
                    Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026 type parameter `$this`\")");
                    return simpleType2;
                }
                {
                    this.$this_getErasedUpperBound = typeParameterDescriptor;
                    super(0);
                }
            };
        }
        return JavaTypeResolverKt.getErasedUpperBound(typeParameterDescriptor, typeParameterDescriptor2, function0);
    }

    public static final /* synthetic */ FqName access$getJAVA_LANG_CLASS_FQ_NAME$p() {
        return JAVA_LANG_CLASS_FQ_NAME;
    }
}

