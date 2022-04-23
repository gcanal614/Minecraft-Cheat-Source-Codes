/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;

public final class EmptyPackageFragmentDescriptor
extends PackageFragmentDescriptorImpl {
    @Override
    @NotNull
    public MemberScope.Empty getMemberScope() {
        return MemberScope.Empty.INSTANCE;
    }

    public EmptyPackageFragmentDescriptor(@NotNull ModuleDescriptor module, @NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        super(module, fqName2);
    }
}

