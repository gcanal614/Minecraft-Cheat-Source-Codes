package com.zerosense.Events.impl;

import com.zerosense.Events.Event;

public class EventCancellable extends Event {

    private boolean cancelled;

    protected EventCancellable() {
    }

    /**
     * @see com.zerosense.Events.impl.EventCancellable;
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     */
    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

}
