/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class NoDefaultAndVarargsCheck
implements Check {
    @NotNull
    private static final String description;
    public static final NoDefaultAndVarargsCheck INSTANCE;

    @Override
    @NotNull
    public String getDescription() {
        return description;
    }

    @Override
    public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            List<ValueParameterDescriptor> list = functionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "functionDescriptor.valueParameters");
            Iterable $this$all$iv = list;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    ValueParameterDescriptor it = (ValueParameterDescriptor)element$iv;
                    boolean bl2 = false;
                    ValueParameterDescriptor valueParameterDescriptor = it;
                    Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "it");
                    if (!DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameterDescriptor) && it.getVarargElementType() == null) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
        }
        return bl;
    }

    private NoDefaultAndVarargsCheck() {
    }

    static {
        NoDefaultAndVarargsCheck noDefaultAndVarargsCheck;
        INSTANCE = noDefaultAndVarargsCheck = new NoDefaultAndVarargsCheck();
        description = "should not have varargs or parameters with default values";
    }

    @Override
    @Nullable
    public String invoke(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }
}

