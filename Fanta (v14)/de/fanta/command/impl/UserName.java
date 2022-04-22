/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.command.Command;
import de.fanta.utils.ChatUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.Minecraft;

public class UserName
extends Command {
    public UserName() {
        super("setUserName");
    }

    @Override
    public void execute(String[] args) {
        try {
            UserName.setUsername(args[0]);
            ChatUtil.sendChatMessage(args[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void setUsername(String string) {
        Thread t = new Thread(() -> {
            try {
                ChatUtil.sendChatMessageWithPrefix("\u00a77Trying to set username...");
                URL url = new URL("https://api.minecraftservices.com/minecraft/profile/name/" + string);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);
                conn.setRequestProperty("Authorization", "Bearer " + Minecraft.getMinecraft().getSession().getToken());
                conn.setRequestProperty("Content-Type", "application/json");
                ChatUtil.sendChatMessageWithPrefix("Response: " + conn.getResponseCode() + " " + conn.getResponseMessage());
                if (conn.getResponseMessage().equals("OK")) {
                    ChatUtil.sendChatMessageWithPrefix("\u00a7aUsername was set!");
                } else {
                    ChatUtil.sendChatMessageWithPrefix("\u00a7cUsername couldn't be set!");
                }
                conn.disconnect();
            }
            catch (IOException ex) {
                System.err.println("Username couldn't be set!");
                ex.printStackTrace();
            }
        });
        t.start();
    }
}

