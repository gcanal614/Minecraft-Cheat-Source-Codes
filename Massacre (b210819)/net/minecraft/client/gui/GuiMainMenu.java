package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;
import team.massacre.api.ui.alt.GuiAltManager;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
   private static final AtomicInteger field_175373_f = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();
   private static final Random RANDOM = new Random();
   private float updateCounter;
   private String splashText;
   private GuiButton buttonResetDemo;
   private int panoramaTimer;
   private DynamicTexture viewportTexture;
   private boolean field_175375_v = true;
   private final Object threadLock = new Object();
   private String openGLWarning1;
   private String openGLWarning2;
   private String openGLWarningLink;
   private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
   private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
   public static final String field_96138_a;
   private int field_92024_r;
   private int field_92023_s;
   private int field_92022_t;
   private int field_92021_u;
   private int field_92020_v;
   private int field_92019_w;
   private ResourceLocation backgroundTexture;
   private GuiButton realmsButton;

   public GuiMainMenu() {
      this.openGLWarning2 = field_96138_a;
      this.splashText = "missingno";
      BufferedReader bufferedreader = null;

      try {
         List<String> list = Lists.newArrayList();
         bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));

         String s;
         while((s = bufferedreader.readLine()) != null) {
            s = s.trim();
            if (!s.isEmpty()) {
               list.add(s);
            }
         }

         if (!list.isEmpty()) {
            do {
               this.splashText = (String)list.get(RANDOM.nextInt(list.size()));
            } while(this.splashText.hashCode() == 125780783);
         }
      } catch (IOException var12) {
      } finally {
         if (bufferedreader != null) {
            try {
               bufferedreader.close();
            } catch (IOException var11) {
            }
         }

      }

      this.updateCounter = RANDOM.nextFloat();
      this.openGLWarning1 = "";
      if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
         this.openGLWarning1 = I18n.format("title.oldgl1");
         this.openGLWarning2 = I18n.format("title.oldgl2");
         this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
      }

   }

   public void updateScreen() {
      ++this.panoramaTimer;
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
   }

   public void initGui() {
      this.viewportTexture = new DynamicTexture(256, 256);
      this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
         this.splashText = "Merry X-mas!";
      } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
         this.splashText = "Happy new year!";
      } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
         this.splashText = "OOoooOOOoooo! Spooky!";
      }

      int i = true;
      int j = this.height / 4 + 48;
      if (this.mc.isDemo()) {
         this.addDemoButtons(j, 24);
      } else {
         this.addSingleplayerMultiplayerButtons(j, 24);
      }

      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 30, 98, 20, I18n.format("menu.options")));
      this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 30, 98, 20, I18n.format("menu.quit")));
      synchronized(this.threadLock) {
         this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
         this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
         int k = Math.max(this.field_92023_s, this.field_92024_r);
         this.field_92022_t = (this.width - k) / 2;
         this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
         this.field_92020_v = this.field_92022_t + k;
         this.field_92019_w = this.field_92021_u + 24;
      }

      this.mc.func_181537_a(false);
   }

   private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_ + 30, I18n.format("menu.singleplayer")));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1 + 30, I18n.format("menu.multiplayer")));
      this.buttonList.add(new GuiButton(14, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2 + 30, I18n.format("Alt Login")));
   }

   private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
      this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
      this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo")));
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
      if (worldinfo == null) {
         this.buttonResetDemo.enabled = false;
      }

   }

   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 1) {
         this.mc.displayGuiScreen(new GuiSelectWorld(this));
      }

      if (button.id == 2) {
         this.mc.displayGuiScreen(new GuiMultiplayer(this));
      }

      if (button.id == 5) {
         this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
      }

      if (button.id == 11) {
         this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
      }

      if (button.id == 12) {
         ISaveFormat isaveformat = this.mc.getSaveLoader();
         WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
         if (worldinfo != null) {
            GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
            this.mc.displayGuiScreen(guiyesno);
         }
      }

      if (button.id == 14) {
         this.mc.displayGuiScreen(new GuiAltManager());
      }

      if (button.id == 0) {
         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
      }

      if (button.id == 4) {
         this.mc.shutdown();
      }

   }

   private void switchToRealms() {
      RealmsBridge realmsbridge = new RealmsBridge();
      realmsbridge.switchToRealms(this);
   }

   public void confirmClicked(boolean result, int id) {
      if (result && id == 12) {
         ISaveFormat isaveformat = this.mc.getSaveLoader();
         isaveformat.flushCache();
         isaveformat.deleteWorldDirectory("Demo_World");
         this.mc.displayGuiScreen(this);
      } else if (id == 13) {
         if (result) {
            try {
               Class<?> oclass = Class.forName("java.awt.Desktop");
               Object object = oclass.getMethod("getDesktop").invoke((Object)null);
               oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
            } catch (Throwable var5) {
               logger.error("Couldn't open link", var5);
            }
         }

         this.mc.displayGuiScreen(this);
      }

   }

   private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.disableCull();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      int i = 8;

      for(int j = 0; j < i * i; ++j) {
         GlStateManager.pushMatrix();
         float f = ((float)(j % i) / (float)i - 0.5F) / 64.0F;
         float f1 = ((float)(j / i) / (float)i - 0.5F) / 64.0F;
         float f2 = 0.0F;
         GlStateManager.translate(f, f1, f2);
         GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

         for(int k = 0; k < 6; ++k) {
            GlStateManager.pushMatrix();
            if (k == 1) {
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (k == 2) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (k == 3) {
               GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (k == 4) {
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (k == 5) {
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            this.mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            int l = 255 / (j + 1);
            float f3 = 0.0F;
            worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
            worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
            worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
            worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
         }

         GlStateManager.popMatrix();
         GlStateManager.colorMask(true, true, true, false);
      }

      worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
      GlStateManager.colorMask(true, true, true, true);
      GlStateManager.matrixMode(5889);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.enableDepth();
   }

   private void rotateAndBlurSkybox(float p_73968_1_) {
      this.mc.getTextureManager().bindTexture(this.backgroundTexture);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.colorMask(true, true, true, false);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      GlStateManager.disableAlpha();
      int i = 3;

      for(int j = 0; j < i; ++j) {
         float f = 1.0F / (float)(j + 1);
         int k = this.width;
         int l = this.height;
         float f1 = (float)(j - i / 2) / 256.0F;
         worldrenderer.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
         worldrenderer.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
         worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
         worldrenderer.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
      }

      tessellator.draw();
      GlStateManager.enableAlpha();
      GlStateManager.colorMask(true, true, true, true);
   }

   private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
      this.mc.getFramebuffer().unbindFramebuffer();
      GlStateManager.viewport(0, 0, 256, 256);
      this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.rotateAndBlurSkybox(p_73971_3_);
      this.mc.getFramebuffer().bindFramebuffer(true);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      float f = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
      float f1 = (float)this.height * f / 256.0F;
      float f2 = (float)this.width * f / 256.0F;
      int i = this.width;
      int j = this.height;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      worldrenderer.pos(0.0D, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      worldrenderer.pos((double)i, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      worldrenderer.pos((double)i, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      tessellator.draw();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.mc.getTextureManager().bindTexture(new ResourceLocation("massacre/background2.png"));
      Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height, (float)this.width, (float)this.height);
      this.mc.getTextureManager().bindTexture(new ResourceLocation("massacre/MainLogo.png"));
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      Gui.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 96, sr.getScaledHeight() / 2 - 125, 0.0F, 0.0F, 192, 108, 192.0F, 108.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      synchronized(this.threadLock) {
         if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
            GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
            guiconfirmopenlink.disableSecurityWarning();
            this.mc.displayGuiScreen(guiconfirmopenlink);
         }

      }
   }

   static {
      field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
   }
}
