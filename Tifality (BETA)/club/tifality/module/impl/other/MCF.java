/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.Tifality;
import club.tifality.gui.notification.client.NotificationPublisher;
import club.tifality.gui.notification.client.NotificationType;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

@ModuleInfo(label="MCF", category=ModuleCategory.OTHER)
public final class MCF
extends Module {
    private boolean down;

    @Listener
    public void onUpdate(UpdatePositionEvent event) {
        if (Mouse.isButtonDown(2) && !this.down) {
            if (MCF.mc.objectMouseOver.entityHit != null) {
                EntityPlayer player = (EntityPlayer)MCF.mc.objectMouseOver.entityHit;
                String playername = player.getName();
                if (!Tifality.INSTANCE.getFriendManager().isFriend(playername)) {
                    MCF.mc.thePlayer.sendChatMessage(".f add " + playername);
                    Wrapper.addChatMessage("Successfully added playername");
                    DevNotifications.getManager().post("Successfully added playername");
                    NotificationPublisher.queue("Middle Click", "Successfully added playername", NotificationType.OKAY, 3000);
                } else {
                    MCF.mc.thePlayer.sendChatMessage(".f remove " + playername);
                    Wrapper.addChatMessage("Successfully removed playername");
                    DevNotifications.getManager().post("Successfully removed playername");
                    NotificationPublisher.queue("Middle Click", "Successfully removed playername", NotificationType.WARNING, 3000);
                }
            }
            this.down = true;
        }
        if (!Mouse.isButtonDown(2)) {
            this.down = false;
        }
    }
}

