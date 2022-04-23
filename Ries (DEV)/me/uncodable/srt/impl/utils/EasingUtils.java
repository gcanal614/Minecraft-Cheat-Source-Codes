/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.utils;

import net.minecraft.util.MathHelper;

public class EasingUtils {
    public static float easeOutCircle(float n) {
        return MathHelper.sqrt_float((float)(1.0 - Math.pow((double)n - 1.0, 2.0)));
    }

    public static float easeInOutSine(float n) {
        return (float)(-(Math.cos(Math.PI * (double)n) - 1.0) / 2.0);
    }

    public static float easeInSecantTest(float n) {
        return (float)Math.abs(1.0 - 1.0 / Math.cos(n));
    }

    public static float easeOutBack(float n) {
        float c1 = 1.70158f;
        float c3 = c1 + 1.0f;
        return (float)(1.0 + (double)c3 * Math.pow(n - 1.0f, 3.0) + (double)c1 * Math.pow(n - 1.0f, 2.0));
    }

    public static float easeOutBounce(float n) {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        if (n < 1.0f / d1) {
            return n1 * n * n;
        }
        if (n < 2.0f / d1) {
            return n1 * (n -= 1.5f / d1) * n + 0.75f;
        }
        if (n < 2.5f / d1) {
            return n1 * (n -= 2.25f / d1) * n + 0.9375f;
        }
        return n1 * (n -= 2.625f / d1) * n + 0.984375f;
    }
}

