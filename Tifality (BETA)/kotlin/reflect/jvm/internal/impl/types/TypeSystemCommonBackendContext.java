/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeSystemCommonBackendContext
extends TypeSystemContext {
    public boolean hasAnnotation(@NotNull KotlinTypeMarker var1, @NotNull FqName var2);

    @Nullable
    public TypeParameterMarker getTypeParameterClassifier(@NotNull TypeConstructorMarker var1);

    public boolean isInlineClass(@NotNull TypeConstructorMarker var1);

    @NotNull
    public KotlinTypeMarker getRepresentativeUpperBound(@NotNull TypeParameterMarker var1);

    @Nullable
    public KotlinTypeMarker getSubstitutedUnderlyingType(@NotNull KotlinTypeMarker var1);

    public boolean isMarkedNullable(@NotNull KotlinTypeMarker var1);

    @NotNull
    public KotlinTypeMarker makeNullable(@NotNull KotlinTypeMarker var1);

    @Nullable
    public PrimitiveType getPrimitiveType(@NotNull TypeConstructorMarker var1);

    @Nullable
    public PrimitiveType getPrimitiveArrayType(@NotNull TypeConstructorMarker var1);

    public boolean isUnderKotlinPackage(@NotNull TypeConstructorMarker var1);

    @Nullable
    public FqNameUnsafe getClassFqNameUnsafe(@NotNull TypeConstructorMarker var1);

    public static final class DefaultImpls {
        public static boolean isMarkedNullable(@NotNull TypeSystemCommonBackendContext $this, @NotNull KotlinTypeMarker $this$isMarkedNullable) {
            Intrinsics.checkNotNullParameter($this$isMarkedNullable, "$this$isMarkedNullable");
            return $this$isMarkedNullable instanceof SimpleTypeMarker && $this.isMarkedNullable((SimpleTypeMarker)$this$isMarkedNullable);
        }

        @NotNull
        public static KotlinTypeMarker makeNullable(@NotNull TypeSystemCommonBackendContext $this, @NotNull KotlinTypeMarker $this$makeNullable) {
            Intrinsics.checkNotNullParameter($this$makeNullable, "$this$makeNullable");
            SimpleTypeMarker simpleTypeMarker = $this.asSimpleType($this$makeNullable);
            return simpleTypeMarker != null && (simpleTypeMarker = $this.withNullability(simpleTypeMarker, true)) != null ? (KotlinTypeMarker)simpleTypeMarker : $this$makeNullable;
        }
    }
}

