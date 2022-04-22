package me.module.impl.player;



import me.event.impl.EventReceivePacket;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module{
    public NoRotate() {
        super("NoRotate", 0, Category.PLAYER);
    }
    @Override
    public void setup() {
    }
     @Handler
     public void onPacketReceive(EventReceivePacket eventPacketReceive) {
            if(eventPacketReceive.getPacket() instanceof S08PacketPlayerPosLook) {
              S08PacketPlayerPosLook packetPlayerPosLook = (S08PacketPlayerPosLook) eventPacketReceive.getPacket();
              packetPlayerPosLook.setYaw(mc.thePlayer.rotationYaw);
              packetPlayerPosLook.setPitch(mc.thePlayer.rotationPitch);
            }
        }
}