package wtf.astronicy.IMPL.module.impl.visuals;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.game.TickEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModName("Full Bright")
@Category(ModuleCategory.VISUALS)
@Aliases({"fullbright", "brightness"})
public final class FullBrightMod extends Module {
   @Listener(TickEvent.class)
   public final void onTick() {
      mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 1000000, 2));
   }

   public void onDisabled() {
      mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
   }
}
