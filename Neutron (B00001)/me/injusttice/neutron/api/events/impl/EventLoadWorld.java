package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.client.multiplayer.WorldClient;

public final class EventLoadWorld extends Event {

    private final WorldClient worldClient;

    public EventLoadWorld(WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    public WorldClient getWorldClient() {
        return worldClient;
    }
}
