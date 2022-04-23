// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.network.Packet;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.client.C01PacketChatMessage;
import bozoware.impl.property.EnumProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Chat Bypass", moduleCategory = ModuleCategory.PLAYER)
public class ChatBypass extends Module
{
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    private final EnumProperty<bypassModes> bypassMode;
    
    public ChatBypass() {
        this.bypassMode = new EnumProperty<bypassModes>("Mode", bypassModes.Invis, this);
        final StringBuilder stringBuilder = new StringBuilder();
        C01PacketChatMessage c01;
        String firstLetter;
        char[] array;
        final StringBuilder sb;
        int i;
        char addChar;
        int j;
        char addChar2;
        this.onPacketSendEvent = (e -> {
            if (e.getPacket() instanceof C01PacketChatMessage) {
                c01 = (C01PacketChatMessage)e.getPacket();
                firstLetter = c01.getMessage().charAt(0) + "";
                if (!firstLetter.equals("/")) {
                    if (!firstLetter.equals(".")) {
                        e.setCancelled(true);
                        array = c01.getMessage().toCharArray();
                        sb.delete(0, sb.toString().length());
                        switch (this.bypassMode.getPropertyValue()) {
                            case Invis: {
                                for (i = 0; i < c01.getMessage().length(); ++i) {
                                    addChar = array[i];
                                    sb.append(addChar).append("\u200b");
                                }
                                break;
                            }
                            case Dots: {
                                for (j = 0; j < c01.getMessage().length(); ++j) {
                                    addChar2 = array[j];
                                    sb.append(addChar2).append(".");
                                }
                                break;
                            }
                        }
                        Wrapper.sendPacketDirect(new C01PacketChatMessage(sb.toString()));
                    }
                }
            }
        });
    }
    
    public enum bypassModes
    {
        Invis, 
        Dots;
    }
}
