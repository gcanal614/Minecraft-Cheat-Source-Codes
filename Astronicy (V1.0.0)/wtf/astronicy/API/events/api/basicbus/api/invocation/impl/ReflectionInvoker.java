// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.basicbus.api.invocation.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import wtf.astronicy.API.events.api.basicbus.api.invocation.Invoker;

public final class ReflectionInvoker implements Invoker
{
    @Override
    public void invoke(final Object instance, final Method method, final Object... parameters) {
        try {
            method.invoke(instance, parameters);
        }
        catch (IllegalAccessException ex) {}
        catch (InvocationTargetException ex2) {}
    }
}
