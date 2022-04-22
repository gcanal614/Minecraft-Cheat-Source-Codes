package me.module.impl.render;

import me.Hime;
import me.module.Module;
import me.settings.Setting;

public class CustomGlint extends Module{
	
	public CustomGlint() {
		super("CustomGlint", 0, Category.RENDER);
		  Hime.instance.settingsManager.rSetting(new Setting("Color", this, 20, 0, 255));
		  Hime.instance.settingsManager.rSetting(new Setting("Brightness2", this, 1, -20, 5, 1));
		  Hime.instance.settingsManager.rSetting(new Setting("Saturation2", this, 1, -20, 5, "eeee"));
		  Hime.instance.settingsManager.rSetting(new Setting("Rainbow Glint", this, false));
	}
}
