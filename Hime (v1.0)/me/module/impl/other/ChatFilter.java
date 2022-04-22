package me.module.impl.other;

import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;



public class ChatFilter extends Module{
	
	public Setting mode;
	public Setting interval;
	TimeUtil time = new TimeUtil();
	char bypassChar = '\u05fc';
	
    public ChatFilter() {
        super("ChatFilter", 0, Category.MISC);
        
    	ArrayList<String> options = new ArrayList<String>();
        options.add("Bypass");
        options.add("Clear");
        Hime.instance.settingsManager.rSetting(mode = new Setting("ChatFilter Mode", this, "Bypass", options));
        Hime.instance.settingsManager.rSetting(this.interval = new Setting("Clear Delay", this, 900, 0, 5000, true));
    }
    
    public void onDisable() {
    	super.onDisable();
    	time.reset();
    }
    
    @Handler
    public void onUpdate(EventUpdate event) {
    	this.setSuffix(mode.getValString());
      if(this.mode.getValString().equalsIgnoreCase("Clear")) {
    	 if(this.time.hasTimePassed((long) this.interval.getValDouble())) {
    	  mc.ingameGUI.getChatGUI().clearChatMessages();
    	  time.reset();
    	 }
      }
    }
    
    @Handler
    public void onSendPacket(EventSendPacket event) {
      if(this.mode.getValString().equalsIgnoreCase("Bypass")) { 
        if (event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = (C01PacketChatMessage)event.getPacket();
            
            if (packet.getMessage().startsWith("/") || packet.getMessage().startsWith(".")) return;
            
            event.cancel();
            
            String finalMessage = "";
            for (char letter : packet.getMessage().toCharArray()) {
            	finalMessage += letter + "\u05fc";
            }
            mc.getNetHandler().addToSendQueueSilent(new C01PacketChatMessage(finalMessage.toString().replaceFirst("%", "")));
        }
      }
    }
}