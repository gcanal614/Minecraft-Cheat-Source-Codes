package me.command.commands;


import io.netty.buffer.Unpooled;
import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ClientBrand extends Command{
    protected Minecraft mc = Minecraft.getMinecraft();
    @Override
    public String getName() {
        return "clientbrand";
    }

    @Override
    public String getDescription() {
        return "changes client brand (kinda like fakeforge)";
    }

    @Override
    public String getSyntax() {
        return ".clientbrand [TEXT]";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {

        Hime.instance.brand = String.join(" ", args);
    
        mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(Hime.instance.brand)));
           Hime.instance.addClientChatMessage("Set Clients brand to: " + String.join(" ", args));
    }
}