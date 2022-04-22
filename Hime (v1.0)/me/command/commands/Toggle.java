package me.command.commands;

import me.Hime;
import me.command.Command;
import me.module.Module;

public class Toggle extends Command{

	@Override
	public String getName() {
		return "toggle";
	}

	@Override
	public String getDescription() {
		return "Toggles a hack though the console";
	}

	@Override
	public String getSyntax() {
		return ".toggle <Module>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		boolean found = false;
		for(Module m: Hime.instance.moduleManager.getModules()){
			if(args[0].equalsIgnoreCase(m.getName())){
				m.toggle();
				found = true;
				Hime.addClientChatMessage("§9" + m.getName() + " §fwas "+ (m.isToggled() ? "§l§aEnabled" : "§l§cDisabled"));
			}
		}
		if(found == false){
			Hime.addClientChatMessage("Targeted Module was not found!");
		}
	}

}
