// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.event;

@FunctionalInterface
public interface EventConsumer<Event>
{
    void call(final Event p0);
}
