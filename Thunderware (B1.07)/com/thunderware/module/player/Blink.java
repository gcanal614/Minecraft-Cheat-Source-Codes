package com.thunderware.module.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.EventDirection;
import com.thunderware.events.listeners.EventPacket;
import com.thunderware.module.ModuleBase;
import com.thunderware.module.movement.Flight;
import com.thunderware.settings.settings.ModeSetting;
import com.thunderware.utils.TimerUtils;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Blink extends ModuleBase {

	public ModeSetting mode;
	public TimerUtils timer = new TimerUtils();
	public static ArrayList<Packet> packets = new ArrayList<Packet>();
	
	public Blink() {
		super("Blink", Keyboard.KEY_NONE, Category.PLAYER);
	}
	
	public void onEvent(Event event) {
		if(event instanceof EventPacket) {
			if(mc.thePlayer == null || event.isIncoming())
				return;
			EventPacket e = (EventPacket)event;
			if(e.getDirection() == EventDirection.OUTGOING) {
				e.setCancelled(true);
				packets.add(e.getPacket());
			}
		}
	}
	
	public void onDisable() {
		if(packets.isEmpty())
			return;
		for(Packet p : packets) {
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(p);
			if(p instanceof C03PacketPlayer) {
				C03PacketPlayer zaza = (C03PacketPlayer) p;
				mc.thePlayer.setPosition(zaza.getPositionX(), zaza.getPositionY(), zaza.getPositionZ());
			}
		}
		packets.clear();
	}
}
