package non.asset.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import non.asset.Clarinet;
import non.asset.utils.OFC.Printer;
import non.asset.utils.OFC.TimerUtil;

public class PlayerManager {
	
	static TimerUtil timer1 = new TimerUtil();
	
	public static void PacketDamageFall(double fallDistance) {
		TimerUtil timer = new TimerUtil();
		Minecraft mc = Minecraft.getMinecraft();
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + fallDistance, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		if(timer.hasTimerElapsed((long) (1000 / 1000), true)) {
			mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - fallDistance, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		}
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01, mc.thePlayer.posZ, false));
		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
	}
	
	public static void voidDamage() {
		Minecraft mc = Minecraft.getMinecraft();
		if(!mc.thePlayer.onGround) return; 
		mc.thePlayer.damagePlayer();
	}

	public static void PacketDamageFall2(double fall) {
		Minecraft mc = Minecraft.getMinecraft();
		if(!mc.thePlayer.onGround) return;	
		final double lasty = mc.thePlayer.posY;
		if(mc.thePlayer.getActivePotionEffect(Potion.resistance) != null) {
			mc.thePlayer.posY = lasty;	
			mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
			mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - fall - 0.05, mc.thePlayer.posZ, false));
			mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05, mc.thePlayer.posZ, true));
			timer1.reset();
			return;
		}	
		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, lasty, mc.thePlayer.posZ, true));
		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, lasty - fall, mc.thePlayer.posZ, false));
		mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
		mc.thePlayer.posY = lasty;	
	}
	
	public static boolean blockAbove(int blockUp) {
		Minecraft mc = Minecraft.getMinecraft();
		BlockPos above;
		for (int i = 0; i < 9; i++) {
			above = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + i, mc.thePlayer.posZ);
			if(!mc.theWorld.isAirBlock(above)) {
				return true;
			}
		}
		return false;
	}
}