package wtf.astronicy.IMPL.module;

public enum ModuleCategory {
   COMBAT("Combat"),
   PLAYER("Player"),
   MOVEMENT("Movement"),
   VISUALS("Visuals"),
   WORLD("World"),
   EXPLOIT("Exploit"),
   CLIENT("Client");

   private final String name;

   private ModuleCategory(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }
}
