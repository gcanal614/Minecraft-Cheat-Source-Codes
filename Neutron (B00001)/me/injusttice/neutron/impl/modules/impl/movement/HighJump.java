package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;

public class HighJump extends Module {

    public DoubleSet multi;
    public ModeSet mode;

    public HighJump() {
        super("HighJump", 0, Category.MOVEMENT);
        mode = new ModeSet("Mode", "Vanilla","Vanilla");
        multi = new DoubleSet("Multiplier", 1.0, 0.8, 1.5, 0.1);
        addSettings(mode, multi);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (mode.is("Vanilla") && mc.thePlayer.onGround) {
            mc.thePlayer.motionY = multi.getValue();
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0f;
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        switch (mode.getMode()) {
            case "Vanilla":
                if (e.isPre() && mc.thePlayer.onGround) {
                    toggle();
                }
                break;
        }
    }
}
