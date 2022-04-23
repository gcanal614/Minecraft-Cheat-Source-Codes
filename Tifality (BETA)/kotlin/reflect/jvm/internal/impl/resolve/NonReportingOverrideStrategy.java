/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingStrategy;
import org.jetbrains.annotations.NotNull;

public abstract class NonReportingOverrideStrategy
extends OverridingStrategy {
    @Override
    public void overrideConflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
        Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
        Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
        this.conflict(fromSuper, fromCurrent);
    }

    @Override
    public void inheritanceConflict(@NotNull CallableMemberDescriptor first, @NotNull CallableMemberDescriptor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        this.conflict(first, second);
    }

    protected abstract void conflict(@NotNull CallableMemberDescriptor var1, @NotNull CallableMemberDescriptor var2);
}

