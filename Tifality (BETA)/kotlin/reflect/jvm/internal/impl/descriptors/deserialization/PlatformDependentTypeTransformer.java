/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public interface PlatformDependentTypeTransformer {
    @NotNull
    public SimpleType transformPlatformType(@NotNull ClassId var1, @NotNull SimpleType var2);

    public static final class None
    implements PlatformDependentTypeTransformer {
        public static final None INSTANCE;

        @Override
        @NotNull
        public SimpleType transformPlatformType(@NotNull ClassId classId, @NotNull SimpleType computedType) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(computedType, "computedType");
            return computedType;
        }

        private None() {
        }

        static {
            None none;
            INSTANCE = none = new None();
        }
    }
}

