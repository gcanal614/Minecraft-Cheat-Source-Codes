/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.lavache.anime.Animate
 *  fr.lavache.anime.Easing
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class ArrayList
extends Module {
    public static ArrayList INSTANCE;
    public static double Blur;
    public static double Alpha;
    public static double RainbowOffset;
    int i = 0;
    Module mod;
    boolean max = false;
    private String mod1 = "";

    public ArrayList() {
        super("Arraylist", 0, Module.Type.Visual, Color.blue);
        this.settings.add(new Setting("Rainbow", new CheckBox(true)));
        this.settings.add(new Setting("Backround", new CheckBox(true)));
        this.settings.add(new Setting("ModuleColor", new CheckBox(false)));
        this.settings.add(new Setting("SideWardsRect", new CheckBox(true)));
        this.settings.add(new Setting("Suffix", new CheckBox(true)));
        this.settings.add(new Setting("Alpha", new Slider(0.0, 255.0, 0.1, 200.0)));
        this.settings.add(new Setting("RainbowOffset", new Slider(1.0, 20.0, 0.1, 18.1)));
        this.settings.add(new Setting("Fonts", new DropdownBox("Roboto-Thin", new String[]{"Winter Insight", "Arial", "Roboto-Thin", "MC", "Ambien"})));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
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
        block164: {
            String input;
            ScaledResolution sr;
            block168: {
                block167: {
                    block166: {
                        block165: {
                            boolean cfr_ignored_0 = e instanceof EventTick;
                            if (!(e instanceof EventRender2D) || !e.isPre()) break block164;
                            sr = new ScaledResolution(mc);
                            input = ((DropdownBox)this.getSetting((String)"Fonts").getSetting()).curOption;
                            if (!input.equalsIgnoreCase("Winter Insight")) break block165;
                            if (!Client.INSTANCE.moduleManager.getModule("Themes").isState()) {
                                if (((CheckBox)this.getSetting((String)"Suffix").getSetting()).state) {
                                    int count = 0;
                                    int rainbow = 0;
                                    int index = 0;
                                    boolean count2 = false;
                                    int index2 = 0;
                                    float offset2 = 0.0f;
                                    GL11.glEnable((int)3042);
                                    GL11.glBlendFunc((int)770, (int)771);
                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                    for (Module m : Client.INSTANCE.moduleManager.modules) {
                                        if (!m.isState()) continue;
                                        float wSet = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 3;
                                        float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 5;
                                        if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                                        Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                                        RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                        if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                            if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                                Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, m.getColor().getRGB());
                                            }
                                        } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                            if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                                Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                            }
                                        } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, this.getColor2());
                                        }
                                        if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2 + 1.0f, offset2, sr.getScaledWidth(), offset2 + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                                        }
                                        if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                            Client.INSTANCE.unicodeBasicFontRenderer3.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, m.getColor().getRGB());
                                        } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                            Client.INSTANCE.unicodeBasicFontRenderer3.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                        } else {
                                            Client.INSTANCE.unicodeBasicFontRenderer3.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, this.getColor2());
                                        }
                                        offset2 += 10.0f;
                                        ++index2;
                                    }
                                    float offset = 0.0f;
                                    for (Module m : Client.INSTANCE.moduleManager.modules) {
                                        if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                                        m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                                        Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                        Client.INSTANCE.unicodeBasicFontRenderer3.getClass();
                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                                        float wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                                        if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                            ++rainbow;
                                            ++count;
                                            ++index;
                                            offset += m.anim2.getValue();
                                        }
                                        GL11.glDisable((int)3042);
                                    }
                                } else {
                                    float wSet;
                                    int count = 0;
                                    int index = 0;
                                    int count2 = 0;
                                    int index2 = 0;
                                    GL11.glEnable((int)3042);
                                    GL11.glBlendFunc((int)770, (int)771);
                                    Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(value.name)));
                                    for (Module m : Client.INSTANCE.moduleManager.modules) {
                                        if (!m.isState()) continue;
                                        float offset = count2 * Client.INSTANCE.arial.FONT_HEIGHT;
                                        wSet = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m.name) - 3;
                                        float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m.name) - 5;
                                        if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                                        if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                            if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                                Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, m.getColor().getRGB());
                                            }
                                        } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                            if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                                Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                            }
                                        } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, this.getColor2());
                                        }
                                        if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2 + 1.0f, offset, sr.getScaledWidth(), offset + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                                        }
                                        ++count2;
                                        ++index2;
                                    }
                                    float offset = 0.0f;
                                    for (Module m : Client.INSTANCE.moduleManager.modules) {
                                        if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                                        m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer3.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                                        Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                        Client.INSTANCE.arial.getClass();
                                        animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                                        wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                                        if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                            if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                                Client.INSTANCE.unicodeBasicFontRenderer3.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset - 1.0f, m.getColor().getRGB());
                                            } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                                Client.INSTANCE.unicodeBasicFontRenderer3.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset - 1.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                            } else {
                                                Client.INSTANCE.unicodeBasicFontRenderer3.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset - 1.0f, this.getColor2());
                                            }
                                            ++count;
                                            ++index;
                                            offset += m.anim2.getValue();
                                        }
                                        GL11.glDisable((int)3042);
                                    }
                                }
                            }
                            break block164;
                        }
                        if (!input.equalsIgnoreCase("MC")) break block166;
                        if (!Client.INSTANCE.moduleManager.getModule("Themes").isState()) {
                            if (((CheckBox)this.getSetting((String)"Suffix").getSetting()).state) {
                                int count = 0;
                                int rainbow = 0;
                                int index = 0;
                                boolean count2 = false;
                                int index2 = 0;
                                float offset2 = 0.0f;
                                GL11.glEnable((int)3042);
                                GL11.glBlendFunc((int)770, (int)771);
                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - ArrayList.mc.fontRendererObj.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                                for (Module m : Client.INSTANCE.moduleManager.modules) {
                                    if (!m.isState()) continue;
                                    float wSet = sr.getScaledWidth() - ArrayList.mc.fontRendererObj.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode())}));
                                    float wSet2 = sr.getScaledWidth() - ArrayList.mc.fontRendererObj.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode())})) - 2;
                                    if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                                    Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                                    RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, m.getColor().getRGB());
                                        }
                                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                        }
                                    } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, this.getColor2());
                                    }
                                    if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2 + 1.0f, offset2, sr.getScaledWidth(), offset2 + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                                    }
                                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                        ArrayList.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 + 2.0f, m.getColor().getRGB());
                                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                        ArrayList.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 + 2.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                    } else {
                                        ArrayList.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 + 2.0f, this.getColor2());
                                    }
                                    offset2 += 10.0f;
                                    ++index2;
                                }
                                float offset = 0.0f;
                                for (Module m : Client.INSTANCE.moduleManager.modules) {
                                    if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                                    m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(ArrayList.mc.fontRendererObj.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                                    m.anim2.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)ArrayList.mc.fontRendererObj.getFontHeight()).setSpeed(200.0f).setReversed(!m.isState()).update();
                                    float wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                                    if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                        ++rainbow;
                                        ++count;
                                        ++index;
                                        offset += m.anim2.getValue();
                                    }
                                    GL11.glDisable((int)3042);
                                }
                            } else {
                                float wSet;
                                int count = 0;
                                int index = 0;
                                int count2 = 0;
                                int index2 = 0;
                                GL11.glEnable((int)3042);
                                GL11.glBlendFunc((int)770, (int)771);
                                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -ArrayList.mc.fontRendererObj.getStringWidth(value.name)));
                                for (Module m : Client.INSTANCE.moduleManager.modules) {
                                    if (!m.isState()) continue;
                                    float offset = count2 * ArrayList.mc.fontRendererObj.getFontHeight();
                                    wSet = sr.getScaledWidth() - ArrayList.mc.fontRendererObj.getStringWidth(m.name);
                                    float wSet2 = sr.getScaledWidth() - ArrayList.mc.fontRendererObj.getStringWidth(m.name) - 5;
                                    if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2, offset + 1.0f, sr.getScaledWidth(), offset + 11.0f, m.getColor().getRGB());
                                        }
                                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                        RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                            Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                        }
                                    } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2, offset + 1.0f, sr.getScaledWidth(), offset + 11.0f, this.getColor2());
                                    }
                                    Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                                    if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2 + 1.0f, offset + 1.0f, sr.getScaledWidth(), offset + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                                    }
                                    ++count2;
                                    ++index2;
                                }
                                float offset = 0.0f;
                                for (Module m : Client.INSTANCE.moduleManager.modules) {
                                    if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                                    m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(ArrayList.mc.fontRendererObj.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                                    m.anim2.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)ArrayList.mc.fontRendererObj.getFontHeight()).setSpeed(200.0f).setReversed(!m.isState()).update();
                                    wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                                    if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                        if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                            ArrayList.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset + 2.0f, m.getColor().getRGB());
                                        } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                            RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                            ArrayList.mc.fontRendererObj.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset + 2.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                        } else {
                                            ArrayList.mc.fontRendererObj.drawStringWithShadow(m.name, wSet + 1.0f, (int)offset + 2, this.getColor2());
                                        }
                                        ++count;
                                        ++index;
                                        offset += m.anim2.getValue();
                                    }
                                    GL11.glDisable((int)3042);
                                }
                            }
                        }
                        break block164;
                    }
                    if (!input.equalsIgnoreCase("Roboto-Thin")) break block167;
                    if (!Client.INSTANCE.moduleManager.getModule("Themes").isState()) {
                        if (((CheckBox)this.getSetting((String)"Suffix").getSetting()).state) {
                            int count = 0;
                            int rainbow = 0;
                            int index = 0;
                            boolean count2 = false;
                            int index2 = 0;
                            float offset2 = 0.0f;
                            GL11.glEnable((int)3042);
                            GL11.glBlendFunc((int)770, (int)771);
                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                            for (Module m : Client.INSTANCE.moduleManager.modules) {
                                if (!m.isState()) continue;
                                float wSet = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 3;
                                float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 5;
                                if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                                Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                                RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                    if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, m.getColor().getRGB());
                                    }
                                } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                    if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                    }
                                } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, this.getColor2());
                                }
                                if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2 + 1.0f, offset2, sr.getScaledWidth(), offset2 + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                                }
                                if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                    Client.INSTANCE.unicodeBasicFontRenderer4.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, m.getColor().getRGB());
                                } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                    Client.INSTANCE.unicodeBasicFontRenderer4.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                } else {
                                    Client.INSTANCE.unicodeBasicFontRenderer4.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, this.getColor2());
                                }
                                offset2 += 10.0f;
                                ++index2;
                            }
                            float offset = 0.0f;
                            for (Module m : Client.INSTANCE.moduleManager.modules) {
                                if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                                m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                                Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                Client.INSTANCE.unicodeBasicFontRenderer4.getClass();
                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                                float wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                                if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                    ++rainbow;
                                    ++count;
                                    ++index;
                                    offset += m.anim2.getValue();
                                }
                                GL11.glDisable((int)3042);
                            }
                        } else {
                            float wSet;
                            int count = 0;
                            int index = 0;
                            int count2 = 0;
                            int index2 = 0;
                            GL11.glEnable((int)3042);
                            GL11.glBlendFunc((int)770, (int)771);
                            Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(value.name)));
                            for (Module m : Client.INSTANCE.moduleManager.modules) {
                                if (!m.isState()) continue;
                                float offset = count2 * Client.INSTANCE.unicodeBasicFontRenderer4.FONT_HEIGHT;
                                wSet = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m.name) - 3;
                                float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m.name) - 5;
                                if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                                if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                    if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, m.getColor().getRGB());
                                    }
                                } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                    RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                    if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                        Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                    }
                                } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, this.getColor2());
                                }
                                Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                                if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2 + 1.0f, offset, sr.getScaledWidth(), offset + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                                }
                                ++count2;
                                ++index2;
                            }
                            float offset = 0.0f;
                            for (Module m : Client.INSTANCE.moduleManager.modules) {
                                if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                                m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer4.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                                Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                                Client.INSTANCE.unicodeBasicFontRenderer4.getClass();
                                animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                                wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                                if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                        Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(m.name, wSet, (int)offset - 1, m.getColor().getRGB());
                                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                        RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                        Client.INSTANCE.unicodeBasicFontRenderer4.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset - 1.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                    } else {
                                        Client.INSTANCE.unicodeBasicFontRenderer4.drawStringWithShadow(m.name, wSet, (int)offset - 1, this.getColor2());
                                    }
                                    ++count;
                                    ++index;
                                    offset += m.anim2.getValue();
                                }
                                GL11.glDisable((int)3042);
                            }
                        }
                    }
                    break block164;
                }
                if (!input.equalsIgnoreCase("Ambien")) break block168;
                if (!Client.INSTANCE.moduleManager.getModule("Themes").isState()) {
                    if (((CheckBox)this.getSetting((String)"Suffix").getSetting()).state) {
                        int count = 0;
                        int rainbow = 0;
                        int index = 0;
                        boolean count2 = false;
                        int index2 = 0;
                        float offset2 = 0.0f;
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.ViolenceTabGUi.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                        for (Module m : Client.INSTANCE.moduleManager.modules) {
                            if (!m.isState()) continue;
                            float wSet = sr.getScaledWidth() - Client.INSTANCE.ViolenceTabGUi.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 3;
                            float wSet2 = sr.getScaledWidth() - Client.INSTANCE.ViolenceTabGUi.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 5;
                            if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                            Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                            RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                            if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, m.getColor().getRGB());
                                }
                            } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                                }
                            } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, this.getColor2());
                            }
                            if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                Gui.drawRect(wSet2 + 1.0f, offset2, sr.getScaledWidth(), offset2 + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                            }
                            if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                Client.INSTANCE.ViolenceTabGUi.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, m.getColor().getRGB());
                            } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                Client.INSTANCE.ViolenceTabGUi.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                            } else {
                                Client.INSTANCE.ViolenceTabGUi.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, this.getColor2());
                            }
                            offset2 += 10.0f;
                            ++index2;
                        }
                        float offset = 0.0f;
                        for (Module m : Client.INSTANCE.moduleManager.modules) {
                            if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                            m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.ViolenceTabGUi.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                            Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                            Client.INSTANCE.ViolenceTabGUi.getClass();
                            animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                            float wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                            if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                ++rainbow;
                                ++count;
                                ++index;
                                offset += m.anim2.getValue();
                            }
                            GL11.glDisable((int)3042);
                        }
                    } else {
                        float wSet;
                        int count = 0;
                        int index = 0;
                        int count2 = 0;
                        int index2 = 0;
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.ViolenceTabGUi.getStringWidth(value.name)));
                        for (Module m : Client.INSTANCE.moduleManager.modules) {
                            if (!m.isState()) continue;
                            float offset = count2 * Client.INSTANCE.ViolenceTabGUi.FONT_HEIGHT;
                            wSet = sr.getScaledWidth() - Client.INSTANCE.ViolenceTabGUi.getStringWidth(m.name) - 3;
                            float wSet2 = sr.getScaledWidth() - Client.INSTANCE.ViolenceTabGUi.getStringWidth(m.name) - 5;
                            if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                            if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, m.getColor().getRGB());
                                }
                            } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                    Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                }
                            } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, this.getColor2());
                            }
                            Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                            if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                                Gui.drawRect(wSet2 + 1.0f, offset, sr.getScaledWidth(), offset + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                            }
                            ++count2;
                            ++index2;
                        }
                        float offset = 0.0f;
                        for (Module m : Client.INSTANCE.moduleManager.modules) {
                            if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                            m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.ViolenceTabGUi.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                            Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                            Client.INSTANCE.ViolenceTabGUi.getClass();
                            animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                            wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                            if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                                if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                                    Client.INSTANCE.ViolenceTabGUi.drawStringWithShadow(m.name, wSet, (int)offset - 1, m.getColor().getRGB());
                                } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                                    RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                                    Client.INSTANCE.ViolenceTabGUi.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset - 1.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                                } else {
                                    Client.INSTANCE.ViolenceTabGUi.drawStringWithShadow(m.name, wSet, (int)offset - 1, this.getColor2());
                                }
                                ++count;
                                ++index;
                                offset += m.anim2.getValue();
                            }
                            GL11.glDisable((int)3042);
                        }
                    }
                }
                break block164;
            }
            if (!input.equalsIgnoreCase("Arial") || Client.INSTANCE.moduleManager.getModule("Themes").isState()) break block164;
            if (((CheckBox)this.getSetting((String)"Suffix").getSetting()).state) {
                int count = 0;
                int rainbow = 0;
                int index = 0;
                boolean count2 = false;
                int index2 = 0;
                float offset2 = 0.0f;
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.getMode().isEmpty() ? value.name : String.format("%s %s%s", new Object[]{value.name, EnumChatFormatting.WHITE, String.valueOf(value.getMode()) + " "}))));
                for (Module m : Client.INSTANCE.moduleManager.modules) {
                    if (!m.isState()) continue;
                    float wSet = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 3;
                    float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, String.valueOf(m.getMode()) + " "})) - 5;
                    if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                    Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                    RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                            Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, m.getColor().getRGB());
                        }
                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                            Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                        }
                    } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                        Gui.drawRect(wSet2, offset2, sr.getScaledWidth(), offset2 + 11.0f, this.getColor2());
                    }
                    if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                        Gui.drawRect(wSet2 + 1.0f, offset2, sr.getScaledWidth(), offset2 + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                    }
                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, m.getColor().getRGB());
                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, ArrayList.rainbow((int)offset2 * (int)RainbowOffset));
                    } else {
                        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset2 - 1.0f, this.getColor2());
                    }
                    offset2 += 10.0f;
                    ++index2;
                }
                float offset = 0.0f;
                for (Module m : Client.INSTANCE.moduleManager.modules) {
                    if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                    m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                    Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                    Client.INSTANCE.unicodeBasicFontRenderer.getClass();
                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                    float wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                    if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                        ++rainbow;
                        ++count;
                        ++index;
                        offset += m.anim2.getValue();
                    }
                    GL11.glDisable((int)3042);
                }
            } else {
                float wSet;
                int count = 0;
                int index = 0;
                int count2 = 0;
                int index2 = 0;
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                Client.INSTANCE.moduleManager.modules.sort(Comparator.comparingInt(value -> -Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(value.name)));
                for (Module m : Client.INSTANCE.moduleManager.modules) {
                    if (!m.isState()) continue;
                    float offset = count2 * Client.INSTANCE.unicodeBasicFontRenderer.FONT_HEIGHT;
                    wSet = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m.name) - 3;
                    float wSet2 = sr.getScaledWidth() - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m.name) - 5;
                    if (ArrayList.mc.gameSettings.showDebugInfo || m.name == "HUD" || m.name == "Animations" || m.name == "") continue;
                    if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                            Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, m.getColor().getRGB());
                        }
                    } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                        RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                        if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                            Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                        }
                    } else if (((CheckBox)this.getSetting((String)"SideWardsRect").getSetting()).state && ((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                        Gui.drawRect(wSet2, offset, sr.getScaledWidth(), offset + 11.0f, this.getColor2());
                    }
                    Alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
                    if (((CheckBox)this.getSetting((String)"Backround").getSetting()).state) {
                        Gui.drawRect(wSet2 + 1.0f, offset, sr.getScaledWidth(), offset + 10.0f, new Color(20, 20, 20, (int)Alpha).getRGB());
                    }
                    ++count2;
                    ++index2;
                }
                float offset = 0.0f;
                for (Module m : Client.INSTANCE.moduleManager.modules) {
                    if (!m.isState() && m.anim.getValue() == 0.0f) continue;
                    m.anim.setEase(Easing.LINEAR).setMin(0.0f).setMax((float)(Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(m.name) + 3)).setSpeed(200.0f).setReversed(!m.isState()).update();
                    Animate animate = m.anim2.setEase(Easing.LINEAR).setMin(0.0f);
                    Client.INSTANCE.unicodeBasicFontRenderer.getClass();
                    animate.setMax(9.0f).setSpeed(200.0f).setReversed(!m.isState()).update();
                    wSet = (float)sr.getScaledWidth() - m.anim.getValue();
                    if (!ArrayList.mc.gameSettings.showDebugInfo && m.name != "HUD" && m.name != "Animations" && m.name != "") {
                        if (((CheckBox)this.getSetting((String)"ModuleColor").getSetting()).state) {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m.name, wSet, (int)offset - 1, m.getColor().getRGB());
                        } else if (((CheckBox)this.getSetting((String)"Rainbow").getSetting()).state) {
                            RainbowOffset = ((Slider)this.getSetting((String)"RainbowOffset").getSetting()).curValue;
                            Client.INSTANCE.unicodeBasicFontRenderer.drawString(m.getMode().isEmpty() ? m.name : String.format("%s %s%s", new Object[]{m.name, EnumChatFormatting.GRAY, m.getMode()}), wSet, offset - 1.0f, ArrayList.rainbow((int)offset * (int)RainbowOffset));
                        } else {
                            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(m.name, wSet, (int)offset - 1, this.getColor2());
                        }
                        ++count;
                        ++index;
                        offset += m.anim2.getValue();
                    }
                    GL11.glDisable((int)3042);
                }
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
}

