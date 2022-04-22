package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;

public class FullBright extends Module {

    private float oldBrightness;

    public FullBright(){
        super("FullBright", 0, Category.VISUAL);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        this.setDisplayName("Full Bright");
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onEnable(){
        super.onEnable();
        oldBrightness = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable(){
        super.onDisable();
        mc.gameSettings.gammaSetting = oldBrightness;
    }
}
