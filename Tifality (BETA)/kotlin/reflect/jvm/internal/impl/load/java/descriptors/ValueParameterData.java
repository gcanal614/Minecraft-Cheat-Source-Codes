/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

public final class ValueParameterData {
    @NotNull
    private final KotlinType type;
    private final boolean hasDefaultValue;

    @NotNull
    public final KotlinType getType() {
        return this.type;
    }

    public final boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }

    public ValueParameterData(@NotNull KotlinType type2, boolean hasDefaultValue) {
        Intrinsics.checkNotNullParameter(type2, "type");
        this.type = type2;
        this.hasDefaultValue = hasDefaultValue;
    }
}

