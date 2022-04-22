package me.injusttice.neutron.impl.modules.impl.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventPostMotion;
import me.injusttice.neutron.api.events.impl.EventRender2D;
import me.injusttice.neutron.api.events.impl.EventRender3D;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.Targets;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.Setting;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.impl.modules.impl.other.AutoHypixel;
import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.movement.RotationUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.TimerHelper;
import me.injusttice.neutron.utils.render.ColorUtil;
import me.injusttice.neutron.utils.render.Render2DUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class KillAura extends Module {
    
    public static Entity closestTarget;

    public ModeSet killAuraMode = new ModeSet("Mode", "Single", "Single", "Switch", "NCP Multi", "Verus Multi", "Vanilla Multi");
    public BooleanSet moveFix = new BooleanSet("Strafe Fix", false);
    public DoubleSet apsMaxSet = new DoubleSet("Max Speed", 10.0D, 1.0D, 20.0D, 0.05D, "APS");
    public DoubleSet apsMinSet = new DoubleSet("Min Speed", 5.0D, 1.0D, 20.0D, 0.05D, "APS");
    public DoubleSet hitChance = new DoubleSet("Hit Chance", 100.0D, 0.0D, 100.0D, 1.0D, "%");
    public DoubleSet attackRange = new DoubleSet("Range", 3.4D, 3.0D, 7.0D, 0.1D) {
        public void onChange() {
            if (KillAura.this.attackRange.getValue() > KillAura.this.blockRange.getValue())
                KillAura.this.blockRange.setValue(KillAura.this.attackRange.getValue() + 0.1D);
            KillAura.this.blockRange.setMin(KillAura.this.attackRange.getValue());
        }
    };
    public DoubleSet blockRange = new DoubleSet("Block Range", 3.5D, 3.0D, 9.0D, 0.1D) {
        public void onChange() {
            if (KillAura.this.attackRange.getValue() > KillAura.this.blockRange.getValue())
                setValue(KillAura.this.attackRange.getValue() + 0.1D);
            setMin(KillAura.this.attackRange.getValue());
        }
    };
    public DoubleSet switchDelay = new DoubleSet("Switch Delay", 500.0D, 60.0D, 1000.0D, 10.0D, "ms");
    public ModeSet attackState = new ModeSet("Attack State", "Pre", "Pre", "Post", "Double");
    public ModeSet autoBlockSet = new ModeSet("AutoBlock", "Watchdog", "Watchdog", "UnBlock", "Fake", "Vanilla", "Post", "None", "Reverse", "Tick", "Interact", "Verus");
    public ModuleCategory rotationsCategory = new ModuleCategory("Rotations...");
    public ModeSet rotationsMode = new ModeSet("Rots Mode", "Verus", "Verus", "Random", "Disabled", "EyeHeight", "Real Y", "LastTick", "UniversoCraft", "Dev2");
    public BooleanSet realRotate = new BooleanSet("Ray Trace", true);
    public DoubleSet smoothnessSet = new DoubleSet("Smoothness", 35.0D, 0.0D, 100.0D, 1.0D);
    public ModeSet renderRotationsMode = new ModeSet("Render Rots", "Eye", "Eye", "Disabled");
    public ModuleCategory targetsCategory = new ModuleCategory("Targets...");
    public BooleanSet attackPlayers = new BooleanSet("Players", true);
    public BooleanSet attackMobs = new BooleanSet("Mobs", true);
    public BooleanSet attackAnimals = new BooleanSet("Animals", false);
    public BooleanSet attackVillagers = new BooleanSet("Villagers", false);
    public BooleanSet attackInvis = new BooleanSet("Invisibles", false);
    public BooleanSet attackTargets = new BooleanSet("Targets", true);
    public BooleanSet attackFriends = new BooleanSet("Friends", false);
    public ModuleCategory checksCategory = new ModuleCategory("Checks...");
    public BooleanSet teamsEnabled = new BooleanSet("Teams", true);
    public BooleanSet botCheck = new BooleanSet("NPC Check", true);
    public DoubleSet ticksExistedSet = new DoubleSet("Ticks Existed", 10.0D, 0.0D, 100.0D, 1.0D);
    public DoubleSet hurtTimeCheckSet = new DoubleSet("Hurt Time", 10.0D, 0.0D, 10.0D, 1.0D);
    public BooleanSet inventoryCheckSet = new BooleanSet("Inventory", false);
    public BooleanSet onlyCritsSet = new BooleanSet("Only Falling", false);
    public ModuleCategory otherCategory = new ModuleCategory("Other...");
    public ModeSet swingModeSet = new ModeSet("Swing", "Client", "Client", "Silent", "Disabled", "Spam");
    public BooleanSet keepSprintSet = new BooleanSet("KeepSprint", false);
    public BooleanSet swingOnBlockRangeSet = new BooleanSet("Block-Range Swing", true);
    public BooleanSet slowDownSet = new BooleanSet("Slowdown", false);
    public DoubleSet slowDownFactor = new DoubleSet("Slowdown Factor", 0.95D, 0.5D, 0.99D, 0.005D);
    public BooleanSet packetUnSprintSet = new BooleanSet("Packet UnSprint", false);
    public BooleanSet noDelaySet = new BooleanSet("NoDelay", false);
    public ModuleCategory hudCategory = new ModuleCategory("Visual...");
    public BooleanSet hudEnabled = new BooleanSet("Enabled", true);
    public ModeSet hudMode = new ModeSet("Mode", "Neutron Old", "Neutron Old", "Neutron");
    public DoubleSet targetHudOppacity = new DoubleSet("HUD Oppacity", 121.0D, 0.0D, 255.0D, 1.0D);
    public ModeSet particleModeSet = new ModeSet("Particles", "Sharp", "Sharp", "Crit", "Both", "None");
    public DoubleSet particleSet = new DoubleSet("ParticleMult", 1.0D, 0.0D, 5.0D, 1.0D);
    public float pYaw = 0.0F;
    public float pPitch = 0.0F;

    public Setting[] settings = new Setting[] {
            killAuraMode, moveFix, apsMinSet, apsMaxSet, hitChance, switchDelay, rotationsCategory, targetsCategory, checksCategory, hudCategory,
            otherCategory, attackRange, blockRange, attackState, autoBlockSet };

    List<Entity> entitiesToDraw;
    public static Entity target;
    TimerHelper timer;
    TimerHelper swingTimer;
    TimerHelper switchTimer;
    float[] serversideRotations;
    double doggoUpAndDown;
    int doggoStatus;
    double progressVal;
    boolean isProgressing;
    double leftForHealth;
    double left;
    double right;
    double realRight;
    double lastRight;
    boolean drawDone;
    int switchState;
    boolean attacking;
    boolean isBlocking;
    boolean verusBlocking;
    float oldYaw;
    float oldPitch;
    public float checkYaw;
    public float checkPitch;

    public KillAura() {
        super("KillAura", 0, Category.COMBAT);
        entitiesToDraw = new ArrayList<>();
        target = null;
        timer = new TimerHelper();
        swingTimer = new TimerHelper();
        switchTimer = new TimerHelper();
        doggoUpAndDown = 0.0D;
        doggoStatus = 0;
        progressVal = 0.0D;
        isProgressing = false;
        leftForHealth = 0.0D;
        left = 0.0D;
        right = 0.0D;
        realRight = 0.0D;
        lastRight = 0.0D;
        drawDone = false;
        switchState = 0;
        attacking = false;
        isBlocking = false;
        verusBlocking = false;
        oldYaw = 0.0F;
        oldPitch = 0.0F;
        addSettings(settings);
        targetsCategory.addCatSettings(attackPlayers, attackMobs, attackVillagers, attackAnimals, attackInvis, attackTargets, attackFriends);
        rotationsCategory.addCatSettings(rotationsMode, realRotate, renderRotationsMode, smoothnessSet);
        hudCategory.addCatSettings(hudEnabled, hudMode, targetHudOppacity, particleModeSet, particleSet);
        checksCategory.addCatSettings(teamsEnabled, botCheck, ticksExistedSet, hurtTimeCheckSet, inventoryCheckSet, onlyCritsSet);
        otherCategory.addCatSettings(swingModeSet, swingOnBlockRangeSet, slowDownSet, slowDownFactor, packetUnSprintSet, keepSprintSet, noDelaySet);
    }

    public void onEnable() {
        super.onEnable();
        drawDone = false;
        progressVal = left;
    }

    public void onDisable() {
        super.onDisable();
        progressVal = leftForHealth;
        mc.gameSettings.keyBindUseItem.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindUseItem);
        if (autoBlockSet.is("Tick") &&
                isBlocking)
            PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        if (autoBlockSet.is("Verus") &&
                verusBlocking)
            PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }

    @EventTarget
    public void onPre(EventMotion e) {
        if (swingOnBlockRangeSet.isEnabled() &&
                !getTargets(blockRange.getValue()).isEmpty() && getTargets(attackRange.getValue()).isEmpty() &&
                swingTimer.timeElapsed(getAPSSpeed())) {
            swingArm();
            swingTimer.reset();
        }
        setDisplayName("Kill Aura §7" + killAuraMode.getMode());
        if (inventoryCheckSet.isEnabled())
            if (mc.currentScreen != null)
                return;
        List<Entity> targetList = getTargets(blockRange.getValue());
        entitiesToDraw = targetList;
        if (targetList.isEmpty()) {
            target = null;
            closestTarget = null;
        } else {
            double doX, doZ, minX, maxX, minZ, maxZ, rdY, rX, rZ, doX2, doZ2, minX2, maxX2, minZ2, maxZ2, rdY2, rX2, rZ2;
            long delay = (long)switchDelay.getValue();
            if (killAuraMode.getMode().equalsIgnoreCase("Verus Multi"))
                delay = 20L;
            if (killAuraMode.getMode().equalsIgnoreCase("NCP Multi"))
                delay = 120L;
            if (switchTimer.timeElapsed(delay)) {
                if (switchState > targetList.size()) {
                    switchState = 0;
                } else {
                    switchState++;
                }
                switchTimer.reset();
            }
            switch (killAuraMode.getMode()) {
                case "NCP Multi":
                case "Verus Multi":
                case "Switch":
                    if (switchState >= targetList.size())
                        switchState = 0;
                    closestTarget = targetList.get(switchState);
                    target = targetList.get(switchState);
                    break;
                default:
                    closestTarget = targetList.get(0);
                    target = targetList.get(0);
                    break;
            }
            switch (rotationsMode.getMode()) {
                case "Dev2":
                    doX = target.lastTickPosX + target.motionX;
                    doZ = target.lastTickPosZ + target.motionZ;
                    minX = doX - 0.2D;
                    maxX = doX + 0.2D;
                    minZ = doZ - 0.2D;
                    maxZ = doZ + 0.2D;
                    rdY = target.lastTickPosY + target.motionY + 0.6D;
                    rX = ThreadLocalRandom.current().nextDouble(minX, maxX);
                    rZ = ThreadLocalRandom.current().nextDouble(minZ, maxZ);
                    serversideRotations = RotationUtils.getRotationsWithDir(rX, rdY, rZ, 15.2F, 24.2F, oldYaw, oldPitch);
                    e.setYaw(serversideRotations[0]);
                    e.setPitch(serversideRotations[1]);
                    oldYaw = serversideRotations[0];
                    oldPitch = serversideRotations[1];
                    break;
                case "Verus":
                    if (doggoUpAndDown == 0.7D || doggoUpAndDown > 0.7D)
                        doggoStatus = 1;
                    if (doggoUpAndDown == -0.7D || doggoUpAndDown < -0.7D)
                        doggoStatus = 0;
                    if (doggoStatus == 0)
                        doggoUpAndDown += 0.12D;
                    if (doggoStatus == 1)
                        doggoUpAndDown -= 0.12D;
                    this
                            .serversideRotations = RotationUtils.getRotsByPos(target.posX +
                            ThreadLocalRandom.current().nextDouble(0.1D, 0.6D), target.posY + 0.92D + doggoUpAndDown, target.posZ -

                            ThreadLocalRandom.current().nextDouble(0.1D, 0.6D));
                    e.setYaw(serversideRotations[0]);
                    e.setPitch(serversideRotations[1]);
                    break;
                case "Random":
                    doX2 = target.lastTickPosX + target.motionX;
                    doZ2 = target.lastTickPosZ + target.motionZ;
                    minX2 = doX2 - 0.2D;
                    maxX2 = doX2 + 0.2D;
                    minZ2 = doZ2 - 0.2D;
                    maxZ2 = doZ2 + 0.2D;
                    rdY2 = target.lastTickPosY + target.motionY + 0.6D;
                    rX2 = ThreadLocalRandom.current().nextDouble(minX2, maxX2);
                    rZ2 = ThreadLocalRandom.current().nextDouble(minZ2, maxZ2);
                    serversideRotations = RotationUtils.getRotationsWithDir(rX2, rdY2, rZ2,

                            ThreadLocalRandom.current().nextInt(0, 45), 24.2F, oldYaw, oldPitch);
                    e.setYaw(serversideRotations[0]);
                    e.setPitch(serversideRotations[1]);
                    oldYaw = serversideRotations[0];
                    oldPitch = serversideRotations[1];
                    break;
                case "EyeHeight":
                    serversideRotations = RotationUtils.getRotsByPos(target.posX, target.posY, target.posZ);
                    e.setYaw(serversideRotations[0]);
                    e.setPitch(serversideRotations[1]);
                    break;
                case "Real Y":
                    serversideRotations = RotationUtils.getRotsByPos(target.posX, target.posY, target.posZ);
                    e.setYaw(serversideRotations[0]);
                    e.setPitch(serversideRotations[1]);
                    break;
                case "LastTick":
                    serversideRotations = RotationUtils.getRotsByPos(target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ);
                    e.setYaw(serversideRotations[0]);
                    e.setPitch(serversideRotations[1]);
                    break;
                case "UniversoCraft":
                    if (doggoUpAndDown == 0.7D || doggoUpAndDown > 0.7D)
                        doggoStatus = 1;
                    if (doggoUpAndDown == -0.7D || doggoUpAndDown < -0.7D)
                        doggoStatus = 0;
                    if (doggoStatus == 0)
                        doggoUpAndDown += 0.12D;
                    if (doggoStatus == 1)
                        doggoUpAndDown -= 0.12D;
                    serversideRotations = RotationUtils.getRotsByPos(target.lastTickPosX, target.posY + 0.92D + doggoUpAndDown, target.lastTickPosZ);
                    e.setYaw((float)(serversideRotations[0] + ThreadLocalRandom.current().nextDouble(-10.0D, 10.0D)));
                    e.setPitch(Math.max(Math.min(serversideRotations[1], 90.0F), -90.0F));
                    break;
            }
            mc.thePlayer.renderYawOffset = e.getYaw();
            mc.thePlayer.rotationYawHead = e.getYaw();
            mc.thePlayer.prevRenderYawOffset = e.getYaw();
        }
        if (!getTargets(blockRange.getValue()).isEmpty() &&
                mc.thePlayer.getCurrentEquippedItem() != null) {
            switch (autoBlockSet.getMode()) {
                case "Vanilla":
                    mc.gameSettings.keyBindUseItem.pressed = true;
                    break;
                case "Interact":
                case "UnBlock":
                case "Watchdog":
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                    break;
                case "Reverse":
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                    PacketUtil.sendPacketSilent(new C08PacketPlayerBlockPlacement(new BlockPos((new Random())
                            .nextInt(), (new Random()).nextInt(), (new Random()).nextInt()), 255, mc.thePlayer.inventory
                            .getCurrentItem(), 0.0F, 0.0F, 0.0F));
                    break;
            }
            boolean verusAllowAttack = (!verusBlocking || !autoBlockSet.is("Verus"));
            if ((attackState.is("Pre") || attackState.is("Double")) && !targetList.isEmpty() && verusAllowAttack) {
                Entity attackingTarget = target;
                if ((noDelaySet.isEnabled() || timer.timeElapsed(getAPSSpeed())) && mc.thePlayer.getDistanceToEntity(attackingTarget) < attackRange.getValue()) {
                    if (killAuraMode.is("Vanilla Multi")) {
                        for (Entity e1 : targetList)
                            attackEntity(e1);
                    } else {
                        attackEntity(attackingTarget);
                    }
                    timer.reset();
                }
            }
        }
        pYaw = e.getYaw();
        pPitch = e.getPitch();
    }

    @EventTarget
    public void onPost(EventPostMotion e) {
        if (inventoryCheckSet.isEnabled())
            if (mc.currentScreen != null)
                return;
        boolean verusAllowAttack = (!verusBlocking || !autoBlockSet.is("Verus"));
        if ((attackState.is("Post") || attackState.is("Double")) &&
                target != null && verusAllowAttack) {
            Entity attackingTarget = target;
            if ((noDelaySet.isEnabled() || timer.timeElapsed(getAPSSpeed())) && mc.thePlayer.getDistanceToEntity(attackingTarget) < attackRange.getValue()) {
                if (killAuraMode.is("Vanilla Multi")) {
                    for (Entity e1 : getTargets(blockRange.getValue()))
                        attackEntity(e1);
                } else {
                    attackEntity(attackingTarget);
                }
                timer.reset();
            }
        }
        if (!getTargets(blockRange.getValue()).isEmpty() &&
                mc.thePlayer .getCurrentEquippedItem() != null) {
            switch (autoBlockSet.getMode()) {
                case "Verus":
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                        verusBlocking = true;
                        break;
                    }
                    verusBlocking = false;
                    break;
                case "Interact":
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                    if (closestTarget != null) {
                        PacketUtil.sendPacket(new C02PacketUseEntity(closestTarget, RotationUtils.getVectorToEntity(closestTarget)));
                        PacketUtil.sendPacket(new C02PacketUseEntity(closestTarget, C02PacketUseEntity.Action.INTERACT));
                    }
                    PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-0.5534147541D, -0.5534147541D, -0.5534147541D), 255,

                            (Minecraft.getMinecraft()).thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                    break;
                case "Watchdog":
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
                    break;
                case "UnBlock":
                case "Post":
                    mc.playerController.sendUseItem((EntityPlayer)mc.thePlayer, (World)mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                    break;
                case "Reverse":
                    PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    break;
                case "Tick":
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        if (!isBlocking)
                            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
                        isBlocking = true;
                        break;
                    }
                    if (isBlocking)
                        PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    isBlocking = false;
                    break;
            }
        } else {
            verusBlocking = false;
            isBlocking = false;
        }
    }

    @EventTarget
    public void onDraw(EventRender2D e) {
        double barTop;
        double left2, barTop2, b;
        int a;
        ScaledResolution sr;
        float y, x;
        int color, xHealthbar, yHealthbar;
        float add;
        double addX;
        int index;
        double startY;
        left = (e.getWidth() / 2.0F - 70.0F);
        double otherLeft = left;
        if (target == null) {
            progressVal = otherLeft;
            isProgressing = false;
            return;
        }
        EntityLivingBase target2 = (EntityLivingBase)target;
        Color healthCol = new Color(-1);
        if (target2.getHealth() > 18.0F) {
            healthCol = new Color(6487914);
        } else if (target2.getHealth() > 16.0F) {
            healthCol = new Color(8716130);
        } else if (target2.getHealth() > 14.0F) {
            healthCol = new Color(11992930);
        } else if (target2.getHealth() > 12.0F) {
            healthCol = new Color(14876514);
        } else if (target2.getHealth() > 10.0F) {
            healthCol = new Color(16771682);
        } else if (target2.getHealth() > 8.0F) {
            healthCol = new Color(16757602);
        } else if (target2.getHealth() > 6.0F) {
            healthCol = new Color(16751189);
        } else if (target2.getHealth() > 4.0F) {
            healthCol = new Color(16738645);
        } else if (target2.getHealth() > 2.0F) {
            healthCol = new Color(16730698);
        } else {
            healthCol = new Color(16724273);
        }
        if (!isProgressing) {
            progressVal = otherLeft;
            isProgressing = true;
        } else {
            progressVal += 3.0D;
        }
        lastRight = left + (target2.getHealth() * 7.0F);
        if (!drawDone) {
            right = left + (target2.getHealth() * 7.0F);
            drawDone = true;
        }
        if (right < lastRight) {
            right = lastRight;
        } else {
            right -= 0.5D;
        }
        realRight = Math.min(progressVal, Math.max(lastRight, right));
        double difference = 4.0D;
        double staticRight = left + 140.0D;
        if (!hudEnabled.isEnabled())
            return;
        switch (hudMode.getMode()) {
            case "Neutron":
                y = e.getHeight() / 2.0F + 35.0F;
                x = e.getWidth() / 2.0F - 77.5F;
                xHealthbar = 30;
                yHealthbar = 25;
                add = 120.0F;
                addX = (x + xHealthbar + target2.getHealth() / target2.getMaxHealth() * add);
                color = (new Color(16734296)).getRGB();
                drawRectB(x - 1.0F, y + 2.0F, 155.0F, 35.0F, new Color(-1459157241, true));
                Fonts.astolfoArray.drawStringWithShadow(target.getName(), x + 31.0F, y + 6.0F, -1);
                Fonts.astolfoArray.drawStringWithShadow("Hurt Time: " + ((EntityLivingBase) target).hurtTime, x + 31.0F, y + 16.0F, -1);
                GL11.glPopMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GuiInventory.drawEntityOnScreenWithoutName((int)x + 16, (int)y + 35, 16, target.rotationYaw, -target.rotationPitch, target2);
                drawRectB(x + xHealthbar, y + yHealthbar, add, 8.0F, healthCol.darker().darker().darker());
                drawRectB(x + xHealthbar, y + yHealthbar, target2.getHealth() / target2.getMaxHealth() * add, 8.0F, healthCol);
                Fonts.astolfoArray.drawStringWithShadow((Math.round((target2.getHealth() / 2.0F) * 10.0D) / 10.0D) + "", (float)(addX - 16.0D), y + yHealthbar + 1.5f, -1);
                break;
            case "Neutron Old":
                if(target instanceof EntityPlayer) {
                    ScaledResolution s1r = new ScaledResolution(mc);
                    double hpPercentage = target2.getHealth() / target2.getMaxHealth();
                    float scaledWidth = s1r.getScaledWidth();
                    float scaledHeight = s1r.getScaledHeight();
                    EntityPlayer player = (EntityPlayer) target;
                    if (hpPercentage > 1)
                        hpPercentage = 1;
                    else if (hpPercentage < 0)
                        hpPercentage = 0;
                    NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
                    Render2DUtils.drawRect2(scaledWidth / 2 - 200, scaledHeight / 2 - 42, scaledWidth / 2 - 200 + 40 + (mc.fontRendererObj.getStringWidth(player.getName()) > 105 ? mc.fontRendererObj.getStringWidth(player.getName()) - 10 : 105), scaledHeight / 2 - 2, new Color(0, 0, 0, 150).getRGB());
                    Render2DUtils.drawFace((int) scaledWidth / 2 - 196, (int) (scaledHeight / 2 - 38), 8, 8, 8, 8, 32, 32, 64, 64, (AbstractClientPlayer) player);
                    mc.fontRendererObj.drawStringWithShadow(player.getName(), scaledWidth / 2 - 196 + 40, scaledHeight / 2 - 36, -1);
                    Render2DUtils.drawRect2(scaledWidth / 2 - 196 + 40, scaledHeight / 2 - 26, (float) (scaledWidth / 2 - 196 + 40 + (70 * 1.25)), scaledHeight / 2 - 14, new Color(0, 0, 0).getRGB());
                    Render2DUtils.drawRect2(scaledWidth / 2 - 196 + 40, scaledHeight / 2 - 26, (float) (scaledWidth / 2 - 196 + 40 + (hpPercentage * 1.25) * 70), scaledHeight / 2 - 14, ColorUtil.getHealthColor(player).getRGB());
                    mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", player.getHealth()), scaledWidth / 2 - 196 + 40 + 36, scaledHeight / 2 - 23, ColorUtil.getHealthColor(player).getRGB());
                    mc.fontRendererObj.drawStringWithShadow("Ping: \2477" + (networkPlayerInfo == null ? "0ms" : networkPlayerInfo.responseTime + "ms"), scaledWidth / 2 - 196 + 40, scaledHeight / 2 - 12, -1);
                    //mc.fontRendererObj.drawStringWithShadow("Distance: \2477" + (int) mc.thePlayer.getDistanceToEntity(player) + "m", scaledWidth / 2 - 196 + 40, scaledHeight / 2 - 12, -1);
                }
                break;
        }
    }

    @EventTarget
    public void onRender(EventRender3D e) {
        checkYaw = pYaw;
        checkPitch = pPitch;
        if (target == null)
            return;
        if (inventoryCheckSet.isEnabled())
            if (mc.currentScreen != null)
                return;
        if (renderRotationsMode.is("Eye")) {
            double posX = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks;
            double posY = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks;
            double posZ = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks;
            float[] serverRots = RotationUtils.getRotsByPos(posX, posY/* + target.getEyeHeight()*/, posZ);
            mc.thePlayer.renderYawOffset = pYaw;
            mc.thePlayer.rotationYawHead = pYaw;
            mc.thePlayer.prevRenderYawOffset = pYaw;
        }
    }

    public void drawRectB(float x, float y, float w, float h, Color color) {
        Gui.drawRect(x, y, (x + w), (y + h), color.getRGB());
    }

    public void attackEntity(Entity e) {
        if (!realRotate.isEnabled() ||
                mc.objectMouseOver.entityHit == e) {
            if (e.ticksExisted < ticksExistedSet.getValue())
                return;
            if (e instanceof EntityLivingBase) {
                EntityLivingBase castedEnt = (EntityLivingBase)e;
                if (castedEnt.hurtTime != 0 && castedEnt.hurtTime >= hurtTimeCheckSet.getValue())
                    return;
            }
            switch (swingModeSet.getMode()) {
                case "Client":
                    mc.thePlayer.swingItem();
                    break;
                case "Silent":
                    PacketUtil.sendPacket(new C0APacketAnimation());
                    break;
                case "Spam":
                    mc.thePlayer.swingItem();
                    PacketUtil.sendPacketSilent(new C0APacketAnimation());
                    break;
            }
            if (getRNG((int)hitChance.getValue())) {
                if (!keepSprintSet.isEnabled()) {
                    mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, e);
                } else {
                    PacketUtil.sendPacket(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
                }
                for (int a = 0; a < particleSet.getValue(); a++) {
                    switch (particleModeSet.getMode()) {
                        case "Sharp":
                            mc.thePlayer.onEnchantmentCritical(e);
                            break;
                        case "Crit":
                            mc.thePlayer.onCriticalHit(e);
                            break;
                        case "Both":
                            mc.thePlayer.onCriticalHit(e);
                            mc.thePlayer.onEnchantmentCritical(e);
                            break;
                    }
                }
            }
            return;
        }
    }

    public void swingArm() {
        mc.thePlayer.swingItem();
    }

    public boolean getRNG(int chance) {
        int random = ThreadLocalRandom.current().nextInt(0, 100);
        return (random < chance);
    }

    public List<Entity> getTargets(double range) {
        return Targets.getKillAuraTargets(range, attackPlayers.isEnabled(), attackVillagers
                .isEnabled(), attackMobs.isEnabled(), attackInvis
                .isEnabled(), attackAnimals.isEnabled(), attackFriends
                .isEnabled(), attackTargets.isEnabled(), teamsEnabled.isEnabled());
    }

    public long getAPSSpeed() {
        double maxSpeed = apsMaxSet.getValue();
        double minSpeed = apsMinSet.getValue();
        if (minSpeed >= maxSpeed)
            maxSpeed = Math.max(maxSpeed, minSpeed) + 0.15D;
        return (long)(1000.0D / Math.max(0.0D, ThreadLocalRandom.current().nextDouble(minSpeed, maxSpeed)));
    }

    public static KillAura instance(){
        return ModuleManager.getModule(KillAura.class);
    }

}
