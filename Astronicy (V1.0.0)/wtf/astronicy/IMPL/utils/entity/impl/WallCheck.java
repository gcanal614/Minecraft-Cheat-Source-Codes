package wtf.astronicy.IMPL.utils.entity.impl;

import wtf.astronicy.IMPL.utils.entity.ICheck;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class WallCheck implements ICheck {
   public boolean validate(Entity entity) {
      return Minecraft.getMinecraft().thePlayer.canEntityBeSeen(entity);
   }
}
