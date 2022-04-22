package me.module.impl.player;

import me.event.impl.EventReceivePacket;
import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.module.impl.movement.AntiVoid;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;

public class Nofall extends Module {
	public String mode;
  public Nofall() {
    super("Nofall", 49, Category.PLAYER);
  }
  @Override
  public void setup() {
      this.addModes("Nofall Mode", "AAC", "Vanilla", "MLG", "NCP", "Ban", "Hypixel", "Edit", "Watchdog", "Ground Spoof");
      this.mode = this.getModes("Nofall Mode");
  }
  
  @Handler
  public void onSend(EventSendPacket event) {
	 if(this.mode.equalsIgnoreCase("Watchdog")) {
      if (event.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance > 2.5 && AntiVoid.isBlockUnder()) {
          event.cancel();
      }
	 }
  }

	@Handler
	public void onReceive(EventReceivePacket event) {
		if (event.getPacket() instanceof S02PacketChat) {
			S02PacketChat packet = (S02PacketChat)event.getPacket();
			String message = packet.getChatComponent().getUnformattedText();
			if(message.contains("The game starts in 2 seconds!") && this.isToggled()){
				NotificationManager.show(new Notification(NotificationType.WARNING, "Nofall Alert", "NoFall Disabled To Prevent Bans!", 2));
				this.toggle();
			}
		}
	}

  @Handler
  public void onUpdate(EventUpdate event) {
	   this.mode = this.getModes("Nofall Mode");
    	 this.setSuffix(mode);
    	 switch(mode) {
    	 case "NCP":
    		   BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 6.0D, mc.thePlayer.posZ);
    	        Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
    	        BlockPos blockPos2 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 5.0D, mc.thePlayer.posZ);
    	        Block block2 = Minecraft.getMinecraft().theWorld.getBlockState(blockPos2).getBlock();
    	        BlockPos blockPos3 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 4.0D, mc.thePlayer.posZ);
    	        Block block3 = Minecraft.getMinecraft().theWorld.getBlockState(blockPos3).getBlock();
    	        if ((block != Blocks.air || block2 != Blocks.air || block3 != Blocks.air) && mc.thePlayer.fallDistance > 2.0F) {
    	            mc.getNetHandler().addToSendQueue( new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
    	                    mc.thePlayer.posY + 0.1D, mc.thePlayer.posZ, false));
    	            mc.thePlayer.motionY = -10.0D;
    	            mc.thePlayer.fallDistance = 0.0F;
    	        }
    	     break;
    	 case "Ground Spoof":
    		 if(this.mc.thePlayer.fallDistance > 2.0F) {
    			 event.setGround(true);
    		 }
    		 break;
    	case"Hypixel":
    		 // if(this.mc.thePlayer.fallDistance > 3f) {
    		     // this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true)); 
    			  //mc.thePlayer.onGround = true;
    			 // event.setGround(true);
    		// } 
    	     if(this.mc.thePlayer.fallDistance > 2.0F) {
    	         this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true)); 
    	          }
    	     break;
    	case"Edit":
   		  if(this.mc.thePlayer.fallDistance > 2.5F) {
   			  mc.thePlayer.onGround = true;
   		  }
   		  break;
    	case"Ban":
    		 if(this.mc.thePlayer.fallDistance > 2.0F) {
    			 mc.thePlayer.onGround=true;
    		 }
    	 break;
        case"Vanilla":
      if(this.mc.thePlayer.fallDistance > 2.0F) {
       this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true)); 
        }
      break;
   case "AAC":
    	  if(mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isBurning()) {
        	  mc.thePlayer.motionY = -6;
    	 }
    	 break;
        }
  }
    
   
  
  @Handler
  public void onPreUpdate(EventUpdate event) {
  			switch(mode) {
  			case "MLG":
  		    	 int slot = -1;
  		    	
  		    	  if(this.mc.thePlayer.fallDistance > 2.0F) {
  		    	

  		         for(int i = 0; i < 9; i++) {
  		             final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

  		             if(itemStack != null && itemStack.getItem() instanceof ItemBucket) {
  		            //     final float itemDamage = ((ItemSword) itemStack.getItem()).func_150931_i() + 4F;

  		               //  if(itemDamage > bestDamage) {
  		                    // bestDamage = itemDamage;
  		            	 event.setPitch(455);
  		            	 mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(i));
  		               
  		                    
  		             
  		            	 mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
  		                     
  		                // }
  		             }
  		         }
  		      
  		         if(slot != -1 && slot != mc.thePlayer.inventory.currentItem) {
  		             mc.thePlayer.inventory.currentItem = slot;
  		             mc.playerController.updateController();
  		             }
  		           }else {
  		       
  		           }
  		    	  break;
  		        }
  		}
  	
  }

  	   

