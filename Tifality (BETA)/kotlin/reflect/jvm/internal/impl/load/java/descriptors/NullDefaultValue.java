/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;

public final class NullDefaultValue
extends AnnotationDefaultValue {
    public static final NullDefaultValue INSTANCE;

    private NullDefaultValue() {
        super(null);
    }

    static {
        NullDefaultValue nullDefaultValue;
        INSTANCE = nullDefaultValue = new NullDefaultValue();
    }
}

