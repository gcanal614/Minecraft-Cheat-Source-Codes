/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.Set;
import kotlin.collections.SetsKt;

public final class ReflectKotlinClassKt {
    private static final Set<Class<?>> TYPES_ELIGIBLE_FOR_SIMPLE_VISIT = SetsKt.setOf(Integer.class, Character.class, Byte.class, Long.class, Short.class, Boolean.class, Double.class, Float.class, int[].class, char[].class, byte[].class, long[].class, short[].class, boolean[].class, double[].class, float[].class, Class.class, String.class);

    public static final /* synthetic */ Set access$getTYPES_ELIGIBLE_FOR_SIMPLE_VISIT$p() {
        return TYPES_ELIGIBLE_FOR_SIMPLE_VISIT;
    }
}

