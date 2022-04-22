package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", 0, Category.OTHER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventTarget
    public void onEvent(EventNigger e) {
        this.setDisplayName("Fast Place");
        mc.rightClickDelayTimer = -0;
    }
}