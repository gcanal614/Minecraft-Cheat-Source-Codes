// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.EnumProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "High Jump", moduleCategory = ModuleCategory.MOVEMENT)
public class HighJump extends Module
{
    private final EnumProperty mode;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    public static boolean falling;
    public static boolean boosting;
    public double yEnable;
    
    public HighJump() {
        this.mode = new EnumProperty("Mode", (T)HighJumpEnum.VIPER, this);
        this.onModuleEnabled = (() -> {
            HighJump.boosting = true;
            HighJump.falling = false;
            this.yEnable = HighJump.mc.thePlayer.posY;
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (this.mode.getPropertyValue().equals(HighJumpEnum.VIPER)) {
                e.setOnGround(true);
                if (HighJump.boosting && !HighJump.falling) {
                    HighJump.mc.thePlayer.motionY = 2.0;
                }
                if (this.yEnable <= 10.0) {
                    this.toggleModule();
                }
                if (HighJump.mc.thePlayer.onGround) {
                    this.toggleModule();
                }
            }
            return;
        });
        this.onModuleDisabled = (() -> {
            HighJump.falling = false;
            HighJump.boosting = true;
        });
    }
    
    private enum HighJumpEnum
    {
        VIPER("Viper");
        
        private final String name;
        
        private HighJumpEnum(final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
}
