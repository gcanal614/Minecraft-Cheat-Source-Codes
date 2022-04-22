package me.module.impl.movement;



import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;

public class Timer extends Module{

	public Setting time;

	public Timer() {
		super("Timer", 0, Category.MOVEMENT);
		Hime.instance.settingsManager.rSetting(this.time = new Setting("Timer Value", this, 1.5, 0.1, 10, false));
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}

	  @Handler
	  public void onUpdate(EventUpdate event) {	
		  this.setSuffix(this.time.getValDouble()+"");
          mc.timer.timerSpeed = (float) this.time.getValDouble();
	}

	
}
