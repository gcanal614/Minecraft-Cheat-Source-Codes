package me.module.impl.combat;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public class AutoPot extends Module {
  public AutoPot() {
    super("AutoPot", 0, Category.COMBAT);
  }
  public Setting delay;
 // public Setting speed;
 // public Setting health;
 // public Setting amount;
  private int timer;
  private int slot;
  
  private float oldPitch;
  
  @Override
public void setup() {   
      Hime.instance.settingsManager.rSetting(delay = new Setting("Throw Delay", this, 1000, 0, 5000, true));
    //  Hime.instance.settingsManager.rSetting(amount = new Setting("Health Amount", this, 12, 1, 20, true));
     // Hime.instance.settingsManager.rSetting(speed = new Setting("Speed", this, true));
     // Hime.instance.settingsManager.rSetting(health = new Setting("Health/Regen", this, true));
  }
 

  
  public TimeUtil time = new TimeUtil();
 
  /*@Handler
  public void onPre(EventUpdate event) {
    	this.setSuffix("" + delay.getValDouble());
    	int prevSlot = mc.thePlayer.inventory.currentItem;
        if(time.hasTimePassed((long) delay.getValDouble()) && !mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
        	   for (int x = 36; x < 45; x++) {
                    ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(x).getStack();
                    if(itemStack.getItem() instanceof ItemPotion && itemStack != null) {
                    	 if (ItemPotion.isSplash(itemStack.getItemDamage())) {
                    		 ItemPotion pot = (ItemPotion)itemStack.getItem();
                             if(pot.getEffects(itemStack).isEmpty())
                                 return;
                             
                    	        PotionEffect potionEffect = (PotionEffect) pot.getEffects(itemStack).get(0);
                    	        if(potionEffect.getPotionID() == Potion.moveSpeed.id) {
                    	        	  oldPitch = mc.thePlayer.rotationPitch;
                    	        	  mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
                                      mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(x - 36));
                                      mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                      mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                                      mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, oldPitch, mc.thePlayer.onGround));
                    	        }
                    	}
                    }
        	   }
        	time.reset();
        }
  }*/
  
  @Handler
  public void onPre(EventUpdate event) {
    	this.setSuffix("" + Math.round(delay.getValDouble()));
             final int prevSlot = mc.thePlayer.inventory.currentItem;
             
    		 if (!(this.mc.currentScreen instanceof GuiChest || this.mc.currentScreen instanceof GuiChat|| this.mc.currentScreen instanceof GuiInventory)) {

		         for(int i = 0; i < 9; i++) {
		             final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
		             if(itemStack != null && itemStack.getItem() instanceof ItemPotion) {
		            	  final ItemPotion potion = (ItemPotion) itemStack.getItem();
		                  if (ItemPotion.isSplash(itemStack.getItemDamage())) {
		                      for (final Object o : potion.getEffects(itemStack)) {
		                          final PotionEffect effect = (PotionEffect) o;
		                          if (effect.getPotionID() == Potion.heal.id || effect.getPotionID() == Potion.regeneration.id) {
		            	 // if (this.mc.thePlayer.inventory.getStackInSlot(i).getItem().getItemStackDisplayName(this.mc.thePlayer.inventory.getStackInSlot(i)).equalsIgnoreCase("Splash Potion of Regeneration") || this.mc.thePlayer.inventory.getStackInSlot(i).getItem().getItemStackDisplayName(this.mc.thePlayer.inventory.getStackInSlot(i)).equalsIgnoreCase("Splash Potion of Healing")) {
		            	           if (mc.thePlayer.getHealth() <= 13.0F) {
		            	        	   if(!mc.thePlayer.isPotionActive(Potion.regeneration)) {
		            	        		   if(time.hasTimePassed((long) delay.getValDouble())) {
		            	        			   if(mc.thePlayer.onGround) {
		            	        			   oldPitch = mc.thePlayer.rotationPitch;
		            	        			   mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
		            	        			   mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(i));
		            	        			   mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
		            	        			   mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
		            	        			   mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, oldPitch, mc.thePlayer.onGround));
		            	        			   time.reset();
		            	        		   }
		            	        		   }
		                      // }
		            	        	   }
		            	           }
		                          }else if (effect.getPotionID() == Potion.moveSpeed.id && effect.getPotionID() != Potion.jump.id) {
		                        		if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {   
		       		            		 if(time.hasTimePassed((long) delay.getValDouble())) {
		       		            		  if(mc.thePlayer.onGround) {
		       			                   	   oldPitch = mc.thePlayer.rotationPitch;
		                      	        	      mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
		                                          mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(i));
		                                          mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
		                                          mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
		                                          mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, oldPitch, mc.thePlayer.onGround));
		       			                       	  time.reset();
		       			                  }
		       		            		 }
		       		            		} 
		                          }
		                    }
		            	 /*}else if (this.mc.thePlayer.inventory.getStackInSlot(i).getItem().getItemStackDisplayName(this.mc.thePlayer.inventory.getStackInSlot(i)).equalsIgnoreCase("Splash Potion of Swiftness")) {
		            		if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {   
		            		 if(time.hasTimePassed((long) delay.getValDouble())) {
			                   	   oldPitch = mc.thePlayer.rotationPitch;
               	        	       mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89, mc.thePlayer.onGround));
                                   mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(i));
                                   mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                                   mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                                   mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, oldPitch, mc.thePlayer.onGround));
			                       time.reset();
			                  }
		            		}*/
		            	 }
		             }else {
		            //   if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {  
		            	//if(!mc.thePlayer.isPotionActive(Potion.regeneration)) {
                          this.movePotsToHotbar();
		            	// }
		             //  }
		             }
		         }
		        }
             }  

  

private void movePotsToHotbar() {
    boolean added = false;
    if (!isHotbarFull()) {
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            if (k > 8 && !added) {
                final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
                if (itemStack != null && this.isValid(itemStack)) {
                    shiftClick(k);
                    added = true;
                }
            }
        }
    }
}
private boolean isValid(ItemStack itemStack) {
    if (itemStack.getItem() instanceof ItemPotion) {
    	//if(ItemPotion.isSplash(itemStack.getItemDamage())) {
    	   return true;
    	// }
    }
    return false;
}
public static void shiftClick(int slot) {
    Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
}
public boolean isHotbarFull() {
    int count = 0;
    for (int k = 0; k < 9; ++k) {
        final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
        if (itemStack != null) {
            count++;
        }
    }
    return count == 8;
}
}

  
  
 
