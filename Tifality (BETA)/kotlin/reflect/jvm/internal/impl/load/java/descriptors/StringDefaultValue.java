/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import org.jetbrains.annotations.NotNull;

public final class StringDefaultValue
extends AnnotationDefaultValue {
    @NotNull
    private final String value;

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public StringDefaultValue(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(null);
        this.value = value;
    }
}

