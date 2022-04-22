package wtf.astronicy.API.events.render;

import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;

public final class Render3DEvent extends Cancellable implements Event {
   private final float partialTicks;

   public Render3DEvent(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }
}
