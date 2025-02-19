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
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.opengl.GLContext;

public final class ARBRobustness {
    public static final int GL_GUILTY_CONTEXT_RESET_ARB = 33363;
    public static final int GL_INNOCENT_CONTEXT_RESET_ARB = 33364;
    public static final int GL_UNKNOWN_CONTEXT_RESET_ARB = 33365;
    public static final int GL_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
    public static final int GL_LOSE_CONTEXT_ON_RESET_ARB = 33362;
    public static final int GL_NO_RESET_NOTIFICATION_ARB = 33377;
    public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT_ARB = 4;

    private ARBRobustness() {
    }

    public static int glGetGraphicsResetStatusARB() {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetGraphicsResetStatusARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        int __result = ARBRobustness.nglGetGraphicsResetStatusARB(function_pointer);
        return __result;
    }

    static native int nglGetGraphicsResetStatusARB(long var0);

    public static void glGetnMapdvARB(int target, int query, DoubleBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMapdvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        ARBRobustness.nglGetnMapdvARB(target, query, v.remaining() << 3, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglGetnMapdvARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnMapfvARB(int target, int query, FloatBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMapfvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        ARBRobustness.nglGetnMapfvARB(target, query, v.remaining() << 2, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglGetnMapfvARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnMapivARB(int target, int query, IntBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMapivARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        ARBRobustness.nglGetnMapivARB(target, query, v.remaining() << 2, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglGetnMapivARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnPixelMapfvARB(int map2, FloatBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnPixelMapfvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnPixelMapfvARB(map2, values2.remaining() << 2, MemoryUtil.getAddress(values2), function_pointer);
    }

    static native void nglGetnPixelMapfvARB(int var0, int var1, long var2, long var4);

    public static void glGetnPixelMapuivARB(int map2, IntBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnPixelMapuivARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnPixelMapuivARB(map2, values2.remaining() << 2, MemoryUtil.getAddress(values2), function_pointer);
    }

    static native void nglGetnPixelMapuivARB(int var0, int var1, long var2, long var4);

    public static void glGetnPixelMapusvARB(int map2, ShortBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnPixelMapusvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnPixelMapusvARB(map2, values2.remaining() << 1, MemoryUtil.getAddress(values2), function_pointer);
    }

    static native void nglGetnPixelMapusvARB(int var0, int var1, long var2, long var4);

    public static void glGetnPolygonStippleARB(ByteBuffer pattern) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnPolygonStippleARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(pattern);
        ARBRobustness.nglGetnPolygonStippleARB(pattern.remaining(), MemoryUtil.getAddress(pattern), function_pointer);
    }

    static native void nglGetnPolygonStippleARB(int var0, long var1, long var3);

    public static void glGetnTexImageARB(int target, int level, int format, int type2, ByteBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnTexImageARB(target, level, format, type2, img.remaining(), MemoryUtil.getAddress(img), function_pointer);
    }

    public static void glGetnTexImageARB(int target, int level, int format, int type2, DoubleBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnTexImageARB(target, level, format, type2, img.remaining() << 3, MemoryUtil.getAddress(img), function_pointer);
    }

    public static void glGetnTexImageARB(int target, int level, int format, int type2, FloatBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnTexImageARB(target, level, format, type2, img.remaining() << 2, MemoryUtil.getAddress(img), function_pointer);
    }

    public static void glGetnTexImageARB(int target, int level, int format, int type2, IntBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnTexImageARB(target, level, format, type2, img.remaining() << 2, MemoryUtil.getAddress(img), function_pointer);
    }

    public static void glGetnTexImageARB(int target, int level, int format, int type2, ShortBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnTexImageARB(target, level, format, type2, img.remaining() << 1, MemoryUtil.getAddress(img), function_pointer);
    }

    static native void nglGetnTexImageARB(int var0, int var1, int var2, int var3, int var4, long var5, long var7);

    public static void glGetnTexImageARB(int target, int level, int format, int type2, int img_bufSize, long img_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglGetnTexImageARBBO(target, level, format, type2, img_bufSize, img_buffer_offset, function_pointer);
    }

    static native void nglGetnTexImageARBBO(int var0, int var1, int var2, int var3, int var4, long var5, long var7);

    public static void glReadnPixelsARB(int x, int y, int width, int height, int format, int type2, ByteBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(data2);
        ARBRobustness.nglReadnPixelsARB(x, y, width, height, format, type2, data2.remaining(), MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glReadnPixelsARB(int x, int y, int width, int height, int format, int type2, DoubleBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(data2);
        ARBRobustness.nglReadnPixelsARB(x, y, width, height, format, type2, data2.remaining() << 3, MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glReadnPixelsARB(int x, int y, int width, int height, int format, int type2, FloatBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(data2);
        ARBRobustness.nglReadnPixelsARB(x, y, width, height, format, type2, data2.remaining() << 2, MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glReadnPixelsARB(int x, int y, int width, int height, int format, int type2, IntBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(data2);
        ARBRobustness.nglReadnPixelsARB(x, y, width, height, format, type2, data2.remaining() << 2, MemoryUtil.getAddress(data2), function_pointer);
    }

    public static void glReadnPixelsARB(int x, int y, int width, int height, int format, int type2, ShortBuffer data2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(data2);
        ARBRobustness.nglReadnPixelsARB(x, y, width, height, format, type2, data2.remaining() << 1, MemoryUtil.getAddress(data2), function_pointer);
    }

    static native void nglReadnPixelsARB(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7, long var9);

    public static void glReadnPixelsARB(int x, int y, int width, int height, int format, int type2, int data_bufSize, long data_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glReadnPixelsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglReadnPixelsARBBO(x, y, width, height, format, type2, data_bufSize, data_buffer_offset, function_pointer);
    }

    static native void nglReadnPixelsARBBO(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7, long var9);

    public static void glGetnColorTableARB(int target, int format, int type2, ByteBuffer table) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnColorTableARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(table);
        ARBRobustness.nglGetnColorTableARB(target, format, type2, table.remaining(), MemoryUtil.getAddress(table), function_pointer);
    }

    public static void glGetnColorTableARB(int target, int format, int type2, DoubleBuffer table) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnColorTableARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(table);
        ARBRobustness.nglGetnColorTableARB(target, format, type2, table.remaining() << 3, MemoryUtil.getAddress(table), function_pointer);
    }

    public static void glGetnColorTableARB(int target, int format, int type2, FloatBuffer table) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnColorTableARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(table);
        ARBRobustness.nglGetnColorTableARB(target, format, type2, table.remaining() << 2, MemoryUtil.getAddress(table), function_pointer);
    }

    static native void nglGetnColorTableARB(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glGetnConvolutionFilterARB(int target, int format, int type2, ByteBuffer image) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(image);
        ARBRobustness.nglGetnConvolutionFilterARB(target, format, type2, image.remaining(), MemoryUtil.getAddress(image), function_pointer);
    }

    public static void glGetnConvolutionFilterARB(int target, int format, int type2, DoubleBuffer image) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(image);
        ARBRobustness.nglGetnConvolutionFilterARB(target, format, type2, image.remaining() << 3, MemoryUtil.getAddress(image), function_pointer);
    }

    public static void glGetnConvolutionFilterARB(int target, int format, int type2, FloatBuffer image) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(image);
        ARBRobustness.nglGetnConvolutionFilterARB(target, format, type2, image.remaining() << 2, MemoryUtil.getAddress(image), function_pointer);
    }

    public static void glGetnConvolutionFilterARB(int target, int format, int type2, IntBuffer image) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(image);
        ARBRobustness.nglGetnConvolutionFilterARB(target, format, type2, image.remaining() << 2, MemoryUtil.getAddress(image), function_pointer);
    }

    public static void glGetnConvolutionFilterARB(int target, int format, int type2, ShortBuffer image) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(image);
        ARBRobustness.nglGetnConvolutionFilterARB(target, format, type2, image.remaining() << 1, MemoryUtil.getAddress(image), function_pointer);
    }

    static native void nglGetnConvolutionFilterARB(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glGetnConvolutionFilterARB(int target, int format, int type2, int image_bufSize, long image_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnConvolutionFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglGetnConvolutionFilterARBBO(target, format, type2, image_bufSize, image_buffer_offset, function_pointer);
    }

    static native void nglGetnConvolutionFilterARBBO(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ByteBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ByteBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ByteBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ByteBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ByteBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, DoubleBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, DoubleBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, DoubleBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, DoubleBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, DoubleBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, FloatBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, FloatBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, FloatBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, FloatBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, FloatBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, IntBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, IntBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, IntBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, IntBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, IntBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ShortBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ShortBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ShortBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ShortBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ByteBuffer row, ShortBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining(), MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ByteBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ByteBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ByteBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ByteBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ByteBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, DoubleBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, DoubleBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, DoubleBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, DoubleBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, DoubleBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, FloatBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, FloatBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, FloatBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, FloatBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, FloatBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, IntBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, IntBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, IntBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, IntBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, IntBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ShortBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ShortBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ShortBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ShortBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, DoubleBuffer row, ShortBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 3, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ByteBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ByteBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ByteBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ByteBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ByteBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, DoubleBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, DoubleBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, DoubleBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, DoubleBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, DoubleBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, FloatBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, FloatBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, FloatBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, FloatBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, FloatBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, IntBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, IntBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, IntBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, IntBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, IntBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ShortBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ShortBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ShortBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ShortBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, FloatBuffer row, ShortBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ByteBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ByteBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ByteBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ByteBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ByteBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, DoubleBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, DoubleBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, DoubleBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, DoubleBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, DoubleBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, FloatBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, FloatBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, FloatBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, FloatBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, FloatBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, IntBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, IntBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, IntBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, IntBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, IntBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ShortBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ShortBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ShortBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ShortBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, IntBuffer row, ShortBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 2, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ByteBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ByteBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ByteBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ByteBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ByteBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining(), MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, DoubleBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, DoubleBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, DoubleBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, DoubleBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, DoubleBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 3, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, FloatBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, FloatBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, FloatBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, FloatBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, FloatBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, IntBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, IntBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, IntBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, IntBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, IntBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 2, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ShortBuffer column, ByteBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ShortBuffer column, DoubleBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ShortBuffer column, FloatBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ShortBuffer column, IntBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    public static void glGetnSeparableFilterARB(int target, int format, int type2, ShortBuffer row, ShortBuffer column, ShortBuffer span) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(row);
        BufferChecks.checkDirect(column);
        BufferChecks.checkDirect(span);
        ARBRobustness.nglGetnSeparableFilterARB(target, format, type2, row.remaining() << 1, MemoryUtil.getAddress(row), column.remaining() << 1, MemoryUtil.getAddress(column), MemoryUtil.getAddress(span), function_pointer);
    }

    static native void nglGetnSeparableFilterARB(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9, long var11);

    public static void glGetnSeparableFilterARB(int target, int format, int type2, int row_rowBufSize, long row_buffer_offset, int column_columnBufSize, long column_buffer_offset, long span_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnSeparableFilterARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglGetnSeparableFilterARBBO(target, format, type2, row_rowBufSize, row_buffer_offset, column_columnBufSize, column_buffer_offset, span_buffer_offset, function_pointer);
    }

    static native void nglGetnSeparableFilterARBBO(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9, long var11);

    public static void glGetnHistogramARB(int target, boolean reset, int format, int type2, ByteBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnHistogramARB(target, reset, format, type2, values2.remaining(), MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnHistogramARB(int target, boolean reset, int format, int type2, DoubleBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnHistogramARB(target, reset, format, type2, values2.remaining() << 3, MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnHistogramARB(int target, boolean reset, int format, int type2, FloatBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnHistogramARB(target, reset, format, type2, values2.remaining() << 2, MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnHistogramARB(int target, boolean reset, int format, int type2, IntBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnHistogramARB(target, reset, format, type2, values2.remaining() << 2, MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnHistogramARB(int target, boolean reset, int format, int type2, ShortBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnHistogramARB(target, reset, format, type2, values2.remaining() << 1, MemoryUtil.getAddress(values2), function_pointer);
    }

    static native void nglGetnHistogramARB(int var0, boolean var1, int var2, int var3, int var4, long var5, long var7);

    public static void glGetnHistogramARB(int target, boolean reset, int format, int type2, int values_bufSize, long values_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnHistogramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglGetnHistogramARBBO(target, reset, format, type2, values_bufSize, values_buffer_offset, function_pointer);
    }

    static native void nglGetnHistogramARBBO(int var0, boolean var1, int var2, int var3, int var4, long var5, long var7);

    public static void glGetnMinmaxARB(int target, boolean reset, int format, int type2, ByteBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnMinmaxARB(target, reset, format, type2, values2.remaining(), MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnMinmaxARB(int target, boolean reset, int format, int type2, DoubleBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnMinmaxARB(target, reset, format, type2, values2.remaining() << 3, MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnMinmaxARB(int target, boolean reset, int format, int type2, FloatBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnMinmaxARB(target, reset, format, type2, values2.remaining() << 2, MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnMinmaxARB(int target, boolean reset, int format, int type2, IntBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnMinmaxARB(target, reset, format, type2, values2.remaining() << 2, MemoryUtil.getAddress(values2), function_pointer);
    }

    public static void glGetnMinmaxARB(int target, boolean reset, int format, int type2, ShortBuffer values2) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(values2);
        ARBRobustness.nglGetnMinmaxARB(target, reset, format, type2, values2.remaining() << 1, MemoryUtil.getAddress(values2), function_pointer);
    }

    static native void nglGetnMinmaxARB(int var0, boolean var1, int var2, int var3, int var4, long var5, long var7);

    public static void glGetnMinmaxARB(int target, boolean reset, int format, int type2, int values_bufSize, long values_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnMinmaxARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglGetnMinmaxARBBO(target, reset, format, type2, values_bufSize, values_buffer_offset, function_pointer);
    }

    static native void nglGetnMinmaxARBBO(int var0, boolean var1, int var2, int var3, int var4, long var5, long var7);

    public static void glGetnCompressedTexImageARB(int target, int lod, ByteBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnCompressedTexImageARB(target, lod, img.remaining(), MemoryUtil.getAddress(img), function_pointer);
    }

    public static void glGetnCompressedTexImageARB(int target, int lod, IntBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnCompressedTexImageARB(target, lod, img.remaining() << 2, MemoryUtil.getAddress(img), function_pointer);
    }

    public static void glGetnCompressedTexImageARB(int target, int lod, ShortBuffer img) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOdisabled(caps);
        BufferChecks.checkDirect(img);
        ARBRobustness.nglGetnCompressedTexImageARB(target, lod, img.remaining() << 1, MemoryUtil.getAddress(img), function_pointer);
    }

    static native void nglGetnCompressedTexImageARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnCompressedTexImageARB(int target, int lod, int img_bufSize, long img_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnCompressedTexImageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensurePackPBOenabled(caps);
        ARBRobustness.nglGetnCompressedTexImageARBBO(target, lod, img_bufSize, img_buffer_offset, function_pointer);
    }

    static native void nglGetnCompressedTexImageARBBO(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnUniformfvARB(int program, int location, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnUniformfvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(params);
        ARBRobustness.nglGetnUniformfvARB(program, location, params.remaining() << 2, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetnUniformfvARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnUniformivARB(int program, int location, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnUniformivARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(params);
        ARBRobustness.nglGetnUniformivARB(program, location, params.remaining() << 2, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetnUniformivARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnUniformuivARB(int program, int location, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnUniformuivARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(params);
        ARBRobustness.nglGetnUniformuivARB(program, location, params.remaining() << 2, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetnUniformuivARB(int var0, int var1, int var2, long var3, long var5);

    public static void glGetnUniformdvARB(int program, int location, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetnUniformdvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(params);
        ARBRobustness.nglGetnUniformdvARB(program, location, params.remaining() << 3, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetnUniformdvARB(int var0, int var1, int var2, long var3, long var5);
}

