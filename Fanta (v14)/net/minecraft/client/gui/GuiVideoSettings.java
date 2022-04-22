/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.GuiAnimationSettingsOF
 *  optifine.GuiDetailSettingsOF
 *  optifine.GuiOptionButtonOF
 *  optifine.GuiOptionSliderOF
 *  optifine.GuiOtherSettingsOF
 *  optifine.GuiPerformanceSettingsOF
 *  optifine.GuiQualitySettingsOF
 *  optifine.Lang
 *  optifine.TooltipManager
 *  shadersmod.client.GuiShaders
 */
package net.minecraft.client.gui;

import de.fanta.Client;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import optifine.Config;
import optifine.GuiAnimationSettingsOF;
import optifine.GuiDetailSettingsOF;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.GuiOtherSettingsOF;
import optifine.GuiPerformanceSettingsOF;
import optifine.GuiQualitySettingsOF;
import optifine.Lang;
import optifine.TooltipManager;
import shadersmod.client.GuiShaders;

public class GuiVideoSettings
extends GuiScreen {
    private GuiScreen parentGuiScreen;
    protected String screenTitle = "Video Settings";
    private GameSettings guiGameSettings;
    private static GameSettings.Options[] videoOptions = new GameSettings.Options[]{GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START};
    private static final String __OBFID = "CL_00000718";
    private TooltipManager tooltipManager = new TooltipManager((GuiScreen)this);

    public GuiVideoSettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
        this.parentGuiScreen = parentScreenIn;
        this.guiGameSettings = gameSettingsIn;
    }

    @Override
    public void initGui() {
        this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        int i = 0;
        while (i < videoOptions.length) {
            GameSettings.Options gamesettings$options = videoOptions[i];
            if (gamesettings$options != null) {
                int j = width / 2 - 155 + i % 2 * 160;
                int k = height / 6 + 21 * (i / 2) - 12;
                if (gamesettings$options.getEnumFloat()) {
                    this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
                } else {
                    this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.guiGameSettings.getKeyBinding(gamesettings$options)));
                }
            }
            ++i;
        }
        int l = height / 6 + 21 * (videoOptions.length / 2) - 12;
        int i1 = 0;
        i1 = width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get((String)"of.options.shaders")));
        i1 = width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get((String)"of.options.quality")));
        i1 = width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(201, i1, l += 21, Lang.get((String)"of.options.details")));
        i1 = width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get((String)"of.options.performance")));
        i1 = width / 2 - 155 + 0;
        this.buttonList.add(new GuiOptionButton(211, i1, l += 21, Lang.get((String)"of.options.animations")));
        i1 = width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get((String)"of.options.other")));
        l += 21;
        this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            int i = this.guiGameSettings.guiScale;
            if (button.id < 200 && button instanceof GuiOptionButton) {
                this.guiGameSettings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (this.guiGameSettings.guiScale != i) {
                ScaledResolution scaledresolution = new ScaledResolution(this.mc);
                int j = scaledresolution.getScaledWidth();
                int k = scaledresolution.getScaledHeight();
                this.setWorldAndResolution(this.mc, j, k);
            }
            if (button.id == 201) {
                this.mc.gameSettings.saveOptions();
                GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF((GuiScreen)this, this.guiGameSettings);
                this.mc.displayGuiScreen((GuiScreen)guidetailsettingsof);
            }
            if (button.id == 202) {
                this.mc.gameSettings.saveOptions();
                GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF((GuiScreen)this, this.guiGameSettings);
                this.mc.displayGuiScreen((GuiScreen)guiqualitysettingsof);
            }
            if (button.id == 211) {
                this.mc.gameSettings.saveOptions();
                GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF((GuiScreen)this, this.guiGameSettings);
                this.mc.displayGuiScreen((GuiScreen)guianimationsettingsof);
            }
            if (button.id == 212) {
                this.mc.gameSettings.saveOptions();
                GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF((GuiScreen)this, this.guiGameSettings);
                this.mc.displayGuiScreen((GuiScreen)guiperformancesettingsof);
            }
            if (button.id == 222) {
                this.mc.gameSettings.saveOptions();
                GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF((GuiScreen)this, this.guiGameSettings);
                this.mc.displayGuiScreen((GuiScreen)guiothersettingsof);
            }
            if (button.id == 231) {
                if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
                    Config.showGuiMessage((String)Lang.get((String)"of.message.shaders.aa1"), (String)Lang.get((String)"of.message.shaders.aa2"));
                    return;
                }
                if (Config.isAnisotropicFiltering()) {
                    Config.showGuiMessage((String)Lang.get((String)"of.message.shaders.af1"), (String)Lang.get((String)"of.message.shaders.af2"));
                    return;
                }
                if (Config.isFastRender()) {
                    Config.showGuiMessage((String)Lang.get((String)"of.message.shaders.fr1"), (String)Lang.get((String)"of.message.shaders.fr2"));
                    return;
                }
                this.mc.gameSettings.saveOptions();
                GuiShaders guishaders = new GuiShaders((GuiScreen)this, this.guiGameSettings);
                this.mc.displayGuiScreen((GuiScreen)guishaders);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Client.getBackgrundAPI3().renderShader();
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, width / 2, 15, 0xFFFFFF);
        String s = Config.getVersion();
        String s1 = "HD_U";
        if (s1.equals("HD")) {
            s = "OptiFine HD H8";
        }
        if (s1.equals("HD_U")) {
            s = "OptiFine HD H8 Ultra";
        }
        if (s1.equals("L")) {
            s = "OptiFine H8 Light";
        }
        this.drawString(this.fontRendererObj, s, 2.0f, height - 10, 0x808080);
        String s2 = "Minecraft 1.8.8";
        int i = this.fontRendererObj.getStringWidth(s2);
        this.drawString(this.fontRendererObj, s2, width - i - 2, height - 10, 0x808080);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
    }

    public static int getButtonWidth(GuiButton p_getButtonWidth_0_) {
        return p_getButtonWidth_0_.width;
    }

    public static int getButtonHeight(GuiButton p_getButtonHeight_0_) {
        return p_getButtonHeight_0_.height;
    }

    public static void drawGradientRect(GuiScreen p_drawGradientRect_0_, int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_) {
        GuiScreen.drawGradientRect(p_drawGradientRect_1_, p_drawGradientRect_2_, p_drawGradientRect_3_, p_drawGradientRect_4_, p_drawGradientRect_5_, p_drawGradientRect_6_);
    }
}

