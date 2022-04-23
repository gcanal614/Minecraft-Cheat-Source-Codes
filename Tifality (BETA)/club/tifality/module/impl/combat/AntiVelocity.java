/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.combat;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.server.ServerUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModuleInfo(label="AntiVelocity", category=ModuleCategory.COMBAT)
public final class AntiVelocity
extends Module {
    private final DoubleProperty horizontalPercentProperty = new DoubleProperty("Horizontal", 0.0, 0.0, 100.0, 0.5, Representation.PERCENTAGE);
    private final DoubleProperty verticalPercentProperty = new DoubleProperty("Vertical", 0.0, 0.0, 100.0, 0.5, Representation.PERCENTAGE);

    @Listener
    private void onPacketReceiveEvent(PacketReceiveEvent e) {
        Packet<?> packet = e.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity velocityPacket = (S12PacketEntityVelocity)packet;
            if (velocityPacket.getEntityID() == AntiVelocity.mc.thePlayer.getEntityId()) {
                double verticalPerc = (Double)this.verticalPercentProperty.getValue();
                double horizontalPerc = (Double)this.horizontalPercentProperty.getValue();
                if (verticalPerc == 0.0 && horizontalPerc == 0.0) {
                    e.setCancelled();
                    return;
                }
                velocityPacket.motionX = (int)((double)velocityPacket.motionX * (Double)this.horizontalPercentProperty.getValue() / 100.0);
                velocityPacket.motionY = (int)((double)velocityPacket.motionY * (Double)this.verticalPercentProperty.getValue() / 100.0);
                velocityPacket.motionZ = (int)((double)velocityPacket.motionZ * (Double)this.horizontalPercentProperty.getValue() / 100.0);
            }
        } else if (packet instanceof S27PacketExplosion && ServerUtils.isOnHypixel()) {
            double verticalPerc = (Double)this.verticalPercentProperty.getValue();
            double horizontalPerc = (Double)this.horizontalPercentProperty.getValue();
            if (verticalPerc == 0.0 && horizontalPerc == 0.0) {
                e.setCancelled();
                return;
            }
            S27PacketExplosion packetExplosion = (S27PacketExplosion)packet;
            packetExplosion.motionX = (float)((double)packetExplosion.motionX * (Double)this.horizontalPercentProperty.getValue() / 100.0);
            packetExplosion.motionY = (float)((double)packetExplosion.motionY * (Double)this.verticalPercentProperty.getValue() / 100.0);
            packetExplosion.motionZ = (float)((double)packetExplosion.motionZ * (Double)this.horizontalPercentProperty.getValue() / 100.0);
        }
    }
}

