package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class ClientName extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "clientname";
	}

	@Override
	public String getDescription() {
		return "changes clients name";
	}

	@Override
	public String getSyntax() {
		return ".clientname [TEXT]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {

		Hime.instance.name = String.join(" ", args);
	
	
		   Hime.instance.addClientChatMessage("Set Clients name to: " + String.join(" ", args));
	}
}
