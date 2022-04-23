/*
 * Decompiled with CFR 0.152.
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.FromNativeContext;
import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import com.sun.jna.WString;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Pointer {
    public static final int SIZE = Native.POINTER_SIZE;
    public static final Pointer NULL;
    protected long peer;

    public static final Pointer createConstant(long peer) {
        return new Opaque(peer);
    }

    public static final Pointer createConstant(int peer) {
        return new Opaque((long)peer & 0xFFFFFFFFFFFFFFFFL);
    }

    Pointer() {
    }

    public Pointer(long peer) {
        this.peer = peer;
    }

    public Pointer share(long offset) {
        return this.share(offset, 0L);
    }

    public Pointer share(long offset, long sz) {
        if (offset == 0L) {
            return this;
        }
        return new Pointer(this.peer + offset);
    }

    public void clear(long size) {
        this.setMemory(0L, size, (byte)0);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        return o instanceof Pointer && ((Pointer)o).peer == this.peer;
    }

    public int hashCode() {
        return (int)((this.peer >>> 32) + (this.peer & 0xFFFFFFFFFFFFFFFFL));
    }

    public long indexOf(long offset, byte value) {
        return Native.indexOf(this.peer + offset, value);
    }

    public void read(long offset, byte[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, short[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, char[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, int[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, long[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, float[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, double[] buf, int index, int length) {
        Native.read(this.peer + offset, buf, index, length);
    }

    public void read(long offset, Pointer[] buf, int index, int length) {
        for (int i = 0; i < length; ++i) {
            Pointer p = this.getPointer(offset + (long)(i * SIZE));
            Pointer oldp = buf[i + index];
            if (oldp != null && p != null && p.peer == oldp.peer) continue;
            buf[i + index] = p;
        }
    }

    public void write(long offset, byte[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long offset, short[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long offset, char[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long offset, int[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long offset, long[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long offset, float[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long offset, double[] buf, int index, int length) {
        Native.write(this.peer + offset, buf, index, length);
    }

    public void write(long bOff, Pointer[] buf, int index, int length) {
        for (int i = 0; i < length; ++i) {
            this.setPointer(bOff + (long)(i * SIZE), buf[index + i]);
        }
    }

    Object getValue(long offset, Class type2, Object currentValue) {
        Object result2 = null;
        if (Structure.class.isAssignableFrom(type2)) {
            Structure s = (Structure)currentValue;
            if (Structure.ByReference.class.isAssignableFrom(type2)) {
                s = Structure.updateStructureByReference(type2, s, this.getPointer(offset));
            } else {
                s.useMemory(this, (int)offset);
                s.read();
            }
            result2 = s;
        } else if (type2 == Boolean.TYPE || type2 == Boolean.class) {
            result2 = Function.valueOf(this.getInt(offset) != 0);
        } else if (type2 == Byte.TYPE || type2 == Byte.class) {
            result2 = new Byte(this.getByte(offset));
        } else if (type2 == Short.TYPE || type2 == Short.class) {
            result2 = new Short(this.getShort(offset));
        } else if (type2 == Character.TYPE || type2 == Character.class) {
            result2 = new Character(this.getChar(offset));
        } else if (type2 == Integer.TYPE || type2 == Integer.class) {
            result2 = new Integer(this.getInt(offset));
        } else if (type2 == Long.TYPE || type2 == Long.class) {
            result2 = new Long(this.getLong(offset));
        } else if (type2 == Float.TYPE || type2 == Float.class) {
            result2 = new Float(this.getFloat(offset));
        } else if (type2 == Double.TYPE || type2 == Double.class) {
            result2 = new Double(this.getDouble(offset));
        } else if (Pointer.class.isAssignableFrom(type2)) {
            Pointer p = this.getPointer(offset);
            if (p != null) {
                Pointer oldp;
                Pointer pointer = oldp = currentValue instanceof Pointer ? (Pointer)currentValue : null;
                result2 = oldp == null || p.peer != oldp.peer ? p : oldp;
            }
        } else if (type2 == String.class) {
            Pointer p = this.getPointer(offset);
            result2 = p != null ? p.getString(0L) : null;
        } else if (type2 == WString.class) {
            Pointer p = this.getPointer(offset);
            result2 = p != null ? new WString(p.getString(0L, true)) : null;
        } else if (Callback.class.isAssignableFrom(type2)) {
            Pointer fp = this.getPointer(offset);
            if (fp == null) {
                result2 = null;
            } else {
                Callback cb = (Callback)currentValue;
                Pointer oldfp = CallbackReference.getFunctionPointer(cb);
                if (!fp.equals(oldfp)) {
                    cb = CallbackReference.getCallback(type2, fp);
                }
                result2 = cb;
            }
        } else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(type2)) {
            Pointer bp = this.getPointer(offset);
            if (bp == null) {
                result2 = null;
            } else {
                Pointer oldbp;
                Pointer pointer = oldbp = currentValue == null ? null : Native.getDirectBufferPointer((Buffer)currentValue);
                if (oldbp == null || !oldbp.equals(bp)) {
                    throw new IllegalStateException("Can't autogenerate a direct buffer on memory read");
                }
                result2 = currentValue;
            }
        } else if (NativeMapped.class.isAssignableFrom(type2)) {
            NativeMapped nm = (NativeMapped)currentValue;
            if (nm != null) {
                Object value = this.getValue(offset, nm.nativeType(), null);
                result2 = nm.fromNative(value, new FromNativeContext(type2));
            } else {
                NativeMappedConverter tc = NativeMappedConverter.getInstance(type2);
                Object value = this.getValue(offset, tc.nativeType(), null);
                result2 = tc.fromNative(value, new FromNativeContext(type2));
            }
        } else if (type2.isArray()) {
            result2 = currentValue;
            if (result2 == null) {
                throw new IllegalStateException("Need an initialized array");
            }
            this.getArrayValue(offset, result2, type2.getComponentType());
        } else {
            throw new IllegalArgumentException("Reading \"" + type2 + "\" from memory is not supported");
        }
        return result2;
    }

    private void getArrayValue(long offset, Object o, Class cls) {
        int length = 0;
        length = Array.getLength(o);
        Object result2 = o;
        if (cls == Byte.TYPE) {
            this.read(offset, (byte[])result2, 0, length);
        } else if (cls == Short.TYPE) {
            this.read(offset, (short[])result2, 0, length);
        } else if (cls == Character.TYPE) {
            this.read(offset, (char[])result2, 0, length);
        } else if (cls == Integer.TYPE) {
            this.read(offset, (int[])result2, 0, length);
        } else if (cls == Long.TYPE) {
            this.read(offset, (long[])result2, 0, length);
        } else if (cls == Float.TYPE) {
            this.read(offset, (float[])result2, 0, length);
        } else if (cls == Double.TYPE) {
            this.read(offset, (double[])result2, 0, length);
        } else if (Pointer.class.isAssignableFrom(cls)) {
            this.read(offset, (Pointer[])result2, 0, length);
        } else if (Structure.class.isAssignableFrom(cls)) {
            Structure[] sarray = (Structure[])result2;
            if (Structure.ByReference.class.isAssignableFrom(cls)) {
                Pointer[] parray = this.getPointerArray(offset, sarray.length);
                for (int i = 0; i < sarray.length; ++i) {
                    sarray[i] = Structure.updateStructureByReference(cls, sarray[i], parray[i]);
                }
            } else {
                for (int i = 0; i < sarray.length; ++i) {
                    if (sarray[i] == null) {
                        sarray[i] = Structure.newInstance(cls);
                    }
                    sarray[i].useMemory(this, (int)(offset + (long)(i * sarray[i].size())));
                    sarray[i].read();
                }
            }
        } else if (NativeMapped.class.isAssignableFrom(cls)) {
            NativeMapped[] array = (NativeMapped[])result2;
            NativeMappedConverter tc = NativeMappedConverter.getInstance(cls);
            int size = Native.getNativeSize(result2.getClass(), result2) / array.length;
            for (int i = 0; i < array.length; ++i) {
                Object value = this.getValue(offset + (long)(size * i), tc.nativeType(), array[i]);
                array[i] = (NativeMapped)tc.fromNative(value, new FromNativeContext(cls));
            }
        } else {
            throw new IllegalArgumentException("Reading array of " + cls + " from memory not supported");
        }
    }

    public byte getByte(long offset) {
        return Native.getByte(this.peer + offset);
    }

    public char getChar(long offset) {
        return Native.getChar(this.peer + offset);
    }

    public short getShort(long offset) {
        return Native.getShort(this.peer + offset);
    }

    public int getInt(long offset) {
        return Native.getInt(this.peer + offset);
    }

    public long getLong(long offset) {
        return Native.getLong(this.peer + offset);
    }

    public NativeLong getNativeLong(long offset) {
        return new NativeLong(NativeLong.SIZE == 8 ? this.getLong(offset) : (long)this.getInt(offset));
    }

    public float getFloat(long offset) {
        return Native.getFloat(this.peer + offset);
    }

    public double getDouble(long offset) {
        return Native.getDouble(this.peer + offset);
    }

    public Pointer getPointer(long offset) {
        return Native.getPointer(this.peer + offset);
    }

    public ByteBuffer getByteBuffer(long offset, long length) {
        return Native.getDirectByteBuffer(this.peer + offset, length).order(ByteOrder.nativeOrder());
    }

    public String getString(long offset, boolean wide) {
        return Native.getString(this.peer + offset, wide);
    }

    public String getString(long offset) {
        long len;
        String encoding = System.getProperty("jna.encoding");
        if (encoding != null && (len = this.indexOf(offset, (byte)0)) != -1L) {
            if (len > Integer.MAX_VALUE) {
                throw new OutOfMemoryError("String exceeds maximum length: " + len);
            }
            byte[] data2 = this.getByteArray(offset, (int)len);
            try {
                return new String(data2, encoding);
            }
            catch (UnsupportedEncodingException e) {
                // empty catch block
            }
        }
        return this.getString(offset, false);
    }

    public byte[] getByteArray(long offset, int arraySize) {
        byte[] buf = new byte[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public char[] getCharArray(long offset, int arraySize) {
        char[] buf = new char[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public short[] getShortArray(long offset, int arraySize) {
        short[] buf = new short[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public int[] getIntArray(long offset, int arraySize) {
        int[] buf = new int[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public long[] getLongArray(long offset, int arraySize) {
        long[] buf = new long[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public float[] getFloatArray(long offset, int arraySize) {
        float[] buf = new float[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public double[] getDoubleArray(long offset, int arraySize) {
        double[] buf = new double[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public Pointer[] getPointerArray(long offset) {
        ArrayList<Pointer> array = new ArrayList<Pointer>();
        int addOffset = 0;
        Pointer p = this.getPointer(offset);
        while (p != null) {
            array.add(p);
            p = this.getPointer(offset + (long)(addOffset += SIZE));
        }
        return array.toArray(new Pointer[array.size()]);
    }

    public Pointer[] getPointerArray(long offset, int arraySize) {
        Pointer[] buf = new Pointer[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public String[] getStringArray(long offset) {
        return this.getStringArray(offset, -1, false);
    }

    public String[] getStringArray(long offset, int length) {
        return this.getStringArray(offset, length, false);
    }

    public String[] getStringArray(long offset, boolean wide) {
        return this.getStringArray(offset, -1, wide);
    }

    public String[] getStringArray(long offset, int length, boolean wide) {
        ArrayList<String> strings = new ArrayList<String>();
        int addOffset = 0;
        if (length != -1) {
            Pointer p = this.getPointer(offset + (long)addOffset);
            int count = 0;
            while (count++ < length) {
                String s = p == null ? null : p.getString(0L, wide);
                strings.add(s);
                if (count >= length) continue;
                p = this.getPointer(offset + (long)(addOffset += SIZE));
            }
        } else {
            Pointer p;
            while ((p = this.getPointer(offset + (long)addOffset)) != null) {
                String s = p == null ? null : p.getString(0L, wide);
                strings.add(s);
                addOffset += SIZE;
            }
        }
        return strings.toArray(new String[strings.size()]);
    }

    void setValue(long offset, Object value, Class type2) {
        if (type2 == Boolean.TYPE || type2 == Boolean.class) {
            this.setInt(offset, Boolean.TRUE.equals(value) ? -1 : 0);
        } else if (type2 == Byte.TYPE || type2 == Byte.class) {
            this.setByte(offset, value == null ? (byte)0 : (Byte)value);
        } else if (type2 == Short.TYPE || type2 == Short.class) {
            this.setShort(offset, value == null ? (short)0 : (Short)value);
        } else if (type2 == Character.TYPE || type2 == Character.class) {
            this.setChar(offset, value == null ? (char)'\u0000' : ((Character)value).charValue());
        } else if (type2 == Integer.TYPE || type2 == Integer.class) {
            this.setInt(offset, value == null ? 0 : (Integer)value);
        } else if (type2 == Long.TYPE || type2 == Long.class) {
            this.setLong(offset, value == null ? 0L : (Long)value);
        } else if (type2 == Float.TYPE || type2 == Float.class) {
            this.setFloat(offset, value == null ? 0.0f : ((Float)value).floatValue());
        } else if (type2 == Double.TYPE || type2 == Double.class) {
            this.setDouble(offset, value == null ? 0.0 : (Double)value);
        } else if (type2 == Pointer.class) {
            this.setPointer(offset, (Pointer)value);
        } else if (type2 == String.class) {
            this.setPointer(offset, (Pointer)value);
        } else if (type2 == WString.class) {
            this.setPointer(offset, (Pointer)value);
        } else if (Structure.class.isAssignableFrom(type2)) {
            Structure s = (Structure)value;
            if (Structure.ByReference.class.isAssignableFrom(type2)) {
                this.setPointer(offset, s == null ? null : s.getPointer());
                if (s != null) {
                    s.autoWrite();
                }
            } else {
                s.useMemory(this, (int)offset);
                s.write();
            }
        } else if (Callback.class.isAssignableFrom(type2)) {
            this.setPointer(offset, CallbackReference.getFunctionPointer((Callback)value));
        } else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(type2)) {
            Pointer p = value == null ? null : Native.getDirectBufferPointer((Buffer)value);
            this.setPointer(offset, p);
        } else if (NativeMapped.class.isAssignableFrom(type2)) {
            NativeMappedConverter tc = NativeMappedConverter.getInstance(type2);
            Class nativeType = tc.nativeType();
            this.setValue(offset, tc.toNative(value, new ToNativeContext()), nativeType);
        } else if (type2.isArray()) {
            this.setArrayValue(offset, value, type2.getComponentType());
        } else {
            throw new IllegalArgumentException("Writing " + type2 + " to memory is not supported");
        }
    }

    private void setArrayValue(long offset, Object value, Class cls) {
        if (cls == Byte.TYPE) {
            byte[] buf = (byte[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Short.TYPE) {
            short[] buf = (short[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Character.TYPE) {
            char[] buf = (char[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Integer.TYPE) {
            int[] buf = (int[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Long.TYPE) {
            long[] buf = (long[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Float.TYPE) {
            float[] buf = (float[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Double.TYPE) {
            double[] buf = (double[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (Pointer.class.isAssignableFrom(cls)) {
            Pointer[] buf = (Pointer[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (Structure.class.isAssignableFrom(cls)) {
            Structure[] sbuf = (Structure[])value;
            if (Structure.ByReference.class.isAssignableFrom(cls)) {
                Pointer[] buf = new Pointer[sbuf.length];
                for (int i = 0; i < sbuf.length; ++i) {
                    if (sbuf[i] == null) {
                        buf[i] = null;
                        continue;
                    }
                    buf[i] = sbuf[i].getPointer();
                    sbuf[i].write();
                }
                this.write(offset, buf, 0, buf.length);
            } else {
                for (int i = 0; i < sbuf.length; ++i) {
                    if (sbuf[i] == null) {
                        sbuf[i] = Structure.newInstance(cls);
                    }
                    sbuf[i].useMemory(this, (int)(offset + (long)(i * sbuf[i].size())));
                    sbuf[i].write();
                }
            }
        } else if (NativeMapped.class.isAssignableFrom(cls)) {
            NativeMapped[] buf = (NativeMapped[])value;
            NativeMappedConverter tc = NativeMappedConverter.getInstance(cls);
            Class nativeType = tc.nativeType();
            int size = Native.getNativeSize(value.getClass(), value) / buf.length;
            for (int i = 0; i < buf.length; ++i) {
                Object element = tc.toNative(buf[i], new ToNativeContext());
                this.setValue(offset + (long)(i * size), element, nativeType);
            }
        } else {
            throw new IllegalArgumentException("Writing array of " + cls + " to memory not supported");
        }
    }

    public void setMemory(long offset, long length, byte value) {
        Native.setMemory(this.peer + offset, length, value);
    }

    public void setByte(long offset, byte value) {
        Native.setByte(this.peer + offset, value);
    }

    public void setShort(long offset, short value) {
        Native.setShort(this.peer + offset, value);
    }

    public void setChar(long offset, char value) {
        Native.setChar(this.peer + offset, value);
    }

    public void setInt(long offset, int value) {
        Native.setInt(this.peer + offset, value);
    }

    public void setLong(long offset, long value) {
        Native.setLong(this.peer + offset, value);
    }

    public void setNativeLong(long offset, NativeLong value) {
        if (NativeLong.SIZE == 8) {
            this.setLong(offset, value.longValue());
        } else {
            this.setInt(offset, value.intValue());
        }
    }

    public void setFloat(long offset, float value) {
        Native.setFloat(this.peer + offset, value);
    }

    public void setDouble(long offset, double value) {
        Native.setDouble(this.peer + offset, value);
    }

    public void setPointer(long offset, Pointer value) {
        Native.setPointer(this.peer + offset, value != null ? value.peer : 0L);
    }

    public void setString(long offset, String value, boolean wide) {
        Native.setString(this.peer + offset, value, wide);
    }

    public void setString(long offset, String value) {
        byte[] data2 = Native.getBytes(value);
        this.write(offset, data2, 0, data2.length);
        this.setByte(offset + (long)data2.length, (byte)0);
    }

    public String toString() {
        return "native@0x" + Long.toHexString(this.peer);
    }

    public static long nativeValue(Pointer p) {
        return p.peer;
    }

    public static void nativeValue(Pointer p, long value) {
        p.peer = value;
    }

    static {
        if (SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        NULL = null;
    }

    private static class Opaque
    extends Pointer {
        private final String MSG = "This pointer is opaque: " + this;

        private Opaque(long peer) {
            super(peer);
        }

        public long indexOf(long offset, byte value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, byte[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, char[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, short[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, int[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, long[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, float[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void read(long bOff, double[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, byte[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, char[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, short[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, int[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, long[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, float[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void write(long bOff, double[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public byte getByte(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public char getChar(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public short getShort(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public int getInt(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public long getLong(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public float getFloat(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public double getDouble(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public Pointer getPointer(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public String getString(long bOff, boolean wide) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setByte(long bOff, byte value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setChar(long bOff, char value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setShort(long bOff, short value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setInt(long bOff, int value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setLong(long bOff, long value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setFloat(long bOff, float value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setDouble(long bOff, double value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setPointer(long offset, Pointer value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public void setString(long offset, String value, boolean wide) {
            throw new UnsupportedOperationException(this.MSG);
        }

        public String toString() {
            return "opaque@0x" + Long.toHexString(this.peer);
        }
    }
}

