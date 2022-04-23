// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.notifications;

import java.util.function.Function;

public enum NotificationType
{
    INFO(time -> -11900679), 
    WARNING(time -> -40960), 
    ERROR(time -> -65536), 
    SUCCESS(time -> -16711936);
    
    private final Function<Long, Integer> getColorFunc;
    
    private NotificationType(final Function<Long, Integer> getColorFunc) {
        this.getColorFunc = getColorFunc;
    }
    
    public int getColor(final long time) {
        return this.getColorFunc.apply(time);
    }
}
