package me.module.impl.other;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventOpenChest;
import me.event.impl.EventRenderHUD;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.PlayerUtils;
import me.util.TimeUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;

public class ChestStealer extends Module{
	

	public Setting min;
	public Setting max;
	public Setting auto;
	public Setting ignore;
	public Setting nogui;
	
	public Setting aura; 
	public Setting aurarange; 
	public Setting auradelay;
	public Setting guitext;
	
	public boolean chestopen = false;
	public boolean canRotate = false;
	ContainerChest chest;
	
	protected int delayOver;
	protected BlockPos openNext = null, pos = null;
	public ArrayList opened = new ArrayList<>();
	
	
	  public ChestStealer() {
		    super("ChestStealer", Keyboard.KEY_Z, Category.MISC);
	  }
	    @Override
		public void setup() {
			 Hime.instance.settingsManager.rSetting(min = new Setting("Steal Delay Min", this, 150, 0, 2000, true));
			 Hime.instance.settingsManager.rSetting(max = new Setting("Steal Delay Max", this, 190, 0, 2000, true));
			 Hime.instance.settingsManager.rSetting(auto = new Setting("Auto Close", this, true));
			 Hime.instance.settingsManager.rSetting(ignore = new Setting("Ignore bad", this, true));
			 Hime.instance.settingsManager.rSetting(nogui = new Setting("No Chest Gui", this, false));
			 Hime.instance.settingsManager.rSetting(guitext = new Setting("Show Text When Silent", this, false));
			 Hime.instance.settingsManager.rSetting(aura = new Setting("Chest Aura", this, false));
			 Hime.instance.settingsManager.rSetting(aurarange = new Setting("Chest Aura Range", this, 4.5, 0.1, 6, false));
			 Hime.instance.settingsManager.rSetting(auradelay = new Setting("Chest Aura Delay", this, 2.5, 0.1, 10, false));
		}
	  public double delay = 0;
	  TimeUtil time = new TimeUtil();
	  
	  public void onDisable() {
		  super.onDisable();
		  this.chestopen = false;
		  this.canRotate = false;
		  this.delayOver = 0;
		  pos = null;
		  this.opened.clear();
	  }
	  
	 // public void updateSettings() {
		//  delay = ThreadLocalRandom.current().nextDouble(Hime.instance.settingsManager.getSettingByName("Steal Delay Min").getValDouble(), Hime.instance.settingsManager.getSettingByName("Steal Delay Max").getValDouble());
// if(Hime.instance.settingsManager.getSettingByName("Steal Delay Min").getValDouble() >= Hime.instance.settingsManager.getSettingByName("Steal Delay Max").getValDouble()) {
			//  delay = delay - 1;
		 // }  
	//  }
	  
	    @Handler
	    public void onRender2D(EventRenderHUD event) {
	    	 ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
	    	if(this.nogui.getValBoolean()) {
	    		if(this.chestopen) {
					if(this.chest != null) {
					try {
				     if(this.guitext.getValBoolean())
			          Hime.instance.rfrs.drawCenteredString("Stealing Chest", (sr.getScaledWidth() / 2) + 0.1F, (sr.getScaledHeight() / 2) + Hime.instance.rfrs.getHeight("Stealing Chest") + 0.1F, -1);
					}catch(Exception e) {
						e.printStackTrace();
					}
					}
	    		}
	    	}
	    }
	  
	    @Handler
		public void onChestOpen(EventOpenChest event) {
	    	this.chestopen = true;
	    	this.chest = event.getChest();
	       if(this.nogui.getValBoolean()) {
			  Thread t = new Thread(new Runnable() {
	              @Override
	              public void run() {
	            	  mc.displayGuiScreen(null);
	              }
	          });
	          t.start();
			mc.displayGuiScreen(null);
			//Hime.addClientChatMessage("ee" + Math.random());
	       }
		}
	  public static int randomNumber(int max, int min) {
		    return Math.round(min + (float)Math.random() * (max - min));
		  }
		  
	    @Handler
	    public void onUpdate(EventUpdate event) {
	    	if(this.nogui.getValBoolean()) {
	    		setSuffix("Silent");
	    	}else {
	          this.setSuffix("Normal");
	    	}
		//	  this.updateSettings();
			if(this.nogui.getValBoolean()) {
				if(this.chestopen) {
					if(this.chest != null) {
					try {
					  ContainerChest chest = this.chest;
					  if ((this.isChestEmpty() && auto.getValBoolean() && time.hasTimePassed((long) this.randomNumber((int)min.getValDouble(), (int)max.getValDouble())))) {
							Minecraft.getMinecraft().thePlayer.closeScreen();
							this.chestopen = false;
							time.reset();
							return;
						}
					  for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
						  final ItemStack stack = chest.lowerChestInventory.getStackInSlot(i);
						  if((chest.getLowerChestInventory().getStackInSlot(i) != null) && time.hasTimePassed((long) this.randomNumber((int)min.getValDouble(), (int)max.getValDouble())) && (!PlayerUtils.isBad(stack) || ignore.getValBoolean())) {// && (!PlayerUtils.isBad(stack) || ignore.getValBoolean())
							//if(mc.thePlayer.isAllowEdit()) {
							  mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
							//}
							  this.time.reset();
						  }
					  }
					}catch(Exception e) {e.printStackTrace();}
				  }
				}
			}else {
			 if((mc.thePlayer.openContainer != null) && ((mc.thePlayer.openContainer instanceof ContainerChest))) {
				  ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
				  if ((this.isChestEmpty() && auto.getValBoolean() && time.hasTimePassed((long) this.randomNumber((int)min.getValDouble(), (int)max.getValDouble())))) {
						Minecraft.getMinecraft().thePlayer.closeScreen();
						time.reset();
						return;
					}
				  for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
					  final ItemStack stack = chest.lowerChestInventory.getStackInSlot(i);
					  if((chest.getLowerChestInventory().getStackInSlot(i) != null) && time.hasTimePassed((long) this.randomNumber((int)min.getValDouble(), (int)max.getValDouble())) && (!PlayerUtils.isBad(stack) || ignore.getValBoolean())) { //  && (!PlayerUtils.isBad(stack) || ignore.getValBoolean())
						//if(mc.thePlayer.isAllowEdit()) {
						  mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
						//}
						  this.time.reset();
					  }
				  }
			  }
			}
			
			//chest aura
			if(this.aura.getValBoolean()) {
				if(openNext != null) {
			//	if(this.canRotate) {
					float facing[] = me.util.setBlockAndFacing.BlockUtil.getDirectionToBlock(pos.getX(), pos.getY(), pos.getZ(), mc.thePlayer.getHorizontalFacing());
				  event.setYaw(facing[0]);
				  event.setPitch(facing[1]);
				 // Hime.addClientChatMessage("aa" + Math.random());
				//}
				}
				
				if(!mc.thePlayer.isUsingItem()) {
					
					if(openNext != null) {
						this.openChest(this.openNext);
						this.openNext = null;
					}else {
						canRotate = false;
					}
					
					if(this.delayOver < this.auradelay.getValDouble()) {
						this.delayOver++;
					}else {
						delayOver = 0;
						for(double x = -this.aurarange.getValDouble(); x < this.aurarange.getValDouble(); x++) {
							for(double y = -this.aurarange.getValDouble(); y < this.aurarange.getValDouble(); y++) {
								for(double z = -this.aurarange.getValDouble(); z < this.aurarange.getValDouble(); z++) {
									
									final BlockPos pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
									
									if(!this.opened.contains(pos) && Block.getIdFromBlock(mc.theWorld.getBlockState(pos).getBlock()) == Block.getIdFromBlock(Blocks.chest) && Math.sqrt(mc.thePlayer.getDistanceSq(pos)) < this.aurarange.getValDouble()) {
										
										this.pos = pos;
										
										mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
										this.openNext = pos;
										
									}
								}
							}
						}
					}
				}
			}
		  }
	  
	public void openChest(BlockPos pos) {
		this.canRotate = true;
		mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(pos, 
				(double) pos.getY() + 0.5D < mc.thePlayer.posY + 1.7D ? 1 : 0,
						mc.thePlayer.getCurrentEquippedItem(), 
						0.0f, 0.0f, 0.0f));
		mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
		this.opened.add(pos);
	}
	    
	private boolean isChestEmpty() {
	    try {
			if (this.chestopen) {
			//	final GuiChest chest = (GuiChest)mc.currentScreen;
		        for (int index = 0; index < this.chest.lowerChestInventory.getSizeInventory(); ++index) {
		            final ItemStack stack = this.chest.lowerChestInventory.getStackInSlot(index);
		            if (stack != null && (!PlayerUtils.isBad(stack) || ignore.getValBoolean())) { //  && (!PlayerUtils.isBad(stack) || !ignore.getValBoolean())
		                return false;
		            }
		        }
			}
	    }catch(Exception e) {e.printStackTrace();}
	        return true;
		}
}
