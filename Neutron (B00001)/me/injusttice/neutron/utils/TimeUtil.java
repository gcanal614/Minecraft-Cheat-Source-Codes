package me.injusttice.neutron.utils;

public final class TimeUtil {

    private long time = -1L;

    public boolean hasTimePassed(final long ms) {
        return System.currentTimeMillis() >= time + ms;
    }

    public long hasTimeLeft(final long ms) {
        return (ms + time) - System.currentTimeMillis();
    }
    
    public long timePassed() {
        return System.currentTimeMillis() - time;
    }

    public void reset() {
        time = System.currentTimeMillis();
    }
}