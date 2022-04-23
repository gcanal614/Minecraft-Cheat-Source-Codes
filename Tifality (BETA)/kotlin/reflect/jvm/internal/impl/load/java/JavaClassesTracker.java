/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.annotations.NotNull;

public interface JavaClassesTracker {
    public void reportClass(@NotNull JavaClassDescriptor var1);

    public static final class Default
    implements JavaClassesTracker {
        public static final Default INSTANCE;

        @Override
        public void reportClass(@NotNull JavaClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        }

        private Default() {
        }

        static {
            Default default_;
            INSTANCE = default_ = new Default();
        }
    }
}

