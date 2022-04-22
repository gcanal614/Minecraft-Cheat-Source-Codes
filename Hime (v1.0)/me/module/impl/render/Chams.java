package me.module.impl.render;

import me.Hime;
import me.module.Module;
import me.settings.Setting;

public class Chams extends Module {

	public Setting e;
	public Setting a;

	public Chams() {
		super("Chams", 0, Category.RENDER);
		Hime.instance.settingsManager.rSetting(e = new Setting("Colored", this, false));
		Hime.instance.settingsManager.rSetting(a = new Setting("Behind Walls", this, true));
	}
}
