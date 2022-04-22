package wtf.astronicy.IMPL.module.impl.movement;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.API.events.player.UseItemEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.combat.AuraMod;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.utils.PlayerUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModName("No Slow")
@Category(ModuleCategory.MOVEMENT)
@Aliases({"noslow", "noslowdown"})
public final class NoSlowMod extends Module {
   public final BoolOption ncp = new BoolOption("NCP", true);

   public NoSlowMod() {
      this.addOptions(new Option[]{this.ncp});
   }

   @Listener(UseItemEvent.class)
   public final void onUseItem(UseItemEvent event) {
      event.setCancelled();
   }

   @Listener(MotionUpdateEvent.class)
   public final void onMotionUpdate(MotionUpdateEvent event) {
      if (this.ncp.getValue() && this.isBlocking() && mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
         if (event.isPre()) {
            mc.playerController.syncCurrentPlayItem();
            mc.getNetHandler().addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         } else {
            mc.playerController.syncCurrentPlayItem();
            mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
         }
      }

   }

   private boolean isBlocking() {
      return PlayerUtils.isHoldingSword() && mc.thePlayer.isBlocking() && !AuraMod.isBlocking;
   }
}
