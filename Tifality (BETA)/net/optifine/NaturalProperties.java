/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class NaturalProperties {
    public int rotation = 1;
    public boolean flip = false;
    private Map[] quadMaps = new Map[8];

    public NaturalProperties(String type2) {
        if (type2.equals("4")) {
            this.rotation = 4;
        } else if (type2.equals("2")) {
            this.rotation = 2;
        } else if (type2.equals("F")) {
            this.flip = true;
        } else if (type2.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
        } else if (type2.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
        } else {
            Config.warn("NaturalTextures: Unknown type: " + type2);
        }
    }

    public boolean isValid() {
        return this.rotation != 2 && this.rotation != 4 ? this.flip : true;
    }

    public synchronized BakedQuad getQuad(BakedQuad quadIn, int rotate, boolean flipU) {
        int i = rotate;
        if (flipU) {
            i = rotate | 4;
        }
        if (i > 0 && i < this.quadMaps.length) {
            BakedQuad bakedquad;
            IdentityHashMap<BakedQuad, BakedQuad> map2 = this.quadMaps[i];
            if (map2 == null) {
                this.quadMaps[i] = map2 = new IdentityHashMap<BakedQuad, BakedQuad>(1);
            }
            if ((bakedquad = (BakedQuad)map2.get(quadIn)) == null) {
                bakedquad = this.makeQuad(quadIn, rotate, flipU);
                map2.put(quadIn, bakedquad);
            }
            return bakedquad;
        }
        return quadIn;
    }

    private BakedQuad makeQuad(BakedQuad quad, int rotate, boolean flipU) {
        int[] aint = quad.getVertexData();
        int i = quad.getTintIndex();
        EnumFacing enumfacing = quad.getFace();
        TextureAtlasSprite textureatlassprite = quad.getSprite();
        if (!this.isFullSprite(quad)) {
            rotate = 0;
        }
        aint = this.transformVertexData(aint, rotate, flipU);
        BakedQuad bakedquad = new BakedQuad(aint, i, enumfacing, textureatlassprite);
        return bakedquad;
    }

    private int[] transformVertexData(int[] vertexData, int rotate, boolean flipU) {
        int[] aint = (int[])vertexData.clone();
        int i = 4 - rotate;
        if (flipU) {
            i += 3;
        }
        i %= 4;
        int j = aint.length / 4;
        for (int k = 0; k < 4; ++k) {
            int l = k * j;
            int i1 = i * j;
            aint[i1 + 4] = vertexData[l + 4];
            aint[i1 + 4 + 1] = vertexData[l + 4 + 1];
            if (flipU) {
                if (--i >= 0) continue;
                i = 3;
                continue;
            }
            if (++i <= 3) continue;
            i = 0;
        }
        return aint;
    }

    private boolean isFullSprite(BakedQuad quad) {
        TextureAtlasSprite textureatlassprite = quad.getSprite();
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMaxU();
        float f2 = f1 - f;
        float f3 = f2 / 256.0f;
        float f4 = textureatlassprite.getMinV();
        float f5 = textureatlassprite.getMaxV();
        float f6 = f5 - f4;
        float f7 = f6 / 256.0f;
        int[] aint = quad.getVertexData();
        int i = aint.length / 4;
        for (int j = 0; j < 4; ++j) {
            int k = j * i;
            float f8 = Float.intBitsToFloat(aint[k + 4]);
            float f9 = Float.intBitsToFloat(aint[k + 4 + 1]);
            if (!this.equalsDelta(f8, f, f3) && !this.equalsDelta(f8, f1, f3)) {
                return false;
            }
            if (this.equalsDelta(f9, f4, f7) || this.equalsDelta(f9, f5, f7)) continue;
            return false;
        }
        return true;
    }

    private boolean equalsDelta(float x1, float x2, float deltaMax) {
        float f = MathHelper.abs(x1 - x2);
        return f < deltaMax;
    }
}

