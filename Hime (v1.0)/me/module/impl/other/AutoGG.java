package me.module.impl.other;


import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventReceivePacket;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.util.TimeUtil;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoGG extends Module{

	private ArrayList<String> triggers = new ArrayList<String>();
	TimeUtil time = new TimeUtil();
	
	public AutoGG() {
		super("AutoGG", 0, Category.MISC);
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
		triggers.add("Top Survivors");
		triggers.add("Winners - ");
		triggers.add("Gostou do mapa?");
		triggers.add("Sumo Duel -");
		triggers.add("venceu a partida!");
		triggers.add("For more info, check the gamelog:");
	}
	
	    @Handler
	    public void onReceive(EventReceivePacket event) {
	      if (event.getPacket() instanceof S02PacketChat) {
	        S02PacketChat packet = (S02PacketChat)event.getPacket();
	        String message = packet.getChatComponent().getUnformattedText();
	            if (!message.isEmpty()) {
	          	  for (String trigger : triggers) {
	      			if (message.contains(trigger)) {
	      				if (mc.getCurrentServerData().serverIP.contains("hypixel")) {
	    					mc.thePlayer.sendChatMessage("/ac " + Hime.instance.autogg);
	      				}else {
	      					mc.thePlayer.sendChatMessage(Hime.instance.autogg);
	      				}
	    				    //me.notification2.NotificationManager.addNotification("Auto GG", 2000,  me.notification2.Notification.Mode.INFO); 
	    				}
	      			}
	          	  }
	            }
	  }
}