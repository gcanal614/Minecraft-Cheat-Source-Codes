/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

public interface CustomTypeVariable {
    public boolean isTypeVariable();

    @NotNull
    public KotlinType substitutionResult(@NotNull KotlinType var1);
}

