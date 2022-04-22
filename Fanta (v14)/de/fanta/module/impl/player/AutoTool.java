/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventClickMouse;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.module.Module;
import java.awt.Color;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition;

public class AutoTool
extends Module {
    private int slot = 0;

    public AutoTool() {
        super("AutoTool", 0, Module.Type.Player, Color.cyan);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPreMotion) {
            double lastSpeed = 0.0;
            this.slot = AutoTool.mc.thePlayer.inventory.currentItem;
            if (AutoTool.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = 36;
                while (i < AutoTool.mc.thePlayer.inventoryContainer.inventorySlots.size()) {
                    ItemStack itemStack = AutoTool.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (itemStack != null) {
                        Item item = itemStack.getItem();
                        if (AutoTool.mc.gameSettings.keyBindAttack.isKeyDown() && (item instanceof ItemTool || item instanceof ItemSword)) {
                            double toolSpeed = this.getToolSpeed(itemStack);
                            double currentSpeed = this.getToolSpeed(AutoTool.mc.thePlayer.getHeldItem());
                            if (toolSpeed > 1.0 && toolSpeed > currentSpeed && toolSpeed > lastSpeed) {
                                this.slot = i - 36;
                                lastSpeed = toolSpeed;
                            }
                        }
                    }
                    ++i;
                }
            }
        }
        if (event instanceof EventClickMouse) {
            ((EventClickMouse)event).setSlot(this.slot);
        }
    }

    private double getToolSpeed(ItemStack itemStack) {
        double damage = 0.0;
        if (itemStack != null && (itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemSword)) {
            if (itemStack.getItem() instanceof ItemAxe) {
                damage += (double)(itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock()) + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack));
            } else if (itemStack.getItem() instanceof ItemPickaxe) {
                damage += (double)(itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock()) + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack));
            } else if (itemStack.getItem() instanceof ItemSpade) {
                damage += (double)(itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock()) + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack));
            } else if (itemStack.getItem() instanceof ItemSword) {
                damage += (double)itemStack.getItem().getStrVsBlock(itemStack, AutoTool.mc.theWorld.getBlockState(AutoTool.mc.objectMouseOver.getBlockPos()).getBlock());
            }
            damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
            damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) / 11.0;
            damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) / 33.0;
            return damage -= (double)itemStack.getItemDamage() / 10000.0;
        }
        return 0.0;
    }
}

