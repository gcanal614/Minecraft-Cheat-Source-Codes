/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class ChestStealer
extends Module {
    public static double dellay;
    TimeUtil time = new TimeUtil();

    public ChestStealer() {
        super("Stealer", 16, Module.Type.World, Color.RED);
        this.settings.add(new Setting("Delay", new Slider(0.0, 500.0, 1.0, 110.0)));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick) {
            dellay = ((Slider)this.getSetting((String)"Delay").getSetting()).curValue;
            if (ChestStealer.mc.thePlayer.openContainer != null && ChestStealer.mc.thePlayer.openContainer instanceof ContainerChest) {
                ContainerChest container = (ContainerChest)ChestStealer.mc.thePlayer.openContainer;
                int i = 0;
                while (i < container.getLowerChestInventory().getSizeInventory()) {
                    if (container.getLowerChestInventory().getStackInSlot(i) != null && this.time.hasReached((long)dellay)) {
                        ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, 1, ChestStealer.mc.thePlayer);
                        this.time.reset();
                    }
                    ++i;
                }
                GuiChest chest = (GuiChest)ChestStealer.mc.currentScreen;
                if (this.isChestEmpty(chest) || this.isInventoryFull()) {
                    ChestStealer.mc.thePlayer.closeScreen();
                }
            }
        }
    }

    public boolean isContainerEmpty(Container container) {
        boolean temp = true;
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        int i = 0;
        while (i < slotAmount) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
            ++i;
        }
        return temp;
    }

    private boolean isBestSword(ItemStack input) {
        boolean best = true;
        int i = 9;
        while (i < 45) {
            ItemStack stack;
            if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (stack = ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemSword && this.getWeaponStrength(stack) > this.getWeaponStrength(input)) {
                best = false;
            }
            ++i;
        }
        return best;
    }

    private boolean isBestArmor(ItemStack input) {
        boolean best = true;
        int i = 5;
        while (i < 45) {
            ItemStack stack;
            if (ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (stack = ChestStealer.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor)stack.getItem();
                if (armor.armorType == ((ItemArmor)input.getItem()).armorType && this.getProtection(stack) >= this.getProtection(input)) {
                    best = false;
                }
            }
            ++i;
        }
        return best;
    }

    private double getProtection(ItemStack stack) {
        double protection = 0.0;
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)stack.getItem();
            protection += (double)armor.damageReduceAmount;
            protection += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
        }
        return protection;
    }

    private double getWeaponStrength(ItemStack stack) {
        double damage = 0.0;
        if (stack != null) {
            if (stack.getItem() instanceof ItemSword) {
                ItemSword sword = (ItemSword)stack.getItem();
                damage += (double)sword.getDamageVsEntity();
            }
            if (stack.getItem() instanceof ItemTool) {
                ItemTool tool = (ItemTool)stack.getItem();
                damage += (double)tool.getToolMaterial().getDamageVsEntity();
            }
            damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        }
        return damage;
    }

    private boolean isValidItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock;
    }

    private boolean isChestEmpty(GuiChest chest) {
        int index = 0;
        while (index < chest.getLowerChestInventory().getSizeInventory()) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null && this.isValidItem(stack)) {
                return false;
            }
            ++index;
        }
        return true;
    }

    private boolean isInventoryFull() {
        int index = 9;
        while (index <= 44) {
            ItemStack stack = ChestStealer.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
            ++index;
        }
        return true;
    }
}

