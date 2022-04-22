package wtf.astronicy.API.events.render;

import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class RenderCrosshairEvent extends Cancellable implements Event {
   private final ScaledResolution sr;

   public RenderCrosshairEvent(ScaledResolution sr) {
      this.sr = sr;
   }

   public ScaledResolution getScaledRes() {
      return this.sr;
   }
}
