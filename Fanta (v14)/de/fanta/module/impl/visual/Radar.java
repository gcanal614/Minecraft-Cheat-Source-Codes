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
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class Radar
extends Module {
    public static int mouseX;
    public static int mouseY;
    public static int lastMouseX;
    public static int lastMouseY;
    public static boolean dragging;
    public static Setting setting;
    public static int posX;
    public static int posY;
    public static double width;
    public static double height;
    public static double size;
    public static double alphaaa;
    public static double blurAlpha;

    static {
        posX = 13;
        posY = 34;
        width = 70.0;
        height = 70.0;
    }

    public Radar() {
        super("Radar", 0, Module.Type.Visual, new Color(108, 2, 139));
        this.settings.add(new Setting("Outline", new CheckBox(true)));
        this.settings.add(new Setting("Blur", new CheckBox(false)));
        this.settings.add(new Setting("BlurAlpha", new Slider(0.0, 255.0, 1.0, 10.0)));
        this.settings.add(new Setting("Size", new Slider(0.0, 255.0, 1.0, 70.0)));
        this.settings.add(new Setting("Alpha", new Slider(0.0, 255.0, 1.0, 255.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
        blurAlpha = ((Slider)this.getSetting((String)"BlurAlpha").getSetting()).curValue;
        size = ((Slider)this.getSetting((String)"Size").getSetting()).curValue;
        if (event instanceof EventRender2D && event.isPre() && !Client.INSTANCE.moduleManager.getModule("TabGui").isState()) {
            int index1 = 0;
            int posX = Radar.posX + (mouseX -= lastMouseX);
            int posY = Radar.posY + (mouseY -= lastMouseY);
            if (((CheckBox)this.getSetting((String)"Blur").getSetting()).state) {
                Client.blurHelper.blur2(posX, posY - 8, (float)((double)posX + size), (float)((double)posY + size - 10.0), (float)blurAlpha);
            }
            alphaaa = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
            Gui.drawRect2(posX, posY - 8, (double)posX + size, (double)posY + size - 10.0, new Color(30, 30, 30, (int)alphaaa).getRGB());
            if (((CheckBox)this.getSetting((String)"Outline").getSetting()).state) {
                Gui.drawRect2(posX, posY - 8, (double)posX + size + 1.0, (double)posY + size - 77.0, this.getColor2());
                Gui.drawRect2(posX, posY + 60, (double)posX + size + 1.0, (double)posY + size - 11.0, this.getColor2());
                Gui.drawRect2(posX, posY - 8, (double)posX + size - 69.0, (double)posY + size - 11.0, this.getColor2());
                Gui.drawRect2(posX + 70, posY - 8, (double)posX + size + 1.0, (double)posY + size - 11.0, this.getColor2());
            }
            ++index1;
            double halfWidth = size / 2.0 + 0.5;
            double halfHeight = size / 2.0 - 10.5;
            GuiIngame.drawRect((double)posX + halfWidth, (double)posY + halfHeight, (double)posX + halfWidth + size / 70.0, (double)posY + halfHeight + size / 70.0, this.getColor2());
            for (EntityPlayer player : Radar.mc.theWorld.playerEntities) {
                double playerZ;
                double diffZ;
                double playerX;
                double diffX;
                if (player == Radar.mc.thePlayer || !(MathHelper.sqrt_double((diffX = (playerX = player.posX) - Radar.mc.thePlayer.posX) * diffX + (diffZ = (playerZ = player.posZ) - Radar.mc.thePlayer.posZ) * diffZ) < 50.0f)) continue;
                double clampedX = MathHelper.clamp_double(diffX, -halfWidth + 3.0, halfWidth - 3.0);
                double clampedY = MathHelper.clamp_double(diffZ, -halfHeight + 5.0, halfHeight - 3.0);
                GuiIngame.drawRect((double)posX + halfWidth + clampedX, (double)posY + halfHeight + clampedY, (double)posX + halfWidth + clampedX + size / 70.0, (double)posY + halfHeight + clampedY + size / 70.0, Color.pink.getRGB());
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

    public static float getAngle(Entity entity) {
        double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, 1.0) - RenderUtil.interpolate(Radar.mc.thePlayer.posX, Radar.mc.thePlayer.lastTickPosX, 1.0);
        double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, 1.0) - RenderUtil.interpolate(Radar.mc.thePlayer.posZ, Radar.mc.thePlayer.lastTickPosZ, 1.0);
        float yaw = (float)(-Math.toDegrees(Math.atan2(x, z)));
        return (float)((double)yaw - RenderUtil.interpolate(Radar.mc.thePlayer.rotationYaw, Radar.mc.thePlayer.prevRotationYaw, 1.0));
    }
}

