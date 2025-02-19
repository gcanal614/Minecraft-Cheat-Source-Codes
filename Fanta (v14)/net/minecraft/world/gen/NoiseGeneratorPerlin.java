/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

public class NoiseGeneratorPerlin
extends NoiseGenerator {
    private NoiseGeneratorSimplex[] field_151603_a;
    private int field_151602_b;

    public NoiseGeneratorPerlin(Random p_i45470_1_, int p_i45470_2_) {
        this.field_151602_b = p_i45470_2_;
        this.field_151603_a = new NoiseGeneratorSimplex[p_i45470_2_];
        int i = 0;
        while (i < p_i45470_2_) {
            this.field_151603_a[i] = new NoiseGeneratorSimplex(p_i45470_1_);
            ++i;
        }
    }

    public double func_151601_a(double p_151601_1_, double p_151601_3_) {
        double d0 = 0.0;
        double d1 = 1.0;
        int i = 0;
        while (i < this.field_151602_b) {
            d0 += this.field_151603_a[i].func_151605_a(p_151601_1_ * d1, p_151601_3_ * d1) / d1;
            d1 /= 2.0;
            ++i;
        }
        return d0;
    }

    public double[] func_151599_a(double[] p_151599_1_, double p_151599_2_, double p_151599_4_, int p_151599_6_, int p_151599_7_, double p_151599_8_, double p_151599_10_, double p_151599_12_) {
        return this.func_151600_a(p_151599_1_, p_151599_2_, p_151599_4_, p_151599_6_, p_151599_7_, p_151599_8_, p_151599_10_, p_151599_12_, 0.5);
    }

    public double[] func_151600_a(double[] p_151600_1_, double p_151600_2_, double p_151600_4_, int p_151600_6_, int p_151600_7_, double p_151600_8_, double p_151600_10_, double p_151600_12_, double p_151600_14_) {
        if (p_151600_1_ != null && p_151600_1_.length >= p_151600_6_ * p_151600_7_) {
            int i = 0;
            while (i < p_151600_1_.length) {
                p_151600_1_[i] = 0.0;
                ++i;
            }
        } else {
            p_151600_1_ = new double[p_151600_6_ * p_151600_7_];
        }
        double d1 = 1.0;
        double d0 = 1.0;
        int j = 0;
        while (j < this.field_151602_b) {
            this.field_151603_a[j].func_151606_a(p_151600_1_, p_151600_2_, p_151600_4_, p_151600_6_, p_151600_7_, p_151600_8_ * d0 * d1, p_151600_10_ * d0 * d1, 0.55 / d1);
            d0 *= p_151600_12_;
            d1 *= p_151600_14_;
            ++j;
        }
        return p_151600_1_;
    }
}

