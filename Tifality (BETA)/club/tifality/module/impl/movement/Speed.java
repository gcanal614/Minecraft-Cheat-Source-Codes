/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package club.tifality.module.impl.movement;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.api.annotations.Priority;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.module.impl.movement.Flight;
import club.tifality.module.impl.movement.Scaffold;
import club.tifality.module.impl.movement.Speed$WhenMappings;
import club.tifality.module.impl.movement.Step;
import club.tifality.module.impl.other.GameSpeed;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.MathUtils;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(label="Speed", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001:\u0005JKLMNB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0003J\b\u0010=\u001a\u00020:H\u0016J\b\u0010>\u001a\u00020:H\u0016J\u0010\u0010?\u001a\u00020:2\u0006\u0010;\u001a\u00020@H\u0003J\u0010\u0010A\u001a\u00020:2\u0006\u0010;\u001a\u00020@H\u0003J\u0010\u0010B\u001a\u00020:2\u0006\u0010C\u001a\u00020DH\u0007J\u0010\u0010E\u001a\u00020:2\u0006\u0010C\u001a\u00020<H\u0007J\u0010\u0010F\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0003J\u0010\u0010G\u001a\u00020:2\u0006\u0010C\u001a\u00020<H\u0007J\u0010\u0010H\u001a\u00020:2\u0006\u0010C\u001a\u00020<H\u0007J\u0012\u0010I\u001a\u00020:2\b\u0010C\u001a\u0004\u0018\u00010<H\u0007R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010\u0007\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\b0\b0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u001c\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00160\u00160\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00188BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u001c\u0010\u001a\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00180\u00180\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00180\u00180\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\"\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00180\u00180\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010$\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010%0%0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u00020'X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001f\u0010,\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00180\u00180\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u000e\u0010/\u001a\u00020'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u00104\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u000105050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\nR\u001c\u00107\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00180\u00180\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006O"}, d2={"Lclub/tifality/module/impl/movement/Speed;", "Lclub/tifality/module/Module;", "()V", "boostModeValue", "Lclub/tifality/property/impl/EnumProperty;", "Lclub/tifality/module/impl/movement/Speed$BoostMode;", "kotlin.jvm.PlatformType", "bypass", "Lclub/tifality/module/impl/movement/Speed$Bypass;", "getBypass", "()Lclub/tifality/property/impl/EnumProperty;", "chocoSpeed", "Lclub/tifality/property/impl/DoubleProperty;", "downStrafeValue", "fallDistValue", "getFallDistValue", "()Lclub/tifality/property/impl/DoubleProperty;", "fallTimerValue", "getFallTimerValue", "groundTimerValue", "getGroundTimerValue", "hopType", "Lclub/tifality/module/impl/movement/Speed$HopType;", "isWatchdog", "", "()Z", "lagBackCheckValue", "Lclub/tifality/property/Property;", "lastDist", "", "limitSpeedValue", "liquidCheck", "motionYValue", "movementSpeed", "noRotateSetValue", "normalTimerValue", "speedModeProperty", "Lclub/tifality/module/impl/movement/Speed$SpeedMode;", "stage", "", "getStage", "()I", "setStage", "(I)V", "stepCheck", "getStepCheck", "()Lclub/tifality/property/Property;", "stopTicks", "stopTicksValue", "strafeValue", "tacoSpeed", "timerAmountValue", "timerMode", "Lclub/tifality/module/impl/movement/Speed$Timer;", "getTimerMode", "timerValue", "watchdogSpeed", "chocoPie", "", "e", "Lclub/tifality/manager/event/impl/player/UpdatePositionEvent;", "onDisable", "onEnable", "onMove", "Lclub/tifality/manager/event/impl/player/MoveEntityEvent;", "onMoveEntityEvent", "onPacket", "event", "Lclub/tifality/manager/event/impl/packet/PacketReceiveEvent;", "onPreUpdate", "onUpdate", "onUpdatePosition", "onWhenYouSeeIt", "proGaming", "BoostMode", "Bypass", "HopType", "SpeedMode", "Timer", "Client"})
public final class Speed
extends Module {
    private final EnumProperty<SpeedMode> speedModeProperty = new EnumProperty<Enum>("Mode", SpeedMode.WATCHDOG);
    private final Property<Boolean> timerValue = new Property<Boolean>("Timer", true, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$isWatchdog$p(this.this$0);
        }
        {
            this.this$0 = speed;
        }
    });
    private final DoubleProperty chocoSpeed = new DoubleProperty("Choco Speed", 0.475, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.CHOCO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.3, 0.6, 5.0E-4);
    private final DoubleProperty watchdogSpeed = new DoubleProperty("Watchdog Speed", 0.475, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.WATCHDOG);
        }
        {
            this.this$0 = speed;
        }
    }, 0.475, 1.3, 5.0E-4);
    private final DoubleProperty tacoSpeed = new DoubleProperty("Taco Speed", 0.475, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.475, 1.3, 5.0E-4);
    private final DoubleProperty timerAmountValue = new DoubleProperty("Timer Amount", 1.6, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$isWatchdog$p(this.this$0);
        }
        {
            this.this$0 = speed;
        }
    }, 0.9, 1.6, 0.05);
    private final Property<Boolean> liquidCheck = new Property<Boolean>("Liquid Check", false, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.WATCHDOG) || Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.CHOCO);
        }
        {
            this.this$0 = speed;
        }
    });
    private final DoubleProperty motionYValue = new DoubleProperty("Motion Y", 0.4, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.0, 0.42, 0.01);
    private final EnumProperty<HopType> hopType = new EnumProperty<Enum>("Hop Type", HopType.Normal, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    });
    private final EnumProperty<BoostMode> boostModeValue = new EnumProperty<Enum>("Boost Mode", BoostMode.Yport, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    });
    @NotNull
    private final Property<Boolean> stepCheck = new Property<Boolean>("Step Check", false);
    private final DoubleProperty limitSpeedValue = new DoubleProperty("Limit Speed", 0.82, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.66, 0.85, 0.01);
    private final DoubleProperty strafeValue = new DoubleProperty("Strafe", 159.0, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 33.0, 159.0, 1.0);
    private final DoubleProperty downStrafeValue = new DoubleProperty("Down Strafe", 159.0, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 33.0, 159.0, 1.0);
    @NotNull
    private final EnumProperty<Bypass> bypass = new EnumProperty<Enum>("Bypass", Bypass.BypassOffset, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    });
    @NotNull
    private final EnumProperty<Timer> timerMode = new EnumProperty<Enum>("Timer", Timer.None, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    });
    private final Property<Boolean> lagBackCheckValue = new Property<Boolean>("Lag Back Check", true);
    private final Property<Boolean> noRotateSetValue = new Property<Boolean>("No Rotate Set", false);
    private final DoubleProperty stopTicksValue = new DoubleProperty("Stop Ticks", 16.0, 3.0, 25.0, 1.0);
    private final DoubleProperty normalTimerValue = new DoubleProperty("Normal Timer", 2.6, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.25, 5.0, 0.01);
    @NotNull
    private final DoubleProperty groundTimerValue = new DoubleProperty("Ground Timer", 1.2, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.25, 5.0, 0.01);
    @NotNull
    private final DoubleProperty fallDistValue = new DoubleProperty("Fall Dist", 255.0, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.0, 255.0, 1.0);
    @NotNull
    private final DoubleProperty fallTimerValue = new DoubleProperty("Fall Timer", 0.98, new Supplier<Boolean>(this){
        final /* synthetic */ Speed this$0;

        public final Boolean get() {
            return Speed.access$getSpeedModeProperty$p(this.this$0).isSelected((Enum)SpeedMode.TACO);
        }
        {
            this.this$0 = speed;
        }
    }, 0.25, 5.0, 0.01);
    private int stage;
    private double movementSpeed;
    private double lastDist;
    private int stopTicks;

    @NotNull
    public final Property<Boolean> getStepCheck() {
        return this.stepCheck;
    }

    @NotNull
    public final EnumProperty<Bypass> getBypass() {
        return this.bypass;
    }

    @NotNull
    public final EnumProperty<Timer> getTimerMode() {
        return this.timerMode;
    }

    @NotNull
    public final DoubleProperty getGroundTimerValue() {
        return this.groundTimerValue;
    }

    @NotNull
    public final DoubleProperty getFallDistValue() {
        return this.fallDistValue;
    }

    @NotNull
    public final DoubleProperty getFallTimerValue() {
        return this.fallTimerValue;
    }

    private final boolean isWatchdog() {
        return this.speedModeProperty.isSelected((SpeedMode)((Enum)SpeedMode.WATCHDOG));
    }

    public final int getStage() {
        return this.stage;
    }

    public final void setStage(int n) {
        this.stage = n;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.stopTicks = 0;
        Module.mc.timer.timerSpeed = 1.0f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (Module.mc.thePlayer == null) {
            return;
        }
        this.stopTicks = 0;
        Module.mc.timer.timerSpeed = 1.0f;
        Module.mc.thePlayer.motionX *= 0.25;
        Module.mc.thePlayer.motionZ *= 0.25;
    }

    @Listener
    public final void onUpdatePosition(@NotNull UpdatePositionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Tifality tifality = Tifality.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.INSTANCE");
        Step stepModule = tifality.getModuleManager().getModule(Step.class);
        if ((SpeedMode)((Object)this.speedModeProperty.get()) == SpeedMode.TACO) {
            Step step = stepModule;
            Intrinsics.checkNotNullExpressionValue(step, "stepModule");
            if (step.isEnabled() && stepModule.getOnStep() && this.stage <= 2) {
                this.movementSpeed = 0.0;
            }
        }
    }

    @Listener
    public final void proGaming(@Nullable UpdatePositionEvent event) {
        if (this.stopTicks > 0) {
            int n = this.stopTicks;
            this.stopTicks = n + -1;
            return;
        }
        if (this.speedModeProperty.isSelected((SpeedMode)((Enum)SpeedMode.TACO))) {
            if (MovementUtils.isMoving()) {
                if (Module.mc.thePlayer.onGround) {
                    MovementUtils.fakeJump();
                }
                EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
                Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                entityPlayerSP.setSprinting(true);
            }
            if ((HopType)((Object)this.hopType.get()) == HopType.Lower && !Module.mc.thePlayer.isCollidedHorizontally) {
                if (MathUtils.round(Module.mc.thePlayer.posY - (double)((int)Module.mc.thePlayer.posY), 3.0) == MathUtils.round(0.753, 3.0)) {
                    Module.mc.thePlayer.motionY -= 0.01;
                } else if (MathUtils.round(Module.mc.thePlayer.posY - (double)((int)Module.mc.thePlayer.posY), 3.0) == MathUtils.round(0.991, 3.0)) {
                    Module.mc.thePlayer.motionY -= 0.02;
                } else if (MathUtils.round(Module.mc.thePlayer.posY - (double)((int)Module.mc.thePlayer.posY), 3.0) == MathUtils.round(0.136, 3.0)) {
                    Module.mc.thePlayer.motionY -= 0.01;
                } else if (MathUtils.round(Module.mc.thePlayer.posY - (double)((int)Module.mc.thePlayer.posY), 3.0) == MathUtils.round(0.19, 3.0)) {
                    Module.mc.thePlayer.motionY -= 0.02;
                } else if (MathUtils.round(Module.mc.thePlayer.posY - (double)((int)Module.mc.thePlayer.posY), 3.0) == MathUtils.round(0.902, 3.0)) {
                    Module.mc.thePlayer.motionY -= 0.01;
                }
            }
            if ((HopType)((Object)this.hopType.get()) == HopType.Higher && (double)Module.mc.thePlayer.fallDistance <= 1.4) {
                Module.mc.thePlayer.motionY += 0.005574432;
            }
            GameSpeed timer = ModuleManager.getInstance(GameSpeed.class);
            Step step = ModuleManager.getInstance(Step.class);
            if (!step.getOnStep()) {
                GameSpeed gameSpeed = timer;
                Intrinsics.checkNotNullExpressionValue(gameSpeed, "timer");
                if (!gameSpeed.isEnabled() && (Timer)((Object)this.timerMode.get()) != Timer.None && MovementUtils.isMoving()) {
                    if ((double)Module.mc.thePlayer.fallDistance > 0.0) {
                        double d = Module.mc.thePlayer.fallDistance;
                        Object t = this.fallDistValue.get();
                        Intrinsics.checkNotNullExpressionValue(t, "fallDistValue.get()");
                        Module.mc.timer.timerSpeed = d > ((Number)t).doubleValue() ? (float)((Number)this.normalTimerValue.get()).doubleValue() : (float)((Number)this.fallTimerValue.get()).doubleValue();
                    } else {
                        Module.mc.timer.timerSpeed = Module.mc.thePlayer.onGround ? (float)((Number)this.groundTimerValue.get()).doubleValue() : (float)((Number)this.normalTimerValue.get()).doubleValue();
                    }
                }
            }
        }
    }

    @Listener
    public final void onWhenYouSeeIt(@NotNull UpdatePositionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.stopTicks > 0) {
            return;
        }
        if (this.speedModeProperty.isSelected((SpeedMode)((Enum)SpeedMode.TACO))) {
            if ((Bypass)((Object)this.bypass.get()) == Bypass.SpoofGround && MovementUtils.isMoving() && Module.mc.thePlayer.onGround) {
                event.setPosY(event.getPosY() + ThreadLocalRandom.current().nextDouble() / 1000.0);
                event.setOnGround(true);
            }
            if ((Bypass)((Object)this.bypass.get()) == Bypass.BypassOffset) {
                MovementUtils.bypassOffSet(event);
            }
        }
    }

    @Listener
    private final void chocoPie(UpdatePositionEvent e) {
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        if (entityPlayerSP.isSneaking() || this.stopTicks > 0) {
            return;
        }
        if (this.speedModeProperty.isSelected((SpeedMode)((Enum)SpeedMode.TACO)) && MovementUtils.isMoving()) {
            EntityPlayerSP entityPlayerSP2 = Module.mc.thePlayer;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP2, "mc.thePlayer");
            entityPlayerSP2.setSprinting(true);
        }
    }

    @Listener
    public final void onPreUpdate(@NotNull UpdatePositionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if ((SpeedMode)((Object)this.speedModeProperty.get()) == SpeedMode.TACO) {
            double xDist = Module.mc.thePlayer.posX - Module.mc.thePlayer.prevPosX;
            double zDist = Module.mc.thePlayer.posZ - Module.mc.thePlayer.prevPosZ;
            double d = xDist * xDist + zDist * zDist;
            boolean bl = false;
            this.lastDist = Math.sqrt(d);
        }
    }

    @Listener
    private final void onMove(MoveEntityEvent e) {
        block3: {
            block2: {
                EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
                Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                if (entityPlayerSP.isSneaking() || this.stopTicks > 0) break block2;
                Tifality tifality = Tifality.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.INSTANCE");
                Flight flight = tifality.getModuleManager().getModule(Flight.class);
                Intrinsics.checkNotNullExpressionValue(flight, "Tifality.INSTANCE.module\u2026odule(Flight::class.java)");
                if (!flight.isEnabled()) break block3;
            }
            return;
        }
    }

    @Listener
    public final void onPacket(@NotNull PacketReceiveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (Module.mc.thePlayer == null) {
            return;
        }
        Boolean bl = this.lagBackCheckValue.get();
        Intrinsics.checkNotNullExpressionValue(bl, "lagBackCheckValue.get()");
        if (bl.booleanValue() && event.getPacket() instanceof S08PacketPlayerPosLook) {
            Module.mc.thePlayer.motionX *= 0.0;
            Module.mc.thePlayer.motionY *= 0.0;
            Module.mc.thePlayer.motionZ *= 0.0;
            this.stopTicks = (int)((Number)this.stopTicksValue.get()).doubleValue();
            Boolean bl2 = this.noRotateSetValue.get();
            Intrinsics.checkNotNullExpressionValue(bl2, "noRotateSetValue.get()");
            if (bl2.booleanValue()) {
                Packet<?> packet = event.getPacket();
                if (packet == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.network.play.server.S08PacketPlayerPosLook");
                }
                S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook)packet;
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    @Listener(value=Priority.LOW)
    private final void onMoveEntityEvent(MoveEntityEvent e) {
        block38: {
            block39: {
                block37: {
                    baseSpeed = MovementUtils.getBaseMoveSpeed(0.2873);
                    if ((SpeedMode)this.speedModeProperty.get() != SpeedMode.CHOCO) break block37;
                    blockPos = new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ);
                    if (MovementUtils.isMoving()) {
                        v0 = this.liquidCheck.get();
                        Intrinsics.checkNotNullExpressionValue(v0, "liquidCheck.get()");
                        if (v0.booleanValue()) {
                            v1 = Wrapper.getPlayer();
                            Intrinsics.checkNotNullExpressionValue(v1, "Wrapper.getPlayer()");
                            if (!v1.isInWater()) {
                                v2 = Wrapper.getPlayer();
                                Intrinsics.checkNotNullExpressionValue(v2, "Wrapper.getPlayer()");
                                if (!v2.isInLava()) {
                                    v3 = ModuleManager.getInstance(GameSpeed.class);
                                    Intrinsics.checkNotNullExpressionValue(v3, "ModuleManager.getInstance(GameSpeed::class.java)");
                                    if (!v3.isEnabled()) {
                                        Module.mc.timer.timerSpeed = (float)((Number)this.timerAmountValue.get()).doubleValue();
                                    }
                                    v4 = Wrapper.getWorld().getBlockState(blockPos);
                                    Intrinsics.checkNotNullExpressionValue(v4, "Wrapper.getWorld().getBlockState(blockPos)");
                                    if (v4.getBlock() instanceof BlockStairs) {
                                        MovementUtils.setMotion(MovementUtils.getBaseMoveSpeed(0.2873));
                                    } else if (Wrapper.getPlayer().onGround) {
                                        Module.mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0.39999998, true);
                                        v5 = ((Number)this.chocoSpeed.getValue()).doubleValue() + (double)MovementUtils.getSpeedEffect() * 0.1;
                                        v6 = ModuleManager.getInstance(Scaffold.class);
                                        Intrinsics.checkNotNullExpressionValue(v6, "ModuleManager.getInstance(Scaffold::class.java)");
                                        MovementUtils.setMotion(RangesKt.coerceAtLeast(v5 * (v6.isEnabled() != false ? 0.66 : 1.0), baseSpeed));
                                    } else {
                                        MovementUtils.setMotion(MovementUtils.getSpeed());
                                    }
                                    ** GOTO lbl180
                                }
                            }
                        }
                    }
                    break block38;
                }
                if ((SpeedMode)this.speedModeProperty.get() != SpeedMode.WATCHDOG) break block39;
                v7 = Module.mc.thePlayer;
                if (v7 == null) {
                    return;
                }
                player = v7;
                if (MovementUtils.isMoving()) {
                    if (player.isCollidedHorizontally) {
                        MovementUtils.setMotio(e, MovementUtils.getBaseMoveSpeed(0.258));
                    }
                }
                break block38;
            }
            if ((SpeedMode)this.speedModeProperty.get() != SpeedMode.TACO) break block38;
            v8 = Module.mc.thePlayer;
            if (v8 == null) {
                return;
            }
            thePlayer = v8;
            v9 = Tifality.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(v9, "Tifality.INSTANCE");
            scaffoldModule = v9.getModuleManager().getModule(Scaffold.class);
            normalSpeed = ((Number)this.tacoSpeed.get()).doubleValue() * MovementUtils.getBaseMoveSpeed();
            bhopSpeed = ((Number)this.tacoSpeed.get()).doubleValue() * MovementUtils.getBaseMoveSpeed();
            lowhopSpeed = ((Number)this.tacoSpeed.get()).doubleValue() * MovementUtils.getBaseMoveSpeed();
            yportSpeed = ((Number)this.tacoSpeed.get()).doubleValue() * MovementUtils.getBaseMoveSpeed();
            v10 = slowDown = (double)Module.mc.thePlayer.fallDistance > 0.0;
            if (!MovementUtils.isMoving()) break block38;
            v11 = this.stepCheck.get();
            Intrinsics.checkNotNullExpressionValue(v11, "stepCheck.get()");
            thePlayer.stepHeight = v11 != false ? 0.6f : 1.0f;
            switch (this.stage) {
                case 2: {
                    if (!thePlayer.onGround) ** GOTO lbl109
                    v12 = e;
                    v13 = (BoostMode)this.boostModeValue.get();
                    if (v13 == null) ** GOTO lbl-1000
                    switch (Speed$WhenMappings.$EnumSwitchMapping$0[v13.ordinal()]) {
                        case 1: {
                            var15_12 = MovementUtils.getJumpBoostModifier(0.39999998, true);
                            var17_13 = false;
                            var18_16 = false;
                            var19_18 = var15_12;
                            var22_21 = v12;
                            $i$a$-also-Speed$onMoveEntityEvent$1 = false;
                            thePlayer.motionY = it;
                            var23_27 = Unit.INSTANCE;
                            v12 = var22_21;
                            v14 = var15_12;
                            break;
                        }
                        case 2: {
                            var15_12 = MovementUtils.getJumpBoostModifier(0.16, false);
                            var17_14 = false;
                            var18_16 = false;
                            it = var15_12;
                            var22_22 = v12;
                            $i$a$-also-Speed$onMoveEntityEvent$2 = false;
                            thePlayer.motionY = it;
                            var23_28 = Unit.INSTANCE;
                            v12 = var22_22;
                            v14 = var15_12;
                            break;
                        }
                        case 3: {
                            v14 = MovementUtils.getJumpBoostModifier(0.1, false);
                            break;
                        }
                        default: lbl-1000:
                        // 2 sources

                        {
                            var15_12 = MovementUtils.getJumpBoostModifier(((Number)this.motionYValue.get()).doubleValue(), true);
                            var17_15 = false;
                            var18_16 = false;
                            it = var15_12;
                            var22_23 = v12;
                            $i$a$-also-Speed$onMoveEntityEvent$3 = false;
                            thePlayer.motionY = it;
                            var23_29 = Unit.INSTANCE;
                            v12 = var22_23;
                            v14 = var15_12;
                        }
                    }
                    v12.setY(v14);
lbl109:
                    // 2 sources

                    v15 = (BoostMode)this.boostModeValue.get();
                    if (v15 == null) ** GOTO lbl153
                    switch (Speed$WhenMappings.$EnumSwitchMapping$1[v15.ordinal()]) {
                        case 1: {
                            if (MovementUtils.isOnIce()) {
                                v16 = 1.12 * bhopSpeed;
                                break;
                            }
                            if (MovementUtils.isInLiquid()) {
                                v16 = 0.5 * bhopSpeed;
                                break;
                            }
                            if (thePlayer.isInLava()) {
                                v16 = 0.25 * bhopSpeed;
                                break;
                            }
                            v16 = bhopSpeed;
                            break;
                        }
                        case 2: {
                            if (MovementUtils.isOnIce()) {
                                v16 = 1.12 * lowhopSpeed;
                                break;
                            }
                            if (MovementUtils.isInLiquid()) {
                                v16 = 0.5 * lowhopSpeed;
                                break;
                            }
                            if (thePlayer.isInLava()) {
                                v16 = 0.25 * lowhopSpeed;
                                break;
                            }
                            v16 = lowhopSpeed;
                            break;
                        }
                        case 3: {
                            if (MovementUtils.isOnIce()) {
                                v16 = 1.12 * yportSpeed;
                                break;
                            }
                            if (MovementUtils.isInLiquid()) {
                                v16 = 0.5 * yportSpeed;
                                break;
                            }
                            if (thePlayer.isInLava()) {
                                v16 = 0.25 * yportSpeed;
                                break;
                            }
                            v16 = yportSpeed;
                            break;
                        }
                        default: {
lbl153:
                            // 2 sources

                            v16 = MovementUtils.isOnIce() != false ? 1.12 * normalSpeed : (MovementUtils.isInLiquid() != false ? 0.5 * normalSpeed : (thePlayer.isInLava() != false ? 0.25 * normalSpeed : normalSpeed));
                        }
                    }
                    this.movementSpeed = v16;
                    break;
                }
                case 3: {
                    difference = ((Number)this.limitSpeedValue.get()).doubleValue() * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                    this.movementSpeed = this.lastDist - difference;
                    if ((Timer)this.timerMode.get() != Timer.None) break;
                    Module.mc.timer.timerSpeed = 1.07f;
                    break;
                }
                default: {
                    if (MovementUtils.isOnGround(-thePlayer.motionY) || thePlayer.isCollidedVertically && thePlayer.onGround) {
                        this.stage = 1;
                    }
                    v17 = slowDown != false ? (Double)this.downStrafeValue.get() : (Double)this.strafeValue.get();
                    Intrinsics.checkNotNullExpressionValue(v17, "if (slowDown) downStrafe\u2026() else strafeValue.get()");
                    this.movementSpeed = this.lastDist - this.lastDist / v17;
                }
            }
            var15_12 = this.movementSpeed;
            var18_17 = MovementUtils.getBaseMoveSpeed();
            var20_30 = false;
            this.movementSpeed = Math.max(var15_12, var18_17);
            v18 = scaffoldModule;
            Intrinsics.checkNotNullExpressionValue(v18, "scaffoldModule");
            MovementUtils.setMotion(e, v18.isEnabled() != false ? this.movementSpeed * 0.5 : this.movementSpeed, 1.0);
            v19 = this;
            ++v19.stage;
            v19.stage;
        }
    }

    @Listener
    private final void onUpdate(UpdatePositionEvent e) {
        if ((SpeedMode)((Object)this.speedModeProperty.get()) == SpeedMode.WATCHDOG) {
            EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
            if (entityPlayerSP == null) {
                return;
            }
            EntityPlayerSP player = entityPlayerSP;
            Scaffold scaffoldModule = ModuleManager.getInstance(Scaffold.class);
            GameSpeed timer = ModuleManager.getInstance(GameSpeed.class);
            BlockPos blockPos = new BlockPos(player.posX, player.posY, player.posZ);
            if (MovementUtils.isMoving()) {
                if (blockPos instanceof BlockStairs) {
                    MovementUtils.setMotion(MovementUtils.getBaseMoveSpeed(0.2873));
                } else if (player.onGround) {
                    player.motionY = MovementUtils.getJumpBoostModifier(0.39999998, true);
                    double d = ((Number)this.watchdogSpeed.get()).doubleValue() + (double)MovementUtils.getSpeedEffect() * 0.1;
                    Scaffold scaffold = scaffoldModule;
                    Intrinsics.checkNotNull(scaffold);
                    double d2 = d * (scaffold.isEnabled() ? 0.66 : 1.0);
                    double d3 = MovementUtils.getBaseMoveSpeed(0.2873);
                    boolean bl = false;
                    MovementUtils.setMotion(Math.max(d2, d3));
                } else {
                    GameSpeed gameSpeed = timer;
                    Intrinsics.checkNotNull(gameSpeed);
                    if (!gameSpeed.isEnabled()) {
                        Module.mc.timer.timerSpeed = 1.07f;
                    } else {
                        Boolean bl = this.timerValue.get();
                        Intrinsics.checkNotNullExpressionValue(bl, "timerValue.get()");
                        if (bl.booleanValue()) {
                            Module.mc.timer.timerSpeed = (float)((Number)this.timerAmountValue.get()).doubleValue();
                        }
                    }
                    MovementUtils.setMotion(MovementUtils.getSpeed());
                }
            } else {
                player.motionX *= 0.0;
                player.motionZ *= 0.0;
            }
        }
    }

    public Speed() {
        this.setSuffixListener(this.speedModeProperty);
    }

    public static final /* synthetic */ boolean access$isWatchdog$p(Speed $this) {
        return $this.isWatchdog();
    }

    public static final /* synthetic */ EnumProperty access$getSpeedModeProperty$p(Speed $this) {
        return $this.speedModeProperty;
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lclub/tifality/module/impl/movement/Speed$SpeedMode;", "", "(Ljava/lang/String;I)V", "WATCHDOG", "TACO", "CHOCO", "Client"})
    public static final class SpeedMode
    extends Enum<SpeedMode> {
        public static final /* enum */ SpeedMode WATCHDOG;
        public static final /* enum */ SpeedMode TACO;
        public static final /* enum */ SpeedMode CHOCO;
        private static final /* synthetic */ SpeedMode[] $VALUES;

        static {
            SpeedMode[] speedModeArray = new SpeedMode[3];
            SpeedMode[] speedModeArray2 = speedModeArray;
            speedModeArray[0] = WATCHDOG = new SpeedMode();
            speedModeArray[1] = TACO = new SpeedMode();
            speedModeArray[2] = CHOCO = new SpeedMode();
            $VALUES = speedModeArray;
        }

        public static SpeedMode[] values() {
            return (SpeedMode[])$VALUES.clone();
        }

        public static SpeedMode valueOf(String string) {
            return Enum.valueOf(SpeedMode.class, string);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lclub/tifality/module/impl/movement/Speed$HopType;", "", "(Ljava/lang/String;I)V", "Normal", "Lower", "Higher", "Client"})
    public static final class HopType
    extends Enum<HopType> {
        public static final /* enum */ HopType Normal;
        public static final /* enum */ HopType Lower;
        public static final /* enum */ HopType Higher;
        private static final /* synthetic */ HopType[] $VALUES;

        static {
            HopType[] hopTypeArray = new HopType[3];
            HopType[] hopTypeArray2 = hopTypeArray;
            hopTypeArray[0] = Normal = new HopType();
            hopTypeArray[1] = Lower = new HopType();
            hopTypeArray[2] = Higher = new HopType();
            $VALUES = hopTypeArray;
        }

        public static HopType[] values() {
            return (HopType[])$VALUES.clone();
        }

        public static HopType valueOf(String string) {
            return Enum.valueOf(HopType.class, string);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lclub/tifality/module/impl/movement/Speed$BoostMode;", "", "(Ljava/lang/String;I)V", "Normal", "Bhop", "LowHop", "Yport", "Client"})
    public static final class BoostMode
    extends Enum<BoostMode> {
        public static final /* enum */ BoostMode Normal;
        public static final /* enum */ BoostMode Bhop;
        public static final /* enum */ BoostMode LowHop;
        public static final /* enum */ BoostMode Yport;
        private static final /* synthetic */ BoostMode[] $VALUES;

        static {
            BoostMode[] boostModeArray = new BoostMode[4];
            BoostMode[] boostModeArray2 = boostModeArray;
            boostModeArray[0] = Normal = new BoostMode();
            boostModeArray[1] = Bhop = new BoostMode();
            boostModeArray[2] = LowHop = new BoostMode();
            boostModeArray[3] = Yport = new BoostMode();
            $VALUES = boostModeArray;
        }

        public static BoostMode[] values() {
            return (BoostMode[])$VALUES.clone();
        }

        public static BoostMode valueOf(String string) {
            return Enum.valueOf(BoostMode.class, string);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lclub/tifality/module/impl/movement/Speed$Bypass;", "", "(Ljava/lang/String;I)V", "SpoofGround", "BypassOffset", "None", "Client"})
    public static final class Bypass
    extends Enum<Bypass> {
        public static final /* enum */ Bypass SpoofGround;
        public static final /* enum */ Bypass BypassOffset;
        public static final /* enum */ Bypass None;
        private static final /* synthetic */ Bypass[] $VALUES;

        static {
            Bypass[] bypassArray = new Bypass[3];
            Bypass[] bypassArray2 = bypassArray;
            bypassArray[0] = SpoofGround = new Bypass();
            bypassArray[1] = BypassOffset = new Bypass();
            bypassArray[2] = None = new Bypass();
            $VALUES = bypassArray;
        }

        public static Bypass[] values() {
            return (Bypass[])$VALUES.clone();
        }

        public static Bypass valueOf(String string) {
            return Enum.valueOf(Bypass.class, string);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lclub/tifality/module/impl/movement/Speed$Timer;", "", "(Ljava/lang/String;I)V", "Custom", "Stage", "None", "Client"})
    public static final class Timer
    extends Enum<Timer> {
        public static final /* enum */ Timer Custom;
        public static final /* enum */ Timer Stage;
        public static final /* enum */ Timer None;
        private static final /* synthetic */ Timer[] $VALUES;

        static {
            Timer[] timerArray = new Timer[3];
            Timer[] timerArray2 = timerArray;
            timerArray[0] = Custom = new Timer();
            timerArray[1] = Stage = new Timer();
            timerArray[2] = None = new Timer();
            $VALUES = timerArray;
        }

        public static Timer[] values() {
            return (Timer[])$VALUES.clone();
        }

        public static Timer valueOf(String string) {
            return Enum.valueOf(Timer.class, string);
        }
    }
}

