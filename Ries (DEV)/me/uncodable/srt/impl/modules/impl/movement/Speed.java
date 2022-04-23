/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Native
 */
package me.uncodable.srt.impl.modules.impl.movement;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventJump;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.events.events.packet.EventPacket;
import me.uncodable.srt.impl.events.events.peripheral.EventKeyPress;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.MovementUtils;
import me.uncodable.srt.impl.utils.PacketUtils;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import store.intent.intentguard.annotation.Native;

@ModuleInfo(internalName="Speed", name="Speed", desc="Allows you to increase your movement speed drastically.\n(P.S try out the Dragpak, Hellcat, and Demon modes!)", category=Module.Category.MOVEMENT)
public class Speed
extends Module {
    private static final String INTERNAL_SPEED_VALUE = "INTERNAL_SPEED_VALUE";
    private static final String INTERNAL_BREAK_ON_TOGGLE = "INTERNAL_BREAK_ON_TOGGLE";
    private static final String INTERNAL_SPEED_SHIFTING = "INTERNAL_SPEED_SHIFTING";
    private static final String INTERNAL_SPEED_SHIFTING_VALUE = "INTERNAL_SPEED_SHIFTING_VALUE";
    private static final String COMBO_BOX_SETTING_NAME = "Speed Mode";
    private static final String SPEED_VALUE_SETTING_NAME = "Speed";
    private static final String BREAK_ON_TOGGLE_SETTING_NAME = "Break On Toggle";
    private static final String SPEED_SHIFTING_SETTING_NAME = "Manual Mode";
    private static final String SPEED_SHIFTING_VALUE_SETTING_NAME = "Speed Shift";
    private static final String DRAGPAK = "Dragpak";
    private static final String DEMON = "Demon";
    private static final String HELLCAT = "Hellcat";
    private static final String VANILLA_BHOP = "Vanilla Bunny Hop";
    private static final String VANILLA_GROUND = "Vanilla Ground";
    private static final String VANILLA_BHOP_FRICTION = "Vanilla Bunny Hop Friction";
    private static final String VERUS = "Verus";
    private static final String ANTICHEAT_RELOADED = "AntiCheat Reloaded";
    private static final String VENOM = "Venom";
    private static final String NCP = "NoCheat+";
    private static final String LEGACY_WATCHDOG = "Legacy Watchdog";
    private static final String VERUS_II = "Verus II";
    private static final String VERUS_III = "Verus III";
    private static final String SPARTAN = "Spartan";
    private static final String LEGACY_VOID = "Legacy Void Anti-Cheat";
    private static final String LEGACY_JI = "Legacy JI Janitor";
    private static final String LEGACY_AGC_II = "Legacy AntiGamingChair II";
    private static final String LEGACY_AGC_III = "Legacy AntiGamingChair III";
    private static final String LEGACY_ALICE = "Legacy Alice";
    private static final String LEGACY_ANTIVIRUS = "Legacy Antivirus";
    private static final String ANTIHAXERMAN = "AntiHaxerman";
    private static final String GUARD = "Guard";
    private static final String LEGACY_AGC = "Legacy AntiGamingChair";
    private static final String TROJAN = "Trojan";
    private static final String MATRIX = "Matrix";
    private static final String HURT_TIME = "Hurt-Time";
    private static final String GWEN = "GWEN";
    private static final String SPACE_POLICE = "Space Police";
    private static final String AAC_3_3_15 = "AAC 3.3.15";
    private static final String SYUU = "Syuu";
    private static final String SYUU_II = "Syuu II";
    private static final String CARBON = "Carbon";
    private static final String AREA_51 = "Area 51";
    private static final String WATCHDOG_TEST = "Watchdog DEV";
    private static final String VULCAN = "Vulcan";
    private double lastSpeed;
    private float oldTimerSpeed;
    private int gear;
    private int stage;

    public Speed(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, DRAGPAK, DEMON, HELLCAT, VANILLA_BHOP, VANILLA_GROUND, VANILLA_BHOP_FRICTION, HURT_TIME, VERUS, VERUS_II, VERUS_III, VULCAN, SPARTAN, MATRIX, ANTICHEAT_RELOADED, VENOM, NCP, WATCHDOG_TEST, LEGACY_WATCHDOG, SYUU, SYUU_II, GWEN, LEGACY_VOID, LEGACY_JI, TROJAN, LEGACY_ALICE, LEGACY_ANTIVIRUS, ANTIHAXERMAN, GUARD, LEGACY_AGC, LEGACY_AGC_II, LEGACY_AGC_III, SPACE_POLICE, AAC_3_3_15, CARBON, AREA_51);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SPEED_VALUE, SPEED_VALUE_SETTING_NAME, 5.0, 0.0, 10.0);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SPEED_SHIFTING_VALUE, SPEED_SHIFTING_VALUE_SETTING_NAME, 0.5, 0.0, 10.0);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_BREAK_ON_TOGGLE, BREAK_ON_TOGGLE_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_SPEED_SHIFTING, SPEED_SHIFTING_SETTING_NAME, true);
    }

    @Override
    public void onEnable() {
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Watchdog DEV": {
                Module disabler = Ries.INSTANCE.getModuleManager().getModuleByName("Disabler");
                Ries.INSTANCE.getSettingManager().getSetting(disabler, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).setCurrentCombo("Verus Partial");
                if (disabler.isEnabled()) break;
                disabler.toggle();
            }
        }
        this.oldTimerSpeed = Speed.MC.timer.timerSpeed;
    }

    @Override
    public void onDisable() {
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_BREAK_ON_TOGGLE, Setting.Type.CHECKBOX).isTicked() && MovementUtils.isMoving2()) {
            MovementUtils.zeroMotion2();
        }
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Hellcat": 
            case "Demon": {
                Speed.MC.renderGlobal.loadRenderers();
            }
        }
        Speed.MC.timer.timerSpeed = this.oldTimerSpeed;
        this.lastSpeed = 0.0;
        this.gear = 0;
        this.stage = 0;
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
                    if (speed + shift > max && !Speed.MC.gameSettings.expertMode) {
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
                    if (speed - shift < min && !Speed.MC.gameSettings.expertMode) {
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

    @EventTarget(target=EventJump.class)
    public void onJump(EventJump e) {
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Matrix": 
            case "Guard": 
            case "Demon": 
            case "Hellcat": 
            case "Verus II": 
            case "AntiCheat Reloaded": 
            case "Space Police": 
            case "Carbon": 
            case "Spartan": {
                e.setCancelled(true);
            }
        }
    }

    @EventTarget(target=EventMotionUpdate.class)
    @Native
    public void onMotion(EventMotionUpdate e) {
        if (e.getState() == EventMotionUpdate.State.PRE) {
            String mode;
            double speed = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPEED_VALUE, Setting.Type.SLIDER).getCurrentValue();
            switch (mode = Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Hurt-Time": {
                    if (Speed.MC.thePlayer.hurtTime <= 0 || Speed.MC.thePlayer.hurtTime >= 5) break;
                    MovementUtils.setMoveSpeed(speed);
                    break;
                }
                case "Vanilla Bunny Hop Friction": {
                    if (!Speed.MC.thePlayer.onGround) {
                        MovementUtils.addFriction();
                    }
                }
                case "Vanilla Bunny Hop": {
                    if (MovementUtils.isMoving()) {
                        if (Speed.MC.thePlayer.onGround) {
                            Speed.MC.thePlayer.jump();
                        }
                        MovementUtils.setMoveSpeed(speed);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Area 51": {
                    Speed.MC.timer.timerSpeed = 0.4f;
                    if (MovementUtils.isMoving()) {
                        if (Speed.MC.thePlayer.onGround) {
                            Speed.MC.thePlayer.jump();
                        }
                        MovementUtils.setMoveSpeed(speed);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Vanilla Ground": {
                    MovementUtils.setMoveSpeed(MovementUtils.isMoving() ? speed : 0.0);
                    break;
                }
                case "Hellcat": 
                case "Demon": 
                case "Dragpak": {
                    if (!MovementUtils.isMoving()) break;
                    MovementUtils.setMoveSpeedTeleport(mode.equals(HELLCAT) ? 1000.0 : (mode.equals(DEMON) ? 10000.0 : 100000.0));
                    break;
                }
                case "Vulcan": {
                    double hurtTimeBoost;
                    double d = hurtTimeBoost = Speed.MC.thePlayer.hurtTime > 0 ? 0.5 : 0.0;
                    if (!MovementUtils.isMoving() || !Speed.MC.thePlayer.onGround) break;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.015 + hurtTimeBoost);
                    Speed.MC.thePlayer.jump();
                    break;
                }
                case "Verus": {
                    if (!MovementUtils.isMoving()) break;
                    double hurtTimeBoost = Speed.MC.thePlayer.hurtTime > 0 ? 0.15 : 0.0;
                    double speedEffect = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.1 - 0.01445 : 0.0;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.0445 - speedEffect + hurtTimeBoost);
                    if (!Speed.MC.thePlayer.onGround) break;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() - 0.021 + hurtTimeBoost);
                    Speed.MC.thePlayer.jump();
                    break;
                }
                case "Verus III": {
                    if (!MovementUtils.isMoving()) break;
                    double hurtTimeBoost = Speed.MC.thePlayer.hurtTime > 0 && Speed.MC.thePlayer.hurtTime < 16 ? 0.6 : 0.0;
                    double speedEffect = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.1 - 0.01445 : 0.0;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.0445 - speedEffect + hurtTimeBoost);
                    if (!Speed.MC.thePlayer.onGround) break;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() - 0.021 + hurtTimeBoost);
                    Speed.MC.thePlayer.jump();
                    break;
                }
                case "AntiCheat Reloaded": {
                    double decrement = (Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.05 : 0.0) - ((float)Speed.MC.thePlayer.hurtTime > 0.0f ? 0.65 : 0.0);
                    if (Speed.MC.thePlayer.ticksExisted % 3 != 0) break;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.07 - decrement);
                    break;
                }
                case "Venom": {
                    if (MovementUtils.isMoving()) {
                        double hawkSpeed = MovementUtils.getBaseMoveSpeed();
                        if (Speed.MC.thePlayer.onGround) {
                            Speed.MC.thePlayer.motionY = 0.42f;
                        }
                        if (this.targetEntity == null && Speed.MC.thePlayer.moveStrafing == 0.0f) {
                            hawkSpeed = Speed.MC.thePlayer.fallDistance <= 0.0f ? (hawkSpeed += 0.095) : (hawkSpeed += 0.03);
                        }
                        if (Speed.MC.thePlayer.hurtTime > 0) {
                            hawkSpeed += 0.2;
                        }
                        MovementUtils.setMoveSpeed(hawkSpeed);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Watchdog DEV": {
                    if (MovementUtils.isMoving()) {
                        double amplifierBoost = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.01 : 0.0;
                        double ncpSpeed = MovementUtils.getBaseMoveSpeed() + 0.11 + amplifierBoost;
                        if (Speed.MC.thePlayer.hurtTime > 3 && Speed.MC.thePlayer.hurtTime < 19) {
                            ncpSpeed += 0.02;
                        }
                        if (Speed.MC.thePlayer.onGround) {
                            this.lastSpeed = ncpSpeed;
                            Speed.MC.thePlayer.jump();
                            break;
                        }
                        MovementUtils.setMoveSpeed(this.lastSpeed *= 0.95);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Legacy Watchdog": {
                    if (MovementUtils.isMoving() && Speed.MC.thePlayer.moveStrafing == 0.0f && Speed.MC.gameSettings.keyBindForward.isKeyDown()) {
                        double amplifierBoost = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.01 : 0.0;
                        double ncpSpeed = MovementUtils.getBaseMoveSpeed() + 0.0911495 + amplifierBoost;
                        if (Speed.MC.thePlayer.hurtTime > 3 && Speed.MC.thePlayer.hurtTime < 19) {
                            ncpSpeed += 0.02;
                        }
                        if (Speed.MC.thePlayer.onGround) {
                            this.lastSpeed = ncpSpeed;
                            Speed.MC.thePlayer.jump();
                            break;
                        }
                        MovementUtils.setMoveSpeed(this.lastSpeed *= 0.9685);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "NoCheat+": {
                    if (MovementUtils.isMoving()) {
                        double amplifierBoost = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.0095 : 0.0;
                        double ncpSpeed = MovementUtils.getBaseMoveSpeed() + 0.091154 + amplifierBoost;
                        if (Speed.MC.thePlayer.hurtTime > 3 && Speed.MC.thePlayer.hurtTime < 19) {
                            ncpSpeed += 0.05;
                        }
                        if (Speed.MC.thePlayer.onGround) {
                            this.lastSpeed = ncpSpeed;
                            Speed.MC.thePlayer.jump();
                            break;
                        }
                        MovementUtils.setMoveSpeed(this.lastSpeed *= (double)0.97f);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Verus II": {
                    if (!MovementUtils.isMoving() || !Speed.MC.thePlayer.onGround) break;
                    MovementUtils.addFriction(1.22);
                    break;
                }
                case "Legacy Void Anti-Cheat": {
                    Speed.MC.thePlayer.motionY = 0.0;
                    MovementUtils.setMoveSpeed(MovementUtils.isMoving() ? 4.0 : 0.0);
                    break;
                }
                case "Legacy JI Janitor": {
                    double speedDecrement = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.09 : 0.0;
                    double jiSpeed = MovementUtils.getBaseMoveSpeed() + 0.27 - speedDecrement;
                    if (MovementUtils.isMoving()) {
                        if (Speed.MC.thePlayer.onGround) {
                            Speed.MC.thePlayer.jump();
                        }
                        MovementUtils.setMoveSpeed(jiSpeed);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Legacy AntiGamingChair II": {
                    if (Speed.MC.thePlayer.onGround) {
                        Speed.MC.thePlayer.jump();
                        Speed.MC.timer.timerSpeed = 0.5f;
                        break;
                    }
                    if (!(Speed.MC.thePlayer.fallDistance > 0.0f)) break;
                    Speed.MC.timer.timerSpeed = 40.0f;
                    break;
                }
                case "Legacy Alice": {
                    if (MovementUtils.isMoving() && Speed.MC.thePlayer.onGround && !Speed.MC.gameSettings.keyBindJump.isKeyDown() && Speed.MC.thePlayer.moveStrafing == 0.0f && Speed.MC.thePlayer.fallDistance == 0.0f) {
                        if (Speed.MC.thePlayer.ticksExisted % 2 == 0) {
                            e.setPosY(Speed.MC.thePlayer.getEntityBoundingBox().minY - 0.01);
                            break;
                        }
                        MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.17);
                        break;
                    }
                    MovementUtils.addFriction();
                    break;
                }
                case "Legacy Antivirus": {
                    if (Speed.MC.thePlayer.ticksExisted % 2 == 0) {
                        MovementUtils.setMoveSpeed(speed);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "AntiHaxerman": {
                    if (!MovementUtils.isMoving()) break;
                    if (Speed.MC.thePlayer.onGround) {
                        Speed.MC.thePlayer.jump();
                        this.stage = 0;
                        break;
                    }
                    if (this.stage != 0) break;
                    MovementUtils.addFriction(1.022);
                    ++this.stage;
                    break;
                }
                case "Legacy AntiGamingChair": {
                    if (MovementUtils.isMoving()) {
                        MovementUtils.setMoveSpeed(Speed.MC.thePlayer.ticksExisted % 3 == 0 ? speed : 0.0);
                        if (!Speed.MC.thePlayer.onGround) break;
                        Speed.MC.thePlayer.jump();
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Guard": {
                    MovementUtils.setMoveSpeed(Speed.MC.thePlayer.moveStrafing == 0.0f && Speed.MC.thePlayer.ticksExisted % 2 == 0 ? MovementUtils.getBaseMoveSpeed() + 0.2653 : 0.0);
                    break;
                }
                case "Spartan": {
                    double increment;
                    double d = increment = Speed.MC.thePlayer.onGround ? 0.0851 : 0.0;
                    if (!MovementUtils.isMoving() || !Speed.MC.gameSettings.keyBindForward.isKeyDown() || Speed.MC.thePlayer.moveStrafing != 0.0f) break;
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + increment);
                    break;
                }
                case "Legacy AntiGamingChair III": {
                    ++this.stage;
                    Ries.INSTANCE.msg("stage: " + this.stage);
                    Speed.MC.timer.timerSpeed = this.stage > 2 ? 20.0f : 0.01f;
                    if (this.stage <= 220) break;
                    this.stage = 0;
                    this.toggle();
                    break;
                }
                case "Trojan": {
                    if (MovementUtils.isMoving()) {
                        MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.04);
                        if (!Speed.MC.thePlayer.onGround) break;
                        Speed.MC.thePlayer.jump();
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Matrix": {
                    if (MovementUtils.isMoving() && Speed.MC.gameSettings.keyBindForward.isKeyDown() && Speed.MC.thePlayer.moveStrafing == 0.0f) {
                        MovementUtils.addFriction(1.02285);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "GWEN": {
                    if (MovementUtils.isMoving()) {
                        if (Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                            MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed());
                        } else {
                            MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.05);
                        }
                        if (!Speed.MC.thePlayer.onGround) break;
                        Speed.MC.thePlayer.jump();
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Space Police": {
                    if (!MovementUtils.isMoving()) break;
                    if (Speed.MC.thePlayer.hurtTime > 0 && Speed.MC.thePlayer.hurtTime < 10) {
                        MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed() + 0.5);
                        break;
                    }
                    if (!Speed.MC.thePlayer.onGround) break;
                    MovementUtils.addFriction(1.3);
                    break;
                }
                case "AAC 3.3.15": {
                    if (!MovementUtils.isMoving() || !Speed.MC.thePlayer.onGround || !Speed.MC.gameSettings.keyBindForward.isKeyDown()) break;
                    Speed.MC.thePlayer.jump();
                    MovementUtils.setMoveSpeed(MovementUtils.getBaseMoveSpeed());
                    MovementUtils.addFriction(1.7);
                    break;
                }
                case "Syuu": {
                    if (MovementUtils.isMoving()) {
                        double amplifierBoost = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.01 : 0.0;
                        double ncpSpeed = MovementUtils.getBaseMoveSpeed() + 0.089 + amplifierBoost;
                        if (Speed.MC.thePlayer.hurtTime > 3 && Speed.MC.thePlayer.hurtTime < 19) {
                            ncpSpeed += 0.05;
                        }
                        if (Speed.MC.thePlayer.onGround) {
                            this.lastSpeed = ncpSpeed;
                            Speed.MC.thePlayer.jump();
                            break;
                        }
                        MovementUtils.setMoveSpeed(this.lastSpeed *= (double)0.97f);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Syuu II": {
                    if (MovementUtils.isMoving()) {
                        double amplifierBoost = Speed.MC.thePlayer.isPotionActive(Potion.moveSpeed) ? (double)Speed.MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() * 0.12 : 0.0;
                        double ncpSpeed = MovementUtils.getBaseMoveSpeed() + 0.2 + amplifierBoost;
                        if (Speed.MC.thePlayer.hurtTime > 3 && Speed.MC.thePlayer.hurtTime < 19) {
                            ncpSpeed += 0.05;
                        }
                        if (Speed.MC.thePlayer.onGround) {
                            this.lastSpeed = ncpSpeed;
                            Speed.MC.thePlayer.jump();
                            break;
                        }
                        MovementUtils.setMoveSpeed(this.lastSpeed *= (double)0.97f);
                        break;
                    }
                    MovementUtils.zeroMotion();
                    break;
                }
                case "Carbon": {
                    if (!MovementUtils.isMoving() || !Speed.MC.thePlayer.onGround) break;
                    double carbonSpeed = MovementUtils.getBaseMoveSpeed() + 0.5;
                    if (Speed.MC.thePlayer.hurtTime > 0) {
                        carbonSpeed *= 2.0;
                    }
                    MovementUtils.setMoveSpeed(carbonSpeed);
                }
            }
        }
    }

    @EventTarget(target=EventPacket.class)
    @Native
    public void onPacket(EventPacket e) {
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Verus": {
                if (!(e.getPacket() instanceof C0BPacketEntityAction)) break;
                C0BPacketEntityAction packet = (C0BPacketEntityAction)PacketUtils.getPacket(e.getPacket());
                e.setCancelled(packet.getAction() == C0BPacketEntityAction.Action.START_SPRINTING || packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING);
            }
        }
    }
}

