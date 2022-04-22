package me.command.commands;

import me.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Say extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "say";
	}

	@Override
	public String getDescription() {
		return "says something in chat";
	}

	@Override
	public String getSyntax() {
		return ".say [TEXT]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		 mc.getNetHandler().addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
	}
}
