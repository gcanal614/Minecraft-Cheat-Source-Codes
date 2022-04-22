/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import de.fanta.Client;
import de.fanta.gui.Theme.GuiThemeManager;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;

public class GuiIngameMenu
extends GuiScreen {
    private int field_146445_a;
    private int field_146444_f;

    @Override
    public void initGui() {
        this.field_146445_a = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));
        if (!this.mc.isIntegratedServerRunning()) {
            ((GuiButton)this.buttonList.get((int)0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(222, width / 2 - 100, height / 4 + 72 + i, I18n.format("ThemeManager", new Object[0])));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
        GuiButton guibutton = new GuiButton(7, width / 2 + 2, height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0]));
        this.buttonList.add(guibutton);
        this.buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 1: {
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.func_181540_al();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                if (flag) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                if (flag1) {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                    break;
                }
                this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
            }
            default: {
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
            }
        }
        if (button.id == 222) {
            this.mc.displayGuiScreen(new GuiThemeManager());
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146444_f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), width / 2, 40, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (Client.INSTANCE.moduleManager.getModule("GuiBlur").isState() && OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }
        super.onGuiClosed();
    }
}

