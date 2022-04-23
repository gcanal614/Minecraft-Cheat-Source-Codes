/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL44;

public final class ARBClearTexture {
    public static final int GL_CLEAR_TEXTURE = 37733;

    private ARBClearTexture() {
    }

    public static void glClearTexImage(int texture, int level, int format, int type2, ByteBuffer data2) {
        GL44.glClearTexImage(texture, level, format, type2, data2);
    }

    public static void glClearTexImage(int texture, int level, int format, int type2, DoubleBuffer data2) {
        GL44.glClearTexImage(texture, level, format, type2, data2);
    }

    public static void glClearTexImage(int texture, int level, int format, int type2, FloatBuffer data2) {
        GL44.glClearTexImage(texture, level, format, type2, data2);
    }

    public static void glClearTexImage(int texture, int level, int format, int type2, IntBuffer data2) {
        GL44.glClearTexImage(texture, level, format, type2, data2);
    }

    public static void glClearTexImage(int texture, int level, int format, int type2, ShortBuffer data2) {
        GL44.glClearTexImage(texture, level, format, type2, data2);
    }

    public static void glClearTexImage(int texture, int level, int format, int type2, LongBuffer data2) {
        GL44.glClearTexImage(texture, level, format, type2, data2);
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, ByteBuffer data2) {
        GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, data2);
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, DoubleBuffer data2) {
        GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, data2);
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, FloatBuffer data2) {
        GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, data2);
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, IntBuffer data2) {
        GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, data2);
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, ShortBuffer data2) {
        GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, data2);
    }

    public static void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, LongBuffer data2) {
        GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, data2);
    }
}

