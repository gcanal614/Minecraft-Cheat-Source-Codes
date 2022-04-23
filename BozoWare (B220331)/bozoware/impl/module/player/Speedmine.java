// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Speed Mine", moduleCategory = ModuleCategory.PLAYER)
public class Speedmine extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public Speedmine() {
        this.onModuleDisabled = (() -> Speedmine.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId()));
        boolean item;
        EntityPlayerSP thePlayer;
        final PotionEffect potioneffectIn;
        this.onUpdatePositionEvent = (e -> {
            if (e.isPre) {
                Speedmine.mc.playerController.blockHitDelay = 0;
                item = (Speedmine.mc.thePlayer.getCurrentEquippedItem() == null);
                thePlayer = Speedmine.mc.thePlayer;
                new PotionEffect(Potion.digSpeed.getId(), 100, item ? 1 : 0);
                thePlayer.addPotionEffect(potioneffectIn);
            }
        });
    }
}
