/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.module.impl.other.GameSpeed;
import kotlin.Metadata;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
public final class GameSpeed$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[GameSpeed.TimerMode.values().length];
        GameSpeed$WhenMappings.$EnumSwitchMapping$0[GameSpeed.TimerMode.TICK.ordinal()] = 1;
        GameSpeed$WhenMappings.$EnumSwitchMapping$0[GameSpeed.TimerMode.TIMER.ordinal()] = 2;
    }
}

