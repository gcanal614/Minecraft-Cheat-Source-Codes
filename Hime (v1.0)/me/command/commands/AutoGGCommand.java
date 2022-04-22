package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class AutoGGCommand extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "autoggtext";
	}

	@Override
	public String getDescription() {
		return "changes autogg text";
	}

	@Override
	public String getSyntax() {
		return ".autoggtext [TEXT]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {

      Hime.instance.autogg = String.join(" ", args);
	
	
	  Hime.instance.addClientChatMessage("Set Auto GG text to: " + String.join(" ", args));
	}
}
