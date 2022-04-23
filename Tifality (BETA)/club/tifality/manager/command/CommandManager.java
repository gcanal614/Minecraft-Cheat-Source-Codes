/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.command;

import club.tifality.Tifality;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.command.Command;
import club.tifality.manager.command.CommandExecutionException;
import club.tifality.manager.command.impl.BindCommand;
import club.tifality.manager.command.impl.ClientNameCommand;
import club.tifality.manager.command.impl.ConfigCommand;
import club.tifality.manager.command.impl.FriendCommand;
import club.tifality.manager.command.impl.HClipCommand;
import club.tifality.manager.command.impl.HelpCommand;
import club.tifality.manager.command.impl.HideCommand;
import club.tifality.manager.command.impl.NameCommand;
import club.tifality.manager.command.impl.PanicCommand;
import club.tifality.manager.command.impl.ToggleCommand;
import club.tifality.manager.command.impl.VClipCommand;
import club.tifality.manager.event.impl.player.SendMessageEvent;
import club.tifality.utils.Wrapper;
import club.tifality.utils.handler.Manager;
import java.util.Arrays;

public final class CommandManager
extends Manager<Command> {
    private static final String PREFIX = ".";
    private static final String HELP_MESSAGE = "Try '.help'";

    public CommandManager() {
        super(Arrays.asList(new HelpCommand(), new ToggleCommand(), new ConfigCommand(), new BindCommand(), new HideCommand(), new NameCommand(), new PanicCommand(), new VClipCommand(), new HClipCommand(), new FriendCommand(), new ClientNameCommand()));
        Tifality.getInstance().getEventBus().subscribe(this);
    }

    @Listener
    public void onSendMessageEvent(SendMessageEvent event) {
        String message = event.getMessage();
        if (message.startsWith(PREFIX)) {
            event.setCancelled();
            String removedPrefix = message.substring(1);
            String[] arguments2 = removedPrefix.split(" ");
            if (!removedPrefix.isEmpty() && arguments2.length > 0) {
                for (Command command : this.getElements()) {
                    for (String alias : command.getAliases()) {
                        if (!alias.equalsIgnoreCase(arguments2[0])) continue;
                        try {
                            command.execute(arguments2);
                        }
                        catch (CommandExecutionException e) {
                            Wrapper.addChatMessage("Invalid command syntax. Hint: " + e.getMessage());
                            DevNotifications.getManager().post("Invalid command syntax. Hint: " + e.getMessage());
                        }
                        return;
                    }
                }
                Wrapper.addChatMessage("'" + arguments2[0] + "' is not a command. " + HELP_MESSAGE);
                DevNotifications.getManager().post("'" + arguments2[0] + "' is not a command. " + HELP_MESSAGE);
            } else {
                Wrapper.addChatMessage("No arguments were supplied. Try '.help'");
            }
            DevNotifications.getManager().post("No arguments were supplied. Try '.help'");
        }
    }
}

