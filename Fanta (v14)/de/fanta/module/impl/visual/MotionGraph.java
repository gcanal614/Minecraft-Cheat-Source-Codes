/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.Slider;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class MotionGraph
extends Module {
    private ArrayList<Motion> motions = new ArrayList();

    public MotionGraph() {
        super("MotionGraph", 0, Module.Type.Visual, Color.blue);
        this.settings.add(new Setting("Outline", new CheckBox(true)));
        this.settings.add(new Setting("Blur", new CheckBox(true)));
        this.settings.add(new Setting("Background", new CheckBox(true)));
        this.settings.add(new Setting("Time MS", new Slider(2000.0, 7000.0, 100.0, 3000.0)));
    }

    @Override
    public void onDisable() {
        this.motions.clear();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender2D) {
            EventRender2D e = (EventRender2D)event;
            if (!e.isPre()) {
                return;
            }
            ScaledResolution sr = new ScaledResolution(mc);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Gui gui = new Gui();
            if (((CheckBox)this.getSetting((String)"Blur").getSetting()).state) {
                Client.blurHelper.blur2(sr.getScaledWidth() / 2 - this.motions.size(), sr.getScaledHeight() - 100, sr.getScaledWidth() / 2 + this.motions.size(), sr.getScaledHeight() - 50, 100.0f);
            }
            if (((CheckBox)this.getSetting((String)"Background").getSetting()).state) {
                Gui.drawRect(sr.getScaledWidth() / 2 - this.motions.size(), sr.getScaledHeight() - 100, sr.getScaledWidth() / 2 + this.motions.size(), sr.getScaledHeight() - 50, Integer.MIN_VALUE);
            }
            if (((CheckBox)this.getSetting((String)"Outline").getSetting()).state) {
                gui.drawHollowRect((float)(sr.getScaledWidth() / 2 - this.motions.size()) - 0.5f, (float)sr.getScaledHeight() - 100.5f, this.motions.size() * 2 + 1, 51.0f, 0.5f, Color.white.getRGB());
            }
            int i = 0;
            while (i < this.motions.size()) {
                float y2;
                float x2;
                Motion mot = this.motions.get(i);
                if (mot.motion > 58.0f) {
                    mot.motion = 58.0f;
                }
                try {
                    if (this.motions.get((int)(i + 1)).motion > 58.0f) {
                        this.motions.get((int)(i + 1)).motion = 58.0f;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                float x = sr.getScaledWidth() / 2 - this.motions.size() + i * 2;
                float y = (float)(sr.getScaledHeight() - 60) - mot.motion / 2.0f;
                try {
                    x2 = sr.getScaledWidth() / 2 - this.motions.size() + (i + 1) * 2;
                }
                catch (Exception e2) {
                    x2 = x;
                }
                try {
                    y2 = (float)(sr.getScaledHeight() - 60) - this.motions.get((int)(i + 1)).motion / 2.0f;
                }
                catch (Exception e2) {
                    y2 = y;
                }
                Gui.draw2dLine(x, y, x2, y2, Color.white);
                ++i;
            }
        }
        if (event instanceof EventUpdate) {
            double len = Math.sqrt(MotionGraph.mc.thePlayer.motionX * MotionGraph.mc.thePlayer.motionX + MotionGraph.mc.thePlayer.motionY * MotionGraph.mc.thePlayer.motionY + MotionGraph.mc.thePlayer.motionZ * MotionGraph.mc.thePlayer.motionZ) * 50.0;
            if (len <= 3.92) {
                len = 0.0;
            }
            this.motions.add(new Motion((float)len, System.currentTimeMillis()));
            if (this.motions == null || this.motions.isEmpty()) {
                return;
            }
            try {
                for (Motion motion : this.motions) {
                    if (System.currentTimeMillis() - motion.time <= (long)((int)((Slider)this.getSetting((String)"Time MS").getSetting()).curValue)) continue;
                    this.motions.remove(motion);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    class Motion {
        public float motion;
        public long time;

        public Motion(float motion, long time) {
            this.motion = motion;
            this.time = time;
        }
    }
}

