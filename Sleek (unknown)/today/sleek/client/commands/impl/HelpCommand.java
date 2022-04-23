package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

import java.text.MessageFormat;
import java.util.Arrays;

@CommandData(
        name = "help",
        description = "Displays this message",
        aliases = {"h"}
)
public class HelpCommand extends Command {
    @Override
    public void run(String[] args) {
        for (Command command : Sleek.getInstance().getCommandManager().getCommands()) {
            CommandData anno = command.getClass().getAnnotation(CommandData.class);
            String name = anno.name();
            String description = anno.description();
            String aliases;
            StringBuilder sb = new StringBuilder();
            for (String alias : anno.aliases()) {
                sb.append(alias);
                if (Arrays.asList(anno.aliases()).indexOf(alias) != anno.aliases().length - 1) {
                    sb.append(",");
                }
            }
            aliases = sb.toString();
            ChatUtil.log(MessageFormat.format("§b{0} §7- §f{1}" + (aliases.isEmpty() ? "" : " §7(Aliases: {2})"), name, description, aliases));
        }
    }
}
