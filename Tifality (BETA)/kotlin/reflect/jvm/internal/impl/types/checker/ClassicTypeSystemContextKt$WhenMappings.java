/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;

public final class ClassicTypeSystemContextKt$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[TypeVariance.values().length];
        ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$0[TypeVariance.INV.ordinal()] = 1;
        ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$0[TypeVariance.IN.ordinal()] = 2;
        ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$0[TypeVariance.OUT.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[Variance.values().length];
        ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$1[Variance.INVARIANT.ordinal()] = 1;
        ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$1[Variance.IN_VARIANCE.ordinal()] = 2;
        ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$1[Variance.OUT_VARIANCE.ordinal()] = 3;
    }
}

