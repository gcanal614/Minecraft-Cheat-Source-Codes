package me.module.impl.world;


import java.util.ArrayList;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Module{

	public Setting mode;
	
	public Phase() {
		super("Phase", 0, Category.WORLD);
		ArrayList<String> options = new ArrayList<String>();
        options.add("Hypixel");
        options.add("Redesky");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Phase Mode", this, "Hypixel", options));
	}
	
	public void onEnable() {
	 super.onEnable();
	   if(this.mode.getValString().equalsIgnoreCase("Hypixel")) {
		try {		
			this.toggle();
			Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX, 
					Minecraft.getMinecraft().thePlayer.posY - 3,
					Minecraft.getMinecraft().thePlayer.posZ);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	   }else if(this.mode.getValString().equalsIgnoreCase("Redesky")) {
		   this.toggle();
		   if (mc.thePlayer.isCollidedHorizontally) {
		    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 0.00000001, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 0.000001, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		   } else {
		    Hime.addClientChatMessage("Stand against blocks.");
		   }
	   }
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	@Handler
	public void onUpdate(EventUpdate event) {
		this.setSuffix(mode.getValString());
	}
	
}