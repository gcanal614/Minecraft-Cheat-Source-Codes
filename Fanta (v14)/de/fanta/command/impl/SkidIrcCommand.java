/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.Client;
import de.fanta.command.Command;
import java.util.Arrays;

public class SkidIrcCommand
extends Command {
    public SkidIrcCommand() {
        super("irc");
    }

    @Override
    public void execute(String[] args) {
        Client.INSTANCE.ircClient.executeCommand(String.join((CharSequence)" ", Arrays.copyOfRange(args, 1, args.length)));
    }
}

