/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

public class RecipesBanners {
    void addRecipes(CraftingManager p_179534_1_) {
        EnumDyeColor[] enumDyeColorArray = EnumDyeColor.values();
        int n = enumDyeColorArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumDyeColor enumdyecolor = enumDyeColorArray[n2];
            p_179534_1_.addRecipe(new ItemStack(Items.banner, 1, enumdyecolor.getDyeDamage()), "###", "###", " | ", Character.valueOf('#'), new ItemStack(Blocks.wool, 1, enumdyecolor.getMetadata()), Character.valueOf('|'), Items.stick);
            ++n2;
        }
        p_179534_1_.addRecipe(new RecipeDuplicatePattern());
        p_179534_1_.addRecipe(new RecipeAddPattern());
    }

    static class RecipeAddPattern
    implements IRecipe {
        private RecipeAddPattern() {
        }

        @Override
        public boolean matches(InventoryCrafting inv, World worldIn) {
            boolean flag = false;
            int i = 0;
            while (i < inv.getSizeInventory()) {
                ItemStack itemstack = inv.getStackInSlot(i);
                if (itemstack != null && itemstack.getItem() == Items.banner) {
                    if (flag) {
                        return false;
                    }
                    if (TileEntityBanner.getPatterns(itemstack) >= 6) {
                        return false;
                    }
                    flag = true;
                }
                ++i;
            }
            if (!flag) {
                return false;
            }
            return this.func_179533_c(inv) != null;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inv) {
            ItemStack itemstack = null;
            int i = 0;
            while (i < inv.getSizeInventory()) {
                ItemStack itemstack1 = inv.getStackInSlot(i);
                if (itemstack1 != null && itemstack1.getItem() == Items.banner) {
                    itemstack = itemstack1.copy();
                    itemstack.stackSize = 1;
                    break;
                }
                ++i;
            }
            TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = this.func_179533_c(inv);
            if (tileentitybanner$enumbannerpattern != null) {
                int k = 0;
                int j = 0;
                while (j < inv.getSizeInventory()) {
                    ItemStack itemstack2 = inv.getStackInSlot(j);
                    if (itemstack2 != null && itemstack2.getItem() == Items.dye) {
                        k = itemstack2.getMetadata();
                        break;
                    }
                    ++j;
                }
                NBTTagCompound nbttagcompound1 = itemstack.getSubCompound("BlockEntityTag", true);
                NBTTagList nbttaglist = null;
                if (nbttagcompound1.hasKey("Patterns", 9)) {
                    nbttaglist = nbttagcompound1.getTagList("Patterns", 10);
                } else {
                    nbttaglist = new NBTTagList();
                    nbttagcompound1.setTag("Patterns", nbttaglist);
                }
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setString("Pattern", tileentitybanner$enumbannerpattern.getPatternID());
                nbttagcompound.setInteger("Color", k);
                nbttaglist.appendTag(nbttagcompound);
            }
            return itemstack;
        }

        @Override
        public int getRecipeSize() {
            return 10;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
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

        private TileEntityBanner.EnumBannerPattern func_179533_c(InventoryCrafting p_179533_1_) {
            TileEntityBanner.EnumBannerPattern[] enumBannerPatternArray = TileEntityBanner.EnumBannerPattern.values();
            int n = enumBannerPatternArray.length;
            int n2 = 0;
            while (n2 < n) {
                TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = enumBannerPatternArray[n2];
                if (tileentitybanner$enumbannerpattern.hasValidCrafting()) {
                    boolean flag = true;
                    if (tileentitybanner$enumbannerpattern.hasCraftingStack()) {
                        boolean flag1 = false;
                        boolean flag2 = false;
                        int i = 0;
                        while (i < p_179533_1_.getSizeInventory() && flag) {
                            ItemStack itemstack = p_179533_1_.getStackInSlot(i);
                            if (itemstack != null && itemstack.getItem() != Items.banner) {
                                if (itemstack.getItem() == Items.dye) {
                                    if (flag2) {
                                        flag = false;
                                        break;
                                    }
                                    flag2 = true;
                                } else {
                                    if (flag1 || !itemstack.isItemEqual(tileentitybanner$enumbannerpattern.getCraftingStack())) {
                                        flag = false;
                                        break;
                                    }
                                    flag1 = true;
                                }
                            }
                            ++i;
                        }
                        if (!flag1) {
                            flag = false;
                        }
                    } else if (p_179533_1_.getSizeInventory() == tileentitybanner$enumbannerpattern.getCraftingLayers().length * tileentitybanner$enumbannerpattern.getCraftingLayers()[0].length()) {
                        int j = -1;
                        int k = 0;
                        while (k < p_179533_1_.getSizeInventory() && flag) {
                            int l = k / 3;
                            int i1 = k % 3;
                            ItemStack itemstack1 = p_179533_1_.getStackInSlot(k);
                            if (itemstack1 != null && itemstack1.getItem() != Items.banner) {
                                if (itemstack1.getItem() != Items.dye) {
                                    flag = false;
                                    break;
                                }
                                if (j != -1 && j != itemstack1.getMetadata()) {
                                    flag = false;
                                    break;
                                }
                                if (tileentitybanner$enumbannerpattern.getCraftingLayers()[l].charAt(i1) == ' ') {
                                    flag = false;
                                    break;
                                }
                                j = itemstack1.getMetadata();
                            } else if (tileentitybanner$enumbannerpattern.getCraftingLayers()[l].charAt(i1) != ' ') {
                                flag = false;
                                break;
                            }
                            ++k;
                        }
                    } else {
                        flag = false;
                    }
                    if (flag) {
                        return tileentitybanner$enumbannerpattern;
                    }
                }
                ++n2;
            }
            return null;
        }
    }

    static class RecipeDuplicatePattern
    implements IRecipe {
        private RecipeDuplicatePattern() {
        }

        @Override
        public boolean matches(InventoryCrafting inv, World worldIn) {
            ItemStack itemstack = null;
            ItemStack itemstack1 = null;
            int i = 0;
            while (i < inv.getSizeInventory()) {
                ItemStack itemstack2 = inv.getStackInSlot(i);
                if (itemstack2 != null) {
                    boolean flag;
                    if (itemstack2.getItem() != Items.banner) {
                        return false;
                    }
                    if (itemstack != null && itemstack1 != null) {
                        return false;
                    }
                    int j = TileEntityBanner.getBaseColor(itemstack2);
                    boolean bl = flag = TileEntityBanner.getPatterns(itemstack2) > 0;
                    if (itemstack != null) {
                        if (flag) {
                            return false;
                        }
                        if (j != TileEntityBanner.getBaseColor(itemstack)) {
                            return false;
                        }
                        itemstack1 = itemstack2;
                    } else if (itemstack1 != null) {
                        if (!flag) {
                            return false;
                        }
                        if (j != TileEntityBanner.getBaseColor(itemstack1)) {
                            return false;
                        }
                        itemstack = itemstack2;
                    } else if (flag) {
                        itemstack = itemstack2;
                    } else {
                        itemstack1 = itemstack2;
                    }
                }
                ++i;
            }
            return itemstack != null && itemstack1 != null;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inv) {
            int i = 0;
            while (i < inv.getSizeInventory()) {
                ItemStack itemstack = inv.getStackInSlot(i);
                if (itemstack != null && TileEntityBanner.getPatterns(itemstack) > 0) {
                    ItemStack itemstack1 = itemstack.copy();
                    itemstack1.stackSize = 1;
                    return itemstack1;
                }
                ++i;
            }
            return null;
        }

        @Override
        public int getRecipeSize() {
            return 2;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }

        @Override
        public ItemStack[] getRemainingItems(InventoryCrafting inv) {
            ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
            int i = 0;
            while (i < aitemstack.length) {
                ItemStack itemstack = inv.getStackInSlot(i);
                if (itemstack != null) {
                    if (itemstack.getItem().hasContainerItem()) {
                        aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
                    } else if (itemstack.hasTagCompound() && TileEntityBanner.getPatterns(itemstack) > 0) {
                        aitemstack[i] = itemstack.copy();
                        aitemstack[i].stackSize = 1;
                    }
                }
                ++i;
            }
            return aitemstack;
        }
    }
}

