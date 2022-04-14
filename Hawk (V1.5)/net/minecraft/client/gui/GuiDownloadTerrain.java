package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen {
   private int progress;
   private NetHandlerPlayClient netHandlerPlayClient;
   private static final String __OBFID = "CL_00000708";

   public GuiDownloadTerrain(NetHandlerPlayClient var1) {
      this.netHandlerPlayClient = var1;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawBackground(0);
      this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   public void initGui() {
      this.buttonList.clear();
   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }

   public void updateScreen() {
      ++this.progress;
      if (this.progress % 20 == 0) {
         this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
