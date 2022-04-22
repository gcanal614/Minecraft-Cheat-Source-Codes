package wtf.astronicy.IMPL.module.binding;

import java.util.List;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.game.KeyPressEvent;
import wtf.astronicy.IMPL.module.ModuleManager;
import wtf.astronicy.IMPL.module.impl.Module;

public final class KeyBindHandler {
   private final ModuleManager moduleManager;

   public KeyBindHandler(ModuleManager moduleManager) {
      this.moduleManager = moduleManager;
   }

   @Listener(KeyPressEvent.class)
   public final void onKeyPress(KeyPressEvent event) {
      List modules = this.moduleManager.getModules();
      int i = 0;

      for(int keysSize = modules.size(); i < keysSize; ++i) {
         Module module = (Module)modules.get(i);
         if (event.getKeyCode() == module.getKeyBind().getKeyCode()) {
            module.toggle();
         }
      }

   }
}
