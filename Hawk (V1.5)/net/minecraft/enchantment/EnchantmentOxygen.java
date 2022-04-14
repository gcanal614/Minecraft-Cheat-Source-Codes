package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentOxygen extends Enchantment {
   private static final String __OBFID = "CL_00000120";

   public int getMinEnchantability(int var1) {
      return 10 * var1;
   }

   public int getMaxLevel() {
      return 3;
   }

   public int getMaxEnchantability(int var1) {
      return this.getMinEnchantability(var1) + 30;
   }

   public EnchantmentOxygen(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.ARMOR_HEAD);
      this.setName("oxygen");
   }
}
