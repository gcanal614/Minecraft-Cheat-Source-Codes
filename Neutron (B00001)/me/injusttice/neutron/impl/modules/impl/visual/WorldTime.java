package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.events.impl.EventTick;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class WorldTime extends Module {

    public DoubleSet time = new DoubleSet("Time", 18.0D, 0.0D, 24.0D, 0.5D);

    public WorldTime() {
        super("WorldTime", 0, Category.VISUAL);
        addSettings(time);
    }

    @EventTarget
    public void onUpdate(EventTick e) {
        setDisplayName("World Time §7" + Math.round(time.getValue()));
        mc.theWorld.setWorldTime((long)time.getValue() * 1000L);
    }

    @EventTarget
    public void onGet(EventReceivePacket e) {
        if (e.getPacket() instanceof S03PacketTimeUpdate)
            e.setCancelled(true);
    }
}