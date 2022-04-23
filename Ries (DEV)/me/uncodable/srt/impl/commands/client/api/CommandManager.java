/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.client.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.uncodable.srt.impl.commands.client.api.Command;

public class CommandManager {
    private final List<Command> commands = new ArrayList<Command>();

    public void addCommands(Command ... commandsList) {
        this.commands.addAll(Arrays.asList(commandsList));
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}

