package me.command.commands;

import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class DmUser extends Command{
	  public final Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "dmplayer";
	}

	@Override
	public String getDescription() {
		return "Sets the player to dm in spammer";
	}

	@Override
	public String getSyntax() {
		return ".dmplayer [Name of player]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
        Hime.instance.dmplayer = args[0];
		Hime.instance.addClientChatMessage("Set spammers player to dm to: " + args[0]);
	}

}
