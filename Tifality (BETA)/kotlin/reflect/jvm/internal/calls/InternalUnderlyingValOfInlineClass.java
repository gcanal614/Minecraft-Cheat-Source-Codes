/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.calls.BoundCaller;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0002\u0016\u0017B\u001d\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J%\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0004\u00a2\u0006\u0002\u0010\u0015R\u0013\u0010\b\u001a\u0004\u0018\u00010\u00028F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0001\u0002\u0018\u0019\u00a8\u0006\u001a"}, d2={"Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "Ljava/lang/reflect/Method;", "unboxMethod", "parameterTypes", "", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Method;Ljava/util/List;)V", "member", "getMember", "()Ljava/lang/reflect/Method;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "callMethod", "", "instance", "args", "", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", "Bound", "Unbound", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Unbound;", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Bound;", "kotlin-reflection"})
public abstract class InternalUnderlyingValOfInlineClass
implements Caller<Method> {
    @NotNull
    private final Type returnType;
    private final Method unboxMethod;
    @NotNull
    private final List<Type> parameterTypes;

    @Override
    @Nullable
    public final Method getMember() {
        return null;
    }

    @Override
    @NotNull
    public final Type getReturnType() {
        return this.returnType;
    }

    @Nullable
    protected final Object callMethod(@Nullable Object instance, @NotNull Object[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        return this.unboxMethod.invoke(instance, Arrays.copyOf(args2, args2.length));
    }

    @Override
    @NotNull
    public final List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    private InternalUnderlyingValOfInlineClass(Method unboxMethod, List<? extends Type> parameterTypes) {
        this.unboxMethod = unboxMethod;
        this.parameterTypes = parameterTypes;
        Class<?> clazz = this.unboxMethod.getReturnType();
        Intrinsics.checkNotNullExpressionValue(clazz, "unboxMethod.returnType");
        this.returnType = clazz;
    }

    public void checkArguments(@NotNull Object[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        Caller.DefaultImpls.checkArguments(this, args2);
    }

    public /* synthetic */ InternalUnderlyingValOfInlineClass(Method unboxMethod, List parameterTypes, DefaultConstructorMarker $constructor_marker) {
        this(unboxMethod, parameterTypes);
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Unbound;", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass;", "unboxMethod", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
    public static final class Unbound
    extends InternalUnderlyingValOfInlineClass {
        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Object[] objectArray;
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            Object object = args2[0];
            Object[] $this$dropFirst$iv = args2;
            CallerImpl.Companion companion = CallerImpl.Companion;
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

        public Unbound(@NotNull Method unboxMethod) {
            Intrinsics.checkNotNullParameter(unboxMethod, "unboxMethod");
            Class<?> clazz = unboxMethod.getDeclaringClass();
            Intrinsics.checkNotNullExpressionValue(clazz, "unboxMethod.declaringClass");
            super(unboxMethod, CollectionsKt.listOf(clazz), null);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Bound;", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "unboxMethod", "Ljava/lang/reflect/Method;", "boundReceiver", "", "(Ljava/lang/reflect/Method;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"})
    public static final class Bound
    extends InternalUnderlyingValOfInlineClass
    implements BoundCaller {
        private final Object boundReceiver;

        @Override
        @Nullable
        public Object call(@NotNull Object[] args2) {
            Intrinsics.checkNotNullParameter(args2, "args");
            this.checkArguments(args2);
            return this.callMethod(this.boundReceiver, args2);
        }

        public Bound(@NotNull Method unboxMethod, @Nullable Object boundReceiver) {
            Intrinsics.checkNotNullParameter(unboxMethod, "unboxMethod");
            super(unboxMethod, CollectionsKt.emptyList(), null);
            this.boundReceiver = boundReceiver;
        }
    }
}

