/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import org.jetbrains.annotations.NotNull;

public interface TypeProjection
extends TypeArgumentMarker {
    @NotNull
    public Variance getProjectionKind();

    @NotNull
    public KotlinType getType();

    public boolean isStarProjection();

    @NotNull
    public TypeProjection refine(@NotNull KotlinTypeRefiner var1);
}

