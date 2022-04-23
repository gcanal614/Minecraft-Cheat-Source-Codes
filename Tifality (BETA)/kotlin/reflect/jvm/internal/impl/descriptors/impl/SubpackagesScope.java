/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SubpackagesScope
extends MemberScopeImpl {
    private final ModuleDescriptor moduleDescriptor;
    private final FqName fqName;

    @Nullable
    protected final PackageViewDescriptor getPackage(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (name.isSpecial()) {
            return null;
        }
        FqName fqName2 = this.fqName.child(name);
        Intrinsics.checkNotNullExpressionValue(fqName2, "fqName.child(name)");
        PackageViewDescriptor packageViewDescriptor = this.moduleDescriptor.getPackage(fqName2);
        if (packageViewDescriptor.isEmpty()) {
            return null;
        }
        return packageViewDescriptor;
    }

    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (!kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getPACKAGES_MASK())) {
            boolean bl = false;
            return kotlin.collections.CollectionsKt.emptyList();
        }
        if (this.fqName.isRoot() && kindFilter.getExcludes().contains(DescriptorKindExclude.TopLevelPackages.INSTANCE)) {
            boolean bl = false;
            return kotlin.collections.CollectionsKt.emptyList();
        }
        Collection<FqName> subFqNames = this.moduleDescriptor.getSubPackagesOf(this.fqName, nameFilter);
        ArrayList result2 = new ArrayList(subFqNames.size());
        for (FqName subFqName : subFqNames) {
            Name shortName;
            Intrinsics.checkNotNullExpressionValue(subFqName.shortName(), "subFqName.shortName()");
            if (!nameFilter.invoke(shortName).booleanValue()) continue;
            CollectionsKt.addIfNotNull(result2, this.getPackage(shortName));
        }
        return result2;
    }

    @Override
    @NotNull
    public Set<Name> getClassifierNames() {
        return SetsKt.emptySet();
    }

    public SubpackagesScope(@NotNull ModuleDescriptor moduleDescriptor, @NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        this.moduleDescriptor = moduleDescriptor;
        this.fqName = fqName2;
    }
}

