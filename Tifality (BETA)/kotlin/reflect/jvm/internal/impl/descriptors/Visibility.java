/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Visibility {
    @NotNull
    private final String name;
    private final boolean isPublicAPI;

    public abstract boolean isVisible(@Nullable ReceiverValue var1, @NotNull DeclarationDescriptorWithVisibility var2, @NotNull DeclarationDescriptor var3);

    @Nullable
    protected Integer compareTo(@NotNull Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return Visibilities.compareLocal(this, visibility);
    }

    @NotNull
    public String getInternalDisplayName() {
        return this.name;
    }

    @NotNull
    public final String toString() {
        return this.getInternalDisplayName();
    }

    @NotNull
    public Visibility normalize() {
        return this;
    }

    public final boolean isPublicAPI() {
        return this.isPublicAPI;
    }

    protected Visibility(@NotNull String name, boolean isPublicAPI) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.isPublicAPI = isPublicAPI;
    }
}

