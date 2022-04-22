/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import de.fanta.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
    public static void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void sendChatMessageWithPrefix(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7f" + Client.INSTANCE.name + "\u00a77]\u00a7f " + message));
    }

    public static void messageWithoutPrefix(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void sendChatInfo(String string) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7f" + Client.INSTANCE.name + "\u00a77]\u00a7a " + string));
    }

    public static void sendChatError(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7f" + Client.INSTANCE.name + "\u00a77]\u00a7c " + message));
    }
}

