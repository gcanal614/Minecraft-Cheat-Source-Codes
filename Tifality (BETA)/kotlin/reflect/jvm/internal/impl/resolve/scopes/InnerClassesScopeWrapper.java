/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InnerClassesScopeWrapper
extends MemberScopeImpl {
    @NotNull
    private final MemberScope workerScope;

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor classifierDescriptor = this.workerScope.getContributedClassifier(name, location);
        if (classifierDescriptor != null) {
            ClassifierDescriptor classifierDescriptor2 = classifierDescriptor;
            boolean bl = false;
            boolean bl2 = false;
            ClassifierDescriptor it = classifierDescriptor2;
            boolean bl3 = false;
            ClassifierDescriptor classifierDescriptor3 = it;
            if (!(classifierDescriptor3 instanceof ClassDescriptor)) {
                classifierDescriptor3 = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor3;
            if (classDescriptor != null) {
                classifierDescriptorWithTypeParameters = classDescriptor;
            } else {
                ClassifierDescriptor classifierDescriptor4 = it;
                if (!(classifierDescriptor4 instanceof TypeAliasDescriptor)) {
                    classifierDescriptor4 = null;
                }
                classifierDescriptorWithTypeParameters = (TypeAliasDescriptor)classifierDescriptor4;
            }
        } else {
            classifierDescriptorWithTypeParameters = null;
        }
        return classifierDescriptorWithTypeParameters;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ClassifierDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        DescriptorKindFilter descriptorKindFilter = kindFilter.restrictedToKindsOrNull(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK());
        if (descriptorKindFilter == null) {
            boolean bl = false;
            return CollectionsKt.emptyList();
        }
        DescriptorKindFilter restrictedFilter = descriptorKindFilter;
        Iterable $this$filterIsInstance$iv = this.workerScope.getContributedDescriptors(restrictedFilter, nameFilter);
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof ClassifierDescriptorWithTypeParameters)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        return this.workerScope.getFunctionNames();
    }

    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        return this.workerScope.getVariableNames();
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        return this.workerScope.getClassifierNames();
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.workerScope.recordLookup(name, location);
    }

    @NotNull
    public String toString() {
        return "Classes from " + this.workerScope;
    }

    public InnerClassesScopeWrapper(@NotNull MemberScope workerScope) {
        Intrinsics.checkNotNullParameter(workerScope, "workerScope");
        this.workerScope = workerScope;
    }
}

