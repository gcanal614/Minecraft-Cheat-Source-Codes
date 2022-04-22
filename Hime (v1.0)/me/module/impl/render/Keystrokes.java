package me.module.impl.render;

import me.Hime;
import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.RainbowUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Keystrokes extends Module {
    /**
     * Settings
     */
    public Setting wasd;
    public Setting space;
    public Setting mb;
    public Setting yAdd;
    public Setting xAdd;

    /**
     * Fade stuff
     */
    public int fade;
    public int fade2;
    public int fade3;
    public int fade4;
    public int fade5;
    public int fade6;
    public int fade7;

    /**
     * punjabi habibi resolution 2021 bye pase hypixel no scam allah
     */
    ScaledResolution sr = new ScaledResolution(this.mc);

    public Keystrokes() {
        super("Keystrokes", 0, Category.RENDER);
        /**
         * g8lol is very sex
         */
    }

    public void setup() {
        Hime.instance.settingsManager.rSetting(yAdd = new Setting("Y (Up)", this, 0, 0, 300, false));
        Hime.instance.settingsManager.rSetting(xAdd = new Setting("X (Right)", this, 0, 0, 850, false));
        Hime.instance.settingsManager.rSetting(wasd = new Setting("WASD", this, true));
        Hime.instance.settingsManager.rSetting(space = new Setting("Spacebar", this, true));
        Hime.instance.settingsManager.rSetting(mb = new Setting("Mouse Buttons", this, true));
    }

    @Handler
    public void onRenderHud(EventRenderHUD event) {
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindForward.isKeyDown() && fade != 150) {
            if(!(mc.currentScreen instanceof GuiScreen)) {
                fade += 5;
            }
        } else {
            if (!mc.gameSettings.keyBindForward.isKeyDown() && fade != 0) {
                fade -= 5;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindLeft.isKeyDown() && fade2 != 150) {
            if(!(mc.currentScreen instanceof GuiScreen)) {
                fade2 += 5;
            }
        } else {
            if (!mc.gameSettings.keyBindLeft.isKeyDown() && fade2 != 0) {
                fade2 -= 5;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindBack.isKeyDown() && fade3 != 150) {
            if(!(mc.currentScreen instanceof GuiScreen)) {
                fade3 += 5;
            }
        } else {
            if (!mc.gameSettings.keyBindBack.isKeyDown() && fade3 != 0) {
                fade3 -= 5;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindRight.isKeyDown() && fade4 != 150) {
            if(!(mc.currentScreen instanceof GuiScreen)) {
                fade4 += 5;
            }
        } else {
            if (!mc.gameSettings.keyBindRight.isKeyDown() && fade4 != 0) {
                fade4 -= 5;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindJump.isKeyDown() && fade5 != 150) {
            if(!(mc.currentScreen instanceof GuiScreen)) {
                fade5 += 5;
            }
        } else {
            if (!mc.gameSettings.keyBindJump.isKeyDown() && fade5 != 0) {
                fade5 -= 5;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindAttack.isKeyDown() && fade6 != 160) {
            fade6 += 20;
        } else {
            if (!mc.gameSettings.keyBindAttack.isKeyDown() && fade6 != 0) {
                fade6 -= 20;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        if (mc.gameSettings.keyBindUseItem.isKeyDown() && fade7 != 160) {
            fade7 += 20;
        } else {
            if (!mc.gameSettings.keyBindUseItem.isKeyDown() && fade7 != 0) {
                fade7 -= 20;
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                if (wasd.getValBoolean() == true)
                {
                    Gui.drawRect(sr.getScaledWidth() / 7 - 98.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 5 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 70-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 33 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 98.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 5 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 70-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 33 + 250 - yAdd.getValDouble(), white(fade).getRGB());
                    Hime.instance.cfr.drawString("W", (float) (sr.getScaledWidth() / 7 - 90.5f - 3.9 + xAdd.getValDouble() + 80), (float) (sr.getScaledHeight() / 4 + 14 + 250 - yAdd.getValDouble()), RainbowUtil.rainbow(50, 1, 1));
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 39 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 104-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 67 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 39 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 104-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 67 + 250 - yAdd.getValDouble(), white(fade2).getRGB());
                    Hime.instance.cfr.drawString("A", (float) (sr.getScaledWidth() / 7 - 124-3.9 + xAdd.getValDouble() + 80), (float) (sr.getScaledHeight() / 4 + 48 + 250 - yAdd.getValDouble()), RainbowUtil.rainbow(50, 1, 1));
                    Gui.drawRect(sr.getScaledWidth() / 7 - 98.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 39 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 70-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 67 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 98.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 39 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 70-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 67 + 250 - yAdd.getValDouble(), white(fade3).getRGB());
                    Hime.instance.cfr.drawString("S", (float) (sr.getScaledWidth() / 7 - 90.5f-3.9 + xAdd.getValDouble() + 80), (float) (sr.getScaledHeight() / 4 + 48 + 250 - yAdd.getValDouble()), RainbowUtil.rainbow(50, 1, 1));
                    Gui.drawRect(sr.getScaledWidth() / 7 - 64.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 39 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 67 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 64.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 39 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 67 + 250 - yAdd.getValDouble(), white(fade4).getRGB());
                    Hime.instance.cfr.drawString("D", (float) (sr.getScaledWidth() / 7 - 56-3.9 + xAdd.getValDouble() + 80), (float) (sr.getScaledHeight() / 4 + 48 + 250 - yAdd.getValDouble()), RainbowUtil.rainbow(50, 1, 1));
                }
                if (space.getValBoolean() == true && mb.getValBoolean() == true)
                {
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 107 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 135 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 107 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 135 + 250 - yAdd.getValDouble(), white(fade5).getRGB());
                    Gui.drawRect(sr.getScaledWidth() / 7 - 122.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 120 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 46-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 122 + 250 - yAdd.getValDouble(), RainbowUtil.rainbow(50, 1, 1));
                }
                if (space.getValBoolean() == true && mb.getValBoolean() == false)
                {
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 73 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 101 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 73 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 101 + 250 - yAdd.getValDouble(), white(fade5).getRGB());
                    Gui.drawRect(sr.getScaledWidth() / 7 - 122.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 86 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 46-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 88 + 250 - yAdd.getValDouble(), RainbowUtil.rainbow(50, 1, 1));
                }                                                                                   //13 more                                                                                              //13 less
                if (mb.getValBoolean() == true)
                {
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 73 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 88-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 101 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 132.1f-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 73 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 88-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 101 + 250 - yAdd.getValDouble(), white(fade6).getRGB());
                    Hime.instance.cfr.drawString("LMB", (float) (sr.getScaledWidth() / 7 - 125.72f-3.9 + xAdd.getValDouble() + 80), (float) (sr.getScaledHeight() / 4 + 82.5f + 250 - yAdd.getValDouble()), RainbowUtil.rainbow(50, 1, 1));
                    Gui.drawRect(sr.getScaledWidth() / 7 - 82-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 73 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 101 + 250 - yAdd.getValDouble(), 0x80000000);
                    Gui.drawRect(sr.getScaledWidth() / 7 - 82-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 73 + 250 - yAdd.getValDouble(), sr.getScaledWidth() / 7 - 36-3.9 + xAdd.getValDouble() + 80, sr.getScaledHeight() / 4 + 101 + 250 - yAdd.getValDouble(), white(fade7).getRGB());
                    Hime.instance.cfr.drawString("RMB", (float) (sr.getScaledWidth() / 7 - 74-3.9 + xAdd.getValDouble() + 80), (float) (sr.getScaledHeight() / 4 + 82.5f + 250 - yAdd.getValDouble()), RainbowUtil.rainbow(50, 1, 1));
                }
            }
    public static Color white(int a) {
        Color c = new Color(255, 255, 255, a);
        return c;
    }
}
