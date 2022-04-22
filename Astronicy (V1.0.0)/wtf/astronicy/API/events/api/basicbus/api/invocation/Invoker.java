// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.basicbus.api.invocation;

import java.lang.reflect.Method;

@FunctionalInterface
public interface Invoker
{
    void invoke(final Object p0, final Method p1, final Object... p2);
}
