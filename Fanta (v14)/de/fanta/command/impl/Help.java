/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.Client;
import de.fanta.command.Command;
import de.fanta.command.CommandManager;
import de.fanta.utils.ChatUtil;

public class Help
extends Command {
    public Help() {
        super("help");
    }

    @Override
    public void execute(String[] args) {
        CommandManager cfr_ignored_0 = Client.INSTANCE.commandManager;
        for (Command c : CommandManager.commands) {
            ChatUtil.sendChatMessageWithPrefix(c.getName());
        }
    }
}

