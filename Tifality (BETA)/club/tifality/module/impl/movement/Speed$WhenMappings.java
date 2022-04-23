/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.module.impl.movement.Speed;
import kotlin.Metadata;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
public final class Speed$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;

    static {
        $EnumSwitchMapping$0 = new int[Speed.BoostMode.values().length];
        Speed$WhenMappings.$EnumSwitchMapping$0[Speed.BoostMode.Bhop.ordinal()] = 1;
        Speed$WhenMappings.$EnumSwitchMapping$0[Speed.BoostMode.LowHop.ordinal()] = 2;
        Speed$WhenMappings.$EnumSwitchMapping$0[Speed.BoostMode.Yport.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[Speed.BoostMode.values().length];
        Speed$WhenMappings.$EnumSwitchMapping$1[Speed.BoostMode.Bhop.ordinal()] = 1;
        Speed$WhenMappings.$EnumSwitchMapping$1[Speed.BoostMode.LowHop.ordinal()] = 2;
        Speed$WhenMappings.$EnumSwitchMapping$1[Speed.BoostMode.Yport.ordinal()] = 3;
    }
}

