// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import bozoware.base.util.visual.BlurUtil;
import bozoware.base.util.visual.RenderUtil;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import bozoware.impl.event.world.onWorldLoadEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.util.visual.Animate.Animate;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "SessionInfo", moduleCategory = ModuleCategory.VISUAL)
public class SessionInfo extends Module
{
    public static int wins;
    public static int bruh;
    public static int kills;
    public static String name;
    Animate anim;
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<onWorldLoadEvent> onLoadWorldEvent;
    
    public SessionInfo() {
        this.anim = new Animate();
        this.onModuleEnabled = (() -> {
            SessionInfo.name = SessionInfo.mc.thePlayer.getName();
            this.anim.reset();
            return;
        });
        this.onLoadWorldEvent = (e -> {});
        S45PacketTitle s45;
        String message;
        S02PacketChat s46;
        this.onPacketReceiveEvent = (rece -> {
            if (rece.getPacket() != null) {
                if (rece.getPacket() instanceof S45PacketTitle) {
                    s45 = (S45PacketTitle)rece.getPacket();
                    if (s45.getMessage() != null) {
                        message = s45.getMessage().getUnformattedText();
                        if (message.contains("VICTORY!")) {
                            ++SessionInfo.wins;
                        }
                    }
                }
                if (rece.getPacket() instanceof S02PacketChat) {
                    s46 = (S02PacketChat)rece.getPacket();
                    if (s46.getChatComponent().getUnformattedText() != null) {
                        if (((S02PacketChat)rece.getPacket()).getChatComponent().getUnformattedText().contains("by " + SessionInfo.mc.thePlayer.getName()) && !((S02PacketChat)rece.getPacket()).getChatComponent().getUnformattedText().contains(":")) {
                            ++SessionInfo.kills;
                        }
                    }
                }
            }
            return;
        });
        final ScaledResolution sr;
        final int diff;
        this.onRender2DEvent = (e -> {
            sr = new ScaledResolution(SessionInfo.mc, SessionInfo.mc.displayWidth, SessionInfo.mc.displayHeight);
            if (!Objects.equals(SessionInfo.mc.thePlayer.getName(), SessionInfo.name)) {
                SessionInfo.bruh = (int)System.currentTimeMillis();
                SessionInfo.wins = 0;
                SessionInfo.kills = 0;
                SessionInfo.name = SessionInfo.mc.thePlayer.getName();
            }
            RenderUtil.drawSmoothRoundedRect(2.0f, 33.0f, 100.0f, 78.0f, 15.0f, 1880561431);
            RenderUtil.drawRoundedOutline(2.0f, 33.0f, 100.0f, 78.0f, -16777216, 3.0f, 2.0f);
            RenderUtil.drawRoundedOutline(2.0f, 33.0f, 100.0f, 78.0f, -12829636, 2.0f, 2.0f);
            RenderUtil.drawRoundedOutline(2.0f, 33.0f, 100.0f, 78.0f, -14145496, 0.8f, 2.0f);
            BlurUtil.blurArea(2.0, 33.0, 98.0, 45.0);
            BozoWare.getInstance().getFontManager().smallCSGORenderer.drawStringWithShadow("Session Info", 30.0, 39.0, -1);
            diff = (int)System.currentTimeMillis() - SessionInfo.bruh;
            BozoWare.getInstance().getFontManager().smallCSGORenderer.drawStringWithShadow("Session Time: " + diff / 3600000 % 24 + "h " + diff / 60000 % 60 + "m " + diff / 1000 % 60 + "s", 7.0, 49.0, -1);
            BozoWare.getInstance().getFontManager().smallCSGORenderer.drawStringWithShadow("Wins: " + SessionInfo.wins, 7.0, 59.0, -1);
            BozoWare.getInstance().getFontManager().smallCSGORenderer.drawStringWithShadow("Kills: " + SessionInfo.kills, 7.0, 69.0, -1);
        });
    }
    
    static {
        SessionInfo.wins = 0;
        SessionInfo.bruh = (int)System.currentTimeMillis();
        SessionInfo.kills = 0;
    }
}
