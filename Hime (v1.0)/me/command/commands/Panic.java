package me.command.commands;

import me.Hime;
import me.command.Command;
import me.module.Module;
import net.minecraft.client.Minecraft;

public class Panic extends Command{
	  public final Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "panic";
	}

	@Override
	public String getDescription() {
		return "Turns off all modules";
	}

	@Override
	public String getSyntax() {
		return ".panic";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		for(Module m : Hime.instance.moduleManager.getModules()) {
			m.setToggled(false);
		}
	}

}
