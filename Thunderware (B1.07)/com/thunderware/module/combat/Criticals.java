package com.thunderware.module.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.events.listeners.EventPacket;
import com.thunderware.events.listeners.EventUpdate;
import com.thunderware.module.ModuleBase;
import com.thunderware.utils.TimerUtils;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

public class Criticals extends ModuleBase {
	
	public boolean shouldCrit = false;
	public TimerUtils timer = new TimerUtils();
	public int groundTicks = 0;
	
	public Criticals() {
		super("Criticals", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	public void onEvent(Event event) {
		if(event instanceof EventMotion) {
			EventMotion e = (EventMotion)event;
			if(e.isPre()) {
				
			}
		}

		if(event instanceof EventPacket) {
			EventPacket packetEvent = ((EventPacket) event);
			if(packetEvent.getPacket() instanceof C02PacketUseEntity) {
				
			}
		}
	}
	
	public void onDisable() {
		shouldCrit = false;
		groundTicks = 0;
	}
	
}
