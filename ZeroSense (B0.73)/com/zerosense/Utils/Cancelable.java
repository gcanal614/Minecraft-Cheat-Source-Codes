package com.zerosense.Utils;

public class Cancelable {

    /**
     * Weather to cancel the event or not.
     */
    private boolean cancel;

    /**
     * Gets if the event is cancelled.
     *
     * @return If the event is cancelled.
     */
    public boolean isCancellled() {
        return cancel;
    }

    /**
     * Sets the cancel state to {@code cancel}.
     *
     * @param cancel Weather to cancel the event.
     */
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
