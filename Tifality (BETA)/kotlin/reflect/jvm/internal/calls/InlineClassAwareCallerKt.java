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
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.BoundCaller;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCaller;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a6\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\n\b\u0000\u0010\n*\u0004\u0018\u00010\u000b*\b\u0012\u0004\u0012\u0002H\n0\t2\u0006\u0010\u0007\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0000\u001a\u0018\u0010\u000e\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a\u0018\u0010\u0011\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a\f\u0010\u0012\u001a\u00020\r*\u00020\u0002H\u0002\u001a\u0014\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010*\u0004\u0018\u00010\u0014H\u0000\u001a\u0012\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010*\u00020\u0001H\u0000\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0015"}, d2={"expectedReceiverType", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "getExpectedReceiverType", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "coerceToExpectedReceiverType", "", "descriptor", "createInlineClassAwareCallerIfNeeded", "Lkotlin/reflect/jvm/internal/calls/Caller;", "M", "Ljava/lang/reflect/Member;", "isDefault", "", "getBoxMethod", "Ljava/lang/reflect/Method;", "Ljava/lang/Class;", "getUnboxMethod", "hasInlineClassReceiver", "toInlineClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/DeclarationDescriptor;", "kotlin-reflection"})
public final class InlineClassAwareCallerKt {
    /*
     * Unable to fully structure code
     */
    @NotNull
    public static final <M extends Member> Caller<M> createInlineClassAwareCallerIfNeeded(@NotNull Caller<? extends M> $this$createInlineClassAwareCallerIfNeeded, @NotNull CallableMemberDescriptor descriptor, boolean isDefault) {
        block5: {
            Intrinsics.checkNotNullParameter($this$createInlineClassAwareCallerIfNeeded, "$this$createInlineClassAwareCallerIfNeeded");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(descriptor)) ** GOTO lbl-1000
            v0 = descriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(v0, "descriptor.valueParameters");
            $this$any$iv = v0;
            $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                v1 = false;
            } else {
                for (T element$iv : $this$any$iv) {
                    it = (ValueParameterDescriptor)element$iv;
                    $i$a$-any-InlineClassAwareCallerKt$createInlineClassAwareCallerIfNeeded$needsInlineAwareCaller$1 = false;
                    v2 = it;
                    Intrinsics.checkNotNullExpressionValue(v2, "it");
                    v3 = v2.getType();
                    Intrinsics.checkNotNullExpressionValue(v3, "it.type");
                    if (!InlineClassesUtilsKt.isInlineClassType(v3)) continue;
                    v1 = true;
                    break block5;
                }
                v1 = false;
            }
        }
        if (v1) ** GOTO lbl-1000
        v4 = descriptor.getReturnType();
        if (v4 != null && InlineClassesUtilsKt.isInlineClassType(v4)) ** GOTO lbl-1000
        if (!($this$createInlineClassAwareCallerIfNeeded instanceof BoundCaller) && InlineClassAwareCallerKt.hasInlineClassReceiver(descriptor)) lbl-1000:
        // 4 sources

        {
            v5 = true;
        } else {
            v5 = false;
        }
        needsInlineAwareCaller = v5;
        return needsInlineAwareCaller != false ? (Caller)new InlineClassAwareCaller<M>(descriptor, $this$createInlineClassAwareCallerIfNeeded, isDefault) : $this$createInlineClassAwareCallerIfNeeded;
    }

    public static /* synthetic */ Caller createInlineClassAwareCallerIfNeeded$default(Caller caller2, CallableMemberDescriptor callableMemberDescriptor, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded(caller2, callableMemberDescriptor, bl);
    }

    private static final boolean hasInlineClassReceiver(CallableMemberDescriptor $this$hasInlineClassReceiver) {
        KotlinType kotlinType = InlineClassAwareCallerKt.getExpectedReceiverType($this$hasInlineClassReceiver);
        return kotlinType != null && InlineClassesUtilsKt.isInlineClassType(kotlinType);
    }

    @NotNull
    public static final Method getUnboxMethod(@NotNull Class<?> $this$getUnboxMethod, @NotNull CallableMemberDescriptor descriptor2) {
        Method method;
        Intrinsics.checkNotNullParameter($this$getUnboxMethod, "$this$getUnboxMethod");
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        try {
            method = $this$getUnboxMethod.getDeclaredMethod("unbox-impl", new Class[0]);
        }
        catch (NoSuchMethodException e) {
            throw (Throwable)new KotlinReflectionInternalError("No unbox method found in inline class: " + $this$getUnboxMethod + " (calling " + descriptor2 + ')');
        }
        return method;
    }

    @NotNull
    public static final Method getBoxMethod(@NotNull Class<?> $this$getBoxMethod, @NotNull CallableMemberDescriptor descriptor2) {
        Method method;
        Intrinsics.checkNotNullParameter($this$getBoxMethod, "$this$getBoxMethod");
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        try {
            method = $this$getBoxMethod.getDeclaredMethod("box-impl", InlineClassAwareCallerKt.getUnboxMethod($this$getBoxMethod, descriptor2).getReturnType());
        }
        catch (NoSuchMethodException e) {
            throw (Throwable)new KotlinReflectionInternalError("No box method found in inline class: " + $this$getBoxMethod + " (calling " + descriptor2 + ')');
        }
        return method;
    }

    @Nullable
    public static final Class<?> toInlineClass(@NotNull KotlinType $this$toInlineClass) {
        Intrinsics.checkNotNullParameter($this$toInlineClass, "$this$toInlineClass");
        return InlineClassAwareCallerKt.toInlineClass($this$toInlineClass.getConstructor().getDeclarationDescriptor());
    }

    @Nullable
    public static final Class<?> toInlineClass(@Nullable DeclarationDescriptor $this$toInlineClass) {
        Class<?> clazz;
        if ($this$toInlineClass instanceof ClassDescriptor && ((ClassDescriptor)$this$toInlineClass).isInline()) {
            clazz = UtilKt.toJavaClass((ClassDescriptor)$this$toInlineClass);
            if (clazz == null) {
                throw (Throwable)new KotlinReflectionInternalError("Class object for the class " + ((ClassDescriptor)$this$toInlineClass).getName() + " cannot be found (classId=" + DescriptorUtilsKt.getClassId((ClassifierDescriptor)$this$toInlineClass) + ')');
            }
        } else {
            clazz = null;
        }
        return clazz;
    }

    private static final KotlinType getExpectedReceiverType(CallableMemberDescriptor $this$expectedReceiverType) {
        KotlinType kotlinType;
        ReceiverParameterDescriptor extensionReceiver = $this$expectedReceiverType.getExtensionReceiverParameter();
        ReceiverParameterDescriptor dispatchReceiver = $this$expectedReceiverType.getDispatchReceiverParameter();
        if (extensionReceiver != null) {
            kotlinType = extensionReceiver.getType();
        } else if (dispatchReceiver == null) {
            kotlinType = null;
        } else if ($this$expectedReceiverType instanceof ConstructorDescriptor) {
            kotlinType = dispatchReceiver.getType();
        } else {
            DeclarationDescriptor declarationDescriptor = $this$expectedReceiverType.getContainingDeclaration();
            if (!(declarationDescriptor instanceof ClassDescriptor)) {
                declarationDescriptor = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor;
            kotlinType = classDescriptor != null ? classDescriptor.getDefaultType() : null;
        }
        return kotlinType;
    }

    @Nullable
    public static final Object coerceToExpectedReceiverType(@Nullable Object $this$coerceToExpectedReceiverType, @NotNull CallableMemberDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        if (descriptor2 instanceof PropertyDescriptor && InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass((VariableDescriptor)((Object)descriptor2))) {
            return $this$coerceToExpectedReceiverType;
        }
        KotlinType expectedReceiverType = InlineClassAwareCallerKt.getExpectedReceiverType(descriptor2);
        Object object = expectedReceiverType;
        if (object == null || (object = InlineClassAwareCallerKt.toInlineClass((KotlinType)object)) == null || (object = InlineClassAwareCallerKt.getUnboxMethod(object, descriptor2)) == null) {
            return $this$coerceToExpectedReceiverType;
        }
        Object unboxMethod = object;
        return ((Method)unboxMethod).invoke($this$coerceToExpectedReceiverType, new Object[0]);
    }
}

