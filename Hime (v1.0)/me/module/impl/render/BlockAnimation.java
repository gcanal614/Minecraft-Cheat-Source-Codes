package me.module.impl.render;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;


public class BlockAnimation extends Module {
	public Setting speed;
	public Setting height;
	public Setting blockspeed;
	public String mode;
	
  public BlockAnimation() {
    super("Animations", 0, Category.RENDER);
  }	
  public void setup() {
	  Hime.instance.settingsManager.rSetting(blockspeed= new Setting("BlockAnimation Speed", this, 4, 1, 20, false));
	  Hime.instance.settingsManager.rSetting(speed = new Setting("Swing Speed", this, 6, 1, 25, true));
      Hime.instance.settingsManager.rSetting(height = new Setting("Item Size", this, 1, 0.1, 2.0, false));
      Hime.instance.settingsManager.rSetting(new Setting("X", this, 0.0D, -1.0D, 1.0D, false));
	  Hime.instance.settingsManager.rSetting(new Setting("Y", this, 0.0D, -1.0D, 1.0D, false));
	  Hime.instance.settingsManager.rSetting(new Setting("Z", this, 0.0D, -1.0D, 1.0D, false));
	  Hime.instance.settingsManager.rSetting(new Setting("ItemX", this, 0.0D, -1.0D, 1.0D, false));
      Hime.instance.settingsManager.rSetting(new Setting("ItemY", this, 0.0D, -1.0D, 1.0D, false));
	  Hime.instance.settingsManager.rSetting(new Setting("ItemZ", this, 0.0D, -1.0D, 1.0D, false));
      this.addModes("Animation type", "Vanilla","Spin","Warped","Exhibi","Slide","Tap","Tap2", "Tap3", "Sink","Sigma","Spin","Down", "Down2", "Push", "Table", "Stab", "Shake", "Memer", "Allah", "Gato");
      this.mode = this.getModes("Animation type");
  }


  @Handler
  public void onUpdate(EventUpdate event) {
	   this.mode = this.getModes("Animation type");
     this.setSuffix(mode);
    }
}