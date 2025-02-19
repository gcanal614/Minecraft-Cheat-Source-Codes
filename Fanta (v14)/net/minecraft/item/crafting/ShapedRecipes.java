/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ShapedRecipes
implements IRecipe {
    private final int recipeWidth;
    private final int recipeHeight;
    private final ItemStack[] recipeItems;
    private final ItemStack recipeOutput;
    private boolean copyIngredientNBT;

    public ShapedRecipes(int width, int height, ItemStack[] p_i1917_3_, ItemStack output) {
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.recipeItems = p_i1917_3_;
        this.recipeOutput = output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
        int i = 0;
        while (i < aitemstack.length) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (itemstack != null && itemstack.getItem().hasContainerItem()) {
                aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
            }
            ++i;
        }
        return aitemstack;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int i = 0;
        while (i <= 3 - this.recipeWidth) {
            int j = 0;
            while (j <= 3 - this.recipeHeight) {
                if (this.checkMatch(inv, i, j, true)) {
                    return true;
                }
                if (this.checkMatch(inv, i, j, false)) {
                    return true;
                }
                ++j;
            }
            ++i;
        }
        return false;
    }

    private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
        int i = 0;
        while (i < 3) {
            int j = 0;
            while (j < 3) {
                ItemStack itemstack1;
                int k = i - p_77573_2_;
                int l = j - p_77573_3_;
                ItemStack itemstack = null;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    itemstack = p_77573_4_ ? this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth] : this.recipeItems[k + l * this.recipeWidth];
                }
                if ((itemstack1 = p_77573_1_.getStackInRowAndColumn(i, j)) != null || itemstack != null) {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                        return false;
                    }
                    if (itemstack.getItem() != itemstack1.getItem()) {
                        return false;
                    }
                    if (itemstack.getMetadata() != Short.MAX_VALUE && itemstack.getMetadata() != itemstack1.getMetadata()) {
                        return false;
                    }
                }
                ++j;
            }
            ++i;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack itemstack = this.getRecipeOutput().copy();
        if (this.copyIngredientNBT) {
            int i = 0;
            while (i < inv.getSizeInventory()) {
                ItemStack itemstack1 = inv.getStackInSlot(i);
                if (itemstack1 != null && itemstack1.hasTagCompound()) {
                    itemstack.setTagCompound((NBTTagCompound)itemstack1.getTagCompound().copy());
                }
                ++i;
            }
        }
        return itemstack;
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
}

