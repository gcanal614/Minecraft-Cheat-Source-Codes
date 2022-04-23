/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.BoundCaller;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import kotlin.reflect.jvm.internal.calls.CallerKt;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0000\u0018\u0000*\f\b\u0000\u0010\u0001 \u0001*\u0004\u0018\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u001cB#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u001b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0016\u00a2\u0006\u0002\u0010\u001bR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00028\u00008VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001d"}, d2={"Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller;", "M", "Ljava/lang/reflect/Member;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "caller", "isDefault", "", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lkotlin/reflect/jvm/internal/calls/Caller;Z)V", "data", "Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller$BoxUnboxData;", "member", "getMember", "()Ljava/lang/reflect/Member;", "parameterTypes", "", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "BoxUnboxData", "kotlin-reflection"})
public final class InlineClassAwareCaller<M extends Member>
implements Caller<M> {
    private final BoxUnboxData data;
    private final Caller<M> caller;
    private final boolean isDefault;

    @Override
    public M getMember() {
        return this.caller.getMember();
    }

    @Override
    @NotNull
    public Type getReturnType() {
        return this.caller.getReturnType();
    }

    @Override
    @NotNull
    public List<Type> getParameterTypes() {
        return this.caller.getParameterTypes();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public Object call(@NotNull Object[] args2) {
        void range;
        Intrinsics.checkNotNullParameter(args2, "args");
        BoxUnboxData boxUnboxData = this.data;
        IntRange intRange = boxUnboxData.component1();
        Method[] methodArray = boxUnboxData.component2();
        Method box = boxUnboxData.component3();
        Object[] objectArray = args2;
        int n = 0;
        Object[] objectArray2 = Arrays.copyOf(objectArray, objectArray.length);
        Intrinsics.checkNotNullExpressionValue(objectArray2, "java.util.Arrays.copyOf(this, size)");
        if (objectArray2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        Object[] unboxed = objectArray2;
        void v1 = range;
        int n2 = v1.getFirst();
        if (n2 <= (n = v1.getLast())) {
            while (true) {
                Object object;
                void index;
                void unbox;
                void method = unbox[index];
                Object arg = args2[index];
                if (method != null) {
                    if (arg != null) {
                        object = method.invoke(arg, new Object[0]);
                    } else {
                        Class<?> clazz = method.getReturnType();
                        Intrinsics.checkNotNullExpressionValue(clazz, "method.returnType");
                        object = UtilKt.defaultPrimitiveValue(clazz);
                    }
                } else {
                    object = unboxed[index] = arg;
                }
                if (index == n) break;
                ++index;
            }
        }
        Object result2 = this.caller.call(unboxed);
        Object object = box;
        if (object == null || (object = ((Method)object).invoke(null, result2)) == null) {
            object = result2;
        }
        return object;
    }

    /*
     * WARNING - void declaration
     */
    public InlineClassAwareCaller(@NotNull CallableMemberDescriptor descriptor2, @NotNull Caller<? extends M> caller2, boolean isDefault) {
        BoxUnboxData boxUnboxData;
        BoxUnboxData boxUnboxData2;
        Method box;
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(caller2, "caller");
        this.caller = caller2;
        this.isDefault = isDefault;
        InlineClassAwareCaller inlineClassAwareCaller = this;
        boolean bl = false;
        boolean bl2 = false;
        InlineClassAwareCaller inlineClassAwareCaller2 = inlineClassAwareCaller;
        InlineClassAwareCaller inlineClassAwareCaller3 = this;
        boolean bl3 = false;
        KotlinType kotlinType = descriptor2.getReturnType();
        Intrinsics.checkNotNull(kotlinType);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "descriptor.returnType!!");
        Class<?> clazz = InlineClassAwareCallerKt.toInlineClass(kotlinType);
        Method method = box = clazz != null ? InlineClassAwareCallerKt.getBoxMethod(clazz, descriptor2) : null;
        if (InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(descriptor2)) {
            boxUnboxData2 = new BoxUnboxData(IntRange.Companion.getEMPTY(), new Method[0], box);
        } else {
            KotlinType extensionReceiverType2;
            int n;
            void $this$run;
            if ($this$run.caller instanceof CallerImpl.Method.BoundStatic) {
                n = -1;
            } else if (descriptor2 instanceof ConstructorDescriptor) {
                n = $this$run.caller instanceof BoundCaller ? -1 : 0;
            } else if (descriptor2.getDispatchReceiverParameter() != null && !($this$run.caller instanceof BoundCaller)) {
                DeclarationDescriptor declarationDescriptor = descriptor2.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "descriptor.containingDeclaration");
                n = InlineClassesUtilsKt.isInlineClass(declarationDescriptor) ? 0 : 1;
            } else {
                n = 0;
            }
            int shift = n;
            int extraArgumentsTail = $this$run.isDefault ? 2 : 0;
            ArrayList<KotlinType> arrayList = new ArrayList<KotlinType>();
            boolean bl4 = false;
            int n2 = 0;
            ArrayList<KotlinType> kotlinParameterTypes = arrayList;
            boolean bl5 = false;
            ReceiverParameterDescriptor receiverParameterDescriptor = descriptor2.getExtensionReceiverParameter();
            KotlinType kotlinType2 = extensionReceiverType2 = receiverParameterDescriptor != null ? receiverParameterDescriptor.getType() : null;
            if (extensionReceiverType2 != null) {
                kotlinParameterTypes.add(extensionReceiverType2);
            } else if (descriptor2 instanceof ConstructorDescriptor) {
                ClassDescriptor classDescriptor = ((ConstructorDescriptor)descriptor2).getConstructedClass();
                Intrinsics.checkNotNullExpressionValue(classDescriptor, "descriptor.constructedClass");
                ClassDescriptor constructedClass = classDescriptor;
                if (constructedClass.isInner()) {
                    DeclarationDescriptor declarationDescriptor = constructedClass.getContainingDeclaration();
                    if (declarationDescriptor == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                    kotlinParameterTypes.add(((ClassDescriptor)declarationDescriptor).getDefaultType());
                }
            } else {
                DeclarationDescriptor declarationDescriptor = descriptor2.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "descriptor.containingDeclaration");
                DeclarationDescriptor containingDeclaration = declarationDescriptor;
                if (containingDeclaration instanceof ClassDescriptor && ((ClassDescriptor)containingDeclaration).isInline()) {
                    kotlinParameterTypes.add(((ClassDescriptor)containingDeclaration).getDefaultType());
                }
            }
            List<ValueParameterDescriptor> list = descriptor2.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "descriptor.valueParameters");
            Iterable $this$mapTo$iv = list;
            boolean $i$f$mapTo = false;
            for (Object item$iv : $this$mapTo$iv) {
                void p1;
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)item$iv;
                Collection collection = kotlinParameterTypes;
                boolean bl6 = false;
                KotlinType kotlinType3 = p1.getType();
                collection.add(kotlinType3);
            }
            List kotlinParameterTypes2 = arrayList;
            int expectedArgsSize = kotlinParameterTypes2.size() + shift + extraArgumentsTail;
            if (CallerKt.getArity((Caller)$this$run) != expectedArgsSize) {
                throw (Throwable)new KotlinReflectionInternalError("Inconsistent number of parameters in the descriptor and Java reflection object: " + CallerKt.getArity((Caller)$this$run) + " != " + expectedArgsSize + '\n' + "Calling: " + descriptor2 + '\n' + "Parameter types: " + $this$run.getParameterTypes() + ")\n" + "Default: " + $this$run.isDefault);
            }
            n2 = 0;
            boolean bl7 = false;
            IntRange argumentRange = RangesKt.until(Math.max(shift, n2), kotlinParameterTypes2.size() + shift);
            Method[] methodArray = new Method[expectedArgsSize];
            for (int i = 0; i < expectedArgsSize; ++i) {
                Method method2;
                Method method3;
                void i2;
                int extensionReceiverType2 = i;
                int n3 = i;
                Method[] methodArray2 = methodArray;
                boolean bl8 = false;
                if (argumentRange.contains((int)i2)) {
                    Class<?> clazz2 = InlineClassAwareCallerKt.toInlineClass((KotlinType)kotlinParameterTypes2.get((int)(i2 - shift)));
                    method3 = clazz2 != null ? InlineClassAwareCallerKt.getUnboxMethod(clazz2, descriptor2) : null;
                } else {
                    method3 = null;
                }
                methodArray2[n3] = method2 = method3;
            }
            Method[] unbox = methodArray;
            boxUnboxData2 = new BoxUnboxData(argumentRange, unbox, box);
        }
        inlineClassAwareCaller3.data = boxUnboxData = boxUnboxData2;
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\r\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0003H\u0086\u0002J\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005H\u0086\u0002\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0006H\u0086\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller$BoxUnboxData;", "", "argumentRange", "Lkotlin/ranges/IntRange;", "unbox", "", "Ljava/lang/reflect/Method;", "box", "(Lkotlin/ranges/IntRange;[Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getArgumentRange", "()Lkotlin/ranges/IntRange;", "getBox", "()Ljava/lang/reflect/Method;", "getUnbox", "()[Ljava/lang/reflect/Method;", "[Ljava/lang/reflect/Method;", "component1", "component2", "component3", "kotlin-reflection"})
    private static final class BoxUnboxData {
        @NotNull
        private final IntRange argumentRange;
        @NotNull
        private final Method[] unbox;
        @Nullable
        private final Method box;

        @NotNull
        public final IntRange component1() {
            return this.argumentRange;
        }

        @NotNull
        public final Method[] component2() {
            return this.unbox;
        }

        @Nullable
        public final Method component3() {
            return this.box;
        }

        public BoxUnboxData(@NotNull IntRange argumentRange, @NotNull Method[] unbox, @Nullable Method box) {
            Intrinsics.checkNotNullParameter(argumentRange, "argumentRange");
            Intrinsics.checkNotNullParameter(unbox, "unbox");
            this.argumentRange = argumentRange;
            this.unbox = unbox;
            this.box = box;
        }
    }
}

