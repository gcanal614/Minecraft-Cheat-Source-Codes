package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Toggle extends Command {

	public Toggle() {
		super("Toggle", "Toggles a module by name.", "toggle <module name>", "t");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length > 0) {
			String moduleName = args[0];
			
			boolean foundModule = false;
			
			for(Module module : NeutronMain.instance.moduleManager.getModules()) {
				if(module.name.equalsIgnoreCase(moduleName)) {
					module.toggle();

					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(module.isEnabled() ? module.name + " " + "was enabled" : module.name + " " + "was disabled"));
					
					foundModule = true;
					break;
				}
			}
			
			if(!foundModule) {
				NeutronMain.addChatMessage("§8> §aCould not find the module.");
			}
		}
	}
}
