/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class InvManager
extends Module {
    TimeUtil time = new TimeUtil();
    private static int[] bestArmorDamageReducement;
    private int[] bestArmorSlots;
    private float bestSwordDamage;
    private int bestSwordSlot;
    private List<Integer> trash = new ArrayList<Integer>();
    private boolean canFake;
    public static double DelayY;

    public InvManager() {
        super("InvManager", 0, Module.Type.Player, Color.cyan);
        this.settings.add(new Setting("PrevSwords", new CheckBox(false)));
        this.settings.add(new Setting("OpenInv", new CheckBox(false)));
        this.settings.add(new Setting("FakeInv", new CheckBox(false)));
        this.settings.add(new Setting("Delay", new Slider(1.0, 1000.0, 1.0, 60.0)));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick) {
            this.searchForItems();
            int i = 0;
            while (i < 4) {
                if (this.bestArmorSlots[i] != -1) {
                    int bestSlot = this.bestArmorSlots[i];
                    DelayY = ((Slider)this.getSetting((String)"Delay").getSetting()).curValue;
                    ItemStack oldArmor = InvManager.mc.thePlayer.inventory.armorItemInSlot(i);
                    if (((CheckBox)this.getSetting((String)"OpenInv").getSetting()).state && InvManager.mc.currentScreen instanceof GuiInventory && !((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && oldArmor != null && oldArmor.getItem() != null && this.time.hasReached((long)DelayY)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, 8 - i, 0, 1, InvManager.mc.thePlayer);
                        this.time.reset();
                    }
                    if (((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && this.canFakeInv()) {
                        InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                        if (oldArmor != null && oldArmor.getItem() != null && this.time.hasReached((long)DelayY)) {
                            InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, 8 - i, 0, 1, InvManager.mc.thePlayer);
                            this.time.reset();
                        }
                    }
                    if (((CheckBox)this.getSetting((String)"OpenInv").getSetting()).state && InvManager.mc.currentScreen instanceof GuiInventory && !((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && this.time.hasReached((long)DelayY)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, bestSlot < 9 ? bestSlot + 36 : bestSlot, 0, 1, InvManager.mc.thePlayer);
                        this.time.reset();
                    }
                    if (((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && this.canFakeInv()) {
                        InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                        if (this.time.hasReached((long)DelayY)) {
                            InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, bestSlot < 9 ? bestSlot + 36 : bestSlot, 0, 1, InvManager.mc.thePlayer);
                            this.time.reset();
                        }
                    }
                }
                ++i;
            }
            if (((CheckBox)this.getSetting((String)"OpenInv").getSetting()).state && InvManager.mc.currentScreen instanceof GuiInventory && !((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && this.bestSwordSlot != -1 && this.bestSwordDamage != -1.0f && this.time.hasReached((long)DelayY)) {
                InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, this.bestSwordSlot < 9 ? this.bestSwordSlot + 36 : this.bestSwordSlot, 0, 2, InvManager.mc.thePlayer);
                this.time.reset();
            }
            if (((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && this.canFakeInv()) {
                InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                if (this.bestSwordSlot != -1 && this.bestSwordDamage != -1.0f && this.time.hasReached((long)DelayY)) {
                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, this.bestSwordSlot < 9 ? this.bestSwordSlot + 36 : this.bestSwordSlot, 0, 2, InvManager.mc.thePlayer);
                    this.time.reset();
                }
            }
            this.searchForTrash();
            Collections.shuffle(this.trash);
            if (((CheckBox)this.getSetting((String)"OpenInv").getSetting()).state && InvManager.mc.currentScreen instanceof GuiInventory && !((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state) {
                for (Integer integer : this.trash) {
                    if (!this.time.hasReached((long)DelayY)) continue;
                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, integer < 9 ? integer + 36 : integer, 0, 4, InvManager.mc.thePlayer);
                    this.time.reset();
                }
            }
            if (((CheckBox)this.getSetting((String)"FakeInv").getSetting()).state && this.canFakeInv()) {
                InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                for (Integer integer : this.trash) {
                    if (!this.time.hasReached((long)DelayY)) continue;
                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, integer < 9 ? integer + 36 : integer, 0, 4, InvManager.mc.thePlayer);
                    this.time.reset();
                }
            }
        }
    }

    public boolean canFakeInv() {
        return !InvManager.mc.thePlayer.isUsingItem() && !InvManager.mc.thePlayer.isEating() && InvManager.mc.currentScreen == null && !InvManager.mc.gameSettings.keyBindUseItem.isKeyDown() && !InvManager.mc.gameSettings.keyBindAttack.isKeyDown() && !InvManager.mc.gameSettings.keyBindJump.isKeyDown() && (double)InvManager.mc.thePlayer.swingProgress == 0.0;
    }

    private void searchForItems() {
        ItemArmor armor;
        ItemStack itemStack;
        bestArmorDamageReducement = new int[4];
        this.bestArmorSlots = new int[4];
        this.bestSwordDamage = -1.0f;
        this.bestSwordSlot = -1;
        Arrays.fill(bestArmorDamageReducement, -1);
        Arrays.fill(this.bestArmorSlots, -1);
        int i = 0;
        while (i < this.bestArmorSlots.length) {
            itemStack = InvManager.mc.thePlayer.inventory.armorItemInSlot(i);
            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
                armor = (ItemArmor)itemStack.getItem();
                InvManager.bestArmorDamageReducement[i] = armor.damageReduceAmount;
            }
            ++i;
        }
        i = 0;
        while (i < 36) {
            itemStack = InvManager.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() != null) {
                Item sword;
                if (itemStack.getItem() instanceof ItemArmor) {
                    armor = (ItemArmor)itemStack.getItem();
                    int armorType = 3 - armor.armorType;
                    if (bestArmorDamageReducement[armorType] < armor.damageReduceAmount) {
                        InvManager.bestArmorDamageReducement[armorType] = armor.damageReduceAmount;
                        this.bestArmorSlots[armorType] = i;
                    }
                }
                if (itemStack.getItem() instanceof ItemSword && this.bestSwordDamage < ((ItemSword)(sword = (ItemSword)itemStack.getItem())).getDamageVsEntity() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack)) {
                    this.bestSwordDamage = ((ItemSword)sword).getDamageVsEntity() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                    this.bestSwordSlot = i;
                }
                if (itemStack.getItem() instanceof ItemTool) {
                    sword = (ItemTool)itemStack.getItem();
                    float damage = ((ItemTool)sword).getToolMaterial().getDamageVsEntity() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                    try {
                        if (((CheckBox)this.getSetting((String)"PrevSwords").getSetting()).state) {
                            damage -= 1.0f;
                        }
                        if (this.bestSwordDamage < damage) {
                            this.bestSwordDamage = damage;
                            this.bestSwordSlot = i;
                        }
                    }
                    catch (NullPointerException nullPointerException) {
                        // empty catch block
                    }
                }
            }
            ++i;
        }
    }

    private void searchForTrash() {
        ItemArmor armor;
        ItemStack itemStack;
        this.trash.clear();
        bestArmorDamageReducement = new int[4];
        this.bestArmorSlots = new int[4];
        this.bestSwordDamage = -1.0f;
        this.bestSwordSlot = -1;
        Arrays.fill(bestArmorDamageReducement, -1);
        Arrays.fill(this.bestArmorSlots, -1);
        List[] allItems = new List[4];
        ArrayList<Integer> allSwords = new ArrayList<Integer>();
        int i = 0;
        while (i < this.bestArmorSlots.length) {
            itemStack = InvManager.mc.thePlayer.inventory.armorItemInSlot(i);
            allItems[i] = new ArrayList();
            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
                armor = (ItemArmor)itemStack.getItem();
                InvManager.bestArmorDamageReducement[i] = armor.damageReduceAmount;
                this.bestArmorSlots[i] = 8 + i;
            }
            ++i;
        }
        i = 0;
        while (i < 36) {
            itemStack = InvManager.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() != null) {
                Item sword;
                if (itemStack.getItem() instanceof ItemArmor) {
                    armor = (ItemArmor)itemStack.getItem();
                    int armorType = 3 - armor.armorType;
                    allItems[armorType].add(i);
                    if (bestArmorDamageReducement[armorType] < armor.damageReduceAmount) {
                        InvManager.bestArmorDamageReducement[armorType] = armor.damageReduceAmount;
                        this.bestArmorSlots[armorType] = i;
                    }
                }
                if (itemStack.getItem() instanceof ItemSword) {
                    sword = (ItemSword)itemStack.getItem();
                    allSwords.add(i);
                    if (this.bestSwordDamage < ((ItemSword)sword).getDamageVsEntity() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack)) {
                        this.bestSwordDamage = ((ItemSword)sword).getDamageVsEntity() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                        this.bestSwordSlot = i;
                    }
                }
                if (itemStack.getItem() instanceof ItemTool) {
                    sword = (ItemTool)itemStack.getItem();
                    float damage = ((ItemTool)sword).getToolMaterial().getDamageVsEntity() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                    try {
                        if (((CheckBox)this.getSetting((String)"PrevSwords").getSetting()).state) {
                            damage -= 1.0f;
                        }
                        if (this.bestSwordDamage < damage) {
                            this.bestSwordDamage = damage;
                            this.bestSwordSlot = i;
                        }
                    }
                    catch (NullPointerException nullPointerException) {
                        // empty catch block
                    }
                }
            }
            ++i;
        }
        i = 0;
        while (i < allItems.length) {
            List allItem = allItems[i];
            int finalI = i++;
            allItem.stream().filter(slot -> slot != this.bestArmorSlots[finalI]).forEach(this.trash::add);
        }
        allSwords.stream().filter(slot -> slot != this.bestSwordSlot).forEach(this.trash::add);
    }
}

