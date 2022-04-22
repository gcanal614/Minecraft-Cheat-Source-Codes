package wtf.astronicy.IMPL.command;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.game.SendMessageEvent;

public final class CommandHandler {
   private final CommandManager commandManager;

   public CommandHandler(CommandManager commandManager) {
      this.commandManager = commandManager;
   }

   @Listener(SendMessageEvent.class)
   public final void onMessage(SendMessageEvent event) {
      String msg = event.getMessage();
      if (msg.length() > 0 && msg.startsWith(".")) {
         event.setCancelled(this.commandManager.execute(msg));
      }

   }
}
