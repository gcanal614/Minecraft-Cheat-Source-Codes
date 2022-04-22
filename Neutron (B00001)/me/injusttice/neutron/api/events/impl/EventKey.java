package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EventKey extends Event {

    public int key;

    public EventKey(int key){
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }
}
