/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public interface MinecraftUtil {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final WorldClient world = MinecraftUtil.mc.theWorld;

    default public EntityPlayerSP getPlayer() {
        return MinecraftUtil.mc.thePlayer;
    }

    default public void resetRotation(float yaw) {
        this.getPlayer().rotationYaw = yaw - yaw % 360.0f + this.getPlayer().rotationYaw % 360.0f;
    }
}

