// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import bozoware.base.util.Wrapper;
import bozoware.impl.module.player.BlockFly;
import bozoware.base.BozoWare;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.PlayerMoveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Sprint", moduleCategory = ModuleCategory.MOVEMENT)
public class Sprint extends Module
{
    private final BooleanProperty omniBool;
    @EventListener
    EventConsumer<PlayerMoveEvent> playerMoveEvent;
    
    public Sprint() {
        this.omniBool = new BooleanProperty("Omni-Sprint", true, this);
        this.setModuleBind(0);
        this.onModuleEnabled = (() -> {});
        this.playerMoveEvent = (updatePositionEvent -> {
            if (!BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled()) {
                if (!this.omniBool.getPropertyValue()) {
                    if (Wrapper.getPlayer().isMovingForward() && (Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6 || Wrapper.getPlayer().capabilities.isCreativeMode) && !Sprint.mc.thePlayer.isCollidedHorizontally) {
                        Sprint.mc.thePlayer.setSprinting(true);
                    }
                }
                else if (Wrapper.getPlayer().isMoving() && (Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6 || Wrapper.getPlayer().capabilities.isCreativeMode) && !Sprint.mc.thePlayer.isCollidedHorizontally) {
                    Sprint.mc.thePlayer.setSprinting(true);
                }
            }
        });
    }
}
