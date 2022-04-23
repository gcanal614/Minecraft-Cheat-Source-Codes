/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.full;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.full.KClasses$isSubclassOf$1;
import kotlin.reflect.full.KClasses$sam$org_jetbrains_kotlin_utils_DFS_Neighbors$0;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000Z\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\r\u001a+\u0010S\u001a\u0002H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00022\b\u0010T\u001a\u0004\u0018\u00010\u0010H\u0007\u00a2\u0006\u0002\u0010U\u001a!\u0010V\u001a\u0002H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u0002H\u0007\u00a2\u0006\u0002\u0010\u0013\u001a\u001c\u0010W\u001a\u000203*\u0006\u0012\u0002\b\u00030\u00022\n\u0010X\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0007\u001a\u001c\u0010Y\u001a\u000203*\u0006\u0012\u0002\b\u00030\u00022\n\u0010Z\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0007\u001a-\u0010[\u001a\u0004\u0018\u0001H\u001d\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00022\b\u0010T\u001a\u0004\u0018\u00010\u0010H\u0007\u00a2\u0006\u0002\u0010U\",\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"(\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u000e\"$\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0013\",\u0010\u0014\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0016\u0010\u0004\u001a\u0004\b\u0017\u0010\u0006\",\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0019\u0010\u0004\u001a\u0004\b\u001a\u0010\u0006\"B\u0010\u001b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001c0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u001e\u0010\u0004\u001a\u0004\b\u001f\u0010\u0006\",\u0010 \u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b!\u0010\u0004\u001a\u0004\b\"\u0010\u0006\">\u0010#\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u00030$0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b%\u0010\u0004\u001a\u0004\b&\u0010\u0006\",\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b)\u0010\u0004\u001a\u0004\b*\u0010\u0006\"\"\u0010+\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b,\u0010\u0004\u001a\u0004\b-\u0010.\",\u0010/\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b0\u0010\u0004\u001a\u0004\b1\u0010\u0006\"\u001c\u00102\u001a\u000203*\u0006\u0012\u0002\b\u0003048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u00105\"\u001c\u00106\u001a\u000203*\u0006\u0012\u0002\b\u0003048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b6\u00105\",\u00107\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b8\u0010\u0004\u001a\u0004\b9\u0010\u0006\"B\u0010:\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001c0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b;\u0010\u0004\u001a\u0004\b<\u0010\u0006\",\u0010=\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b>\u0010\u0004\u001a\u0004\b?\u0010\u0006\">\u0010@\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002H\u001d\u0012\u0002\b\u00030$0\u0001\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bA\u0010\u0004\u001a\u0004\bB\u0010\u0006\"6\u0010C\u001a\n\u0012\u0004\u0012\u0002H\u001d\u0018\u00010\u0015\"\b\b\u0000\u0010\u001d*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u001d0\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bD\u0010\u0004\u001a\u0004\bE\u0010F\",\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bH\u0010\u0004\u001a\u0004\bI\u0010\u0006\",\u0010J\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030K0\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bL\u0010\u0004\u001a\u0004\bM\u0010\u0006\",\u0010N\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020O*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bP\u0010\u0004\u001a\u0004\bQ\u0010R\u00a8\u0006\\"}, d2={"allSuperclasses", "", "Lkotlin/reflect/KClass;", "getAllSuperclasses$annotations", "(Lkotlin/reflect/KClass;)V", "getAllSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/Collection;", "allSupertypes", "Lkotlin/reflect/KType;", "getAllSupertypes$annotations", "getAllSupertypes", "companionObject", "getCompanionObject$annotations", "getCompanionObject", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KClass;", "companionObjectInstance", "", "getCompanionObjectInstance$annotations", "getCompanionObjectInstance", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "declaredFunctions", "Lkotlin/reflect/KFunction;", "getDeclaredFunctions$annotations", "getDeclaredFunctions", "declaredMemberExtensionFunctions", "getDeclaredMemberExtensionFunctions$annotations", "getDeclaredMemberExtensionFunctions", "declaredMemberExtensionProperties", "Lkotlin/reflect/KProperty2;", "T", "getDeclaredMemberExtensionProperties$annotations", "getDeclaredMemberExtensionProperties", "declaredMemberFunctions", "getDeclaredMemberFunctions$annotations", "getDeclaredMemberFunctions", "declaredMemberProperties", "Lkotlin/reflect/KProperty1;", "getDeclaredMemberProperties$annotations", "getDeclaredMemberProperties", "declaredMembers", "Lkotlin/reflect/KCallable;", "getDeclaredMembers$annotations", "getDeclaredMembers", "defaultType", "getDefaultType$annotations", "getDefaultType", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KType;", "functions", "getFunctions$annotations", "getFunctions", "isExtension", "", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "(Lkotlin/reflect/jvm/internal/KCallableImpl;)Z", "isNotExtension", "memberExtensionFunctions", "getMemberExtensionFunctions$annotations", "getMemberExtensionFunctions", "memberExtensionProperties", "getMemberExtensionProperties$annotations", "getMemberExtensionProperties", "memberFunctions", "getMemberFunctions$annotations", "getMemberFunctions", "memberProperties", "getMemberProperties$annotations", "getMemberProperties", "primaryConstructor", "getPrimaryConstructor$annotations", "getPrimaryConstructor", "(Lkotlin/reflect/KClass;)Lkotlin/reflect/KFunction;", "staticFunctions", "getStaticFunctions$annotations", "getStaticFunctions", "staticProperties", "Lkotlin/reflect/KProperty0;", "getStaticProperties$annotations", "getStaticProperties", "superclasses", "", "getSuperclasses$annotations", "getSuperclasses", "(Lkotlin/reflect/KClass;)Ljava/util/List;", "cast", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "createInstance", "isSubclassOf", "base", "isSuperclassOf", "derived", "safeCast", "kotlin-reflection"})
@JvmName(name="KClasses")
public final class KClasses {
    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getPrimaryConstructor$annotations(KClass kClass) {
    }

    @Nullable
    public static final <T> KFunction<T> getPrimaryConstructor(@NotNull KClass<T> $this$primaryConstructor) {
        Object v2;
        block3: {
            Intrinsics.checkNotNullParameter($this$primaryConstructor, "$this$primaryConstructor");
            Iterable $this$firstOrNull$iv = ((KClassImpl)$this$primaryConstructor).getConstructors();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                KFunction it = (KFunction)element$iv;
                boolean bl = false;
                KFunction kFunction = it;
                if (kFunction == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KFunctionImpl");
                }
                FunctionDescriptor functionDescriptor = ((KFunctionImpl)kFunction).getDescriptor();
                if (functionDescriptor == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ConstructorDescriptor");
                }
                if (!((ConstructorDescriptor)functionDescriptor).isPrimary()) continue;
                v2 = element$iv;
                break block3;
            }
            v2 = null;
        }
        return v2;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getCompanionObject$annotations(KClass kClass) {
    }

    @Nullable
    public static final KClass<?> getCompanionObject(@NotNull KClass<?> $this$companionObject) {
        Object v1;
        block2: {
            Intrinsics.checkNotNullParameter($this$companionObject, "$this$companionObject");
            Iterable $this$firstOrNull$iv = $this$companionObject.getNestedClasses();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                KClass it = (KClass)element$iv;
                boolean bl = false;
                KClass kClass = it;
                if (kClass == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
                }
                if (!((KClassImpl)kClass).getDescriptor().isCompanionObject()) continue;
                v1 = element$iv;
                break block2;
            }
            v1 = null;
        }
        return v1;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getCompanionObjectInstance$annotations(KClass kClass) {
    }

    @Nullable
    public static final Object getCompanionObjectInstance(@NotNull KClass<?> $this$companionObjectInstance) {
        Intrinsics.checkNotNullParameter($this$companionObjectInstance, "$this$companionObjectInstance");
        KClass<?> kClass = KClasses.getCompanionObject($this$companionObjectInstance);
        return kClass != null ? kClass.getObjectInstance() : null;
    }

    @Deprecated(message="This function creates a type which rarely makes sense for generic classes. For example, such type can only be used in signatures of members of that class. Use starProjectedType or createType() for clearer semantics.")
    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDefaultType$annotations(KClass kClass) {
    }

    @NotNull
    public static final KType getDefaultType(@NotNull KClass<?> $this$defaultType) {
        Intrinsics.checkNotNullParameter($this$defaultType, "$this$defaultType");
        SimpleType simpleType2 = ((KClassImpl)$this$defaultType).getDescriptor().getDefaultType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "(this as KClassImpl<*>).descriptor.defaultType");
        return new KTypeImpl(simpleType2, (Function0<? extends Type>)new Function0<Type>($this$defaultType){
            final /* synthetic */ KClass $this_defaultType;

            @NotNull
            public final Type invoke() {
                return ((KClassImpl)this.$this_defaultType).getJClass();
            }
            {
                this.$this_defaultType = kClass;
                super(0);
            }
        });
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDeclaredMembers$annotations(KClass kClass) {
    }

    @NotNull
    public static final Collection<KCallable<?>> getDeclaredMembers(@NotNull KClass<?> $this$declaredMembers) {
        Intrinsics.checkNotNullParameter($this$declaredMembers, "$this$declaredMembers");
        return ((KClassImpl)$this$declaredMembers).getData().invoke().getDeclaredMembers();
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getFunctions(@NotNull KClass<?> $this$functions) {
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$functions, "$this$functions");
        Iterable $this$filterIsInstance$iv = $this$functions.getMembers();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getStaticFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getStaticFunctions(@NotNull KClass<?> $this$staticFunctions) {
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$staticFunctions, "$this$staticFunctions");
        Iterable $this$filterIsInstance$iv = ((KClassImpl)$this$staticFunctions).getData().invoke().getAllStaticMembers();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getMemberFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getMemberFunctions(@NotNull KClass<?> $this$memberFunctions) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$memberFunctions, "$this$memberFunctions");
        Iterable $this$filter$iv = ((KClassImpl)$this$memberFunctions).getData().invoke().getAllNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isNotExtension(it) && it instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getMemberExtensionFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getMemberExtensionFunctions(@NotNull KClass<?> $this$memberExtensionFunctions) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$memberExtensionFunctions, "$this$memberExtensionFunctions");
        Iterable $this$filter$iv = ((KClassImpl)$this$memberExtensionFunctions).getData().invoke().getAllNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isExtension(it) && it instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDeclaredFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getDeclaredFunctions(@NotNull KClass<?> $this$declaredFunctions) {
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$declaredFunctions, "$this$declaredFunctions");
        Iterable $this$filterIsInstance$iv = ((KClassImpl)$this$declaredFunctions).getData().invoke().getDeclaredMembers();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDeclaredMemberFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getDeclaredMemberFunctions(@NotNull KClass<?> $this$declaredMemberFunctions) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$declaredMemberFunctions, "$this$declaredMemberFunctions");
        Iterable $this$filter$iv = ((KClassImpl)$this$declaredMemberFunctions).getData().invoke().getDeclaredNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isNotExtension(it) && it instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDeclaredMemberExtensionFunctions$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KFunction<?>> getDeclaredMemberExtensionFunctions(@NotNull KClass<?> $this$declaredMemberExtensionFunctions) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$declaredMemberExtensionFunctions, "$this$declaredMemberExtensionFunctions");
        Iterable $this$filter$iv = ((KClassImpl)$this$declaredMemberExtensionFunctions).getData().invoke().getDeclaredNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isExtension(it) && it instanceof KFunction)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getStaticProperties$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KProperty0<?>> getStaticProperties(@NotNull KClass<?> $this$staticProperties) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$staticProperties, "$this$staticProperties");
        Iterable $this$filter$iv = ((KClassImpl)$this$staticProperties).getData().invoke().getAllStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isNotExtension(it) && it instanceof KProperty0)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getMemberProperties$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <T> Collection<KProperty1<T, ?>> getMemberProperties(@NotNull KClass<T> $this$memberProperties) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$memberProperties, "$this$memberProperties");
        Iterable $this$filter$iv = ((KClassImpl)$this$memberProperties).getData().invoke().getAllNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isNotExtension(it) && it instanceof KProperty1)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getMemberExtensionProperties$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <T> Collection<KProperty2<T, ?, ?>> getMemberExtensionProperties(@NotNull KClass<T> $this$memberExtensionProperties) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$memberExtensionProperties, "$this$memberExtensionProperties");
        Iterable $this$filter$iv = ((KClassImpl)$this$memberExtensionProperties).getData().invoke().getAllNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isExtension(it) && it instanceof KProperty2)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDeclaredMemberProperties$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <T> Collection<KProperty1<T, ?>> getDeclaredMemberProperties(@NotNull KClass<T> $this$declaredMemberProperties) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$declaredMemberProperties, "$this$declaredMemberProperties");
        Iterable $this$filter$iv = ((KClassImpl)$this$declaredMemberProperties).getData().invoke().getDeclaredNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isNotExtension(it) && it instanceof KProperty1)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getDeclaredMemberExtensionProperties$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <T> Collection<KProperty2<T, ?, ?>> getDeclaredMemberExtensionProperties(@NotNull KClass<T> $this$declaredMemberExtensionProperties) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$declaredMemberExtensionProperties, "$this$declaredMemberExtensionProperties");
        Iterable $this$filter$iv = ((KClassImpl)$this$declaredMemberExtensionProperties).getData().invoke().getDeclaredNonStaticMembers();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KCallableImpl it = (KCallableImpl)element$iv$iv;
            boolean bl = false;
            if (!(KClasses.isExtension(it) && it instanceof KProperty2)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    private static final boolean isExtension(KCallableImpl<?> $this$isExtension) {
        return $this$isExtension.getDescriptor().getExtensionReceiverParameter() != null;
    }

    private static final boolean isNotExtension(KCallableImpl<?> $this$isNotExtension) {
        return !KClasses.isExtension($this$isNotExtension);
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getSuperclasses$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<KClass<?>> getSuperclasses(@NotNull KClass<?> $this$superclasses) {
        void $this$mapNotNullTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$superclasses, "$this$superclasses");
        Iterable $this$mapNotNull$iv = $this$superclasses.getSupertypes();
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            KClass kClass;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            KType it = (KType)element$iv$iv;
            boolean bl2 = false;
            KClassifier kClassifier = it.getClassifier();
            if (!(kClassifier instanceof KClass)) {
                kClassifier = null;
            }
            if ((KClass)kClassifier == null) continue;
            boolean bl3 = false;
            boolean bl4 = false;
            KClass it$iv$iv = kClass;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getAllSupertypes$annotations(KClass kClass) {
    }

    @NotNull
    public static final Collection<KType> getAllSupertypes(@NotNull KClass<?> $this$allSupertypes) {
        Intrinsics.checkNotNullParameter($this$allSupertypes, "$this$allSupertypes");
        Object r = DFS.dfs((Collection)$this$allSupertypes.getSupertypes(), allSupertypes.1.INSTANCE, new DFS.VisitedWithSet(), (DFS.NodeHandler)new DFS.NodeHandlerWithListResult<KType, KType>(){

            public boolean beforeChildren(@NotNull KType current) {
                Intrinsics.checkNotNullParameter(current, "current");
                ((LinkedList)this.result).add(current);
                return true;
            }
        });
        Intrinsics.checkNotNullExpressionValue(r, "DFS.dfs(\n            sup\u2026    }\n            }\n    )");
        return (Collection)r;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getAllSuperclasses$annotations(KClass kClass) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Collection<KClass<?>> getAllSuperclasses(@NotNull KClass<?> $this$allSuperclasses) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$allSuperclasses, "$this$allSuperclasses");
        Iterable $this$map$iv = KClasses.getAllSupertypes($this$allSuperclasses);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            KClass kClass;
            void supertype;
            KType kType = (KType)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            KClassifier kClassifier = supertype.getClassifier();
            if (!(kClassifier instanceof KClass)) {
                kClassifier = null;
            }
            if ((KClass)kClassifier == null) {
                throw (Throwable)new KotlinReflectionInternalError("Supertype not a class: " + supertype);
            }
            collection.add(kClass);
        }
        return (List)destination$iv$iv;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SinceKotlin(version="1.1")
    public static final boolean isSubclassOf(@NotNull KClass<?> $this$isSubclassOf, @NotNull KClass<?> base) {
        Intrinsics.checkNotNullParameter($this$isSubclassOf, "$this$isSubclassOf");
        Intrinsics.checkNotNullParameter(base, "base");
        if (Intrinsics.areEqual($this$isSubclassOf, base)) return true;
        Collection collection = CollectionsKt.listOf($this$isSubclassOf);
        Object object = KClasses$isSubclassOf$1.INSTANCE;
        if (object != null) {
            Function1 function1 = object;
            object = new KClasses$sam$org_jetbrains_kotlin_utils_DFS_Neighbors$0(function1);
        }
        Boolean bl = DFS.ifAny(collection, (DFS.Neighbors)object, new Function1<KClass<?>, Boolean>(base){
            final /* synthetic */ KClass $base;

            public final Boolean invoke(KClass<?> it) {
                return Intrinsics.areEqual(it, this.$base);
            }
            {
                this.$base = kClass;
                super(1);
            }
        });
        Intrinsics.checkNotNullExpressionValue(bl, "DFS.ifAny(listOf(this), \u2026erclasses) { it == base }");
        if (bl == false) return false;
        return true;
    }

    @SinceKotlin(version="1.1")
    public static final boolean isSuperclassOf(@NotNull KClass<?> $this$isSuperclassOf, @NotNull KClass<?> derived) {
        Intrinsics.checkNotNullParameter($this$isSuperclassOf, "$this$isSuperclassOf");
        Intrinsics.checkNotNullParameter(derived, "derived");
        return KClasses.isSubclassOf(derived, $this$isSuperclassOf);
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T> T cast(@NotNull KClass<T> $this$cast, @Nullable Object value) {
        Intrinsics.checkNotNullParameter($this$cast, "$this$cast");
        if (!$this$cast.isInstance(value)) {
            throw (Throwable)new TypeCastException("Value cannot be cast to " + $this$cast.getQualifiedName());
        }
        Object object = value;
        if (object == null) {
            throw new NullPointerException("null cannot be cast to non-null type T");
        }
        return (T)object;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final <T> T safeCast(@NotNull KClass<T> $this$safeCast, @Nullable Object value) {
        Object object;
        Intrinsics.checkNotNullParameter($this$safeCast, "$this$safeCast");
        if ($this$safeCast.isInstance(value)) {
            object = value;
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type T");
            }
        } else {
            object = null;
        }
        return (T)object;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T> T createInstance(@NotNull KClass<T> $this$createInstance) {
        Object v1;
        block7: {
            Intrinsics.checkNotNullParameter($this$createInstance, "$this$createInstance");
            Iterable $this$singleOrNull$iv = $this$createInstance.getConstructors();
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                boolean bl;
                block6: {
                    KFunction it = (KFunction)element$iv;
                    boolean bl2 = false;
                    Iterable $this$all$iv = it.getParameters();
                    boolean $i$f$all = false;
                    if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                        bl = true;
                    } else {
                        Iterator iterator2 = $this$all$iv.iterator();
                        while (iterator2.hasNext()) {
                            Object element$iv2;
                            Object receiver = element$iv2 = iterator2.next();
                            boolean bl3 = false;
                            if (((KParameter)receiver).isOptional()) continue;
                            bl = false;
                            break block6;
                        }
                        bl = true;
                    }
                }
                if (!bl) continue;
                if (found$iv) {
                    v1 = null;
                    break block7;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            v1 = !found$iv ? null : single$iv;
        }
        KFunction kFunction = v1;
        if (kFunction == null) {
            throw (Throwable)new IllegalArgumentException("Class should have a single no-arg constructor: " + $this$createInstance);
        }
        KFunction noArgsConstructor = kFunction;
        return (T)noArgsConstructor.callBy(MapsKt.emptyMap());
    }
}

