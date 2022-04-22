package wtf.astronicy.API.registry.impl;

import wtf.astronicy.API.events.api.basicbus.api.bus.Bus;
import wtf.astronicy.API.events.api.basicbus.api.bus.impl.EventBusImpl;
import wtf.astronicy.API.events.api.basicbus.api.invocation.impl.ReflectionInvoker;
import wtf.astronicy.API.registry.Registry;

public final class EventBusRegistry implements Registry {
   public final Bus eventBus = new EventBusImpl(new ReflectionInvoker());
}
