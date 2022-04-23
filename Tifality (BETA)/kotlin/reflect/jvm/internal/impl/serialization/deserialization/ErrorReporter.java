/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import org.jetbrains.annotations.NotNull;

public interface ErrorReporter {
    public static final ErrorReporter DO_NOTHING = new ErrorReporter(){

        @Override
        public void reportIncompleteHierarchy(@NotNull ClassDescriptor descriptor2, @NotNull List<String> unresolvedSuperClasses) {
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(0);
            }
            if (unresolvedSuperClasses == null) {
                1.$$$reportNull$$$0(1);
            }
        }

        @Override
        public void reportCannotInferVisibility(@NotNull CallableMemberDescriptor descriptor2) {
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(2);
            }
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "descriptor";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "unresolvedSuperClasses";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/serialization/deserialization/ErrorReporter$1";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "reportIncompleteHierarchy";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[2] = "reportCannotInferVisibility";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };

    public void reportIncompleteHierarchy(@NotNull ClassDescriptor var1, @NotNull List<String> var2);

    public void reportCannotInferVisibility(@NotNull CallableMemberDescriptor var1);
}

