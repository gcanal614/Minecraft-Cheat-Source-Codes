/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.jvm.internal.impl.types.Variance;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
public final class KTypeImpl$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[Variance.values().length];
        KTypeImpl$WhenMappings.$EnumSwitchMapping$0[Variance.INVARIANT.ordinal()] = 1;
        KTypeImpl$WhenMappings.$EnumSwitchMapping$0[Variance.IN_VARIANCE.ordinal()] = 2;
        KTypeImpl$WhenMappings.$EnumSwitchMapping$0[Variance.OUT_VARIANCE.ordinal()] = 3;
    }
}

