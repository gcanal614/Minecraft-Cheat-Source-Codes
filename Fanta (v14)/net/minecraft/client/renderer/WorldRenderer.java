/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.TextureUtils
 *  org.apache.logging.log4j.LogManager
 *  org.lwjgl.opengl.GL11
 *  shadersmod.client.SVertexBuilder
 */
package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.WorldRenderer$1;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;
import shadersmod.client.SVertexBuilder;

public class WorldRenderer {
    private ByteBuffer byteBuffer;
    public IntBuffer rawIntBuffer;
    private ShortBuffer field_181676_c;
    public FloatBuffer rawFloatBuffer;
    public int vertexCount;
    private VertexFormatElement field_181677_f;
    private int field_181678_g;
    private boolean needsUpdate;
    public int drawMode;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private VertexFormat vertexFormat;
    private boolean isDrawing;
    private static final String __OBFID = "CL_00000942";
    private EnumWorldBlockLayer blockLayer = null;
    private boolean[] drawnIcons = new boolean[256];
    private TextureAtlasSprite[] quadSprites = null;
    private TextureAtlasSprite[] quadSpritesPrev = null;
    private TextureAtlasSprite quadSprite = null;
    public SVertexBuilder sVertexBuilder;

    public WorldRenderer(int bufferSizeIn) {
        if (Config.isShaders()) {
            bufferSizeIn *= 2;
        }
        this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.field_181676_c = this.byteBuffer.asShortBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        SVertexBuilder.initVertexBuilder((WorldRenderer)this);
    }

    private void func_181670_b(int p_181670_1_) {
        if (Config.isShaders()) {
            p_181670_1_ *= 2;
        }
        if (p_181670_1_ > this.rawIntBuffer.remaining()) {
            int i = this.byteBuffer.capacity();
            int j = i % 0x200000;
            int k = j + (((this.rawIntBuffer.position() + p_181670_1_) * 4 - j) / 0x200000 + 1) * 0x200000;
            LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + i + " bytes, new size " + k + " bytes.");
            int l = this.rawIntBuffer.position();
            ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(k);
            this.byteBuffer.position(0);
            bytebuffer.put(this.byteBuffer);
            bytebuffer.rewind();
            this.byteBuffer = bytebuffer;
            this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
            this.rawIntBuffer = this.byteBuffer.asIntBuffer();
            this.rawIntBuffer.position(l);
            this.field_181676_c = this.byteBuffer.asShortBuffer();
            this.field_181676_c.position(l << 1);
            if (this.quadSprites != null) {
                TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
                int i1 = this.getBufferQuadSize();
                this.quadSprites = new TextureAtlasSprite[i1];
                System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
                this.quadSpritesPrev = null;
            }
        }
    }

    public void func_181674_a(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
        int i = this.vertexCount / 4;
        float[] afloat = new float[i];
        int j = 0;
        while (j < i) {
            afloat[j] = WorldRenderer.func_181665_a(this.rawFloatBuffer, (float)((double)p_181674_1_ + this.xOffset), (float)((double)p_181674_2_ + this.yOffset), (float)((double)p_181674_3_ + this.zOffset), this.vertexFormat.func_181719_f(), j * this.vertexFormat.getNextOffset());
            ++j;
        }
        Integer[] ainteger = new Integer[i];
        int k = 0;
        while (k < ainteger.length) {
            ainteger[k] = k;
            ++k;
        }
        Arrays.sort(ainteger, new WorldRenderer$1(this, afloat));
        BitSet bitset = new BitSet();
        int l = this.vertexFormat.getNextOffset();
        int[] aint = new int[l];
        int l1 = 0;
        while ((l1 = bitset.nextClearBit(l1)) < ainteger.length) {
            int i1 = ainteger[l1];
            if (i1 != l1) {
                this.rawIntBuffer.limit(i1 * l + l);
                this.rawIntBuffer.position(i1 * l);
                this.rawIntBuffer.get(aint);
                int j1 = i1;
                int k1 = ainteger[i1];
                while (j1 != l1) {
                    this.rawIntBuffer.limit(k1 * l + l);
                    this.rawIntBuffer.position(k1 * l);
                    IntBuffer intbuffer = this.rawIntBuffer.slice();
                    this.rawIntBuffer.limit(j1 * l + l);
                    this.rawIntBuffer.position(j1 * l);
                    this.rawIntBuffer.put(intbuffer);
                    bitset.set(j1);
                    j1 = k1;
                    k1 = ainteger[k1];
                }
                this.rawIntBuffer.limit(l1 * l + l);
                this.rawIntBuffer.position(l1 * l);
                this.rawIntBuffer.put(aint);
            }
            bitset.set(l1);
            ++l1;
        }
        this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
        this.rawIntBuffer.position(this.func_181664_j());
        if (this.quadSprites != null) {
            TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];
            int i2 = this.vertexFormat.func_181719_f() / 4 * 4;
            int j2 = 0;
            while (j2 < ainteger.length) {
                int k2 = ainteger[j2];
                atextureatlassprite[j2] = this.quadSprites[k2];
                ++j2;
            }
            System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
        }
    }

    public State func_181672_a() {
        this.rawIntBuffer.rewind();
        int i = this.func_181664_j();
        this.rawIntBuffer.limit(i);
        int[] aint = new int[i];
        this.rawIntBuffer.get(aint);
        this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
        this.rawIntBuffer.position(i);
        TextureAtlasSprite[] atextureatlassprite = null;
        if (this.quadSprites != null) {
            int j = this.vertexCount / 4;
            atextureatlassprite = new TextureAtlasSprite[j];
            System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
        }
        return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
    }

    public int func_181664_j() {
        return this.vertexCount * this.vertexFormat.func_181719_f();
    }

    private static float func_181665_a(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
        float f = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 0);
        float f1 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 1);
        float f2 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 2);
        float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 0);
        float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 1);
        float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 2);
        float f6 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 0);
        float f7 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 1);
        float f8 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 2);
        float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 0);
        float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
        float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
        float f12 = (f + f3 + f6 + f9) * 0.25f - p_181665_1_;
        float f13 = (f1 + f4 + f7 + f10) * 0.25f - p_181665_2_;
        float f14 = (f2 + f5 + f8 + f11) * 0.25f - p_181665_3_;
        return f12 * f12 + f13 * f13 + f14 * f14;
    }

    public void setVertexState(State state) {
        this.rawIntBuffer.clear();
        this.func_181670_b(state.getRawBuffer().length);
        this.rawIntBuffer.put(state.getRawBuffer());
        this.vertexCount = state.getVertexCount();
        this.vertexFormat = new VertexFormat(state.getVertexFormat());
        if (state.stateQuadSprites != null) {
            if (this.quadSprites == null) {
                this.quadSprites = this.quadSpritesPrev;
            }
            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
            TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
            System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
        } else {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
        }
    }

    public void reset() {
        this.vertexCount = 0;
        this.field_181677_f = null;
        this.field_181678_g = 0;
        this.quadSprite = null;
    }

    public void begin(int p_181668_1_, VertexFormat p_181668_2_) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = p_181668_1_;
        this.vertexFormat = p_181668_2_;
        this.field_181677_f = p_181668_2_.getElement(this.field_181678_g);
        this.needsUpdate = false;
        this.byteBuffer.limit(this.byteBuffer.capacity());
        if (Config.isShaders()) {
            SVertexBuilder.endSetVertexFormat((WorldRenderer)this);
        }
        if (Config.isMultiTexture()) {
            if (this.blockLayer != null) {
                if (this.quadSprites == null) {
                    this.quadSprites = this.quadSpritesPrev;
                }
                if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                    this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
                }
            }
        } else {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
        }
    }

    public WorldRenderer tex(double p_181673_1_, double p_181673_3_) {
        if (this.quadSprite != null && this.quadSprites != null) {
            p_181673_1_ = this.quadSprite.toSingleU((float)p_181673_1_);
            p_181673_3_ = this.quadSprite.toSingleV((float)p_181673_3_);
            this.quadSprites[this.vertexCount / 4] = this.quadSprite;
        }
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (this.field_181677_f.getType()) {
            case FLOAT: {
                this.byteBuffer.putFloat(i, (float)p_181673_1_);
                this.byteBuffer.putFloat(i + 4, (float)p_181673_3_);
                break;
            }
            case UINT: 
            case INT: {
                this.byteBuffer.putInt(i, (int)p_181673_1_);
                this.byteBuffer.putInt(i + 4, (int)p_181673_3_);
                break;
            }
            case USHORT: 
            case SHORT: {
                this.byteBuffer.putShort(i, (short)p_181673_3_);
                this.byteBuffer.putShort(i + 2, (short)p_181673_1_);
                break;
            }
            case UBYTE: 
            case BYTE: {
                this.byteBuffer.put(i, (byte)p_181673_3_);
                this.byteBuffer.put(i + 1, (byte)p_181673_1_);
            }
        }
        this.func_181667_k();
        return this;
    }

    public WorldRenderer lightmap(int p_181671_1_, int p_181671_2_) {
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (this.field_181677_f.getType()) {
            case FLOAT: {
                this.byteBuffer.putFloat(i, p_181671_1_);
                this.byteBuffer.putFloat(i + 4, p_181671_2_);
                break;
            }
            case UINT: 
            case INT: {
                this.byteBuffer.putInt(i, p_181671_1_);
                this.byteBuffer.putInt(i + 4, p_181671_2_);
                break;
            }
            case USHORT: 
            case SHORT: {
                this.byteBuffer.putShort(i, (short)p_181671_2_);
                this.byteBuffer.putShort(i + 2, (short)p_181671_1_);
                break;
            }
            case UBYTE: 
            case BYTE: {
                this.byteBuffer.put(i, (byte)p_181671_2_);
                this.byteBuffer.put(i + 1, (byte)p_181671_1_);
            }
        }
        this.func_181667_k();
        return this;
    }

    public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
        int i = (this.vertexCount - 4) * this.vertexFormat.func_181719_f() + this.vertexFormat.getUvOffsetById(1) / 4;
        int j = this.vertexFormat.getNextOffset() >> 2;
        this.rawIntBuffer.put(i, p_178962_1_);
        this.rawIntBuffer.put(i + j, p_178962_2_);
        this.rawIntBuffer.put(i + j * 2, p_178962_3_);
        this.rawIntBuffer.put(i + j * 3, p_178962_4_);
    }

    public void putPosition(double x, double y, double z) {
        int i = this.vertexFormat.func_181719_f();
        int j = (this.vertexCount - 4) * i;
        int k = 0;
        while (k < 4) {
            int l = j + k * i;
            int i1 = l + 1;
            int j1 = i1 + 1;
            this.rawIntBuffer.put(l, Float.floatToRawIntBits((float)(x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
            this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float)(y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
            this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float)(z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
            ++k;
        }
    }

    public int getColorIndex(int p_78909_1_) {
        return ((this.vertexCount - p_78909_1_) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
    }

    public void putColorMultiplier(float red, float green, float blue, int p_178978_4_) {
        int i = this.getColorIndex(p_178978_4_);
        int j = -1;
        if (!this.needsUpdate) {
            j = this.rawIntBuffer.get(i);
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                int k = (int)((float)(j & 0xFF) * red);
                int l = (int)((float)(j >> 8 & 0xFF) * green);
                int i1 = (int)((float)(j >> 16 & 0xFF) * blue);
                j &= 0xFF000000;
                j = j | i1 << 16 | l << 8 | k;
            } else {
                int j1 = (int)((float)(j >> 24 & 0xFF) * red);
                int k1 = (int)((float)(j >> 16 & 0xFF) * green);
                int l1 = (int)((float)(j >> 8 & 0xFF) * blue);
                j &= 0xFF;
                j = j | j1 << 24 | k1 << 16 | l1 << 8;
            }
        }
        this.rawIntBuffer.put(i, j);
    }

    private void putColor(int argb, int p_178988_2_) {
        int i = this.getColorIndex(p_178988_2_);
        int j = argb >> 16 & 0xFF;
        int k = argb >> 8 & 0xFF;
        int l = argb & 0xFF;
        int i1 = argb >> 24 & 0xFF;
        this.putColorRGBA(i, j, k, l, i1);
    }

    public void putColorRGB_F(float red, float green, float blue, int p_178994_4_) {
        int i = this.getColorIndex(p_178994_4_);
        int j = MathHelper.clamp_int((int)(red * 255.0f), 0, 255);
        int k = MathHelper.clamp_int((int)(green * 255.0f), 0, 255);
        int l = MathHelper.clamp_int((int)(blue * 255.0f), 0, 255);
        this.putColorRGBA(i, j, k, l, 255);
    }

    public void putColorRGBA(int index, int red, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(index, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | red);
        } else {
            this.rawIntBuffer.put(index, red << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
        }
    }

    public void markDirty() {
        this.needsUpdate = true;
    }

    public WorldRenderer color(float p_181666_1_, float p_181666_2_, float p_181666_3_, float p_181666_4_) {
        return this.color((int)(p_181666_1_ * 255.0f), (int)(p_181666_2_ * 255.0f), (int)(p_181666_3_ * 255.0f), (int)(p_181666_4_ * 255.0f));
    }

    public WorldRenderer color(int p_181669_1_, int p_181669_2_, int p_181669_3_, int p_181669_4_) {
        if (this.needsUpdate) {
            return this;
        }
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (this.field_181677_f.getType()) {
            case FLOAT: {
                this.byteBuffer.putFloat(i, (float)p_181669_1_ / 255.0f);
                this.byteBuffer.putFloat(i + 4, (float)p_181669_2_ / 255.0f);
                this.byteBuffer.putFloat(i + 8, (float)p_181669_3_ / 255.0f);
                this.byteBuffer.putFloat(i + 12, (float)p_181669_4_ / 255.0f);
                break;
            }
            case UINT: 
            case INT: {
                this.byteBuffer.putFloat(i, p_181669_1_);
                this.byteBuffer.putFloat(i + 4, p_181669_2_);
                this.byteBuffer.putFloat(i + 8, p_181669_3_);
                this.byteBuffer.putFloat(i + 12, p_181669_4_);
                break;
            }
            case USHORT: 
            case SHORT: {
                this.byteBuffer.putShort(i, (short)p_181669_1_);
                this.byteBuffer.putShort(i + 2, (short)p_181669_2_);
                this.byteBuffer.putShort(i + 4, (short)p_181669_3_);
                this.byteBuffer.putShort(i + 6, (short)p_181669_4_);
                break;
            }
            case UBYTE: 
            case BYTE: {
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    this.byteBuffer.put(i, (byte)p_181669_1_);
                    this.byteBuffer.put(i + 1, (byte)p_181669_2_);
                    this.byteBuffer.put(i + 2, (byte)p_181669_3_);
                    this.byteBuffer.put(i + 3, (byte)p_181669_4_);
                    break;
                }
                this.byteBuffer.put(i, (byte)p_181669_4_);
                this.byteBuffer.put(i + 1, (byte)p_181669_3_);
                this.byteBuffer.put(i + 2, (byte)p_181669_2_);
                this.byteBuffer.put(i + 3, (byte)p_181669_1_);
            }
        }
        this.func_181667_k();
        return this;
    }

    public void addVertexData(int[] vertexData) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertexData((WorldRenderer)this, (int[])vertexData);
        }
        this.func_181670_b(vertexData.length);
        this.rawIntBuffer.position(this.func_181664_j());
        this.rawIntBuffer.put(vertexData);
        this.vertexCount += vertexData.length / this.vertexFormat.func_181719_f();
        if (Config.isShaders()) {
            SVertexBuilder.endAddVertexData((WorldRenderer)this);
        }
    }

    public void endVertex() {
        ++this.vertexCount;
        this.func_181670_b(this.vertexFormat.func_181719_f());
        this.field_181678_g = 0;
        this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
        if (Config.isShaders()) {
            SVertexBuilder.endAddVertex((WorldRenderer)this);
        }
    }

    public WorldRenderer pos(double p_181662_1_, double p_181662_3_, double p_181662_5_) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertex((WorldRenderer)this);
        }
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (this.field_181677_f.getType()) {
            case FLOAT: {
                this.byteBuffer.putFloat(i, (float)(p_181662_1_ + this.xOffset));
                this.byteBuffer.putFloat(i + 4, (float)(p_181662_3_ + this.yOffset));
                this.byteBuffer.putFloat(i + 8, (float)(p_181662_5_ + this.zOffset));
                break;
            }
            case UINT: 
            case INT: {
                this.byteBuffer.putInt(i, Float.floatToRawIntBits((float)(p_181662_1_ + this.xOffset)));
                this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float)(p_181662_3_ + this.yOffset)));
                this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float)(p_181662_5_ + this.zOffset)));
                break;
            }
            case USHORT: 
            case SHORT: {
                this.byteBuffer.putShort(i, (short)(p_181662_1_ + this.xOffset));
                this.byteBuffer.putShort(i + 2, (short)(p_181662_3_ + this.yOffset));
                this.byteBuffer.putShort(i + 4, (short)(p_181662_5_ + this.zOffset));
                break;
            }
            case UBYTE: 
            case BYTE: {
                this.byteBuffer.put(i, (byte)(p_181662_1_ + this.xOffset));
                this.byteBuffer.put(i + 1, (byte)(p_181662_3_ + this.yOffset));
                this.byteBuffer.put(i + 2, (byte)(p_181662_5_ + this.zOffset));
            }
        }
        this.func_181667_k();
        return this;
    }

    public void putNormal(float x, float y, float z) {
        int i = (byte)(x * 127.0f) & 0xFF;
        int j = (byte)(y * 127.0f) & 0xFF;
        int k = (byte)(z * 127.0f) & 0xFF;
        int l = i | j << 8 | k << 16;
        int i1 = this.vertexFormat.getNextOffset() >> 2;
        int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
        this.rawIntBuffer.put(j1, l);
        this.rawIntBuffer.put(j1 + i1, l);
        this.rawIntBuffer.put(j1 + i1 * 2, l);
        this.rawIntBuffer.put(j1 + i1 * 3, l);
    }

    private void func_181667_k() {
        ++this.field_181678_g;
        this.field_181678_g %= this.vertexFormat.getElementCount();
        this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
        if (this.field_181677_f.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
            this.func_181667_k();
        }
    }

    public WorldRenderer normal(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (this.field_181677_f.getType()) {
            case FLOAT: {
                this.byteBuffer.putFloat(i, p_181663_1_);
                this.byteBuffer.putFloat(i + 4, p_181663_2_);
                this.byteBuffer.putFloat(i + 8, p_181663_3_);
                break;
            }
            case UINT: 
            case INT: {
                this.byteBuffer.putInt(i, (int)p_181663_1_);
                this.byteBuffer.putInt(i + 4, (int)p_181663_2_);
                this.byteBuffer.putInt(i + 8, (int)p_181663_3_);
                break;
            }
            case USHORT: 
            case SHORT: {
                this.byteBuffer.putShort(i, (short)((int)(p_181663_1_ * 32767.0f) & 0xFFFF));
                this.byteBuffer.putShort(i + 2, (short)((int)(p_181663_2_ * 32767.0f) & 0xFFFF));
                this.byteBuffer.putShort(i + 4, (short)((int)(p_181663_3_ * 32767.0f) & 0xFFFF));
                break;
            }
            case UBYTE: 
            case BYTE: {
                this.byteBuffer.put(i, (byte)((int)(p_181663_1_ * 127.0f) & 0xFF));
                this.byteBuffer.put(i + 1, (byte)((int)(p_181663_2_ * 127.0f) & 0xFF));
                this.byteBuffer.put(i + 2, (byte)((int)(p_181663_3_ * 127.0f) & 0xFF));
            }
        }
        this.func_181667_k();
        return this;
    }

    public void setTranslation(double x, double y, double z) {
        this.xOffset = x;
        this.yOffset = y;
        this.zOffset = z;
    }

    public void finishDrawing() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        }
        this.isDrawing = false;
        this.byteBuffer.position(0);
        this.byteBuffer.limit(this.func_181664_j() * 4);
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    public int getDrawMode() {
        return this.drawMode;
    }

    public void putColor4(int argb) {
        int i = 0;
        while (i < 4) {
            this.putColor(argb, i + 1);
            ++i;
        }
    }

    public void putColorRGB_F4(float red, float green, float blue) {
        int i = 0;
        while (i < 4) {
            this.putColorRGB_F(red, green, blue, i + 1);
            ++i;
        }
    }

    public void putSprite(TextureAtlasSprite p_putSprite_1_) {
        if (this.quadSprites != null) {
            int i = this.vertexCount / 4;
            this.quadSprites[i - 1] = p_putSprite_1_;
        }
    }

    public void setSprite(TextureAtlasSprite p_setSprite_1_) {
        if (this.quadSprites != null) {
            this.quadSprite = p_setSprite_1_;
        }
    }

    public boolean isMultiTexture() {
        return this.quadSprites != null;
    }

    public void drawMultiTexture() {
        if (this.quadSprites != null) {
            int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
            if (this.drawnIcons.length <= i) {
                this.drawnIcons = new boolean[i + 1];
            }
            Arrays.fill(this.drawnIcons, false);
            int j = 0;
            int k = -1;
            int l = this.vertexCount / 4;
            int i1 = 0;
            while (i1 < l) {
                int j1;
                TextureAtlasSprite textureatlassprite = this.quadSprites[i1];
                if (textureatlassprite != null && !this.drawnIcons[j1 = textureatlassprite.getIndexInMap()]) {
                    if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
                        if (k < 0) {
                            k = i1;
                        }
                    } else {
                        i1 = this.drawForIcon(textureatlassprite, i1) - 1;
                        ++j;
                        if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                            this.drawnIcons[j1] = true;
                        }
                    }
                }
                ++i1;
            }
            if (k >= 0) {
                this.drawForIcon(TextureUtils.iconGrassSideOverlay, k);
                ++j;
            }
            if (j > 0) {
                // empty if block
            }
        }
    }

    private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
        GL11.glBindTexture((int)3553, (int)p_drawForIcon_1_.glSpriteTextureId);
        int i = -1;
        int j = -1;
        int k = this.vertexCount / 4;
        int l = p_drawForIcon_2_;
        while (l < k) {
            TextureAtlasSprite textureatlassprite = this.quadSprites[l];
            if (textureatlassprite == p_drawForIcon_1_) {
                if (j < 0) {
                    j = l;
                }
            } else if (j >= 0) {
                this.draw(j, l);
                if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
                    return l;
                }
                j = -1;
                if (i < 0) {
                    i = l;
                }
            }
            ++l;
        }
        if (j >= 0) {
            this.draw(j, k);
        }
        if (i < 0) {
            i = k;
        }
        return i;
    }

    private void draw(int p_draw_1_, int p_draw_2_) {
        int i = p_draw_2_ - p_draw_1_;
        if (i > 0) {
            int j = p_draw_1_ * 4;
            int k = i * 4;
            GL11.glDrawArrays((int)this.drawMode, (int)j, (int)k);
        }
    }

    public void setBlockLayer(EnumWorldBlockLayer p_setBlockLayer_1_) {
        this.blockLayer = p_setBlockLayer_1_;
        if (p_setBlockLayer_1_ == null) {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
            this.quadSprite = null;
        }
    }

    private int getBufferQuadSize() {
        int i = this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.func_181719_f() * 4);
        return i;
    }

    public void checkAndGrow() {
        this.func_181670_b(this.vertexFormat.func_181719_f());
    }

    public boolean isColorDisabled() {
        return this.needsUpdate;
    }

    public class State {
        private final int[] stateRawBuffer;
        private final VertexFormat stateVertexFormat;
        private static final String __OBFID = "CL_00002568";
        private TextureAtlasSprite[] stateQuadSprites;

        public State(int[] p_i2_2_, VertexFormat p_i2_3_, TextureAtlasSprite[] p_i2_4_) {
            this.stateRawBuffer = p_i2_2_;
            this.stateVertexFormat = p_i2_3_;
            this.stateQuadSprites = p_i2_4_;
        }

        public State(int[] p_i46453_2_, VertexFormat p_i46453_3_) {
            this.stateRawBuffer = p_i46453_2_;
            this.stateVertexFormat = p_i46453_3_;
        }

        public int[] getRawBuffer() {
            return this.stateRawBuffer;
        }

        public int getVertexCount() {
            return this.stateRawBuffer.length / this.stateVertexFormat.func_181719_f();
        }

        public VertexFormat getVertexFormat() {
            return this.stateVertexFormat;
        }
    }
}

