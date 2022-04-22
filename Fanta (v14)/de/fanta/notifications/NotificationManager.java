/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.notifications;

import de.fanta.notifications.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static List<Notification> notis = new ArrayList<Notification>();
    private static Notification current = null;

    public static void addNotificationToQueue(Notification noti) {
        notis.add(noti);
    }

    public static void removeFirst() {
        if (notis.isEmpty()) {
            return;
        }
        notis.remove(0);
        current = null;
    }

    private static void update() {
        if (!notis.isEmpty()) {
            if (current == null) {
                current = notis.get(0);
            }
            if (current != null && !current.isShowing() && !current.isStarted()) {
                current.setShowing(true);
                current.setStarted(true);
            }
            if (current != null && !current.isShowing() && current.isStarted()) {
                NotificationManager.removeFirst();
            }
        }
    }

    public static void render() {
        NotificationManager.update();
        if (!notis.isEmpty() && NotificationManager.getCurrent() != null) {
            NotificationManager.getCurrent().draw();
        }
    }

    public static Notification getCurrent() {
        return current;
    }
}

