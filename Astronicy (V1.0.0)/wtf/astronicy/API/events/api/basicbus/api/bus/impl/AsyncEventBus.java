// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.basicbus.api.bus.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.api.basicbus.api.bus.Bus;
import wtf.astronicy.API.events.api.basicbus.api.bus.CallLocation;
import wtf.astronicy.API.events.api.basicbus.api.invocation.Invoker;

public final class AsyncEventBus<T> implements Bus<T>
{
    private final Map<Class<?>, List<CallLocation>> eventClassMethodMap;
    private final Invoker invoker;
    
    public AsyncEventBus(final Invoker invoker) {
        this.eventClassMethodMap = new HashMap<Class<?>, List<CallLocation>>();
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
    
    @Override
    public void subscribe(final Object subscriber) {
        final Method[] methods = subscriber.getClass().getDeclaredMethods();
        final Map<Class<?>, List<CallLocation>> eventClassMethodMapRef = this.eventClassMethodMap;
        for (int i = methods.length - 1; i >= 0; --i) {
            final Method method = methods[i];
            final Listener listener = method.getAnnotation(Listener.class);
            if (listener != null) {
                final Class<?>[] params = method.getParameterTypes();
                final int paramsLength = params.length;
                if (paramsLength <= 1) {
                    final Class<?> eventClass = listener.value();
                    if (paramsLength != 1 || eventClass == params[0]) {
                        final CallLocation callLoc = new CallLocation(subscriber, method);
                        if (eventClassMethodMapRef.containsKey(eventClass)) {
                            eventClassMethodMapRef.get(eventClass).add(callLoc);
                        }
                        else {
                            eventClassMethodMapRef.put(eventClass, Arrays.asList(callLoc));
                        }
                    }
                }
            }
        }
    }
}
