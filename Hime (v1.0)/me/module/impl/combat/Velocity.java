package me.module.impl.combat;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {
	
	
public Velocity() {
		super("Velocity", Keyboard.KEY_NONE, Category.COMBAT);
	}
public Setting mode;
public Setting h;
public Setting debug;
public Setting v;

@Override
public void setup() {
	Hime.instance.settingsManager.rSetting(this.h = new Setting("Horizontal", this, 100.0D, 0.0D, 100.0D, true));
	 Hime.instance.settingsManager.rSetting(this.v = new Setting("Vertical", this, 100.0D, 0.0D, 100.0D, true));
    ArrayList<String> options = new ArrayList<String>();
    options.add("NCP");
    options.add("GommeHD");
    options.add("AAC");
    options.add("AACTest");
    options.add("Intave");
    options.add("Cancel");
    options.add("Percentage");
    options.add("NoPacket");
    Hime.instance.settingsManager.rSetting(mode = new Setting("Velocity Mode", this, "Cancel", options));
    Hime.instance.settingsManager.rSetting(debug = new Setting("Debug", this, false));
}


  @Override
  public void onDisable() {
    super.onDisable();
  }
  @Handler
  public void onReceivePacket(EventReceivePacket event) {
	  if(mode.getValString().equalsIgnoreCase("Intave")) {
		  Packet packet = event.getPacket();
		  if (packet instanceof S27PacketExplosion) {
			  event.cancel();
		  }
	  }
	  if(mode.getValString().equalsIgnoreCase("Cancel")) {
	  Packet packet = event.getPacket();
	    if (packet instanceof S27PacketExplosion && mc.thePlayer.hurtTime > 1) {
	    	S27PacketExplosion velocityPacket = (S27PacketExplosion) event.getPacket();
	  	  if(this.debug.getValBoolean()) {
	    		Hime.addCustomChatMessage(ChatFormatting.BLUE + "Velocity" + ChatFormatting.GRAY + " >> ", ChatFormatting.WHITE + "" + velocityPacket.getStrength());
	      }
	    }if (packet instanceof S12PacketEntityVelocity && mc.thePlayer.hurtTime > 1) {
	    	S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity) event.getPacket();
	    	if(this.debug.getValBoolean()) {
	    		Hime.addCustomChatMessage(ChatFormatting.BLUE + "Velocity" + ChatFormatting.GRAY + " >> ", ChatFormatting.WHITE + "" + velocityPacket.getMotionX() + " " + velocityPacket.getMotionY() + " " + velocityPacket.getMotionZ());
	      }
	    }
      if (packet instanceof S12PacketEntityVelocity || packet instanceof S27PacketExplosion) {
         event.cancel();
       }
      }
  }
  @Handler
  public void onUpdate(EventUpdate event) {
    	 this.setSuffix(mode.getValString());
    	 
         if(mode.getValString().equalsIgnoreCase("AAC")) {
        	 if(mc.thePlayer.hurtTime > 0) {
        		 mc.thePlayer.onGround = true;
        	 }
         }
         if(mode.getValString().equalsIgnoreCase("AACTest")) {
        	 if(mc.thePlayer.hurtTime > 0) {
        		 mc.thePlayer.motionZ *= 0;
        		 
        		 mc.thePlayer.motionX *= 0;
        		 
        		 mc.thePlayer.onGround = true;
        	 }
         }
         if(mode.getValString().equalsIgnoreCase("NoPacket")) {
        
        	 if(mc.thePlayer.hurtTime > 2) {
    			 mc.thePlayer.motionX=0;
    			// mc.thePlayer.motionY=0;
    			 mc.thePlayer.motionZ=0;
    			 mc.thePlayer.setSpeed(0.264F);
    			// mc.thePlayer.setSpeed(0.1F);
    		 }
         }
         if(mode.getValString().equalsIgnoreCase("NCP")) {
        
     		if(mc.thePlayer.hurtTime > 0 && mc.thePlayer.hurtTime <= 6) {
			      this.mc.thePlayer.motionX *= 0.6F;
			      this.mc.thePlayer.motionZ *= 0.6F;
			      this.mc.thePlayer.motionY *= 0.6F;
			   } 
      }
         if(mode.getValString().equalsIgnoreCase("Percentage")) {
        		float horizontal = (float) Hime.instance.settingsManager.getSettingByName("Horizontal").getValDouble();
        		float vertical = (float) Hime.instance.settingsManager.getSettingByName("Vertical").getValDouble();
        		if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
        			mc.thePlayer.motionX *= (float) horizontal / 100;
        			mc.thePlayer.motionY *= (float) vertical / 100;
        			mc.thePlayer.motionZ *= (float) horizontal / 100;
        		}
			}
     if(mode.getValString().equalsIgnoreCase("GommeHD")) {
    	
    	 if(this.mc.thePlayer.hurtTime > 0) {
 			/* Flag Velocity */
 			this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.26, this.mc.thePlayer.posZ);
 			this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.3, this.mc.thePlayer.posZ);
 		}
     }
  // super.
  }
  
}
         
    
    
  

