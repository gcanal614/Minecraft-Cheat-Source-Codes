// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import bozoware.base.BozoWare;
import net.minecraft.network.play.server.S02PacketChat;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.RenderNametagEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Name Hider", moduleCategory = ModuleCategory.VISUAL)
public class NameHider extends Module
{
    @EventListener
    EventConsumer<RenderNametagEvent> onRenderNametagEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    
    public NameHider() {
        this.onRenderNametagEvent = (e -> e.setRenderedName("BOZO"));
        S02PacketChat packet;
        String temp;
        String[] array;
        String[] list;
        int length;
        int i = 0;
        String str;
        this.onPacketReceiveEvent = (ep -> {
            if (ep.getPacket() instanceof S02PacketChat) {
                packet = (S02PacketChat)ep.getPacket();
                if (packet.getChatComponent().getUnformattedText().contains(NameHider.mc.thePlayer.getName())) {
                    temp = packet.getChatComponent().getFormattedText();
                    printChat(temp.replaceAll(NameHider.mc.thePlayer.getName(), "§a" + BozoWare.BozoUserName + "§r"));
                    ep.setCancelled(true);
                }
                else {
                    list = (array = new String[] { "join", "left", "leave", "leaving", "lobby", "server", "fell", "died", "slain", "burn", "void", "disconnect", "kill", "by", "was", "quit", "blood", "game" });
                    length = array.length;
                    while (i < length) {
                        str = array[i];
                        if (packet.getChatComponent().getUnformattedText().toLowerCase().contains(str)) {
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
            }
        });
    }
    
    public static void printChat(final String text) {
        NameHider.mc.thePlayer.addChatComponentMessage(new ChatComponentText(text));
    }
}
