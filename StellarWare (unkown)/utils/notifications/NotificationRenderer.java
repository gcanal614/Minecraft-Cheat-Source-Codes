package stellar.skid.utils.notifications;

import stellar.skid.StellarWare;
import stellar.skid.utils.fonts.impl.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;

import java.awt.*;

public final class NotificationRenderer {

    private static final int RED = new Color(255, 80, 80).getRGB();
    private static final int GREEN = new Color(135, 227, 49).getRGB();
    private static final int ORANGE = new Color(255, 215, 100).getRGB();
    private static final int WHITE = new Color(255, 255, 255).getRGB();

    private static final Minecraft mc = Minecraft.getInstance();
    private static final NotificationManager notificationManager = StellarWare.getInstance().getNotificationManager();

    private static int displayHeight = 0, displayWidth = 0;

    public static void draw(ScaledResolution resolution) {
        if (!notificationManager.getNotifications().isEmpty()) {
            notificationManager.update();
        }

        for (Notification notification : notificationManager.getNotifications()) {

            double x = notification.getX();
            double y = resolution.getScaledHeightStatic(mc) - notification.getY();

            // region notification-rendering
            String callReason = notification.getCallReason() == null ? StringUtils.capitalize(notification.getType().toString()) :
                    notification.getCallReason();
            String message = notification.getMessage();
            String seconds = String.valueOf((notification.getDelay() - notification.getCount()) / 1000.0), formatted = "(" + seconds.substring(0, seconds.indexOf(".") + 2) + "s) ";

            Gui.drawRect(resolution.getScaledWidthStatic(mc) - x - (notification.getNotificationType() == NotificationType.WARNING || notification.getNotificationType() == NotificationType.SUCCESS || notification.getNotificationType() == NotificationType.INFO ? 2 : 0) + 20,
                    y,
                    resolution.getScaledWidthStatic(mc) + 20,
                    y + 24,
                    new Color(0, 0, 0,255).getRGB());

            mc.fontRendererObj.drawString(callReason,resolution.getScaledWidthStatic(mc) - (float)x + 25,(float)y + 2,0xffffffff,true);
            mc.fontRendererObj.drawString(message + " " + formatted,resolution.getScaledWidthStatic(mc) - (float)x + 25,(float)y + 12.5F,new Color(200,200,200,255).getRGB(),true);
            //endregion

            //region timebar-rendering
            double width = notification.getX();
            double w1 = width/100;
            double a = notification.getDelay()/100;
            double perc = (float) (notification.getCount()/a);

            Gui.drawRect(resolution.getScaledWidthStatic(mc) - x - (notification.getNotificationType() == NotificationType.WARNING || notification.getNotificationType() == NotificationType.SUCCESS || notification.getNotificationType() == NotificationType.INFO ? 2 : 0) + 20,
                    y + 23,
                    resolution.getScaledWidthStatic(mc) - x + (perc * w1) + 20,
                    y + 24,
                    getColorForType(notification.getType()));
            //endregion
        }

    }


    private static int getColorForType(NotificationType type) {
        switch (type) { // @off
            case SUCCESS:
                return GREEN;
            case ERROR:
                return RED;
            case WARNING:
                return ORANGE;
            case INFO:
                return WHITE;
        } // @on

        return 0;
    }
}

