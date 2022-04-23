// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "KillSays", moduleCategory = ModuleCategory.PLAYER)
public class KillSays extends Module
{
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    private int messageIndex;
    private final String[] MESSAGES;
    
    public KillSays() {
        this.MESSAGES = new String[] { "%s should've used BozoWare", "%s, bozoware > ur shiddy paste", "%s ask me what dn is. I dare you", "%s COPE!", "sorry nn, this bypass value is exclusive", "SHIDDER HVH > YOU | Quit.#1706 UID 0000 ON KCATS JAJAJA" };
        S02PacketChat packet;
        String text;
        this.onPacketReceiveEvent = (event -> {
            if (event.getPacket() instanceof S02PacketChat) {
                packet = (S02PacketChat)event.getPacket();
                text = packet.getChatComponent().getUnformattedText();
                if (text.contains("by " + Minecraft.getMinecraft().thePlayer.getName()) && !text.contains(":")) {
                    if (this.messageIndex >= this.MESSAGES.length) {
                        this.messageIndex = 0;
                    }
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(String.format(this.MESSAGES[this.messageIndex], text.split(" ")[0]));
                    ++this.messageIndex;
                }
            }
        });
    }
}
