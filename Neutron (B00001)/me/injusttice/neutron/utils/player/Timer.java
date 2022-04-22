package me.injusttice.neutron.utils.player;

public class Timer {

    public Timer() {
        reset1();
    }

    public long lastMS = System.currentTimeMillis();

    private long lastTime = getCurrentTime();

    public void reset1() {
        lastMS = System.currentTimeMillis();
    }

    public long getCurrentTime() {
        return System.nanoTime() / 1000000;
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getDifference() {
        return getCurrentTime() - lastTime;
    }

    public void reset() {
        lastTime = getCurrentTime();
    }

    public boolean hasReached(long milliseconds) {
        return getDifference() >= milliseconds;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if(System.currentTimeMillis()-lastMS > time) {
            if(reset)
                reset1();

            return true;
        }

        return false;
    }
}