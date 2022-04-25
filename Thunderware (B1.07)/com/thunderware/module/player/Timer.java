package com.thunderware.module.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.module.ModuleBase;
import com.thunderware.settings.settings.ModeSetting;
import com.thunderware.settings.settings.NumberSetting;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Timer extends ModuleBase {
	public NumberSetting timer = new NumberSetting("Timer", 2.0, 0.05, 0.05, 10.0);
	
	public Timer() {
		super("Timer", Keyboard.KEY_NONE, Category.PLAYER);
		addSettings(timer);
	}
	
	public void onEnable() {
		//mc.timer.timerSpeed = timer.getValue().floatValue(); bc useless ig
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			mc.timer.timerSpeed = timer.getValue().floatValue();
		}
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
	}
}
