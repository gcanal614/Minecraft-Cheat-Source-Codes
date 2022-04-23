/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class BooleanValue
extends ConstantValue<Boolean> {
    @Override
    @NotNull
    public SimpleType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        SimpleType simpleType2 = module.getBuiltIns().getBooleanType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "module.builtIns.booleanType");
        return simpleType2;
    }

    public BooleanValue(boolean value) {
        super(value);
    }
}

