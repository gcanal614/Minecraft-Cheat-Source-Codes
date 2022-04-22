package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;
import me.injusttice.neutron.impl.modules.Module;

public class Panic extends Command {

    public Panic() {
        super("Panic", "Disables all mods instantly.", "panic", "panic");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length == 1) {
            for(Module m : NeutronMain.instance.moduleManager.getModules()) {
                m.setToggled(false);
            }
        }
    }
}
