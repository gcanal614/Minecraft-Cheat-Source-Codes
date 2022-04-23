/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.api.bus;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.api.annotations.Priority;
import club.tifality.manager.api.bus.Bus;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BusImpl<T>
implements Bus<T> {
    private static final Site[] PLACEHOLDER = new Site[1];
    private final Map<Class<?>, List<Site>> map = new HashMap();

    @Override
    public void subscribe(Object subscriber) {
        for (Method m : subscriber.getClass().getDeclaredMethods()) {
            Class<?>[] params;
            Listener l = m.getAnnotation(Listener.class);
            if (l == null || (params = m.getParameterTypes()).length != 1) continue;
            Map<Class<?>, List<Site>> map2 = this.map;
            Class<?> ecs = params[0];
            Site cl = new Site(subscriber, m, l.value());
            if (map2.containsKey(ecs)) {
                List<Site> ss = map2.get(ecs);
                ss.add(cl);
                ss.sort(Comparator.comparingInt(site -> site.p));
                continue;
            }
            BusImpl.PLACEHOLDER[0] = cl;
            map2.put(ecs, new ArrayList<Site>(Arrays.asList(PLACEHOLDER)));
        }
    }

    @Override
    public void unsubscribe(Object subscriber) {
        for (List<Site> cls : this.map.values()) {
            cls.removeIf(c -> c.s == subscriber);
            cls.sort(Comparator.comparingInt(site -> site.p));
        }
    }

    @Override
    public void post(T event) {
        List<Site> cls = this.map.get(event.getClass());
        try {
            if (cls != null && !cls.isEmpty()) {
                for (Site site : cls) {
                    try {
                        Method m = site.m;
                        Object sub = site.s;
                        m.invoke(sub, event);
                    }
                    catch (IllegalAccessException | IndexOutOfBoundsException | InvocationTargetException exception) {}
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("exception 123");
        }
    }

    static final class Site {
        final Object s;
        final Method m;
        final byte p;

        Site(Object s, Method m, Priority p) {
            this.s = s;
            if (!m.isAccessible()) {
                m.setAccessible(true);
            }
            this.m = m;
            this.p = (byte)p.ordinal();
        }
    }
}

