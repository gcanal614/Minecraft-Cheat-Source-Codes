package wtf.astronicy.UIs.clickgui;

import com.google.common.collect.Lists;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.visuals.hud.HUDMod;
import wtf.astronicy.UIs.clickgui.panel.Panel;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public final class ClickGuiScreen extends GuiScreen {
   private static final HUDMod HUD;
   private static ClickGuiScreen INSTANCE;
   private final List panels = Lists.newArrayList();

   private ClickGuiScreen() {
      ModuleCategory[] categories = ModuleCategory.values();

      for(int i = categories.length - 1; i >= 0; --i) {
         this.panels.add(new Panel(categories[i], 5 + 120 * i, 5));
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      int i = 0;

      for(int panelsSize = this.panels.size(); i < panelsSize; ++i) {
         ((Panel)this.panels.get(i)).onDraw(mouseX, mouseY);
      }

   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int i = 0;

      for(int panelsSize = this.panels.size(); i < panelsSize; ++i) {
         ((Panel)this.panels.get(i)).onMouseClick(mouseX, mouseY, mouseButton);
      }

      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      int i = 0;

      for(int panelsSize = this.panels.size(); i < panelsSize; ++i) {
         ((Panel)this.panels.get(i)).onMouseRelease(mouseX, mouseY, state);
      }

   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      int i = 0;

      for(int panelsSize = this.panels.size(); i < panelsSize; ++i) {
         ((Panel)this.panels.get(i)).onKeyPress(typedChar, keyCode);
      }

      super.keyTyped(typedChar, keyCode);
   }

   public static ClickGuiScreen getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ClickGuiScreen();
      }

      return INSTANCE;
   }

   public static Color getColor() {
      return new Color(255,255,255);
   }

   static {
      HUD = (HUDMod) Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(HUDMod.class);
   }
}
