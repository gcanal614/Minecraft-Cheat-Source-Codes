/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

public final class NullabilityQualifier
extends Enum<NullabilityQualifier> {
    public static final /* enum */ NullabilityQualifier NULLABLE;
    public static final /* enum */ NullabilityQualifier NOT_NULL;
    public static final /* enum */ NullabilityQualifier FORCE_FLEXIBILITY;
    private static final /* synthetic */ NullabilityQualifier[] $VALUES;

    static {
        NullabilityQualifier[] nullabilityQualifierArray = new NullabilityQualifier[3];
        NullabilityQualifier[] nullabilityQualifierArray2 = nullabilityQualifierArray;
        nullabilityQualifierArray[0] = NULLABLE = new NullabilityQualifier();
        nullabilityQualifierArray[1] = NOT_NULL = new NullabilityQualifier();
        nullabilityQualifierArray[2] = FORCE_FLEXIBILITY = new NullabilityQualifier();
        $VALUES = nullabilityQualifierArray;
    }

    public static NullabilityQualifier[] values() {
        return (NullabilityQualifier[])$VALUES.clone();
    }

    public static NullabilityQualifier valueOf(String string) {
        return Enum.valueOf(NullabilityQualifier.class, string);
    }
}

