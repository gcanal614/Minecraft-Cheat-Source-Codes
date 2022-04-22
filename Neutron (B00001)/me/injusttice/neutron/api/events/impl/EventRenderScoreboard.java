package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;

public class EventRenderScoreboard extends Event {
    State state;

    public EventRenderScoreboard(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public boolean isPre(){
        return this.state == State.PRE;
    }

    public void setState(State state) {
        this.state = state;
    }
}
