/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeFlexibility;

public final class RawSubstitution$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[JavaTypeFlexibility.values().length];
        RawSubstitution$WhenMappings.$EnumSwitchMapping$0[JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND.ordinal()] = 1;
        RawSubstitution$WhenMappings.$EnumSwitchMapping$0[JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND.ordinal()] = 2;
        RawSubstitution$WhenMappings.$EnumSwitchMapping$0[JavaTypeFlexibility.INFLEXIBLE.ordinal()] = 3;
    }
}

