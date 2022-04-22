package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class Username extends Command{

	@Override
	public String getName() {

		return "username";
	}

	@Override
	public String getDescription() {

		return "gets your username";
	}

	@Override
	public String getSyntax() {

		return ".username";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		String username = Minecraft.getMinecraft().thePlayer.getName();
	
		
		Hime.addClientChatMessage("Username: " + username);
	}
	}


