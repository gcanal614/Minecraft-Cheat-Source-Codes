/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.Client;
import de.fanta.command.Command;
import de.fanta.command.CommandManager;
import de.fanta.module.ModuleManager;
import de.fanta.utils.FileUtil;

public class Reload
extends Command {
    public Reload() {
        super("reload");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            Reload.messageWithPrefix(" \u00a77Modules reloaded \u00a7f");
            Reload.messageWithPrefix("\u00a77Commands reloaded \u00a7f");
            FileUtil.save();
            Client.INSTANCE.moduleManager.modules.clear();
            Client.INSTANCE.moduleManager = new ModuleManager();
            Client.INSTANCE.commandManager = new CommandManager();
            Client.INSTANCE.onStart2();
            FileUtil.loadModules();
            FileUtil.loadKeys();
            return;
        }
    }
}

