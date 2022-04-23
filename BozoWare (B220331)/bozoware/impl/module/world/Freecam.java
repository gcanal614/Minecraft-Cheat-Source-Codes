// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import bozoware.base.util.player.MovementUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import bozoware.base.util.Wrapper;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import bozoware.impl.event.block.EventAABB;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.PlayerPushEvent;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.ValueProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Freecam", moduleCategory = ModuleCategory.WORLD)
public class Freecam extends Module
{
    private final ValueProperty<Double> Speed;
    @EventListener
    EventConsumer<PlayerPushEvent> onPlayerPushEvent;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<EventAABB> onEventAABB;
    public EntityOtherPlayerMP copy;
    
    public Freecam() {
        this.Speed = new ValueProperty<Double>("Freecam Speed", 1.0, 0.1, 5.0, this);
        final EntityOtherPlayerMP copy;
        this.onModuleEnabled = (() -> {
            Wrapper.getPlayer().motionY = 0.0;
            copy = new EntityOtherPlayerMP(Freecam.mc.theWorld, Freecam.mc.thePlayer.getGameProfile());
            (this.copy = copy).clonePlayer(Freecam.mc.thePlayer, true);
            this.copy.setLocationAndAngles(Freecam.mc.thePlayer.posX, Freecam.mc.thePlayer.posY, Freecam.mc.thePlayer.posZ, Freecam.mc.thePlayer.rotationYaw, Freecam.mc.thePlayer.rotationPitch);
            this.copy.rotationYawHead = Freecam.mc.thePlayer.rotationYawHead;
            this.copy.setEntityId(-6969);
            this.copy.setSneaking(Freecam.mc.thePlayer.isSneaking());
            Freecam.mc.theWorld.addEntityToWorld(this.copy.getEntityId(), this.copy);
            return;
        });
        this.onModuleDisabled = (() -> {
            MovementUtil.setSpeed(0.0);
            if (this.copy != null && Freecam.mc.theWorld != null && Freecam.mc.thePlayer != null) {
                Freecam.mc.thePlayer.setLocationAndAngles(this.copy.posX, this.copy.posY, this.copy.posZ, this.copy.rotationYaw, this.copy.rotationPitch);
                Freecam.mc.thePlayer.rotationYawHead = this.copy.rotationYawHead;
                Freecam.mc.theWorld.removeEntityFromWorld(this.copy.getEntityId());
                Freecam.mc.thePlayer.setSneaking(this.copy.isSneaking());
                this.copy = null;
                Freecam.mc.renderGlobal.loadRenderers();
            }
            return;
        });
        this.onPacketSendEvent = (e -> {
            if (e.getPacket() instanceof C03PacketPlayer || e.getPacket() instanceof C02PacketUseEntity) {
                e.setCancelled(true);
            }
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            Freecam.mc.thePlayer.noClip = true;
            Freecam.mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
            Wrapper.getPlayer().motionY = 0.0;
            if (Freecam.mc.thePlayer.movementInput.jump) {
                Wrapper.getPlayer().motionY = this.Speed.getPropertyValue() / 2.0;
            }
            if (Freecam.mc.thePlayer.movementInput.sneak) {
                Wrapper.getPlayer().motionY = -this.Speed.getPropertyValue() / 2.0;
            }
            MovementUtil.setMoveSpeed(this.Speed.getPropertyValue());
            return;
        });
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof C03PacketPlayer || e.getPacket() instanceof C02PacketUseEntity) {
                e.setCancelled(true);
            }
            return;
        });
        this.onEventAABB = (e -> e.setBoundingBox(null));
        this.onPlayerPushEvent = (e -> e.setCancelled(true));
    }
}
