// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.basicbus.api.bus.impl;

import java.util.concurrent.ConcurrentHashMap;

import wtf.astronicy.API.events.api.basicbus.api.bus.Bus;
import wtf.astronicy.API.events.api.basicbus.api.bus.CallLocation;
import wtf.astronicy.API.events.api.basicbus.api.invocation.Invoker;

import java.util.List;
import java.util.Map;

public final class EventBusImpl<T> implements Bus<T>
{
    private final Map<Class<?>, List<CallLocation>> eventClassMethodMap;
    private final Invoker invoker;
    
    public EventBusImpl(final Invoker invoker) {
        this.eventClassMethodMap = new ConcurrentHashMap<Class<?>, List<CallLocation>>();
        this.invoker = invoker;
    }
    
    @Override
    public Invoker invoker() {
        return this.invoker;
    }
    
    @Override
    public Map<Class<?>, List<CallLocation>> map() {
        return this.eventClassMethodMap;
    }
}
