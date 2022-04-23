/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FieldOverridabilityCondition
implements ExternalOverridabilityCondition {
    @Override
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor) {
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (!(subDescriptor instanceof PropertyDescriptor) || !(superDescriptor instanceof PropertyDescriptor)) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        if (Intrinsics.areEqual(((PropertyDescriptor)subDescriptor).getName(), ((PropertyDescriptor)superDescriptor).getName()) ^ true) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        if (JavaDescriptorUtilKt.isJavaField((PropertyDescriptor)subDescriptor) && JavaDescriptorUtilKt.isJavaField((PropertyDescriptor)superDescriptor)) {
            return ExternalOverridabilityCondition.Result.OVERRIDABLE;
        }
        if (JavaDescriptorUtilKt.isJavaField((PropertyDescriptor)subDescriptor) || JavaDescriptorUtilKt.isJavaField((PropertyDescriptor)superDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    @Override
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.BOTH;
    }
}

