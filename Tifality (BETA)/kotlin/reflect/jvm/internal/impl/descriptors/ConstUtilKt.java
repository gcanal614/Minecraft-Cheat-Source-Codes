/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;

public final class ConstUtilKt {
    public static final boolean canBeUsedForConstVal(@NotNull KotlinType $this$canBeUsedForConstVal) {
        Intrinsics.checkNotNullParameter($this$canBeUsedForConstVal, "$this$canBeUsedForConstVal");
        return (KotlinBuiltIns.isPrimitiveType($this$canBeUsedForConstVal) || UnsignedTypes.isUnsignedType($this$canBeUsedForConstVal)) && !TypeUtils.isNullableType($this$canBeUsedForConstVal) || KotlinBuiltIns.isString($this$canBeUsedForConstVal);
    }
}

