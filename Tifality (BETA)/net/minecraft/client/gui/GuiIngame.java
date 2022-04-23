/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import club.tifality.Tifality;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.manager.event.impl.render.RenderBossHealthEvent;
import club.tifality.manager.event.impl.render.RenderCrosshairEvent;
import club.tifality.manager.event.impl.render.RenderScoreboardEvent;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Pattern;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.MinecraftFontRenderer;
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
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import net.optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public class GuiIngame
extends Gui {
    private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
    private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
    private final Random rand = new Random();
    private final Minecraft mc;
    private final RenderItem itemRenderer;
    public static String modeText = "";
    private final GuiNewChat persistantChatGUI;
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
    private int field_175195_w;
    private String field_175201_x = "";
    private String field_175200_y = "";
    private int field_175199_z;
    private int field_175192_A;
    private int field_175193_B;
    private int playerHealth = 0;
    private int lastPlayerHealth = 0;
    private static String lastScoreboardHeader;
    private static final Pattern domainPattern;
    private long lastSystemTime = 0L;
    private long healthUpdateCounter = 0L;

    public GuiIngame(Minecraft mcIn) {
        this.mc = mcIn;
        this.itemRenderer = mcIn.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mcIn);
        this.spectatorGui = new GuiSpectator(mcIn);
        this.persistantChatGUI = new GuiNewChat(mcIn);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
        this.func_175177_a();
    }

    public void func_175177_a() {
        this.field_175199_z = 10;
        this.field_175192_A = 70;
        this.field_175193_B = 20;
    }

    public void renderGameOverlay(float partialTicks) {
        ScoreObjective scoreobjective1;
        int i1;
        float f;
        boolean useNormal;
        ScaledResolution scaledresolution = RenderingUtils.getScaledResolution();
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        LockedResolution lockedResolution = RenderingUtils.getLockedResolution();
        boolean bl = useNormal = lockedResolution.getWidth() == i && lockedResolution.getHeight() == j;
        if (useNormal) {
            this.setupScaledResolution(scaledresolution);
        } else {
            this.setupLockedResolution(lockedResolution);
        }
        if (!useNormal) {
            this.setupScaledResolution(scaledresolution);
        }
        GlStateManager.enableBlend();
        if (Config.isVignetteEnabled()) {
            this.renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
        } else {
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
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(icons);
        GlStateManager.enableBlend();
        RenderCrosshairEvent crosshairEvent = new RenderCrosshairEvent();
        Tifality.getInstance().getEventBus().post(crosshairEvent);
        if (!crosshairEvent.isCancelled() && this.showCrosshair()) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            Gui.drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
        }
        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderBossHealthEvent renderBossHealthEvent = new RenderBossHealthEvent();
        Tifality.getInstance().getEventBus().post(renderBossHealthEvent);
        if (!renderBossHealthEvent.isCancelled()) {
            this.renderBossHealth();
        }
        if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(scaledresolution);
        }
        GlStateManager.disableBlend();
        Tifality.getInstance().getEventBus().post(new Render2DEvent(lockedResolution, partialTicks));
        if (this.mc.thePlayer.getSleepTimer() > 0) {
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int j1 = this.mc.thePlayer.getSleepTimer();
            float f1 = (float)j1 / 100.0f;
            if (f1 > 1.0f) {
                f1 = 1.0f - (float)(j1 - 100) / 10.0f;
            }
            int k = (int)(220.0f * f1) << 24 | 0x101020;
            GuiIngame.drawRect(0.0f, 0.0f, i, j, k);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int k1 = i / 2 - 91;
        if (this.mc.thePlayer.isRidingHorse()) {
            this.renderHorseJumpBar(scaledresolution, k1);
        } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.renderExpBar(scaledresolution, k1);
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
        DevNotifications.getManager().updateAndRender();
        if (this.recordPlayingUpFor > 0) {
            float f2 = (float)this.recordPlayingUpFor - partialTicks;
            int l1 = (int)(f2 * 255.0f / 20.0f);
            if (l1 > 255) {
                l1 = 255;
            }
            if (l1 > 8) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)i / 2.0f, j - 68, 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                int l = 0xFFFFFF;
                if (this.recordIsPlaying) {
                    l = MathHelper.func_181758_c(f2 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                this.getFontRenderer().drawString(this.recordPlaying, -this.getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4.0f, l + (l1 << 24 & 0xFF000000));
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
            }
        }
        if (this.field_175195_w > 0) {
            float f3 = (float)this.field_175195_w - partialTicks;
            int i2 = 255;
            if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
                float f4 = (float)(this.field_175199_z + this.field_175192_A + this.field_175193_B) - f3;
                i2 = (int)(f4 * 255.0f / (float)this.field_175199_z);
            }
            if (this.field_175195_w <= this.field_175193_B) {
                i2 = (int)(f3 * 255.0f / (float)this.field_175193_B);
            }
            if ((i2 = MathHelper.clamp_int(i2, 0, 255)) > 8) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)i / 2.0f, (float)j / 2.0f, 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GL11.glPushMatrix();
                GL11.glScalef(4.0f, 4.0f, 4.0f);
                int j2 = i2 << 24 & 0xFF000000;
                this.getFontRenderer().drawString(this.field_175201_x, (float)(-this.getFontRenderer().getStringWidth(this.field_175201_x)) / 2.0f, -10.0f, 0xFFFFFF | j2, true);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(2.0f, 2.0f, 2.0f);
                this.getFontRenderer().drawString(this.field_175200_y, (float)(-this.getFontRenderer().getStringWidth(this.field_175200_y)) / 2.0f, 5.0f, 0xFFFFFF | j2, true);
                GL11.glPopMatrix();
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
            }
        }
        Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getCommandSenderName());
        if (scoreplayerteam != null && (i1 = scoreplayerteam.getChatFormat().getColorIndex()) >= 0) {
            scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
        }
        ScoreObjective scoreObjective = scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreobjective1 != null) {
            RenderScoreboardEvent event = new RenderScoreboardEvent();
            Tifality.getInstance().getEventBus().post(event);
            if (!event.isCancelled()) {
                this.renderScoreboard(scoreobjective1, scaledresolution);
            }
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, j - 48, 0.0f);
        this.persistantChatGUI.drawChat(this.updateCounter);
        GL11.glPopMatrix();
        scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
        if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || scoreobjective1 != null)) {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
        } else {
            this.overlayPlayerList.updatePlayerList(false);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }

    protected void renderTooltip(ScaledResolution sr, float partialTicks) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(widgetsTexPath);
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            int i = sr.getScaledWidth() / 2;
            float f = this.zLevel;
            this.zLevel = -90.0f;
            GuiIngame.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
            GuiIngame.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
            this.zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            int l = sr.getScaledHeight() - 16 - 3;
            int k = sr.getScaledWidth() / 2 - 90;
            for (int j = 0; j < 9; ++j) {
                this.renderHotbarItem(j, k + j * 20 + 2, l, partialTicks, entityplayer);
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    private void setupScaledResolution(ScaledResolution sr) {
        this.mc.entityRenderer.setupOverlayRendering(sr, null, false);
    }

    private void setupLockedResolution(LockedResolution lr) {
        this.mc.entityRenderer.setupOverlayRendering(null, lr, true);
    }

    public void renderHorseJumpBar(ScaledResolution p_175186_1_, int p_175186_2_) {
        this.mc.getTextureManager().bindTexture(Gui.icons);
        float f = this.mc.thePlayer.getHorseJumpPower();
        int i = 182;
        int j = (int)(f * (float)(i + 1));
        int k = p_175186_1_.getScaledHeight() - 32 + 3;
        GuiIngame.drawTexturedModalRect(p_175186_2_, k, 0, 84, i, 5);
        if (j > 0) {
            GuiIngame.drawTexturedModalRect(p_175186_2_, k, 0, 89, j, 5);
        }
    }

    public void renderExpBar(ScaledResolution p_175176_1_, int p_175176_2_) {
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int i = this.mc.thePlayer.xpBarCap();
        if (i > 0) {
            int j = 182;
            int k = (int)(this.mc.thePlayer.experience * (float)(j + 1));
            int l = p_175176_1_.getScaledHeight() - 32 + 3;
            GuiIngame.drawTexturedModalRect(p_175176_2_, l, 0, 64, j, 5);
            if (k > 0) {
                GuiIngame.drawTexturedModalRect(p_175176_2_, l, 0, 69, k, 5);
            }
        }
        if (this.mc.thePlayer.experienceLevel > 0) {
            int k1 = 8453920;
            if (Config.isCustomColors()) {
                k1 = CustomColors.getExpBarTextColor(k1);
            }
            String s = "" + this.mc.thePlayer.experienceLevel;
            int l1 = (p_175176_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int i1 = p_175176_1_.getScaledHeight() - 31 - 4;
            boolean j1 = false;
            this.getFontRenderer().drawString(s, l1 + 1, i1, 0);
            this.getFontRenderer().drawString(s, l1 - 1, i1, 0);
            this.getFontRenderer().drawString(s, l1, i1 + 1, 0);
            this.getFontRenderer().drawString(s, l1, i1 - 1, 0);
            this.getFontRenderer().drawString(s, l1, i1, k1);
        }
    }

    public void func_181551_a(ScaledResolution p_181551_1_) {
        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
            int k;
            String s = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                s = (Object)((Object)EnumChatFormatting.ITALIC) + s;
            }
            int i = (p_181551_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int j = p_181551_1_.getScaledHeight() - 59;
            if (!this.mc.playerController.shouldDrawHUD()) {
                j += 14;
            }
            if ((k = (int)((float)this.remainingHighlightTicks * 256.0f / 10.0f)) > 255) {
                k = 255;
            }
            if (k > 0) {
                GL11.glPushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.getFontRenderer().drawStringWithShadow(s, i, j, 0xFFFFFF + (k << 24));
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
            }
        }
    }

    public void renderDemo(ScaledResolution p_175185_1_) {
        String s = "";
        s = this.mc.theWorld.getTotalWorldTime() >= 120500L ? I18n.format("demo.demoExpired", new Object[0]) : I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())));
        int i = this.getFontRenderer().getStringWidth(s);
        this.getFontRenderer().drawStringWithShadow(s, p_175185_1_.getScaledWidth() - i - 10, 5.0f, 0xFFFFFF);
    }

    protected boolean showCrosshair() {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            return false;
        }
        if (this.mc.playerController.isSpectator()) {
            if (this.mc.pointedEntity != null) {
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

    protected void renderScoreboard(ScoreObjective p_renderScoreboard_1_, ScaledResolution p_renderScoreboard_2_) {
        Scoreboard lvt_3_1_ = p_renderScoreboard_1_.getScoreboard();
        Collection<Score> lvt_4_1_ = lvt_3_1_.getSortedScores(p_renderScoreboard_1_);
        ArrayList<Score> lvt_5_1_ = Lists.newArrayList(Iterables.filter(lvt_4_1_, p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")));
        lvt_4_1_ = lvt_5_1_.size() > 15 ? Lists.newArrayList(Iterables.skip(lvt_5_1_, lvt_4_1_.size() - 15)) : lvt_5_1_;
        int lvt_6_1_ = this.getFontRenderer().getStringWidth(p_renderScoreboard_1_.getDisplayName());
        for (Score lvt_8_1_ : lvt_4_1_) {
            ScorePlayerTeam lvt_9_1_ = lvt_3_1_.getPlayersTeam(lvt_8_1_.getPlayerName());
            String lvt_10_1_ = ScorePlayerTeam.formatPlayerName(lvt_9_1_, lvt_8_1_.getPlayerName()) + ": " + (Object)((Object)EnumChatFormatting.RED) + lvt_8_1_.getScorePoints();
            lvt_6_1_ = Math.max(lvt_6_1_, this.getFontRenderer().getStringWidth(lvt_10_1_));
        }
        int lvt_7_2_ = lvt_4_1_.size() * this.getFontRenderer().FONT_HEIGHT;
        int lvt_8_2_ = p_renderScoreboard_2_.getScaledHeight() / 2 + lvt_7_2_ / 3 + ((Double)Hud.scoreBoardHeight.get()).intValue();
        int lvt_9_2_ = 3;
        int lvt_10_2_ = p_renderScoreboard_2_.getScaledWidth() - lvt_6_1_ - lvt_9_2_ - 2;
        int lvt_11_1_ = 0;
        for (Score lvt_13_1_ : lvt_4_1_) {
            ScorePlayerTeam lvt_14_1_ = lvt_3_1_.getPlayersTeam(lvt_13_1_.getPlayerName());
            String lvt_15_1_ = ScorePlayerTeam.formatPlayerName(lvt_14_1_, lvt_13_1_.getPlayerName());
            String lvt_16_1_ = (Object)((Object)EnumChatFormatting.RED) + "" + lvt_13_1_.getScorePoints();
            int lvt_18_1_ = lvt_8_2_ - ++lvt_11_1_ * this.getFontRenderer().FONT_HEIGHT;
            int lvt_19_1_ = p_renderScoreboard_2_.getScaledWidth() - lvt_9_2_ + 2;
            GuiIngame.drawRect(lvt_10_2_ - 2, lvt_18_1_, lvt_19_1_, lvt_18_1_ + this.getFontRenderer().FONT_HEIGHT, 0x50000000);
            Hud hud = Tifality.INSTANCE.getModuleManager().getModule(Hud.class);
            boolean skid = hud.isEnabled() && domainPattern.matcher(lvt_15_1_).find();
            GlStateManager.resetColor();
            if (skid) {
                RenderingUtils.drawOutlinedString("www.tifality.club", lvt_10_2_, lvt_18_1_, hud.arrayListColorModeProperty.get() == Hud.ArrayListColorMode.RAINBOW ? RenderingUtils.getRainbow(((Double)hud.rainbowSpeed.getValue()).intValue(), ((Double)hud.rainbowWidth.getValue()).intValue(), (int)(System.currentTimeMillis() / 15L)) : Hud.hudColor.get(), new Color(0, 0, 0).getRGB());
            } else {
                this.getFontRenderer().drawString(lvt_15_1_, lvt_10_2_, lvt_18_1_, 0x20FFFFFF);
            }
            this.getFontRenderer().drawString(lvt_16_1_, lvt_19_1_ - this.getFontRenderer().getStringWidth(lvt_16_1_), lvt_18_1_, 0x20FFFFFF);
            if (lvt_11_1_ != lvt_4_1_.size()) continue;
            String lvt_20_1_ = p_renderScoreboard_1_.getDisplayName();
            GuiIngame.drawRect(lvt_10_2_ - 2, lvt_18_1_ - this.getFontRenderer().FONT_HEIGHT - 1, lvt_19_1_, lvt_18_1_ - 1, 0x60000000);
            GuiIngame.drawRect(lvt_10_2_ - 2, lvt_18_1_ - 1, lvt_19_1_, lvt_18_1_, 0x50000000);
            this.getFontRenderer().drawString(lvt_20_1_, (float)lvt_10_2_ + (float)lvt_6_1_ / 2.0f - (float)this.getFontRenderer().getStringWidth(lvt_20_1_) / 2.0f, lvt_18_1_ - this.getFontRenderer().FONT_HEIGHT, 0x20FFFFFF);
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
            for (int i3 = 0; i3 < 10; ++i3) {
                if (k2 <= 0) continue;
                int j3 = i1 + i3 * 8;
                if (i3 * 2 + 1 < k2) {
                    GuiIngame.drawTexturedModalRect(j3, j2, 34, 9, 9, 9);
                }
                if (i3 * 2 + 1 == k2) {
                    GuiIngame.drawTexturedModalRect(j3, j2, 25, 9, 9, 9);
                }
                if (i3 * 2 + 1 <= k2) continue;
                GuiIngame.drawTexturedModalRect(j3, j2, 16, 9, 9, 9);
            }
            for (int i6 = MathHelper.ceiling_float_int((f + f1) / 2.0f) - 1; i6 >= 0; --i6) {
                int j6 = 16;
                if (entityplayer.isPotionActive(Potion.poison)) {
                    j6 += 36;
                } else if (entityplayer.isPotionActive(Potion.wither)) {
                    j6 += 72;
                }
                int k3 = 0;
                if (flag) {
                    k3 = 1;
                }
                int l3 = MathHelper.ceiling_float_int((float)(i6 + 1) / 10.0f) - 1;
                int i4 = i1 + i6 % 10 * 8;
                int j4 = k1 - l3 * i2;
                if (i <= 4) {
                    j4 += this.rand.nextInt(2);
                }
                if (i6 == l2) {
                    j4 -= 2;
                }
                int k4 = 0;
                if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
                    k4 = 5;
                }
                GuiIngame.drawTexturedModalRect(i4, j4, 16 + k3 * 9, 9 * k4, 9, 9);
                if (flag) {
                    if (i6 * 2 + 1 < j) {
                        GuiIngame.drawTexturedModalRect(i4, j4, j6 + 54, 9 * k4, 9, 9);
                    }
                    if (i6 * 2 + 1 == j) {
                        GuiIngame.drawTexturedModalRect(i4, j4, j6 + 63, 9 * k4, 9, 9);
                    }
                }
                if (f2 <= 0.0f) {
                    if (i6 * 2 + 1 < i) {
                        GuiIngame.drawTexturedModalRect(i4, j4, j6 + 36, 9 * k4, 9, 9);
                    }
                    if (i6 * 2 + 1 != i) continue;
                    GuiIngame.drawTexturedModalRect(i4, j4, j6 + 45, 9 * k4, 9, 9);
                    continue;
                }
                if (f2 == f1 && f1 % 2.0f == 1.0f) {
                    GuiIngame.drawTexturedModalRect(i4, j4, j6 + 153, 9 * k4, 9, 9);
                } else {
                    GuiIngame.drawTexturedModalRect(i4, j4, j6 + 144, 9 * k4, 9, 9);
                }
                f2 -= 2.0f;
            }
            Entity entity = entityplayer.ridingEntity;
            if (entity == null) {
                for (int k6 = 0; k6 < 10; ++k6) {
                    int j7 = k1;
                    int l7 = 16;
                    int k8 = 0;
                    if (entityplayer.isPotionActive(Potion.hunger)) {
                        l7 += 36;
                        k8 = 13;
                    }
                    if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0f && this.updateCounter % (k * 3 + 1) == 0) {
                        j7 = k1 + (this.rand.nextInt(3) - 1);
                    }
                    int j9 = j1 - k6 * 8 - 9;
                    GuiIngame.drawTexturedModalRect(j9, j7, 16 + k8 * 9, 27, 9, 9);
                    if (k6 * 2 + 1 < k) {
                        GuiIngame.drawTexturedModalRect(j9, j7, l7 + 36, 27, 9, 9);
                    }
                    if (k6 * 2 + 1 != k) continue;
                    GuiIngame.drawTexturedModalRect(j9, j7, l7 + 45, 27, 9, 9);
                }
            } else if (entity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                int i7 = (int)Math.ceil(entitylivingbase.getHealth());
                float f3 = entitylivingbase.getMaxHealth();
                int j8 = (int)(f3 + 0.5f) / 2;
                if (j8 > 30) {
                    j8 = 30;
                }
                int i9 = k1;
                int k9 = 0;
                while (j8 > 0) {
                    int l4 = Math.min(j8, 10);
                    j8 -= l4;
                    for (int i5 = 0; i5 < l4; ++i5) {
                        int j5 = 52;
                        int k5 = 0;
                        int l5 = j1 - i5 * 8 - 9;
                        GuiIngame.drawTexturedModalRect(l5, i9, j5 + k5 * 9, 9, 9, 9);
                        if (i5 * 2 + 1 + k9 < i7) {
                            GuiIngame.drawTexturedModalRect(l5, i9, j5 + 36, 9, 9, 9);
                        }
                        if (i5 * 2 + 1 + k9 != i7) continue;
                        GuiIngame.drawTexturedModalRect(l5, i9, j5 + 45, 9, 9, 9);
                    }
                    i9 -= 10;
                    k9 += 20;
                }
            }
            if (entityplayer.isInsideOfMaterial(Material.water)) {
                int l6 = this.mc.thePlayer.getAir();
                int k7 = MathHelper.ceiling_double_int((double)(l6 - 2) * 10.0 / 300.0);
                int i8 = MathHelper.ceiling_double_int((double)l6 * 10.0 / 300.0) - k7;
                for (int l8 = 0; l8 < k7 + i8; ++l8) {
                    if (l8 < k7) {
                        GuiIngame.drawTexturedModalRect(j1 - l8 * 8 - 9, j2, 16, 18, 9, 9);
                        continue;
                    }
                    GuiIngame.drawTexturedModalRect(j1 - l8 * 8 - 9, j2, 25, 18, 9, 9);
                }
            }
        }
    }

    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            ScaledResolution scaledresolution = RenderingUtils.getScaledResolution();
            int i = scaledresolution.getScaledWidth();
            int j = 182;
            int k = i / 2 - j / 2;
            int l = (int)(BossStatus.healthScale * (float)(j + 1));
            int i1 = 12;
            GuiIngame.drawTexturedModalRect(k, i1, 0, 74, j, 5);
            GuiIngame.drawTexturedModalRect(k, i1, 0, 74, j, 5);
            if (l > 0) {
                GuiIngame.drawTexturedModalRect(k, i1, 0, 79, l, 5);
            }
            String s = BossStatus.bossName;
            this.getFontRenderer().drawStringWithShadow(s, i / 2 - this.getFontRenderer().getStringWidth(s) / 2, i1 - 10, 0xFFFFFF);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(icons);
        }
    }

    private void renderPumpkinOverlay(ScaledResolution p_180476_1_) {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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
                GL11.glColor4f(0.0f, f, f, 1.0f);
            } else {
                GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
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
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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
        GL11.glColor4f(1.0f, 1.0f, 1.0f, p_180474_1_);
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
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer p_175184_5_) {
        ItemStack itemstack = p_175184_5_.inventory.mainInventory[index];
        if (itemstack != null) {
            boolean complete;
            float f = (float)itemstack.animationsToGo - partialTicks;
            boolean bl = complete = f > 0.0f;
            if (complete) {
                GL11.glPushMatrix();
                float f1 = 1.0f + f / 5.0f;
                GL11.glTranslatef(xPos + 8, yPos + 12, 0.0f);
                GL11.glScalef(1.0f / f1, (f1 + 1.0f) / 2.0f, 1.0f);
                GL11.glTranslatef(-(xPos + 8), -(yPos + 12), 0.0f);
            }
            this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
            if (complete) {
                GL11.glPopMatrix();
            }
            this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemstack, xPos, yPos);
        }
    }

    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        if (this.field_175195_w > 0) {
            --this.field_175195_w;
            if (this.field_175195_w <= 0) {
                this.field_175201_x = "";
                this.field_175200_y = "";
            }
        }
        ++this.updateCounter;
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
            this.field_175195_w = 0;
        } else if (p_175178_1_ != null) {
            this.field_175201_x = p_175178_1_;
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
        } else if (p_175178_2_ != null) {
            this.field_175200_y = p_175178_2_;
        } else {
            if (p_175178_3_ >= 0) {
                this.field_175199_z = p_175178_3_;
            }
            if (p_175178_4_ >= 0) {
                this.field_175192_A = p_175178_4_;
            }
            if (p_175178_5_ >= 0) {
                this.field_175193_B = p_175178_5_;
            }
            if (this.field_175195_w > 0) {
                this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
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

    public MinecraftFontRenderer getFontRenderer() {
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

    static {
        domainPattern = Pattern.compile("(([a-zA-Z])|([a-zA-Z][a-zA-Z])|([a-zA-Z][0-9])|([0-9][a-zA-Z])|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})");
    }
}

