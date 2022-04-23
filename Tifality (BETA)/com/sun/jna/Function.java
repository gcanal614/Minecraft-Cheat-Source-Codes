/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  [Lcom.sun.jna.NativeMapped;
 *  [Lcom.sun.jna.Pointer;
 *  [Lcom.sun.jna.Structure;
 *  [Lcom.sun.jna.WString;
 *  [Ljava.lang.String;
 */
package com.sun.jna;

import [Lcom.sun.jna.NativeMapped;;
import [Lcom.sun.jna.Pointer;;
import [Lcom.sun.jna.Structure;;
import [Lcom.sun.jna.WString;;
import [Ljava.lang.String;;
import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.FunctionParameterContext;
import com.sun.jna.FunctionResultContext;
import com.sun.jna.Memory;
import com.sun.jna.MethodParameterContext;
import com.sun.jna.MethodResultContext;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.NativeString;
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.WString;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class Function
extends Pointer {
    public static final int MAX_NARGS = 256;
    public static final int C_CONVENTION = 0;
    public static final int ALT_CONVENTION = 1;
    private static final int MASK_CC = 3;
    public static final int THROW_LAST_ERROR = 4;
    static final Integer INTEGER_TRUE = new Integer(-1);
    static final Integer INTEGER_FALSE = new Integer(0);
    private NativeLibrary library;
    private final String functionName;
    int callFlags;
    final Map options;
    static final String OPTION_INVOKING_METHOD = "invoking-method";
    static /* synthetic */ Class array$Lcom$sun$jna$Structure$ByReference;

    public static Function getFunction(String libraryName, String functionName) {
        return NativeLibrary.getInstance(libraryName).getFunction(functionName);
    }

    public static Function getFunction(String libraryName, String functionName, int callFlags) {
        return NativeLibrary.getInstance(libraryName).getFunction(functionName, callFlags);
    }

    public static Function getFunction(Pointer p) {
        return Function.getFunction(p, 0);
    }

    public static Function getFunction(Pointer p, int callFlags) {
        return new Function(p, callFlags);
    }

    Function(NativeLibrary library, String functionName, int callFlags) {
        this.checkCallingConvention(callFlags & 3);
        if (functionName == null) {
            throw new NullPointerException("Function name must not be null");
        }
        this.library = library;
        this.functionName = functionName;
        this.callFlags = callFlags;
        this.options = library.options;
        try {
            this.peer = library.getSymbolAddress(functionName);
        }
        catch (UnsatisfiedLinkError e) {
            throw new UnsatisfiedLinkError("Error looking up function '" + functionName + "': " + e.getMessage());
        }
    }

    Function(Pointer functionAddress, int callFlags) {
        this.checkCallingConvention(callFlags & 3);
        if (functionAddress == null || functionAddress.peer == 0L) {
            throw new NullPointerException("Function address may not be null");
        }
        this.functionName = functionAddress.toString();
        this.callFlags = callFlags;
        this.peer = functionAddress.peer;
        this.options = Collections.EMPTY_MAP;
    }

    private void checkCallingConvention(int convention) throws IllegalArgumentException {
        switch (convention) {
            case 0: 
            case 1: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Unrecognized calling convention: " + convention);
            }
        }
    }

    public String getName() {
        return this.functionName;
    }

    public int getCallingConvention() {
        return this.callFlags & 3;
    }

    public Object invoke(Class returnType, Object[] inArgs) {
        return this.invoke(returnType, inArgs, this.options);
    }

    public Object invoke(Class returnType, Object[] inArgs, Map options) {
        Object[] args2 = new Object[]{};
        if (inArgs != null) {
            if (inArgs.length > 256) {
                throw new UnsupportedOperationException("Maximum argument count is 256");
            }
            args2 = new Object[inArgs.length];
            System.arraycopy(inArgs, 0, args2, 0, args2.length);
        }
        TypeMapper mapper = (TypeMapper)options.get("type-mapper");
        Method invokingMethod = (Method)options.get(OPTION_INVOKING_METHOD);
        boolean allowObjects = Boolean.TRUE.equals(options.get("allow-objects"));
        for (int i = 0; i < args2.length; ++i) {
            args2[i] = this.convertArgument(args2, i, invokingMethod, mapper, allowObjects);
        }
        Class nativeType = returnType;
        FromNativeConverter resultConverter = null;
        if (NativeMapped.class.isAssignableFrom(returnType)) {
            NativeMappedConverter tc = NativeMappedConverter.getInstance(returnType);
            resultConverter = tc;
            nativeType = tc.nativeType();
        } else if (mapper != null && (resultConverter = mapper.getFromNativeConverter(returnType)) != null) {
            nativeType = resultConverter.nativeType();
        }
        Object result2 = this.invoke(args2, nativeType, allowObjects);
        if (resultConverter != null) {
            FunctionResultContext context = invokingMethod != null ? new MethodResultContext(returnType, this, inArgs, invokingMethod) : new FunctionResultContext(returnType, this, inArgs);
            result2 = resultConverter.fromNative(result2, context);
        }
        if (inArgs != null) {
            for (int i = 0; i < inArgs.length; ++i) {
                Object inArg = inArgs[i];
                if (inArg == null) continue;
                if (inArg instanceof Structure) {
                    if (inArg instanceof Structure.ByValue) continue;
                    ((Structure)inArg).autoRead();
                    continue;
                }
                if (args2[i] instanceof PostCallRead) {
                    ((PostCallRead)args2[i]).read();
                    if (!(args2[i] instanceof PointerArray)) continue;
                    PointerArray array = (PointerArray)args2[i];
                    if (!(array$Lcom$sun$jna$Structure$ByReference == null ? Function.class$("[Lcom.sun.jna.Structure$ByReference;") : array$Lcom$sun$jna$Structure$ByReference).isAssignableFrom(inArg.getClass())) continue;
                    Class<?> type2 = inArg.getClass().getComponentType();
                    Structure[] ss = (Structure[])inArg;
                    for (int si = 0; si < ss.length; ++si) {
                        Pointer p = array.getPointer(Pointer.SIZE * si);
                        ss[si] = Structure.updateStructureByReference(type2, ss[si], p);
                    }
                    continue;
                }
                if (!(array$Lcom$sun$jna$Structure == null ? Function.class$("[Lcom.sun.jna.Structure;") : array$Lcom$sun$jna$Structure).isAssignableFrom(inArg.getClass())) continue;
                Structure.autoRead((Structure[])inArg);
            }
        }
        return result2;
    }

    Object invoke(Object[] args2, Class returnType, boolean allowObjects) {
        Object result2 = null;
        if (returnType == null || returnType == Void.TYPE || returnType == Void.class) {
            Native.invokeVoid(this.peer, this.callFlags, args2);
            result2 = null;
        } else if (returnType == Boolean.TYPE || returnType == Boolean.class) {
            result2 = Function.valueOf(Native.invokeInt(this.peer, this.callFlags, args2) != 0);
        } else if (returnType == Byte.TYPE || returnType == Byte.class) {
            result2 = new Byte((byte)Native.invokeInt(this.peer, this.callFlags, args2));
        } else if (returnType == Short.TYPE || returnType == Short.class) {
            result2 = new Short((short)Native.invokeInt(this.peer, this.callFlags, args2));
        } else if (returnType == Character.TYPE || returnType == Character.class) {
            result2 = new Character((char)Native.invokeInt(this.peer, this.callFlags, args2));
        } else if (returnType == Integer.TYPE || returnType == Integer.class) {
            result2 = new Integer(Native.invokeInt(this.peer, this.callFlags, args2));
        } else if (returnType == Long.TYPE || returnType == Long.class) {
            result2 = new Long(Native.invokeLong(this.peer, this.callFlags, args2));
        } else if (returnType == Float.TYPE || returnType == Float.class) {
            result2 = new Float(Native.invokeFloat(this.peer, this.callFlags, args2));
        } else if (returnType == Double.TYPE || returnType == Double.class) {
            result2 = new Double(Native.invokeDouble(this.peer, this.callFlags, args2));
        } else if (returnType == String.class) {
            result2 = this.invokeString(this.callFlags, args2, false);
        } else if (returnType == WString.class) {
            String s = this.invokeString(this.callFlags, args2, true);
            if (s != null) {
                result2 = new WString(s);
            }
        } else {
            if (Pointer.class.isAssignableFrom(returnType)) {
                return this.invokePointer(this.callFlags, args2);
            }
            if (Structure.class.isAssignableFrom(returnType)) {
                if (Structure.ByValue.class.isAssignableFrom(returnType)) {
                    Structure s = Native.invokeStructure(this.peer, this.callFlags, args2, Structure.newInstance(returnType));
                    s.autoRead();
                    result2 = s;
                } else {
                    result2 = this.invokePointer(this.callFlags, args2);
                    if (result2 != null) {
                        Structure s = Structure.newInstance(returnType);
                        s.useMemory((Pointer)result2);
                        s.autoRead();
                        result2 = s;
                    }
                }
            } else if (Callback.class.isAssignableFrom(returnType)) {
                result2 = this.invokePointer(this.callFlags, args2);
                if (result2 != null) {
                    result2 = CallbackReference.getCallback(returnType, (Pointer)result2);
                }
            } else if (returnType == String;.class) {
                Pointer p = this.invokePointer(this.callFlags, args2);
                if (p != null) {
                    result2 = p.getStringArray(0L);
                }
            } else if (returnType == WString;.class) {
                Pointer p = this.invokePointer(this.callFlags, args2);
                if (p != null) {
                    String[] arr = p.getStringArray(0L, true);
                    WString[] warr = new WString[arr.length];
                    for (int i = 0; i < arr.length; ++i) {
                        warr[i] = new WString(arr[i]);
                    }
                    result2 = warr;
                }
            } else if (returnType == Pointer;.class) {
                Pointer p = this.invokePointer(this.callFlags, args2);
                if (p != null) {
                    result2 = p.getPointerArray(0L);
                }
            } else if (allowObjects) {
                result2 = Native.invokeObject(this.peer, this.callFlags, args2);
                if (result2 != null && !returnType.isAssignableFrom(result2.getClass())) {
                    throw new ClassCastException("Return type " + returnType + " does not match result " + result2.getClass());
                }
            } else {
                throw new IllegalArgumentException("Unsupported return type " + returnType + " in function " + this.getName());
            }
        }
        return result2;
    }

    private Pointer invokePointer(int callFlags, Object[] args2) {
        long ptr = Native.invokePointer(this.peer, callFlags, args2);
        return ptr == 0L ? null : new Pointer(ptr);
    }

    private Object convertArgument(Object[] args2, int index, Method invokingMethod, TypeMapper mapper, boolean allowObjects) {
        Object arg = args2[index];
        if (arg != null) {
            Class<?> type2 = arg.getClass();
            ToNativeConverter converter = null;
            if (NativeMapped.class.isAssignableFrom(type2)) {
                converter = NativeMappedConverter.getInstance(type2);
            } else if (mapper != null) {
                converter = mapper.getToNativeConverter(type2);
            }
            if (converter != null) {
                FunctionParameterContext context = invokingMethod != null ? new MethodParameterContext(this, args2, index, invokingMethod) : new FunctionParameterContext(this, args2, index);
                arg = converter.toNative(arg, context);
            }
        }
        if (arg == null || this.isPrimitiveArray(arg.getClass())) {
            return arg;
        }
        Class<?> argClass = arg.getClass();
        if (arg instanceof Structure) {
            Structure struct = (Structure)arg;
            struct.autoWrite();
            if (struct instanceof Structure.ByValue) {
                Class<?> ptype = struct.getClass();
                if (invokingMethod != null) {
                    Class<?>[] ptypes = invokingMethod.getParameterTypes();
                    if (Function.isVarArgs(invokingMethod)) {
                        if (index < ptypes.length - 1) {
                            ptype = ptypes[index];
                        } else {
                            Class<?> etype = ptypes[ptypes.length - 1].getComponentType();
                            if (etype != Object.class) {
                                ptype = etype;
                            }
                        }
                    } else {
                        ptype = ptypes[index];
                    }
                }
                if (Structure.ByValue.class.isAssignableFrom(ptype)) {
                    return struct;
                }
            }
            return struct.getPointer();
        }
        if (arg instanceof Callback) {
            return CallbackReference.getFunctionPointer((Callback)arg);
        }
        if (arg instanceof String) {
            return new NativeString((String)arg, false).getPointer();
        }
        if (arg instanceof WString) {
            return new NativeString(arg.toString(), true).getPointer();
        }
        if (arg instanceof Boolean) {
            return Boolean.TRUE.equals(arg) ? INTEGER_TRUE : INTEGER_FALSE;
        }
        if (String;.class == argClass) {
            return new StringArray((String[])arg);
        }
        if (WString;.class == argClass) {
            return new StringArray((WString[])arg);
        }
        if (Pointer;.class == argClass) {
            return new PointerArray((Pointer[])arg);
        }
        if (NativeMapped;.class.isAssignableFrom(argClass)) {
            return new NativeMappedArray((NativeMapped[])arg);
        }
        if (Structure;.class.isAssignableFrom(argClass)) {
            Class<?> type3;
            Structure[] ss = (Structure[])arg;
            boolean byRef = Structure.ByReference.class.isAssignableFrom(type3 = argClass.getComponentType());
            if (byRef) {
                Pointer[] pointers = new Pointer[ss.length + 1];
                for (int i = 0; i < ss.length; ++i) {
                    pointers[i] = ss[i] != null ? ss[i].getPointer() : null;
                }
                return new PointerArray(pointers);
            }
            if (ss.length == 0) {
                throw new IllegalArgumentException("Structure array must have non-zero length");
            }
            if (ss[0] == null) {
                Structure.newInstance(type3).toArray(ss);
                return ss[0].getPointer();
            }
            Structure.autoWrite(ss);
            return ss[0].getPointer();
        }
        if (argClass.isArray()) {
            throw new IllegalArgumentException("Unsupported array argument type: " + argClass.getComponentType());
        }
        if (allowObjects) {
            return arg;
        }
        if (!Native.isSupportedNativeType(arg.getClass())) {
            throw new IllegalArgumentException("Unsupported argument type " + arg.getClass().getName() + " at parameter " + index + " of function " + this.getName());
        }
        return arg;
    }

    private boolean isPrimitiveArray(Class argClass) {
        return argClass.isArray() && argClass.getComponentType().isPrimitive();
    }

    public void invoke(Object[] args2) {
        this.invoke(Void.class, args2);
    }

    private String invokeString(int callFlags, Object[] args2, boolean wide) {
        Pointer ptr = this.invokePointer(callFlags, args2);
        String s = null;
        if (ptr != null) {
            s = wide ? ptr.getString(0L, wide) : ptr.getString(0L);
        }
        return s;
    }

    public String toString() {
        if (this.library != null) {
            return "native function " + this.functionName + "(" + this.library.getName() + ")@0x" + Long.toHexString(this.peer);
        }
        return "native function@0x" + Long.toHexString(this.peer);
    }

    public Object invokeObject(Object[] args2) {
        return this.invoke(Object.class, args2);
    }

    public Pointer invokePointer(Object[] args2) {
        return (Pointer)this.invoke(Pointer.class, args2);
    }

    public String invokeString(Object[] args2, boolean wide) {
        Class clazz = wide ? WString.class : String.class;
        Object o = this.invoke(clazz, args2);
        return o != null ? o.toString() : null;
    }

    public int invokeInt(Object[] args2) {
        return (Integer)this.invoke(Integer.class, args2);
    }

    public long invokeLong(Object[] args2) {
        return (Long)this.invoke(Long.class, args2);
    }

    public float invokeFloat(Object[] args2) {
        return ((Float)this.invoke(Float.class, args2)).floatValue();
    }

    public double invokeDouble(Object[] args2) {
        return (Double)this.invoke(Double.class, args2);
    }

    public void invokeVoid(Object[] args2) {
        this.invoke(Void.class, args2);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() == this.getClass()) {
            Function other = (Function)o;
            return other.callFlags == this.callFlags && ((Object)other.options).equals(this.options) && other.peer == this.peer;
        }
        return false;
    }

    public int hashCode() {
        return this.callFlags + ((Object)this.options).hashCode() + super.hashCode();
    }

    static Object[] concatenateVarArgs(Object[] inArgs) {
        if (inArgs != null && inArgs.length > 0) {
            Class<?> argType;
            Object lastArg = inArgs[inArgs.length - 1];
            Class<?> clazz = argType = lastArg != null ? lastArg.getClass() : null;
            if (argType != null && argType.isArray()) {
                Object[] varArgs = (Object[])lastArg;
                Object[] fullArgs = new Object[inArgs.length + varArgs.length];
                System.arraycopy(inArgs, 0, fullArgs, 0, inArgs.length - 1);
                System.arraycopy(varArgs, 0, fullArgs, inArgs.length - 1, varArgs.length);
                fullArgs[fullArgs.length - 1] = null;
                inArgs = fullArgs;
            }
        }
        return inArgs;
    }

    static boolean isVarArgs(Method m) {
        try {
            Method v = m.getClass().getMethod("isVarArgs", new Class[0]);
            return Boolean.TRUE.equals(v.invoke(m, new Object[0]));
        }
        catch (SecurityException e) {
        }
        catch (NoSuchMethodException e) {
        }
        catch (IllegalArgumentException e) {
        }
        catch (IllegalAccessException e) {
        }
        catch (InvocationTargetException invocationTargetException) {
            // empty catch block
        }
        return false;
    }

    static Boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    private static class PointerArray
    extends Memory
    implements PostCallRead {
        private final Pointer[] original;

        public PointerArray(Pointer[] arg) {
            super(Pointer.SIZE * (arg.length + 1));
            this.original = arg;
            for (int i = 0; i < arg.length; ++i) {
                this.setPointer(i * Pointer.SIZE, arg[i]);
            }
            this.setPointer(Pointer.SIZE * arg.length, null);
        }

        public void read() {
            this.read(0L, this.original, 0, this.original.length);
        }
    }

    private static class NativeMappedArray
    extends Memory
    implements PostCallRead {
        private final NativeMapped[] original;

        public NativeMappedArray(NativeMapped[] arg) {
            super(Native.getNativeSize(arg.getClass(), arg));
            this.original = arg;
            this.setValue(0L, this.original, this.original.getClass());
        }

        public void read() {
            this.getValue(0L, this.original.getClass(), this.original);
        }
    }

    public static interface PostCallRead {
        public void read();
    }
}

