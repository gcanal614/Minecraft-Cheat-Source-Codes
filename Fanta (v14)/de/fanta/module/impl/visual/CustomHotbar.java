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
import de.fanta.utils.Colors;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class CustomHotbar
extends Module {
    public static double alpha;
    public static double backgroundalpha;

    public CustomHotbar() {
        super("CustomHotbar", 0, Module.Type.Visual, Color.orange);
        this.settings.add(new Setting("Blur", new CheckBox(true)));
        this.settings.add(new Setting("Background", new CheckBox(true)));
        this.settings.add(new Setting("Alpha", new Slider(0.0, 255.0, 0.1, 80.0)));
        this.settings.add(new Setting("Alpha2", new Slider(0.0, 255.0, 0.1, 150.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender2D) {
            alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
            backgroundalpha = ((Slider)this.getSetting((String)"Alpha2").getSetting()).curValue;
            int[] rgb = Colors.getRGB(this.getColor2());
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int i = 8;
            if (((CheckBox)this.getSetting((String)"Blur").getSetting()).state) {
                Client.blurHelper.blur2(389.0f, scaledResolution.getScaledHeight() - 24, scaledResolution.getScaledWidth() / 2 + 91, scaledResolution.getScaledHeight(), 50.0f);
            }
            if (((CheckBox)this.getSetting((String)"Background").getSetting()).state) {
                Gui.drawRect2(389.0, scaledResolution.getScaledHeight() - 22, scaledResolution.getScaledWidth() / 2 + 91, scaledResolution.getScaledHeight(), new Color(20, 20, 20, (int)backgroundalpha).getRGB());
            }
            int rainbow = 0;
            ++rainbow;
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("FPS " + mc.getDebugFPS(), 2.0f, ScaledResolution.INSTANCE.getScaledHeight() - 18, -1);
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("V" + Client.INSTANCE.getVersion(), 50.0f, ScaledResolution.INSTANCE.getScaledHeight() - 18, -1);
            if (CustomHotbar.mc.thePlayer.inventory.currentItem == 0) {
                Gui.drawRect(scaledResolution.getScaledWidth() / 2 - 91 + CustomHotbar.mc.thePlayer.inventory.currentItem * 20, scaledResolution.getScaledHeight() - 22, scaledResolution.getScaledWidth() / 2 + 91 - 20 * i, scaledResolution.getScaledHeight(), Colors.getColor(rgb[0], rgb[1], rgb[2], (int)alpha));
            } else {
                Gui.drawRect(scaledResolution.getScaledWidth() / 2 - 91 + CustomHotbar.mc.thePlayer.inventory.currentItem * 20, scaledResolution.getScaledHeight() - 22, scaledResolution.getScaledWidth() / 2 + 91 - 20 * (8 - CustomHotbar.mc.thePlayer.inventory.currentItem), scaledResolution.getScaledHeight(), Colors.getColor(rgb[0], rgb[1], rgb[2], (int)alpha));
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

