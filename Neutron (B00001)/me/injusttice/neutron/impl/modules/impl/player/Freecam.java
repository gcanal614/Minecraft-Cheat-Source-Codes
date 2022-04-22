package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Freecam extends Module {
    
    public DoubleSet speed = new DoubleSet("Speed", 2.0, 0.1, 10.0, 0.1);
    public double oldX;
    public double oldY;
    public double oldZ;
    public float oldYaw;
    public float oldPitch;
    public EntityOtherPlayerMP player;
    
    public Freecam() {
        super("Freecam", 0, Category.EXPLOIT);
        addSettings(speed);
    }
    
    @Override
    public void onEnable() {
        oldX = mc.thePlayer.posX;
        oldY = mc.thePlayer.posY;
        oldZ = mc.thePlayer.posZ;
        oldYaw = mc.thePlayer.rotationYaw;
        oldPitch = mc.thePlayer.rotationPitch;
        (player = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile())).copyLocationAndAnglesFrom(mc.thePlayer);
        player.rotationYawHead = mc.thePlayer.rotationYawHead;
        player.renderYawOffset = mc.thePlayer.renderYawOffset;
        mc.theWorld.addEntityToWorld(-6969, player);
        mc.thePlayer.noClip = true;
    }
    
    @Override
    public void onDisable() {
        mc.thePlayer.setPosition(oldX, oldY, oldZ);
        mc.thePlayer.rotationYaw = oldYaw;
        mc.thePlayer.rotationPitch = oldPitch;
        mc.thePlayer.motionX = 0.0;
        mc.thePlayer.motionY = 0.0;
        mc.thePlayer.motionZ = 0.0;
        mc.theWorld.removeEntity(player);
        player = null;
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.thePlayer.noClip = true;
        mc.thePlayer.fallDistance = 0.0f;
        mc.thePlayer.motionX = 0.0;
        mc.thePlayer.motionY = 0.0;
        mc.thePlayer.motionZ = 0.0;
        MovementUtils.strafe((float)speed.getValue());
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            EntityPlayerSP thePlayer3;
            EntityPlayerSP thePlayer = thePlayer3 = mc.thePlayer;
            thePlayer3.motionY += 0.5;
        }
        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            EntityPlayerSP thePlayer4;
            EntityPlayerSP thePlayer2 = thePlayer4 = mc.thePlayer;
            thePlayer4.motionY -= 0.5;
        }
    }
    
    @EventTarget
    public void onSend(EventSendPacket e) {
        if (e.getPacket() instanceof C03PacketPlayer || e.getPacket() instanceof C0BPacketEntityAction) {
            e.setCancelled(true);
        }
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            oldX = ((S08PacketPlayerPosLook)e.getPacket()).getX();
            oldY = ((S08PacketPlayerPosLook)e.getPacket()).getY();
            oldZ = ((S08PacketPlayerPosLook)e.getPacket()).getY();
            oldYaw = ((S08PacketPlayerPosLook)e.getPacket()).getYaw();
            oldPitch = ((S08PacketPlayerPosLook)e.getPacket()).getPitch();
            player.posX = oldX;
            player.posY = oldY;
            player.posZ = oldZ;
            player.rotationYaw = oldYaw;
            player.rotationPitch = oldPitch;
            e.setCancelled(true);
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(oldX, oldY, oldZ, oldYaw, oldPitch, false));
        }
        if (e.getPacket() instanceof S07PacketRespawn) {
            toggled = false;
        }
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            EntityOtherPlayerMP player = null;
            EntityOtherPlayerMP playerMP = player = player;
            player.motionY += 4.0;
        }
    }
}
