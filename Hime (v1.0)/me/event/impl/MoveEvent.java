package me.event.impl;

import me.event.Event;

public class MoveEvent extends Event {

	private double motionX, motionY, motionZ;
	
	public MoveEvent(double motionX, double motionY, double motionZ) {
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public void actualSetSpeedX(double motionX) {
		this.motionX = motionX;
	}
	
	public void actualSetSpeedY(double motionY) {
		this.motionY = motionY;
	}
	
	public void actualSetSpeedZ(double motionZ) {
		this.motionZ = motionZ;
	}
	
	public double getMotionX() {
		return motionX;
	}
	
	public double getMotionY() {
		return motionY;
	}
	
	public double getMotionZ() {
		return motionZ;
	}
	
	public void zero() {
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
	}
	
}
