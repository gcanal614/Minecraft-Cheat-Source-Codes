package wtf.astronicy.IMPL.command.impl;

import com.google.common.collect.UnmodifiableIterator;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.command.AbstractCommand;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.utils.Logger;

import org.lwjgl.input.Keyboard;

public final class BindCommand extends AbstractCommand {
   public BindCommand() {
      super("Bind", "Set and delete key binds.", "bind <module/*> <key/NONE>", "bind", "b");
   }

   public void execute(String... arguments) {
      if (arguments.length == 3) {
         String moduleName = arguments[1];
         Module module = Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(moduleName);
         String bind = arguments[2].toUpperCase();
         if (module != null) {
            module.getKeyBind().setKeyCode(Keyboard.getKeyIndex(bind));
            Logger.log("Bound '" + module.getLabel() + "' to '" + bind + "'.");
         } else if (!moduleName.equals("*")) {
            Logger.log("'" + moduleName + "' is not a module.");
         } else {
            UnmodifiableIterator var5 = Astronicy.MANAGER_REGISTRY.moduleManager.getModules().iterator();

            while(var5.hasNext()) {
               Module module1 = (Module)var5.next();
               module1.getKeyBind().setKey(bind);
            }

            Logger.log("Bound all modules to '" + bind + "'.");
         }

         Astronicy.MANAGER_REGISTRY.moduleManager.saveData();
      } else {
         this.usage();
      }

   }
}
