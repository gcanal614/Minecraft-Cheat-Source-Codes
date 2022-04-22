package me.module.impl.other;


import me.Hime;
import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import me.util.TimeUtil;
import net.minecraft.network.play.server.S02PacketChat;

import java.util.ArrayList;

public class AutoPlay extends Module{

	private ArrayList<String> triggers = new ArrayList<String>();
	TimeUtil time = new TimeUtil();
	public String type;
	
	public AutoPlay() {
		super("AutoPlay", 0, Category.MISC);
		triggers.add("1st Killer - ");
		triggers.add("1st Place - ");
		triggers.add("Winner: ");
		triggers.add(" - Damage Dealt - ");
		triggers.add("Winning Team -");
		triggers.add("1st - ");
		triggers.add("Winners: ");
		triggers.add("Winner: ");
		triggers.add("Winning Team: ");
		triggers.add(" won the game!");
		triggers.add("Top Seeker: ");
		triggers.add("1st Place: ");
		triggers.add("Last team standing!");
		triggers.add("Winner #1 (");
		triggers.add("venceu a partida!");
		triggers.add("Top Survivors");
		triggers.add("Winners - ");
		triggers.add("Gostou do mapa?");
		triggers.add("Sumo Duel -");
		triggers.add("For more info, check the gamelog:");
	
	    this.addModes("Hypixel Play type", "Skywars Solo Insane", "Skywars Solo Normal", "Skywars Doubles Insane", "Skywars Doubles Normal","Bedwars Solo", "Bedwars Double");
	    type = this.getModes("Hypixel Play type");
	}
	
	   @Handler
	   public void onUpdate(EventUpdate event) {
		   this.setSuffix(type);
			type = this.getModes("Hypixel Play type");
	   }
	

	    @Handler
	    public void onReceive(EventReceivePacket event) {
	      if (event.getPacket() instanceof S02PacketChat) {
	        S02PacketChat packet = (S02PacketChat)event.getPacket();
	        String message = packet.getChatComponent().getUnformattedText();
	            if (!message.isEmpty()) {
	          	  for (String trigger : triggers) {
	      			if (message.contains(trigger)) {
	      				if(Hime.instance.moduleManager.getModule("Killaura").isToggled()) {
	      					if(Hime.instance.settingsManager.getSettingByName("AutoDisable").getValBoolean()) {
	      						Hime.instance.moduleManager.getModule("Killaura").setToggled(false);
								NotificationManager.show(new Notification(NotificationType.INFO, "KillAura Alert", "Disabled Due End Of A Game", 2));
	      					}
	      				}
	      				if (mc.getCurrentServerData().serverIP.contains("hypixel")) {
	    				//	NotificationManager.show(new Notification(NotificationType.INFO, "Auto Play","Automatically joining a new game in 2 sec.", 2));
	    					if(time.hasTimePassed(2000)) {
	    				     switch(type) {
	    				     case "Skywars Solo Insane":
	    				    	  mc.thePlayer.sendChatMessage("/play solo_insane");
	    				    	 break;
	    				     case "Skywars Solo Normal":
	    				   	  mc.thePlayer.sendChatMessage("/play solo_normal");
	    				    	 break;
	    				     case "Skywars Doubles Insane":
	    				    	  mc.thePlayer.sendChatMessage("/play teams_insane");
	    				    	 break;
	    				     case "Skywars Doubles Normal":
	    				   	  mc.thePlayer.sendChatMessage("/play teams_normal");
	    				    	 break;
	    				     case "Bedwars Solo":
	    				   	  mc.thePlayer.sendChatMessage("/play bedwars_eight_one"); 
	    				    	 break;
                             case "Bedwars Double":
                            	 mc.thePlayer.sendChatMessage("/play bedwars_eight_two"); 
	    				    	 break;
	    				     }
								NotificationManager.show(new Notification(NotificationType.INFO, "Hypixel Alert", "Joing In A New Game", 2));
	    					 this.time.reset();
	    					}
	    				}if (mc.getCurrentServerData().serverIP.contains("mineplex")) {
							NotificationManager.show(new Notification(NotificationType.INFO, "Mineplax Alert", "Joing In A New Game", 2));
	    				//	NotificationManager.show(new Notification(NotificationType.INFO, "Auto Play","Automatically joining a new game in 2 sec.", 2));
	    					if(time.hasTimePassed(2000)) {
	    					 mc.thePlayer.sendChatMessage("/playagain");
	    					 this.time.reset();
	    					}
	    				}if (mc.getCurrentServerData().serverIP.contains("hivemc")) {
							NotificationManager.show(new Notification(NotificationType.INFO, "Hive Alert", "Joing In A New Game", 2));
	    					//NotificationManager.show(new Notification(NotificationType.INFO, "Auto Play","Automatically joining a new game in 2 sec.", 2));
	    					if(time.hasTimePassed(2000)) {
	    					 mc.thePlayer.sendChatMessage("/newgame");
	    					 this.time.reset();
	    					}
	    				}
	      			}
	          	  }
	            }
	      }
	  }
}