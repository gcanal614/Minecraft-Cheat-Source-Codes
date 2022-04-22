package me.module.impl.combat.ghost;

import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;


public class Hitbox extends Module {


	
	public Setting min2;
	public Setting max2;
	public Setting mode;
	
	 public double hitbox = 0;
	
	public Hitbox() {
		super("Hitbox",0, Category.COMBAT);
		
		Hime.instance.settingsManager.rSetting(this.min2 = new Setting("Hitbox Expand", this, 0, 0, 8, false));
		//Hime.instance.settingsManager.rSetting(this.max2 = new Setting("Max Hitbox Expand", this, 0.2, 0, 5, false));
		ArrayList<String> options = new ArrayList<String>();
        options.add("Normal");
        options.add("Silent Raycast");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Hitbox Mode", this, "Normal", options));
	}
	

    @Handler
    public void onUpdate(EventUpdate event) {
       	this.setSuffix("" + min2.getValDouble());
    	//this.updateVals();
	
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		//this.updateVals();
	}
	
	private void updateVals() {
	//	hitbox = ThreadLocalRandom.current().nextDouble(this.min2.getValDouble(), this.max2.getValDouble());
	}
}
