/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResolutionScope {
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name var1, @NotNull LookupLocation var2);

    @NotNull
    public Collection<? extends FunctionDescriptor> getContributedFunctions(@NotNull Name var1, @NotNull LookupLocation var2);

    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter var1, @NotNull Function1<? super Name, Boolean> var2);

    public void recordLookup(@NotNull Name var1, @NotNull LookupLocation var2);

    public static final class DefaultImpls {
        public static /* synthetic */ Collection getContributedDescriptors$default(ResolutionScope resolutionScope, DescriptorKindFilter descriptorKindFilter, Function1 function1, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getContributedDescriptors");
            }
            if ((n & 1) != 0) {
                descriptorKindFilter = DescriptorKindFilter.ALL;
            }
            if ((n & 2) != 0) {
                function1 = MemberScope.Companion.getALL_NAME_FILTER();
            }
            return resolutionScope.getContributedDescriptors(descriptorKindFilter, function1);
        }

        public static void recordLookup(@NotNull ResolutionScope $this, @NotNull Name name, @NotNull LookupLocation location) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            $this.getContributedFunctions(name, location);
        }
    }
}

