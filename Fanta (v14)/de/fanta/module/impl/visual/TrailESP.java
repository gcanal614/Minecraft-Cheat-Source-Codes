/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

public class TrailESP
extends Module {
    public ArrayList<Point> points = new ArrayList();
    public static double lenghtttt;
    public static double alphaaaaa;

    public TrailESP() {
        super("TrailESP", 0, Module.Type.Visual, new Color(108, 2, 139));
        this.settings.add(new Setting("Smooth Ending", new CheckBox(false)));
        this.settings.add(new Setting("Length", new Slider(1.0, 1000.0, 0.1, 4.0)));
        this.settings.add(new Setting("Alpha", new Slider(1.0, 255.0, 0.1, 4.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onDisable() {
        this.points.clear();
        TrailESP.mc.gameSettings.keyBindSprint.pressed = false;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        lenghtttt = ((Slider)this.getSetting((String)"Length").getSetting()).curValue;
        alphaaaaa = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
        if (event instanceof EventRender3D) {
            this.points.removeIf(p -> (double)p.age >= lenghtttt);
            if (TrailESP.mc.thePlayer.motionX != 0.0 || TrailESP.mc.thePlayer.motionZ != 0.0) {
                float x = (float)(TrailESP.mc.thePlayer.lastTickPosX + (TrailESP.mc.thePlayer.posX - TrailESP.mc.thePlayer.lastTickPosX) * (double)((EventRender3D)event).getPartialTicks());
                float y = (float)(TrailESP.mc.thePlayer.lastTickPosY + (TrailESP.mc.thePlayer.posY - TrailESP.mc.thePlayer.lastTickPosY) * (double)((EventRender3D)event).getPartialTicks());
                float z = (float)(TrailESP.mc.thePlayer.lastTickPosZ + (TrailESP.mc.thePlayer.posZ - TrailESP.mc.thePlayer.lastTickPosZ) * (double)((EventRender3D)event).getPartialTicks());
                this.points.add(new Point((float)((double)x - TrailESP.mc.thePlayer.motionX), y, (float)((double)z - TrailESP.mc.thePlayer.motionZ)));
            }
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.disableLighting();
            GL11.glLineWidth((float)3.0f);
            GL11.glShadeModel((int)7425);
            GL11.glDisable((int)2884);
            GL11.glEnable((int)2929);
            for (Point t : this.points) {
                if (this.points.indexOf(t) >= this.points.size() - 1) continue;
                Point temp = this.points.get(this.points.indexOf(t) + 1);
                float a = (float)alphaaaaa;
                if (((CheckBox)this.getSetting("Smooth Ending").getSetting()).isState()) {
                    a = (float)(alphaaaaa * (double)((float)this.points.indexOf(t) / (float)this.points.size()));
                }
                int[] rgb = Colors.getRGB(this.getColor2());
                GL11.glBegin((int)8);
                double d = t.x;
                mc.getRenderManager();
                double x = d - RenderManager.renderPosX;
                double d2 = t.y;
                mc.getRenderManager();
                double y = d2 - RenderManager.renderPosY;
                double d3 = t.z;
                mc.getRenderManager();
                double z = d3 - RenderManager.renderPosZ;
                double d4 = temp.x;
                mc.getRenderManager();
                double x1 = d4 - RenderManager.renderPosX;
                double d5 = temp.y;
                mc.getRenderManager();
                double y1 = d5 - RenderManager.renderPosY;
                double d6 = temp.z;
                mc.getRenderManager();
                double z1 = d6 - RenderManager.renderPosZ;
                RenderUtil.color(Colors.getColor(rgb[0], rgb[1], rgb[2], 0));
                GL11.glVertex3d((double)x, (double)(y + (double)TrailESP.mc.thePlayer.height), (double)z);
                RenderUtil.color(this.getColor2());
                GL11.glVertex3d((double)x, (double)y, (double)z);
                RenderUtil.color(Colors.getColor(rgb[0], rgb[1], rgb[2], 0));
                GL11.glVertex3d((double)x1, (double)(y1 + (double)TrailESP.mc.thePlayer.height), (double)z1);
                RenderUtil.color(this.getColor2());
                GL11.glVertex3d((double)x1, (double)y1, (double)z1);
                GL11.glEnd();
                t.age += 1.0f;
            }
            GlStateManager.enableAlpha();
            GL11.glShadeModel((int)7424);
            GL11.glDisable((int)2848);
            GL11.glEnable((int)2884);
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.resetColor();
            GlStateManager.popMatrix();
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

    class Point {
        public final float x;
        public final float y;
        public final float z;
        public float age = 0.0f;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

