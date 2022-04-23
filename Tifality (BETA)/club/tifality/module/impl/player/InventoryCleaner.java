/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.timer.TimerUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(label="InventoryCleaner", category=ModuleCategory.PLAYER)
public final class InventoryCleaner
extends Module {
    public static int weaponSlot = 36;
    public static int pickaxeSlot = 42;
    public static int axeSlot = 43;
    public static int shovelSlot = 44;
    private final DoubleProperty Delay = new DoubleProperty("Delay", 1.0, 1.0, 500.0, 1.0, Representation.MILLISECONDS);
    public final EnumProperty<Mode> mode = new EnumProperty<Mode>("Mode", Mode.OPENINV);
    private final Property<Boolean> Food = new Property<Boolean>("Food", false);
    private final Property<Boolean> Archery = new Property<Boolean>("Archery", true);
    private final Property<Boolean> UHC = new Property<Boolean>("UHC", false);
    private final TimerUtil timer = new TimerUtil();

    @Listener
    public void onUpdate(UpdatePositionEvent event) {
        if (!(InventoryCleaner.mc.thePlayer.openContainer instanceof ContainerChest) || !(InventoryCleaner.mc.currentScreen instanceof GuiContainer)) {
            long delay = ((Double)this.Delay.get()).longValue() * 100L;
            if ((this.mode.get() == Mode.Basic || InventoryCleaner.mc.currentScreen instanceof GuiInventory) && (InventoryCleaner.mc.currentScreen == null || InventoryCleaner.mc.currentScreen instanceof GuiInventory || InventoryCleaner.mc.currentScreen instanceof GuiChat)) {
                if (this.timer.hasReached(delay) && weaponSlot >= 36) {
                    if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getHasStack()) {
                        this.getBestWeapon(weaponSlot);
                    } else if (!this.isBestWeapon(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack())) {
                        this.getBestWeapon(weaponSlot);
                    }
                }
                if (this.timer.hasReached(delay) && pickaxeSlot >= 36) {
                    this.getBestPickaxe();
                }
                if (this.timer.hasReached(delay) && shovelSlot >= 36) {
                    this.getBestShovel();
                }
                if (this.timer.hasReached(delay) && axeSlot >= 36) {
                    this.getBestAxe();
                }
                if (this.timer.hasReached(delay)) {
                    for (int i = 9; i < 45; ++i) {
                        ItemStack is;
                        if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.shouldDrop(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack(), i)) continue;
                        this.drop(i);
                        if (delay > 0L) break;
                    }
                }
            }
        }
    }

    public void swap(int slot1, int hotbarSlot) {
        InventoryCleaner.mc.playerController.windowClick(InventoryCleaner.mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, 2, InventoryCleaner.mc.thePlayer);
    }

    public void drop(int slot) {
        InventoryCleaner.mc.playerController.windowClick(InventoryCleaner.mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, InventoryCleaner.mc.thePlayer);
        this.timer.reset();
    }

    public boolean isBestWeapon(ItemStack stack) {
        float damage = this.getDamage(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(this.getDamage(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > damage) || !(is.getItem() instanceof ItemSword)) continue;
            return false;
        }
        return stack.getItem() instanceof ItemSword;
    }

    public void getBestWeapon(int slot) {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isBestWeapon(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) || !(this.getDamage(is) > 0.0f) || !(is.getItem() instanceof ItemSword)) continue;
            this.swap(i, slot - 36);
            this.timer.reset();
            break;
        }
    }

    private float getDamage(ItemStack stack) {
        float damage = 0.0f;
        Item item = stack.getItem();
        if (item instanceof ItemTool) {
            ItemTool sword = (ItemTool)item;
            damage += (float)sword.getMaxDamage();
        }
        if (item instanceof ItemSword) {
            ItemSword sword1 = (ItemSword)item;
            damage += sword1.getDamageVsEntity();
        }
        return damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
    }

    public boolean shouldDrop(ItemStack stack, int slot) {
        if (stack.getDisplayName().toLowerCase().contains("(right click)")) {
            return false;
        }
        if (this.UHC.get().booleanValue()) {
            if (stack.getDisplayName().toLowerCase().contains("apple")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("head")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("gold")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("crafting table")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("stick")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("and") && stack.getDisplayName().toLowerCase().contains("ril")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("axe of perun")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("barbarian")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("bloodlust")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("dragonchest")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("dragon sword")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("dragon armor")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("excalibur")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("exodus")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("fusion armor")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("hermes boots")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("hide of leviathan")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("scythe")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("seven-league boots")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("shoes of vidar")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("apprentice")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("master")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("vorpal")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("enchanted")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("spiked")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("tarnhelm")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("philosopher")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("anvil")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("panacea")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("fusion")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("excalibur")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("backpack")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("hermes")) {
                return false;
            }
            if (stack.getDisplayName().toLowerCase().contains("barbarian")) {
                return false;
            }
        }
        if (slot == weaponSlot && this.isBestWeapon(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack())) {
            return false;
        }
        if (slot == pickaxeSlot && this.isBestPickaxe(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack()) && pickaxeSlot >= 0) {
            return false;
        }
        if (slot == axeSlot && this.isBestAxe(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack()) && axeSlot >= 0) {
            return false;
        }
        if (slot == shovelSlot && this.isBestShovel(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack()) && shovelSlot >= 0) {
            return false;
        }
        if (stack.getItem() instanceof ItemArmor) {
            for (int type2 = 1; type2 < 5; ++type2) {
                ItemStack is;
                if (InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(4 + type2).getHasStack() && this.isBestArmor(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(4 + type2).getStack(), type2) || !this.isBestArmor(stack, type2)) continue;
                return false;
            }
        }
        if (stack.getItem() instanceof ItemPotion && this.isBadPotion(stack)) {
            return true;
        }
        if (stack.getItem() instanceof ItemFood && this.Food.get().booleanValue() && !(stack.getItem() instanceof ItemAppleGold)) {
            return true;
        }
        if (!(stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor)) {
            if ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow")) && !this.Archery.get().booleanValue()) {
                return true;
            }
            return stack.getItem().getUnlocalizedName().contains("tnt") || stack.getItem().getUnlocalizedName().contains("stick") || stack.getItem().getUnlocalizedName().contains("egg") || stack.getItem().getUnlocalizedName().contains("string") || stack.getItem().getUnlocalizedName().contains("cake") || stack.getItem().getUnlocalizedName().contains("mushroom") || stack.getItem().getUnlocalizedName().contains("flint") || stack.getItem().getUnlocalizedName().contains("compass") || stack.getItem().getUnlocalizedName().contains("dyePowder") || stack.getItem().getUnlocalizedName().contains("feather") || stack.getItem().getUnlocalizedName().contains("bucket") || stack.getItem().getUnlocalizedName().contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect") || stack.getItem().getUnlocalizedName().contains("snow") || stack.getItem().getUnlocalizedName().contains("fish") || stack.getItem().getUnlocalizedName().contains("enchant") || stack.getItem().getUnlocalizedName().contains("exp") || stack.getItem().getUnlocalizedName().contains("shears") || stack.getItem().getUnlocalizedName().contains("anvil") || stack.getItem().getUnlocalizedName().contains("torch") || stack.getItem().getUnlocalizedName().contains("seeds") || stack.getItem().getUnlocalizedName().contains("leather") || stack.getItem().getUnlocalizedName().contains("reeds") || stack.getItem().getUnlocalizedName().contains("skull") || stack.getItem().getUnlocalizedName().contains("record") || stack.getItem().getUnlocalizedName().contains("snowball") || stack.getItem() instanceof ItemGlassBottle || stack.getItem().getUnlocalizedName().contains("piston");
        }
        return true;
    }

    private void getBestPickaxe() {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isBestPickaxe(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) || pickaxeSlot == i || this.isBestWeapon(is)) continue;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getHasStack()) {
                this.swap(i, pickaxeSlot - 36);
                this.timer.reset();
                if (((Double)this.Delay.get()).longValue() <= 0L) continue;
                return;
            }
            if (this.isBestPickaxe(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack())) continue;
            this.swap(i, pickaxeSlot - 36);
            this.timer.reset();
            if (((Double)this.Delay.get()).longValue() <= 0L) continue;
            return;
        }
    }

    private void getBestShovel() {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isBestShovel(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) || shovelSlot == i || this.isBestWeapon(is)) continue;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getHasStack()) {
                this.swap(i, shovelSlot - 36);
                this.timer.reset();
                if (((Double)this.Delay.get()).longValue() <= 0L) continue;
                return;
            }
            if (this.isBestShovel(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack())) continue;
            this.swap(i, shovelSlot - 36);
            this.timer.reset();
            if (((Double)this.Delay.get()).longValue() <= 0L) continue;
            return;
        }
    }

    private void getBestAxe() {
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isBestAxe(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) || axeSlot == i || this.isBestWeapon(is)) continue;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(axeSlot).getHasStack()) {
                this.swap(i, axeSlot - 36);
                this.timer.reset();
                if (((Double)this.Delay.get()).longValue() <= 0L) continue;
                return;
            }
            if (this.isBestAxe(InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack())) continue;
            this.swap(i, axeSlot - 36);
            this.timer.reset();
            if (((Double)this.Delay.get()).longValue() <= 0L) continue;
            return;
        }
    }

    boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        }
        float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(this.getToolEffect(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemPickaxe)) continue;
            return false;
        }
        return true;
    }

    boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        }
        float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(this.getToolEffect(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemSpade)) continue;
            return false;
        }
        return true;
    }

    boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        }
        float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(this.getToolEffect(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemAxe) || this.isBestWeapon(stack)) continue;
            return false;
        }
        return true;
    }

    float getToolEffect(ItemStack stack) {
        float value;
        Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        String name = item.getUnlocalizedName();
        ItemTool tool = (ItemTool)item;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else {
            if (!(item instanceof ItemAxe)) {
                return 1.0f;
            }
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075);
        value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0);
        return value;
    }

    boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion)stack.getItem();
            if (potion.getEffects(stack) == null) {
                return true;
            }
            for (PotionEffect o : potion.getEffects(stack)) {
                PotionEffect effect = o;
                if (effect.getPotionID() != Potion.poison.getId() && effect.getPotionID() != Potion.harm.getId() && effect.getPotionID() != Potion.moveSlowdown.getId() && effect.getPotionID() != Potion.weakness.getId()) continue;
                return true;
            }
        }
        return false;
    }

    boolean isBestArmor(ItemStack stack, int type2) {
        float prot = this.getProtection(stack);
        String strType = "";
        if (type2 == 1) {
            strType = "helmet";
        } else if (type2 == 2) {
            strType = "chestplate";
        } else if (type2 == 3) {
            strType = "leggings";
        } else if (type2 == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            ItemStack is;
            if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(this.getProtection(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > prot) || !is.getUnlocalizedName().contains(strType)) continue;
            return false;
        }
        return true;
    }

    float getProtection(ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)stack.getItem();
            prot = (float)((double)prot + (double)armor.damageReduceAmount + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, stack) / 100.0);
        }
        return prot;
    }

    public static enum Mode {
        OPENINV,
        Basic;

    }
}

