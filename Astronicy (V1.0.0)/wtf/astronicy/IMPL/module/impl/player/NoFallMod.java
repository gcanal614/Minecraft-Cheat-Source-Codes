package wtf.astronicy.IMPL.module.impl.player;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.packet.SendPacketEvent;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.movement.Fly;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

@ModName("No Fall")
@Category(ModuleCategory.PLAYER)
@Aliases({"nofall"})
public final class NoFallMod extends Module {
   public final EnumOption mode;

   public NoFallMod() {
      this.mode = new EnumOption("Mode", NoFallMod.Mode.HYPIXEL);
      this.setMode(this.mode);
      this.addOptions(new Option[]{this.mode});
   }

   @Listener(SendPacketEvent.class)
   public final void onSendPacket(SendPacketEvent event) {
      if (this.mode.getValue() == NoFallMod.Mode.TEST && event.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance > 2.0F && !Fly.getInstance().isEnabled()) {
         C03PacketPlayer packetPlayer = (C03PacketPlayer)event.getPacket();
         if (!packetPlayer.isMoving()) {
            packetPlayer.onGround = true;
         } else {
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
         }
      }

   }

   @Listener(MotionUpdateEvent.class)
   public final void onMotionUpdate(MotionUpdateEvent event) {
      if (event.isPre()) {
         switch((NoFallMod.Mode)this.mode.getValue()) {
         case HYPIXEL:
            if (mc.thePlayer.fallDistance > 3.0F && this.isBlockUnder() && !Fly.getInstance().isEnabled() && (mc.thePlayer.posY % 0.0625D != 0.0D || mc.thePlayer.posY % 0.015256D != 0.0D)) {
               mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
               mc.thePlayer.fallDistance = 0.0F;
            }
            break;
         case GROUND:
            if (mc.thePlayer.fallDistance > 3.0F && !Fly.getInstance().isEnabled()) {
               event.setOnGround(true);
            }
		default:
			break;
         }
      } else if (this.mode.getValue() == NoFallMod.Mode.HYPIXEL && mc.thePlayer.fallDistance > 3.0F && this.isBlockUnder() && !Fly.getInstance().isEnabled() && (mc.thePlayer.posY % 0.0625D != 0.0D || mc.thePlayer.posY % 0.015256D != 0.0D)) {
         mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
         mc.thePlayer.fallDistance = 0.0F;
      }

   }

   private boolean isBlockUnder() {
      for(int i = (int)(mc.thePlayer.posY - 1.0D); i > 0; --i) {
         BlockPos pos = new BlockPos(mc.thePlayer.posX, (double)i, mc.thePlayer.posZ);
         if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
            return true;
         }
      }

      return false;
   }

   public static enum Mode {
      HYPIXEL,
      GROUND,
      TEST;
   }
}
