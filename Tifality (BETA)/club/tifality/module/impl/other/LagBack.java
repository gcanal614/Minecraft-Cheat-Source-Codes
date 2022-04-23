/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.gui.notification.client.NotificationPublisher;
import club.tifality.gui.notification.client.NotificationType;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.module.impl.movement.Flight;
import club.tifality.module.impl.movement.Speed;
import club.tifality.property.Property;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(label="LagBack", category=ModuleCategory.OTHER)
public final class LagBack
extends Module {
    private final Property<Boolean> noti = new Property<Boolean>("Notification", true);
    private List<Module> movementModules;

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            boolean msg = false;
            for (Module module : this.movementModules) {
                if (!module.isEnabled()) continue;
                module.toggle();
                if (msg) continue;
                msg = true;
            }
            if (msg && this.noti.getValue().booleanValue()) {
                NotificationPublisher.queue("Lag Back!", "Disabling modules to prevent flags", NotificationType.WARNING, 3000);
                DevNotifications.getManager().post("Disabling modules to prevent flags");
            }
        }
    }

    @Override
    public void onEnable() {
        this.movementModules = Arrays.asList(ModuleManager.getInstance(Speed.class), ModuleManager.getInstance(Flight.class));
    }
}

