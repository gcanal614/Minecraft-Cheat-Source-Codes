/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.opengl.GLContext;

public final class NVBindlessMultiDrawIndirect {
    private NVBindlessMultiDrawIndirect() {
    }

    public static void glMultiDrawArraysIndirectBindlessNV(int mode, ByteBuffer indirect, int drawCount, int stride, int vertexBufferCount) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glMultiDrawArraysIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureIndirectBOdisabled(caps);
        BufferChecks.checkBuffer(indirect, (stride == 0 ? 20 + 24 * vertexBufferCount : stride) * drawCount);
        NVBindlessMultiDrawIndirect.nglMultiDrawArraysIndirectBindlessNV(mode, MemoryUtil.getAddress(indirect), drawCount, stride, vertexBufferCount, function_pointer);
    }

    static native void nglMultiDrawArraysIndirectBindlessNV(int var0, long var1, int var3, int var4, int var5, long var6);

    public static void glMultiDrawArraysIndirectBindlessNV(int mode, long indirect_buffer_offset, int drawCount, int stride, int vertexBufferCount) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glMultiDrawArraysIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureIndirectBOenabled(caps);
        NVBindlessMultiDrawIndirect.nglMultiDrawArraysIndirectBindlessNVBO(mode, indirect_buffer_offset, drawCount, stride, vertexBufferCount, function_pointer);
    }

    static native void nglMultiDrawArraysIndirectBindlessNVBO(int var0, long var1, int var3, int var4, int var5, long var6);

    public static void glMultiDrawElementsIndirectBindlessNV(int mode, int type2, ByteBuffer indirect, int drawCount, int stride, int vertexBufferCount) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glMultiDrawElementsIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureIndirectBOdisabled(caps);
        BufferChecks.checkBuffer(indirect, (stride == 0 ? 48 + 24 * vertexBufferCount : stride) * drawCount);
        NVBindlessMultiDrawIndirect.nglMultiDrawElementsIndirectBindlessNV(mode, type2, MemoryUtil.getAddress(indirect), drawCount, stride, vertexBufferCount, function_pointer);
    }

    static native void nglMultiDrawElementsIndirectBindlessNV(int var0, int var1, long var2, int var4, int var5, int var6, long var7);

    public static void glMultiDrawElementsIndirectBindlessNV(int mode, int type2, long indirect_buffer_offset, int drawCount, int stride, int vertexBufferCount) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glMultiDrawElementsIndirectBindlessNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureIndirectBOenabled(caps);
        NVBindlessMultiDrawIndirect.nglMultiDrawElementsIndirectBindlessNVBO(mode, type2, indirect_buffer_offset, drawCount, stride, vertexBufferCount, function_pointer);
    }

    static native void nglMultiDrawElementsIndirectBindlessNVBO(int var0, int var1, long var2, int var4, int var5, int var6, long var7);
}

