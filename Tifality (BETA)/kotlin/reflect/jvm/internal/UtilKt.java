/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference;
import kotlin.reflect.KCallable;
import kotlin.reflect.KType;
import kotlin.reflect.KVisibility;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.ModuleByClassLoaderKt;
import kotlin.reflect.jvm.internal.Util;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectAnnotationSource;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectJavaClassFinderKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinarySourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.NullValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u00c0\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0000\u001an\u0010\u0011\u001a\u0004\u0018\u0001H\u0012\"\b\b\u0000\u0010\u0013*\u00020\u0014\"\b\b\u0001\u0010\u0012*\u00020\u00062\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\u0017\u001a\u0002H\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u001d\u0010\u001e\u001a\u0019\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u00120\u001f\u00a2\u0006\u0002\b!H\u0000\u00a2\u0006\u0002\u0010\"\u001a.\u0010#\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00162\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\u0006\u0010)\u001a\u00020*H\u0002\u001a(\u0010#\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00162\u0006\u0010$\u001a\u00020%2\u0006\u0010+\u001a\u00020,2\b\b\u0002\u0010)\u001a\u00020*H\u0002\u001a%\u0010-\u001a\u0002H.\"\u0004\b\u0000\u0010.2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H.00H\u0080\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u00101\u001a\u0014\u00102\u001a\b\u0012\u0002\b\u0003\u0018\u000103*\u0004\u0018\u00010\u000eH\u0000\u001a\u0010\u00104\u001a\u0004\u0018\u000105*\u0004\u0018\u00010\u000eH\u0000\u001a\u0014\u00106\u001a\b\u0012\u0002\b\u0003\u0018\u000107*\u0004\u0018\u00010\u000eH\u0000\u001a\u0012\u00108\u001a\b\u0012\u0004\u0012\u00020:09*\u00020;H\u0000\u001a\u000e\u0010<\u001a\u0004\u0018\u00010:*\u00020=H\u0002\u001a\u0012\u0010>\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016*\u00020?H\u0000\u001a\u000e\u0010@\u001a\u0004\u0018\u00010A*\u00020BH\u0000\u001a\u001a\u0010C\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030D2\u0006\u0010$\u001a\u00020%H\u0002\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00068@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006E"}, d2={"JVM_STATIC", "Lkotlin/reflect/jvm/internal/impl/name/FqName;", "getJVM_STATIC", "()Lorg/jetbrains/kotlin/name/FqName;", "instanceReceiverParameter", "Lkotlin/reflect/jvm/internal/impl/descriptors/ReceiverParameterDescriptor;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;", "getInstanceReceiverParameter", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isInlineClassType", "", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KType;)Z", "defaultPrimitiveValue", "", "type", "Ljava/lang/reflect/Type;", "deserializeToDescriptor", "D", "M", "Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;", "moduleAnchor", "Ljava/lang/Class;", "proto", "nameResolver", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;", "typeTable", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/TypeTable;", "metadataVersion", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion;", "createDescriptor", "Lkotlin/Function2;", "Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/MemberDeserializer;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Class;Lorg/jetbrains/kotlin/protobuf/MessageLite;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "loadClass", "classLoader", "Ljava/lang/ClassLoader;", "packageName", "", "className", "arrayDimensions", "", "kotlinClassId", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "reflectionCall", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "asKCallableImpl", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "asKFunctionImpl", "Lkotlin/reflect/jvm/internal/KFunctionImpl;", "asKPropertyImpl", "Lkotlin/reflect/jvm/internal/KPropertyImpl;", "computeAnnotations", "", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/Annotated;", "toAnnotationInstance", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptor;", "toJavaClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "toKVisibility", "Lkotlin/reflect/KVisibility;", "Lkotlin/reflect/jvm/internal/impl/descriptors/Visibility;", "toRuntimeValue", "Lkotlin/reflect/jvm/internal/impl/resolve/constants/ConstantValue;", "kotlin-reflection"})
public final class UtilKt {
    @NotNull
    private static final FqName JVM_STATIC = new FqName("kotlin.jvm.JvmStatic");

    @NotNull
    public static final FqName getJVM_STATIC() {
        return JVM_STATIC;
    }

    @Nullable
    public static final Class<?> toJavaClass(@NotNull ClassDescriptor $this$toJavaClass) {
        AnnotatedElement annotatedElement;
        SourceElement source;
        Intrinsics.checkNotNullParameter($this$toJavaClass, "$this$toJavaClass");
        SourceElement sourceElement = $this$toJavaClass.getSource();
        Intrinsics.checkNotNullExpressionValue(sourceElement, "source");
        SourceElement sourceElement2 = source = sourceElement;
        if (sourceElement2 instanceof KotlinJvmBinarySourceElement) {
            KotlinJvmBinaryClass kotlinJvmBinaryClass = ((KotlinJvmBinarySourceElement)source).getBinaryClass();
            if (kotlinJvmBinaryClass == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.runtime.components.ReflectKotlinClass");
            }
            annotatedElement = ((ReflectKotlinClass)kotlinJvmBinaryClass).getKlass();
        } else if (sourceElement2 instanceof RuntimeSourceElementFactory.RuntimeSourceElement) {
            ReflectJavaElement reflectJavaElement = ((RuntimeSourceElementFactory.RuntimeSourceElement)source).getJavaElement();
            if (reflectJavaElement == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass");
            }
            annotatedElement = ((ReflectJavaClass)reflectJavaElement).getElement();
        } else {
            ClassId classId = DescriptorUtilsKt.getClassId($this$toJavaClass);
            if (classId == null) {
                return null;
            }
            ClassId classId2 = classId;
            annotatedElement = UtilKt.loadClass(ReflectClassUtilKt.getSafeClassLoader($this$toJavaClass.getClass()), classId2, 0);
        }
        return annotatedElement;
    }

    private static final Class<?> loadClass(ClassLoader classLoader, ClassId kotlinClassId, int arrayDimensions) {
        FqNameUnsafe fqNameUnsafe = kotlinClassId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "kotlinClassId.asSingleFqName().toUnsafe()");
        ClassId classId = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(fqNameUnsafe);
        if (classId == null) {
            classId = kotlinClassId;
        }
        ClassId javaClassId = classId;
        String string = javaClassId.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "javaClassId.packageFqName.asString()");
        String string2 = javaClassId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(string2, "javaClassId.relativeClassName.asString()");
        return UtilKt.loadClass(classLoader, string, string2, arrayDimensions);
    }

    static /* synthetic */ Class loadClass$default(ClassLoader classLoader, ClassId classId, int n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = 0;
        }
        return UtilKt.loadClass(classLoader, classId, n);
    }

    private static final Class<?> loadClass(ClassLoader classLoader, String packageName, String className, int arrayDimensions) {
        if (Intrinsics.areEqual(packageName, "kotlin")) {
            switch (className) {
                case "Array": {
                    return Object[].class;
                }
                case "BooleanArray": {
                    return boolean[].class;
                }
                case "ByteArray": {
                    return byte[].class;
                }
                case "CharArray": {
                    return char[].class;
                }
                case "DoubleArray": {
                    return double[].class;
                }
                case "FloatArray": {
                    return float[].class;
                }
                case "IntArray": {
                    return int[].class;
                }
                case "LongArray": {
                    return long[].class;
                }
                case "ShortArray": {
                    return short[].class;
                }
            }
        }
        String fqName2 = packageName + '.' + StringsKt.replace$default(className, '.', '$', false, 4, null);
        if (arrayDimensions > 0) {
            fqName2 = StringsKt.repeat("[", arrayDimensions) + 'L' + fqName2 + ';';
        }
        return ReflectJavaClassFinderKt.tryLoadClass(classLoader, fqName2);
    }

    @Nullable
    public static final KVisibility toKVisibility(@NotNull Visibility $this$toKVisibility) {
        Intrinsics.checkNotNullParameter($this$toKVisibility, "$this$toKVisibility");
        Visibility visibility = $this$toKVisibility;
        return Intrinsics.areEqual(visibility, Visibilities.PUBLIC) ? KVisibility.PUBLIC : (Intrinsics.areEqual(visibility, Visibilities.PROTECTED) ? KVisibility.PROTECTED : (Intrinsics.areEqual(visibility, Visibilities.INTERNAL) ? KVisibility.INTERNAL : (Intrinsics.areEqual(visibility, Visibilities.PRIVATE) || Intrinsics.areEqual(visibility, Visibilities.PRIVATE_TO_THIS) ? KVisibility.PRIVATE : null)));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<Annotation> computeAnnotations(@NotNull Annotated $this$computeAnnotations) {
        void $this$mapNotNullTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$computeAnnotations, "$this$computeAnnotations");
        Iterable $this$mapNotNull$iv = $this$computeAnnotations.getAnnotations();
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Annotation annotation;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            AnnotationDescriptor it = (AnnotationDescriptor)element$iv$iv;
            boolean bl2 = false;
            SourceElement source = it.getSource();
            SourceElement sourceElement = source;
            if (sourceElement instanceof ReflectAnnotationSource) {
                annotation = ((ReflectAnnotationSource)source).getAnnotation();
            } else if (sourceElement instanceof RuntimeSourceElementFactory.RuntimeSourceElement) {
                ReflectJavaElement reflectJavaElement = ((RuntimeSourceElementFactory.RuntimeSourceElement)source).getJavaElement();
                if (!(reflectJavaElement instanceof ReflectJavaAnnotation)) {
                    reflectJavaElement = null;
                }
                ReflectJavaAnnotation reflectJavaAnnotation = (ReflectJavaAnnotation)reflectJavaElement;
                annotation = reflectJavaAnnotation != null ? reflectJavaAnnotation.getAnnotation() : null;
            } else {
                annotation = UtilKt.toAnnotationInstance(it);
            }
            if (annotation == null) continue;
            Annotation annotation2 = annotation;
            boolean bl3 = false;
            boolean bl4 = false;
            Annotation it$iv$iv = annotation2;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private static final Annotation toAnnotationInstance(AnnotationDescriptor $this$toAnnotationInstance) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        ClassDescriptor classDescriptor = DescriptorUtilsKt.getAnnotationClass($this$toAnnotationInstance);
        Class<Object> clazz = classDescriptor != null ? UtilKt.toJavaClass(classDescriptor) : null;
        if (!(clazz instanceof Class)) {
            clazz = null;
        }
        if (clazz == null) {
            return null;
        }
        Class<?> annotationClass = clazz;
        Iterable iterable = $this$toAnnotationInstance.getAllValueArguments().entrySet();
        Class<?> clazz2 = annotationClass;
        boolean $i$f$mapNotNull = false;
        void var4_5 = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Pair<String, Object> pair;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            Map.Entry $dstr$name$value = (Map.Entry)element$iv$iv;
            boolean bl2 = false;
            Object object = $dstr$name$value;
            boolean bl3 = false;
            Name name = (Name)object.getKey();
            object = $dstr$name$value;
            bl3 = false;
            ConstantValue value = (ConstantValue)object.getValue();
            ClassLoader classLoader = annotationClass.getClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "annotationClass.classLoader");
            Object object2 = UtilKt.toRuntimeValue(value, classLoader);
            if (object2 != null) {
                void name2;
                object = object2;
                String string = name2.asString();
                boolean bl4 = false;
                boolean bl5 = false;
                Object p1 = object;
                boolean bl6 = false;
                pair = TuplesKt.to(string, p1);
            } else {
                pair = null;
            }
            if (pair == null) continue;
            Pair<String, Object> pair2 = pair;
            boolean bl7 = false;
            boolean bl8 = false;
            Pair<String, Object> it$iv$iv = pair2;
            boolean bl9 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        List list = (List)destination$iv$iv;
        return (Annotation)AnnotationConstructorCallerKt.createAnnotationInstance$default(clazz2, MapsKt.toMap(list), null, 4, null);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final Object toRuntimeValue(ConstantValue<?> $this$toRuntimeValue, ClassLoader classLoader) {
        Object object;
        ConstantValue<?> constantValue = $this$toRuntimeValue;
        if (constantValue instanceof AnnotationValue) {
            object = UtilKt.toAnnotationInstance((AnnotationDescriptor)((AnnotationValue)$this$toRuntimeValue).getValue());
            return object;
        } else {
            if (constantValue instanceof ArrayValue) {
                Iterable $this$map$iv = (Iterable)((ArrayValue)$this$toRuntimeValue).getValue();
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : iterable) {
                    void it;
                    ConstantValue constantValue2 = (ConstantValue)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    Object object2 = UtilKt.toRuntimeValue(it, classLoader);
                    collection.add(object2);
                }
                Collection $this$toTypedArray$iv = (List)destination$iv$iv;
                boolean $i$f$toTypedArray = false;
                Collection collection = $this$toTypedArray$iv;
                Object[] objectArray = collection.toArray(new Object[0]);
                object = objectArray;
                if (objectArray != null) return object;
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            if (constantValue instanceof EnumValue) {
                void enumClassId;
                Pair pair = (Pair)((EnumValue)$this$toRuntimeValue).getValue();
                ClassId $this$toTypedArray$iv = (ClassId)pair.component1();
                Name entryName = (Name)pair.component2();
                Class clazz = UtilKt.loadClass$default(classLoader, (ClassId)enumClassId, 0, 4, null);
                if (clazz == null) return null;
                Class clazz2 = clazz;
                boolean bl = false;
                boolean bl2 = false;
                Class clazz3 = clazz2;
                boolean bl3 = false;
                Class clazz4 = clazz3;
                if (clazz4 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out kotlin.Enum<*>>");
                }
                object = Util.getEnumConstantByName(clazz4, entryName.asString());
                return object;
            } else if (constantValue instanceof KClassValue) {
                KClassValue.Value classValue = (KClassValue.Value)((KClassValue)$this$toRuntimeValue).getValue();
                if (classValue instanceof KClassValue.Value.NormalClass) {
                    object = UtilKt.loadClass(classLoader, ((KClassValue.Value.NormalClass)classValue).getClassId(), ((KClassValue.Value.NormalClass)classValue).getArrayDimensions());
                    return object;
                } else {
                    if (!(classValue instanceof KClassValue.Value.LocalClass)) throw new NoWhenBranchMatchedException();
                    ClassifierDescriptor classifierDescriptor = ((KClassValue.Value.LocalClass)classValue).getType().getConstructor().getDeclarationDescriptor();
                    if (!(classifierDescriptor instanceof ClassDescriptor)) {
                        classifierDescriptor = null;
                    }
                    ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor;
                    if (classDescriptor == null) return null;
                    object = UtilKt.toJavaClass(classDescriptor);
                }
                return object;
            } else {
                if (constantValue instanceof ErrorValue || constantValue instanceof NullValue) {
                    return null;
                }
                object = $this$toRuntimeValue.getValue();
            }
        }
        return object;
    }

    @Nullable
    public static final KFunctionImpl asKFunctionImpl(@Nullable Object $this$asKFunctionImpl) {
        KFunctionImpl kFunctionImpl;
        Object object = $this$asKFunctionImpl;
        if (!(object instanceof KFunctionImpl)) {
            object = null;
        }
        if ((kFunctionImpl = (KFunctionImpl)object) == null) {
            Object object2 = $this$asKFunctionImpl;
            if (!(object2 instanceof FunctionReference)) {
                object2 = null;
            }
            FunctionReference functionReference = (FunctionReference)object2;
            KCallable kCallable = functionReference != null ? functionReference.compute() : null;
            if (!(kCallable instanceof KFunctionImpl)) {
                kCallable = null;
            }
            kFunctionImpl = (KFunctionImpl)kCallable;
        }
        return kFunctionImpl;
    }

    @Nullable
    public static final KPropertyImpl<?> asKPropertyImpl(@Nullable Object $this$asKPropertyImpl) {
        KPropertyImpl kPropertyImpl;
        Object object = $this$asKPropertyImpl;
        if (!(object instanceof KPropertyImpl)) {
            object = null;
        }
        if ((kPropertyImpl = (KPropertyImpl)object) == null) {
            Object object2 = $this$asKPropertyImpl;
            if (!(object2 instanceof PropertyReference)) {
                object2 = null;
            }
            PropertyReference propertyReference = (PropertyReference)object2;
            KCallable kCallable = propertyReference != null ? propertyReference.compute() : null;
            if (!(kCallable instanceof KPropertyImpl)) {
                kCallable = null;
            }
            kPropertyImpl = (KPropertyImpl)kCallable;
        }
        return kPropertyImpl;
    }

    @Nullable
    public static final KCallableImpl<?> asKCallableImpl(@Nullable Object $this$asKCallableImpl) {
        KCallableImpl kCallableImpl;
        KCallableImpl kCallableImpl2;
        Object object = $this$asKCallableImpl;
        if (!(object instanceof KCallableImpl)) {
            object = null;
        }
        if ((kCallableImpl2 = (KCallableImpl)object) == null) {
            kCallableImpl2 = kCallableImpl = (KCallableImpl)UtilKt.asKFunctionImpl($this$asKCallableImpl);
        }
        if (kCallableImpl2 == null) {
            kCallableImpl = UtilKt.asKPropertyImpl($this$asKCallableImpl);
        }
        return kCallableImpl;
    }

    @Nullable
    public static final ReceiverParameterDescriptor getInstanceReceiverParameter(@NotNull CallableDescriptor $this$instanceReceiverParameter) {
        ReceiverParameterDescriptor receiverParameterDescriptor;
        Intrinsics.checkNotNullParameter($this$instanceReceiverParameter, "$this$instanceReceiverParameter");
        if ($this$instanceReceiverParameter.getDispatchReceiverParameter() != null) {
            DeclarationDescriptor declarationDescriptor = $this$instanceReceiverParameter.getContainingDeclaration();
            if (declarationDescriptor == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            receiverParameterDescriptor = ((ClassDescriptor)declarationDescriptor).getThisAsReceiverParameter();
        } else {
            receiverParameterDescriptor = null;
        }
        return receiverParameterDescriptor;
    }

    @Nullable
    public static final <M extends MessageLite, D extends CallableDescriptor> D deserializeToDescriptor(@NotNull Class<?> moduleAnchor, @NotNull M proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull BinaryVersion metadataVersion, @NotNull Function2<? super MemberDeserializer, ? super M, ? extends D> createDescriptor) {
        List<ProtoBuf.TypeParameter> list;
        Intrinsics.checkNotNullParameter(moduleAnchor, "moduleAnchor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(createDescriptor, "createDescriptor");
        RuntimeModuleData moduleData2 = ModuleByClassLoaderKt.getOrCreateModule(moduleAnchor);
        M m = proto;
        if (m instanceof ProtoBuf.Function) {
            list = ((ProtoBuf.Function)proto).getTypeParameterList();
        } else if (m instanceof ProtoBuf.Property) {
            list = ((ProtoBuf.Property)proto).getTypeParameterList();
        } else {
            String string = "Unsupported message: " + proto;
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        List<ProtoBuf.TypeParameter> typeParameters2 = list;
        DeserializationComponents deserializationComponents = moduleData2.getDeserialization();
        DeclarationDescriptor declarationDescriptor = moduleData2.getModule();
        VersionRequirementTable versionRequirementTable = VersionRequirementTable.Companion.getEMPTY();
        List<ProtoBuf.TypeParameter> list2 = typeParameters2;
        Intrinsics.checkNotNullExpressionValue(list2, "typeParameters");
        DeserializationContext context = new DeserializationContext(deserializationComponents, nameResolver, declarationDescriptor, typeTable, versionRequirementTable, metadataVersion, null, null, list2);
        return (D)((CallableDescriptor)createDescriptor.invoke(new MemberDeserializer(context), proto));
    }

    public static final boolean isInlineClassType(@NotNull KType $this$isInlineClassType) {
        Object object;
        Intrinsics.checkNotNullParameter($this$isInlineClassType, "$this$isInlineClassType");
        KType kType = $this$isInlineClassType;
        if (!(kType instanceof KTypeImpl)) {
            kType = null;
        }
        return (object = (KTypeImpl)kType) != null && (object = ((KTypeImpl)object).getType()) != null && InlineClassesUtilsKt.isInlineClassType((KotlinType)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static final Object defaultPrimitiveValue(@NotNull Type type2) {
        Comparable<Boolean> comparable;
        Intrinsics.checkNotNullParameter(type2, "type");
        if (!(type2 instanceof Class)) return null;
        if (!((Class)type2).isPrimitive()) return null;
        Type type3 = type2;
        if (Intrinsics.areEqual(type3, Boolean.TYPE)) {
            comparable = false;
            return comparable;
        } else if (Intrinsics.areEqual(type3, Character.TYPE)) {
            comparable = Character.valueOf((char)0);
            return comparable;
        } else if (Intrinsics.areEqual(type3, Byte.TYPE)) {
            comparable = (byte)0;
            return comparable;
        } else if (Intrinsics.areEqual(type3, Short.TYPE)) {
            comparable = (short)0;
            return comparable;
        } else if (Intrinsics.areEqual(type3, Integer.TYPE)) {
            comparable = 0;
            return comparable;
        } else if (Intrinsics.areEqual(type3, Float.TYPE)) {
            comparable = Float.valueOf(0.0f);
            return comparable;
        } else if (Intrinsics.areEqual(type3, Long.TYPE)) {
            comparable = 0L;
            return comparable;
        } else if (Intrinsics.areEqual(type3, Double.TYPE)) {
            comparable = 0.0;
            return comparable;
        } else {
            if (!Intrinsics.areEqual(type3, Void.TYPE)) throw (Throwable)new UnsupportedOperationException("Unknown primitive: " + type2);
            throw (Throwable)new IllegalStateException("Parameter with void type is illegal");
        }
    }
}

