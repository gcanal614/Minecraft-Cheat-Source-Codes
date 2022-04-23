// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Lightning Detector", moduleCategory = ModuleCategory.WORLD)
public class LightningDetector extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public LightningDetector() {
        this.onUpdatePositionEvent = (e -> {});
    }
}
