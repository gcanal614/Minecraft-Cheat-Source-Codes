/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.io.Charsets
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import de.fanta.Client;
import de.fanta.design.AltManager;
import de.fanta.gui.flux.GuiButtonFanta;
import de.fanta.utils.AnimationUtil;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.UnicodeFontRenderer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class GuiMainMenu
extends GuiScreen
implements GuiYesNoCallback {
    public static final String MORE_INFO_TEXT = "Please click " + (Object)((Object)EnumChatFormatting.UNDERLINE) + "here" + (Object)((Object)EnumChatFormatting.RESET) + " for more information.";
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    private final Object threadLock;
    public boolean shaderSwitch;
    private final float updateCounter;
    private final boolean field_175375_v = true;
    private String splashText;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;
    private int openGLWarning2Width;
    private int openGLWarning1Width;
    private int openGLWarningX1;
    private int openGLWarningY1;
    private int openGLWarningX2;
    private int openGLWarningY2;
    private ResourceLocation backgroundTexture;
    private double current;
    final UnicodeFontRenderer nameFont;
    final UnicodeFontRenderer versionFont;

    public GuiMainMenu() {
        block18: {
            this.threadLock = new Object();
            this.field_175375_v = true;
            this.nameFont = UnicodeFontRenderer.getFontOnPC("Moonbeam", 100);
            this.versionFont = UnicodeFontRenderer.getFontOnPC("Sitka Text", 100);
            this.openGLWarning2 = MORE_INFO_TEXT;
            this.splashText = "missingno";
            BufferedReader bufferedreader = null;
            try {
                try {
                    String s;
                    ArrayList list = Lists.newArrayList();
                    bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
                    while ((s = bufferedreader.readLine()) != null) {
                        if ((s = s.trim()).isEmpty()) continue;
                        list.add(s);
                    }
                    if (!list.isEmpty()) {
                        do {
                            this.splashText = (String)list.get(RANDOM.nextInt(list.size()));
                        } while (this.splashText.hashCode() == 125780783);
                    }
                }
                catch (IOException iOException) {
                    if (bufferedreader != null) {
                        try {
                            bufferedreader.close();
                        }
                        catch (IOException iOException2) {}
                    }
                    break block18;
                }
            }
            catch (Throwable throwable) {
                if (bufferedreader != null) {
                    try {
                        bufferedreader.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                throw throwable;
            }
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        this.current = 0.5;
        int i = 24;
        int j = height / 4 + 48;
        this.buttonList.add(new GuiButtonFanta(26, width / 2 - 100, i + j + 25, I18n.format("AltManager", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(0, width / 2 - 100, j + 62 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(1, width / 2 - 100, j, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(2, width / 2 - 100, i + j, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(4, width / 2 + 2, j + 62 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        Object object = this.threadLock;
        synchronized (object) {
            this.openGLWarning1Width = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.openGLWarning2Width = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
            this.openGLWarningX1 = (width - k) / 2;
            this.openGLWarningY1 = ((GuiButton)this.buttonList.get((int)0)).yPosition - 24;
            this.openGLWarningX2 = this.openGLWarningX1 + k;
            this.openGLWarningY2 = this.openGLWarningY1 + 24;
        }
        if (!Client.allowed) {
            try {
                Display.releaseContext();
            }
            catch (LWJGLException e) {
                e.printStackTrace();
            }
        }
        this.mc.setConnectedToRealms(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 26) {
            this.mc.displayGuiScreen(new AltManager(this));
        }
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int i = 274;
        int j = width / 2 - i / 2;
        int k = 30;
        String mcInfo = "Minecraft 1.8.8 Modded by LCA_MODZ,Command JO,Exeos";
        String copyright = "Fanta V" + Client.INSTANCE.version + " Beta Build";
        GuiMainMenu.drawGradientRect(0.0, 0.0, width, height, 0, Integer.MIN_VALUE);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (Keyboard.isKeyDown((int)205)) {
            this.shaderSwitch = true;
        }
        if (Keyboard.isKeyDown((int)203)) {
            this.shaderSwitch = false;
        }
        if (this.shaderSwitch) {
            Client.getBackgrundAPI2().renderShader();
        } else {
            Client.getBackgrundAPI3().renderShader();
        }
        Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow(copyright, width - Client.INSTANCE.fluxTabGuiFont.getStringWidth(copyright) - 4, height - 15, -1);
        Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow(mcInfo, 0.0f, height - 15, -1);
        this.current = AnimationUtil.animate(this.current, 1.0, 0.02);
        GL11.glPushMatrix();
        GL11.glScaled((double)this.current, (double)this.current, (double)this.current);
        RenderUtil.drawRoundedRect2(width / 2 - 120, height / 4 + 40, 240.0, 110.0, 11.0, new Color(0, 0, 0, 120));
        Client.INSTANCE.fluxTabGuiFont2.drawStringWithShadow(Client.INSTANCE.getName(), width / 2 - Client.INSTANCE.unicodeBasicFontRenderer2.getStringWidth(Client.INSTANCE.getName()) / 2, height / 4, 0xFFFFFF);
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            GuiMainMenu.drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 0x55200000);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (width - this.openGLWarning2Width) / 2, ((GuiButton)this.buttonList.get((int)0)).yPosition - 12, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Object object = this.threadLock;
        synchronized (object) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink((GuiYesNoCallback)this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }

    @Override
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
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                }
                catch (Throwable throwable) {
                    logger.error("Couldn't open link", throwable);
                }
            }
            this.mc.displayGuiScreen(this);
        }
    }
}

