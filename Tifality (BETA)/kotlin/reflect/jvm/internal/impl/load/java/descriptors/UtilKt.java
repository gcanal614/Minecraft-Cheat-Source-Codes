/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UtilKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<ValueParameterDescriptor> copyValueParameters(@NotNull Collection<ValueParameterData> newValueParametersTypes, @NotNull Collection<? extends ValueParameterDescriptor> oldValueParameters, @NotNull CallableDescriptor newOwner) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(newValueParametersTypes, "newValueParametersTypes");
        Intrinsics.checkNotNullParameter(oldValueParameters, "oldValueParameters");
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        boolean bl = newValueParametersTypes.size() == oldValueParameters.size();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-UtilKt$copyValueParameters$22 = false;
            String $i$a$-assert-UtilKt$copyValueParameters$22 = "Different value parameters sizes: Enhanced = " + newValueParametersTypes.size() + ", Old = " + oldValueParameters.size();
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-UtilKt$copyValueParameters$22));
        }
        Iterable $this$map$iv = CollectionsKt.zip((Iterable)newValueParametersTypes, (Iterable)oldValueParameters);
        boolean $i$f$map = false;
        Iterable $i$a$-assert-UtilKt$copyValueParameters$22 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void newParameter;
            void $dstr$newParameter$oldParameter;
            Pair pair = (Pair)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl3 = false;
            ValueParameterData valueParameterData = (ValueParameterData)$dstr$newParameter$oldParameter.component1();
            ValueParameterDescriptor oldParameter = (ValueParameterDescriptor)$dstr$newParameter$oldParameter.component2();
            int n = oldParameter.getIndex();
            Annotations annotations2 = oldParameter.getAnnotations();
            Name name = oldParameter.getName();
            Intrinsics.checkNotNullExpressionValue(name, "oldParameter.name");
            KotlinType kotlinType = newParameter.getType();
            boolean bl4 = newParameter.getHasDefaultValue();
            boolean bl5 = oldParameter.isCrossinline();
            boolean bl6 = oldParameter.isNoinline();
            KotlinType kotlinType2 = oldParameter.getVarargElementType() != null ? DescriptorUtilsKt.getModule(newOwner).getBuiltIns().getArrayElementType(newParameter.getType()) : null;
            SourceElement sourceElement = oldParameter.getSource();
            Intrinsics.checkNotNullExpressionValue(sourceElement, "oldParameter.source");
            ValueParameterDescriptorImpl valueParameterDescriptorImpl = new ValueParameterDescriptorImpl(newOwner, null, n, annotations2, name, kotlinType, bl4, bl5, bl6, kotlinType2, sourceElement);
            collection.add(valueParameterDescriptorImpl);
        }
        return (List)destination$iv$iv;
    }

    @Nullable
    public static final LazyJavaStaticClassScope getParentJavaStaticClassScope(@NotNull ClassDescriptor $this$getParentJavaStaticClassScope) {
        LazyJavaStaticClassScope lazyJavaStaticClassScope;
        Intrinsics.checkNotNullParameter($this$getParentJavaStaticClassScope, "$this$getParentJavaStaticClassScope");
        ClassDescriptor classDescriptor = DescriptorUtilsKt.getSuperClassNotAny($this$getParentJavaStaticClassScope);
        if (classDescriptor == null) {
            return null;
        }
        ClassDescriptor superClassDescriptor = classDescriptor;
        MemberScope memberScope2 = superClassDescriptor.getStaticScope();
        if (!(memberScope2 instanceof LazyJavaStaticClassScope)) {
            memberScope2 = null;
        }
        if ((lazyJavaStaticClassScope = (LazyJavaStaticClassScope)memberScope2) == null) {
            lazyJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(superClassDescriptor);
        }
        return lazyJavaStaticClassScope;
    }

    @Nullable
    public static final AnnotationDefaultValue getDefaultValueFromAnnotation(@NotNull ValueParameterDescriptor $this$getDefaultValueFromAnnotation) {
        Intrinsics.checkNotNullParameter($this$getDefaultValueFromAnnotation, "$this$getDefaultValueFromAnnotation");
        Annotations annotations2 = $this$getDefaultValueFromAnnotation.getAnnotations();
        FqName fqName2 = JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME;
        Intrinsics.checkNotNullExpressionValue(fqName2, "JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME");
        Object object = annotations2.findAnnotation(fqName2);
        if (object != null && (object = DescriptorUtilsKt.firstArgument((AnnotationDescriptor)object)) != null) {
            Object $this$safeAs$iv = object;
            boolean $i$f$safeAs = false;
            Object object2 = $this$safeAs$iv;
            if (!(object2 instanceof StringValue)) {
                object2 = null;
            }
            if ((object = (StringValue)object2) != null && (object = (String)((ConstantValue)object).getValue()) != null) {
                Object object3 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object3;
                boolean bl3 = false;
                return new StringDefaultValue((String)it);
            }
        }
        Annotations annotations3 = $this$getDefaultValueFromAnnotation.getAnnotations();
        FqName fqName3 = JvmAnnotationNames.DEFAULT_NULL_FQ_NAME;
        Intrinsics.checkNotNullExpressionValue(fqName3, "JvmAnnotationNames.DEFAULT_NULL_FQ_NAME");
        if (annotations3.hasAnnotation(fqName3)) {
            return NullDefaultValue.INSTANCE;
        }
        return null;
    }
}

