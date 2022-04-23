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
import java.util.List;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeKt;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChainedMemberScope
implements MemberScope {
    private final String debugName;
    private final MemberScope[] scopes;
    public static final Companion Companion = new Companion(null);

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        ClassifierDescriptor classifierDescriptor;
        block2: {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            MemberScope[] scopes$iv = this.scopes;
            boolean $i$f$getFirstClassifierDiscriminateHeaders = false;
            ClassifierDescriptor result$iv = null;
            MemberScope[] memberScopeArray = scopes$iv;
            int n = memberScopeArray.length;
            for (int i = 0; i < n; ++i) {
                MemberScope scope$iv;
                MemberScope it = scope$iv = memberScopeArray[i];
                boolean bl = false;
                ClassifierDescriptor newResult$iv = it.getContributedClassifier(name, location);
                if (newResult$iv == null) continue;
                if (newResult$iv instanceof ClassifierDescriptorWithTypeParameters && ((ClassifierDescriptorWithTypeParameters)newResult$iv).isExpect()) {
                    if (result$iv != null) continue;
                    result$iv = newResult$iv;
                    continue;
                }
                classifierDescriptor = newResult$iv;
                break block2;
            }
            classifierDescriptor = result$iv;
        }
        return classifierDescriptor;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Collection<? extends PropertyDescriptor> collection;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope[] scopes$iv = this.scopes;
        boolean $i$f$getFromAllScopes = false;
        switch (scopes$iv.length) {
            case 0: {
                collection = CollectionsKt.emptyList();
                break;
            }
            case 1: {
                MemberScope it = scopes$iv[0];
                boolean bl = false;
                collection = it.getContributedVariables(name, location);
                break;
            }
            default: {
                Collection<? extends PropertyDescriptor> result$iv = null;
                MemberScope[] memberScopeArray = scopes$iv;
                int n = memberScopeArray.length;
                for (int i = 0; i < n; ++i) {
                    void it;
                    MemberScope scope$iv;
                    MemberScope memberScope2 = scope$iv = memberScopeArray[i];
                    Collection<? extends PropertyDescriptor> collection2 = result$iv;
                    boolean bl = false;
                    Collection<? extends PropertyDescriptor> collection3 = it.getContributedVariables(name, location);
                    result$iv = ScopeUtilsKt.concat(collection2, collection3);
                }
                collection = result$iv;
                if (collection != null) break;
                collection = SetsKt.emptySet();
            }
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Collection<? extends SimpleFunctionDescriptor> collection;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope[] scopes$iv = this.scopes;
        boolean $i$f$getFromAllScopes = false;
        switch (scopes$iv.length) {
            case 0: {
                collection = CollectionsKt.emptyList();
                break;
            }
            case 1: {
                MemberScope it = scopes$iv[0];
                boolean bl = false;
                collection = it.getContributedFunctions(name, location);
                break;
            }
            default: {
                Collection<? extends SimpleFunctionDescriptor> result$iv = null;
                MemberScope[] memberScopeArray = scopes$iv;
                int n = memberScopeArray.length;
                for (int i = 0; i < n; ++i) {
                    void it;
                    MemberScope scope$iv;
                    MemberScope memberScope2 = scope$iv = memberScopeArray[i];
                    Collection<? extends SimpleFunctionDescriptor> collection2 = result$iv;
                    boolean bl = false;
                    Collection<? extends SimpleFunctionDescriptor> collection3 = it.getContributedFunctions(name, location);
                    result$iv = ScopeUtilsKt.concat(collection2, collection3);
                }
                collection = result$iv;
                if (collection != null) break;
                collection = SetsKt.emptySet();
            }
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Collection<DeclarationDescriptor> collection;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        MemberScope[] scopes$iv = this.scopes;
        boolean $i$f$getFromAllScopes = false;
        switch (scopes$iv.length) {
            case 0: {
                collection = CollectionsKt.emptyList();
                break;
            }
            case 1: {
                MemberScope it = scopes$iv[0];
                boolean bl = false;
                collection = it.getContributedDescriptors(kindFilter, nameFilter);
                break;
            }
            default: {
                Collection<DeclarationDescriptor> result$iv = null;
                MemberScope[] memberScopeArray = scopes$iv;
                int n = memberScopeArray.length;
                for (int i = 0; i < n; ++i) {
                    void it;
                    MemberScope scope$iv;
                    MemberScope memberScope2 = scope$iv = memberScopeArray[i];
                    Collection<DeclarationDescriptor> collection2 = result$iv;
                    boolean bl = false;
                    Collection<DeclarationDescriptor> collection3 = it.getContributedDescriptors(kindFilter, nameFilter);
                    result$iv = ScopeUtilsKt.concat(collection2, collection3);
                }
                collection = result$iv;
                if (collection != null) break;
                collection = SetsKt.emptySet();
            }
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        void var2_3;
        void $this$flatMapTo$iv;
        MemberScope[] memberScopeArray = this.scopes;
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$flatMapTo = false;
        void var4_5 = $this$flatMapTo$iv;
        int n = ((void)var4_5).length;
        for (int i = 0; i < n; ++i) {
            void element$iv;
            void it = element$iv = var4_5[i];
            boolean bl2 = false;
            Iterable list$iv = it.getFunctionNames();
            CollectionsKt.addAll(destination$iv, list$iv);
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
        void $this$flatMapTo$iv;
        MemberScope[] memberScopeArray = this.scopes;
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$flatMapTo = false;
        void var4_5 = $this$flatMapTo$iv;
        int n = ((void)var4_5).length;
        for (int i = 0; i < n; ++i) {
            void element$iv;
            void it = element$iv = var4_5[i];
            boolean bl2 = false;
            Iterable list$iv = it.getVariableNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (Set)var2_3;
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        return MemberScopeKt.flatMapClassifierNamesOrNull(ArraysKt.asIterable(this.scopes));
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope[] $this$forEach$iv = this.scopes;
        boolean $i$f$forEach = false;
        MemberScope[] memberScopeArray = $this$forEach$iv;
        int n = memberScopeArray.length;
        for (int i = 0; i < n; ++i) {
            MemberScope element$iv;
            MemberScope it = element$iv = memberScopeArray[i];
            boolean bl = false;
            it.recordLookup(name, location);
        }
    }

    @NotNull
    public String toString() {
        return this.debugName;
    }

    private ChainedMemberScope(String debugName, MemberScope[] scopes) {
        this.debugName = debugName;
        this.scopes = scopes;
    }

    public /* synthetic */ ChainedMemberScope(String debugName, MemberScope[] scopes, DefaultConstructorMarker $constructor_marker) {
        this(debugName, scopes);
    }

    public static final class Companion {
        @NotNull
        public final MemberScope create(@NotNull String debugName, @NotNull Iterable<? extends MemberScope> scopes) {
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            Intrinsics.checkNotNullParameter(scopes, "scopes");
            SmartList<MemberScope> flattenedNonEmptyScopes = new SmartList<MemberScope>();
            for (MemberScope memberScope2 : scopes) {
                if (memberScope2 == MemberScope.Empty.INSTANCE) continue;
                if (memberScope2 instanceof ChainedMemberScope) {
                    CollectionsKt.addAll((Collection)flattenedNonEmptyScopes, ((ChainedMemberScope)memberScope2).scopes);
                    continue;
                }
                flattenedNonEmptyScopes.add(memberScope2);
            }
            return this.createOrSingle$descriptors(debugName, (List<? extends MemberScope>)flattenedNonEmptyScopes);
        }

        @NotNull
        public final MemberScope createOrSingle$descriptors(@NotNull String debugName, @NotNull List<? extends MemberScope> scopes) {
            MemberScope memberScope2;
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            Intrinsics.checkNotNullParameter(scopes, "scopes");
            switch (scopes.size()) {
                case 0: {
                    memberScope2 = MemberScope.Empty.INSTANCE;
                    break;
                }
                case 1: {
                    memberScope2 = scopes.get(0);
                    break;
                }
                default: {
                    Collection $this$toTypedArray$iv = scopes;
                    boolean $i$f$toTypedArray = false;
                    Collection thisCollection$iv = $this$toTypedArray$iv;
                    MemberScope[] memberScopeArray = thisCollection$iv.toArray(new MemberScope[0]);
                    if (memberScopeArray == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                    memberScope2 = new ChainedMemberScope(debugName, memberScopeArray, null);
                }
            }
            return memberScope2;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

