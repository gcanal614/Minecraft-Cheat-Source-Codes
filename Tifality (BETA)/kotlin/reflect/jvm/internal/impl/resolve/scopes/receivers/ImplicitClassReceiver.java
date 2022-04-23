/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ImplicitReceiver;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ThisClassReceiver;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImplicitClassReceiver
implements ImplicitReceiver,
ThisClassReceiver {
    private final ImplicitClassReceiver original;
    @NotNull
    private final ClassDescriptor declarationDescriptor;
    @NotNull
    private final ClassDescriptor classDescriptor;

    @Override
    @NotNull
    public SimpleType getType() {
        SimpleType simpleType2 = this.classDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "classDescriptor.defaultType");
        return simpleType2;
    }

    public boolean equals(@Nullable Object other) {
        Object object = other;
        if (!(object instanceof ImplicitClassReceiver)) {
            object = null;
        }
        ImplicitClassReceiver implicitClassReceiver = (ImplicitClassReceiver)object;
        return Intrinsics.areEqual(this.classDescriptor, implicitClassReceiver != null ? implicitClassReceiver.classDescriptor : null);
    }

    public int hashCode() {
        return this.classDescriptor.hashCode();
    }

    @NotNull
    public String toString() {
        return "Class{" + this.getType() + '}';
    }

    @Override
    @NotNull
    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public ImplicitClassReceiver(@NotNull ClassDescriptor classDescriptor, @Nullable ImplicitClassReceiver original) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        this.classDescriptor = classDescriptor;
        ImplicitClassReceiver implicitClassReceiver = original;
        if (implicitClassReceiver == null) {
            implicitClassReceiver = this;
        }
        this.original = implicitClassReceiver;
        this.declarationDescriptor = this.classDescriptor;
    }
}

