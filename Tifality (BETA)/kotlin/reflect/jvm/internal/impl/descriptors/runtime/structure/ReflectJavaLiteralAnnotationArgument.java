/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaLiteralAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaLiteralAnnotationArgument
extends ReflectJavaAnnotationArgument
implements JavaLiteralAnnotationArgument {
    @NotNull
    private final Object value;

    @Override
    @NotNull
    public Object getValue() {
        return this.value;
    }

    public ReflectJavaLiteralAnnotationArgument(@Nullable Name name, @NotNull Object value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(name);
        this.value = value;
    }
}

