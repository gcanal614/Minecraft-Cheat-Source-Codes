/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt;
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
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MemberScopeImpl
implements MemberScope {
    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    @Override
    @NotNull
    public Collection<? extends PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.emptyList();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        void var2_3;
        void $this$filterIsInstanceMapTo$iv;
        Iterable iterable = this.getContributedDescriptors(DescriptorKindFilter.FUNCTIONS, FunctionsKt.alwaysTrue());
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$filterIsInstanceMapTo = false;
        for (Object element$iv : $this$filterIsInstanceMapTo$iv) {
            Name name;
            void it;
            if (!(element$iv instanceof SimpleFunctionDescriptor)) continue;
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)element$iv;
            Collection collection = destination$iv;
            boolean bl2 = false;
            Intrinsics.checkNotNullExpressionValue(it.getName(), "it.name");
            collection.add(name);
        }
        return (Set)var2_3;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        void var2_3;
        void $this$filterIsInstanceMapTo$iv;
        Iterable iterable = this.getContributedDescriptors(DescriptorKindFilter.VARIABLES, FunctionsKt.alwaysTrue());
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$filterIsInstanceMapTo = false;
        for (Object element$iv : $this$filterIsInstanceMapTo$iv) {
            Name name;
            void it;
            if (!(element$iv instanceof SimpleFunctionDescriptor)) continue;
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)element$iv;
            Collection collection = destination$iv;
            boolean bl2 = false;
            Intrinsics.checkNotNullExpressionValue(it.getName(), "it.name");
            collection.add(name);
        }
        return (Set)var2_3;
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        return null;
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope.DefaultImpls.recordLookup(this, name, location);
    }
}

