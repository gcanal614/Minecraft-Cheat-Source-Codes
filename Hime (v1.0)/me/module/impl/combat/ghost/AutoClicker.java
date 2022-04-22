package me.module.impl.combat.ghost;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Mouse;

import io.netty.util.internal.ThreadLocalRandom;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.settings.KeyBinding;


public class AutoClicker extends Module {

	private long lastClick;
	private long hold;
	
	public Setting min2;
	public Setting max2;
	
	public Setting mode;
	
	private double speed;
	private double holdLength;
	private double min;
	private double max;
	
	public AutoClicker() {
		super("AutoClicker",0, Category.COMBAT);
		
		Hime.instance.settingsManager.rSetting(this.min2 = new Setting("MinCPS", this, 8, 1, 20, false));
		Hime.instance.settingsManager.rSetting(this.max2 = new Setting("MaxCPS", this, 12, 1, 20, false));
		ArrayList<String> options = new ArrayList<String>();
        options.add("Left");
        options.add("Right");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Click Mode", this, "Left", options));
	}
	

    @Handler
    public void onUpdate(EventUpdate event) {
    			if (Mouse.isButtonDown(0) && this.mode.getValString().equalsIgnoreCase("Left")) {
	
			if (System.currentTimeMillis() - lastClick > speed * 1000) {
				lastClick = System.currentTimeMillis();
				if (hold < lastClick) {
					hold = lastClick;
				}
				int key;
				if(this.mode.getValString().equalsIgnoreCase("Left")) {
				 key = mc.gameSettings.keyBindAttack.getKeyCode();
				}else {
			      key = mc.gameSettings.keyBindUseItem.getKeyCode();
				}
				KeyBinding.setKeyBindState(key, true);
				
				KeyBinding.onTick(key);
				this.updateVals();
			} else if (System.currentTimeMillis() - hold > holdLength * 1000) {
				if(this.mode.getValString().equalsIgnoreCase("Left")) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
				}else {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
				}
				this.updateVals();
			}
		}
    			if (Mouse.isButtonDown(1) && this.mode.getValString().equalsIgnoreCase("Right")) {
    				
    				if (System.currentTimeMillis() - lastClick > speed * 1000) {
    					lastClick = System.currentTimeMillis();
    					if (hold < lastClick) {
    						hold = lastClick;
    					}
    					int key;
    					if(this.mode.getValString().equalsIgnoreCase("Left")) {
    					 key = mc.gameSettings.keyBindAttack.getKeyCode();
    					}else {
    				      key = mc.gameSettings.keyBindUseItem.getKeyCode();
    					}
    					KeyBinding.setKeyBindState(key, true);
    					
    					KeyBinding.onTick(key);
    					this.updateVals();
    				} else if (System.currentTimeMillis() - hold > holdLength * 1000) {
    					if(this.mode.getValString().equalsIgnoreCase("Left")) {
    					KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
    					}else {
    					KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    					}
    					this.updateVals();
    				}
    			}
	
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.updateVals();
	}
	
	private void updateVals() {
		min = this.min2.getValDouble();
		max = this.max2.getValDouble();
		
		if (min >= max) {
			max = min + 1;
		}
		
		speed = 1.0 / ThreadLocalRandom.current().nextDouble(min - 0.2, max);
		holdLength = speed / ThreadLocalRandom.current().nextDouble(min, max);
	}
}
