package non.asset.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
    public static void printChat(String text) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(text));
    }

    public static void sendChat_NoFilter(String text) {
    	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(text));
    }

    public static void sendChat(String text) {
    	Minecraft.getMinecraft().thePlayer.sendChatMessage(text);
    }
}
