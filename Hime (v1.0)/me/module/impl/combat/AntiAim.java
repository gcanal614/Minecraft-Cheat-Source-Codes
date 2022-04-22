package me.module.impl.combat;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;

public class AntiAim extends Module {
	
	
public AntiAim() {
		super("AntiAim", Keyboard.KEY_NONE, Category.COMBAT);
	}
public Setting mode;
public Setting h;

public Setting v;
@Override
public void setup() {
    ArrayList<String> options = new ArrayList<String>();
    options.add("YawJitter");
    options.add("PitchJitter");
    options.add("PitchDown");
    Hime.instance.settingsManager.rSetting(mode = new Setting("AntiAim Mode", this, "PitchJitter", options));
}


  @Override
  public void onDisable() {
    super.onDisable();
  }

  @Handler
  public void onUpdate(EventUpdate event) {
    	 this.setSuffix(mode.getValString());
    	 if(this.mode.getValString().equalsIgnoreCase("PitchDown")) {
    		 event.setPitch(90);
    		 mc.thePlayer.rotationPitchHead = 90;
    	 }
    	 if(this.mode.getValString().equalsIgnoreCase("PitchJitter")) {
    		 if(mc.thePlayer.ticksExisted % 2 == 0) {
    			 mc.thePlayer.rotationPitchHead = 90;
    			 event.setPitch(90);
    		 }else {
    			 mc.thePlayer.rotationPitchHead = -90;
    			 event.setPitch(-90);
    		 }
    	 }
    	 if(this.mode.getValString().equalsIgnoreCase("YawJitter")) {
    		 if(mc.thePlayer.ticksExisted % 2 == 0) {
    	           mc.thePlayer.rotationYawHead = 90;
    	           mc.thePlayer.renderYawOffset = 90;
    			 event.setYaw(90);
    		 }else {
   	           mc.thePlayer.rotationYawHead = -90;
   	           mc.thePlayer.renderYawOffset = -90;
    			 event.setYaw(-90);
    		 }
    	 }
  }
  
}
         
    
    
  

