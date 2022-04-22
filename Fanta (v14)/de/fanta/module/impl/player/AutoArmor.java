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
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor
extends Module {
    public static boolean needArmor = false;
    private int[] bestArmor;
    TimeUtil time = new TimeUtil();
    private int[] boots;
    private int[] chestplate;
    private int[] helmet;
    private int[] leggings;

    public AutoArmor() {
        super("AutoArmor", 0, Module.Type.Player, Color.YELLOW);
        this.settings.add(new Setting("Delay", new Slider(1.0, 600.0, 1.0, 200.0)));
    }

    @Override
    public void onEvent(Event e) {
        this.boots = new int[]{313, 309, 317, 305, 301};
        this.chestplate = new int[]{311, 307, 315, 303, 299};
        this.helmet = new int[]{310, 306, 314, 302, 298};
        this.leggings = new int[]{312, 308, 316, 304, 300};
        if (AutoArmor.mc.currentScreen instanceof GuiInventory) {
            int i = 5;
            while (i < 9) {
                needArmor = true;
                ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                int value = this.getProtection(stack);
                int type = i - 5;
                int bestSlot = -1;
                int highestValue = 0;
                int inv = 9;
                while (inv < 45) {
                    ItemStack invStx = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(inv).getStack();
                    if (invStx != null && invStx.getItem() instanceof ItemArmor) {
                        ItemArmor armour = (ItemArmor)invStx.getItem();
                        int armourProtection = armour.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, invStx);
                        if (armour.armorType == type && armourProtection > value && armourProtection > highestValue) {
                            highestValue = armourProtection;
                            bestSlot = inv;
                        }
                    }
                    if (inv == 45) {
                        needArmor = false;
                    }
                    ++inv;
                }
                if (bestSlot != -1 && this.time.hasReached((long)((Slider)this.getSetting((String)"Delay").getSetting()).curValue)) {
                    if (stack != null && e instanceof EventTick) {
                        AutoArmor.mc.playerController.windowClick(0, i, 1, 4, AutoArmor.mc.thePlayer);
                    }
                    if (e instanceof EventTick) {
                        AutoArmor.mc.playerController.windowClick(0, bestSlot, 0, 1, AutoArmor.mc.thePlayer);
                    }
                    this.time.reset();
                }
                ++i;
            }
        }
    }

    private int getProtection(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemArmor) {
            int normalValue = ((ItemArmor)stack.getItem()).damageReduceAmount;
            int enchantmentValue = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            return normalValue + enchantmentValue;
        }
        return -1;
    }
}

