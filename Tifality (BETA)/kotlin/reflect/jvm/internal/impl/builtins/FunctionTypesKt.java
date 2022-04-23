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
import kotlin.TuplesKt;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FunctionTypesKt {
    public static final boolean isFunctionType(@NotNull KotlinType $this$isFunctionType) {
        Intrinsics.checkNotNullParameter($this$isFunctionType, "$this$isFunctionType");
        ClassifierDescriptor classifierDescriptor = $this$isFunctionType.getConstructor().getDeclarationDescriptor();
        return (classifierDescriptor != null ? FunctionTypesKt.getFunctionalClassKind(classifierDescriptor) : null) == FunctionClassDescriptor.Kind.Function;
    }

    public static final boolean isSuspendFunctionType(@NotNull KotlinType $this$isSuspendFunctionType) {
        Intrinsics.checkNotNullParameter($this$isSuspendFunctionType, "$this$isSuspendFunctionType");
        ClassifierDescriptor classifierDescriptor = $this$isSuspendFunctionType.getConstructor().getDeclarationDescriptor();
        return (classifierDescriptor != null ? FunctionTypesKt.getFunctionalClassKind(classifierDescriptor) : null) == FunctionClassDescriptor.Kind.SuspendFunction;
    }

    public static final boolean isBuiltinFunctionalType(@NotNull KotlinType $this$isBuiltinFunctionalType) {
        Intrinsics.checkNotNullParameter($this$isBuiltinFunctionalType, "$this$isBuiltinFunctionalType");
        ClassifierDescriptor classifierDescriptor = $this$isBuiltinFunctionalType.getConstructor().getDeclarationDescriptor();
        return classifierDescriptor != null && FunctionTypesKt.isBuiltinFunctionalClassDescriptor(classifierDescriptor);
    }

    public static final boolean isBuiltinFunctionalClassDescriptor(@NotNull DeclarationDescriptor $this$isBuiltinFunctionalClassDescriptor) {
        Intrinsics.checkNotNullParameter($this$isBuiltinFunctionalClassDescriptor, "$this$isBuiltinFunctionalClassDescriptor");
        FunctionClassDescriptor.Kind functionalClassKind = FunctionTypesKt.getFunctionalClassKind($this$isBuiltinFunctionalClassDescriptor);
        return functionalClassKind == FunctionClassDescriptor.Kind.Function || functionalClassKind == FunctionClassDescriptor.Kind.SuspendFunction;
    }

    public static final boolean isBuiltinExtensionFunctionalType(@NotNull KotlinType $this$isBuiltinExtensionFunctionalType) {
        Intrinsics.checkNotNullParameter($this$isBuiltinExtensionFunctionalType, "$this$isBuiltinExtensionFunctionalType");
        return FunctionTypesKt.isBuiltinFunctionalType($this$isBuiltinExtensionFunctionalType) && FunctionTypesKt.isTypeAnnotatedWithExtensionFunctionType($this$isBuiltinExtensionFunctionalType);
    }

    private static final boolean isTypeAnnotatedWithExtensionFunctionType(KotlinType $this$isTypeAnnotatedWithExtensionFunctionType) {
        Annotations annotations2 = $this$isTypeAnnotatedWithExtensionFunctionType.getAnnotations();
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
        Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
        return annotations2.findAnnotation(fqName2) != null;
    }

    @Nullable
    public static final FunctionClassDescriptor.Kind getFunctionalClassKind(@NotNull DeclarationDescriptor $this$getFunctionalClassKind) {
        Intrinsics.checkNotNullParameter($this$getFunctionalClassKind, "$this$getFunctionalClassKind");
        if (!($this$getFunctionalClassKind instanceof ClassDescriptor)) {
            return null;
        }
        if (!KotlinBuiltIns.isUnderKotlinPackage($this$getFunctionalClassKind)) {
            return null;
        }
        return FunctionTypesKt.getFunctionalClassKind(DescriptorUtilsKt.getFqNameUnsafe($this$getFunctionalClassKind));
    }

    private static final FunctionClassDescriptor.Kind getFunctionalClassKind(FqNameUnsafe $this$getFunctionalClassKind) {
        if (!$this$getFunctionalClassKind.isSafe() || $this$getFunctionalClassKind.isRoot()) {
            return null;
        }
        String string = $this$getFunctionalClassKind.shortName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "shortName().asString()");
        FqName fqName2 = $this$getFunctionalClassKind.toSafe().parent();
        Intrinsics.checkNotNullExpressionValue(fqName2, "toSafe().parent()");
        return BuiltInFictitiousFunctionClassFactory.Companion.getFunctionalClassKind(string, fqName2);
    }

    @Nullable
    public static final KotlinType getReceiverTypeFromFunctionType(@NotNull KotlinType $this$getReceiverTypeFromFunctionType) {
        Intrinsics.checkNotNullParameter($this$getReceiverTypeFromFunctionType, "$this$getReceiverTypeFromFunctionType");
        boolean bl = FunctionTypesKt.isBuiltinFunctionalType($this$getReceiverTypeFromFunctionType);
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Not a function type: " + $this$getReceiverTypeFromFunctionType;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        return FunctionTypesKt.isTypeAnnotatedWithExtensionFunctionType($this$getReceiverTypeFromFunctionType) ? CollectionsKt.first($this$getReceiverTypeFromFunctionType.getArguments()).getType() : null;
    }

    @NotNull
    public static final KotlinType getReturnTypeFromFunctionType(@NotNull KotlinType $this$getReturnTypeFromFunctionType) {
        Intrinsics.checkNotNullParameter($this$getReturnTypeFromFunctionType, "$this$getReturnTypeFromFunctionType");
        boolean bl = FunctionTypesKt.isBuiltinFunctionalType($this$getReturnTypeFromFunctionType);
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Not a function type: " + $this$getReturnTypeFromFunctionType;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        KotlinType kotlinType = CollectionsKt.last($this$getReturnTypeFromFunctionType.getArguments()).getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "arguments.last().type");
        return kotlinType;
    }

    @NotNull
    public static final List<TypeProjection> getValueParameterTypesFromFunctionType(@NotNull KotlinType $this$getValueParameterTypesFromFunctionType) {
        Intrinsics.checkNotNullParameter($this$getValueParameterTypesFromFunctionType, "$this$getValueParameterTypesFromFunctionType");
        boolean bl = FunctionTypesKt.isBuiltinFunctionalType($this$getValueParameterTypesFromFunctionType);
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-FunctionTypesKt$getValueParameterTypesFromFunctionType$32 = false;
            String $i$a$-assert-FunctionTypesKt$getValueParameterTypesFromFunctionType$32 = "Not a function type: " + $this$getValueParameterTypesFromFunctionType;
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-FunctionTypesKt$getValueParameterTypesFromFunctionType$32));
        }
        List<TypeProjection> arguments2 = $this$getValueParameterTypesFromFunctionType.getArguments();
        int first = FunctionTypesKt.isBuiltinExtensionFunctionalType($this$getValueParameterTypesFromFunctionType) ? 1 : 0;
        int last = arguments2.size() - 1;
        boolean bl3 = first <= last;
        boolean bl4 = false;
        if (_Assertions.ENABLED && !bl3) {
            boolean bl5 = false;
            String string = "Not an exact function type: " + $this$getValueParameterTypesFromFunctionType;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        return arguments2.subList(first, last);
    }

    @Nullable
    public static final Name extractParameterNameFromFunctionTypeArgument(@NotNull KotlinType $this$extractParameterNameFromFunctionTypeArgument) {
        Object object;
        block6: {
            block5: {
                Intrinsics.checkNotNullParameter($this$extractParameterNameFromFunctionTypeArgument, "$this$extractParameterNameFromFunctionTypeArgument");
                Annotations annotations2 = $this$extractParameterNameFromFunctionTypeArgument.getAnnotations();
                FqName fqName2 = KotlinBuiltIns.FQ_NAMES.parameterName;
                Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.parameterName");
                AnnotationDescriptor annotationDescriptor = annotations2.findAnnotation(fqName2);
                if (annotationDescriptor == null) {
                    return null;
                }
                AnnotationDescriptor annotation = annotationDescriptor;
                Object t = CollectionsKt.singleOrNull((Iterable)annotation.getAllValueArguments().values());
                if (!(t instanceof StringValue)) {
                    t = null;
                }
                if ((object = (StringValue)t) == null || (object = (String)((ConstantValue)object).getValue()) == null) break block5;
                Object object2 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                object = Name.isValidIdentifier((String)it) ? object2 : null;
                if (object != null) break block6;
            }
            return null;
        }
        Object name = object;
        return Name.identifier((String)name);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<TypeProjection> getFunctionTypeArgumentProjections(@Nullable KotlinType receiverType, @NotNull List<? extends KotlinType> parameterTypes, @Nullable List<Name> parameterNames, @NotNull KotlinType returnType, @NotNull KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ArrayList<TypeProjection> arguments2 = new ArrayList<TypeProjection>(parameterTypes.size() + (receiverType != null ? 1 : 0) + 1);
        KotlinType kotlinType = receiverType;
        kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull((Collection)arguments2, kotlinType != null ? TypeUtilsKt.asTypeProjection(kotlinType) : null);
        Iterable $this$mapIndexedTo$iv = parameterTypes;
        boolean $i$f$mapIndexedTo = false;
        int index$iv = 0;
        for (Object item$iv : $this$mapIndexedTo$iv) {
            KotlinType kotlinType2;
            void type2;
            List<Name> name;
            List<Name> list;
            void index;
            Collection collection = arguments2;
            int n = index$iv++;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType kotlinType3 = (KotlinType)item$iv;
            int n2 = n;
            Collection collection2 = collection;
            boolean bl2 = false;
            List<Name> list2 = parameterNames;
            if (list2 != null && (list2 = list2.get((int)index)) != null) {
                List<Name> list3 = list2;
                boolean bl3 = false;
                boolean bl4 = false;
                List<Name> it = list3;
                boolean bl5 = false;
                list = !((Name)((Object)it)).isSpecial() ? list3 : null;
            } else {
                list = name = null;
            }
            if (name != null) {
                FqName fqName2 = KotlinBuiltIns.FQ_NAMES.parameterName;
                Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.parameterName");
                Name name2 = Name.identifier("name");
                String string = ((Name)((Object)name)).asString();
                Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
                BuiltInAnnotationDescriptor parameterNameAnnotation = new BuiltInAnnotationDescriptor(builtIns, fqName2, MapsKt.mapOf(TuplesKt.to(name2, new StringValue(string))));
                kotlinType2 = TypeUtilsKt.replaceAnnotations((KotlinType)type2, Annotations.Companion.create(CollectionsKt.plus((Iterable)type2.getAnnotations(), parameterNameAnnotation)));
            } else {
                kotlinType2 = type2;
            }
            void typeToUse = kotlinType2;
            TypeProjection typeProjection = TypeUtilsKt.asTypeProjection((KotlinType)typeToUse);
            collection2.add(typeProjection);
        }
        arguments2.add(TypeUtilsKt.asTypeProjection(returnType));
        return arguments2;
    }

    @JvmOverloads
    @NotNull
    public static final SimpleType createFunctionType(@NotNull KotlinBuiltIns builtIns, @NotNull Annotations annotations2, @Nullable KotlinType receiverType, @NotNull List<? extends KotlinType> parameterTypes, @Nullable List<Name> parameterNames, @NotNull KotlinType returnType, boolean suspendFunction) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        List<TypeProjection> arguments2 = FunctionTypesKt.getFunctionTypeArgumentProjections(receiverType, parameterTypes, parameterNames, returnType, builtIns);
        int parameterCount = receiverType == null ? parameterTypes.size() : parameterTypes.size() + 1;
        ClassDescriptor classDescriptor = FunctionTypesKt.getFunctionDescriptor(builtIns, parameterCount, suspendFunction);
        Annotations typeAnnotations = receiverType != null ? FunctionTypesKt.withExtensionFunctionAnnotation(annotations2, builtIns) : annotations2;
        return KotlinTypeFactory.simpleNotNullType(typeAnnotations, classDescriptor, arguments2);
    }

    public static /* synthetic */ SimpleType createFunctionType$default(KotlinBuiltIns kotlinBuiltIns, Annotations annotations2, KotlinType kotlinType, List list, List list2, KotlinType kotlinType2, boolean bl, int n, Object object) {
        if ((n & 0x40) != 0) {
            bl = false;
        }
        return FunctionTypesKt.createFunctionType(kotlinBuiltIns, annotations2, kotlinType, list, list2, kotlinType2, bl);
    }

    @NotNull
    public static final Annotations withExtensionFunctionAnnotation(@NotNull Annotations $this$withExtensionFunctionAnnotation, @NotNull KotlinBuiltIns builtIns) {
        Annotations annotations2;
        Intrinsics.checkNotNullParameter($this$withExtensionFunctionAnnotation, "$this$withExtensionFunctionAnnotation");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
        Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
        if ($this$withExtensionFunctionAnnotation.hasAnnotation(fqName2)) {
            annotations2 = $this$withExtensionFunctionAnnotation;
        } else {
            Iterable iterable = $this$withExtensionFunctionAnnotation;
            FqName fqName3 = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
            Intrinsics.checkNotNullExpressionValue(fqName3, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
            annotations2 = Annotations.Companion.create(CollectionsKt.plus(iterable, new BuiltInAnnotationDescriptor(builtIns, fqName3, MapsKt.emptyMap())));
        }
        return annotations2;
    }

    @NotNull
    public static final ClassDescriptor getFunctionDescriptor(@NotNull KotlinBuiltIns builtIns, int parameterCount, boolean isSuspendFunction) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassDescriptor classDescriptor = isSuspendFunction ? builtIns.getSuspendFunction(parameterCount) : builtIns.getFunction(parameterCount);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "if (isSuspendFunction) b\u2026tFunction(parameterCount)");
        return classDescriptor;
    }
}

