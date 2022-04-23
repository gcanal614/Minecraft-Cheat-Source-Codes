/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelegatedTypeSubstitution
extends TypeSubstitution {
    @NotNull
    private final TypeSubstitution substitution;

    @Override
    @Nullable
    public TypeProjection get(@NotNull KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.substitution.get(key);
    }

    @Override
    @NotNull
    public KotlinType prepareTopLevelType(@NotNull KotlinType topLevelType, @NotNull Variance position) {
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter((Object)position, "position");
        return this.substitution.prepareTopLevelType(topLevelType, position);
    }

    @Override
    public boolean isEmpty() {
        return this.substitution.isEmpty();
    }

    @Override
    public boolean approximateCapturedTypes() {
        return this.substitution.approximateCapturedTypes();
    }

    @Override
    public boolean approximateContravariantCapturedTypes() {
        return this.substitution.approximateContravariantCapturedTypes();
    }

    @Override
    @NotNull
    public Annotations filterAnnotations(@NotNull Annotations annotations2) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        return this.substitution.filterAnnotations(annotations2);
    }

    public DelegatedTypeSubstitution(@NotNull TypeSubstitution substitution) {
        Intrinsics.checkNotNullParameter(substitution, "substitution");
        this.substitution = substitution;
    }
}

