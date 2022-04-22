package me.util;

import me.Hime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

public class ServerUtil {
 
  
  public static Minecraft mc = Minecraft.getMinecraft();
  
  public static ServerData serverData = mc.getCurrentServerData();
  
  public static void connectToLastServer() {
    if (Hime.instance.serverData == null)
      return; 
    mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, Hime.instance.serverData));
  }
  
  
}