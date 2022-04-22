package me.event.impl;

import me.event.Event;

public class EventRenderScoreboard extends Event {
	
	 public EventRenderScoreboard() {
       this.pre = true;
     }
	 
	 public EventRenderScoreboard(boolean cancel) {
        this.pre = false;
     }
	
	 public boolean isPre() {
	    return pre;
	 }

	 public boolean isPost() {
	    return !pre;
	 }
	 
}
