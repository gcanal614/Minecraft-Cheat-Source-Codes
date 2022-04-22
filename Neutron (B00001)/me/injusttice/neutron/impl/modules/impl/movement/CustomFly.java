package me.injusttice.neutron.impl.modules.impl.movement;

import java.util.concurrent.ThreadLocalRandom;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventMove;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.ValueUtil;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class CustomFly extends Module {
    
    final ModuleCategory motionCategory = new ModuleCategory("Motion...");
    private BooleanSet strafeSet = new BooleanSet("Strafe", true);
    private BooleanSet motionSet = new BooleanSet("Enabled", true);
    private ModeSet speedModeSet = new ModeSet("Speed Mode", "Custom", "Custom", "Legit", "OnHurt");
    private DoubleSet speedSet = new DoubleSet("Custom Speed", 1.0D, 0.1D, 5.0D, 0.05D);
    private DoubleSet strafeTicksSet = new DoubleSet("Strafe Ticks", 1.0D, 1.0D, 20.0D, 1.0D);
    private DoubleSet yAxisSpeedSet = new DoubleSet("Vertical Speed", 0.5D, 0.0D, 2.0D, 0.01D);
    private DoubleSet speedPotionMultSet = new DoubleSet("Potion Multiplier", 0.02D, 0.0D, 0.2D, 0.001D);
    final ModuleCategory bypassCategory = new ModuleCategory("Bypass...");
    private ModeSet damageModeSet = new ModeSet("Damage", "None", "None", "Simple", "Incremental", "NoGround");
    private ModeSet bypassModeSet = new ModeSet("Bypass", "None", "None", "Creative", "LagBack");
    private DoubleSet timerSpeedSet = new DoubleSet("Timer Speed", 1.0D, 0.3D, 3.0D, 0.05D);
    private ModeSet spoofModeSet = new ModeSet("Ground Spoof", "Edit", "Edit", "None", "Packet");
    final ModuleCategory otherCategory = new ModuleCategory("Other...");
    private BooleanSet jumpSet = new BooleanSet("Jump", false);
    private BooleanSet stopMotionSet = new BooleanSet("Stop Motion", true);
    private BooleanSet timerOnMoveSet = new BooleanSet("Timer Moving Check", false);
    private BooleanSet lagbackCheckSet = new BooleanSet("LagBack Check", false);
    private BooleanSet groundCheckSet = new BooleanSet("Ground Check", false);
    int hopsCount;
    boolean done;
    boolean stopMotionTotally;

    public CustomFly() {
        super("CustomFly", 0, Category.MOVEMENT);
        hopsCount = 0;
        done = false;
        stopMotionTotally = false;
        addSettings(motionCategory, bypassCategory, otherCategory);
        motionCategory.addCatSettings(strafeSet, motionSet, speedModeSet, speedSet, strafeTicksSet, yAxisSpeedSet, speedPotionMultSet);
        bypassCategory.addCatSettings(damageModeSet, bypassModeSet, timerSpeedSet, spoofModeSet);
        otherCategory.addCatSettings(jumpSet, stopMotionSet, timerOnMoveSet, lagbackCheckSet, groundCheckSet);
    }

    @Override
    public void onEnable() {
        int a;
        super.onEnable();
        if (groundCheckSet.isEnabled() && mc.thePlayer.onGround) {
            NeutronMain.addChatMessage("Please toggle this on ground");
            toggle();
            return;
        }
        done = false;
        hopsCount = 0;
        switch (damageModeSet.getMode()) {
            case "Simple":
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.035D, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                break;
            case "Incremental":
                for (a = 0; a < 65; a++)
                    PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + (a / 20.0F), mc.thePlayer.posZ, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                break;
        }
        if (jumpSet.isEnabled() && !damageModeSet.getMode().equalsIgnoreCase("NoGround"))
            mc.thePlayer.jump();
    }

    public void onDisable() {
        super.onDisable();
        mc.timer.setTimerSpeed(1.0F);
        mc.thePlayer.capabilities.isFlying = false;
        if (stopMotionSet.isEnabled()) {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
        }
    }

    @EventTarget
    public void onGet(EventReceivePacket e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook && lagbackCheckSet.isEnabled()) {
            NeutronMain.addChatMessage("You got lagged back, disabling fly to prevent flags..");
            toggle();
        }
    }

    @EventTarget
    public void onPre(EventMotion e) {
        PlayerCapabilities pc;
        setDisplayName("Custom Fly");
        stopMotionTotally = false;
        if (damageModeSet.getMode().equalsIgnoreCase("NoGround") && hopsCount < 4) {
            stopMotionTotally = true;
            if (mc.thePlayer.onGround) {
                hopsCount++;
                mc.thePlayer.jump();
            }
            e.setOnGround(false);
            return;
        }
        if (timerOnMoveSet.isEnabled()) {
            if (MovementUtils.isMoving()) {
                mc.timer.setTimerSpeed((float)timerSpeedSet.getValue());
            } else {
                mc.timer.setTimerSpeed(1.0F);
            }
        } else {
            mc.timer.setTimerSpeed((float)timerSpeedSet.getValue());
        }
        if (motionSet.isEnabled()) {
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = yAxisSpeedSet.getValue();
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -yAxisSpeedSet.getValue();
            } else {
                mc.thePlayer.motionY = 0.0D;
            }
        } else {
            mc.thePlayer.capabilities.isFlying = true;
        }
        if (strafeSet.isEnabled())
            if (MovementUtils.isMoving()) {
                if (mc.thePlayer.ticksExisted % strafeTicksSet.getValue() == 0.0D)
                    switch (speedModeSet.getMode()) {
                        case "Custom":
                            MovementUtils.setSpeed1((float) ValueUtil.getMotion(speedSet.getValue(), speedPotionMultSet.getValue()));
                            break;
                        case "Legit":
                            MovementUtils.setSpeed1(MovementUtils.getSpeed());
                            break;
                        case "OnHurt":
                            mc.thePlayer.motionX = 0.0D;
                            mc.thePlayer.motionZ = 0.0D;
                            if (mc.thePlayer.hurtTime > 0) {
                                MovementUtils.setSpeed1((float)ValueUtil.getMotion(speedSet.getValue(), speedPotionMultSet.getValue()));
                                break;
                            }
                            MovementUtils.setSpeed1(0.263F);
                            break;
                    }
            } else if (stopMotionSet.isEnabled()) {
                mc.thePlayer.motionX = 0.0D;
                mc.thePlayer.motionZ = 0.0D;
            }
        switch (spoofModeSet.getMode()) {
            case "Edit":
                e.setOnGround(true);
                break;
            case "Packet":
                PacketUtil.sendPacketSilent(new C03PacketPlayer(true));
                break;
        }
        switch (bypassModeSet.getMode()) {
            case "Creative":
                pc = new PlayerCapabilities();
                pc.isFlying = true;
                pc.isCreativeMode = true;
                PacketUtil.sendPacketSilent(new C13PacketPlayerAbilities(pc));
                break;
            case "LagBack":
                if (mc.thePlayer.ticksExisted % 25 == 0)
                    PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(0.0D, ThreadLocalRandom.current().nextDouble(-3.2141313E7D, -3.6171231E7D), 0.0D, false));
                break;
        }
    }

    @EventTarget
    public void onMove(EventMove e) {
        if (stopMotionTotally) {
            e.setX(0.0D);
            e.setZ(0.0D);
        }
    }
}
