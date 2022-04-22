package wtf.astronicy.UIs.menu;

import wtf.astronicy.UIs.alt.gui.GuiAltManager;
import wtf.astronicy.UIs.menu.buttons.TransparentButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;

public final class AstronicyMainMenu extends GuiScreen {
   private ResourceLocation background;

   public void initGui() {
      super.initGui();
      background = new ResourceLocation("Astronicy/menu/background.png");
      this.buttonList.add(new TransparentButton(0, this.width / 2, this.height / 2 - 40, "Singleplayer"));
      this.buttonList.add(new TransparentButton(1, this.width / 2, this.height / 2 - 20, "Multiplayer"));
      this.buttonList.add(new TransparentButton(2, this.width / 2, this.height / 2, "Alt Manager"));
      this.buttonList.add(new TransparentButton(3, this.width / 2, this.height / 2 + 20, "Settings"));
      this.buttonList.add(new TransparentButton(4, this.width / 2, this.height / 2 + 40, "Exit"));
   }

   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         this.mc.displayGuiScreen(new GuiSelectWorld(this));
         break;
      case 1:
         this.mc.displayGuiScreen(new GuiMultiplayer(this));
         break;
      case 2:
         this.mc.displayGuiScreen(new GuiAltManager(this));
         break;
      case 3:
         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
         break;
      case 4:
         mc.shutdown();
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
   }
}
