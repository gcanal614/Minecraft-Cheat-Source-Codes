// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import bozoware.impl.module.combat.Aura;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Hit Sounds", moduleCategory = ModuleCategory.PLAYER)
public class HitSounds extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public HitSounds() {
        double x;
        double y;
        double z;
        this.onUpdatePositionEvent = (e -> {
            if (Aura.target != null && Aura.target.hurtTime > 9) {
                x = Aura.target.posX;
                y = Aura.target.posY;
                z = Aura.target.posZ;
                HitSounds.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("assets/minecraft/BozoWare/Sounds/skeet.ogg"), (float)x, (float)y, (float)z));
            }
        });
    }
}
