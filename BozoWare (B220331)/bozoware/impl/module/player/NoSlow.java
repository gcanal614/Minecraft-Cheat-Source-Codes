// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import bozoware.impl.property.EnumProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "NoSlow", moduleCategory = ModuleCategory.PLAYER)
public class NoSlow extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    final EnumProperty<Modes> mode;
    
    public NoSlow() {
        this.mode = new EnumProperty<Modes>("Mode", Modes.NCP, this);
        this.setModuleSuffix(this.mode.getPropertyValue().toString());
        this.onUpdatePositionEvent = (updatePositionEvent -> {
            if (this.mode.getPropertyValue().equals(Modes.NCP) && NoSlow.mc.thePlayer.isBlocking() && (NoSlow.mc.thePlayer.motionX != 0.0 || NoSlow.mc.thePlayer.motionZ != 0.0)) {
                if (!updatePositionEvent.isPre()) {
                    if (!updatePositionEvent.isPre()) {
                        NoSlow.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(NoSlow.mc.thePlayer.inventory.getCurrentItem()));
                    }
                }
            }
            return;
        });
        this.mode.onValueChange = (() -> this.setModuleSuffix(this.mode.getPropertyValue().name()));
    }
    
    public enum Modes
    {
        Vanilla, 
        NCP;
    }
}
