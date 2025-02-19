/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.WeightedRandom;

public class WeightedRandomChestContent
extends WeightedRandom.Item {
    private ItemStack theItemId;
    private int minStackSize;
    private int maxStackSize;

    public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int minimumChance, int maximumChance, int itemWeightIn) {
        super(itemWeightIn);
        this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.minStackSize = minimumChance;
        this.maxStackSize = maximumChance;
    }

    public WeightedRandomChestContent(ItemStack stack, int minimumChance, int maximumChance, int itemWeightIn) {
        super(itemWeightIn);
        this.theItemId = stack;
        this.minStackSize = minimumChance;
        this.maxStackSize = maximumChance;
    }

    public static void generateChestContents(Random random, List<WeightedRandomChestContent> listIn, IInventory inv, int max) {
        int i = 0;
        while (i < max) {
            WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.getRandomItem(random, listIn);
            int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
            if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
                ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
                itemstack1.stackSize = j;
                inv.setInventorySlotContents(random.nextInt(inv.getSizeInventory()), itemstack1);
            } else {
                int k = 0;
                while (k < j) {
                    ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
                    itemstack.stackSize = 1;
                    inv.setInventorySlotContents(random.nextInt(inv.getSizeInventory()), itemstack);
                    ++k;
                }
            }
            ++i;
        }
    }

    public static void generateDispenserContents(Random random, List<WeightedRandomChestContent> listIn, TileEntityDispenser dispenser, int max) {
        int i = 0;
        while (i < max) {
            WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.getRandomItem(random, listIn);
            int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
            if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
                ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
                itemstack1.stackSize = j;
                dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack1);
            } else {
                int k = 0;
                while (k < j) {
                    ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
                    itemstack.stackSize = 1;
                    dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack);
                    ++k;
                }
            }
            ++i;
        }
    }

    public static List<WeightedRandomChestContent> func_177629_a(List<WeightedRandomChestContent> p_177629_0_, WeightedRandomChestContent ... p_177629_1_) {
        ArrayList list = Lists.newArrayList(p_177629_0_);
        Collections.addAll(list, p_177629_1_);
        return list;
    }
}

