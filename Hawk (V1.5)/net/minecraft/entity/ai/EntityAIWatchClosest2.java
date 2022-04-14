package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest {
   private static final String __OBFID = "CL_00001590";

   public EntityAIWatchClosest2(EntityLiving var1, Class var2, float var3, float var4) {
      super(var1, var2, var3, var4);
      this.setMutexBits(3);
   }
}
