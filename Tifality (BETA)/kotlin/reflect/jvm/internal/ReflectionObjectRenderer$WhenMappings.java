/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KParameter;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
public final class ReflectionObjectRenderer$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[KParameter.Kind.values().length];
        ReflectionObjectRenderer$WhenMappings.$EnumSwitchMapping$0[KParameter.Kind.EXTENSION_RECEIVER.ordinal()] = 1;
        ReflectionObjectRenderer$WhenMappings.$EnumSwitchMapping$0[KParameter.Kind.INSTANCE.ordinal()] = 2;
        ReflectionObjectRenderer$WhenMappings.$EnumSwitchMapping$0[KParameter.Kind.VALUE.ordinal()] = 3;
    }
}

