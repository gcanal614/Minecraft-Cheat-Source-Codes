package com.boomer.client.command.impl;

import com.boomer.client.Client;
import com.boomer.client.command.Command;
import com.boomer.client.utils.Printer;

public class Modules extends Command {

    public Modules() {
        super("Modules", new String[]{"modules","mods","m"});
    }

    @Override
    public void onRun(final String[] s) {
        StringBuilder mods = new StringBuilder("Modules (" + Client.INSTANCE.getModuleManager().getModuleMap().values().size() + "): ");
        Client.INSTANCE.getModuleManager().getModuleMap().values()
                .forEach(mod -> mods.append(mod.isEnabled() ? "\247a" : "\247c").append(mod.getLabel()).append("\247r, "));
        Printer.print(mods.toString().substring(0, mods.length() - 2));
    }
}