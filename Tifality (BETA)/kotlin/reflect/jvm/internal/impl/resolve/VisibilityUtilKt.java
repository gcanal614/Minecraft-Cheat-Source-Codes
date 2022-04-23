/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import org.jetbrains.annotations.NotNull;

public final class VisibilityUtilKt {
    @NotNull
    public static final CallableMemberDescriptor findMemberWithMaxVisibility(@NotNull Collection<? extends CallableMemberDescriptor> descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        Collection<? extends CallableMemberDescriptor> collection = descriptors;
        boolean bl = false;
        boolean bl2 = !collection.isEmpty();
        boolean bl3 = false;
        boolean bl4 = false;
        if (_Assertions.ENABLED && !bl2) {
            boolean string = false;
            String string2 = "Assertion failed";
            throw (Throwable)((Object)new AssertionError((Object)string2));
        }
        CallableMemberDescriptor descriptor2 = null;
        for (CallableMemberDescriptor callableMemberDescriptor : descriptors) {
            if (descriptor2 == null) {
                descriptor2 = callableMemberDescriptor;
                continue;
            }
            Integer result2 = Visibilities.compare(descriptor2.getVisibility(), callableMemberDescriptor.getVisibility());
            if (result2 == null || result2 >= 0) continue;
            descriptor2 = callableMemberDescriptor;
        }
        CallableMemberDescriptor callableMemberDescriptor = descriptor2;
        Intrinsics.checkNotNull(callableMemberDescriptor);
        return callableMemberDescriptor;
    }
}

