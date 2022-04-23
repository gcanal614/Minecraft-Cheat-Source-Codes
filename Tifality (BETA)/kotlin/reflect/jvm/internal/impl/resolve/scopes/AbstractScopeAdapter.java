/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractScopeAdapter
implements MemberScope {
    @NotNull
    protected abstract MemberScope getWorkerScope();

    @NotNull
    public final MemberScope getActualScope() {
        MemberScope memberScope2;
        if (this.getWorkerScope() instanceof AbstractScopeAdapter) {
            MemberScope memberScope3 = this.getWorkerScope();
            if (memberScope3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.AbstractScopeAdapter");
            }
            memberScope2 = ((AbstractScopeAdapter)memberScope3).getActualScope();
        } else {
            memberScope2 = this.getWorkerScope();
        }
        return memberScope2;
    }

    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.getWorkerScope().getContributedFunctions(name, location);
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.getWorkerScope().getContributedClassifier(name, location);
    }

    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.getWorkerScope().getContributedVariables(name, location);
    }

    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.getWorkerScope().getContributedDescriptors(kindFilter, nameFilter);
    }

    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        return this.getWorkerScope().getFunctionNames();
    }

    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        return this.getWorkerScope().getVariableNames();
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        return this.getWorkerScope().getClassifierNames();
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.getWorkerScope().recordLookup(name, location);
    }
}

