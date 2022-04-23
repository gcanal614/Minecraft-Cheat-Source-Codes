/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.notification;

import club.tifality.Tifality;
import club.tifality.gui.notification.Notification;
import club.tifality.utils.handler.Manager;
import club.tifality.utils.render.LockedResolution;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;

public final class NotificationManager
extends Manager<Notification> {
    public NotificationManager() {
        super(new ArrayList());
        Tifality.getInstance().getEventBus().subscribe(this);
    }

    public void render(ScaledResolution scaledResolution, LockedResolution lockedResolution, boolean inGame, int yOffset) {
        List notifications = this.getElements();
        Notification remove = null;
        for (int i = 0; i < notifications.size(); ++i) {
            Notification notification = (Notification)notifications.get(i);
            if (notification.isDead()) {
                remove = notification;
                continue;
            }
            notification.render(lockedResolution, scaledResolution, i + 1, yOffset);
        }
        if (remove != null) {
            this.getElements().remove(remove);
        }
    }

    public void add(Notification notification) {
        this.getElements().add(notification);
    }
}

