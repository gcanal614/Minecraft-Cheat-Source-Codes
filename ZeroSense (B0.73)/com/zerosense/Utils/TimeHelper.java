package com.zerosense.Utils;

public class TimeHelper {
    private long lastMS = 0L;

    public int convertToMS(int d) {
        return 1000 / d;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(long milliseconds) {
        return getCurrentMS() - lastMS >= milliseconds;
    }

    public boolean hasTimeReached(long delay){
        return System.currentTimeMillis() - lastMS >= delay;
    }

    public long getDelay(){
        return System.currentTimeMillis() - lastMS;
    }

    public void reset(){
        lastMS = getCurrentMS();
    }

    public void setLastMS(){
        lastMS = System.currentTimeMillis();
    }

    public void setlastMS(long lastMS){
        this.lastMS = lastMS;
    }

    public boolean hasTimeElapsed(double value) {
        return hasTimeReached((long) value);
    }

	public boolean invCooldownElapsed(long l) {
		// TODO Auto-generated method stub
		return false;
	}
}