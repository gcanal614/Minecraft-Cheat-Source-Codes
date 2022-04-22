package me.util;

public class MSTimer {
    private long current, last;
    public void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    public void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    public boolean hasTimePassed(long ms) {
        if(current - last > ms) {
            return true;
        }
        return false;
    }
}
