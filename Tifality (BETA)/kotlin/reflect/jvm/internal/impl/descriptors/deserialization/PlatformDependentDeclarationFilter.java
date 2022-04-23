/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.annotations.NotNull;

public interface PlatformDependentDeclarationFilter {
    public boolean isFunctionAvailable(@NotNull ClassDescriptor var1, @NotNull SimpleFunctionDescriptor var2);

    public static final class All
    implements PlatformDependentDeclarationFilter {
        public static final All INSTANCE;

        @Override
        public boolean isFunctionAvailable(@NotNull ClassDescriptor classDescriptor, @NotNull SimpleFunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return true;
        }

        private All() {
        }

        static {
            All all;
            INSTANCE = all = new All();
        }
    }

    public static final class NoPlatformDependent
    implements PlatformDependentDeclarationFilter {
        public static final NoPlatformDependent INSTANCE;

        @Override
        public boolean isFunctionAvailable(@NotNull ClassDescriptor classDescriptor, @NotNull SimpleFunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return !functionDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME());
        }

        private NoPlatformDependent() {
        }

        static {
            NoPlatformDependent noPlatformDependent;
            INSTANCE = noPlatformDependent = new NoPlatformDependent();
        }
    }
}

