package com.zerosense;

import com.google.common.eventbus.SubscriberExceptionContext;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.Display;

public class ZeroSense {

    public static String NAME = "MikronClient"; // Zerosense
    public static String VERSION = "b0.1"; // B0.73
    public static String CREATOR = "mikron & VOUR"; //Aloneee
    public static String REALSED = "Owner";


    // client by me and alone <33333
    // Client base by Aloneee <3

    public static void StartingClient() {
        Display.setTitle("[" + NAME + "] - " + VERSION + " by " + CREATOR + " - " + REALSED);

    }


    public static void addChatMessage(String message) {
        message = "[ " + NAME + " ] : " + message;
        (Minecraft.getMinecraft()).thePlayer.addChatMessage((IChatComponent) new ChatComponentText(message));
    }


}