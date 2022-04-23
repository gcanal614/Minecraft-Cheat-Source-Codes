/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.model;

import org.jetbrains.annotations.NotNull;

public final class TypeVariance
extends Enum<TypeVariance> {
    public static final /* enum */ TypeVariance IN;
    public static final /* enum */ TypeVariance OUT;
    public static final /* enum */ TypeVariance INV;
    private static final /* synthetic */ TypeVariance[] $VALUES;
    @NotNull
    private final String presentation;

    static {
        TypeVariance[] typeVarianceArray = new TypeVariance[3];
        TypeVariance[] typeVarianceArray2 = typeVarianceArray;
        typeVarianceArray[0] = IN = new TypeVariance("in");
        typeVarianceArray[1] = OUT = new TypeVariance("out");
        typeVarianceArray[2] = INV = new TypeVariance("");
        $VALUES = typeVarianceArray;
    }

    @NotNull
    public String toString() {
        return this.presentation;
    }

    private TypeVariance(String presentation) {
        this.presentation = presentation;
    }

    public static TypeVariance[] values() {
        return (TypeVariance[])$VALUES.clone();
    }

    public static TypeVariance valueOf(String string) {
        return Enum.valueOf(TypeVariance.class, string);
    }
}

