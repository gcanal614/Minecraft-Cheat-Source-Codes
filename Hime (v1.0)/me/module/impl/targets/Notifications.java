package me.module.impl.targets;


import me.Hime;
import me.module.Module;
import me.settings.Setting;

public class Notifications extends Module{

	public Notifications() {
		super("Notifications", 0, Category.TARGETS);
		Hime.instance.settingsManager.rSetting(new Setting("Module Enabled/Disabled", this, false));
	}
	
	

}