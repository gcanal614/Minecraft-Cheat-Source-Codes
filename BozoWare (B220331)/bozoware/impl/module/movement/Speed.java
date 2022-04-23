// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import bozoware.impl.module.player.BlockFly;
import bozoware.base.BozoWare;
import bozoware.base.util.Wrapper;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.util.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.player.PlayerMoveEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Speed", moduleCategory = ModuleCategory.MOVEMENT)
public class Speed extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<PlayerMoveEvent> onPlayerMoveEvent;
    private final EnumProperty<SpeedModes> speedMode;
    private final EnumProperty<watchdogSpeedModes> watchdogSpeedMode;
    private final ValueProperty<Double> Speed;
    private final ValueProperty<Double> funnyTicks;
    private final BooleanProperty groundStrafeBool;
    public final BooleanProperty ffBool;
    private final ValueProperty<Float> fSpeed;
    public final BooleanProperty timerBool;
    private final ValueProperty<Double> timerMin;
    private final ValueProperty<Double> timerMax;
    private final BooleanProperty flagCheckBool;
    private double bms;
    double difference;
    double motionY;
    boolean boosted;
    boolean reset;
    boolean lastReset;
    private boolean wasOnGround;
    private int stage;
    private int hop;
    private int hops;
    private double rounded;
    private double moveSpeed;
    private double lastDist;
    private double speed;
    public double startY;
    private double hDist;
    private boolean hDistSlowdown;
    private double[] values;
    public TimerUtil timer;
    static int bruh;
    public boolean nigger;
    public boolean cooldown;
    public boolean falling;
    
    public Speed() {
        this.speedMode = new EnumProperty<SpeedModes>("Mode", SpeedModes.NCP, this);
        this.watchdogSpeedMode = new EnumProperty<watchdogSpeedModes>("Mode", watchdogSpeedModes.Bhop, this);
        this.Speed = new ValueProperty<Double>("Speed", 5.0, 0.0, 10.0, this);
        this.funnyTicks = new ValueProperty<Double>("Funcraft Ticks", 5.0, 5.0, 20.0, this);
        this.groundStrafeBool = new BooleanProperty("Ground Strafe", false, this);
        this.ffBool = new BooleanProperty("FastFall", false, this);
        this.fSpeed = new ValueProperty<Float>("Fall Speed", 0.42f, 0.01f, 1.0f, this);
        this.timerBool = new BooleanProperty("Timer", false, this);
        this.timerMin = new ValueProperty<Double>("Timer Min", 0.75, 0.1, 5.0, this);
        this.timerMax = new ValueProperty<Double>("Timer Max", 1.45, 0.1, 5.0, this);
        this.flagCheckBool = new BooleanProperty("Flag Check", false, this);
        this.values = new double[] { 0.08, 0.09316090325960147, 1.35, 2.149, 0.66 };
        this.timer = new TimerUtil();
        this.nigger = false;
        this.cooldown = false;
        this.watchdogSpeedMode.setHidden(true);
        this.groundStrafeBool.setHidden(true);
        this.funnyTicks.setHidden(true);
        this.timerMax.setHidden(true);
        this.timerMin.setHidden(true);
        this.fSpeed.setHidden(true);
        this.timerBool.onValueChange = (() -> {
            this.timerMax.setHidden(!this.timerBool.getPropertyValue());
            this.timerMin.setHidden(!this.timerBool.getPropertyValue());
            return;
        });
        this.ffBool.onValueChange = (() -> this.fSpeed.setHidden(!this.ffBool.getPropertyValue()));
        this.speedMode.onValueChange = (() -> {
            if (this.speedMode.getPropertyValue().equals(SpeedModes.Funcraft2)) {
                this.funnyTicks.setHidden(false);
            }
            else {
                this.funnyTicks.setHidden(true);
            }
            if (this.speedMode.getPropertyValue().equals(SpeedModes.Watchdog)) {
                this.watchdogSpeedMode.setHidden(false);
            }
            else {
                this.watchdogSpeedMode.setHidden(true);
            }
            return;
        });
        this.setModuleBind(33);
        this.setModuleSuffix(this.speedMode.getPropertyValue().toString());
        this.onModuleDisabled = (() -> {
            bozoware.impl.module.movement.Speed.mc.timer.timerSpeed = 1.0f;
            bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = false;
            this.startY = 0.0;
            this.reset = true;
            this.speed = 0.0;
            this.moveSpeed = 0.0;
            this.lastDist = 0.0;
            this.stage = 0;
            return;
        });
        this.onModuleEnabled = (() -> {
            bozoware.impl.module.movement.Speed.bruh = (int)System.currentTimeMillis();
            this.moveSpeed = MovementUtil.getBaseMoveSpeed();
            this.lastDist = 0.0;
            this.stage = 0;
            this.reset = true;
            this.speed = MovementUtil.getBaseMoveSpeed();
            this.hops = 1;
            this.moveSpeed = 0.0;
            this.lastDist = 0.0;
            this.stage = 0;
            return;
        });
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S08PacketPlayerPosLook && this.flagCheckBool.getPropertyValue() && bozoware.impl.module.movement.Speed.mc.thePlayer != null && bozoware.impl.module.movement.Speed.mc.theWorld != null) {
                this.toggleModule();
            }
            return;
        });
        final double x;
        final double y;
        final double z;
        EntityPlayerSP thePlayer;
        final double motionY;
        int i;
        EntityPlayerSP thePlayer2;
        final double motionY2;
        int j;
        EntityPlayerSP thePlayer3;
        final double motionY3;
        this.onPlayerMoveEvent = (e -> {
            x = bozoware.impl.module.movement.Speed.mc.thePlayer.posX;
            y = bozoware.impl.module.movement.Speed.mc.thePlayer.posY;
            z = bozoware.impl.module.movement.Speed.mc.thePlayer.posZ;
            switch (this.speedMode.getPropertyValue()) {
                case Funcraft: {
                    if (MovementUtil.isMoving()) {
                        this.bms = MovementUtil.getBaseMoveSpeed();
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            if (this.reset) {
                                thePlayer = bozoware.impl.module.movement.Speed.mc.thePlayer;
                                e.setMotionY(thePlayer.motionY = motionY);
                                this.speed *= 2.1449999809265137;
                            }
                            else {
                                this.speed = this.bms;
                            }
                        }
                        else if (this.reset) {
                            this.speed = this.lastDist - 0.66 * (this.lastDist - this.bms);
                        }
                        else {
                            this.speed = this.lastDist - this.lastDist / 159.0;
                        }
                        MovementUtil.setSpeed(e, this.speed);
                        this.reset = bozoware.impl.module.movement.Speed.mc.thePlayer.onGround;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Funcraft2: {
                    if (!bozoware.impl.module.movement.Speed.mc.thePlayer.isMoving()) {
                        MovementUtil.setSpeed(e, this.moveSpeed = 0.0);
                        return;
                    }
                    else {
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            for (i = 0; i < this.funnyTicks.getPropertyValue(); ++i) {
                                MovementUtil.hClip(0.15);
                                bozoware.impl.module.movement.Speed.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(bozoware.impl.module.movement.Speed.mc.thePlayer.posX, bozoware.impl.module.movement.Speed.mc.thePlayer.posY, bozoware.impl.module.movement.Speed.mc.thePlayer.posZ, true));
                            }
                            thePlayer2 = bozoware.impl.module.movement.Speed.mc.thePlayer;
                            e.setMotionY(thePlayer2.motionY = motionY2);
                            this.moveSpeed = 0.6;
                            this.nigger = true;
                        }
                        else if (this.nigger) {
                            this.moveSpeed = 0.8;
                            this.nigger = false;
                            MovementUtil.setSpeed(e, this.moveSpeed);
                            return;
                        }
                        else {
                            this.moveSpeed = Math.max(this.moveSpeed - this.moveSpeed / 154.0, MovementUtil.getBaseMoveSpeed());
                        }
                        MovementUtil.setSpeed(e, this.moveSpeed);
                        break;
                    }
                    break;
                }
                case NCPClip: {
                    if (!bozoware.impl.module.movement.Speed.mc.thePlayer.isMoving()) {
                        MovementUtil.setSpeed(e, this.moveSpeed = 0.0);
                        return;
                    }
                    else {
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            for (j = 0; j < 1; ++j) {
                                MovementUtil.hClip(0.16);
                                bozoware.impl.module.movement.Speed.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(bozoware.impl.module.movement.Speed.mc.thePlayer.posX, bozoware.impl.module.movement.Speed.mc.thePlayer.posY, bozoware.impl.module.movement.Speed.mc.thePlayer.posZ, true));
                            }
                            thePlayer3 = bozoware.impl.module.movement.Speed.mc.thePlayer;
                            e.setMotionY(thePlayer3.motionY = motionY3);
                            this.moveSpeed = 0.4;
                            this.nigger = true;
                        }
                        else if (this.nigger) {
                            this.moveSpeed = 0.4;
                            this.nigger = false;
                            MovementUtil.setSpeed(e, this.moveSpeed);
                            return;
                        }
                        else {
                            this.moveSpeed = Math.max(this.moveSpeed - this.moveSpeed / 159.0, MovementUtil.getBaseMoveSpeed());
                        }
                        MovementUtil.setSpeed(e, this.moveSpeed);
                        break;
                    }
                    break;
                }
            }
            return;
        });
        double timerMinClamped;
        double bruh;
        double x2;
        double y2;
        double z2;
        double timerMinClamped2;
        double[] xZCalculations;
        int groundY;
        boolean canJump;
        boolean canJump2;
        double height;
        boolean canStep;
        EntityPlayerSP thePlayer4;
        double motionY4;
        this.onUpdatePositionEvent = (e -> {
            if (this.timerBool.getPropertyValue()) {
                timerMinClamped = MathHelper.clamp_double(this.timerMin.getPropertyValue(), 0.1, this.timerMax.getPropertyValue() - 0.1);
                bruh = ThreadLocalRandom.current().nextDouble(timerMinClamped, (double)this.timerMax.getPropertyValue());
                bozoware.impl.module.movement.Speed.mc.timer.timerSpeed = (float)bruh;
            }
            if (this.cooldown) {
                this.timer.reset();
                if (this.timer.hasReached(500L)) {
                    this.cooldown = false;
                }
                return;
            }
            else {
                this.lastDist = MovementUtil.lastDist();
                x2 = bozoware.impl.module.movement.Speed.mc.thePlayer.posX;
                y2 = bozoware.impl.module.movement.Speed.mc.thePlayer.posY;
                z2 = bozoware.impl.module.movement.Speed.mc.thePlayer.posZ;
                if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                    this.startY = bozoware.impl.module.movement.Speed.mc.thePlayer.posY;
                }
                if (this.timerBool.getPropertyValue()) {
                    timerMinClamped2 = MathHelper.clamp_double(this.timerMin.getPropertyValue(), 0.1, this.timerMax.getPropertyValue() - 0.1);
                    ThreadLocalRandom.current().nextDouble(timerMinClamped2, (double)this.timerMax.getPropertyValue());
                }
                switch (this.speedMode.getPropertyValue()) {
                    case LowHop: {
                        if (e.isPre()) {
                            if (bozoware.impl.module.movement.Speed.mc.thePlayer.isCollidedHorizontally) {}
                            if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && !bozoware.impl.module.movement.Speed.mc.thePlayer.isCollidedHorizontally) {
                                bozoware.impl.module.movement.Speed.mc.thePlayer.onGround = false;
                                bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.3499999940395355;
                                break;
                            }
                            else if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && bozoware.impl.module.movement.Speed.mc.thePlayer.isCollidedVertically) {
                                bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = true;
                                this.speed = 1.1699999570846558;
                                break;
                            }
                            else {
                                bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = false;
                                if (this.speed > 0.94) {
                                    this.speed -= 0.01;
                                }
                                MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * this.speed - Math.random() / 500.0);
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Packet: {
                        xZCalculations = new double[] { -Math.sin(Math.toRadians(e.getYaw())) * MovementUtil.getBaseMoveSpeed(), Math.cos(Math.toRadians(e.getYaw())) * MovementUtil.getBaseMoveSpeed() };
                        if (e.isPre() && bozoware.impl.module.movement.Speed.mc.thePlayer.isMoving() && bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(e.getX() - xZCalculations[0], e.getY(), e.getZ() - xZCalculations[1], e.isOnGround()));
                        }
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && bozoware.impl.module.movement.Speed.mc.thePlayer.isMoving()) {
                            if (bozoware.impl.module.movement.Speed.mc.thePlayer.ticksExisted % 2 == 0) {
                                MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * 1.15);
                            }
                            else {
                                MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed());
                            }
                        }
                        e.setY(e.getY() + 0.015625);
                        break;
                    }
                    case Motion: {
                        bozoware.impl.module.movement.Speed.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                        if (Wrapper.getPlayer().onGround && Wrapper.getPlayer().isMoving()) {
                            Wrapper.getPlayer().motionY = 0.41999998688697815;
                        }
                        MovementUtil.setMoveSpeed(this.Speed.getPropertyValue());
                        break;
                    }
                    case Viper: {
                        groundY = (int)bozoware.impl.module.movement.Speed.mc.thePlayer.posY;
                        canJump = false;
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            canJump = true;
                            groundY = (int)bozoware.impl.module.movement.Speed.mc.thePlayer.posY;
                        }
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.isMoving()) {
                            if (canJump && groundY <= bozoware.impl.module.movement.Speed.mc.thePlayer.posY) {
                                bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.5;
                                e.setOnGround(true);
                            }
                            else {
                                canJump = false;
                            }
                        }
                        if (!canJump && !bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = -0.10999999940395355;
                            e.setOnGround(true);
                        }
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.isCollidedVertically) {
                            canJump2 = false;
                        }
                        height = bozoware.impl.module.movement.Speed.mc.thePlayer.getEntityBoundingBox().minY - bozoware.impl.module.movement.Speed.mc.thePlayer.posY;
                        canStep = (!BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled() && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled());
                        if ((canStep && height >= 0.625 && bozoware.impl.module.movement.Speed.mc.thePlayer.isCollidedHorizontally) || bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.32;
                        }
                        MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * 1.125);
                        break;
                    }
                    case NCP: {
                        this.motionY = 0.24500000476837158;
                        MovementUtil.Dynamic(MovementUtil.getBaseMoveSpeed(), (float)this.motionY, 159.0, 0.66, false, false, 0.0, 0.0);
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.isAirBorne && this.ffBool.getPropertyValue() && bozoware.impl.module.movement.Speed.mc.thePlayer.posY == this.startY + 0.41999998688697815) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = -this.fSpeed.getPropertyValue();
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case SpartanHop: {
                        if (MovementUtil.isMoving()) {
                            if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                                bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.41999998688697815;
                                this.moveSpeed = 1.3;
                                break;
                            }
                            else if (bozoware.impl.module.movement.Speed.mc.thePlayer.isAirBorne) {
                                if (bozoware.impl.module.movement.Speed.mc.thePlayer.posY > this.startY && bozoware.impl.module.movement.Speed.mc.thePlayer.fallDistance > 0.0f) {
                                    this.moveSpeed = 1.1;
                                }
                                MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * this.moveSpeed - Math.random() / 450.0);
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case SpartanGround: {
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && MovementUtil.isMoving()) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.41999998688697815;
                            this.speed = 1.2799999713897705;
                        }
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.isAirBorne && MovementUtil.isMoving()) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = -0.41999998688697815;
                            bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = false;
                            if (this.speed > 0.94) {
                                this.speed -= 0.01;
                            }
                            MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * this.speed - Math.random() / 450.0);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Watchdog: {
                        switch (this.watchdogSpeedMode.getPropertyValue()) {
                            case Bhop: {
                                if (this.groundStrafeBool.getPropertyValue() && !this.reset && !Keyboard.isKeyDown(bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindLeft.getKeyCode()) && !Keyboard.isKeyDown(bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindRight.getKeyCode())) {
                                    MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * 1.125);
                                }
                                if (!this.groundStrafeBool.getPropertyValue()) {
                                    if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.isMoving()) {}
                                        this.falling = false;
                                        if (MovementUtil.isMoving()) {
                                            bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = true;
                                            if ((!Keyboard.isKeyDown(30) && !Keyboard.isKeyDown(31) && !Keyboard.isKeyDown(32) && Keyboard.isKeyDown(17)) || (Keyboard.isKeyDown(30) && Keyboard.isKeyDown(32) && Keyboard.isKeyDown(17) && !Keyboard.isKeyDown(31))) {
                                                this.speed = 1.149999976158142;
                                            }
                                            else {
                                                this.speed = 1.0499999523162842;
                                            }
                                        }
                                    }
                                    else {
                                        bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = false;
                                        if (this.speed > 0.94) {
                                            this.speed -= 0.01;
                                        }
                                        MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * this.speed - Math.random() / 500.0);
                                    }
                                }
                                if (this.groundStrafeBool.getPropertyValue()) {
                                    if (MovementUtil.isMoving()) {
                                        if ((!bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && Keyboard.isKeyDown(30)) || Keyboard.isKeyDown(32)) {
                                            bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = true;
                                            return;
                                        }
                                        else if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                                            bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = true;
                                            if (!bozoware.impl.module.movement.Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {}
                                            this.reset = false;
                                            break;
                                        }
                                        else {
                                            bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = false;
                                            if (!this.reset && !Keyboard.isKeyDown(bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindLeft.getKeyCode()) && !Keyboard.isKeyDown(bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindRight.getKeyCode())) {
                                                MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * 1.125);
                                            }
                                            MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * 0.9);
                                            this.reset = true;
                                            break;
                                        }
                                    }
                                    else {
                                        bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindJump.pressed = false;
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                                break;
                            }
                            case Smooth: {
                                if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && MovementUtil.isMoving()) {
                                    thePlayer4 = bozoware.impl.module.movement.Speed.mc.thePlayer;
                                    motionY4 = MovementUtil.getBaseJumpMotion() * 0.9523;
                                    e.setY(thePlayer4.motionY = motionY4);
                                    this.stage = 0;
                                }
                                switch (this.stage) {
                                    case 0: {
                                        bozoware.impl.module.movement.Speed.mc.thePlayer.onGround = true;
                                        e.setY(e.getY() + 0.004 * Math.random());
                                        this.moveSpeed = getBaseMoveSpeedSlow() * 2.149;
                                        break;
                                    }
                                    case 1: {
                                        this.moveSpeed *= 0.58;
                                        break;
                                    }
                                    case 4: {
                                        if (!bozoware.impl.module.movement.Speed.mc.thePlayer.isPotionActive(Potion.jump)) {
                                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = -0.12;
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                        break;
                                    }
                                    default: {
                                        this.moveSpeed *= 0.975;
                                        break;
                                    }
                                }
                                ++this.stage;
                                MovementUtil.setSpeed(bozoware.impl.module.movement.Speed.mc.gameSettings.keyBindForward.isKeyDown() ? Math.max(getBaseMoveSpeedSlow(), this.moveSpeed) : 0.17);
                                break;
                            }
                        }
                        break;
                    }
                    case VerusLowHop: {
                        if (MovementUtil.isMoving() && !bozoware.impl.module.movement.Speed.mc.thePlayer.onGround && MovementUtil.isOnGround(1.0)) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = -0.07840000152587834;
                            MovementUtil.setSpeed(0.3);
                        }
                        if (MovementUtil.isMoving() && bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            bozoware.impl.module.movement.Speed.mc.thePlayer.jump();
                            MovementUtil.setMoveSpeed(0.5);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Verus: {
                        MovementUtil.setMoveSpeed(0.36);
                        if (Wrapper.getPlayer().onGround) {
                            Wrapper.getPlayer().jump();
                            MovementUtil.setMoveSpeed(0.5);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case VerusAir: {
                        if (MovementUtil.isMoving()) {
                            if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                                bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.41999998688697815;
                            }
                            else {
                                if (MovementUtil.isOnGround(3.0)) {
                                    bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = -0.07840000152587834;
                                }
                                if (bozoware.impl.module.movement.Speed.mc.thePlayer.isCollidedHorizontally) {
                                    bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.41999998688697815;
                                }
                                if (bozoware.impl.module.movement.Speed.mc.thePlayer.fallDistance >= 2.0f && e.getY() < bozoware.impl.module.movement.Speed.mc.thePlayer.posY) {
                                    bozoware.impl.module.movement.Speed.mc.thePlayer.onGround = true;
                                }
                            }
                            MovementUtil.setMoveSpeed((bozoware.impl.module.movement.Speed.mc.thePlayer.ticksExisted % 4 == 0) ? 0.36 : 0.3);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case YPort: {
                        e.setY(e.getY() + (this.lastReset ? 0.41999998688697815 : 0.001));
                        if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                            this.lastReset = (bozoware.impl.module.movement.Speed.mc.thePlayer.ticksExisted % 2 == 0);
                            MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * 1.125 - Math.random() / 150.0);
                        }
                        BozoWare.getInstance().chat(String.valueOf(this.lastReset));
                        break;
                    }
                    case Dev: {
                        if (MovementUtil.isMoving()) {
                            if (bozoware.impl.module.movement.Speed.mc.thePlayer.onGround) {
                                bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.25999999046325684;
                                MovementUtil.setMoveSpeed(0.19);
                                bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.07428571428571429;
                                this.reset = false;
                                break;
                            }
                            else {
                                if (!this.reset) {
                                    bozoware.impl.module.movement.Speed.mc.thePlayer.motionY = 0.25999999046325684;
                                    MovementUtil.setMoveSpeed(0.35);
                                }
                                this.reset = true;
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                return;
            }
        });
        this.watchdogSpeedMode.onValueChange = (() -> {
            if (this.watchdogSpeedMode.getPropertyValue().equals(watchdogSpeedModes.Smooth)) {
                this.groundStrafeBool.setHidden(true);
            }
            else {
                this.groundStrafeBool.setHidden(false);
            }
            return;
        });
        this.speedMode.onValueChange = (() -> {
            if (this.speedMode.getPropertyValue().equals(SpeedModes.Watchdog)) {
                this.watchdogSpeedMode.setHidden(false);
            }
            else {
                this.watchdogSpeedMode.setHidden(true);
            }
            this.setModuleSuffix(this.speedMode.getPropertyValue().name());
        });
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static double getBaseMoveSpeedSlow() {
        double baseSpeed = 0.2873;
        if (Speed.mc.thePlayer != null && Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.11 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    static {
        Speed.bruh = (int)System.currentTimeMillis();
    }
    
    private enum SpeedModes
    {
        NCP, 
        NCPClip, 
        Watchdog, 
        Watchdog2, 
        Motion, 
        LowHop, 
        SpartanHop, 
        SpartanGround, 
        Packet, 
        Viper, 
        YPort, 
        Funcraft, 
        Funcraft2, 
        Verus, 
        VerusAir, 
        VerusLowHop, 
        Dev;
    }
    
    private enum watchdogSpeedModes
    {
        Smooth, 
        Bhop;
    }
}
