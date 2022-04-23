/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.CreateKCallableVisitor;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ModuleByClassLoaderKt;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.RuntimeTypeMapper;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectJavaClassFinderKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\n\b \u0018\u0000 <2\u00020\u0001:\u0003<=>B\u0005\u00a2\u0006\u0002\u0010\u0002J*\u0010\f\u001a\u00020\r2\u0010\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u0010\u0014\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00152\u0006\u0010\u0010\u001a\u00020\u0011J\u0014\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00152\u0006\u0010\u0010\u001a\u00020\u0011J \u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0013J\u0016\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011J\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u001f\u001a\u00020 2\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00042\u0006\u0010\u0019\u001a\u00020\"H&J\u0012\u0010#\u001a\u0004\u0018\u00010 2\u0006\u0010$\u001a\u00020%H&J\"\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030'0\u00042\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0004J\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020 0\u00042\u0006\u0010\u0019\u001a\u00020\"H&J\u001a\u0010-\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0.2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0014\u0010/\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J$\u00100\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u00101\u001a\u00020%2\u0006\u00102\u001a\u00020%H\u0002JE\u00103\u001a\u0004\u0018\u00010\u0018*\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0019\u001a\u00020\u00112\u0010\u00104\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t052\n\u00106\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u00107\u001a\u00020\u0013H\u0002\u00a2\u0006\u0002\u00108J(\u00109\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0015*\u0006\u0012\u0002\b\u00030\t2\u0010\u00104\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0.H\u0002J=\u0010:\u001a\u0004\u0018\u00010\u0018*\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\u0019\u001a\u00020\u00112\u0010\u00104\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t052\n\u00106\u001a\u0006\u0012\u0002\b\u00030\tH\u0002\u00a2\u0006\u0002\u0010;R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t8TX\u0094\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006?"}, d2={"Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "()V", "constructorDescriptors", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/ConstructorDescriptor;", "getConstructorDescriptors", "()Ljava/util/Collection;", "methodOwner", "Ljava/lang/Class;", "getMethodOwner", "()Ljava/lang/Class;", "addParametersAndMasks", "", "result", "", "desc", "", "isConstructor", "", "findConstructorBySignature", "Ljava/lang/reflect/Constructor;", "findDefaultConstructor", "findDefaultMethod", "Ljava/lang/reflect/Method;", "name", "isMember", "findFunctionDescriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "signature", "findMethodBySignature", "findPropertyDescriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "getFunctions", "Lkotlin/reflect/jvm/internal/impl/name/Name;", "getLocalProperty", "index", "", "getMembers", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "scope", "Lkotlin/reflect/jvm/internal/impl/resolve/scopes/MemberScope;", "belonginess", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$MemberBelonginess;", "getProperties", "loadParameterTypes", "", "loadReturnType", "parseType", "begin", "end", "lookupMethod", "parameterTypes", "", "returnType", "isStaticDefault", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;Ljava/lang/Class;Z)Ljava/lang/reflect/Method;", "tryGetConstructor", "tryGetMethod", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;Ljava/lang/Class;)Ljava/lang/reflect/Method;", "Companion", "Data", "MemberBelonginess", "kotlin-reflection"})
public abstract class KDeclarationContainerImpl
implements ClassBasedDeclarationContainer {
    private static final Class<?> DEFAULT_CONSTRUCTOR_MARKER;
    @NotNull
    private static final Regex LOCAL_PROPERTY_SIGNATURE;
    public static final Companion Companion;

    @NotNull
    protected Class<?> getMethodOwner() {
        Class<?> clazz = ReflectClassUtilKt.getWrapperByPrimitive(this.getJClass());
        if (clazz == null) {
            clazz = this.getJClass();
        }
        return clazz;
    }

    @NotNull
    public abstract Collection<ConstructorDescriptor> getConstructorDescriptors();

    @NotNull
    public abstract Collection<PropertyDescriptor> getProperties(@NotNull Name var1);

    @NotNull
    public abstract Collection<FunctionDescriptor> getFunctions(@NotNull Name var1);

    @Nullable
    public abstract PropertyDescriptor getLocalProperty(int var1);

    /*
     * WARNING - void declaration
     */
    @NotNull
    protected final Collection<KCallableImpl<?>> getMembers(@NotNull MemberScope scope2, @NotNull MemberBelonginess belonginess) {
        void $this$mapNotNullTo$iv$iv;
        Intrinsics.checkNotNullParameter(scope2, "scope");
        Intrinsics.checkNotNullParameter((Object)belonginess, "belonginess");
        CreateKCallableVisitor visitor2 = new CreateKCallableVisitor(this, this){
            final /* synthetic */ KDeclarationContainerImpl this$0;

            @NotNull
            public KCallableImpl<?> visitConstructorDescriptor(@NotNull ConstructorDescriptor descriptor2, @NotNull Unit data2) {
                Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
                Intrinsics.checkNotNullParameter(data2, "data");
                throw (Throwable)new IllegalStateException("No constructors should appear here: " + descriptor2);
            }
            {
                this.this$0 = this$0;
                super($super_call_param$1);
            }
        };
        Iterable $this$mapNotNull$iv = ResolutionScope.DefaultImpls.getContributedDescriptors$default(scope2, null, null, 3, null);
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            KCallableImpl kCallableImpl;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            DeclarationDescriptor descriptor2 = (DeclarationDescriptor)element$iv$iv;
            boolean bl2 = false;
            if ((descriptor2 instanceof CallableMemberDescriptor && Intrinsics.areEqual(((CallableMemberDescriptor)descriptor2).getVisibility(), Visibilities.INVISIBLE_FAKE) ^ true && belonginess.accept((CallableMemberDescriptor)descriptor2) ? (KCallableImpl)descriptor2.accept(visitor2, Unit.INSTANCE) : null) == null) continue;
            kCallableImpl = kCallableImpl;
            boolean bl3 = false;
            boolean bl4 = false;
            KCallableImpl it$iv$iv = kCallableImpl;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return CollectionsKt.toList((List)destination$iv$iv);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final PropertyDescriptor findPropertyDescriptor(@NotNull String name, @NotNull String signature2) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signature2, "signature");
        MatchResult match = LOCAL_PROPERTY_SIGNATURE.matchEntire(signature2);
        if (match != null) {
            Object object = match.getDestructured();
            MatchResult.Destructured destructured = object;
            boolean bl = false;
            String number = destructured.getMatch().getGroupValues().get(1);
            object = number;
            boolean bl2 = false;
            PropertyDescriptor propertyDescriptor = this.getLocalProperty(Integer.parseInt((String)object));
            if (propertyDescriptor == null) {
                throw (Throwable)new KotlinReflectionInternalError("Local property #" + number + " not found in " + this.getJClass());
            }
            return propertyDescriptor;
        }
        Name name2 = Name.identifier(name);
        Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(name)");
        Iterable $this$filter$iv = this.getProperties(name2);
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            PropertyDescriptor descriptor2 = (PropertyDescriptor)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(RuntimeTypeMapper.INSTANCE.mapPropertySignature(descriptor2).asString(), signature2)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List properties2 = (List)destination$iv$iv;
        if (properties2.isEmpty()) {
            throw (Throwable)new KotlinReflectionInternalError("Property '" + name + "' (JVM signature: " + signature2 + ") not resolved in " + this);
        }
        if (properties2.size() != 1) {
            void $this$groupByTo$iv$iv;
            Iterable $this$groupBy$iv = properties2;
            boolean $i$f$groupBy = false;
            destination$iv$iv = $this$groupBy$iv;
            Map destination$iv$iv2 = new LinkedHashMap();
            boolean $i$f$groupByTo = false;
            for (Object element$iv$iv : $this$groupByTo$iv$iv) {
                Object object;
                PropertyDescriptor it = (PropertyDescriptor)element$iv$iv;
                boolean bl = false;
                Visibility key$iv$iv = it.getVisibility();
                Map $this$getOrPut$iv$iv$iv = destination$iv$iv2;
                boolean $i$f$getOrPut = false;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    boolean bl3 = false;
                    List answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                    object = answer$iv$iv$iv;
                } else {
                    object = value$iv$iv$iv;
                }
                List list$iv$iv = (List)object;
                list$iv$iv.add(element$iv$iv);
            }
            Collection collection = MapsKt.toSortedMap(destination$iv$iv2, findPropertyDescriptor.mostVisibleProperties.2.INSTANCE).values();
            Intrinsics.checkNotNullExpressionValue(collection, "properties\n             \u2026                }).values");
            List mostVisibleProperties2 = (List)CollectionsKt.last(collection);
            if (mostVisibleProperties2.size() == 1) {
                List list = mostVisibleProperties2;
                Intrinsics.checkNotNullExpressionValue(list, "mostVisibleProperties");
                return (PropertyDescriptor)CollectionsKt.first(list);
            }
            Name name3 = Name.identifier(name);
            Intrinsics.checkNotNullExpressionValue(name3, "Name.identifier(name)");
            String allMembers2 = CollectionsKt.joinToString$default(this.getProperties(name3), "\n", null, null, 0, null, findPropertyDescriptor.allMembers.1.INSTANCE, 30, null);
            CharSequence charSequence = allMembers2;
            boolean bl = false;
            throw (Throwable)new KotlinReflectionInternalError("Property '" + name + "' (JVM signature: " + signature2 + ") not resolved in " + this + ':' + (charSequence.length() == 0 ? " no members found" : '\n' + allMembers2));
        }
        return (PropertyDescriptor)CollectionsKt.single(properties2);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final FunctionDescriptor findFunctionDescriptor(@NotNull String name, @NotNull String signature2) {
        void $this$filterTo$iv$iv;
        Collection<FunctionDescriptor> collection;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signature2, "signature");
        if (Intrinsics.areEqual(name, "<init>")) {
            collection = CollectionsKt.toList((Iterable)this.getConstructorDescriptors());
        } else {
            Name name2 = Name.identifier(name);
            Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(name)");
            collection = this.getFunctions(name2);
        }
        Collection<FunctionDescriptor> members2 = collection;
        Iterable $this$filter$iv = members2;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            FunctionDescriptor descriptor2 = (FunctionDescriptor)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(RuntimeTypeMapper.INSTANCE.mapSignature(descriptor2).asString(), signature2)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List functions2 = (List)destination$iv$iv;
        if (functions2.size() != 1) {
            String allMembers2 = CollectionsKt.joinToString$default(members2, "\n", null, null, 0, null, findFunctionDescriptor.allMembers.1.INSTANCE, 30, null);
            CharSequence charSequence = allMembers2;
            boolean bl = false;
            throw (Throwable)new KotlinReflectionInternalError("Function '" + name + "' (JVM signature: " + signature2 + ") not resolved in " + this + ':' + (charSequence.length() == 0 ? " no members found" : '\n' + allMembers2));
        }
        return (FunctionDescriptor)CollectionsKt.single(functions2);
    }

    private final Method lookupMethod(Class<?> $this$lookupMethod, String name, Class<?>[] parameterTypes, Class<?> returnType, boolean isStaticDefault) {
        if (isStaticDefault) {
            parameterTypes[0] = $this$lookupMethod;
        }
        Method method = this.tryGetMethod($this$lookupMethod, name, parameterTypes, returnType);
        if (method != null) {
            Method method2 = method;
            boolean bl = false;
            boolean bl2 = false;
            Method it = method2;
            boolean bl3 = false;
            return it;
        }
        GenericDeclaration genericDeclaration = $this$lookupMethod.getSuperclass();
        if (genericDeclaration != null && (genericDeclaration = this.lookupMethod((Class<?>)genericDeclaration, name, parameterTypes, returnType, isStaticDefault)) != null) {
            GenericDeclaration genericDeclaration2 = genericDeclaration;
            boolean bl = false;
            boolean bl4 = false;
            GenericDeclaration it = genericDeclaration2;
            boolean bl5 = false;
            return it;
        }
        for (Class<?> superInterface : $this$lookupMethod.getInterfaces()) {
            Class<?> defaultImpls;
            Method method3 = this.lookupMethod(superInterface, name, parameterTypes, returnType, isStaticDefault);
            if (method3 != null) {
                Method bl5 = method3;
                boolean bl = false;
                boolean bl6 = false;
                Method it = bl5;
                boolean bl7 = false;
                return it;
            }
            if (!isStaticDefault || (defaultImpls = ReflectJavaClassFinderKt.tryLoadClass(ReflectClassUtilKt.getSafeClassLoader(superInterface), superInterface.getName() + "$DefaultImpls")) == null) continue;
            Intrinsics.checkNotNullExpressionValue(superInterface, "superInterface");
            Method method4 = this.tryGetMethod(defaultImpls, name, parameterTypes, returnType);
            if (method4 == null) continue;
            Method method5 = method4;
            boolean bl = false;
            boolean bl8 = false;
            Method it = method5;
            boolean bl9 = false;
            return it;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    private final Method tryGetMethod(Class<?> $this$tryGetMethod, String name, Class<?>[] parameterTypes, Class<?> returnType) {
        try {
            block5: {
                block6: {
                    v0 = result = $this$tryGetMethod.getDeclaredMethod(name, Arrays.copyOf(parameterTypes, parameterTypes.length));
                    Intrinsics.checkNotNullExpressionValue(v0, "result");
                    if (!Intrinsics.areEqual(v0.getReturnType(), returnType)) break block6;
                    v1 = result;
                    break block5;
                }
                v2 = $this$tryGetMethod.getDeclaredMethods();
                Intrinsics.checkNotNullExpressionValue(v2, "declaredMethods");
                $this$firstOrNull$iv = v2;
                $i$f$firstOrNull = false;
                var8_9 = $this$firstOrNull$iv;
                var9_10 = var8_9.length;
                for (var10_11 = 0; var10_11 < var9_10; ++var10_11) {
                    method = element$iv = var8_9[var10_11];
                    $i$a$-firstOrNull-KDeclarationContainerImpl$tryGetMethod$1 = false;
                    v3 = method;
                    Intrinsics.checkNotNullExpressionValue(v3, "method");
                    if (!Intrinsics.areEqual(v3.getName(), name) || !Intrinsics.areEqual(method.getReturnType(), returnType)) ** GOTO lbl-1000
                    Intrinsics.checkNotNull(method.getParameterTypes());
                    var15_16 = parameterTypes;
                    var16_17 = false;
                    if (Arrays.equals(var14_15, var15_16)) {
                        v4 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v4 = false;
                    }
                    if (!v4) continue;
                    v1 = element$iv;
                    break block5;
                }
                v1 = null;
            }
            var5_5 = v1;
        }
        catch (NoSuchMethodException e) {
            var5_5 = null;
        }
        return var5_5;
    }

    private final Constructor<?> tryGetConstructor(Class<?> $this$tryGetConstructor, List<? extends Class<?>> parameterTypes) {
        Constructor<?> constructor;
        try {
            Collection $this$toTypedArray$iv = parameterTypes;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            Class[] classArray = thisCollection$iv.toArray(new Class[0]);
            if (classArray == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            constructor = $this$tryGetConstructor.getDeclaredConstructor(Arrays.copyOf(classArray, classArray.length));
        }
        catch (NoSuchMethodException e) {
            constructor = null;
        }
        return constructor;
    }

    @Nullable
    public final Method findMethodBySignature(@NotNull String name, @NotNull String desc) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if (Intrinsics.areEqual(name, "<init>")) {
            return null;
        }
        Collection $this$toTypedArray$iv = this.loadParameterTypes(desc);
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        Class[] classArray = thisCollection$iv.toArray(new Class[0]);
        if (classArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Class[] parameterTypes = classArray;
        Class<?> returnType = this.loadReturnType(desc);
        Method method = this.lookupMethod(this.getMethodOwner(), name, parameterTypes, returnType, false);
        if (method != null) {
            Method method2 = method;
            boolean bl = false;
            boolean bl2 = false;
            Method it = method2;
            boolean bl3 = false;
            return it;
        }
        if (this.getMethodOwner().isInterface()) {
            Method method3 = this.lookupMethod(Object.class, name, parameterTypes, returnType, false);
            if (method3 != null) {
                Method method4 = method3;
                boolean bl = false;
                boolean bl4 = false;
                Method it = method4;
                boolean bl5 = false;
                return it;
            }
        }
        return null;
    }

    @Nullable
    public final Method findDefaultMethod(@NotNull String name, @NotNull String desc, boolean isMember) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if (Intrinsics.areEqual(name, "<init>")) {
            return null;
        }
        boolean bl = false;
        ArrayList parameterTypes = new ArrayList();
        if (isMember) {
            parameterTypes.add(this.getJClass());
        }
        this.addParametersAndMasks(parameterTypes, desc, false);
        Collection $this$toTypedArray$iv = parameterTypes;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        Class[] classArray = thisCollection$iv.toArray(new Class[0]);
        if (classArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return this.lookupMethod(this.getMethodOwner(), name + "$default", classArray, this.loadReturnType(desc), isMember);
    }

    @Nullable
    public final Constructor<?> findConstructorBySignature(@NotNull String desc) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return this.tryGetConstructor(this.getJClass(), this.loadParameterTypes(desc));
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final Constructor<?> findDefaultConstructor(@NotNull String desc) {
        void parameterTypes;
        Intrinsics.checkNotNullParameter(desc, "desc");
        boolean bl = false;
        ArrayList arrayList = new ArrayList();
        boolean bl2 = false;
        boolean bl3 = false;
        ArrayList arrayList2 = arrayList;
        Class<?> clazz = this.getJClass();
        KDeclarationContainerImpl kDeclarationContainerImpl = this;
        boolean bl4 = false;
        this.addParametersAndMasks((List)parameterTypes, desc, true);
        Unit unit = Unit.INSTANCE;
        return kDeclarationContainerImpl.tryGetConstructor(clazz, arrayList);
    }

    private final void addParametersAndMasks(List<Class<?>> result2, String desc, boolean isConstructor) {
        Class clazz;
        List<Class<?>> valueParameters = this.loadParameterTypes(desc);
        result2.addAll((Collection)valueParameters);
        int n = (valueParameters.size() + 32 - 1) / 32;
        boolean bl = false;
        int n2 = 0;
        n2 = 0;
        int n3 = n;
        while (n2 < n3) {
            int it = n2++;
            boolean bl2 = false;
            Class<Integer> clazz2 = Integer.TYPE;
            Intrinsics.checkNotNullExpressionValue(clazz2, "Integer.TYPE");
            result2.add(clazz2);
        }
        if (isConstructor) {
            Class<?> clazz3 = DEFAULT_CONSTRUCTOR_MARKER;
            clazz = clazz3;
            Intrinsics.checkNotNullExpressionValue(clazz3, "DEFAULT_CONSTRUCTOR_MARKER");
        } else {
            clazz = Object.class;
        }
        result2.add(clazz);
    }

    private final List<Class<?>> loadParameterTypes(String desc) {
        boolean bl = false;
        ArrayList result2 = new ArrayList();
        int begin = 1;
        while (desc.charAt(begin) != ')') {
            int end = begin;
            while (desc.charAt(end) == '[') {
                ++end;
            }
            char c = desc.charAt(end);
            if (StringsKt.contains$default((CharSequence)"VZCBSIFJD", c, false, 2, null)) {
                ++end;
            } else if (c == 'L') {
                end = StringsKt.indexOf$default((CharSequence)desc, ';', begin, false, 4, null) + 1;
            } else {
                throw (Throwable)new KotlinReflectionInternalError("Unknown type prefix in the method signature: " + desc);
            }
            result2.add(this.parseType(desc, begin, end));
            begin = end;
        }
        return result2;
    }

    private final Class<?> parseType(String desc, int begin, int end) {
        Class<Object> clazz;
        switch (desc.charAt(begin)) {
            case 'L': {
                ClassLoader classLoader = ReflectClassUtilKt.getSafeClassLoader(this.getJClass());
                String string = desc;
                int n = begin + 1;
                int n2 = end - 1;
                boolean bl = false;
                String string2 = string;
                if (string2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
                String string3 = string2.substring(n, n2);
                Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                clazz = classLoader.loadClass(StringsKt.replace$default(string3, '/', '.', false, 4, null));
                break;
            }
            case '[': {
                clazz = ReflectClassUtilKt.createArrayType(this.parseType(desc, begin + 1, end));
                break;
            }
            case 'V': {
                clazz = Void.TYPE;
                break;
            }
            case 'Z': {
                clazz = Boolean.TYPE;
                break;
            }
            case 'C': {
                clazz = Character.TYPE;
                break;
            }
            case 'B': {
                clazz = Byte.TYPE;
                break;
            }
            case 'S': {
                clazz = Short.TYPE;
                break;
            }
            case 'I': {
                clazz = Integer.TYPE;
                break;
            }
            case 'F': {
                clazz = Float.TYPE;
                break;
            }
            case 'J': {
                clazz = Long.TYPE;
                break;
            }
            case 'D': {
                clazz = Double.TYPE;
                break;
            }
            default: {
                throw (Throwable)new KotlinReflectionInternalError("Unknown type prefix in the method signature: " + desc);
            }
        }
        Intrinsics.checkNotNullExpressionValue(clazz, "when (desc[begin]) {\n   \u2026nature: $desc\")\n        }");
        return clazz;
    }

    private final Class<?> loadReturnType(String desc) {
        return this.parseType(desc, StringsKt.indexOf$default((CharSequence)desc, ')', 0, false, 6, null) + 1, desc.length());
    }

    static {
        Companion = new Companion(null);
        DEFAULT_CONSTRUCTOR_MARKER = Class.forName("kotlin.jvm.internal.DefaultConstructorMarker");
        String string = "<v#(\\d+)>";
        boolean bl = false;
        LOCAL_PROPERTY_SIGNATURE = new Regex(string);
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00a6\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$Data;", "", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;)V", "moduleData", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/RuntimeModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeModuleData;", "moduleData$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "kotlin-reflection"})
    public abstract class Data {
        static final /* synthetic */ KProperty[] $$delegatedProperties;
        @NotNull
        private final ReflectProperties.LazySoftVal moduleData$delegate = ReflectProperties.lazySoft((Function0)new Function0<RuntimeModuleData>(this){
            final /* synthetic */ Data this$0;

            public final RuntimeModuleData invoke() {
                return ModuleByClassLoaderKt.getOrCreateModule(this.this$0.KDeclarationContainerImpl.this.getJClass());
            }
            {
                this.this$0 = data2;
                super(0);
            }
        });

        static {
            $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "moduleData", "getModuleData()Lorg/jetbrains/kotlin/descriptors/runtime/components/RuntimeModuleData;"))};
        }

        @NotNull
        public final RuntimeModuleData getModuleData() {
            return (RuntimeModuleData)this.moduleData$delegate.getValue(this, $$delegatedProperties[0]);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0084\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$MemberBelonginess;", "", "(Ljava/lang/String;I)V", "accept", "", "member", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "DECLARED", "INHERITED", "kotlin-reflection"})
    protected static final class MemberBelonginess
    extends Enum<MemberBelonginess> {
        public static final /* enum */ MemberBelonginess DECLARED;
        public static final /* enum */ MemberBelonginess INHERITED;
        private static final /* synthetic */ MemberBelonginess[] $VALUES;

        static {
            MemberBelonginess[] memberBelonginessArray = new MemberBelonginess[2];
            MemberBelonginess[] memberBelonginessArray2 = memberBelonginessArray;
            memberBelonginessArray[0] = DECLARED = new MemberBelonginess();
            memberBelonginessArray[1] = INHERITED = new MemberBelonginess();
            $VALUES = memberBelonginessArray;
        }

        public final boolean accept(@NotNull CallableMemberDescriptor member) {
            Intrinsics.checkNotNullParameter(member, "member");
            CallableMemberDescriptor.Kind kind = member.getKind();
            Intrinsics.checkNotNullExpressionValue((Object)kind, "member.kind");
            return kind.isReal() == (this == DECLARED);
        }

        public static MemberBelonginess[] values() {
            return (MemberBelonginess[])$VALUES.clone();
        }

        public static MemberBelonginess valueOf(String string) {
            return Enum.valueOf(MemberBelonginess.class, string);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0002\b\u0003 \u0005*\b\u0012\u0002\b\u0003\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$Companion;", "", "()V", "DEFAULT_CONSTRUCTOR_MARKER", "Ljava/lang/Class;", "kotlin.jvm.PlatformType", "LOCAL_PROPERTY_SIGNATURE", "Lkotlin/text/Regex;", "getLOCAL_PROPERTY_SIGNATURE$kotlin_reflection", "()Lkotlin/text/Regex;", "kotlin-reflection"})
    public static final class Companion {
        @NotNull
        public final Regex getLOCAL_PROPERTY_SIGNATURE$kotlin_reflection() {
            return LOCAL_PROPERTY_SIGNATURE;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

