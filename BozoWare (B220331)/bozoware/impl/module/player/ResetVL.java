// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.client.entity.EntityPlayerSP;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "ResetVL", moduleCategory = ModuleCategory.PLAYER)
public class ResetVL extends Module
{
    int jumps;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public ResetVL() {
        this.jumps = 0;
        final EntityPlayerSP thePlayer;
        final EntityPlayerSP thePlayer2;
        this.onUpdatePositionEvent = (e -> {
            MovementUtil.setSpeed(0.0);
            thePlayer = ResetVL.mc.thePlayer;
            thePlayer.posY -= ResetVL.mc.thePlayer.posY - ResetVL.mc.thePlayer.lastTickPosY;
            thePlayer2 = ResetVL.mc.thePlayer;
            thePlayer2.lastTickPosY -= ResetVL.mc.thePlayer.posY - ResetVL.mc.thePlayer.lastTickPosY;
            ResetVL.mc.timer.timerSpeed = 2.0f;
            if (ResetVL.mc.thePlayer.onGround) {
                ++this.jumps;
                ResetVL.mc.thePlayer.jump();
            }
            if (this.jumps == 5) {
                ResetVL.mc.timer.timerSpeed = 1.0f;
                this.jumps = 0;
                this.toggleModule();
            }
        });
    }
}
