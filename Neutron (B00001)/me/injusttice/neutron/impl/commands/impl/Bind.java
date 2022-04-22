package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;
import me.injusttice.neutron.impl.modules.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {

	public Bind() {
		super("Bind", "Binds a module", "bind <name> <key> |q clear","b");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if (args.length == 2) {
			String moduleName = args[0];
			String keyName = args[1];
			boolean found = false;
			for (Module module : NeutronMain.instance.moduleManager.getModules()) {
				if (module.getName().equalsIgnoreCase(moduleName)) {
					module.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
					NeutronMain.addChatMessage(String.format("Bound %s to %s", new Object[] { module.getName(), Keyboard.getKeyName(module.getKey()) }));
					found = true;
					break;
				}
			}
			if (!found)
				NeutronMain.addChatMessage("Could not find the module.");
		}
		if (args.length == 1) {
			boolean found = false;
			if (args[0].equalsIgnoreCase("clear")) {
				for (Module module : NeutronMain.instance.moduleManager.getModules()) {
					if (!module.getName().equalsIgnoreCase("clickgui"))
						module.setKey(0);
				}
				NeutronMain.addChatMessage("Cleared all your keybinds.");
				found = true;
			}
			if (!found)
				NeutronMain.addChatMessage("Could not find the module.");
		}
	}
}
