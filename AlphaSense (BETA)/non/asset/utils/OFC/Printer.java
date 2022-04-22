package non.asset.utils.OFC;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import non.asset.Clarinet;

public class Printer {

	public static void print(final String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(ChatFormatting.DARK_BLUE + Clarinet.getName() + ChatFormatting.WHITE + " > "+ message, new Object[0]));
	}
	public static void custom(ChatFormatting a, String messager,String message, ChatFormatting b) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(a + messager + b + " > "+ message, new Object[0]));
	}
}