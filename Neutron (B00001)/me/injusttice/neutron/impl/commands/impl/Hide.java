package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;
import me.injusttice.neutron.impl.modules.Module;

public class Hide extends Command {

    public Hide() {
        super("Hide", "Hides a module by name", "hide <module name>", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear")) {
                for (Module m : NeutronMain.instance.moduleManager.getModules())
                    m.setVisible(true);
                return;
            }
            for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                if (m.getName().equalsIgnoreCase(args[0])) {
                    NeutronMain.addChatMessage((m.isVisible() ? "Hidden " : "Now showing ") + m.getName());
                    m.setVisible(!m.isVisible());
                }
            }
        }
    }
}
