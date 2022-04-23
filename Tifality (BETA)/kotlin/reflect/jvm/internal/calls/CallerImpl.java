/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.jvm.internal.calls.BoundCaller;
import kotlin.reflect.jvm.internal.calls.Caller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u0000 \u001e*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\b\u001b\u001c\u001d\u001e\u001f !\"B3\b\u0002\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n\u00a2\u0006\u0002\u0010\u000bJ\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0004R\u0017\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u00028\u0000\u00a2\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u0082\u0001\u0007#$%&'()\u00a8\u0006*"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "M", "Ljava/lang/reflect/Member;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "member", "returnType", "Ljava/lang/reflect/Type;", "instanceClass", "Ljava/lang/Class;", "valueParameterTypes", "", "(Ljava/lang/reflect/Member;Ljava/lang/reflect/Type;Ljava/lang/Class;[Ljava/lang/reflect/Type;)V", "getInstanceClass", "()Ljava/lang/Class;", "getMember", "()Ljava/lang/reflect/Member;", "Ljava/lang/reflect/Member;", "parameterTypes", "", "getParameterTypes", "()Ljava/util/List;", "getReturnType", "()Ljava/lang/reflect/Type;", "checkObjectInstance", "", "obj", "", "AccessorForHiddenBoundConstructor", "AccessorForHiddenConstructor", "BoundConstructor", "Companion", "Constructor", "FieldGetter", "FieldSetter", "Method", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Constructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$BoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenBoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "kotlin-reflection"})
public abstract class CallerImpl<M extends Member>
implements Caller<M> {
    @NotNull
    private final List<Type> parameterTypes;
    @NotNull
    private final M member;
    @NotNull
    private final Type returnType;
    @Nullable
    private final Class<?> instanceClass;
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    protected final void checkObjectInstance(@Nullable Object obj) {
        if (obj == null || !this.member.getDeclaringClass().isInstance(obj)) {
            throw (Throwable)new IllegalArgumentException("An object member requires the object instance passed as the first argument.");
        }
    }

    @Override
    @NotNull
    public final M getMember() {
        return this.member;
    }

    @Override
    @NotNull
    public final Type getReturnType() {
        return this.returnType;
    }

    @Nullable
    public final Class<?> getInstanceClass() {
        return this.instanceClass;
    }

    /*
     * WARNING - void declaration
     */
    private CallerImpl(M member, Type returnType, Class<?> instanceClass, Type[] valueParameterTypes) {
        List<Type> list;
        block3: {
            block2: {
                void it;
                this.member = member;
                this.returnType = returnType;
                this.instanceClass = instanceClass;
                CallerImpl callerImpl = this;
                list = this.instanceClass;
                if (list == null) break block2;
                Class<?> clazz = list;
                boolean bl = false;
                boolean bl2 = false;
                Class<?> clazz2 = clazz;
                CallerImpl callerImpl2 = callerImpl;
                boolean bl3 = false;
                SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                spreadBuilder.add((Type)it);
                spreadBuilder.addSpread(valueParameterTypes);
                List<Type> list2 = CollectionsKt.listOf((Type[])spreadBuilder.toArray(new Type[spreadBuilder.size()]));
                callerImpl = callerImpl2;
                list = list2;
                if (list != null) break block3;
            }
            list = ArraysKt.toList(valueParameterTypes);
        }
        callerImpl.parameterTypes = list;
    }

    public void checkArguments(@NotNull Object[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        Caller.DefaultImpls.checkArguments(this, args2);
    }

    public /* synthetic */ CallerImpl(Member member, Type returnType, Class instanceClass, Type[] valueParameterTypes, DefaultConstructorMarker $constructor_marker) {
        this(member, returnType, instanceClass, valueParameterTypes);
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0011\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Constructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "constructor", "(Ljava/lang/reflect/Constructor;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
    public static final class Constructor
    extends CallerImpl<java.lang.reflect.Constructor<?>> {
        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            return ((java.lang.reflect.Constructor)this.getMember()).newInstance(Arrays.copyOf(args2, args2.length));
        }

        /*
         * WARNING - void declaration
         */
        public Constructor(@NotNull java.lang.reflect.Constructor<?> constructor) {
            void klass;
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Member member = constructor;
            Class<?> clazz = constructor.getDeclaringClass();
            Intrinsics.checkNotNullExpressionValue(clazz, "constructor.declaringClass");
            Class<?> clazz2 = constructor.getDeclaringClass();
            boolean bl = false;
            boolean bl2 = false;
            Class<?> clazz3 = clazz2;
            Type type2 = clazz;
            Member member2 = member;
            Constructor constructor2 = this;
            boolean bl3 = false;
            Class<?> outerClass = klass.getDeclaringClass();
            Class<?> clazz4 = outerClass != null && !Modifier.isStatic(klass.getModifiers()) ? outerClass : null;
            Type[] typeArray = constructor.getGenericParameterTypes();
            Intrinsics.checkNotNullExpressionValue(typeArray, "constructor.genericParameterTypes");
            super(member2, type2, clazz4, typeArray, null);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002B\u001b\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$BoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "constructor", "boundReceiver", "", "(Ljava/lang/reflect/Constructor;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
    public static final class BoundConstructor
    extends CallerImpl<java.lang.reflect.Constructor<?>>
    implements BoundCaller {
        private final Object boundReceiver;

        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            java.lang.reflect.Constructor constructor = (java.lang.reflect.Constructor)this.getMember();
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.add(this.boundReceiver);
            spreadBuilder.addSpread(args2);
            return constructor.newInstance(spreadBuilder.toArray(new Object[spreadBuilder.size()]));
        }

        public BoundConstructor(@NotNull java.lang.reflect.Constructor<?> constructor, @Nullable Object boundReceiver) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Member member = constructor;
            Class<?> clazz = constructor.getDeclaringClass();
            Intrinsics.checkNotNullExpressionValue(clazz, "constructor.declaringClass");
            Type type2 = clazz;
            Type[] typeArray = constructor.getGenericParameterTypes();
            Intrinsics.checkNotNullExpressionValue(typeArray, "constructor.genericParameterTypes");
            super(member, type2, null, typeArray, null);
            this.boundReceiver = boundReceiver;
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0011\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "constructor", "(Ljava/lang/reflect/Constructor;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
    public static final class AccessorForHiddenConstructor
    extends CallerImpl<java.lang.reflect.Constructor<?>> {
        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            java.lang.reflect.Constructor constructor = (java.lang.reflect.Constructor)this.getMember();
            SpreadBuilder spreadBuilder = new SpreadBuilder(2);
            spreadBuilder.addSpread(args2);
            spreadBuilder.add(null);
            return constructor.newInstance(spreadBuilder.toArray(new Object[spreadBuilder.size()]));
        }

        public AccessorForHiddenConstructor(@NotNull java.lang.reflect.Constructor<?> constructor) {
            Type[] typeArray;
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Member member = constructor;
            Class<?> clazz = constructor.getDeclaringClass();
            Intrinsics.checkNotNullExpressionValue(clazz, "constructor.declaringClass");
            Type type2 = clazz;
            Type[] typeArray2 = constructor.getGenericParameterTypes();
            Intrinsics.checkNotNullExpressionValue(typeArray2, "constructor.genericParameterTypes");
            Type[] $this$dropLast$iv = typeArray2;
            Companion this_$iv = Companion;
            boolean $i$f$dropLast = false;
            if ($this$dropLast$iv.length <= 1) {
                typeArray = new Type[]{};
            } else {
                Type[] typeArray3 = $this$dropLast$iv;
                int n = 0;
                int n2 = $this$dropLast$iv.length - 1;
                boolean bl = false;
                typeArray = ArraysKt.copyOfRange(typeArray3, n, n2);
                if (typeArray == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            super(member, type2, null, typeArray, null);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\u00020\u0003B\u001b\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenBoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "constructor", "boundReceiver", "", "(Ljava/lang/reflect/Constructor;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
    public static final class AccessorForHiddenBoundConstructor
    extends CallerImpl<java.lang.reflect.Constructor<?>>
    implements BoundCaller {
        private final Object boundReceiver;

        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            java.lang.reflect.Constructor constructor = (java.lang.reflect.Constructor)this.getMember();
            SpreadBuilder spreadBuilder = new SpreadBuilder(3);
            spreadBuilder.add(this.boundReceiver);
            spreadBuilder.addSpread(args2);
            spreadBuilder.add(null);
            return constructor.newInstance(spreadBuilder.toArray(new Object[spreadBuilder.size()]));
        }

        /*
         * WARNING - void declaration
         */
        public AccessorForHiddenBoundConstructor(@NotNull java.lang.reflect.Constructor<?> constructor, @Nullable Object boundReceiver) {
            Type[] typeArray;
            void $this$dropFirstAndLast$iv;
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Member member = constructor;
            Class<?> clazz = constructor.getDeclaringClass();
            Intrinsics.checkNotNullExpressionValue(clazz, "constructor.declaringClass");
            Type type2 = clazz;
            Type[] typeArray2 = constructor.getGenericParameterTypes();
            Intrinsics.checkNotNullExpressionValue(typeArray2, "constructor.genericParameterTypes");
            Type[] typeArray3 = typeArray2;
            Companion this_$iv = Companion;
            boolean $i$f$dropFirstAndLast = false;
            if (((void)$this$dropFirstAndLast$iv).length <= 2) {
                typeArray = new Type[]{};
            } else {
                void var6_6 = $this$dropFirstAndLast$iv;
                int n = 1;
                int n2 = ((void)$this$dropFirstAndLast$iv).length - 1;
                boolean bl = false;
                typeArray = ArraysKt.copyOfRange(var6_6, n, n2);
                if (typeArray == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            super(member, type2, null, typeArray, null);
            this.boundReceiver = boundReceiver;
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0006\u0010\u0011\u0012\u0013\u0014\u0015B)\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ%\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0004\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\n\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0001\u0006\u0016\u0017\u0018\u0019\u001a\u001b\u00a8\u0006\u001c"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Method;", "method", "requiresInstance", "", "parameterTypes", "", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Method;Z[Ljava/lang/reflect/Type;)V", "isVoidMethod", "callMethod", "", "instance", "args", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", "BoundInstance", "BoundJvmStaticInObject", "BoundStatic", "Instance", "JvmStaticInObject", "Static", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundStatic;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundJvmStaticInObject;", "kotlin-reflection"})
    public static abstract class Method
    extends CallerImpl<java.lang.reflect.Method> {
        private final boolean isVoidMethod;

        @Nullable
        protected final Object callMethod(@Nullable Object instance, @NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            Object result2 = ((java.lang.reflect.Method)this.getMember()).invoke(instance, Arrays.copyOf(args2, args2.length));
            return this.isVoidMethod ? Unit.INSTANCE : result2;
        }

        private Method(java.lang.reflect.Method method, boolean requiresInstance, Type[] parameterTypes) {
            Member member = method;
            Type type2 = method.getGenericReturnType();
            Intrinsics.checkNotNullExpressionValue(type2, "method.genericReturnType");
            super(member, type2, requiresInstance ? method.getDeclaringClass() : null, parameterTypes, null);
            this.isVoidMethod = Intrinsics.areEqual(this.getReturnType(), Void.TYPE);
        }

        /* synthetic */ Method(java.lang.reflect.Method method, boolean bl, Type[] typeArray, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 2) != 0) {
                boolean bl2 = bl = !Modifier.isStatic(method.getModifiers());
            }
            if ((n & 4) != 0) {
                Type[] typeArray2 = method.getGenericParameterTypes();
                Intrinsics.checkNotNullExpressionValue(typeArray2, "method.genericParameterTypes");
                typeArray = typeArray2;
            }
            this(method, bl, typeArray);
        }

        public /* synthetic */ Method(java.lang.reflect.Method method, boolean requiresInstance, Type[] parameterTypes, DefaultConstructorMarker $constructor_marker) {
            this(method, requiresInstance, parameterTypes);
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class Static
        extends Method {
            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                return this.callMethod(null, args2);
            }

            public Static(@NotNull java.lang.reflect.Method method) {
                Intrinsics.checkNotNullParameter(method, "method");
                super(method, false, null, 6, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class Instance
        extends Method {
            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Object[] objectArray;
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                Object object = args2[0];
                Object[] $this$dropFirst$iv = args2;
                Companion this_$iv = Companion;
                boolean $i$f$dropFirst = false;
                if ($this$dropFirst$iv.length <= 1) {
                    objectArray = new Object[]{};
                } else {
                    Object[] objectArray2 = $this$dropFirst$iv;
                    int n = 1;
                    int n2 = $this$dropFirst$iv.length;
                    boolean bl = false;
                    objectArray = ArraysKt.copyOfRange(objectArray2, n, n2);
                    if (objectArray == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                }
                return this.callMethod(object, objectArray);
            }

            public Instance(@NotNull java.lang.reflect.Method method) {
                Intrinsics.checkNotNullParameter(method, "method");
                super(method, false, null, 6, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class JvmStaticInObject
        extends Method {
            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Object[] objectArray;
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                this.checkObjectInstance(ArraysKt.firstOrNull(args2));
                Object[] $this$dropFirst$iv = args2;
                Companion this_$iv = Companion;
                boolean $i$f$dropFirst = false;
                if ($this$dropFirst$iv.length <= 1) {
                    objectArray = new Object[]{};
                } else {
                    Object[] objectArray2 = $this$dropFirst$iv;
                    int n = 1;
                    int n2 = $this$dropFirst$iv.length;
                    boolean bl = false;
                    objectArray = ArraysKt.copyOfRange(objectArray2, n, n2);
                    if (objectArray == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                }
                return this.callMethod(null, objectArray);
            }

            public JvmStaticInObject(@NotNull java.lang.reflect.Method method) {
                Intrinsics.checkNotNullParameter(method, "method");
                super(method, true, null, 4, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundStatic;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "boundReceiver", "", "(Ljava/lang/reflect/Method;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class BoundStatic
        extends Method
        implements BoundCaller {
            private final Object boundReceiver;

            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                spreadBuilder.add(this.boundReceiver);
                spreadBuilder.addSpread(args2);
                return this.callMethod(null, spreadBuilder.toArray(new Object[spreadBuilder.size()]));
            }

            /*
             * WARNING - void declaration
             */
            public BoundStatic(@NotNull java.lang.reflect.Method method, @Nullable Object boundReceiver) {
                Type[] typeArray;
                void $this$dropFirst$iv;
                Intrinsics.checkNotNullParameter(method, "method");
                Type[] typeArray2 = method.getGenericParameterTypes();
                Intrinsics.checkNotNullExpressionValue(typeArray2, "method.genericParameterTypes");
                Type[] typeArray3 = typeArray2;
                Companion this_$iv = Companion;
                boolean $i$f$dropFirst = false;
                if (((void)$this$dropFirst$iv).length <= 1) {
                    typeArray = new Type[]{};
                } else {
                    void var6_6 = $this$dropFirst$iv;
                    int n = 1;
                    int n2 = ((void)$this$dropFirst$iv).length;
                    boolean bl = false;
                    typeArray = ArraysKt.copyOfRange(var6_6, n, n2);
                    if (typeArray == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                }
                super(method, false, typeArray, null);
                this.boundReceiver = boundReceiver;
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "boundReceiver", "", "(Ljava/lang/reflect/Method;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class BoundInstance
        extends Method
        implements BoundCaller {
            private final Object boundReceiver;

            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                return this.callMethod(this.boundReceiver, args2);
            }

            public BoundInstance(@NotNull java.lang.reflect.Method method, @Nullable Object boundReceiver) {
                Intrinsics.checkNotNullParameter(method, "method");
                super(method, false, null, 4, null);
                this.boundReceiver = boundReceiver;
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u001b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0016\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundJvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class BoundJvmStaticInObject
        extends Method
        implements BoundCaller {
            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                return this.callMethod(null, args2);
            }

            public BoundJvmStaticInObject(@NotNull java.lang.reflect.Method method) {
                Intrinsics.checkNotNullParameter(method, "method");
                super(method, false, null, 4, null);
            }
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0005\f\r\u000e\u000f\u0010B\u0017\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u0082\u0001\u0005\u0011\u0012\u0013\u0014\u0015\u00a8\u0006\u0016"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Field;", "field", "requiresInstance", "", "(Ljava/lang/reflect/Field;Z)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "BoundInstance", "BoundJvmStaticInObject", "Instance", "JvmStaticInObject", "Static", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundJvmStaticInObject;", "kotlin-reflection"})
    public static abstract class FieldGetter
    extends CallerImpl<Field> {
        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            return ((Field)this.getMember()).get(this.getInstanceClass() != null ? ArraysKt.first(args2) : null);
        }

        private FieldGetter(Field field, boolean requiresInstance) {
            Member member = field;
            Type type2 = field.getGenericType();
            Intrinsics.checkNotNullExpressionValue(type2, "field.genericType");
            super(member, type2, requiresInstance ? field.getDeclaringClass() : null, new Type[0], null);
        }

        public /* synthetic */ FieldGetter(Field field, boolean requiresInstance, DefaultConstructorMarker $constructor_marker) {
            this(field, requiresInstance);
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "kotlin-reflection"})
        public static final class Static
        extends FieldGetter {
            public Static(@NotNull Field field) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, false, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "kotlin-reflection"})
        public static final class Instance
        extends FieldGetter {
            public Instance(@NotNull Field field) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, true, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "checkArguments", "", "args", "", "([Ljava/lang/Object;)V", "kotlin-reflection"})
        public static final class JvmStaticInObject
        extends FieldGetter {
            @Override
            public void checkArguments(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                super.checkArguments(args2);
                this.checkObjectInstance(ArraysKt.firstOrNull(args2));
            }

            public JvmStaticInObject(@NotNull Field field) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, true, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "boundReceiver", "", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class BoundInstance
        extends FieldGetter
        implements BoundCaller {
            private final Object boundReceiver;

            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                return ((Field)this.getMember()).get(this.boundReceiver);
            }

            public BoundInstance(@NotNull Field field, @Nullable Object boundReceiver) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, false, null);
                this.boundReceiver = boundReceiver;
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundJvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "kotlin-reflection"})
        public static final class BoundJvmStaticInObject
        extends FieldGetter
        implements BoundCaller {
            public BoundJvmStaticInObject(@NotNull Field field) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, false, null);
            }
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0005\u0010\u0011\u0012\u0013\u0014B\u001f\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\u000e2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0001\u0005\u0015\u0016\u0017\u0018\u0019\u00a8\u0006\u001a"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Field;", "field", "notNull", "", "requiresInstance", "(Ljava/lang/reflect/Field;ZZ)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "checkArguments", "", "([Ljava/lang/Object;)V", "BoundInstance", "BoundJvmStaticInObject", "Instance", "JvmStaticInObject", "Static", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundJvmStaticInObject;", "kotlin-reflection"})
    public static abstract class FieldSetter
    extends CallerImpl<Field> {
        private final boolean notNull;

        @Override
        public void checkArguments(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            super.checkArguments(args2);
            if (this.notNull && ArraysKt.last(args2) == null) {
                throw (Throwable)new IllegalArgumentException("null is not allowed as a value for this property.");
            }
        }

        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            ((Field)this.getMember()).set(this.getInstanceClass() != null ? ArraysKt.first(args2) : null, ArraysKt.last(args2));
            return Unit.INSTANCE;
        }

        private FieldSetter(Field field, boolean notNull, boolean requiresInstance) {
            Member member = field;
            Class<Void> clazz = Void.TYPE;
            Intrinsics.checkNotNullExpressionValue(clazz, "Void.TYPE");
            Type type2 = clazz;
            Class<?> clazz2 = requiresInstance ? field.getDeclaringClass() : null;
            Type[] typeArray = new Type[1];
            Type type3 = field.getGenericType();
            Intrinsics.checkNotNullExpressionValue(type3, "field.genericType");
            typeArray[0] = type3;
            super(member, type2, clazz2, typeArray, null);
            this.notNull = notNull;
        }

        public /* synthetic */ FieldSetter(Field field, boolean notNull, boolean requiresInstance, DefaultConstructorMarker $constructor_marker) {
            this(field, notNull, requiresInstance);
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "kotlin-reflection"})
        public static final class Static
        extends FieldSetter {
            public Static(@NotNull Field field, boolean notNull) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, notNull, false, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "kotlin-reflection"})
        public static final class Instance
        extends FieldSetter {
            public Instance(@NotNull Field field, boolean notNull) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, notNull, true, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "checkArguments", "", "args", "", "([Ljava/lang/Object;)V", "kotlin-reflection"})
        public static final class JvmStaticInObject
        extends FieldSetter {
            @Override
            public void checkArguments(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                super.checkArguments(args2);
                this.checkObjectInstance(ArraysKt.firstOrNull(args2));
            }

            public JvmStaticInObject(@NotNull Field field, boolean notNull) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, notNull, true, null);
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\u001b\u0010\n\u001a\u0004\u0018\u00010\b2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0016\u00a2\u0006\u0002\u0010\rR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "boundReceiver", "", "(Ljava/lang/reflect/Field;ZLjava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class BoundInstance
        extends FieldSetter
        implements BoundCaller {
            private final Object boundReceiver;

            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                ((Field)this.getMember()).set(this.boundReceiver, ArraysKt.first(args2));
                return Unit.INSTANCE;
            }

            public BoundInstance(@NotNull Field field, boolean notNull, @Nullable Object boundReceiver) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, notNull, false, null);
                this.boundReceiver = boundReceiver;
            }
        }

        @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundJvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
        public static final class BoundJvmStaticInObject
        extends FieldSetter
        implements BoundCaller {
            @Override
            @Nullable
            public Object call(@NotNull Object[] args2) {
                Intrinsics.checkNotNullParameter(args2, "args");
                this.checkArguments(args2);
                ((Field)this.getMember()).set(null, ArraysKt.last(args2));
                return Unit.INSTANCE;
            }

            public BoundJvmStaticInObject(@NotNull Field field, boolean notNull) {
                Intrinsics.checkNotNullParameter(field, "field");
                super(field, notNull, false, null);
            }
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0006\b\u0001\u0010\u0005\u0018\u0001*\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0006J(\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0006\b\u0001\u0010\u0005\u0018\u0001*\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0006J(\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0006\b\u0001\u0010\u0005\u0018\u0001*\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\t"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Companion;", "", "()V", "dropFirst", "", "T", "([Ljava/lang/Object;)[Ljava/lang/Object;", "dropFirstAndLast", "dropLast", "kotlin-reflection"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

