/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.ref.SoftReference;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReflectProperties {
    @NotNull
    public static <T> LazyVal<T> lazy(@NotNull Function0<T> initializer) {
        if (initializer == null) {
            ReflectProperties.$$$reportNull$$$0(0);
        }
        return new LazyVal<T>(initializer);
    }

    @NotNull
    public static <T> LazySoftVal<T> lazySoft(@Nullable T initialValue, @NotNull Function0<T> initializer) {
        if (initializer == null) {
            ReflectProperties.$$$reportNull$$$0(1);
        }
        return new LazySoftVal<T>(initialValue, initializer);
    }

    @NotNull
    public static <T> LazySoftVal<T> lazySoft(@NotNull Function0<T> initializer) {
        if (initializer == null) {
            ReflectProperties.$$$reportNull$$$0(2);
        }
        return ReflectProperties.lazySoft(null, initializer);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2 = new Object[3];
        objectArray2[0] = "initializer";
        objectArray2[1] = "kotlin/reflect/jvm/internal/ReflectProperties";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "lazy";
                break;
            }
            case 1: 
            case 2: {
                objectArray = objectArray2;
                objectArray2[2] = "lazySoft";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }

    public static class LazySoftVal<T>
    extends Val<T>
    implements Function0<T> {
        private final Function0<T> initializer;
        private volatile SoftReference<Object> value;

        public LazySoftVal(@Nullable T initialValue, @NotNull Function0<T> initializer) {
            if (initializer == null) {
                LazySoftVal.$$$reportNull$$$0(0);
            }
            this.value = null;
            this.initializer = initializer;
            if (initialValue != null) {
                this.value = new SoftReference<Object>(this.escape(initialValue));
            }
        }

        @Override
        public T invoke() {
            Object result2;
            SoftReference<Object> cached = this.value;
            if (cached != null && (result2 = cached.get()) != null) {
                return this.unescape(result2);
            }
            result2 = this.initializer.invoke();
            this.value = new SoftReference<Object>(this.escape(result2));
            return (T)result2;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "initializer", "kotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal", "<init>"));
        }
    }

    public static class LazyVal<T>
    extends Val<T> {
        private final Function0<T> initializer;
        private volatile Object value;

        public LazyVal(@NotNull Function0<T> initializer) {
            if (initializer == null) {
                LazyVal.$$$reportNull$$$0(0);
            }
            this.value = null;
            this.initializer = initializer;
        }

        @Override
        public T invoke() {
            Object cached = this.value;
            if (cached != null) {
                return this.unescape(cached);
            }
            T result2 = this.initializer.invoke();
            this.value = this.escape(result2);
            return result2;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "initializer", "kotlin/reflect/jvm/internal/ReflectProperties$LazyVal", "<init>"));
        }
    }

    public static abstract class Val<T> {
        private static final Object NULL_VALUE = new Object(){};

        public final T getValue(Object instance, Object metadata2) {
            return this.invoke();
        }

        public abstract T invoke();

        protected Object escape(T value) {
            return value == null ? NULL_VALUE : value;
        }

        protected T unescape(Object value) {
            return (T)(value == NULL_VALUE ? null : value);
        }
    }
}

