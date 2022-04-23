// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import net.minecraft.network.play.server.S2EPacketCloseWindow;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "No GUI Close", moduleCategory = ModuleCategory.WORLD)
public class NoGUIClose extends Module
{
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketRecieveEvent;
    
    public NoGUIClose() {
        this.onPacketRecieveEvent = (e -> {
            if (e.getPacket() instanceof S2EPacketCloseWindow) {
                e.setCancelled(true);
            }
        });
    }
}
