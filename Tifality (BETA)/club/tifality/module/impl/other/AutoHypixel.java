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
import club.tifality.module.impl.combat.KillAura;
import club.tifality.module.impl.player.ChestStealer;
import club.tifality.module.impl.player.InventoryCleaner;
import club.tifality.property.Property;
import club.tifality.utils.timer.TimerUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;

@ModuleInfo(label="AutoHypixel", category=ModuleCategory.OTHER)
public final class AutoHypixel
extends Module {
    private final Property<Boolean> respawnProperty = new Property<Boolean>("On Respawn", true);
    private final Property<Boolean> autoReport = new Property<Boolean>("Auto Report", true);
    private final TimerUtil respawnTimer = new TimerUtil();
    private List<Module> disableOnRespawn;
    private final List<UUID> reportedPlayers = new ArrayList<UUID>();

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S07PacketRespawn && this.respawnProperty.getValue().booleanValue()) {
            if (this.respawnTimer.hasElapsed(50L)) {
                boolean msg = false;
                for (Module module : this.disableOnRespawn) {
                    if (!module.isEnabled()) continue;
                    module.toggle();
                    if (msg) continue;
                    msg = true;
                }
                if (msg) {
                    NotificationPublisher.queue("Respawn Detected!", "Disabled some modules on respawn", NotificationType.INFO, 3500);
                    DevNotifications.getManager().post("Disabled some modules on respawn");
                }
                this.respawnTimer.reset();
            }
        } else if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packetChat = (S02PacketChat)event.getPacket();
            String message = packetChat.getChatComponent().getUnformattedText();
            if (packetChat.getChatComponent().getUnformattedText().contains("Protect your bed and destroy the enemy beds")) {
                NotificationPublisher.queue("Warning", "Do not fly until this notification closes", NotificationType.WARNING, 20000);
                DevNotifications.getManager().post("Do not fly until this notification closes");
            }
            for (Object entity : AutoHypixel.mc.theWorld.loadedEntityList) {
                EntityPlayer p;
                if (!(entity instanceof EntityPlayer) || !message.contains((p = (EntityPlayer)entity).getName()) || !message.contains(AutoHypixel.mc.thePlayer.getName()) || !message.contains("killed") && !message.contains("slain") && !message.contains("knocked") && !message.contains("thrown") && !message.contains("foi morto por") || p.getName().equalsIgnoreCase(AutoHypixel.mc.thePlayer.getName())) continue;
                EntityPlayer e = (EntityPlayer)entity;
                if (!this.autoReport.get().booleanValue()) continue;
                AutoHypixel.mc.thePlayer.sendQueue.sendPacket(new C01PacketChatMessage("/report " + e.getName() + " hacking"));
            }
        }
    }

    @Override
    public void onEnable() {
        this.disableOnRespawn = Arrays.asList(ModuleManager.getInstance(KillAura.class), ModuleManager.getInstance(InventoryCleaner.class), ModuleManager.getInstance(ChestStealer.class));
    }
}

