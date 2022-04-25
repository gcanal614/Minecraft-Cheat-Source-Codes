package com.thunderware.module.player;

import org.lwjgl.input.Keyboard;

import com.thunderware.Thunderware;
import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.module.ModuleBase;

public class Sprint extends ModuleBase {
	
	public Sprint() {
		super("Sprint", Keyboard.KEY_M, Category.PLAYER);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !mc.thePlayer.isSneaking() && !Thunderware.moduleManager.getModuleByName("Scaffold").isToggled())
				mc.thePlayer.setSprinting(true);
		}
	}
}
