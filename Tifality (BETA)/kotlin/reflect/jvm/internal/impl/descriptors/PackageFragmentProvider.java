/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public interface PackageFragmentProvider {
    @NotNull
    public List<PackageFragmentDescriptor> getPackageFragments(@NotNull FqName var1);

    @NotNull
    public Collection<FqName> getSubPackagesOf(@NotNull FqName var1, @NotNull Function1<? super Name, Boolean> var2);
}

