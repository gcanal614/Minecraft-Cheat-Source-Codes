/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events;

import de.fanta.events.Event;
import net.minecraft.network.Packet;

public abstract class EventCancellable
extends Event {
    private Packet packet;
    private boolean cancelled;

    protected EventCancellable() {
    }

    public Packet getPacket() {
        return this.packet;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

