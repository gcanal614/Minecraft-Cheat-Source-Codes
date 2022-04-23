/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypes;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class IsKPropertyCheck
implements Check {
    @NotNull
    private static final String description;
    public static final IsKPropertyCheck INSTANCE;

    @Override
    @NotNull
    public String getDescription() {
        return description;
    }

    @Override
    public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
        boolean bl;
        ValueParameterDescriptor secondParameter;
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        ValueParameterDescriptor valueParameterDescriptor = secondParameter = functionDescriptor.getValueParameters().get(1);
        Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "secondParameter");
        KotlinType kotlinType = ReflectionTypes.Companion.createKPropertyStarType(DescriptorUtilsKt.getModule(valueParameterDescriptor));
        if (kotlinType != null) {
            KotlinType kotlinType2 = secondParameter.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType2, "secondParameter.type");
            bl = TypeUtilsKt.isSubtypeOf(kotlinType, TypeUtilsKt.makeNotNullable(kotlinType2));
        } else {
            bl = false;
        }
        return bl;
    }

    private IsKPropertyCheck() {
    }

    static {
        IsKPropertyCheck isKPropertyCheck;
        INSTANCE = isKPropertyCheck = new IsKPropertyCheck();
        description = "second parameter must be of type KProperty<*> or its supertype";
    }

    @Override
    @Nullable
    public String invoke(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }
}

