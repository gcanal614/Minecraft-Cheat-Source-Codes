package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public interface IMerchant {
   void useRecipe(MerchantRecipe var1);

   MerchantRecipeList getRecipes(EntityPlayer var1);

   void setCustomer(EntityPlayer var1);

   void verifySellingItem(ItemStack var1);

   EntityPlayer getCustomer();

   void setRecipes(MerchantRecipeList var1);

   IChatComponent getDisplayName();
}
