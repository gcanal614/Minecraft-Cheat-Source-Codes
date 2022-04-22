package me.event;

import me.Hime;

public class EventC {
	
	
    public static void subscribe(Object o) {
        Hime.instance.getEventBus().subscribe(o);
    }

    public static void unsubscribe(Object o) {
        Hime.instance.getEventBus().unsubscribe(o);
    }

    public static void dispatch(Event event) {
        Hime.instance.getEventBus().post(event).dispatch();
    } 

}