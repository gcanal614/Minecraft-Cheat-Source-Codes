package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipeRepairItem implements IRecipe {
   private static final String __OBFID = "CL_00002156";

   public ItemStack[] func_179532_b(InventoryCrafting var1) {
      ItemStack[] var2 = new ItemStack[var1.getSizeInventory()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ItemStack var4 = var1.getStackInSlot(var3);
         if (var4 != null && var4.getItem().hasContainerItem()) {
            var2[var3] = new ItemStack(var4.getItem().getContainerItem());
         }
      }

      return var2;
   }

   public int getRecipeSize() {
      return 4;
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      ArrayList var2 = Lists.newArrayList();

      ItemStack var3;
      for(int var4 = 0; var4 < var1.getSizeInventory(); ++var4) {
         var3 = var1.getStackInSlot(var4);
         if (var3 != null) {
            var2.add(var3);
            if (var2.size() > 1) {
               ItemStack var5 = (ItemStack)var2.get(0);
               if (var3.getItem() != var5.getItem() || var5.stackSize != 1 || var3.stackSize != 1 || !var5.getItem().isDamageable()) {
                  return null;
               }
            }
         }
      }

      if (var2.size() == 2) {
         ItemStack var10 = (ItemStack)var2.get(0);
         var3 = (ItemStack)var2.get(1);
         if (var10.getItem() == var3.getItem() && var10.stackSize == 1 && var3.stackSize == 1 && var10.getItem().isDamageable()) {
            Item var11 = var10.getItem();
            int var6 = var11.getMaxDamage() - var10.getItemDamage();
            int var7 = var11.getMaxDamage() - var3.getItemDamage();
            int var8 = var6 + var7 + var11.getMaxDamage() * 5 / 100;
            int var9 = var11.getMaxDamage() - var8;
            if (var9 < 0) {
               var9 = 0;
            }

            return new ItemStack(var10.getItem(), 1, var9);
         }
      }

      return null;
   }

   public boolean matches(InventoryCrafting var1, World var2) {
      ArrayList var3 = Lists.newArrayList();

      for(int var4 = 0; var4 < var1.getSizeInventory(); ++var4) {
         ItemStack var5 = var1.getStackInSlot(var4);
         if (var5 != null) {
            var3.add(var5);
            if (var3.size() > 1) {
               ItemStack var6 = (ItemStack)var3.get(0);
               if (var5.getItem() != var6.getItem() || var6.stackSize != 1 || var5.stackSize != 1 || !var6.getItem().isDamageable()) {
                  return false;
               }
            }
         }
      }

      if (var3.size() == 2) {
         return true;
      } else {
         return false;
      }
   }

   public ItemStack getRecipeOutput() {
      return null;
   }
}
