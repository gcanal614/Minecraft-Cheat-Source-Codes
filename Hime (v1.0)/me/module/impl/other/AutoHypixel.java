package me.module.impl.other;


import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;

public class AutoHypixel extends Module{

	private ArrayList<String> triggers = new ArrayList<String>();
	TimeUtil time = new TimeUtil();
	private boolean paper = false;  
	public String type;
	int prevSlot;
	public Setting paper2;
	
	public AutoHypixel() {
		super("AutoHypixel", 0, Category.MISC);
		triggers.add("The game starts in");
		Hime.instance.settingsManager.rSetting(paper2 = new Setting("Paper Challenge", this, false));
	
	}
	public void onDisable() {
		super.onDisable();
		
	}
	
	   @Handler
	   public void onUpdate(EventUpdate event) {
		   if (mc.thePlayer.ticksExisted < 5) {
	            paper = false;
	        }
	   }
	
        /* made by auth and me */
	    @Handler
	    public void onReceive(EventReceivePacket event) {
	     if(this.paper2.getValBoolean()) {
	      if (event.getPacket() instanceof S02PacketChat) {
	        S02PacketChat packet = (S02PacketChat)event.getPacket();
	        String message = packet.getChatComponent().getUnformattedText();
	            if (!message.isEmpty()) {
	            if(!this.paper) {
	          	 switch(message) {
	              case "The game starts in 5 seconds!":
	                        prevSlot = mc.thePlayer.inventory.currentItem;
	                        mc.thePlayer.closeScreen();
	                        mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(7));
	                        mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
	                    break;

	                case "The game starts in 4 seconds!":
	                        if (mc.thePlayer.openContainer instanceof ContainerChest) {
	                            final ContainerChest containerChest = (ContainerChest) mc.thePlayer.openContainer;

	                            if (containerChest.getLowerChestInventory().getStackInSlot(7) != null) {
	                                mc.playerController.windowClick(containerChest.windowId, 7, 0, 3, mc.thePlayer);
	                            }

	                            mc.thePlayer.closeScreen();
	                        }

	                        mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(prevSlot));
	                        mc.thePlayer.closeScreen();
	                    break;
	                    
	                case "You have activated the Paper Challenge!":
	                	//Hime.addClientChatMessage("test");
	                    mc.thePlayer.closeScreen();
	                    paper = true;
	                    mc.thePlayer.closeScreen();
	                    break;
	      			}
	            	}
	          	}
	      }
	      }
	  }
}