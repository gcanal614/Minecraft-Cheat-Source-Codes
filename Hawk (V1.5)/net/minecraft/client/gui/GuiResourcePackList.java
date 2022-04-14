package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
   protected final Minecraft mc;
   protected final List field_148204_l;
   private static final String __OBFID = "CL_00000825";

   public List getList() {
      return this.field_148204_l;
   }

   public ResourcePackListEntry getListEntry(int var1) {
      return (ResourcePackListEntry)this.getList().get(var1);
   }

   protected int getSize() {
      return this.getList().size();
   }

   protected void drawListHeader(int var1, int var2, Tessellator var3) {
      String var4 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.UNDERLINE).append(EnumChatFormatting.BOLD).append(this.getListHeader()));
      this.mc.fontRendererObj.drawString(var4, (double)(var1 + this.width / 2 - this.mc.fontRendererObj.getStringWidth(var4) / 2), (double)Math.min(this.top + 3, var2), 16777215);
   }

   public GuiListExtended.IGuiListEntry getListEntry1(int var1) {
      return this.getListEntry(var1);
   }

   public GuiResourcePackList(Minecraft var1, int var2, int var3, List var4) {
      super(var1, var2, var3, 32, var3 - 55 + 4, 36);
      this.mc = var1;
      this.field_148204_l = var4;
      this.field_148163_i = false;
      this.setHasListHeader(true, (int)((float)var1.fontRendererObj.FONT_HEIGHT * 1.5F));
   }

   public GuiListExtended.IGuiListEntry getListEntry(int var1) {
      return this.getListEntry(var1);
   }

   protected int getScrollBarX() {
      return this.right - 6;
   }

   protected abstract String getListHeader();

   public int getListWidth() {
      return this.width;
   }
}
