/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.player;

import club.tifality.manager.event.CancellableEvent;

public final class SendMessageEvent
extends CancellableEvent {
    private String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

