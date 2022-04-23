// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.util.Wrapper;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.world.WorldTimeChangeEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Time Changer", moduleCategory = ModuleCategory.VISUAL)
public class TimeChanger extends Module
{
    @EventListener
    EventConsumer<WorldTimeChangeEvent> onWorldTimeChangeEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Long> timeProperty;
    
    public TimeChanger() {
        this.timeProperty = new ValueProperty<Long>("Hour", 20000L, 0L, 20000L, this);
        this.onWorldTimeChangeEvent = (worldTimeChangeEvent -> worldTimeChangeEvent.setCancelled(true));
        this.onPacketReceiveEvent = (packetReceiveEvent -> {
            if (packetReceiveEvent.getPacket() instanceof S03PacketTimeUpdate) {
                packetReceiveEvent.setCancelled(true);
            }
            return;
        });
        this.onUpdatePositionEvent = (updatePositionEvent -> Wrapper.getWorld().setWorldTime(this.timeProperty.getPropertyValue()));
    }
}
