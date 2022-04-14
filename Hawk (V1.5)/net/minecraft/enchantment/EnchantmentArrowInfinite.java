package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowInfinite extends Enchantment {
   private static final String __OBFID = "CL_00000100";

   public int getMaxLevel() {
      return 1;
   }

   public int getMinEnchantability(int var1) {
      return 20;
   }

   public EnchantmentArrowInfinite(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.BOW);
      this.setName("arrowInfinite");
   }

   public int getMaxEnchantability(int var1) {
      return 50;
   }
}
