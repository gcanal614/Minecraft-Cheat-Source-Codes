/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.storage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNullableValues;
import kotlin.reflect.jvm.internal.impl.storage.EmptySimpleLock;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.SimpleLock;
import kotlin.reflect.jvm.internal.impl.storage.SingleThreadValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.WrappedValues;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LockBasedStorageManager
implements StorageManager {
    private static final String PACKAGE_NAME = StringsKt.substringBeforeLast(LockBasedStorageManager.class.getCanonicalName(), ".", "");
    public static final StorageManager NO_LOCKS = new LockBasedStorageManager("NO_LOCKS", ExceptionHandlingStrategy.THROW, EmptySimpleLock.INSTANCE){

        @Override
        @NotNull
        protected <T> RecursionDetectedResult<T> recursionDetectedDefault() {
            RecursionDetectedResult recursionDetectedResult = RecursionDetectedResult.fallThrough();
            if (recursionDetectedResult == null) {
                1.$$$reportNull$$$0(0);
            }
            return recursionDetectedResult;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$1", "recursionDetectedDefault"));
        }
    };
    protected final SimpleLock lock;
    private final ExceptionHandlingStrategy exceptionHandlingStrategy;
    private final String debugText;

    private LockBasedStorageManager(@NotNull String debugText, @NotNull ExceptionHandlingStrategy exceptionHandlingStrategy, @NotNull SimpleLock lock) {
        if (debugText == null) {
            LockBasedStorageManager.$$$reportNull$$$0(4);
        }
        if (exceptionHandlingStrategy == null) {
            LockBasedStorageManager.$$$reportNull$$$0(5);
        }
        if (lock == null) {
            LockBasedStorageManager.$$$reportNull$$$0(6);
        }
        this.lock = lock;
        this.exceptionHandlingStrategy = exceptionHandlingStrategy;
        this.debugText = debugText;
    }

    public LockBasedStorageManager(String debugText) {
        this(debugText, (Runnable)null, (Function1<InterruptedException, Unit>)null);
    }

    public LockBasedStorageManager(String debugText, @Nullable Runnable checkCancelled, @Nullable Function1<InterruptedException, Unit> interruptedExceptionHandler) {
        this(debugText, ExceptionHandlingStrategy.THROW, SimpleLock.Companion.simpleLock(checkCancelled, interruptedExceptionHandler));
    }

    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + " (" + this.debugText + ")";
    }

    @Override
    @NotNull
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(@NotNull Function1<? super K, ? extends V> compute) {
        if (compute == null) {
            LockBasedStorageManager.$$$reportNull$$$0(9);
        }
        MemoizedFunctionToNotNull<? super K, ? extends V> memoizedFunctionToNotNull = this.createMemoizedFunction(compute, LockBasedStorageManager.<K>createConcurrentHashMap());
        if (memoizedFunctionToNotNull == null) {
            LockBasedStorageManager.$$$reportNull$$$0(10);
        }
        return memoizedFunctionToNotNull;
    }

    @NotNull
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(@NotNull Function1<? super K, ? extends V> compute, @NotNull ConcurrentMap<K, Object> map2) {
        if (compute == null) {
            LockBasedStorageManager.$$$reportNull$$$0(11);
        }
        if (map2 == null) {
            LockBasedStorageManager.$$$reportNull$$$0(12);
        }
        return new MapBasedMemoizedFunctionToNotNull<K, V>(this, map2, compute);
    }

    @Override
    @NotNull
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(@NotNull Function1<? super K, ? extends V> compute) {
        if (compute == null) {
            LockBasedStorageManager.$$$reportNull$$$0(13);
        }
        MemoizedFunctionToNullable<? super K, ? extends V> memoizedFunctionToNullable = this.createMemoizedFunctionWithNullableValues(compute, LockBasedStorageManager.<K>createConcurrentHashMap());
        if (memoizedFunctionToNullable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(14);
        }
        return memoizedFunctionToNullable;
    }

    @NotNull
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(@NotNull Function1<? super K, ? extends V> compute, @NotNull ConcurrentMap<K, Object> map2) {
        if (compute == null) {
            LockBasedStorageManager.$$$reportNull$$$0(15);
        }
        if (map2 == null) {
            LockBasedStorageManager.$$$reportNull$$$0(16);
        }
        return new MapBasedMemoizedFunction<K, V>(this, map2, compute);
    }

    @Override
    @NotNull
    public <T> NotNullLazyValue<T> createLazyValue(@NotNull Function0<? extends T> computable) {
        if (computable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(17);
        }
        return new LockBasedNotNullLazyValue<T>(this, computable);
    }

    @Override
    @NotNull
    public <T> NotNullLazyValue<T> createRecursionTolerantLazyValue(@NotNull Function0<? extends T> computable, final @NotNull T onRecursiveCall) {
        if (computable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(20);
        }
        if (onRecursiveCall == null) {
            LockBasedStorageManager.$$$reportNull$$$0(21);
        }
        return new LockBasedNotNullLazyValue<T>(this, computable){

            @Override
            @NotNull
            protected RecursionDetectedResult<T> recursionDetected(boolean firstTime) {
                RecursionDetectedResult<Object> recursionDetectedResult = RecursionDetectedResult.value(onRecursiveCall);
                if (recursionDetectedResult == null) {
                    3.$$$reportNull$$$0(0);
                }
                return recursionDetectedResult;
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$3", "recursionDetected"));
            }
        };
    }

    @Override
    @NotNull
    public <T> NotNullLazyValue<T> createLazyValueWithPostCompute(@NotNull Function0<? extends T> computable, final Function1<? super Boolean, ? extends T> onRecursiveCall, final @NotNull Function1<? super T, Unit> postCompute) {
        if (computable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(22);
        }
        if (postCompute == null) {
            LockBasedStorageManager.$$$reportNull$$$0(23);
        }
        return new LockBasedNotNullLazyValueWithPostCompute<T>(this, computable){

            @Override
            @NotNull
            protected RecursionDetectedResult<T> recursionDetected(boolean firstTime) {
                if (onRecursiveCall == null) {
                    RecursionDetectedResult recursionDetectedResult = super.recursionDetected(firstTime);
                    if (recursionDetectedResult == null) {
                        4.$$$reportNull$$$0(0);
                    }
                    return recursionDetectedResult;
                }
                RecursionDetectedResult recursionDetectedResult = RecursionDetectedResult.value(onRecursiveCall.invoke(firstTime));
                if (recursionDetectedResult == null) {
                    4.$$$reportNull$$$0(1);
                }
                return recursionDetectedResult;
            }

            @Override
            protected void doPostCompute(@NotNull T value) {
                if (value == null) {
                    4.$$$reportNull$$$0(2);
                }
                postCompute.invoke(value);
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                RuntimeException runtimeException;
                Object[] objectArray;
                Object[] objectArray2;
                int n2;
                String string;
                switch (n) {
                    default: {
                        string = "@NotNull method %s.%s must not return null";
                        break;
                    }
                    case 2: {
                        string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        n2 = 2;
                        break;
                    }
                    case 2: {
                        n2 = 3;
                        break;
                    }
                }
                Object[] objectArray3 = new Object[n2];
                switch (n) {
                    default: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$4";
                        break;
                    }
                    case 2: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "value";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[1] = "recursionDetected";
                        break;
                    }
                    case 2: {
                        objectArray = objectArray2;
                        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$4";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        break;
                    }
                    case 2: {
                        objectArray = objectArray;
                        objectArray[2] = "doPostCompute";
                        break;
                    }
                }
                String string2 = String.format(string, objectArray);
                switch (n) {
                    default: {
                        runtimeException = new IllegalStateException(string2);
                        break;
                    }
                    case 2: {
                        runtimeException = new IllegalArgumentException(string2);
                        break;
                    }
                }
                throw runtimeException;
            }
        };
    }

    @Override
    @NotNull
    public <T> NullableLazyValue<T> createNullableLazyValue(@NotNull Function0<? extends T> computable) {
        if (computable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(24);
        }
        return new LockBasedLazyValue<T>(this, computable);
    }

    @Override
    public <T> T compute(@NotNull Function0<? extends T> computable) {
        if (computable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(28);
        }
        this.lock.lock();
        try {
            T t = computable.invoke();
            return t;
        }
        catch (Throwable throwable) {
            throw this.exceptionHandlingStrategy.handleException(throwable);
        }
        finally {
            this.lock.unlock();
        }
    }

    @NotNull
    private static <K> ConcurrentMap<K, Object> createConcurrentHashMap() {
        return new ConcurrentHashMap(3, 1.0f, 2);
    }

    @NotNull
    protected <T> RecursionDetectedResult<T> recursionDetectedDefault() {
        throw LockBasedStorageManager.sanitizeStackTrace(new IllegalStateException("Recursive call in a lazy value under " + this));
    }

    @NotNull
    private static <T extends Throwable> T sanitizeStackTrace(@NotNull T throwable) {
        if (throwable == null) {
            LockBasedStorageManager.$$$reportNull$$$0(29);
        }
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int firstNonStorage = -1;
        for (int i = 0; i < size; ++i) {
            if (stackTrace[i].getClassName().startsWith(PACKAGE_NAME)) continue;
            firstNonStorage = i;
            break;
        }
        assert (firstNonStorage >= 0) : "This method should only be called on exceptions created in LockBasedStorageManager";
        List<StackTraceElement> list = Arrays.asList(stackTrace).subList(firstNonStorage, size);
        throwable.setStackTrace(list.toArray(new StackTraceElement[list.size()]));
        T t = throwable;
        if (t == null) {
            LockBasedStorageManager.$$$reportNull$$$0(30);
        }
        return t;
    }

    @Override
    @NotNull
    public <K, V> CacheWithNullableValues<K, V> createCacheWithNullableValues() {
        return new CacheWithNullableValuesBasedOnMemoizedFunction(this, (ConcurrentMap)LockBasedStorageManager.<K>createConcurrentHashMap());
    }

    @Override
    @NotNull
    public <K, V> CacheWithNotNullValues<K, V> createCacheWithNotNullValues() {
        return new CacheWithNotNullValuesBasedOnMemoizedFunction(this, (ConcurrentMap)LockBasedStorageManager.<K>createConcurrentHashMap());
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
            case 10: 
            case 14: 
            case 30: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 10: 
            case 14: 
            case 30: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "debugText";
                break;
            }
            case 1: 
            case 3: 
            case 5: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "exceptionHandlingStrategy";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "lock";
                break;
            }
            case 9: 
            case 11: 
            case 13: 
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "compute";
                break;
            }
            case 10: 
            case 14: 
            case 30: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager";
                break;
            }
            case 12: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "map";
                break;
            }
            case 17: 
            case 18: 
            case 20: 
            case 22: 
            case 24: 
            case 25: 
            case 26: 
            case 28: {
                objectArray2 = objectArray3;
                objectArray3[0] = "computable";
                break;
            }
            case 19: 
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "onRecursiveCall";
                break;
            }
            case 23: 
            case 27: {
                objectArray2 = objectArray3;
                objectArray3[0] = "postCompute";
                break;
            }
            case 29: {
                objectArray2 = objectArray3;
                objectArray3[0] = "throwable";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "createMemoizedFunction";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "createMemoizedFunctionWithNullableValues";
                break;
            }
            case 30: {
                objectArray = objectArray2;
                objectArray2[1] = "sanitizeStackTrace";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "createWithExceptionHandling";
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 7: 
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "replaceExceptionHandling";
                break;
            }
            case 9: 
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "createMemoizedFunction";
                break;
            }
            case 10: 
            case 14: 
            case 30: {
                break;
            }
            case 13: 
            case 15: 
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "createMemoizedFunctionWithNullableValues";
                break;
            }
            case 17: 
            case 18: 
            case 19: {
                objectArray = objectArray;
                objectArray[2] = "createLazyValue";
                break;
            }
            case 20: 
            case 21: {
                objectArray = objectArray;
                objectArray[2] = "createRecursionTolerantLazyValue";
                break;
            }
            case 22: 
            case 23: {
                objectArray = objectArray;
                objectArray[2] = "createLazyValueWithPostCompute";
                break;
            }
            case 24: {
                objectArray = objectArray;
                objectArray[2] = "createNullableLazyValue";
                break;
            }
            case 25: {
                objectArray = objectArray;
                objectArray[2] = "createRecursionTolerantNullableLazyValue";
                break;
            }
            case 26: 
            case 27: {
                objectArray = objectArray;
                objectArray[2] = "createNullableLazyValueWithPostCompute";
                break;
            }
            case 28: {
                objectArray = objectArray;
                objectArray[2] = "compute";
                break;
            }
            case 29: {
                objectArray = objectArray;
                objectArray[2] = "sanitizeStackTrace";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 10: 
            case 14: 
            case 30: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    private static class KeyWithComputation<K, V> {
        private final K key;
        private final Function0<? extends V> computation;

        public KeyWithComputation(K key, Function0<? extends V> computation) {
            this.key = key;
            this.computation = computation;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            KeyWithComputation that = (KeyWithComputation)o;
            return this.key.equals(that.key);
        }

        public int hashCode() {
            return this.key.hashCode();
        }
    }

    private static class CacheWithNotNullValuesBasedOnMemoizedFunction<K, V>
    extends CacheWithNullableValuesBasedOnMemoizedFunction<K, V>
    implements CacheWithNotNullValues<K, V> {
        private CacheWithNotNullValuesBasedOnMemoizedFunction(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<KeyWithComputation<K, V>, Object> map2) {
            if (storageManager == null) {
                CacheWithNotNullValuesBasedOnMemoizedFunction.$$$reportNull$$$0(0);
            }
            if (map2 == null) {
                CacheWithNotNullValuesBasedOnMemoizedFunction.$$$reportNull$$$0(1);
            }
            super(storageManager, map2);
        }

        @Override
        @NotNull
        public V computeIfAbsent(K key, @NotNull Function0<? extends V> computation) {
            if (computation == null) {
                CacheWithNotNullValuesBasedOnMemoizedFunction.$$$reportNull$$$0(2);
            }
            V result2 = super.computeIfAbsent(key, computation);
            assert (result2 != null) : "computeIfAbsent() returned null under " + this.getStorageManager();
            V v = result2;
            if (v == null) {
                CacheWithNotNullValuesBasedOnMemoizedFunction.$$$reportNull$$$0(3);
            }
            return v;
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
                case 3: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 3: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "map";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "computation";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$CacheWithNotNullValuesBasedOnMemoizedFunction";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$CacheWithNotNullValuesBasedOnMemoizedFunction";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "computeIfAbsent";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 2: {
                    objectArray = objectArray;
                    objectArray[2] = "computeIfAbsent";
                    break;
                }
                case 3: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 3: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static class CacheWithNullableValuesBasedOnMemoizedFunction<K, V>
    extends MapBasedMemoizedFunction<KeyWithComputation<K, V>, V>
    implements CacheWithNullableValues<K, V> {
        private CacheWithNullableValuesBasedOnMemoizedFunction(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<KeyWithComputation<K, V>, Object> map2) {
            if (storageManager == null) {
                CacheWithNullableValuesBasedOnMemoizedFunction.$$$reportNull$$$0(0);
            }
            if (map2 == null) {
                CacheWithNullableValuesBasedOnMemoizedFunction.$$$reportNull$$$0(1);
            }
            super(storageManager, map2, new Function1<KeyWithComputation<K, V>, V>(){

                @Override
                public V invoke(KeyWithComputation<K, V> computation) {
                    return computation.computation.invoke();
                }
            });
        }

        @Nullable
        public V computeIfAbsent(K key, @NotNull Function0<? extends V> computation) {
            if (computation == null) {
                CacheWithNullableValuesBasedOnMemoizedFunction.$$$reportNull$$$0(2);
            }
            return this.invoke(new KeyWithComputation<K, V>(key, computation));
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "map";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "computation";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$CacheWithNullableValuesBasedOnMemoizedFunction";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "<init>";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[2] = "computeIfAbsent";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    }

    private static class MapBasedMemoizedFunctionToNotNull<K, V>
    extends MapBasedMemoizedFunction<K, V>
    implements MemoizedFunctionToNotNull<K, V> {
        public MapBasedMemoizedFunctionToNotNull(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<K, Object> map2, @NotNull Function1<? super K, ? extends V> compute) {
            if (storageManager == null) {
                MapBasedMemoizedFunctionToNotNull.$$$reportNull$$$0(0);
            }
            if (map2 == null) {
                MapBasedMemoizedFunctionToNotNull.$$$reportNull$$$0(1);
            }
            if (compute == null) {
                MapBasedMemoizedFunctionToNotNull.$$$reportNull$$$0(2);
            }
            super(storageManager, map2, compute);
        }

        @Override
        @NotNull
        public V invoke(K input) {
            Object result2 = super.invoke(input);
            assert (result2 != null) : "compute() returned null under " + this.getStorageManager();
            Object v = result2;
            if (v == null) {
                MapBasedMemoizedFunctionToNotNull.$$$reportNull$$$0(3);
            }
            return v;
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
                case 3: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 3: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "map";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "compute";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunctionToNotNull";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunctionToNotNull";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "invoke";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 3: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 3: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static class MapBasedMemoizedFunction<K, V>
    implements MemoizedFunctionToNullable<K, V> {
        private final LockBasedStorageManager storageManager;
        private final ConcurrentMap<K, Object> cache;
        private final Function1<? super K, ? extends V> compute;

        public MapBasedMemoizedFunction(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<K, Object> map2, @NotNull Function1<? super K, ? extends V> compute) {
            if (storageManager == null) {
                MapBasedMemoizedFunction.$$$reportNull$$$0(0);
            }
            if (map2 == null) {
                MapBasedMemoizedFunction.$$$reportNull$$$0(1);
            }
            if (compute == null) {
                MapBasedMemoizedFunction.$$$reportNull$$$0(2);
            }
            this.storageManager = storageManager;
            this.cache = map2;
            this.compute = compute;
        }

        @Override
        @Nullable
        public V invoke(K input) {
            Object value = this.cache.get(input);
            if (value != null && value != NotValue.COMPUTING) {
                return WrappedValues.unescapeExceptionOrNull(value);
            }
            this.storageManager.lock.lock();
            try {
                V v;
                value = this.cache.get(input);
                if (value == NotValue.COMPUTING) {
                    throw this.recursionDetected(input);
                }
                if (value != null) {
                    Object v2 = WrappedValues.unescapeExceptionOrNull(value);
                    return v2;
                }
                AssertionError error = null;
                try {
                    this.cache.put(input, (Object)NotValue.COMPUTING);
                    V typedValue = this.compute.invoke(input);
                    Object oldValue = this.cache.put(input, WrappedValues.escapeNull(typedValue));
                    if (oldValue != NotValue.COMPUTING) {
                        error = this.raceCondition(input, oldValue);
                        throw error;
                    }
                    v = typedValue;
                }
                catch (Throwable throwable) {
                    if (ExceptionUtilsKt.isProcessCanceledException(throwable)) {
                        this.cache.remove(input);
                        throw (RuntimeException)throwable;
                    }
                    if (throwable == error) {
                        throw this.storageManager.exceptionHandlingStrategy.handleException(throwable);
                    }
                    Object oldValue = this.cache.put(input, WrappedValues.escapeThrowable(throwable));
                    if (oldValue != NotValue.COMPUTING) {
                        throw this.raceCondition(input, oldValue);
                    }
                    throw this.storageManager.exceptionHandlingStrategy.handleException(throwable);
                }
                return v;
            }
            finally {
                this.storageManager.lock.unlock();
            }
        }

        @NotNull
        private AssertionError recursionDetected(K input) {
            AssertionError assertionError = (AssertionError)((Object)LockBasedStorageManager.sanitizeStackTrace((Throwable)((Object)new AssertionError((Object)("Recursion detected on input: " + input + " under " + this.storageManager)))));
            if (assertionError == null) {
                MapBasedMemoizedFunction.$$$reportNull$$$0(3);
            }
            return assertionError;
        }

        @NotNull
        private AssertionError raceCondition(K input, Object oldValue) {
            AssertionError assertionError = (AssertionError)((Object)LockBasedStorageManager.sanitizeStackTrace((Throwable)((Object)new AssertionError((Object)("Race condition detected on input " + input + ". Old value is " + oldValue + " under " + this.storageManager)))));
            if (assertionError == null) {
                MapBasedMemoizedFunction.$$$reportNull$$$0(4);
            }
            return assertionError;
        }

        protected LockBasedStorageManager getStorageManager() {
            return this.storageManager;
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
                case 3: 
                case 4: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 3: 
                case 4: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "map";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "compute";
                    break;
                }
                case 3: 
                case 4: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunction";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$MapBasedMemoizedFunction";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "recursionDetected";
                    break;
                }
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[1] = "raceCondition";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 3: 
                case 4: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 3: 
                case 4: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static class LockBasedNotNullLazyValue<T>
    extends LockBasedLazyValue<T>
    implements NotNullLazyValue<T> {
        public LockBasedNotNullLazyValue(@NotNull LockBasedStorageManager storageManager, @NotNull Function0<? extends T> computable) {
            if (storageManager == null) {
                LockBasedNotNullLazyValue.$$$reportNull$$$0(0);
            }
            if (computable == null) {
                LockBasedNotNullLazyValue.$$$reportNull$$$0(1);
            }
            super(storageManager, computable);
        }

        @Override
        @NotNull
        public T invoke() {
            Object result2 = super.invoke();
            assert (result2 != null) : "compute() returned null";
            Object t = result2;
            if (t == null) {
                LockBasedNotNullLazyValue.$$$reportNull$$$0(2);
            }
            return t;
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
                case 2: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "computable";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValue";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValue";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "invoke";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 2: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 2: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static abstract class LockBasedNotNullLazyValueWithPostCompute<T>
    extends LockBasedLazyValueWithPostCompute<T>
    implements NotNullLazyValue<T> {
        public LockBasedNotNullLazyValueWithPostCompute(@NotNull LockBasedStorageManager storageManager, @NotNull Function0<? extends T> computable) {
            if (storageManager == null) {
                LockBasedNotNullLazyValueWithPostCompute.$$$reportNull$$$0(0);
            }
            if (computable == null) {
                LockBasedNotNullLazyValueWithPostCompute.$$$reportNull$$$0(1);
            }
            super(storageManager, computable);
        }

        @Override
        @NotNull
        public T invoke() {
            Object result2 = super.invoke();
            assert (result2 != null) : "compute() returned null";
            Object t = result2;
            if (t == null) {
                LockBasedNotNullLazyValueWithPostCompute.$$$reportNull$$$0(2);
            }
            return t;
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
                case 2: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "computable";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValueWithPostCompute";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedNotNullLazyValueWithPostCompute";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "invoke";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 2: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 2: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static abstract class LockBasedLazyValueWithPostCompute<T>
    extends LockBasedLazyValue<T> {
        @Nullable
        private volatile SingleThreadValue<T> valuePostCompute;

        public LockBasedLazyValueWithPostCompute(@NotNull LockBasedStorageManager storageManager, @NotNull Function0<? extends T> computable) {
            if (storageManager == null) {
                LockBasedLazyValueWithPostCompute.$$$reportNull$$$0(0);
            }
            if (computable == null) {
                LockBasedLazyValueWithPostCompute.$$$reportNull$$$0(1);
            }
            super(storageManager, computable);
            this.valuePostCompute = null;
        }

        @Override
        public T invoke() {
            SingleThreadValue<T> postComputeCache = this.valuePostCompute;
            if (postComputeCache != null && postComputeCache.hasValue()) {
                return postComputeCache.getValue();
            }
            return super.invoke();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected final void postCompute(T value) {
            this.valuePostCompute = new SingleThreadValue<T>(value);
            try {
                this.doPostCompute(value);
            }
            finally {
                this.valuePostCompute = null;
            }
        }

        protected abstract void doPostCompute(T var1);

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "computable";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedLazyValueWithPostCompute";
            objectArray[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    }

    private static class LockBasedLazyValue<T>
    implements NullableLazyValue<T> {
        private final LockBasedStorageManager storageManager;
        private final Function0<? extends T> computable;
        @Nullable
        private volatile Object value;

        public LockBasedLazyValue(@NotNull LockBasedStorageManager storageManager, @NotNull Function0<? extends T> computable) {
            if (storageManager == null) {
                LockBasedLazyValue.$$$reportNull$$$0(0);
            }
            if (computable == null) {
                LockBasedLazyValue.$$$reportNull$$$0(1);
            }
            this.value = NotValue.NOT_COMPUTED;
            this.storageManager = storageManager;
            this.computable = computable;
        }

        public boolean isComputed() {
            return this.value != NotValue.NOT_COMPUTED && this.value != NotValue.COMPUTING;
        }

        @Override
        public T invoke() {
            Object _value = this.value;
            if (!(_value instanceof NotValue)) {
                return (T)WrappedValues.unescapeThrowable(_value);
            }
            this.storageManager.lock.lock();
            try {
                T t;
                RecursionDetectedResult<T> result2;
                _value = this.value;
                if (!(_value instanceof NotValue)) {
                    Object v = WrappedValues.unescapeThrowable(_value);
                    return (T)v;
                }
                if (_value == NotValue.COMPUTING) {
                    this.value = NotValue.RECURSION_WAS_DETECTED;
                    result2 = this.recursionDetected(true);
                    if (!result2.isFallThrough()) {
                        T t2 = result2.getValue();
                        return t2;
                    }
                }
                if (_value == NotValue.RECURSION_WAS_DETECTED && !(result2 = this.recursionDetected(false)).isFallThrough()) {
                    T t3 = result2.getValue();
                    return t3;
                }
                this.value = NotValue.COMPUTING;
                try {
                    T typedValue = this.computable.invoke();
                    this.postCompute(typedValue);
                    this.value = typedValue;
                    t = typedValue;
                }
                catch (Throwable throwable) {
                    if (ExceptionUtilsKt.isProcessCanceledException(throwable)) {
                        this.value = NotValue.NOT_COMPUTED;
                        throw (RuntimeException)throwable;
                    }
                    if (this.value == NotValue.COMPUTING) {
                        this.value = WrappedValues.escapeThrowable(throwable);
                    }
                    throw this.storageManager.exceptionHandlingStrategy.handleException(throwable);
                }
                return t;
            }
            finally {
                this.storageManager.lock.unlock();
            }
        }

        @NotNull
        protected RecursionDetectedResult<T> recursionDetected(boolean firstTime) {
            RecursionDetectedResult recursionDetectedResult = this.storageManager.recursionDetectedDefault();
            if (recursionDetectedResult == null) {
                LockBasedLazyValue.$$$reportNull$$$0(2);
            }
            return recursionDetectedResult;
        }

        protected void postCompute(T value) {
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
                case 2: 
                case 3: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 2: 
                case 3: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "computable";
                    break;
                }
                case 2: 
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedLazyValue";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$LockBasedLazyValue";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "recursionDetected";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "renderDebugInformation";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 2: 
                case 3: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 2: 
                case 3: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static enum NotValue {
        NOT_COMPUTED,
        COMPUTING,
        RECURSION_WAS_DETECTED;

    }

    private static class RecursionDetectedResult<T> {
        private final T value;
        private final boolean fallThrough;

        @NotNull
        public static <T> RecursionDetectedResult<T> value(T value) {
            return new RecursionDetectedResult<T>(value, false);
        }

        @NotNull
        public static <T> RecursionDetectedResult<T> fallThrough() {
            return new RecursionDetectedResult<Object>(null, true);
        }

        private RecursionDetectedResult(T value, boolean fallThrough) {
            this.value = value;
            this.fallThrough = fallThrough;
        }

        public T getValue() {
            assert (!this.fallThrough) : "A value requested from FALL_THROUGH in " + this;
            return this.value;
        }

        public boolean isFallThrough() {
            return this.fallThrough;
        }

        public String toString() {
            return this.isFallThrough() ? "FALL_THROUGH" : String.valueOf(this.value);
        }
    }

    public static interface ExceptionHandlingStrategy {
        public static final ExceptionHandlingStrategy THROW = new ExceptionHandlingStrategy(){

            @Override
            @NotNull
            public RuntimeException handleException(@NotNull Throwable throwable) {
                if (throwable == null) {
                    1.$$$reportNull$$$0(0);
                }
                throw ExceptionUtilsKt.rethrow(throwable);
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "throwable", "kotlin/reflect/jvm/internal/impl/storage/LockBasedStorageManager$ExceptionHandlingStrategy$1", "handleException"));
            }
        };

        @NotNull
        public RuntimeException handleException(@NotNull Throwable var1);
    }
}

