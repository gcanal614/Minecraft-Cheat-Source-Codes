package wtf.astronicy.IMPL.utils.entity.impl;

import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.utils.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class DistanceCheck implements ICheck {
   private final DoubleOption distance;

   public DistanceCheck(DoubleOption distance) {
      this.distance = distance;
   }

   public boolean validate(Entity entity) {
      return (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= (Double)this.distance.getValue();
   }
}
