/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.core.jmx;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.jmx.StatusLoggerAdminMBean;
import org.apache.logging.log4j.status.StatusData;
import org.apache.logging.log4j.status.StatusListener;
import org.apache.logging.log4j.status.StatusLogger;

public class StatusLoggerAdmin
extends NotificationBroadcasterSupport
implements StatusListener,
StatusLoggerAdminMBean {
    private final AtomicLong sequenceNo = new AtomicLong();
    private final ObjectName objectName;
    private Level level = Level.WARN;

    public StatusLoggerAdmin(Executor executor) {
        super(executor, StatusLoggerAdmin.createNotificationInfo());
        try {
            this.objectName = new ObjectName("org.apache.logging.log4j2:type=StatusLogger");
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
        StatusLogger.getLogger().registerListener(this);
    }

    private static MBeanNotificationInfo createNotificationInfo() {
        String[] notifTypes = new String[]{"com.apache.logging.log4j.core.jmx.statuslogger.data", "com.apache.logging.log4j.core.jmx.statuslogger.message"};
        String name = Notification.class.getName();
        String description2 = "StatusLogger has logged an event";
        return new MBeanNotificationInfo(notifTypes, name, "StatusLogger has logged an event");
    }

    @Override
    public String[] getStatusDataHistory() {
        List<StatusData> data2 = this.getStatusData();
        String[] result2 = new String[data2.size()];
        for (int i = 0; i < result2.length; ++i) {
            result2[i] = data2.get(i).getFormattedStatus();
        }
        return result2;
    }

    @Override
    public List<StatusData> getStatusData() {
        return StatusLogger.getLogger().getStatusData();
    }

    @Override
    public String getLevel() {
        return this.level.name();
    }

    @Override
    public Level getStatusLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = Level.toLevel(level, Level.ERROR);
    }

    @Override
    public void log(StatusData data2) {
        Notification notifMsg = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.message", this.getObjectName(), this.nextSeqNo(), this.now(), data2.getFormattedStatus());
        this.sendNotification(notifMsg);
        Notification notifData = new Notification("com.apache.logging.log4j.core.jmx.statuslogger.data", (Object)this.getObjectName(), this.nextSeqNo(), this.now());
        notifData.setUserData(data2);
        this.sendNotification(notifData);
    }

    public ObjectName getObjectName() {
        return this.objectName;
    }

    private long nextSeqNo() {
        return this.sequenceNo.getAndIncrement();
    }

    private long now() {
        return System.currentTimeMillis();
    }
}

