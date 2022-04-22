package wtf.astronicy.API.events.render;

import wtf.astronicy.API.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class RenderGuiEvent implements Event {
   private final ScaledResolution scaledResolution;
   private final float partialTicks;

   public RenderGuiEvent(ScaledResolution scaledResolution, float partialTicks) {
      this.scaledResolution = scaledResolution;
      this.partialTicks = partialTicks;
   }

   public ScaledResolution getScaledResolution() {
      return this.scaledResolution;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }
}
