/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event;

import club.tifality.manager.event.Event;

public class CancellableEvent
implements Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setCancelled() {
        this.cancelled = true;
    }
}

