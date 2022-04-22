package me.injusttice.neutron.api.events.impl;

import me.injusttice.neutron.api.events.Event;
import net.minecraft.entity.Entity;

public class EventNamePlayer extends Event {

    public Entity p;

    public EventNamePlayer(final Entity p2) {
        this.p = p2;
    }
}
