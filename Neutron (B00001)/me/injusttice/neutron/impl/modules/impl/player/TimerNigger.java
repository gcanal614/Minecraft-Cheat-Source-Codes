package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.api.events.impl.UpdateEvent;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;

public class TimerNigger extends Module {

    public DoubleSet timerSpeed = new DoubleSet("Delay", 1.0, 0.1, 20.0, 0.1D);

    public TimerNigger(){
        super("Timer", 0, Category.PLAYER);
        addSettings(timerSpeed);
    }

    @EventTarget
    public void onEvent(EventNigger e) {
        if(e instanceof UpdateEvent) {
            mc.timer.timerSpeed = (float) timerSpeed.getValue();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0f;
    }
}
