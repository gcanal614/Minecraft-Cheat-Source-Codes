/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;

public interface PackageViewDescriptor
extends DeclarationDescriptor {
    @NotNull
    public FqName getFqName();

    @NotNull
    public MemberScope getMemberScope();

    @NotNull
    public ModuleDescriptor getModule();

    @NotNull
    public List<PackageFragmentDescriptor> getFragments();

    public boolean isEmpty();

    public static final class DefaultImpls {
        public static boolean isEmpty(@NotNull PackageViewDescriptor $this) {
            return $this.getFragments().isEmpty();
        }
    }
}

