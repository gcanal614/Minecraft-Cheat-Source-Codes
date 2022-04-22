/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.lavache.anime.Animate
 *  fr.lavache.anime.Easing
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.module.impl.visual.Themes;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.TimeUtil;
import fr.lavache.anime.Animate;
import fr.lavache.anime.Easing;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Tabgui
extends Module {
    private int index;
    private int indexModules;
    private boolean opened;
    private final Animate animate = new Animate();
    private final Animate animate2 = new Animate();
    private int last = 0;
    private boolean reversed = false;
    private boolean reversed2 = false;
    private TimeUtil moveDelayTimer = new TimeUtil();
    private int min = -15;
    private int max = 115;
    private int maxModules;

    public Tabgui() {
        super("Tabgui", 0, Module.Type.Visual, Color.red);
        this.settings.add(new Setting("HoloPicture", new CheckBox(false)));
        this.settings.add(new Setting("Mode", new DropdownBox("Flux", new String[]{"Flux", "Rounded", "Violence", "Hero", "Holo", "Jello", "ZeroDay", "Ambien", "Ambien2"})));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
        if (this.getMode().equals("Flux")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Gui.drawRect(x, y, x + width, y + height - 1.0f, new Color(50, 50, 50, 50).getRGB());
                Client.blurHelper.blur2(x, y, x + width, y + height, 1.0f);
                Client.INSTANCE.fluxicon.drawStringWithShadow("q", 25.0f, 7.0f, this.getColor2());
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Flux", 4.0f, 6.0f, this.getColor2());
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.fluxTabGuiFont.drawString(Module.Type.values()[yT].name(), x + 5.0f, y + (float)(yT * 15), this.index == yT ? Color.white.getRGB() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                Gui.drawRect(x + 1.0f, y + 2.0f + (float)(this.index * 15), x + 2.5f, y + 13.0f + (float)(this.index * 15), this.getColor2());
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15), x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + moduleHeight - 1.0f, new Color(50, 50, 50, 50).getRGB());
                    Client.blurHelper2.blur2(x + width + 5.0f, y + (float)(this.index * 15), x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + moduleHeight - 1.0f, 30.0f);
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 5.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Client.INSTANCE.fluxTabGuiFont.drawString(((Module)modules.get(i)).getName(), x + width + 10.0f, y + (float)(this.index * 15) + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                    Gui.drawRect(x + width + 5.0f + 1.0f, y + (float)(this.index * 15) + 2.0f + (float)(this.indexModules * 15), x + width + 5.0f + 2.5f, y + (float)(this.index * 15) + 13.0f + (float)(this.indexModules * 15), this.getColor2());
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Rounded")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 90.0f;
                Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Fanta", 3.0f, 1.0f, this.getColor2());
                RenderUtil.drawRoundedRect2(x, y, width - 35.0f, height - 15.0f, 6.0, new Color(50, 50, 50, 200));
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.fluxTabGuiFont.drawString(Module.Type.values()[yT].name(), x + 5.0f, y + (float)(yT * 12), this.index == yT ? this.getColor2() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    RenderUtil.drawRoundedRect2(x + width - 30.0f, y + (float)(this.index * 15), x + 35.0f + (float)modules.size() + 18.0f, modules.size() * 15 - 1, 5.0, new Color(50, 50, 50, 200));
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width - 25.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Client.INSTANCE.fluxTabGuiFont.drawString(((Module)modules.get(i)).getName(), x + width - 25.0f, y + (float)(this.index * 15) + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                    Client.INSTANCE.fluxTabGuiFont.drawString(".", x + width - 28.0f + 1.0f, y + (float)(this.index * 15) - 2.0f + (float)(this.indexModules * 15), this.getColor2());
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Holo")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Gui.drawRect(x, y + 15.0f, x + width, y + height + 15.0f, new Color(255, 255, 255, 30).getRGB());
                Client.blurHelper.blur2(x, y + 14.5f, x + width, y + height + 17.0f, 1.0f);
                Gui.drawRect(x + width, y + (float)(this.index * 15) + 15.0f, x, y + 15.0f + (float)(this.index * 15) + 15.0f, new Color(20, 20, 20, 70).getRGB());
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.fluxTabGuiFont.drawString(Module.Type.values()[yT].name(), x + 5.0f, y + (float)(yT * 15) + 17.0f, this.index == yT ? Color.white.getRGB() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                int Fade = 0;
                Gui.drawRect(x, y + 15.0f, x + 1.0f, y + height + 15.0f, Themes.getGradientOffset(Color.blue, Color.cyan, Fade / 100).getRGB());
                ++Fade;
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15) + 15.0f, x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + 15.0f + moduleHeight - 1.0f, new Color(50, 50, 50, 50).getRGB());
                    Client.blurHelper2.blur2(x + width + 5.0f, y + (float)(this.index * 15) + 15.0f, x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + 15.0f + moduleHeight - 1.0f, 30.0f);
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 5.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15) - 2.0f + 15.0f + 2.0f + (float)(this.indexModules * 15), x + width + width + 5.0f, y + (float)(this.index * 15) + 2.0f + 15.0f + 13.0f + (float)(this.indexModules * 15), new Color(20, 20, 20, 30).getRGB());
                        Client.INSTANCE.fluxTabGuiFont.drawString(((Module)modules.get(i)).getName(), x + width + 10.0f, y + (float)(this.index * 15) + 17.0f + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
                if (((CheckBox)this.getSetting((String)"HoloPicture").getSetting()).state) {
                    Tabgui.drawImage(ScaledResolution.INSTANCE.getScaledWidth() / 140, 10, 60, 20, new ResourceLocation("Fanta/gui/Holo2.png"));
                } else {
                    Client.INSTANCE.fluxTabGuiFont2.drawString("Fanta", 3.0f, 0.0f, -1);
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Jello")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Client.blurHelper.blur2(x, y + 17.0f, x + width, y + height + 3.0f, 1.0f);
                Gui.drawRect(x, y + 17.0f, x + width, y + height + 1.0f, new Color(255, 255, 255, 27).getRGB());
                Gui.drawRect(x + width, y + (float)(this.index * 12) + 17.0f, x, y + 15.0f + (float)(this.index * 12) + 17.0f, new Color(20, 20, 20, 50).getRGB());
                Client.INSTANCE.Jello2.drawString("Sigma", 8.0f, 8.0f, new Color(255, 255, 255, 190).getRGB());
                Client.INSTANCE.Jello3.drawString("Jello", 8.0f, 27.0f, new Color(255, 255, 255, 190).getRGB());
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.Jello.drawString(Module.Type.values()[yT].name(), x + 5.0f, y + (float)(yT * 12) + 21.0f, this.index == yT ? Color.white.getRGB() : Color.white.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                int Fade = 0;
                ++Fade;
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15) + 15.0f, x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + 15.0f + moduleHeight - 1.0f, new Color(50, 50, 50, 50).getRGB());
                    Client.blurHelper2.blur2(x + width + 5.0f, y + (float)(this.index * 15) + 15.0f, x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + 15.0f + moduleHeight - 1.0f, 30.0f);
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 5.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15) - 2.0f + 15.0f + 2.0f + (float)(this.indexModules * 15), x + width + width + 5.0f, y + (float)(this.index * 15) + 2.0f + 15.0f + 13.0f + (float)(this.indexModules * 15), new Color(20, 20, 20, 30).getRGB());
                        Client.INSTANCE.Jello.drawString(((Module)modules.get(i)).getName(), x + width + 10.0f, y + (float)(this.index * 15) + 17.0f + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("ZeroDay")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                RenderUtil.drawRoundedRect2(x, y + 15.0f, x + width + 5.0f, y + height - 28.0f, 3.0, new Color(0, 0, 0, 150));
                Gui.drawRect(x + 1.5f, y + (float)(this.index * 12) + 20.0f, x, y + 17.0f + (float)(this.index * 12) + 16.0f, Color.green.getRGB());
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.verdana2.drawStringWithShadow(Module.Type.values()[yT].name(), x + 5.0f, y + (float)(yT * 12) + 21.0f, this.index == yT ? Color.white.getRGB() : Color.white.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                int Fade = 0;
                ++Fade;
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15) + 15.0f, x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + 15.0f + moduleHeight - 1.0f, new Color(50, 50, 50, 50).getRGB());
                    Client.blurHelper2.blur2(x + width + 5.0f, y + (float)(this.index * 15) + 15.0f, x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + 15.0f + moduleHeight - 1.0f, 30.0f);
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 5.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Gui.drawRect(x + width + 5.0f, y + (float)(this.index * 15) - 2.0f + 15.0f + 2.0f + (float)(this.indexModules * 15), x + width + width + 5.0f, y + (float)(this.index * 15) + 2.0f + 15.0f + 13.0f + (float)(this.indexModules * 15), new Color(20, 20, 20, 30).getRGB());
                        Client.INSTANCE.Jello.drawString(((Module)modules.get(i)).getName(), x + width + 10.0f, y + (float)(this.index * 15) + 17.0f + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
                Tabgui.drawImage(ScaledResolution.INSTANCE.getScaledWidth() / 140 - 12, -3, 150, 47, new ResourceLocation("Fanta/gui/zeroday.png"));
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Hero")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Gui.drawRect(x - 5.0f, y - 20.0f, x + width - 10.0f, y + height - 10.0f, new Color(20, 20, 20, 180).getRGB());
                Gui.drawRect(x + 50.0f, y + (float)(this.index * 13), x - 5.0f, y + 14.0f + (float)(this.index * 13), new Color(20, 255, 100, 200).getRGB());
                Gui.drawRect(x + 50.0f, y - 20.0f, x + 51.0f, y + 80.0f, new Color(20, 255, 100, 200).getRGB());
                Gui.drawRect(x - 5.0f, y + 79.0f, x + 51.0f, y + 80.0f, new Color(20, 255, 100, 200).getRGB());
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.heroTabGui.drawStringWithShadow("Hero", 1.5f, -3.5f, new Color(100, 255, 90, 255).getRGB());
                    Client.INSTANCE.unicodeBasicFontRenderer4.drawString(Module.Type.values()[yT].name(), x - 3.0f, y + (float)(yT * 13), this.index == yT ? Color.white.getRGB() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width - 8.0f, y + (float)(this.index * 15), x + 5.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + moduleHeight - 1.0f, new Color(20, 20, 20, 170).getRGB());
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width - 8.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor() + 26.0f)), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Gui.drawRect(x + width - 8.0f, y + (float)(this.index * 15) + (float)(this.indexModules * 15), x + width + 62.5f + 2.5f, y + (float)(this.index * 15) + 14.0f + (float)(this.indexModules * 15), new Color(20, 255, 100, 200).getRGB());
                        Client.INSTANCE.unicodeBasicFontRenderer4.drawString(((Module)modules.get(i)).getName(), x + width - 2.0f, y + (float)(this.index * 15) + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Ambien")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 26.0f;
                float y = 80.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Gui.drawRect(x - 18.0f, y - 11.0f, x + width + 12.0f, y + height - 4.0f, new Color(0, 0, 0, 230).getRGB());
                Gui.drawRect(x - 18.0f, y - 10.5f, x + width - 74.5f, y + height - 1.0f, -1);
                Gui.drawRect(x - 18.0f, y + 89.0f, x + width + 12.0f, y + height - 4.0f, -1);
                Gui.drawRect(x + 69.0f, y - 10.5f, x + width + 12.0f, y + height - 1.0f, -1);
                Gui.drawRect(x + 69.0f, y - 1.0f + (float)(this.index * 14) + 1.0f, x - 15.0f, y + 14.0f + (float)(this.index * 14) - 1.0f, new Color(20, 102, 52).getRGB());
                Tabgui.drawImage(ScaledResolution.INSTANCE.getScaledWidth() / 140 - 1, -4, 96, 96, new ResourceLocation("Fanta/gui/ambien.png"));
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.ambien.drawStringWithShadow(Module.Type.values()[yT].name(), x + (width - 8.0f) / 2.0f - (float)(Client.INSTANCE.ambien.getStringWidth(Module.Type.values()[yT].name()) / 2), y + (float)(yT * 14), this.index == yT ? Color.white.getRGB() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width + 15.0f, y + (float)(this.index * 15), x + 20.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + moduleHeight - 1.0f, new Color(0, 0, 0, 230).getRGB());
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 19.5f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Gui.drawRect(x + width + 12.5f + 1.0f, y + (float)(this.index * 15) + (float)(this.indexModules * 15), x + width + 79.5f, y + (float)(this.index * 15) + 13.0f + (float)(this.indexModules * 15), new Color(20, 102, 52).getRGB());
                        Client.INSTANCE.ambien.drawStringWithShadow2(((Module)modules.get(i)).getName(), x + width + 22.0f, y + (float)(this.index * 15) + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Ambien2")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 26.0f;
                float y = 80.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Gui.drawRect(x - 18.0f, y - 11.0f, x + width + 12.0f, y + height - 4.0f, new Color(0, 0, 0, 225).getRGB());
                Gui.drawRect(x - 18.0f, y - 11.0f, x + width + 12.0f, y + height - 90.0f, new Color(91, 49, 134, 150).getRGB());
                Gui.drawRect(x + 72.0f, y - 1.0f + (float)(this.index * 14) + 1.0f, x - 18.0f, y + 14.0f + (float)(this.index * 14) - 1.0f, new Color(91, 49, 134, 255).getRGB());
                Tabgui.drawImage(ScaledResolution.INSTANCE.getScaledWidth() / 140 - 1, -4, 96, 96, new ResourceLocation("Fanta/gui/ambien2.png"));
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.ambien.drawStringWithShadow(Module.Type.values()[yT].name(), x + (width - 8.0f) / 2.0f - (float)(Client.INSTANCE.ambien.getStringWidth(Module.Type.values()[yT].name()) / 2), y + (float)(yT * 14), this.index == yT ? Color.white.getRGB() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width + 15.0f, y + (float)(this.index * 15), x + 20.0f + width + this.animate.getValue(), y + (float)(this.index * 15) + moduleHeight - 1.0f, new Color(0, 0, 0, 230).getRGB());
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 19.5f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Gui.drawRect(x + width + 12.5f + 1.0f, y + (float)(this.index * 15) + (float)(this.indexModules * 15), x + width + 79.5f, y + (float)(this.index * 15) + 13.0f + (float)(this.indexModules * 15), new Color(91, 49, 134, 255).getRGB());
                        Client.INSTANCE.ambien.drawStringWithShadow2(((Module)modules.get(i)).getName(), x + width + 22.0f, y + (float)(this.index * 15) + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.white.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
            }
        } else if (this.getMode().equals("Violence")) {
            ArrayList<Module> modules = new ArrayList<Module>();
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                if (module.type != Module.Type.values()[this.index]) continue;
                modules.add(module);
            }
            if (!this.opened) {
                this.indexModules = 0;
            }
            this.maxModules = modules.size();
            if (this.reversed && this.animate.getValue() == 0.0f) {
                this.opened = false;
                this.animate.reset();
            }
            if (event instanceof EventRender2D && event.isPre()) {
                ScaledResolution sr = new ScaledResolution(mc);
                Gui gui = new Gui();
                float x = 5.0f;
                float y = 20.0f;
                float height = Module.Type.values().length * 15;
                float width = 60.0f;
                Gui.drawRect(x, y, x + width - 7.0f, y + height - 17.0f, Color.black.getRGB());
                Gui.drawRect(x + 52.0f, y - 1.0f + (float)(this.index * 12) + 1.0f, x, y + 13.0f + (float)(this.index * 12) - 1.0f, new Color(90, 100, 255).getRGB());
                Client.INSTANCE.Violence2.drawStringWithShadow("V", 3.0f, 1.0f, new Color(60, 50, 255).getRGB());
                Client.INSTANCE.Violence.drawString("IOLENCE", 11.0f, 6.0f, -1);
                int yT = 0;
                while (yT < Module.Type.values().length) {
                    Client.INSTANCE.ViolenceTabGUi.drawStringWithShadow(Module.Type.values()[yT].name(), x + (width - 8.0f) / 2.0f - (float)(Client.INSTANCE.ViolenceTabGUi.getStringWidth(Module.Type.values()[yT].name()) / 2), y + (float)(yT * 12), this.index == yT ? Color.white.getRGB() : Color.lightGray.getRGB());
                    ++yT;
                }
                this.animate.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                this.animate2.setEase(Easing.SINE_IN_OUT).setMin(0.0f).setMax(width).setSpeed(220.0f).setReversed(this.reversed).update();
                if (this.opened) {
                    float moduleHeight = modules.size() * 15;
                    Gui.drawRect(x + width - 5.0f, y + (float)(this.index * 15), x + width + this.animate.getValue(), y + (float)(this.index * 15) + moduleHeight - 1.0f, Color.black.getRGB());
                    Gui.drawRect(x + width - 6.0f + 1.0f, y + (float)(this.index * 15) + (float)(this.indexModules * 15), x + width + 59.0f, y + (float)(this.index * 15) + 13.0f + (float)(this.indexModules * 15), new Color(90, 100, 255).getRGB());
                    int i = 0;
                    while (i < modules.size()) {
                        GL11.glEnable((int)3089);
                        GL11.glScissor((int)((int)((x + width + 5.0f) * (float)sr.getScaleFactor())), (int)((int)(((float)sr.getScaledHeight() - y - (float)(this.index * 15) - (float)(i * 15) - moduleHeight) * (float)sr.getScaleFactor())), (int)((int)(this.animate.getValue() * (float)sr.getScaleFactor())), (int)((int)(moduleHeight * (float)sr.getScaleFactor())));
                        Client.INSTANCE.ViolenceTabGUi.drawStringWithShadow2(((Module)modules.get(i)).getName(), x + width + 8.0f, y + (float)(this.index * 15) + (float)(i * 15), this.indexModules == i || ((Module)modules.get(i)).isState() ? Color.orange.getRGB() : Color.lightGray.getRGB());
                        GL11.glDisable((int)3089);
                        ++i;
                    }
                }
            }
            if (event instanceof EventTick) {
                if (!this.opened) {
                    if (Keyboard.isKeyDown((int)208) && this.index < Module.Type.values().length - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.index;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.index > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.index;
                        this.moveDelayTimer.reset();
                    }
                } else {
                    if (Keyboard.isKeyDown((int)208) && this.indexModules < this.maxModules - 1 && this.moveDelayTimer.hasReached(120L)) {
                        ++this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                    if (Keyboard.isKeyDown((int)200) && this.indexModules > 0 && this.moveDelayTimer.hasReached(120L)) {
                        --this.indexModules;
                        this.moveDelayTimer.reset();
                    }
                }
                if (Keyboard.isKeyDown((int)205)) {
                    if (this.opened && this.moveDelayTimer.hasReached(200L)) {
                        ((Module)modules.get(this.indexModules)).setState(!((Module)modules.get(this.indexModules)).isState());
                        this.moveDelayTimer.reset();
                    } else {
                        this.moveDelayTimer.reset();
                        this.reversed = false;
                        this.opened = true;
                    }
                }
                if (Keyboard.isKeyDown((int)203)) {
                    this.reversed = true;
                }
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

    @Override
    public String getMode() {
        try {
            return ((DropdownBox)this.getSetting((String)"Mode").getSetting()).curOption;
        }
        catch (Exception e) {
            return "Flux";
        }
    }

    public static void drawImage(int x, int y, int width, int height, ResourceLocation resourceLocation) {
        mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

