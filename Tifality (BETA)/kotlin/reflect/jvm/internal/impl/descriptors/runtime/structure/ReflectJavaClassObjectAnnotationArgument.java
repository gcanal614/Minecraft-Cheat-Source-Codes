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
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassObjectAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaClassObjectAnnotationArgument
extends ReflectJavaAnnotationArgument
implements JavaClassObjectAnnotationArgument {
    private final Class<?> klass;

    @Override
    @NotNull
    public JavaType getReferencedType() {
        return ReflectJavaType.Factory.create(this.klass);
    }

    public ReflectJavaClassObjectAnnotationArgument(@Nullable Name name, @NotNull Class<?> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        super(name);
        this.klass = klass;
    }
}

