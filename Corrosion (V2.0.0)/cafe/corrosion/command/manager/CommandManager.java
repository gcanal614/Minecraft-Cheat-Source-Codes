/*
 * Decompiled with CFR 0.152.
 */
package cafe.corrosion.command.manager;

import cafe.corrosion.attributes.CommandAttributes;
import cafe.corrosion.command.ICommand;
import cafe.corrosion.command.impl.ConfigCommand;
import cafe.corrosion.command.impl.FriendCommand;
import cafe.corrosion.command.impl.ToggleCommand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<CommandAttributes, ICommand> commandAttributes = new HashMap<CommandAttributes, ICommand>();

    public void init() {
        Arrays.asList(new ToggleCommand(), new ConfigCommand(), new FriendCommand()).forEach(command -> {
            Class<?> clazz = command.getClass();
            if (!clazz.isAnnotationPresent(CommandAttributes.class)) {
                throw new RuntimeException("No CommandAttributes found for command " + clazz.getSimpleName());
            }
            CommandAttributes attributes = clazz.getDeclaredAnnotation(CommandAttributes.class);
            this.commandAttributes.put(attributes, (ICommand)command);
        });
    }

    public void processCommand(String commandName, String[] commandContext) {
        this.commandAttributes.entrySet().stream().filter(entry -> {
            CommandAttributes attributes = (CommandAttributes)entry.getKey();
            return attributes.name().equalsIgnoreCase(commandName);
        }).map(Map.Entry::getValue).forEach(command -> command.handle(commandContext));
    }
}

