// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.event;

import net.minecraft.client.Minecraft;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.ParameterizedType;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Map;

public final class EventManager<Event>
{
    private final Map<Type, List<CallSite<Event>>> callSiteMap;
    private final Map<Type, List<EventConsumer<Event>>> listenerCache;
    
    public EventManager() {
        this.callSiteMap = new HashMap<Type, List<CallSite<Event>>>();
        this.listenerCache = new HashMap<Type, List<EventConsumer<Event>>>();
    }
    
    public final void subscribe(final Object subscriber) {
        final Field[] arrayOfField;
        final int i = (arrayOfField = subscriber.getClass().getDeclaredFields()).length;
        for (byte b = 0; b < i; ++b) {
            final Field field = arrayOfField[b];
            if (field.isAnnotationPresent(EventListener.class)) {
                final Type eventType = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    final EventConsumer<Event> eventConsumer = (EventConsumer<Event>)field.get(subscriber);
                    final byte priority = 2;
                    if (this.callSiteMap.containsKey(eventType)) {
                        final List<CallSite<Event>> callSites = this.callSiteMap.get(eventType);
                        callSites.add(new CallSite<Event>(subscriber, eventConsumer, priority));
                        callSites.sort((o1, o2) -> o2.priority - o1.priority);
                    }
                    else {
                        this.callSiteMap.put(eventType, new ArrayList<CallSite<Event>>((Collection<? extends CallSite<Event>>)Arrays.asList(new CallSite(subscriber, (EventConsumer<Event>)eventConsumer, priority))));
                    }
                }
                catch (IllegalAccessException ex) {}
            }
        }
        this.populateListenerCache();
    }
    
    private void populateListenerCache() {
        final Map<Type, List<CallSite<Event>>> callSiteMap = this.callSiteMap;
        final Map<Type, List<EventConsumer<Event>>> listenerCache = this.listenerCache;
        for (final Type type : callSiteMap.keySet()) {
            final List<CallSite<Event>> callSites = callSiteMap.get(type);
            final int size = callSites.size();
            final List<EventConsumer<Event>> eventConsumers = new ArrayList<EventConsumer<Event>>(size);
            for (final CallSite<Event> callSite : callSites) {
                eventConsumers.add(((CallSite<Object>)callSite).eventConsumer);
            }
            listenerCache.put(type, eventConsumers);
        }
    }
    
    public final void unsubscribe(final Object subscriber) {
        for (final List<CallSite<Event>> callSites : this.callSiteMap.values()) {
            callSites.removeIf(eventCallSite -> eventCallSite.owner == subscriber);
        }
        this.populateListenerCache();
    }
    
    public final void callEvent(final Event event) {
        final List<EventConsumer<Event>> eventConsumers = this.listenerCache.get(event.getClass());
        if (eventConsumers != null && Minecraft.getMinecraft().thePlayer != null) {
            for (final EventConsumer<Event> eventConsumer : eventConsumers) {
                eventConsumer.call(event);
            }
        }
    }
    
    private static class CallSite<Event>
    {
        private final Object owner;
        private final EventConsumer<Event> eventConsumer;
        private final byte priority;
        
        public CallSite(final Object owner, final EventConsumer<Event> eventConsumer, final byte priority) {
            this.owner = owner;
            this.eventConsumer = eventConsumer;
            this.priority = priority;
        }
    }
}
