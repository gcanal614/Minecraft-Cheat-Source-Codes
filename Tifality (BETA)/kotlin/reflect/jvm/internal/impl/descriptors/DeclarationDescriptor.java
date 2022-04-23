/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.Named;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DeclarationDescriptor
extends Named,
Annotated {
    @NotNull
    public DeclarationDescriptor getOriginal();

    @Nullable
    public DeclarationDescriptor getContainingDeclaration();

    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> var1, D var2);
}

