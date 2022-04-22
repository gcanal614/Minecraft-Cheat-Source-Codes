package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class NameProtect extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		
		return "nameprotect";
	}

	@Override
	public String getDescription() {
	
		return "changes nameprotect name";
	}

	@Override
	public String getSyntax() {
		
		return ".nameprotect [NAME]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Hime.instance.nameprotect = String.join(" ", args);
	    
		Hime.instance.addClientChatMessage("Set NameProtects name to: " + String.join(" ", args));
	}
}
