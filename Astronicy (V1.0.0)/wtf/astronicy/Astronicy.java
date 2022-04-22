package wtf.astronicy;

import org.lwjgl.opengl.Display;

import wtf.astronicy.API.registry.impl.EventBusRegistry;
import wtf.astronicy.API.registry.impl.ManagerRegistry;

public final class Astronicy {
   public static final Astronicy INSTANCE = builder().name("Astronicy").version("1.0.0").authors("Matzin").build();
   public static final EventBusRegistry EVENT_BUS_REGISTRY = new EventBusRegistry();
   public static final ManagerRegistry MANAGER_REGISTRY = new ManagerRegistry();
   private final String name;
   private final String version;
   private final String[] authors;

   private Astronicy(String name, String version, String[] authors) {
      this.name = name;
      this.version = version;
      this.authors = authors;
   }

   private static Astronicy.Builder builder() {
      return new Astronicy.Builder();
   }

   public String getName() {
      return this.name;
   }

   public String getVersion() {
      return this.version;
   }

   public String[] getAuthors() {
      return this.authors;
   }

   public void start() {
      Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
      Display.setTitle(this.name + " " + this.version);
   }

   public void stop() {
      MANAGER_REGISTRY.moduleManager.saveData();
   }

   public static class Builder {
      private String name;
      private String version;
      private String[] authors;

      protected Builder() {
      }

      public Astronicy.Builder name(String name) {
         this.name = name;
         return this;
      }

      public Astronicy.Builder version(String version) {
         this.version = version;
         return this;
      }

      public Astronicy.Builder authors(String... authors) {
         this.authors = authors;
         return this;
      }

      public final Astronicy build() {
         return new Astronicy(this.name, this.version, this.authors);
      }
   }
}
