package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMove;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.player.ValueUtil;

public class CustomSpeed extends Module {
    
    public ModuleCategory jumpCategory = new ModuleCategory("Jump...");
    public ModuleCategory speedCategory = new ModuleCategory("Speed...");
    public ModuleCategory otherCategory = new ModuleCategory("Other...");
    private DoubleSet timerSpeedSet = new DoubleSet("Timer Speed", 1.0D, 0.3D, 3.0D, 0.05D);
    private ModeSet jumpModeSet = new ModeSet("Mode", "Custom", "Custom", "Legit");
    private BooleanSet autoJumpSet = new BooleanSet("Auto", true);
    private DoubleSet customJumpHeightSet = new DoubleSet("Height", 0.42D, 0.06D, 1.3D, 0.01D);
    private ModeSet speedModeSet = new ModeSet("Mode", "Custom", "Custom", "Legit", "Ground", "None");
    private DoubleSet groundSpeedSet = new DoubleSet("Ground Multiplier", 1.5D, 0.0D, 3.0D, 0.01D);
    private DoubleSet speedSet = new DoubleSet("Speed", 0.31D, 0.1D, 2.0D, 0.001D);
    private DoubleSet speedPotionMultSet = new DoubleSet("Potion Multiplier", 0.02D, 0.0D, 0.2D, 0.001D);
    private DoubleSet strafeTicksSet = new DoubleSet("Ticks", 1.0D, 1.0D, 20.0D, 1.0D);
    private ModeSet airFrictionMode = new ModeSet("Friction", "Normal", "None", "Low", "Normal", "High");
    private BooleanSet stopMotionSet = new BooleanSet("Stop Motion", true);
    double dec;
    double speed;

    public CustomSpeed() {
        super("CustomSpeed", 0, Category.MOVEMENT);
        dec = 0.0D;
        speed = 0.0D;
        addSettings(timerSpeedSet, jumpCategory, speedCategory, otherCategory );
        jumpCategory.addCatSettings(jumpModeSet, autoJumpSet, customJumpHeightSet );
        speedCategory.addCatSettings(speedModeSet, groundSpeedSet, speedSet, speedPotionMultSet, strafeTicksSet );
        otherCategory.addCatSettings(airFrictionMode, stopMotionSet );
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.setTimerSpeed(1.0F);
    }

    @EventTarget
    public void onMove(EventMove e) {
        mc.timer.setTimerSpeed((float) timerSpeedSet.getValue());
        switch (airFrictionMode.getMode()) {
            case "Low":
                dec += 0.0021D;
                break;
            case "Normal":
                dec += 0.0101D;
                break;
            case "High":
                dec += 0.02314D;
                break;
            case "None":
                dec = 0.0D;
                break;
        }
        if (mc.thePlayer.onGround) {
            dec = 0.0D;
            speed = speedSet.getValue() * groundSpeedSet.getValue();
            if (autoJumpSet.isEnabled())
                switch (jumpModeSet.getMode()) {
                    case "Custom":
                        e.setY(ValueUtil.getModifiedMotionY((float) customJumpHeightSet.getValue()));
                        break;
                    case "Legit":
                        e.setY(ValueUtil.getBaseMotionY());
                        break;
                }
        } else {
            speed = speedSet.getValue();
        }
        speed -= dec;
        if (mc.thePlayer.ticksExisted % strafeTicksSet.getValue() == 0.0D || mc.thePlayer.onGround)
            if (MovementUtils.isMoving()) {
                switch (speedModeSet.getMode()) {
                    case "Custom":
                        MovementUtils.setSpeed1((float) ValueUtil.getMotion(speed, speedPotionMultSet.getValue()));
                        break;
                    case "Legit":
                        MovementUtils.setSpeed1(MovementUtils.getSpeed());
                        break;
                    case "Ground":
                        if (mc.thePlayer.onGround)
                            MovementUtils.setSpeed1((float) ValueUtil.getMotion(speed, speedPotionMultSet.getValue()));
                        break;
                }
            } else if (stopMotionSet.isEnabled()) {
                mc.thePlayer.motionX = 0.0D;
                mc.thePlayer.motionZ = 0.0D;
            }
    }
}
