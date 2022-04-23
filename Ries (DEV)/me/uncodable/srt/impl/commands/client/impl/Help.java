/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.client.impl;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.commands.client.api.Command;
import me.uncodable.srt.impl.commands.client.api.CommandInfo;

@CommandInfo(name="Help", desc="Displays a list of commands with their corresponding description.", usage=".help")
public class Help
extends Command {
    @Override
    public void exec(String[] args) {
        Ries.INSTANCE.msg("Available SRT commands...");
        Ries.INSTANCE.getCommandManager().getCommands().forEach(command -> Ries.INSTANCE.msg(String.format("\u00a7b%s - %s (\u00a7bUsage\u00a77: %s)", command.getInfo().name(), command.getInfo().desc(), command.getInfo().usage())));
    }
}

