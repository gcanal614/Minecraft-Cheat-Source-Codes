/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WrappedValues {
    private static final Object NULL_VALUE = new Object(){

        public String toString() {
            return "NULL_VALUE";
        }
    };
    public static volatile boolean throwWrappedProcessCanceledException = false;

    @Nullable
    public static <V> V unescapeNull(@NotNull Object value) {
        if (value == null) {
            WrappedValues.$$$reportNull$$$0(0);
        }
        if (value == NULL_VALUE) {
            return null;
        }
        return (V)value;
    }

    @NotNull
    public static <V> Object escapeNull(@Nullable V value) {
        if (value == null) {
            Object object = NULL_VALUE;
            if (object == null) {
                WrappedValues.$$$reportNull$$$0(1);
            }
            return object;
        }
        V v = value;
        if (v == null) {
            WrappedValues.$$$reportNull$$$0(2);
        }
        return v;
    }

    @NotNull
    public static Object escapeThrowable(@NotNull Throwable throwable) {
        if (throwable == null) {
            WrappedValues.$$$reportNull$$$0(3);
        }
        return new ThrowableWrapper(throwable);
    }

    @Nullable
    public static <V> V unescapeExceptionOrNull(@NotNull Object value) {
        if (value == null) {
            WrappedValues.$$$reportNull$$$0(4);
        }
        return WrappedValues.unescapeNull(WrappedValues.unescapeThrowable(value));
    }

    @Nullable
    public static <V> V unescapeThrowable(@Nullable Object value) {
        if (value instanceof ThrowableWrapper) {
            Throwable originThrowable = ((ThrowableWrapper)value).getThrowable();
            if (throwWrappedProcessCanceledException && ExceptionUtilsKt.isProcessCanceledException(originThrowable)) {
                throw new WrappedProcessCanceledException(originThrowable);
            }
            throw ExceptionUtilsKt.rethrow(originThrowable);
        }
        return (V)value;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 1: 
            case 2: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 1: 
            case 2: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "value";
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "throwable";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues";
                break;
            }
            case 1: 
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "escapeNull";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "unescapeNull";
                break;
            }
            case 1: 
            case 2: {
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "escapeThrowable";
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "unescapeExceptionOrNull";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 1: 
            case 2: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    public static class WrappedProcessCanceledException
    extends RuntimeException {
        public WrappedProcessCanceledException(Throwable cause) {
            super("Rethrow stored exception", cause);
        }
    }

    private static final class ThrowableWrapper {
        private final Throwable throwable;

        private ThrowableWrapper(@NotNull Throwable throwable) {
            if (throwable == null) {
                ThrowableWrapper.$$$reportNull$$$0(0);
            }
            this.throwable = throwable;
        }

        @NotNull
        public Throwable getThrowable() {
            Throwable throwable = this.throwable;
            if (throwable == null) {
                ThrowableWrapper.$$$reportNull$$$0(1);
            }
            return throwable;
        }

        public String toString() {
            return this.throwable.toString();
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            RuntimeException runtimeException;
            Object[] objectArray;
            Object[] objectArray2;
            int n2;
            String string;
            switch (n) {
                default: {
                    string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                }
                case 1: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 1: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "throwable";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues$ThrowableWrapper";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues$ThrowableWrapper";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getThrowable";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 1: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 1: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

