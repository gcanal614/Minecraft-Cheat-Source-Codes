/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.module.impl.player.AutoArmor;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class InvCleaner
extends Module {
    private TimeUtil timer = new TimeUtil();
    private List<Integer> trash = new ArrayList<Integer>();

    public InvCleaner() {
        super("InvCleaner", 0, Module.Type.Player, Color.cyan);
        this.settings.add(new Setting("OpenInv", new CheckBox(true)));
        this.settings.add(new Setting("Delay", new Slider(1.0, 1000.0, 1.0, 100.0)));
    }

    @Override
    public void onEnable() {
        this.timer.reset();
    }

    @Override
    public void onDisable() {
        this.timer.reset();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventTick && e.isPre() && (InvCleaner.mc.currentScreen == null || InvCleaner.mc.currentScreen instanceof GuiInventory) && ((CheckBox)this.getSetting((String)"OpenInv").getSetting()).state) {
            this.collect();
            Collections.shuffle(this.trash);
            if (!AutoArmor.needArmor) {
                for (Integer integer : this.trash) {
                    if (!this.timer.hasReached(100L)) continue;
                    InvCleaner.mc.playerController.windowClick(InvCleaner.mc.thePlayer.inventoryContainer.windowId, integer < 9 ? integer + 36 : integer, 1, 4, InvCleaner.mc.thePlayer);
                    InvCleaner.mc.playerController.updateController();
                    this.timer.reset();
                }
            }
        }
    }

    private void collect() {
        this.trash.clear();
        int slot = 0;
        while (slot < 36) {
            ItemStack stack = InvCleaner.mc.thePlayer.inventory.getStackInSlot(slot);
            if (!(stack == null || stack.getItem() == null || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAppleGold || stack.getItem() instanceof ItemEnderPearl || stack.getItem() instanceof ItemBlock || stack.getItem() instanceof ItemFishingRod || stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemPotion || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemArmor)) {
                this.trash.add(slot);
            }
            if (stack != null && stack.getItem() instanceof ItemSword && !this.isBestSword(stack)) {
                this.trash.add(slot);
            }
            if (stack != null && stack.getItem() instanceof ItemArmor && !this.isBestArmor(stack)) {
                this.trash.add(slot);
            }
            ++slot;
        }
    }

    private boolean isBestSword(ItemStack input) {
        boolean best = true;
        int i = 9;
        while (i < 45) {
            ItemStack stack;
            if (InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (stack = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemSword && this.getWeaponStrength(stack) > this.getWeaponStrength(input)) {
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
            if (InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (stack = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemArmor) {
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
}

