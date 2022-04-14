package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDigging extends Enchantment {
   private static final String __OBFID = "CL_00000104";

   public int getMaxLevel() {
      return 5;
   }

   public int getMaxEnchantability(int var1) {
      return super.getMinEnchantability(var1) + 50;
   }

   protected EnchantmentDigging(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.DIGGER);
      this.setName("digging");
   }

   public boolean canApply(ItemStack var1) {
      return var1.getItem() == Items.shears ? true : super.canApply(var1);
   }

   public int getMinEnchantability(int var1) {
      return 1 + 10 * (var1 - 1);
   }
}
