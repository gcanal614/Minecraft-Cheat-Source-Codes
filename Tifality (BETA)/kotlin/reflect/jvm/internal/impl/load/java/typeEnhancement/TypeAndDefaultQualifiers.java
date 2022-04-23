/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TypeAndDefaultQualifiers {
    @NotNull
    private final KotlinType type;
    @Nullable
    private final JavaTypeQualifiers defaultQualifiers;

    @NotNull
    public final KotlinType getType() {
        return this.type;
    }

    public TypeAndDefaultQualifiers(@NotNull KotlinType type2, @Nullable JavaTypeQualifiers defaultQualifiers) {
        Intrinsics.checkNotNullParameter(type2, "type");
        this.type = type2;
        this.defaultQualifiers = defaultQualifiers;
    }

    @NotNull
    public final KotlinType component1() {
        return this.type;
    }

    @Nullable
    public final JavaTypeQualifiers component2() {
        return this.defaultQualifiers;
    }

    @NotNull
    public String toString() {
        return "TypeAndDefaultQualifiers(type=" + this.type + ", defaultQualifiers=" + this.defaultQualifiers + ")";
    }

    public int hashCode() {
        KotlinType kotlinType = this.type;
        JavaTypeQualifiers javaTypeQualifiers = this.defaultQualifiers;
        return (kotlinType != null ? ((Object)kotlinType).hashCode() : 0) * 31 + (javaTypeQualifiers != null ? javaTypeQualifiers.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof TypeAndDefaultQualifiers)) break block3;
                TypeAndDefaultQualifiers typeAndDefaultQualifiers = (TypeAndDefaultQualifiers)object;
                if (!Intrinsics.areEqual(this.type, typeAndDefaultQualifiers.type) || !Intrinsics.areEqual(this.defaultQualifiers, typeAndDefaultQualifiers.defaultQualifiers)) break block3;
            }
            return true;
        }
        return false;
    }
}

