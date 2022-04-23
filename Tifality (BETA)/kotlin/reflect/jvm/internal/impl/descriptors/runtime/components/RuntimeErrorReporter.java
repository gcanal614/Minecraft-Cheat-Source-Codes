/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import org.jetbrains.annotations.NotNull;

public final class RuntimeErrorReporter
implements ErrorReporter {
    public static final RuntimeErrorReporter INSTANCE;

    @Override
    public void reportIncompleteHierarchy(@NotNull ClassDescriptor descriptor2, @NotNull List<String> unresolvedSuperClasses) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(unresolvedSuperClasses, "unresolvedSuperClasses");
        throw (Throwable)new IllegalStateException("Incomplete hierarchy for class " + descriptor2.getName() + ", unresolved classes " + unresolvedSuperClasses);
    }

    @Override
    public void reportCannotInferVisibility(@NotNull CallableMemberDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        throw (Throwable)new IllegalStateException("Cannot infer visibility for " + descriptor2);
    }

    private RuntimeErrorReporter() {
    }

    static {
        RuntimeErrorReporter runtimeErrorReporter;
        INSTANCE = runtimeErrorReporter = new RuntimeErrorReporter();
    }
}

