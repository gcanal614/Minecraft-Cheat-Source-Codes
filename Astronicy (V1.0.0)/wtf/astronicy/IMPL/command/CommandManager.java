package wtf.astronicy.IMPL.command;

import java.util.ArrayList;
import java.util.List;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.command.impl.BindCommand;
import wtf.astronicy.IMPL.command.impl.ClientNameCommand;
import wtf.astronicy.IMPL.command.impl.ConfigCommand;
import wtf.astronicy.IMPL.command.impl.HelpCommand;
import wtf.astronicy.IMPL.command.impl.TeleportCommand;
import wtf.astronicy.IMPL.command.impl.ToggleCommand;
import wtf.astronicy.IMPL.command.impl.VClipCommand;

public final class CommandManager {
   public static final String PREFIX = ".";
   private final List commands = new ArrayList();

   public CommandManager() {
      Astronicy.EVENT_BUS_REGISTRY.eventBus.subscribe(new CommandHandler(this));
      this.commands.add(new HelpCommand());
      this.commands.add(new BindCommand());
      this.commands.add(new VClipCommand());
      this.commands.add(new TeleportCommand());
      this.commands.add(new ToggleCommand());
      this.commands.add(new ConfigCommand());
      this.commands.add(new ClientNameCommand());
   }

   public List getCommands() {
      return this.commands;
   }

   public final boolean execute(String args) {
      String noPrefix = args.substring(1);
      String[] split = noPrefix.split(" ");
      if (split.length > 0) {
         List commands = this.commands;
         int i = 0;

         for(int commandsSize = commands.size(); i < commandsSize; ++i) {
            AbstractCommand command = (AbstractCommand)commands.get(i);
            String[] var8 = command.getAliases();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String alias = var8[var10];
               if (split[0].equalsIgnoreCase(alias)) {
                  command.execute(split);
                  return true;
               }
            }
         }
      }

      return false;
   }
}
