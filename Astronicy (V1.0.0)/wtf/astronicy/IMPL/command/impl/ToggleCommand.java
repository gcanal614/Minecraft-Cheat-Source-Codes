package wtf.astronicy.IMPL.command.impl;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.command.AbstractCommand;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.utils.Logger;

public final class ToggleCommand extends AbstractCommand {
   public ToggleCommand() {
      super("Toggle", "Toggle modules on and off.", "toggle <module>", "toggle", "t");
   }

   public void execute(String... arguments) {
      if (arguments.length == 2) {
         String moduleName = arguments[1];
         Module module = Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(moduleName);
         if (module != null) {
            module.toggle();
         } else {
            Logger.log("'" + moduleName + "' is not a module.");
         }
      } else {
         this.usage();
      }

   }
}
