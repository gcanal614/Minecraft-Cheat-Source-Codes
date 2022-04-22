package me.module.impl.movement;

import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;


public class Step extends Module {

	int ticks;
  public Step() {
    super("Step", 0, Category.MOVEMENT);
  }
  public Setting mode;
  public Setting height;
  @Override
  public void setup() {
      Hime.instance.settingsManager.rSetting(height = new Setting("Step Height", this, 2, 1, 20, true));
      ArrayList<String> options = new ArrayList<String>();
      options.add("Vanilla");
      options.add("AAC");
      options.add("NCP");
      options.add("Legit");
      options.add("Redesky");
      Hime.instance.settingsManager.rSetting(mode = new Setting("Step Mode", this, "Vanilla", options));
  }

  
  public void onDisable() {
	  mc.thePlayer.stepHeight = .5F;
    super.onDisable();
  }
  
  @Handler
  public void onUpdate(EventUpdate event) {
    	this.setSuffix(mode.getValString());
   	 if(mode.getValString().equalsIgnoreCase("Vanilla")) {
    	mc.thePlayer.stepHeight = (float) height.getValDouble();
    }else{
  	  mc.thePlayer.stepHeight = .5F;
    }
   	if(mode.getValString().equalsIgnoreCase("NCP")) {
   		if(mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && mc.thePlayer.onGround) {
   			mc.thePlayer.motionY = 0.41999998688697815D;
   		}
   	}
	if(mode.getValString().equalsIgnoreCase("Legit")) {
   		if(mc.thePlayer.isCollidedHorizontally  && !mc.thePlayer.isOnLadder() && mc.thePlayer.onGround) {
   		mc.thePlayer.jump();
   		}
   	}
	if(mode.getValString().equalsIgnoreCase("Redesky")) {
   		if(mc.thePlayer.isCollidedHorizontally  && !mc.thePlayer.isOnLadder()) {
   		mc.thePlayer.jump();
   		}
   	}
   	if(mode.getValString().equalsIgnoreCase("AAC")) {
   		if(mc.thePlayer.isCollidedHorizontally) {
   			
			switch(ticks) {
			case 0:
				if(mc.thePlayer.onGround) 
				   mc.thePlayer.jump();
				
				break;
			case 7:
				mc.thePlayer.motionY = 0;
				break;
			case 8:
				if(!mc.thePlayer.onGround) 
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY +1, mc.thePlayer.posZ);
				break;
   			
   			}
			ticks++;
   		}else {
   			ticks = 0;
   		}		
   		
		}
   	
    }
   
  
}