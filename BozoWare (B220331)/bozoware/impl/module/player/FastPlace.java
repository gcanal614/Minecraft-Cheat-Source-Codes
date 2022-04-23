// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Fast Place", moduleCategory = ModuleCategory.PLAYER)
public class FastPlace extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public FastPlace() {
        this.onUpdatePositionEvent = (e -> FastPlace.mc.rightClickDelayTimer = 0);
        this.onModuleDisabled = (() -> FastPlace.mc.rightClickDelayTimer = 6);
    }
}
