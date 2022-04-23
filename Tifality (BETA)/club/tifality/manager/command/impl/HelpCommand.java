/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.command.impl;

import club.tifality.Tifality;
import club.tifality.manager.command.Command;
import club.tifality.manager.command.CommandExecutionException;
import club.tifality.utils.Wrapper;
import java.util.Arrays;

public final class HelpCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"help", "h"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        for (Command command : Tifality.getInstance().getCommandHandler().getElements()) {
            Wrapper.addChatMessage(Arrays.toString(command.getAliases()) + ": " + command.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "help/h";
    }
}

