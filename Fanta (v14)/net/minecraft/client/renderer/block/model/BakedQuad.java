/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.Reflector
 *  optifine.ReflectorMethod
 */
package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.IVertexProducer;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorMethod;

public class BakedQuad
implements IVertexProducer {
    protected int[] vertexData;
    protected final int tintIndex;
    protected final EnumFacing face;
    private static final String __OBFID = "CL_00002512";
    private TextureAtlasSprite sprite = null;
    private int[] vertexDataSingle = null;

    public BakedQuad(int[] p_i9_1_, int p_i9_2_, EnumFacing p_i9_3_, TextureAtlasSprite p_i9_4_) {
        this.vertexData = p_i9_1_;
        this.tintIndex = p_i9_2_;
        this.face = p_i9_3_;
        this.sprite = p_i9_4_;
        this.fixVertexData();
    }

    public TextureAtlasSprite getSprite() {
        if (this.sprite == null) {
            this.sprite = BakedQuad.getSpriteByUv(this.getVertexData());
        }
        return this.sprite;
    }

    public String toString() {
        return "vertex: " + this.vertexData.length / 7 + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
    }

    public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn) {
        this.vertexData = vertexDataIn;
        this.tintIndex = tintIndexIn;
        this.face = faceIn;
        this.fixVertexData();
    }

    public int[] getVertexData() {
        this.fixVertexData();
        return this.vertexData;
    }

    public boolean hasTintIndex() {
        return this.tintIndex != -1;
    }

    public int getTintIndex() {
        return this.tintIndex;
    }

    public EnumFacing getFace() {
        return this.face;
    }

    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = BakedQuad.makeVertexDataSingle(this.getVertexData(), this.getSprite());
        }
        return this.vertexDataSingle;
    }

    private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_) {
        int[] aint = (int[])p_makeVertexDataSingle_0_.clone();
        int i = p_makeVertexDataSingle_1_.sheetWidth / p_makeVertexDataSingle_1_.getIconWidth();
        int j = p_makeVertexDataSingle_1_.sheetHeight / p_makeVertexDataSingle_1_.getIconHeight();
        int k = aint.length / 4;
        int l = 0;
        while (l < 4) {
            int i1 = l * k;
            float f = Float.intBitsToFloat(aint[i1 + 4]);
            float f1 = Float.intBitsToFloat(aint[i1 + 4 + 1]);
            float f2 = p_makeVertexDataSingle_1_.toSingleU(f);
            float f3 = p_makeVertexDataSingle_1_.toSingleV(f1);
            aint[i1 + 4] = Float.floatToRawIntBits(f2);
            aint[i1 + 4 + 1] = Float.floatToRawIntBits(f3);
            ++l;
        }
        return aint;
    }

    @Override
    public void pipe(IVertexConsumer p_pipe_1_) {
        Reflector.callVoid((ReflectorMethod)Reflector.LightUtil_putBakedQuad, (Object[])new Object[]{p_pipe_1_, this});
    }

    private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_) {
        float f = 1.0f;
        float f1 = 1.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i = p_getSpriteByUv_0_.length / 4;
        int j = 0;
        while (j < 4) {
            int k = j * i;
            float f4 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4]);
            float f5 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4 + 1]);
            f = Math.min(f, f4);
            f1 = Math.min(f1, f5);
            f2 = Math.max(f2, f4);
            f3 = Math.max(f3, f5);
            ++j;
        }
        float f6 = (f + f2) / 2.0f;
        float f7 = (f1 + f3) / 2.0f;
        TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
        return textureatlassprite;
    }

    private void fixVertexData() {
        if (Config.isShaders()) {
            if (this.vertexData.length == 28) {
                this.vertexData = BakedQuad.expandVertexData(this.vertexData);
            }
        } else if (this.vertexData.length == 56) {
            this.vertexData = BakedQuad.compactVertexData(this.vertexData);
        }
    }

    private static int[] expandVertexData(int[] p_expandVertexData_0_) {
        int i = p_expandVertexData_0_.length / 4;
        int j = i * 2;
        int[] aint = new int[j * 4];
        int k = 0;
        while (k < 4) {
            System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
            ++k;
        }
        return aint;
    }

    private static int[] compactVertexData(int[] p_compactVertexData_0_) {
        int i = p_compactVertexData_0_.length / 4;
        int j = i / 2;
        int[] aint = new int[j * 4];
        int k = 0;
        while (k < 4) {
            System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
            ++k;
        }
        return aint;
    }
}

