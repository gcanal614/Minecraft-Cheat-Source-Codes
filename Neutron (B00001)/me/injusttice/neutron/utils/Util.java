package me.injusttice.neutron.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;

public class Util {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static FontRenderer fr = mc.fontRendererObj;

    public static EntityPlayerSP getPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }

    public static EntityPlayerSP player() {
        return mc.thePlayer;
    }

    public static PlayerControllerMP playerController() {
        return mc.playerController;
    }

    public static WorldClient world() {
        return mc.theWorld;
    }
}
