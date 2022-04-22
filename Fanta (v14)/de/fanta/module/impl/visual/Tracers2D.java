/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.ColorValue;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.RotationUtil;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.Display;

public class Tracers2D
extends Module {
    public static Tracers2D INSTANCE;

    public Tracers2D() {
        super("2D-Tracers", 0, Module.Type.Visual, Color.magenta);
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
        INSTANCE = this;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender2D) {
            int x = Display.getWidth() / 2 / Math.max(Tracers2D.mc.gameSettings.guiScale, 1);
            int y = Display.getHeight() / 2 / Math.max(Tracers2D.mc.gameSettings.guiScale, 1);
            List playerList = Tracers2D.mc.theWorld.playerEntities;
            playerList.removeIf(entity -> entity == Tracers2D.mc.thePlayer || entity.isInvisible());
            int[] rgb = Colors.getRGB(this.getColor2());
            for (EntityPlayer player : playerList) {
                int alpha = (int)(255.0f - Math.min(Tracers2D.mc.thePlayer.getDistanceToEntity(player), 255.0f));
                float angle = RotationUtil.getAngle(player) % 360.0f + 180.0f;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, 0.0f);
                GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate(-x, -y, 0.0f);
                RenderUtil.drawTriangleFilled(x, y + 50, 5.0f, 9.0f, Colors.getColor(rgb[0], rgb[1], rgb[2], alpha));
                GlStateManager.translate(x, y, 0.0f);
                GlStateManager.rotate(-angle, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate(-x, -y, 0.0f);
                GlStateManager.popMatrix();
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

