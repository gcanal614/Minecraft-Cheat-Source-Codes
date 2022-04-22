// 
// Decompiled by Procyon v0.5.36
// 

package wtf.astronicy.API.events.api.security;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.lang.invoke.MethodHandles;

public final class MethodIndirection
{
    private static final MethodHandles.Lookup LOOKUP;
    private static final Class<?> INT_CLASS;
    private static final Class<?> SYSTEM_CLASS;
    private static final Class<?> STRING_CLASS;
    private static final Class<?> BASE64_CLASS;
    private static System system;
    private static Base64 base64;
    
    public static String getenv(final String property) {
        try {
            return (String)MethodIndirection.SYSTEM_CLASS.getDeclaredMethod("getenv", MethodIndirection.STRING_CLASS).invoke(MethodIndirection.system, property);
        }
        catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            return null;
        }
    }
    
    public static String getProperty(final String property) {
        try {
            return (String)MethodIndirection.SYSTEM_CLASS.getDeclaredMethod("getProperty", MethodIndirection.STRING_CLASS).invoke(MethodIndirection.system, property);
        }
        catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            return null;
        }
    }
    
    public static Base64.Encoder getEncoder() {
        try {
            return (Base64.Encoder)MethodIndirection.BASE64_CLASS.getDeclaredMethod("getEncoder", (Class<?>[])new Class[0]).invoke(MethodIndirection.base64, new Object[0]);
        }
        catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            return null;
        }
    }
    
    public static void exit() {
        try {
            final MethodHandle exitMethod = MethodIndirection.LOOKUP.findStatic(MethodIndirection.SYSTEM_CLASS, "exit", MethodType.methodType(Void.TYPE, MethodIndirection.INT_CLASS));
            exitMethod.invoke(0);
        }
        catch (Throwable t) {}
    }
    
    static {
        LOOKUP = MethodHandles.lookup();
        INT_CLASS = Integer.TYPE;
        SYSTEM_CLASS = System.class;
        STRING_CLASS = String.class;
        BASE64_CLASS = Base64.class;
        try {
            MethodIndirection.system = (System)MethodIndirection.SYSTEM_CLASS.newInstance();
            MethodIndirection.base64 = (Base64)MethodIndirection.BASE64_CLASS.newInstance();
        }
        catch (InstantiationException ex) {}
        catch (IllegalAccessException ex2) {}
    }
}
