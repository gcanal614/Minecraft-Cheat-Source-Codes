package wtf.astronicy.API.events.game;

import wtf.astronicy.API.events.Event;

public final class KeyPressEvent implements Event {
   private final int keyCode;

   public KeyPressEvent(int keyCode) {
      this.keyCode = keyCode;
   }

   public int getKeyCode() {
      return this.keyCode;
   }
}
