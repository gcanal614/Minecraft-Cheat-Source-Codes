package me.util;
public final class TimeUtil {

    private long time = -1L;

    public boolean hasTimePassed(final long ms) {
        return System.currentTimeMillis() >= time + ms;
    }

    public long hasTimeLeft(final long ms) {
        return (ms + time) - System.currentTimeMillis();
    }

    public void reset() {
        time = System.currentTimeMillis();
    }
    
    public long getCurrentMS() {
        return System.currentTimeMillis();
    }
    
    public final long getElapsedTime() {
        return this.getCurrentMS() - this.time;
    }
}