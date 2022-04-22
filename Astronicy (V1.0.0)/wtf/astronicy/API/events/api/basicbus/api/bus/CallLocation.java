// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.basicbus.api.bus;

import java.lang.reflect.Method;

public final class CallLocation
{
    public final Object subscriber;
    public final Method method;
    public final boolean noParams;
    
    public CallLocation(final Object subscriber, final Method method) {
        this.subscriber = subscriber;
        method.setAccessible(true);
        this.method = method;
        this.noParams = (method.getParameterCount() == 0);
    }
    
    @Override
    public String toString() {
        return "CallLocation: {\n   class: " + this.subscriber.getClass().getCanonicalName() + "\n   method: " + this.method.getName() + "\n};";
    }
}
