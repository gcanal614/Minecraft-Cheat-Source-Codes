package me.injusttice.neutron.api.events.impl;

public class EventChat extends EventNigger<EventChat> {

    public String message;

    public EventChat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
