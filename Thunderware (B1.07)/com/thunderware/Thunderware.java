package com.thunderware;

import org.lwjgl.opengl.Display;

import com.thunderware.module.ModuleManager;
import com.thunderware.utils.api.DiscordAPI.RPC;

public class Thunderware {

	public RPC richPresence;
	
	public static Thunderware instance = new Thunderware();
	
	public String buildVersion = "1.07";
	public static ModuleManager moduleManager = new ModuleManager();
	
	public void setupClient() {
		richPresence = new RPC();
		Display.setTitle("Thunderware | " + buildVersion);
		richPresence.start();
		moduleManager.setupModules();
	}
	
	public void onClientClose() {
		richPresence.shutdown();
	}
}



/*

 	We all love FuzzySalt,
 	He was the best java developer,
 	We all miss FuzzySalt.
 
*/
