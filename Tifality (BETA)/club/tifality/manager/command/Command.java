/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.command;

import club.tifality.manager.command.CommandExecutionException;

public abstract class Command {
    public abstract String[] getAliases();

    public abstract void execute(String[] var1) throws CommandExecutionException;

    public abstract String getUsage();
}

