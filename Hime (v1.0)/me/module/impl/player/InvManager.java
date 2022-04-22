package me.module.impl.player;

import me.Hime;
import org.lwjgl.input.Keyboard;

import io.netty.util.internal.ThreadLocalRandom;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;


public class InvManager extends Module {

	private TimeUtil timer = new TimeUtil();
	public Setting delay2;
	public Setting slot;
	public Setting slot2;
	public Setting slot3;
	public Setting slot4;
	public Setting clean2;
	public Setting cleanbad;
	public Setting inven;
	public Setting swing;
	public Setting toolslot;
	public Setting throwtool;
	
	public InvManager() {
		super("InvManager", Keyboard.KEY_Z, Category.PLAYER);
	}
	
	@Override
	public void setup() {
		Hime.instance.settingsManager.rSetting(delay2 = new Setting("Delay", this, 200, 0, 1000, true));
		Hime.instance.settingsManager.rSetting(toolslot = new Setting("Tool Slots", this, false));
		Hime.instance.settingsManager.rSetting(throwtool = new Setting("Throw Tools", this, false));
		Hime.instance.settingsManager.rSetting(slot  = new Setting("Sword Slot", this, 1, 1, 9, true));
		Hime.instance.settingsManager.rSetting(slot2  = new Setting("Pickaxe Slot", this, 3, 1, 9, true));
		Hime.instance.settingsManager.rSetting(slot3  = new Setting("Axe Slot", this, 4, 1, 9, true));
		Hime.instance.settingsManager.rSetting(slot4  = new Setting("Shovel Slot", this, 5, 1, 9, true));
		Hime.instance.settingsManager.rSetting(clean2 = new Setting("Clean", this, true));
		Hime.instance.settingsManager.rSetting(cleanbad = new Setting("CleanBadItems", this, true));
		Hime.instance.settingsManager.rSetting(inven = new Setting("On Inventory", this, false));
		Hime.instance.settingsManager.rSetting(swing = new Setting("Swing", this, false));
	}
	
	@Handler
	public void onUpdate(EventUpdate eu) {
		if(mc.currentScreen instanceof GuiChest) {
			return;
		}
		
		if(!(mc.currentScreen instanceof GuiInventory) && this.inven.getValBoolean()) {
			return;
		}
			double delay = Math.max(20, delay2.getValDouble() + ThreadLocalRandom.current().nextDouble(-20, 20));
			if (mc.currentScreen != null) {
				//timer.reset();
				//return;
			}
			if (timer.hasTimePassed((long) delay)) {
				int bestSword = this.getBestSword();
				int bestPick = this.getBestPickaxe();
				int bestAxe = this.getBestAxe();
				int bestShovel = this.getBestShovel();
				
				int bestDiamondArmor = this.getBestDiamondArmor();
				
				for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
					ItemStack is = mc.thePlayer.inventory.mainInventory[k];
					if (is != null && !(is.getItem() instanceof ItemArmor)) { // && !(is.getItem() instanceof ItemArmor)
						boolean clean = clean2.getValBoolean();
						if (clean) {
							if (is.getItem() instanceof ItemSword) {
								if (bestSword != -1 && bestSword != k) {
									this.drop(k, is);
									if(swing.getValBoolean() == true) {
										Minecraft.getMinecraft().thePlayer.swingItem();
									}
									timer.reset();
									return;
								}
							}
						  if(!this.throwtool.getValBoolean()) {
							if (is.getItem() instanceof ItemPickaxe) {
								if (bestPick != -1 && bestPick != k) {
									this.drop(k, is);
									if(swing.getValBoolean() == true) {
										Minecraft.getMinecraft().thePlayer.swingItem();
									}
									timer.reset();
									return;
								}
							}
							
							if (is.getItem() instanceof ItemAxe) {
								if (bestAxe != -1 && bestAxe != k) {
									this.drop(k, is);
									if(swing.getValBoolean() == true) {
										Minecraft.getMinecraft().thePlayer.swingItem();
									}
									timer.reset();
									return;
								}
							}
							
							if (this.isShovel(is.getItem())) {
								if (bestShovel != -1 && bestShovel != k) {
									this.drop(k, is);
									if(swing.getValBoolean() == true) {
										Minecraft.getMinecraft().thePlayer.swingItem();
									}
									timer.reset();
									return;
								}
							}
						  }else {
								if (is.getItem() instanceof ItemPickaxe) {
										this.drop(k, is);
										if(swing.getValBoolean() == true) {
											Minecraft.getMinecraft().thePlayer.swingItem();
										}
										timer.reset();
										return;
								}
								
								if (is.getItem() instanceof ItemAxe) {
										this.drop(k, is);
										if(swing.getValBoolean() == true) {
											Minecraft.getMinecraft().thePlayer.swingItem();
										}
										timer.reset();
										return;
								}
								
								if (this.isShovel(is.getItem())) {
										this.drop(k, is);
										if(swing.getValBoolean() == true) {
											Minecraft.getMinecraft().thePlayer.swingItem();
										}
										timer.reset();
										return;
								} 
						  }
							
							if (is.getItem() instanceof ItemArmor) {
								if (bestDiamondArmor != -1 && bestDiamondArmor != k) {
									Hime.addClientChatMessage("aa");
									this.drop(k, is);
									if(swing.getValBoolean() == true) {
										Minecraft.getMinecraft().thePlayer.swingItem();
									}
									timer.reset();
									return;
								}
							}
						}
						int swordSlot = slot.getValInt() - 1;
						if (bestSword != -1 && bestSword != swordSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestSword]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, swordSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}
					  if(this.toolslot.getValBoolean()) {
						int pickSlot = slot2.getValInt() - 1;
						if (bestPick != -1 && bestPick != pickSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestPick]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, pickSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}
						
						int axeSlot = slot3.getValInt() - 1;
						if (bestAxe != -1 && bestAxe != axeSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestAxe]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, axeSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}
						
						int shovelSlot = slot4.getValInt() - 1;
						if (bestShovel != -1 && bestShovel != shovelSlot) {
							for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
								Slot s = mc.thePlayer.inventoryContainer.inventorySlots.get(i);
								if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestShovel]) {
									mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, shovelSlot, 2, mc.thePlayer);
									timer.reset();
									return;
								}
							}
						}
					  }
						
						if (cleanbad.getValBoolean() && this.isBad(is.getItem())) {
							this.drop(k, is);
							if(swing.getValBoolean() == true) {
								Minecraft.getMinecraft().thePlayer.swingItem();
							}
							timer.reset();
							return;
						}
					}
				}
				timer.reset();
			}
	}
	
	private int getBestSword() {
		int bestSword = -1;
		float bestDamage = 1F;
		
		for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
			ItemStack is = mc.thePlayer.inventory.mainInventory[k];
			if (is != null && is.getItem() instanceof ItemSword) {
				ItemSword itemSword = (ItemSword) is.getItem();
				float damage = itemSword.getDamageVsEntity();
				damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
				if (damage > bestDamage) {
					bestDamage = damage;
					bestSword = k;
				}
			}
		}
		return bestSword;
	}
	
	private int getBestDiamondArmor() {
		int bestDiamond = -1;
		float bestDamage = 1F;
		
		for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
			ItemStack is = mc.thePlayer.inventory.mainInventory[k];
			if (is != null && is.getItem() instanceof ItemArmor) {
				ItemArmor itemArmor = (ItemArmor) is.getItem();
			//	float damage = itemArmor.getDamageVsEntity();
				float damage = 0;
				damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is);
				if (damage > bestDamage) {
					bestDamage = damage;
					bestDiamond = k;
				}
			}
		}
		return bestDiamond;
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
	    final int prevSlot = mc.thePlayer.inventory.currentItem;
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
		    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
			if(swing.getValBoolean() == true) {
				Minecraft.getMinecraft().thePlayer.swingItem();
			}
		} else {
			mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, mc.thePlayer);
			mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
			if(swing.getValBoolean() == true) {
				Minecraft.getMinecraft().thePlayer.swingItem();
			}
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
			//	i.getUnlocalizedName().contains("bucket") ||
				i.getUnlocalizedName().contains("feather") ||
				i.getUnlocalizedName().contains("snow") ||
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
