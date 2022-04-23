// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import java.util.Iterator;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.entity.player.EntityPlayer;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Teleport", moduleCategory = ModuleCategory.PLAYER)
public class Teleport extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePosEvent;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    boolean hasTeleported;
    TimerUtil timer;
    
    public Teleport() {
        this.hasTeleported = false;
        this.timer = new TimerUtil();
        this.onModuleEnabled = (() -> {
            this.timer.reset();
            this.hasTeleported = false;
            return;
        });
        final String dansName;
        EntityPlayer danPlayer;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer loadedPlayers;
        final double x;
        final double y;
        final double z;
        this.onUpdatePosEvent = (e -> {
            dansName = "OGJediMaster";
            danPlayer = null;
            Teleport.mc.theWorld.playerEntities.iterator();
            while (iterator.hasNext()) {
                loadedPlayers = iterator.next();
                if (loadedPlayers.getName().equalsIgnoreCase(dansName)) {
                    danPlayer = loadedPlayers;
                }
            }
            x = 0.0;
            y = 0.0;
            z = 0.0;
            if (danPlayer != null && this.timer.hasReached(1050L) && !this.hasTeleported) {
                Teleport.mc.thePlayer.setPosition(danPlayer.posX, danPlayer.posY, danPlayer.posZ);
                this.hasTeleported = true;
                this.toggleModule();
            }
            return;
        });
        this.onPacketSendEvent = (e -> {
            if (e.getPacket() instanceof C03PacketPlayer) {
                e.setCancelled(true);
            }
        });
    }
}
