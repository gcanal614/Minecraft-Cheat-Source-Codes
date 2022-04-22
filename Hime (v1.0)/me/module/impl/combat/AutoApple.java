package me.module.impl.combat;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

public class AutoApple extends Module {
  public AutoApple() {
    super("AutoGapple", 0, Category.COMBAT);
  }
  public Setting delay;
  private int slot;
  
  @Override
  public void setup() {   
      Hime.instance.settingsManager.rSetting(delay = new Setting("Eat Delay", this, 1000, 0, 5000, true));
    //  Hime.instance.settingsManager.rSetting(amount = new Setting("Health Amount", this, 12, 1, 20, true));
     // Hime.instance.settingsManager.rSetting(speed = new Setting("Speed", this, true));
     // Hime.instance.settingsManager.rSetting(health = new Setting("Health/Regen", this, true));
  }
 
  
  public void onToggle() {
	  super.onToggle();
	  this.time.reset();
  }

  
  public TimeUtil time = new TimeUtil();

  
  @Handler
  public void onPre(EventUpdate event) {
             final int prevSlot = mc.thePlayer.inventory.currentItem;
    		 if (!(this.mc.currentScreen instanceof GuiChest || this.mc.currentScreen instanceof GuiChat|| this.mc.currentScreen instanceof GuiInventory)) {
		         for(int i = 0; i < 9; i++) {
		             final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
		             if(itemStack != null && itemStack.getItem() instanceof ItemAppleGold) {
		            	   if (mc.thePlayer.getHealth() <= 13.0F) {
		       	    //    if(time.hasTimePassed((long) delay.getValDouble())) {
		            		
		                     mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = i));
		                     Hime.addClientChatMessage("a" + i);
		                     mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, mc.thePlayer.inventory.getCurrentItem(), 0, 0, 0));
		                     // mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
		                    if(time.hasTimePassed(100)) {
		                    // mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = prevSlot));
		                    time.reset();
		                    }
		                     //  time.reset();
		       	        }
		           // }
		         }
		        }
    		 }
    }  

}

  
  
 
