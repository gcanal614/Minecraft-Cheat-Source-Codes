/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AtomicDouble
 *  fr.lavache.anime.Animate
 *  fr.lavache.anime.Easing
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

import com.google.common.util.concurrent.AtomicDouble;
import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import fr.lavache.anime.Animate;
import fr.lavache.anime.Easing;
import java.awt.Color;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Themes
extends Module {
    public static Themes INSTANCE;
    public static double Blur;
    int i = 0;
    Module mod;
    boolean max = false;
    private String mod1 = "";

    public Themes() {
        super("Themes", 0, Module.Type.Visual, Color.blue);
        this.settings.add(new Setting("VantaShadow", new CheckBox(false)));
        this.settings.add(new Setting("VantaMagenta", new CheckBox(false)));
        this.settings.add(new Setting("BlurSt\u00e4rke", new Slider(0.0, 1000.0, 0.1, 50.0)));
        this.settings.add(new Setting("ArrayColor", new DropdownBox("RED", new String[]{"RED", "BLUE", "GREEN", "YELLOW", "Novoline", "Rainbow"})));
        this.settings.add(new Setting("Fonts", new DropdownBox("Arial-Shadow", new String[]{"Winter Insight", "Arial", "Arial-Shadow", "Roboto-Thin", "MC", "MC-Shadow", "Arial-Rainbow", "Medusa", "Astolfo", "Rainbow", "Novoline", "RemixOld", "Clean", "Flux", "Custom", "Sigma4", "Test", "Vanta", "Test2", "Violence", "Skid", "Hero", "Holo", "Jello", "ZeroDay", "AstolfoLike", "Unify", "Centaurus", "Skeet", "Ambien", "Clou", "Novoline2.0"})));
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event e) {
        block261: {
            float f;
            String input;
            ScaledResolution sr;
            block306: {
                float f2;
                block303: {
                    float f3;
                    block300: {
                        float f4;
                        block299: {
                            block296: {
                                float f5;
                                block295: {
                                    float f6;
                                    block294: {
                                        float f7;
                                        block293: {
                                            float f8;
                                            block292: {
                                                float f9;
                                                block291: {
                                                    block290: {
                                                        block289: {
                                                            block288: {
                                                                block287: {
                                                                    block286: {
                                                                        block285: {
                                                                            block284: {
                                                                                block283: {
                                                                                    block282: {
                                                                                        block281: {
                                                                                            block280: {
                                                                                                block279: {
                                                                                                    block278: {
                                                                                                        float f10;
                                                                                                        block277: {
                                                                                                            block276: {
                                                                                                                block275: {
                                                                                                                    block272: {
                                                                                                                        block269: {
                                                                                                                            float f11;
                                                                                                                            block266: {
                                                                                                                                float f12;
                                                                                                                                block265: {
                                                                                                                                    block262: {
                                                                                                                                        float f13;
                                                                                                                                        boolean cfr_ignored_0 = e instanceof EventTick;
                                                                                                                                        if (!(e instanceof EventRender2D) || !e.isPre()) break block261;
                                                                                                                                        sr = new ScaledResolution(mc);
                                                                                                                                        input = ((DropdownBox)this.getSetting((String)"Fonts").getSetting()).curOption;
                                                                                                                                        if (!input.equalsIgnoreCase("Winter Insight")) break block262;
                                                                                                                                        int count = 0;
                                                                                                                                        int index = 0;
                                                                                                                                        int count2 = 0;
                                                                                                                                        int index2 = 0;
                                                                                                                                        GL11.glEnable((int)3042);
                                                                                                                                        GL11.glBlendFunc((int)770, (int)771);
                                                                                                                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(value.name)));
                                                                                                                                        for (Module m2 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                            if (!m2.isState()) continue;
                                                                                                                                            float offset = count2 * Client.INSTANCE.arial.FONT_HEIGHT;
                                                                                                                                            f13 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m2.name) - 3;
                                                                                                                                            float wSet22 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m2.name) - 5;
                                                                                                                                            if (Themes.mc.gameSettings.showDebugInfo || m2.name == "HUD" || m2.name == "Animations" || m2.name == "") continue;
                                                                                                                                            Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                                            float f14 = sr.getScaledWidth();
                                                                                                                                            Client.INSTANCE.arial.getClass();
                                                                                                                                            Client.blurHelper.blur2(wSet22, -1.0f, f14, offset + 9.0f + 5.5f, (float)Blur);
                                                                                                                                            ++count2;
                                                                                                                                            ++index2;
                                                                                                                                        }
                                                                                                                                        float offset = 0.0f;
                                                                                                                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                            block263: {
                                                                                                                                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                                Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                                                Client.INSTANCE.arial.getClass();
                                                                                                                                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                                f13 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                                                if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block263;
                                                                                                                                                switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                                                                                                                                    case "RED": {
                                                                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer3.drawStringWithShadow(module.name, f13, (int)offset - 1, Themes.getGradientOffset(new Color(255, 0, 0), new Color(64, 0, 0), (double)index / 12.4).getRGB());
                                                                                                                                                        break;
                                                                                                                                                    }
                                                                                                                                                    case "BLUE": {
                                                                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer3.drawStringWithShadow(module.name, f13, (int)offset - 1, Themes.getGradientOffset(new Color(0, 37, 255), new Color(0, 16, 110), (double)index / 12.4).getRGB());
                                                                                                                                                        break;
                                                                                                                                                    }
                                                                                                                                                    case "GREEN": {
                                                                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer3.drawStringWithShadow(module.name, f13, (int)offset - 1, Themes.getGradientOffset(new Color(0, 255, 4), new Color(0, 54, 0), (double)index / 12.4).getRGB());
                                                                                                                                                        break;
                                                                                                                                                    }
                                                                                                                                                    case "YELLOW": {
                                                                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer3.drawStringWithShadow(module.name, f13, (int)offset - 1, Themes.getGradientOffset(new Color(230, 255, 0), new Color(98, 122, 1), (double)index / 12.4).getRGB());
                                                                                                                                                        break;
                                                                                                                                                    }
                                                                                                                                                    case "Novoline": {
                                                                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer3.drawStringWithShadow(module.name, f13, (int)offset - 1, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                ++count;
                                                                                                                                                ++index;
                                                                                                                                                offset += module.anim2.getValue();
                                                                                                                                            }
                                                                                                                                            GL11.glDisable((int)3042);
                                                                                                                                        }
                                                                                                                                        break block261;
                                                                                                                                    }
                                                                                                                                    if (!input.equalsIgnoreCase("Arial-Rainbow")) break block265;
                                                                                                                                    int count = 0;
                                                                                                                                    int rainbow = 0;
                                                                                                                                    int index = 0;
                                                                                                                                    boolean count2 = false;
                                                                                                                                    int index2 = 0;
                                                                                                                                    float f15 = 0.0f;
                                                                                                                                    GL11.glEnable((int)3042);
                                                                                                                                    GL11.glBlendFunc((int)770, (int)771);
                                                                                                                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                                                                    for (Module m4 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                        if (!m4.isState()) continue;
                                                                                                                                        float wSet16 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m4.getMode().isEmpty() ? m4.name : String.format("%s %s%s", new Object[]{m4.name, EnumChatFormatting.GRAY, String.valueOf(m4.getMode()) + " "})) - 3;
                                                                                                                                        float wSet23 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m4.getMode().isEmpty() ? m4.name : String.format("%s %s%s", new Object[]{m4.name, EnumChatFormatting.GRAY, String.valueOf(m4.getMode()) + " "})) - 5;
                                                                                                                                        if (Themes.mc.gameSettings.showDebugInfo || m4.name == "HUD" || m4.name == "Animations" || m4.name == "") continue;
                                                                                                                                        Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                                        Gui.drawRect(wSet23, f15, sr.getScaledWidth(), f15 + 11.0f, Themes.rainbow((int)f15 * 9));
                                                                                                                                        Gui.drawRect(wSet23 + 1.0f, f15, sr.getScaledWidth(), f15 + 10.0f, Color.black.getRGB());
                                                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer.drawString(m4.getMode().isEmpty() ? m4.name : String.format("%s %s%s", new Object[]{m4.name, EnumChatFormatting.GRAY, m4.getMode()}), wSet16, f15 - 1.0f, Themes.rainbow((int)f15 * 9));
                                                                                                                                        f15 += 10.0f;
                                                                                                                                        ++index2;
                                                                                                                                    }
                                                                                                                                    float offset = 0.0f;
                                                                                                                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                                        Client.INSTANCE.arial.getClass();
                                                                                                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                        float wSet17 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                                                            ++rainbow;
                                                                                                                                            ++count;
                                                                                                                                            ++index;
                                                                                                                                            offset += module.anim2.getValue();
                                                                                                                                        }
                                                                                                                                        GL11.glDisable((int)3042);
                                                                                                                                    }
                                                                                                                                    break block261;
                                                                                                                                }
                                                                                                                                if (!input.equalsIgnoreCase("Holo")) break block266;
                                                                                                                                int count = 0;
                                                                                                                                int index = 0;
                                                                                                                                int count2 = 0;
                                                                                                                                int index2 = 0;
                                                                                                                                GL11.glEnable((int)3042);
                                                                                                                                GL11.glBlendFunc((int)770, (int)771);
                                                                                                                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.Holo.getStringWidth(value.name)));
                                                                                                                                for (Module m6 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                    if (!m6.isState()) continue;
                                                                                                                                    float offset = count2 * Client.INSTANCE.Holo.FONT_HEIGHT;
                                                                                                                                    f12 = sr.getScaledWidth() - Client.INSTANCE.Holo.getStringWidth(m6.name) - 5;
                                                                                                                                    float wSet24 = sr.getScaledWidth() - Client.INSTANCE.Holo.getStringWidth(m6.name) - 5;
                                                                                                                                    if (Themes.mc.gameSettings.showDebugInfo || m6.name == "HUD" || m6.name == "Animations" || m6.name == "") continue;
                                                                                                                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                                    float f16 = sr.getScaledWidth();
                                                                                                                                    Client.INSTANCE.Holo.getClass();
                                                                                                                                    Client.blurHelper.blur2(f12, offset - 5.0f, f16, offset + 9.0f + 1.5f, 50.0f);
                                                                                                                                    float f17 = sr.getScaledWidth();
                                                                                                                                    Client.INSTANCE.Holo.getClass();
                                                                                                                                    Gui.drawRect(f12, offset - 5.0f, f17, offset + 9.0f, new Color(255, 255, 255, 27).getRGB());
                                                                                                                                    ++count2;
                                                                                                                                    ++index2;
                                                                                                                                }
                                                                                                                                float offset = 0.0f;
                                                                                                                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                    block267: {
                                                                                                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.Holo.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                                        Client.INSTANCE.Holo.getClass();
                                                                                                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                        f12 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                                        Gui.drawRect((float)sr.getScaledWidth() - 1.0f, offset, sr.getScaledWidth(), offset + 10.0f, Themes.getGradientOffset(Color.cyan, Color.blue, (double)index / 12.4).getRGB());
                                                                                                                                        if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block267;
                                                                                                                                        switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                                                                                                                            case "RED": {
                                                                                                                                                Client.INSTANCE.Holo.drawString(module.name, f12 - 1.0f, (int)offset - 1, -1);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            case "BLUE": {
                                                                                                                                                Client.INSTANCE.Holo.drawString(module.name, f12 - 1.0f, (int)offset - 1, -1);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            case "GREEN": {
                                                                                                                                                Client.INSTANCE.Holo.drawString(module.name, f12 - 1.0f, (int)offset - 1, -1);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            case "YELLOW": {
                                                                                                                                                Client.INSTANCE.Holo.drawString(module.name, f12 - 1.0f, (int)offset - 1, -1);
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            case "Novoline": {
                                                                                                                                                Client.INSTANCE.Holo.drawString(module.name, f12 - 1.0f, (int)offset - 1, -1);
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        ++count;
                                                                                                                                        ++index;
                                                                                                                                        offset += module.anim2.getValue();
                                                                                                                                    }
                                                                                                                                    GL11.glDisable((int)3042);
                                                                                                                                }
                                                                                                                                break block261;
                                                                                                                            }
                                                                                                                            if (!input.equalsIgnoreCase("Jello")) break block269;
                                                                                                                            int count = 0;
                                                                                                                            int index = 0;
                                                                                                                            int count2 = 0;
                                                                                                                            int index2 = 0;
                                                                                                                            GL11.glEnable((int)3042);
                                                                                                                            GL11.glBlendFunc((int)770, (int)771);
                                                                                                                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.Jello3.getStringWidth(value.name)));
                                                                                                                            for (Module m8 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                if (!m8.isState()) continue;
                                                                                                                                float offset = count2 * Client.INSTANCE.Jello3.FONT_HEIGHT;
                                                                                                                                f11 = sr.getScaledWidth() - Client.INSTANCE.Jello3.getStringWidth(m8.name) - 5;
                                                                                                                                float wSet25 = sr.getScaledWidth() - Client.INSTANCE.Jello3.getStringWidth(m8.name) - 5;
                                                                                                                                if (Themes.mc.gameSettings.showDebugInfo || m8.name == "HUD" || m8.name == "Animations" || m8.name == "") continue;
                                                                                                                                Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                                float f18 = sr.getScaledWidth();
                                                                                                                                Client.INSTANCE.Jello3.getClass();
                                                                                                                                Client.blurHelper.blur2(f11, offset - 5.0f, f18, offset + 9.0f + 1.5f, 50.0f);
                                                                                                                                ++count2;
                                                                                                                                ++index2;
                                                                                                                            }
                                                                                                                            float offset = 0.0f;
                                                                                                                            for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                                block270: {
                                                                                                                                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.Jello3.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                                    Client.INSTANCE.Jello3.getClass();
                                                                                                                                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                                    f11 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                                    if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block270;
                                                                                                                                    switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                                                                                                                        case "RED": {
                                                                                                                                            Client.INSTANCE.Jello3.drawString(module.name, f11 - 1.0f, (int)offset, -1);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        case "BLUE": {
                                                                                                                                            Client.INSTANCE.Jello3.drawString(module.name, f11 - 1.0f, (int)offset - 1, -1);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        case "GREEN": {
                                                                                                                                            Client.INSTANCE.Jello3.drawString(module.name, f11 - 1.0f, (int)offset - 1, -1);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        case "YELLOW": {
                                                                                                                                            Client.INSTANCE.Jello3.drawString(module.name, f11 - 1.0f, (int)offset - 1, -1);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        case "Novoline": {
                                                                                                                                            Client.INSTANCE.Jello3.drawString(module.name, f11 - 1.0f, (int)offset - 1, -1);
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    ++count;
                                                                                                                                    ++index;
                                                                                                                                    offset += module.anim2.getValue();
                                                                                                                                }
                                                                                                                                GL11.glDisable((int)3042);
                                                                                                                            }
                                                                                                                            break block261;
                                                                                                                        }
                                                                                                                        if (!input.equalsIgnoreCase("AstolfoLike")) break block272;
                                                                                                                        int count = 0;
                                                                                                                        int index = 0;
                                                                                                                        int count2 = 0;
                                                                                                                        int index2 = 0;
                                                                                                                        float offset2 = 0.0f;
                                                                                                                        GL11.glEnable((int)3042);
                                                                                                                        GL11.glBlendFunc((int)770, (int)771);
                                                                                                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.fluxTabGuiFont.getStringWidth(value.name)));
                                                                                                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                            if (!module.isState()) continue;
                                                                                                                            float f19 = count2 * Client.INSTANCE.fluxTabGuiFont.FONT_HEIGHT;
                                                                                                                            float wSet12 = sr.getScaledWidth() - Client.INSTANCE.fluxTabGuiFont.getStringWidth(module.name) - 5;
                                                                                                                            float wSet26 = sr.getScaledWidth() - Client.INSTANCE.fluxTabGuiFont.getStringWidth(module.name) - 5;
                                                                                                                            if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") continue;
                                                                                                                            Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                            float f20 = sr.getScaledWidth();
                                                                                                                            Client.INSTANCE.fluxTabGuiFont.getClass();
                                                                                                                            Client.blurHelper.blur2(wSet12 - 3.0f, f19 - 6.0f, f20, f19 + 9.0f + 2.0f, 50.0f);
                                                                                                                            Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f19 - 20.0f, sr.getScaledWidth(), f19 + 8.0f, module.getMode().equalsIgnoreCase("") || module.getMode() == null || module.getMode().isEmpty() ? Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB() : Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                            ++count2;
                                                                                                                            ++index2;
                                                                                                                            ++index;
                                                                                                                        }
                                                                                                                        float f21 = 0.0f;
                                                                                                                        for (Module m10 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                            block273: {
                                                                                                                                if (!m10.isState() && m10.anim.getValue() == 0.0f) continue;
                                                                                                                                m10.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.fluxTabGuiFont.getStringWidth(m10.name) + 3)).setSpeed(200.0f).setReversed(!m10.isState()).update();
                                                                                                                                Animate animate = m10.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                                Client.INSTANCE.fluxTabGuiFont.getClass();
                                                                                                                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m10.isState()).update();
                                                                                                                                float wSet = (float)sr.getScaledWidth() - m10.anim.getValue();
                                                                                                                                if (Themes.mc.gameSettings.showDebugInfo || m10.name == "HUD" || m10.name == "Animations" || m10.name == "") break block273;
                                                                                                                                switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                                                                                                                    case "RED": {
                                                                                                                                        Client.INSTANCE.fluxTabGuiFont.drawString(m10.name, wSet - 3.0f, (int)f21 - 3, Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                    case "BLUE": {
                                                                                                                                        Client.INSTANCE.fluxTabGuiFont.drawString(m10.name, wSet - 1.0f, (int)f21 - 1, Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                    case "GREEN": {
                                                                                                                                        Client.INSTANCE.fluxTabGuiFont.drawString(m10.name, wSet - 1.0f, (int)f21 - 1, Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                    case "YELLOW": {
                                                                                                                                        Client.INSTANCE.fluxTabGuiFont.drawString(m10.name, wSet - 1.0f, (int)f21 - 1, Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                    case "Novoline": {
                                                                                                                                        Client.INSTANCE.fluxTabGuiFont.drawString(m10.name, wSet - 1.0f, (int)f21 - 1, Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                ++count;
                                                                                                                                ++index;
                                                                                                                                f21 += m10.anim2.getValue();
                                                                                                                            }
                                                                                                                            GL11.glDisable((int)3042);
                                                                                                                        }
                                                                                                                        break block261;
                                                                                                                    }
                                                                                                                    if (!input.equalsIgnoreCase("Sigma4")) break block275;
                                                                                                                    int count = 0;
                                                                                                                    int rainbow = 0;
                                                                                                                    int index = 0;
                                                                                                                    boolean count2 = false;
                                                                                                                    int index2 = 0;
                                                                                                                    float f22 = 0.0f;
                                                                                                                    GL11.glEnable((int)3042);
                                                                                                                    GL11.glBlendFunc((int)770, (int)771);
                                                                                                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.Sigma.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                                                    for (Module m11 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                        if (!m11.isState()) continue;
                                                                                                                        float wSet18 = sr.getScaledWidth() - Client.INSTANCE.Sigma.getStringWidth(m11.getMode().isEmpty() ? m11.name : String.format("%s %s%s", new Object[]{m11.name, EnumChatFormatting.GRAY, String.valueOf(m11.getMode()) + " "})) - 3;
                                                                                                                        float wSet27 = sr.getScaledWidth() - Client.INSTANCE.Sigma.getStringWidth(m11.getMode().isEmpty() ? m11.name : String.format("%s %s%s", new Object[]{m11.name, EnumChatFormatting.GRAY, String.valueOf(m11.getMode()) + " "})) - 5;
                                                                                                                        if (Themes.mc.gameSettings.showDebugInfo || m11.name == "HUD" || m11.name == "Animations" || m11.name == "") continue;
                                                                                                                        Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                        Gui.drawRect(wSet27, f22, sr.getScaledWidth(), f22 + 11.0f, Themes.rainbowSigma((int)f22 * 15));
                                                                                                                        Gui.drawRect(wSet27 + 1.0f, f22, sr.getScaledWidth(), f22 + 10.0f, Color.black.getRGB());
                                                                                                                        Client.INSTANCE.Sigma.drawString(m11.getMode().isEmpty() ? m11.name : String.format("%s %s%s", new Object[]{m11.name, EnumChatFormatting.GRAY, m11.getMode()}), wSet18, f22 - 1.0f, Themes.rainbowSigma((int)f22 * 15));
                                                                                                                        f22 += 10.0f;
                                                                                                                        ++index2;
                                                                                                                    }
                                                                                                                    float offset = 0.0f;
                                                                                                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.Sigma.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                        Client.INSTANCE.Sigma.getClass();
                                                                                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                        float wSet19 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                                            ++rainbow;
                                                                                                                            ++count;
                                                                                                                            ++index;
                                                                                                                            offset += module.anim2.getValue();
                                                                                                                        }
                                                                                                                        GL11.glDisable((int)3042);
                                                                                                                    }
                                                                                                                    break block261;
                                                                                                                }
                                                                                                                if (!input.equalsIgnoreCase("RemixOld")) break block276;
                                                                                                                int count = 0;
                                                                                                                int rainbow = 0;
                                                                                                                int index = 0;
                                                                                                                boolean count2 = false;
                                                                                                                int index2 = 0;
                                                                                                                float f23 = 0.0f;
                                                                                                                GL11.glEnable((int)3042);
                                                                                                                GL11.glBlendFunc((int)770, (int)771);
                                                                                                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer6.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                                                for (Module m13 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                    if (!m13.isState()) continue;
                                                                                                                    float wSet20 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer6.getStringWidth(m13.getMode().isEmpty() ? m13.name : String.format("%s %s%s", new Object[]{m13.name, EnumChatFormatting.GRAY, String.valueOf(m13.getMode()) + " "})) - 3;
                                                                                                                    float wSet28 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer6.getStringWidth(m13.getMode().isEmpty() ? m13.name : String.format("%s %s%s", new Object[]{m13.name, EnumChatFormatting.GRAY, String.valueOf(m13.getMode()) + " "})) - 5;
                                                                                                                    if (Themes.mc.gameSettings.showDebugInfo || m13.name == "HUD" || m13.name == "Animations" || m13.name == "") continue;
                                                                                                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                    Gui.drawRect(wSet28, f23, sr.getScaledWidth(), f23 + 11.0f, Themes.getGradientOffset(new Color(227, 145, 16, 255), new Color(95, 61, 8, 255), (double)index / 4.4).getRGB());
                                                                                                                    Gui.drawRect(wSet28 + 1.0f, f23, sr.getScaledWidth(), f23 + 10.0f, new Color(30, 30, 30, 255).getRGB());
                                                                                                                    Gui.drawRect((float)sr.getScaledWidth() + 0.5f, f23, (float)sr.getScaledWidth() - 0.5f, f23 + 10.0f, Themes.getGradientOffset(new Color(227, 145, 16, 255), new Color(95, 61, 8, 255), (double)index / 4.4).getRGB());
                                                                                                                    Client.INSTANCE.unicodeBasicFontRenderer6.drawStringWithShadow(m13.getMode().isEmpty() ? m13.name : String.format("%s %s%s", new Object[]{m13.name, EnumChatFormatting.GRAY, m13.getMode()}), wSet20 - 0.5f, f23 - 1.0f, Themes.getGradientOffset(new Color(227, 145, 16, 255), new Color(95, 61, 8, 255), (double)index / 3.4).getRGB());
                                                                                                                    f23 += 10.0f;
                                                                                                                    ++index2;
                                                                                                                    ++index;
                                                                                                                }
                                                                                                                float offset = 0.0f;
                                                                                                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer6.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                    Client.INSTANCE.arial.getClass();
                                                                                                                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                    float wSet21 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                    if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                                        ++rainbow;
                                                                                                                        ++count;
                                                                                                                        ++index;
                                                                                                                        offset += module.anim2.getValue();
                                                                                                                    }
                                                                                                                    GL11.glDisable((int)3042);
                                                                                                                }
                                                                                                                break block261;
                                                                                                            }
                                                                                                            if (!input.equalsIgnoreCase("Astolfo")) break block277;
                                                                                                            int count = 0;
                                                                                                            int rainbow = 0;
                                                                                                            int index = 0;
                                                                                                            boolean count2 = false;
                                                                                                            int index2 = 0;
                                                                                                            float f24 = 0.0f;
                                                                                                            GL11.glEnable((int)3042);
                                                                                                            GL11.glBlendFunc((int)770, (int)771);
                                                                                                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                                            for (Module m14 : Client.INSTANCE.moduleManager.modules) {
                                                                                                                if (!m14.isState()) continue;
                                                                                                                float wSet22 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m14.getMode().isEmpty() ? m14.name : String.format("%s %s%s", new Object[]{m14.name, EnumChatFormatting.GRAY, String.valueOf(m14.getMode()) + " "})) - 3;
                                                                                                                float wSet29 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m14.getMode().isEmpty() ? m14.name : String.format("%s %s%s", new Object[]{m14.name, EnumChatFormatting.GRAY, String.valueOf(m14.getMode()) + " "})) - 5;
                                                                                                                if (Themes.mc.gameSettings.showDebugInfo || m14.name == "HUD" || m14.name == "Animations" || m14.name == "") continue;
                                                                                                                Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                                Gui.drawRect(wSet29, f24, sr.getScaledWidth(), f24 + 10.0f, new Color(0, 0, 0, 120).getRGB());
                                                                                                                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m14.getMode().isEmpty() ? m14.name : String.format("%s %s%s", new Object[]{m14.name, EnumChatFormatting.GRAY, m14.getMode()}), wSet22, f24 - 1.0f, Themes.SkyRainbow(index, 1.0f, 0.6f).getRGB());
                                                                                                                ++index;
                                                                                                                f24 += 10.0f;
                                                                                                                ++index2;
                                                                                                            }
                                                                                                            float offset = 0.0f;
                                                                                                            for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                                Client.INSTANCE.arial.getClass();
                                                                                                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                                float wSet23 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                                if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                                    ++rainbow;
                                                                                                                    ++count;
                                                                                                                    ++index;
                                                                                                                    offset += module.anim2.getValue();
                                                                                                                }
                                                                                                                GL11.glDisable((int)3042);
                                                                                                            }
                                                                                                            break block261;
                                                                                                        }
                                                                                                        if (!input.equalsIgnoreCase("Custom")) break block278;
                                                                                                        int count = 0;
                                                                                                        int index = 0;
                                                                                                        int count2 = 0;
                                                                                                        int index2 = 0;
                                                                                                        GL11.glEnable((int)3042);
                                                                                                        GL11.glBlendFunc((int)770, (int)771);
                                                                                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Themes.mc.fontRendererObj.getStringWidth(value.name)));
                                                                                                        for (Module m16 : Client.INSTANCE.moduleManager.modules) {
                                                                                                            if (!m16.isState()) continue;
                                                                                                            float offset = count2 * Themes.mc.fontRendererObj.getFontHeight();
                                                                                                            f10 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m16.name) - 3;
                                                                                                            float wSet210 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m16.name) - 5;
                                                                                                            if (Themes.mc.gameSettings.showDebugInfo || m16.name == "HUD" || m16.name == "Animations" || m16.name == "") continue;
                                                                                                            ++count2;
                                                                                                            ++index2;
                                                                                                        }
                                                                                                        float offset = 0.0f;
                                                                                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                            if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                            module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Themes.mc.fontRendererObj.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                            module.anim2.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)Themes.mc.fontRendererObj.getFontHeight()).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                            f10 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                            if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                                Gui.drawRect(f10 - 1.0f, offset, sr.getScaledWidth(), offset + 10.0f, Color.black.getRGB());
                                                                                                                Themes.mc.fontRendererObj.drawStringWithShadow(module.name, f10, (int)offset, this.getColor2());
                                                                                                                ++count;
                                                                                                                ++index;
                                                                                                                offset += module.anim2.getValue();
                                                                                                            }
                                                                                                            GL11.glDisable((int)3042);
                                                                                                        }
                                                                                                        break block261;
                                                                                                    }
                                                                                                    if (!input.equalsIgnoreCase("Medusa")) break block279;
                                                                                                    int count = 0;
                                                                                                    int rainbow = 0;
                                                                                                    int index = 0;
                                                                                                    boolean count2 = false;
                                                                                                    int index2 = 0;
                                                                                                    float f25 = 0.0f;
                                                                                                    GL11.glEnable((int)3042);
                                                                                                    GL11.glBlendFunc((int)770, (int)771);
                                                                                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(String.valueOf(value.name) + " " + value.getMode())));
                                                                                                    for (Module m18 : Client.INSTANCE.moduleManager.modules) {
                                                                                                        if (!m18.isState()) continue;
                                                                                                        float wSet24 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m18.getMode().isEmpty() ? m18.name : String.format("%s %s%s", new Object[]{m18.name, EnumChatFormatting.GRAY, String.valueOf(m18.getMode()) + " "})) - 3;
                                                                                                        float wSet211 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m18.getMode().isEmpty() ? m18.name : String.format("%s %s%s", new Object[]{m18.name, EnumChatFormatting.GRAY, String.valueOf(m18.getMode()) + " "})) - 5;
                                                                                                        if (Themes.mc.gameSettings.showDebugInfo || m18.name == "HUD" || m18.name == "Animations" || m18.name == "") continue;
                                                                                                        Gui.drawRect(wSet211 - 4.0f, f25, sr.getScaledWidth(), f25 + 10.0f, new Color(40, 40, 40).getRGB());
                                                                                                        Client.INSTANCE.unicodeBasicFontRenderer.drawString(m18.getMode().isEmpty() ? m18.name : String.format("%s %s%s", new Object[]{m18.name, EnumChatFormatting.GRAY, m18.getMode()}), wSet24 - 5.0f, f25 - 1.0f, new Color(100, 254, 155).getRGB());
                                                                                                        Gui.drawRect(sr.getScaledWidth() - 4, f25, sr.getScaledWidth() - 2, f25 + 10.0f, m18.getMode().equalsIgnoreCase("") || m18.getMode() == null || m18.getMode().isEmpty() ? new Color(100, 254, 155).getRGB() : Color.gray.getRGB());
                                                                                                        f25 += 10.0f;
                                                                                                        ++index2;
                                                                                                    }
                                                                                                    float offset = 0.0f;
                                                                                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                        Client.INSTANCE.arial.getClass();
                                                                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                        float wSet25 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                            ++rainbow;
                                                                                                            ++count;
                                                                                                            ++index;
                                                                                                            offset += module.anim2.getValue();
                                                                                                        }
                                                                                                        GL11.glDisable((int)3042);
                                                                                                    }
                                                                                                    break block261;
                                                                                                }
                                                                                                if (!input.equalsIgnoreCase("Rainbow")) break block280;
                                                                                                int count = 0;
                                                                                                int rainbow = 0;
                                                                                                int index = 0;
                                                                                                boolean count2 = false;
                                                                                                int index2 = 0;
                                                                                                float f26 = 0.0f;
                                                                                                GL11.glEnable((int)3042);
                                                                                                GL11.glBlendFunc((int)770, (int)771);
                                                                                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                                for (Module m20 : Client.INSTANCE.moduleManager.modules) {
                                                                                                    if (!m20.isState()) continue;
                                                                                                    float wSet26 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m20.getMode().isEmpty() ? m20.name : String.format("%s %s%s", new Object[]{m20.name, EnumChatFormatting.GRAY, String.valueOf(m20.getMode()) + " "})) - 3;
                                                                                                    float wSet212 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m20.getMode().isEmpty() ? m20.name : String.format("%s %s%s", new Object[]{m20.name, EnumChatFormatting.GRAY, String.valueOf(m20.getMode()) + " "})) - 5;
                                                                                                    if (Themes.mc.gameSettings.showDebugInfo || m20.name == "HUD" || m20.name == "Animations" || m20.name == "") continue;
                                                                                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                    Gui.drawRect(wSet212 - 4.0f, f26, sr.getScaledWidth(), f26 + 10.0f, new Color(0, 0, 0, 180).getRGB());
                                                                                                    Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m20.getMode().isEmpty() ? m20.name : String.format("%s %s%s", new Object[]{m20.name, EnumChatFormatting.GRAY, m20.getMode()}), wSet26 - 5.0f, f26 - 1.0f, -1);
                                                                                                    Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f26, sr.getScaledWidth(), f26 + 10.0f, m20.getMode().equalsIgnoreCase("") || m20.getMode() == null || m20.getMode().isEmpty() ? Themes.rainbow((int)f26 * 9) : Themes.rainbow((int)f26 * 9));
                                                                                                    f26 += 10.0f;
                                                                                                    ++index2;
                                                                                                }
                                                                                                float offset = 0.0f;
                                                                                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                    Client.INSTANCE.arial.getClass();
                                                                                                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                    float wSet27 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                    if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                        ++rainbow;
                                                                                                        ++count;
                                                                                                        ++index;
                                                                                                        offset += module.anim2.getValue();
                                                                                                    }
                                                                                                    GL11.glDisable((int)3042);
                                                                                                }
                                                                                                break block261;
                                                                                            }
                                                                                            if (!input.equalsIgnoreCase("Flux")) break block281;
                                                                                            int count = 0;
                                                                                            int rainbow = 0;
                                                                                            int index = 0;
                                                                                            boolean count2 = false;
                                                                                            int index2 = 0;
                                                                                            float f27 = 0.0f;
                                                                                            GL11.glEnable((int)3042);
                                                                                            GL11.glBlendFunc((int)770, (int)771);
                                                                                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                            for (Module m21 : Client.INSTANCE.moduleManager.modules) {
                                                                                                if (!m21.isState()) continue;
                                                                                                float wSet28 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(m21.getMode().isEmpty() ? m21.name : String.format("%s %s%s", new Object[]{m21.name, EnumChatFormatting.GRAY, String.valueOf(m21.getMode()) + " "})) - 3;
                                                                                                float wSet213 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(m21.getMode().isEmpty() ? m21.name : String.format("%s %s%s", new Object[]{m21.name, EnumChatFormatting.GRAY, String.valueOf(m21.getMode()) + " "})) - 5;
                                                                                                if (Themes.mc.gameSettings.showDebugInfo || m21.name == "HUD" || m21.name == "Animations" || m21.name == "") continue;
                                                                                                Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                                Gui.drawRect(wSet213 - 4.0f, f27, sr.getScaledWidth(), f27 + 10.0f, new Color(0, 0, 0, 180).getRGB());
                                                                                                Client.INSTANCE.unicodeBasicFontRenderer7.drawStringWithShadow(m21.getMode().isEmpty() ? m21.name : String.format("%s %s%s", new Object[]{m21.name, EnumChatFormatting.GRAY, m21.getMode()}), wSet28 - 4.0f, f27 - 1.0f, Themes.rainbow((int)f27 * 9));
                                                                                                Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f27, sr.getScaledWidth(), f27 + 10.0f, m21.getMode().equalsIgnoreCase("") || m21.getMode() == null || m21.getMode().isEmpty() ? Themes.rainbow((int)f27 * 9) : Themes.rainbow((int)f27 * 9));
                                                                                                f27 += 10.0f;
                                                                                                ++index2;
                                                                                            }
                                                                                            float offset = 0.0f;
                                                                                            for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                                Client.INSTANCE.arial.getClass();
                                                                                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                                float wSet29 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                                if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                    ++rainbow;
                                                                                                    ++count;
                                                                                                    ++index;
                                                                                                    offset += module.anim2.getValue();
                                                                                                }
                                                                                                GL11.glDisable((int)3042);
                                                                                            }
                                                                                            break block261;
                                                                                        }
                                                                                        if (!input.equalsIgnoreCase("Clou")) break block282;
                                                                                        int count = 0;
                                                                                        int rainbow = 0;
                                                                                        int index = 0;
                                                                                        boolean count2 = false;
                                                                                        int index2 = 0;
                                                                                        float f28 = 0.0f;
                                                                                        GL11.glEnable((int)3042);
                                                                                        GL11.glBlendFunc((int)770, (int)771);
                                                                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unify2.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s [%s%s]", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                        for (Module m23 : Client.INSTANCE.moduleManager.modules) {
                                                                                            if (!m23.isState()) continue;
                                                                                            float wSet30 = sr.getScaledWidth() - Client.INSTANCE.unify2.getStringWidth(m23.getMode().isEmpty() ? m23.name : String.format("%s [%s%s]", new Object[]{m23.name, EnumChatFormatting.GRAY, String.valueOf(m23.getMode()) + " "})) - 3;
                                                                                            float wSet214 = sr.getScaledWidth() - Client.INSTANCE.unify2.getStringWidth(m23.getMode().isEmpty() ? m23.name : String.format("%s [%s%s]", new Object[]{m23.name, EnumChatFormatting.GRAY, String.valueOf(m23.getMode()) + " "})) - 5;
                                                                                            if (Themes.mc.gameSettings.showDebugInfo || m23.name == "HUD" || m23.name == "Animations" || m23.name == "") continue;
                                                                                            Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                            Gui.drawRect(wSet214 - 2.0f, f28, sr.getScaledWidth(), f28 + 11.0f, new Color(85, 85, 255).getRGB());
                                                                                            Gui.drawRect(wSet214 - 1.0f, f28, sr.getScaledWidth(), f28 + 10.0f, new Color(0, 0, 0, 255).getRGB());
                                                                                            Client.INSTANCE.unify2.drawString(m23.getMode().isEmpty() ? m23.name : String.format("%s \u00a79[%s%s]", new Object[]{m23.name, EnumChatFormatting.BLUE, m23.getMode()}), wSet30 - 2.0f, f28, -1);
                                                                                            f28 += 10.0f;
                                                                                            ++index2;
                                                                                        }
                                                                                        float offset = 0.0f;
                                                                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                            if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                            module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                            Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                            Client.INSTANCE.arial.getClass();
                                                                                            animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                            float wSet31 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                            if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                                ++rainbow;
                                                                                                ++count;
                                                                                                ++index;
                                                                                                offset += module.anim2.getValue();
                                                                                            }
                                                                                            GL11.glDisable((int)3042);
                                                                                        }
                                                                                        break block261;
                                                                                    }
                                                                                    if (!input.equalsIgnoreCase("Centaurus")) break block283;
                                                                                    int count = 0;
                                                                                    int rainbow = 0;
                                                                                    int index = 0;
                                                                                    boolean count2 = false;
                                                                                    int index2 = 0;
                                                                                    float f29 = 0.0f;
                                                                                    GL11.glEnable((int)3042);
                                                                                    GL11.glBlendFunc((int)770, (int)771);
                                                                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                                    for (Module m25 : Client.INSTANCE.moduleManager.modules) {
                                                                                        if (!m25.isState()) continue;
                                                                                        float wSet32 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(m25.getMode().isEmpty() ? m25.name : String.format("%s %s%s", new Object[]{m25.name, EnumChatFormatting.GRAY, String.valueOf(m25.getMode()) + " "})) - 3;
                                                                                        float wSet215 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(m25.getMode().isEmpty() ? m25.name : String.format("%s %s%s", new Object[]{m25.name, EnumChatFormatting.GRAY, String.valueOf(m25.getMode()) + " "})) - 5;
                                                                                        if (Themes.mc.gameSettings.showDebugInfo || m25.name == "HUD" || m25.name == "Animations" || m25.name == "") continue;
                                                                                        Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                        Client.INSTANCE.unicodeBasicFontRenderer7.drawStringWithShadow(m25.getMode().isEmpty() ? m25.name : String.format("%s %s%s", new Object[]{m25.name, EnumChatFormatting.GRAY, m25.getMode()}), wSet32, f29 - 1.0f, Themes.rainbowSigma((int)f29 * 9));
                                                                                        f29 += 9.0f;
                                                                                        ++index2;
                                                                                    }
                                                                                    float offset = 0.0f;
                                                                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                        Client.INSTANCE.unicodeBasicFontRenderer7.getClass();
                                                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                        float wSet33 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                            ++rainbow;
                                                                                            ++count;
                                                                                            ++index;
                                                                                            offset += module.anim2.getValue();
                                                                                        }
                                                                                        GL11.glDisable((int)3042);
                                                                                    }
                                                                                    break block261;
                                                                                }
                                                                                if (!input.equalsIgnoreCase("Unify")) break block284;
                                                                                int count = 0;
                                                                                int rainbow = 0;
                                                                                int index = 0;
                                                                                int count2 = 0;
                                                                                int index2 = 0;
                                                                                float f30 = 0.0f;
                                                                                GL11.glEnable((int)3042);
                                                                                GL11.glBlendFunc((int)770, (int)771);
                                                                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unify2.getStringWidth(value.name)));
                                                                                for (Module m27 : Client.INSTANCE.moduleManager.modules) {
                                                                                    if (!m27.isState()) continue;
                                                                                    float offset = count2 * Client.INSTANCE.unicodeBasicFontRenderer5.FONT_HEIGHT;
                                                                                    float wSet10 = sr.getScaledWidth() - Client.INSTANCE.unify2.getStringWidth(m27.name);
                                                                                    float wSet216 = sr.getScaledWidth() - Client.INSTANCE.unify2.getStringWidth(m27.name) - 2;
                                                                                    if (Themes.mc.gameSettings.showDebugInfo || m27.name == "HUD" || m27.name == "Animations" || m27.name == "") continue;
                                                                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                    Gui.drawRect(wSet216 - 8.0f, f30, sr.getScaledWidth() - 2, f30 + 11.0f, new Color(0, 172, 255).getRGB());
                                                                                    Gui.drawRect(wSet216 - 7.0f, f30, sr.getScaledWidth() - 2, f30 + 10.0f, new Color(0, 0, 0, 255).getRGB());
                                                                                    Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f30, sr.getScaledWidth(), f30 + 11.0f, new Color(0, 172, 255).getRGB());
                                                                                    Gui.drawRect(sr.getScaledWidth(), f30 + 10.0f, sr.getScaledWidth(), f30 + 12.0f, -1);
                                                                                    Client.INSTANCE.unify2.drawStringWithShadow(m27.name, wSet10 - 7.0f, (int)f30, -1);
                                                                                    f30 += 10.0f;
                                                                                    ++index2;
                                                                                }
                                                                                float offset = 0.0f;
                                                                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unify2.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                                    Client.INSTANCE.unify2.getClass();
                                                                                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                                    float wSet = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                                    if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                        ++rainbow;
                                                                                        ++count;
                                                                                        ++index;
                                                                                        offset += module.anim2.getValue();
                                                                                    }
                                                                                    GL11.glDisable((int)3042);
                                                                                }
                                                                                break block261;
                                                                            }
                                                                            if (!input.equalsIgnoreCase("Violence")) break block285;
                                                                            AtomicInteger count = new AtomicInteger(0);
                                                                            AtomicInteger rainbow = new AtomicInteger(0);
                                                                            AtomicInteger index = new AtomicInteger(0);
                                                                            AtomicInteger count2 = new AtomicInteger(0);
                                                                            AtomicInteger index2 = new AtomicInteger(0);
                                                                            AtomicDouble atomicDouble = new AtomicDouble(0.0);
                                                                            GL11.glEnable((int)3042);
                                                                            GL11.glBlendFunc((int)770, (int)771);
                                                                            Client.INSTANCE.moduleManager.modules.stream().sorted(Comparator.comparingInt(value -> -Themes.mc.fontRendererObj.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.GRAY, String.valueOf(value.getMode()) + " "})))).filter(Module::isState).forEach(m -> {
                                                                                float wSet = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()})) - 3;
                                                                                float wSet2 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()})) - 5;
                                                                                if (!Themes.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                                                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                                    Gui.drawRect(wSet2 + 3.0f, offset2.floatValue() + 2.6f, sr.getScaledWidth(), offset2.floatValue() + 12.0f, new Color(0, 0, 0, 100).getRGB());
                                                                                    Themes.mc.fontRendererObj.drawStringWithShadow(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet + 2.0f, offset2.floatValue() + 2.0f, m.oneTimeColor);
                                                                                    offset2.getAndAdd(9.5);
                                                                                    index2.getAndAdd(1);
                                                                                }
                                                                                float offset = 0.0f;
                                                                                for (Module mod : Client.INSTANCE.moduleManager.modules) {
                                                                                    if (!mod.isState() && mod.anim.getValue() == 0.0f) continue;
                                                                                    if (!Themes.mc.gameSettings.showDebugInfo && mod.name != "HUD" && mod.name != "Animations" && mod.name != "") {
                                                                                        rainbow.addAndGet(1);
                                                                                        count.getAndAdd(1);
                                                                                        index.getAndAdd(1);
                                                                                        offset += m.anim2.getValue();
                                                                                    }
                                                                                    GL11.glDisable((int)3042);
                                                                                }
                                                                            });
                                                                            break block261;
                                                                        }
                                                                        if (!input.equalsIgnoreCase("Skid")) break block286;
                                                                        int count = 0;
                                                                        int rainbow = 0;
                                                                        int index = 0;
                                                                        boolean count2 = false;
                                                                        int index2 = 0;
                                                                        float f31 = 0.0f;
                                                                        GL11.glEnable((int)3042);
                                                                        GL11.glBlendFunc((int)770, (int)771);
                                                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.Skid.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                        for (Module m29 : Client.INSTANCE.moduleManager.modules) {
                                                                            if (!m29.isState()) continue;
                                                                            float wSet34 = sr.getScaledWidth() - Client.INSTANCE.Skid.getStringWidth(m29.getMode().isEmpty() ? m29.name : String.format("%s %s%s", new Object[]{m29.name, EnumChatFormatting.WHITE, String.valueOf(m29.getMode()) + " "})) - 4;
                                                                            float wSet217 = sr.getScaledWidth() - Client.INSTANCE.Skid.getStringWidth(m29.getMode().isEmpty() ? m29.name : String.format("%s %s%s", new Object[]{m29.name, EnumChatFormatting.WHITE, String.valueOf(m29.getMode()) + " "})) - 5;
                                                                            if (Themes.mc.gameSettings.showDebugInfo || m29.name == "HUD" || m29.name == "Animations" || m29.name == "") continue;
                                                                            Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                            Gui.drawRect(wSet217 + 2.0f, f31, sr.getScaledWidth(), f31 + 10.0f, new Color(0, 0, 0, 120).getRGB());
                                                                            Client.INSTANCE.Skid.drawStringWithShadow(m29.getMode().isEmpty() ? m29.name : String.format("%s %s%s", new Object[]{m29.name, EnumChatFormatting.WHITE, m29.getMode()}), wSet34 + 0.5f, f31 + 0.5f, m29.oneTimeColor);
                                                                            Gui.drawRect(wSet217 + 1.5f, f31, wSet217 + 0.5f, f31 + 10.0f, -1);
                                                                            f31 += 10.0f;
                                                                            ++index2;
                                                                        }
                                                                        float offset = 0.0f;
                                                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                            if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                            module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.Skid.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                            Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                            Client.INSTANCE.Skid.getClass();
                                                                            animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                            float wSet35 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                            if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                                ++rainbow;
                                                                                ++count;
                                                                                ++index;
                                                                                offset += module.anim2.getValue();
                                                                            }
                                                                            GL11.glDisable((int)3042);
                                                                        }
                                                                        break block261;
                                                                    }
                                                                    if (!input.equalsIgnoreCase("ZeroDay")) break block287;
                                                                    int count = 0;
                                                                    int rainbow = 0;
                                                                    int index = 0;
                                                                    boolean count2 = false;
                                                                    int index2 = 0;
                                                                    float f32 = 0.0f;
                                                                    GL11.glEnable((int)3042);
                                                                    GL11.glBlendFunc((int)770, (int)771);
                                                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.verdana.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                    for (Module m31 : Client.INSTANCE.moduleManager.modules) {
                                                                        if (!m31.isState()) continue;
                                                                        float wSet36 = sr.getScaledWidth() - Client.INSTANCE.verdana.getStringWidth(m31.getMode().isEmpty() ? m31.name : String.format("%s %s%s", new Object[]{m31.name, EnumChatFormatting.WHITE, String.valueOf(m31.getMode()) + " "})) - 2;
                                                                        float wSet218 = sr.getScaledWidth() - Client.INSTANCE.verdana.getStringWidth(m31.getMode().isEmpty() ? m31.name : String.format("%s %s%s", new Object[]{m31.name, EnumChatFormatting.WHITE, String.valueOf(m31.getMode()) + " "})) - 5;
                                                                        if (Themes.mc.gameSettings.showDebugInfo || m31.name == "HUD" || m31.name == "Animations" || m31.name == "") continue;
                                                                        Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                        Gui.drawRect(wSet218 + 2.0f, f32 + 1.0f, sr.getScaledWidth(), f32 + 11.0f, new Color(0, 0, 0, 110).getRGB());
                                                                        Client.INSTANCE.verdana.drawStringWithShadow(m31.getMode().isEmpty() ? m31.name : String.format("%s %s%s", new Object[]{m31.name, EnumChatFormatting.GRAY, m31.getMode()}), wSet36 + 0.5f, f32 + 1.0f, Themes.rainbow((int)f32 * 8));
                                                                        Gui.drawRect(wSet218 + 2.5f, f32 + 1.0f, wSet218 + 0.5f, f32 + 11.0f, Themes.rainbow((int)f32 * 8));
                                                                        f32 += 10.0f;
                                                                        ++index2;
                                                                    }
                                                                    float offset = 0.0f;
                                                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.verdana.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                        Client.INSTANCE.verdana.getClass();
                                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                        float wSet37 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                            ++rainbow;
                                                                            ++count;
                                                                            ++index;
                                                                            offset += module.anim2.getValue();
                                                                        }
                                                                        GL11.glDisable((int)3042);
                                                                    }
                                                                    break block261;
                                                                }
                                                                if (!input.equalsIgnoreCase("Vanta")) break block288;
                                                                int count = 0;
                                                                int rainbow = 0;
                                                                int index = 0;
                                                                boolean count2 = false;
                                                                int index2 = 0;
                                                                float f33 = 0.0f;
                                                                GL11.glEnable((int)3042);
                                                                GL11.glBlendFunc((int)770, (int)771);
                                                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                                for (Module m33 : Client.INSTANCE.moduleManager.modules) {
                                                                    if (!m33.isState()) continue;
                                                                    float wSet38 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(m33.getMode().isEmpty() ? m33.name : String.format("%s %s%s", new Object[]{m33.name, EnumChatFormatting.GRAY, String.valueOf(m33.getMode()) + " "})) - 3;
                                                                    float wSet219 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(m33.getMode().isEmpty() ? m33.name : String.format("%s %s%s", new Object[]{m33.name, EnumChatFormatting.GRAY, String.valueOf(m33.getMode()) + " "})) - 5;
                                                                    if (Themes.mc.gameSettings.showDebugInfo || m33.name == "HUD" || m33.name == "Animations" || m33.name == "") continue;
                                                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                    Gui.drawRect(wSet219 - 4.0f, f33, sr.getScaledWidth(), f33 + 10.0f, new Color(30, 30, 30, 120).getRGB());
                                                                    if (((CheckBox)this.getSetting((String)"VantaShadow").getSetting()).state) {
                                                                        if (((CheckBox)this.getSetting((String)"VantaMagenta").getSetting()).state) {
                                                                            Client.INSTANCE.unicodeBasicFontRenderer5.drawString(m33.getMode().isEmpty() ? m33.name : String.format("%s %s%s", new Object[]{m33.name, EnumChatFormatting.GRAY, m33.getMode()}), wSet38 - 4.0f, f33 - 1.0f, Color.magenta.getRGB());
                                                                        } else {
                                                                            Client.INSTANCE.unicodeBasicFontRenderer5.drawStringWithShadow2(m33.getMode().isEmpty() ? m33.name : String.format("%s %s%s", new Object[]{m33.name, EnumChatFormatting.GRAY, m33.getMode()}), wSet38 - 4.0f, f33 - 1.0f, -1);
                                                                        }
                                                                    } else {
                                                                        Client.INSTANCE.unicodeBasicFontRenderer5.drawString(m33.getMode().isEmpty() ? m33.name : String.format("%s %s%s", new Object[]{m33.name, EnumChatFormatting.GRAY, m33.getMode()}), wSet38 - 4.0f, f33 - 1.0f, -1);
                                                                    }
                                                                    if (((CheckBox)this.getSetting((String)"VantaMagenta").getSetting()).state) {
                                                                        Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f33, sr.getScaledWidth(), f33 + 10.0f, m33.getMode().equalsIgnoreCase("") || m33.getMode() == null || m33.getMode().isEmpty() ? Color.magenta.getRGB() : Color.magenta.getRGB());
                                                                    } else {
                                                                        Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f33, sr.getScaledWidth(), f33 + 10.0f, m33.getMode().equalsIgnoreCase("") || m33.getMode() == null || m33.getMode().isEmpty() ? Themes.rainbowSigma((int)f33 * 9) : Themes.rainbowSigma((int)f33 * 9));
                                                                    }
                                                                    f33 += 10.0f;
                                                                    ++index2;
                                                                }
                                                                float offset = 0.0f;
                                                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                    Client.INSTANCE.unicodeBasicFontRenderer5.getClass();
                                                                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                    float wSet39 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                    if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                        ++rainbow;
                                                                        ++count;
                                                                        ++index;
                                                                        offset += module.anim2.getValue();
                                                                    }
                                                                    GL11.glDisable((int)3042);
                                                                }
                                                                break block261;
                                                            }
                                                            if (!input.equalsIgnoreCase("Test")) break block289;
                                                            int count = 0;
                                                            int rainbow = 0;
                                                            int index = 0;
                                                            boolean count2 = false;
                                                            int index2 = 0;
                                                            float f34 = 0.0f;
                                                            GL11.glEnable((int)3042);
                                                            GL11.glBlendFunc((int)770, (int)771);
                                                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.fluxTabGuiFont.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                            for (Module m35 : Client.INSTANCE.moduleManager.modules) {
                                                                if (!m35.isState()) continue;
                                                                float wSet40 = sr.getScaledWidth() - Client.INSTANCE.fluxTabGuiFont.getStringWidth(m35.getMode().isEmpty() ? m35.name : String.format("%s %s%s", new Object[]{m35.name, EnumChatFormatting.GRAY, String.valueOf(m35.getMode()) + " "})) - 3;
                                                                float wSet220 = sr.getScaledWidth() - Client.INSTANCE.fluxTabGuiFont.getStringWidth(m35.getMode().isEmpty() ? m35.name : String.format("%s %s%s", new Object[]{m35.name, EnumChatFormatting.GRAY, String.valueOf(m35.getMode()) + " "})) - 5;
                                                                if (Themes.mc.gameSettings.showDebugInfo || m35.name == "HUD" || m35.name == "Animations" || m35.name == "") continue;
                                                                Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                                Gui.drawRect(wSet220 - 4.0f, f34, sr.getScaledWidth(), f34 + 10.0f, new Color(0, 0, 0, 170).getRGB());
                                                                Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow(m35.getMode().isEmpty() ? m35.name : String.format("%s %s%s", new Object[]{m35.name, EnumChatFormatting.GRAY, m35.getMode()}), wSet40 - 4.0f, f34 - 2.5f, Themes.rainbowSigma((int)f34 * 9));
                                                                Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f34, sr.getScaledWidth(), f34 + 10.0f, m35.getMode().equalsIgnoreCase("") || m35.getMode() == null || m35.getMode().isEmpty() ? Themes.rainbowSigma((int)f34 * 9) : Themes.rainbowSigma((int)f34 * 9));
                                                                f34 += 10.0f;
                                                                ++index2;
                                                                ++index;
                                                            }
                                                            float offset = 0.0f;
                                                            for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer7.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                                Client.INSTANCE.arial.getClass();
                                                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                                float wSet41 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                                if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                    ++rainbow;
                                                                    ++count;
                                                                    ++index;
                                                                    offset += module.anim2.getValue();
                                                                }
                                                                GL11.glDisable((int)3042);
                                                            }
                                                            break block261;
                                                        }
                                                        if (!input.equalsIgnoreCase("Ambien")) break block290;
                                                        int count = 0;
                                                        int rainbow = 0;
                                                        int index = 0;
                                                        boolean count2 = false;
                                                        int index2 = 0;
                                                        float f35 = 0.0f;
                                                        GL11.glEnable((int)3042);
                                                        GL11.glBlendFunc((int)770, (int)771);
                                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.ambien.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                        for (Module m37 : Client.INSTANCE.moduleManager.modules) {
                                                            if (!m37.isState()) continue;
                                                            float wSet42 = sr.getScaledWidth() - Client.INSTANCE.ambien.getStringWidth(m37.getMode().isEmpty() ? m37.name : String.format("%s %s%s", new Object[]{m37.name, EnumChatFormatting.GRAY, String.valueOf(m37.getMode()) + " "})) - 3;
                                                            float wSet221 = sr.getScaledWidth() - Client.INSTANCE.ambien.getStringWidth(m37.getMode().isEmpty() ? m37.name : String.format("%s %s%s", new Object[]{m37.name, EnumChatFormatting.GRAY, String.valueOf(m37.getMode()) + " "})) - 5;
                                                            if (Themes.mc.gameSettings.showDebugInfo || m37.name == "HUD" || m37.name == "Animations" || m37.name == "") continue;
                                                            Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                            Gui.drawRect(wSet221 - 3.0f, f35 + 2.0f, sr.getScaledWidth(), f35 + 13.0f, new Color(0, 0, 0, 220).getRGB());
                                                            Client.INSTANCE.ambien.drawStringWithShadow(m37.getMode().isEmpty() ? m37.name : String.format("%s %s%s", new Object[]{m37.name, EnumChatFormatting.GRAY, m37.getMode()}), wSet42 - 4.0f, f35, Themes.rainbow((int)f35 * 9));
                                                            Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f35, sr.getScaledWidth(), f35 + 12.0f, Themes.rainbow((int)f35 * 9));
                                                            f35 += 11.0f;
                                                            ++index2;
                                                            ++index;
                                                        }
                                                        float offset = 0.0f;
                                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                            if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                            module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.ambien.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                            Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                            Client.INSTANCE.ambien.getClass();
                                                            animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                            float wSet43 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                            if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                                ++rainbow;
                                                                ++count;
                                                                ++index;
                                                                offset += module.anim2.getValue();
                                                            }
                                                            GL11.glDisable((int)3042);
                                                        }
                                                        break block261;
                                                    }
                                                    if (!input.equalsIgnoreCase("Clean")) break block291;
                                                    int count = 0;
                                                    int rainbow = 0;
                                                    int index = 0;
                                                    boolean count2 = false;
                                                    int index2 = 0;
                                                    float f36 = 0.0f;
                                                    GL11.glEnable((int)3042);
                                                    GL11.glBlendFunc((int)770, (int)771);
                                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                                    for (Module m39 : Client.INSTANCE.moduleManager.modules) {
                                                        if (!m39.isState()) continue;
                                                        float wSet44 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m39.getMode().isEmpty() ? m39.name : String.format("%s %s%s", new Object[]{m39.name, EnumChatFormatting.GRAY, String.valueOf(m39.getMode()) + " "})) - 3;
                                                        float wSet222 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m39.getMode().isEmpty() ? m39.name : String.format("%s %s%s", new Object[]{m39.name, EnumChatFormatting.GRAY, String.valueOf(m39.getMode()) + " "})) - 5;
                                                        if (Themes.mc.gameSettings.showDebugInfo || m39.name == "HUD" || m39.name == "Animations" || m39.name == "") continue;
                                                        Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                                        Gui.drawRect(wSet222 - 4.0f, f36, sr.getScaledWidth(), f36 + 10.0f, new Color(0, 0, 0, 180).getRGB());
                                                        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m39.getMode().isEmpty() ? m39.name : String.format("%s %s%s", new Object[]{m39.name, EnumChatFormatting.GRAY, m39.getMode()}), wSet44 - 5.0f, f36 - 1.0f, -1);
                                                        Gui.drawRect((float)sr.getScaledWidth() - 2.0f, f36, sr.getScaledWidth(), f36 + 10.0f, -1);
                                                        f36 += 10.0f;
                                                        ++index2;
                                                    }
                                                    float offset = 0.0f;
                                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                        Client.INSTANCE.arial.getClass();
                                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                        float wSet45 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                            ++rainbow;
                                                            ++count;
                                                            ++index;
                                                            offset += module.anim2.getValue();
                                                        }
                                                        GL11.glDisable((int)3042);
                                                    }
                                                    break block261;
                                                }
                                                if (!input.equalsIgnoreCase("Test2")) break block292;
                                                int count = 0;
                                                int index = 0;
                                                int count2 = 0;
                                                int index2 = 0;
                                                GL11.glEnable((int)3042);
                                                GL11.glBlendFunc((int)770, (int)771);
                                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(value.name)));
                                                for (Module m41 : Client.INSTANCE.moduleManager.modules) {
                                                    if (!m41.isState()) continue;
                                                    float offset = count2 * Client.INSTANCE.unicodeBasicFontRenderer5.FONT_HEIGHT;
                                                    f9 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(m41.name) - 3;
                                                    float wSet223 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(m41.name) - 5;
                                                    if (Themes.mc.gameSettings.showDebugInfo || m41.name == "HUD" || m41.name == "Animations" || m41.name == "") continue;
                                                    ++count2;
                                                    ++index2;
                                                }
                                                float offset = 0.0f;
                                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                    Client.INSTANCE.unicodeBasicFontRenderer5.getClass();
                                                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                    f9 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                    if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                        Gui.drawRect(f9 - 7.0f, offset + 4.0f, sr.getScaledWidth() - 3, offset + 13.0f, new Color(0, 0, 0, 130).getRGB());
                                                        Client.INSTANCE.unicodeBasicFontRenderer5.drawString(module.name, f9 - 5.0f, (int)offset + 3, Themes.getGradientOffset(new Color(0, 255, 0), new Color(0, 64, 0), (double)index / 5.4).getRGB());
                                                        ++count;
                                                        ++index;
                                                        offset += module.anim2.getValue();
                                                        ++index2;
                                                    }
                                                    GL11.glDisable((int)3042);
                                                }
                                                break block261;
                                            }
                                            if (!input.equalsIgnoreCase("Skeet")) break block293;
                                            int count = 0;
                                            int index = 0;
                                            int count2 = 0;
                                            int index2 = 0;
                                            GL11.glEnable((int)3042);
                                            GL11.glBlendFunc((int)770, (int)771);
                                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(value.name)));
                                            for (Module m43 : Client.INSTANCE.moduleManager.modules) {
                                                if (!m43.isState()) continue;
                                                float offset = count2 * Client.INSTANCE.unicodeBasicFontRenderer5.FONT_HEIGHT;
                                                f8 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(m43.name) - 3;
                                                float wSet224 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(m43.name) - 5;
                                                if (Themes.mc.gameSettings.showDebugInfo || m43.name == "HUD" || m43.name == "Animations" || m43.name == "") continue;
                                                ++count2;
                                                ++index2;
                                            }
                                            float offset = 0.0f;
                                            for (Module module : Client.INSTANCE.moduleManager.modules) {
                                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                                Client.INSTANCE.unicodeBasicFontRenderer5.getClass();
                                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                                f8 = (float)sr.getScaledWidth() - module.anim.getValue();
                                                if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                    Gui.drawRect(f8 - 7.0f, offset + 4.0f, sr.getScaledWidth() - 3, offset + 13.0f, new Color(0, 0, 0, 100).getRGB());
                                                    Client.INSTANCE.unicodeBasicFontRenderer5.drawStringWithShadow(module.name, f8 - 5.0f, (int)offset + 3, Themes.rainbow((int)offset * 15));
                                                    ++count;
                                                    ++index;
                                                    offset += module.anim2.getValue();
                                                    ++index2;
                                                }
                                                GL11.glDisable((int)3042);
                                            }
                                            break block261;
                                        }
                                        if (!input.equalsIgnoreCase("Hero")) break block294;
                                        int count = 0;
                                        int index = 0;
                                        int count2 = 0;
                                        int index2 = 0;
                                        GL11.glEnable((int)3042);
                                        GL11.glBlendFunc((int)770, (int)771);
                                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(value.name)));
                                        for (Module m44 : Client.INSTANCE.moduleManager.modules) {
                                            if (!m44.isState()) continue;
                                            float offset = count2 * Client.INSTANCE.unicodeBasicFontRenderer4.FONT_HEIGHT;
                                            f7 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m44.name) - 3;
                                            float wSet225 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m44.name) - 5;
                                            if (Themes.mc.gameSettings.showDebugInfo || m44.name == "HUD" || m44.name == "Animations" || m44.name == "") continue;
                                            ++count2;
                                            ++index2;
                                        }
                                        float offset = 0.0f;
                                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                                            if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                            module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                            Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                            Client.INSTANCE.unicodeBasicFontRenderer4.getClass();
                                            animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                            f7 = (float)sr.getScaledWidth() - module.anim.getValue();
                                            if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                                Gui.drawRect(f7 - 4.0f, offset + 2.0f, sr.getScaledWidth() - 2, offset + 11.0f, new Color(0, 0, 0, 125).getRGB());
                                                Gui.drawRect((float)sr.getScaledWidth() - 2.0f, offset + 2.5f, sr.getScaledWidth(), offset + 11.0f, module.getColor().getRGB());
                                                Client.INSTANCE.unicodeBasicFontRenderer4.drawString(module.name, f7 - 3.0f, (int)offset, module.getColor().getRGB());
                                                Gui.drawRect(f7 - 4.0f, offset + 2.0f, sr.getScaledWidth() - 2, offset + 11.0f, new Color(0, 0, 0, 110).getRGB());
                                                ++count;
                                                ++index;
                                                offset += module.anim2.getValue();
                                            }
                                            GL11.glDisable((int)3042);
                                        }
                                        break block261;
                                    }
                                    if (!input.equalsIgnoreCase("Novoline2.0")) break block295;
                                    int count = 0;
                                    int index = 0;
                                    int count2 = 0;
                                    int index2 = 0;
                                    GL11.glEnable((int)3042);
                                    GL11.glBlendFunc((int)770, (int)771);
                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.arrial.getStringWidth(value.name)));
                                    for (Module m46 : Client.INSTANCE.moduleManager.modules) {
                                        if (!m46.isState()) continue;
                                        float offset = count2 * Client.INSTANCE.arrial.FONT_HEIGHT;
                                        f6 = sr.getScaledWidth() - Client.INSTANCE.arrial.getStringWidth(m46.name) - 3;
                                        float wSet226 = sr.getScaledWidth() - Client.INSTANCE.arrial.getStringWidth(m46.name) - 5;
                                        if (Themes.mc.gameSettings.showDebugInfo || m46.name == "HUD" || m46.name == "Animations" || m46.name == "") continue;
                                        ++count2;
                                        ++index2;
                                    }
                                    float offset = 0.0f;
                                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.arrial.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                        Client.INSTANCE.arrial.getClass();
                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                        f6 = (float)sr.getScaledWidth() - module.anim.getValue();
                                        if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                            Gui.drawRect(f6 - 4.0f, offset, sr.getScaledWidth(), offset + 10.0f, Themes.getGradientOffset2(Color.blue, Color.magenta, (double)index / 20.02).getRGB());
                                            Gui.drawRect(f6 - 4.0f, offset, sr.getScaledWidth() - 2, offset + 10.0f, new Color(0, 0, 0, 200).getRGB());
                                            Client.INSTANCE.arrial.drawString(module.name, f6 - 3.0f, (int)offset - 2, Themes.getGradientOffset2(Color.blue, Color.magenta, (double)index / 20.02).getRGB());
                                            ++count;
                                            ++index;
                                            offset = (float)((double)offset + ((double)module.anim2.getValue() + 0.1));
                                        }
                                        GL11.glDisable((int)3042);
                                    }
                                    break block261;
                                }
                                if (!input.equalsIgnoreCase("Arial")) break block296;
                                int count = 0;
                                int index = 0;
                                int count2 = 0;
                                int index2 = 0;
                                GL11.glEnable((int)3042);
                                GL11.glBlendFunc((int)770, (int)771);
                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.name)));
                                for (Module m48 : Client.INSTANCE.moduleManager.modules) {
                                    if (!m48.isState()) continue;
                                    float offset = count2 * Client.INSTANCE.arial.FONT_HEIGHT;
                                    f5 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m48.name) - 3;
                                    float wSet227 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m48.name) - 5;
                                    if (Themes.mc.gameSettings.showDebugInfo || m48.name == "HUD" || m48.name == "Animations" || m48.name == "") continue;
                                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                                    float f37 = sr.getScaledWidth();
                                    Client.INSTANCE.arial.getClass();
                                    Client.blurHelper.blur2(wSet227, -1.0f, f37, offset + 9.0f + 5.5f, (float)Blur);
                                    ++count2;
                                    ++index2;
                                }
                                float offset = 0.0f;
                                for (Module module : Client.INSTANCE.moduleManager.modules) {
                                    block297: {
                                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                        Client.INSTANCE.arial.getClass();
                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                        f5 = (float)sr.getScaledWidth() - module.anim.getValue();
                                        if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block297;
                                        switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                            case "RED": {
                                                Client.INSTANCE.unicodeBasicFontRenderer.drawString(module.name, f5, (int)offset - 1, Themes.getGradientOffset(new Color(255, 0, 0), new Color(64, 0, 0), (double)index / 12.4).getRGB());
                                                break;
                                            }
                                            case "BLUE": {
                                                Client.INSTANCE.unicodeBasicFontRenderer.drawString(module.name, f5, (int)offset - 1, Themes.getGradientOffset(new Color(0, 37, 255), new Color(0, 16, 110), (double)index / 12.4).getRGB());
                                                break;
                                            }
                                            case "GREEN": {
                                                Client.INSTANCE.unicodeBasicFontRenderer.drawString(module.name, f5, (int)offset - 1, Themes.getGradientOffset(new Color(0, 255, 4), new Color(0, 54, 0), (double)index / 12.4).getRGB());
                                                break;
                                            }
                                            case "YELLOW": {
                                                Client.INSTANCE.unicodeBasicFontRenderer.drawString(module.name, f5, (int)offset - 1, Themes.getGradientOffset(new Color(230, 255, 0), new Color(98, 122, 1), (double)index / 12.4).getRGB());
                                                break;
                                            }
                                            case "Novoline": {
                                                Client.INSTANCE.unicodeBasicFontRenderer.drawString(module.name, f5, (int)offset - 1, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                                            }
                                        }
                                        ++count;
                                        ++index;
                                        offset += module.anim2.getValue();
                                    }
                                    GL11.glDisable((int)3042);
                                }
                                break block261;
                            }
                            if (!input.equalsIgnoreCase("Novoline")) break block299;
                            int count = 0;
                            int rainbow = 0;
                            int index = 0;
                            boolean count2 = false;
                            int index2 = 0;
                            float f38 = 0.0f;
                            GL11.glEnable((int)3042);
                            GL11.glBlendFunc((int)770, (int)771);
                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                            for (Module m50 : Client.INSTANCE.moduleManager.modules) {
                                if (!m50.isState()) continue;
                                float wSet46 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m50.getMode().isEmpty() ? m50.name : String.format("%s %s%s", new Object[]{m50.name, EnumChatFormatting.GRAY, String.valueOf(m50.getMode()) + " "})) - 3;
                                float wSet228 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m50.getMode().isEmpty() ? m50.name : String.format("%s %s%s", new Object[]{m50.name, EnumChatFormatting.GRAY, String.valueOf(m50.getMode()) + " "})) - 5;
                                if (Themes.mc.gameSettings.showDebugInfo || m50.name == "HUD" || m50.name == "Animations" || m50.name == "") continue;
                                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m50.getMode().isEmpty() ? m50.name : String.format("%s %s%s", new Object[]{m50.name, EnumChatFormatting.GRAY, m50.getMode()}), wSet46 - 5.0f, f38 + 1.0f, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                                f38 += 10.0f;
                                ++index2;
                                ++index;
                            }
                            float offset = 0.0f;
                            for (Module module : Client.INSTANCE.moduleManager.modules) {
                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                Client.INSTANCE.arial.getClass();
                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                                float wSet47 = (float)sr.getScaledWidth() - module.anim.getValue();
                                if (!Themes.mc.gameSettings.showDebugInfo && module.name != "HUD" && module.name != "Animations" && module.name != "") {
                                    ++rainbow;
                                    ++count;
                                    ++index;
                                    offset += module.anim2.getValue();
                                }
                                GL11.glDisable((int)3042);
                            }
                            break block261;
                        }
                        if (!input.equalsIgnoreCase("MC")) break block300;
                        int count = 0;
                        int index = 0;
                        int count2 = 0;
                        int index2 = 0;
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Themes.mc.fontRendererObj.getStringWidth(value.name)));
                        for (Module m52 : Client.INSTANCE.moduleManager.modules) {
                            if (!m52.isState()) continue;
                            float offset = count2 * Themes.mc.fontRendererObj.getFontHeight();
                            f4 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m52.name) - 3;
                            float wSet229 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m52.name) - 5;
                            if (Themes.mc.gameSettings.showDebugInfo || m52.name == "HUD" || m52.name == "Animations" || m52.name == "") continue;
                            Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                            Client.blurHelper.blur2(wSet229, -1.0f, sr.getScaledWidth(), offset + (float)Themes.mc.fontRendererObj.getFontHeight(), (float)Blur);
                            ++count2;
                            ++index2;
                        }
                        float offset = 0.0f;
                        for (Module module : Client.INSTANCE.moduleManager.modules) {
                            block301: {
                                if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                                module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Themes.mc.fontRendererObj.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                                module.anim2.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)Themes.mc.fontRendererObj.getFontHeight()).setSpeed(200.0f).setReversed(!module.isState()).update();
                                f4 = (float)sr.getScaledWidth() - module.anim.getValue();
                                if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block301;
                                switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                    case "RED": {
                                        Themes.mc.fontRendererObj.drawString(module.name, (int)f4, (int)offset - 1, Themes.getGradientOffset(new Color(255, 0, 0), new Color(64, 0, 0), (double)index / 12.4).getRGB());
                                        break;
                                    }
                                    case "BLUE": {
                                        Themes.mc.fontRendererObj.drawString(module.name, (int)f4, (int)offset - 1, Themes.getGradientOffset(new Color(0, 37, 255), new Color(0, 16, 110), (double)index / 12.4).getRGB());
                                        break;
                                    }
                                    case "GREEN": {
                                        Themes.mc.fontRendererObj.drawString(module.name, (int)f4, (int)offset - 1, Themes.getGradientOffset(new Color(0, 255, 4), new Color(0, 54, 0), (double)index / 12.4).getRGB());
                                        break;
                                    }
                                    case "YELLOW": {
                                        Themes.mc.fontRendererObj.drawString(module.name, (int)f4, (int)offset - 1, Themes.getGradientOffset(new Color(230, 255, 0), new Color(98, 122, 1), (double)index / 12.4).getRGB());
                                        break;
                                    }
                                    case "Novoline": {
                                        Themes.mc.fontRendererObj.drawString(module.name, f4, (int)offset - 1, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                                    }
                                }
                                ++count;
                                ++index;
                                offset += module.anim2.getValue();
                            }
                            GL11.glDisable((int)3042);
                        }
                        break block261;
                    }
                    if (!input.equalsIgnoreCase("MC-Shadow")) break block303;
                    int count = 0;
                    int index = 0;
                    int count2 = 0;
                    int index2 = 0;
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Themes.mc.fontRendererObj.getStringWidth(value.name)));
                    for (Module m54 : Client.INSTANCE.moduleManager.modules) {
                        if (!m54.isState()) continue;
                        float offset = count2 * Themes.mc.fontRendererObj.getFontHeight();
                        f3 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m54.name) - 3;
                        float wSet230 = sr.getScaledWidth() - Themes.mc.fontRendererObj.getStringWidth(m54.name) - 5;
                        if (Themes.mc.gameSettings.showDebugInfo || m54.name == "HUD" || m54.name == "Animations" || m54.name == "") continue;
                        Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                        Client.blurHelper.blur2(wSet230, -1.0f, sr.getScaledWidth(), offset + (float)Themes.mc.fontRendererObj.getFontHeight(), (float)Blur);
                        ++count2;
                        ++index2;
                    }
                    float offset = 0.0f;
                    for (Module module : Client.INSTANCE.moduleManager.modules) {
                        block304: {
                            if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                            module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Themes.mc.fontRendererObj.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                            module.anim2.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)Themes.mc.fontRendererObj.getFontHeight()).setSpeed(200.0f).setReversed(!module.isState()).update();
                            f3 = (float)sr.getScaledWidth() - module.anim.getValue();
                            if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block304;
                            switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                                case "RED": {
                                    Themes.mc.fontRendererObj.drawStringWithShadow(module.name, (int)f3, (int)offset - 1, Themes.getGradientOffset(new Color(255, 0, 0), new Color(64, 0, 0), (double)index / 12.4).getRGB());
                                    break;
                                }
                                case "BLUE": {
                                    Themes.mc.fontRendererObj.drawStringWithShadow(module.name, (int)f3, (int)offset - 1, Themes.getGradientOffset(new Color(0, 37, 255), new Color(0, 16, 110), (double)index / 12.4).getRGB());
                                    break;
                                }
                                case "GREEN": {
                                    Themes.mc.fontRendererObj.drawStringWithShadow(module.name, (int)f3, (int)offset - 1, Themes.getGradientOffset(new Color(0, 255, 4), new Color(0, 54, 0), (double)index / 12.4).getRGB());
                                    break;
                                }
                                case "YELLOW": {
                                    Themes.mc.fontRendererObj.drawStringWithShadow(module.name, (int)f3, (int)offset - 1, Themes.getGradientOffset(new Color(230, 255, 0), new Color(98, 122, 1), (double)index / 12.4).getRGB());
                                    break;
                                }
                                case "Novoline": {
                                    Themes.mc.fontRendererObj.drawStringWithShadow(module.name, f3, (int)offset - 1, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                                }
                            }
                            ++count;
                            ++index;
                            offset += module.anim2.getValue();
                        }
                        GL11.glDisable((int)3042);
                    }
                    break block261;
                }
                if (!input.equalsIgnoreCase("Roboto-Thin")) break block306;
                int count = 0;
                int index = 0;
                int count2 = 0;
                int index2 = 0;
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(value.name)));
                for (Module m55 : Client.INSTANCE.moduleManager.modules) {
                    if (!m55.isState()) continue;
                    float offset = count2 * Client.INSTANCE.arial.FONT_HEIGHT;
                    f2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m55.name) - 3;
                    float wSet231 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m55.name) - 5;
                    if (Themes.mc.gameSettings.showDebugInfo || m55.name == "HUD" || m55.name == "Animations" || m55.name == "") continue;
                    Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                    float f39 = sr.getScaledWidth();
                    Client.INSTANCE.arial.getClass();
                    Client.blurHelper.blur2(wSet231, -1.0f, f39, offset + 9.0f + 5.5f, (float)Blur);
                    ++count2;
                    ++index2;
                }
                float offset = 0.0f;
                for (Module module : Client.INSTANCE.moduleManager.modules) {
                    block307: {
                        if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                        module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                        Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                        Client.INSTANCE.arial.getClass();
                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                        f2 = (float)sr.getScaledWidth() - module.anim.getValue();
                        if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block307;
                        switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                            case "RED": {
                                Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(module.name, f2, (int)offset - 1, Themes.getGradientOffset(new Color(255, 0, 0), new Color(64, 0, 0), (double)index / 12.4).getRGB());
                                break;
                            }
                            case "BLUE": {
                                Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(module.name, f2, (int)offset - 1, Themes.getGradientOffset(new Color(0, 37, 255), new Color(0, 16, 110), (double)index / 12.4).getRGB());
                                break;
                            }
                            case "GREEN": {
                                Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(module.name, f2, (int)offset - 1, Themes.getGradientOffset(new Color(0, 255, 4), new Color(0, 54, 0), (double)index / 12.4).getRGB());
                                break;
                            }
                            case "YELLOW": {
                                Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(module.name, f2, (int)offset - 1, Themes.getGradientOffset(new Color(230, 255, 0), new Color(98, 122, 1), (double)index / 12.4).getRGB());
                                break;
                            }
                            case "Novoline": {
                                Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(module.name, f2, (int)offset - 1, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                            }
                        }
                        ++count;
                        ++index;
                        offset += module.anim2.getValue();
                    }
                    GL11.glDisable((int)3042);
                }
                break block261;
            }
            if (!input.equalsIgnoreCase("Arial-Shadow")) break block261;
            int count = 0;
            int index = 0;
            int count2 = 0;
            int index2 = 0;
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.name)));
            for (Module m57 : Client.INSTANCE.moduleManager.modules) {
                if (!m57.isState()) continue;
                float offset = count2 * Client.INSTANCE.arial.FONT_HEIGHT;
                f = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m57.name) - 3;
                float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m57.name) - 5;
                if (Themes.mc.gameSettings.showDebugInfo || m57.name == "HUD" || m57.name == "Animations" || m57.name == "") continue;
                Blur = ((Slider)this.getSetting((String)"BlurSt\u00e4rke").getSetting()).curValue;
                float f40 = sr.getScaledWidth();
                Client.INSTANCE.arial.getClass();
                Client.blurHelper.blur2(wSet2, -1.0f, f40, offset + 9.0f + 5.5f, (float)Blur);
                ++count2;
                ++index2;
            }
            float offset = 0.0f;
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                block309: {
                    if (!module.isState() && module.anim.getValue() == 0.0f) continue;
                    module.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name) + 3)).setSpeed(200.0f).setReversed(!module.isState()).update();
                    Animate animate = module.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                    Client.INSTANCE.arial.getClass();
                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!module.isState()).update();
                    f = (float)sr.getScaledWidth() - module.anim.getValue();
                    if (Themes.mc.gameSettings.showDebugInfo || module.name == "HUD" || module.name == "Animations" || module.name == "") break block309;
                    switch (((DropdownBox)this.getSetting((String)"ArrayColor").getSetting()).curOption) {
                        case "RED": {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(module.name, f, (int)offset - 1, Themes.getGradientOffset(new Color(255, 0, 0), new Color(64, 0, 0), (double)index / 12.4).getRGB());
                            break;
                        }
                        case "BLUE": {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(module.name, f, (int)offset - 1, Themes.getGradientOffset(new Color(0, 37, 255), new Color(0, 16, 110), (double)index / 12.4).getRGB());
                            break;
                        }
                        case "GREEN": {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(module.name, f, (int)offset - 1, Themes.getGradientOffset(new Color(0, 255, 4), new Color(0, 54, 0), (double)index / 12.4).getRGB());
                            break;
                        }
                        case "YELLOW": {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(module.name, f, (int)offset - 1, Themes.getGradientOffset(new Color(230, 255, 0), new Color(98, 122, 1), (double)index / 12.4).getRGB());
                            break;
                        }
                        case "Novoline": {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(module.name, f, (int)offset - 1, Themes.getGradientOffset(new Color(3, 81, 74, 255), new Color(5, 244, 222, 255), (double)index / 4.4).getRGB());
                        }
                    }
                    ++count;
                    ++index;
                    offset += module.anim2.getValue();
                }
                GL11.glDisable((int)3042);
            }
        }
    }

    public static Color getGradientOffset(Color color1, Color color2, double index) {
        double offs = (double)Math.abs(System.currentTimeMillis() / 13L) / 60.0 + index;
        if (offs > 1.0) {
            double left = offs % 1.0;
            int off = (int)offs;
            offs = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offs;
        int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offs);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offs);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offs);
        return new Color(redPart, greenPart, bluePart);
    }

    public static Color getGradientOffset2(Color color1, Color color2, double index) {
        double offs = (double)Math.abs(System.currentTimeMillis() / 50L) / 60.0 + index;
        if (offs > 1.0) {
            double left = offs % 1.0;
            int off = (int)offs;
            offs = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offs;
        int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offs);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offs);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offs);
        return new Color(redPart, greenPart, bluePart);
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + (long)delay) / 7L);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.9f, 1.0f).getRGB();
    }

    public static int rainbowSigma(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + (long)delay) / 9L);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.5f, 1.0f).getRGB();
    }

    public static void startScissor(float startX, float startY, float endX, float endY) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        float width = endX - startX;
        float height = endY - startY;
        assert (Minecraft.getMinecraft().currentScreen != null);
        GuiScreen cfr_ignored_0 = Minecraft.getMinecraft().currentScreen;
        float bottomY = (float)GuiScreen.height - endY;
        float factor = scaledResolution.getScaleFactor();
        float scissorX = startX * factor;
        float scissorY = bottomY * factor;
        float scissorWidth = width * factor;
        float scissorHeight = height * factor;
        GL11.glScissor((int)((int)scissorX), (int)((int)scissorY), (int)((int)scissorWidth), (int)((int)scissorHeight));
        GL11.glEnable((int)3089);
    }

    public static void stopScissor() {
        GL11.glDisable((int)3089);
    }

    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)((int)(x * (float)factor)), (int)((int)(((float)scale.getScaledHeight() - y2) * (float)factor)), (int)((int)((x2 - x) * (float)factor)), (int)((int)((y2 - y) * (float)factor)));
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }

    public static Color SkyRainbow(int counter, float bright, float st) {
        double d;
        double v1 = Math.ceil(System.currentTimeMillis() + (long)counter * 109L) / 5.0;
        return Color.getHSBColor((double)((float)(d / 360.0)) < 0.5 ? -((float)(v1 / 360.0)) : (float)((v1 %= 360.0) / 360.0), st, bright);
    }

    public final int getHeight() {
        int height = 0;
        for (Module module : Client.INSTANCE.moduleManager.modules) {
            if (!module.state) continue;
            height = (int)((float)height + module.anim2.getValue());
        }
        return height;
    }

    public static void drawImage(int x, int y, int width, int height, ResourceLocation resourceLocation) {
        mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

