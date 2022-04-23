/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public final class CompositePackageFragmentProvider
implements PackageFragmentProvider {
    private final List<PackageFragmentProvider> providers;

    @Override
    @NotNull
    public List<PackageFragmentDescriptor> getPackageFragments(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        ArrayList result2 = new ArrayList();
        for (PackageFragmentProvider provider : this.providers) {
            result2.addAll(provider.getPackageFragments(fqName2));
        }
        return CollectionsKt.toList(result2);
    }

    @Override
    @NotNull
    public Collection<FqName> getSubPackagesOf(@NotNull FqName fqName2, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        HashSet<FqName> result2 = new HashSet<FqName>();
        for (PackageFragmentProvider provider : this.providers) {
            result2.addAll(provider.getSubPackagesOf(fqName2, nameFilter));
        }
        return result2;
    }

    public CompositePackageFragmentProvider(@NotNull List<? extends PackageFragmentProvider> providers) {
        Intrinsics.checkNotNullParameter(providers, "providers");
        this.providers = providers;
    }
}

