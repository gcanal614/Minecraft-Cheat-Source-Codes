package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;

public class GuiResourcePackAvailable extends GuiResourcePackList {
  public GuiResourcePackAvailable(Minecraft mcIn, int p_i45054_2_, int p_i45054_3_, List<ResourcePackListEntry> p_i45054_4_) {
    super(mcIn, p_i45054_2_, p_i45054_3_, p_i45054_4_);
  }
  
  protected String getListHeader() {
    return I18n.format("resourcePack.available.title", new Object[0]);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\client\gui\GuiResourcePackAvailable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */