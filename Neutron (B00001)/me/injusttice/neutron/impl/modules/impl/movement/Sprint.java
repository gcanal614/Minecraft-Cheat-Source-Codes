package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;

public class Sprint extends Module {

    public Sprint(){
        super("Sprint", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally)
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable(){
        super.onDisable();
        mc.thePlayer.setSprinting(false);
    }
}
