/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeFlexibility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaTypeAttributes {
    @NotNull
    private final TypeUsage howThisTypeIsUsed;
    @NotNull
    private final JavaTypeFlexibility flexibility;
    private final boolean isForAnnotationParameter;
    @Nullable
    private final TypeParameterDescriptor upperBoundOfTypeParameter;

    @NotNull
    public final JavaTypeAttributes withFlexibility(@NotNull JavaTypeFlexibility flexibility) {
        Intrinsics.checkNotNullParameter((Object)flexibility, "flexibility");
        return JavaTypeAttributes.copy$default(this, null, flexibility, false, null, 13, null);
    }

    @NotNull
    public final TypeUsage getHowThisTypeIsUsed() {
        return this.howThisTypeIsUsed;
    }

    @NotNull
    public final JavaTypeFlexibility getFlexibility() {
        return this.flexibility;
    }

    public final boolean isForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    @Nullable
    public final TypeParameterDescriptor getUpperBoundOfTypeParameter() {
        return this.upperBoundOfTypeParameter;
    }

    public JavaTypeAttributes(@NotNull TypeUsage howThisTypeIsUsed, @NotNull JavaTypeFlexibility flexibility, boolean isForAnnotationParameter, @Nullable TypeParameterDescriptor upperBoundOfTypeParameter) {
        Intrinsics.checkNotNullParameter((Object)howThisTypeIsUsed, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter((Object)flexibility, "flexibility");
        this.howThisTypeIsUsed = howThisTypeIsUsed;
        this.flexibility = flexibility;
        this.isForAnnotationParameter = isForAnnotationParameter;
        this.upperBoundOfTypeParameter = upperBoundOfTypeParameter;
    }

    public /* synthetic */ JavaTypeAttributes(TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean bl, TypeParameterDescriptor typeParameterDescriptor, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            javaTypeFlexibility = JavaTypeFlexibility.INFLEXIBLE;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 8) != 0) {
            typeParameterDescriptor = null;
        }
        this(typeUsage, javaTypeFlexibility, bl, typeParameterDescriptor);
    }

    @NotNull
    public final JavaTypeAttributes copy(@NotNull TypeUsage howThisTypeIsUsed, @NotNull JavaTypeFlexibility flexibility, boolean isForAnnotationParameter, @Nullable TypeParameterDescriptor upperBoundOfTypeParameter) {
        Intrinsics.checkNotNullParameter((Object)howThisTypeIsUsed, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter((Object)flexibility, "flexibility");
        return new JavaTypeAttributes(howThisTypeIsUsed, flexibility, isForAnnotationParameter, upperBoundOfTypeParameter);
    }

    public static /* synthetic */ JavaTypeAttributes copy$default(JavaTypeAttributes javaTypeAttributes, TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean bl, TypeParameterDescriptor typeParameterDescriptor, int n, Object object) {
        if ((n & 1) != 0) {
            typeUsage = javaTypeAttributes.howThisTypeIsUsed;
        }
        if ((n & 2) != 0) {
            javaTypeFlexibility = javaTypeAttributes.flexibility;
        }
        if ((n & 4) != 0) {
            bl = javaTypeAttributes.isForAnnotationParameter;
        }
        if ((n & 8) != 0) {
            typeParameterDescriptor = javaTypeAttributes.upperBoundOfTypeParameter;
        }
        return javaTypeAttributes.copy(typeUsage, javaTypeFlexibility, bl, typeParameterDescriptor);
    }

    @NotNull
    public String toString() {
        return "JavaTypeAttributes(howThisTypeIsUsed=" + (Object)((Object)this.howThisTypeIsUsed) + ", flexibility=" + (Object)((Object)this.flexibility) + ", isForAnnotationParameter=" + this.isForAnnotationParameter + ", upperBoundOfTypeParameter=" + this.upperBoundOfTypeParameter + ")";
    }

    public int hashCode() {
        TypeUsage typeUsage = this.howThisTypeIsUsed;
        JavaTypeFlexibility javaTypeFlexibility = this.flexibility;
        int n = ((typeUsage != null ? ((Object)((Object)typeUsage)).hashCode() : 0) * 31 + (javaTypeFlexibility != null ? ((Object)((Object)javaTypeFlexibility)).hashCode() : 0)) * 31;
        int n2 = this.isForAnnotationParameter ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        TypeParameterDescriptor typeParameterDescriptor = this.upperBoundOfTypeParameter;
        return (n + n2) * 31 + (typeParameterDescriptor != null ? typeParameterDescriptor.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof JavaTypeAttributes)) break block3;
                JavaTypeAttributes javaTypeAttributes = (JavaTypeAttributes)object;
                if (!Intrinsics.areEqual((Object)this.howThisTypeIsUsed, (Object)javaTypeAttributes.howThisTypeIsUsed) || !Intrinsics.areEqual((Object)this.flexibility, (Object)javaTypeAttributes.flexibility) || this.isForAnnotationParameter != javaTypeAttributes.isForAnnotationParameter || !Intrinsics.areEqual(this.upperBoundOfTypeParameter, javaTypeAttributes.upperBoundOfTypeParameter)) break block3;
            }
            return true;
        }
        return false;
    }
}

