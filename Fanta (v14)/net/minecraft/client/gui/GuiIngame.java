/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  fr.lavache.anime.Animate
 *  optifine.Config
 *  optifine.CustomColors
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.fanta.Client;
import de.fanta.events.EventType;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.module.impl.visual.ArrayList;
import de.fanta.module.impl.visual.Themes;
import de.fanta.module.impl.world.Scaffold;
import de.fanta.notifications.NotificationManager;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.TimeUtil;
import fr.lavache.anime.Animate;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.GuiStreamIndicator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import optifine.Config;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public class GuiIngame
extends Gui {
    public static int count;
    Animate anim3 = new Animate();
    Animate anim4 = new Animate();
    Animate anim5 = new Animate();
    Animate anim6 = new Animate();
    Animate anim7 = new Animate();
    Animate anim8 = new Animate();
    Animate anim9 = new Animate();
    Animate anim10 = new Animate();
    public static int mouseX;
    public static int mouseY;
    public static int lastMouseX;
    public static int lastMouseY;
    public static boolean dragging;
    public static int posX;
    public static int posY;
    public static double width;
    public static double height;
    private static double Alpha;
    private static final ResourceLocation vignetteTexPath;
    private static final ResourceLocation widgetsTexPath;
    private static final ResourceLocation pumpkinBlurTexPath;
    private static final String __OBFID = "CL_00000661";
    private final Random rand = new Random();
    private final Minecraft mc;
    private final RenderItem itemRenderer;
    private String title;
    private String messsage;
    private long start;
    private long fadedIn;
    private long fadeOut;
    private long end;
    private final GuiNewChat persistantChatGUI;
    private final GuiStreamIndicator streamIndicator;
    private final GuiOverlayDebug overlayDebug;
    private final GuiSpectator spectatorGui;
    private final GuiPlayerTabOverlay overlayPlayerList;
    public float prevVignetteBrightness = 1.0f;
    private int updateCounter;
    private String recordPlaying = "";
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack;
    private int titlesTimer;
    private String field_175201_x = "";
    private String field_175200_y = "";
    private int titleFadeIn;
    private int titleDisplayTime;
    private int titleFadeOut;
    private int playerHealth = 0;
    private int lastPlayerHealth = 0;
    TimeUtil time = new TimeUtil();
    private long startTime;
    private long lastSystemTime = 0L;
    private long healthUpdateCounter = 0L;
    static Minecraft mc1;

    static {
        posX = 2;
        posY = 23;
        width = 75.0;
        height = 75.0;
        Alpha = 0.0;
        vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
        widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
        pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
        mc1 = Minecraft.getMinecraft();
    }

    public GuiIngame(Minecraft mcIn) {
        this.mc = mcIn;
        this.itemRenderer = mcIn.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mcIn);
        this.spectatorGui = new GuiSpectator(mcIn);
        this.persistantChatGUI = new GuiNewChat(mcIn);
        this.streamIndicator = new GuiStreamIndicator(mcIn);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
        this.func_175177_a();
    }

    public void func_175177_a() {
        this.titleFadeIn = 10;
        this.titleDisplayTime = 70;
        this.titleFadeOut = 20;
    }

    public void renderGameOverlay(float partialTicks) {
        ScoreObjective scoreobjective1;
        float f;
        int index;
        int j;
        int i;
        ScaledResolution scaledresolution;
        block63: {
            block64: {
                scaledresolution = new ScaledResolution(this.mc);
                i = scaledresolution.getScaledWidth();
                j = scaledresolution.getScaledHeight();
                this.mc.entityRenderer.setupOverlayRendering();
                GlStateManager.enableBlend();
                EventRender2D eventRender2D = new EventRender2D();
                eventRender2D.setType(EventType.PRE);
                Client.INSTANCE.onEvent(eventRender2D);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                index = 0;
                Client.blurHelper.blur2(0.0f, 0.0f, 0.0f, 15.0f, 25.0f);
                int xP = 15;
                int yP = 5;
                int widthP = Client.INSTANCE.fluxTabGuiFont.getStringWidth("atero");
                int heightP = (int)Client.INSTANCE.fluxTabGuiFont.getHeight("atero") / 2;
                LocalDateTime now = LocalDateTime.now();
                double seconds = now.getSecond();
                double minutes = now.getMinute();
                double hours = now.getHour();
                int width = scaledresolution.getScaledWidth() / 10;
                int height = scaledresolution.getScaledHeight() / 45;
                int clockW = 30;
                int clockH = 30;
                if (!Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                    if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption == "Novoline2.0") {
                        Gui.drawRect(13.0f, 5.0f, 45 + Client.INSTANCE.arriall.getStringWidth("Fanta | " + Client.INSTANCE.ircClient.getIngameName() + " | " + Minecraft.getDebugFPS() + " | " + this.mc.getCurrentServerData().serverIP + " | " + now.getHour() + ":" + GuiIngame.insertNulls(now.getMinute(), 2) + ":" + GuiIngame.insertNulls(now.getSecond(), 2)), 20.0f, new Color(30, 30, 30, 255).getRGB());
                        Client.INSTANCE.arriall.drawString("Fanta | LCA_MODZ | " + Minecraft.getDebugFPS() + " | " + this.mc.getCurrentServerData().serverIP + " | " + now.getHour() + ":" + GuiIngame.insertNulls(now.getMinute(), 2) + ":" + GuiIngame.insertNulls(now.getSecond(), 2), 17.0f, 8.0f, -1);
                    }
                    if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption != "Novoline2.0" && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption != "Centaurus" && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption != "Unify") {
                        Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow(now.getHour() + ":" + GuiIngame.insertNulls(now.getMinute(), 2) + ":" + GuiIngame.insertNulls(now.getSecond(), 2), 50.0f, 6.0f, -1);
                        Gui.drawRGBLineHorizontal(13.0, 5.0, 72.0, 2.0f, 2, true);
                        Gui.drawRect(13.0f, 5.0f, 85.0f, 20.0f, new Color(0, 0, 0, 140).getRGB());
                    }
                }
                if (Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                    String cfr_ignored_0 = ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption;
                    if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption == "Violence") {
                        Client.INSTANCE.Violence2.drawStringWithShadow("V", 3.0f, 1.0f, new Color(60, 50, 255).getRGB());
                        Client.INSTANCE.Violence.drawString("IOLENCE", 11.0f, 6.0f, -1);
                    }
                }
                if (Client.INSTANCE.moduleManager.getModule("Tabgui").isState() || ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption == "Novoline2.0") break block63;
                if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption == "Unify") {
                    GuiIngame.drawImage(0, 2, 120, 110, new ResourceLocation("Fanta/gui/Unify.png"));
                }
                if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption == "Centaurus") {
                    GuiIngame.drawImage(0, 2, 90, 80, new ResourceLocation("Fanta/gui/Centaurus.png"));
                }
                if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption == "Centaurus") break block63;
                if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption == "Unify") break block64;
                switch (((DropdownBox)Themes.INSTANCE.getSetting((String)"ArrayColor").getSetting()).curOption) {
                    case "RED": {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawString("F", 17.0f, 7.0f, Themes.rainbow(index / 12));
                        break;
                    }
                    case "BLUE": {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawString("F", 17.0f, 7.0f, Themes.rainbow(index / 12));
                        break;
                    }
                    case "GREEN": {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawString("F", 17.0f, 7.0f, Themes.rainbow(index / 12));
                        break;
                    }
                    case "YELLOW": {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawString("F", 17.0f, 7.0f, Themes.rainbow(index / 12));
                        break;
                    }
                    case "Novoline": {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawString("F", 17.0f, 7.0f, Themes.rainbow(index / 12));
                    }
                }
            }
            if (((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption != "Unify") {
                Client.INSTANCE.unicodeBasicFontRenderer.drawString("anta", 23.0f, 7.0f, -1);
            }
        }
        GL11.glDisable((int)3042);
        NotificationManager.render();
        Client.INSTANCE.moduleManager.getModule("Flight").isState();
        int top = scaledresolution.getScaledHeight() / 2;
        int bottom = scaledresolution.getScaledWidth() / 2;
        try {
            if (Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
                GuiIngame.drawRect2((float)scaledresolution.getScaledWidth() / 2.0f + 10.0f, (float)scaledresolution.getScaledHeight() / 2.0f - 5.0f, (float)scaledresolution.getScaledWidth() / 2.0f + 30.0f, (float)scaledresolution.getScaledHeight() / 2.0f + 15.0f, new Color(23, 23, 23, 230).getRGB());
                GuiIngame.drawRect2((float)scaledresolution.getScaledWidth() / 2.0f + 10.0f, (float)scaledresolution.getScaledHeight() / 2.0f - 5.0f, (float)scaledresolution.getScaledWidth() / 2.0f + 30.0f, (float)scaledresolution.getScaledHeight() / 2.0f - 4.0f, Color.green.getRGB());
                ++index;
                Client.INSTANCE.unicodeBasicFontRenderer.drawString("" + Scaffold.getBlockCount(), (float)scaledresolution.getScaledWidth() / 2.0f - (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth("" + Scaffold.getBlockCount()) / 2.0f + 19.5f, top, Color.white.getRGB());
            }
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        if (Config.isVignetteEnabled()) {
            this.renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
        } else {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
        ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinOverlay(scaledresolution);
        }
        if (!this.mc.thePlayer.isPotionActive(Potion.confusion) && (f = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks) > 0.0f) {
            this.func_180474_b(f, scaledresolution);
        }
        if (this.mc.playerController.isSpectator()) {
            this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
        } else {
            this.renderTooltip(scaledresolution, partialTicks);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(icons);
        GlStateManager.enableBlend();
        if (this.showCrosshair() && this.mc.gameSettings.thirdPersonView < 1) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.mc.mcProfiler.startSection("bossHealth");
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();
        if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(scaledresolution);
        }
        GlStateManager.disableBlend();
        if (this.mc.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int l = this.mc.thePlayer.getSleepTimer();
            float f2 = (float)l / 100.0f;
            if (f2 > 1.0f) {
                f2 = 1.0f - (float)(l - 100) / 10.0f;
            }
            int k = (int)(220.0f * f2) << 24 | 0x101020;
            GuiIngame.drawRect(0.0f, 0.0f, i, j, k);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        int i2 = i / 2 - 91;
        if (this.mc.thePlayer.isRidingHorse()) {
            this.renderHorseJumpBar(scaledresolution, i2);
        } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.renderExpBar(scaledresolution, i2);
        }
        if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
            this.func_181551_a(scaledresolution);
        } else if (this.mc.thePlayer.isSpectator()) {
            this.spectatorGui.func_175263_a(scaledresolution);
        }
        if (this.mc.isDemo()) {
            this.renderDemo(scaledresolution);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.renderDebugInfo(scaledresolution);
        }
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            float f3 = (float)this.recordPlayingUpFor - partialTicks;
            int k1 = (int)(f3 * 255.0f / 20.0f);
            if (k1 > 255) {
                k1 = 255;
            }
            if (k1 > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(i / 2, j - 68, 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                int i1 = 0xFFFFFF;
                if (this.recordIsPlaying) {
                    i1 = MathHelper.func_181758_c(f3 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                this.getBasicFontRenderer().drawString(this.recordPlaying, -this.getBasicFontRenderer().getStringWidth(this.recordPlaying) / 2, -4.0f, i1 + (k1 << 24 & 0xFF000000));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        if (this.titlesTimer > 0) {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            float f4 = (float)this.titlesTimer - partialTicks;
            int l1 = 255;
            if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
                float f1 = (float)(this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f4;
                l1 = (int)(f1 * 255.0f / (float)this.titleFadeIn);
            }
            if (this.titlesTimer <= this.titleFadeOut) {
                l1 = (int)(f4 * 255.0f / (float)this.titleFadeOut);
            }
            if ((l1 = MathHelper.clamp_int(l1, 0, 255)) > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(i / 2, j / 2, 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0f, 4.0f, 4.0f);
                int j2 = l1 << 24 & 0xFF000000;
                this.getBasicFontRenderer().drawString(this.field_175201_x, -this.getBasicFontRenderer().getStringWidth(this.field_175201_x) / 2, -10.0f, 0xFFFFFF | j2, true);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                this.getBasicFontRenderer().drawString(this.field_175200_y, -this.getBasicFontRenderer().getStringWidth(this.field_175200_y) / 2, 5.0f, 0xFFFFFF | j2, true);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
        if (scoreplayerteam != null) {
            try {
                int j1 = scoreplayerteam.getChatFormat().getColorIndex();
                if (j1 >= 0) {
                    scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j1);
                }
            }
            catch (NullPointerException j1) {
                // empty catch block
            }
        }
        ScoreObjective scoreObjective = scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreobjective1 != null) {
            this.renderScoreboard(scoreobjective1, scaledresolution);
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, j - 48, 0.0f);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GlStateManager.popMatrix();
        scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
        if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || this.mc.isIntegratedServerRunning() && this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= 1 && scoreobjective1 == null) {
            this.overlayPlayerList.updatePlayerList(false);
        } else {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }

    protected void renderTooltip(ScaledResolution sr, float partialTicks) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(widgetsTexPath);
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            int i = sr.getScaledWidth() / 2;
            float f = zLevel;
            zLevel = -90.0f;
            if (!Client.INSTANCE.moduleManager.getModule("CustomHotbar").isState()) {
                this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
                this.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
            }
            zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            int j = 0;
            while (j < 9) {
                int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
                int l = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(j, k, l, partialTicks, entityplayer);
                ++j;
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    public void renderHorseJumpBar(ScaledResolution p_175186_1_, int p_175186_2_) {
        this.mc.mcProfiler.startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        float f = this.mc.thePlayer.getHorseJumpPower();
        int short1 = 182;
        int i = (int)(f * (float)(short1 + 1));
        int j = p_175186_1_.getScaledHeight() - 32 + 3;
        this.drawTexturedModalRect(p_175186_2_, j, 0, 84, short1, 5);
        if (i > 0) {
            this.drawTexturedModalRect(p_175186_2_, j, 0, 89, i, 5);
        }
        this.mc.mcProfiler.endSection();
    }

    public void renderExpBar(ScaledResolution p_175176_1_, int p_175176_2_) {
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int i = this.mc.thePlayer.xpBarCap();
        if (i > 0) {
            int short1 = 182;
            int k = (int)(this.mc.thePlayer.experience * (float)(short1 + 1));
            int j = p_175176_1_.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(p_175176_2_, j, 0, 64, short1, 5);
            if (k > 0) {
                this.drawTexturedModalRect(p_175176_2_, j, 0, 69, k, 5);
            }
        }
        this.mc.mcProfiler.endSection();
        if (this.mc.thePlayer.experienceLevel > 0) {
            this.mc.mcProfiler.startSection("expLevel");
            int j1 = 8453920;
            if (Config.isCustomColors()) {
                j1 = CustomColors.getExpBarTextColor((int)j1);
            }
            String s = "" + this.mc.thePlayer.experienceLevel;
            int i1 = (p_175176_1_.getScaledWidth() - this.getBasicFontRenderer().getStringWidth(s)) / 2;
            int l = p_175176_1_.getScaledHeight() - 31 - 4;
            boolean flag = false;
            this.getBasicFontRenderer().drawString(s, i1 + 1, l, 0);
            this.getBasicFontRenderer().drawString(s, i1 - 1, l, 0);
            this.getBasicFontRenderer().drawString(s, i1, l + 1, 0);
            this.getBasicFontRenderer().drawString(s, i1, l - 1, 0);
            this.getBasicFontRenderer().drawString(s, i1, l, j1);
            this.mc.mcProfiler.endSection();
        }
    }

    public void func_181551_a(ScaledResolution p_181551_1_) {
        this.mc.mcProfiler.startSection("selectedItemName");
        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
            int k;
            String s = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                s = (Object)((Object)EnumChatFormatting.ITALIC) + s;
            }
            int i = (p_181551_1_.getScaledWidth() - this.getBasicFontRenderer().getStringWidth(s)) / 2;
            int j = p_181551_1_.getScaledHeight() - 59;
            if (!this.mc.playerController.shouldDrawHUD()) {
                j += 14;
            }
            if ((k = (int)((float)this.remainingHighlightTicks * 256.0f / 10.0f)) > 255) {
                k = 255;
            }
            if (k > 0) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.getBasicFontRenderer().drawStringWithShadow(s, i, j, 0xFFFFFF + (k << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
        this.mc.mcProfiler.endSection();
    }

    public void renderDemo(ScaledResolution p_175185_1_) {
        this.mc.mcProfiler.startSection("demo");
        String s = "";
        s = this.mc.theWorld.getTotalWorldTime() >= 120500L ? I18n.format("demo.demoExpired", new Object[0]) : I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())));
        int i = this.getBasicFontRenderer().getStringWidth(s);
        this.getBasicFontRenderer().drawStringWithShadow(s, p_175185_1_.getScaledWidth() - i - 10, 5.0f, 0xFFFFFF);
        this.mc.mcProfiler.endSection();
    }

    protected boolean showCrosshair() {
        block6: {
            block7: {
                block5: {
                    try {
                        if ((!this.mc.gameSettings.showDebugInfo || this.mc.thePlayer.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo) && !Client.INSTANCE.moduleManager.getModule((String)"Crosshair").state) break block5;
                        return false;
                    }
                    catch (NullPointerException e) {
                        return false;
                    }
                }
                if (!this.mc.playerController.isSpectator()) break block6;
                if (this.mc.pointedEntity == null) break block7;
                return true;
            }
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
                return this.mc.theWorld.getTileEntity(blockpos) instanceof IInventory;
            }
            return false;
        }
        return true;
    }

    public void renderStreamIndicator(ScaledResolution p_180478_1_) {
        this.streamIndicator.render(p_180478_1_.getScaledWidth() - 10, 10);
    }

    private void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_) {
        Scoreboard scoreboard = p_180475_1_.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(p_180475_1_);
        java.util.ArrayList arraylist = Lists.newArrayList((Iterable)Iterables.filter(collection, (Predicate)new Predicate(){
            private static final String __OBFID = "CL_00001958";

            public boolean apply(Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }

            public boolean apply(Object p_apply_1_) {
                return this.apply((Score)p_apply_1_);
            }
        }));
        java.util.ArrayList arraylist1 = arraylist.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip((Iterable)arraylist, (int)(collection.size() - 15))) : arraylist;
        int i = this.mc.fontRendererObj.getStringWidth(p_180475_1_.getDisplayName());
        for (Object score : arraylist1) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(((Score)score).getPlayerName());
            String s = String.valueOf(ScorePlayerTeam.formatPlayerName(scoreplayerteam, ((Score)score).getPlayerName())) + ": " + (Object)((Object)EnumChatFormatting.RED) + ((Score)score).getScorePoints();
            i = Math.max(i, Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(s));
        }
        int j1 = arraylist1.size() * this.mc.fontRendererObj.getFontHeight();
        int k1 = Client.INSTANCE.moduleManager.isToggled(Themes.class) || Client.INSTANCE.moduleManager.isToggled(ArrayList.class) ? Client.INSTANCE.moduleManager.getModule(Themes.class).getHeight() + 70 + j1 : p_180475_2_.getScaledHeight() / 2 + j1 + this.mc.fontRendererObj.getFontHeight() + 15;
        int b0 = 3;
        int j = p_180475_2_.getScaledWidth() - i - b0;
        int k = 0;
        for (Object score1 : arraylist1) {
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(((Score)score1).getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, ((Score)score1).getPlayerName());
            String s2 = "" + (Object)((Object)EnumChatFormatting.RED) + ((Score)score1).getScorePoints();
            int l = k1 - ++k * this.mc.fontRendererObj.getFontHeight();
            int i1 = p_180475_2_.getScaledWidth() - b0 + 2;
            GuiIngame.drawRect(j - 2, l, i1, l + this.mc.fontRendererObj.getFontHeight(), new Color(0, 0, 0, 180).getRGB());
            GuiIngame.drawRect(j + 200, l, i1, l + this.mc.fontRendererObj.getFontHeight(), Color.white.getRGB());
            this.mc.fontRendererObj.drawString(s1.replaceAll(this.mc.getCurrentServerData().serverIP.toLowerCase(), "fanta.best"), j, l, Color.white.getRGB());
            if (k != arraylist1.size()) continue;
            String s3 = p_180475_1_.getDisplayName();
            GuiIngame.drawRect(j - 2, l - this.getBasicFontRenderer().getFontHeight() - 1, i1, l - 1, new Color(0, 0, 0, 180).getRGB());
            GuiIngame.drawRect(j + 200, l - this.getBasicFontRenderer().getFontHeight() - 1, i1, l - 1, Color.white.getRGB());
            GuiIngame.drawRect(j - 2, l - 1, i1, l, new Color(0, 0, 0, 180).getRGB());
            GuiIngame.drawRect(j + 200, l - 1, i1, l, Color.white.getRGB());
            this.mc.fontRendererObj.drawString(s3, j + i / 2 - this.mc.fontRendererObj.getStringWidth(s3) / 2, l - this.mc.fontRendererObj.getFontHeight(), Color.white.getRGB());
        }
    }

    private void renderPlayerStats(ScaledResolution p_180477_1_) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            boolean flag;
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            int i = MathHelper.ceiling_float_int(entityplayer.getHealth());
            boolean bl = flag = this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L;
            if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + 20;
            } else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + 10;
            }
            if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
                this.playerHealth = i;
                this.lastPlayerHealth = i;
                this.lastSystemTime = Minecraft.getSystemTime();
            }
            this.playerHealth = i;
            int j = this.lastPlayerHealth;
            this.rand.setSeed(this.updateCounter * 312871);
            boolean flag1 = false;
            FoodStats foodstats = entityplayer.getFoodStats();
            int k = foodstats.getFoodLevel();
            int l = foodstats.getPrevFoodLevel();
            IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            int i1 = p_180477_1_.getScaledWidth() / 2 - 91;
            int j1 = p_180477_1_.getScaledWidth() / 2 + 91;
            int k1 = p_180477_1_.getScaledHeight() - 39;
            float f = (float)iattributeinstance.getAttributeValue();
            float f1 = entityplayer.getAbsorptionAmount();
            int l1 = MathHelper.ceiling_float_int((f + f1) / 2.0f / 10.0f);
            int i2 = Math.max(10 - (l1 - 2), 3);
            int j2 = k1 - (l1 - 1) * i2 - 10;
            float f2 = f1;
            int k2 = entityplayer.getTotalArmorValue();
            int l2 = -1;
            if (entityplayer.isPotionActive(Potion.regeneration)) {
                l2 = this.updateCounter % MathHelper.ceiling_float_int(f + 5.0f);
            }
            this.mc.mcProfiler.startSection("armor");
            int i3 = 0;
            while (i3 < 10) {
                if (k2 > 0) {
                    int j3 = i1 + i3 * 8;
                    if (i3 * 2 + 1 < k2) {
                        this.drawTexturedModalRect(j3, j2, 34, 9, 9, 9);
                    }
                    if (i3 * 2 + 1 == k2) {
                        this.drawTexturedModalRect(j3, j2, 25, 9, 9, 9);
                    }
                    if (i3 * 2 + 1 > k2) {
                        this.drawTexturedModalRect(j3, j2, 16, 9, 9, 9);
                    }
                }
                ++i3;
            }
            this.mc.mcProfiler.endStartSection("health");
            int j5 = MathHelper.ceiling_float_int((f + f1) / 2.0f) - 1;
            while (j5 >= 0) {
                int k5 = 16;
                if (entityplayer.isPotionActive(Potion.poison)) {
                    k5 += 36;
                } else if (entityplayer.isPotionActive(Potion.wither)) {
                    k5 += 72;
                }
                int b0 = 0;
                if (flag) {
                    b0 = 1;
                }
                int k3 = MathHelper.ceiling_float_int((float)(j5 + 1) / 10.0f) - 1;
                int l3 = i1 + j5 % 10 * 8;
                int i4 = k1 - k3 * i2;
                if (i <= 4) {
                    i4 += this.rand.nextInt(2);
                }
                if (j5 == l2) {
                    i4 -= 2;
                }
                int b1 = 0;
                if (EntityPlayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
                    b1 = 5;
                }
                this.drawTexturedModalRect(l3, i4, 16 + b0 * 9, 9 * b1, 9, 9);
                if (flag) {
                    if (j5 * 2 + 1 < j) {
                        this.drawTexturedModalRect(l3, i4, k5 + 54, 9 * b1, 9, 9);
                    }
                    if (j5 * 2 + 1 == j) {
                        this.drawTexturedModalRect(l3, i4, k5 + 63, 9 * b1, 9, 9);
                    }
                }
                if (f2 <= 0.0f) {
                    if (j5 * 2 + 1 < i) {
                        this.drawTexturedModalRect(l3, i4, k5 + 36, 9 * b1, 9, 9);
                    }
                    if (j5 * 2 + 1 == i) {
                        this.drawTexturedModalRect(l3, i4, k5 + 45, 9 * b1, 9, 9);
                    }
                } else {
                    if (f2 == f1 && f1 % 2.0f == 1.0f) {
                        this.drawTexturedModalRect(l3, i4, k5 + 153, 9 * b1, 9, 9);
                    } else {
                        this.drawTexturedModalRect(l3, i4, k5 + 144, 9 * b1, 9, 9);
                    }
                    f2 -= 2.0f;
                }
                --j5;
            }
            Entity entity = entityplayer.ridingEntity;
            if (entity == null) {
                this.mc.mcProfiler.endStartSection("food");
                int l5 = 0;
                while (l5 < 10) {
                    int i8 = k1;
                    int j6 = 16;
                    int b4 = 0;
                    if (entityplayer.isPotionActive(Potion.hunger)) {
                        j6 += 36;
                        b4 = 13;
                    }
                    if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0f && this.updateCounter % (k * 3 + 1) == 0) {
                        i8 = k1 + (this.rand.nextInt(3) - 1);
                    }
                    if (flag1) {
                        b4 = 1;
                    }
                    int k7 = j1 - l5 * 8 - 9;
                    this.drawTexturedModalRect(k7, i8, 16 + b4 * 9, 27, 9, 9);
                    if (flag1) {
                        if (l5 * 2 + 1 < l) {
                            this.drawTexturedModalRect(k7, i8, j6 + 54, 27, 9, 9);
                        }
                        if (l5 * 2 + 1 == l) {
                            this.drawTexturedModalRect(k7, i8, j6 + 63, 27, 9, 9);
                        }
                    }
                    if (l5 * 2 + 1 < k) {
                        this.drawTexturedModalRect(k7, i8, j6 + 36, 27, 9, 9);
                    }
                    if (l5 * 2 + 1 == k) {
                        this.drawTexturedModalRect(k7, i8, j6 + 45, 27, 9, 9);
                    }
                    ++l5;
                }
            } else if (entity instanceof EntityLivingBase) {
                this.mc.mcProfiler.endStartSection("mountHealth");
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                int l7 = (int)Math.ceil(entitylivingbase.getHealth());
                float f3 = entitylivingbase.getMaxHealth();
                int l6 = (int)(f3 + 0.5f) / 2;
                if (l6 > 30) {
                    l6 = 30;
                }
                int j7 = k1;
                int j4 = 0;
                while (l6 > 0) {
                    int k4 = Math.min(l6, 10);
                    l6 -= k4;
                    int l4 = 0;
                    while (l4 < k4) {
                        int b2 = 52;
                        int b3 = 0;
                        if (flag1) {
                            b3 = 1;
                        }
                        int i5 = j1 - l4 * 8 - 9;
                        this.drawTexturedModalRect(i5, j7, b2 + b3 * 9, 9, 9, 9);
                        if (l4 * 2 + 1 + j4 < l7) {
                            this.drawTexturedModalRect(i5, j7, b2 + 36, 9, 9, 9);
                        }
                        if (l4 * 2 + 1 + j4 == l7) {
                            this.drawTexturedModalRect(i5, j7, b2 + 45, 9, 9, 9);
                        }
                        ++l4;
                    }
                    j7 -= 10;
                    j4 += 20;
                }
            }
            this.mc.mcProfiler.endStartSection("air");
            if (entityplayer.isInsideOfMaterial(Material.water)) {
                int i6 = this.mc.thePlayer.getAir();
                int j8 = MathHelper.ceiling_double_int((double)(i6 - 2) * 10.0 / 300.0);
                int k6 = MathHelper.ceiling_double_int((double)i6 * 10.0 / 300.0) - j8;
                int i7 = 0;
                while (i7 < j8 + k6) {
                    if (i7 < j8) {
                        this.drawTexturedModalRect(j1 - i7 * 8 - 9, j2, 16, 18, 9, 9);
                    } else {
                        this.drawTexturedModalRect(j1 - i7 * 8 - 9, j2, 25, 18, 9, 9);
                    }
                    ++i7;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }

    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            BasicFontRenderer fontrenderer = this.mc.fontRendererObj;
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = scaledresolution.getScaledWidth();
            int short1 = 182;
            int j = i / 2 - short1 / 2;
            int k = (int)(BossStatus.healthScale * (float)(short1 + 1));
            int b0 = 12;
            this.drawTexturedModalRect(j, b0, 0, 74, short1, 5);
            this.drawTexturedModalRect(j, b0, 0, 74, short1, 5);
            if (k > 0) {
                this.drawTexturedModalRect(j, b0, 0, 79, k, 5);
            }
            String s = BossStatus.bossName;
            int l = 0xFFFFFF;
            if (Config.isCustomColors()) {
                l = CustomColors.getBossTextColor((int)l);
            }
            this.getBasicFontRenderer().drawStringWithShadow(s, i / 2 - this.getBasicFontRenderer().getStringWidth(s) / 2, b0 - 10, l);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(icons);
        }
    }

    private void renderPumpkinOverlay(ScaledResolution p_180476_1_) {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableAlpha();
        this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(0.0, p_180476_1_.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        worldrenderer.pos(p_180476_1_.getScaledWidth(), p_180476_1_.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        worldrenderer.pos(p_180476_1_.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        worldrenderer.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderVignette(float p_180480_1_, ScaledResolution p_180480_2_) {
        if (!Config.isVignetteEnabled()) {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        } else {
            p_180480_1_ = 1.0f - p_180480_1_;
            p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0f, 1.0f);
            WorldBorder worldborder = this.mc.theWorld.getWorldBorder();
            float f = (float)worldborder.getClosestDistance(this.mc.thePlayer);
            double d0 = Math.min(worldborder.getResizeSpeed() * (double)worldborder.getWarningTime() * 1000.0, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
            double d1 = Math.max((double)worldborder.getWarningDistance(), d0);
            f = (double)f < d1 ? 1.0f - (float)((double)f / d1) : 0.0f;
            this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(p_180480_1_ - this.prevVignetteBrightness) * 0.01);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
            if (f > 0.0f) {
                GlStateManager.color(0.0f, f, f, 1.0f);
            } else {
                GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(vignetteTexPath);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(0.0, p_180480_2_.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
            worldrenderer.pos(p_180480_2_.getScaledWidth(), p_180480_2_.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
            worldrenderer.pos(p_180480_2_.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
            worldrenderer.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
    }

    private void func_180474_b(float p_180474_1_, ScaledResolution p_180474_2_) {
        if (p_180474_1_ < 1.0f) {
            p_180474_1_ *= p_180474_1_;
            p_180474_1_ *= p_180474_1_;
            p_180474_1_ = p_180474_1_ * 0.8f + 0.2f;
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, p_180474_1_);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMinV();
        float f2 = textureatlassprite.getMaxU();
        float f3 = textureatlassprite.getMaxV();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(0.0, p_180474_2_.getScaledHeight(), -90.0).tex(f, f3).endVertex();
        worldrenderer.pos(p_180474_2_.getScaledWidth(), p_180474_2_.getScaledHeight(), -90.0).tex(f2, f3).endVertex();
        worldrenderer.pos(p_180474_2_.getScaledWidth(), 0.0, -90.0).tex(f2, f1).endVertex();
        worldrenderer.pos(0.0, 0.0, -90.0).tex(f, f1).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer p_175184_5_) {
        ItemStack itemstack = p_175184_5_.inventory.mainInventory[index];
        if (itemstack != null) {
            float f = (float)itemstack.animationsToGo - partialTicks;
            if (f > 0.0f) {
                GlStateManager.pushMatrix();
                float f1 = 1.0f + f / 5.0f;
                GlStateManager.translate(xPos + 8, yPos + 12, 0.0f);
                GlStateManager.scale(1.0f / f1, (f1 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0f);
            }
            this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
            if (f > 0.0f) {
                GlStateManager.popMatrix();
            }
            this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemstack, xPos, yPos);
        }
    }

    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        if (this.titlesTimer > 0) {
            --this.titlesTimer;
            if (this.titlesTimer <= 0) {
                this.field_175201_x = "";
                this.field_175200_y = "";
            }
        }
        ++this.updateCounter;
        this.streamIndicator.func_152439_a();
        if (this.mc.thePlayer != null) {
            ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
            if (itemstack == null) {
                this.remainingHighlightTicks = 0;
            } else if (this.highlightingItemStack != null && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            } else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = itemstack;
        }
    }

    public void setRecordPlayingMessage(String p_73833_1_) {
        this.setRecordPlaying(I18n.format("record.nowPlaying", p_73833_1_), true);
    }

    public void setRecordPlaying(String p_110326_1_, boolean p_110326_2_) {
        this.recordPlaying = p_110326_1_;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = p_110326_2_;
    }

    public void displayTitle(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_, int p_175178_5_) {
        if (p_175178_1_ == null && p_175178_2_ == null && p_175178_3_ < 0 && p_175178_4_ < 0 && p_175178_5_ < 0) {
            this.field_175201_x = "";
            this.field_175200_y = "";
            this.titlesTimer = 0;
        } else if (p_175178_1_ != null) {
            this.field_175201_x = p_175178_1_;
            this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
        } else if (p_175178_2_ != null) {
            this.field_175200_y = p_175178_2_;
        } else {
            if (p_175178_3_ >= 0) {
                this.titleFadeIn = p_175178_3_;
            }
            if (p_175178_4_ >= 0) {
                this.titleDisplayTime = p_175178_4_;
            }
            if (p_175178_5_ >= 0) {
                this.titleFadeOut = p_175178_5_;
            }
            if (this.titlesTimer > 0) {
                this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
            }
        }
    }

    public void setRecordPlaying(IChatComponent p_175188_1_, boolean p_175188_2_) {
        this.setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
    }

    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter() {
        return this.updateCounter;
    }

    public BasicFontRenderer getBasicFontRenderer() {
        return this.mc.fontRendererObj;
    }

    public GuiSpectator getSpectatorGui() {
        return this.spectatorGui;
    }

    public GuiPlayerTabOverlay getTabList() {
        return this.overlayPlayerList;
    }

    public void func_181029_i() {
        this.overlayPlayerList.func_181030_a();
    }

    public static void drawImage(int x, int y, int width, int height, ResourceLocation resourceLocation) {
        mc1.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static int getStartReady() {
        int i = 0;
        while (i < 8) {
            ++i;
        }
        return 0;
    }

    public static String insertNulls(int val, int min_digits) {
        String s = String.valueOf(val);
        while (s.length() < min_digits) {
            s = "0" + s;
        }
        return s;
    }

    public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        Gui.drawRect2(left - (!borderIncludedInBounds ? borderWidth : 0.0), top - (!borderIncludedInBounds ? borderWidth : 0.0), right + (!borderIncludedInBounds ? borderWidth : 0.0), bottom + (!borderIncludedInBounds ? borderWidth : 0.0), borderColor);
        Gui.drawRect2(left + (borderIncludedInBounds ? borderWidth : 0.0), top + (borderIncludedInBounds ? borderWidth : 0.0), right - (borderIncludedInBounds ? borderWidth : 0.0), bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

