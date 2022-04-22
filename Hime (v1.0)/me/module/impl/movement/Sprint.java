package me.module.impl.movement;

import me.event.impl.EventReceivePacket;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;

public class Sprint extends Module	{

	public Sprint() {
		super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	@Handler
	public void onUpdate(EventUpdate event) {
		if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isMoving() && !mc.thePlayer.isBlocking() && !mc.thePlayer.isSneaking()) {
			mc.thePlayer.setSprinting(true);
		}
	}

	@Handler
	public void onPacket(EventReceivePacket e) {

	}

}
