/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaWildcardType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaWildcardType
extends ReflectJavaType
implements JavaWildcardType {
    @NotNull
    private final WildcardType reflectType;

    @Override
    @Nullable
    public ReflectJavaType getBound() {
        ReflectJavaType reflectJavaType;
        Type[] upperBounds2 = this.getReflectType().getUpperBounds();
        Type[] lowerBounds = this.getReflectType().getLowerBounds();
        if (upperBounds2.length > 1 || lowerBounds.length > 1) {
            throw (Throwable)new UnsupportedOperationException("Wildcard types with many bounds are not yet supported: " + this.getReflectType());
        }
        if (lowerBounds.length == 1) {
            Intrinsics.checkNotNullExpressionValue(lowerBounds, "lowerBounds");
            Type type2 = ArraysKt.single(lowerBounds);
            Intrinsics.checkNotNullExpressionValue(type2, "lowerBounds.single()");
            reflectJavaType = ReflectJavaType.Factory.create(type2);
        } else if (upperBounds2.length == 1) {
            Intrinsics.checkNotNullExpressionValue(upperBounds2, "upperBounds");
            Type type3 = ArraysKt.single(upperBounds2);
            boolean bl = false;
            boolean bl2 = false;
            Type ub = type3;
            boolean bl3 = false;
            if (Intrinsics.areEqual(ub, Object.class) ^ true) {
                Type type4 = ub;
                Intrinsics.checkNotNullExpressionValue(type4, "ub");
                reflectJavaType = ReflectJavaType.Factory.create(type4);
            } else {
                reflectJavaType = null;
            }
        } else {
            reflectJavaType = null;
        }
        return reflectJavaType;
    }

    @Override
    public boolean isExtends() {
        Type[] typeArray = this.getReflectType().getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(typeArray, "reflectType.upperBounds");
        return Intrinsics.areEqual(ArraysKt.firstOrNull(typeArray), Object.class) ^ true;
    }

    @Override
    @NotNull
    protected WildcardType getReflectType() {
        return this.reflectType;
    }

    public ReflectJavaWildcardType(@NotNull WildcardType reflectType) {
        Intrinsics.checkNotNullParameter(reflectType, "reflectType");
        this.reflectType = reflectType;
    }
}

