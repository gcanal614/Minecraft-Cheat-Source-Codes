package com.zerosense.Utils;

public class TimerUtils {

	private long lastMS;

	public TimerUtils() {
		this.reset();
	}

	public boolean reach(final long time) {
		return time() >= time;
	}

	public long time() {
		return System.nanoTime() / 1000000L - lastMS;
	}


	public final long getElapsedTime() {
		return this.getCurrentMS() - this.lastMS;
	}

	private long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}

	public boolean hasReached(final double milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}

	public void reset() {
		this.lastMS = this.getCurrentMS();
	}

	public boolean delay(final float milliSec) {
		return this.getTime() - this.lastMS >= milliSec;
	}

	public long getTime() {
		return System.nanoTime() / 1000000L;
	}

}
