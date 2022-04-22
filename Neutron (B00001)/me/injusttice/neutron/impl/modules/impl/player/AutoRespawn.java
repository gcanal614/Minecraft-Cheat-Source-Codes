package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;

public class AutoRespawn extends Module {

    public AutoRespawn(){
        super("AutoRespawn", 0, Category.PLAYER);
    }

    @EventTarget
    public void onPre(EventUpdate e){
        this.setDisplayName("Auto Respawn");
        if(!mc.thePlayer.isEntityAlive()) {
            mc.thePlayer.respawnPlayer();
        }
    }
}
