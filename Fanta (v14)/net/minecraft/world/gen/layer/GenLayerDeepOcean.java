/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDeepOcean
extends GenLayer {
    public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_) {
        super(p_i45472_1_);
        this.parent = p_i45472_3_;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int i = areaX - 1;
        int j = areaY - 1;
        int k = areaWidth + 2;
        int l = areaHeight + 2;
        int[] aint = this.parent.getInts(i, j, k, l);
        int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
        int i1 = 0;
        while (i1 < areaHeight) {
            int j1 = 0;
            while (j1 < areaWidth) {
                int k1 = aint[j1 + 1 + (i1 + 1 - 1) * (areaWidth + 2)];
                int l1 = aint[j1 + 1 + 1 + (i1 + 1) * (areaWidth + 2)];
                int i2 = aint[j1 + 1 - 1 + (i1 + 1) * (areaWidth + 2)];
                int j2 = aint[j1 + 1 + (i1 + 1 + 1) * (areaWidth + 2)];
                int k2 = aint[j1 + 1 + (i1 + 1) * k];
                int l2 = 0;
                if (k1 == 0) {
                    ++l2;
                }
                if (l1 == 0) {
                    ++l2;
                }
                if (i2 == 0) {
                    ++l2;
                }
                if (j2 == 0) {
                    ++l2;
                }
                aint1[j1 + i1 * areaWidth] = k2 == 0 && l2 > 3 ? BiomeGenBase.deepOcean.biomeID : k2;
                ++j1;
            }
            ++i1;
        }
        return aint1;
    }
}

