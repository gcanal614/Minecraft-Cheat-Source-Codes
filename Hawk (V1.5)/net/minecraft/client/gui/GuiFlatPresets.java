package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

public class GuiFlatPresets extends GuiScreen {
   private static final List field_146431_f = Lists.newArrayList();
   private String field_146436_r;
   private GuiTextField field_146433_u;
   private final GuiCreateFlatWorld field_146432_g;
   private String field_146439_i;
   private GuiFlatPresets.ListSlot field_146435_s;
   private String field_146438_h;
   private static final String __OBFID = "CL_00000704";
   private GuiButton field_146434_t;

   private static void func_146421_a(String var0, Item var1, BiomeGenBase var2, List var3, FlatLayerInfo... var4) {
      func_175354_a(var0, var1, 0, var2, var3, var4);
   }

   public GuiFlatPresets(GuiCreateFlatWorld var1) {
      this.field_146432_g = var1;
   }

   private static void func_146425_a(String var0, Item var1, BiomeGenBase var2, FlatLayerInfo... var3) {
      func_175354_a(var0, var1, 0, var2, (List)null, var3);
   }

   static GuiTextField access$1(GuiFlatPresets var0) {
      return var0.field_146433_u;
   }

   public void updateScreen() {
      this.field_146433_u.updateCursorCounter();
      super.updateScreen();
   }

   public void func_146426_g() {
      boolean var1 = this.func_146430_p();
      this.field_146434_t.enabled = var1;
   }

   private static void func_175354_a(String var0, Item var1, int var2, BiomeGenBase var3, List var4, FlatLayerInfo... var5) {
      FlatGeneratorInfo var6 = new FlatGeneratorInfo();

      for(int var7 = var5.length - 1; var7 >= 0; --var7) {
         var6.getFlatLayers().add(var5[var7]);
      }

      var6.setBiome(var3.biomeID);
      var6.func_82645_d();
      if (var4 != null) {
         Iterator var9 = var4.iterator();

         while(var9.hasNext()) {
            String var8 = (String)var9.next();
            var6.getWorldFeatures().put(var8, Maps.newHashMap());
         }
      }

      field_146431_f.add(new GuiFlatPresets.LayerItem(var1, var2, var0, var6.toString()));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id == 0 && this.func_146430_p()) {
         this.field_146432_g.func_146383_a(this.field_146433_u.getText());
         this.mc.displayGuiScreen(this.field_146432_g);
      } else if (var1.id == 1) {
         this.mc.displayGuiScreen(this.field_146432_g);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.field_146435_s.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.field_146438_h, this.width / 2, 8, 16777215);
      this.drawString(this.fontRendererObj, this.field_146439_i, 50, 30, 10526880);
      this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
      this.field_146433_u.drawTextBox();
      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (!this.field_146433_u.textboxKeyTyped(var1, var2)) {
         super.keyTyped(var1, var2);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      this.field_146433_u.mouseClicked(var1, var2, var3);
      super.mouseClicked(var1, var2, var3);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void initGui() {
      this.buttonList.clear();
      Keyboard.enableRepeatEvents(true);
      this.field_146438_h = I18n.format("createWorld.customize.presets.title");
      this.field_146439_i = I18n.format("createWorld.customize.presets.share");
      this.field_146436_r = I18n.format("createWorld.customize.presets.list");
      this.field_146433_u = new GuiTextField(2, this.fontRendererObj, 50, 40, this.width - 100, 20);
      this.field_146435_s = new GuiFlatPresets.ListSlot(this);
      this.field_146433_u.setMaxStringLength(1230);
      this.field_146433_u.setText(this.field_146432_g.func_146384_e());
      this.buttonList.add(this.field_146434_t = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select")));
      this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
      this.func_146426_g();
   }

   static List access$0() {
      return field_146431_f;
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146435_s.func_178039_p();
   }

   private boolean func_146430_p() {
      return this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < field_146431_f.size() || this.field_146433_u.getText().length() > 1;
   }

   static GuiFlatPresets.ListSlot access$2(GuiFlatPresets var0) {
      return var0.field_146435_s;
   }

   static {
      func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList("village"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock));
      func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
      func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean, Arrays.asList("biome_1", "oceanmonument"), new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
      func_175354_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BlockTallGrass.EnumType.GRASS.func_177044_a(), BiomeGenBase.plains, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
      func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
      func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone));
      func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
      func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
   }

   static class LayerItem {
      private static final String __OBFID = "CL_00000705";
      public int field_179037_b;
      public Item field_148234_a;
      public String field_148233_c;
      public String field_148232_b;

      public LayerItem(Item var1, int var2, String var3, String var4) {
         this.field_148234_a = var1;
         this.field_179037_b = var2;
         this.field_148232_b = var3;
         this.field_148233_c = var4;
      }
   }

   class ListSlot extends GuiSlot {
      final GuiFlatPresets this$0;
      public int field_148175_k;
      private static final String __OBFID = "CL_00000706";

      protected boolean isSelected(int var1) {
         return var1 == this.field_148175_k;
      }

      protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
         GuiFlatPresets.LayerItem var7 = (GuiFlatPresets.LayerItem)GuiFlatPresets.access$0().get(var1);
         this.func_178054_a(var2, var3, var7.field_148234_a, var7.field_179037_b);
         this.this$0.fontRendererObj.drawString(var7.field_148232_b, (double)(var2 + 18 + 5), (double)(var3 + 6), 16777215);
      }

      private void func_148171_c(int var1, int var2, int var3, int var4) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(Gui.statIcons);
         float var5 = 0.0078125F;
         float var6 = 0.0078125F;
         boolean var7 = true;
         boolean var8 = true;
         Tessellator var9 = Tessellator.getInstance();
         WorldRenderer var10 = var9.getWorldRenderer();
         var10.startDrawingQuads();
         var10.addVertexWithUV((double)var1, (double)(var2 + 18), (double)this.this$0.zLevel, (double)((float)var3 * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F));
         var10.addVertexWithUV((double)(var1 + 18), (double)(var2 + 18), (double)this.this$0.zLevel, (double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F));
         var10.addVertexWithUV((double)(var1 + 18), (double)var2, (double)this.this$0.zLevel, (double)((float)(var3 + 18) * 0.0078125F), (double)((float)var4 * 0.0078125F));
         var10.addVertexWithUV((double)var1, (double)var2, (double)this.this$0.zLevel, (double)((float)var3 * 0.0078125F), (double)((float)var4 * 0.0078125F));
         var9.draw();
      }

      public ListSlot(GuiFlatPresets var1) {
         super(var1.mc, var1.width, var1.height, 80, var1.height - 37, 24);
         this.this$0 = var1;
         this.field_148175_k = -1;
      }

      protected void drawBackground() {
      }

      protected int getSize() {
         return GuiFlatPresets.access$0().size();
      }

      private void func_148173_e(int var1, int var2) {
         this.func_148171_c(var1, var2, 0, 0);
      }

      protected void elementClicked(int var1, boolean var2, int var3, int var4) {
         this.field_148175_k = var1;
         this.this$0.func_146426_g();
         GuiFlatPresets.access$1(this.this$0).setText(((GuiFlatPresets.LayerItem)GuiFlatPresets.access$0().get(GuiFlatPresets.access$2(this.this$0).field_148175_k)).field_148233_c);
      }

      private void func_178054_a(int var1, int var2, Item var3, int var4) {
         this.func_148173_e(var1 + 1, var2 + 1);
         GlStateManager.enableRescaleNormal();
         RenderHelper.enableGUIStandardItemLighting();
         this.this$0.itemRender.func_175042_a(new ItemStack(var3, 1, var4), var1 + 2, var2 + 2);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
      }
   }
}
