package wtf.astronicy.API.events.game;

import wtf.astronicy.API.events.Cancellable;
import wtf.astronicy.API.events.Event;

public final class SendMessageEvent extends Cancellable implements Event {
   private final String message;

   public SendMessageEvent(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }
}
