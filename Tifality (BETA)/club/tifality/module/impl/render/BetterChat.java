/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.Wrapper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

@ModuleInfo(label="BetterChat", category=ModuleCategory.RENDER)
public final class BetterChat
extends Module {
    private String lastMessage = "";
    private int amount;
    private int line;

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent e) {
        S02PacketChat s02PacketChat;
        Packet<?> packet = e.getPacket();
        if (packet instanceof S2EPacketCloseWindow) {
            if (this.isTypingInChat()) {
                e.setCancelled();
            }
        } else if (packet instanceof S02PacketChat && (s02PacketChat = (S02PacketChat)packet).getType() == 0) {
            IChatComponent message = s02PacketChat.getChatComponent();
            String rawMessage = message.getFormattedText();
            GuiNewChat chatGui = Wrapper.getMinecraft().ingameGUI.getChatGUI();
            if (this.lastMessage.equals(rawMessage)) {
                chatGui.deleteChatLine(this.line);
                ++this.amount;
                s02PacketChat.getChatComponent().appendText((Object)((Object)EnumChatFormatting.GRAY) + " [x" + this.amount + "]");
            } else {
                this.amount = 1;
            }
            ++this.line;
            this.lastMessage = rawMessage;
            chatGui.printChatMessageWithOptionalDeletion(message, this.line);
            if (this.line > 256) {
                this.line = 0;
            }
            e.setCancelled();
        }
    }

    private boolean isTypingInChat() {
        return Wrapper.getCurrentScreen() instanceof GuiChat;
    }
}

