package com.zerosense.Events.impl;


import com.zerosense.Events.Event;

//EntityPlayerSP
public class EventPushBlock extends Event {
	boolean isPre;
    public void fire(boolean pre) {
    	this.isPre = pre;
    }
    public boolean isPre() {
        return isPre;
    }

}
