/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.api.bus;

public interface Bus<T> {
    public void subscribe(Object var1);

    public void unsubscribe(Object var1);

    public void post(T var1);
}

