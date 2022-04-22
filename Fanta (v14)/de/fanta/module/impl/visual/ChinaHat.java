/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.module.Module;
import de.fanta.utils.ColorUtils;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;

public class ChinaHat
extends Module {
    public ChinaHat() {
        super("China Hat", 0, Module.Type.Visual, Color.green);
    }

    @Override
    public void onEvent(Event event) {
        if (ChinaHat.mc.gameSettings.thirdPersonView == 0) {
            return;
        }
        if (event instanceof EventRender3D) {
            float radius = 0.75f;
            float x = (float)ChinaHat.mc.thePlayer.posX;
            float z = (float)ChinaHat.mc.thePlayer.posZ;
            float y = (float)ChinaHat.mc.thePlayer.posY;
            int i = 0;
            while (i < 360) {
                float dX = radius * (float)Math.cos(Math.toRadians(i));
                float dZ = radius * (float)Math.sin(Math.toRadians(i));
                int radians = i;
                float minus = ChinaHat.mc.thePlayer.isSneaking() ? 0.2f : 0.0f;
                GlStateManager.pushMatrix();
                RenderUtil.draw3dLine(0.0, 2.25, 0.0, dX, 1.9, dZ, ColorUtils.getColorAlpha(ColorUtils.rainBowEffectWithOffset(radians, 3600.0f), 110));
                GlStateManager.popMatrix();
                ++i;
            }
        }
    }
}

