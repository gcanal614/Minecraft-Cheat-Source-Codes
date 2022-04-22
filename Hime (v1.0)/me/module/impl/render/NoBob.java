package me.module.impl.render;

import me.module.Module;



public class NoBob extends Module {

  public NoBob() {
    super("NoBob", 0, Category.RENDER);
  }
  
  public void onEnable() {
	  super.onEnable();
            this.mc.gameSettings.viewBobbing = false;
            this.mc.thePlayer.distanceWalkedModified = 0.0F;
        }

  public void onDisable() {
	  super.onDisable();
      this.mc.gameSettings.viewBobbing = true;
      this.mc.thePlayer.distanceWalkedModified = 0.0F;
  }
}