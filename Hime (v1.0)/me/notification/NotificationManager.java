package me.notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {
    private static final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<>();
	public static NotificationManager instance;
    private static LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<Notification>();
    private static Notification currentNotification = null;

    public static void show(Notification notification) {
        pendingNotifications.add(notification);
    }
    public static void clear() {
        pendingNotifications.clear();
    }
    public int getNotificationsLeft() {
       return pendingNotifications.size();
    }
    public static void update() {
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }

    }

    public static void render() {
        update();

        if (currentNotification != null)
                 currentNotification.render();
    }

    public static List<Notification> getNotifications() {
        return NOTIFICATIONS;
    }
}
