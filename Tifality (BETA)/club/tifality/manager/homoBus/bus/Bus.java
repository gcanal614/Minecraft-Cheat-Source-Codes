/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.homoBus.bus;

public interface Bus<Event> {
    public void subscribe(Object var1);

    public void unsubscribe(Object var1);

    public void post(Event var1);
}

