package me.module.impl.render;

import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import net.minecraft.potion.PotionEffect;

public class Fullbright extends Module {

  public Fullbright() {
    super("Fullbright", 0, Category.RENDER);
  }
  
  public float oldGamma = 0.0f;
  public String mode;
   @Override
  public void setup() {
      this.addModes("BrightMode", "Potion", "Gamma");
      this.mode = this.getModes("BrightMode");
      
  }
  
   public void onEnable() {
	   this.oldGamma = mc.gameSettings.gammaSetting;
	   super.onEnable();
   }
  public void onDisable() {
	  mc.thePlayer.removePotionEffect(16);
	  this.mc.gameSettings.gammaSetting = this.oldGamma;
  super.onDisable();
  }
  
  @Handler
  public void onUpdate(EventUpdate event) {
	  this.mode = this.getModes("BrightMode");
    	this.setSuffix(mode);
    	switch(mode) {
    	case "Gamma":
          this.mc.gameSettings.gammaSetting = 100.0F;
          break;
    	case "Potion":
     		mc.thePlayer.addPotionEffect(new PotionEffect(16, 5200, 1));
     		this.mc.gameSettings.gammaSetting = this.oldGamma;
    	 break;
    	}
     }
}
