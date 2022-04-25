package com.thunderware.module.player;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventUpdate;
import com.thunderware.module.ModuleBase;

public class AutoRespawn extends ModuleBase{

	public AutoRespawn() {
		super("AutoRespawn", Keyboard.KEY_NONE, Category.PLAYER);
		setDisplayName("Auto Respawn");
	}
	 
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(mc.thePlayer.isDead) {
				mc.thePlayer.respawnPlayer();
			}
			
		}

	}
	
}
