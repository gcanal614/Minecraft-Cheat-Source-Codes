/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;

public abstract class UnsignedValueConstant<T>
extends ConstantValue<T> {
    protected UnsignedValueConstant(T value) {
        super(value);
    }
}

