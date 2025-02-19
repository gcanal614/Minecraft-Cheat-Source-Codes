/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class NVPixelDataRange {
    public static final int GL_WRITE_PIXEL_DATA_RANGE_NV = 34936;
    public static final int GL_READ_PIXEL_DATA_RANGE_NV = 34937;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_LENGTH_NV = 34938;
    public static final int GL_READ_PIXEL_DATA_RANGE_LENGTH_NV = 34939;
    public static final int GL_WRITE_PIXEL_DATA_RANGE_POINTER_NV = 34940;
    public static final int GL_READ_PIXEL_DATA_RANGE_POINTER_NV = 34941;

    private NVPixelDataRange() {
    }

    public static void glPixelDataRangeNV(int target, ByteBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data2);
        NVPixelDataRange.nglPixelDataRangeNV(target, data2.remaining(), MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, DoubleBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data2);
        NVPixelDataRange.nglPixelDataRangeNV(target, data2.remaining() << 3, MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, FloatBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data2);
        NVPixelDataRange.nglPixelDataRangeNV(target, data2.remaining() << 2, MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, IntBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data2);
        NVPixelDataRange.nglPixelDataRangeNV(target, data2.remaining() << 2, MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, ShortBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data2);
        NVPixelDataRange.nglPixelDataRangeNV(target, data2.remaining() << 1, MemoryUtil.getAddress(data2), function_pointer);
    }

    static native void nglPixelDataRangeNV(int var0, int var1, long var2, long var4);

    public static void glFlushPixelDataRangeNV(int target) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glFlushPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVPixelDataRange.nglFlushPixelDataRangeNV(target, function_pointer);
    }

    static native void nglFlushPixelDataRangeNV(int var0, long var1);
}

