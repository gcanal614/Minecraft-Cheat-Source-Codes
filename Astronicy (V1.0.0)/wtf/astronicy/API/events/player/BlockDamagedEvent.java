package wtf.astronicy.API.events.player;

import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;
import net.minecraft.util.BlockPos;

public final class BlockDamagedEvent extends Cancellable implements Event {
   private final BlockPos blockPos;

   public BlockDamagedEvent(BlockPos blockPos) {
      this.blockPos = blockPos;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }
}
