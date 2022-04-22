package me.discord;


//import club.minnced.discord.rpc.*;
import net.minecraft.client.Minecraft;

public class Discord {
    public static Discord instance = new Discord();
	
	public void RPC() {
		/*String secondString = "";
		String firstString = "";
	//	if(Hime.instance.MainMenu = true) {
		//	firstString = "Main Menu";
		//}
		
		
		DiscordRPC lib = DiscordRPC.INSTANCE;
		String applicationId = "804119867904360480";
		String steamId = "";
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		//handlers.ready = () -> System.out.println("Ready!");
		lib.Discord_Initialize(applicationId, handlers, true, steamId);
		
		DiscordRichPresence presence = new DiscordRichPresence();
		presence.largeImageText = "Hime For Minecraft 1.8.9";
	//	presence.smallImageText = Minecraft.getMinecraft().getSession().getUsername();
		presence.largeImageKey = "artemislogo1";
	//	presence.smallImageKey = "steve";

	
		presence.startTimestamp = System.currentTimeMillis() / 1000;
	
		presence.state =  "Minecraft 1.8.9";	
		presence.details = "Playing on Hime";
		lib.Discord_UpdatePresence(presence);
	
		new Thread(() -> {
			while(!Thread.currentThread().isInterrupted()) {
				lib.Discord_RunCallbacks();
				try {
					Thread.sleep(2000);
				}catch(InterruptedException e) {}
			}
		}, "RPC-Callback-Handler").start();*/
		
	}
	public void update(String firstline, String secondline) {
	/*	DiscordRPC lib = DiscordRPC.INSTANCE;
		String applicationId = "731906658065842287";
		String steamId = "";
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		DiscordRichPresence presence = new DiscordRichPresence();
		lib.Discord_Initialize(applicationId, handlers, true, steamId);
		presence.largeImageText = "Hime For Minecraft 1.8";
		presence.smallImageText = Minecraft.getMinecraft().getSession().getUsername();
		presence.largeImageKey = "rpc2";
		presence.smallImageKey = "steve";
		presence.state =  secondline;	
		presence.details = firstline;
		presence.startTimestamp = System.currentTimeMillis() / 1000;
		lib.Discord_UpdatePresence(presence);
		*/
	}
}
