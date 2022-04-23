/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerValueConstant;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class ShortValue
extends IntegerValueConstant<Short> {
    @Override
    @NotNull
    public SimpleType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        SimpleType simpleType2 = module.getBuiltIns().getShortType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "module.builtIns.shortType");
        return simpleType2;
    }

    @Override
    @NotNull
    public String toString() {
        return ((Number)this.getValue()).shortValue() + ".toShort()";
    }

    public ShortValue(short value) {
        super(value);
    }
}

