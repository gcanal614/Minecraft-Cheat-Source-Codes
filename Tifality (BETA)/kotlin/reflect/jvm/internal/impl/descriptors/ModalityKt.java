/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import org.jetbrains.annotations.NotNull;

public final class ModalityKt {
    public static final boolean isFinalClass(@NotNull ClassDescriptor $this$isFinalClass) {
        Intrinsics.checkNotNullParameter($this$isFinalClass, "$this$isFinalClass");
        return $this$isFinalClass.getModality() == Modality.FINAL && $this$isFinalClass.getKind() != ClassKind.ENUM_CLASS;
    }
}

