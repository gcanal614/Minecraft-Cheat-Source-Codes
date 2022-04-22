// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.basicbus.api.bus;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.api.basicbus.api.invocation.Invoker;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Bus<T>
{
    Invoker invoker();
    
    Map<Class<?>, List<CallLocation>> map();
    
    default void subscribe(final Object subscriber) {
        final Method[] methods = subscriber.getClass().getDeclaredMethods();
        final Map<Class<?>, List<CallLocation>> eventClassMethodMapRef = this.map();
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
                            eventClassMethodMapRef.put(eventClass, new CopyOnWriteArrayList<CallLocation>(Arrays.asList(callLoc)));
                        }
                    }
                }
            }
        }
    }
    
    default void unsubscribe(final Object subscriber) {
        final Collection<List<CallLocation>> callLocationsRef = this.map().values();
        for (final List<CallLocation> callLocations : callLocationsRef) {
            for (final CallLocation callLocation : callLocations) {
                if (callLocation.subscriber == subscriber) {
                    callLocations.remove(callLocation);
                }
            }
        }
    }
    
    default void post(final T event) {
        final List<CallLocation> callLocations = this.map().get(event.getClass());
        if (callLocations != null) {
            for (final CallLocation callLocation : callLocations) {
                final Method method = callLocation.method;
                final Object sub = callLocation.subscriber;
                if (callLocation.noParams) {
                    this.invoker().invoke(sub, method, new Object[0]);
                }
                else {
                    this.invoker().invoke(sub, method, event);
                }
            }
        }
    }
}
