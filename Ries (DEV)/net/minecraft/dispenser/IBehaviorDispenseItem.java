/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.dispenser;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
    public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = (source, stack) -> stack;

    public ItemStack dispense(IBlockSource var1, ItemStack var2);
}

