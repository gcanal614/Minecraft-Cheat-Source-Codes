/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;

@ModuleInfo(label="Inventory", category=ModuleCategory.PLAYER)
public final class InventoryMove
extends Module {
    private final Property<Boolean> cancelPacketProperty = new Property<Boolean>("Packet Test", false);
    public static final Property<Boolean> noMove = new Property<Boolean>("Cancel Inventory", false);

    @Listener
    public void onPacketSendEvent(PacketSendEvent event) {
        if (this.cancelPacketProperty.getValue().booleanValue() && (event.getPacket() instanceof C16PacketClientStatus || event.getPacket() instanceof C0DPacketCloseWindow)) {
            event.setCancelled();
        }
    }
}

