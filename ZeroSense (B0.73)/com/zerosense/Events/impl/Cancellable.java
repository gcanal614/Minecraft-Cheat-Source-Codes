package com.zerosense.Events.impl;

public class Cancellable {

    private boolean canceled;

    public boolean isCancelled() {
        return canceled;
    }

    public void setCancelled(boolean canceled) {
        this.canceled = canceled;
    }
}
