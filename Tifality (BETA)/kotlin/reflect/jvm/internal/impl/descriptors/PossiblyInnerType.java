/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PossiblyInnerType {
    @NotNull
    private final ClassifierDescriptorWithTypeParameters classifierDescriptor;
    @NotNull
    private final List<TypeProjection> arguments;
    @Nullable
    private final PossiblyInnerType outerType;

    @NotNull
    public final ClassifierDescriptorWithTypeParameters getClassifierDescriptor() {
        return this.classifierDescriptor;
    }

    @NotNull
    public final List<TypeProjection> getArguments() {
        return this.arguments;
    }

    @Nullable
    public final PossiblyInnerType getOuterType() {
        return this.outerType;
    }

    public PossiblyInnerType(@NotNull ClassifierDescriptorWithTypeParameters classifierDescriptor, @NotNull List<? extends TypeProjection> arguments2, @Nullable PossiblyInnerType outerType) {
        Intrinsics.checkNotNullParameter(classifierDescriptor, "classifierDescriptor");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        this.classifierDescriptor = classifierDescriptor;
        this.arguments = arguments2;
        this.outerType = outerType;
    }
}

