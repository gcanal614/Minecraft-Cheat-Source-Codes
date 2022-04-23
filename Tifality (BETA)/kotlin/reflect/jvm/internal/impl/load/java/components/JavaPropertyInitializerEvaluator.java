/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaPropertyInitializerEvaluator {
    @Nullable
    public ConstantValue<?> getInitializerConstant(@NotNull JavaField var1, @NotNull PropertyDescriptor var2);

    public static final class DoNothing
    implements JavaPropertyInitializerEvaluator {
        public static final DoNothing INSTANCE;

        @Override
        @Nullable
        public ConstantValue<?> getInitializerConstant(@NotNull JavaField field, @NotNull PropertyDescriptor descriptor2) {
            Intrinsics.checkNotNullParameter(field, "field");
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            return null;
        }

        private DoNothing() {
        }

        static {
            DoNothing doNothing;
            INSTANCE = doNothing = new DoNothing();
        }
    }
}

