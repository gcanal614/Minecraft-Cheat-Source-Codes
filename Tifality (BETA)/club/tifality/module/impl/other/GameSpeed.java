/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.module.impl.other;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.other.GameSpeed$WhenMappings;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.timer.TimerUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(label="GameSpeed", category=ModuleCategory.OTHER)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\f0\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lclub/tifality/module/impl/other/GameSpeed;", "Lclub/tifality/module/Module;", "()V", "maxTimerValue", "Lclub/tifality/property/impl/DoubleProperty;", "minTimerValue", "modeValue", "Lclub/tifality/property/impl/EnumProperty;", "Lclub/tifality/module/impl/other/GameSpeed$TimerMode;", "kotlin.jvm.PlatformType", "onMoveValue", "Lclub/tifality/property/Property;", "", "stage", "tickValue", "timer", "Lclub/tifality/utils/timer/TimerUtil;", "onDisable", "", "onEnable", "onUpdate", "event", "Lclub/tifality/manager/event/impl/player/UpdatePositionEvent;", "TimerMode", "Client"})
public final class GameSpeed
extends Module {
    private final EnumProperty<TimerMode> modeValue = new EnumProperty<Enum>("Mode", TimerMode.TICK);
    private final DoubleProperty tickValue = new DoubleProperty("Ticks Existed", 1.0, 1.0, 5.0, 1.0);
    private final DoubleProperty maxTimerValue = new DoubleProperty("Max Timer", 1.5, 0.1, 2.5, 0.1);
    private final DoubleProperty minTimerValue = new DoubleProperty("Min Timer", 1.3, 0.1, 2.5, 0.1);
    private final Property<Boolean> onMoveValue = new Property<Boolean>("On Move", true);
    private boolean stage;
    private final TimerUtil timer = new TimerUtil();

    @Override
    public void onEnable() {
        if (Module.mc.thePlayer == null) {
            return;
        }
        Module.mc.timer.timerSpeed = (float)((Number)this.maxTimerValue.get()).doubleValue();
    }

    @Override
    public void onDisable() {
        if (Module.mc.thePlayer == null) {
            return;
        }
        Module.mc.timer.timerSpeed = 1.0f;
    }

    @Listener
    public final void onUpdate(@NotNull UpdatePositionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MovementUtils.isMoving() || !this.onMoveValue.get().booleanValue()) {
            Enum enum_ = (Enum)this.modeValue.getValue();
            Intrinsics.checkNotNullExpressionValue(enum_, "modeValue.value");
            TimerMode mode = (TimerMode)enum_;
            switch (GameSpeed$WhenMappings.$EnumSwitchMapping$0[mode.ordinal()]) {
                case 1: {
                    if (Module.mc.thePlayer.ticksExisted % (int)((Number)this.tickValue.get()).doubleValue() != 0) break;
                    Module.mc.timer.timerSpeed = (float)((Number)this.maxTimerValue.get()).doubleValue();
                    break;
                }
                case 2: {
                    if (this.stage) {
                        Module.mc.timer.timerSpeed = (float)((Number)this.minTimerValue.get()).doubleValue();
                        if (!this.timer.hasElapsed(550L)) break;
                        this.timer.reset();
                        this.stage = !this.stage;
                        break;
                    }
                    Module.mc.timer.timerSpeed = (float)((Number)this.maxTimerValue.get()).doubleValue();
                    if (!this.timer.hasElapsed(400L)) break;
                    this.timer.reset();
                    this.stage = !this.stage;
                    break;
                }
            }
            return;
        }
        Module.mc.timer.timerSpeed = 1.0f;
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lclub/tifality/module/impl/other/GameSpeed$TimerMode;", "", "(Ljava/lang/String;I)V", "TICK", "TIMER", "Client"})
    public static final class TimerMode
    extends Enum<TimerMode> {
        public static final /* enum */ TimerMode TICK;
        public static final /* enum */ TimerMode TIMER;
        private static final /* synthetic */ TimerMode[] $VALUES;

        static {
            TimerMode[] timerModeArray = new TimerMode[2];
            TimerMode[] timerModeArray2 = timerModeArray;
            timerModeArray[0] = TICK = new TimerMode();
            timerModeArray[1] = TIMER = new TimerMode();
            $VALUES = timerModeArray;
        }

        public static TimerMode[] values() {
            return (TimerMode[])$VALUES.clone();
        }

        public static TimerMode valueOf(String string) {
            return Enum.valueOf(TimerMode.class, string);
        }
    }
}

