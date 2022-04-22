package com.zerosense.Events.impl;


import com.zerosense.Events.Event;

public class EventChat extends Event<EventChat> {
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EventChat(String message) {
        this.message = message;
    }
}
