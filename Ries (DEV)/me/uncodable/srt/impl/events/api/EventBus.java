/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.events.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.Event;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.api.IListener;
import me.uncodable.srt.impl.events.events.peripheral.EventKeyPress;
import me.uncodable.srt.impl.modules.api.Module;

public class EventBus {
    private static final Map<IListener, List<Method>> HANDLERS = new HashMap<IListener, List<Method>>();

    public static void setup(List<IListener> targets) {
        targets.forEach(object -> {
            Class<?> clazz = object.getClass();
            List declaredMethods = Stream.of(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(EventTarget.class)).collect(Collectors.toList());
            HANDLERS.put((IListener)object, declaredMethods);
        });
    }

    public static void processEvent(Event event) {
        if (event instanceof EventKeyPress) {
            Ries.INSTANCE.getKeyBindSystem().onKeyPress((EventKeyPress)event);
        }
        Class<?> clazz = event.getClass();
        HANDLERS.forEach((listener, methods) -> methods.forEach(method -> {
            EventTarget target = method.getDeclaredAnnotation(EventTarget.class);
            Class<? extends Event> targetClazz = target.target();
            if (targetClazz.equals(clazz)) {
                if (listener instanceof Module && !((Module)listener).isEnabled()) {
                    return;
                }
                try {
                    method.invoke(listener, event);
                }
                catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                    // empty catch block
                }
            }
        }));
    }
}

