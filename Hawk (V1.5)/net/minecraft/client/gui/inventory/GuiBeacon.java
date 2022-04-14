package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiBeacon extends GuiContainer {
   private static final Logger logger = LogManager.getLogger();
   private static final String __OBFID = "CL_00000739";
   private boolean buttonsNotDrawn;
   private IInventory tileBeacon;
   private GuiBeacon.ConfirmButton beaconConfirmButton;
   private static final ResourceLocation beaconGuiTextures = new ResourceLocation("textures/gui/container/beacon.png");

   public void updateScreen() {
      super.updateScreen();
      int var1 = this.tileBeacon.getField(0);
      int var2 = this.tileBeacon.getField(1);
      int var3 = this.tileBeacon.getField(2);
      if (this.buttonsNotDrawn && var1 >= 0) {
         this.buttonsNotDrawn = false;

         int var4;
         int var5;
         int var6;
         int var7;
         GuiBeacon.PowerButton var8;
         for(int var9 = 0; var9 <= 2; ++var9) {
            var4 = TileEntityBeacon.effectsList[var9].length;
            var5 = var4 * 22 + (var4 - 1) * 2;

            for(var6 = 0; var6 < var4; ++var6) {
               var7 = TileEntityBeacon.effectsList[var9][var6].id;
               var8 = new GuiBeacon.PowerButton(this, var9 << 8 | var7, this.guiLeft + 76 + var6 * 24 - var5 / 2, this.guiTop + 22 + var9 * 25, var7, var9);
               this.buttonList.add(var8);
               if (var9 >= var1) {
                  var8.enabled = false;
               } else if (var7 == var2) {
                  var8.func_146140_b(true);
               }
            }
         }

         byte var11 = 3;
         var4 = TileEntityBeacon.effectsList[var11].length + 1;
         var5 = var4 * 22 + (var4 - 1) * 2;

         for(var6 = 0; var6 < var4 - 1; ++var6) {
            var7 = TileEntityBeacon.effectsList[var11][var6].id;
            var8 = new GuiBeacon.PowerButton(this, var11 << 8 | var7, this.guiLeft + 167 + var6 * 24 - var5 / 2, this.guiTop + 47, var7, var11);
            this.buttonList.add(var8);
            if (var11 >= var1) {
               var8.enabled = false;
            } else if (var7 == var3) {
               var8.func_146140_b(true);
            }
         }

         if (var2 > 0) {
            GuiBeacon.PowerButton var10 = new GuiBeacon.PowerButton(this, var11 << 8 | var2, this.guiLeft + 167 + (var4 - 1) * 24 - var5 / 2, this.guiTop + 47, var2, var11);
            this.buttonList.add(var10);
            if (var11 >= var1) {
               var10.enabled = false;
            } else if (var2 == var3) {
               var10.func_146140_b(true);
            }
         }
      }

      this.beaconConfirmButton.enabled = this.tileBeacon.getStackInSlot(0) != null && var2 > 0;
   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      RenderHelper.disableStandardItemLighting();
      this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary"), 62, 10, 14737632);
      this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary"), 169, 10, 14737632);
      Iterator var3 = this.buttonList.iterator();

      while(var3.hasNext()) {
         GuiButton var4 = (GuiButton)var3.next();
         if (var4.isMouseOver()) {
            var4.drawButtonForegroundLayer(var1 - this.guiLeft, var2 - this.guiTop);
            break;
         }
      }

      RenderHelper.enableGUIStandardItemLighting();
   }

   public void initGui() {
      super.initGui();
      this.buttonList.add(this.beaconConfirmButton = new GuiBeacon.ConfirmButton(this, -1, this.guiLeft + 164, this.guiTop + 107));
      this.buttonList.add(new GuiBeacon.CancelButton(this, -2, this.guiLeft + 190, this.guiTop + 107));
      this.buttonsNotDrawn = true;
      this.beaconConfirmButton.enabled = false;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id == -2) {
         this.mc.displayGuiScreen((GuiScreen)null);
      } else if (var1.id == -1) {
         String var2 = "MC|Beacon";
         PacketBuffer var3 = new PacketBuffer(Unpooled.buffer());
         var3.writeInt(this.tileBeacon.getField(1));
         var3.writeInt(this.tileBeacon.getField(2));
         this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
         this.mc.displayGuiScreen((GuiScreen)null);
      } else if (var1 instanceof GuiBeacon.PowerButton) {
         if (((GuiBeacon.PowerButton)var1).func_146141_c()) {
            return;
         }

         int var5 = var1.id;
         int var6 = var5 & 255;
         int var4 = var5 >> 8;
         if (var4 < 3) {
            this.tileBeacon.setField(1, var6);
         } else {
            this.tileBeacon.setField(2, var6);
         }

         this.buttonList.clear();
         this.initGui();
         this.updateScreen();
      }

   }

   static ResourceLocation access$0() {
      return beaconGuiTextures;
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(beaconGuiTextures);
      int var4 = (this.width - this.xSize) / 2;
      int var5 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
      this.itemRender.zLevel = 100.0F;
      this.itemRender.func_180450_b(new ItemStack(Items.emerald), var4 + 42, var5 + 109);
      this.itemRender.func_180450_b(new ItemStack(Items.diamond), var4 + 42 + 22, var5 + 109);
      this.itemRender.func_180450_b(new ItemStack(Items.gold_ingot), var4 + 42 + 44, var5 + 109);
      this.itemRender.func_180450_b(new ItemStack(Items.iron_ingot), var4 + 42 + 66, var5 + 109);
      this.itemRender.zLevel = 0.0F;
   }

   static void access$1(GuiBeacon var0, String var1, int var2, int var3) {
      var0.drawCreativeTabHoveringText(var1, var2, var3);
   }

   public GuiBeacon(InventoryPlayer var1, IInventory var2) {
      super(new ContainerBeacon(var1, var2));
      this.tileBeacon = var2;
      this.xSize = 230;
      this.ySize = 219;
   }

   class ConfirmButton extends GuiBeacon.Button {
      final GuiBeacon this$0;
      private static final String __OBFID = "CL_00000741";

      public ConfirmButton(GuiBeacon var1, int var2, int var3, int var4) {
         super(var2, var3, var4, GuiBeacon.access$0(), 90, 220);
         this.this$0 = var1;
      }

      public void drawButtonForegroundLayer(int var1, int var2) {
         GuiBeacon.access$1(this.this$0, I18n.format("gui.done"), var1, var2);
      }
   }

   static class Button extends GuiButton {
      private final int field_146144_p;
      private boolean field_146142_r;
      private final int field_146143_q;
      private final ResourceLocation field_146145_o;
      private static final String __OBFID = "CL_00000743";

      public boolean func_146141_c() {
         return this.field_146142_r;
      }

      public void func_146140_b(boolean var1) {
         this.field_146142_r = var1;
      }

      protected Button(int var1, int var2, int var3, ResourceLocation var4, int var5, int var6) {
         super(var1, var2, var3, 22, 22, "");
         this.field_146145_o = var4;
         this.field_146144_p = var5;
         this.field_146143_q = var6;
      }

      public void drawButton(Minecraft var1, int var2, int var3) {
         if (this.visible) {
            var1.getTextureManager().bindTexture(GuiBeacon.access$0());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height;
            short var4 = 219;
            int var5 = 0;
            if (!this.enabled) {
               var5 += this.width * 2;
            } else if (this.field_146142_r) {
               var5 += this.width * 1;
            } else if (this.hovered) {
               var5 += this.width * 3;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var5, var4, this.width, this.height);
            if (!GuiBeacon.access$0().equals(this.field_146145_o)) {
               var1.getTextureManager().bindTexture(this.field_146145_o);
            }

            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.field_146144_p, this.field_146143_q, 18, 18);
         }

      }
   }

   class PowerButton extends GuiBeacon.Button {
      private static final String __OBFID = "CL_00000742";
      final GuiBeacon this$0;
      private final int field_146149_p;
      private final int field_146148_q;

      public void drawButtonForegroundLayer(int var1, int var2) {
         String var3 = I18n.format(Potion.potionTypes[this.field_146149_p].getName());
         if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
            var3 = String.valueOf((new StringBuilder(String.valueOf(var3))).append(" II"));
         }

         GuiBeacon.access$1(this.this$0, var3, var1, var2);
      }

      public PowerButton(GuiBeacon var1, int var2, int var3, int var4, int var5, int var6) {
         super(var2, var3, var4, GuiContainer.inventoryBackground, 0 + Potion.potionTypes[var5].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[var5].getStatusIconIndex() / 8 * 18);
         this.this$0 = var1;
         this.field_146149_p = var5;
         this.field_146148_q = var6;
      }
   }

   class CancelButton extends GuiBeacon.Button {
      final GuiBeacon this$0;
      private static final String __OBFID = "CL_00000740";

      public CancelButton(GuiBeacon var1, int var2, int var3, int var4) {
         super(var2, var3, var4, GuiBeacon.access$0(), 112, 220);
         this.this$0 = var1;
      }

      public void drawButtonForegroundLayer(int var1, int var2) {
         GuiBeacon.access$1(this.this$0, I18n.format("gui.cancel"), var1, var2);
      }
   }
}
