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
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL45;

public final class ARBDirectStateAccess {
    public static final int GL_TEXTURE_TARGET = 4102;
    public static final int GL_QUERY_TARGET = 33514;
    public static final int GL_TEXTURE_BINDING = 33515;

    private ARBDirectStateAccess() {
    }

    public static void glCreateTransformFeedbacks(IntBuffer ids) {
        GL45.glCreateTransformFeedbacks(ids);
    }

    public static int glCreateTransformFeedbacks() {
        return GL45.glCreateTransformFeedbacks();
    }

    public static void glTransformFeedbackBufferBase(int xfb, int index, int buffer) {
        GL45.glTransformFeedbackBufferBase(xfb, index, buffer);
    }

    public static void glTransformFeedbackBufferRange(int xfb, int index, int buffer, long offset, long size) {
        GL45.glTransformFeedbackBufferRange(xfb, index, buffer, offset, size);
    }

    public static void glGetTransformFeedback(int xfb, int pname, IntBuffer param) {
        GL45.glGetTransformFeedback(xfb, pname, param);
    }

    public static int glGetTransformFeedbacki(int xfb, int pname) {
        return GL45.glGetTransformFeedbacki(xfb, pname);
    }

    public static void glGetTransformFeedback(int xfb, int pname, int index, IntBuffer param) {
        GL45.glGetTransformFeedback(xfb, pname, index, param);
    }

    public static int glGetTransformFeedbacki(int xfb, int pname, int index) {
        return GL45.glGetTransformFeedbacki(xfb, pname, index);
    }

    public static void glGetTransformFeedback(int xfb, int pname, int index, LongBuffer param) {
        GL45.glGetTransformFeedback(xfb, pname, index, param);
    }

    public static long glGetTransformFeedbacki64(int xfb, int pname, int index) {
        return GL45.glGetTransformFeedbacki64(xfb, pname, index);
    }

    public static void glCreateBuffers(IntBuffer buffers) {
        GL45.glCreateBuffers(buffers);
    }

    public static int glCreateBuffers() {
        return GL45.glCreateBuffers();
    }

    public static void glNamedBufferStorage(int buffer, ByteBuffer data2, int flags) {
        GL45.glNamedBufferStorage(buffer, data2, flags);
    }

    public static void glNamedBufferStorage(int buffer, DoubleBuffer data2, int flags) {
        GL45.glNamedBufferStorage(buffer, data2, flags);
    }

    public static void glNamedBufferStorage(int buffer, FloatBuffer data2, int flags) {
        GL45.glNamedBufferStorage(buffer, data2, flags);
    }

    public static void glNamedBufferStorage(int buffer, IntBuffer data2, int flags) {
        GL45.glNamedBufferStorage(buffer, data2, flags);
    }

    public static void glNamedBufferStorage(int buffer, ShortBuffer data2, int flags) {
        GL45.glNamedBufferStorage(buffer, data2, flags);
    }

    public static void glNamedBufferStorage(int buffer, LongBuffer data2, int flags) {
        GL45.glNamedBufferStorage(buffer, data2, flags);
    }

    public static void glNamedBufferStorage(int buffer, long size, int flags) {
        GL45.glNamedBufferStorage(buffer, size, flags);
    }

    public static void glNamedBufferData(int buffer, long data_size, int usage) {
        GL45.glNamedBufferData(buffer, data_size, usage);
    }

    public static void glNamedBufferData(int buffer, ByteBuffer data2, int usage) {
        GL45.glNamedBufferData(buffer, data2, usage);
    }

    public static void glNamedBufferData(int buffer, DoubleBuffer data2, int usage) {
        GL45.glNamedBufferData(buffer, data2, usage);
    }

    public static void glNamedBufferData(int buffer, FloatBuffer data2, int usage) {
        GL45.glNamedBufferData(buffer, data2, usage);
    }

    public static void glNamedBufferData(int buffer, IntBuffer data2, int usage) {
        GL45.glNamedBufferData(buffer, data2, usage);
    }

    public static void glNamedBufferData(int buffer, ShortBuffer data2, int usage) {
        GL45.glNamedBufferData(buffer, data2, usage);
    }

    public static void glNamedBufferSubData(int buffer, long offset, ByteBuffer data2) {
        GL45.glNamedBufferSubData(buffer, offset, data2);
    }

    public static void glNamedBufferSubData(int buffer, long offset, DoubleBuffer data2) {
        GL45.glNamedBufferSubData(buffer, offset, data2);
    }

    public static void glNamedBufferSubData(int buffer, long offset, FloatBuffer data2) {
        GL45.glNamedBufferSubData(buffer, offset, data2);
    }

    public static void glNamedBufferSubData(int buffer, long offset, IntBuffer data2) {
        GL45.glNamedBufferSubData(buffer, offset, data2);
    }

    public static void glNamedBufferSubData(int buffer, long offset, ShortBuffer data2) {
        GL45.glNamedBufferSubData(buffer, offset, data2);
    }

    public static void glCopyNamedBufferSubData(int readBuffer, int writeBuffer, long readOffset, long writeOffset, long size) {
        GL45.glCopyNamedBufferSubData(readBuffer, writeBuffer, readOffset, writeOffset, size);
    }

    public static void glClearNamedBufferData(int buffer, int internalformat, int format, int type2, ByteBuffer data2) {
        GL45.glClearNamedBufferData(buffer, internalformat, format, type2, data2);
    }

    public static void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type2, ByteBuffer data2) {
        GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type2, data2);
    }

    public static ByteBuffer glMapNamedBuffer(int buffer, int access, ByteBuffer old_buffer) {
        return GL45.glMapNamedBuffer(buffer, access, old_buffer);
    }

    public static ByteBuffer glMapNamedBuffer(int buffer, int access, long length, ByteBuffer old_buffer) {
        return GL45.glMapNamedBuffer(buffer, access, length, old_buffer);
    }

    public static ByteBuffer glMapNamedBufferRange(int buffer, long offset, long length, int access, ByteBuffer old_buffer) {
        return GL45.glMapNamedBufferRange(buffer, offset, length, access, old_buffer);
    }

    public static boolean glUnmapNamedBuffer(int buffer) {
        return GL45.glUnmapNamedBuffer(buffer);
    }

    public static void glFlushMappedNamedBufferRange(int buffer, long offset, long length) {
        GL45.glFlushMappedNamedBufferRange(buffer, offset, length);
    }

    public static void glGetNamedBufferParameter(int buffer, int pname, IntBuffer params) {
        GL45.glGetNamedBufferParameter(buffer, pname, params);
    }

    public static int glGetNamedBufferParameteri(int buffer, int pname) {
        return GL45.glGetNamedBufferParameteri(buffer, pname);
    }

    public static void glGetNamedBufferParameter(int buffer, int pname, LongBuffer params) {
        GL45.glGetNamedBufferParameter(buffer, pname, params);
    }

    public static long glGetNamedBufferParameteri64(int buffer, int pname) {
        return GL45.glGetNamedBufferParameteri64(buffer, pname);
    }

    public static ByteBuffer glGetNamedBufferPointer(int buffer, int pname) {
        return GL45.glGetNamedBufferPointer(buffer, pname);
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, ByteBuffer data2) {
        GL45.glGetNamedBufferSubData(buffer, offset, data2);
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, DoubleBuffer data2) {
        GL45.glGetNamedBufferSubData(buffer, offset, data2);
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, FloatBuffer data2) {
        GL45.glGetNamedBufferSubData(buffer, offset, data2);
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, IntBuffer data2) {
        GL45.glGetNamedBufferSubData(buffer, offset, data2);
    }

    public static void glGetNamedBufferSubData(int buffer, long offset, ShortBuffer data2) {
        GL45.glGetNamedBufferSubData(buffer, offset, data2);
    }

    public static void glCreateFramebuffers(IntBuffer framebuffers) {
        GL45.glCreateFramebuffers(framebuffers);
    }

    public static int glCreateFramebuffers() {
        return GL45.glCreateFramebuffers();
    }

    public static void glNamedFramebufferRenderbuffer(int framebuffer, int attachment, int renderbuffertarget, int renderbuffer) {
        GL45.glNamedFramebufferRenderbuffer(framebuffer, attachment, renderbuffertarget, renderbuffer);
    }

    public static void glNamedFramebufferParameteri(int framebuffer, int pname, int param) {
        GL45.glNamedFramebufferParameteri(framebuffer, pname, param);
    }

    public static void glNamedFramebufferTexture(int framebuffer, int attachment, int texture, int level) {
        GL45.glNamedFramebufferTexture(framebuffer, attachment, texture, level);
    }

    public static void glNamedFramebufferTextureLayer(int framebuffer, int attachment, int texture, int level, int layer) {
        GL45.glNamedFramebufferTextureLayer(framebuffer, attachment, texture, level, layer);
    }

    public static void glNamedFramebufferDrawBuffer(int framebuffer, int mode) {
        GL45.glNamedFramebufferDrawBuffer(framebuffer, mode);
    }

    public static void glNamedFramebufferDrawBuffers(int framebuffer, IntBuffer bufs) {
        GL45.glNamedFramebufferDrawBuffers(framebuffer, bufs);
    }

    public static void glNamedFramebufferReadBuffer(int framebuffer, int mode) {
        GL45.glNamedFramebufferReadBuffer(framebuffer, mode);
    }

    public static void glInvalidateNamedFramebufferData(int framebuffer, IntBuffer attachments) {
        GL45.glInvalidateNamedFramebufferData(framebuffer, attachments);
    }

    public static void glInvalidateNamedFramebufferSubData(int framebuffer, IntBuffer attachments, int x, int y, int width, int height) {
        GL45.glInvalidateNamedFramebufferSubData(framebuffer, attachments, x, y, width, height);
    }

    public static void glClearNamedFramebuffer(int framebuffer, int buffer, int drawbuffer, IntBuffer value) {
        GL45.glClearNamedFramebuffer(framebuffer, buffer, drawbuffer, value);
    }

    public static void glClearNamedFramebufferu(int framebuffer, int buffer, int drawbuffer, IntBuffer value) {
        GL45.glClearNamedFramebufferu(framebuffer, buffer, drawbuffer, value);
    }

    public static void glClearNamedFramebuffer(int framebuffer, int buffer, int drawbuffer, FloatBuffer value) {
        GL45.glClearNamedFramebuffer(framebuffer, buffer, drawbuffer, value);
    }

    public static void glClearNamedFramebufferfi(int framebuffer, int buffer, float depth, int stencil) {
        GL45.glClearNamedFramebufferfi(framebuffer, buffer, depth, stencil);
    }

    public static void glBlitNamedFramebuffer(int readFramebuffer, int drawFramebuffer, int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
        GL45.glBlitNamedFramebuffer(readFramebuffer, drawFramebuffer, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }

    public static int glCheckNamedFramebufferStatus(int framebuffer, int target) {
        return GL45.glCheckNamedFramebufferStatus(framebuffer, target);
    }

    public static void glGetNamedFramebufferParameter(int framebuffer, int pname, IntBuffer params) {
        GL45.glGetNamedFramebufferParameter(framebuffer, pname, params);
    }

    public static int glGetNamedFramebufferParameter(int framebuffer, int pname) {
        return GL45.glGetNamedFramebufferParameter(framebuffer, pname);
    }

    public static void glGetNamedFramebufferAttachmentParameter(int framebuffer, int attachment, int pname, IntBuffer params) {
        GL45.glGetNamedFramebufferAttachmentParameter(framebuffer, attachment, pname, params);
    }

    public static int glGetNamedFramebufferAttachmentParameter(int framebuffer, int attachment, int pname) {
        return GL45.glGetNamedFramebufferAttachmentParameter(framebuffer, attachment, pname);
    }

    public static void glCreateRenderbuffers(IntBuffer renderbuffers) {
        GL45.glCreateRenderbuffers(renderbuffers);
    }

    public static int glCreateRenderbuffers() {
        return GL45.glCreateRenderbuffers();
    }

    public static void glNamedRenderbufferStorage(int renderbuffer, int internalformat, int width, int height) {
        GL45.glNamedRenderbufferStorage(renderbuffer, internalformat, width, height);
    }

    public static void glNamedRenderbufferStorageMultisample(int renderbuffer, int samples, int internalformat, int width, int height) {
        GL45.glNamedRenderbufferStorageMultisample(renderbuffer, samples, internalformat, width, height);
    }

    public static void glGetNamedRenderbufferParameter(int renderbuffer, int pname, IntBuffer params) {
        GL45.glGetNamedRenderbufferParameter(renderbuffer, pname, params);
    }

    public static int glGetNamedRenderbufferParameter(int renderbuffer, int pname) {
        return GL45.glGetNamedRenderbufferParameter(renderbuffer, pname);
    }

    public static void glCreateTextures(int target, IntBuffer textures) {
        GL45.glCreateTextures(target, textures);
    }

    public static int glCreateTextures(int target) {
        return GL45.glCreateTextures(target);
    }

    public static void glTextureBuffer(int texture, int internalformat, int buffer) {
        GL45.glTextureBuffer(texture, internalformat, buffer);
    }

    public static void glTextureBufferRange(int texture, int internalformat, int buffer, long offset, long size) {
        GL45.glTextureBufferRange(texture, internalformat, buffer, offset, size);
    }

    public static void glTextureStorage1D(int texture, int levels, int internalformat, int width) {
        GL45.glTextureStorage1D(texture, levels, internalformat, width);
    }

    public static void glTextureStorage2D(int texture, int levels, int internalformat, int width, int height) {
        GL45.glTextureStorage2D(texture, levels, internalformat, width, height);
    }

    public static void glTextureStorage3D(int texture, int levels, int internalformat, int width, int height, int depth) {
        GL45.glTextureStorage3D(texture, levels, internalformat, width, height, depth);
    }

    public static void glTextureStorage2DMultisample(int texture, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) {
        GL45.glTextureStorage2DMultisample(texture, samples, internalformat, width, height, fixedsamplelocations);
    }

    public static void glTextureStorage3DMultisample(int texture, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) {
        GL45.glTextureStorage3DMultisample(texture, samples, internalformat, width, height, depth, fixedsamplelocations);
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type2, ByteBuffer pixels) {
        GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type2, pixels);
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type2, DoubleBuffer pixels) {
        GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type2, pixels);
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type2, FloatBuffer pixels) {
        GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type2, pixels);
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type2, IntBuffer pixels) {
        GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type2, pixels);
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type2, ShortBuffer pixels) {
        GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type2, pixels);
    }

    public static void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type2, long pixels_buffer_offset) {
        GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type2, pixels_buffer_offset);
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type2, ByteBuffer pixels) {
        GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type2, pixels);
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type2, DoubleBuffer pixels) {
        GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type2, pixels);
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type2, FloatBuffer pixels) {
        GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type2, pixels);
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type2, IntBuffer pixels) {
        GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type2, pixels);
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type2, ShortBuffer pixels) {
        GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type2, pixels);
    }

    public static void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type2, long pixels_buffer_offset) {
        GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type2, pixels_buffer_offset);
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, ByteBuffer pixels) {
        GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, DoubleBuffer pixels) {
        GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, FloatBuffer pixels) {
        GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, IntBuffer pixels) {
        GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, ShortBuffer pixels) {
        GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels);
    }

    public static void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type2, long pixels_buffer_offset) {
        GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type2, pixels_buffer_offset);
    }

    public static void glCompressedTextureSubImage1D(int texture, int level, int xoffset, int width, int format, ByteBuffer data2) {
        GL45.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, data2);
    }

    public static void glCompressedTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int data_imageSize, long data_buffer_offset) {
        GL45.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, data_imageSize, data_buffer_offset);
    }

    public static void glCompressedTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer data2) {
        GL45.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, data2);
    }

    public static void glCompressedTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int data_imageSize, long data_buffer_offset) {
        GL45.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, data_imageSize, data_buffer_offset);
    }

    public static void glCompressedTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, ByteBuffer data2) {
        GL45.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data2);
    }

    public static void glCompressedTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, long data_buffer_offset) {
        GL45.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data_buffer_offset);
    }

    public static void glCopyTextureSubImage1D(int texture, int level, int xoffset, int x, int y, int width) {
        GL45.glCopyTextureSubImage1D(texture, level, xoffset, x, y, width);
    }

    public static void glCopyTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        GL45.glCopyTextureSubImage2D(texture, level, xoffset, yoffset, x, y, width, height);
    }

    public static void glCopyTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) {
        GL45.glCopyTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, x, y, width, height);
    }

    public static void glTextureParameterf(int texture, int pname, float param) {
        GL45.glTextureParameterf(texture, pname, param);
    }

    public static void glTextureParameter(int texture, int pname, FloatBuffer params) {
        GL45.glTextureParameter(texture, pname, params);
    }

    public static void glTextureParameteri(int texture, int pname, int param) {
        GL45.glTextureParameteri(texture, pname, param);
    }

    public static void glTextureParameterI(int texture, int pname, IntBuffer params) {
        GL45.glTextureParameterI(texture, pname, params);
    }

    public static void glTextureParameterIu(int texture, int pname, IntBuffer params) {
        GL45.glTextureParameterIu(texture, pname, params);
    }

    public static void glTextureParameter(int texture, int pname, IntBuffer params) {
        GL45.glTextureParameter(texture, pname, params);
    }

    public static void glGenerateTextureMipmap(int texture) {
        GL45.glGenerateTextureMipmap(texture);
    }

    public static void glBindTextureUnit(int unit, int texture) {
        GL45.glBindTextureUnit(unit, texture);
    }

    public static void glGetTextureImage(int texture, int level, int format, int type2, ByteBuffer pixels) {
        GL45.glGetTextureImage(texture, level, format, type2, pixels);
    }

    public static void glGetTextureImage(int texture, int level, int format, int type2, DoubleBuffer pixels) {
        GL45.glGetTextureImage(texture, level, format, type2, pixels);
    }

    public static void glGetTextureImage(int texture, int level, int format, int type2, FloatBuffer pixels) {
        GL45.glGetTextureImage(texture, level, format, type2, pixels);
    }

    public static void glGetTextureImage(int texture, int level, int format, int type2, IntBuffer pixels) {
        GL45.glGetTextureImage(texture, level, format, type2, pixels);
    }

    public static void glGetTextureImage(int texture, int level, int format, int type2, ShortBuffer pixels) {
        GL45.glGetTextureImage(texture, level, format, type2, pixels);
    }

    public static void glGetTextureImage(int texture, int level, int format, int type2, int pixels_bufSize, long pixels_buffer_offset) {
        GL45.glGetTextureImage(texture, level, format, type2, pixels_bufSize, pixels_buffer_offset);
    }

    public static void glGetCompressedTextureImage(int texture, int level, ByteBuffer pixels) {
        GL45.glGetCompressedTextureImage(texture, level, pixels);
    }

    public static void glGetCompressedTextureImage(int texture, int level, IntBuffer pixels) {
        GL45.glGetCompressedTextureImage(texture, level, pixels);
    }

    public static void glGetCompressedTextureImage(int texture, int level, ShortBuffer pixels) {
        GL45.glGetCompressedTextureImage(texture, level, pixels);
    }

    public static void glGetCompressedTextureImage(int texture, int level, int pixels_bufSize, long pixels_buffer_offset) {
        GL45.glGetCompressedTextureImage(texture, level, pixels_bufSize, pixels_buffer_offset);
    }

    public static void glGetTextureLevelParameter(int texture, int level, int pname, FloatBuffer params) {
        GL45.glGetTextureLevelParameter(texture, level, pname, params);
    }

    public static float glGetTextureLevelParameterf(int texture, int level, int pname) {
        return GL45.glGetTextureLevelParameterf(texture, level, pname);
    }

    public static void glGetTextureLevelParameter(int texture, int level, int pname, IntBuffer params) {
        GL45.glGetTextureLevelParameter(texture, level, pname, params);
    }

    public static int glGetTextureLevelParameteri(int texture, int level, int pname) {
        return GL45.glGetTextureLevelParameteri(texture, level, pname);
    }

    public static void glGetTextureParameter(int texture, int pname, FloatBuffer params) {
        GL45.glGetTextureParameter(texture, pname, params);
    }

    public static float glGetTextureParameterf(int texture, int pname) {
        return GL45.glGetTextureParameterf(texture, pname);
    }

    public static void glGetTextureParameterI(int texture, int pname, IntBuffer params) {
        GL45.glGetTextureParameterI(texture, pname, params);
    }

    public static int glGetTextureParameterIi(int texture, int pname) {
        return GL45.glGetTextureParameterIi(texture, pname);
    }

    public static void glGetTextureParameterIu(int texture, int pname, IntBuffer params) {
        GL45.glGetTextureParameterIu(texture, pname, params);
    }

    public static int glGetTextureParameterIui(int texture, int pname) {
        return GL45.glGetTextureParameterIui(texture, pname);
    }

    public static void glGetTextureParameter(int texture, int pname, IntBuffer params) {
        GL45.glGetTextureParameter(texture, pname, params);
    }

    public static int glGetTextureParameteri(int texture, int pname) {
        return GL45.glGetTextureParameteri(texture, pname);
    }

    public static void glCreateVertexArrays(IntBuffer arrays) {
        GL45.glCreateVertexArrays(arrays);
    }

    public static int glCreateVertexArrays() {
        return GL45.glCreateVertexArrays();
    }

    public static void glDisableVertexArrayAttrib(int vaobj, int index) {
        GL45.glDisableVertexArrayAttrib(vaobj, index);
    }

    public static void glEnableVertexArrayAttrib(int vaobj, int index) {
        GL45.glEnableVertexArrayAttrib(vaobj, index);
    }

    public static void glVertexArrayElementBuffer(int vaobj, int buffer) {
        GL45.glVertexArrayElementBuffer(vaobj, buffer);
    }

    public static void glVertexArrayVertexBuffer(int vaobj, int bindingindex, int buffer, long offset, int stride) {
        GL45.glVertexArrayVertexBuffer(vaobj, bindingindex, buffer, offset, stride);
    }

    public static void glVertexArrayVertexBuffers(int vaobj, int first, int count, IntBuffer buffers, PointerBuffer offsets, IntBuffer strides) {
        GL45.glVertexArrayVertexBuffers(vaobj, first, count, buffers, offsets, strides);
    }

    public static void glVertexArrayAttribFormat(int vaobj, int attribindex, int size, int type2, boolean normalized, int relativeoffset) {
        GL45.glVertexArrayAttribFormat(vaobj, attribindex, size, type2, normalized, relativeoffset);
    }

    public static void glVertexArrayAttribIFormat(int vaobj, int attribindex, int size, int type2, int relativeoffset) {
        GL45.glVertexArrayAttribIFormat(vaobj, attribindex, size, type2, relativeoffset);
    }

    public static void glVertexArrayAttribLFormat(int vaobj, int attribindex, int size, int type2, int relativeoffset) {
        GL45.glVertexArrayAttribLFormat(vaobj, attribindex, size, type2, relativeoffset);
    }

    public static void glVertexArrayAttribBinding(int vaobj, int attribindex, int bindingindex) {
        GL45.glVertexArrayAttribBinding(vaobj, attribindex, bindingindex);
    }

    public static void glVertexArrayBindingDivisor(int vaobj, int bindingindex, int divisor) {
        GL45.glVertexArrayBindingDivisor(vaobj, bindingindex, divisor);
    }

    public static void glGetVertexArray(int vaobj, int pname, IntBuffer param) {
        GL45.glGetVertexArray(vaobj, pname, param);
    }

    public static int glGetVertexArray(int vaobj, int pname) {
        return GL45.glGetVertexArray(vaobj, pname);
    }

    public static void glGetVertexArrayIndexed(int vaobj, int index, int pname, IntBuffer param) {
        GL45.glGetVertexArrayIndexed(vaobj, index, pname, param);
    }

    public static int glGetVertexArrayIndexed(int vaobj, int index, int pname) {
        return GL45.glGetVertexArrayIndexed(vaobj, index, pname);
    }

    public static void glGetVertexArrayIndexed64i(int vaobj, int index, int pname, LongBuffer param) {
        GL45.glGetVertexArrayIndexed64i(vaobj, index, pname, param);
    }

    public static long glGetVertexArrayIndexed64i(int vaobj, int index, int pname) {
        return GL45.glGetVertexArrayIndexed64i(vaobj, index, pname);
    }

    public static void glCreateSamplers(IntBuffer samplers) {
        GL45.glCreateSamplers(samplers);
    }

    public static int glCreateSamplers() {
        return GL45.glCreateSamplers();
    }

    public static void glCreateProgramPipelines(IntBuffer pipelines) {
        GL45.glCreateProgramPipelines(pipelines);
    }

    public static int glCreateProgramPipelines() {
        return GL45.glCreateProgramPipelines();
    }

    public static void glCreateQueries(int target, IntBuffer ids) {
        GL45.glCreateQueries(target, ids);
    }

    public static int glCreateQueries(int target) {
        return GL45.glCreateQueries(target);
    }
}

