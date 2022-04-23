/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.RandomUtils
 */
package me.uncodable.srt.impl.modules.impl.combat;

import java.util.Comparator;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventJump;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.events.events.entity.EventUpdate;
import me.uncodable.srt.impl.events.events.packet.EventPacket;
import me.uncodable.srt.impl.events.events.render.EventGameRender;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.CombatUtils;
import me.uncodable.srt.impl.utils.EntityUtils;
import me.uncodable.srt.impl.utils.MovementUtils;
import me.uncodable.srt.impl.utils.PacketUtils;
import me.uncodable.srt.impl.utils.RngUtils;
import me.uncodable.srt.impl.utils.RotationUtils;
import me.uncodable.srt.impl.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import org.apache.commons.lang3.RandomUtils;

@ModuleInfo(internalName="Aura", name="Aura", desc="Allows you to attack any entity in an aura, in a range of your choosing.\n\nNOTE: The \"Hit-box Check\" may alter the aura's functionality drastically, e.g. aura reach.\nEnsure that settings are adjusted accordingly!", category=Module.Category.COMBAT, exp=true)
public class Aura
extends Module {
    private static final String INTERNAL_AUTOBLOCK_COMBO_BOX = "INTERNAL_AUTOBLOCK_COMBO_BOX";
    private static final String INTERNAL_STATE_COMBO_BOX = "INTERNAL_STATE_COMBO_BOX";
    private static final String INTERNAL_PRIORITY_COMBO_BOX = "INTERNAL_PRIORITY_COMBO_BOX";
    private static final String INTERNAL_ROTATIONS_COMBO_BOX = "INTERNAL_ROTATIONS_COMBO_BOX";
    private static final String INTERNAL_PLAYERS = "INTERNAL_PLAYERS";
    private static final String INTERNAL_ANIMALS = "INTERNAL_ANIMALS";
    private static final String INTERNAL_MOBS = "INTERNAL_MOBS";
    private static final String INTERNAL_INVISIBLES = "INTERNAL_INVISIBLES";
    private static final String INTERNAL_FRIENDS = "INTERNAL_FRIENDS";
    private static final String INTERNAL_DEAD = "INTERNAL_DEAD";
    private static final String INTERNAL_TEAMS = "INTERNAL_TEAMS";
    private static final String INTERNAL_WEAPONS_ONLY = "INTERNAL_WEAPONS_ONLY";
    private static final String INTERNAL_REACH_VALUE = "INTERNAL_REACH_VALUE";
    private static final String INTERNAL_RAYTRACE = "INTERNAL_WALLS";
    private static final String INTERNAL_MINIMUM_CPS_VALUE = "INTERNAL_MINIMUM_CPS_VALUE";
    private static final String INTERNAL_MAXIMUM_CPS_VALUE = "INTERNAL_MAXIMUM_CPS_VALUE";
    private static final String INTERNAL_HIT_CHANCE_VALUE = "INTERNAL_HIT_CHANCE_VALUE";
    private static final String INTERNAL_NO_KEEP_SPRINT = "INTERNAL_NO_KEEP_SPRINT";
    private static final String INTERNAL_LOCK_VIEW = "INTERNAL_LOCK_VIEW";
    private static final String INTERNAL_UNBLOCK = "INTERNAL_UNBLOCK";
    private static final String INTERNAL_POSITION_FIX = "INTERNAL_POSITION_FIX";
    private static final String INTERNAL_INVENTORY_CHECK = "INTERNAL_INVENTORY_CHECK";
    private static final String INTERNAL_INFINITE_REACH_VALUE = "INTERNAL_INFINITE_REACH_VALUE";
    private static final String INTERNAL_REACH_WHEN_MOVING = "INTERNAL_REACH_WHEN_MOVING";
    private static final String INTERNAL_REACH_WHEN_OPPONENT_MOVING = "INTERNAL_REACH_WHEN_OPPONENT_MOVING";
    private static final String INTERNAL_ATTACK_COUNT_VALUE = "INTERNAL_ATTACK_COUNT_VALUE";
    private static final String INTERNAL_SWING_REACH_VALUE = "INTERNAL_SWING_REACH_VALUE";
    private static final String INTERNAL_ROTATION_DISTANCE_VALUE = "INTERNAL_ROTATION_DISTANCE_VALUE";
    private static final String INTERNAL_HIT_BOX_CHECK = "INTERNAL_HIT_BOX_CHECK";
    private static final String INTERNAL_MINIMUM_CPS_SPIKE_VALUE = "INTERNAL_MINIMUM_CPS_SPIKE_VALUE";
    private static final String INTERNAL_MAXIMUM_CPS_SPIKE_VALUE = "INTERNAL_MAXIMUM_CPS_SPIKE_VALUE";
    private static final String INTERNAL_SPIKE_CHANCE_VALUE = "INTERNAL_SPIKE_CHANCE_VALUE";
    private static final String INTERNAL_CPS_SPIKE_DURATION_VALUE = "INTERNAL_CPS_SPIKE_DURATION_VALUE";
    private static final String COMBO_BOX_SETTING_NAME = "Aura Mode";
    private static final String AUTOBLOCK_SETTING_NAME = "Auto Block Mode";
    private static final String STATE_SETTING_NAME = "State";
    private static final String PRIORITY_SETTING_NAME = "Priority";
    private static final String ROTATIONS_SETTING_NAME = "Rotations";
    private static final String PLAYERS_SETTING_NAME = "Target Players";
    private static final String ANIMALS_SETTING_NAME = "Target Animals";
    private static final String MOBS_SETTING_NAME = "Target Mobs";
    private static final String INVISIBLES_SETTING_NAME = "Target Invisible Entities";
    private static final String FRIENDS_SETTING_NAME = "Target Friends";
    private static final String DEAD_SETTING_NAME = "Target Dead Entities";
    private static final String TEAMS_SETTING_NAME = "Teams Check";
    private static final String WEAPONS_ONLY_SETTING_NAME = "Use Weapons Only";
    private static final String REACH_VALUE_SETTING_NAME = "Reach";
    private static final String RAYTRACE_SETTING_NAME = "Ray Trace";
    private static final String MINIMUM_CPS_VALUE_SETTING_NAME = "Minimum CPS";
    private static final String MAXIMUM_CPS_VALUE_SETTING_NAME = "Maximum CPS";
    private static final String HIT_CHANCE_VALUE_SETTING_NAME = "Hit Chance";
    private static final String NO_KEEP_SPRINT_SETTING_NAME = "No Keep-Sprint";
    private static final String LOCK_VIEW_SETTING_NAME = "Use Lock View";
    private static final String UNBLOCK_SETTING_NAME = "Unblock";
    private static final String POSITION_FIX_SETTING_NAME = "Position Fix";
    private static final String INVENTORY_CHECK_SETTING_NAME = "Inventory Check";
    private static final String INFINITE_REACH_VALUE_SETTING_NAME = "Infinite Aura Reach";
    private static final String REACH_WHEN_MOVING_SETTING_NAME = "Reach While Move Only";
    private static final String REACH_WHEN_OPPONENT_MOVE_SETTING_NAME = "Reach While Opponent Moves";
    private static final String ATTACK_COUNT_VALUE_SETTING_NAME = "Times To Attack";
    private static final String SWING_REACH_VALUE_SETTING_NAME = "Swing Reach";
    private static final String ROTATION_DISTANCE_SETTING_NAME = "Rotation Range";
    private static final String HIT_BOX_CHECK_SETTING_NAME = "Hit-box Check";
    private static final String MINIMUM_CPS_SPIKE_VALUE_SETTING_NAME = "Minimum CPS Spike";
    private static final String MAXIMUM_CPS_SPIKE_VALUE_SETTING_NAME = "Maximum CPS Spike";
    private static final String SPIKE_CHANCE_VALUE_SETTING_NAME = "CPS Spike Chance";
    private static final String CPS_SPIKE_DURATION_VALUE_SETTING_NAME = "CPS Spike Duration";
    private static final String SINGLE = "Single";
    private static final String MULTI = "Multi";
    private static final String INFINITE_AURA = "Infinite Aura";
    private static final String PRE = "Pre";
    private static final String POST = "Post";
    private static final String BOTH = "Both";
    private static final String DISTANCE = "Distance";
    private static final String HEALTH = "Health";
    private static final String NONE = "None";
    private static final String BASIC = "Basic";
    private static final String ROUNDED = "Rounded";
    private static final String BYPASS_1 = "Bypass I";
    private static final String NO_PITCH = "No Pitch";
    private static final String TICK_ROUND = "Tick Round";
    private static final String CINEMATIC = "Cinematic";
    private static final String BYPASS_II = "Bypass II";
    private static final String BYPASS_III = "Bypass III";
    private static final String BYPASS_IV = "Bypass IV";
    private static final String VANILLA = "Vanilla";
    private static final String VERUS = "Verus";
    private static final String LEGACY_VERUS_INFINITE_AURA = "Legacy Verus Infinite Aura";
    private boolean blocking;
    private boolean looking;
    private boolean sent;
    private final Timer timer = new Timer();
    private final MouseFilter mouseFilterX = new MouseFilter();
    private final MouseFilter mouseFilterY = new MouseFilter();
    private EventMotionUpdate motionUpdate;
    private int ticksSinceSent;
    private float lastYaw;
    private float lastPitch;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private double min;
    private double max;
    private double reach;
    private long actualCPS;

    public Aura(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, SINGLE, MULTI, INFINITE_AURA, LEGACY_VERUS_INFINITE_AURA);
        Ries.INSTANCE.getSettingManager().addComboBox(this, INTERNAL_ROTATIONS_COMBO_BOX, ROTATIONS_SETTING_NAME, BYPASS_IV, BYPASS_III, BYPASS_II, BYPASS_1, BASIC, CINEMATIC, TICK_ROUND, ROUNDED, NO_PITCH, NONE);
        Ries.INSTANCE.getSettingManager().addComboBox(this, INTERNAL_STATE_COMBO_BOX, STATE_SETTING_NAME, PRE, POST, BOTH);
        Ries.INSTANCE.getSettingManager().addComboBox(this, INTERNAL_AUTOBLOCK_COMBO_BOX, AUTOBLOCK_SETTING_NAME, NONE, VERUS, VANILLA);
        Ries.INSTANCE.getSettingManager().addComboBox(this, INTERNAL_PRIORITY_COMBO_BOX, PRIORITY_SETTING_NAME, DISTANCE);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_REACH_VALUE, REACH_VALUE_SETTING_NAME, 4.0, 3.0, 6.0);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_MINIMUM_CPS_VALUE, MINIMUM_CPS_VALUE_SETTING_NAME, 17.0, 1.0, 20.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_MAXIMUM_CPS_VALUE, MAXIMUM_CPS_VALUE_SETTING_NAME, 50.0, 1.0, 20.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_MINIMUM_CPS_SPIKE_VALUE, MINIMUM_CPS_SPIKE_VALUE_SETTING_NAME, 3.0, 1.0, 20.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_MAXIMUM_CPS_SPIKE_VALUE, MAXIMUM_CPS_SPIKE_VALUE_SETTING_NAME, 6.0, 1.0, 20.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SPIKE_CHANCE_VALUE, SPIKE_CHANCE_VALUE_SETTING_NAME, 6.0, 0.0, 100.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_CPS_SPIKE_DURATION_VALUE, CPS_SPIKE_DURATION_VALUE_SETTING_NAME, 500.0, 0.0, 5000.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_ATTACK_COUNT_VALUE, ATTACK_COUNT_VALUE_SETTING_NAME, 1.0, 1.0, 100.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_HIT_CHANCE_VALUE, HIT_CHANCE_VALUE_SETTING_NAME, 100.0, 0.0, 100.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SWING_REACH_VALUE, SWING_REACH_VALUE_SETTING_NAME, 10.0, 0.0, 100.0);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_ROTATION_DISTANCE_VALUE, ROTATION_DISTANCE_SETTING_NAME, 15.0, 0.0, 40.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_INFINITE_REACH_VALUE, INFINITE_REACH_VALUE_SETTING_NAME, 100.0, 0.0, 100.0, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_UNBLOCK, UNBLOCK_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_REACH_WHEN_MOVING, REACH_WHEN_MOVING_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_REACH_WHEN_OPPONENT_MOVING, REACH_WHEN_OPPONENT_MOVE_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_HIT_BOX_CHECK, HIT_BOX_CHECK_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_INVENTORY_CHECK, INVENTORY_CHECK_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_POSITION_FIX, POSITION_FIX_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_LOCK_VIEW, LOCK_VIEW_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_RAYTRACE, RAYTRACE_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_PLAYERS, PLAYERS_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_ANIMALS, ANIMALS_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_MOBS, MOBS_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_INVISIBLES, INVISIBLES_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_FRIENDS, FRIENDS_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_DEAD, DEAD_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_TEAMS, TEAMS_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_WEAPONS_ONLY, WEAPONS_ONLY_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_NO_KEEP_SPRINT, NO_KEEP_SPRINT_SETTING_NAME);
    }

    @Override
    public void onEnable() {
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_HIT_BOX_CHECK, Setting.Type.CHECKBOX).isTicked()) {
            Ries.INSTANCE.msg("The hit-box check option is not complete.");
        }
    }

    @Override
    public void onDisable() {
        this.motionUpdate = null;
        this.mouseFilterX.reset();
        this.mouseFilterY.reset();
        this.looking = false;
        this.sent = false;
        this.blocking = false;
        this.ticksSinceSent = 0;
        this.lastYaw = Aura.MC.thePlayer.rotationYaw;
        this.lastPitch = Aura.MC.thePlayer.rotationPitch;
        this.smoothCamFilterY = 0.0f;
        this.smoothCamFilterX = 0.0f;
        this.smoothCamPitch = 0.0f;
        this.smoothCamYaw = 0.0f;
        this.timer.reset();
        this.reach = 0.0;
        Aura.MC.gameSettings.keyBindUseItem.setKeyDown(false);
        CombatUtils.setCounter(0);
    }

    @EventTarget(target=EventGameRender.class)
    public void onRender(EventGameRender e) {
        if (EntityUtils.skipEntity2(this.targetEntity, this) && (!Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_INVENTORY_CHECK, Setting.Type.CHECKBOX).isTicked() || Aura.MC.currentScreen == null) && (double)Aura.MC.thePlayer.getDistanceToEntity(this.targetEntity) <= Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ROTATION_DISTANCE_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ROTATIONS_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Bypass IV": {
                    float sensitivity = Aura.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                    float gcd = sensitivity * sensitivity * sensitivity * 1.2f;
                    float[] rotations = RotationUtils.doBypassIIRotations(this.targetEntity);
                    float deltaX = rotations[0] - Aura.MC.thePlayer.prevRotationYaw;
                    Aura.MC.thePlayer.setAngles((int)(deltaX * gcd), (int)((float)Aura.MC.mouseHelper.deltaY * gcd));
                    this.looking = Aura.MC.objectMouseOver.entityHit == this.targetEntity;
                    break;
                }
                case "Bypass III": {
                    float sensitivity = Aura.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                    float gcd = sensitivity * sensitivity * sensitivity * 1.2f;
                    float[] rotations = RotationUtils.doBypassIIRotations(this.targetEntity);
                    float deltaX = rotations[0] - this.lastYaw;
                    float deltaY = rotations[1] - this.lastPitch;
                    deltaX -= deltaX % gcd;
                    deltaY -= deltaY % gcd;
                    Aura.MC.thePlayer.rotationYaw += deltaX * gcd;
                    Aura.MC.thePlayer.rotationPitch += deltaY * gcd;
                    this.lastYaw = Aura.MC.thePlayer.rotationYaw;
                    this.lastPitch = MathHelper.clamp_float(Aura.MC.thePlayer.rotationPitch, -90.0f, 90.0f);
                    this.looking = Aura.MC.objectMouseOver.entityHit == this.targetEntity;
                    break;
                }
                case "Bypass II": {
                    float sensitivity = Aura.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                    float gcd = sensitivity * sensitivity * sensitivity * 1.2f;
                    float[] rotations = RotationUtils.doBypassIIRotations(this.targetEntity);
                    float deltaX = rotations[0] - this.lastYaw;
                    float deltaY = rotations[1] - this.lastPitch;
                    deltaX -= deltaX % gcd;
                    deltaY -= deltaY % gcd;
                    Aura.MC.thePlayer.rotationYaw += deltaX * gcd;
                    Aura.MC.thePlayer.rotationPitch += deltaY * gcd;
                    this.lastYaw = Aura.MC.thePlayer.rotationYaw -= Aura.MC.thePlayer.rotationYaw % gcd;
                    this.lastPitch = MathHelper.clamp_float(Aura.MC.thePlayer.rotationPitch -= Aura.MC.thePlayer.rotationPitch % gcd, -90.0f, 90.0f);
                    this.looking = Aura.MC.objectMouseOver.entityHit == this.targetEntity;
                    break;
                }
                case "Cinematic": {
                    float sensitivity = Aura.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                    float gcd = sensitivity * sensitivity * sensitivity * 8.0f;
                    float[] rotations = RotationUtils.doBypassIIRotations(this.targetEntity);
                    float deltaX = (rotations[0] - this.lastYaw) * gcd;
                    float deltaY = (rotations[1] - this.lastPitch) * gcd;
                    this.smoothCamYaw += deltaX;
                    this.smoothCamPitch += deltaY;
                    float partialTicks = Aura.MC.entityRenderer.getConstantUpdatePTicks() - this.smoothCamPartialTicks;
                    this.smoothCamPartialTicks = Aura.MC.entityRenderer.getConstantUpdatePTicks();
                    this.smoothCamFilterX = this.mouseFilterX.smooth(this.smoothCamYaw, 0.05f * gcd);
                    this.smoothCamFilterY = this.mouseFilterY.smooth(this.smoothCamPitch, 0.05f * gcd);
                    deltaX = this.smoothCamFilterX * partialTicks;
                    deltaY = this.smoothCamFilterY * partialTicks;
                    Aura.MC.thePlayer.rotationYaw -= deltaX;
                    Aura.MC.thePlayer.rotationPitch -= deltaY;
                    this.smoothCamFilterY = 0.0f;
                    this.smoothCamFilterX = 0.0f;
                    this.smoothCamPitch = 0.0f;
                    this.smoothCamYaw = 0.0f;
                    this.lastYaw = Aura.MC.thePlayer.rotationYaw;
                    this.lastPitch = Aura.MC.thePlayer.rotationPitch;
                    this.looking = Aura.MC.objectMouseOver.entityHit == this.targetEntity;
                }
            }
        } else {
            this.lastYaw = Aura.MC.thePlayer.rotationYaw;
            this.lastPitch = Aura.MC.thePlayer.rotationPitch;
            this.smoothCamFilterY = 0.0f;
            this.smoothCamFilterX = 0.0f;
            this.smoothCamPitch = 0.0f;
            this.smoothCamYaw = 0.0f;
        }
    }

    @EventTarget(target=EventMotionUpdate.class)
    public void onMotion(EventMotionUpdate e) {
        this.motionUpdate = e;
        if (EntityUtils.skipEntity2(this.targetEntity, this) && (double)Aura.MC.thePlayer.getDistanceToEntity(this.targetEntity) <= Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ROTATION_DISTANCE_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
            this.doRotation(this.targetEntity, e);
        }
    }

    @EventTarget(target=EventJump.class)
    public void onJump(EventJump e) {
        e.setCancelled(LEGACY_VERUS_INFINITE_AURA.equals(Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()));
    }

    @EventTarget(target=EventUpdate.class)
    public void onUpdate(EventUpdate e) {
        if (this.doAuraSetups(e)) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Single": {
                    for (Entity entity1 : Aura.MC.theWorld.getLoadedEntityList()) {
                        EntityLivingBase entity2;
                        if (!(entity1 instanceof EntityLivingBase) || !EntityUtils.skipEntity2(entity2 = (EntityLivingBase)entity1, this) || Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_RAYTRACE, Setting.Type.CHECKBOX).isTicked() && !Aura.MC.thePlayer.canEntityBeSeen(entity2) || !this.timer.elapsed(1000L / this.actualCPS)) continue;
                        this.targetEntity = entity2;
                        this.doAttack(this.targetEntity);
                        this.timer.reset();
                    }
                    break;
                }
                case "Multi": {
                    if (!this.timer.elapsed(1000L / this.actualCPS)) break;
                    Aura.MC.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).forEach(o -> {
                        EntityLivingBase entity = (EntityLivingBase)o;
                        if (EntityUtils.skipEntity2(entity, this) && (!Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_RAYTRACE, Setting.Type.CHECKBOX).isTicked() || Aura.MC.thePlayer.canEntityBeSeen(entity))) {
                            this.targetEntity = entity;
                            this.doAttack(this.targetEntity);
                        }
                    });
                    this.timer.reset();
                    break;
                }
                case "Infinite Aura": {
                    if (!this.timer.elapsed(1000L / this.actualCPS)) break;
                    Aura.MC.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).forEach(o -> {
                        EntityLivingBase entity = (EntityLivingBase)o;
                        if (EntityUtils.skipEntity(entity, this) && (!Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_RAYTRACE, Setting.Type.CHECKBOX).isTicked() || Aura.MC.thePlayer.canEntityBeSeen(entity))) {
                            this.targetEntity = entity;
                            EntityUtils.teleportToEntity(this.targetEntity, false);
                            this.doAttack(this.targetEntity);
                            EntityUtils.teleportBack(this.targetEntity);
                        }
                    });
                    this.timer.reset();
                    break;
                }
                case "Legacy Verus Infinite Aura": {
                    if (!this.timer.elapsed(1000L / this.actualCPS) || !Aura.MC.thePlayer.onGround) break;
                    Aura.MC.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).forEach(o -> {
                        EntityLivingBase entity = (EntityLivingBase)o;
                        if (EntityUtils.skipEntity(entity, this) && (!Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_RAYTRACE, Setting.Type.CHECKBOX).isTicked() || Aura.MC.thePlayer.canEntityBeSeen(entity))) {
                            this.targetEntity = entity;
                            EntityUtils.teleportToEntityGroundOnly(this.targetEntity, false);
                            this.doAttack(this.targetEntity);
                            EntityUtils.teleportBackGroundOnly(this.targetEntity);
                        }
                    });
                    this.timer.reset();
                }
            }
        }
    }

    @EventTarget(target=EventPacket.class)
    public void onPacket(EventPacket e) {
        Packet<INetHandlerPlayServer> packet;
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_NO_KEEP_SPRINT, Setting.Type.CHECKBOX).isTicked()) {
            if (e.getPacket() instanceof C02PacketUseEntity) {
                packet = (C02PacketUseEntity)PacketUtils.getPacket(e.getPacket());
                e.setCancelled(((C02PacketUseEntity)packet).getAction() == C02PacketUseEntity.Action.ATTACK && this.sent && this.ticksSinceSent > 0);
            } else if (e.getPacket() instanceof C0BPacketEntityAction) {
                e.setCancelled(this.sent);
                this.sent = true;
            } else if (e.getPacket() instanceof C03PacketPlayer) {
                this.sent = false;
            }
            this.ticksSinceSent = this.sent ? ++this.ticksSinceSent : 0;
        }
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_POSITION_FIX, Setting.Type.CHECKBOX).isTicked() && Aura.MC.thePlayer.positionUpdateTicks >= 20 && !MovementUtils.isMoving2() && e.getPacket() instanceof C03PacketPlayer) {
            packet = (C03PacketPlayer)PacketUtils.getPacket(e.getPacket());
            ((C03PacketPlayer)packet).setX(Aura.MC.thePlayer.posX);
            ((C03PacketPlayer)packet).setY(Aura.MC.thePlayer.posY);
            ((C03PacketPlayer)packet).setZ(Aura.MC.thePlayer.posZ);
            Aura.MC.thePlayer.positionUpdateTicks = 0;
        }
        if (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo().equals(LEGACY_VERUS_INFINITE_AURA)) {
            if (e.getPacket() instanceof C03PacketPlayer) {
                Aura.MC.thePlayer.sendQueue.packetNoEvent(new C0CPacketInput());
            }
            e.setCancelled(e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion);
        }
    }

    private void doAttack(EntityLivingBase entity) {
        if (entity != null) {
            boolean flag;
            this.doUnblock();
            if ((double)Aura.MC.thePlayer.getDistanceToEntity(entity) <= Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SWING_REACH_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
                Aura.MC.thePlayer.swingItem();
            }
            boolean bl = flag = (double)Aura.MC.thePlayer.getDistanceToEntity(entity) > 3.0 && Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_REACH_WHEN_MOVING, Setting.Type.CHECKBOX).isTicked() && !MovementUtils.isMoving() || Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_REACH_WHEN_OPPONENT_MOVING, Setting.Type.CHECKBOX).isTicked() && !EntityUtils.isOpponentMoving(entity);
            if (!flag) {
                int i = 0;
                while ((double)i < Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ATTACK_COUNT_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
                    if (RngUtils.isChance((int)Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_HIT_CHANCE_VALUE, Setting.Type.SLIDER).getCurrentValue(), 0, 100) && EntityUtils.skipEntity(entity, this) && this.looking) {
                        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_NO_KEEP_SPRINT, Setting.Type.CHECKBOX).isTicked()) {
                            MovementUtils.addFriction(0.6);
                            Aura.MC.thePlayer.setSprinting(false);
                        }
                        Aura.MC.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
                    }
                    ++i;
                }
            }
            this.doAutoBlock();
        }
    }

    private void doRotation(EntityLivingBase entity, EventMotionUpdate e) {
        if (entity != null && (double)Aura.MC.thePlayer.getDistanceToEntity(entity) <= Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ROTATION_DISTANCE_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ROTATIONS_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Basic": {
                    float[] rotations = RotationUtils.doBasicRotations(entity);
                    if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_LOCK_VIEW, Setting.Type.CHECKBOX).isTicked()) {
                        Aura.MC.thePlayer.rotationYaw = rotations[0];
                        Aura.MC.thePlayer.rotationPitch = rotations[1];
                    } else {
                        e.setRotationYaw(rotations[0]);
                        e.setRotationPitch(rotations[1]);
                    }
                    this.looking = true;
                    break;
                }
                case "Bypass I": {
                    float[] rotations = RotationUtils.doBypassIRotations(entity);
                    if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_LOCK_VIEW, Setting.Type.CHECKBOX).isTicked()) {
                        Aura.MC.thePlayer.rotationYaw = rotations[0];
                        Aura.MC.thePlayer.rotationPitch = rotations[1];
                    } else {
                        e.setRotationYaw(rotations[0]);
                        e.setRotationPitch(rotations[1]);
                    }
                    this.looking = true;
                    break;
                }
                case "Rounded": {
                    float[] rotations = RotationUtils.doBasicRotations(entity);
                    if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_LOCK_VIEW, Setting.Type.CHECKBOX).isTicked()) {
                        Aura.MC.thePlayer.rotationYaw = (int)rotations[0];
                        Aura.MC.thePlayer.rotationPitch = (int)rotations[1];
                    } else {
                        e.setRotationYaw((int)rotations[0]);
                        e.setRotationPitch((int)rotations[1]);
                    }
                    this.looking = true;
                    break;
                }
                case "No Pitch": {
                    float[] rotations = RotationUtils.doBasicRotations(entity);
                    if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_LOCK_VIEW, Setting.Type.CHECKBOX).isTicked()) {
                        Aura.MC.thePlayer.rotationYaw = rotations[0];
                    } else {
                        e.setRotationYaw(rotations[0]);
                    }
                    this.looking = true;
                    break;
                }
                case "Tick Round": {
                    float[] rotations = RotationUtils.doBasicRotations(entity);
                    if (Aura.MC.thePlayer.ticksExisted % 3 == 0) {
                        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_LOCK_VIEW, Setting.Type.CHECKBOX).isTicked()) {
                            Aura.MC.thePlayer.rotationYaw = (int)rotations[0];
                            Aura.MC.thePlayer.rotationPitch = (int)rotations[1];
                        } else {
                            e.setRotationYaw((int)rotations[0]);
                            e.setRotationPitch((int)rotations[1]);
                        }
                    }
                    this.looking = true;
                    break;
                }
                case "None": {
                    this.looking = true;
                }
            }
        } else {
            this.looking = entity != null && Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo().equals(NONE);
        }
    }

    private void doAutoBlock() {
        if (Aura.MC.thePlayer.getHeldItem() != null && Aura.MC.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_AUTOBLOCK_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Vanilla": {
                    Aura.MC.gameSettings.keyBindUseItem.setKeyDown(Aura.MC.thePlayer.getHeldItem() != null && Aura.MC.thePlayer.getHeldItem().getItem() instanceof ItemSword);
                    break;
                }
                case "Verus": {
                    Aura.MC.gameSettings.keyBindUseItem.setKeyDown(false);
                    Aura.MC.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, Aura.MC.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                }
            }
            this.blocking = !Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_AUTOBLOCK_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo().equals(NONE);
        }
    }

    private void doUnblock() {
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_UNBLOCK, Setting.Type.CHECKBOX).isTicked() && this.blocking) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_AUTOBLOCK_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Verus": {
                    Aura.MC.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    break;
                }
                case "Vanilla": {
                    Aura.MC.gameSettings.keyBindUseItem.setKeyDown(false);
                }
            }
            this.blocking = false;
        }
    }

    private boolean doAuraSetups(EventUpdate e) {
        double reach = Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo().contains(INFINITE_AURA) ? Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_INFINITE_REACH_VALUE, Setting.Type.SLIDER).getCurrentValue() : Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_REACH_VALUE, Setting.Type.SLIDER).getCurrentValue();
        double min = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MINIMUM_CPS_VALUE, Setting.Type.SLIDER).getCurrentValue();
        double max = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MAXIMUM_CPS_VALUE, Setting.Type.SLIDER).getCurrentValue();
        double minSpike = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MINIMUM_CPS_SPIKE_VALUE, Setting.Type.SLIDER).getCurrentValue();
        double maxSpike = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MAXIMUM_CPS_SPIKE_VALUE, Setting.Type.SLIDER).getCurrentValue();
        if (min > max) {
            Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MAXIMUM_CPS_VALUE, Setting.Type.SLIDER).setCurrentValue(min);
            max = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MAXIMUM_CPS_VALUE, Setting.Type.SLIDER).getCurrentValue();
        }
        this.actualCPS = RandomUtils.nextLong((long)((long)min), (long)((long)max));
        if (minSpike > maxSpike) {
            Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MAXIMUM_CPS_SPIKE_VALUE, Setting.Type.SLIDER).setCurrentValue(minSpike);
            maxSpike = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MAXIMUM_CPS_SPIKE_VALUE, Setting.Type.SLIDER).getCurrentValue();
        }
        this.actualCPS = (long)((double)this.actualCPS + CombatUtils.elevateCPS((int)Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIKE_CHANCE_VALUE, Setting.Type.SLIDER).getCurrentValue() - 1, (long)Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_CPS_SPIKE_DURATION_VALUE, Setting.Type.SLIDER).getCurrentValue(), minSpike, maxSpike));
        if (this.actualCPS <= 1L) {
            this.actualCPS = 1L;
        }
        if (this.targetEntity != null && ((double)Aura.MC.thePlayer.getDistanceToEntity(this.targetEntity) > reach || this.targetEntity.getHealth() <= 0.0f && !Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_DEAD, Setting.Type.CHECKBOX).isTicked())) {
            this.targetEntity = null;
            this.doUnblock();
            this.looking = false;
            this.blocking = false;
        }
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_STATE_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Pre": {
                if (e.getState() == EventUpdate.State.PRE) break;
                return false;
            }
            case "Post": {
                if (e.getState() == EventUpdate.State.POST) break;
                return false;
            }
        }
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_INVENTORY_CHECK, Setting.Type.CHECKBOX).isTicked() && Aura.MC.currentScreen != null) {
            return false;
        }
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_PRIORITY_COMBO_BOX, Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Distance": {
                Aura.MC.theWorld.getLoadedEntityList().sort(Comparator.comparing(entity -> Float.valueOf(Aura.MC.thePlayer.getDistanceToEntity((Entity)entity))));
                break;
            }
        }
        return true;
    }

    public boolean isBlocking() {
        return this.blocking;
    }

    public boolean isLooking() {
        return this.looking;
    }

    public boolean isSent() {
        return this.sent;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public MouseFilter getMouseFilterX() {
        return this.mouseFilterX;
    }

    public MouseFilter getMouseFilterY() {
        return this.mouseFilterY;
    }

    public EventMotionUpdate getMotionUpdate() {
        return this.motionUpdate;
    }

    public int getTicksSinceSent() {
        return this.ticksSinceSent;
    }

    public float getLastYaw() {
        return this.lastYaw;
    }

    public float getLastPitch() {
        return this.lastPitch;
    }

    public float getSmoothCamYaw() {
        return this.smoothCamYaw;
    }

    public float getSmoothCamPitch() {
        return this.smoothCamPitch;
    }

    public float getSmoothCamFilterX() {
        return this.smoothCamFilterX;
    }

    public float getSmoothCamFilterY() {
        return this.smoothCamFilterY;
    }

    public float getSmoothCamPartialTicks() {
        return this.smoothCamPartialTicks;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getReach() {
        return this.reach;
    }

    public long getActualCPS() {
        return this.actualCPS;
    }
}

