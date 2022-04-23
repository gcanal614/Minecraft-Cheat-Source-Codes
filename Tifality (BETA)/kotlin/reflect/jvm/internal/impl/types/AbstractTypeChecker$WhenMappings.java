/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;

public final class AbstractTypeChecker$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[TypeVariance.values().length];
        AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$0[TypeVariance.INV.ordinal()] = 1;
        AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$0[TypeVariance.OUT.ordinal()] = 2;
        AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$0[TypeVariance.IN.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[AbstractTypeCheckerContext.LowerCapturedTypePolicy.values().length];
        AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$1[AbstractTypeCheckerContext.LowerCapturedTypePolicy.CHECK_ONLY_LOWER.ordinal()] = 1;
        AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$1[AbstractTypeCheckerContext.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
        AbstractTypeChecker$WhenMappings.$EnumSwitchMapping$1[AbstractTypeCheckerContext.LowerCapturedTypePolicy.SKIP_LOWER.ordinal()] = 3;
    }
}

