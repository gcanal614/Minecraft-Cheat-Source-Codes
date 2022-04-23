/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.util;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.util.CheckResult;
import kotlin.reflect.jvm.internal.impl.util.Checks;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractModifierChecks {
    @NotNull
    public abstract List<Checks> getChecks$descriptors();

    @NotNull
    public final CheckResult check(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        for (Checks check : this.getChecks$descriptors()) {
            if (!check.isApplicable(functionDescriptor)) continue;
            return check.checkAll(functionDescriptor);
        }
        return CheckResult.IllegalFunctionName.INSTANCE;
    }
}

