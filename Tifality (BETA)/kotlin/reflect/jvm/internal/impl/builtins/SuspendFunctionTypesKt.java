/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.MutableClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SuspendFunctionTypesKt {
    private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL;
    private static final MutableClassDescriptor FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final SimpleType transformSuspendFunctionToRuntimeFunctionType(@NotNull KotlinType suspendFunType, boolean isReleaseCoroutines) {
        Collection<KotlinType> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(suspendFunType, "suspendFunType");
        boolean bl = FunctionTypesKt.isSuspendFunctionType(suspendFunType);
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-SuspendFunctionTypesKt$transformSuspendFunctionToRuntimeFunctionType$22 = false;
            String $i$a$-assert-SuspendFunctionTypesKt$transformSuspendFunctionToRuntimeFunctionType$22 = "This type should be suspend function type: " + suspendFunType;
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-SuspendFunctionTypesKt$transformSuspendFunctionToRuntimeFunctionType$22));
        }
        Iterable iterable = FunctionTypesKt.getValueParameterTypesFromFunctionType(suspendFunType);
        KotlinType kotlinType = FunctionTypesKt.getReceiverTypeFromFunctionType(suspendFunType);
        Annotations annotations2 = suspendFunType.getAnnotations();
        KotlinBuiltIns kotlinBuiltIns = TypeUtilsKt.getBuiltIns(suspendFunType);
        boolean $i$f$map = false;
        void $i$a$-assert-SuspendFunctionTypesKt$transformSuspendFunctionToRuntimeFunctionType$22 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            TypeProjection typeProjection = (TypeProjection)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl3 = false;
            KotlinType kotlinType2 = p1.getType();
            collection.add(kotlinType2);
        }
        collection = (List)destination$iv$iv;
        Collection collection2 = collection;
        Annotations annotations3 = Annotations.Companion.getEMPTY();
        TypeConstructor typeConstructor2 = isReleaseCoroutines ? FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE.getTypeConstructor() : FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "if (isReleaseCoroutines)\u2026ERIMENTAL.typeConstructor");
        List<SimpleType> list = CollectionsKt.plus(collection2, KotlinTypeFactory.simpleType$default(annotations3, typeConstructor2, CollectionsKt.listOf(TypeUtilsKt.asTypeProjection(FunctionTypesKt.getReturnTypeFromFunctionType(suspendFunType))), false, null, 16, null));
        SimpleType simpleType2 = TypeUtilsKt.getBuiltIns(suspendFunType).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "suspendFunType.builtIns.nullableAnyType");
        return FunctionTypesKt.createFunctionType$default(kotlinBuiltIns, annotations2, kotlinType, list, null, simpleType2, false, 64, null).makeNullableAsSpecified(suspendFunType.isMarkedNullable());
    }

    public static final boolean isContinuation(@Nullable FqName name, boolean isReleaseCoroutines) {
        return isReleaseCoroutines ? Intrinsics.areEqual(name, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE) : Intrinsics.areEqual(name, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL);
    }

    static {
        TypeParameterDescriptor p1;
        ModuleDescriptor moduleDescriptor = ErrorUtils.getErrorModule();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "ErrorUtils.getErrorModule()");
        FqName fqName2 = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL;
        Intrinsics.checkNotNullExpressionValue(fqName2, "DescriptorUtils.COROUTIN\u2026KAGE_FQ_NAME_EXPERIMENTAL");
        MutableClassDescriptor mutableClassDescriptor = new MutableClassDescriptor(new EmptyPackageFragmentDescriptor(moduleDescriptor, fqName2), ClassKind.INTERFACE, false, false, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        boolean bl = false;
        boolean bl2 = false;
        MutableClassDescriptor $this$apply = mutableClassDescriptor;
        boolean bl3 = false;
        $this$apply.setModality(Modality.ABSTRACT);
        $this$apply.setVisibility(Visibilities.PUBLIC);
        TypeParameterDescriptor typeParameterDescriptor = TypeParameterDescriptorImpl.createWithDefaultBound($this$apply, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier("T"), 0, LockBasedStorageManager.NO_LOCKS);
        boolean bl4 = false;
        boolean bl5 = false;
        TypeParameterDescriptor typeParameterDescriptor2 = typeParameterDescriptor;
        MutableClassDescriptor mutableClassDescriptor2 = $this$apply;
        boolean bl6 = false;
        List<Object> list = CollectionsKt.listOf(p1);
        mutableClassDescriptor2.setTypeParameterDescriptors(list);
        $this$apply.createTypeConstructor();
        FAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL = mutableClassDescriptor;
        ModuleDescriptor moduleDescriptor2 = ErrorUtils.getErrorModule();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptor2, "ErrorUtils.getErrorModule()");
        FqName fqName3 = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
        Intrinsics.checkNotNullExpressionValue(fqName3, "DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE");
        mutableClassDescriptor = new MutableClassDescriptor(new EmptyPackageFragmentDescriptor(moduleDescriptor2, fqName3), ClassKind.INTERFACE, false, false, DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE.shortName(), SourceElement.NO_SOURCE, LockBasedStorageManager.NO_LOCKS);
        bl = false;
        bl2 = false;
        $this$apply = mutableClassDescriptor;
        boolean bl7 = false;
        $this$apply.setModality(Modality.ABSTRACT);
        $this$apply.setVisibility(Visibilities.PUBLIC);
        typeParameterDescriptor = TypeParameterDescriptorImpl.createWithDefaultBound($this$apply, Annotations.Companion.getEMPTY(), false, Variance.IN_VARIANCE, Name.identifier("T"), 0, LockBasedStorageManager.NO_LOCKS);
        bl4 = false;
        bl5 = false;
        p1 = typeParameterDescriptor;
        mutableClassDescriptor2 = $this$apply;
        boolean bl8 = false;
        list = CollectionsKt.listOf(p1);
        mutableClassDescriptor2.setTypeParameterDescriptors(list);
        $this$apply.createTypeConstructor();
        FAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE = mutableClassDescriptor;
    }
}

