package wtf.astronicy.IMPL.module.impl.movement;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.player.MoveEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.world.Scaffold.Scaffold;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import net.minecraft.client.entity.EntityPlayerSP;

@ModName("Sprint")
@Category(ModuleCategory.MOVEMENT)
public final class SprintMod extends Module {
   @Listener(MoveEvent.class)
   public final void onMove() {
      EntityPlayerSP player = mc.thePlayer;
      Scaffold scaff = new Scaffold();
      if(scaff.isEnabled()) return;
      if (player.isMoving() && player.getFoodStats().getFoodLevel() > 6) {
         player.setSprinting(true);
      }

   }
}
