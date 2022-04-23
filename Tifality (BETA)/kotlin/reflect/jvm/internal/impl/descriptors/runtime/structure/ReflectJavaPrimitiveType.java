/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPrimitiveType;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaPrimitiveType
extends ReflectJavaType
implements JavaPrimitiveType {
    @NotNull
    private final Class<?> reflectType;

    @Override
    @Nullable
    public PrimitiveType getType() {
        PrimitiveType primitiveType;
        if (Intrinsics.areEqual(this.getReflectType(), Void.TYPE)) {
            primitiveType = null;
        } else {
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(((Class)this.getReflectType()).getName());
            Intrinsics.checkNotNullExpressionValue((Object)jvmPrimitiveType, "JvmPrimitiveType.get(reflectType.name)");
            primitiveType = jvmPrimitiveType.getPrimitiveType();
        }
        return primitiveType;
    }

    @Override
    @NotNull
    protected Class<?> getReflectType() {
        return this.reflectType;
    }

    public ReflectJavaPrimitiveType(@NotNull Class<?> reflectType) {
        Intrinsics.checkNotNullParameter(reflectType, "reflectType");
        this.reflectType = reflectType;
    }
}

