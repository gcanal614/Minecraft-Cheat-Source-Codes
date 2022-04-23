/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Native
 */
package me.uncodable.srt.impl.modules.impl.movement;

import java.util.ArrayList;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.block.EventAddCollision;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.events.events.packet.EventPacket;
import me.uncodable.srt.impl.events.events.peripheral.EventKeyPress;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.MovementUtils;
import me.uncodable.srt.impl.utils.PacketUtils;
import me.uncodable.srt.impl.utils.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import store.intent.intentguard.annotation.Native;

@ModuleInfo(internalName="Flight", name="Flight", desc="Allows you to take flight and fly away.", category=Module.Category.MOVEMENT)
@Native
public class Flight
extends Module {
    private static final String INTERNAL_SPEED_VALUE = "INTERNAL_SPEED_VALUE";
    private static final String INTERNAL_BREAK_ON_TOGGLE = "INTERNAL_BREAK_ON_TOGGLE";
    private static final String INTERNAL_SPEED_SHIFTING = "INTERNAL_SPEED_SHIFTING";
    private static final String INTERNAL_SPEED_SHIFTING_VALUE = "INTERNAL_SPEED_SHIFTING_VALUE";
    private static final String COMBO_BOX_SETTING_NAME = "Flight Mode";
    private static final String SPEED_VALUE_SETTING_NAME = "Speed";
    private static final String BREAK_ON_TOGGLE_SETTING_NAME = "Break On Toggle";
    private static final String SPEED_SHIFTING_SETTING_NAME = "Manual Mode";
    private static final String SPEED_SHIFTING_VALUE_SETTING_NAME = "Speed Shift";
    private static final String CREATIVE = "Creative";
    private static final String DRAGPAK = "Dragpak";
    private static final String DEMON = "Demon";
    private static final String HELLCAT = "Hellcat";
    private static final String MOTION = "Motion";
    private static final String ANTI_KICK = "Anti-Kick Motion";
    private static final String VERUS = "Verus";
    private static final String VERUS_II = "Verus II";
    private static final String VERUS_III = "Verus III";
    private static final String COLLISION = "Collision";
    private static final String NO_GROUND_DAMAGE = "No Ground Damage";
    private static final String ANTICHEAT_RELOADED_GLIDE = "AntiCheat Reloaded Glide";
    private static final String LEGACY_NCP = "Legacy NoCheat+";
    private static final String LEGACY_ANTIVIRUS = "Legacy Antivirus";
    private static final String NORULES = "No Rules";
    private static final String LEGACY_AGC = "Legacy AntiGamingChair";
    private static final String CHUNK_GLIDE = "Chunk Glide";
    private static final String VULCAN_TEST = "Vulcan DEV";
    private static final String LEGACY_AGC_II = "Legacy AntiGamingChair II";
    private static final String LEGACY_CARBON = "Legacy Carbon";
    private static final String AREA_51 = "Area 51";
    private final ArrayList<C03PacketPlayer> players = new ArrayList();
    private int stage;
    private int stage2;
    private int groundTicks;
    private int gear;
    private final Timer timer = new Timer();
    private float oldTimerSpeed;
    private double decrement;
    private double x;
    private double y;
    private double z;
    private boolean damaged;

    public Flight(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, CREATIVE, DRAGPAK, DEMON, HELLCAT, MOTION, ANTI_KICK, VERUS, VERUS_II, VERUS_III, COLLISION, NO_GROUND_DAMAGE, ANTICHEAT_RELOADED_GLIDE, LEGACY_NCP, LEGACY_ANTIVIRUS, NORULES, CHUNK_GLIDE, VULCAN_TEST, LEGACY_AGC, LEGACY_AGC_II, LEGACY_CARBON, AREA_51);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SPEED_VALUE, SPEED_VALUE_SETTING_NAME, 5.0, 0.0, 10.0);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SPEED_SHIFTING_VALUE, SPEED_SHIFTING_VALUE_SETTING_NAME, 0.5, 0.0, 10.0);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_BREAK_ON_TOGGLE, BREAK_ON_TOGGLE_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_SPEED_SHIFTING, SPEED_SHIFTING_SETTING_NAME, true);
    }

    @Override
    @Native
    public void onEnable() {
        if (Flight.MC.thePlayer != null) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Verus III": {
                    for (int i = 0; i < 3; ++i) {
                        Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.getEntityBoundingBox().minY + 1.1, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, false));
                        Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.getEntityBoundingBox().minY, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, false));
                    }
                    Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.getEntityBoundingBox().minY, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, true));
                    break;
                }
                case "Verus II": {
                    MovementUtils.zeroMotion();
                    for (int i = 0; i < 8; ++i) {
                        Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.getEntityBoundingBox().minY + (double)0.42f, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, false));
                        Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.getEntityBoundingBox().minY, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, false));
                    }
                    Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.getEntityBoundingBox().minY, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, true));
                    break;
                }
                case "Vulcan DEV": {
                    Ries.INSTANCE.msg("This bypass is currently in development.");
                    break;
                }
                case "Legacy AntiGamingChair II": {
                    Flight.MC.thePlayer.setPositionAndUpdate(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.posY - 2.0, Flight.MC.thePlayer.posZ);
                    break;
                }
                case "Legacy Carbon": {
                    if (Flight.MC.thePlayer.onGround && !Ries.INSTANCE.getModuleManager().getModuleByName("AirJump").isEnabled()) break;
                    Ries.INSTANCE.msg("You must be on ground to toggle this flight mode.");
                    this.toggle();
                }
            }
            this.oldTimerSpeed = Flight.MC.timer.timerSpeed;
            this.x = Flight.MC.thePlayer.posX;
            this.y = Flight.MC.thePlayer.posY;
            this.z = Flight.MC.thePlayer.posZ;
        } else {
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        if (Flight.MC.thePlayer != null) {
            if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_BREAK_ON_TOGGLE, Setting.Type.CHECKBOX).isTicked() && MovementUtils.isMoving2()) {
                MovementUtils.zeroMotion2();
            }
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Dragpak": 
                case "Hellcat": 
                case "Demon": {
                    Flight.MC.renderGlobal.loadRenderers();
                    break;
                }
                case "Legacy AntiGamingChair II": {
                    MovementUtils.zeroMotion();
                }
            }
            this.players.forEach(packet -> Flight.MC.thePlayer.sendQueue.packetNoEvent((Packet)packet));
            this.players.clear();
            this.damaged = false;
            Flight.MC.thePlayer.capabilities.isFlying = false;
            this.gear = 0;
            this.groundTicks = 0;
            this.stage2 = 0;
            this.stage = 0;
            Flight.MC.timer.timerSpeed = this.oldTimerSpeed;
            this.z = 0.0;
            this.y = 0.0;
            this.x = 0.0;
            this.decrement = 0.0;
            this.timer.reset();
        }
    }

    @EventTarget(target=EventKeyPress.class)
    @Native
    public void onKeyPress(EventKeyPress e) {
        double shift = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_SHIFTING_VALUE, Setting.Type.SLIDER).getCurrentValue();
        double speed = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).getCurrentValue();
        double min = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).getMinimumValue();
        double max = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).getMaximumValue();
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_SHIFTING, Setting.Type.CHECKBOX).isTicked()) {
            block0 : switch (e.getKey()) {
                case 200: {
                    if (speed + shift > max && !Flight.MC.gameSettings.expertMode) {
                        Ries.INSTANCE.msg("The speed value will exceed the maximum speed limit with this shift. The value has been rounded downwards to the maximum allowed. To bypass this limit, enable expert mode.");
                        shift = Math.abs(max - speed);
                    }
                    ++this.gear;
                    this.gear = MathHelper.clamp_int(this.gear, -1, 8);
                    if (this.gear <= 8) {
                        Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).setCurrentValue(speed + shift);
                    }
                    if (this.gear == 0) {
                        Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).setCurrentValue(0.0);
                        Ries.INSTANCE.msg(String.format("Shifted into neutral for \"%s.\"", this.getInfo().name()));
                        break;
                    }
                    Ries.INSTANCE.msg(String.format("Up-shifted into speed %d for \"%s.\"", this.gear, this.getInfo().name()));
                    break;
                }
                case 208: {
                    if (speed - shift < min && !Flight.MC.gameSettings.expertMode) {
                        Ries.INSTANCE.msg("The speed value will exceed the minimum speed limit with this shift. The value has been rounded upwards to the minimum allowed. To bypass this limit, enable expert mode.");
                        shift = Math.abs(min - speed);
                    }
                    --this.gear;
                    this.gear = MathHelper.clamp_int(this.gear, -1, 8);
                    if (this.gear > -1) {
                        Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).setCurrentValue(speed - shift);
                    }
                    switch (this.gear) {
                        case -1: {
                            Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).setCurrentValue(-shift);
                            Ries.INSTANCE.msg(String.format("Shifted into reverse for \"%s.\"", this.getInfo().name()));
                            break block0;
                        }
                        case 0: {
                            Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).setCurrentValue(0.0);
                            Ries.INSTANCE.msg(String.format("Shifted into neutral for \"%s.\"", this.getInfo().name()));
                            break block0;
                        }
                    }
                    Ries.INSTANCE.msg(String.format("Down-shifted into speed %d for \"%s.\"", this.gear, this.getInfo().name()));
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventTarget(target=EventMotionUpdate.class)
    @Native
    public void onMotion(EventMotionUpdate e) {
        double speed = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).getCurrentValue();
        String mode = Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo();
        if (e.getState() != EventMotionUpdate.State.PRE) return;
        switch (mode) {
            case "Creative": {
                Flight.MC.thePlayer.capabilities.isFlying = true;
                return;
            }
            case "Area 51": {
                Flight.MC.timer.timerSpeed = 0.4f;
            }
            case "Anti-Kick Motion": 
            case "Motion": {
                if (MovementUtils.isMoving()) {
                    MovementUtils.setMoveSpeed(speed);
                } else {
                    MovementUtils.zeroMotion();
                }
                if (Flight.MC.gameSettings.keyBindJump.isKeyDown()) {
                    Flight.MC.thePlayer.motionY = MathHelper.clamp_double(speed, 0.0, 10.0);
                    return;
                }
                if (Flight.MC.gameSettings.keyBindSneak.isKeyDown()) {
                    Flight.MC.thePlayer.motionY = -MathHelper.clamp_double(speed, 0.0, 10.0);
                    return;
                }
                Flight.MC.thePlayer.motionY = mode.equals(ANTI_KICK) ? -0.125 : 0.0;
                return;
            }
            case "Hellcat": 
            case "Demon": 
            case "Dragpak": {
                if (MovementUtils.isMoving()) {
                    MovementUtils.setMoveSpeedTeleport(mode.equals(HELLCAT) ? 1000.0 : (mode.equals(DEMON) ? 10000.0 : 100000.0));
                    return;
                }
                if (Flight.MC.gameSettings.keyBindJump.isKeyDown()) {
                    Flight.MC.thePlayer.motionY = MathHelper.clamp_double(speed, 0.0, 10.0);
                    return;
                }
                if (Flight.MC.gameSettings.keyBindSneak.isKeyDown()) {
                    Flight.MC.thePlayer.motionY = -MathHelper.clamp_double(speed, 0.0, 10.0);
                    return;
                }
                Flight.MC.thePlayer.motionY = 0.0;
                return;
            }
            case "AntiCheat Reloaded Glide": {
                if (!(Flight.MC.thePlayer.fallDistance > 0.0f)) return;
                if (Flight.MC.thePlayer.ticksExisted % 5 != 0) return;
                MovementUtils.zeroMotion2();
                MovementUtils.setMoveSpeed(0.2225 - (double)Flight.MC.thePlayer.fallDistance * 0.01);
                return;
            }
            case "No Ground Damage": {
                if (Flight.MC.thePlayer.onGround) {
                    Flight.MC.thePlayer.jump();
                    ++this.stage;
                }
                if (this.stage > 4 || Flight.MC.thePlayer.hurtTime > 0 && Flight.MC.thePlayer.hurtTime < 18) {
                    this.stage = 0;
                    if (MovementUtils.isMoving()) {
                        MovementUtils.setMoveSpeed(speed);
                    } else {
                        MovementUtils.zeroMotion();
                    }
                    if (Flight.MC.gameSettings.keyBindJump.isKeyDown()) {
                        Flight.MC.thePlayer.motionY = MathHelper.clamp_double(speed, 0.0, 10.0);
                        return;
                    }
                    if (Flight.MC.gameSettings.keyBindSneak.isKeyDown()) {
                        Flight.MC.thePlayer.motionY = -MathHelper.clamp_double(speed, 0.0, 10.0);
                        return;
                    }
                    Flight.MC.thePlayer.motionY = 0.0;
                    return;
                }
                MovementUtils.zeroMotion();
                return;
            }
            case "Legacy NoCheat+": {
                Flight.MC.thePlayer.motionY = 0.0;
                Flight.MC.thePlayer.posY = Flight.MC.thePlayer.posY + (Flight.MC.thePlayer.ticksExisted % 2 == 0 ? 1.0E-4 : -1.0E-4);
                MovementUtils.setMoveSpeed(0.25);
                return;
            }
            case "Legacy Antivirus": {
                if (Flight.MC.thePlayer.ticksExisted % 2 == 0) {
                    MovementUtils.setMoveSpeed(speed);
                } else {
                    MovementUtils.zeroMotion();
                }
                Flight.MC.thePlayer.motionY = -0.17;
                return;
            }
            case "No Rules": {
                if (!Flight.MC.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    Ries.INSTANCE.msg("This bypass requires speed to use.");
                    this.toggle();
                    return;
                }
                if (Flight.MC.thePlayer.hurtTime == 0 && this.stage == 0) {
                    Flight.MC.timer.timerSpeed = 0.08f;
                    int i = 0;
                    while (true) {
                        if (i >= 8) {
                            Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.posY, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, true));
                            ++this.stage;
                            return;
                        }
                        Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.posY + (double)0.42f, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, false));
                        Flight.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.posY, Flight.MC.thePlayer.posZ, Flight.MC.thePlayer.rotationYaw, Flight.MC.thePlayer.rotationPitch, false));
                        ++i;
                    }
                }
                if (this.stage != 1) return;
                if (MovementUtils.isMoving()) {
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.312);
                } else {
                    MovementUtils.zeroMotion();
                }
                Flight.MC.thePlayer.motionY = 0.0;
                Flight.MC.timer.timerSpeed = 1.8f;
                return;
            }
            case "Verus": {
                if (!Flight.MC.thePlayer.isCollidedVertically) return;
                if (!Flight.MC.thePlayer.onGround) return;
                if (this.groundTicks > 1) {
                    Flight.MC.thePlayer.jump();
                    this.groundTicks = 0;
                }
                ++this.groundTicks;
                return;
            }
            case "Verus II": {
                if (1.5 - this.decrement > 0.33) {
                    MovementUtils.setMoveSpeed(1.5 - this.decrement);
                    this.decrement += 0.05;
                } else {
                    MovementUtils.setMoveSpeed(0.33);
                }
                if (!Flight.MC.thePlayer.isCollidedVertically) return;
                if (!Flight.MC.thePlayer.onGround) return;
                if (this.groundTicks > 1) {
                    Flight.MC.thePlayer.jump();
                    this.groundTicks = 0;
                }
                ++this.groundTicks;
                return;
            }
            case "Verus III": {
                if (Flight.MC.thePlayer.hurtTime > 0 && Flight.MC.thePlayer.hurtTime < 25) {
                    MovementUtils.setMoveSpeed(speed);
                } else {
                    MovementUtils.setMoveSpeed(0.33);
                }
                if (!Flight.MC.thePlayer.isCollidedVertically) return;
                if (!Flight.MC.thePlayer.onGround) return;
                if (this.groundTicks > 1) {
                    Flight.MC.thePlayer.jump();
                    this.groundTicks = 0;
                }
                ++this.groundTicks;
                return;
            }
            case "Legacy AntiGamingChair": {
                if (Flight.MC.thePlayer.ticksExisted % 3 == 0) {
                    MovementUtils.setMoveSpeed(speed);
                    if (Flight.MC.timer.timerSpeed == 0.1f) {
                        Flight.MC.timer.timerSpeed = 1.0f;
                    }
                    if (Flight.MC.gameSettings.keyBindSneak.isKeyDown()) {
                        Flight.MC.thePlayer.setPositionAndUpdate(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.posY - 5.0, Flight.MC.thePlayer.posZ);
                    }
                } else {
                    MovementUtils.zeroMotion();
                }
                if (Flight.MC.thePlayer.ticksExisted % 20 == 0 && Flight.MC.gameSettings.keyBindJump.isKeyDown()) {
                    Flight.MC.timer.timerSpeed = 0.1f;
                    Flight.MC.thePlayer.setPositionAndUpdate(Flight.MC.thePlayer.posX, Flight.MC.thePlayer.posY + 10.0, Flight.MC.thePlayer.posZ);
                }
                Flight.MC.thePlayer.motionY = -0.125;
                return;
            }
            case "Vulcan DEV": {
                if (Flight.MC.thePlayer.ticksExisted % (this.stage2 % 2 == 0 ? 6 : 5) == 0) {
                    this.stage = 1;
                    ++this.stage2;
                }
                if (this.stage != 1) return;
                if (!(Flight.MC.thePlayer.fallDistance > 0.4f)) return;
                this.stage = 0;
                return;
            }
            case "Chunk Glide": {
                Flight.MC.thePlayer.motionY = -0.08;
                Flight.MC.thePlayer.motionY *= (double)0.98f;
                MovementUtils.addFriction(0.91f);
                return;
            }
            case "Legacy AntiGamingChair II": {
                if (!this.damaged && Flight.MC.thePlayer.hurtTime > 0) {
                    this.damaged = true;
                    return;
                }
                Flight.MC.timer.timerSpeed = 0.25f;
                if (MovementUtils.isMoving()) {
                    MovementUtils.setMoveSpeed(8.0);
                } else {
                    MovementUtils.zeroMotion();
                }
                Flight.MC.thePlayer.motionY = 0.0;
                if (!this.timer.elapsed(5000L)) return;
                this.toggle();
                this.timer.reset();
                return;
            }
            case "Legacy Carbon": {
                switch (this.stage) {
                    case 0: {
                        if (Flight.MC.thePlayer.posY < this.y + 20.0) {
                            Flight.MC.thePlayer.setPositionAndUpdate(this.x, Flight.MC.thePlayer.posY, this.z);
                            if (Flight.MC.thePlayer.ticksExisted % 4 != 0) return;
                            Flight.MC.thePlayer.motionY = 0.25;
                            return;
                        }
                        this.stage = 1;
                        return;
                    }
                    case 1: {
                        if (Flight.MC.thePlayer.fallDistance > 12.0f || Flight.MC.thePlayer.onGround) {
                            this.x = Flight.MC.thePlayer.posX;
                            this.y = Flight.MC.thePlayer.posY;
                            this.z = Flight.MC.thePlayer.posZ;
                            Flight.MC.thePlayer.fallDistance = 0.0f;
                            this.stage = 0;
                            return;
                        }
                        MovementUtils.setMoveSpeed(0.25);
                    }
                }
                return;
            }
        }
    }

    @EventTarget(target=EventPacket.class)
    @Native
    public void onPacket(EventPacket e) {
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "No Ground Damage": {
                if (this.stage <= 3 && e.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer packet = (C03PacketPlayer)PacketUtils.getPacket(e.getPacket());
                    packet.setOnGround(false);
                }
                e.setCancelled(e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion);
                break;
            }
            case "Legacy AntiGamingChair II": {
                if (!(e.getPacket() instanceof C03PacketPlayer) || !this.damaged) break;
                this.players.add((C03PacketPlayer)PacketUtils.getPacket(e.getPacket()));
                e.setCancelled(true);
                break;
            }
            case "Verus II": 
            case "Verus III": {
                e.setCancelled(e.getPacket() instanceof S27PacketExplosion || e.getPacket() instanceof S12PacketEntityVelocity);
            }
        }
    }

    @EventTarget(target=EventAddCollision.class)
    @Native
    public void onCollision(EventAddCollision e) {
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Vulcan DEV": {
                if (this.stage == 1) break;
            }
            case "Verus": 
            case "Verus II": 
            case "Verus III": 
            case "Collision": {
                e.setBoundingBox(new AxisAlignedBB(-3.0, -2.0, -3.0, 3.0, 1.0, 3.0).offset(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()));
            }
        }
    }
}

