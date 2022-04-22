package com.zerosense.Utils;

public class Timer {


    private long prevMS;

    public void setTime(long time) {
        lastMS = time;
    }


    public Timer() {
        this.prevMS = 0L;
    }

    public long getDifference() {
        return getTime() - this.prevMS;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public long lastMS = System.currentTimeMillis();

    public void reset(){
        lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset){
        if(System.currentTimeMillis()-lastMS > time){
            if(reset)
                reset();

            return true;
        }

        return false;
    }

    public void clear() {
    }

    public boolean delay(int milliSec) {
        return (float) (getTime() - this.prevMS) >= milliSec;
    }
}
