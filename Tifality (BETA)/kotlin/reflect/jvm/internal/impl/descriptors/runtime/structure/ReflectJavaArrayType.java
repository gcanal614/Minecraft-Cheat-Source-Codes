/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import org.jetbrains.annotations.NotNull;

public final class ReflectJavaArrayType
extends ReflectJavaType
implements JavaArrayType {
    @NotNull
    private final ReflectJavaType componentType;
    @NotNull
    private final Type reflectType;

    @Override
    @NotNull
    public ReflectJavaType getComponentType() {
        return this.componentType;
    }

    @Override
    @NotNull
    protected Type getReflectType() {
        return this.reflectType;
    }

    /*
     * WARNING - void declaration
     */
    public ReflectJavaArrayType(@NotNull Type reflectType) {
        ReflectJavaType reflectJavaType;
        ReflectJavaType reflectJavaType2;
        void $this$with;
        Intrinsics.checkNotNullParameter(reflectType, "reflectType");
        this.reflectType = reflectType;
        Type type2 = this.getReflectType();
        boolean bl = false;
        boolean bl2 = false;
        Type type3 = type2;
        ReflectJavaArrayType reflectJavaArrayType = this;
        boolean bl3 = false;
        if ($this$with instanceof GenericArrayType) {
            Type type4 = ((GenericArrayType)$this$with).getGenericComponentType();
            Intrinsics.checkNotNullExpressionValue(type4, "genericComponentType");
            reflectJavaType2 = ReflectJavaType.Factory.create(type4);
        } else if ($this$with instanceof Class && ((Class)$this$with).isArray()) {
            Class<?> clazz = ((Class)$this$with).getComponentType();
            Intrinsics.checkNotNullExpressionValue(clazz, "getComponentType()");
            reflectJavaType2 = ReflectJavaType.Factory.create(clazz);
        } else {
            throw (Throwable)new IllegalArgumentException("Not an array type (" + this.getReflectType().getClass() + "): " + this.getReflectType());
        }
        reflectJavaArrayType.componentType = reflectJavaType = reflectJavaType2;
    }
}

