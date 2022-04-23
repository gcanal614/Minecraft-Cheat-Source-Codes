// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import java.util.Iterator;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import java.util.Map;
import net.minecraft.util.DamageSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemBlock;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.client.gui.inventory.GuiInventory;
import bozoware.base.BozoWare;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.client.Minecraft;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "InvManager", moduleCategory = ModuleCategory.PLAYER)
public class InvManager extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Double> Speed;
    private final BooleanProperty openinvBool;
    private final BooleanProperty organize;
    private final BooleanProperty throwFood;
    private final ValueProperty<Integer> swordSlot;
    private final ValueProperty<Integer> gapSlot;
    private final BooleanProperty throwTools;
    public Minecraft mc;
    boolean hasSet;
    private double delay;
    private TimerUtil updateTimer;
    public static boolean dropping;
    public int weaponSlot;
    static float bestSwordDamage;
    static boolean cleaning;
    static boolean sorting;
    int realSlot;
    int bestSword;
    public TimerUtil timer;
    static double a;
    
    public static InvManager getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(InvManager.class);
    }
    
    public InvManager() {
        this.Speed = new ValueProperty<Double>("Speed", 100.0, 50.0, 200.0, this);
        this.openinvBool = new BooleanProperty("OpenInv", true, this);
        this.organize = new BooleanProperty("Organize", true, this);
        this.throwFood = new BooleanProperty("Throw Food", true, this);
        this.swordSlot = new ValueProperty<Integer>("Sword Slot", 1, 1, 9, this);
        this.gapSlot = new ValueProperty<Integer>("Gapple Slot", 1, 1, 9, this);
        this.throwTools = new BooleanProperty("Throw Tools", true, this);
        this.mc = Minecraft.getMinecraft();
        this.hasSet = false;
        this.updateTimer = new TimerUtil();
        this.weaponSlot = 1;
        this.timer = new TimerUtil();
        this.onModuleDisabled = (() -> {});
        this.onModuleEnabled = (() -> {});
        this.swordSlot.onValueChange = (() -> this.realSlot = this.swordSlot.getPropertyValue() + 1);
        this.Speed.onValueChange = (() -> InvManager.a = this.Speed.getPropertyValue() * 2.0);
        boolean openinv;
        int i;
        ItemStack is;
        float prot;
        String strType;
        int type;
        int x;
        Minecraft mc;
        this.onUpdatePositionEvent = (e -> {
            if (this.updateTimer.hasReached((long)this.delay)) {
                this.hasSet = true;
            }
            if (!this.hasSet) {
                return;
            }
            else {
                openinv = this.openinvBool.getPropertyValue();
                if (openinv && !(this.mc.currentScreen instanceof GuiInventory)) {
                    return;
                }
                else {
                    this.weaponSlot = this.swordSlot.getPropertyValue();
                    if (this.weaponSlot == 0 || this.weaponSlot > 9) {
                        this.weaponSlot = 69;
                    }
                    --this.weaponSlot;
                    if (e.isPre()) {
                        if (this.mc.thePlayer != null && !InvManager.dropping && (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory) && this.timer.hasReached((long)this.delay)) {
                            for (i = 9; i < 45; ++i) {
                                if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                                    is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                                    if (this.isBad(is, i) && !(is.getItem() instanceof ItemArmor) && is != this.mc.thePlayer.getCurrentEquippedItem()) {
                                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, 0, 0, this.mc.thePlayer);
                                        InvManager.dropping = true;
                                        this.timer.reset();
                                        break;
                                    }
                                    else {
                                        if (this.weaponSlot < 10 && is.getItem() instanceof ItemSword && this.isBestWeapon(is) && 45 - i - 9 != this.weaponSlot && !InvManager.dropping) {
                                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, this.weaponSlot, 2, this.mc.thePlayer);
                                            this.timer.reset();
                                        }
                                        if (is.getItem() instanceof ItemArmor) {
                                            prot = AutoArmor.getProtection(is);
                                            strType = "";
                                            for (type = 0; type < 5; ++type) {
                                                if (type == 1) {
                                                    strType = "helmet";
                                                }
                                                else if (type == 2) {
                                                    strType = "chestplate";
                                                }
                                                else if (type == 3) {
                                                    strType = "leggings";
                                                }
                                                else if (type == 4) {
                                                    strType = "boots";
                                                }
                                                if (!is.getUnlocalizedName().contains(strType)) {
                                                    this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, 0, 0, this.mc.thePlayer);
                                                }
                                                InvManager.dropping = true;
                                                this.timer.reset();
                                                for (x = 5; x < 45; ++x) {
                                                    mc = Minecraft.getMinecraft();
                                                    if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                                                        if (AutoArmor.getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                                                            this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 0, mc.thePlayer);
                                                        }
                                                        InvManager.dropping = true;
                                                        this.timer.reset();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else if (InvManager.dropping && this.timer.hasReached((long)(this.delay / 2.0))) {
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, this.mc.thePlayer);
                            this.timer.reset();
                            InvManager.dropping = false;
                        }
                    }
                    return;
                }
            }
        });
        this.onModuleEnabled = (() -> InvManager.dropping = false);
    }
    
    private ItemStack bestSword() {
        ItemStack best = null;
        float swordDamage = 0.0f;
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is.getItem() instanceof ItemSword) {
                    final float swordD = this.getItemDamage(is);
                    if (swordD > swordDamage) {
                        swordDamage = swordD;
                        best = is;
                    }
                }
            }
        }
        return best;
    }
    
    private boolean isBad(final ItemStack stack, final int slot) {
        return !stack.getDisplayName().toLowerCase().contains("(right click)") && !stack.getDisplayName().toLowerCase().contains("§k||") && (slot != this.weaponSlot || !this.isBestWeapon(this.mc.thePlayer.inventoryContainer.getSlot(this.weaponSlot).getStack())) && ((stack.getItem() instanceof ItemSword && !this.isBestWeapon(stack)) || (stack.getItem() instanceof ItemPickaxe && !this.isBestPickaxe(stack)) || (stack.getItem() instanceof ItemAxe && !this.isBestAxe(stack)) || (stack.getItem().getUnlocalizedName().contains("shovel") && !this.isBestShovel(stack)) || (stack.getItem() instanceof ItemSword && this.weaponSlot > 10) || (stack.getItem() instanceof ItemPotion && this.isBadPotion(stack)) || (stack.getItem() instanceof ItemFood && this.throwFood.getPropertyValue() && !(stack.getItem() instanceof ItemAppleGold)) || (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemArmor) || (stack.getItem().getUnlocalizedName().contains("tnt") || stack.getItem().getUnlocalizedName().contains("stick") || stack.getItem().getUnlocalizedName().contains("egg") || stack.getItem().getUnlocalizedName().contains("string") || stack.getItem().getUnlocalizedName().contains("cake") || stack.getItem().getUnlocalizedName().contains("mushroom") || stack.getItem().getUnlocalizedName().contains("flint") || stack.getItem().getUnlocalizedName().contains("compass") || stack.getItem().getUnlocalizedName().contains("dyePowder") || stack.getItem().getUnlocalizedName().contains("feather") || stack.getItem().getUnlocalizedName().contains("bucket") || (stack.getItem().getUnlocalizedName().contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect")) || stack.getItem().getUnlocalizedName().contains("snow") || stack.getItem().getUnlocalizedName().contains("fish") || stack.getItem().getUnlocalizedName().contains("enchant") || stack.getItem().getUnlocalizedName().contains("exp") || stack.getItem().getUnlocalizedName().contains("shears") || stack.getItem().getUnlocalizedName().contains("anvil") || stack.getItem().getUnlocalizedName().contains("torch") || stack.getItem().getUnlocalizedName().contains("seeds") || stack.getItem().getUnlocalizedName().contains("leather") || stack.getItem().getUnlocalizedName().contains("reeds") || stack.getItem().getUnlocalizedName().contains("skull") || stack.getItem().getUnlocalizedName().contains("record") || stack.getItem().getUnlocalizedName().contains("snowball") || stack.getItem() instanceof ItemGlassBottle || stack.getItem().getUnlocalizedName().contains("piston")) || this.isDuplicate(stack, slot));
    }
    
    private List<ItemStack> getBest() {
        final List<ItemStack> best = new ArrayList<ItemStack>();
        for (int i = 0; i < 4; ++i) {
            ItemStack armorStack = null;
            for (final ItemStack itemStack : this.mc.thePlayer.inventory.armorInventory) {
                if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                    final ItemArmor stackArmor = (ItemArmor)itemStack.getItem();
                    if (stackArmor.armorType == i) {
                        armorStack = itemStack;
                    }
                }
            }
            final double reduction = (armorStack == null) ? -1.0 : this.getArmorStrength(armorStack);
            ItemStack slotStack = this.findBestArmor(i);
            if (slotStack != null && this.getArmorStrength(slotStack) <= reduction) {
                slotStack = armorStack;
            }
            if (slotStack != null) {
                best.add(slotStack);
            }
        }
        return best;
    }
    
    public boolean isDuplicate(final ItemStack stack, final int slot) {
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is != stack && slot != i && is.getUnlocalizedName().equalsIgnoreCase(stack.getUnlocalizedName()) && !(is.getItem() instanceof ItemPotion) && !(is.getItem() instanceof ItemBlock)) {
                    if (is.getItem() instanceof ItemSword) {
                        if (this.getDamage(is) == this.getDamage(stack)) {
                            return true;
                        }
                    }
                    else if (is.getItem() instanceof ItemTool) {
                        if (this.getToolEffect(is) == this.getToolEffect(stack)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
    
    private ItemStack findBestArmor(final int itemSlot) {
        ItemStack i = null;
        double maxReduction = 0.0;
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.mainInventory[slot];
            if (itemStack != null) {
                final double reduction = this.getArmorStrength(itemStack);
                if (reduction != -1.0) {
                    final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                    if (itemArmor.armorType == itemSlot && reduction >= maxReduction) {
                        maxReduction = reduction;
                        i = itemStack;
                    }
                }
            }
        }
        return i;
    }
    
    private double getArmorStrength(final ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemArmor)) {
            return -1.0;
        }
        float damageReduction = (float)((ItemArmor)itemStack.getItem()).damageReduceAmount;
        final Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
        if (enchantments.containsKey(Enchantment.protection.effectId)) {
            final int level = enchantments.get(Enchantment.protection.effectId);
            damageReduction += Enchantment.protection.calcModifierDamage(level, DamageSource.generic);
        }
        return damageReduction;
    }
    
    private boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private float getItemDamage(final ItemStack itemStack) {
        float damage = ((ItemSword)itemStack.getItem()).getDamageVsEntity();
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f;
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.01f;
        return damage;
    }
    
    private boolean isBestPickaxe(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBestShovel(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBestAxe(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !this.isBestWeapon(stack)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isBestWeapon(final ItemStack stack) {
        final float damage = this.getDamage(stack);
        for (int i = 0; i < 36; ++i) {
            if (this.mc.thePlayer.inventory.mainInventory[i] != null) {
                final ItemStack is = this.mc.thePlayer.inventory.mainInventory[i];
                if (this.getDamage(is) > damage && is.getItem() instanceof ItemSword) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private float getDamage(final ItemStack stack) {
        float damage = 0.0f;
        final Item item = stack.getItem();
        if (item instanceof ItemTool) {
            final ItemTool tool = (ItemTool)item;
            damage += tool.getDamage();
        }
        if (item instanceof ItemSword) {
            final ItemSword sword = (ItemSword)item;
            damage += sword.getAttackDamage();
        }
        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
        return damage;
    }
    
    private float getToolEffect(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        final String name = item.getUnlocalizedName();
        final ItemTool tool = (ItemTool)item;
        float value = 1.0f;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        else {
            if (!(item instanceof ItemAxe)) {
                return 1.0f;
            }
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        }
        value += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075);
        value += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0);
        return value;
    }
    
    static {
        InvManager.dropping = false;
    }
}
