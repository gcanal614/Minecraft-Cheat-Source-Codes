package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;

public class Rename extends Command {

    public Rename() {
        super("Rename", "Renames the client", "rename <clientname>", "rename");
    }

    public void onCommand(String[] args, String command) {
        if (args.length > 0)
            NeutronMain.instance.name = String.join(" ", args);
    }
}
