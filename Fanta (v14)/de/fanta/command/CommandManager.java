/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command;

import de.fanta.command.Command;
import de.fanta.command.impl.Bind;
import de.fanta.command.impl.Config;
import de.fanta.command.impl.FriendCommand;
import de.fanta.command.impl.Global;
import de.fanta.command.impl.Help;
import de.fanta.command.impl.Reload;
import de.fanta.command.impl.Report;
import de.fanta.command.impl.SkidIrcCommand;
import de.fanta.command.impl.Spec;
import de.fanta.command.impl.Toggle;
import de.fanta.command.impl.UserName;
import de.fanta.command.impl.setSkin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    public static List<Command> commands = new ArrayList<Command>();
    public final String Chat_Prefix = ".";

    public CommandManager() {
        this.addCommand(new Bind());
        this.addCommand(new Toggle());
        this.addCommand(new Reload());
        this.addCommand(new FriendCommand());
        this.addCommand(new SkidIrcCommand());
        this.addCommand(new Global());
        this.addCommand(new Config());
        this.addCommand(new Spec());
        this.addCommand(new Report());
        this.addCommand(new UserName());
        this.addCommand(new setSkin());
        this.addCommand(new Help());
    }

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public boolean execute(String text) {
        if (!text.startsWith(".")) {
            return false;
        }
        text = text.substring(1);
        String[] arguments = text.split(" ");
        for (Command cmd : commands) {
            if (!cmd.getName().equalsIgnoreCase(arguments[0])) continue;
            String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
            cmd.execute(args);
            return true;
        }
        return false;
    }
}

