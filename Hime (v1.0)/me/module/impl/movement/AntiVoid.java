package me.module.impl.movement;



import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module{

	public Setting mode;
    private boolean motion;
    private BlockPos lastSafePos;
	public AntiVoid() {
		super("AntiVoid", 0, Category.MOVEMENT);
		 ArrayList<String> options2 = new ArrayList<String>();
	     options2.add("Vanilla");
	     options2.add("Hypixel");
	     options2.add("Watchdog");
	     options2.add("Redesky");
	     options2.add("Latingamers");
	     Hime.instance.settingsManager.rSetting(mode = new Setting("Antivoid mode", this, "Hypixel", options2));
	}

	 @Handler
	 public void onSend(EventSendPacket event) {
		if(this.mode.getValString().equalsIgnoreCase("Watchdog")) {
		 if(!isBlockUnder()) {
             if (event.getPacket() instanceof C03PacketPlayer) {
                 event.cancel();
             }
         }
		}
	 }
	
	  @Handler
	  public void onUpdate(EventUpdate event) {	
		  this.setSuffix(mode.getValString());
			 if(this.mode.getValString().equalsIgnoreCase("Vanilla")) {
		        if (mc.thePlayer.onGround) {
		        	lastSafePos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		        }
		        if(!isBlockUnder()) {
		            if(mc.thePlayer.fallDistance > 2.9f) {
		            	if (motion) {

		                    mc.thePlayer.setPosition(lastSafePos.getX(), lastSafePos.getY(), lastSafePos.getZ());
		            		motion = false;
		            	} else { 
		                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(lastSafePos.getX(), lastSafePos.getY(), lastSafePos.getZ(), true));
		                    mc.thePlayer.fallDistance = 0;
		            	}
		            } else {
		            	motion = true;
		            }
		        } else {
		        	motion = true;
		        }
			 }
			 if(this.mode.getValString().equalsIgnoreCase("Watchdog")) {
				 if (mc.thePlayer.onGround) {
	                   lastSafePos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
	               }
	               if(!isBlockUnder()) {
	                   if(mc.thePlayer.fallDistance > 5.9f) {
	                       if (motion) {

	                           mc.thePlayer.setPosition(lastSafePos.getX(), lastSafePos.getY(), lastSafePos.getZ());
	                           motion = false;
	                       } else { 
	                           mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(lastSafePos.getX(), lastSafePos.getY(), lastSafePos.getZ(), true));
	                           mc.thePlayer.fallDistance = 0;
	                       }
	                   } else {
	                       motion = true;
	                   }
	               } else {
	                   motion = true;
	               } 
			 }
			 if(this.mode.getValString().equalsIgnoreCase("Latingamers")) {
	              if (mc.thePlayer.fallDistance > 3.0f && !isBlockUnder()) {
	                  if(!mc.thePlayer.onGround) {
	                      mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + 5.5f, mc.thePlayer.posY + mc.thePlayer.posY, mc.thePlayer.posZ + 5.5f, true));
	                  }
	              }
	          }
			 if(event.isPre()) {
				 if(this.mode.getValString().equalsIgnoreCase("Hypixel")) {
					 if (mc.thePlayer.fallDistance > 3.0F && !isBlockUnder() && mc.thePlayer.ticksExisted % 3 == 0 && !Hime.instance.moduleManager.getModule("Flight").isToggled() && !Hime.instance.moduleManager.getModule("HighJump").isToggled())
				          event.setY(event.getY() + 8.0D); 
					 
				 }else if(this.mode.getValString().equalsIgnoreCase("Redesky")) {
					 
				 }
			 }
		
	}
	public static boolean isBlockUnder() {
        if(Minecraft.getMinecraft().thePlayer.posY < 0)
            return false;
        for(int off = 0; off < (int)Minecraft.getMinecraft().thePlayer.posY+2; off += 2){
            AxisAlignedBB bb = Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0, -off, 0);
            if(!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, bb).isEmpty()){
                return true;
            }
        }
        return false;
    }
	
}
