/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;
import net.minecraft.network.Packet;

public class EventPacket
extends Event {
    public static Packet packet;
    private boolean cancel;
    private Action eventAction;

    public EventPacket(Action action, Packet packet) {
        this.eventAction = action;
        EventPacket.packet = packet;
    }

    public static Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        EventPacket.packet = packet;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Action getEventAction() {
        return this.eventAction;
    }

    public boolean isSend() {
        return this.getEventAction() == Action.SEND;
    }

    public boolean isReceive() {
        return this.getEventAction() == Action.RECEIVE;
    }

    public static enum Action {
        SEND,
        RECEIVE;

    }
}

