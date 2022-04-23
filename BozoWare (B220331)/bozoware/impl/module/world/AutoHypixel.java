// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S02PacketChat;
import bozoware.impl.event.world.onWorldLoadEvent;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Auto Hypixel", moduleCategory = ModuleCategory.WORLD)
public class AutoHypixel extends Module
{
    private final BooleanProperty antiLimbo;
    private final BooleanProperty autoLeave;
    TimerUtil timer;
    public static boolean BOZO;
    public int time;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    @EventListener
    EventConsumer<onWorldLoadEvent> onLoadWorldEvent;
    
    public AutoHypixel() {
        this.antiLimbo = new BooleanProperty("Limbo Quit", true, this);
        this.autoLeave = new BooleanProperty("Auto Leave on Ban", true, this);
        this.timer = new TimerUtil();
        this.time = (int)System.currentTimeMillis();
        this.onLoadWorldEvent = (e -> {});
        S02PacketChat s02;
        S45PacketTitle s3;
        String message;
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() != null && e.getPacket() instanceof S02PacketChat) {
                s02 = (S02PacketChat)e.getPacket();
                if (s02.getChatComponent().getUnformattedText() != null) {
                    if (this.antiLimbo.getPropertyValue() && (s02.getChatComponent().getUnformattedText().contains("You were spawned in Limbo.") || s02.getChatComponent().getUnformattedText().contains("/limbo for more information."))) {
                        AutoHypixel.mc.thePlayer.sendChatMessage("/play solo_insane");
                    }
                    if (this.autoLeave.getPropertyValue() && s02.getChatComponent().getUnformattedText().contains("A player has been removed")) {
                        AutoHypixel.mc.thePlayer.sendChatMessage("/play solo_insane");
                    }
                }
            }
            if (e.getPacket() != null && e.getPacket() instanceof S45PacketTitle) {
                s3 = (S45PacketTitle)e.getPacket();
                if (s3.getMessage() != null) {
                    message = s3.getMessage().getUnformattedText();
                    if (message.contains("YOU DIED!") || message.contains("GAME END") || message.contains("VICTORY!") || message.contains("You are now a spectator!")) {
                        AutoHypixel.mc.thePlayer.sendChatMessage("/play solo_insane");
                    }
                }
            }
            return;
        });
        this.onRender2DEvent = (e -> {
            if (AutoHypixel.BOZO || this.timer.hasReached(3000L)) {}
        });
    }
    
    static {
        AutoHypixel.BOZO = true;
    }
}
