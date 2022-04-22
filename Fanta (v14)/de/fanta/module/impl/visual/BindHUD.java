/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.Slider;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class BindHUD
extends Module {
    public static double alpha;

    public BindHUD() {
        super("BindHUD", 0, Module.Type.Visual, new Color(143, 254, 139));
        this.settings.add(new Setting("RGBLine", new CheckBox(false)));
        this.settings.add(new Setting("Blur", new CheckBox(false)));
        this.settings.add(new Setting("Alpha", new Slider(1.0, 255.0, 0.1, 50.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
        alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
        if (event instanceof EventRender2D) {
            float x = 7.5f;
            float y = 20.0f;
            if (Client.INSTANCE.moduleManager.getModule("Radar").isState()) {
                y = 100.0f;
            }
            if (Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                y = 140.0f;
            }
            if (Client.INSTANCE.moduleManager.getModule("InventoryHUD").isState()) {
                y = 90.0f;
            }
            if (Client.INSTANCE.moduleManager.getModule("Radar").isState()) {
                y = 170.0f;
            }
            if (Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                x = -1.0f;
                if (Client.INSTANCE.moduleManager.getModule("InventoryHUD").isState()) {
                    y = 210.0f;
                }
            }
            if (((CheckBox)this.getSetting((String)"Blur").getSetting()).state) {
                Client.blurHelper.blur2(x + 6.0f, y + 17.0f - 15.0f, x + 153.0f + 9.0f, y + 75.0f, 10.0f);
            }
            new Gui();
            Gui.drawRect(x + 6.0f, y + 17.0f - 15.0f, x + 153.0f + 9.0f, y + 75.0f, new Color(20, 20, 20, (int)alpha).getRGB());
            if (((CheckBox)this.getSetting((String)"RGBLine").getSetting()).state) {
                if (Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                    Gui.drawRGBLineHorizontal(x + 6.0f, y + 29.0f - 16.0f, x + 153.0f + 4.0f, 2.0f, 1, true);
                } else {
                    Gui.drawRGBLineHorizontal(x + 6.0f, y + 29.0f - 16.0f, x + 153.0f - 5.0f, 2.0f, 1, true);
                }
            } else {
                new Gui();
                Gui.drawRect(x + 6.0f, y + 29.0f - 15.0f, x + 153.0f + 9.0f, y + 13.0f, this.getColor2());
            }
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Binds:", x + 70.0f, y + 1.0f, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("TestAura", x + 6.0f, y + 12.0f, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Stealer", x + 6.0f, y + 22.0f, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Fucker", x + 6.0f, y + 32.0f, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Scaffold", x + 6.0f, y + 42.0f, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Flight", x + 6.0f, y + 52.0f, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Speed", x + 6.0f, y + 62.0f, -1);
            if (Client.INSTANCE.moduleManager.getModule("TestAura").isState()) {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("On", x + 145.0f, y + 12.0f, Color.green.getRGB());
            } else {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Off", x + 145.0f, y + 12.0f, Color.red.getRGB());
            }
            if (Client.INSTANCE.moduleManager.getModule("Stealer").isState()) {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("On", x + 145.0f, y + 22.0f, Color.green.getRGB());
            } else {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Off", x + 145.0f, y + 22.0f, Color.red.getRGB());
            }
            if (Client.INSTANCE.moduleManager.getModule("Fucker").isState()) {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("On", x + 145.0f, y + 32.0f, Color.green.getRGB());
            } else {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Off", x + 145.0f, y + 32.0f, Color.red.getRGB());
            }
            if (Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("On", x + 145.0f, y + 42.0f, Color.green.getRGB());
            } else {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Off", x + 145.0f, y + 42.0f, Color.red.getRGB());
            }
            if (Client.INSTANCE.moduleManager.getModule("Flight").isState()) {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("On", x + 145.0f, y + 52.0f, Color.green.getRGB());
            } else {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Off", x + 145.0f, y + 52.0f, Color.red.getRGB());
            }
            if (Client.INSTANCE.moduleManager.getModule("Speed").isState()) {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("On", x + 145.0f, y + 62.0f, Color.green.getRGB());
            } else {
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Off", x + 145.0f, y + 62.0f, Color.red.getRGB());
            }
        }
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
}

