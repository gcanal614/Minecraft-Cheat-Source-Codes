// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Blink", moduleCategory = ModuleCategory.PLAYER)
public class Blink extends Module
{
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    public EntityOtherPlayerMP copy;
    
    public Blink() {
        final EntityOtherPlayerMP copy;
        this.onModuleEnabled = (() -> {
            copy = new EntityOtherPlayerMP(Blink.mc.theWorld, Blink.mc.thePlayer.getGameProfile());
            (this.copy = copy).clonePlayer(Blink.mc.thePlayer, true);
            this.copy.setLocationAndAngles(Blink.mc.thePlayer.posX, Blink.mc.thePlayer.posY, Blink.mc.thePlayer.posZ, Blink.mc.thePlayer.rotationYaw, Blink.mc.thePlayer.rotationPitch);
            this.copy.rotationYawHead = Blink.mc.thePlayer.rotationYawHead;
            this.copy.setEntityId(-6969);
            this.copy.setSneaking(Blink.mc.thePlayer.isSneaking());
            return;
        });
        this.onModuleDisabled = (() -> {
            if (this.copy != null && Blink.mc.theWorld != null && Blink.mc.thePlayer != null) {
                Blink.mc.thePlayer.setLocationAndAngles(this.copy.posX, this.copy.posY, this.copy.posZ, this.copy.rotationYaw, this.copy.rotationPitch);
                Blink.mc.thePlayer.rotationYawHead = this.copy.rotationYawHead;
                Blink.mc.theWorld.removeEntityFromWorld(this.copy.getEntityId());
                Blink.mc.thePlayer.setSneaking(this.copy.isSneaking());
                this.copy = null;
                Blink.mc.renderGlobal.loadRenderers();
            }
            return;
        });
        this.onPacketSendEvent = (e -> {
            if (e.getPacket() instanceof C03PacketPlayer || e.getPacket() instanceof C02PacketUseEntity) {
                e.setCancelled(true);
            }
            return;
        });
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof C03PacketPlayer || e.getPacket() instanceof C02PacketUseEntity) {
                e.setCancelled(true);
            }
        });
    }
}
