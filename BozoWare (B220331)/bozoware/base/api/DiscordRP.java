// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.api;

import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.arikia.dev.drpc.DiscordEventHandlers;

public class DiscordRP
{
    public static boolean running;
    private static long created;
    
    public static void start() {
        DiscordRP.created = System.currentTimeMillis();
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(final DiscordUser user) {
                System.out.println("Welcome " + user.username + "#" + user.discriminator);
                DiscordRP.update("Loading Bozoware", "");
            }
        }).build();
        DiscordRPC.discordInitialize("913142366003687485", handlers, true);
        new Thread("DiscordRPC Callback") {
            @Override
            public void run() {
                while (DiscordRP.running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }
    
    public void shutdown() {
        DiscordRP.running = false;
        DiscordRPC.discordShutdown();
    }
    
    public static void update(final String firstLine, final String secondLine) {
        final DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("bozoheart512x", "Hello Bozo!");
        b.setSmallImage("bozoblack", "by Kobley, Posk, and Shidder");
        b.setDetails(firstLine);
        b.setStartTimestamps(DiscordRP.created);
        DiscordRPC.discordUpdatePresence(b.build());
    }
    
    public void onEnable() {
        DiscordRP.running = true;
        DiscordRPC.discordRunCallbacks();
    }
    
    public void onDisable() {
        DiscordRP.running = false;
        DiscordRPC.discordShutdown();
    }
    
    static {
        DiscordRP.running = true;
        DiscordRP.created = 0L;
    }
}
