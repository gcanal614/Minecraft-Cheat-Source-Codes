package non.asset.module.impl.player;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import non.asset.event.bus.Handler;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.NumberValue;

public class InvManager extends Module {

    private NumberValue<Integer> delay = new NumberValue<>("Delay", 150, 50, 300, 10);
    private NumberValue<Float> swordSlotC = new NumberValue<>("Sword Slow", 0f, 0f, 9f, 1f);
    private NumberValue<Float> pickaxeSlotC = new NumberValue<>("Delay", 1f, 0f, 9f, 1f);
    private NumberValue<Float> axeSlotC = new NumberValue<>("Delay", 2f, 0f, 9f, 1f);
    
    private BooleanValue inventoryonly = new BooleanValue("Inventory Only",false);
    private TimerUtil timer = new TimerUtil();

    public InvManager() {
        super("InvManager", Category.PLAYER);
        setRenderLabel("InventoryManager");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
        	
        	int random = (Math.round(MathUtils.getRandomInRange(1, 75) / 50) * 50);
        	
        	if(timer.sleep(delay.getValue() + random)) {
				
				int bestSword = this.getBestSword();
				int bestPick = this.getBestPickaxe();
				int bestAxe = this.getBestAxe();
				int bestShovel = this.getBestShovel();
				
				for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
					ItemStack is = mc.thePlayer.inventory.mainInventory[k];
					if (is != null && !(is.getItem() instanceof ItemArmor)) {
						
						float swordSlot = (float) swordSlotC.getValue();
						
						float axeSlot = (float) axeSlotC.getValue();

						float pickaxeSlot = (float) pickaxeSlotC.getValue();
					
						if (bestSword != -1 && bestSword != swordSlot && bestSword != axeSlot && bestSword != pickaxeSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = (Slot) mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestSword]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, (int) swordSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}
						
						
						if (bestAxe != -1 && bestAxe != axeSlot && bestAxe != swordSlot && axeSlot != pickaxeSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = (Slot) mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestAxe]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, (int) axeSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}

						
						if (bestPick != -1 && bestPick != pickaxeSlot && bestPick != axeSlot && bestPick != swordSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = (Slot) mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestPick]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, (int) pickaxeSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}
					
					}
				}	
			timer.reset();
		}            
    }
}
private int getBestSword() {
	int bestSword = -1;
	float bestDamage = 1F;
	
	for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
		ItemStack is = mc.thePlayer.inventory.mainInventory[k];
		if (is != null && is.getItem() instanceof ItemSword) {
			ItemSword itemSword = (ItemSword) is.getItem();
			float damage = (float) itemSword.getDamageVsEntity();
			damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
			if (damage > bestDamage) {
				bestDamage = damage;
				bestSword = k;
			}
		}
	}
	return bestSword;
}

private int getBestPickaxe() {
	int bestPick = -1;
	float bestDamage = 1F;
	
	for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
		ItemStack is = mc.thePlayer.inventory.mainInventory[k];
		if (is != null && is.getItem() instanceof ItemPickaxe) {
			ItemPickaxe itemSword = (ItemPickaxe) is.getItem();
			float damage = itemSword.getStrVsBlock(is, Block.getBlockById(4));
			if (damage > bestDamage) {
				bestDamage = damage;
				bestPick = k;
			}
		}
	}
	return bestPick;
}

private int getBestAxe() {
	int bestPick = -1;
	float bestDamage = 1F;
	
	for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
		ItemStack is = mc.thePlayer.inventory.mainInventory[k];
		if (is != null && is.getItem() instanceof ItemAxe) {
			ItemAxe itemSword = (ItemAxe) is.getItem();
			float damage = itemSword.getStrVsBlock(is, Block.getBlockById(17));
			if (damage > bestDamage) {
				bestDamage = damage;
				bestPick = k;
			}
		}
	}
	return bestPick;
}

private int getBestShovel() {
	int bestPick = -1;
	float bestDamage = 1F;
	
	for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
		ItemStack is = mc.thePlayer.inventory.mainInventory[k];
		if (is != null && this.isShovel(is.getItem())) {
			ItemTool itemSword = (ItemTool) is.getItem();
			float damage = itemSword.getStrVsBlock(is, Block.getBlockById(3));
			if (damage > bestDamage) {
				bestDamage = damage;
				bestPick = k;
			}
		}
	}
	return bestPick;
}

private boolean isShovel(Item is) {
	return Item.getItemById(256) == is || Item.getItemById(269) == is || Item.getItemById(273) == is || Item.getItemById(277) == is || Item.getItemById(284) == is;
}

private void drop(int slot, ItemStack item) {
	boolean hotbar = false;
	for (int k = 0; k < 9; k++) {
		ItemStack itemK = mc.thePlayer.inventory.getStackInSlot(k);
		if (itemK != null && itemK == item) {
			hotbar = true;
			continue;
		}
	}
	
	if (hotbar) {
		mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
		C07PacketPlayerDigging.Action diggingAction = C07PacketPlayerDigging.Action.DROP_ALL_ITEMS;
		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(diggingAction, BlockPos.ORIGIN, EnumFacing.DOWN));
	} else {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, mc.thePlayer);
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
	}
}

private boolean isBad(Item i) {

	
	return i.getUnlocalizedName().contains("tnt") ||
			i.getUnlocalizedName().contains("stick") ||
			i.getUnlocalizedName().contains("egg") ||
			i.getUnlocalizedName().contains("string") ||
			i.getUnlocalizedName().contains("flint") ||
			
			//i.getUnlocalizedName().contains("bow") ||
			//i.getUnlocalizedName().contains("arrow") ||
			
			i.getUnlocalizedName().contains("bucket") ||
			i.getUnlocalizedName().contains("feather") ||
			i.getUnlocalizedName().contains("snow") ||
			i.getUnlocalizedName().contains("rotten") ||
			i.getUnlocalizedName().contains("piston") || 
			i instanceof ItemGlassBottle ||
			i.getUnlocalizedName().contains("web") ||
			i.getUnlocalizedName().contains("slime") ||
			i.getUnlocalizedName().contains("trip") ||
			i.getUnlocalizedName().contains("wire") ||
			i.getUnlocalizedName().contains("sugar") ||
			i.getUnlocalizedName().contains("note") ||
			i.getUnlocalizedName().contains("record") ||
			i.getUnlocalizedName().contains("flower") ||
			i.getUnlocalizedName().contains("wheat") ||
			i.getUnlocalizedName().contains("fishing") ||
			i.getUnlocalizedName().contains("boat") ||
			i.getUnlocalizedName().contains("leather") ||
			i.getUnlocalizedName().contains("seeds") ||
			i.getUnlocalizedName().contains("skull") ||
			i.getUnlocalizedName().contains("torch") ||
			i.getUnlocalizedName().contains("anvil") ||
			i.getUnlocalizedName().contains("enchant") ||
			i.getUnlocalizedName().contains("exp") ||
			i.getUnlocalizedName().contains("shears");
}
}


