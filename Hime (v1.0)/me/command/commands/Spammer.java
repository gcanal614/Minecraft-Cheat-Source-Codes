package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class Spammer extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		
		return "spamtext";
	}

	@Override
	public String getDescription() {
	
		return "changes text spammmer spams";
	}

	@Override
	public String getSyntax() {
		
		return ".spamtext [TEXT]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Hime.instance.spam = String.join(" ", args);
	    
		Hime.instance.addClientChatMessage("Set spammers text to: " + String.join(" ", args));
	}
}
