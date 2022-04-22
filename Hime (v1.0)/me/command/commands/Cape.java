package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class Cape extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "cape";
	}

	@Override
	public String getDescription() {
		return "toggles cape";
	}

	@Override
	public String getSyntax() {
		return ".cape";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {

	//	Hime.instance.name = String.join(" ", args);
	
	Hime.instance.cape = !Hime.instance.cape;
	Hime.instance.addClientChatMessage("Cape is:  " + (Hime.instance.cape == true ? "Enabled" : "Disabled"));
	}
}
