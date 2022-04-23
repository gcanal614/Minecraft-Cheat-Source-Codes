// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.notifications;

import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager
{
    public static LinkedBlockingQueue<Notification> pendingNotifications;
    public static Notification currentNotification;
    
    public static void show(final Notification notification) {
        NotificationManager.pendingNotifications.add(notification);
    }
    
    public static void update() {
        if (NotificationManager.currentNotification != null && !NotificationManager.currentNotification.isShown()) {
            NotificationManager.currentNotification = null;
        }
        if (NotificationManager.currentNotification == null && !NotificationManager.pendingNotifications.isEmpty()) {
            (NotificationManager.currentNotification = NotificationManager.pendingNotifications.poll()).show();
        }
    }
    
    public static void render() {
        update();
        if (NotificationManager.currentNotification != null) {
            NotificationManager.currentNotification.render();
        }
    }
    
    static {
        NotificationManager.pendingNotifications = new LinkedBlockingQueue<Notification>();
        NotificationManager.currentNotification = null;
    }
}
