/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.List;
import kotlin._Assertions;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

public final class ArrayValue
extends ConstantValue<List<? extends ConstantValue<?>>> {
    private final Function1<ModuleDescriptor, KotlinType> computeType;

    @Override
    @NotNull
    public KotlinType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        KotlinType kotlinType = this.computeType.invoke(module);
        boolean bl = false;
        boolean bl2 = false;
        KotlinType type2 = kotlinType;
        boolean bl3 = false;
        boolean bl4 = KotlinBuiltIns.isArray(type2) || KotlinBuiltIns.isPrimitiveArray(type2);
        boolean bl5 = false;
        if (_Assertions.ENABLED && !bl4) {
            boolean bl6 = false;
            String string = "Type should be an array, but was " + type2 + ": " + (List)this.getValue();
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        return kotlinType;
    }

    public ArrayValue(@NotNull List<? extends ConstantValue<?>> value, @NotNull Function1<? super ModuleDescriptor, ? extends KotlinType> computeType) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(computeType, "computeType");
        super(value);
        this.computeType = computeType;
    }
}

