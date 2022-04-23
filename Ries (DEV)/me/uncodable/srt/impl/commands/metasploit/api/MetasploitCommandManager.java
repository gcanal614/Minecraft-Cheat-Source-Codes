/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.metasploit.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.uncodable.srt.impl.commands.metasploit.api.MetasploitCommand;

public class MetasploitCommandManager {
    private final List<MetasploitCommand> metasploitCommands = new ArrayList<MetasploitCommand>();

    public void addCommands(MetasploitCommand ... metasploitCommandsList) {
        this.metasploitCommands.addAll(Arrays.asList(metasploitCommandsList));
    }

    public List<MetasploitCommand> getMetasploitCommands() {
        return this.metasploitCommands;
    }
}

