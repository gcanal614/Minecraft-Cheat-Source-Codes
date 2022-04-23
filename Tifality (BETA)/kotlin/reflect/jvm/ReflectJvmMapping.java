/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KType;
import kotlin.reflect.TypesJVMKt;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping$WhenMappings;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KPackageImpl;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010%\u001a\u0004\u0018\u00010&*\u00020'H\u0002\"/\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038F\u00a2\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u001b\u0010\b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u001b\u0010\u0014\u001a\u0004\u0018\u00010\u000e*\u0006\u0012\u0002\b\u00030\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u0015\u0010\u0018\u001a\u00020\u0019*\u00020\u001a8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\"-\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001e*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 \"\u001b\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003*\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010!\"\u001b\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n*\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b#\u0010$\u00a8\u0006("}, d2={"javaConstructor", "Ljava/lang/reflect/Constructor;", "T", "Lkotlin/reflect/KFunction;", "getJavaConstructor$annotations", "(Lkotlin/reflect/KFunction;)V", "getJavaConstructor", "(Lkotlin/reflect/KFunction;)Ljava/lang/reflect/Constructor;", "javaField", "Ljava/lang/reflect/Field;", "Lkotlin/reflect/KProperty;", "getJavaField", "(Lkotlin/reflect/KProperty;)Ljava/lang/reflect/Field;", "javaGetter", "Ljava/lang/reflect/Method;", "getJavaGetter", "(Lkotlin/reflect/KProperty;)Ljava/lang/reflect/Method;", "javaMethod", "getJavaMethod", "(Lkotlin/reflect/KFunction;)Ljava/lang/reflect/Method;", "javaSetter", "Lkotlin/reflect/KMutableProperty;", "getJavaSetter", "(Lkotlin/reflect/KMutableProperty;)Ljava/lang/reflect/Method;", "javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "kotlinFunction", "", "getKotlinFunction", "(Ljava/lang/reflect/Constructor;)Lkotlin/reflect/KFunction;", "(Ljava/lang/reflect/Method;)Lkotlin/reflect/KFunction;", "kotlinProperty", "getKotlinProperty", "(Ljava/lang/reflect/Field;)Lkotlin/reflect/KProperty;", "getKPackage", "Lkotlin/reflect/KDeclarationContainer;", "Ljava/lang/reflect/Member;", "kotlin-reflection"})
@JvmName(name="ReflectJvmMapping")
public final class ReflectJvmMapping {
    @Nullable
    public static final Field getJavaField(@NotNull KProperty<?> $this$javaField) {
        Intrinsics.checkNotNullParameter($this$javaField, "$this$javaField");
        KPropertyImpl<?> kPropertyImpl = UtilKt.asKPropertyImpl($this$javaField);
        return kPropertyImpl != null ? kPropertyImpl.getJavaField() : null;
    }

    @Nullable
    public static final Method getJavaGetter(@NotNull KProperty<?> $this$javaGetter) {
        Intrinsics.checkNotNullParameter($this$javaGetter, "$this$javaGetter");
        return ReflectJvmMapping.getJavaMethod((KFunction)$this$javaGetter.getGetter());
    }

    @Nullable
    public static final Method getJavaSetter(@NotNull KMutableProperty<?> $this$javaSetter) {
        Intrinsics.checkNotNullParameter($this$javaSetter, "$this$javaSetter");
        return ReflectJvmMapping.getJavaMethod((KFunction)$this$javaSetter.getSetter());
    }

    @Nullable
    public static final Method getJavaMethod(@NotNull KFunction<?> $this$javaMethod) {
        Object v1;
        Intrinsics.checkNotNullParameter($this$javaMethod, "$this$javaMethod");
        KCallableImpl<?> kCallableImpl = UtilKt.asKCallableImpl($this$javaMethod);
        if (!((kCallableImpl != null && (kCallableImpl = kCallableImpl.getCaller()) != null ? kCallableImpl.getMember() : (v1 = null)) instanceof Method)) {
            v1 = null;
        }
        return v1;
    }

    public static /* synthetic */ void getJavaConstructor$annotations(KFunction kFunction) {
    }

    @Nullable
    public static final <T> Constructor<T> getJavaConstructor(@NotNull KFunction<? extends T> $this$javaConstructor) {
        Object v1;
        Intrinsics.checkNotNullParameter($this$javaConstructor, "$this$javaConstructor");
        KCallableImpl<?> kCallableImpl = UtilKt.asKCallableImpl($this$javaConstructor);
        if (!((kCallableImpl != null && (kCallableImpl = kCallableImpl.getCaller()) != null ? kCallableImpl.getMember() : (v1 = null)) instanceof Constructor)) {
            v1 = null;
        }
        return v1;
    }

    @NotNull
    public static final Type getJavaType(@NotNull KType $this$javaType) {
        Intrinsics.checkNotNullParameter($this$javaType, "$this$javaType");
        Type type2 = ((KTypeImpl)$this$javaType).getJavaType();
        if (type2 == null) {
            type2 = TypesJVMKt.getJavaType($this$javaType);
        }
        return type2;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final KProperty<?> getKotlinProperty(@NotNull Field $this$kotlinProperty) {
        Object v1;
        block6: {
            Intrinsics.checkNotNullParameter($this$kotlinProperty, "$this$kotlinProperty");
            if ($this$kotlinProperty.isSynthetic()) {
                return null;
            }
            KDeclarationContainer kotlinPackage = ReflectJvmMapping.getKPackage($this$kotlinProperty);
            if (kotlinPackage != null) {
                Object v0;
                block5: {
                    void $this$filterIsInstanceTo$iv$iv;
                    Iterable $this$filterIsInstance$iv = kotlinPackage.getMembers();
                    boolean $i$f$filterIsInstance = false;
                    Iterable iterable = $this$filterIsInstance$iv;
                    Collection destination$iv$iv = new ArrayList();
                    boolean $i$f$filterIsInstanceTo = false;
                    for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                        if (!(element$iv$iv instanceof KProperty)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    Iterable $this$firstOrNull$iv = (List)destination$iv$iv;
                    boolean $i$f$firstOrNull = false;
                    for (Object element$iv : $this$firstOrNull$iv) {
                        KProperty it = (KProperty)element$iv;
                        boolean bl = false;
                        if (!Intrinsics.areEqual(ReflectJvmMapping.getJavaField(it), $this$kotlinProperty)) continue;
                        v0 = element$iv;
                        break block5;
                    }
                    v0 = null;
                }
                return v0;
            }
            Iterable $this$firstOrNull$iv = KClasses.getMemberProperties(JvmClassMappingKt.getKotlinClass($this$kotlinProperty.getDeclaringClass()));
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                KProperty1 it = (KProperty1)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual(ReflectJvmMapping.getJavaField(it), $this$kotlinProperty)) continue;
                v1 = element$iv;
                break block6;
            }
            v1 = null;
        }
        return v1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final KDeclarationContainer getKPackage(Member $this$getKPackage) {
        KPackageImpl kPackageImpl;
        Class<?> clazz = $this$getKPackage.getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(clazz, "declaringClass");
        Object object = ReflectKotlinClass.Factory.create(clazz);
        KotlinClassHeader.Kind kind = object != null && (object = ((ReflectKotlinClass)object).getClassHeader()) != null ? ((KotlinClassHeader)object).getKind() : null;
        if (kind != null) {
            switch (ReflectJvmMapping$WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1: 
                case 2: 
                case 3: {
                    Class<?> clazz2 = $this$getKPackage.getDeclaringClass();
                    Intrinsics.checkNotNullExpressionValue(clazz2, "declaringClass");
                    kPackageImpl = new KPackageImpl(clazz2, null, 2, null);
                    return kPackageImpl;
                }
            }
        }
        kPackageImpl = null;
        return kPackageImpl;
    }

    /*
     * Unable to fully structure code
     */
    @Nullable
    public static final KFunction<?> getKotlinFunction(@NotNull Method $this$kotlinFunction) {
        block10: {
            block11: {
                block9: {
                    Intrinsics.checkNotNullParameter($this$kotlinFunction, "$this$kotlinFunction");
                    if (!Modifier.isStatic($this$kotlinFunction.getModifiers())) break block11;
                    kotlinPackage = ReflectJvmMapping.getKPackage($this$kotlinFunction);
                    if (kotlinPackage != null) {
                        block8: {
                            $this$filterIsInstance$iv = kotlinPackage.getMembers();
                            $i$f$filterIsInstance = false;
                            var4_7 = $this$filterIsInstance$iv;
                            destination$iv$iv = new ArrayList<E>();
                            $i$f$filterIsInstanceTo = false;
                            var7_17 = $this$filterIsInstanceTo$iv$iv.iterator();
                            while (var7_17.hasNext()) {
                                element$iv$iv = var7_17.next();
                                if (!(element$iv$iv instanceof KFunction)) continue;
                                destination$iv$iv.add(element$iv$iv);
                            }
                            $this$firstOrNull$iv = (List)destination$iv$iv;
                            $i$f$firstOrNull = false;
                            for (T element$iv : $this$firstOrNull$iv) {
                                it = (KFunction)element$iv;
                                $i$a$-firstOrNull-ReflectJvmMapping$kotlinFunction$1 = false;
                                if (!Intrinsics.areEqual(ReflectJvmMapping.getJavaMethod(it), $this$kotlinFunction)) continue;
                                v0 = element$iv;
                                break block8;
                            }
                            v0 = null;
                        }
                        return v0;
                    }
                    companion = KClasses.getCompanionObject(JvmClassMappingKt.getKotlinClass($this$kotlinFunction.getDeclaringClass()));
                    if (companion == null) break block11;
                    $this$firstOrNull$iv = KClasses.getFunctions(companion);
                    $i$f$firstOrNull = false;
                    for (T element$iv : $this$firstOrNull$iv) {
                        it = (KFunction)element$iv;
                        $i$a$-firstOrNull-ReflectJvmMapping$kotlinFunction$2 = false;
                        m = ReflectJvmMapping.getJavaMethod(it);
                        if (m == null || !Intrinsics.areEqual(m.getName(), $this$kotlinFunction.getName())) ** GOTO lbl-1000
                        Intrinsics.checkNotNull(m.getParameterTypes());
                        var11_25 = $this$kotlinFunction.getParameterTypes();
                        var12_26 = false;
                        if (Arrays.equals(var10_24, var11_25) && Intrinsics.areEqual(m.getReturnType(), $this$kotlinFunction.getReturnType())) {
                            v1 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v1 = false;
                        }
                        if (!v1) continue;
                        v2 = element$iv;
                        break block9;
                    }
                    v2 = null;
                }
                v3 = v2;
                if (v3 != null) {
                    var3_6 = v3;
                    $i$f$firstOrNull = false;
                    element$iv = false;
                    it = var3_6;
                    $i$a$-let-ReflectJvmMapping$kotlinFunction$3 = false;
                    return it;
                }
            }
            $this$firstOrNull$iv = KClasses.getFunctions(JvmClassMappingKt.getKotlinClass($this$kotlinFunction.getDeclaringClass()));
            $i$f$firstOrNull = false;
            for (T element$iv : $this$firstOrNull$iv) {
                it = (KFunction)element$iv;
                $i$a$-firstOrNull-ReflectJvmMapping$kotlinFunction$4 = false;
                if (!Intrinsics.areEqual(ReflectJvmMapping.getJavaMethod(it), $this$kotlinFunction)) continue;
                v4 = element$iv;
                break block10;
            }
            v4 = null;
        }
        return v4;
    }

    @Nullable
    public static final <T> KFunction<T> getKotlinFunction(@NotNull Constructor<T> $this$kotlinFunction) {
        Object v1;
        block1: {
            Intrinsics.checkNotNullParameter($this$kotlinFunction, "$this$kotlinFunction");
            Class<T> clazz = $this$kotlinFunction.getDeclaringClass();
            Intrinsics.checkNotNullExpressionValue(clazz, "declaringClass");
            Iterable $this$firstOrNull$iv = JvmClassMappingKt.getKotlinClass(clazz).getConstructors();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                KFunction it = (KFunction)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual(ReflectJvmMapping.getJavaConstructor(it), $this$kotlinFunction)) continue;
                v1 = element$iv;
                break block1;
            }
            v1 = null;
        }
        return v1;
    }
}

