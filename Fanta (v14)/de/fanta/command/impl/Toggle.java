/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.Client;
import de.fanta.command.Command;
import de.fanta.utils.FileUtil;

public class Toggle
extends Command {
    public Toggle() {
        super("toggle");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Toggle.messageWithPrefix(" Toggle <Module> ");
            return;
        }
        String module = args[0];
        try {
            Client.INSTANCE.moduleManager.getModule(module).setState();
            Toggle.messageWithPrefix(" " + Client.INSTANCE.moduleManager.getModule((String)module).name + " \u00a7was " + (Client.INSTANCE.moduleManager.getModule(module).getState() ? "\u00a7aenabled" : "\u00a7cdisabled"));
            if (Client.INSTANCE.moduleManager.getModule(module).getState()) {
                Client.INSTANCE.moduleManager.getModule(module).onEnable();
                FileUtil.saveModules();
                FileUtil.saveKeys();
            } else {
                Client.INSTANCE.moduleManager.getModule(module).onDisable();
                FileUtil.saveModules();
                FileUtil.saveValues("values", false);
                FileUtil.saveKeys();
            }
        }
        catch (Exception e) {
            Toggle.messageWithPrefix("Module not found");
        }
    }
}

