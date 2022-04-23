/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.calls;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u00004\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u001aI\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0000\u00a2\u0006\u0002\u0010\u000b\u001a$\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00072\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0002\u001a\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0002*\u0004\u0018\u00010\u00022\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0002\u00a8\u0006\u0014"}, d2={"createAnnotationInstance", "T", "", "annotationClass", "Ljava/lang/Class;", "values", "", "", "methods", "", "Ljava/lang/reflect/Method;", "(Ljava/lang/Class;Ljava/util/Map;Ljava/util/List;)Ljava/lang/Object;", "throwIllegalArgumentType", "", "index", "", "name", "expectedJvmType", "transformKotlinToJvm", "expectedType", "kotlin-reflection"})
public final class AnnotationConstructorCallerKt {
    /*
     * WARNING - void declaration
     */
    private static final Object transformKotlinToJvm(Object $this$transformKotlinToJvm, Class<?> expectedType) {
        Object[] objectArray;
        Object object = $this$transformKotlinToJvm;
        if (object instanceof Class) {
            return null;
        }
        if (object instanceof KClass) {
            objectArray = JvmClassMappingKt.getJavaClass((KClass)$this$transformKotlinToJvm);
        } else if (object instanceof Object[]) {
            if ((Object[])$this$transformKotlinToJvm instanceof Class[]) {
                return null;
            }
            if ((Object[])$this$transformKotlinToJvm instanceof KClass[]) {
                void $this$mapTo$iv$iv;
                Object object2 = $this$transformKotlinToJvm;
                if (object2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.reflect.KClass<*>>");
                }
                KClass[] $this$map$iv = (KClass[])object2;
                boolean $i$f$map = false;
                KClass[] kClassArray = $this$map$iv;
                Collection destination$iv$iv = new ArrayList($this$map$iv.length);
                boolean $i$f$mapTo = false;
                void var9_8 = $this$mapTo$iv$iv;
                int n = ((void)var9_8).length;
                for (int i = 0; i < n; ++i) {
                    void receiver;
                    void item$iv$iv;
                    void var13_12 = item$iv$iv = var9_8[i];
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    Class<void> clazz = JvmClassMappingKt.getJavaClass(receiver);
                    collection.add(clazz);
                }
                Collection $this$toTypedArray$iv = (List)destination$iv$iv;
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                objectArray = thisCollection$iv.toArray(new Class[0]);
                if (objectArray == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            } else {
                objectArray = (Object[])$this$transformKotlinToJvm;
            }
        } else {
            objectArray = $this$transformKotlinToJvm;
        }
        Object[] result2 = objectArray;
        return expectedType.isInstance(result2) ? result2 : null;
    }

    private static final Void throwIllegalArgumentType(int index, String name, Class<?> expectedJvmType) {
        KClass kotlinClass2 = Intrinsics.areEqual(expectedJvmType, Class.class) ? Reflection.getOrCreateKotlinClass(KClass.class) : (expectedJvmType.isArray() && Intrinsics.areEqual(expectedJvmType.getComponentType(), Class.class) ? Reflection.getOrCreateKotlinClass(KClass[].class) : JvmClassMappingKt.getKotlinClass(expectedJvmType));
        String typeString = Intrinsics.areEqual(kotlinClass2.getQualifiedName(), Reflection.getOrCreateKotlinClass(Object[].class).getQualifiedName()) ? kotlinClass2.getQualifiedName() + '<' + JvmClassMappingKt.getKotlinClass(JvmClassMappingKt.getJavaClass(kotlinClass2).getComponentType()).getQualifiedName() + '>' : kotlinClass2.getQualifiedName();
        throw (Throwable)new IllegalArgumentException("Argument #" + index + ' ' + name + " is not of the required type " + typeString);
    }

    @NotNull
    public static final <T> T createAnnotationInstance(@NotNull Class<T> annotationClass, @NotNull Map<String, ? extends Object> values2, @NotNull List<Method> methods2) {
        Object result2;
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(values2, "values");
        Intrinsics.checkNotNullParameter(methods2, "methods");
        Function1<Object, Boolean> $fun$equals$2 = new Function1<Object, Boolean>(annotationClass, methods2, values2){
            final /* synthetic */ Class $annotationClass;
            final /* synthetic */ List $methods;
            final /* synthetic */ Map $values;

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public final boolean invoke(@Nullable Object other) {
                boolean bl;
                Object object;
                Object object2 = other;
                if (!(object2 instanceof Annotation)) {
                    object2 = null;
                }
                if (!Intrinsics.areEqual((object = (Annotation)object2) != null && (object = JvmClassMappingKt.getAnnotationClass(object)) != null ? JvmClassMappingKt.getJavaClass(object) : null, this.$annotationClass)) return false;
                Iterable $this$all$iv = this.$methods;
                boolean $i$f$all = false;
                if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                    return true;
                }
                Iterator<T> iterator2 = $this$all$iv.iterator();
                do {
                    if (!iterator2.hasNext()) return true;
                    T element$iv = iterator2.next();
                    Method method = (Method)element$iv;
                    boolean bl2 = false;
                    V ours = this.$values.get(method.getName());
                    Object theirs = method.invoke(other, new Object[0]);
                    V v = ours;
                    if (v instanceof boolean[]) {
                        Object object3 = theirs;
                        if (object3 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.BooleanArray");
                        }
                        bl = Arrays.equals((boolean[])ours, (boolean[])object3);
                        continue;
                    }
                    if (v instanceof char[]) {
                        Object object4 = theirs;
                        if (object4 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.CharArray");
                        }
                        bl = Arrays.equals((char[])ours, (char[])object4);
                        continue;
                    }
                    if (v instanceof byte[]) {
                        Object object5 = theirs;
                        if (object5 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.ByteArray");
                        }
                        bl = Arrays.equals((byte[])ours, (byte[])object5);
                        continue;
                    }
                    if (v instanceof short[]) {
                        Object object6 = theirs;
                        if (object6 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.ShortArray");
                        }
                        bl = Arrays.equals((short[])ours, (short[])object6);
                        continue;
                    }
                    if (v instanceof int[]) {
                        Object object7 = theirs;
                        if (object7 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.IntArray");
                        }
                        bl = Arrays.equals((int[])ours, (int[])object7);
                        continue;
                    }
                    if (v instanceof float[]) {
                        Object object8 = theirs;
                        if (object8 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.FloatArray");
                        }
                        bl = Arrays.equals((float[])ours, (float[])object8);
                        continue;
                    }
                    if (v instanceof long[]) {
                        Object object9 = theirs;
                        if (object9 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.LongArray");
                        }
                        bl = Arrays.equals((long[])ours, (long[])object9);
                        continue;
                    }
                    if (v instanceof double[]) {
                        Object object10 = theirs;
                        if (object10 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.DoubleArray");
                        }
                        bl = Arrays.equals((double[])ours, (double[])object10);
                        continue;
                    }
                    if (v instanceof Object[]) {
                        Object object11 = theirs;
                        if (object11 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<*>");
                        }
                        bl = Arrays.equals((Object[])ours, (Object[])object11);
                        continue;
                    }
                    bl = Intrinsics.areEqual(ours, theirs);
                } while (bl);
                return false;
            }
            {
                this.$annotationClass = clazz;
                this.$methods = list;
                this.$values = map2;
                super(1);
            }
        };
        KProperty kProperty = null;
        Lazy hashCode2 = LazyKt.lazy((Function0)new Function0<Integer>(values2){
            final /* synthetic */ Map $values;

            /*
             * WARNING - void declaration
             */
            public final int invoke() {
                void var3_3;
                Iterable $this$sumBy$iv = this.$values.entrySet();
                boolean $i$f$sumBy = false;
                int sum$iv = 0;
                for (T element$iv : $this$sumBy$iv) {
                    void key;
                    void entry;
                    Map.Entry entry2 = (Map.Entry)element$iv;
                    int n = sum$iv;
                    boolean bl = false;
                    void var8_9 = entry;
                    V v = var8_9;
                    boolean bl2 = false;
                    String string = (String)v.getKey();
                    v = var8_9;
                    bl2 = false;
                    V value = v.getValue();
                    int valueHash = (v = value) instanceof boolean[] ? Arrays.hashCode((boolean[])value) : (v instanceof char[] ? Arrays.hashCode((char[])value) : (v instanceof byte[] ? Arrays.hashCode((byte[])value) : (v instanceof short[] ? Arrays.hashCode((short[])value) : (v instanceof int[] ? Arrays.hashCode((int[])value) : (v instanceof float[] ? Arrays.hashCode((float[])value) : (v instanceof long[] ? Arrays.hashCode((long[])value) : (v instanceof double[] ? Arrays.hashCode((double[])value) : (v instanceof Object[] ? Arrays.hashCode((Object[])value) : value.hashCode()))))))));
                    int n2 = 127 * key.hashCode() ^ valueHash;
                    sum$iv = n + n2;
                }
                return (int)var3_3;
            }
            {
                this.$values = map2;
                super(0);
            }
        });
        KProperty kProperty2 = null;
        Lazy toString2 = LazyKt.lazy((Function0)new Function0<String>(annotationClass, values2){
            final /* synthetic */ Class $annotationClass;
            final /* synthetic */ Map $values;

            @NotNull
            public final String invoke() {
                boolean bl = false;
                boolean bl2 = false;
                StringBuilder stringBuilder = new StringBuilder();
                boolean bl3 = false;
                boolean bl4 = false;
                StringBuilder $this$buildString = stringBuilder;
                boolean bl5 = false;
                $this$buildString.append('@');
                $this$buildString.append(this.$annotationClass.getCanonicalName());
                CollectionsKt.joinTo$default(this.$values.entrySet(), $this$buildString, ", ", "(", ")", 0, null, createAnnotationInstance.toString.1.1.INSTANCE, 48, null);
                String string = stringBuilder.toString();
                Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
                return string;
            }
            {
                this.$annotationClass = clazz;
                this.$values = map2;
                super(0);
            }
        });
        Object object = result2 = Proxy.newProxyInstance(annotationClass.getClassLoader(), new Class[]{annotationClass}, new InvocationHandler(annotationClass, toString2, kProperty2, hashCode2, kProperty, $fun$equals$2, values2){
            final /* synthetic */ Class $annotationClass;
            final /* synthetic */ Lazy $toString;
            final /* synthetic */ KProperty $toString$metadata;
            final /* synthetic */ Lazy $hashCode;
            final /* synthetic */ KProperty $hashCode$metadata;
            final /* synthetic */ createAnnotationInstance.2 $equals$2;
            final /* synthetic */ Map $values;

            /*
             * Enabled aggressive block sorting
             */
            public final Object invoke(Object $noName_0, Method method, Object[] args2) {
                Serializable serializable;
                String name;
                Method method2 = method;
                Intrinsics.checkNotNullExpressionValue(method2, "method");
                String string = name = method2.getName();
                if (string != null) {
                    switch (string) {
                        case "annotationType": {
                            serializable = this.$annotationClass;
                            return serializable;
                        }
                        case "toString": {
                            Lazy lazy = this.$toString;
                            Object var7_9 = null;
                            KProperty kProperty = this.$toString$metadata;
                            boolean bl = false;
                            serializable = lazy.getValue();
                            return serializable;
                        }
                        case "hashCode": {
                            Lazy lazy = this.$hashCode;
                            Object var7_10 = null;
                            KProperty kProperty = this.$hashCode$metadata;
                            boolean bl = false;
                            serializable = lazy.getValue();
                            return serializable;
                        }
                    }
                }
                if (Intrinsics.areEqual(name, "equals") && args2 != null && args2.length == 1) {
                    serializable = Boolean.valueOf(this.$equals$2.invoke(ArraysKt.single(args2)));
                    return serializable;
                }
                if (this.$values.containsKey(name)) {
                    serializable = this.$values.get(name);
                    return serializable;
                }
                StringBuilder stringBuilder = new StringBuilder().append("Method is not supported: ").append(method).append(" (args: ");
                Object[] $this$orEmpty$iv = args2;
                boolean $i$f$orEmpty = false;
                Object[] objectArray = $this$orEmpty$iv;
                if ($this$orEmpty$iv != null) {
                    throw (Throwable)new KotlinReflectionInternalError(stringBuilder.append(ArraysKt.toList(objectArray)).append(')').toString());
                }
                objectArray = new Object[]{};
                throw (Throwable)new KotlinReflectionInternalError(stringBuilder.append(ArraysKt.toList(objectArray)).append(')').toString());
            }
            {
                this.$annotationClass = clazz;
                this.$toString = lazy;
                this.$toString$metadata = kProperty;
                this.$hashCode = lazy2;
                this.$hashCode$metadata = kProperty2;
                this.$equals$2 = var6_6;
                this.$values = map2;
            }
        });
        if (object == null) {
            throw new NullPointerException("null cannot be cast to non-null type T");
        }
        return (T)object;
    }

    /*
     * WARNING - void declaration
     */
    public static /* synthetic */ Object createAnnotationInstance$default(Class clazz, Map map2, List list, int n, Object object) {
        if ((n & 4) != 0) {
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = map2.keySet();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void name;
                String string = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                Method method = clazz.getDeclaredMethod((String)name, new Class[0]);
                collection.add(method);
            }
            list = (List)destination$iv$iv;
        }
        return AnnotationConstructorCallerKt.createAnnotationInstance(clazz, map2, list);
    }

    public static final /* synthetic */ Object access$transformKotlinToJvm(Object $this$access_u24transformKotlinToJvm, Class expectedType) {
        return AnnotationConstructorCallerKt.transformKotlinToJvm($this$access_u24transformKotlinToJvm, expectedType);
    }

    public static final /* synthetic */ Void access$throwIllegalArgumentType(int index, String name, Class expectedJvmType) {
        return AnnotationConstructorCallerKt.throwIllegalArgumentType(index, name, expectedJvmType);
    }
}

