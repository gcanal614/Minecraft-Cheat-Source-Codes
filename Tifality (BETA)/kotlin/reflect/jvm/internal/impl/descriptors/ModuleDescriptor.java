/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ModuleDescriptor
extends DeclarationDescriptor {
    @NotNull
    public KotlinBuiltIns getBuiltIns();

    public boolean shouldSeeInternalsOf(@NotNull ModuleDescriptor var1);

    @NotNull
    public PackageViewDescriptor getPackage(@NotNull FqName var1);

    @NotNull
    public Collection<FqName> getSubPackagesOf(@NotNull FqName var1, @NotNull Function1<? super Name, Boolean> var2);

    @NotNull
    public List<ModuleDescriptor> getExpectedByModules();

    @Nullable
    public <T> T getCapability(@NotNull Capability<T> var1);

    public static final class Capability<T> {
        @NotNull
        private final String name;

        @NotNull
        public String toString() {
            return this.name;
        }

        public Capability(@NotNull String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
        }
    }

    public static final class DefaultImpls {
        @Nullable
        public static DeclarationDescriptor getContainingDeclaration(@NotNull ModuleDescriptor $this) {
            return null;
        }

        public static <R, D> R accept(@NotNull ModuleDescriptor $this, @NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
            Intrinsics.checkNotNullParameter(visitor2, "visitor");
            return visitor2.visitModuleDeclaration($this, data2);
        }
    }
}

