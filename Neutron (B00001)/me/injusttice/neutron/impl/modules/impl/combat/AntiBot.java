package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {

    public static List<EntityLivingBase> bots = new ArrayList<>();
    public ModeSet mode = new ModeSet("Mode", "Advanced", new String[] { "Advanced", "Watchdog" });

    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT);
        addSettings(mode);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        switch(mode.getMode()) {
            case "Advanced":
                if(event.getPacket() instanceof S0CPacketSpawnPlayer) {
                    this.setDisplayName("Anti Bot §7Advanced");
                    S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
                    double posX = packet.getX() / 32D;
                    double posY = packet.getY() / 32D;
                    double posZ = packet.getZ() / 32D;

                    double diffX = mc.thePlayer.posX - posX;
                    double diffY = mc.thePlayer.posY - posY;
                    double diffZ = mc.thePlayer.posZ - posZ;

                    double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

                    if (dist <= 17D && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ)
                        event.setCancelled(true);
                }
                break;
        }
    }

    public static boolean isBot(EntityPlayer ep) {
        return bots.contains(ep);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        switch (mode.getMode()) {
            case "Watchdog":
                this.setDisplayName("Anti Bot §7Watchdog");
                for (Object entity : mc.theWorld.loadedEntityList)
                    if (((Entity) entity).isInvisible() && entity != mc.thePlayer)
                        mc.theWorld.removeEntity((Entity) entity);
                break;
        }
    }
}
