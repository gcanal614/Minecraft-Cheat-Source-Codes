/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.achievement;

import java.io.IOException;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAchievements
extends GuiScreen
implements IProgressMeter {
    private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
    private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
    private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
    private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
    private static final ResourceLocation ACHIEVEMENT_BACKGROUND = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    protected GuiScreen parentScreen;
    protected int field_146555_f = 256;
    protected int field_146557_g = 202;
    protected int field_146563_h;
    protected int field_146564_i;
    protected float field_146570_r = 1.0f;
    protected double field_146569_s;
    protected double field_146568_t;
    protected double field_146567_u;
    protected double field_146566_v;
    protected double field_146565_w;
    protected double field_146573_x;
    private int field_146554_D;
    private StatFileWriter statFileWriter;
    private boolean loadingAchievements = true;

    public GuiAchievements(GuiScreen parentScreenIn, StatFileWriter statFileWriterIn) {
        double field_146568_t;
        double field_146569_s;
        this.parentScreen = parentScreenIn;
        this.statFileWriter = statFileWriterIn;
        int i = 141;
        int j = 141;
        this.field_146565_w = field_146569_s = (double)(AchievementList.openInventory.displayColumn * 24 - 70 - 12);
        this.field_146567_u = field_146569_s;
        this.field_146569_s = field_146569_s;
        this.field_146573_x = field_146568_t = (double)(AchievementList.openInventory.displayRow * 24 - 70);
        this.field_146566_v = field_146568_t;
        this.field_146568_t = field_146568_t;
    }

    @Override
    public void initGui() {
        this.mc.getNetHandler().sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (!this.loadingAchievements && button.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.loadingAchievements) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 0xFFFFFF);
            this.drawCenteredString(this.fontRendererObj, lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % (long)lanSearchStates.length)], this.width / 2, this.height / 2 + 18, 0xFFFFFF);
        } else {
            if (Mouse.isButtonDown(0)) {
                int i = (this.width - this.field_146555_f) / 2;
                int j = (this.height - this.field_146557_g) / 2;
                int k = i + 8;
                int l = j + 17;
                if ((this.field_146554_D == 0 || this.field_146554_D == 1) && mouseX >= k && mouseX < k + 224 && mouseY >= l && mouseY < l + 155) {
                    if (this.field_146554_D == 0) {
                        this.field_146554_D = 1;
                    } else {
                        double field_146566_v;
                        double field_146567_u;
                        this.field_146567_u -= (double)((float)(mouseX - this.field_146563_h) * this.field_146570_r);
                        this.field_146566_v -= (double)((float)(mouseY - this.field_146564_i) * this.field_146570_r);
                        this.field_146569_s = field_146567_u = this.field_146567_u;
                        this.field_146565_w = field_146567_u;
                        this.field_146568_t = field_146566_v = this.field_146566_v;
                        this.field_146573_x = field_146566_v;
                    }
                    this.field_146563_h = mouseX;
                    this.field_146564_i = mouseY;
                }
            } else {
                this.field_146554_D = 0;
            }
            int i2 = Mouse.getDWheel();
            float f3 = this.field_146570_r;
            if (i2 < 0) {
                this.field_146570_r += 0.25f;
            } else if (i2 > 0) {
                this.field_146570_r -= 0.25f;
            }
            this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0f, 2.0f);
            if (this.field_146570_r != f3) {
                double field_146566_v2;
                double field_146567_u2;
                float f4 = f3 - this.field_146570_r;
                float f5 = f3 * (float)this.field_146555_f;
                float f6 = f3 * (float)this.field_146557_g;
                float f7 = this.field_146570_r * (float)this.field_146555_f;
                float f8 = this.field_146570_r * (float)this.field_146557_g;
                this.field_146567_u -= (double)((f7 - f5) * 0.5f);
                this.field_146566_v -= (double)((f8 - f6) * 0.5f);
                this.field_146569_s = field_146567_u2 = this.field_146567_u;
                this.field_146565_w = field_146567_u2;
                this.field_146568_t = field_146566_v2 = this.field_146566_v;
                this.field_146573_x = field_146566_v2;
            }
            if (this.field_146565_w < (double)field_146572_y) {
                this.field_146565_w = field_146572_y;
            }
            if (this.field_146573_x < (double)field_146571_z) {
                this.field_146573_x = field_146571_z;
            }
            if (this.field_146565_w >= (double)field_146559_A) {
                this.field_146565_w = field_146559_A - 1;
            }
            if (this.field_146573_x >= (double)field_146560_B) {
                this.field_146573_x = field_146560_B - 1;
            }
            this.drawDefaultBackground();
            this.drawAchievementScreen(mouseX, mouseY, partialTicks);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            this.drawTitle();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }

    @Override
    public void doneLoading() {
        if (this.loadingAchievements) {
            this.loadingAchievements = false;
        }
    }

    @Override
    public void updateScreen() {
        if (!this.loadingAchievements) {
            this.field_146569_s = this.field_146567_u;
            this.field_146568_t = this.field_146566_v;
            double d0 = this.field_146565_w - this.field_146567_u;
            double d2 = this.field_146573_x - this.field_146566_v;
            if (d0 * d0 + d2 * d2 < 4.0) {
                this.field_146567_u += d0;
                this.field_146566_v += d2;
            } else {
                this.field_146567_u += d0 * 0.85;
                this.field_146566_v += d2 * 0.85;
            }
        }
    }

    protected void drawTitle() {
        int i = (this.width - this.field_146555_f) / 2;
        int j = (this.height - this.field_146557_g) / 2;
        this.fontRendererObj.drawString(I18n.format("gui.achievements", new Object[0]), i + 15, j + 5, 0x404040);
    }

    protected void drawAchievementScreen(int p_146552_1_, int p_146552_2_, float p_146552_3_) {
        int i = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * (double)p_146552_3_);
        int j = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * (double)p_146552_3_);
        if (i < field_146572_y) {
            i = field_146572_y;
        }
        if (j < field_146571_z) {
            j = field_146571_z;
        }
        if (i >= field_146559_A) {
            i = field_146559_A - 1;
        }
        if (j >= field_146560_B) {
            j = field_146560_B - 1;
        }
        int k = (this.width - this.field_146555_f) / 2;
        int l = (this.height - this.field_146557_g) / 2;
        int i2 = k + 16;
        int j2 = l + 17;
        this.zLevel = 0.0f;
        GlStateManager.depthFunc(518);
        GL11.glPushMatrix();
        GL11.glTranslatef(i2, j2, -200.0f);
        GL11.glScalef(1.0f / this.field_146570_r, 1.0f / this.field_146570_r, 0.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        int k2 = i + 288 >> 4;
        int l2 = j + 288 >> 4;
        int i3 = (i + 288) % 16;
        int j3 = (j + 288) % 16;
        int k3 = 4;
        int l3 = 8;
        int i4 = 10;
        int j4 = 22;
        int k4 = 37;
        Random random = new Random();
        float f = 16.0f / this.field_146570_r;
        float f2 = 16.0f / this.field_146570_r;
        int l4 = 0;
        while ((float)l4 * f - (float)j3 < 155.0f) {
            float f3 = 0.6f - (float)(l2 + l4) / 25.0f * 0.3f;
            GL11.glColor4f(f3, f3, f3, 1.0f);
            int i5 = 0;
            while ((float)i5 * f2 - (float)i3 < 224.0f) {
                random.setSeed(this.mc.getSession().getPlayerID().hashCode() + k2 + i5 + (l2 + l4) * 16);
                int j5 = random.nextInt(1 + l2 + l4) + (l2 + l4) / 2;
                TextureAtlasSprite textureatlassprite = this.func_175371_a(Blocks.sand);
                if (j5 <= 37 && l2 + l4 != 35) {
                    if (j5 == 22) {
                        textureatlassprite = random.nextInt(2) == 0 ? this.func_175371_a(Blocks.diamond_ore) : this.func_175371_a(Blocks.redstone_ore);
                    } else if (j5 == 10) {
                        textureatlassprite = this.func_175371_a(Blocks.iron_ore);
                    } else if (j5 == 8) {
                        textureatlassprite = this.func_175371_a(Blocks.coal_ore);
                    } else if (j5 > 4) {
                        textureatlassprite = this.func_175371_a(Blocks.stone);
                    } else if (j5 > 0) {
                        textureatlassprite = this.func_175371_a(Blocks.dirt);
                    }
                } else {
                    Block block = Blocks.bedrock;
                    textureatlassprite = this.func_175371_a(block);
                }
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                Gui.drawTexturedModalRect(i5 * 16 - i3, l4 * 16 - j3, textureatlassprite, 16, 16);
                ++i5;
            }
            ++l4;
        }
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
        for (int j6 = 0; j6 < AchievementList.achievementList.size(); ++j6) {
            Achievement achievement1 = AchievementList.achievementList.get(j6);
            if (achievement1.parentAchievement == null) continue;
            int k5 = achievement1.displayColumn * 24 - i + 11;
            int l5 = achievement1.displayRow * 24 - j + 11;
            int j7 = achievement1.parentAchievement.displayColumn * 24 - i + 11;
            int k6 = achievement1.parentAchievement.displayRow * 24 - j + 11;
            boolean flag = this.statFileWriter.hasAchievementUnlocked(achievement1);
            boolean flag2 = this.statFileWriter.canUnlockAchievement(achievement1);
            int k7 = this.statFileWriter.func_150874_c(achievement1);
            if (k7 > 4) continue;
            int l6 = -16777216;
            if (flag) {
                l6 = -6250336;
            } else if (flag2) {
                l6 = -16711936;
            }
            this.drawHorizontalLine(k5, j7, l5, l6);
            this.drawVerticalLine(j7, l5, k6, l6);
            if (k5 > j7) {
                Gui.drawTexturedModalRect(k5 - 11 - 7, l5 - 5, 114, 234, 7, 11);
                continue;
            }
            if (k5 < j7) {
                Gui.drawTexturedModalRect(k5 + 11, l5 - 5, 107, 234, 7, 11);
                continue;
            }
            if (l5 > k6) {
                Gui.drawTexturedModalRect(k5 - 5, l5 - 11 - 7, 96, 234, 11, 7);
                continue;
            }
            if (l5 >= k6) continue;
            Gui.drawTexturedModalRect(k5 - 5, l5 + 11, 96, 241, 11, 7);
        }
        Achievement achievement2 = null;
        float f4 = (float)(p_146552_1_ - i2) * this.field_146570_r;
        float f5 = (float)(p_146552_2_ - j2) * this.field_146570_r;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        for (int i6 = 0; i6 < AchievementList.achievementList.size(); ++i6) {
            Achievement achievement3 = AchievementList.achievementList.get(i6);
            int l7 = achievement3.displayColumn * 24 - i;
            int j8 = achievement3.displayRow * 24 - j;
            if (l7 < -24 || j8 < -24 || !((float)l7 <= 224.0f * this.field_146570_r) || !((float)j8 <= 155.0f * this.field_146570_r)) continue;
            int l8 = this.statFileWriter.func_150874_c(achievement3);
            if (this.statFileWriter.hasAchievementUnlocked(achievement3)) {
                float f6 = 0.75f;
                GL11.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
            } else if (this.statFileWriter.canUnlockAchievement(achievement3)) {
                float f7 = 1.0f;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            } else if (l8 < 3) {
                float f8 = 0.3f;
                GL11.glColor4f(0.3f, 0.3f, 0.3f, 1.0f);
            } else if (l8 == 3) {
                float f9 = 0.2f;
                GL11.glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
            } else {
                if (l8 != 4) continue;
                float f10 = 0.1f;
                GL11.glColor4f(0.1f, 0.1f, 0.1f, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
            if (achievement3.getSpecial()) {
                Gui.drawTexturedModalRect(l7 - 2, j8 - 2, 26, 202, 26, 26);
            } else {
                Gui.drawTexturedModalRect(l7 - 2, j8 - 2, 0, 202, 26, 26);
            }
            if (!this.statFileWriter.canUnlockAchievement(achievement3)) {
                float f11 = 0.1f;
                GL11.glColor4f(0.1f, 0.1f, 0.1f, 1.0f);
                this.itemRender.func_175039_a(false);
            }
            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            this.itemRender.renderItemAndEffectIntoGUI(achievement3.theItemStack, l7 + 3, j8 + 3);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.disableLighting();
            if (!this.statFileWriter.canUnlockAchievement(achievement3)) {
                this.itemRender.func_175039_a(true);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (!(f4 >= (float)l7) || !(f4 <= (float)(l7 + 22)) || !(f5 >= (float)j8) || !(f5 <= (float)(j8 + 22))) continue;
            achievement2 = achievement3;
        }
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
        Gui.drawTexturedModalRect(k, l, 0, 0, this.field_146555_f, this.field_146557_g);
        this.zLevel = 0.0f;
        GlStateManager.depthFunc(515);
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);
        if (achievement2 != null) {
            String s = achievement2.getStatName().getUnformattedText();
            String s2 = achievement2.getDescription();
            int i7 = p_146552_1_ + 12;
            int k8 = p_146552_2_ - 4;
            int i8 = this.statFileWriter.func_150874_c(achievement2);
            if (this.statFileWriter.canUnlockAchievement(achievement2)) {
                int j9 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                int i9 = this.fontRendererObj.splitStringWidth(s2, j9);
                if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
                    i9 += 12;
                }
                Gui.drawGradientRect(i7 - 3, k8 - 3, i7 + j9 + 3, k8 + i9 + 3 + 12, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(s2, i7, k8 + 12, j9, -6250336);
                if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
                    this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken", new Object[0]), i7, k8 + i9 + 4, -7302913);
                }
            } else if (i8 == 3) {
                s = I18n.format("achievement.unknown", new Object[0]);
                int k9 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                String s3 = new ChatComponentTranslation("achievement.requires", achievement2.parentAchievement.getStatName()).getUnformattedText();
                int i10 = this.fontRendererObj.splitStringWidth(s3, k9);
                Gui.drawGradientRect(i7 - 3, k8 - 3, i7 + k9 + 3, k8 + i10 + 12 + 3, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(s3, i7, k8 + 12, k9, -9416624);
            } else if (i8 < 3) {
                int l9 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                String s4 = new ChatComponentTranslation("achievement.requires", achievement2.parentAchievement.getStatName()).getUnformattedText();
                int j10 = this.fontRendererObj.splitStringWidth(s4, l9);
                Gui.drawGradientRect(i7 - 3, k8 - 3, i7 + l9 + 3, k8 + j10 + 12 + 3, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(s4, i7, k8 + 12, l9, -9416624);
            } else {
                s = null;
            }
            if (s != null) {
                this.fontRendererObj.drawStringWithShadow(s, i7, k8, this.statFileWriter.canUnlockAchievement(achievement2) ? (achievement2.getSpecial() ? -128 : -1) : (achievement2.getSpecial() ? -8355776 : -8355712));
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
    }

    private TextureAtlasSprite func_175371_a(Block p_175371_1_) {
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(p_175371_1_.getDefaultState());
    }

    @Override
    public boolean doesGuiPauseGame() {
        return !this.loadingAchievements;
    }
}

