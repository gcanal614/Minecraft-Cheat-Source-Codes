// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import bozoware.base.util.player.MovementUtil;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Test", moduleCategory = ModuleCategory.WORLD)
public class Test extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    public boolean Jumped;
    public boolean Clipped;
    
    public Test() {
        this.onModuleEnabled = (() -> {
            this.Jumped = false;
            this.Clipped = false;
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (e.isPre) {
                if (!this.Jumped) {
                    Test.mc.thePlayer.motionY = 0.05000000074505806;
                    this.Jumped = true;
                }
                if (!this.Clipped && Test.mc.thePlayer.onGround) {
                    e.setY(e.getY() - 0.2150000035762787);
                    this.Clipped = true;
                }
                if (this.Clipped) {
                    Test.mc.thePlayer.motionY = 0.0;
                    MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed() * 0.94);
                }
            }
        });
    }
}
