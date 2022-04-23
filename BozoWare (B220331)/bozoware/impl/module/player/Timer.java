// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Timer", moduleCategory = ModuleCategory.PLAYER)
public class Timer extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Float> speed;
    private final BooleanProperty tickBool;
    
    public Timer() {
        this.speed = new ValueProperty<Float>("Timer Speed", 2.0f, 0.1f, 10.0f, this);
        this.tickBool = new BooleanProperty("Tick", true, this);
        this.onUpdatePositionEvent = (e -> {
            if (!this.tickBool.getPropertyValue()) {
                Timer.mc.timer.timerSpeed = this.speed.getPropertyValue();
            }
            else if (Timer.mc.thePlayer.ticksExisted % 2 == 0) {
                Timer.mc.timer.timerSpeed = 1.0f;
            }
            else {
                Timer.mc.timer.timerSpeed = this.speed.getPropertyValue();
            }
            return;
        });
        this.onModuleDisabled = (() -> Timer.mc.timer.timerSpeed = 1.0f);
    }
}
