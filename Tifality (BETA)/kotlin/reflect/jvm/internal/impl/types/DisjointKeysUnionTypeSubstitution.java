/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DisjointKeysUnionTypeSubstitution
extends TypeSubstitution {
    private final TypeSubstitution first;
    private final TypeSubstitution second;
    public static final Companion Companion = new Companion(null);

    @Override
    @Nullable
    public TypeProjection get(@NotNull KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        TypeProjection typeProjection = this.first.get(key);
        if (typeProjection == null) {
            typeProjection = this.second.get(key);
        }
        return typeProjection;
    }

    @Override
    @NotNull
    public KotlinType prepareTopLevelType(@NotNull KotlinType topLevelType, @NotNull Variance position) {
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter((Object)position, "position");
        return this.second.prepareTopLevelType(this.first.prepareTopLevelType(topLevelType, position), position);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean approximateCapturedTypes() {
        return this.first.approximateCapturedTypes() || this.second.approximateCapturedTypes();
    }

    @Override
    public boolean approximateContravariantCapturedTypes() {
        return this.first.approximateContravariantCapturedTypes() || this.second.approximateContravariantCapturedTypes();
    }

    @Override
    @NotNull
    public Annotations filterAnnotations(@NotNull Annotations annotations2) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        return this.second.filterAnnotations(this.first.filterAnnotations(annotations2));
    }

    private DisjointKeysUnionTypeSubstitution(TypeSubstitution first, TypeSubstitution second) {
        this.first = first;
        this.second = second;
    }

    public /* synthetic */ DisjointKeysUnionTypeSubstitution(TypeSubstitution first, TypeSubstitution second, DefaultConstructorMarker $constructor_marker) {
        this(first, second);
    }

    @JvmStatic
    @NotNull
    public static final TypeSubstitution create(@NotNull TypeSubstitution first, @NotNull TypeSubstitution second) {
        return Companion.create(first, second);
    }

    public static final class Companion {
        @JvmStatic
        @NotNull
        public final TypeSubstitution create(@NotNull TypeSubstitution first, @NotNull TypeSubstitution second) {
            Intrinsics.checkNotNullParameter(first, "first");
            Intrinsics.checkNotNullParameter(second, "second");
            if (first.isEmpty()) {
                return second;
            }
            if (second.isEmpty()) {
                return first;
            }
            return new DisjointKeysUnionTypeSubstitution(first, second, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

