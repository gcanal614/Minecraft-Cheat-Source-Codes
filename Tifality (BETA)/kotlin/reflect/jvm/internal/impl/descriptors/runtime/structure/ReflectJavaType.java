/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaArrayType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClassifierType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaPrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaWildcardType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ReflectJavaType
implements JavaType {
    public static final Factory Factory = new Factory(null);

    @NotNull
    protected abstract Type getReflectType();

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectJavaType && Intrinsics.areEqual(this.getReflectType(), ((ReflectJavaType)other).getReflectType());
    }

    public int hashCode() {
        return this.getReflectType().hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.getReflectType();
    }

    public static final class Factory {
        @NotNull
        public final ReflectJavaType create(@NotNull Type type2) {
            Intrinsics.checkNotNullParameter(type2, "type");
            return type2 instanceof Class && ((Class)type2).isPrimitive() ? (ReflectJavaType)new ReflectJavaPrimitiveType((Class)type2) : (type2 instanceof GenericArrayType || type2 instanceof Class && ((Class)type2).isArray() ? (ReflectJavaType)new ReflectJavaArrayType(type2) : (type2 instanceof WildcardType ? (ReflectJavaType)new ReflectJavaWildcardType((WildcardType)type2) : (ReflectJavaType)new ReflectJavaClassifierType(type2)));
        }

        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

