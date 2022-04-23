/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectAnnotationSource;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClassKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.SignatureSerializer;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;

final class ReflectClassStructure {
    public static final ReflectClassStructure INSTANCE;

    public final void loadClassAnnotations(@NotNull Class<?> klass, @NotNull KotlinJvmBinaryClass.AnnotationVisitor visitor2) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        Annotation[] annotationArray = klass.getDeclaredAnnotations();
        int n = annotationArray.length;
        for (int i = 0; i < n; ++i) {
            Annotation annotation;
            Annotation annotation2 = annotation = annotationArray[i];
            Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
            this.processAnnotation(visitor2, annotation2);
        }
        visitor2.visitEnd();
    }

    public final void visitMembers(@NotNull Class<?> klass, @NotNull KotlinJvmBinaryClass.MemberVisitor memberVisitor) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(memberVisitor, "memberVisitor");
        this.loadMethodAnnotations(klass, memberVisitor);
        this.loadConstructorAnnotations(klass, memberVisitor);
        this.loadFieldAnnotations(klass, memberVisitor);
    }

    /*
     * WARNING - void declaration
     */
    private final void loadMethodAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) {
        Method[] methodArray = klass.getDeclaredMethods();
        int n = methodArray.length;
        for (int i = 0; i < n; ++i) {
            KotlinJvmBinaryClass.MethodAnnotationVisitor visitor2;
            Method method;
            Method method2 = method = methodArray[i];
            Intrinsics.checkNotNullExpressionValue(method2, "method");
            Name name = Name.identifier(method2.getName());
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(method.name)");
            if (memberVisitor.visitMethod(name, SignatureSerializer.INSTANCE.methodDesc(method)) == null) {
                continue;
            }
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                KotlinJvmBinaryClass.AnnotationVisitor annotationVisitor = visitor2;
                Annotation annotation2 = annotation;
                Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                this.processAnnotation(annotationVisitor, annotation2);
            }
            Annotation[][] annotationArray = method.getParameterAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotationArray, "method.parameterAnnotations");
            Annotation[] annotationArray2 = annotationArray;
            int n2 = annotationArray2.length;
            int annotation = 0;
            while (annotation < n2) {
                void parameterIndex;
                Annotation annotations2;
                for (Annotation annotation3 : annotations2 = annotationArray2[annotation]) {
                    KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor;
                    Class<KClass<Annotation>> annotationType = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation3));
                    ClassId classId = ReflectClassUtilKt.getClassId(annotationType);
                    Annotation annotation4 = annotation3;
                    Intrinsics.checkNotNullExpressionValue(annotation4, "annotation");
                    if (visitor2.visitParameterAnnotation((int)parameterIndex, classId, new ReflectAnnotationSource(annotation4)) == null) continue;
                    boolean bl = false;
                    boolean bl2 = false;
                    KotlinJvmBinaryClass.AnnotationArgumentVisitor it = annotationArgumentVisitor;
                    boolean bl3 = false;
                    INSTANCE.processAnnotationArguments(it, annotation3, annotationType);
                }
                ++parameterIndex;
            }
            visitor2.visitEnd();
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void loadConstructorAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) {
        for (Constructor<?> constructor : klass.getDeclaredConstructors()) {
            KotlinJvmBinaryClass.MethodAnnotationVisitor visitor2;
            Name name = Name.special("<init>");
            Intrinsics.checkNotNullExpressionValue(name, "Name.special(\"<init>\")");
            Constructor<?> constructor2 = constructor;
            Intrinsics.checkNotNullExpressionValue(constructor2, "constructor");
            if (memberVisitor.visitMethod(name, SignatureSerializer.INSTANCE.constructorDesc(constructor2)) == null) {
                continue;
            }
            for (Annotation annotation : constructor.getDeclaredAnnotations()) {
                KotlinJvmBinaryClass.AnnotationVisitor annotationVisitor = visitor2;
                Annotation annotation2 = annotation;
                Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                this.processAnnotation(annotationVisitor, annotation2);
            }
            Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
            Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "parameterAnnotations");
            Object[] objectArray = (Object[])parameterAnnotations;
            int n = 0;
            Object[] objectArray2 = objectArray;
            boolean bl = false;
            if (!(objectArray2.length == 0)) {
                int shift = constructor.getParameterTypes().length - ((Object[])parameterAnnotations).length;
                Annotation[][] annotationArray = parameterAnnotations;
                int n2 = annotationArray.length;
                n = 0;
                while (n < n2) {
                    void parameterIndex;
                    Annotation[] annotations2;
                    for (Annotation annotation : annotations2 = annotationArray[n]) {
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor;
                        Class<KClass<Annotation>> annotationType = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation));
                        void v4 = parameterIndex + shift;
                        ClassId classId = ReflectClassUtilKt.getClassId(annotationType);
                        Annotation annotation3 = annotation;
                        Intrinsics.checkNotNullExpressionValue(annotation3, "annotation");
                        if (visitor2.visitParameterAnnotation((int)v4, classId, new ReflectAnnotationSource(annotation3)) == null) continue;
                        boolean bl2 = false;
                        boolean bl3 = false;
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor it = annotationArgumentVisitor;
                        boolean bl4 = false;
                        INSTANCE.processAnnotationArguments(it, annotation, annotationType);
                    }
                    ++parameterIndex;
                }
            }
            visitor2.visitEnd();
        }
    }

    private final void loadFieldAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) {
        Field[] fieldArray = klass.getDeclaredFields();
        int n = fieldArray.length;
        for (int i = 0; i < n; ++i) {
            KotlinJvmBinaryClass.AnnotationVisitor visitor2;
            Field field;
            Field field2 = field = fieldArray[i];
            Intrinsics.checkNotNullExpressionValue(field2, "field");
            Name name = Name.identifier(field2.getName());
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(field.name)");
            if (memberVisitor.visitField(name, SignatureSerializer.INSTANCE.fieldDesc(field), null) == null) {
                continue;
            }
            Annotation[] annotationArray = field.getDeclaredAnnotations();
            int n2 = annotationArray.length;
            for (int j = 0; j < n2; ++j) {
                Annotation annotation;
                Annotation annotation2 = annotation = annotationArray[j];
                Intrinsics.checkNotNullExpressionValue(annotation2, "annotation");
                this.processAnnotation(visitor2, annotation2);
            }
            visitor2.visitEnd();
        }
    }

    private final void processAnnotation(KotlinJvmBinaryClass.AnnotationVisitor visitor2, Annotation annotation) {
        block0: {
            Class<KClass<Annotation>> annotationType = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation));
            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor = visitor2.visitAnnotation(ReflectClassUtilKt.getClassId(annotationType), new ReflectAnnotationSource(annotation));
            if (annotationArgumentVisitor == null) break block0;
            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor2 = annotationArgumentVisitor;
            boolean bl = false;
            boolean bl2 = false;
            KotlinJvmBinaryClass.AnnotationArgumentVisitor it = annotationArgumentVisitor2;
            boolean bl3 = false;
            INSTANCE.processAnnotationArguments(it, annotation, annotationType);
        }
    }

    private final void processAnnotationArguments(KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor2, Annotation annotation, Class<?> annotationType) {
        for (Method method : annotationType.getDeclaredMethods()) {
            Object object;
            try {
                Intrinsics.checkNotNull(method.invoke(annotation, new Object[0]));
            }
            catch (IllegalAccessException e) {
                continue;
            }
            Object value = object;
            Method method2 = method;
            Intrinsics.checkNotNullExpressionValue(method2, "method");
            Name name = Name.identifier(method2.getName());
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(method.name)");
            this.processAnnotationArgumentValue(visitor2, name, value);
        }
        visitor2.visitEnd();
    }

    private final ClassLiteralValue classLiteralValue(Class<?> $this$classLiteralValue) {
        Class<?> currentClass = $this$classLiteralValue;
        int dimensions = 0;
        while (currentClass.isArray()) {
            ++dimensions;
            Intrinsics.checkNotNullExpressionValue(currentClass.getComponentType(), "currentClass.componentType");
        }
        if (currentClass.isPrimitive()) {
            if (Intrinsics.areEqual(currentClass, Void.TYPE)) {
                ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.unit.toSafe());
                Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026s.FQ_NAMES.unit.toSafe())");
                return new ClassLiteralValue(classId, dimensions);
            }
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(currentClass.getName());
            Intrinsics.checkNotNullExpressionValue((Object)jvmPrimitiveType, "JvmPrimitiveType.get(currentClass.name)");
            PrimitiveType primitiveType = jvmPrimitiveType.getPrimitiveType();
            Intrinsics.checkNotNullExpressionValue((Object)primitiveType, "JvmPrimitiveType.get(cur\u2026Class.name).primitiveType");
            PrimitiveType primitiveType2 = primitiveType;
            if (dimensions > 0) {
                ClassId classId = ClassId.topLevel(primitiveType2.getArrayTypeFqName());
                Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(primitiveType.arrayTypeFqName)");
                return new ClassLiteralValue(classId, dimensions - 1);
            }
            ClassId classId = ClassId.topLevel(primitiveType2.getTypeFqName());
            Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(primitiveType.typeFqName)");
            return new ClassLiteralValue(classId, dimensions);
        }
        ClassId javaClassId = ReflectClassUtilKt.getClassId(currentClass);
        FqName fqName2 = javaClassId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "javaClassId.asSingleFqName()");
        ClassId classId = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(fqName2);
        if (classId == null) {
            classId = javaClassId;
        }
        ClassId kotlinClassId = classId;
        return new ClassLiteralValue(kotlinClassId, dimensions);
    }

    private final void processAnnotationArgumentValue(KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor2, Name name, Object value) {
        Class<?> clazz = value.getClass();
        if (Intrinsics.areEqual(clazz, Class.class)) {
            Object object = value;
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<*>");
            }
            visitor2.visitClassLiteral(name, this.classLiteralValue((Class)object));
        } else if (ReflectKotlinClassKt.access$getTYPES_ELIGIBLE_FOR_SIMPLE_VISIT$p().contains(clazz)) {
            visitor2.visit(name, value);
        } else if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(clazz)) {
            Class<?> clazz2;
            if (clazz.isEnum()) {
                clazz2 = clazz;
            } else {
                Class<?> clazz3 = clazz.getEnclosingClass();
                clazz2 = clazz3;
                Intrinsics.checkNotNullExpressionValue(clazz3, "clazz.enclosingClass");
            }
            ClassId classId = ReflectClassUtilKt.getClassId(clazz2);
            Object object = value;
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Enum<*>");
            }
            Name name2 = Name.identifier(((Enum)object).name());
            Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier((value as Enum<*>).name)");
            visitor2.visitEnum(name, classId, name2);
        } else if (Annotation.class.isAssignableFrom(clazz)) {
            Class<?>[] classArray = clazz.getInterfaces();
            Intrinsics.checkNotNullExpressionValue(classArray, "clazz.interfaces");
            Class<?> annotationClass = ArraysKt.single(classArray);
            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor = visitor2.visitAnnotation(name, ReflectClassUtilKt.getClassId(annotationClass));
            if (annotationArgumentVisitor == null) {
                return;
            }
            KotlinJvmBinaryClass.AnnotationArgumentVisitor v = annotationArgumentVisitor;
            Object object = value;
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Annotation");
            }
            Annotation annotation = (Annotation)object;
            Class<?> clazz4 = annotationClass;
            Intrinsics.checkNotNullExpressionValue(clazz4, "annotationClass");
            this.processAnnotationArguments(v, annotation, clazz4);
        } else if (clazz.isArray()) {
            KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor annotationArrayArgumentVisitor = visitor2.visitArray(name);
            if (annotationArrayArgumentVisitor == null) {
                return;
            }
            KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor v = annotationArrayArgumentVisitor;
            Class<?> componentType = clazz.getComponentType();
            if (componentType.isEnum()) {
                ClassId enumClassId = ReflectClassUtilKt.getClassId(componentType);
                Object object = value;
                if (object == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<*>");
                }
                Object[] objectArray = (Object[])object;
                int n = objectArray.length;
                for (int i = 0; i < n; ++i) {
                    Object element;
                    Object object2 = element = objectArray[i];
                    if (object2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Enum<*>");
                    }
                    Name name3 = Name.identifier(((Enum)object2).name());
                    Intrinsics.checkNotNullExpressionValue(name3, "Name.identifier((element as Enum<*>).name)");
                    v.visitEnum(enumClassId, name3);
                }
            } else if (Intrinsics.areEqual(componentType, Class.class)) {
                Object object = value;
                if (object == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<*>");
                }
                Object[] objectArray = (Object[])object;
                int n = objectArray.length;
                for (int i = 0; i < n; ++i) {
                    Object element;
                    Object object3 = element = objectArray[i];
                    if (object3 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<*>");
                    }
                    v.visitClassLiteral(this.classLiteralValue((Class)object3));
                }
            } else {
                Object object = value;
                if (object == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<*>");
                }
                for (Object element : (Object[])object) {
                    v.visit(element);
                }
            }
            v.visitEnd();
        } else {
            throw (Throwable)new UnsupportedOperationException("Unsupported annotation argument value (" + clazz + "): " + value);
        }
    }

    private ReflectClassStructure() {
    }

    static {
        ReflectClassStructure reflectClassStructure;
        INSTANCE = reflectClassStructure = new ReflectClassStructure();
    }
}

