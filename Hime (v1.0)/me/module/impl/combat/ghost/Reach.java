package me.module.impl.combat.ghost;

import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.entity.Entity;


public class Reach extends Module {


	
	public Setting min2;
	public Setting max2;
	public Setting mode;
	public Setting misplace;
	
	 public double reach = 0;
	
	public Reach() {
		super("Reach",0, Category.COMBAT);
		
		Hime.instance.settingsManager.rSetting(this.min2 = new Setting("Reach", this, 3, 3, 10, false));
		//Hime.instance.settingsManager.rSetting(this.max2 = new Setting("Max Reach", this, 3.4, 3, 6, false));
		ArrayList<String> options = new ArrayList<String>();
        options.add("Combat Reach");
        options.add("Build Reach");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Reach Mode", this, "Combat Reach", options));
        
        Hime.instance.settingsManager.rSetting(misplace = new Setting("Misplace", this, false));
	}
	

    @Handler
    public void onUpdate(EventUpdate event) {
    	this.setSuffix("" + min2.getValDouble());
    	//this.updateVals();
    	if(this.misplace.getValBoolean()) {
    	    for (Object e : mc.theWorld.loadedEntityList) {
    	    	Entity o = (Entity)e;
    	        if (o.getName() == mc.thePlayer.getName()) {
    	            continue;
    	        }
    	        double oldX = o.posX;
    	        double oldY = o.posY;
    	        double oldZ = o.posZ;
    	        if (mc.thePlayer.getDistanceToEntity(o) <= this.min2.getValDouble() && mc.thePlayer.getDistanceToEntity(o) > 2) {
    	            double mx = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90));
    	            double mz = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90));
    	            o.setPosition(oldX - mx / mc.thePlayer.getDistanceToEntity(o) * .5, oldY,
    	            oldZ - mz / mc.thePlayer.getDistanceToEntity(o) * .5);
    	            }
    	        }
    	}
	
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		//this.updateVals();
	}
	
	private void updateVals() {
		// reach = ThreadLocalRandom.current().nextDouble(this.min2.getValDouble(), this.max2.getValDouble());
	}
}
