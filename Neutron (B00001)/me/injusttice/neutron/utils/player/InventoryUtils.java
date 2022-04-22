package me.injusttice.neutron.utils.player;

import com.google.common.collect.Multimap;
import me.injusttice.neutron.utils.network.PacketUtil;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemAppleGold;
import java.util.Iterator;
import java.util.List;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import java.awt.AWTException;
import net.minecraft.client.gui.GuiScreen;
import java.awt.Robot;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import java.util.function.Predicate;
import net.minecraft.inventory.IInventory;

public class InventoryUtils
{
    public static int INCLUDE_ARMOR_BEGIN = 5;
    public static int EXCLUDE_ARMOR_BEGIN = 9;
    public static int ONLY_HOT_BAR_BEGIN = 36;
    public static int END = 45;
    public static Timer timerUtil;

    private InventoryUtils() {
    }

    public static boolean isInventoryEmpty(IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (isValid(inventory.getStackInSlot(i))) {
                return false;
            }
        }
        return true;
    }

    public static int findInHotBar(Predicate<ItemStack> cond) {
        for (int i = 36; i < 45; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (cond.test(stack)) {
                return i - 36;
            }
        }
        return -1;
    }

    public static ItemStack getStackInSlot(int index) {
        return getLocalPlayer().inventoryContainer.getSlot(index).getStack();
    }

    public static EntityPlayerSP getLocalPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static double getTotalArmorProtection(EntityPlayer player) {
        double totalArmor = 0.0;
        for (int i = 0; i < 4; ++i) {
            ItemStack armorStack = player.getCurrentArmor(i);
            if (armorStack != null && armorStack.getItem() instanceof ItemArmor) {
                totalArmor += getDamageReduction(armorStack);
            }
        }
        return totalArmor;
    }

    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 0) {
                if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack && i < 9) {
                    highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                    if ((slot = i) == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                        highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            int i = getFreeHotbarSlot();
            if (i == -1) {
                return -1;
            }
            Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, i, 2, Minecraft.getMinecraft().thePlayer);
        }
        return slot;
    }

    public static boolean canItemBePlaced(ItemStack item) {
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 116) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 30) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 175) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 28) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 27) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 66) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 157) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 6) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 32) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 140) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 390) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 37) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 38) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 39) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 40) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 69) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 50) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 75) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 76) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 54) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 130) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 146) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 342) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 12) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 77) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 143) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 46) {
            return false;
        }
        item.getItem();
        return Item.getIdFromItem(item.getItem()) != 145;
    }

    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; ++k) {
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
            }
        }
        return hotbarNum;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; ++k) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k] == null) {
                hotbarNum = k;
            }
            else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }

    public static void openInventory() {
        PacketUtil.sendPacketSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }

    public static void closeInventory() {
        PacketUtil.sendPacketSilent(new C0DPacketCloseWindow(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId));
    }

    public static void windowClick(int windowId, int slotId, int mouseButtonClicked, ClickType mode) {
        Minecraft.getMinecraft().playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), Minecraft.getMinecraft().thePlayer);
    }

    public static void legitClick(Slot slot, int windowId, int slotId, int mouseButtonClicked, ClickType mode) {
        try {
            Robot robot = new Robot();
            robot.mouseMove((int)(slot.xDisplayPosition * 2 + GuiScreen.width / 1.2), (int)(slot.yDisplayPosition * 2 + GuiScreen.height / 1.35));
            Minecraft.getMinecraft().playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), Minecraft.getMinecraft().thePlayer);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void legitClick(Slot slot, int slotId, int mouseButtonClicked, ClickType mode) {
        try {
            Robot robot = new Robot();
            robot.mouseMove((int)(slot.xDisplayPosition * 2 + GuiScreen.width / 1.2), (int)(slot.yDisplayPosition * 2 + GuiScreen.height / 1.35));
            Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slotId, mouseButtonClicked, mode.ordinal(), Minecraft.getMinecraft().thePlayer);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void windowClick(int slotId, int mouseButtonClicked, ClickType mode) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slotId, mouseButtonClicked, mode.ordinal(), Minecraft.getMinecraft().thePlayer);
    }

    public static double getDamageReduction(ItemStack stack) {
        double reduction = 0.0;
        ItemArmor armor = (ItemArmor)stack.getItem();
        reduction += armor.damageReduceAmount;
        if (stack.isItemEnchanted()) {
            reduction += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
        }
        return reduction;
    }

    public static boolean isValid(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem().getUnlocalizedName().contains("arrow")) {
            return true;
        }
        if (stack.getItem() instanceof ItemBlock) {
            return isGoodBlockStack(stack);
        }
        if (stack.getItem() instanceof ItemSword) {
            return isBestSword(stack);
        }
        if (stack.getItem() instanceof ItemTool) {
            return isBestTool(stack);
        }
        if (stack.getItem() instanceof ItemArmor) {
            return isBestArmor(stack);
        }
        if (stack.getItem() instanceof ItemPotion) {
            return isBuffPotion(stack);
        }
        if (stack.getItem() instanceof ItemFood) {
            return isGoodFood(stack);
        }
        if (stack.getItem() instanceof ItemBow) {
            return isBestBow(stack);
        }
        return isGoodItem(stack);
    }

    public static boolean isBestBow(ItemStack itemStack) {
        double bestBowDmg = -1.0;
        ItemStack bestBow = null;
        for (int i = 9; i < 45; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBow) {
                double damage = getBowDamage(stack);
                if (damage > bestBowDmg) {
                    bestBow = stack;
                    bestBowDmg = damage;
                }
            }
        }
        return itemStack == bestBow || getBowDamage(itemStack) > bestBowDmg;
    }

    public static double getBowDamage(ItemStack stack) {
        double damage = 0.0;
        if (stack.getItem() instanceof ItemBow && stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
        }
        return damage;
    }

    public static boolean isGoodItem(ItemStack stack) {
        Item item = stack.getItem();
        return (!(item instanceof ItemBucket) || ((ItemBucket)item).isFull == Blocks.flowing_water) && !(item instanceof ItemExpBottle) && !(item instanceof ItemFishingRod) && !(item instanceof ItemEgg) && !(item instanceof ItemSnowball) && !(item instanceof ItemSkull) && !(item instanceof ItemBucket);
    }

    public static boolean isBuffPotion(ItemStack stack) {
        ItemPotion potion = (ItemPotion)stack.getItem();
        List<PotionEffect> effects = potion.getEffects(stack);
        for (PotionEffect effect : effects) {
            if (Potion.potionTypes[effect.getPotionID()].isBadEffect()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGoodFood(ItemStack stack) {
        ItemFood food = (ItemFood)stack.getItem();
        return food instanceof ItemAppleGold || (food.getHealAmount(stack) >= 4 && food.getSaturationModifier(stack) >= 0.3f);
    }

    public static boolean isBestSword(ItemStack itemStack) {
        double damage = 0.0;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemSword) {
                double newDamage = getItemDamage(stack);
                if (newDamage > damage) {
                    damage = newDamage;
                    bestStack = stack;
                }
            }
        }
        return bestStack == itemStack || getItemDamage(itemStack) >= damage;
    }

    public static int getToolType(ItemStack stack) {
        ItemTool tool = (ItemTool)stack.getItem();
        if (tool instanceof ItemPickaxe) {
            return 0;
        }
        if (tool instanceof ItemAxe) {
            return 1;
        }
        if (tool instanceof ItemSpade) {
            return 2;
        }
        return -1;
    }

    public static boolean isBestTool(ItemStack itemStack) {
        int type = getToolType(itemStack);
        Tool bestTool = new Tool(-1, -1.0, null);
        for (int i = 9; i < 45; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemTool && type == getToolType(stack)) {
                double efficiency = getToolEfficiency(stack);
                if (efficiency > bestTool.getEfficiency()) {
                    bestTool = new Tool(i, efficiency, stack);
                }
            }
        }
        return bestTool.getStack() == itemStack || getToolEfficiency(itemStack) > bestTool.getEfficiency();
    }

    public static float getToolEfficiency(ItemStack itemStack) {
        ItemTool tool = (ItemTool)itemStack.getItem();
        float efficiency = tool.getItemEnchantability();
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
        if (efficiency > 1.0f && lvl > 0) {
            efficiency += lvl * lvl + 1;
        }
        return efficiency;
    }

    public static boolean isBestArmor(ItemStack itemStack) {
        ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
        double reduction = 0.0;
        ItemStack bestStack = null;
        for (int i = 5; i < 45; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemArmor) {
                ItemArmor stackArmor = (ItemArmor)stack.getItem();
                if (stackArmor.armorType == itemArmor.armorType) {
                    double newReduction = getDamageReduction(stack);
                    if (newReduction > reduction) {
                        reduction = newReduction;
                        bestStack = stack;
                    }
                }
            }
        }
        return bestStack == itemStack || getDamageReduction(itemStack) > reduction;
    }

    public static boolean isGoodBlockStack(ItemStack stack) {
        return stack.stackSize >= 1 && isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
    }

    public static boolean isValidBlock(Block block, boolean toPlace) {
        if (block instanceof BlockContainer) {
            return false;
        }
        if (toPlace) {
            return !(block instanceof BlockFalling) && block.isFullBlock() && block.isFullCube();
        }
        Material material = block.getMaterial();
        return !material.isReplaceable() && !material.isLiquid();
    }

    public static double getItemDamage(ItemStack stack) {
        double damage = 0.0;
        Multimap<String, AttributeModifier> attributeModifierMap = stack.getAttributeModifiers();
        for (String attributeName : attributeModifierMap.keySet()) {
            if (attributeName.equals("generic.attackDamage")) {
                Iterator<AttributeModifier> attributeModifiers = attributeModifierMap.get((String) attributeName).iterator();
                if (attributeModifiers.hasNext()) {
                    damage += attributeModifiers.next().getAmount();
                    break;
                }
                break;
            }
        }
        if (stack.isItemEnchanted()) {
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        }
        return damage;
    }

    static {
        InventoryUtils.timerUtil = new Timer();
    }

    public enum ClickType {
        CLICK("CLICK", 0),
        SHIFT_CLICK("SHIFT_CLICK", 1),
        SWAP_WITH_HOT_BAR_SLOT("SWAP_WITH_HOT_BAR_SLOT", 2),
        PLACEHOLDER("PLACEHOLDER", 3),
        DROP_ITEM("DROP_ITEM", 4);

        private ClickType(String name, int ordinal) {
        }
    }

    private static class Tool
    {
        private int slot;
        private double efficiency;
        private ItemStack stack;

        public Tool(int slot, double efficiency, ItemStack stack) {
            this.slot = slot;
            this.efficiency = efficiency;
            this.stack = stack;
        }

        public int getSlot() {
            return this.slot;
        }

        public double getEfficiency() {
            return this.efficiency;
        }

        public ItemStack getStack() {
            return this.stack;
        }
    }
}
