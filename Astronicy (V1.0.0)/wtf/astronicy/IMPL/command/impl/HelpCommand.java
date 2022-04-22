package wtf.astronicy.IMPL.command.impl;

import java.util.Iterator;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.command.AbstractCommand;
import wtf.astronicy.IMPL.utils.Logger;

public final class HelpCommand extends AbstractCommand {
   public HelpCommand() {
      super("Help", "Lists all commands.", "help", "help", "h");
   }

   public void execute(String... arguments) {
      if (arguments.length == 1) {
         Logger.log("---------------- Help ----------------");
         Iterator var2 = Astronicy.MANAGER_REGISTRY.commandManager.getCommands().iterator();

         while(var2.hasNext()) {
            AbstractCommand command = (AbstractCommand)var2.next();
            Logger.log(command.getName() + ": §F" + command.getDescription());
         }
      } else {
         this.usage();
      }

   }
}
