package wtf.astronicy.IMPL.module.impl;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.binding.KeyboardKey;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Configurable;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.IMPL.utils.render.Translate;
import wtf.astronicy.UIs.Notifications.NotificationManager;
import net.minecraft.client.Minecraft;

public class Module extends Configurable {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   private final String label = ((ModName)this.getClass().getAnnotation(ModName.class)).value();
   private final ModuleCategory category = ((Category)this.getClass().getAnnotation(Category.class)).value();
   private final Translate translate = new Translate(0.0F, 0.0F);
   private final KeyboardKey keyBind = new KeyboardKey("");
   private String[] aliases;
   private boolean enabled;
   private boolean hidden;

   public Module() {
      this.aliases = new String[]{this.label};
   }

   private static String capitalizeThenLowercase(String str) {
      return Character.toTitleCase(str.charAt(0)) + str.substring(1).toLowerCase();
   }

   public String[] getAliases() {
      return this.aliases;
   }

   public void setAliases(String[] aliases) {
      this.aliases = aliases;
   }

   public KeyboardKey getKeyBind() {
      return this.keyBind;
   }

   public Translate getTranslate() {
      return this.translate;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public void setHidden(boolean hidden) {
      this.hidden = hidden;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      if (this.enabled != enabled) {
         this.enabled = enabled;
         if (enabled) {
            this.onEnabled();
            Astronicy.EVENT_BUS_REGISTRY.eventBus.subscribe(this);
         } else {
            Astronicy.EVENT_BUS_REGISTRY.eventBus.unsubscribe(this);
            this.onDisabled();
         }
      }

   }

   public final String getLabel() {
      return this.label;
   }

   public final String getDisplayLabel() {
      EnumOption mode = this.getMode();
      if (mode != null && mode.getValue() != null) {
         String modeValue = ((Enum)mode.getValue()).name();
         String formattedMode = capitalizeThenLowercase(modeValue);
         return this.label + "§7 " + formattedMode;
      } else {
         return this.label;
      }
   }

   public final ModuleCategory getCategory() {
      return this.category;
   }

   public final void toggle() {
      this.setEnabled(!this.enabled);
   }

   public void onEnabled() {
	   NotificationManager.show("Enabled", "Enabled " + getLabel(), 2000);
   }

   public void onDisabled() {
	   NotificationManager.show("Enabled", "Disabled " + getLabel(), 2000);
   }
}
