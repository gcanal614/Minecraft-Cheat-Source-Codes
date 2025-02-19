/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.APIUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class EXTSeparateShaderObjects {
    public static final int GL_ACTIVE_PROGRAM_EXT = 35725;

    private EXTSeparateShaderObjects() {
    }

    public static void glUseShaderProgramEXT(int type2, int program) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glUseShaderProgramEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        EXTSeparateShaderObjects.nglUseShaderProgramEXT(type2, program, function_pointer);
    }

    static native void nglUseShaderProgramEXT(int var0, int var1, long var2);

    public static void glActiveProgramEXT(int program) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glActiveProgramEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        EXTSeparateShaderObjects.nglActiveProgramEXT(program, function_pointer);
    }

    static native void nglActiveProgramEXT(int var0, long var1);

    public static int glCreateShaderProgramEXT(int type2, ByteBuffer string) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glCreateShaderProgramEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(string);
        BufferChecks.checkNullTerminated(string);
        int __result = EXTSeparateShaderObjects.nglCreateShaderProgramEXT(type2, MemoryUtil.getAddress(string), function_pointer);
        return __result;
    }

    static native int nglCreateShaderProgramEXT(int var0, long var1, long var3);

    public static int glCreateShaderProgramEXT(int type2, CharSequence string) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glCreateShaderProgramEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        int __result = EXTSeparateShaderObjects.nglCreateShaderProgramEXT(type2, APIUtil.getBufferNT(caps, string), function_pointer);
        return __result;
    }
}

