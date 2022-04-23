/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class Java8ParameterNamesLoader {
    @Nullable
    private static Cache cache;
    public static final Java8ParameterNamesLoader INSTANCE;

    @NotNull
    public final Cache buildCache(@NotNull Member member) {
        Method method;
        Intrinsics.checkNotNullParameter(member, "member");
        Class<?> methodOrConstructorClass = member.getClass();
        try {
            method = methodOrConstructorClass.getMethod("getParameters", new Class[0]);
        }
        catch (NoSuchMethodException e) {
            return new Cache(null, null);
        }
        Method getParameters = method;
        Class<?> parameterClass = ReflectClassUtilKt.getSafeClassLoader(methodOrConstructorClass).loadClass("java.lang.reflect.Parameter");
        return new Cache(getParameters, parameterClass.getMethod("getName", new Class[0]));
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final List<String> loadParameterNames(@NotNull Member member) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(member, "member");
        Cache cache = Java8ParameterNamesLoader.cache;
        if (cache == null) {
            Java8ParameterNamesLoader.cache = cache = this.buildCache(member);
        }
        Method method = cache.getGetParameters();
        if (method == null) {
            return null;
        }
        Method getParameters = method;
        Method method2 = cache.getGetName();
        if (method2 == null) {
            return null;
        }
        Method getName = method2;
        Object object = getParameters.invoke(member, new Object[0]);
        if (object == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<*>");
        }
        Object[] $this$map$iv = (Object[])object;
        boolean $i$f$map = false;
        Object[] objectArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var10_10 = $this$mapTo$iv$iv;
        int n = ((void)var10_10).length;
        for (int i = 0; i < n; ++i) {
            void param;
            void item$iv$iv;
            void var14_14 = item$iv$iv = var10_10[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Object object2 = getName.invoke(param, new Object[0]);
            if (object2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            String string = (String)object2;
            collection.add(string);
        }
        return (List)destination$iv$iv;
    }

    private Java8ParameterNamesLoader() {
    }

    static {
        Java8ParameterNamesLoader java8ParameterNamesLoader;
        INSTANCE = java8ParameterNamesLoader = new Java8ParameterNamesLoader();
    }

    public static final class Cache {
        @Nullable
        private final Method getParameters;
        @Nullable
        private final Method getName;

        @Nullable
        public final Method getGetParameters() {
            return this.getParameters;
        }

        @Nullable
        public final Method getGetName() {
            return this.getName;
        }

        public Cache(@Nullable Method getParameters, @Nullable Method getName) {
            this.getParameters = getParameters;
            this.getName = getName;
        }
    }
}

