/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeMappingConfiguration<T> {
    @NotNull
    public KotlinType commonSupertype(@NotNull Collection<KotlinType> var1);

    @Nullable
    public T getPredefinedTypeForClass(@NotNull ClassDescriptor var1);

    @Nullable
    public String getPredefinedInternalNameForClass(@NotNull ClassDescriptor var1);

    @Nullable
    public String getPredefinedFullInternalNameForClass(@NotNull ClassDescriptor var1);

    public void processErrorType(@NotNull KotlinType var1, @NotNull ClassDescriptor var2);

    @Nullable
    public KotlinType preprocessType(@NotNull KotlinType var1);

    public boolean releaseCoroutines();

    public static final class DefaultImpls {
        @Nullable
        public static <T> String getPredefinedFullInternalNameForClass(@NotNull TypeMappingConfiguration<? extends T> $this, @NotNull ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return null;
        }

        @Nullable
        public static <T> KotlinType preprocessType(@NotNull TypeMappingConfiguration<? extends T> $this, @NotNull KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return null;
        }

        public static <T> boolean releaseCoroutines(@NotNull TypeMappingConfiguration<? extends T> $this) {
            return true;
        }
    }
}

