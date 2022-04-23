/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectClassUtilKt {
    private static final List<KClass<? extends Object>> PRIMITIVE_CLASSES;
    private static final Map<Class<? extends Object>, Class<? extends Object>> WRAPPER_TO_PRIMITIVE;
    private static final Map<Class<? extends Object>, Class<? extends Object>> PRIMITIVE_TO_WRAPPER;
    private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;

    @NotNull
    public static final ClassLoader getSafeClassLoader(@NotNull Class<?> $this$safeClassLoader) {
        Intrinsics.checkNotNullParameter($this$safeClassLoader, "$this$safeClassLoader");
        ClassLoader classLoader = $this$safeClassLoader.getClassLoader();
        if (classLoader == null) {
            ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
            classLoader = classLoader2;
            Intrinsics.checkNotNullExpressionValue(classLoader2, "ClassLoader.getSystemClassLoader()");
        }
        return classLoader;
    }

    public static final boolean isEnumClassOrSpecializedEnumEntryClass(@NotNull Class<?> $this$isEnumClassOrSpecializedEnumEntryClass) {
        Intrinsics.checkNotNullParameter($this$isEnumClassOrSpecializedEnumEntryClass, "$this$isEnumClassOrSpecializedEnumEntryClass");
        return Enum.class.isAssignableFrom($this$isEnumClassOrSpecializedEnumEntryClass);
    }

    @Nullable
    public static final Class<?> getPrimitiveByWrapper(@NotNull Class<?> $this$primitiveByWrapper) {
        Intrinsics.checkNotNullParameter($this$primitiveByWrapper, "$this$primitiveByWrapper");
        return WRAPPER_TO_PRIMITIVE.get($this$primitiveByWrapper);
    }

    @Nullable
    public static final Class<?> getWrapperByPrimitive(@NotNull Class<?> $this$wrapperByPrimitive) {
        Intrinsics.checkNotNullParameter($this$wrapperByPrimitive, "$this$wrapperByPrimitive");
        return PRIMITIVE_TO_WRAPPER.get($this$wrapperByPrimitive);
    }

    @Nullable
    public static final Integer getFunctionClassArity(@NotNull Class<?> $this$functionClassArity) {
        Intrinsics.checkNotNullParameter($this$functionClassArity, "$this$functionClassArity");
        Map<Class<Function<?>>, Integer> map2 = FUNCTION_CLASSES;
        boolean bl = false;
        return map2.get($this$functionClassArity);
    }

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public static final ClassId getClassId(@NotNull Class<?> $this$classId) {
        Object object;
        block7: {
            block6: {
                Intrinsics.checkNotNullParameter($this$classId, "$this$classId");
                if ($this$classId.isPrimitive()) {
                    throw (Throwable)new IllegalArgumentException("Can't compute ClassId for primitive type: " + $this$classId);
                }
                if ($this$classId.isArray()) {
                    throw (Throwable)new IllegalArgumentException("Can't compute ClassId for array type: " + $this$classId);
                }
                if ($this$classId.getEnclosingMethod() != null || $this$classId.getEnclosingConstructor() != null) break block6;
                String string = $this$classId.getSimpleName();
                Intrinsics.checkNotNullExpressionValue(string, "simpleName");
                CharSequence charSequence = string;
                boolean bl = false;
                if (!(charSequence.length() == 0)) break block7;
            }
            FqName fqName2 = new FqName($this$classId.getName());
            object = new ClassId(fqName2.parent(), FqName.topLevel(fqName2.shortName()), true);
            return object;
        }
        Class<?> clazz = $this$classId.getDeclaringClass();
        if (clazz == null || (clazz = ReflectClassUtilKt.getClassId(clazz)) == null || (clazz = ((ClassId)((Object)clazz)).createNestedClassId(Name.identifier($this$classId.getSimpleName()))) == null) {
            clazz = ClassId.topLevel(new FqName($this$classId.getName()));
        }
        object = clazz;
        Intrinsics.checkNotNullExpressionValue(clazz, "declaringClass?.classId?\u2026Id.topLevel(FqName(name))");
        return object;
    }

    @NotNull
    public static final String getDesc(@NotNull Class<?> $this$desc) {
        Intrinsics.checkNotNullParameter($this$desc, "$this$desc");
        if (Intrinsics.areEqual($this$desc, Void.TYPE)) {
            return "V";
        }
        String string = ReflectClassUtilKt.createArrayType($this$desc).getName();
        Intrinsics.checkNotNullExpressionValue(string, "createArrayType().name");
        String string2 = string;
        int n = 1;
        boolean bl = false;
        String string3 = string2;
        if (string3 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string4 = string3.substring(n);
        Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).substring(startIndex)");
        return StringsKt.replace$default(string4, '.', '/', false, 4, null);
    }

    @NotNull
    public static final Class<?> createArrayType(@NotNull Class<?> $this$createArrayType) {
        Intrinsics.checkNotNullParameter($this$createArrayType, "$this$createArrayType");
        return Array.newInstance($this$createArrayType, 0).getClass();
    }

    @NotNull
    public static final List<Type> getParameterizedTypeArguments(@NotNull Type $this$parameterizedTypeArguments) {
        Intrinsics.checkNotNullParameter($this$parameterizedTypeArguments, "$this$parameterizedTypeArguments");
        if (!($this$parameterizedTypeArguments instanceof ParameterizedType)) {
            return CollectionsKt.emptyList();
        }
        if (((ParameterizedType)$this$parameterizedTypeArguments).getOwnerType() == null) {
            Type[] typeArray = ((ParameterizedType)$this$parameterizedTypeArguments).getActualTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArray, "actualTypeArguments");
            return ArraysKt.toList(typeArray);
        }
        return SequencesKt.toList(SequencesKt.flatMap(SequencesKt.generateSequence($this$parameterizedTypeArguments, (Function1)parameterizedTypeArguments.1.INSTANCE), parameterizedTypeArguments.2.INSTANCE));
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_3;
        void $this$mapIndexedTo$iv$iv;
        Pair<Object, Serializable> pair;
        KClass it;
        Collection collection;
        Iterable $this$mapTo$iv$iv;
        PRIMITIVE_CLASSES = CollectionsKt.listOf(Reflection.getOrCreateKotlinClass(Boolean.TYPE), Reflection.getOrCreateKotlinClass(Byte.TYPE), Reflection.getOrCreateKotlinClass(Character.TYPE), Reflection.getOrCreateKotlinClass(Double.TYPE), Reflection.getOrCreateKotlinClass(Float.TYPE), Reflection.getOrCreateKotlinClass(Integer.TYPE), Reflection.getOrCreateKotlinClass(Long.TYPE), Reflection.getOrCreateKotlinClass(Short.TYPE));
        Iterable $this$map$iv = PRIMITIVE_CLASSES;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            KClass kClass = (KClass)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            pair = TuplesKt.to(JvmClassMappingKt.getJavaObjectType(it), JvmClassMappingKt.getJavaPrimitiveType(it));
            collection.add(pair);
        }
        WRAPPER_TO_PRIMITIVE = MapsKt.toMap((List)destination$iv$iv);
        $this$map$iv = PRIMITIVE_CLASSES;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (KClass)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            pair = TuplesKt.to(JvmClassMappingKt.getJavaPrimitiveType(it), JvmClassMappingKt.getJavaObjectType(it));
            collection.add(pair);
        }
        PRIMITIVE_TO_WRAPPER = MapsKt.toMap((List)destination$iv$iv);
        Iterable $this$mapIndexed$iv = CollectionsKt.listOf(Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class);
        boolean $i$f$mapIndexed = false;
        $this$mapTo$iv$iv = $this$mapIndexed$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            void i;
            void clazz;
            int n = index$iv$iv++;
            boolean bl = false;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Class clazz2 = (Class)item$iv$iv;
            int n2 = n;
            collection = destination$iv$iv;
            boolean bl2 = false;
            pair = TuplesKt.to(clazz, (int)i);
            collection.add(pair);
        }
        FUNCTION_CLASSES = MapsKt.toMap((List)var3_3);
    }
}

