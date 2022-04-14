package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private Entity indirectEntity;
   private static final String __OBFID = "CL_00001523";

   public IChatComponent getDeathMessage(EntityLivingBase var1) {
      IChatComponent var2 = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
      ItemStack var3 = this.indirectEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
      String var4 = String.valueOf((new StringBuilder("death.attack.")).append(this.damageType));
      String var5 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(".item"));
      return var3 != null && var3.hasDisplayName() && StatCollector.canTranslate(var5) ? new ChatComponentTranslation(var5, new Object[]{var1.getDisplayName(), var2, var3.getChatComponent()}) : new ChatComponentTranslation(var4, new Object[]{var1.getDisplayName(), var2});
   }

   public Entity getSourceOfDamage() {
      return this.damageSourceEntity;
   }

   public Entity getEntity() {
      return this.indirectEntity;
   }

   public EntityDamageSourceIndirect(String var1, Entity var2, Entity var3) {
      super(var1, var2);
      this.indirectEntity = var3;
   }
}
