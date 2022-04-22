package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventHurt;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;

public class NoHurtCam extends Module {

    public NoHurtCam() {
        super("NoHurtCam", 0, Category.VISUAL);
    }

    @EventTarget
    public void onEvent(EventUpdate e) {
        this.setDisplayName("No Hurt Cam");
    }

    @EventTarget
    public void Hurtcam(EventHurt event) {
        event.setCancelled(true);
    }
}
