package non.asset.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;

public class BypassUtils {

	static Minecraft mc = Minecraft.getMinecraft();
	
	public static void sendVerus() {
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
	}
	
	public static void sendPos(boolean plusPos, double x, double y, double z, double rotX, double rotZ, boolean onGround) {
		if(plusPos) {
			mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z, mc.thePlayer.rotationYaw + (float) rotX, mc.thePlayer.rotationPitch = (float) rotZ, onGround));
		}else {
			mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX = x, mc.thePlayer.posY = y, mc.thePlayer.posZ = z, mc.thePlayer.rotationYaw + (float) rotX, mc.thePlayer.rotationPitch = (float) rotZ, onGround));
		}
		
	}
	
	public static void hurtSound() {
		mc.thePlayer.performHurtAnimation();
		mc.thePlayer.playSound("game.neutral.hurt", 20.0F, 1.0F);
	}
	
}
