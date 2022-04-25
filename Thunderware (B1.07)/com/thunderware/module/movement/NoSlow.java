package com.thunderware.module.movement;

import org.lwjgl.input.Keyboard;

import com.thunderware.Thunderware;
import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.events.listeners.EventSlowDown;
import com.thunderware.module.ModuleBase;
import com.thunderware.module.combat.Killaura;
import com.thunderware.settings.settings.BooleanSetting;
import com.thunderware.settings.settings.NumberSetting;

import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;

public class NoSlow extends ModuleBase {

	public NoSlow() {
		super("NoSlow", Keyboard.KEY_NONE, Category.MOVEMENT);
		setDisplayName("No Slowdown");
	}
	
	public void onEnable() {

	}
	
	public void onDisable() {

	}
	
	public void onEvent(Event e) {
		if(e instanceof EventSlowDown) {
			((EventSlowDown) e).setForwardMultiplier(1);
			((EventSlowDown) e).setStrafeMultiplier(1);
		}
		if(e instanceof EventMotion) {
			if(mc.thePlayer.getHeldItem() == null)
				return;
			if(Killaura.currentTarget != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && ((BooleanSetting)(Thunderware.moduleManager.getModuleByName("KillAura").getSettings().get(3))).getValue()) {
				if(mc.thePlayer.ticksExisted % 4 != 0)
					return;
				
				if(e.isPost())
					mc.thePlayer.stopUsingItem();
			}
		}
	}

}
