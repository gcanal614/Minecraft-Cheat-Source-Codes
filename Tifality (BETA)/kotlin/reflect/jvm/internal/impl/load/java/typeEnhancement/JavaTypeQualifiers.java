/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaTypeQualifiers {
    @Nullable
    private final NullabilityQualifier nullability;
    @Nullable
    private final MutabilityQualifier mutability;
    private final boolean isNotNullTypeParameter;
    private final boolean isNullabilityQualifierForWarning;
    @NotNull
    private static final JavaTypeQualifiers NONE;
    public static final Companion Companion;

    @Nullable
    public final NullabilityQualifier getNullability() {
        return this.nullability;
    }

    @Nullable
    public final MutabilityQualifier getMutability() {
        return this.mutability;
    }

    public final boolean isNotNullTypeParameter() {
        return this.isNotNullTypeParameter;
    }

    public final boolean isNullabilityQualifierForWarning() {
        return this.isNullabilityQualifierForWarning;
    }

    public JavaTypeQualifiers(@Nullable NullabilityQualifier nullability, @Nullable MutabilityQualifier mutability, boolean isNotNullTypeParameter, boolean isNullabilityQualifierForWarning) {
        this.nullability = nullability;
        this.mutability = mutability;
        this.isNotNullTypeParameter = isNotNullTypeParameter;
        this.isNullabilityQualifierForWarning = isNullabilityQualifierForWarning;
    }

    public /* synthetic */ JavaTypeQualifiers(NullabilityQualifier nullabilityQualifier, MutabilityQualifier mutabilityQualifier, boolean bl, boolean bl2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            bl2 = false;
        }
        this(nullabilityQualifier, mutabilityQualifier, bl, bl2);
    }

    static {
        Companion = new Companion(null);
        NONE = new JavaTypeQualifiers(null, null, false, false, 8, null);
    }

    public static final class Companion {
        @NotNull
        public final JavaTypeQualifiers getNONE() {
            return NONE;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

