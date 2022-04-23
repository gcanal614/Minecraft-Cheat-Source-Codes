/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static int[] getArmorRating(int slot) {
        int rating = 0;
        int armorType = 4;
        if (ItemUtils.MC.thePlayer.inventoryContainer.getSlot(slot).getStack() != null && ItemUtils.MC.thePlayer.inventoryContainer.getSlot(slot).getStack().getItem() instanceof ItemArmor) {
            ItemStack stack = ItemUtils.MC.thePlayer.inventoryContainer.getSlot(slot).getStack();
            ItemArmor armor = (ItemArmor)stack.getItem();
            block0 : switch (armor.armorType) {
                case 0: {
                    armorType = 0;
                    switch (Item.getIdFromItem(stack.getItem())) {
                        case 298: {
                            rating += 25;
                            break;
                        }
                        case 314: {
                            rating += 50;
                            break;
                        }
                        case 302: {
                            rating += 75;
                            break;
                        }
                        case 306: {
                            rating += 100;
                            break;
                        }
                        case 310: {
                            rating += 125;
                        }
                    }
                    break;
                }
                case 1: {
                    armorType = 1;
                    switch (Item.getIdFromItem(stack.getItem())) {
                        case 299: {
                            rating += 25;
                            break;
                        }
                        case 315: {
                            rating += 50;
                            break;
                        }
                        case 303: {
                            rating += 75;
                            break;
                        }
                        case 307: {
                            rating += 100;
                            break;
                        }
                        case 311: {
                            rating += 125;
                        }
                    }
                    break;
                }
                case 2: {
                    armorType = 2;
                    switch (Item.getIdFromItem(stack.getItem())) {
                        case 300: {
                            rating += 25;
                            break;
                        }
                        case 316: {
                            rating += 50;
                            break;
                        }
                        case 304: {
                            rating += 75;
                            break;
                        }
                        case 308: {
                            rating += 100;
                            break;
                        }
                        case 312: {
                            rating += 125;
                        }
                    }
                    break;
                }
                case 3: {
                    armorType = 3;
                    switch (Item.getIdFromItem(stack.getItem())) {
                        case 301: {
                            rating += 25;
                            break block0;
                        }
                        case 317: {
                            rating += 50;
                            break block0;
                        }
                        case 305: {
                            rating += 75;
                            break block0;
                        }
                        case 309: {
                            rating += 100;
                            break block0;
                        }
                        case 313: {
                            rating += 125;
                        }
                    }
                }
            }
            if (stack.isItemEnchanted()) {
                rating += 10;
                rating += EnchantmentHelper.getEnchantmentLevel(0, stack) * 35;
                rating += EnchantmentHelper.getEnchantmentLevel(4, stack) * 10;
                rating += EnchantmentHelper.getEnchantmentLevel(3, stack) * 10;
                rating += EnchantmentHelper.getEnchantmentLevel(1, stack) * 10;
                rating += EnchantmentHelper.getEnchantmentLevel(7, stack) * 10;
                rating += EnchantmentHelper.getEnchantmentLevel(34, stack) * 5;
            }
        }
        return new int[]{rating, armorType};
    }

    public static void swapArmor(int armorSlot, int inventorySlot) {
        ItemUtils.MC.playerController.windowClick(ItemUtils.MC.thePlayer.inventoryContainer.windowId, armorSlot, 1, 4, ItemUtils.MC.thePlayer);
        ItemUtils.MC.playerController.windowClick(ItemUtils.MC.thePlayer.inventoryContainer.windowId, inventorySlot, 1, 1, ItemUtils.MC.thePlayer);
    }
}

