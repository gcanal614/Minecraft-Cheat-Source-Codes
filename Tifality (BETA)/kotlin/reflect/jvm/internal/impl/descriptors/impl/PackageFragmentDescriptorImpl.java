/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;

public abstract class PackageFragmentDescriptorImpl
extends DeclarationDescriptorNonRootImpl
implements PackageFragmentDescriptor {
    @NotNull
    private final FqName fqName;

    @Override
    public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        return visitor2.visitPackageFragmentDescriptor(this, data2);
    }

    @Override
    @NotNull
    public ModuleDescriptor getContainingDeclaration() {
        DeclarationDescriptor declarationDescriptor = super.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ModuleDescriptor");
        }
        return (ModuleDescriptor)declarationDescriptor;
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
        return sourceElement;
    }

    @Override
    @NotNull
    public String toString() {
        return "package " + this.fqName;
    }

    @Override
    @NotNull
    public final FqName getFqName() {
        return this.fqName;
    }

    public PackageFragmentDescriptorImpl(@NotNull ModuleDescriptor module, @NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        super(module, Annotations.Companion.getEMPTY(), fqName2.shortNameOrSpecial(), SourceElement.NO_SOURCE);
        this.fqName = fqName2;
    }
}

