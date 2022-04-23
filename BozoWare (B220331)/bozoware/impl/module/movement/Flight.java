// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import net.minecraft.potion.PotionEffect;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.util.Vec3;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.BlockPos;
import bozoware.impl.module.world.PingSpoofer;
import net.minecraft.potion.Potion;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.client.C03PacketPlayer;
import bozoware.base.BozoWare;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import bozoware.base.util.player.PlayerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.util.MathHelper;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.network.Packet;
import java.util.LinkedList;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.impl.event.block.EventAABB;
import bozoware.impl.event.player.PlayerMoveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Flight", moduleCategory = ModuleCategory.MOVEMENT)
public class Flight extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PlayerMoveEvent> onPlayerMoveEvent;
    @EventListener
    EventConsumer<EventAABB> onEventAABB;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    private final EnumProperty<FlightModes> flightModeProperty;
    private final EnumProperty<VerusModes> VerusModeProperty;
    private final ValueProperty<Double> Speed;
    private final EnumProperty<damageModes> damageMode;
    public final BooleanProperty ColOrMotion;
    public final BooleanProperty verusntBool;
    public final BooleanProperty damageBool;
    public final BooleanProperty boostBool;
    public final BooleanProperty timerBool;
    private final ValueProperty<Double> timerMin;
    private final ValueProperty<Double> timerMax;
    private final BooleanProperty viewBobBool;
    private final BooleanProperty flagCheckBool;
    private final BooleanProperty stopOnDisable;
    LinkedList<Packet> packets;
    public double moveSpeed;
    public TimerUtil timer;
    TimerUtil blinkTimer;
    double lastDist;
    double speed;
    double startY;
    static int stage;
    static int stage2;
    static int airTicks;
    public int ticks;
    static int state;
    static boolean hasClipped;
    TimerUtil viperStopWatch;
    boolean received;
    boolean damaged;
    boolean lostBoost;
    boolean canSend;
    private static int y;
    boolean Jumped;
    boolean Clipped;
    private int lastX;
    private int lastY;
    private int lastZ;
    
    public Flight() {
        this.flightModeProperty = new EnumProperty<FlightModes>("Mode", FlightModes.Motion, this);
        this.VerusModeProperty = new EnumProperty<VerusModes>("Verus Mode", VerusModes.Basic, this);
        this.Speed = new ValueProperty<Double>("Speed", 1.0, 0.0, 30.0, this);
        this.damageMode = new EnumProperty<damageModes>("Damage Mode", damageModes.Basic, this);
        this.ColOrMotion = new BooleanProperty("Verus Collision", true, this);
        this.verusntBool = new BooleanProperty("Damage Timer", false, this);
        this.damageBool = new BooleanProperty("Damage", false, this);
        this.boostBool = new BooleanProperty("Boost", false, this);
        this.timerBool = new BooleanProperty("Timer", false, this);
        this.timerMin = new ValueProperty<Double>("Timer Min", 0.75, 0.1, 5.0, this);
        this.timerMax = new ValueProperty<Double>("Timer Max", 1.45, 0.1, 5.0, this);
        this.viewBobBool = new BooleanProperty("View-Bobbing", true, this);
        this.flagCheckBool = new BooleanProperty("Flag Check", false, this);
        this.stopOnDisable = new BooleanProperty("Stop on Disable", false, this);
        this.packets = new LinkedList<Packet>();
        this.timer = new TimerUtil();
        this.blinkTimer = new TimerUtil();
        this.lastDist = MovementUtil.lastDist();
        this.viperStopWatch = new TimerUtil();
        this.Jumped = false;
        this.Clipped = false;
        this.VerusModeProperty.setHidden(true);
        this.damageMode.setHidden(true);
        this.ColOrMotion.setHidden(true);
        this.verusntBool.setHidden(true);
        this.timerMax.setHidden(true);
        this.boostBool.setHidden(true);
        this.timerMin.setHidden(true);
        this.damageBool.setHidden(true);
        this.timerBool.onValueChange = (() -> {
            this.timerMax.setHidden(!this.timerBool.getPropertyValue());
            this.timerMin.setHidden(!this.timerBool.getPropertyValue());
            return;
        });
        if (this.stopOnDisable.getPropertyValue()) {
            Flight.mc.thePlayer.motionX = 0.0;
            Flight.mc.thePlayer.motionZ = 0.0;
        }
        this.VerusModeProperty.onValueChange = (() -> {
            if (this.VerusModeProperty.getPropertyValue().equals(VerusModes.Damage) || this.VerusModeProperty.getPropertyValue().equals(VerusModes.New)) {
                this.damageMode.setHidden(false);
                this.ColOrMotion.setHidden(false);
                this.verusntBool.setHidden(false);
            }
            else {
                this.damageMode.setHidden(true);
                this.ColOrMotion.setHidden(true);
                this.verusntBool.setHidden(true);
            }
            return;
        });
        this.setModuleBind(45);
        this.setModuleSuffix(this.flightModeProperty.getPropertyValue().toString());
        this.onModuleDisabled = (() -> {
            this.timer.reset();
            this.timer.reset();
            MovementUtil.setMoveSpeed(0L);
            Flight.mc.gameSettings.keyBindJump.pressed = false;
            this.ticks = 0;
            Flight.mc.timer.timerSpeed = 1.0f;
            this.Jumped = false;
            this.Clipped = false;
            return;
        });
        double timerMinClamped;
        double bruh;
        this.onModuleEnabled = (() -> {
            this.blinkTimer.reset();
            this.lastX = (int)Flight.mc.thePlayer.posX;
            this.lastY = (int)Flight.mc.thePlayer.posY;
            this.lastZ = (int)Flight.mc.thePlayer.posZ;
            this.Jumped = false;
            this.Clipped = false;
            Flight.state = 0;
            this.ticks = 0;
            this.canSend = true;
            this.viperStopWatch.reset();
            this.timer.reset();
            this.lastDist = 0.0;
            Flight.stage = 0;
            Flight.stage2 = 0;
            this.lostBoost = false;
            Flight.airTicks = 18;
            if (this.timerBool.getPropertyValue()) {
                timerMinClamped = MathHelper.clamp_double(this.timerMin.getPropertyValue(), 0.1, this.timerMax.getPropertyValue() - 0.1);
                bruh = ThreadLocalRandom.current().nextDouble(timerMinClamped, (double)this.timerMax.getPropertyValue());
                Flight.mc.timer.timerSpeed = (float)bruh;
            }
            switch (this.flightModeProperty.getPropertyValue()) {
                case Watchdog: {
                    if (Flight.mc.thePlayer.onGround && this.damageBool.getPropertyValue()) {
                        damage();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case LoyisaNCP: {
                    Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY - 8.0E-15, Flight.mc.thePlayer.posZ);
                    break;
                }
            }
            if (this.VerusModeProperty.getPropertyValue().equals(VerusModes.Damage)) {
                switch (this.damageMode.getPropertyValue()) {
                    case Basic: {
                        PlayerUtils.damageVerusBasic();
                        break;
                    }
                    case Advanced: {
                        PlayerUtils.damageVerusAdvanced();
                        break;
                    }
                }
            }
            this.speed = 0.0;
            this.moveSpeed = 0.0;
            return;
        });
        this.onPacketSendEvent = (e -> {
            if (this.flightModeProperty.getPropertyValue().equals(FlightModes.Watchdog)) {}
            return;
        });
        S08PacketPlayerPosLook packetPlayerPosLook;
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S08PacketPlayerPosLook && this.flagCheckBool.getPropertyValue() && Flight.mc.thePlayer != null && Flight.mc.theWorld != null) {
                BozoWare.getInstance().chat("Disabled Flight because you flagged/got teleported!");
                this.toggleModule();
            }
            if (e.getPacket() instanceof S08PacketPlayerPosLook && Flight.stage == 1) {
                packetPlayerPosLook = (S08PacketPlayerPosLook)e.getPacket();
                Flight.y = (int)packetPlayerPosLook.getY();
                System.out.println(Flight.y);
                Flight.mc.thePlayer.motionY = 0.05;
                Flight.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(packetPlayerPosLook.getX(), packetPlayerPosLook.getY(), packetPlayerPosLook.getZ(), false));
                Flight.stage = 2;
                e.setCancelled(true);
            }
            return;
        });
        final double x;
        final double y;
        final double z;
        EntityPlayerSP thePlayer;
        EntityPlayerSP thePlayer2;
        EntityPlayerSP thePlayer3;
        EntityPlayerSP thePlayer4;
        EntityPlayerSP thePlayer5;
        EntityPlayerSP thePlayer6;
        BlockPos blockPos;
        Vec3 vec;
        this.onUpdatePositionEvent = (e -> {
            this.VerusModeProperty.setHidden(!this.flightModeProperty.getPropertyValue().equals(FlightModes.Verus));
            x = Flight.mc.thePlayer.posX;
            y = Flight.mc.thePlayer.posY;
            z = Flight.mc.thePlayer.posZ;
            this.lastDist = MovementUtil.lastDist();
            if (this.viewBobBool.getPropertyValue()) {
                Flight.mc.thePlayer.cameraYaw = 0.1f;
            }
            if (e.isPre()) {
                switch (this.flightModeProperty.getPropertyValue()) {
                    case Motion: {
                        Wrapper.getPlayer().motionY = 0.0;
                        if (Flight.mc.thePlayer.movementInput.jump) {
                            Wrapper.getPlayer().motionY = this.Speed.getPropertyValue() / 2.0;
                        }
                        if (Flight.mc.thePlayer.movementInput.sneak) {
                            Wrapper.getPlayer().motionY = -this.Speed.getPropertyValue() / 2.0;
                        }
                        MovementUtil.setMoveSpeed(this.Speed.getPropertyValue());
                        break;
                    }
                    case Watchdog: {
                        if (Flight.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            if (e.isPre()) {
                                ++Flight.stage2;
                                if (Flight.state == 2 || Flight.stage2 >= 2) {
                                    if (this.boostBool.getPropertyValue()) {
                                        if (this.timer.hasReached(1000L)) {
                                            Flight.mc.timer.timerSpeed = 2.5f;
                                        }
                                        else {
                                            Flight.mc.timer.timerSpeed = 3.0f;
                                        }
                                        if (this.timer.hasReached(2500L)) {
                                            Flight.mc.timer.timerSpeed = 2.0f;
                                        }
                                        if (this.timer.hasReached(3500L)) {
                                            Flight.mc.timer.timerSpeed = 1.5f;
                                        }
                                        if (this.timer.hasReached(5000L)) {
                                            Flight.mc.timer.timerSpeed = 1.25f;
                                        }
                                    }
                                    Flight.mc.thePlayer.motionY = 0.0;
                                    Flight.mc.thePlayer.cameraYaw = 0.1f;
                                    e.setOnGround(true);
                                    MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * 0.938);
                                }
                                if (Flight.state == 0) {
                                    this.blinkTimer.reset();
                                    this.timer.reset();
                                    if (Flight.stage2 == 1) {
                                        Flight.mc.thePlayer.motionY = 0.05;
                                    }
                                    if (Flight.stage2 == 3) {
                                        e.setY(e.getY() - 0.215);
                                        thePlayer = Flight.mc.thePlayer;
                                        thePlayer.posY -= Flight.mc.thePlayer.posY - Flight.mc.thePlayer.lastTickPosY;
                                        thePlayer2 = Flight.mc.thePlayer;
                                        thePlayer2.lastTickPosY -= Flight.mc.thePlayer.posY - Flight.mc.thePlayer.lastTickPosY;
                                    }
                                    if (Flight.stage2 == 4) {
                                        Flight.mc.thePlayer.motionY = -0.481009647894567;
                                        thePlayer3 = Flight.mc.thePlayer;
                                        thePlayer3.posY -= Flight.mc.thePlayer.posY - Flight.mc.thePlayer.lastTickPosY;
                                        thePlayer4 = Flight.mc.thePlayer;
                                        thePlayer4.lastTickPosY -= Flight.mc.thePlayer.posY - Flight.mc.thePlayer.lastTickPosY;
                                    }
                                    if (Flight.stage2 == 5) {
                                        Flight.mc.thePlayer.motionY = -0.481009647894567;
                                        thePlayer5 = Flight.mc.thePlayer;
                                        thePlayer5.posY -= Flight.mc.thePlayer.posY - Flight.mc.thePlayer.lastTickPosY;
                                        thePlayer6 = Flight.mc.thePlayer;
                                        thePlayer6.lastTickPosY -= Flight.mc.thePlayer.posY - Flight.mc.thePlayer.lastTickPosY;
                                        Flight.state = 1;
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        else if (e.isPre) {
                            if (!this.Jumped && Flight.mc.thePlayer.onGround) {
                                Flight.mc.thePlayer.motionY = 0.07500000298023224;
                                this.Jumped = true;
                                return;
                            }
                            else {
                                if (Flight.mc.thePlayer.onGround && !this.Clipped) {
                                    e.setY(e.getY() - 0.07500000298023224);
                                    e.setOnGround(true);
                                    this.Clipped = true;
                                }
                                if (!this.Clipped) {
                                    this.timer.reset();
                                }
                                if (this.Clipped) {
                                    Flight.mc.thePlayer.motionY = 0.0;
                                    if (this.boostBool.getPropertyValue()) {
                                        if (this.timer.hasReached(1000L)) {
                                            Flight.mc.timer.timerSpeed = 2.0f;
                                        }
                                        else {
                                            Flight.mc.timer.timerSpeed = 2.5f;
                                        }
                                        if (this.timer.hasReached(2500L)) {
                                            Flight.mc.timer.timerSpeed = 1.8f;
                                        }
                                        if (this.timer.hasReached(3500L)) {
                                            Flight.mc.timer.timerSpeed = 1.25f;
                                            break;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Viper: {
                        Flight.mc.thePlayer.motionY = 0.0;
                        MovementUtil.setSpeed(this.Speed.getPropertyValue() * 1.1);
                        e.setOnGround(true);
                        if (Flight.mc.gameSettings.keyBindJump.isKeyDown()) {
                            Flight.mc.thePlayer.motionY = this.Speed.getPropertyValue() * 2.0;
                        }
                        if (Flight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                            Flight.mc.thePlayer.motionY = -(this.Speed.getPropertyValue() * 2.0);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case WatchdogNew: {
                        PingSpoofer.spikeStopwatch.reset();
                        PingSpoofer.stage = 1;
                        Flight.mc.thePlayer.motionY = 0.0;
                        if (!Flight.hasClipped) {
                            if (Flight.stage == 0) {
                                Flight.mc.thePlayer.setPlayerSPHealth(Flight.mc.thePlayer.getHealth() - 1.0f);
                                Flight.mc.thePlayer.playSound("game.neutral.hurt", 1.0f, 1.0f);
                                Flight.mc.thePlayer.motionY = 0.41999998688697815;
                                Flight.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.lastX, this.lastY + 0.05, this.lastZ, true));
                                Flight.stage = 1;
                                break;
                            }
                            else if (Flight.stage == 1) {
                                Flight.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.lastX, this.lastY - 0.215, this.lastZ, true));
                                Flight.hasClipped = true;
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            Flight.mc.timer.timerSpeed = 2.0f;
                            MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * 0.938);
                            e.setOnGround(true);
                            break;
                        }
                        break;
                    }
                    case LoyisaNCP: {
                        Flight.mc.thePlayer.motionY = 0.0;
                        MovementUtil.setSpeed(0.262);
                        break;
                    }
                    case AntiPlate: {
                        if (Flight.mc.thePlayer.isMoving()) {
                            if (Flight.mc.thePlayer.onGround) {
                                MovementUtil.setSpeed(this.moveSpeed = 0.29);
                                Flight.mc.thePlayer.jump();
                                Flight.airTicks = 9;
                                break;
                            }
                            else if (Wrapper.getBlock(new BlockPos(x, y - 1.3, z)).isFullBlock()) {
                                Flight.mc.timer.timerSpeed = 1.085f;
                                this.moveSpeed = 0.262;
                                MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed());
                                --Flight.airTicks;
                                if (Flight.airTicks < 0) {
                                    if (Flight.airTicks <= 0) {
                                        Flight.airTicks = 0;
                                    }
                                    if (Flight.mc.thePlayer.hurtTime <= 0) {
                                        MovementUtil.setSpeed(this.moveSpeed);
                                    }
                                    else {
                                        MovementUtil.setSpeed(this.moveSpeed * 1.5);
                                    }
                                    Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY - 8.0E-15, Flight.mc.thePlayer.posZ);
                                    Flight.mc.thePlayer.motionY = 0.0;
                                    break;
                                }
                                else {
                                    break;
                                }
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
                    case Collision: {
                        Flight.mc.thePlayer.onGround = true;
                        break;
                    }
                    case Funcraft: {
                        Flight.mc.thePlayer.jumpMovementFactor = 0.0f;
                        if (!Flight.mc.thePlayer.isMoving() || Flight.mc.thePlayer.isCollidedHorizontally) {
                            this.lostBoost = true;
                        }
                        if (Flight.stage > 0 || this.lostBoost) {
                            e.setOnGround(true);
                            Flight.mc.thePlayer.motionY = 0.0;
                            if (MovementUtil.isOnGround(0.01)) {
                                Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY + 0.24, Flight.mc.thePlayer.posZ);
                            }
                            if (!MovementUtil.isOnGround(3.33315597345063E-11)) {
                                Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY - 3.33315597345063E-11, Flight.mc.thePlayer.posZ);
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
                    case Funcraft2: {
                        e.setOnGround(true);
                        Flight.mc.thePlayer.jumpMovementFactor = 0.0f;
                        if (!Flight.mc.thePlayer.isMoving()) {
                            this.moveSpeed = 0.25;
                        }
                        if (this.moveSpeed > 0.24) {
                            this.moveSpeed -= this.moveSpeed / 159.0;
                        }
                        if (e.isPre()) {
                            Flight.mc.effectRenderer.spawnEffectParticle(EnumParticleTypes.FLAME.getParticleID(), Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY + 0.2, Flight.mc.thePlayer.posZ, -Flight.mc.thePlayer.motionX, -0.5, -Flight.mc.thePlayer.motionZ, new int[0]);
                            if (Flight.mc.thePlayer.isMoving()) {
                                MovementUtil.setMoveSpeed(this.moveSpeed);
                            }
                            Flight.mc.thePlayer.motionY = 0.0;
                            Flight.mc.thePlayer.setPosition(x, y - 3.33315597345063E-11, z);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                if (this.flightModeProperty.getPropertyValue().equals(FlightModes.Verus)) {
                    switch (this.VerusModeProperty.getPropertyValue()) {
                        case Advanced: {
                            Flight.mc.thePlayer.motionY = 0.0;
                            this.timer.reset();
                            if (this.timer.hasReached(12L)) {
                                Flight.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY + 8.0, Flight.mc.thePlayer.posZ, true));
                                Flight.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY - 8.0, Flight.mc.thePlayer.posZ, true));
                                this.timer.reset();
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case Glide: {
                            Flight.mc.thePlayer.onGround = true;
                            Flight.mc.thePlayer.motionY = -0.07840000152587834;
                            if (Flight.mc.thePlayer.isCollidedHorizontally) {
                                Flight.mc.thePlayer.motionY = 0.30000001192092896;
                            }
                            if (Flight.mc.thePlayer.isAirBorne && !Flight.mc.thePlayer.isCollidedVertically) {
                                MovementUtil.setMoveSpeed(0.26);
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case Damage: {
                            this.damaged = false;
                            this.lostBoost = false;
                            Flight.mc.thePlayer.onGround = true;
                            if (!this.ColOrMotion.getPropertyValue()) {
                                Flight.mc.thePlayer.motionY = 0.0;
                            }
                            else {
                                if (Flight.mc.thePlayer.ticksExisted % 10 == 0) {
                                    Flight.mc.thePlayer.jump();
                                }
                                Flight.mc.gameSettings.keyBindJump.pressed = false;
                            }
                            if (this.received) {
                                this.lostBoost = true;
                            }
                            if (Flight.mc.thePlayer.hurtTime > 0) {
                                this.received = true;
                                Flight.mc.timer.timerSpeed = 1.0f;
                                this.damaged = true;
                                if (Flight.mc.thePlayer.isCollidedHorizontally && this.ticks <= 99) {
                                    this.ticks = 999;
                                    BozoWare.getInstance().chat("Disabled Boost For Safety.");
                                }
                                ++this.ticks;
                                if (!this.received) {
                                    MovementUtil.setMoveSpeed(0L);
                                    break;
                                }
                                else if (this.ticks <= 150) {
                                    MovementUtil.setMoveSpeed(this.Speed.getPropertyValue());
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            else if (this.verusntBool.getPropertyValue() && this.lostBoost) {
                                Flight.mc.timer.timerSpeed = 0.35f;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case New: {
                            Flight.mc.thePlayer.setSprinting(true);
                            MovementUtil.setMoveSpeed((Flight.mc.thePlayer.ticksExisted % 4 == 0) ? 0.5 : 0.36);
                            break;
                        }
                        case New2: {
                            if (Flight.mc.thePlayer.inventory.getCurrentItem() == null) {
                                if (Flight.mc.gameSettings.keyBindJump.isKeyDown() && this.timer.hasReached(100L)) {
                                    Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY + 0.6, Flight.mc.thePlayer.posZ);
                                    this.timer.reset();
                                }
                                if (Flight.mc.thePlayer.isSneaking() && this.timer.hasReached(100L)) {
                                    Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY - 0.6, Flight.mc.thePlayer.posZ);
                                    this.timer.reset();
                                }
                                blockPos = new BlockPos(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.getEntityBoundingBox().minY - 1.0, Flight.mc.thePlayer.posZ);
                                vec = new Vec3(blockPos).addVector(0.4000000059604645, 0.4000000059604645, 0.4000000059604645).add(new Vec3(EnumFacing.UP.getDirectionVec()));
                                Flight.mc.playerController.onPlayerRightClick(Flight.mc.thePlayer, Flight.mc.theWorld, Flight.mc.thePlayer.inventory.getCurrentItem(), blockPos, EnumFacing.UP, new Vec3(vec.xCoord * 0.4000000059604645, vec.yCoord * 0.4000000059604645, vec.zCoord * 0.4000000059604645));
                                MovementUtil.setSpeed(0.27000001072883606);
                                Flight.mc.timer.timerSpeed = (float)(1.0 + this.Speed.getPropertyValue());
                                break;
                            }
                            else {
                                Flight.mc.timer.timerSpeed = 1.0f;
                                BozoWare.getInstance().chat("§8[§c§lMineplex-§a§lFly§8] §aSelect an empty slot to fly.");
                                this.toggleModule();
                                break;
                            }
                            break;
                        }
                        case Floatish: {
                            if (Flight.mc.thePlayer.posY == this.startY) {
                                Flight.mc.thePlayer.motionY = 0.41999998688697815;
                            }
                            if (Flight.mc.thePlayer.posY < this.startY) {
                                Flight.mc.thePlayer.motionY = 0.41999998688697815;
                                Flight.mc.thePlayer.posY = this.startY;
                            }
                            if (Flight.mc.thePlayer.posY >= this.startY + 0.41999998688697815 && Flight.mc.thePlayer.isAirBorne) {
                                Flight.mc.thePlayer.posY = this.startY + 0.42;
                                MovementUtil.setMoveSpeed(MovementUtil.getBaseMoveSpeed() * 2.147483647E9);
                            }
                            MovementUtil.setMoveSpeed(0.36);
                            break;
                        }
                    }
                }
            }
            return;
        });
        this.onPlayerMoveEvent = (e -> {
            switch (this.flightModeProperty.getPropertyValue()) {
                case Funcraft: {
                    if (Flight.mc.thePlayer.isMoving() && !this.lostBoost) {
                        switch (Flight.stage) {
                            case 0: {
                                this.moveSpeed = 0.0;
                                break;
                            }
                            case 1: {
                                e.setMotionY(Flight.mc.thePlayer.motionY + 0.3999);
                                Flight.mc.thePlayer.motionY = 0.3999;
                                this.moveSpeed *= 2.149;
                                break;
                            }
                            case 2: {
                                this.moveSpeed = 1.6;
                                break;
                            }
                            default: {
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                                break;
                            }
                        }
                        MovementUtil.setSpeed(e, this.moveSpeed = Math.max(this.moveSpeed, MovementUtil.getBaseMoveSpeed()));
                        ++Flight.stage;
                        break;
                    }
                    else if (Flight.mc.thePlayer.isMoving() && this.lostBoost) {
                        MovementUtil.setSpeed(e, MovementUtil.getBaseMoveSpeed());
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Watchdog: {
                    if (!Wrapper.getPlayer().isPotionActive(Potion.moveSpeed)) {
                        MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed());
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        final double x2;
        final double y2;
        final double z2;
        AxisAlignedBB blockBB;
        AxisAlignedBB blockBB2;
        this.onEventAABB = (e -> {
            x2 = Flight.mc.thePlayer.posX;
            y2 = Flight.mc.thePlayer.posY;
            z2 = Flight.mc.thePlayer.posZ;
            if (this.flightModeProperty.getPropertyValue().equals(FlightModes.Collision)) {
                blockBB = null;
                if (!Flight.mc.thePlayer.movementInput.sneak) {
                    blockBB = AxisAlignedBB.fromBounds(-5000.0, -2.0, -5000.0, 5000.0, 2.0, 5000.0).offset(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());
                }
                else if (!Wrapper.getBlock(new BlockPos(x2, y2 - 1.0, z2)).isFullCube()) {
                    if (!Wrapper.getBlock(new BlockPos(x2, y2 - 1.0, z2)).isFullBlock()) {
                        if (!Wrapper.getBlock(new BlockPos(x2, y2 - 1.0, z2)).isCollidable()) {
                            blockBB = AxisAlignedBB.fromBounds(-5000.0, y2 - 1.0, -5000.0, 5000.0, 2.0, 5000.0).offset(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());
                        }
                    }
                }
                e.setBoundingBox(blockBB);
            }
            if ((this.ColOrMotion.getPropertyValue() && this.VerusModeProperty.getPropertyValue().equals(VerusModes.Damage)) || this.VerusModeProperty.getPropertyValue().equals(VerusModes.New)) {
                blockBB2 = AxisAlignedBB.fromBounds(-5000.0, -2.0, -5000.0, 5000.0, 2.0, 5000.0).offset(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());
                e.setBoundingBox(blockBB2);
            }
            if (this.VerusModeProperty.getPropertyValue().equals(VerusModes.New2) && Flight.mc.thePlayer.inventory.getCurrentItem() == null && e.getPos().getY() < Flight.mc.thePlayer.posY) {
                e.setBoundingBox(AxisAlignedBB.fromBounds(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), e.getPos().getX() + 1, Flight.mc.thePlayer.posY, e.getPos().getZ() + 1));
            }
            return;
        });
        if (this.flightModeProperty.getPropertyValue() != FlightModes.Verus) {
            this.flightModeProperty.onValueChange = (() -> {
                this.setModuleSuffix(this.flightModeProperty.getPropertyValue().name());
                if (this.flightModeProperty.getPropertyValue().equals(FlightModes.Watchdog)) {
                    this.boostBool.setHidden(false);
                    this.damageBool.setHidden(false);
                }
                else {
                    this.boostBool.setHidden(true);
                    this.damageBool.setHidden(true);
                }
            });
        }
        else {
            this.VerusModeProperty.onValueChange = (() -> this.setModuleSuffix(this.VerusModeProperty.getPropertyValue().name()));
        }
    }
    
    public static Flight getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class);
    }
    
    public static void damage() {
        final double offset = 0.060100000351667404;
        final NetHandlerPlayClient netHandler = Flight.mc.getNetHandler();
        final EntityPlayerSP player = Flight.mc.thePlayer;
        final double x = player.posX;
        final double y = player.posY;
        final double z = player.posZ;
        for (int i = 0; i < getMaxFallDist() / 0.05510000046342611 + 1.0; ++i) {
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291 + 6.01000003516674E-8, z, false));
        }
        netHandler.addToSendQueue(new C03PacketPlayer(true));
    }
    
    public static float getMaxFallDist() {
        final PotionEffect potioneffect = Flight.mc.thePlayer.getActivePotionEffect(Potion.jump);
        final int f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0;
        return (float)(Flight.mc.thePlayer.getMaxFallHeight() + f);
    }
    
    private enum FlightModes
    {
        Motion, 
        Watchdog, 
        WatchdogNew, 
        NCP, 
        LoyisaNCP, 
        AntiPlate, 
        Collision, 
        Verus, 
        Funcraft, 
        Viper, 
        Funcraft2;
    }
    
    private enum VerusModes
    {
        Basic, 
        Advanced, 
        Glide, 
        Floatish, 
        Damage, 
        New, 
        Intave, 
        New2;
    }
    
    private enum damageModes
    {
        Basic, 
        Advanced;
    }
}
