/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.module.impl.movement;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.StepConfirmEvent;
import club.tifality.manager.event.impl.player.StepEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.movement.Phase;
import club.tifality.module.impl.movement.Speed;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.timer.TimerUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(label="Step", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001'B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u0004H\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0016J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0007J\u0010\u0010\u0011\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020&H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\r0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2={"Lclub/tifality/module/impl/movement/Step;", "Lclub/tifality/module/Module;", "()V", "cancelStep", "", "delayValue", "Lclub/tifality/property/impl/DoubleProperty;", "heightValue", "getHeightValue", "()Lclub/tifality/property/impl/DoubleProperty;", "isStep", "modeValue", "Lclub/tifality/property/impl/EnumProperty;", "Lclub/tifality/module/impl/movement/Step$StepMode;", "kotlin.jvm.PlatformType", "ncpNextStep", "", "onStep", "getOnStep", "()Z", "stepX", "", "stepY", "stepZ", "timer", "Lclub/tifality/utils/timer/TimerUtil;", "timerValue", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lclub/tifality/manager/event/impl/player/MoveEntityEvent;", "Lclub/tifality/manager/event/impl/player/StepEvent;", "onStepConfirm", "Lclub/tifality/manager/event/impl/player/StepConfirmEvent;", "onUpdate", "Lclub/tifality/manager/event/impl/player/UpdatePositionEvent;", "StepMode", "Client"})
public final class Step
extends Module {
    private final EnumProperty<StepMode> modeValue = new EnumProperty<Enum>("Mode", StepMode.NCP);
    private final DoubleProperty timerValue = new DoubleProperty("Timer", 0.5, 0.1, 1.0, 0.1);
    @NotNull
    private final DoubleProperty heightValue = new DoubleProperty("Height", 1.0, 0.6, 10.0, 0.1, Representation.DISTANCE);
    private final DoubleProperty delayValue = new DoubleProperty("Delay", 0.0, 0.0, 500.0, 1.0, Representation.MILLISECONDS);
    private boolean isStep;
    private double stepX;
    private double stepY;
    private double stepZ;
    private int ncpNextStep;
    private final TimerUtil timer = new TimerUtil();
    private final boolean cancelStep;

    @NotNull
    public final DoubleProperty getHeightValue() {
        return this.heightValue;
    }

    @Override
    public void onDisable() {
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        if (entityPlayerSP == null) {
            return;
        }
        EntityPlayerSP thePlayer = entityPlayerSP;
        thePlayer.stepHeight = 0.5f;
    }

    @Listener
    public final void onUpdate(@NotNull UpdatePositionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.stepY == new BigDecimal(this.stepY).setScale(3, RoundingMode.HALF_DOWN).doubleValue()) {
            Module.mc.timer.timerSpeed = 1.0f;
        }
    }

    @Listener
    public final void onMove(@NotNull MoveEntityEvent event) {
        block1: {
            block3: {
                block2: {
                    Intrinsics.checkNotNullParameter(event, "event");
                    StepMode mode = (StepMode)((Object)this.modeValue.get());
                    EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
                    if (entityPlayerSP == null) {
                        return;
                    }
                    EntityPlayerSP thePlayer = entityPlayerSP;
                    if (mode != StepMode.MOTIONNCP || !thePlayer.isCollidedHorizontally) break block1;
                    KeyBinding keyBinding = Module.mc.gameSettings.keyBindJump;
                    Intrinsics.checkNotNullExpressionValue(keyBinding, "mc.gameSettings.keyBindJump");
                    if (keyBinding.isKeyDown()) break block1;
                    if (!thePlayer.onGround || !this.couldStep()) break block2;
                    this.fakeJump();
                    thePlayer.motionY = 0.0;
                    event.setY(0.41999998688698);
                    this.ncpNextStep = 1;
                    break block1;
                }
                if (this.ncpNextStep != 1) break block3;
                event.setY(0.33319999363422);
                this.ncpNextStep = 2;
                break block1;
            }
            if (this.ncpNextStep != 2) break block1;
            double yaw = MovementUtils.getDirection();
            event.setY(0.24813599859094704);
            boolean bl = false;
            event.setX(-Math.sin(yaw) * 0.7);
            bl = false;
            event.setZ(Math.cos(yaw) * 0.7);
            this.ncpNextStep = 0;
        }
    }

    @Listener
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        if (entityPlayerSP == null) {
            return;
        }
        EntityPlayerSP thePlayer = entityPlayerSP;
        event.setStepHeight(this.cancelStep ? 0.0f : 1.0f);
        Tifality tifality = Tifality.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.INSTANCE");
        Phase phase = tifality.getModuleManager().getModule(Phase.class);
        Intrinsics.checkNotNull(phase);
        if (phase.isEnabled()) {
            event.setStepHeight(0.0f);
            return;
        }
        StepMode mode = (StepMode)((Object)this.modeValue.get());
        Tifality tifality2 = Tifality.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(tifality2, "Tifality.INSTANCE");
        Speed speed = tifality2.getModuleManager().getModule(Speed.class);
        if (speed == null) {
            throw new NullPointerException("null cannot be cast to non-null type club.tifality.module.impl.movement.Speed");
        }
        Speed speed2 = speed;
        if (speed2.isEnabled()) {
            Boolean bl = speed2.getStepCheck().get();
            Intrinsics.checkNotNullExpressionValue(bl, "speed.stepCheck.get()");
            if (bl.booleanValue()) {
                event.setStepHeight(0.0f);
                return;
            }
        }
        if (!thePlayer.onGround || !this.timer.hasElapsed(speed2.isEnabled() ? 100L : (long)((Number)this.delayValue.get()).doubleValue()) || mode == StepMode.MOTIONNCP) {
            thePlayer.stepHeight = 0.5f;
            event.setStepHeight(0.5f);
            return;
        }
        Double height = (Double)this.heightValue.get();
        thePlayer.stepHeight = (float)height.doubleValue();
        event.setStepHeight((float)height.doubleValue());
        if (event.getStepHeight() > 0.5f) {
            this.isStep = true;
            this.stepX = thePlayer.posX;
            this.stepY = thePlayer.posY;
            this.stepZ = thePlayer.posZ;
        }
    }

    @Listener
    public final void onStepConfirm(@NotNull StepConfirmEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityPlayerSP thePlayer = Module.mc.thePlayer;
        if (thePlayer == null || !this.isStep) {
            return;
        }
        if (thePlayer.getEntityBoundingBox().minY - this.stepY > 0.625) {
            if ((StepMode)((Object)this.modeValue.get()) == StepMode.NCP) {
                this.fakeJump();
                Module.mc.timer.timerSpeed = (float)((Number)this.timerValue.get()).doubleValue();
                Minecraft minecraft = Module.mc;
                Intrinsics.checkNotNullExpressionValue(minecraft, "mc");
                minecraft.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false));
                Minecraft minecraft2 = Module.mc;
                Intrinsics.checkNotNullExpressionValue(minecraft2, "mc");
                minecraft2.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false));
                this.timer.reset();
            }
        }
        this.isStep = false;
        this.stepX = 0.0;
        this.stepY = 0.0;
        this.stepZ = 0.0;
    }

    private final void fakeJump() {
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        if (entityPlayerSP == null) {
            return;
        }
        EntityPlayerSP thePlayer = entityPlayerSP;
        thePlayer.isAirBorne = true;
        thePlayer.triggerAchievement(StatList.jumpStat);
    }

    private final boolean couldStep() {
        double yaw = MovementUtils.getDirection();
        boolean bl = false;
        double x = -Math.sin(yaw) * 0.4;
        boolean bl2 = false;
        double z = Math.cos(yaw) * 0.4;
        WorldClient worldClient = Module.mc.theWorld;
        Intrinsics.checkNotNull(worldClient);
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        Intrinsics.checkNotNull(entityPlayerSP);
        return worldClient.getCollisionBoxes(entityPlayerSP.getEntityBoundingBox().offset(x, 1.001335979112147, z)).isEmpty();
    }

    public final boolean getOnStep() {
        return this.isEnabled() && this.isStep;
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lclub/tifality/module/impl/movement/Step$StepMode;", "", "(Ljava/lang/String;I)V", "VANILLA", "NCP", "MOTIONNCP", "Client"})
    private static final class StepMode
    extends Enum<StepMode> {
        public static final /* enum */ StepMode VANILLA;
        public static final /* enum */ StepMode NCP;
        public static final /* enum */ StepMode MOTIONNCP;
        private static final /* synthetic */ StepMode[] $VALUES;

        static {
            StepMode[] stepModeArray = new StepMode[3];
            StepMode[] stepModeArray2 = stepModeArray;
            stepModeArray[0] = VANILLA = new StepMode();
            stepModeArray[1] = NCP = new StepMode();
            stepModeArray[2] = MOTIONNCP = new StepMode();
            $VALUES = stepModeArray;
        }

        public static StepMode[] values() {
            return (StepMode[])$VALUES.clone();
        }

        public static StepMode valueOf(String string) {
            return Enum.valueOf(StepMode.class, string);
        }
    }
}

