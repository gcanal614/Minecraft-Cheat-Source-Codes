package me.injusttice.neutron.api.events;

import java.lang.reflect.Method;

public class Data {

    public final Object source;

    public final Method target;

    public final byte priority;

    Data(Object source, Method target, byte priority) {

        this.source = source;
        this.target = target;
        this.priority = priority;
    }

}
