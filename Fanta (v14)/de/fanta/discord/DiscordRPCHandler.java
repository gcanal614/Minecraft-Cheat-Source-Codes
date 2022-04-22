/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.discord;

import de.fanta.Client;
import de.fanta.discord.DiscordRPC;

public class DiscordRPCHandler {
    public static final DiscordRPCHandler instance = new DiscordRPCHandler();
    private DiscordRPC discordRPC = new DiscordRPC();
    public static String second = String.valueOf(Client.INSTANCE.getName()) + " " + Client.INSTANCE.getVersion() + " by LCA_MODZ";

    public void init() {
        this.discordRPC.start();
    }

    public void shutdown() {
        this.discordRPC.shutdown();
    }

    public DiscordRPC getDiscordRPC() {
        return this.discordRPC;
    }
}

