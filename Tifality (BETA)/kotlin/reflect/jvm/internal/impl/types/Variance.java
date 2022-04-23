/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import org.jetbrains.annotations.NotNull;

public final class Variance
extends Enum<Variance> {
    public static final /* enum */ Variance INVARIANT;
    public static final /* enum */ Variance IN_VARIANCE;
    public static final /* enum */ Variance OUT_VARIANCE;
    private static final /* synthetic */ Variance[] $VALUES;
    @NotNull
    private final String label;
    private final boolean allowsInPosition;
    private final boolean allowsOutPosition;
    private final int superpositionFactor;

    static {
        Variance[] varianceArray = new Variance[3];
        Variance[] varianceArray2 = varianceArray;
        varianceArray[0] = INVARIANT = new Variance("", true, true, 0);
        varianceArray[1] = IN_VARIANCE = new Variance("in", true, false, -1);
        varianceArray[2] = OUT_VARIANCE = new Variance("out", false, true, 1);
        $VALUES = varianceArray;
    }

    @NotNull
    public String toString() {
        return this.label;
    }

    @NotNull
    public final String getLabel() {
        return this.label;
    }

    public final boolean getAllowsOutPosition() {
        return this.allowsOutPosition;
    }

    private Variance(String label, boolean allowsInPosition, boolean allowsOutPosition, int superpositionFactor) {
        this.label = label;
        this.allowsInPosition = allowsInPosition;
        this.allowsOutPosition = allowsOutPosition;
        this.superpositionFactor = superpositionFactor;
    }

    public static Variance[] values() {
        return (Variance[])$VALUES.clone();
    }

    public static Variance valueOf(String string) {
        return Enum.valueOf(Variance.class, string);
    }
}

